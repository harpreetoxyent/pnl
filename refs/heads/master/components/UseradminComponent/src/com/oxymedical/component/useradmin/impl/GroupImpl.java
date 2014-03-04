/**
 * @author Oxyent Medical Ltd, India
 * 
 * No part of this Source may be copied
 * without Oxyent's prior written permission.
 * Copyright 2007 Oxyent Medical Ltd, All Rights Reserved.
 * 
 *  Version 1.0.0
 */
package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IGroup;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.impl.ldap.LdapImpl;
import com.oxymedical.component.useradmin.model.Group;
import com.oxymedical.component.useradmin.model.GroupsRole;
import com.oxymedical.component.useradmin.model.GroupsRolePK;
import com.oxymedical.component.useradmin.model.Organization;
import com.oxymedical.component.useradmin.model.User;
import com.oxymedical.component.useradmin.model.UsersGroup;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.component.useradmin.util.CounterUtil;

/**
 * This class is an implementation of Group Pojo class,which performs the various database
 * operations i.e add, delete, retrieve. 
 * @author OxyentTechnologies.
 *
 */

public class GroupImpl implements IGroup
{
	private LdapImpl ldapImpl = null;
	CounterUtil counterUtil = null;
	User user = null;
	GroupImpl groupImpl = null;
	RoleImpl roleImpl = null;
	GroupsRole groupsRole = null;
	GroupsRolePK groupsRolePK = null;
	LayoutSetImpl layoutSetImpl = null;
	UserImpl userImpl =null;

	/**
	 * This function is used to add the group to the user admin database .Before adding it validates 
	 * the user,retrieves the companyId for the vaildated user , increments the groupId and saves the object.
	 * @param  userId , name , description , type
	 * @return  addGroup Returns Group Object.
	 * @exception Throws NoSuchUSerException if UserId is not validated.
	 */
	//Change done by netram sahu on 10-Apr-2012
	//Added one parameter companyId
	public Group addGroup(String userId,String className , String classPK,String groupId,String companyId, String description, String type) throws NoSuchUserException,NoSuchGroupException,UAComponentException,DBComponentException
	{
		Group group = new Group();
		counterUtil = new CounterUtil();
		layoutSetImpl = new LayoutSetImpl();
		userImpl =  new UserImpl();
		CompanyImpl companyImpl = new CompanyImpl();
		GroupImpl groupImpl = new GroupImpl();
		
		try
		{
		boolean validateGroup = groupImpl.validateGroup(groupId);;
		}
		catch (NoSuchGroupException ex)	{
		group.setGroupId(groupId);
		group.setCompanyId(companyId);
		group.setDescription(description);
		group.setClassName(className);
		group.setClassPk(classPK);
		group.setName(userId);
		group.setParentGroupId(Constants.DEFAULT_PARENT_GROUP_ID);
		group.setType(type);

		ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(group);
		//			 Layout sets
		
		layoutSetImpl.addLayoutSet(Constants.PRIVATE + groupId, group.getCompanyId());

		layoutSetImpl.addLayoutSet(Constants.PUBLIC + groupId, group.getCompanyId());
		//userImpl.addGroupUsers(group.getGroupId(), new String[] {userId});
		}
		//End Changes
		return group;
	}
	
	public void deleteGroup(String companyId , String groupName , boolean deleteUser) throws UAComponentException
	{
		Group group = null;    
		String groupId = null;
		String sqlQuery = null;
		String delSqlQuery = null;
		String className = null;
		String userId = null;
		ArrayList<NameQueryParameter> listParam = null;
		List<UsersGroup> userGroupList = null;
		NameQueryParameter Id = null;
		NameQueryParameter classname = null;
		Hashtable<String , String> ldapGroupInfo = null;
		try
		{
			group  = getGroupByCompanyAndName(companyId , groupName);
			groupId = group.getGroupId();
			className = group.getClassName();
			if(deleteUser)
			{
				sqlQuery = SQLCommands.FIND_User_Group;
				listParam = new ArrayList<NameQueryParameter>();
				Id = new NameQueryParameter(Constants.groupId, groupId);
				listParam.add(Id);
				userGroupList = ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
				delSqlQuery = SQLCommands.Clear_User_Group;
				for(int userCounter = 0; userCounter < userGroupList.size() ; userCounter++)
				{
					userId = userGroupList.get(userCounter).getComp_id().getUserId();
					listParam = new ArrayList<NameQueryParameter>();
					Id = new NameQueryParameter(Constants.userId, userId);
					listParam.add(Id);
					ConnectionDatabase.GetInstanceOfDatabaseComponent().executeDMLQueryWithNameParameter(sqlQuery, listParam);
				}

			}
			sqlQuery = SQLCommands.Clear_Group;
			listParam = new ArrayList<NameQueryParameter>();
			Id = new NameQueryParameter(Constants.groupId, groupId);
			listParam.add(Id);
			classname = new NameQueryParameter(Constants.className, className);
			listParam.add(classname);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().executeDMLQueryWithNameParameter(sqlQuery,listParam);			

			ldapImpl = new LdapImpl();
			ldapGroupInfo = new Hashtable<String,String>();
			ldapGroupInfo.put("type", "group");
			ldapGroupInfo.put("name", groupName);
			ldapGroupInfo.put("parentname", companyId);
			ldapImpl.deleteLDAPEntry(ldapGroupInfo);
		}
		catch(DBComponentException e)
		{
			e.printStackTrace();
			throw new UAComponentException(e);
		}
	}

	public Group addGroupByCompany(String companyId , String className , String classPK,String name, String description, String type , boolean isLdap) throws NoSuchUserException,NoSuchGroupException,UAComponentException,DBComponentException
	{
		Group group = null;
		boolean newGroup = false;
		String groupId = null;
		Hashtable<String,String> ldapGroupInfo = null;
		counterUtil = new CounterUtil();
		layoutSetImpl = new LayoutSetImpl();


		if(null == name)
		{
			throw (new UAComponentException("Name is required"));
		}
		try
		{
			group  = getGroupByCompanyAndName(companyId , name);
			group.setDescription(description);
			group.setParentGroupId(Constants.DEFAULT_PARENT_GROUP_ID);
			group.setType(type);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(group);
		}
		catch(NoSuchGroupException ex)
		{
			newGroup= true;
			
			//Change done by netram sahu on 17-Apr-2012
            //Auto increment for GroupId is not required,because group name is validate by groupId 
			groupId = String.valueOf(counterUtil.incrementGroupId(GroupImpl.class.getName().toString()));
			//groupId=name;
			//End Changes
			
			group = new Group();
			group.setGroupId(groupId);
			group.setCompanyId(companyId);
			group.setDescription(description);
			group.setClassName(className);
			group.setClassPk(classPK);
			group.setName(name);
			group.setParentGroupId(Constants.DEFAULT_PARENT_GROUP_ID);
			group.setType(type);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(group);
		}
		if(newGroup && isLdap)
		{
			try
			{
				ldapGroupInfo = new Hashtable<String,String>();
				ldapImpl = new LdapImpl();
				//ldapCompanyInfo = new Hashtable<String,String>();
				ldapGroupInfo.put("type", "group");
				ldapGroupInfo.put("name", name);
				ldapGroupInfo.put("parentname", companyId);
				ldapImpl.addLDAPEntry(ldapGroupInfo);
			}
			catch(Exception e)
			{
				UserAdminComponent.logger.log(0,"---In Group Impl---exception while setting ldap info----");
			}

		}
		UserAdminComponent.logger.log(0,"----return group--"+group.getName());	
		return group;
	}
	/**
	 * This function is used to assign a role to various groups  
	 * Before assigning , it validates the role and group
	 * @param  roleId , groupIds[]
	 * @return  addRoleGroups Returns void.
	 * @exception Throws NoSuchRoleException and NoSuchGroupException if roleId and groupId is not validated.
	 */

	public void addRoleGroups(String roleId, String[] groupIds) throws NoSuchRoleException , NoSuchGroupException,DBComponentException
	{
		groupImpl = new GroupImpl();
		roleImpl = new RoleImpl();
		groupsRole = new GroupsRole();
		groupsRolePK = new GroupsRolePK();
		boolean validateRole = false;
		boolean validateGroup = false;
		for (int i = 0; i < groupIds.length; i++) 
		{
			validateGroup = groupImpl.validateGroup(groupIds[i]);
			validateRole = roleImpl.validateRole(roleId);
			if((validateGroup == true) && (validateRole == true))
			{
				groupsRolePK.setGroupId(groupIds[i]);							
				groupsRolePK.setRoleId(roleId);
				groupsRole.setComp_id(groupsRolePK);
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(groupsRole);
			}					
		}



	}


	public void deleteGroup(String groupId) 
	{


	}
	/**
	 * This function is used to retrieve a group with the specified groupId  
	 * @param  groupId
	 * @return  getGroup Returns Group object.
	 * @exception Throws  NoSuchGroupException if groupId is not validated.
	 */
	public Group getGroup(String groupId) throws NoSuchGroupException
	{
		Group group = new Group();

		try
		{

			group.setGroupId(groupId);
			String sqlQuery = SQLCommands.Select_Group;

			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter(Constants.groupId, groupId);
			listParam.add(Id);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if (list != null)
			{
				if (list.size() > 0 )
				{	
					for(Iterator groupRow=list.iterator();groupRow.hasNext();)
					{
						Object groupRowData = groupRow.next();
						group = (Group)groupRowData;
					}
					//					UserAdminComponent.logger.log(0,"Group Exists");

				}
				else 
				{
					//					UserAdminComponent.logger.log(0,"Group does not Exists");
					throw (new NoSuchGroupException("No group with this id Found"));
				}

			}	

		}
		catch(DBComponentException e)
		{
			e.printStackTrace();
		}
		return group;
	}


	/**
	 * This function is used to retrieve a group with the specified companyId and name of Group  
	 * @param  companyId,name
	 * @return  getGroup Returns Group object.
	 * @exception Throws  NoSuchGroupException if no group existes for given companyId and name
	 */
	public Group getGroup(String companyId, String name) throws NoSuchGroupException
	{
		Group group = null;
		try
		{

			String sqlQuery = SQLCommands.Select_Group_C_N;

			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter(Constants.companyId, companyId);
			NameQueryParameter nameId =  new NameQueryParameter(Constants.name, name);
			listParam.add(Id);
			listParam.add(nameId);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if (list != null)
			{
				if (list.size() > 0 )
				{	
					for(Iterator groupRow=list.iterator();groupRow.hasNext();)
					{
						Object groupRowData = groupRow.next();
						group = (Group)groupRowData;
					}
					//					UserAdminComponent.logger.log(0,"Group Exists");

				}
				else 
				{
					//					UserAdminComponent.logger.log(0,"Group does not Exists");
					throw (new NoSuchGroupException("No group with this comapnyId " + companyId +" and name " + name + " exists"));
				}

			}	

		}
		catch(DBComponentException e)
		{
			e.printStackTrace();
		}
		return group;
	}
	/**
	 * This function is used to retrieve all group   
	 * @param  
	 * @return  list.
	 * @exception Throws  NoSuchGroupException if no group existes for given companyId and name
	 */
	
	//Added getAllGroups method by madhusudhan.
	
	public List getAllGroups() throws NoSuchGroupException
	{
		Group group = new Group();
		List list = null;
		try
		{
			String sqlQuery = SQLCommands.Select_Groups;
			list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,null);			
			if (list != null)
			{
				if (list.size() > 0 )
				{	
					for(Iterator groupRow=list.iterator();groupRow.hasNext();)
					{
						Object groupRowData = groupRow.next();
						group = (Group)groupRowData;
					}
//					UserAdminComponent.logger.log(0,"Group Exists");
					
				}
				else 
				{
//					UserAdminComponent.logger.log(0,"Group does not Exists");
					throw (new NoSuchGroupException("No group with this id Found"));
				}
				
			}
			
		}
		catch(DBComponentException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * This function is used to retrieve a group with the specified companyId and organizationId 
	 * @param  companyId,organizationId
	 * @return  getGroup Returns Group object.
	 * @exception Throws  NoSuchGroupException if no group existes for given companyId and organizationId
	 */
	public Group getOrganizationGroup(String companyId, String organizationId)throws NoSuchGroupException,DBComponentException
	{
		Group group = null;
		String sqlQuery = SQLCommands.Get_Organization_Group;

		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
		NameQueryParameter className =  new NameQueryParameter(Constants.className, Organization.class.getName());
		NameQueryParameter classPk =  new NameQueryParameter(Constants.classPk, organizationId);
		listParam.add(compId);
		listParam.add(className);
		listParam.add(classPk);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list != null)
		{
			if (list.size() > 0 )
			{	
				for(Iterator groupRow=list.iterator();groupRow.hasNext();)
				{
					Object groupRowData = groupRow.next();
					group = (Group)groupRowData;
				}
				//				UserAdminComponent.logger.log(0,"Group Exists");

			}
			else 
			{
				//				UserAdminComponent.logger.log(0,"Group does not Exists");
				throw (new NoSuchGroupException("No such group with this companyId " + companyId + " exists"));
			}

		}	

		return group;
	}


	public List getRoleGroups(String roleId) throws DBComponentException,NoSuchGroupException, NoSuchRoleException
	{
		RoleImpl roleImpl = new RoleImpl();
		Group group = null;
		Boolean isRole = roleImpl.validateRole(roleId);
		List list = null;
		if(isRole)
		{
			String sqlQuery = SQLCommands.Get_Group_Roles;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();

			NameQueryParameter groupRoleId = new NameQueryParameter(Constants.roleId, roleId);
			listParam.add(groupRoleId);
			list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if (list != null)
			{
				if (list.size() > 0 )
				{	
					for(Iterator groupRow=list.iterator();groupRow.hasNext();)
					{
						Object groupRowData = groupRow.next();
						group = (Group)groupRowData;
					}
					//					UserAdminComponent.logger.log(0,"Group Exists");

				}
				else 
				{
					//					UserAdminComponent.logger.log(0,"Group does not Exists");
					throw (new NoSuchGroupException("No such group with this roleId " + roleId + " exists"));
				}
			}
		}	
		return list;
	}

	public Group getUserGroup(String companyId, String userId) throws UAComponentException,NoSuchCompanyException, DBComponentException, NoSuchUserException, NoSuchGroupException 
	{
		Group group = null;
		CompanyImpl companyImpl = new CompanyImpl();
		UserImpl userImpl = new UserImpl();

		boolean validateCompany = companyImpl.validateCompany(companyId);
		boolean validateUser = userImpl.validateUser(userId);

		if((validateCompany)&&(validateUser))
		{
			String sqlQuery = SQLCommands.Get_User_Group;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
			NameQueryParameter className =  new NameQueryParameter(Constants.className, User.class.getName());
			NameQueryParameter classPk =  new NameQueryParameter(Constants.classPk, companyId +Constants.DOT+userId);
			listParam.add(compId);
			listParam.add(className);
			listParam.add(classPk);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if (list != null)
			{
				if (list.size() > 0 )
				{	
					for(Iterator groupRow=list.iterator();groupRow.hasNext();)
					{
						Object groupRowData = groupRow.next();
						group = (Group)groupRowData;
					}

				}
				else 
				{
					//					UserAdminComponent.logger.log(0,"Group does not Exists");
					throw (new NoSuchGroupException("No such group with this companyId " + companyId + " exists"));
				}

			}	

		}

		return group;
	}

	public Group getUserGroupGroup(String companyId, String userGroupId)
	{
		Group group = null;
		return group;
	}

	public boolean hasRoleGroup(String roleId, String groupId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasUserGroup(String userId, String groupId) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public Group updateGroup(String groupId, String name, String description, String type, String friendlyURL)
	{
		Group group = null;		
		return group;
	}

	public boolean validateGroup(String groupId)throws NoSuchGroupException
	{
		boolean resultGroup = false;
		Group group = null;
		try
		{


			String sqlQuery = SQLCommands.Validate_Group;

			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter(Constants.groupId, groupId);
			listParam.add(Id);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if (list != null)
			{
				if (list.size() > 0 )
				{	
					//					UserAdminComponent.logger.log(0,"Group Exists");
					resultGroup =  true;
				}
				else 
				{
					//					UserAdminComponent.logger.log(0,"Group does not Exists");
					resultGroup =  false;		
					throw (new NoSuchGroupException("No group with this id Found"));
				}

			}	

		}
		catch(DBComponentException e)
		{
			e.printStackTrace();
		}
		return resultGroup;
	}

	public Group getGroupByCompanyAndName(String companyId , String name)throws NoSuchGroupException,DBComponentException, NoSuchCompanyException
	{
		Group group = null;
		String sqlQuery = SQLCommands.Find_Group_From_Company_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(compId);
		NameQueryParameter orgName = new NameQueryParameter(Constants.name, name);
		listParam.add(orgName);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list == null || (list.size()==0))
		{
			CompanyImpl companyImpl = new CompanyImpl();
			Boolean isCompany = companyImpl.validateCompany(companyId);
			if(isCompany)
			{
				throw (new NoSuchGroupException("No Group with this companyID " + companyId + " and " +name + " found"));
			}
			// if it does not
			else
			{
				throw (new NoSuchCompanyException(companyId + "No such Company exits"));
			}
		}
		else
		{
			for(Iterator groupRow=list.iterator();groupRow.hasNext();)
			{
				Object groupRowData = groupRow.next();
				group = (Group)groupRowData;
			}
		}

		return group;

	}


	public static void main(String args[])
	{
		Group group = new Group(); 
		GroupImpl impl = new GroupImpl();
		//String[] groupIds = {"0","1"};
		try {
			//impl.addRoleGroups("1", groupIds);
			//group = impl.getGroup("0");
			//impl.addGroup("1", GroupImpl.class.getName().toString(), "", "RavneetGroup", "Test Add Group", "Good");
			//group = impl.getGroup("stoneone.com", "1");
			//group = impl.getOrganizationGroup("Oxyent.com", "0");
			//impl.addRoleGroups("1", groupIds);
			//List list = impl.getRoleGroups("1");
			group = impl.getUserGroup("Oxyent.com", "0");


		}catch (DBComponentException e) {
			// TODO Auto-generated catch block

		} catch (NoSuchGroupException e) {
			// TODO Auto-generated catch block

		}
		catch (NoSuchUserException e) {
			// TODO Auto-generated catch block

		} catch (NoSuchCompanyException e) {
			// TODO Auto-generated catch block

		}
		catch (UAComponentException e) {
			// TODO Auto-generated catch block

		}

	}
}

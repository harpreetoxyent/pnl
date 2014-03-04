package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IRole;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.component.useradmin.util.CounterUtil;
public class RoleImpl implements IRole
{

	
	public Role addRole(String userId, String companyId, String name,String defaultURL) throws DBComponentException,NoSuchCompanyException
	{
		boolean isCompany = false;
		
		Role role = null;
		CompanyImpl companyImpl = new CompanyImpl();
		isCompany = companyImpl.validateCompany(companyId);
		if(isCompany)
		{
			role = new Role();
			CounterUtil counterUtil = new CounterUtil();
			String roleId = String.valueOf(counterUtil.incrementRoleId(RoleImpl.class.getName().toString()));
			role.setRoleId(roleId);
			role.setCompanyId(companyId);
			role.setName(name);
			role.setDefaultUrl(defaultURL);
			
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(role);
		}
		UserAdminComponent.logger.log(0,"---Role saved just returned ----"+role.getName());
		return role;
	}

	public Role addRole(String companyId, String name,String defaultURL) throws DBComponentException,NoSuchCompanyException
	{
		boolean isCompany = false;
		
		Role role = null;
		CompanyImpl companyImpl = new CompanyImpl();
		isCompany = companyImpl.validateCompany(companyId);
		try
		{
			role = getRolebyCompanyAndName(companyId,name);
			
		}
		catch(NoSuchRoleException ex)
		{
			if(isCompany)
			{
				role = new Role();
				CounterUtil counterUtil = new CounterUtil();
				String roleId = String.valueOf(counterUtil.incrementRoleId(RoleImpl.class.getName().toString()));
				role.setRoleId(roleId);
				role.setCompanyId(companyId);
				role.setName(name);
				role.setDefaultUrl(defaultURL);
				
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(role);
			}
		}
		return role;
	}
	public Role addRole(String userId, String companyId, String name, String className, String classPK,String defaultURL)throws DBComponentException,NoSuchCompanyException
	{
		boolean isCompany = false;
		
		Role role = new Role();
		CompanyImpl companyImpl = new CompanyImpl();
		isCompany = companyImpl.validateCompany(companyId);
		if(isCompany)
		{
			CounterUtil counterUtil = new CounterUtil();
			String roleId = String.valueOf(counterUtil.incrementOrganizationId(RoleImpl.class.getName().toString()));
			classPK = roleId;
			role.setRoleId(roleId);
			role.setCompanyId(companyId);
			role.setClassName(Role.class.getName());
			role.setClassPk(classPK);
			role.setName(name);
			role.setDefaultUrl(defaultURL);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(role);
			
		}
		
		
		return role;
	}

	public void deleteRole(String roleId)
	{
		// TODO Auto-generated method stub
		
	}

	public Role getGroupRole(String companyId, String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Role getRole(String roleId) throws DBComponentException ,UAComponentException
	{
			Role role = null;
			String sqlQuery = SQLCommands.Select_Role;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter(Constants.roleId, roleId);
			listParam.add(Id);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if ((list == null) || (list.size()== 0))
			{
				throw (new UAComponentException("role with " + roleId + " does not exists"));
			}	
			else 
			{
				for(Iterator roleRow=list.iterator();roleRow.hasNext();)
				{
					Object roleRowRowData= roleRow.next();
					role = (Role)roleRowRowData;
					
				}		
			}
		
			
			
			
			return role;
		
	}

	public Role getRole(String companyId, String name) 
	{
		return null;
	}

	public List getUserRelatedRoles(String userId, List groups) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getUserRoles(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasUserRole(String userId, String companyId, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasUserRoles(String userId, String companyId, String[] names) {
		// TODO Auto-generated method stub
		return false;
	}

	public Role updateRole(String roleId, String name) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean validateRole(String roleId)throws NoSuchRoleException
	{
		boolean resultRole = false;
		try
		{
			
			String sqlQuery = SQLCommands.Validate_Role;
			
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter Id = new NameQueryParameter("roleId", roleId);
			listParam.add(Id);
			List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
			if (list != null)
			{
				if (list.size() > 0 )
				{	
//					UserAdminComponent.logger.log(0,"Role Exists");
					resultRole =  true;
				}
				else 
				{
//					UserAdminComponent.logger.log(0,"Role does not Exists");
					resultRole =  false;		
					throw (new NoSuchRoleException("No role with this id Found"));
				}
				
			}	
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultRole;
	}
	
	
	public Role getRolebyCompanyAndName(String companyId , String name)throws NoSuchRoleException,DBComponentException
	{
		Role role = null;
		String sqlQuery = SQLCommands.Find_Role_From_Company_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter compId = new NameQueryParameter(Constants.companyId, companyId);
		listParam.add(compId);
		NameQueryParameter orgName = new NameQueryParameter(Constants.name, name);
		listParam.add(orgName);
		List list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);			
		if (list == null || (list.size()==0))
		{
			throw (new NoSuchRoleException("No Role with this companyID " + companyId + " and " +name + " found"));
		}
		else
		{
			for(Iterator roleRow=list.iterator();roleRow.hasNext();)
			{
				Object roleRowData = roleRow.next();
				role = (Role)roleRowData;
			}
		}
			
		return role;
		
	}
	public static void main(String args[])
	{
		RoleImpl roleImpl = new RoleImpl();
		try {
			roleImpl.addRole("1", "Oxyent.com", "User");
		} catch (NoSuchCompanyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

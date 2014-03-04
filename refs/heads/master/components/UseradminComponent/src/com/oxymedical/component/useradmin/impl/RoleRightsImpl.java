package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IRoleRights;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Right;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.component.useradmin.model.RoleRight;
import com.oxymedical.component.useradmin.model.RoleRightPK;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;

public class RoleRightsImpl implements IRoleRights
{

	public ArrayList<String> getRoleRights(String name)throws UAComponentException
	{
		ArrayList<String> roleRights = null;
		Role roleLookup = null;
		ArrayList<String> right = null;
		
		// get Role Id from role Name from RoleLookup
		String retrieveRoleQuery = SQLCommands.Select_Role_From_Name ;
		ArrayList<NameQueryParameter> roleListParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter roleId = new NameQueryParameter(Constants.roleName, name);
		roleListParam.add(roleId);
		List rolelist;
		try {
			rolelist = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(retrieveRoleQuery,roleListParam);
		} catch (DBComponentException e) {
			throw new UAComponentException(e.getMessage(),e);
		}			
		if ((rolelist == null) || (rolelist.size()== 0))
		{
			throw (new UAComponentException("role with " + name + " does not exists"));
		}	
		else 
		{
			
			for(Iterator roleRow=rolelist.iterator();roleRow.hasNext();)
			{
				Object roleRowRowData= roleRow.next();
				roleLookup = (Role)roleRowRowData;
				
			}		
		}
		UserAdminComponent.logger.log(0," Role Id is " +roleLookup.getRoleId());
		
		// Select RightId from RoleID in RoleRights Table
		String sqlQuery = SQLCommands.Select_Rights_From_Role;
		ArrayList<NameQueryParameter> roleRightListParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter rightId = new NameQueryParameter(Constants.roleId, roleLookup.getRoleId());
		
		roleRightListParam.add(rightId);
		List roleRightList;
		try {
			roleRightList = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,roleRightListParam);
		} catch (DBComponentException e) {
			throw new UAComponentException(e.getMessage(),e);
		}			
		if ((roleRightList == null) || (roleRightList.size()== 0))
		{
			throw (new UAComponentException("right with role" + roleLookup.getRoleId() + " does not exists"));
		}	
		else 
		{
			roleRights = new ArrayList<String>();
			right = new ArrayList<String>();
			
			for(Iterator roleRightsRow=roleRightList.iterator();roleRightsRow.hasNext();)
			{
				Object[] roleRightsRowRowData= (Object[])roleRightsRow.next();
				
				roleRights.add((String )roleRightsRowRowData[0]);
							
				right.add((String)roleRightsRowRowData[1]);
				
			}	
			
			
		}
		return right;
		
	}
	
	public static void main(String args[])
	{
		RoleRightsImpl impl = new  RoleRightsImpl();
		Right right = null;
		try {
		//ArrayList<String> list = impl.getRoleRights("Administrator");
		ArrayList<Right>rights = new ArrayList<Right>();
		String roleId = "1";
		String companyId = "GIP";
		right = new Right();
		right.setRightId("1");
		rights.add(right);
		right = new Right();
		right.setRightId("2");
		rights.add(right);
		
		impl.addRoleRights(roleId, companyId, rights);
		//UserAdminComponent.logger.log(0,list);
		} catch (DBComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UAComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addRoleRights(String roleId, String companyId, ArrayList<Right> rights) throws DBComponentException, UAComponentException 
	{
		UserAdminComponent.logger.log(0,"-Presen tin Add Role Rights impl--role id--"+roleId);
		UserAdminComponent.logger.log(0,"-Presen tin Add Role Rights impl--company id--"+companyId);
		UserAdminComponent.logger.log(0,"-Presen tin Add Role Rights impl--ArrayList id--"+rights.size());
		boolean isValidRole = false;
		boolean isValidRight = false;
		
		Right right = null;
		RoleImpl roleImpl = new RoleImpl();
		RightImpl rightImpl = new RightImpl();
		RoleRight roleRights = new RoleRight();
		RoleRightPK roleRightsPK = new RoleRightPK();
		
		isValidRole = roleImpl.validateRole(roleId);
		//this code is added to first remove the rights if any rights are perssent for this roleid in case of edit.
		//change done by pra on june 16 2009
		if(isValidRole)
		{
			String retrieveRoleQuery = SQLCommands.Find_RoleRights_Based_on_RoleId ;
			ArrayList<NameQueryParameter> roleListParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter role = new NameQueryParameter(Constants.roleId, roleId);
			roleListParam.add(role);
			List rolelist;
			try {
				rolelist = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(retrieveRoleQuery,roleListParam);
			} catch (DBComponentException e) {
				throw new UAComponentException(e.getMessage(),e);
			}
			if(rolelist!=null && rolelist.size()>0)
			{				
				for(Iterator roleRow=rolelist.iterator();roleRow.hasNext();)
				{
					Object roleRowRowData= roleRow.next();
					RoleRight roleRightObject = (RoleRight)roleRowRowData;				
					try {
						ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(roleRightObject);
					} catch (DBComponentException e) {
					throw new UAComponentException("Error in the fields deleteion--");
					}
					
				}		
			}
			
		
		}
		UserAdminComponent.logger.log(0,"--Is Role Valid--"+isValidRole);
		
		for(Iterator rightsRow= rights.iterator();rightsRow.hasNext();)
		{
			Object rightRowData = rightsRow.next();
			right = (Right)rightRowData;
			UserAdminComponent.logger.log(0,"--Now in For Loop to check rights-"+right.getRightId());
			isValidRight = rightImpl.validateRight(right.getRightId());
			UserAdminComponent.logger.log(0,"--Is Right Valid--"+isValidRight);
			if((isValidRole) && (isValidRight))
			{
				roleRightsPK.setRightId(right.getRightId());
				roleRightsPK.setRoleId(roleId);
				roleRights.setComp_id(roleRightsPK);
				roleRights.setCompanyId(companyId);
				UserAdminComponent.logger.log(0,"--Call save Rights role--");
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(roleRights);
			}
		
		}
		
		
		
	}
	
	/**
	 * This method return the List of Right Object based on the Role.
	 * Method added by pra on 29-may-2009
	 * @param name
	 * @return ArrayList
	 * @throws UAComponentException
	 */
	public ArrayList<Right> getRoleRightsList(String name)throws UAComponentException
	{
		
		Role roleLookup = null;
		ArrayList<Right> right = null;
		
		// get Role Id from role Name from RoleLookup
		String retrieveRoleQuery = SQLCommands.Select_Role_From_Name ;
		ArrayList<NameQueryParameter> roleListParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter roleId = new NameQueryParameter(Constants.roleName, name);
		roleListParam.add(roleId);
		List rolelist;
		try {
			rolelist = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(retrieveRoleQuery,roleListParam);
		} catch (DBComponentException e) {
			throw new UAComponentException(e.getMessage(),e);
		}			
		if ((rolelist == null) || (rolelist.size()== 0))
		{
			throw (new UAComponentException("role with " + name + " does not exists"));
		}	
		else 
		{
			
			for(Iterator roleRow=rolelist.iterator();roleRow.hasNext();)
			{
				Object roleRowRowData= roleRow.next();
				roleLookup = (Role)roleRowRowData;
				
			}		
		}
		UserAdminComponent.logger.log(0," Role Id is " +roleLookup.getRoleId());
		
		// Select RightId from RoleID in RoleRights Table
		String sqlQuery = SQLCommands.Select_Rights_From_Role;
		ArrayList<NameQueryParameter> roleRightListParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter rightId = new NameQueryParameter(Constants.roleId, roleLookup.getRoleId());
		
		roleRightListParam.add(rightId);
		List roleRightList;
		try {
			roleRightList = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,roleRightListParam);
		} catch (DBComponentException e) {
			throw new UAComponentException(e.getMessage(),e);
		}			
		if ((roleRightList == null) || (roleRightList.size()== 0))
		{
			throw (new UAComponentException("right with role" + roleLookup.getRoleId() + " does not exists"));
		}	
		else 
		{
			//roleRights = new ArrayList<Right>();
			right = new ArrayList<Right>();
			Right rightObject=null;
			
			for(Iterator roleRightsRow=roleRightList.iterator();roleRightsRow.hasNext();)
			{
				Object[] roleRightsRowRowData= (Object[])roleRightsRow.next();
				rightObject=new Right();
				String rId=(String )roleRightsRowRowData[0];
				String rightName=(String)roleRightsRowRowData[1];
				rightObject.setName(rightName);
				rightObject.setRightId(rId);
				right.add(rightObject);		
			}		
			
		}
		return right;
		
	}

}

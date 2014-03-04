package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IUserPatternRoles;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Right;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.component.useradmin.model.UserpatternRole;
import com.oxymedical.component.useradmin.model.UserpatternRolePK;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.core.userdata.IRights;
import com.oxymedical.core.userdata.Rights;
import com.oxymedical.core.userdata.RoleRights;

public class UserpatternRolesImpl implements IUserPatternRoles
{
	public void addUserpatternRoles(String userPatternId, String companyId, ArrayList<Role> roles) throws DBComponentException, UAComponentException 
	{
		UserAdminComponent.logger.log(0,"-Presen tin Add Role Rights impl--role id--"+userPatternId);
		UserAdminComponent.logger.log(0,"--Presen tin Add Role Rights impl--company id--"+companyId);
		UserAdminComponent.logger.log(0,"-Presen tin Add Role Rights impl--ArrayList id--"+roles.size());
		boolean isValidUserPattern = false;
		boolean isValidRole = false;
		
		Role role = null;
		UserPatternImpl userpatternImpl = new UserPatternImpl();
		RoleImpl roleImpl = new RoleImpl();
		UserpatternRole userpatternroles = new UserpatternRole();
		UserpatternRolePK userpatternrolePK = new UserpatternRolePK();
		
		isValidUserPattern = userpatternImpl.validateUserPattern(userPatternId);
		UserAdminComponent.logger.log(0,"--Is isValidUserPattern Valid--"+isValidUserPattern);
		for(Iterator rolesRow= roles.iterator();rolesRow.hasNext();)
		{
			Object rolesRowData = rolesRow.next();
			role = (Role)rolesRowData;
			UserAdminComponent.logger.log(0,"-Now in For Loop to check roles-"+role.getRoleId());
			isValidRole = roleImpl.validateRole(role.getRoleId());
			UserAdminComponent.logger.log(0,"--Is isValidRole Valid--"+isValidRole);
			if((isValidUserPattern) && (isValidRole))
			{
				userpatternrolePK.setRoleId(role.getRoleId());
				userpatternrolePK.setUserPatternId(userPatternId);
				userpatternroles.setComp_id(userpatternrolePK);
				userpatternroles.setCompanyId(companyId);
				UserAdminComponent.logger.log(0,"--Call save userpattern roles --");
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(userpatternroles);
			}
		
		}
	}
	//This method was perviously in useradmincomponent 
	//As it returns role and rights based on userpatternid so added here
	//change done by pra on 11-june-2009
	/**
	 * 
	 * This method return the object of RoleRights based on userPatternId
	 * Method added by pra on 29-May-2009
	 * @param hicReturnData
	 * @return IRoleRights
	 */
	public  com.oxymedical.core.userdata.IRoleRights getUserPatternRolesRights(String userPatternId)
	{
	
		Role role=null;
		com.oxymedical.core.userdata.IRoleRights roleRights=null;	    
		 try
		 {		
			
			role=this.getRoleIdByUserPatternId(userPatternId);
			
		} catch (NoSuchRoleException e)
		{
				
			UserAdminComponent.logger.log(0,"NoSuchRoleException is "+e.getMessage());
			
		} catch (UAComponentException e) {
			UserAdminComponent.logger.log(0,"UAComponentException is "+e.getMessage());
			
		}
		
		List<Right> rights=null;
		if(role!=null)
		{
			RoleRightsImpl rightImpl=new RoleRightsImpl();
			try {
				rights=rightImpl.getRoleRightsList(role.getName());
			} catch (UAComponentException e) {
				
				UserAdminComponent.logger.log(0,"UAComponentException is "+e.getMessage());
			}
		} 
		if(rights!=null)
		{
		roleRights=createObjectRoleRight(rights, role);	
		}
		return roleRights;
		
	}

	/**
	 * This method returns and creates the object IRoleRights
	 * @param rights
	 * @param role
	 * @return
	 */
	private com.oxymedical.core.userdata.IRoleRights createObjectRoleRight(List<Right> rights,Role role)
	{
	    com.oxymedical.core.userdata.IRoleRights  iRoleRights= new RoleRights();
		iRoleRights.setRoleId(role.getRoleId());
		List<IRights> iRights= new ArrayList<IRights>();
		
		if(rights!=null)
		{ IRights rig= null;
			for(int i=0;i<rights.size();i++)
			{				
				rig=new Rights();
				Right right=rights.get(i);
				rig.setRightName(right.getName());
				rig.setRightsId(right.getRightId());
				iRights.add(rig);
			}
		}
		if(iRights!=null)
		{
			iRoleRights.setRights(iRights);
		}
		return iRoleRights;
	}
	
	

	/**
	 * This method returns the Object of Role based on UserPatternId
	 * Added by pra on 29-May-2009
	 * @param userPatterId
	 * @return Role
	 * @throws NoSuchRoleException
	 * @throws UAComponentException
	 */
	public Role getRoleIdByUserPatternId(String userPatterId) throws NoSuchRoleException,UAComponentException
	{
		
		Role role = null;
		String sqlQuery = SQLCommands.Find_RoleId_Base_On_UserpatternId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userpatternID = new NameQueryParameter(Constants.userPatternId, userPatterId);
		listParam.add(userpatternID);
		
		List list;
		try {
			list = (List)ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);
		} catch (DBComponentException e) {
		
			throw new UAComponentException(e.getMessage(),e);
		}			
		if (list == null || (list.size()==0))
		{
			throw (new NoSuchRoleException("No Role with this userPatternId " + userPatterId + " found"));
		}
		else
		{
		
			for(Iterator roleRow=list.iterator();roleRow.hasNext();)
			{
				Object[] roleRightsRowRowData= (Object[])roleRow.next();
				role=(Role)roleRightsRowRowData[0];
			}
		}
		return role;	
	
		}
}

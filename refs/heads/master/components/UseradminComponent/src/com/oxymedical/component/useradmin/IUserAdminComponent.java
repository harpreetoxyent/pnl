/**
	 * @author Oxyent Medical Ltd, India
	 * 
	 * No part of this Source may be copied
	 * without Oxyent's prior written permission.
	 * Copyright 2007 Oxyent Medical Ltd, All Rights Reserved.
	 * 
	 *  Version 1.0.0
	 */

package com.oxymedical.component.useradmin;

import org.dom4j.DocumentException;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.DuplicateUserIdException;
import com.oxymedical.component.useradmin.exception.InValidPasswordException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchContainerCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchContainerException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.core.commonData.IHICData;

public interface IUserAdminComponent 
{
	IUserAdminManager userAdminManager = null;
	ISingleSignonManager singleSignonManager = null;	
	IUserAdminManager getUserAdminManager();
	public OrganizationStructure retrieveOrganizationStructureFromContainer(String containerId) throws UAComponentException, DBComponentException,NoSuchContainerException;
	public OrganizationStructure retrieveOrganizationStructureFromCompany(String companyId)throws UAComponentException, DBComponentException;
	void setUserAdminManager(IUserAdminManager userAdminManager);
	ISingleSignonManager getSingleSignonManager();
	void setSingleSignonManager(ISingleSignonManager userAdminManager);
	public IHICData encapsulateOrgStructureForCompany(String companyId) throws UAComponentException, DBComponentException;
	public IHICData encapsulateOrgStructureForContainer(String containerId) throws UAComponentException, DBComponentException;
	public void registerNewApplication(IHICData hicData)throws DBComponentException,NoSuchGroupException,NoSuchCompanyException,DuplicateUserIdException,NoSuchUserException,NoSuchRoleException,NoSuchContainerException,UAComponentException;
	public boolean authenticateUser(String userId, String password, String container ,String ldapServer)throws NoSuchUserException, NoSuchCompanyException, NoSuchContainerException, DBComponentException, NoSuchContainerCompanyException,InValidPasswordException;
	public boolean changePassword(String userId , String oldPassword , String newPassword)throws NoSuchUserException, NoSuchCompanyException,DBComponentException;
	public void invokeContImpl() throws DBComponentException, DocumentException;
	public Role[] retrieveUserRoles(String userId , String password);
	public void retrieveRoleRights(String roleName)throws DBComponentException, UAComponentException ;
	public String generateRolesXML(String userId)throws NoSuchUserException,DBComponentException,UAComponentException ;
	public void setDataBaseSetting(IHICData hicData);
	//All EventSubsriber functions are added in interface because they are exposed to external world
	public IHICData createUserFromApplication(Object hicInputData);
	public IHICData getListInGridUserAdmin(Object hicInputData);
	
	/*
	 * This method has been commented.
	 *Changes by Wasim khan, 21-May-2009
	 */
	//public IHICData getQueryFromUserAdmin(Object hicInputData);
	public IHICData removeUserFromApplication(Object hicInputData);
	public IHICData authenticateUserExernally(Object hicInputData);
	
	
}

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

import java.util.ArrayList;

import org.dom4j.Document;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.ldap.LDAPComponent;
import com.oxymedical.component.useradmin.exception.DuplicateUserIdException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchOrganizationException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.OrganizationParentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.UsersRole;
/**
* This interface defines the functionality of adding users ,assigning roles to the users
* retrieving users etc.
*/
public interface IUser 
{
	
	public void addGroupUsers(java.lang.String groupId,
			java.lang.String[] userIds)throws UAComponentException, NoSuchUserException,NoSuchGroupException,DBComponentException;
			
	public void addGroupUsers(String groupId, String userId) throws UAComponentException,NoSuchUserException,NoSuchGroupException,DBComponentException;
	public void addRoleUsers(java.lang.String roleId, java.lang.String[] userIds) throws UAComponentException,NoSuchUserException ,DBComponentException,NoSuchRoleException ;
	public void addRoleUsers(java.lang.Object[] roleIds, java.lang.String userId) throws UAComponentException,NoSuchUserException ,DBComponentException,NoSuchRoleException ;		
	public ArrayList<UsersRole> getUserRoles(String userId)throws NoSuchUserException , DBComponentException;
	public void addUserGroupUsers(java.lang.String userGroupId,
			java.lang.String[] userIds);
	//public void registerUsersFromXML(Document usersDoc , String applicationName , LDAPComponent ldapComp) throws DBComponentException,NoSuchUserException, NoSuchGroupException, NoSuchCompanyException, NoSuchRoleException, NoSuchOrganizationException, DuplicateUserIdException,UAComponentException;	
//method arguemtns changed as per the implementation for inactive date done by pra on 16-june-2009
//Method variable changed according to the Newly Added Fields in AddUser Account.Change done by pra on 22-May-2009
	//Change done by netram sahu on 10-Apr-2012
	//Add one parameter groupId
	public com.oxymedical.component.useradmin.model.User addUser(
				java.lang.String creatorUserId, java.lang.String companyId,boolean autoUserId,
				java.lang.String userId, java.lang.String password,
				java.lang.String emailAddress,	java.util.Locale locale, java.lang.String firstName,
				java.lang.String middleName, java.lang.String lastName,
				java.lang.String nickName, java.lang.String prefixId,
				java.lang.String suffixId, boolean male, int birthdayMonth,
				int birthdayDay, int birthdayYear, java.lang.String jobTitle,
				java.lang.String organizationId, java.lang.String locationId,java.lang.String employeeNumber,java.lang.String address1,
				java.lang.String address2,java.lang.String nationalProvidedIdentificationNumber,java.lang.String universalProviderIdentificationNumber ,
				java.lang.String category,long city, java.lang.String state,long telephoneNumber,long zipCode,Byte active_,java.util.Date inActiveDate,String groupId)throws NoSuchUserException ,DBComponentException,DuplicateUserIdException,NoSuchOrganizationException,OrganizationParentException,NoSuchCompanyException,NoSuchGroupException,UAComponentException;


	//End Changes
			
	

	public int authenticateByUserId(java.lang.String companyId,
			java.lang.String userId, java.lang.String password,
			java.util.Map headerMap, java.util.Map parameterMap);
		
	public void deleteRoleUser(java.lang.String roleId, java.lang.String userId);

	public java.util.List getGroupUsers(java.lang.String groupId);

	public java.util.List getRoleUsers(java.lang.String roleId);

	public boolean hasGroupUser(java.lang.String groupId,
				java.lang.String userId);

	public boolean hasRoleUser(java.lang.String roleId, java.lang.String userId);

				
	public com.oxymedical.component.useradmin.model.User updatePassword(
			java.lang.String userId, java.lang.String password1,
			java.lang.String password2, boolean passwordReset);
			
	public  com.oxymedical.component.useradmin.model.User updateUser(
				java.lang.String userId, java.lang.String password,
				java.lang.String emailAddress, java.lang.String languageId,
				java.lang.String timeZoneId, java.lang.String greeting,
				java.lang.String resolution, java.lang.String comments,
				java.lang.String firstName, java.lang.String middleName,
				java.lang.String lastName, java.lang.String nickName,
				java.lang.String prefixId, java.lang.String suffixId, boolean male,
				int birthdayMonth, int birthdayDay, int birthdayYear,
				java.lang.String smsSn, java.lang.String aimSn, java.lang.String icqSn,
				java.lang.String jabberSn, java.lang.String msnSn,
				java.lang.String skypeSn, java.lang.String ymSn,
				java.lang.String jobTitle, java.lang.String organizationId,
				java.lang.String locationId);
	
	public void addUserOrganization(String userId, String organizationId)
	throws DBComponentException;
			

}

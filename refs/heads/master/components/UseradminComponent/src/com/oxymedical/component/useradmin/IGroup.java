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

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchGroupException;
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.NoSuchUserException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Group;

public interface IGroup 
{

	//Change done by netram sahu on 10-Apr-2012
	//Add one parameter companyId
	public Group addGroup(String userId,String className, String classPK,		
			String groupId,String companyId, String description,String type)throws NoSuchUserException,NoSuchGroupException,UAComponentException,DBComponentException;
	//End Changes
	public Group addGroupByCompany(String companyId,String className , String classPK,String name, String description, String type , boolean isLdap ) throws NoSuchUserException,NoSuchGroupException,UAComponentException,DBComponentException;
	
	public void addRoleGroups(String roleId,
			String[] groupIds) throws NoSuchRoleException , NoSuchGroupException,DBComponentException;
	
	public void deleteGroup(String companyId , String groupName , boolean deleteUser) throws UAComponentException;
	
	public Group getGroup(String groupId)throws NoSuchGroupException;
	
	public Group getGroup(String companyId,
			String name)throws NoSuchGroupException;

	public Group getOrganizationGroup(
			String companyId, String organizationId)throws NoSuchGroupException,DBComponentException;
		
	public java.util.List getRoleGroups(String roleId)throws DBComponentException,NoSuchGroupException,NoSuchRoleException;
	
	public Group getUserGroup(
			String companyId, String userId)throws UAComponentException,NoSuchCompanyException, DBComponentException ,NoSuchUserException,NoSuchGroupException;

	public Group getGroupByCompanyAndName(String companyId , String name)throws NoSuchGroupException,DBComponentException,NoSuchCompanyException;
	
	public Group getUserGroupGroup(	String companyId, String userGroupId);
	
	public boolean hasRoleGroup(String roleId, String groupId);
	
	public boolean hasUserGroup(String userId, String groupId);
	

	public Group updateGroup(
		String groupId, String name,
		String description, String type,
		String friendlyURL);
	
}

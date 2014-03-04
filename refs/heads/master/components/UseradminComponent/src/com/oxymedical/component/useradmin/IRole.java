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
import com.oxymedical.component.useradmin.exception.NoSuchRoleException;
import com.oxymedical.component.useradmin.exception.UAComponentException;

import com.oxymedical.component.useradmin.model.Role;

public interface IRole 
{
	public com.oxymedical.component.useradmin.model.Role addRole(java.lang.String userId,
			java.lang.String companyId, java.lang.String name,String defaultURL)throws DBComponentException,NoSuchCompanyException;

	public com.oxymedical.component.useradmin.model.Role addRole(java.lang.String companyId, java.lang.String name, String defaultURL)
	throws DBComponentException,NoSuchCompanyException;

	public com.oxymedical.component.useradmin.model.Role addRole(java.lang.String userId,
			java.lang.String companyId, java.lang.String name,
			java.lang.String className,String defaultURL, java.lang.String classPK)throws DBComponentException,NoSuchCompanyException;

	public void deleteRole(java.lang.String roleId);
	
	public com.oxymedical.component.useradmin.model.Role getGroupRole(
			java.lang.String companyId, java.lang.String groupId);
	
	public Role getRolebyCompanyAndName(String companyId , String name)throws NoSuchRoleException,DBComponentException;		

		public com.oxymedical.component.useradmin.model.Role getRole(java.lang.String roleId)throws DBComponentException ,UAComponentException;

		public com.oxymedical.component.useradmin.model.Role getRole(java.lang.String companyId,
			java.lang.String name);

		public java.util.List getUserRelatedRoles(java.lang.String userId,
			java.util.List groups);

		public java.util.List getUserRoles(java.lang.String userId);

		public boolean hasUserRole(java.lang.String userId,
			java.lang.String companyId, java.lang.String name);
		
		public boolean hasUserRoles(java.lang.String userId,
			java.lang.String companyId, java.lang.String[] names);
		

		public com.oxymedical.component.useradmin.model.Role updateRole(java.lang.String roleId,
			java.lang.String name);
}

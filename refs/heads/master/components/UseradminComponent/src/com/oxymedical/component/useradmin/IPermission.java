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

public interface IPermission 
{
	public com.oxymedical.component.useradmin.model.Permission addPermission(
			java.lang.String companyId, java.lang.String actionId,
			java.lang.String resourceId);

		public java.util.List addPermissions(java.lang.String companyId,
			java.lang.String name, java.lang.String resourceId,
			boolean portletActions);

		public void addUserPermissions(java.lang.String userId,
			java.lang.String[] actionIds, java.lang.String resourceId);
		
		public java.util.List getActions(java.util.List permissions);

		public java.util.List getGroupPermissions(java.lang.String groupId,
			java.lang.String resourceId);
		
		public java.util.List getOrgGroupPermissions(
			java.lang.String organizationId, java.lang.String groupId,
			java.lang.String resourceId);
		
		public java.util.List getPermissions(java.lang.String companyId,
			java.lang.String[] actionIds, java.lang.String resourceId);

		public java.util.List getUserPermissions(java.lang.String userId,
			java.lang.String resourceId);
		
		public boolean hasGroupPermission(java.lang.String groupId,
			java.lang.String actionId, java.lang.String resourceId);

		public boolean hasRolePermission(java.lang.String roleId,
			java.lang.String companyId, java.lang.String name,
			java.lang.String typeId, java.lang.String scope,
			java.lang.String actionId);

		public boolean hasRolePermission(java.lang.String roleId,
			java.lang.String companyId, java.lang.String name,
			java.lang.String typeId, java.lang.String scope,
			java.lang.String primKey, java.lang.String actionId);

		public boolean hasUserPermission(java.lang.String userId,
			java.lang.String actionId, java.lang.String resourceId);

		
		public void setGroupPermissions(java.lang.String groupId,
			java.lang.String[] actionIds, java.lang.String resourceId);
		

		public void setGroupPermissions(java.lang.String className,
			java.lang.String classPK, java.lang.String groupId,
			java.lang.String[] actionIds, java.lang.String resourceId);

		public void setOrgGroupPermissions(java.lang.String organizationId,
			java.lang.String groupId, java.lang.String[] actionIds,
			java.lang.String resourceId);

		public void setRolePermission(java.lang.String roleId,
			java.lang.String companyId, java.lang.String name,
			java.lang.String typeId, java.lang.String scope,
			java.lang.String primKey, java.lang.String actionId);

		public void setUserPermissions(java.lang.String userId,
			java.lang.String[] actionIds, java.lang.String resourceId);

		public void unsetRolePermission(java.lang.String roleId,
			java.lang.String companyId, java.lang.String name,
			java.lang.String typeId, java.lang.String scope,
			java.lang.String primKey, java.lang.String actionId);

		
		public void unsetRolePermissions(java.lang.String roleId,
			java.lang.String companyId, java.lang.String name,
			java.lang.String typeId, java.lang.String scope,
			java.lang.String actionId);
		
		public void unsetUserPermissions(java.lang.String userId,
			java.lang.String[] actionIds, java.lang.String resourceId);
}

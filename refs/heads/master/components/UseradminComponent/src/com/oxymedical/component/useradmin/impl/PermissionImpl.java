package com.oxymedical.component.useradmin.impl;

import java.util.List;

import com.oxymedical.component.useradmin.IPermission;
import com.oxymedical.component.useradmin.model.Permission;

public class PermissionImpl implements IPermission{

	public Permission addPermission(String companyId, String actionId, String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List addPermissions(String companyId, String name, String resourceId, boolean portletActions) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addUserPermissions(String userId, String[] actionIds, String resourceId) {
		// TODO Auto-generated method stub
		
	}

	public List getActions(List permissions) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getGroupPermissions(String groupId, String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getOrgGroupPermissions(String organizationId, String groupId, String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getPermissions(String companyId, String[] actionIds, String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getUserPermissions(String userId, String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasGroupPermission(String groupId, String actionId, String resourceId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasRolePermission(String roleId, String companyId, String name, String typeId, String scope, String actionId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasRolePermission(String roleId, String companyId, String name, String typeId, String scope, String primKey, String actionId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasUserPermission(String userId, String actionId, String resourceId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setGroupPermissions(String groupId, String[] actionIds, String resourceId) {
		// TODO Auto-generated method stub
		
	}

	public void setGroupPermissions(String className, String classPK, String groupId, String[] actionIds, String resourceId) {
		// TODO Auto-generated method stub
		
	}

	public void setOrgGroupPermissions(String organizationId, String groupId, String[] actionIds, String resourceId) {
		// TODO Auto-generated method stub
		
	}

	public void setRolePermission(String roleId, String companyId, String name, String typeId, String scope, String primKey, String actionId) {
		// TODO Auto-generated method stub
		
	}

	public void setUserPermissions(String userId, String[] actionIds, String resourceId) {
		// TODO Auto-generated method stub
		
	}

	public void unsetRolePermission(String roleId, String companyId, String name, String typeId, String scope, String primKey, String actionId) {
		// TODO Auto-generated method stub
		
	}

	public void unsetRolePermissions(String roleId, String companyId, String name, String typeId, String scope, String actionId) {
		// TODO Auto-generated method stub
		
	}

	public void unsetUserPermissions(String userId, String[] actionIds, String resourceId) {
		// TODO Auto-generated method stub
		
	}

}

package com.oxymedical.component.useradmin;

import java.util.ArrayList;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Right;



public interface IRoleRights 
{
	public void addRoleRights(String role ,String companyId , ArrayList<Right> rights )throws DBComponentException,UAComponentException;
	public ArrayList<String> getRoleRights(String roleName)throws DBComponentException,UAComponentException;

}

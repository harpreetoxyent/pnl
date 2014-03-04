package com.oxymedical.component.useradmin;

import java.util.ArrayList;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Role;

public interface IUserPatternRoles
{
	public void addUserpatternRoles(String userPatternId, String companyId, ArrayList<Role> roles) throws DBComponentException, UAComponentException;
}

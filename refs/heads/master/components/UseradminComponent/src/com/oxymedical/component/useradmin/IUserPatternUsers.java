package com.oxymedical.component.useradmin;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.User;

public interface IUserPatternUsers
{
	public void addUserpatternUsers(String userPatternId, String companyId, ArrayList<User> users) throws DBComponentException, UAComponentException;
	public List<String> getUserPatternsFromUserId(String userId) throws DBComponentException,
	UAComponentException;
	public void deleteUserPatternForUser(String userPatternId, String userId)
	throws UAComponentException;
}

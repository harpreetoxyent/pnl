package com.oxymedical.core.userdata;

import java.util.List;

public class UserPattern implements IUserPattern,java.io.Serializable
{
	IRoleRights roleRights = null;
	IUser user = null;
	String userPatternId;
	

	
	public IRoleRights getRoles()
	{
		return this.roleRights;
	}

	public IUser getUser()
	{
		return this.user;
	}

	public String getUserPatternId()
	{
		return this.userPatternId;
	}

	public void setRoles(IRoleRights roleRights)
	{
		this.roleRights = roleRights;
	}

	public void setUser(IUser user)
	{
		this.user = user;
	}

	public void setUserPatternId(String userPatternId)
	{
		this.userPatternId = userPatternId;
	}

}

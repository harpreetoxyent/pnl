package com.oxymedical.core.userdata;

public interface IUserPattern
{
	public String getUserPatternId();
	public IRoleRights getRoles();
	public IUser getUser();

	public void setUserPatternId(String userPatternId);
	public void setRoles(IRoleRights roleRights);
	public void setUser(IUser user);
}

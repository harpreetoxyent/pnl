package com.oxymedical.core.userdata;

public class User implements IUser
{
	String userId;
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

}

package com.oxymedical.core.commonData;

public interface IUserInfo
{
	public String getUserId();
	public String getPassword();
	
	public void setUserId(String userId);
	public void setPassword(String password);
	/*
	 * getHostAddress setter and getter added for login information
	 * changes by wasim, 26-May-2009.
	 */
	
	public String getHostAddress();
	public void setHostAddress(String hostAddress);
	
}

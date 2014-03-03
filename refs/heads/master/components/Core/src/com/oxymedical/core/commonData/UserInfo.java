package com.oxymedical.core.commonData;

import java.io.Serializable;

/**
 * @author vka
 * hostAddress added by wasim, 26-May-2009
 */
public class UserInfo implements IUserInfo,Serializable
{
	String userId = null;
	String password = null;
	String hostAddress = null;
	
	
	/**
	 * @return the hostAddress
	 */
	public String getHostAddress() {
		return hostAddress;
	}

	/**
	 * @param hostAddress the hostAddress to set
	 */
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public String getPassword()
	{
		return this.password;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

}

package com.oxymedical.core.userdata;

import com.oxymedical.core.commonData.IUserInfo;

/**
 * To stor userInfroation used this class
 * 26-May-2009
 * @author vka
 *
 */
public class UserInfo implements IUserInfo {
	
	String userId = null;
	String password =null;
	String hostAddress = null;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
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
	
	
	
	
}

package com.oxymedical.component.useradmin.operations;

public class UserDataBaseInfo 
{
	String userName = null;
	String password = null;

	// Added additional information for useradmin database
	String dbName = null;
	String dbServerName = null;
	String dbPort = null;
	String dbType = null;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	/**
	 * @return the dbType
	 */
	public String getDbType() {
		return dbType;
	}
	/**
	 * @param dbType the dbType to set
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	/**
	 * @return the dbPort
	 */
	public String getDBPort() {
		return dbPort;
	}
	/**
	 * @param dbPort the dbPort to set
	 */
	public void setDBPort(String dbPort) {
		this.dbPort = dbPort;
	}
	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}
	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	/**
	 * @return the dbServerName
	 */
	public String getDbServerName() {
		return dbServerName;
	}
	/**
	 * @param dbServerName the dbServerName to set
	 */
	public void setDbServerName(String dbServerName) {
		this.dbServerName = dbServerName;
	}
	
}

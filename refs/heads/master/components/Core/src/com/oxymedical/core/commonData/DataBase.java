package com.oxymedical.core.commonData;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.userdata.IUserPattern;

/**
 * @author vka
 *Tablename attribute removed.
 * Changes by wasim , 27-May-2009
 */
public class DataBase implements IDataBase 
{
	String userName;
	String password;
	String serverName;
	String dataBaseName;
	String driverName;
	String dbType;
	String dataBasePort;
	
	
	
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
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * @return the dataBaseName
	 */
	public String getDataBaseName() {
		return dataBaseName;
	}
	/**
	 * @param dataBaseName the dataBaseName to set
	 */
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}
	
	/**
	 * @return the dataBasePort
	 */
	public String getDataBasePort() {
		return dataBasePort;
	}
	/**
	 * @param dataBasePort the dataBasePort to set
	 */
	public void setDataBasePort(String dataBasePort) {
		this.dataBasePort = dataBasePort;
	}

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
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	

}

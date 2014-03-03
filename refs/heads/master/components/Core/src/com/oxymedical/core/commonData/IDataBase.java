package com.oxymedical.core.commonData;

// TODO - Need to check and remove "extends IData"

/**
 * @author vka
 *IData interface removed. earlier IDataBase extends IData. which was useless.
 *
 */
public interface IDataBase
{
	public String getUserName();
	public void setUserName(String userName);
	public String getPassword();
	public void setPassword(String password);
	public String getDataBaseName();
	public void setDataBaseName(String dataBaseName);
	
	// Added extra information for database identification
	public String getDataBasePort();
	public void setDataBasePort(String dataBasePort);
	public String getDbType();
	public void setDbType(String dbType);
	public String getServerName();
	public void setServerName(String serverName);
}

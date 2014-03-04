package com.oxymedical.component.db.utilities;

/**
 * Utility class pertaining to database server urls
 * @author vka
 *
 */
public class DBServerURLGenerator 
{
	/**
	 * Creates database server URL based on parameters
	 * 
	 * @param dbType
	 *            - e.g. mysql, oracle, sybase etc.
	 * @param dbName
	 *            - name of database
	 * @param serverName
	 *            - server name or address
	 * @param port
	 *            - port on which database server listens for e.g. for mysql,
	 *            default port is 3306
	 * @return - database server URL
	 */
	public static String getServerURL(String dbType, String dbName, String serverName, String port)
	{
		if(dbType.equalsIgnoreCase("mysql"))
		{
			// "jdbc:mysql://localhost:3306/useradmin";
			return new StringBuffer("jdbc:mysql://").append(serverName)
						.append(":").append(port).append("/")
						.append(dbName).toString();
		}
		else
		{
			// Add for other database types like Sybase, Oracle etc.
		}
		return null;
	}

	/**
	 * Returns database name from serverURL
	 * 
	 * @param serverURL
	 *            - serverURL that contains the database name
	 * @param dbName
	 *            name of database for e.g. MySQL, Oracle etc.
	 * @return
	 */
	public static String getDatabaseFromServerURL(String serverURL, String dbName)
	{
		return getDatabaseFromServerURL(serverURL, dbName, false);
	}

	/**
	 * Returns database name from serverURL. If you are sending database driver
	 * rather than database name, set the isDriver param to "true"
	 * 
	 * @param serverURL
	 *            - serverURL that contains the database name
	 * @param dbType
	 *            - Can be database name or database driver string
	 * @param isDriver
	 *            - true / false
	 * @return - database name
	 */
	public static String getDatabaseFromServerURL(String serverURL, String dbType, boolean isDriver)
	{
		String db = dbType;
		
		// if dbType contains database driver information, fetch database name from it.
		if (isDriver)
		{
			if (dbType.toLowerCase().indexOf("jdbc:mysql://")==0)
			{
				db = "mysql";
			}
		}
		
		if(db.equalsIgnoreCase("mysql"))
		{
			// Remove the last backslash
			if (serverURL.lastIndexOf("/") >= serverURL.length()) serverURL = serverURL.substring(0, serverURL.length()-1);
			
			// return database name
			return serverURL.substring(serverURL.lastIndexOf("/") + 1);
		}
		else
		{
			// Add for other database types like Sybase, Oracle etc.
		}
		return null;
	}
}

package com.oxymedical.component.useradmin.operations;

import java.io.InputStream;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.useradmin.UserAdminComponent;
import com.oxymedical.component.useradmin.constants.Constants;


public class ConnectionDatabase 
{
	private static DBComponent databaseComponent = null;
	InputStream moduleStream = null;
	private static boolean connectionEstablished = false;
	private ConnectionDatabase()
	{
	}
	
	public static DBComponent GetInstanceOfDatabaseComponent()
	{
		if(databaseComponent == null){
			databaseComponent = new DBComponent(); 
		}
		connectDatabase();
		return  databaseComponent;
	}
	
	/**
	 * @author vka
	 * 
	 * new method added for generateing complete dbserver address.
	 *
	 */
	private synchronized static void connectDatabase()
	{
		try{
			if (connectionEstablished) {
				databaseComponent.getConnection(Constants.DBNAME);
				return;
			}
		
			databaseComponent.setUpDataConfiguration(
					UserAdminComponent.dataBaseInfo.getUserName(),
					UserAdminComponent.dataBaseInfo.getPassword(),
					UserAdminComponent.dataBaseInfo.getDbName(),
					UserAdminComponent.dataBaseInfo.getDbServerName(),
					UserAdminComponent.dataBaseInfo.getDBPort(),
					UserAdminComponent.dataBaseInfo.getDbType(),
					Constants.PACKAGE_NAME,
					Constants.BASEDIR);
			
			/*databaseComponent.setUpDataConfiguration(UserAdminComponent.dataBaseInfo.userName,UserAdminComponent.dataBaseInfo.password , Constants.SERVER_NAME, Constants.PACKAGE_NAME,Constants.BASEDIR,"resources_ua");
			databaseComponent.registerDBAndGenerateMappings();
			databaseComponent.loadResourcesJarInLoader(Constants.BASEDIR , "resources_ua.jar");
		    databaseComponent.createDBConfiguration();*/
			InputStream cfgInputStream = ConnectionDatabase.sendHibernatecfgToDB();
			databaseComponent.CreateConfigurationFromInputStream(cfgInputStream);
			databaseComponent.storeConnection(Constants.DBNAME, databaseComponent.getDatabaseOperation());
			connectionEstablished = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static InputStream sendHibernatecfgToDB()
	{
		InputStream	modulefileStream =ConnectionDatabase.class.getResourceAsStream(Constants.HibernatePath);
		return modulefileStream;
	}
	
	public static void main(String args[])
	{
		UserAdminComponent.dataBaseInfo = new UserDataBaseInfo();
		UserAdminComponent.dataBaseInfo.setUserName("root");
		UserAdminComponent.dataBaseInfo.setPassword("1234");
//		User user = new User();
//		user.setUserId("1");
//		user.setCompanyId("GIP");
//		user.setEmailAddress("hello@yahoo.com");
//		user.setPassword("1222");
		try
		{
			ConnectionDatabase.GetInstanceOfDatabaseComponent();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserAdminComponent.logger.log(0,"Successfully done");
	}

	public static void setDatabaseComponent(DBComponent databaseComponent) 
	{
		ConnectionDatabase.databaseComponent = databaseComponent;
		if (databaseComponent == null)
		{
			ConnectionDatabase.GetInstanceOfDatabaseComponent();
		}
		else
		{
			connectDatabase();
		}
	}
}

package com.oxymedical.component.workflowComponent.operation.db;

import java.io.InputStream;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;

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
		if (databaseComponent == null){
			databaseComponent = new DBComponent();
		}
		connectDatabase();
		return databaseComponent;
	}


	/**
	 * @author pra
	 * 
	 *         new method added for generateing complete dbserver address.
	 * 
	 */
	private synchronized static void connectDatabase()
	{
		try{
			if (connectionEstablished) {
				databaseComponent.getConnection(WorkflowConstant.DBNAME);
				return;
			}
		
			databaseComponent.setUpDataConfiguration(
					WorkflowComponent.dataBaseInfo.getUserName(),
					WorkflowComponent.dataBaseInfo.getPassword(), 
					WorkflowComponent.dataBaseInfo.getDbName(),
					WorkflowComponent.dataBaseInfo.getDbServerName(), 
					WorkflowComponent.dataBaseInfo.getDBPort(), 
					WorkflowComponent.dataBaseInfo.getDbType(),
					WorkflowConstant.PACKAGE_NAME, 
					WorkflowConstant.BASEDIR);


			// The below lines should be used for updating resources_app.jar for any changes in database
			/*databaseComponent.setUpDataConfiguration(
					WorkflowComponent.dataBaseInfo.userName,
					WorkflowComponent.dataBaseInfo.password,
					WorkflowConstant.SERVER_NAME,
					WorkflowConstant.PACKAGE_CREATE_NAME,
					WorkflowConstant.BASEDIR,"resources_app");
			databaseComponent.registerDBAndGenerateMappings();
			databaseComponent.loadResourcesJarInLoader(WorkflowConstant.BASEDIR, "resources_app.jar"); 
			databaseComponent.createDBConfiguration();*/
			
			InputStream cfgInputStream = ConnectionDatabase.sendHibernatecfgToDB();
			databaseComponent.CreateConfigurationFromInputStream(cfgInputStream);
			databaseComponent.storeConnection(WorkflowConstant.DBNAME, databaseComponent.getDatabaseOperation());
		}catch (Exception e){
			e.printStackTrace();
		}
		connectionEstablished = true;
	}


	public static InputStream sendHibernatecfgToDB()
	{
		InputStream modulefileStream = ConnectionDatabase.class
				.getResourceAsStream(WorkflowConstant.HibernatePath);
		return modulefileStream;
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


	// For generating resources_app.jar based on database structure
	public static void main(String args[])
	{
		WorkflowComponent.dataBaseInfo = new WorkflowDatabaseInfo();
		WorkflowComponent.dataBaseInfo.setUserName("root");
		WorkflowComponent.dataBaseInfo.setPassword("");
		try
		{
			ConnectionDatabase.GetInstanceOfDatabaseComponent();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

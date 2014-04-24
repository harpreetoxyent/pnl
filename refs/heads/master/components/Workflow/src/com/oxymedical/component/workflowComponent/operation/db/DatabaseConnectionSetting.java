package com.oxymedical.component.workflowComponent.operation.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.core.commonData.DataBase;
import com.oxymedical.core.commonData.IDataBase;
import com.oxymedical.core.commonData.IHICData;

public class DatabaseConnectionSetting 
{
	public static void setDataBaseSetting(IHICData hicData )
	{
		if (WorkflowComponent.dataBaseInfo == null)
		{
			IDataBase database = hicData.getDatabase();
			if (database == null)
			{
				String dataSettingPath = hicData.getApplication().getApplicationFolderPath()
				+ WorkflowConstant.DATABASESETTING;
				database = new DataBase();
				database = setDataBaseSettingFromFile(dataSettingPath, database);
			}
		
			WorkflowComponent.dataBaseInfo = new WorkflowDatabaseInfo();
			String dbName = database.getDataBaseName();
			if (dbName == null)
				dbName = WorkflowConstant.DBNAME;
			WorkflowComponent.dataBaseInfo.setUserName(database.getUserName());
			WorkflowComponent.dataBaseInfo.setPassword(database.getPassword());
			WorkflowComponent.dataBaseInfo.setDbServerName(database.getServerName());
			WorkflowComponent.dataBaseInfo.setDbType(database.getDbType());
			WorkflowComponent.dataBaseInfo.setDBPort(database.getDataBasePort());
			WorkflowComponent.dataBaseInfo.setDbName(dbName);
		}
		
		ConnectionDatabase.setDatabaseComponent(WorkflowComponent.workflowDbComponent);
	}

	public static void checkConnectionSettings(IHICData hicData)
	{
		
		setDataBaseSetting(hicData);
		if (WorkflowComponent.dataBaseInfo == null)
		{
			String dataSettingPath = hicData.getApplication().getApplicationFolderPath()
					+ WorkflowConstant.DATABASESETTING;
			//setDataBaseSettingFromFile(dataSettingPath, hicData);
			setDataBaseSetting(hicData);
		}
	}

	/**
	 * This method is used to set the database setting like username , password
	 * 
	 *
	 */
	private static IDataBase setDataBaseSettingFromFile(String dataSettingPath, IDataBase dataBase)
	{
		FileInputStream inputFile = null;
		Document document = null;
		String userName;
		String password;
		String serverName;
		String portNumber;
		String dbType;
		try
		{
			File dbSettingsFile = new File(dataSettingPath);
			if(dbSettingsFile.exists() == false){
				System.out.println("The file '" + dataSettingPath + "' does not exist. Loading it from config.");
				dataSettingPath = new File("datasettings.xml").getAbsolutePath();
			}
			inputFile = new FileInputStream(dataSettingPath);
			InputStreamReader dataInputStream = new InputStreamReader(inputFile);
			SAXReader reader = new SAXReader();
			document = reader.read(dataInputStream);
			Element root = document.getRootElement();
			userName = root.element("user").getTextTrim();

			password = root.element("password").getTextTrim();
			WorkflowComponent.logger.log(0,"database password  " + password);
			serverName = root.element("servername").getTextTrim();
			dbType = root.element("databasetype").getTextTrim();
			portNumber = root.element("portno").getTextTrim();
			
			if (null != userName && null != password && null != serverName)
			{
				dataBase.setUserName(userName);
				dataBase.setPassword(password);
				dataBase.setServerName(serverName);
				dataBase.setDbType(dbType);
				dataBase.setDataBasePort(portNumber);
				dataBase.setDataBaseName(WorkflowConstant.DBNAME);
			}
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		
		return dataBase;
	}

}

/**
 * 
 */
package com.oxymedical.servlet.HICServlet;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.oxymedical.component.UniqueIDGeneratorComponent.UniqueIdGenerator;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.DataBase;
import com.oxymedical.core.commonData.DataPattern;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IDataBase;
import com.oxymedical.core.commonData.IDataPattern;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IMetaData;
import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.IUserInfo;
import com.oxymedical.core.commonData.MetaData;
import com.oxymedical.core.commonData.UserInfo;
import com.oxymedical.core.constants.TopicConstants;
import com.oxymedical.core.deployApplicationInfo.ApplicationInfo;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.hic.application.NOLISRuntime;

/**
 * @author vka
 *
 */
public class DataUnitRouter extends HICRouter implements IDataUnitRouter
{
	private String currentApplicationName;
	
	public IHICData routeToModeler(IDataUnit dataUnit)
	{
		IHICData hicData= null;
		try
		{
			// Get the data unit from external world & convert the external world data into hicData
			hicData = buildHICData(dataUnit);
			hicData = this.routeToComponent(hicData);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicData;
	}	



	/**
	 * @param dataUnit
	 * @return
	 * @throws Exception
	 */
	private IHICData buildHICData(IDataUnit dataUnit) throws Exception
	{
		// Get the hicData instance from Bus
	
		IHICData hicData = super.getHICData();
	
		if (hicData == null)
		{
			throw new Exception(" hicData is null");
		}
		try
		{
			IData data = new Data();
			// Added Data Pattern in DO
			IDataPattern dataPattern = new DataPattern();
			dataPattern.setDataPatternId(dataUnit.getDataPatternId());
			data.setDataPattern(dataPattern);

		
			// Added Form Pattern in DO
			IFormPattern formPattern = new FormPattern();
			formPattern.setFormId(dataUnit.getFormId());
	
			formPattern.setFormValues(dataUnit.getFormValues());
			data.setFormPattern(formPattern);
	
			data.setQueryData(dataUnit.getQueryData());
			data.setColumnOrder(dataUnit.getColumnOrder());
			
				// Set Default status. This might be overwritten by Workflow if any
			// DO is already existent for this newly created DO.
			if (dataUnit.getStatus() == null)
				data.setStatus("DEFAULT");
			else data.setStatus(dataUnit.getStatus());
			if (dataUnit.getUserId() != null)
			{
				data.setUserId(dataUnit.getUserId());
			}
			else
			{
				data.setUserId(null);
			}
			if (dataUnit.getUserPatterns() != null)
			{
				data.setUserPatterns(dataUnit.getUserPatterns());
			}
			else
			{
				data.setUserPatterns(null);
			}
			// Added ComponentId to Check existence
			data.setInvokeComponentId(dataUnit.getInvokeComponentId());
			// Added ComponentClass for external Component
			data.setInvokeComponentClass(dataUnit.getInvokeComponentClass());
			// Added Method Name to set topic for publishing and subscribing
			data.setMethodName(dataUnit.getMethodName());
	
			// Added Meta Data in DO
			IMetaData metadata = new MetaData();
			metadata.setCommonObject(dataUnit.getMetaData());
			hicData.setMetaData(metadata);
	
			// Added source in DO
			LinkedList<ISource> srcHistory = new LinkedList<ISource>();
			srcHistory.add(dataUnit.getSource());
			hicData.setSrcHistoryList(srcHistory);
	
			// Also set uniqueid
			if (dataUnit.getUniqueId() == null)
			{
				hicData.setUniqueID(UniqueIdGenerator.generateUniqueID("IHICData"));
			}
			else
			{
				hicData.setUniqueID(dataUnit.getUniqueId());
			}
	
			// Set application
			IApplication app = null;
			this.currentApplicationName = dataUnit.getCurrentApplicationName();
			app =  ApplicationInfo.getApplicationInfo(currentApplicationName);
			hicData.setApplication(app);
			
			/*String serverAddress=null;
			String dbSettingsFile = app.getApplicationFolderPath() + CoreConstants.DATA_SETTINGS_PATH;
			System.out.println("---------dbSettingsFile/---------------"+dbSettingsFile);
			DataBaseSettingInfo dbInfo = DataBaseInfoParser.getConnectionSettings(dbSettingsFile);
			IDataBase dataBase = new DataBase();
			dataBase.setPassword(dbInfo.getPassword());
			dataBase.setUserName(dbInfo.getUser());

			 
			 * Added additional information about dbName, hostName, dbType and port
			 
			dataBase.setDataBaseName(dataUnit.getDataBaseName());
			dataBase.setServerName(dbInfo.getHostName());
			dataBase.setDbType(dbInfo.getDataBaseType());
			dataBase.setDataBasePort(dbInfo.getPort());

			hicData.setDatabase(dataBase);*/
			
			
			/*
			 * IUserinfo object added for hostAddress, this is used only in userAdmin component.
			 */
			IUserInfo userInfo = new UserInfo();
			if(dataUnit.getUserInfo()!=null)
				userInfo.setHostAddress(dataUnit.getUserInfo().getHostAddress());
			
			data.setUserInfo(userInfo);
			
			// For providing communication data
			data.setCommunicationInfo(dataUnit.getCommunicationData());
			
			// Providing List data
			data.setList(dataUnit.getList());
			
			hicData.setData(data);
			
			// Session Id attribute added
			hicData.setSessionId(dataUnit.getSessionId());
			
			hicData.setLdapData(dataUnit.getLdapData());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
		return hicData;
	}
}

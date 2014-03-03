/**
 * 
 */
package com.oxymedical.core.deployApplicationInfo;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.constants.CoreConstants;

/**
 * @author wkh
 *
 */
public class ApplicationInfo {



	/**
	 * This method take application name and parse applicationinformation.xml.
	 * and return applicationinfoHash.
	 * changes by Wasim Khan 16-july-2009
	 * @param appName
	 * @param paramName
	 * @param currentAppName
	 * @return
	 */
	public static Object retreiveAppInfo(String appName,String paramName,String currentAppName)
	{

		Hashtable<String,Hashtable<String,String>> applicationHash = new Hashtable<String,Hashtable<String,String>>();
		Hashtable<String,String> applicationInfoHash = null;
		ApplicationInfo applicationInfo = null;
		String retriveFileName = null;
		if(currentAppName == null || currentAppName=="" || currentAppName.equalsIgnoreCase(" "))
		{
			retriveFileName = CoreConstants.appConfigInfo;
		}
		else
		{
			retriveFileName = CoreConstants.appInfo +currentAppName+"/"+CoreConstants.appFolder+
			"/"+CoreConstants.APPLICATION_INFO_FILENAME;
		}
		String retAppName = null;
		try
		{       
			SAXReader reader = new SAXReader();
			File masterFile=new File(retriveFileName);
			if(!(masterFile.exists()))
				return null;
			Document document = reader.read(masterFile);
			Element root=document.getRootElement();
			for ( Iterator i = root.elementIterator(); i.hasNext(); )
			{
				Element element = (Element) i.next();
				if(null == appName)
					return null;
				if(appName.equals(""))
				{	            	   
					Element retParam = element.element(CoreConstants.applicationName);
					Element retParamSrcDir = element.element(CoreConstants.sourcedir);
					Element retParamServerLibDir = element.element(CoreConstants.serverLibDirectory);
					Element retParamRenderOption = element.element(CoreConstants.renderOption);	                	
					Element retParamServerDirOption = element.element("serverDirectory");
					if(retParam == null || retParamSrcDir == null || retParamServerLibDir == null || retParamRenderOption == null || retParamServerDirOption == null)
						return null;
					applicationInfoHash = new Hashtable<String,String>();
					retAppName = retParam.getTextTrim();
					applicationInfoHash.put(retParam.getName(), retParam.getTextTrim());

					applicationInfoHash.put(retParamSrcDir.getName(), retParamSrcDir.getTextTrim());

					applicationInfoHash.put(retParamServerLibDir.getName(), retParamServerLibDir.getTextTrim());

					applicationInfoHash.put(retParamRenderOption.getName(), retParamRenderOption.getTextTrim());

					applicationInfoHash.put(retParamServerDirOption.getName(), retParamServerDirOption.getTextTrim());

				}
				else 
				{  
					Element retAppEle = element.element(CoreConstants.applicationName);
					String retAppNa = retAppEle.getTextTrim();
					if(!retAppNa.equalsIgnoreCase(appName.trim()))
						continue;
					Element retParam = element.element(paramName);
					if(retParam.getName().equalsIgnoreCase(paramName)) 
					{
						return retParam.getText().trim();
					}
				}


				if(applicationHash == null)
					return null;
				else
					applicationHash.put(retAppName, applicationInfoHash);

				// masterList.add(ruleSetName);

			}
			return applicationHash;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * This application return application infor based on applicationname
	 * if application name is null then it return last deployed application information.
	 * changes by Wasim Khan, 16-july-2009.
	 * @param currentApplicationName
	 * @return
	 */
	public static IApplication getApplicationInfo(String currentApplicationName)
	{
		Application app = null;
		Hashtable<String, Hashtable<String, String>> applicationHash = 
			(Hashtable<String, Hashtable<String, String>>) retreiveAppInfo("", "", currentApplicationName);
		Iterator<Hashtable<String, String>> apps = applicationHash.values().iterator();
		while (apps.hasNext())
		{
			Hashtable<String, String> storedApp = (Hashtable<String, String>) apps.next();
			app = new Application();
			app.setApplicationFileName(storedApp.get(CoreConstants.applicationName).concat(".esp"));
			app.setApplicationFolderPath(storedApp.get("sourcedir"));
			app.setApplicationName(storedApp.get(CoreConstants.applicationName));
			app.setBaseDirectoryPath(storedApp.get("serverLibDirectory"));
			app.setRenderApplicationType(storedApp.get("renderOption"));
			app.setServerDirectory(storedApp.get("serverDirectory"));
			break;
		}
		return app;
	}


}

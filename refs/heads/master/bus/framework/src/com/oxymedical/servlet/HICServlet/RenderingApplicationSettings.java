package com.oxymedical.servlet.HICServlet;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.propertyUtil.PropertyUtil;

public class RenderingApplicationSettings 
{
	Hashtable<String,Hashtable<String,String>> applicationHash = null;
	Hashtable<String,String> applicationInfoHash = null;
	static RenderingApplicationSettings renderingApplicationSettings = null;

	private RenderingApplicationSettings()
	{
		
	}
	public static RenderingApplicationSettings getInstanceUserAdminSystemSettings()
	{
		if(renderingApplicationSettings == null)
		{
			renderingApplicationSettings = new RenderingApplicationSettings();
		}
		return renderingApplicationSettings;
	}
	
	/*public void createApplicationInfo(Hashtable appHash)
	{
		//File file = new File("C://ApplicationInfo"); 
		try {       

			String persistFilePath = URLConstants.appConfigInfo;
			File configFile = new File(persistFilePath);
			if(configFile.exists())
			{
				boolean oldFileDeleted = configFile.delete();
			}
			OutputStream fout= new FileOutputStream(persistFilePath);
			OutputStream bout= new BufferedOutputStream(fout);
			OutputStreamWriter out 
			= new OutputStreamWriter(bout, "8859_1");

			String serverURL =PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME");
			serverURL = serverURL.replace('\\', '/');

			out.write("<applications>\n");
			Enumeration applicationInfoEnum = appHash.keys();
			
			while(applicationInfoEnum.hasMoreElements())
			{
				String retAppName = (String)applicationInfoEnum.nextElement();
				
				if(retAppName.equalsIgnoreCase("maintenance")) continue;
				if(retAppName.equalsIgnoreCase("BillingTracking")) continue;
				out.write("\t<application>\n");
				
				Hashtable appInfoHash = (Hashtable) appHash.get(retAppName.trim());
				Enumeration appInfoHashEnum = appInfoHash.keys();
				
				while(appInfoHashEnum.hasMoreElements())
				{
					String paramName = (String) appInfoHashEnum.nextElement();
					if(paramName.equalsIgnoreCase(URLConstants.sourcedir))
					{//	continue;
					String paramValue = (String) appInfoHash.get(paramName);
					out.write("\t\t<" + paramName + ">" + paramValue+"/" + "</" + paramName + ">\n");
					}
					if(paramName.equalsIgnoreCase(URLConstants.serverLibDirectory) || paramName.equalsIgnoreCase(URLConstants.applicationName) || paramName.equalsIgnoreCase(URLConstants.renderOption))
					{//	continue;
						String paramValue = (String) appInfoHash.get(paramName);
						out.write("\t\t<" + paramName + ">" + paramValue + "</" + paramName + ">\n");
					}
				}
				out.write("\t\t<serverDirectory>" + serverURL + "/autodeploy</serverDirectory>\n");
				out.write("\t</application>\n");
			}

			out.write("</applications>\n");
			out.flush();  // Don't forget to flush!
			out.close();
		}
		catch(Exception e)
		{
			// e.printStackTrace();
		}

	}*/

	
	public void createApplicationInfo(Hashtable appInfoHash)
	{
		try
		{
			Enumeration applicationInfoEnum = appInfoHash.keys();
			
			while(applicationInfoEnum.hasMoreElements())
			{
				String retAppName = (String)applicationInfoEnum.nextElement();
				Hashtable appHash = (Hashtable) appInfoHash.get(retAppName.trim());
				
				boolean addExtension = appHash.containsKey(URLConstants.BASE_APPLICATION_NAME);
	
				String persistFilePath = URLConstants.appConfigInfo;
				File configFile = new File(persistFilePath);
				if ((!addExtension) && (configFile.exists()))
				{
					configFile.delete();
				}
				
				String serverURL =PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME");
				System.out.println("---------------Inside RenderingApplication-----serverURL="+serverURL);
				serverURL = serverURL.replace('\\', '/');
				System.out.println("---------------After Replace-----serverURL="+serverURL);
				SAXReader reader = new SAXReader();
		        Document document = null;
		        Element rootElem = null;
		        
		        if (addExtension)
		        {
		        	// TODO - Check if there is already an entry pertaining to current App info. If yes, update the existing entry
		        	document = reader.read(persistFilePath);
		        	Node node = document.selectSingleNode( "//applications/application/extensions");
		        	if (node == null)
		        	{
						node = document.selectSingleNode("//applications/application/serverDirectory");
			        	rootElem = node.getParent();
						rootElem = rootElem.addElement("extensions");
		        	}
		        	else
		        	{
		        		rootElem = node.getParent().element("extensions");
		        	}
		        	System.out.println("node Parent\n" + node.getParent().asXML());
					System.out.println("rootElem\n" + rootElem.asXML());
		        	System.out.println("node\n" + node.asXML());
					
					// Check if already existing
					List appElem = document.selectNodes("//applications/application/extensions/application/" + URLConstants.applicationName);
					for (Iterator iter = appElem.iterator(); iter.hasNext(); ) 
					{
						Object obj = iter.next();
						System.out.println("[obj]" + obj);
			            Element elem = (Element) obj;
			            System.out.println("elem\n" + elem.asXML());
			            String appName = elem.getText();
			            if (appHash.get(URLConstants.applicationName).equals(appName))
			            {
			            	rootElem.remove(elem.getParent());
			            	break;
			            }
			        }
		        }
		        else
		        {
		        	document = DocumentHelper.createDocument();
		        	rootElem = document.addElement( "applications" );
		        }
	
		        rootElem = rootElem.addElement("application");
		        addElement(rootElem, URLConstants.applicationName, (String)appHash.get(URLConstants.applicationName));
		        addElement(rootElem, URLConstants.serverLibDirectory, (String)appHash.get(URLConstants.serverLibDirectory));
		        addElement(rootElem, URLConstants.renderOption, (String)appHash.get(URLConstants.renderOption));
		        addElement(rootElem, URLConstants.sourcedir, (String)appHash.get(URLConstants.sourcedir)+"/");
		        addElement(rootElem, "serverDirectory", serverURL + "/autodeploy");
	
		        // Pretty print the document to System.out
		        OutputFormat format = OutputFormat.createPrettyPrint();
		        XMLWriter writer = new XMLWriter(new FileWriter( persistFilePath ), format);
		        writer.write( document );
		        writer.close();
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}

	}
	
	
	private void addElement(Element rootElem, String elemName, String elemValue)
	{
        Element elem = null;
        elem = rootElem.addElement(elemName);
        elem.addText(elemValue);

	}
	

	
	/*public Object retreiveAppInfo(String appName,String paramName,String currentAppName)
	{
		
		applicationHash = new Hashtable<String,Hashtable<String,String>>();
		 String retriveFileName = null;
		if(currentAppName == null || currentAppName=="" || currentAppName.equalsIgnoreCase(" "))
		{
			retriveFileName = URLConstants.appConfigInfo;
		}
		else
		{
			retriveFileName = URLConstants.appInfo +currentAppName+"/"+URLConstants.appFolder+
            "/"+URLConstants.APPLICATION_INFO_FILENAME;
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
	                 	Element retParam = element.element(URLConstants.applicationName);
	                	Element retParamSrcDir = element.element(URLConstants.sourcedir);
	                	Element retParamServerLibDir = element.element(URLConstants.serverLibDirectory);
	                	Element retParamRenderOption = element.element(URLConstants.renderOption);	                	
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
	                	Element retAppEle = element.element(URLConstants.applicationName);
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
	

	public IApplication getApplicationInfo(String currentApplicationName)
	{
		Application app = null;
		Hashtable<String, Hashtable<String, String>> applicationHash = 
				(Hashtable<String, Hashtable<String, String>>) retreiveAppInfo("", "", currentApplicationName);
		Iterator<Hashtable<String, String>> apps = applicationHash.values().iterator();
		while (apps.hasNext())
		{
			Hashtable<String, String> storedApp = (Hashtable<String, String>) apps.next();
			if (!storedApp.get(URLConstants.applicationName).equals(URLConstants.USER_ADMIN_APP))
			{
			app = new Application();
			app.setApplicationFileName(storedApp.get(URLConstants.applicationName).concat(".esp"));
			app.setApplicationFolderPath(storedApp.get("sourcedir"));
			app.setApplicationName(storedApp.get(URLConstants.applicationName));
			app.setBaseDirectoryPath(storedApp.get("serverLibDirectory"));
			app.setRenderApplicationType(storedApp.get("renderOption"));
			app.setServerDirectory(storedApp.get("serverDirectory"));
			break;
		}
		return app;
	}
*/
	
	public String getApplicationName(String appPath)
	{
		String appName=null;
		String strapp=null;

		String[] appArray = appPath.trim().split("/");
		if(appArray.length>0)
			strapp = appArray[(appArray.length-1)];
		String[] lastApp = strapp.trim().split(".zul");
		if(lastApp.length>0)
			appName =lastApp[0];



		return appName;
	}

	public static void main(String[] args)
	{
		Hashtable<String, Object> appHash = new Hashtable<String, Object>();
		appHash.put("serverLibDirectory", "D:/om/dev/glassfish/domains/domain1/lib/ext/");
		appHash.put("renderOption", "ZK");
		appHash.put("serverDirectory", "D:/OM/dev/glassfish/domains/domain1//autodeploy");

		/*appHash.put("ApplicationName", "NEISUI");
		appHash.put("sourcedir", "D:/temp/cruisecontrol/projects/NOLIS/fromsvn/dev/src/main/NOLISApps/NOLIS_Wisconsin/NEISUI/");*/

		/*appHash.put("ApplicationName", "NEISPSG");
		appHash.put("sourcedir", "D:/temp/cruisecontrol/projects/NEISPSG/fromsvn/dev/src/main/NOLISApps/NOLIS_Wisconsin/NEISUI/");
		appHash.put(URLConstants.BASE_APPLICATION_NAME, "NEISUI");*/
		
		appHash.put("ApplicationName", "BrainK");
		appHash.put("sourcedir", "D:/temp/cruisecontrol/projects/BrainK/fromsvn/dev/src/main/NOLISApps/NOLIS_Wisconsin/NEISUI/");
		appHash.put(URLConstants.BASE_APPLICATION_NAME, "NEISUI");

		Hashtable appInfoHash = new Hashtable();
		appInfoHash.put("BrainK", appHash);
		
		RenderingApplicationSettings ras = RenderingApplicationSettings.getInstanceUserAdminSystemSettings();
		ras.createApplicationInfo(appInfoHash);
	}
}
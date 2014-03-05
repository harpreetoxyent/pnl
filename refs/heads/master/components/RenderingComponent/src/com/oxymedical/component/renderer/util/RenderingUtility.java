package com.oxymedical.component.renderer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.dom4j.Document;
import org.dom4j.Element;

import com.oxymedical.component.renderer.Application;
import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.component.renderer.exception.RendererComponentException;
import com.oxymedical.core.ioutil.FileIO;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.xmlutil.XmlReader;
/**
 * this utility class helps the Controller and Modeler
 *
 */
public class RenderingUtility 
{
	private static FileIO fileIO;
	
	public RenderingUtility()
	{
		fileIO = new FileIO();
	}
	
	
	/**
	 * this method is used to get the class name
	 * @param String clsName
	 * @return retClassName
	 */
	public static String getClassName(String clsName)
	{
		String retClassName = "";
		retClassName = clsName.substring(0,1).toUpperCase() + clsName.substring(1);
		return retClassName;
	}
	
	/**
	 * this method is used to get the event name 
	 * @param String eventObject
	 * @param String eventName
	 * @return
	 */
	public static String getEventName(String eventObject, String eventName)
	{
		String retEventName = "";
		retEventName = eventObject.substring(0,1).toLowerCase() + eventObject.substring(1) + "_" + eventName.substring(0,1).toUpperCase() + eventName.substring(1);
		return retEventName;
	}
	
	
	public static String[] getConnectionSettings(String dbSettingsFile) throws RendererComponentException
	{
		String connectionSettings[] = {"","","",""};
		String driverClass, serverName, user, pwd;
					
			XmlReader xmlReader = new XmlReader();
			Document databaseConfig = xmlReader.getDocumentFromPath(dbSettingsFile);
			
			Element el = databaseConfig.getRootElement();
			//connUrl = "jdbc:mysql://localhost:3306/datapattern0";
			driverClass = "com.mysql.jdbc.Driver";
			serverName = el.element("servername").getTextTrim();
			user = el.element("user").getTextTrim();
			pwd = el.element("password").getTextTrim();
			connectionSettings[0] = serverName;
			connectionSettings[1] = driverClass;
			connectionSettings[2] = user;
			connectionSettings[3] = pwd;
		
		return connectionSettings;
	}
	
	
	public static void loadZKJar(Application application)
	{
		 String lib="";
		 String applicationName = application.getApplicationName();
		  RendererComponent.logger.log(0,"-------------in side move method in rendering component------------------");
		  String[] libs ={"zcommon.jar","zcommons-el","zhtml.jar","zk.jar","zkex.jar","zkmax.jar","zkplus.jar","zml.jar","zul.jar","zweb.jar"};
		  String jarPath= PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME")+"/applications/j2ee-modules/"+applicationName+"/lib/";
		  for(int i=0;i<libs.length;i++)
		  {
		   lib=jarPath+libs[i];
		   RendererComponent.logger.log(0,"lib is -----"+lib);
		      try {
				addFile(lib);
			} catch (IOException e) {
				e.printStackTrace();
			}
		      lib="";
		  }
			
	}
	
	
	 public static void addFile(String jarFile) throws IOException 
	 {
		  File file = new File(jarFile); 
		    addURL(file.toURL());
	 }

		 
	 public static void addURL(URL url) throws IOException
	 {
		   URL urls[] = new URL[ ]{ url };
		   ClassLoader aCL = Thread.currentThread().getContextClassLoader();
		   URLClassLoader aUrlCL = new URLClassLoader(urls, aCL);

		   Thread.currentThread().setContextClassLoader(aUrlCL);
	}
		
	public static void jarLoader(Application application) throws RendererComponentException
	{
		String applicationName = application.getApplicationName(); 
		List<String> jarList= getJarList();
		String jarLocation= PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME")+"/applications/j2ee-modules/"+applicationName+"/lib/";
		  
		int jarCount = jarList.size();
		RendererComponent.logger.log(0,"no of array-------"+jarCount);
		try
		{
			
			URL[] url = new URL[jarCount];
			for(int jarCounter = 0; jarCounter < jarCount; jarCounter++)
			{
				RendererComponent.logger.log(0,jarLocation+jarList.get(jarCounter));
				File jarFile = new File(jarLocation+jarList.get(jarCounter));
				url[jarCounter] = jarFile.toURL();
			}
			ClassLoader classLoader = RendererComponent.class.getClassLoader();
			URLClassLoader loader1 = new URLClassLoader(url,classLoader);
			RendererComponent.logger.log(0,"-------------loader1----------"+loader1);
			Thread.currentThread().setContextClassLoader(loader1);
			for(int jarCounter = 0; jarCounter < jarCount; jarCounter++)
			{
				JarFile jarFile = new JarFile(jarLocation+jarList.get(jarCounter));
				RendererComponent.logger.log(0,"jar name = "+jarFile.getName());
		        Enumeration<JarEntry> entries = jarFile.entries();
		        
		        while (entries.hasMoreElements())
		        {
		            ZipEntry entry = (ZipEntry) entries.nextElement();
		            String str=entry.getName();
		            RendererComponent.logger.log(0,"entry name =" + str);
		            int indexOfDot = str.indexOf(".");	
		            if(indexOfDot > 0)
		            {
		            	String extensionFile = str.substring(indexOfDot);
		                if(extensionFile.trim().equals(".class"))
		                {	
		                	str = str.substring(0, indexOfDot);
			                str = str.replace("/", ".");
		                    try {
								loader1.loadClass(str.trim());
							} catch (ClassNotFoundException e) {
								RendererComponent.logger.log(0,"---1----------------------------");
								e.printStackTrace();
							}
		                }
		             }
		        }
			}
			}
			catch (FileNotFoundException exp) {
				RendererComponent.logger.log(0,"---22--------------------------");
				exp.printStackTrace();
				throw new RendererComponentException(exp.getMessage());
			}
			catch(IOException exp)
			{
				RendererComponent.logger.log(0,"---33--------------------------");
				exp.printStackTrace();
				throw new RendererComponentException(exp.getMessage());
			}
	    
			RendererComponent.logger.log(0,"---JAR UPLOADED SUCCESSFULLY---------------");
	}
	
	public static List<String> getJarList()
	{
		String[] libs ={"zk.jar","zhtml.jar","zcommon.jar",/*"zcommons-el","zkplus.jar",*/"zkmax.jar","zkex.jar","zml.jar","zul.jar","zweb.jar"};
		List<String> jarList = new ArrayList<String>();
		for(int counter=0; counter<libs.length;counter++)
		{
			jarList.add(libs[counter]);
		}
		
		
		return jarList;
	}
	
	public String[][] iterateListData(List listValue, String fields[])
	{
		
		Iterator itr = listValue.iterator();
		int counter=0;
		int objectCounter =0;
		Object retValue = null;
		String[][] allValues = new String[listValue.size()][];
		while(itr.hasNext())
		{
			Object obj = itr.next();
			if(!obj.getClass().isArray())
			{
			}
			else
			{
		    	Object[] objArr = (Object[])obj;
		    	
				if(null == obj)
					continue;
				allValues[counter]= new String[objArr.length];
				for(objectCounter = 0;objectCounter<objArr.length; objectCounter++)
				{
					String retField =  fields[objectCounter];
					retValue = objArr[objectCounter];
					allValues[counter][objectCounter] = retValue.toString();
					System.out.println("--- in renderutility----"+retValue.toString());
				}	
			}
			counter++;
		}
		
		
		return allValues;
	}
	
	
	public static void copyGeneratedFilesToDeployedApplication(String baseDestPath, String baseSourcePath) throws IOException
	{
		// Move library to render library folder
		fileIO.copyDirectory(baseSourcePath, baseDestPath);
	}
	
	
}

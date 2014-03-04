package com.oxymedical.component.db;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.hibernate.cfg.Configuration;

import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;

/**
 * This class is used to create configurations file containing class mappings and 
 * to load the resources jar. 
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class DatabaseSetUpUtilities 
{
	/**It is the temp file for creating the main configuration file.  */
    File configFile = null;
    /** */
	String configSettings = null;
	/**This is configuration file that contains the pozo and table mappings.*/
	Configuration config = null;
	
	/**
 	* This method is used to set the object of Configuration class
 	* into config class variable.  
 	* @param config 
 	* @return void
 	*/
	@SuppressWarnings("unchecked")
	public void setConfiguration(Configuration config) 
	{
		this.config = config;
	}
	/**
 	* This method is used to get the instance of Configuration
 	* class of hibernate.  
 	* @return Configuration
 	*/
	public Configuration getConfiguration() 
	{
		return this.config;
	}	
     //	 can give this method as protected
	protected Configuration CreateConfiguration() throws DBComponentException
	{
		config = new Configuration();
		try 
		{
			//File.c
			if(null != configSettings)	  
			{
				configFile = File.createTempFile(DBConstants.DB_HIB_CFG , DBConstants.DB_XML );
				configFile.delete();
				BufferedWriter out = new BufferedWriter(new FileWriter(configFile));
				out.write(configSettings);
				out.close();
			}
			else  // null exception
				throw new DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION + DBConstants.DB_CONFIG_SETTING + configSettings + "in CreateConfiguration");

		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DBComponentException("IOException" + DBConstants.DB_CONFIG_SETTING + configSettings + "in CreateConfiguration");
		}
		config.configure(configFile);
		return config;
	}
	
	/**
 	* This method is used to create the configuration and to get the instance of Configuration
 	* class of hibernate. 
 	* @param modulefileStream 
 	* @return Configuration
 	*/
	public Configuration CreateConfigurationFromInputStream(InputStream modulefileStream) throws DBComponentException
	{
		File configurationFile = null;
		StringBuffer str = new StringBuffer();
		byte b[] = new byte[1];
		try 
		{
			if(null != modulefileStream)
			{
				while ( modulefileStream.read(b) != -1 )
				{
					str.append(new String(b));
				}
				modulefileStream.close();
				configurationFile = File.createTempFile(DBConstants.DB_HIB_CFG, DBConstants.DB_XML);
				FileOutputStream fout = new FileOutputStream(configurationFile);
				
				configurationFile.delete();
				BufferedWriter out = new BufferedWriter(new FileWriter(configurationFile));
				out.write(str.toString()); 
				out.close();
				fout.close();
				
			}
			else  // null exception
			{
				throw new DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION + DBConstants.DB_MODULE_FILE + modulefileStream +" in CreateConfigurationFromInputStream");
			}
			config = new Configuration();
			config.configure(configurationFile);
			
		}		
		catch (IOException e) 
		{
		//	e.printStackTrace();
			// remove 
			throw new DBComponentException(DBExceptionConstants.IO_EXCEPTION +"in CreateConfigurationFromInputStream");
		}
			
		return config;
	}		

	/**
 	* This method is used to load jar in Loader. 
 	* @param pathJarFile 
 	* @param jarFileName
 	* @return URLClassLoader
 	*/
	public URLClassLoader loadResourcesJarInLoader(String pathJarFile, String jarFileName, URLClassLoader DBClassLoader) throws DBComponentException
	{
		configSettings = "";
		JarFile jarFile = null;
		File normalFile = null;
		URL classArray[] = new URL[1];
		try
		{    
			System.out.println("--pathJarFile- "+pathJarFile);
			System.out.println("--jarFileName- "+jarFileName);
			 normalFile = new File(pathJarFile + DBConstants.DB_SLASH + jarFileName);
			 System.out.println("----normalFile--"+normalFile);
			 ClassLoader classLoader = DBComponent.class.getClassLoader();
			 classArray[0] = normalFile.toURL();
			 DBClassLoader = new URLClassLoader(classArray , classLoader);
			 Thread.currentThread().setContextClassLoader(DBClassLoader);
			 jarFile = new JarFile(normalFile);
             Enumeration entries = jarFile.entries();
             while (entries.hasMoreElements()) 
             {
                  ZipEntry entry = (ZipEntry) entries.nextElement();
                  String str=entry.getName();
                  int indexOfDot = str.indexOf(DBConstants.DB_DOT);	
                  if(indexOfDot > 0)
                  {
                      String extensionFile = str.substring(indexOfDot);
                      if(extensionFile.trim().equals(DBConstants.DB_CLASS))
                      {	 
                         str = str.substring(0, indexOfDot);
                         str = str.replace(DBConstants.DB_SLASH , DBConstants.DB_DOT );
                         DBClassLoader.loadClass(str);
                        
                      }
                      else if(extensionFile.trim().equals(DBConstants.DB_CFG_XML))
                      {
                          InputStream input = jarFile.getInputStream(entry);
                          InputStreamReader isr =  new InputStreamReader(input);
                          BufferedReader reader = new BufferedReader(isr);
                          String line;
                          while (null != (line = reader.readLine())) 
                          {
                        	  configSettings = configSettings + line + DBConstants.DB_NEW_LINE;
                          }
                          reader.close();
                          isr.close();
                          input.close();
                      }
                  }            
             }
         }
		catch(FileNotFoundException fne)
		{
			fne.printStackTrace();
			throw new DBComponentException(DBExceptionConstants.FILE_NOT_FOUND_EXCEPTION + pathJarFile + DBConstants.DB_COMMA + jarFileName + "in loadResourcesJarInLoader");
		}
		catch(ClassNotFoundException cnfe)
		{
			//cnfe.printStackTrace();
			throw new DBComponentException(DBExceptionConstants.CLASS_NOT_FOUND_EXCEPTION + pathJarFile + DBConstants.DB_COMMA + jarFileName + "in loadResourcesJarInLoader");
		}
		catch(IOException io)
		{
			//io.printStackTrace();
			throw new DBComponentException(DBExceptionConstants.IO_EXCEPTION + pathJarFile + DBConstants.DB_COMMA + jarFileName + "in loadResourcesJarInLoader");
		}
		
		return DBClassLoader;
	}
	
}

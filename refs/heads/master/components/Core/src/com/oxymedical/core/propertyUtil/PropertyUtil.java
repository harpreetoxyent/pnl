package com.oxymedical.core.propertyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.naming.spi.DirectoryManager;

public class PropertyUtil 
{
/*	private static Properties props = null;
	
	private static Properties createInstance()
	{
		if (props == null)
		{
			props = new Properties();
			try
			{
				props.load(new FileInputStream(new File("./setup.properties")));
			}
			catch (IOException ie)
			{
				System.out.println("Error reading file");
				ie.printStackTrace();
			}
		}
		return props;
	}
	
	
	public static String setUpProperties(String key)
	{
		String propertyValue = null;
		if (null != createInstance())
		{
			propertyValue = props.getProperty(key);
		}
		return propertyValue;
	}
*/	
	
	public static String setUpProperties(String key){
		Properties props=new Properties()  ;   	
		String currentDirectory = System.getProperty("user.dir");
		currentDirectory = currentDirectory.replaceAll("\\\\", "/");
		currentDirectory =  currentDirectory + "/setup.properties";
		FileInputStream fis = null;
		String propertyValue = "";
		try{
			fis = new FileInputStream(currentDirectory);
			props.load(fis) ;
			propertyValue = props.getProperty(key);
		}catch (IOException ie){  
			System.out.println ("Error reading setup.properties file in currentDirectory="+currentDirectory); 
			ie.printStackTrace();
		}  
		finally{
			try{
				if(fis != null){
					fis.close();
				}
			}catch(IOException ex){
				System.out.println ("Error closing setup.properties file");
				ex.printStackTrace();
			}
		}
		return propertyValue;
	}

	//setting properties into the key using a file 
	/**
	 * @param filePath
	 * @param key
	 * @return
	 */
	public static String setUpProperties( String filePath,String key)
	{	
		  Properties props=new Properties()  ;   	  
		   try {  
		     props.load (  new FileInputStream ( new File (filePath))) ; 
		    
		    } catch ( IOException ie )  {  
		     System.out.println ( "Error reading file" ) ; 
		     ie.printStackTrace();
		    }  
		   String propertyValue = props.getProperty (key);
		return propertyValue;
	}
	
	
	public static Properties getPropertiesObject( String filePath)
	{	
		Properties props=new Properties()  ;   	  
		   try {  
		     props.load (  new FileInputStream ( new File (filePath))) ; 
		    
		    } catch ( IOException ie )  {  
		     System.out.println ( "Error reading file" ) ; 
		     ie.printStackTrace();
		    }  
		return props;
	}
}

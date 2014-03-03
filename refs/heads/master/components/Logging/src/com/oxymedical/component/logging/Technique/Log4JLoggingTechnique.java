package com.oxymedical.component.logging.Technique;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.dom4j.Document;

import com.oxymedical.component.logging.ILoggingTechnique;
import com.oxymedical.component.logging.exception.LoggingComponentException;
import com.oxymedical.component.logging.settings.ConfigurationSettings;



// Java Docs missing
public class Log4JLoggingTechnique implements ILoggingTechnique 
{
	public ConfigurationSettings configSettings = null;// shud not be public 	

	private static Logger aspectLogger = null; // shud not be static 
	public static Log4JLoggingTechnique loggingTechnique = null;// shud not be static 
	private Level logLevel = null;
	public Log4JLoggingTechnique()
	{
		aspectLogger =	Logger.getRootLogger();
		
	}
	public void log(int level, String parameter) 
	{
		if(level == 0)//Define constants for 0 -RK
		{
			logLevel = Level.DEBUG;
		}
		if(level == 1)//Define constants for 1 -RK
		{
			logLevel = Level.INFO;
		}
		if(level == 2)
		{
			logLevel = Level.WARN;
		}
		if(level == 3)//Define constants for 3 -RK
		{
			logLevel = Level.ERROR;
		}
		if(level == 4)//Define constants for 4 -RK
		{
			logLevel = Level.FATAL;
		}
		if(level == 5)//Define constants for 4 -RK
		{
			logLevel = Level.OFF;
		}
		//Null check	
		if(null != logLevel)
		{
			aspectLogger.log(logLevel,parameter);
		}
	}
	
	/**
	 * This function is used to get rootLogger 
	 * for Logging Component
	 * @return Logger
	 */
	public Logger getRootLogger()
	{
		return aspectLogger;
	}
	/**
	 * This function is used to get Configuration parameters 
	 * for Logging Component
	 * @return ConfigurationSettings
	 */	
	public ConfigurationSettings getConfigSettings() 
	{
		return configSettings;
	}	
	/**
	 * This function takes  xml file,sets the configuration
	 *  and performs the logging  
	 * for Logging Component.
	 * This object overrides the default configuration settings of 
		Logging.
	 * @param  xmlDoc  Document giving the configuration settings
	 * @return     void
	 */
	public void setConfigSettings(Document xmlDoc)  throws LoggingComponentException
	{
		Writer output = null;//Change the name of variable-RK
		// Aspect Logger null check - RK
		aspectLogger.setLevel(Level.ALL);
		// null check  for XMLDoc -RK
		try
		{
			String xmlDocument = (xmlDoc).asXML();
		    File memoryFile = File.createTempFile("Configuration", ".xml");//Put in constants file-RK
		    boolean fileCreate = memoryFile.exists();
		    if(fileCreate)
		    {
		    	output  = new BufferedWriter( new FileWriter(memoryFile) );
		    	output.write( xmlDocument );
		    	output.close();
		    	
		    //	System.out.println("memoryFile.getAbsolutePath()="+memoryFile.getAbsolutePath());
		    	String memoryFilePath =memoryFile.getAbsolutePath();
		    	DOMConfigurator.configure(memoryFilePath);
		    //	System.out.println("Configuration done");
		    }
		    memoryFile.deleteOnExit();
		}
	
		catch(IOException exp)
		{
			throw new LoggingComponentException(exp.getMessage()); 
		}
		catch(Exception exp)
		{
			throw new LoggingComponentException(exp.getMessage()); 			
		}
	    finally // Put in finaaly close - RK
	    {
	        //flush and close both "output" and its underlying FileWriter
	        if (output != null)
	        	{
	        		//output.close();
	        	}
	      }
	}
	/**
	 * This function is used to add an appender 
	 * for Logging Component
	 * @param fileAppeneder is used for directing and saving the log statements 
	 * A Handler object takes log messages from a Logger and exports them.
	 * @return      void
	 *  */
   
    public void addHandler(FileAppender fileAppender)
    {
    	// null check aspectlogger - RK
    	aspectLogger.addAppender(fileAppender);
    }

    /**
	 * This function is used to get Logger 
	 * for Logging Component
	 * 
	 * @param stringname
	 * @return      Logger
	 */
    
    public  Logger getLogger(String name)
    {
    	return aspectLogger;
    }
}

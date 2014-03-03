package com.oxymedical.component.logging;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.logging.Technique.Log4JLoggingTechnique;
import com.oxymedical.component.logging.exception.LoggingComponentException;
import com.oxymedical.component.logging.settings.ConfigurationSettings;
import com.oxymedical.component.logging.settings.Pattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.containerInfo.Event;
import com.oxymedical.core.maintenanceData.IMaintenanceData;

// Check all the comments

/**
  * Logging is the process of notifying some entity of a particular 
  * event. An entity can be the system console, a file on disk, a database, 
  * or a remote computer, to name a few. -Modify this comment make it more suitable as func of class = RK
  * @author      Oxyent Medical
  * @version     1.0.0
  */

public class LoggingComponent implements ILoggingComponent, IComponent 
{

	public Pattern pattern;//Delete public -RK
	//private static LoggingComponent loggingComponent = null; 	//delete  
	private static ILoggingTechnique currentLoggingTechnique = null;// static not needed
	private InputStream moduleStream = null;	
	public IConfigurationSettings configSettings = null;	

	/**
	 * This method is not required -RK
	 * This function is used to get the Logger and sets the configuration settings
	 * for Logging Component
	 * @return     LoggingComponent Returns an instance of Logging component.
	 */
//	public static LoggingComponent getLoggingComponentInstance() throws LoggingComponentException
//	{
//		
//		if(loggingComponent== null)
//		{
//			loggingComponent = new LoggingComponent();
//			currentLoggingTechnique = new Log4JLoggingTechnique();
//			loggingComponent.setConfigSettings();
//			
//		}
//		return loggingComponent;
//	}
	
	
	public  LoggingComponent()
	{
		currentLoggingTechnique = new Log4JLoggingTechnique();
		try 
		{
			setConfigSettings();
		} 
		catch (LoggingComponentException e) 
		{
			e.printStackTrace();
		}
	}
	/**
	 * This function is used to getConfigurationSettings 
	 * for IConfigurationSettings.
	 * @return configSettings-- COmplete - RK
	 */
	
	public IConfigurationSettings getConfigSettings() 
	{
		return configSettings;
	}	
	/**
	 * This function is used to set the configuration settings 
	 * for IConfigurationSettings.
	 * @return void
	 * @throws LoggingComponentException 
	 */
	
	public void setConfigSettings() throws LoggingComponentException // not a public method
	{
		Document document = null; 	
		InputStreamReader inR = null;
		try
		{
			moduleStream = this.getClass().getResourceAsStream(LoggingConstants.configPath);
			SAXReader reader = new SAXReader();	
			inR = new InputStreamReader(moduleStream);
			document = (Document)reader.read(inR);
			//if((null != loggingComponent) &&(null!= document))
			//{
				getCurrentLoggingTechnique().setConfigSettings(document) ;
				configSettings = new ConfigurationSettings(getCurrentLoggingTechnique().getRootLogger());
			//}

		}
		catch(DocumentException exp)
		{
			throw new LoggingComponentException(exp.getMessage()); 
		}
		finally
		{
			try 
			{
				inR.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This function is used to get log pattern 
	 * for Logging Component
	 * @return pattern
	 *  */
	
	public Pattern getPattern() 
	{
		return pattern;
	}
	
	/**
	 * This function is used to set log pattern 
	 * for Logging Component
	 * @param Pattern    Logging component maintains default
	 *  pattern which is used by Log pattern to log data.
	 * @return      void
	 */

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	


	/**
	 * This function is used to define the level and parameter information for logging 
	 * for Logging Component
	 * @param level   Set the log level specifying 
	 * which message levels will be logged.
	 * @param parameter  It represents the parameters to be logged
	 * e.g. name of the class, method name 
	 * @return      void
	 */
	public void log(int level, String parameter) 
	{
		currentLoggingTechnique.log(level, parameter);
	}
	/**
	 * This function is used to execute 
	 * the Logging Component.
	 * @param event  the event to be logged
	 * @param HICData   HIC will notify the Logging component 
	 * with the log point (Event) and HIC Data Object.
	 * @return void
	 * */
	public void execute(Event evt, HICData hicData) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This function is used to destroy 
	 * the Logging Component.
	 * @return void
	 * */
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This function is used to run 
	 * the Logging Component.
	 * @return void
	 * */
	public void run() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * This function is used to start
	 * the Logging Component.
	 * @return void
	 * */
	public void start(Hashtable<String, org.dom4j.Document> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This function is used to stop
	 * the Logging Component.
	 * @return void
	 * */
	public void stop() 
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This function is used to maintenance
	 * the Logging Component.
	 * @return void
	 * */
	public void maintenance(IMaintenanceData m) 
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This function is used to getCurrentLoggingTechnique 
	 * for ILoggingTechnique.
	 *  */
	public ILoggingTechnique getCurrentLoggingTechnique() 
	{
		return currentLoggingTechnique;
	}
	/**
	 * This function is used to setCurrentLoggingTechnique
	 *  return void
	 *  @param ILoggingTechnique
   * */
	public void setCurrentLoggingTechnique(ILoggingTechnique currentLogging) 
	{
		currentLoggingTechnique = currentLogging;
	}
	/**
	 * This function is used to getLoggingTechnique
	 * for ILoggingTechnique
	 *  return ILoggingTechnique
   * */
	public ILoggingTechnique getLoggingTechnique() 
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * This function is used to setLoggingTechnique
	 *  return void
	 *  @param ILoggingTechnique
   * */
	public void setLoggingTechnique(ILoggingTechnique loggingTechnique) 
	{
		// TODO Auto-generated method stub

	}
	public IHICData getHicData() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setHicData(IHICData arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}

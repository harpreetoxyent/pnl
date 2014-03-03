package com.oxymedical.component.logging;



import com.oxymedical.component.logging.exception.LoggingComponentException;
import com.oxymedical.component.logging.settings.Pattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.containerInfo.Event;
/**
 * This interface contains the method declaration wich are implemented by class LoggingComponent .
 * @author      Oxyent Medical
 * @version     1.0.0
 */
public interface ILoggingComponent 
{
	/**
	 * This function is used to getCurrentLoggingTechnique 
	 * for ILoggingTechnique.
	 */	
	ILoggingTechnique getLoggingTechnique();
	/**
	 * This function is used to setCurrentLoggingTechnique e.g log4j
	 *  return void
	 *  @param ILoggingTechnique
   */
	void setLoggingTechnique(ILoggingTechnique loggingTechnique);	
	/**
	 * This function is used to execute 
	 * the Logging Component to log the event associated with HICData.
	 * @param event  the event to be logged
	 * @param HICData   HICData Object to be logged 
	 * * @return void
	 */
	void execute(Event evt , HICData hicData);
	/**
	 * This function is used to define the level and parameter information for logging 
	 * for Logging Component
	 * @param level   Set the log level specifying 
	 * which message levels will be logged.
	 * @param parameter  It represents the parameters to be logged
	 * e.g. name of the class, method name 
	 * @return      void
	 */
	void log(int level ,String parameter);
	Pattern getPattern();// Delete these public API-RK
	void setPattern(Pattern pattern) ;// Delete these public API -RK
	void setConfigSettings() throws LoggingComponentException; 	// Delete these public API-RK
	
	/**
	 * This function is used to getConfigurationSettings 
	 * for IConfigurationSettings.
	 * @return IConfigurationSettings-- COmplete - RK
	 */
	IConfigurationSettings getConfigSettings(); 
}



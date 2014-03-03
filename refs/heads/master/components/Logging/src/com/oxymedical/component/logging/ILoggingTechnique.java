package com.oxymedical.component.logging;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import com.oxymedical.component.logging.exception.LoggingComponentException;
import com.oxymedical.component.logging.settings.ConfigurationSettings;

/**
 * This function states that LoggingTechnique
 * implements ILoggingTechnique.
 * All fields of ILggingTechnique method 
 * are used in LoggingTechnique.
 **/
public interface ILoggingTechnique 
{
	public void log(int level ,String parameter);
	public Logger getRootLogger();
    public Logger getLogger(String name);
	public ConfigurationSettings getConfigSettings();
	public void setConfigSettings(Document xmlDoc) throws LoggingComponentException ;    
}

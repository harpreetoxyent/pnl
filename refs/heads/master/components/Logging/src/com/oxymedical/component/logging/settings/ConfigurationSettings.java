package com.oxymedical.component.logging.settings;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import com.oxymedical.component.logging.IConfigurationSettings;


/**
* This object overrides the default configuration settings of 
Logging
*/
public class ConfigurationSettings implements IConfigurationSettings
{
		private static Logger aspectLogger = null; 	// non static -RK
		// All access modifiers private , intialize?? - RK
		int levelOfLogging;
		String  seperator;
		long thresholdValue;
		Integer rollbackFiles;
		Boolean performanceLog;
		
		public ConfigurationSettings(Logger logger)
		{
			aspectLogger = logger;
		}
		 /**
		*This function is used to get the log level specifying 
	 	*which message levels will be logged.
		 */
		public int getLevelOfLogging() 
		{
			//RollingFileAppender appender = (RollingFileAppender)aspectLogger.getAppender("rollingFile");
			int value=0; 
			// aspectLogger null check
			Level logLevel = aspectLogger.getLevel();
			 if(logLevel == Level.DEBUG)
				 value=0; // Constants for all  1,2 - RK
			 else if(logLevel == Level.INFO)
				 value=1;
			 else if(logLevel == Level.WARN)
				 value=2;
			 else if(logLevel == Level.ERROR)
				 value=3;
			 else if(logLevel == Level.FATAL)
				 value=4;
			 
			return value; 
		}
		 /**
		*This function is used to set the log level specifying 
	 	*which message levels will be logged.
	 	*@param leveloflogging used to specify the level
	 	*of logging
	 	* @return      void
	 	*/
		public void setLevelOfLogging(int levelOfLogging) {
			this.levelOfLogging = levelOfLogging;
		}
		 /**
		*This function is used to set the log level specifying 
	 	*which message levels will be logged.
	 	*/
		
		public Boolean getPerformanceLog() {
			return performanceLog;
		}
		 /**
		*This function is used to set the performance of
		*Log
		*@return void
	 	*/
		public void setPerformanceLog(Boolean performanceLog) {
			this.performanceLog = performanceLog;
		}
		/**
		*This function is used to get rollback files for
		*Log.Rollback files are used to specify the number 
		*of files.
		*@return integer
	 	*/
		public int getRollbackFiles() 
		{
			// aspectLogger - null check
			RollingFileAppender appender = (RollingFileAppender)aspectLogger.getAppender("rollingFile");// remove hard codin
			// appender null check
			return appender.getMaxBackupIndex();
		}
		/**
		*This function is used to set rollback files for
		*Log.Rollback files are used to specify the number 
		*of files.
		*@return void
	 	*/
		void setRollbackFiles(int rollbackFiles) {
			this.rollbackFiles = rollbackFiles;
		}
		
		/*public String getSeperator() {
			return seperator;
		}
		void setSeperator(String seperator) {
			this.seperator = seperator;
		}*/
		/**
		*This function is used to get the threshhold value for
		*Log.Threshhold value is used to specify the value 
		*for each log file.The size of file is mentioned by
		*using threshhold value.
		*/
		public long getThresholdValue() {
			// appender null check - RK aspect Logger null chek
			RollingFileAppender appender = (RollingFileAppender)aspectLogger.getAppender("rollingFile");// Constants file - RK
			return appender.getMaximumFileSize();
		}
		/**
		*This function is used to set the threshhold value for
		*Log.Threshhold value is used to specify the value 
		*for each log file.The size of file is mentioned by
		*using threshhold value.
		*@param threshhold value
		*@return void
	 	*/
		void setThresholdValue(long thresholdValue) {
			this.thresholdValue = thresholdValue;
		}
	}




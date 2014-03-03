package com.oxymedical.component.logging;


public interface IConfigurationSettings
{
	int getLevelOfLogging();
	void setLevelOfLogging(int levelOfLogging);
	Boolean getPerformanceLog();
	void setPerformanceLog(Boolean performanceLog);
	int getRollbackFiles();
	long getThresholdValue();
	

}


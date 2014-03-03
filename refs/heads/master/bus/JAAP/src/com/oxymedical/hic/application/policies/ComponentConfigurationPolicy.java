package com.oxymedical.hic.application.policies;

import java.util.Hashtable;
import org.dom4j.Document;

public class ComponentConfigurationPolicy implements IComponentConfigurationPolicy
{
	private Hashtable<String,Document> configData = new Hashtable<String,Document>();
	public Hashtable<String,Document> getConfigurationData() 
	{
		return configData;
	}

	public void setConfigurationData(Hashtable<String, Document> configurationData) 
	{
		configData = configurationData;
	}

}

package com.oxymedical.hic.application.policies;

import java.util.Hashtable;

import org.dom4j.Document;

import com.oxymedical.framework.objectbroker.IBuilderPolicy;



public interface IComponentConfigurationPolicy extends IBuilderPolicy 
{
	public Hashtable<String,Document> getConfigurationData(); 
	public void setConfigurationData(Hashtable<String,Document> configurationData); 
	
}

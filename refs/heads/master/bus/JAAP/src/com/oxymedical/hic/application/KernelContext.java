package com.oxymedical.hic.application;

import java.util.Hashtable;

import com.oxymedical.framework.objectbroker.Builder;
import com.oxymedical.framework.objectbroker.Location.Locator;
import com.oxymedical.hic.application.eventmanagement.EventTopic;




public class KernelContext 
{
	private Hashtable<String,EventTopic> eventTopicList;
	private Builder builder = null;
	private Locator locator = null;	
	public KernelContext()
	{
	//empty constructor for the prototype	
	}
	public void initializeKernelContext(Builder builder,Locator locator)
	{
		this.builder = builder;
		this.locator = locator;
		InitializeCollectionFacades();
	}
	private void InitializeCollectionFacades()
	{
		if (eventTopicList == null)
		{
			eventTopicList = new Hashtable<String,EventTopic>();
		}
	}
	public EventTopic getEventTopic(String eventTopic)
	{
		return (EventTopic)eventTopicList.get(eventTopic);
	}
	public void addEventTopic(String topicName, EventTopic eventTopic) 
	{
		this.eventTopicList.put(topicName,eventTopic);
	}	
}

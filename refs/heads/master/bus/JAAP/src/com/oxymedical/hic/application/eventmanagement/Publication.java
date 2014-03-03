package com.oxymedical.hic.application.eventmanagement;
import java.lang.ref.WeakReference;

public class Publication 
{
	private EventTopic topic;
	private WeakReference wrPublisher;
	private PublicationScope scope;
	private String eventName;
	public Publication(EventTopic topic, 
						Object publisher, 
						String eventName, 
						PublicationScope scope)
	{
		this.topic = topic;
		this.wrPublisher = new WeakReference(publisher);
		this.scope = scope;
		this.eventName = eventName;
	}
	public Object getPublisher()
	{
		return this.wrPublisher.get();
	}
	public String getEventName()
	{
		return this.eventName;
	}
	public PublicationScope getScope()
	{
		return this.scope;
	}

}

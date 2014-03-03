package com.oxymedical.hic.application.eventmanagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;

public class EventTopic 
{
	private String name;
	private boolean enabled = true;
	private List<Publication> publications = new ArrayList<Publication>();
	private List<Subscriptions> subscriptions = new ArrayList<Subscriptions>();
	public EventTopic(String name,boolean enabled)
	{
		this.name = name;
		this.enabled = enabled;
	}
	public String getName()
	{
		return name; 
	}
	public boolean getIsEnabled()
	{
		return enabled;
	}
	public void setIsEnabled(boolean enabled)
	{
		this.enabled = enabled; 
	}
	public void addPublication(Object publisher, String eventName, PublicationScope scope)
	{
		Publication publication = null;
		publication = new Publication(this,
									  publisher, 
									  eventName,
									  scope);			
		publications.add(publication);
		
		
	}
	public boolean containsPublication(Object publisher, String eventName)
	{		
		return findPublication(publisher, eventName) != null;
	}
	public void removePublication(Object publisher, 
									String eventName)
	{
		Publication publication = findPublication(publisher, eventName);
		if (publication != null)
		{
			publications.remove(publication);
		}
	}
	public int getPublicationCount()
	{
		return publications.size();
	}
	public IHICData Fire(Object sender, Object[] e, PublicationScope scope)
	{
		if (enabled)
		{
			IHICData returnList = callSubscriptionHandlers(sender, e, getAllHandlers());
			return returnList;
		}	
		return null;
	}
	public void addSubscription(Object subscriber, String handlerMethodName,  Class[] parameterTypes)
	{
		
		Subscriptions subscription = null;
		subscription = new Subscriptions(subscriber, 
						handlerMethodName,
						parameterTypes);		
		subscriptions.add(subscription);
	}

	private EventTopicFireHandler[] getAllHandlers()
	{
		List<EventTopicFireHandler> handlers = new ArrayList<EventTopicFireHandler>();
		for(Subscriptions subscription : subscriptions)
		{
			handlers.add(subscription.getEventTopicHandler());
		}
		//EventTopicFireHandler[] listCollection = handlers.toArray();
		EventTopicFireHandler[] listCollection = handlers.toArray(new EventTopicFireHandler[handlers.size()]);
		return listCollection;
	}
	
	
	public void removeSubscription(Object subscriber, String handlerMethodName)
	{
//		for (Subscriptions wis : subscriptions)
//		{
//	//		wis.removeSubscription(subscriber, handlerMethodName);
//		}
	}
	public boolean containsSubscription(Object subscriber, String handlerMethodName)
	{
		
		//return findSubscription(subscriber, handlerMethodName) != null;
		return false;
	}
	public int getSubscriptionCount()
	{
			
			int count = 0;
			count = this.subscriptions.size();
			return count;	
	}

	private IHICData callSubscriptionHandlers(Object sender, 
											Object[] parametrs, 
											EventTopicFireHandler[] handlers)
	{
		
		for (EventTopicFireHandler handler : handlers)
    	{
    	}
		//TODO handler list size must be one as per design. 
		List<Exception> exceptions = new ArrayList<Exception>();
		//No check for exceptions in prototype yet
		try
		{
		//TODO As per assumption there will be only one subscriber for a kind of functionality 
		//	so removing for loop to get the return of HICData.
    	//	for (EventTopicFireHandler handler : handlers)
    		//{
			if(handlers!=null)
			{
    			IHICData obj = (IHICData)handlers[0].callOnPublisher(sender, parametrs);
    			return obj;
    		}
		}
		catch(ClassCastException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	void fire(Publication publication, Object sender, 
				Object[] e, PublicationScope slope)
	{	
		callSubscriptionHandlers(sender, e, getAllHandlers());
	}
	private Publication findPublication(Object publisher, String eventName)
	{
		  Publication publication = null;
		  Iterator iterator = publications.iterator(); 
		  while (iterator.hasNext ()) 
		  {
			  publication = (Publication)iterator.next (); 
			  if(publication.getEventName()!=null)
			  {
				  if(publication.getEventName().trim().equals(eventName))
				  {
					  return publication;
				  }
			  }
		  }
		  return publication;
	} 
}

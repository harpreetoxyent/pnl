package com.oxymedical.hic.application.eventmanagement;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public class Subscriptions 
{
	private WeakReference wrSubscriber;
	private String handlerMethodName;
	private Method methodHandle;
	private EventTopicFireHandler eventTopicHandler;
	
	public Subscriptions(Object subscriber, 
						String handlerMethodName,
						Class[] parameterTypes)
	{
		this.wrSubscriber = new WeakReference(subscriber);
		try
		{
			this.handlerMethodName = handlerMethodName;
			Method mHandler = getMethodInfo(subscriber,handlerMethodName,parameterTypes);
			this.methodHandle= mHandler;
			eventTopicHandler = new EventTopicFireHandler();
			eventTopicHandler.setMethodInfo(mHandler);
			//eventTopicHandler.setParameters(parameters);
			eventTopicHandler.setSubscriber(subscriber);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	public Method getMethodInfo(Object subscriber, String handlerMethodName,Class[] parameterTypes) throws NoSuchMethodException 
	{
		return subscriber.getClass().getMethod(handlerMethodName,parameterTypes );
	}
	public EventTopicFireHandler getEventTopicHandler() 
	{
		return eventTopicHandler;
	}
	public void setEventTopicHandler(EventTopicFireHandler eventTopicHandler) 
	{
		this.eventTopicHandler = eventTopicHandler;
	}	
}

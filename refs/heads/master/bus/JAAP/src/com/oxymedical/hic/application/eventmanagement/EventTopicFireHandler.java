package com.oxymedical.hic.application.eventmanagement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventTopicFireHandler 
{
	private Object subscriber;
	private Method methodInfo;
	private Object[] parameters;
	public EventTopicFireHandler()
	{
		
	}
	public EventTopicFireHandler(Object subscriber,
								 Method method,
								 Object[] parameters)
	{
		this.subscriber = subscriber;
		this.methodInfo = method;
		this.parameters = parameters;
	}
	
	Object callOnPublisher(Object sender,Object[] parametrs)
	{
		if (this.methodInfo != null)
		{
			try
			{
				Object obj = methodInfo.invoke(this.subscriber, parametrs);
				return obj;
			}
			catch (IllegalAccessException e) 
			{
		        e.printStackTrace();
		    } 
			catch (InvocationTargetException e) 
			{
		          e.printStackTrace();
		    }
		}
		return null;
	}

	public Method getMethodInfo() 
	{
		return methodInfo;
	}

	public void setMethodInfo(Method methodInfo) 
	{
		this.methodInfo = methodInfo;
	}

	public Object[] getParameters() 
	{
		return parameters;
	}

	public void setParameters(Object[] parameters) 
	{
		this.parameters = parameters;
	}

	public Object getSubscriber() 
	{
		return subscriber;
	}

	public void setSubscriber(Object subscriber) 
	{
		this.subscriber = subscriber;
	}
}

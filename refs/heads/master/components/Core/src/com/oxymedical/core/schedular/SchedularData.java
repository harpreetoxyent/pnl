package com.oxymedical.core.schedular;

public class SchedularData implements ISchedularData
{
	private long interval;
	private String subscribedMethod;
	
	public long getInterval() 
	{
		return interval;
	}
	public void setInterval(long interval) 
	{
		this.interval = interval;
	}
	
	public String getSubscribedMethod() 
	{
		return subscribedMethod;
	}
	public void setSubscribedMethod(String subscribedMethod) 
	{
		this.subscribedMethod = subscribedMethod;
	}
	
}

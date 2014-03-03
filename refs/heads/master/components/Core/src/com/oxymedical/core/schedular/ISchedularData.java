package com.oxymedical.core.schedular;

public interface ISchedularData 
{
	public long getInterval();
	public void setInterval(long interval);
	
	public String getSubscribedMethod();
	public void setSubscribedMethod(String subscribedMethod);

}

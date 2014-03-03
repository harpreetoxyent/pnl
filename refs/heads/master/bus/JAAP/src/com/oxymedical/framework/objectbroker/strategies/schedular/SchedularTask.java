package com.oxymedical.framework.objectbroker.strategies.schedular;

import java.util.TimerTask;

import com.oxymedical.hic.application.JAAPKernel;
import com.oxymedical.hic.application.JAAPRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

/**
 * Class that handles all Timer Tasks mentioned in Module.xml
 * 
 * @author hs
 *
 */
public class SchedularTask extends TimerTask 
{
	String methodName;

	public SchedularTask(String methodName) 
	{
		super();
		this.methodName = methodName;
	}

	public void setMethodName(String methodName) 
	{
		this.methodName = methodName;
	}

	@Override
	public void run() 
	{
		try 
		{
			JAAPRuntime.FireEvent(JAAPKernel.getKernelContext(), this.methodName, new Object[] { null } , PublicationScope.Global);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}
}

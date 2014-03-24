package com.oxymedical.hic.application;

import java.util.Hashtable;

import org.dom4j.Document;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;
import com.oxymedical.hic.servlet.NOLISStartupUtility;

public class NOLISRuntime extends JAAPRuntime
{ 
	private static Hashtable<String, org.dom4j.Document> configurationData = null;
	private static JAAPKernel kernel = null;
	private static int requestNumber = 0;
	private static Boolean hasStarted = false;

	public static Boolean startApplication()
	{
	//	NOLISStartupUtility startupUtility = new NOLISStartupUtility();
		configurationData = NOLISStartupUtility.ConfigData;
		JAAPRuntime.configurationData = configurationData;
		// Initialize kernel
		requestNumber++;
		JAAPRuntime.requestNumber = requestNumber;
		kernel = JAAPKernel.getInstance();
		hasStarted = true;
		JAAPRuntime.hasStarted = hasStarted;
		// KernelContext kernelContext = ArgusKernel.GetKernelContext();
		// logger.log(0,"configurationData = " + configurationData);
		if (configurationData != null)
		{
			Document xmlDocument = (Document) configurationData.get("Module.xml");
			// logger.log(0,"!!!!!!!!!!!!!!!!! Moduile xml = " +
			// xmlDocument.getText());
			kernel.getRegistry().LoadXML(xmlDocument);
		}
		// addComponents();
		return new Boolean(hasStarted);
	}

	public Boolean stopApplication()
	{
		// Initialize kernel
		requestNumber++;
		JAAPRuntime.requestNumber = requestNumber;
		kernel = null;
		hasStarted = false;
		JAAPRuntime.hasStarted = hasStarted;
		return new Boolean(hasStarted);
	}

	public static IHICData FireEvent(String topicName, Object[] parameters,
			PublicationScope scope) throws Exception
	{
		JAAPRuntime.getKernel();
		System.out.println("NOLISRuntime.FireEvent: " + topicName);
		IHICData returnHIC = JAAPRuntime.FireEvent(JAAPKernel.getKernelContext(), topicName, parameters, scope);
		//logger.log(0,"----in sdie jaap FireEvent ============="+returnHIC);
		return returnHIC;
	}

	public static Object getComponent(String moduleId)
	{
		Object obj = null;
		try
		{
			if (hasStarted)
			{
				obj = JAAPRuntime.getComponent(moduleId);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	protected void finalize() throws Throwable
	{
		try
		{
			startApplication(); // close open files

		}
		finally
		{
			super.finalize();
		}
	}

}

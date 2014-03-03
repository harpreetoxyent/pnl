package com.oxymedical.hic.request;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.wsprovider.IWSProviderComponent;
import com.oxymedical.component.wsprovider.WSProviderComponent;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class WebServiceRequest implements Runnable
{
//	IWSProviderComponent wsProvider;
////	IApplication app;
//	
//	public WebServiceRequest(IWSProviderComponent wsp)
//	{
//		this.wsProvider = wsp;
////		this.app = app;
//	}

	@Override
	public void run() 
	{
//		System.out.println("=======================inside run of we service thread==============WebServiceRequest = wsProvider = "+wsProvider);
		try
		{
//			System.out.println("class name of wsProvider.getClass().getName()) ============== "+wsProvider.getClass().getName());
//			if ("com.oxymedical.component.wsprovider.WSProviderComponent".equals(wsProvider.getClass().getName()))
//			{
//				System.out.println("=======================inside if of run wsProvider = "+wsProvider.getClass());
//				wsProvider.getClass().getMethod("setApplicaion", new Class[] { IApplication.class } ).invoke(wsProvider, new Object [] { this.app } );
//				wsProvider.getClass().getMethod("generateWebService", new Class[] { null } ).invoke(wsProvider, new Object [] { null });
			NOLISRuntime.FireEvent("generateWebService", new Object[]{null}, PublicationScope.Global);
//				wsProvider.generateWebService();
				System.out.println("=====================after call of method using reflection==inside if of run");
//			}
			// wsProvider.generateWebService();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

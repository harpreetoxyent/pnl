package com.oxymedical.hic.application.strategies.proxypattern;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolutionLocatorKey;
import com.oxymedical.framework.objectbroker.Location.IReadWriteLocator;


/**
 * @author Oxyent Medical Pvt Ltd, India
 * 
 * No part of this Source may be copied
 * without Oxyent Medical prior written permission.
 * Copyright 2007 Oxyent Medical Pvt Ltd, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class ProxyPatternStrategy extends BuilderStrategy 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	public ProxyPatternStrategy()
	{
		cat.info("ProxyPatternStrategy Constructor Called");
	}
	@Override 
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		Object proxyObject = null;
		List interfaceList=new ArrayList();
		try 
		{
			/*for(int i=0;i<existing.getClass().getInterfaces().length;i++)
			{	
				Class impClass = existing.getClass().getInterfaces()[i];
				impClass.getSuperclass()
			}*/
			int interfaceCount = existing.getClass().getInterfaces().length;
			cat.info("ProxyPatternStrategy BuildUp Called");
			proxyObject = existing;
			IProxyPatternPolicy proxyPolicy = context.getPolicies().get(ProxyPatternPolicy.class, typeToBuild, idToBuild);
			cat.info("Check if object has ComponentConfiguration Policy");
			if(existing instanceof IComponent)
			{	
				IComponent componentInstance =  (IComponent)existing;	
				cat.info("Type to build = "+typeToBuild.getName());
				cat.info("idToBuild to build = "+idToBuild);
				
				DependencyResolutionLocatorKey key = new DependencyResolutionLocatorKey(typeToBuild, idToBuild);
				IReadWriteLocator locator = context.getLocator();
				cat.info("Look into context locator for requested object");
				if (existing != null && proxyPolicy != null)
				{
					if(proxyPolicy.isProxy())
					{
						cat.info("created");
						cat.info(existing.getClass().getName());
						cat.info(existing.getClass().getInterfaces().length);
			          	proxyObject = java.lang.reflect.Proxy.newProxyInstance(
			          			existing.getClass().getClassLoader(),
			          			existing.getClass().getInterfaces(),
			          	    new ProxyInvocationHandler(existing));
					}
				}
				cat.info("Instead of real object proxy Object passed to Parent build Up");
			}
		}				
		catch (Exception e) 
		{
			//Not a component so no need of Proxy
			proxyObject = existing;
		}
		cat.info("Not singleton call next strategy"+typeToBuild);
		return super.BuildUp(context, typeToBuild, proxyObject, idToBuild);
	}

}

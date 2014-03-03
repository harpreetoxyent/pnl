package com.oxymedical.framework.objectbroker.strategies.singleton;

import org.apache.log4j.Category;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolutionLocatorKey;
import com.oxymedical.framework.objectbroker.Location.IReadWriteLocator;
import com.oxymedical.hic.application.strategies.proxypattern.ProxyInvocationHandler;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class SingletonStrategy extends BuilderStrategy 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	public SingletonStrategy()
	{
		//System.out.println("Singleton Constructor Called");
	}
	
	//Runtime.getRuntime().exec ("cmd /c start Help.html");
	

	@Override 
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("Singleton BuildUp Called");
		cat.info("Type to build = "+typeToBuild.getName());
		cat.info("idToBuild to build = "+idToBuild);
		DependencyResolutionLocatorKey key = new DependencyResolutionLocatorKey(typeToBuild, idToBuild);
		IReadWriteLocator locator = context.getLocator();
		cat.info("Look into context locator for requested object");
		if (locator != null && locator.contains(key))
		{
			Object proxyObject = null;
			cat.info("Inside Singleton Strategy-value of object key is : "+key);
			cat.info("Object= : "+context.getLocator().Get(key));
			Object containedObject = context.getLocator().Get(key);
			if(containedObject instanceof IComponent)
			{
	          	proxyObject = java.lang.reflect.Proxy.newProxyInstance(
	          			containedObject.getClass().getClassLoader(),
	          			containedObject.getClass().getInterfaces(),
	          	    new ProxyInvocationHandler(containedObject));	
			}
			return proxyObject;
		}
		cat.info("Control passed to Parent build Up");
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}

}

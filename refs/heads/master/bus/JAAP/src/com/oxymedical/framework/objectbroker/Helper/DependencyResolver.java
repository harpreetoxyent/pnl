package com.oxymedical.framework.objectbroker.Helper;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.framework.objectbroker.strategies.singleton.SingletonPolicy;
import com.oxymedical.hic.application.strategies.proxypattern.ProxyPatternPolicy;


/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class DependencyResolver 
{
	IBuilderContext context;
	public DependencyResolver(IBuilderContext context)
	{
		if (context == null)
		{
			//raise exception
		}
		this.context = context;
	}	
	/// Resolves a dependency.
	/// @typeToInject The type to be injected
	/// @id The ID of the object to be resolved. Pass null for the unnamed object
	/// returnsThe dependent object. 

	public Object Resolve(Class typeToInject, String id)
	{
		if (typeToInject == null)
		{
				//raise exception
		}
		DependencyResolutionLocatorKey key = new DependencyResolutionLocatorKey(typeToInject, id);

		if (context.getLocator().contains(key))
		{
			//For InjectProxy to work the component should always exist whose proxy is requested
			return context.getLocator().Get(key);
		}
		else
		{	
			context.getPolicies().set(new ProxyPatternPolicy(false),typeToInject, id);
			context.getPolicies().set(new SingletonPolicy(true),typeToInject, id);
			return context.getHeadOfChain().BuildUp(context, typeToInject, null, key.getID());
		}
	}
	
}

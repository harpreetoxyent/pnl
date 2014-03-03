package com.oxymedical.framework.objectbroker.strategies.method;

import java.lang.reflect.*;

import org.apache.log4j.Category;

import com.oxymedical.framework.objectbroker.*;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class MethodExecutionStrategy extends BuilderStrategy 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	public MethodExecutionStrategy()
	{
		cat.info("MethodExecutionStrategy Constructor Called");
	}

	@Override
	public Object BuildUp(IBuilderContext context, Class typeToBuild, 
							Object existing, String idToBuild)
	{
		ApplyPolicy(context, existing, idToBuild);
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}
	private void ApplyPolicy(IBuilderContext context, Object existing, 
							 String idToBuild)
	{
		if (existing == null)
			return;
		IMethodPolicy methodPolicy = context.getPolicies().get(MethodPolicy.class,
				existing.getClass(), idToBuild);
		if (methodPolicy == null)
			return;
		try
		{
			for (IMethodCallInfo methodCallInfo : methodPolicy.getMethods().values())
			{
				Method methodInfo = methodCallInfo.selectMethod(context, existing.getClass(), idToBuild);
				if (methodInfo != null)
				{
					Object[] parameters = methodCallInfo.getParameters(context, existing.getClass(), idToBuild,null);
					methodInfo.invoke(existing, parameters);
				}
	
			}
		}
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
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
}

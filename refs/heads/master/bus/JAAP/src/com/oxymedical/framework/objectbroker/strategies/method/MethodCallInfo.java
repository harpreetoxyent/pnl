package com.oxymedical.framework.objectbroker.strategies.method;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

import com.oxymedical.framework.objectbroker.IBuilderContext;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class MethodCallInfo implements IMethodCallInfo 
{
	private Method method;
	private String methodName;
	private List<Object> parameters;

	public MethodCallInfo(Method method)	
	{
		this(null, method, null);
	}
	public MethodCallInfo(String methodName)
	{
		this(methodName, (Method)null, null);
	}

	public MethodCallInfo(String methodName, Object[] parameters)
	{
		this(methodName, null, parameters);		
	}

	private MethodCallInfo(String methodName, Method method, Object[] parameters)
	{
		this.methodName = methodName;
		this.method = method;
		this.parameters = new ArrayList<Object>();
		if (parameters != null)
		{
			for(Object param : parameters)
			{
				this.parameters.add(param);
			}
		}
	}
	
	public Object[] getParameters(IBuilderContext context, Class type,
									String id, Method method) 
	{
		return this.parameters.toArray();
	}

	public Method selectMethod(IBuilderContext context, Class type, String id) throws NoSuchMethodException 
	{
		// TODO Auto-generated method stub
		if (method != null)
			return method;
		List<Class> types = null;
		if(parameters!=null && parameters.size() > 0)
		{
			types = new ArrayList<Class>();
			for(Object param : parameters)
			{
				types.add((Class)param.getClass());
			}
		}
		Class[] methodClassArray = null;
		if(types != null)
		{
			methodClassArray = (Class[])types.toArray();
		}
		return type.getMethod(methodName,methodClassArray );
	}

}

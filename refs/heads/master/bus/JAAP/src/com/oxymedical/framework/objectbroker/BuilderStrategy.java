package com.oxymedical.framework.objectbroker;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

import java.lang.reflect.Type;

import org.apache.log4j.Category;

import com.oxymedical.framework.objectbroker.strategies.IBuilderTracePolicy;



@SuppressWarnings("deprecation")
public class BuilderStrategy implements IBuilderStrategy 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	
	@SuppressWarnings("unchecked")
	public <TItem> TItem BuildUp(IBuilderContext context, TItem existing, String idToBuild)
	{
		cat.info("Generic Build Up Called");
		return (TItem)BuildUp(context, existing.getClass(), existing, idToBuild);
	}
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("Builder - Object Build Up Called");
		IBuilderStrategy next = context.getNextInChain(this);
		if (next != null)
		{
			cat.info("Next Strategy in line ="+next.toString());
			return next.BuildUp(context, typeToBuild, existing, idToBuild);
		}
		else
		{
			cat.info("No more strategy left so object returned typeToBuild"+typeToBuild + " existing"+existing);
			return existing;
		}
	}
	public Object TearDown(IBuilderContext context, Object item)
	{
		IBuilderStrategy next = context.getNextInChain(this);

		if (next != null)
			return next.TearDown(context, item);
		else
			return item;
	}
	
	//Creates a trace list of parameter types from a list of Objects.
	protected String ParametersToTypeList(Object[] parameters)
	{
		String returnValue = "";
		int counter = 0;
		for (Object parameter : parameters)
		{
			counter++;
			if(counter < parameters.length)
			returnValue = (String)parameter.getClass().getName() + ", ";
		}
		return returnValue;
	}
	
	protected void TraceBuildUp(IBuilderContext context, Type typeToBuild, String idToBuild, String format, Object[] args)
	{
		//Not implemented in Prototype
	}
	protected void TraceTearDown(IBuilderContext context, Object item, String format, Object[] args)
	{
		//Not implemented in Prototype
	}
	protected boolean TraceEnabled(IBuilderContext context)
	{
		return (context.getPolicies().get(IBuilderTracePolicy.class,null, null) != null);
		
	}
	

}

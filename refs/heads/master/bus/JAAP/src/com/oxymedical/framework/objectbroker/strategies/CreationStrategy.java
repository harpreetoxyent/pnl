package com.oxymedical.framework.objectbroker.strategies;


import org.apache.log4j.Category;

import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolutionLocatorKey;
import com.oxymedical.framework.objectbroker.Lifetime.ILifetimeContainer;
import com.oxymedical.framework.objectbroker.Lifetime.LifetimeContainer;
import com.oxymedical.framework.objectbroker.Location.IReadWriteLocator;
import com.oxymedical.framework.objectbroker.strategies.singleton.ISingletonPolicy;
import com.oxymedical.framework.objectbroker.strategies.singleton.SingletonPolicy;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class CreationStrategy extends BuilderStrategy 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	public CreationStrategy()
	{
		cat.info("Creation Constructor Called");
	}
	@Override
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("CreationStrategy Buildup called");		
		if (existing != null)
			buildUpExistingObject(context, typeToBuild, existing, idToBuild);
		else
			existing = buildUpNewObject(context, typeToBuild, existing, idToBuild);
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}
	private void buildUpExistingObject(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("Build Up Existing Called");
		registerObject(context, typeToBuild, existing, idToBuild);
	}
	private Object buildUpNewObject(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{	
		  cat.info("Build Up New Called");
		  //prototype supports blank constructor now
		  try 
	      {
			  
			  String qualifiedClassName = typeToBuild.getName();
	          Class classDefinition = Class.forName(qualifiedClassName);
	          existing = classDefinition.newInstance();
	          cat.info("New Instance Created Using blank Constructor");	          
	      } 
	      catch (InstantiationException e) 
	      {
	          e.printStackTrace();
	      } 
	      catch (IllegalAccessException e) 
	      {
	          e.printStackTrace();
	      } 
	      catch (ClassNotFoundException e) 
	      {
	          e.printStackTrace();
	      }
	      //register the new object in Argus system
	      registerObject(context, typeToBuild, existing, idToBuild);
	      return existing;
	}
	private void registerObject(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("Register New Object");
		if (context.getLocator() != null)
		{
			IReadWriteLocator loc = context.getLocator();
			ILifetimeContainer lifetime = loc.Get(LifetimeContainer.class);
			cat.info("Add object to lifetime which will make it strongly referenced");
			if (lifetime != null)
			{
				ISingletonPolicy singletonPolicy = context.getPolicies().get(SingletonPolicy.class, typeToBuild, idToBuild);
				cat.info("Check if object has Singleton Policy");
				if (singletonPolicy != null && singletonPolicy.isSingleton())
				{			
					//Protype: Everything is Singleton
					//Create a key for new object
					//add the new object into locator
					cat.info("New Object has Singleton Policy associated with it");
					cat.info("typeToBuild = "+typeToBuild);
					cat.info("idToBuild = "+idToBuild);
					//IComponent component = (IComponent) existing;
					
					context.getLocator().add(new DependencyResolutionLocatorKey(typeToBuild, idToBuild), existing);				
					lifetime.add(existing);
				}
			}
		}
	}
	
}

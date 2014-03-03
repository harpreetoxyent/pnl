package com.oxymedical.framework.objectbroker;
import java.lang.reflect.Type;
import org.apache.log4j.*;

import com.oxymedical.framework.objectbroker.Helper.*;
import com.oxymedical.framework.objectbroker.Location.*;
import com.oxymedical.framework.objectbroker.exception.ObjectBrokerException;




/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class BuilderBase implements IBuilder
{
	static Category cat = Category.getInstance("ObjectBuilder");
	private PolicyList policies = new PolicyList();
	private StrategyList strategies = new StrategyList();
	public BuilderBase()
	{
	}
	public BuilderBase(IBuilderConfigurator configurator)
	{
		//	Build Stages are fixed for prototype and will be changed in future
	}
	public PolicyList getPolicies()
	{
		return policies;
	}
	public StrategyList getStrategies()
	{
		return strategies;
	}
/*	@SuppressWarnings("unchecked")
	public <TTypeToBuild> TTypeToBuild BuildUp(TTypeToBuild t,
			 							IReadWriteLocator locator, 
			 							String idToBuild, 
			 							Object existing,
			 							PolicyList[] transientPolicies) throws ObjectBrokerException
	 {
         cat.info("Generic Build Up Called");
		 return (TTypeToBuild)BuildUp(t.getClass(),locator,  idToBuild, existing, transientPolicies);
	 }*/
	 
	 //needs to be virtual
	 public Object BuildUp(	Class typeToBuild,
			 				IReadWriteLocator locator,
			 				String idToBuild, 
			 				Object existing,
			 				PolicyList[] transientPolicies) throws ObjectBrokerException 
	{
		 try
		 {
	         cat.info("Object Build Up Called");			 
			 Object obj =  DoBuildUp(locator,
				 			typeToBuild,
				 			idToBuild,
				 			existing,transientPolicies);
			 cat.info("DoBuild Up Ended Succesfully" + obj);
			 return obj;
		 }
		 catch(Exception exp)
		 {
			 exp.printStackTrace();
			 throw new ObjectBrokerException("Builder Base Buildup Crashed");
		 }
	}
	 
	 private Object DoBuildUp(IReadWriteLocator locator,
			 				  Class typeToBuild, 
			 				  String idToBuild, 
			 				  Object existing,
			 				  PolicyList[] transientPolicies) throws ObjectBrokerException
	{
		//IBuilderStrategyChain chain = strategies.MakeStrategyChain();

         cat.info("DoBuild Up Called");
         cat.info("DoBuild Arguments: Locator ="+ locator+
        		 " typeToBuild = "+typeToBuild+" idToBuild ="+idToBuild
        		 +" existing = "+existing);
		 IBuilderStrategyChain chain = strategies.makeStrategyChain();
		 //Need to think while testing
         cat.info("Strategy Chain build. Head strategies ="+chain.getHead().toString());
		 throwIfNoStrategiesInChain(chain);
		 IBuilderContext context = makeContext(chain, locator, transientPolicies);
		 IBuilderStrategy headStrategy = chain.getHead();
		 
		 Object result = headStrategy.BuildUp(context, typeToBuild, existing, idToBuild);
		 cat.info("Head Strategy Build Up succesful");
		 return result;
	}
	private IBuilderContext makeContext(IBuilderStrategyChain chain,
				IReadWriteLocator locator, PolicyList[] transientPolicies)
	{
		PolicyList policies = new PolicyList(this.policies);
		if(transientPolicies != null)
		{
			for(PolicyList policyList : transientPolicies)
				policies.AddPolicies(policyList);		
		}
		return new BuilderContext(chain, locator, policies);
	}	 
	
	private static void throwIfNoStrategiesInChain(IBuilderStrategyChain chain) throws ObjectBrokerException
	{
		if (chain.getHead() == null)
		{
			throw new ObjectBrokerException("No Builder Strategies");
		}
	}	
	/* Not Implemented in Prototype
	@SuppressWarnings("unchecked")
	private <TItem> TItem DoTearDown(IReadWriteLocator locator, TItem item) throws Exception
	{
		IBuilderStrategyChain chain = strategies.makeReverseStrategyChain();
		try
		{
			throwIfNoStrategiesInChain(chain);
		}
		catch(Exception e)
		{
			//Exception Happened in tear down
			throw new Exception("Exception happened in tear down",e);
		}
		IBuilderContext context = makeContext(chain, locator,null);
		TItem result = (TItem)chain.getHead().TearDown(context, item);
		return result;
	}
*/
	
	 <TItem> TItem TearDown(IReadWriteLocator locator, TItem item) throws ObjectBrokerException
	 {
		if (item == null)
			throw new ObjectBrokerException("item");
		 
		 return null;
	 }
	 public Object TearDown(IReadWriteLocator locator, Type item) throws ObjectBrokerException
	 {
			if (item == null)
				throw new ObjectBrokerException("item");	 
			 return null;
	 }
}

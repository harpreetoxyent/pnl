package com.oxymedical.framework.objectbroker;

import com.oxymedical.framework.objectbroker.Helper.*;
import com.oxymedical.framework.objectbroker.Location.*;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class BuilderContext implements IBuilderContext 
{
	private IBuilderStrategyChain chain;
	private IReadWriteLocator locator;
	private PolicyList policies;
	protected BuilderContext()
	{
	}
	public BuilderContext(IBuilderStrategyChain chain, IReadWriteLocator locator, PolicyList policies)
	{
		this.chain = chain;
		this.locator = locator;
		this.policies = new PolicyList(policies);
	}
	public IBuilderStrategy getHeadOfChain()
	{
		return chain.getHead();
	}
	public IReadWriteLocator getLocator()
	{
		return locator;
	}	
	protected void setLocator(IReadWriteLocator locator)
	{
		this.locator = locator;
	}	
	public PolicyList getPolicies()
	{
		return policies;
	}	
	protected void setPolicies(PolicyList policies)
	{
		this.policies = policies;
	}	
	protected IBuilderStrategyChain getStrategyChain()
	{
		return chain;
	}	
	protected void setStrategyChain(IBuilderStrategyChain chain)
	{
		this.chain = chain;
	}
	public IBuilderStrategy getNextInChain(IBuilderStrategy currentStrategy)
	{
		return chain.GetNext(currentStrategy);
	}	
}

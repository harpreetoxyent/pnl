package com.oxymedical.hic.application.strategies.proxypattern;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class ProxyPatternPolicy implements IProxyPatternPolicy
{
	private boolean isProxy;
	public ProxyPatternPolicy(boolean isProxy)
	{
		this.isProxy = isProxy;
	}
	public boolean isProxy()
	{
		return isProxy;
	}
}

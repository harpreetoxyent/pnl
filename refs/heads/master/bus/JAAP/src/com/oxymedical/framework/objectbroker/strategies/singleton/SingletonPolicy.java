package com.oxymedical.framework.objectbroker.strategies.singleton;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medicalís prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class SingletonPolicy implements ISingletonPolicy
{
	private boolean isSingleton;
	public SingletonPolicy(boolean isSingleton)
	{
		this.isSingleton = isSingleton;
	}
	public boolean isSingleton()
	{
		return isSingleton;
	}
}

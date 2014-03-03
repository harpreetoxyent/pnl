package com.oxymedical.framework.objectbroker.strategies.method;

import java.util.Hashtable;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class MethodPolicy implements IMethodPolicy 
{
	private Hashtable<String, IMethodCallInfo> methods = 
				new Hashtable<String, IMethodCallInfo>();
	public Hashtable<String, IMethodCallInfo> getMethods() 
	{
		// TODO Auto-generated method stub
		return methods;
	}

}

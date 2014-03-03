package com.oxymedical.framework.objectbroker.strategies.method;

import java.util.Hashtable;

import com.oxymedical.framework.objectbroker.IBuilderPolicy;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IMethodPolicy extends IBuilderPolicy 
{
	Hashtable<String, IMethodCallInfo> getMethods();
}

package com.oxymedical.framework.objectbroker.strategies.method;

import java.lang.reflect.Method;

import com.oxymedical.framework.objectbroker.*;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IMethodCallInfo 
{
	Object[] getParameters(IBuilderContext context, Class type, String id, Method method);
	Method selectMethod(IBuilderContext context, Class type, String id) throws NoSuchMethodException;
}

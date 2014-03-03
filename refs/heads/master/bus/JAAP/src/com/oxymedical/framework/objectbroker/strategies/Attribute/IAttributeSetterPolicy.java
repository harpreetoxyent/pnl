package com.oxymedical.framework.objectbroker.strategies.Attribute;
import java.lang.reflect.Field;
import java.util.Hashtable;

import com.oxymedical.framework.objectbroker.IBuilderPolicy;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IAttributeSetterPolicy extends IBuilderPolicy 
{
	public Hashtable<String,Field> attributes = null;
	public Hashtable<String,Field> getAttribute();
	public void setAttribute(String keyNames);
}

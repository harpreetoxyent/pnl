package com.oxymedical.framework.objectbroker.strategies.Attribute;

import java.util.Hashtable;
import java.lang.reflect.Field;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class AttributeSetterPolicy implements IAttributeSetterPolicy
{

	private Hashtable<String,Field> attributes = new Hashtable<String,Field>();
	public Hashtable<String,Field> getAttribute() 
	{
		return attributes;
	}

	public void setAttribute(String keyNames) 
	{
		// TODO Auto-generated method stub
		
	}

}

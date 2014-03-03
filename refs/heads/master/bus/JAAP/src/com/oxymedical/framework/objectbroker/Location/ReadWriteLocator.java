package com.oxymedical.framework.objectbroker.Location;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public abstract class ReadWriteLocator extends ReadableLocator implements
		IReadWriteLocator
{
	public boolean isReadOnly()
	{
		return false;
	}
	public abstract void add(Object key, Object value);
	public abstract boolean remove(Object key);
	
}

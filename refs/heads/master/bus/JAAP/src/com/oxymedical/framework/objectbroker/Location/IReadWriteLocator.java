package com.oxymedical.framework.objectbroker.Location;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IReadWriteLocator extends IReadableLocator
{
	void add(Object key, Object value);
	boolean remove(Object key);
	public Iterator getIterator();
	public Enumeration<Object> keys();
}

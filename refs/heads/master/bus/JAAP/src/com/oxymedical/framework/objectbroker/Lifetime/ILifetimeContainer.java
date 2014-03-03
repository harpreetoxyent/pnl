package com.oxymedical.framework.objectbroker.Lifetime;
import java.util.Iterator;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface ILifetimeContainer extends Iterator
{
	int getCount();
	void add(Object item);
	boolean contains(Object item);
	void remove(Object item);
}

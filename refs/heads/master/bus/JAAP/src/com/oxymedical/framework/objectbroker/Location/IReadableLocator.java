package com.oxymedical.framework.objectbroker.Location;
import java.util.Iterator;

import com.oxymedical.framework.objectbroker.Helper.*;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IReadableLocator extends Iterator
{
	int getCount();
	IReadableLocator getParentLocator();
	boolean isReadOnly();
	boolean contains(Object key);
	boolean contains(Object key, SearchMode options);
	//	Gets an object from the locator, registered with the key of typeof(T).
	<TItem> TItem Get(Object key);
	<TItem> TItem Get(Object key, SearchMode options);

}

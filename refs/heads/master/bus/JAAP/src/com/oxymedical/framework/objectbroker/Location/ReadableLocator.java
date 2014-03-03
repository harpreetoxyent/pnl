package com.oxymedical.framework.objectbroker.Location;

import com.oxymedical.framework.objectbroker.Helper.SearchMode;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public abstract class ReadableLocator implements IReadableLocator 
{
	private IReadableLocator parentLocator;
	public abstract int getCount();
	public IReadableLocator getParentLocator()
	{
		return parentLocator; 
	}	
	public abstract boolean isReadOnly();
	public boolean contains(Object key)
	{
		return contains(key, SearchMode.Up);
	}
	public abstract boolean contains(Object key, SearchMode options);
	@SuppressWarnings("unchecked")
	public abstract Object Get(Object key, SearchMode options);
	@SuppressWarnings("unchecked")
	public Object Get(Object key)
	{
		return Get(key,SearchMode.Up);
	}


	
    protected void setParentLocator(IReadableLocator parentLocator)
    {
        this.parentLocator = parentLocator;
    }

}

package com.oxymedical.framework.objectbroker.Location;
import com.oxymedical.framework.objectbroker.Helper.*;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class ReadOnlyLocator 
		extends ReadableLocator
{
	private IReadableLocator innerLocator;
	public ReadOnlyLocator(IReadableLocator innerLocator)
	{
		this.innerLocator = innerLocator;
	}	
	public int getCount()
	{
		return this.innerLocator.getCount();
	}
	
	public IReadableLocator getParentLocator()
	{
		return new ReadOnlyLocator(innerLocator.getParentLocator()); 
	}	
	
	public boolean isReadOnly()
	{
		return true;
	}
	public boolean contains(Object key)
	{
		return contains(key, SearchMode.Up);
	}
	public boolean contains(Object key, SearchMode options)
	{
		return innerLocator.contains(key, options);
	}
	public Object Get(Object key, SearchMode options)
	{
		return innerLocator.Get(key, options);
	}	
	@SuppressWarnings("unchecked")
	public <TItem> TItem get(Object key, SearchMode options)
	{
		return (TItem)Get(key, options);
	}
	public boolean hasNext()
	{
		return innerLocator.hasNext();
	}
	public void remove()
	{
		innerLocator.remove();
	}
	public Object next()
	{
		return innerLocator.next();
	}	
	
}

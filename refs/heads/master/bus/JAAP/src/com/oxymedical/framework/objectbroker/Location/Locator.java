package com.oxymedical.framework.objectbroker.Location;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import com.oxymedical.framework.objectbroker.Helper.SearchMode;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class Locator extends ReadWriteLocator 
{
	private Hashtable<Object, Object> references = new Hashtable<Object, Object>();	
	public Locator()
	{
	}
	public Locator(IReadableLocator parentLocator)
	{
		setParentLocator(parentLocator);
	}
	public int getCount()
	{
		return references.size(); 
	}
	public void add(Object key, Object value)
	{
		references.put(key, value);
	}
	
	public boolean contains(Object key, SearchMode options)
	{
		if (key == null)
			return false;
		if (references.containsKey(key))
			return true;
		if (options == SearchMode.Up && this.getParentLocator() != null)
			return this.getParentLocator().contains(key, options);
		return false;
	}
	public Object Get(Object key)
	{
		return references.get(key);
	}
	
	public Enumeration<Object> keys()
	{
		return references.keys();
	}
	public Object Get(Object key, SearchMode options)
	{
		if (references.containsKey(key))
			return references.get(key);

		if (options == SearchMode.Up && this.getParentLocator() != null)
			return this.getParentLocator().Get(key, options);

		return null;
	}
	
	public boolean remove(Object key)
	{
		return (references.remove(key)!=null);
	}
	public boolean hasNext()
	{
		//HS: Need to fix has next as it does not increment for calling 
		// Object. Goes in Endless loop
		return references.values().iterator().hasNext();
	}
	public void remove()
	{
		references.values().iterator().remove();
	}
	public Object next()
	{
		return references.values().iterator().next();
	}	
	
	public Iterator getIterator()
	{
		return references.values().iterator();
	}	


}

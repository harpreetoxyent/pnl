package com.oxymedical.framework.objectbroker.Lifetime;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class LifetimeContainer implements ILifetimeContainer
{
	public LifetimeContainer()
	{
		
	}
	private List<Object> items = new ArrayList<Object>();
	public int getCount()
	{
		return items.size(); 
	}
	public void add(Object item)
	{
		items.add(item);
	}
	public boolean contains(Object item)
	{
		return items.contains(item);
	}
	public void remove(Object item)
	{
		if (!items.contains(item))
			return;

		items.remove(item);
	}
	public Iterator<Object> GetIterator()
	{
		return items.iterator();
	}
	public boolean hasNext()
	{
		return items.iterator().hasNext();
	}
	public void remove()
	{
		items.iterator().remove();
	}
	public Object next()
	{
		return items.iterator().next();
	}	
	public boolean equals(Object obj)
	{
		LifetimeContainer other = (LifetimeContainer)obj ;
		if (other == null)
			return false;
		//object are same if they are of same class type and
		//have same id
		String className = this.getClass().getName();
		boolean classNameFlg = (className == other.getClass().getName());
		return (classNameFlg);
	}

}

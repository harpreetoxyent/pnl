package com.oxymedical.hic.application.collection;

import java.util.Collection;
import java.util.Iterator;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.framework.objectbroker.IBuilder;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolutionLocatorKey;
import com.oxymedical.framework.objectbroker.Lifetime.ILifetimeContainer;
import com.oxymedical.framework.objectbroker.Location.IReadWriteLocator;
public class ComponentCollection<Key> implements Collection 
{
	IBuilder builder;
	private IReadWriteLocator locator = null;
	private ILifetimeContainer lifetime;
	
	public ComponentCollection(IBuilder builder,
								IReadWriteLocator locator,
								ILifetimeContainer lifetime)
	{
		this.builder = builder;
		this.lifetime = lifetime;
		this.locator = locator;
	}
	public boolean add(Object object)
	{
		addObject((Key)object, "") ;
		return false;
	}
	public boolean addObject(Key object, String componentId) 
	{
		// TODO Auto-generated method stub
		
		if(object == null)
			return false;
		else
		{
			locator.add(new DependencyResolutionLocatorKey(IComponent.class,componentId), object);
			return true;
		}
	}

	public boolean addAll(Collection arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() 
	{
		// TODO Auto-generated method stub

	}

	public boolean contains(Object object) 
	{
		// TODO Auto-generated method stub
		DependencyResolutionLocatorKey key = new DependencyResolutionLocatorKey(object.getClass(), "");
		if (locator != null && locator.contains(key))
		{
			
			return true;
		}
		return false;
	}

	public boolean containsAll(Collection arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator iterator() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean remove(Object arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public int size() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public Object[] toArray() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	public Object get(String componentId)
	{
		DependencyResolutionLocatorKey key = new DependencyResolutionLocatorKey(IComponent.class, componentId);
		if (locator != null && locator.contains(key))
		{	
			return locator.Get(key);
		}	
		return null;
	}
}

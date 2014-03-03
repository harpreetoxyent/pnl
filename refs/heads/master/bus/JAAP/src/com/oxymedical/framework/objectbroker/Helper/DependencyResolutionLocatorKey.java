package com.oxymedical.framework.objectbroker.Helper;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class DependencyResolutionLocatorKey 
{
	private Class type;
	private String id;
	public DependencyResolutionLocatorKey()	
	{
		this(null, null);
	}
	public DependencyResolutionLocatorKey(Class type, String id)
	{
		this.type = type;
		this.id = id;
	}
	public String getID()
	{
		return id; 
	}

	public Class getType()
	{
		return type;
	}
	public boolean equals(Object obj)
	{
		DependencyResolutionLocatorKey other = (DependencyResolutionLocatorKey)obj ;
		if (other == null)
			return false;
		//object are same if they are of same class type and
		//have same id
		Class classA = this.type;
		Class classB = other.type;		
		boolean classNameFlg = (classA.getName() == classB.getName());
		boolean classIdFlg = false;
		if(this.id==null && other.id==null)
			classIdFlg = true;
		else if(this.id=="" && other.id=="")
			classIdFlg = true;
		else if(this.id.trim().equals(other.id.trim()))
			classIdFlg = true;
		else
			classIdFlg = false;
		return (classNameFlg && classIdFlg);
	}

	//Not Implemented in Prototype
	public int hashCode()
	{
		return 0;
	}

}

package com.oxymedical.core.hicUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.core.hicUtil.IExtensionPoint;
import com.oxymedical.core.hicUtil.IExtensionPointList;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class ExtensionPointList implements IExtensionPointList
{
	List<IExtensionPoint> listExtensionPoints;

	public ExtensionPointList()
	{
		this.listExtensionPoints = new ArrayList<IExtensionPoint>();
	}
	public List<IExtensionPoint> getListExtensionPoints() 
	{
		return listExtensionPoints;
	}
	public void setListExtensionPoints(List<IExtensionPoint> listExtensionPoints) 
	{
		this.listExtensionPoints = listExtensionPoints;
	}
	public void addExtensionPoint(IExtensionPoint extensionPoint) 
	{
		this.listExtensionPoints.add(extensionPoint);
	}
	public String toString()
	{
	        String returnOutput = "Extension Point List \n";
	        returnOutput = returnOutput+ "Size ="
	        				+ this.listExtensionPoints.size()+ "\n";
			for ( Iterator listCounter = this.listExtensionPoints.iterator();
	        		listCounter.hasNext();) 
	        {
				IExtensionPoint extensionPoint = (IExtensionPoint) listCounter.next();
				returnOutput = returnOutput+extensionPoint.toString()+"\n";
	        }			
			return returnOutput;
	}		
	
}

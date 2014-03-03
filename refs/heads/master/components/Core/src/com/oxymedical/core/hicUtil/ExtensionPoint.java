package com.oxymedical.core.hicUtil;

import com.oxymedical.core.hicUtil.IExtensionPoint;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class ExtensionPoint implements IExtensionPoint
{
	private String extensionPoint;
	private String extensionPointId;
	private String extensionPointName;
	public ExtensionPoint()
	{
	}
	public String getExtensionPoint() 
	{
		return extensionPoint;
	}
	public void setExtensionPoint(String extensionPoint) 
	{
		this.extensionPoint = extensionPoint;
	}
	public String getExtensionPointId() 
	{
		return extensionPointId;
	}
	public void setExtensionPointId(String extensionPointId) 
	{
		this.extensionPointId = extensionPointId;
	}
	public String getExtensionPointName() 
	{
		return extensionPointName;
	}
	public void setExtensionPointName(String extensionPointName) 
	{
		this.extensionPointName = extensionPointName;
	}
	public String toString()
	{
		return "Extension Point: \n" 
				+ " Point = "+ this.extensionPoint
				+ "\n Name = "+this.extensionPointName
				+ "\n Id = "+this.extensionPointId;
	}		
}
package com.oxymedical.core.hicUtil;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IExtensionPoint 
{
	String getExtensionPoint();
	void setExtensionPoint(String extensionPoint);
	String getExtensionPointId();
	void setExtensionPointId(String extensionPointId);
	String getExtensionPointName();
	void setExtensionPointName(String extensionPointName);
}

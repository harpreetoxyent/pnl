package com.oxymedical.core.hicUtil;

import java.util.List;

import com.oxymedical.core.hicUtil.IExtensionPoint;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IExtensionPointList 
{
	public List<IExtensionPoint> getListExtensionPoints();
	public void setListExtensionPoints(List<IExtensionPoint> listExtensionPoints);
	public void addExtensionPoint(IExtensionPoint extensionPoint); 

}

package com.oxymedical.framework.registry;

import java.util.Hashtable;

import com.oxymedical.core.hicUtil.IModuleDescriptor;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */


public interface IRegistry 
{
	public Hashtable<String,IModuleDescriptor> getModuleDescriptorList();
	public void setModuleDescriptorList(Hashtable<String,IModuleDescriptor> moduleDescriptorList);
}

package com.oxymedical.core.hicUtil;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IModuleInfo 
{
	public String getModuleId(); 
	public void setModuleId(String moduleId); 
	public String getModuleName();
	public void setModuleName(String moduleName); 
	public String getModuleproviderName(); 
	public void setModuleproviderName(String moduleproviderName); 
	public String getModuleVersion(); 
	public void setModuleVersion(String moduleVersion); 
	public String getModuleClassName(); 
	public void setModuleClassName(String moduleClassName);
	public String getModuleQualifiedName(); 
	public void setModuleQualifiedName(String moduleQualifiedName); 
}

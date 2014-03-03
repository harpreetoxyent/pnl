package com.oxymedical.core.hicUtil;

import com.oxymedical.core.hicUtil.IModuleInfo;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class ModuleInfo implements IModuleInfo
{
	private String moduleName;
	private String moduleId;
	private String moduleVersion;
	private String moduleproviderName;
	private String moduleClassName;
	private String moduleQualifiedName;
	public ModuleInfo(String moduleName,String moduleId,
			String version,	String providerName,
			String moduleClassName,String moduleQualifiedName)
	{
		this.moduleName = moduleName;
		this.moduleId = moduleId;
		this.moduleVersion = version;
		this.moduleproviderName = providerName;
		this.moduleClassName = moduleClassName;
		this.moduleQualifiedName = moduleQualifiedName;
	}	
	public String getModuleId() 
	{
		return moduleId;
	}
	public void setModuleId(String moduleId) 
	{
		this.moduleId = moduleId;
	}
	public String getModuleName() 
	{
		return moduleName;
	}
	public void setModuleName(String moduleName) 
	{
		this.moduleName = moduleName;
	}
	public String getModuleproviderName() 
	{
		return moduleproviderName;
	}
	public void setModuleproviderName(String moduleproviderName) 
	{
		this.moduleproviderName = moduleproviderName;
	}
	public String getModuleVersion() 
	{
		return moduleVersion;
	}
	public void setModuleVersion(String moduleVersion) 
	{
		this.moduleVersion = moduleVersion;
	}
	public String getModuleClassName() 
	{
		return moduleClassName;
	}
	public void setModuleClassName(String moduleClassName) 
	{
		this.moduleClassName = moduleClassName;
	}
	public String getModuleQualifiedName() 
	{
		return moduleQualifiedName;
	}
	public void setModuleQualifiedName(String moduleQualifiedName) 
	{
		this.moduleQualifiedName = moduleQualifiedName;
	}
}
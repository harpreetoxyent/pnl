package com.oxymedical.core.hicUtil;

import java.util.Hashtable;
import java.util.List;

import com.oxymedical.core.containerInfo.Event;
import com.oxymedical.core.hicUtil.ExtensionPointList;
import com.oxymedical.core.hicUtil.IExtensionPointList;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.core.hicUtil.IRequires;
import com.oxymedical.core.hicUtil.IRuntime;
import com.oxymedical.core.hicUtil.ModuleInfo;
import com.oxymedical.core.hicUtil.Requires;
import com.oxymedical.core.hicUtil.Runtime;
import com.oxymedical.core.maintenanceData.IRegisterMaintenanceDataList;
import com.oxymedical.core.maintenanceData.RegisterMaintenanceDataList;
import com.oxymedical.core.schedular.ISchedularData;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class ModuleDescriptor implements IModuleDescriptor
{
	ModuleInfo moduleInfo;
	IRequires requires;
	IRuntime runtime;
	IExtensionPointList extensionPointList;
	IRegisterMaintenanceDataList registerMaintenanceDataList;
	Hashtable<String,List<Event>> eventList;
	Hashtable<String,Hashtable<Event,List<String>>> auditList;
	List<ISchedularData> schedularList;
	
	public ModuleDescriptor()
	{
		//empty constructor inserting empty module name,
		//id, version, provider name, classname and qualifiedname 
		moduleInfo = new ModuleInfo("","","","","","");
		requires = new Requires();
		runtime = new Runtime();
		extensionPointList = new ExtensionPointList();
		registerMaintenanceDataList = new RegisterMaintenanceDataList();
	}
	public ModuleDescriptor(String moduleName,String moduleId,
							String version,	String providerName,
							String moduleClassName,String moduleQualifiedName)
	{
		if(this.moduleInfo == null)
			moduleInfo = new ModuleInfo(moduleName,
										moduleId,
										version,
										providerName,
										moduleClassName,
										moduleQualifiedName);
	}	
	public ModuleInfo getModuleInfo() 
	{
		return moduleInfo;
	}
	public void setModuleInfo(ModuleInfo moduleInfo) 
	{
		this.moduleInfo = moduleInfo;
	}
	public String toString()
	{
		return "Module Descriptor: \n" +" Module Name = "+this.moduleInfo.getModuleName()
		+"\n Module Id = "+this.moduleInfo.getModuleId()
		+"\n Module Version = "+this.moduleInfo.getModuleVersion()
		+"\n Module Provider Name = "+this.moduleInfo.getModuleproviderName()
		+"\n Module Class Name = "+this.moduleInfo.getModuleClassName()
		+"\n Module Qualified Name = "+this.moduleInfo.getModuleQualifiedName()		
		+"\n "+this.getRequires().toString()
		+"\n " +this.getRuntime().toString()
		+"\n " +this.getExtensionPointList().toString()
		+"\n" + this.getRegisterMaintenanceDataList();
	}
	public IExtensionPointList getExtensionPointList() 
	{
		return extensionPointList;
	}
	public void setExtensionPointList(IExtensionPointList extensionPointList) {
		this.extensionPointList = extensionPointList;
	}
	public IRequires getRequires() 
	{
		return requires;
	}
	public void setRequires(IRequires requires) 
	{
		this.requires = requires;
	}
	public IRuntime getRuntime() 
	{
		return runtime;
	}
	public void setRuntime(IRuntime runtime) 
	{
		this.runtime = runtime;
	}
	public IRegisterMaintenanceDataList getRegisterMaintenanceDataList() {
		return registerMaintenanceDataList;
	}
	public void setRegisterMaintenanceDataList(
			IRegisterMaintenanceDataList registerMaintenanceDataList) {
		this.registerMaintenanceDataList = registerMaintenanceDataList;
	}
	/**
	 * @return the eventList
	 */
	public Hashtable<String, List<Event>> getEventList() {
		return eventList;
	}
	/**
	 * @param eventList the eventList to set
	 */
	public void setEventList(Hashtable<String, List<Event>> eventList) {
		this.eventList = eventList;
	}
	/**
	 * @return the auditList
	 */
	public Hashtable<String, Hashtable<Event, List<String>>> getAuditList() {
		return auditList;
	}
	/**
	 * @param auditList the auditList to set
	 */
	public void setAuditList(
			Hashtable<String, Hashtable<Event, List<String>>> auditList) {
		this.auditList = auditList;
	}
	
	@Override
	public List<ISchedularData> getSchedularList() 
	{
		return this.schedularList;
	}
	@Override
	public void setSchedularDataList(List<ISchedularData> schedularList) 
	{
		this.schedularList = schedularList;
	}
		

}


package com.oxymedical.core.hicUtil;

import java.util.Hashtable;
import java.util.List;

import com.oxymedical.core.containerInfo.Event;
import com.oxymedical.core.hicUtil.IExtensionPointList;
import com.oxymedical.core.hicUtil.IRequires;
import com.oxymedical.core.hicUtil.IRuntime;
import com.oxymedical.core.hicUtil.ModuleInfo;
import com.oxymedical.core.maintenanceData.IRegisterMaintenanceDataList;
import com.oxymedical.core.schedular.ISchedularData;


/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IModuleDescriptor 
{
	ModuleInfo getModuleInfo();
	void setModuleInfo(ModuleInfo moduleInfo);
	IRequires getRequires();
	void setRequires(IRequires requires);
	IRuntime getRuntime();
	void setRuntime(IRuntime runtime);	
	IExtensionPointList getExtensionPointList();
	void setExtensionPointList(IExtensionPointList extensionPointList);
	IRegisterMaintenanceDataList getRegisterMaintenanceDataList();
	void setRegisterMaintenanceDataList(IRegisterMaintenanceDataList registerMaintenanceDataList);
	Hashtable<String, List<Event>> getEventList();
	void setEventList(Hashtable<String, List<Event>> eventList);
	Hashtable<String, Hashtable<Event, List<String>>> getAuditList();
	void setAuditList(Hashtable<String, Hashtable<Event, List<String>>> auditList);

	List<ISchedularData> getSchedularList();
	void setSchedularDataList(List<ISchedularData> schedularList);
}

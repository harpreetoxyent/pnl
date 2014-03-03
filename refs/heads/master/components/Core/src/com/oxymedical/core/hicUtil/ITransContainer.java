package com.oxymedical.core.hicUtil;

import java.util.Hashtable;
import java.util.List;

import com.oxymedical.core.containerInfo.ContainerDetails;
import com.oxymedical.core.hicUtil.IModuleDescriptor;

public interface ITransContainer
{
	List<String> getClientIds();
	Hashtable<ContainerDetails,IModuleDescriptor> getModuleDescriptorListForContainer(ContainerDetails containerDetails);	
	public void addContainer(ContainerDetails containerDetails, Hashtable<String, IModuleDescriptor> moduleDescriptiorList);
	public void addToDefaultContainer(String componentId, IModuleDescriptor compModuleDescriptor);
}

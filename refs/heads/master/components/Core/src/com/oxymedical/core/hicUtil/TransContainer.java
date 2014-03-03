package com.oxymedical.core.hicUtil;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.oxymedical.core.containerInfo.ContainerDetails;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.core.hicUtil.ITransContainer;



/**
 * This class is used to process the transaction xml's of the user
 * 
 * @author Oxyent Medical
 *
 */
public class TransContainer implements ITransContainer  
{
	Hashtable<ContainerDetails,Hashtable<String, IModuleDescriptor>> containerDescriptiorTable = new Hashtable<ContainerDetails,Hashtable<String, IModuleDescriptor>>();
		
	/**
	 * Method to add a container
	 * 
	 * @param String containerId
	 * @param Hashtable<String, IModuleDescriptor>
	 *   
     */
	public void addContainer(ContainerDetails containerDetails, Hashtable<String, IModuleDescriptor> moduleDescriptiorList)
	{
		if(!containerDescriptiorTable.contains(containerDetails))
		{
			containerDescriptiorTable.put(containerDetails, moduleDescriptiorList);
		}
	}
	/**
	 * Method to return the containerDescriptiorTable
	 * 
	 * @return Hashtable<String,Hashtable<String, IModuleDescriptor>>
	 *   
     */
	public Hashtable<ContainerDetails,Hashtable<String, IModuleDescriptor>> getContainerDescriptiorTable(){
		return this.containerDescriptiorTable;
	}
	/**
	 * Method to retrieve the moduleDescriptorList, given a container id
	 * 
	 * @param String containerId
	 */
	public Hashtable<ContainerDetails, IModuleDescriptor> getModuleDescriptorListForContainer(ContainerDetails containerDetails) {
		return null;
		
	}
	/**
	 * Method to retrieve the client ids
	 * 
	 * @return List<String>
	 */
	public List<String> getClientIds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * For updating registry at runtime.
	 * @param componentId
	 * @param compModuleDescriptor
	 */
	public void addToDefaultContainer(String componentId, IModuleDescriptor compModuleDescriptor)
	{
		Enumeration<Hashtable<String, IModuleDescriptor>> moduleDescEnum = containerDescriptiorTable.elements();
		if (moduleDescEnum != null)
		{
			// Get first ContainerDetails
			Hashtable<String, IModuleDescriptor> moduleDesc = moduleDescEnum.nextElement();
			if (moduleDesc == null)
			{
				moduleDesc.put(componentId, compModuleDescriptor);
			}
		}
	}

}

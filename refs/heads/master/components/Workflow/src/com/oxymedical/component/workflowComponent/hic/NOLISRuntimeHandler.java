package com.oxymedical.component.workflowComponent.hic;

import java.util.Hashtable;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.DataPattern;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IDataPattern;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.framework.objectbroker.exception.ObjectBrokerException;
import com.oxymedical.framework.objectbroker.strategies.singleton.SingletonPolicy;
import com.oxymedical.hic.application.JAAPKernel;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.constant.JAAPConstants;
import com.oxymedical.hic.application.policies.ComponentConfigurationPolicy;
import com.oxymedical.hic.application.strategies.proxypattern.ProxyPatternPolicy;

public class NOLISRuntimeHandler extends NOLISRuntime
{
	private static JAAPKernel kernel = null;
	
	public static Object registerComponent(String componentId, String packageClass)
	{
		Object component = null;
		try
		{
			// Just to ensure that a class with this class name exists.
			Class.forName(packageClass);

			// Register component.
			WorkflowComponent.logger.log(0, 
					"Registering component; [componentId]" + componentId 
					+ "\t[packageClass]" + packageClass);
			component = addNewComponent(componentId, componentId, packageClass);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return component;
	}
	
	
	/**
	 * For adding component at runtime
	 * @param moduleName
	 * @param moduleId
	 * @param moduleClassName
	 * @return
	 */
	public static Object addNewComponent(String moduleName, String moduleId,
			String moduleClassName)
	{
		Object obj = null;
		if(moduleId !=null)
		{
			/*kernel = JAAPKernel.getInstance();
			
			// Add module entry to Registry
			kernel.getRegistry().addToRegistry(
					moduleName, moduleId, 
					JAAPConstants.DEFAULT_COMPONENT_VERSION, 
					JAAPConstants.DEFAULT_COMPONENT_PROVIDER_NAME, 
					moduleClassName, null);*/

			// Get component based on added ModuleId
			obj = getComponent(moduleId, moduleClassName);
		}
		return obj;
	}
// method added to slove problem of proxy object
	public static Object getComponent(String moduleId, String toolClass, boolean isSingleton)
	{
		Object obj = null;
		try
		{	
			if (moduleId !=null)
			{
				kernel = JAAPKernel.getInstance();

				if(isSingleton)
				{
					kernel.getRegistry().addToRegistry(moduleId, moduleId, "1.0", "Oxyent Medical", toolClass, toolClass);
				}
				/*Hashtable<String,IModuleDescriptor> listRegistry = kernel.getRegistry().getModuleDescriptorList();
				IModuleDescriptor modDesc = listRegistry.get(moduleId);*/	
				//ClassLoader loader = this.getClass().getClassLoader();
				if(toolClass != null)
				{
					Class moduleClass = Class.forName(toolClass);
					/*ComponentConfigurationPolicy compPolicy = new ComponentConfigurationPolicy();
					compPolicy.setConfigurationData(configurationData);
					kernel.getBuilder().getPolicies().set(compPolicy,moduleClass, moduleId);*/
					kernel.getBuilder().getPolicies().set(new ComponentConfigurationPolicy(), moduleClass, moduleId);
					kernel.getBuilder().getPolicies().set(new SingletonPolicy(isSingleton), moduleClass, moduleId);
					kernel.getBuilder().getPolicies().set(new ProxyPatternPolicy(true), moduleClass, moduleId);
					obj = kernel.getBuilder().BuildUp(moduleClass,kernel.getLocator(), moduleId, null,null); 
				}
				else
				{
					//Module Registry is null so throw exception
					throw (new Exception("No Module with id '"+ moduleId +"' found"));
				}
				
			}
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}		
		catch(ObjectBrokerException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return obj;

	}
	//implementation changed because problem of proxy was coming .
	public static Object getComponent(String moduleId, String toolClass)
	{
		Object obj= getComponent(moduleId,  toolClass, false);
		return obj;
	}

	
	
	public static IHICData prepareHICData(IHICData incomingDataObject)
	{
		if (incomingDataObject.getData() == null)
		{
			IData data = new Data();
			incomingDataObject.setData(data);
		}
		if (incomingDataObject.getData().getFormPattern() == null)
		{
			IFormPattern fp = new FormPattern();
			incomingDataObject.getData().setFormPattern(fp);
		}
		if (incomingDataObject.getData().getDataPattern() == null)
		{
			IDataPattern dp = new DataPattern();
			incomingDataObject.getData().setDataPattern(dp);
		}
		if (incomingDataObject.getData().getWorkflowPattern() == null)
		{
			IWorkflowPattern wp = new WorkflowPattern();
			incomingDataObject.getData().setWorkflowPattern(wp);
		}
		
		return incomingDataObject;

	}
	
}

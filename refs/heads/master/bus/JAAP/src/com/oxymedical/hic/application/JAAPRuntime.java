package com.oxymedical.hic.application;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;
import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolutionLocatorKey;
import com.oxymedical.framework.objectbroker.Location.IReadWriteLocator;
import com.oxymedical.framework.objectbroker.Location.Locator;
import com.oxymedical.framework.objectbroker.exception.ObjectBrokerException;
import com.oxymedical.framework.objectbroker.strategies.singleton.SingletonPolicy;
import com.oxymedical.hic.application.constant.JAAPConstants;
import com.oxymedical.hic.application.eventmanagement.EventTopic;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;
import com.oxymedical.hic.application.policies.ComponentConfigurationPolicy;
import com.oxymedical.hic.application.strategies.proxypattern.ProxyPatternPolicy;


@SuppressWarnings("deprecation")
public class JAAPRuntime 
{
	private static Category cat = Category.getInstance("ObjectBuilder");
	public static Hashtable<String,org.dom4j.Document> configurationData = null;
	private static JAAPKernel kernel = null;
	public static int requestNumber = 0;
	public static Boolean hasStarted = false;
	
	public JAAPRuntime() {
		
	}

	/*
	 * SV: Incomplete code for loading jars at runtime for module.xml 
	IRequires requires = modDesc.getRequires();
	List<String> requiredJarList = requires.getImportLibraryNames();
	String jarLocation = requires.getJarFolder();
	int jarCount = requiredJarList.size();
	URL[] url = new URL[jarCount];
	for(int jarCounter = 0; jarCounter < jarCount; jarCounter++)
	{
		File jarFile = new File(jarLocation+requiredJarList.get(jarCounter));
		url[jarCounter] = jarFile.toURL();
	}
	URLClassLoader loader1 = new URLClassLoader(url,loader);
	for(int jarCounter = 0; jarCounter < jarCount; jarCounter++)
	{
		JarFile jarFile = new JarFile(jarLocation+"/"+requiredJarList.get(jarCounter));
        Enumeration entries = jarFile.entries();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String str=entry.getName();
            int indexOfDot = str.indexOf(".");	
            if(indexOfDot > 0)
            {
            	String extensionFile = str.substring(indexOfDot);
                if(extensionFile.trim().equals(".class"))
                {	
                	str = str.substring(0, indexOfDot);
	                str = str.replace("/", ".");
                    Class<?> c = loader1.loadClass(str.trim());
                }
             }
        }
    }*/

	
	public static Object getComponent(String moduleId)
	{
		Object obj = null;
		try
		{	
			if(hasStarted && moduleId !=null)
			{
				kernel = JAAPKernel.getInstance();
				Hashtable<String,IModuleDescriptor> listRegistry = kernel.getRegistry().getModuleDescriptorList();
				IModuleDescriptor modDesc = listRegistry.get(moduleId);	
				//ClassLoader loader = this.getClass().getClassLoader();
				if(modDesc != null)
				{
					Class moduleClass = Class.forName(modDesc.getModuleInfo().getModuleClassName());
					ComponentConfigurationPolicy compPolicy = new ComponentConfigurationPolicy();
					compPolicy.setConfigurationData(configurationData);
					kernel.getBuilder().getPolicies().set(compPolicy,moduleClass, moduleId);
					kernel.getBuilder().getPolicies().set(new SingletonPolicy(true),moduleClass, moduleId);
					kernel.getBuilder().getPolicies().set(new ProxyPatternPolicy(true),moduleClass, moduleId);
					obj = kernel.getBuilder().BuildUp(moduleClass,kernel.getLocator(), moduleId, null,null); 
				}
				else
				{
					//Module Registry is null so throw exception
					//throw (new Exception("No Module with id '"+ moduleId +"' found"));
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
	public static JAAPKernel getKernel()
	{
		return kernel;
	}
	
	private void addComponents()
	{
		String moduleId = null;
		Object componentInfo = null;
		//the name should come from registry
		Hashtable<String,IModuleDescriptor> listRegistry = kernel.getRegistry().getModuleDescriptorList(); 
		for (Enumeration enumeration = listRegistry.keys() ; enumeration.hasMoreElements() ;)
		{
			try
			{
				moduleId = (String)enumeration.nextElement();
				//componentInfo = this.getComponent(moduleId);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}	
	}	
	public String getModuleInfo(String input)
	{		
		String output =kernel.getRegistry().toString();
		return output;
	}

	public String getMessage()
	{
		return "HIC Architecture";
	}
	
	private JAAPRuntime(Hashtable<String,Document> configData)
	{
		configurationData = configData;
	}
	
	// method to get unique id for each component
	public static List<String> getComponentIDList(Object component)
	{
		List<Class<?>> interfaceClassList = new ArrayList<Class<?>>();
		kernel = JAAPKernel.getInstance();
		Class typeToBuild = component.getClass();
		List<String> idList = new ArrayList<String>();
		IReadWriteLocator locator  = kernel.getLocator();
		Enumeration enumLocator = locator.keys();
		while(enumLocator.hasMoreElements())
		{
			Object key = enumLocator.nextElement();
			Object keyObject = locator.Get(key);
			if(!(keyObject.getClass().getName()).equalsIgnoreCase(component.getClass().getName()))
			{	
			    continue;	
			}
			if(! (keyObject instanceof IComponent))
			{
			}
				
			DependencyResolutionLocatorKey keyObject1 =(DependencyResolutionLocatorKey)key;
			if(keyObject1.getType() == typeToBuild )
			{
				idList.add(keyObject1.getID());
			}
		}
		
		return idList;
	}
	
	public static Object getHICData()
	{
		System.out.println("----------Inside JAAPRuntime----getHICHData hasStarted="+hasStarted);
		Object obj = null;
		try
		{	
			if(hasStarted)
			{
					kernel = JAAPKernel.getInstance();
					Class hicDataClass = HICData.class;
					ComponentConfigurationPolicy compPolicy = new ComponentConfigurationPolicy();
					compPolicy.setConfigurationData(configurationData);
					kernel.getBuilder().getPolicies().set(compPolicy,hicDataClass, "");
					kernel.getBuilder().getPolicies().set(new SingletonPolicy(false),hicDataClass, "");
					obj = kernel.getBuilder().BuildUp(hicDataClass,kernel.getLocator(), "", null,null); 
			}
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
	
	public static <T> Object getClassInstance(Class<T> clz, String id)
	{
		Object obj = null;
		try
		{	
			if(hasStarted)
			{
					kernel = JAAPKernel.getInstance();
					ComponentConfigurationPolicy compPolicy = new ComponentConfigurationPolicy();
					compPolicy.setConfigurationData(configurationData);
					kernel.getBuilder().getPolicies().set(compPolicy,clz, "");
					kernel.getBuilder().getPolicies().set(new SingletonPolicy(false),clz, "");
					obj = kernel.getBuilder().BuildUp(clz,kernel.getLocator(), id, null,null); 
			}
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
	
	public static IModuleDescriptor getComponentDesc(String moduleId)
	{
		IModuleDescriptor obj = null;
		try
		{		
			kernel = JAAPKernel.getInstance();
			Hashtable<String,IModuleDescriptor> listRegistry = kernel.getRegistry().getModuleDescriptorList(); 
			IModuleDescriptor modDesc = listRegistry.get(moduleId);
			if(modDesc != null)
			{
				
				
				obj= modDesc;
				
			}
			else
			{
				throw (new Exception("No Module with this id Found: " + moduleId));
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
	public String getLoadedComponent(String moduleType)
	{
		int availableModule = 0;;
		Locator locator;
		String moduleList = "<moduleList>";
		try
		{
			kernel = JAAPKernel.getInstance();
			availableModule = kernel.getLocator().getCount()-1;
			moduleList = moduleList + "<moduleCount>"+availableModule+"</moduleCount>";
			locator = kernel.getLocator();
			Object nextObject = null;
			for (Iterator moduleIterator = locator.getIterator() ;  
			moduleIterator.hasNext (  ) ;  
			nextObject = moduleIterator.next ())  
			{
			    if(nextObject != null)
			    {
		        	moduleList = moduleList + "<moduleName>"+nextObject.getClass().getName()+"</moduleName>";
			    }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		moduleList = moduleList + "</moduleList>";
		return moduleList;
	}
	
	public static IHICData FireEvent(Object publisher,
							String topicName, 
							Object[] parameters,
							PublicationScope scope) throws Exception
	{
		//get the topic from the context
		kernel = JAAPKernel.getInstance();
		EventTopic topic = JAAPKernel.getKernelContext().getEventTopic(topicName);
		if(topic != null && topic.getSubscriptionCount() > 0)
		{
			//check if the same object has done binding for this event
			//if yes then fire the eventopic
			IHICData obj = topic.Fire(publisher, parameters, scope);
			return obj;
		}
		else if(topic != null && topic.getSubscriptionCount() ==0 )
		{
			throw new Exception("No Subscriber for this event found , event id =" + topic.getName());		
		}
		return null;
	}
	public static boolean hasStarted() 
	{
		return hasStarted;
	}
	
	void setHasStarted(boolean hasStarted) 
	{
		this.hasStarted = hasStarted;
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
		if(hasStarted && moduleId !=null)
		{
			kernel = JAAPKernel.getInstance();
			
			// Add module entry to Registry
			kernel.getRegistry().addToRegistry(
					moduleName, moduleId, 
					JAAPConstants.DEFAULT_COMPONENT_VERSION, 
					JAAPConstants.DEFAULT_COMPONENT_PROVIDER_NAME, 
					moduleClassName, null);

			// Get component based on added ModuleId
			obj = getComponent(moduleId);
		}
		return obj;
	}
	
}

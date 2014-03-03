package com.oxymedical.framework.registry;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.core.containerInfo.ContainerDetails;
import com.oxymedical.core.containerInfo.Event;
import com.oxymedical.core.hicUtil.ExtensionPoint;
import com.oxymedical.core.hicUtil.ExtensionPointList;
import com.oxymedical.core.hicUtil.IExtensionPoint;
import com.oxymedical.core.hicUtil.IExtensionPointList;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.core.hicUtil.IRequires;
import com.oxymedical.core.hicUtil.IRuntime;
import com.oxymedical.core.hicUtil.ITransContainer;
import com.oxymedical.core.hicUtil.ModuleDescriptor;
import com.oxymedical.core.hicUtil.Requires;
import com.oxymedical.core.hicUtil.Runtime;
import com.oxymedical.core.hicUtil.TransContainer;
import com.oxymedical.core.maintenanceData.Action;
import com.oxymedical.core.maintenanceData.Defect;
import com.oxymedical.core.maintenanceData.IAction;
import com.oxymedical.core.maintenanceData.IDefect;
import com.oxymedical.core.maintenanceData.IRegisterMaintenanceData;
import com.oxymedical.core.maintenanceData.IRegisterMaintenanceDataList;
import com.oxymedical.core.maintenanceData.RegisterMaintenanceData;
import com.oxymedical.core.maintenanceData.RegisterMaintenanceDataList;
import com.oxymedical.core.schedular.ISchedularData;
import com.oxymedical.core.schedular.SchedularData;


/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class Registry implements IRegistry 
{
	static Category cat = Category.getInstance("ObjectBuilder");	
	private ITransContainer containerSettings;
	public Hashtable<String,IModuleDescriptor> moduleDescriptorList;
	private static String fileName;
	private Document xmlDocument;
	public static void main(String[] args)
	{
		Registry r1 = new Registry();
		r1.LoadXML(null);
		//Hashtable<String,IModuleDescriptor> listRegistry = r1.getModuleDescriptorList(); 
		//IModuleDescriptor modDesc = listRegistry.get("com.ethalon.argus.component.invoiceworkflow");
	}
	public Registry()
	{
		cat.info("Inside Registry Constructor");
		containerSettings = new TransContainer();
		moduleDescriptorList =  new Hashtable<String,IModuleDescriptor>();
		fileName = "module.xml";
		cat.info("Ended Registry Constructor");
	}
	public void LoadXML(Document xmlDoc)
	{
		try
		{
			xmlDocument = xmlDoc;
			if(xmlDocument == null)
			{
				xmlDocument = parseUsingSAX();
			}
			cat.info("XML document Created");
			generateRegistry(xmlDocument);
			cat.info("Registry Generated");
		}
		catch(Exception e)
		{
			cat.info("Exception happened"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	public Document parseUsingSAX() throws DocumentException
	{
		cat.info("Inside parseUsingSAX method filename ="+fileName);
		//File moduleFile = new File(fileName);
		cat.info("File Read");
		SAXReader reader = new SAXReader();
		cat.info("SAXReader Created Filename ="+fileName);
		InputStream modulefileStream =
			this.getClass().getResourceAsStream("module.xml");
		Document document = reader.read(modulefileStream);
		cat.info("Document Generated ="+document.asXML());
		return document;
	}
	public ModuleDescriptor generateModule(Document document) throws DocumentException
	{
		return null;
	}
	public void generateRegistry(Document document) throws DocumentException
	{
		//String clientId = null;
		String containerTypeElem = null;
		String containerIdElem = null;
		Element root = document.getRootElement();
		//Root element is application name
        for(Iterator rootCounter = root.attributeIterator(); rootCounter.hasNext(); ) 
        {
            Attribute attribute = (Attribute) rootCounter.next();
            if(attribute != null && attribute.getName().equals("type"))
            {
            	containerTypeElem = attribute.getValue();
            }
            if(attribute != null && attribute.getName().equals("id"))
            {
            	containerIdElem = attribute.getValue();
            }
                        
        }
		
        //Root child element will be module List 
        Element moduleListElement = root.element("modulelist");
        List<Element> modules = moduleListElement.elements("module");
		// iterate through child elements of modulelist
		IModuleDescriptor modDesc = null;
		for(Iterator i = modules.iterator(); i.hasNext(); )
		{
			String moduleName = null;
			String moduleId = null;
			String version = null;
			String providerName = null;
			String moduleClassName = null;
			String moduleQualifiedName = null;			
			Element moduleElement = (Element) i.next();
	        // iterate through attributes of module 
	        for(Iterator modCounter = moduleElement.attributeIterator(); modCounter.hasNext(); ) 
	        {
	            Attribute attribute = (Attribute) modCounter.next();
	            if(attribute != null && attribute.getName().equals("name"))	
	            	moduleName = attribute.getValue();
	            if(attribute != null && attribute.getName().equals("id"))	
	            {
	            	moduleId = attribute.getValue();
	            }
	            if(attribute != null && attribute.getName().equals("version"))	
	            	version = attribute.getValue();
	            if(attribute != null && attribute.getName().equals("provider-name"))	
	            	providerName = attribute.getValue();	            
	            if(attribute != null && attribute.getName().equals("class"))	
	            	moduleClassName = attribute.getValue();
	            if(attribute != null && attribute.getName().equals("qualifiedname"))	
	            	moduleQualifiedName	 = attribute.getValue();	            
	            // do something
	        }
			//iterating through Modules under modulelist(root node)
			modDesc = new ModuleDescriptor(moduleName,moduleId,
					   version,providerName,moduleClassName,moduleQualifiedName);
			
			// iterate through child elements of module
			IRuntime runtime = null;
			IRequires requires = null;
			IExtensionPoint extPoint = null;
			IExtensionPointList extPointList = null;
			IRegisterMaintenanceData registerMaintenanceData = null;
			IRegisterMaintenanceDataList registerMaintenanceDataList = null;
			List<IAction> actionList = null;
			List<IDefect> defectList = null;
			List<Event> eventList = new ArrayList<Event>();
			List<ISchedularData> schedularList = null;
			Hashtable<Event,List<String>> eventAuditList = new Hashtable<Event,List<String>>();
			List<String> auditDataList = null;
			Event event = null;
			Hashtable<String,List<Event>> eventDetails = null;
			Hashtable<String,Hashtable<Event,List<String>>> auditDetails = null;
			
			for (Iterator m = moduleElement.elementIterator(); m.hasNext(); )
			{
				extPointList = new ExtensionPointList();
				registerMaintenanceDataList = new RegisterMaintenanceDataList();
				Element moduleChildElement = (Element) m.next();
				if(moduleChildElement.getName().equals("requires"))
				{
					//Inside requires element
					requires = new Requires();
					List<String> libraryNames = new ArrayList<String>(); 
					String jarFloder = null;
					for(Iterator moduleIterate = moduleChildElement.elementIterator(); moduleIterate.hasNext(); )
					{
						Element element = (Element) moduleIterate.next();
						// do something
				        for ( Iterator reqCounter = element.attributeIterator(); reqCounter.hasNext(); ) 
				        {
				            Attribute attribute = (Attribute) reqCounter.next();
				            if(attribute != null && attribute.getName().equals("location"))
				            {
				            	jarFloder = attribute.getValue();
				            }
				            if(attribute != null && attribute.getName().equals("module"))
				            {
				            	libraryNames.add(attribute.getValue());
				            }
				        }										
					}
					requires.setImportLibraryNames(libraryNames);
					requires.setJarFolder(jarFloder);
				}
				if(moduleChildElement.getName().equals("runtime"))
				{
					//Inside runtime element
					runtime = new Runtime();
					
					
					List<String> runtimeModules = new ArrayList<String>(); 
					for (Iterator moduleIterate = moduleChildElement.elementIterator(); moduleIterate.hasNext(); )
					{
						Element element = (Element) moduleIterate.next();
						// do something
				        for (Iterator reqCounter = element.attributeIterator(); reqCounter.hasNext(); ) 
				        {
				            Attribute attribute = (Attribute) reqCounter.next();
				            if(attribute != null && attribute.getName().equals("name"))
				            {
				            	runtimeModules.add(attribute.getValue());
				            }
				        }										
					}
					runtime.setRuntimeLibraryNames(runtimeModules);
				}				
				if(moduleChildElement.getName().equals("extension"))
				{
					
					//extension point
					extPoint = new ExtensionPoint();
			        for ( Iterator extCounter = moduleChildElement.attributeIterator(); extCounter.hasNext(); ) 
			        {
			            Attribute attribute = (Attribute) extCounter.next();
			            if(attribute != null && attribute.getName().equals("point"))
			            	extPoint.setExtensionPoint(attribute.getValue());
			            if(attribute != null && attribute.getName().equals("id"))	
			            	extPoint.setExtensionPointId(attribute.getValue());
			            if(attribute != null && attribute.getName().equals("name"))	
			            	extPoint.setExtensionPointName(attribute.getValue());			            			            
			        }
			        extPointList.addExtensionPoint(extPoint);
					//counter = 0;
				}
				
				if(moduleChildElement.getName().equals("maintenance"))
				{
					//extension point
					
					registerMaintenanceDataList = new RegisterMaintenanceDataList();
					for(Iterator maintIterator = moduleChildElement.elementIterator(); maintIterator.hasNext();)
					{
						Element defect_action= (Element) maintIterator.next();
						registerMaintenanceData = new RegisterMaintenanceData();
						for(Iterator defActIterator = defect_action.elementIterator();  defActIterator.hasNext();)
						{
							Element defOrActionList = (Element)defActIterator.next();
							if(defOrActionList.getName().equalsIgnoreCase("Defect-List"))
							{
								defectList = new ArrayList<IDefect>();
								IDefect defect=null;
								for(Iterator defectListIterator = defOrActionList.elementIterator(); defectListIterator.hasNext();)
								{
									defect= new Defect();
									Element defectElement = (Element)defectListIterator.next();
									for( Iterator defectIterator = defectElement.attributeIterator(); defectIterator.hasNext();)
									{
										Attribute defectAttribute = (Attribute) defectIterator.next();
										if(defectAttribute != null && defectAttribute.getName().equalsIgnoreCase("id"))
										{
											defect.setDefect_id(defectAttribute.getValue());
										}
										if(defectAttribute != null && defectAttribute.getName().equalsIgnoreCase("severity"))
										{
											defect.setSeverity(Integer.parseInt(defectAttribute.getValue()));
										}
									}
									defect.setDefect_type(defectElement.getTextTrim());
									defectList.add(defect);
								}
							}
							else
							if(defOrActionList.getName().equalsIgnoreCase("Action-List"))
							{
								actionList = new ArrayList<IAction>();
								IAction action = null;
								for( Iterator actionListIterator = defOrActionList.elementIterator(); actionListIterator.hasNext();)
								{
									action = new Action();
									Element actionElement = (Element)actionListIterator.next();
									for( Iterator actionAttributeItr = actionElement.attributeIterator(); actionAttributeItr.hasNext();)
									{
										Attribute actionAttr = (Attribute) actionAttributeItr.next();
										action.setAction_id(actionAttr.getValue());
									}
									List<String> defect_id = new ArrayList<String>();
									for(Iterator actionElementItr = actionElement.elementIterator(); actionElementItr.hasNext();)
									{
										Element actionChildElement = (Element)actionElementItr.next();
										if(actionChildElement.getName().equalsIgnoreCase("Defect"))
										{
											defect_id.add(actionChildElement.getTextTrim());
										}
										if(actionChildElement.getName().equalsIgnoreCase("type"))
										{
											action.setAction_type(actionChildElement.getTextTrim());
										}
									}
									action.setDefect_id(defect_id);
									actionList.add(action);
								}
								
							}
							
						}
						registerMaintenanceData.setAction(actionList);
						registerMaintenanceData.setDefect(defectList);
						registerMaintenanceDataList.getListRegisterMaintenanceData().add(registerMaintenanceData);
						
					}
					modDesc.setRegisterMaintenanceDataList(registerMaintenanceDataList);
				}
				if(moduleChildElement.getName().equals("billingTracking"))
				{

					for(Iterator chldElemItr=moduleChildElement.elementIterator();chldElemItr.hasNext();)
					{
						Element eventElem = (Element)chldElemItr.next();
						if(eventElem.getName().equals("event")){
							event = new Event();
							event.setEventId(eventElem.attributeValue("id"));
							
							for(Iterator eventElemItr = eventElem.elementIterator();eventElemItr.hasNext();)
							{
								Element eventValueElem = (Element)eventElemItr.next();								
								if( null!= eventValueElem && eventValueElem.getName().equals("method"))
								{
									event.setMethod(eventValueElem.getText());
								}	
																
							}	
							eventList.add(event);
						}
					}	
				}//end of chldElemItr	
				if(moduleChildElement.getName().equals("auditLogging"))
				{
					eventAuditList = new Hashtable<Event,List<String>>();
					for(Iterator chldElemItr=moduleChildElement.elementIterator();chldElemItr.hasNext();)
					{
						Element eventElem = (Element)chldElemItr.next();
						if(eventElem.getName().equals("event")){
							event = new Event();
							auditDataList = new ArrayList<String>();
							event.setEventId(eventElem.attributeValue("id"));
							for(Iterator eventElemItr = eventElem.elementIterator();eventElemItr.hasNext();){
								Element eventValueElem = (Element)eventElemItr.next();								
								if( null!= eventValueElem && eventValueElem.getName().equals("method")){
									event.setMethod(eventValueElem.attributeValue("name"));
									for(Iterator methodElemItr = eventValueElem.elementIterator();methodElemItr.hasNext();){
										
										Element auditPatternElem = (Element)methodElemItr.next();
									
										//add all the audit-elements corresponding to an event
										
										 if(auditPatternElem.getName().equals("auditPattern")){
											 event.setAuditPattern(auditPatternElem.attributeValue("id"));
											 
											List<Element> auditList = auditPatternElem.elements("auditData");
										
											for(Iterator auditListItr= auditList.iterator() ;auditListItr.hasNext();){
												Element auditDataElem = (Element)auditListItr.next();
												if (null != auditDataElem && auditDataElem.getName().equals("auditData")){
													auditDataList.add(auditDataElem.getText());
												}
											}
											eventAuditList.put(event,auditDataList);
											
										}
									}
								}	
																
							}	
							//eventAuditList.put(event,auditDataList);
						}
					}	
				}//end of chldElemItr
				
				if(moduleChildElement.getName().equals("schedulars"))
				{
					schedularList = new ArrayList<ISchedularData>();
					for (Iterator<Element> iter = moduleChildElement.elementIterator(); iter.hasNext();)
					{
						Element schedular = (Element) iter.next();
						ISchedularData schd = new SchedularData();
						
						String intervalStr = schedular.element("interval-in-secs").getTextTrim();
						schd.setInterval(Long.parseLong(intervalStr));
						schd.setSubscribedMethod(schedular.element("subscribed-method").getTextTrim());
						
						schedularList.add(schd);
					}
				}
				
			} //end if iterate through child elements of module
			
			//setting all events corresponding to component id
			eventDetails = new Hashtable<String,List<Event>>();
			eventDetails.put(modDesc.getModuleInfo().getModuleId(),eventList);
			
			auditDetails = new Hashtable<String,Hashtable<Event,List<String>>>();
			auditDetails.put(modDesc.getModuleInfo().getModuleId(), eventAuditList);
			
			modDesc.setRequires(requires);
			modDesc.setRuntime(runtime);
			modDesc.setExtensionPointList(extPointList);
			
			
			modDesc.setEventList(eventDetails);
			modDesc.setAuditList(auditDetails);
			
			modDesc.setSchedularDataList(schedularList);
			
			this.moduleDescriptorList.put(modDesc.getModuleInfo().getModuleId(),modDesc);
		}
		ContainerDetails containerDetails = new ContainerDetails();
		containerDetails.setContainerId(containerIdElem);
		containerDetails.setContainerType(containerTypeElem);
		
		containerSettings.addContainer(containerDetails,this.moduleDescriptorList);
		
		//persist container details
		//hicRuntime.FireEvent(hicRuntime.getKernel().getKernelContext(), TopicConstants.persistModuleXMLValues, null, PublicationScope.Global);
		
		/*ContainerDetails containerDetails = new ContainerDetails();
		containerDetails.setContainer(containerSettings);
		containerDetails.setType(containerType);*/
		
	}
	public String toString()
	{

        String returnOutput = xmlDocument.getRootElement().asXML(); 
        	
        /*	"Registry Info\n";
        returnOutput = returnOutput+ "Size ="
        				+ this.moduleDescriptorList.size()+ "\n";
		for ( Iterator listCounter = this.moduleDescriptorList.iterator();
        		listCounter.hasNext();) 
        {
			IModuleDescriptor moduleDescriptor = (IModuleDescriptor) listCounter.next();
			returnOutput = returnOutput+moduleDescriptor.toString()+"\n";
        }
        */			
		return returnOutput;
	}	
	public Hashtable<String,IModuleDescriptor> getModuleDescriptorList() 
	{
		return moduleDescriptorList;
	}
	public void setModuleDescriptorList(Hashtable<String,IModuleDescriptor> moduleDescriptorList) 
	{
		this.moduleDescriptorList = moduleDescriptorList;
	}
	/**
	 * @return the containerSettings
	 */
	public ITransContainer getContainerSettings() {
		return containerSettings;
	}
	/**
	 * @param containerSettings the containerSettings to set
	 */
	public void setContainerSettings(ITransContainer containerSettings) {
		this.containerSettings = containerSettings;
	}


	/**
	 * For adding a component entry into Registry at runtime. <br>
	 * TODO - To be enhanced to include input for Container Id, Container Type
	 * and other details like "requires", "runtime", "maintenance",
	 * "billingTracking".
	 * 
	 * @param moduleName
	 * @param moduleId
	 * @param version
	 * @param providerName
	 * @param moduleClassName
	 * @param moduleQualifiedName
	 */
	public void addToRegistry(String moduleName, String moduleId,
			String version, String providerName, String moduleClassName, String moduleQualifiedName)
	{
		IModuleDescriptor modDesc = new ModuleDescriptor(moduleName,moduleId,
				   version,providerName,moduleClassName,moduleQualifiedName);
		
		// Update Trans Container
		this.getContainerSettings().addToDefaultContainer(moduleId, modDesc);
		
		// Update Module Descriptor List
		this.getModuleDescriptorList().put(moduleId, modDesc);
	}
}
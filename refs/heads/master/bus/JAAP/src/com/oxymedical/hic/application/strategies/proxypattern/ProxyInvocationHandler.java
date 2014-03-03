package com.oxymedical.hic.application.strategies.proxypattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventPublisher;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.DataType;
import com.oxymedical.core.commonData.IDataType;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IMetaData;
import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.MetaData;
import com.oxymedical.core.commonData.Source;
import com.oxymedical.core.constants.TopicConstants;
import com.oxymedical.core.containerInfo.Event;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.core.maintenanceData.Defect;
import com.oxymedical.core.maintenanceData.IDefect;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.maintenanceData.MaintenanceData;
import com.oxymedical.core.workflow.IWorkflowTool;
import com.oxymedical.hic.application.JAAPRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class ProxyInvocationHandler implements InvocationHandler 
{
       	
	   Object  realObject = null;
	   Boolean outerLoop = false;
	   
	   
       public ProxyInvocationHandler(Object realObj) 
       {
    	   realObject = realObj;	
    	   
       }
       public Object invoke(Object proxyObject, Method method, Object[] args) throws Throwable 
       {
		Object result = null;
		// code for Billing Tracking
		IComponent invokedComp = (IComponent) realObject;
		IHICData hicData = (IHICData) invokedComp.getHicData();
		if (null != hicData)
		{

			// "$$$method ="+method.getName());
			List<String> componentID = JAAPRuntime.getComponentIDList(realObject);

			// if( !(method.getName().toLowerCase().trim().equals("tostring")))
			// {
			IModuleDescriptor moduleDescriptor = JAAPRuntime.getComponentDesc(componentID.get(0));

			List<Event> eventList = new ArrayList<Event>();
			Hashtable<Event, List<String>> auditList = new Hashtable<Event, List<String>>();
			Object[] auditEventList = null;
			if (null != moduleDescriptor)
			{
				if(moduleDescriptor.getEventList() != null)
				{
					eventList = moduleDescriptor.getEventList().get(componentID.get(0));
					// loop to evaluate BT
					for (Iterator eventItr = eventList.iterator(); eventItr.hasNext();)
					{
						Event event = (Event) eventItr.next();
						if (null != event)
						{
							if (event.getMethod().equals(method.getName()))
							{
								ISource source = new Source();
								source.setComponent_id(componentID.get(0));
								LinkedList<ISource> srclist = new LinkedList<ISource>();
								srclist.add(source);
								hicData.setSrcHistoryList(srclist);
								Hashtable<String, String> dataSet = new Hashtable<String, String>();
								dataSet.put("methodName", method.getName());
								IDataType dataType = new DataType();
								dataType.setDataSet(dataSet);
								hicData.setDataType(dataType);

								Object[] objectArray =
								{ hicData };
								// + hicData.getSource().getComponent_id());
								// +
								// hicData.getDataType().getDataSet().get("methodName"));
								JAAPRuntime.FireEvent(JAAPRuntime.getKernel().getKernelContext(), TopicConstants.billingTracking, objectArray, PublicationScope.Global);
							}
						}
					}					
				}
				if(moduleDescriptor.getAuditList() != null)
				{
					auditList = moduleDescriptor.getAuditList().get(componentID.get(0));
					auditEventList = auditList.keySet().toArray();
					// loop to evaluate auditLogging
					for (int i = 0; i < auditEventList.length; i++)
					{
						Event auditEvent = (Event) auditEventList[i];
						if (null != auditEvent)
						{
							if (auditEvent.getMethod().equals(method.getName()))
							{
								// setting the component object
								IMetaData componentObj = new MetaData();
								componentObj.setCommonObject(proxyObject);
								hicData.setMetaData(componentObj);

								// setting method-name
								ISource source = new Source();
								source.setMethodName(method.getName());
								LinkedList<ISource> srclist = new LinkedList<ISource>();
								srclist.add(source);
								hicData.setSrcHistoryList(srclist);

								// setting the audit-data list
								IDataType dataType = new DataType();
								dataType.setDataList(auditList.get(auditEvent));
								hicData.setDataType(dataType);

								Object[] objectArray =
								{ hicData };
								// + hicData.getSource().getComponent_id());
								// +
								// hicData.getDataType().getDataSet().get("methodName"));
								JAAPRuntime.FireEvent(JAAPRuntime.getKernel().getKernelContext(), TopicConstants.auditLogging, objectArray, PublicationScope.Global);

							}
						}
					}					
				}
			}
		}
		else
		{

		}
		if (method.isAnnotationPresent(MaintenancePublisher.class))
		{
			IMaintenanceData maintenanceData = new MaintenanceData();
			if (!(method.getName().equalsIgnoreCase("maintenance")))
			{
				try
				{
					result = method.invoke(realObject, args);
				}
				catch (InvocationTargetException eBj)
				{
					IDefect reqDefect = new Defect();
					maintenanceData = new MaintenanceData();
					String message = eBj.getTargetException().getMessage();
					int i = message.indexOf(" ");
					String comp_id = message.substring(0, i);
					// JAAPRuntime.getComponentIDList(component)
					IModuleDescriptor moduleDesc = JAAPRuntime.getComponentDesc("id.rendering");
					String defect_id = message.substring(i + 1);
					reqDefect.setDefect_id(defect_id);
					Calendar calendar = Calendar.getInstance();
					Timestamp tmstmp = new java.sql.Timestamp(calendar.getTime().getTime());
					reqDefect.setDefect_occured(tmstmp);
					maintenanceData.setDefect(reqDefect);
					maintenanceData.setComponent_id(comp_id);
					Object[] objectArray =
					{ maintenanceData, moduleDesc };
					// if noone is a subscriber to eat this exception notify
					// HIC no maintenance object instance present.
					JAAPRuntime.FireEvent(proxyObject, TopicConstants.topic, objectArray, PublicationScope.Global);
					// use the objectArray[0] to invoke maintenance method of
					// publisher
					if (realObject instanceof IComponent)
					{
						IComponent component = (IComponent) realObject;
						IMaintenanceData maintenance = (IMaintenanceData) objectArray[0];
						component.maintenance(maintenance);
					}
				}

				finally
				{
					// Do something after the method is called ...
				}
			}
			else
			{
				try
				{
					result = method.invoke(realObject, args);
					maintenanceData = (IMaintenanceData) args[0];
					IModuleDescriptor moduleDesc = JAAPRuntime.getComponentDesc(maintenanceData.getComponent_id());
					Object[] objectArray = { args[0], moduleDesc };
					JAAPRuntime.FireEvent(proxyObject, TopicConstants.topic, objectArray, PublicationScope.Global);
					if (realObject instanceof IComponent)
					{
						IComponent component = (IComponent) realObject;
						IMaintenanceData maintenance = (IMaintenanceData) objectArray[0];
						component.maintenance(maintenance);
					}

				}
				catch (ComponentException e)
				{
					// + e.getMessage());
					throw e;
				}
				catch (Exception e)
				{
					// e.printStackTrace();

					// e.getMessage());
					throw new ComponentException(e.getMessage());
				}
			}
		}
		else
		{
			try
			{
				
			
				result = method.invoke(realObject, args);
				if (method.isAnnotationPresent(EventPublisher.class))
				{
					
					EventPublisher publishTopic = method.getAnnotation(EventPublisher.class);
					JAAPRuntime.FireEvent(proxyObject, publishTopic.topic(), args, PublicationScope.Global);
				}
				else
				{
					JAAPRuntime.FireEvent(proxyObject, TopicConstants.topic, null, PublicationScope.Global);
				}
			}
			catch (Exception e)
  			{
				//This code is added to handle the exception coming from the tool.as after throwing the error the return object was coming null.
				if(realObject instanceof IWorkflowTool)
				{
				   if(result==null)
				   {
					result=(Object)args[0];
				   }
				   else
				   {
					   IComponent component = (IComponent) realObject;
						IMaintenanceData maintenance = new MaintenanceData();
						IDefect defect = new Defect();
						defect.setDefect_type(e.getMessage());
						maintenance.setDefect(defect);
						component.maintenance(maintenance);
				   }
				}
				else if (realObject instanceof IComponent)
				{
					IComponent component = (IComponent) realObject;
					IMaintenanceData maintenance = new MaintenanceData();
					IDefect defect = new Defect();
					defect.setDefect_type(e.getMessage());
					maintenance.setDefect(defect);
					component.maintenance(maintenance);
				}
				// throw e;
			}
		}

		return result;
	}
}
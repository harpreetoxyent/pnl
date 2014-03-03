package com.oxymedical.hic.application.eventmanagement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.oxymedical.component.baseComponent.annotations.EventPublisher;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenanceSubscriber;
import com.oxymedical.core.constants.TopicConstants;
import com.oxymedical.hic.application.KernelContext;


public class EventInspector 
{
	private EventTopic topic = null;
	//public TopicConstants topicConstants = null;
	public void Register(Object item, KernelContext kernelContext)
	{
		ProcessPublishSubscribe(item, kernelContext, true);
	}
	private void ProcessPublishSubscribe(Object item, 
												KernelContext kernelContext,
												boolean register)
	{
		Class classDefinition = item.getClass();
		Method[] listMethods = item.getClass().getMethods();
		for(Method methodInfo:listMethods)
		{
			//Is publication
			if(methodInfo.isAnnotationPresent(EventPublisher.class))
			{
				//Prototype only supports one publish annotation
				//per method. In future one method/class can 
				//publish multiple events
				EventPublisher publishTopic = 
					methodInfo.getAnnotation(EventPublisher.class);
	            if (publishTopic != null)
	            {
					HandlePublisher(item, register, 
									methodInfo, 
									publishTopic, 
									kernelContext);
	            }
			}
			//Is subscription
			if(methodInfo.isAnnotationPresent(EventSubscriber.class))
			{
				//Prototype only supports one publish annotation
				//per method. In future one method/class can 
				//publish multiple events
				EventSubscriber subscriberTopic = 
					methodInfo.getAnnotation(EventSubscriber.class);
	            if (subscriberTopic != null)
	            {
					HandleSubscriber(item, register, 
									 methodInfo, 
									 subscriberTopic,
									 kernelContext);
	            }
			}
			if(methodInfo.isAnnotationPresent(MaintenancePublisher.class))
			{
				//Prototype only supports one publish annotation
				//per method. In future one method/class can 
				//publish multiple events
				MaintenancePublisher publishTopic = 
					methodInfo.getAnnotation(MaintenancePublisher.class);
	            if (publishTopic != null)
	            {
					HandlePublisher(item, register, 
									methodInfo, 
									publishTopic, 
									kernelContext);
	            }
			}
			//Is subscription
			if(methodInfo.isAnnotationPresent(MaintenanceSubscriber.class))
			{
				//Prototype only supports one publish annotation
				//per method. In future one method/class can 
				//publish multiple events
				MaintenanceSubscriber subscriberTopic = 
					methodInfo.getAnnotation(MaintenanceSubscriber.class);
	            if (subscriberTopic != null)
	            {
					HandleSubscriber(item, register, 
									 methodInfo, 
									 subscriberTopic,
									 kernelContext);
	            }
			}
			Annotation[] declaredAnnt = methodInfo.getDeclaredAnnotations();
		}
	}
	
	private void HandlePublisher(Object item, 
			boolean register, 
			Method methodInfo,
			EventPublisher attr,
			KernelContext kernelContext)
	{
		String publishTopic = null;
		//publishTopic = topicConstants.topic;
		publishTopic = attr.topic();
		topic = kernelContext.getEventTopic(publishTopic);
		if (register)
		{
			if(topic == null)
			{
				topic = new EventTopic(publishTopic,true);
			}
			topic.addPublication(item, methodInfo.getName(),  PublicationScope.Global);
			kernelContext.addEventTopic(publishTopic, topic);
		}	
		else
		{
			topic.removeSubscription(item, methodInfo.getName());
		}
	}
	private void HandlePublisher(Object item, 
			boolean register, 
			Method methodInfo,
			MaintenancePublisher attr,
			KernelContext kernelContext)
	{
		String publishTopic = null;
		publishTopic = TopicConstants.topic;
		 
		topic = kernelContext.getEventTopic(publishTopic);
		if (register)
		{
			if(topic == null)
			{
				topic = new EventTopic(publishTopic,true);
			}
			topic.addPublication(item, methodInfo.getName(),  PublicationScope.Global);
			kernelContext.addEventTopic(publishTopic, topic);
		}	
		else
		{
			topic.removeSubscription(item, methodInfo.getName());
		}
	}
	
	private void HandleSubscriber(Object item, 
			  boolean register, 
		      Method methodInfo,
		      EventSubscriber attr,
			  KernelContext kernelContext)
	{
		String subscriberTopic = null;
		//subscriberTopic = topicConstants.topic;
		subscriberTopic = attr.topic();
		topic = kernelContext.getEventTopic(subscriberTopic);
		if (register)
		{
			if(topic == null)
			{
			topic = new EventTopic(subscriberTopic,true);
			}
		Class[] parameterType = methodInfo.getParameterTypes();
		topic.addSubscription(item, methodInfo.getName(),  parameterType);
		kernelContext.addEventTopic(subscriberTopic, topic);
		}	
		else
		{
			topic.removeSubscription(item, methodInfo.getName());
		}
	}
	
	private void HandleSubscriber(Object item, 
								  boolean register, 
							      Method methodInfo,
							      MaintenanceSubscriber attr,
								  KernelContext kernelContext)
	{
		String subscriberTopic = null;
		subscriberTopic = TopicConstants.topic;
		topic = kernelContext.getEventTopic(subscriberTopic);
		if (register)
		{
			if(topic == null)
			{
				topic = new EventTopic(subscriberTopic,true);
			}
			Class[] parameterType = methodInfo.getParameterTypes();
			topic.addSubscription(item, methodInfo.getName(),  parameterType);
			kernelContext.addEventTopic(subscriberTopic, topic);
		}	
		else
		{
			topic.removeSubscription(item, methodInfo.getName());
		}
	}
	
	public Method getMethodInfo(Object subscriber, String handlerMethodName,Class[] parameterTypes) throws NoSuchMethodException 
	{
		return subscriber.getClass().getMethod(handlerMethodName,parameterTypes );
	}

	
}

package com.oxymedical.framework.objectbroker.strategies.Attribute;

import java.lang.reflect.Field;

import org.apache.log4j.Category;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.InjectProxy;
import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolver;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;
import com.oxymedical.framework.objectbroker.annotations.InjectNewType;
import com.oxymedical.hic.application.strategies.proxypattern.ProxyInvocationHandler;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class AttributeSetterStrategy extends BuilderStrategy
{
	static Category cat = Category.getInstance("ObjectBuilder");
	public AttributeSetterStrategy()
	{
		cat.info("AttributeSetterStrategy Called");
	}
	@Override
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("AttributeSetterStrategy Buildup called");
		if (existing != null)
			injectProperties(context, typeToBuild, existing, idToBuild);
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}
	private void injectProperties(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		if (existing == null)
			return;		
		cat.info("Existing object not null so setting properties");
		IAttributeSetterPolicy attributePolicy = context.getPolicies().get(AttributeSetterPolicy.class, typeToBuild, idToBuild);
		cat.info("Check if object has Singleton Policy");
		if (attributePolicy == null)
			return;	
		if(existing instanceof IComponent)
		{	
			if (attributePolicy != null )
			{
				for (Field fieldInfo : attributePolicy.getAttribute().values())
				{
					if (fieldInfo != null)
					{
						Field attributeField;
						Class buildType = existing.getClass();
						try 
						{
							Class clzType = fieldInfo.getType();
							String idComopnent = null;
							if(fieldInfo.isAnnotationPresent(InjectNewType.class))
							{
								System.out.println("--------------Inside InjectNewType------idToBuild="+idToBuild+" typeToBuild="+typeToBuild);
								InjectNewType injectNewTypeClass = fieldInfo.getAnnotation(InjectNewType.class);
								try
								{
									clzType = Class.forName(injectNewTypeClass.implementationClass());
								}
								catch (ClassNotFoundException e)
								{
									cat.error("ClassNotFoundException for class - ".concat(injectNewTypeClass.implementationClass()));
								}
							}
							else if(fieldInfo.isAnnotationPresent(InjectProxy.class))
							{
								InjectProxy injectproxyClass = fieldInfo.getAnnotation(InjectProxy.class);
								
								try
								{
									clzType = Class.forName(injectproxyClass.implementationClass());
									idComopnent = injectproxyClass.idComponent();
									
								}
								catch (ClassNotFoundException e)
								{
									cat.error("ClassNotFoundException for injectproxyClass class - ".concat(injectproxyClass.implementationClass()));
								}
							}
							attributeField = buildType.getField(fieldInfo.getName());
/*							Object attributeValue =  new DependencyResolver(context).Resolve(
									fieldInfo.getType(),fieldInfo.getName());		
*/							Object attributeValue =  null;
							if(fieldInfo.isAnnotationPresent(InjectNewType.class) ||
									fieldInfo.isAnnotationPresent(InjectNew.class))
							{
								attributeValue = new DependencyResolver(context).Resolve(
										clzType, fieldInfo.getName());
							}
							else if(fieldInfo.isAnnotationPresent(InjectProxy.class))
							{
								
								attributeValue = new DependencyResolver(context).Resolve(
										clzType, idComopnent);
								attributeValue = java.lang.reflect.Proxy.newProxyInstance(
										attributeValue.getClass().getClassLoader(),
										attributeValue.getClass().getInterfaces(),
					          	    new ProxyInvocationHandler(attributeValue));
							}
		

							cat.info("######!!!!!attributeField is"+attributeField.getName() +"exception ="+attributeField.getType());
							cat.info("######!!!!!attributeValue is"+attributeValue);
							attributeField.set(existing, attributeValue);
							
						}
						catch (NoSuchFieldException e) 
						{
					          e.printStackTrace();
					    }					
						catch (IllegalAccessException e) 
						{
							  e.printStackTrace();
						}
						catch (IllegalArgumentException e) 
						{
							cat.info("----IllegalArgumentException Caught for ---------"+existing);  
							e.printStackTrace();
						}						
					}
				}
			}
		}
		else
		{
			return;
		
		}
	}
}

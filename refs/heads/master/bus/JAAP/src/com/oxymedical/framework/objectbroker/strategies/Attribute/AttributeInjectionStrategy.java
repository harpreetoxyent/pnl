package com.oxymedical.framework.objectbroker.strategies.Attribute;

import java.lang.reflect.Field;

import org.apache.log4j.Category;

import com.oxymedical.component.baseComponent.annotations.InjectProxy;
import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;
import com.oxymedical.framework.objectbroker.annotations.InjectNewType;





/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class AttributeInjectionStrategy extends BuilderStrategy {
	
	static Category cat = Category.getInstance("ObjectBuilder");
	
	public AttributeInjectionStrategy()
	{
		//logger.log(0,"AttributeInjectionStrategy Constructor Called");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object BuildUp(IBuilderContext context, Class typeToBuild,
			Object existing, String idToBuild) 
	{
		cat.info("InjectNew Build Up Called");	
		if (typeToBuild == null || existing == null)
			return null;
		
		Field[] listFields = typeToBuild.getFields();
		if (listFields != null || listFields.length > 0) 
		{
			//logger.log(0,"[existing.getClass]".concat(existing.getClass().getName()).concat("; [typeToBuild]").concat(typeToBuild.getName()).concat("; [idToBuild]").concat(idToBuild));
			IAttributeSetterPolicy attributePolicy = context.getPolicies().get(AttributeSetterPolicy.class, 
					   existing.getClass(), 
					   idToBuild);
			if (attributePolicy == null)
			{
				attributePolicy = new AttributeSetterPolicy();
				context.getPolicies().set(attributePolicy, typeToBuild, idToBuild);
			}
			for(Field fieldInfo:listFields)
			{
				if (fieldInfo.isAnnotationPresent(InjectNew.class) || 
						fieldInfo.isAnnotationPresent(InjectNewType.class) ||
							fieldInfo.isAnnotationPresent(InjectProxy.class)) 
				{
					String injectedPropertyId = fieldInfo.getName();
					if(!attributePolicy.getAttribute().contains(injectedPropertyId))
					{
						attributePolicy.getAttribute().put(injectedPropertyId, fieldInfo);
					}
				}
			}
		}
		cat.info("InjectNew Buildup called");
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}
}

package com.oxymedical.framework.objectbroker;
import org.apache.log4j.Category;

import com.oxymedical.framework.objectbroker.strategies.CreationStrategy;
import com.oxymedical.framework.objectbroker.strategies.Attribute.AttributeInjectionStrategy;
import com.oxymedical.framework.objectbroker.strategies.Attribute.AttributeSetterStrategy;
import com.oxymedical.framework.objectbroker.strategies.method.MethodExecutionStrategy;
import com.oxymedical.framework.objectbroker.strategies.singleton.SingletonStrategy;
import com.oxymedical.hic.application.strategies.EventBrokerStrategy;




/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class Builder extends BuilderBase 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	public Builder()
	{
			cat.info("Builder Constructor Called");
			this.getStrategies().AddNew(new SingletonStrategy(), BuilderStage.PreCreation.toString());
			this.getStrategies().AddNew(new AttributeInjectionStrategy(), BuilderStage.Initialization.toString());
			this.getStrategies().AddNew(new CreationStrategy(), BuilderStage.Creation.toString());
			this.getStrategies().AddNew(new MethodExecutionStrategy(), BuilderStage.Initialization.toString());
			this.getStrategies().AddNew(new AttributeSetterStrategy(), BuilderStage.Initialization.toString());
			this.getStrategies().AddNew(new EventBrokerStrategy(), BuilderStage.Initialization.toString());
			cat.info("Builder Constructor Ended");
			/*
			Strategies.AddNew<ConstructorReflectionStrategy>(BuilderStage.PreCreation);
			Strategies.AddNew<PropertyReflectionStrategy>(BuilderStage.PreCreation);
			Strategies.AddNew<MethodReflectionStrategy>(BuilderStage.PreCreation);
			
			Strategies.AddNew<MethodExecutionStrategy>(BuilderStage.Initialization);
			Strategies.AddNew<BuilderAwareStrategy>(BuilderStage.PostInitialization);

			Policies.SetDefault<ICreationPolicy>(new DefaultCreationPolicy());
			*/
	}
}

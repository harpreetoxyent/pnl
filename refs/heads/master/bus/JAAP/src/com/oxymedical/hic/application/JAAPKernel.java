package com.oxymedical.hic.application;

import org.apache.log4j.Category;

import com.oxymedical.component.baseComponent.annotations.EventPublisher;
import com.oxymedical.core.constants.TopicConstants;
import com.oxymedical.core.hicUtil.TransContainer;
import com.oxymedical.framework.objectbroker.Builder;
import com.oxymedical.framework.objectbroker.BuilderStage;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolutionLocatorKey;
import com.oxymedical.framework.objectbroker.Lifetime.LifetimeContainer;
import com.oxymedical.framework.objectbroker.Location.Locator;
import com.oxymedical.framework.objectbroker.exception.ObjectBrokerException;
import com.oxymedical.framework.objectbroker.strategies.schedular.SchedularStrategy;
import com.oxymedical.framework.objectbroker.strategies.singleton.SingletonPolicy;
import com.oxymedical.framework.registry.Registry;
import com.oxymedical.hic.application.strategies.ComponentConfigSetterStrategy;
import com.oxymedical.hic.application.strategies.proxypattern.ProxyPatternStrategy;




@SuppressWarnings("deprecation")
public class JAAPKernel 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	private Builder builder = null;
	private Locator locator = null;
	private Registry registry = null;
	private KernelContext kernelContext = null;
	private static JAAPKernel kernel = null;
	

	public static JAAPKernel getInstance()
	{
		if(kernel == null)
		{
			kernel = new JAAPKernel();
			kernel.builder = createBuilder();
			kernel.locator = createLocator();
			kernel.registry = new Registry();
			kernel.kernelContext = kernelContextBuildUp();			
			kernel.locator.add(new DependencyResolutionLocatorKey(TransContainer.class, "containerSettings"), kernel.registry.getContainerSettings());

			return kernel;
		}
		else
		{
			return kernel;
		}
	}

	/**
	 * The method builds a builder and adds an application strategy to builder
	 */
	private static Builder createBuilder()
	{
		Builder builder = new Builder();
		builder.getStrategies().AddNew(new SchedularStrategy(), BuilderStage.PostInitialization.toString());
		builder.getStrategies().AddNew(new ComponentConfigSetterStrategy(), BuilderStage.PostInitialization.toString());
		// The below strategy has to be always the last in HIC sequence
		builder.getStrategies().AddNew(new ProxyPatternStrategy(), BuilderStage.PostInitialization.toString());
	//	builder.getStrategies().AddNew(new InjectionStrategy(), BuilderStage.PostInitialization.toString());

		return builder;
	}
	private JAAPKernel()
	{
		cat.info("******HIC Bus Constructor Called****");
		cat.info("******HIC Bus Constructor Ended****");						

	}
	public Builder getBuilder()
	{
		return kernel.builder;
	}
	public Locator getLocator() 
	{
		return kernel.locator;
	}
	private static Locator createLocator()
	{
		Locator locator = new Locator();
		LifetimeContainer lifetime = new LifetimeContainer();
		locator.add(LifetimeContainer.class, lifetime);
		return locator;
	}
	public static KernelContext getKernelContext()
	{
		KernelContext kernelContext = null;
		cat.info("Getting kernel Context");
		if(kernel.locator != null)
		{
			cat.info("Retrieve kernel Context from Locator");
			kernelContext = (KernelContext)kernel.locator.Get(new
				DependencyResolutionLocatorKey(KernelContext.class, ""));
		}
		cat.info("Return ArgusKernel Context"+kernelContext);
		return kernelContext;
	}
	private static KernelContext kernelContextBuildUp()
	{
		KernelContext kernelContext = null;
		try
		{
			cat.info("Inside kernelContextBuildUp");
			kernel.builder.getPolicies().set(new SingletonPolicy(true),KernelContext.class, "");
			Object obj = kernel.builder.BuildUp(KernelContext.class,kernel.locator, "", null,null);
			cat.info("Building kernel Context is successful");
			kernelContext = (KernelContext)obj;
			kernelContext.initializeKernelContext(kernel.builder,kernel.locator);
		}
		catch(ObjectBrokerException e)
		{
			cat.info("Building Kernel Context Failed");
		}
		return kernelContext;
	}
	public Registry getRegistry()
	{
		return registry;
	}	
	
	@EventPublisher(topic = TopicConstants.billingTracking)
	public void eventPublisherMethod()
	{
	
	}
	
	@EventPublisher(topic = TopicConstants.persistModuleXMLValues)
	public void persistModuleXMLPublisherMethod()
	{
	
	}
	
	@EventPublisher(topic = TopicConstants.persistStrategyXMLValues)
	public void persistStrategyXMLPublisherMethod()
	{
	
	}
	@EventPublisher(topic = TopicConstants.auditLogging)
	public void logAuditDetailsPublisherMethod()
	{
	
	}


}

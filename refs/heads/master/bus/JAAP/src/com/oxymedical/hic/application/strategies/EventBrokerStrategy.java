package com.oxymedical.hic.application.strategies;

import org.apache.log4j.Category;

import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.framework.objectbroker.Helper.DependencyResolutionLocatorKey;
import com.oxymedical.hic.application.KernelContext;
import com.oxymedical.hic.application.eventmanagement.EventInspector;






@SuppressWarnings("deprecation")
public class EventBrokerStrategy  extends BuilderStrategy
{
	static Category cat = Category.getInstance("ObjectBuilder");
	public EventBrokerStrategy()
	{
		cat.info("EventBrokerStrategy Constructor Called");
	}
	@Override
	public Object BuildUp(IBuilderContext context, Class typeToBuild,
			Object existing, String idToBuild) 
	{
		cat.info("EventBroker Build Up Called");
		KernelContext kernelContext = getKernelContext(context, existing);
		cat.info("kernelContext ="+kernelContext.toString());
		EventInspector eventInspector = new EventInspector();
		cat.info("Register the new class's subscriber and publisher");
		eventInspector.Register(existing,kernelContext);
		cat.info("Call Parent buildup");
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}

	public Object TearDown(IBuilderContext context, Object item) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	private KernelContext getKernelContext(IBuilderContext context, 
											Object item)
	{
		cat.info("getKernelContext Called");
		cat.info("Context.Locator Size ="+context.getLocator().getCount());
		return context.getLocator().Get(new
				DependencyResolutionLocatorKey(KernelContext.class, ""));
	}
}

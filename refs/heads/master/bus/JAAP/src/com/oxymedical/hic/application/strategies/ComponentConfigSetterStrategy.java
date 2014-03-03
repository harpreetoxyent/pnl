package com.oxymedical.hic.application.strategies;

import org.apache.log4j.Category;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.hic.application.policies.ComponentConfigurationPolicy;
import com.oxymedical.hic.application.policies.IComponentConfigurationPolicy;

@SuppressWarnings("deprecation")
public class ComponentConfigSetterStrategy extends BuilderStrategy
{
	static Category cat = Category.getInstance("ComponentConfigSetterStrategy");
	public ComponentConfigSetterStrategy()
	{
		cat.info("ComponentConfigSetterStrategy Called");
	}
	@Override
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("ComponentConfigSetterStrategy Buildup called");		
		if (existing != null)
			injectConfiguration(context, typeToBuild, existing, idToBuild);
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}
	private void injectConfiguration(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		if (existing == null)
			return;		
		IComponentConfigurationPolicy compConfigPolicy = context.getPolicies().get(ComponentConfigurationPolicy.class, typeToBuild, idToBuild);
		cat.info("Check if object has ComponentConfiguration Policy");
		if (compConfigPolicy == null)
			return;	
		if (compConfigPolicy != null )
		{
			try 
			{
				if(existing instanceof IComponent){
					IComponent componentInstance =  (IComponent)existing;		
					componentInstance.start(compConfigPolicy.getConfigurationData());
				}
			}				
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}

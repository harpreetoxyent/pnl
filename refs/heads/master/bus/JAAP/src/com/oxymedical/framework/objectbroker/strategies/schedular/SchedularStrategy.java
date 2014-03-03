package com.oxymedical.framework.objectbroker.strategies.schedular;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import org.apache.log4j.Category;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.core.hicUtil.IModuleDescriptor;
import com.oxymedical.core.schedular.ISchedularData;
import com.oxymedical.framework.objectbroker.BuilderStrategy;
import com.oxymedical.framework.objectbroker.IBuilderContext;
import com.oxymedical.hic.application.JAAPKernel;
import com.oxymedical.hic.application.JAAPRuntime;

/**
 * Strategy for handling Scheduled tasks.
 * 
 * @author hs
 */
public class SchedularStrategy extends BuilderStrategy 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	
	public SchedularStrategy()
	{
		cat.info("Inside SchedularStrategy");
	}
	
	public Object BuildUp(IBuilderContext context, Class typeToBuild, Object existing, String idToBuild)
	{
		cat.info("Inside SchedularStrategy - Starting BuildUp");
		
		// Work on if a valid component id is present.
		if ((idToBuild != null) && (idToBuild.length() > 0))
			createSchedular(typeToBuild, existing, idToBuild);
		return super.BuildUp(context, typeToBuild, existing, idToBuild);
	}
	
	private void createSchedular(Class typeToBuild, Object existing, String compId)
	{
		if (existing instanceof IComponent)
		{
			IModuleDescriptor moduleDescriptor =  JAAPKernel.getInstance().getRegistry().getModuleDescriptorList().get(compId); //  JAAPRuntime.getComponentDesc(compId);
			if (moduleDescriptor == null) return;

			List<ISchedularData> schedList = moduleDescriptor.getSchedularList();
			if (schedList == null) return;

			cat.info("createSchedular - schedList size: " + schedList.size());
			for (Iterator iter = schedList.iterator(); iter.hasNext();)
			{
				ISchedularData sched = (ISchedularData) iter.next();
				if (sched == null) continue;

				cat.info("sched Interval: " + sched.getInterval());
				cat.info("sched SubscribedMethod: " + sched.getSubscribedMethod());

				SchedularTask task = new SchedularTask(sched.getSubscribedMethod());
				Timer timer = new Timer();

				/*
				 * The task should start only after some stipulated time
				 * which is 300 seconds in this case. This would take care
				 * of the delay due to deployment and other reasons at the
				 * start of application.
				 */
				long delay = sched.getInterval();
				if (delay < 300) delay = 300;
				
				// Run after every "interval" seconds.
				timer.schedule(task, delay*1000, sched.getInterval()*1000);
			}
		}
	}

}

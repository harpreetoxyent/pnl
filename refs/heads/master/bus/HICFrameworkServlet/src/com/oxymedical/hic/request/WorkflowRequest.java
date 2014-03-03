package com.oxymedical.hic.request;

import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;

public class WorkflowRequest implements Runnable {

	private IHICData hicData;
	private IApplication application;
	
	public IHICData getHicData() {
		return hicData;
	}
	public void setHicData(IHICData hicData) {
		this.hicData = hicData;
	}
	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
		this.application = application;
	}
	public WorkflowRequest(IHICData hicData,IApplication application)
	{
		this.hicData= hicData;
		this.application = application;
	}
	public void intializeWorkFlow()
	{
//		IWorkflowComponent workflowComp
//		= (IWorkflowComponent)NOLISRuntime.getComponent(IComponentIdConstants.WORKFLOW_COMP_ID);
//		System.out.println("----Inside workflow workflowComp-----"+workflowComp+ " --this.application"+this.application);

		this.hicData.setApplication(this.application);
		try {
			NOLISRuntime.FireEvent("initalizeWorkFlow", new Object[]{hicData}, PublicationScope.Global);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		workflowComp.initalizeWorkFlow(this.hicData);
	}
	@Override
	public void run() 
	{
		try
		{
			System.out.println("----Inside workflow initiated thread-----");
			intializeWorkFlow();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}

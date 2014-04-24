package com.oxymedical.component.workflowComponent.workflowManager;

import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.containerInfo.Event;


public interface IWorkflowManager 
{
	public void startWorkflow(IApplication application) throws WorkflowComponentException;
	
	
}

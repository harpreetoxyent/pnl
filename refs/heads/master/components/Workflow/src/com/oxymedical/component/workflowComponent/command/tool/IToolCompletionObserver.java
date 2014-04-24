package com.oxymedical.component.workflowComponent.command.tool;

import com.oxymedical.component.workflowComponent.IWorkflowComponent;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;

public interface IToolCompletionObserver extends IObserver
{
	public IWorkflowComponent getWorkflowComponent();

	public void setCurrentEvent(NodeEvents currEvent);
	
	public void setCurrentNode(NodeObject currNode);
}

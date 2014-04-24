package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;


public interface IWorkflowCommand extends ICommand
{
	public void setFunction(String function);
	public void setCurrentNode(NodeObject currentNode);
	public void setNodeEvent(NodeEvents event);
}

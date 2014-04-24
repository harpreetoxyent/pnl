package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.command.tool.IObserver;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;

public interface IWorkflowToolCommand extends IWorkflowCommand
{
	public void setCurrentNode(NodeObject node);
	public void setObserver(IObserver observer);
}

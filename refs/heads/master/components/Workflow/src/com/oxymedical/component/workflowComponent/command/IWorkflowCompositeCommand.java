package com.oxymedical.component.workflowComponent.command;

import java.util.List;

import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;

public interface IWorkflowCompositeCommand extends ICommand
{
	public void setFunctionList(List<String> list);
	public void setNextNode(NodeObject node);
}
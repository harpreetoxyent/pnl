package com.oxymedical.component.workflowComponent.nodemanager;

import java.util.List;

import com.oxymedical.component.workflowComponent.command.tool.IObserver;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.workflowManager.WorkflowManager;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public interface INodeEventManager
{
	public IHICData executeNodeFunctions(NodeObject nodeObj, NodeEvents nodeEvent, IRouter router, 
			IHICData hicData, NodeObject nextNode, NodeObject currNode, 
			IObserver toolCompletionObserver, WorkflowManager workflowManagerObject) throws WorkflowComponentException;

	public IHICData executeFunctions(IRouter router, 
			IHICData hicData, NodeObject nextNode, List<String> functionList, 
			NodeObject currNode, IObserver toolCompletionObserver, NodeEvents nodeEvent) throws WorkflowComponentException;

}

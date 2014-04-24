package com.oxymedical.component.workflowComponent.nodemanager;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.command.IWorkflowCompositeCommand;
import com.oxymedical.component.workflowComponent.command.WorkflowCompositeCommand;
import com.oxymedical.component.workflowComponent.command.tool.IObserver;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeBuilder.IEventAttributes;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.workflowManager.WorkflowManager;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.router.IRouter;

public class NodeEventManager implements INodeEventManager
{

	@Override
	public IHICData executeFunctions(IRouter router, IHICData hicData,
			NodeObject nextNode, List<String> functionList, NodeObject currNode,
			IObserver toolCompletionObserver, NodeEvents nodeEvent) throws WorkflowComponentException
	{
		IWorkflowCompositeCommand compositeCom = new WorkflowCompositeCommand(router, hicData,
				nextNode, functionList, currNode, toolCompletionObserver, nodeEvent);
		compositeCom.execute();
		
		return compositeCom.getHICData();
	}


	@Override
	public IHICData executeNodeFunctions(NodeObject nodeObj, NodeEvents nodeEvent, IRouter router, IHICData hicData,
			NodeObject nextNode, NodeObject currNode, IObserver toolCompletionObserver, 
			WorkflowManager workflowManagerObject) throws WorkflowComponentException
	{
		List<String> functionList = getFunctionCall(nodeObj, nodeEvent);
		if (functionList == null) 
		{
			markCommandExecutionCompletion(hicData, currNode, nodeEvent);
			return hicData;
		}
		
		return executeFunctions(router, hicData,
				nextNode, functionList, currNode,
				toolCompletionObserver, nodeEvent);
	}
	
	
	
	public List<String> getFunctionCall(NodeObject nodeMgr, NodeEvents nodeEvent)
	{
		List<String> functionList = null;
		String functionStr = null;
		
		IEventAttributes events = nodeMgr.getEventAttributeObject();
		if (events != null)
		{
			switch (nodeEvent)
			{
			case ENTER_NODE: // On Enter
				functionStr = events.getOnEnter();
				break;

			case ON_NODE: // On Node
				functionStr = events.getOnNode();
				break;

			case EXIT_NODE: // On Exit
				functionStr = events.getOnExit();
				break;

			default:
				break;
			}
		}
		
		if ((functionStr != null) && (!functionStr.equals("")))
		{
			functionList = new ArrayList<String>();
			String[] functions = functionStr.trim().split(";");
			for (int counter = 0; counter < functions.length; counter++)
			{
				String function = functions[counter].trim();
				WorkflowComponent.log(0,"[Function on node] ".concat(nodeMgr.getNodeAttributes().getNodeId()).concat(" [is] ").concat(function));
				functionList.add(function);
			}
		}
		
		return functionList;
	}
	
	
	private IHICData markCommandExecutionCompletion(IHICData hicData, NodeObject currNode, NodeEvents event)
	{
		if (hicData.getData() == null)
		{
			IData data = new Data();
			hicData.setData(data);
		}
		if (hicData.getData().getWorkflowPattern() == null)
		{
			IWorkflowPattern wPatt = new WorkflowPattern();
			hicData.getData().setWorkflowPattern(wPatt);
		}
		
		hicData.getData().getWorkflowPattern().addWorkflowNodeEventStatus(currNode.getNodeAttributes().getNodeId() + "_" + event, true);
		return hicData;
	}

}

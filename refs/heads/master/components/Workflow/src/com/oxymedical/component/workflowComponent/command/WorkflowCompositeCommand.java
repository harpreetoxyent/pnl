package com.oxymedical.component.workflowComponent.command;

import java.util.List;

import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.command.tool.IObserver;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public class WorkflowCompositeCommand implements IWorkflowCompositeCommand
{
	List<String> functionList;
	IRouter router;
	IHICData data;
	NodeObject nextNode;
	NodeObject _currNode;
	IObserver _observer;
	NodeEvents _event;
	
	public WorkflowCompositeCommand(IRouter router, IHICData data, NodeObject nextNode, List<String> functions)
	{
		this(router, data, nextNode, functions, null, null, null);
	}
	
	public WorkflowCompositeCommand(IRouter router, IHICData data, NodeObject nextNode, List<String> functions, NodeObject currNode, IObserver observer, NodeEvents event)
	{
		this.router = router;
		this.data = data;
		this.nextNode = nextNode;
		this.functionList = functions;
		this._currNode = currNode;
		this._observer = observer;
		this._event = event;
	}
	
	public void setHICData(IHICData data)
	{
		this.data = data;
	}

	public IHICData getHICData()
	{
		return this.data;
	}

	public void setFunctionList(List<String> list)
	{
		this.functionList = list;
	}

	public void execute()
	{
		WorkflowComponent.log(0, "insidde the execute of exectue---");
		for (int i = 0; i < functionList.size(); i++)
		{
			String function = functionList.get(i);
			WorkflowComponent.log(0, "function is ---"+function);
			IWorkflowCommand command = CommandFactory.getWorkflowCommand(function);
			synchronized (command)
			{
				if (command != null)
				{
					command.setFunction(function);
					command.setHICData(data);
					command.setHICRouter(router);
					command.setNextNode(nextNode);
					
					command.setCurrentNode(_currNode);
					command.setNodeEvent(_event);
					
					if (command instanceof IWorkflowToolCommand)
					{
						// Specific to WorkflowTools. Would not be required for simple workflows.
						//((IWorkflowToolCommand)command).setCurrentNode(_currNode);
						((IWorkflowToolCommand)command).setObserver(_observer);
					}
					
					// Execute command.
					command.execute();
					this.data = command.getHICData();
				}
			}
		}
	}

	public void setHICRouter(IRouter router)
	{
		this.router = router;
	}

	public void setNextNode(NodeObject node)
	{
		this.nextNode = node;
	}
	
	public void setCurrentNode(NodeObject node)
	{
		this._currNode = node;
	}
	
	public void setObserver(IObserver o)
	{
		this._observer = o;
	}

}

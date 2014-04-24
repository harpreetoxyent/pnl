package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public class ChangeStatusCommand extends BaseCommand implements IWorkflowCommand
{
	private IRouter router;
	private IHICData data;
	private NodeObject nextNode;
	private String function;
	
	private NodeObject _currentNode;
	private NodeEvents _event;
	
	public void setFunction(String function)
	{
		this.function = function;
	}

	public void setHICRouter(IRouter router)
	{
		this.router = router;
	}

	public void setHICData(IHICData data)
	{
		this.data = data;
	}

	public IHICData getHICData()
	{
		return this.data;
	}

	public void setNextNode(NodeObject node)
	{
		this.nextNode = node;
	}

	public void execute()
	{
		String event = getEventName();
		this.data = updateHIC(this.data);
		
		if (event != null)
			executeEachFunction(event, this.data, router);
		
		markCommandExecutionCompletion(this.data, this._currentNode, this._event);
	}
	
	private String getEventName()
	{
		return "InvokeWorkflow";
	}
	
	private IHICData updateHIC(IHICData newDO)
	{
		String params = null;
		if (function.indexOf("(") > 0)
		{
			params = function.substring("changeDOStatus".length());
			params = (params.length() == function.length()) ? function.substring("setDataStatus".length()) : params;
			params = params.trim();
			params = params.substring(1, params.length()-1);;
			String[] param = params.split(",");
			if (param[0]!= null) newDO.getData().setStatus(param[0].trim().replaceAll("\"", ""));
			if (param[1]!= null) newDO.getData().getFormPattern().setFormId(param[1].trim().replaceAll("\"", ""));
			if (param[2]!= null) newDO.getData().getDataPattern().setDataPatternId(param[2].trim().replaceAll("\"", ""));
		}
		return newDO;
	}

	@Override
	public void setCurrentNode(NodeObject currentNode)
	{
		this._currentNode = currentNode;
	}

	@Override
	public void setNodeEvent(NodeEvents event)
	{
		this._event = event;
	}
}

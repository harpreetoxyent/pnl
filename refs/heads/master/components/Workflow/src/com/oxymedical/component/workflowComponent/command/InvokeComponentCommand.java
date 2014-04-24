package com.oxymedical.component.workflowComponent.command;

import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public class InvokeComponentCommand extends BaseCommand implements IWorkflowCommand
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
		if (event != null)
		{
			if (event.equals("moveForm"))
			{  
				IWorkflowCommand command = new MoveCommand();
				command.setFunction(function);
				command.setNextNode(nextNode);
				command.setHICData(data);
				command.setHICRouter(router);
				command.setCurrentNode(_currentNode);
				command.setNodeEvent(_event);
				command.execute();
				this.data = command.getHICData();
			}
			else
			{
				executeEachFunction(event, this.data, router);
				markCommandExecutionCompletion(this.data, this._currentNode, this._event);
			}
		}
	}
	
	
	private String getEventName()
	{
		String eventName = null;
		String params = null;
		if (function.indexOf("(") > 0)
		{
			params = function.substring("invokecomponent".length());
			params = params.trim();
			params = params.substring(1, params.length()-1);
			String[] param = params.split(",");
			eventName = param[1].trim().replaceAll("\"", "");
			if (!eventName.equals("moveForm"))
			{  
				this.data.getData().setRawData(param[3].trim().replaceAll("\"", ""));
				/*this.data = updateHICData(data, param[0].trim().replaceAll("\"", ""),
						param[1].trim().replaceAll("\"", ""),
						param[2].trim().replaceAll("\"", ""),
						param[3].trim().replaceAll("\"", ""));*/
			}
		}
		return eventName;
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

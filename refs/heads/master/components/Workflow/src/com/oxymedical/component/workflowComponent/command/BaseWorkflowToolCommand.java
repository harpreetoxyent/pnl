package com.oxymedical.component.workflowComponent.command;

import java.util.HashMap;

import com.oxymedical.component.workflowComponent.command.tool.IObserver;
import com.oxymedical.component.workflowComponent.command.tool.NodeDOQueue;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEvents;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.router.IRouter;

public abstract class BaseWorkflowToolCommand implements IWorkflowToolCommand
{
	static HashMap<NodeObject, NodeDOQueue> queues = new HashMap<NodeObject, NodeDOQueue>();
	IRouter _router;
	IHICData _data;
	NodeObject _nextNode;
	NodeObject _currNode;
	IObserver _observer;
	String _function;
	NodeEvents _event;
	
	
	public void setFunction(String function)	{	this._function = function;	}
	public void setHICRouter(IRouter router)	{	this._router = router;		}
	public void setHICData(IHICData data)		{	this._data = data;			}
	public void setNextNode(NodeObject node)	{	this._nextNode = node;		}
	public void setCurrentNode(NodeObject node)	{	this._currNode = node;		}
	public void setObserver(IObserver observer)	{	this._observer = observer;	}
	public void setNodeEvent(NodeEvents event)	{	_event = event;				}
	

	// This should not be usable for Tool commands since we are using Observer /
	// Observable pattern to inform Workflow component about completion of
	// thread.
	public IHICData getHICData()				{	return this._data;			}
}

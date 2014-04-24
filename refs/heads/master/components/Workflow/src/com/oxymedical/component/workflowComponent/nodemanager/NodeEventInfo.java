package com.oxymedical.component.workflowComponent.nodemanager;

import java.util.LinkedList;
import java.util.List;

import com.oxymedical.component.workflowComponent.WorkflowComponent;

public class NodeEventInfo
{
	private static List<NodeEvents> _executionOrder = null;
	private static List<NodeEvents> _eventList = null;
	private static NodeEvents _stopAt = NodeEvents.ON_NODE;
	
	public static List<NodeEvents> getExecutionOrder()
	{
		if (_executionOrder == null)
		{
			_executionOrder = new LinkedList<NodeEvents>();
			_executionOrder.add(NodeEvents.EXIT_NODE);
			_executionOrder.add(NodeEvents.ENTER_NODE);
			_executionOrder.add(NodeEvents.ON_NODE);
		}
		return _executionOrder;
	}
	
	public static List<NodeEvents> getEventsList()
	{
		if (_eventList == null)
		{
			_eventList = new LinkedList<NodeEvents>();
			for(int i=0;i <NodeEvents.values().length;i++)
			{
				_eventList.add(NodeEvents.values()[i]);
			}
		}
		return _eventList;
	}
	
	public static List<NodeEvents> getSuccessorExecutionEvents(NodeEvents nodeEvent)
	{
		return getSuccessorExecutionEvents(nodeEvent, true);
	}
	
	
	public static List<NodeEvents> getSuccessorExecutionEvents(NodeEvents nodeEvent, boolean includeCurrent)
	{
		List<NodeEvents> succEvent = new LinkedList<NodeEvents>();
		getEventsList();
		getExecutionOrder();
		for(int i=0; i < _eventList.size(); i++)
		{
			if (includeCurrent)
			{
				if ((_eventList.get(i).ordinal() >= nodeEvent.ordinal()) 
						&& (_eventList.get(i).ordinal() < _executionOrder.get(0).ordinal()))
				{
					succEvent.add(_eventList.get(i));
				}
			}
			else
			{
				if ((_eventList.get(i).ordinal() > nodeEvent.ordinal()) 
						&& (_eventList.get(i).ordinal() < _executionOrder.get(0).ordinal()))
				{
					succEvent.add(_eventList.get(i));
				}
			}
		}
		
		if (includeCurrent) if (succEvent.size() <= 0) succEvent.add(nodeEvent);
		return succEvent;
	}
	
	public static NodeEvents getSuccessorExecutionEvent(NodeEvents nodeEvent)
	{
		List<NodeEvents> succEvent = getSuccessorExecutionEvents(nodeEvent, true);
		if (succEvent.size() > 0) return succEvent.get(0);
		return null;
	}
	
	public static NodeEvents getDefaultExecutionStartEvent()
	{
		getExecutionOrder();
		return _executionOrder.get(0);
	}
	
	public static NodeEvents getExecutionStopEvent()
	{
		return _stopAt;
	}
	
	public static NodeEvents getStartEvent()
	{
		getEventsList();
		return _eventList.get(0);
	}
	
	public static NodeEvents getLastEvent()
	{
		getEventsList();
		return _eventList.get(_eventList.size()-1);
	}
	
	public static void main(String[] args)
	{
		/*List<NodeEvents> _executionOrder = NodeEventInfo.getExecutionOrder();
		WorkflowComponent.log(0, "\n\n\n");
		List<NodeEvents> _eventList = NodeEventInfo.getEventsList();*/
		
		List<NodeEvents> list = getSuccessorExecutionEvents(getDefaultExecutionStartEvent());
		for (int i = 0; i < list.size(); i++)
		{
			WorkflowComponent.log(0, list.get(i).toString());
		}
		WorkflowComponent.log(0, "\n\n****\n\n");
		list = getSuccessorExecutionEvents(NodeEvents.ENTER_NODE);
		for (int i = 0; i < list.size(); i++)
		{
			WorkflowComponent.log(0, list.get(i).toString());
		}
		WorkflowComponent.log(0, "\n\n**** **** **** ****\n\n");
		list = getSuccessorExecutionEvents(getDefaultExecutionStartEvent(), false);
		for (int i = 0; i < list.size(); i++)
		{
			WorkflowComponent.log(0, list.get(i).toString());
		}
		WorkflowComponent.log(0, "\n\n****\n\n");
		list = getSuccessorExecutionEvents(NodeEvents.ENTER_NODE, false);
		for (int i = 0; i < list.size(); i++)
		{
			WorkflowComponent.log(0, list.get(i).toString());
		}
	}
}

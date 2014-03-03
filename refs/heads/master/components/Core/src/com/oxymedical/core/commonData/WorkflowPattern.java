package com.oxymedical.core.commonData;

import java.util.Hashtable;

public class WorkflowPattern implements IWorkflowPattern
{
	private String _workflowPattern;
	private String _workflowNode;
	private String _workflowPreviousNode;
	private String _workflowNodeStatus;
	private Hashtable<String, Boolean> _workflowNodeEventStatus = new Hashtable<String, Boolean>();
	
	@Override
	public String getWorkflowNode()
	{
		return this._workflowNode;
	}


	@Override
	public String getWorkflowNodeStatus()
	{
		return this._workflowNodeStatus;
	}


	@Override
	public String getWorkflowPattern()
	{
		return this._workflowPattern;
	}


	@Override
	public String getWorkflowPreviousNode()
	{
		return this._workflowPreviousNode;
	}
	
	
	@Override
	public void setWorkflowNode(String workflowNode)
	{
		this._workflowNode = workflowNode;
	}


	@Override
	public void setWorkflowNodeStatus(String workflowNodeStatus)
	{
		this._workflowNodeStatus = workflowNodeStatus;
	}


	@Override
	public void setWorkflowPattern(String workflowPattern)
	{
		this._workflowPattern = workflowPattern;
	}
	
	
	@Override
	public void setWorkflowPreviousNode(String workflowPreviousNode)
	{
		this._workflowPreviousNode = workflowPreviousNode;
	}


	public Hashtable<String, Boolean> getWorkflowNodeEventStatus()
	{
		return _workflowNodeEventStatus;
	}


	public void addWorkflowNodeEventStatus(String nodeName, Boolean completed)
	{
		_workflowNodeEventStatus.put(nodeName, completed);
	}
	
	public void setWorkflowNodeEventStatus(Hashtable<String, Boolean> workflowNodeEventStatus)
	{
		_workflowNodeEventStatus = workflowNodeEventStatus;
	}
	
	@Override
	public Object clone()
	{
		IWorkflowPattern newWP = new WorkflowPattern();
		newWP.setWorkflowNode(_workflowNode);
		// newWP.setWorkflowNodeEventStatus((Hashtable<String, Boolean>)_workflowNodeEventStatus.clone());
		newWP.setWorkflowNodeStatus(_workflowNodeStatus);
		newWP.setWorkflowPattern(_workflowPattern);
		newWP.setWorkflowPreviousNode(_workflowPreviousNode);
		
		return newWP;
	}
}

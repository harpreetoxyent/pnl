package com.oxymedical.core.commonData;

import java.util.Hashtable;

public interface IWorkflowPattern extends Cloneable
{
	public String getWorkflowPattern();
	public String getWorkflowNode();
	public String getWorkflowNodeStatus();
	public String getWorkflowPreviousNode();
	public Hashtable<String, Boolean> getWorkflowNodeEventStatus();

	public void setWorkflowPattern(String workflowPattern);
	public void setWorkflowNode(String workflowNode);
	public void setWorkflowNodeStatus(String workflowNodeStatus);
	public void setWorkflowPreviousNode(String workflowPreviousNode);
	public void addWorkflowNodeEventStatus(String nodeName, Boolean completed);
	public void setWorkflowNodeEventStatus(Hashtable<String, Boolean> workflowNodeEventStatus);

	public Object clone();
}

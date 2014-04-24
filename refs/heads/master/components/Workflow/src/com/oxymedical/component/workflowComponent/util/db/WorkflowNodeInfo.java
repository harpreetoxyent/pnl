package com.oxymedical.component.workflowComponent.util.db;

public class WorkflowNodeInfo {
	int id;
	int workflowId;
	int nodeId;
	boolean isStartNode=false;
	String status=null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public boolean isStartNode() {
		return isStartNode;
	}
	public void setStartNode(boolean isStartNode) {
		this.isStartNode = isStartNode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}

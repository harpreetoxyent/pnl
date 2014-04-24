package com.oxymedical.component.workflowComponent.util.db;

public class NodeConnectorInfo {
	
	int id;
	String connectorName;
	
	String fromNodeId;
	String toNodeId;
	String workflowName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getConnectorName() {
		return connectorName;
	}
	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}
	
	public String getFromNodeId() {
		return fromNodeId;
	}
	public void setFromNodeId(String fromNodeId) {
		this.fromNodeId = fromNodeId;
	}
	public String getToNodeId() {
		return toNodeId;
	}
	public void setToNodeId(String toNodeId) {
		this.toNodeId = toNodeId;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	

}

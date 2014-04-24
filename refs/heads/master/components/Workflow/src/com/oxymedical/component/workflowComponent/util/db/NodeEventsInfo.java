package com.oxymedical.component.workflowComponent.util.db;

public class NodeEventsInfo {
	
	int id;
	int workflowNodeInfoId;
	String params=null;
	String eventType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public int getWorkflowNodeInfoId() {
		return workflowNodeInfoId;
	}
	public void setWorkflowNodeInfoId(int workflowNodeInfoId) {
		this.workflowNodeInfoId = workflowNodeInfoId;
	}
	

}

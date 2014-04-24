package com.oxymedical.component.workflowComponent.util.db;

public class WorkFlowInfo {

	

	int id=0;
	String workFlowName=null;
	String deleted="0";
	boolean isVisual=false;
	boolean createdFromUI=false;
	int visualizerid=0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWorkFlowName() {
		return workFlowName;
	}
	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public boolean isVisual() {
		return isVisual;
	}
	public void setVisual(boolean isVisual) {
		this.isVisual = isVisual;
	}
	public boolean isCreatedFromUI() {
		return createdFromUI;
	}
	public void setCreatedFromUI(boolean createdFromUI) {
		this.createdFromUI = createdFromUI;
	}
	public int getVisualizerid() {
		return visualizerid;
	}
	public void setVisualizerid(int visualizerid) {
		this.visualizerid = visualizerid;
	}
}

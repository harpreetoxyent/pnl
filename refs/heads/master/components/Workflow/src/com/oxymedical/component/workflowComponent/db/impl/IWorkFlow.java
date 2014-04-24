package com.oxymedical.component.workflowComponent.db.impl;

import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;


public interface IWorkFlow {
	
	public com.oxymedical.component.appadmin.model.resources_app.Workflowinfo addWorkFlow(java.lang.String workflowName,
			java.lang.String deleted,int id,boolean isVisual,boolean createdFromUI,int visualizerid)
	throws WorkflowComponentException;
	public Workflowinfo getWorkFlowBasedOnId(int id) throws WorkflowComponentException;
	public Workflowinfo getWorkFlowBasedOnName(String workflowName) throws WorkflowComponentException;
	public List<Workflowinfo> getListOfWorkFlowList() throws WorkflowComponentException;
	public void deleteWorkFlowBasedOnID(int workflowId) throws WorkflowComponentException;
}

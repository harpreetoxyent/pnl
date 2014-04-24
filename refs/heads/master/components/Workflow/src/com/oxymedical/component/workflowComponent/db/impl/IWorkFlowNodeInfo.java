package com.oxymedical.component.workflowComponent.db.impl;

import java.util.List;


import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;

public interface IWorkFlowNodeInfo {

	public com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo addWorkFlowNodeInfo(int workflowId,int nodeId,java.lang.String status, boolean isStartNode) 
	throws WorkflowComponentException;
	public List<Workflownodeinfo> getWorkflowNodesInfoListBasedOnWorkflowId(int id) throws WorkflowComponentException;
}

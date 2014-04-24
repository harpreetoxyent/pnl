package com.oxymedical.component.workflowComponent.db.impl;

import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodeevent;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;

public interface INodeEvent {
	
	public com.oxymedical.component.appadmin.model.resources_app.Nodeevent addNodeEvent(int nodeId, java.lang.String params,
			java.lang.String eventType) throws WorkflowComponentException;
	public List<Nodeevent> getNodeEventBasedOnNodeId(int nodeId) throws WorkflowComponentException;

}

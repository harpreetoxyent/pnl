package com.oxymedical.component.workflowComponent.db.impl;

import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodeconnector;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;

public interface INodeConnectorInfo 
{

	public com.oxymedical.component.appadmin.model.resources_app.Nodeconnector addNodeConnector(java.lang.String connectorName,
			java.lang.String fromNodeId ,java.lang.String toNodeId,java.lang.String workflowName) throws WorkflowComponentException;
	public List<Nodeconnector> getNodeConnectorsBasedOnWorkFlow(int workflowId);
}

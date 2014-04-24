package com.oxymedical.component.workflowComponent.nodeInfo;

import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.INodeConnector;
import com.oxymedical.component.workflowComponent.operation.db.NodeCollectionOperation;
import com.oxymedical.component.workflowComponent.operation.db.NodeConnectorCollectionOperation;

public class WorkflowNodeCollectionHelper
{
	public static WorkflowNodeCollection createWorkFlowCollection()
	{
		WorkflowNodeCollection flowCollection = null;
		NodeCollectionOperation nodeCollectionOperation = new NodeCollectionOperation();
		
		Hashtable<String, Hashtable<String, NodeObject>> nodeCollection 
			= nodeCollectionOperation.getNodeCollectionObjectFromDatabase();
		
		NodeConnectorCollectionOperation nodeConnectorCollectionOperation 
			= new NodeConnectorCollectionOperation();
		
		Hashtable<String, List<INodeConnector>> connectorCollection 
			= nodeConnectorCollectionOperation.getNodeConnectorCollectionObject();
		
		if (connectorCollection != null && connectorCollection.size() > 0)
		{
			if (flowCollection == null)
				flowCollection = new WorkflowNodeCollection();
			flowCollection.setConnectorCollection(connectorCollection);
		}
		if (nodeCollection != null && nodeCollection.size() > 0)
		{
			if (flowCollection == null)
				flowCollection = new WorkflowNodeCollection();
			flowCollection.setNodeCollection(nodeCollection);
		}

		return flowCollection;
	}
}

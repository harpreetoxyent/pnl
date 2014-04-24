package com.oxymedical.component.workflowComponent.dataObjectManager;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.core.commonData.IHICData;

public interface IDataObjectManager
{
//	public NodeObject setActiveNodeFlowObject(WorkflowNodeCollection workflowCollection) throws WorkflowComponentException;
	
	// Checks if incoming DO is present on any existing node. If yes, returns the corresponding DO that matches the incoming DO
	public Hashtable<String, Hashtable<String, List<IHICData>>> getMatchingDataObject(IHICData incomingDataObject);
	public List<IHICData> getMatchingDataObjectForWorkflowNode(String workflowIdKey, String nodeIdKey, IHICData incomingDataObject);
	public IHICData getMatchingDataObjectForUser(IHICData incomingDataObject);
	public List<IHICData> getAllMatchingDataObjectForUser(IHICData incomingDataObject);
	public boolean isDOPresentOnWorkflowNode(String workflowIdKey, String nodeIdKey, IHICData incomingDataObject);
	
	/*public IHICData getDataObjectFromWorkflowCollection(WorkflowNodeCollection workflowCollection, IHICData incomingDataObject);
	public IHICData getDataObjectFromNodeCollection(Collection<NodeManager> nodeCollection, IHICData incomingDataObject);
	public IHICData getDataObjectFromNode(NodeManager node, IHICData incomingDataObject);*/
	
	/*public NodeManager getNodeFlowObject(WorkflowNodeCollection workflowCollection, IHICData incomingDataObject) throws WorkflowComponentException;*/

	public void storeDataObjectOnNode(String workflowId, String nodeId, IHICData dataObject);
	
	public void removeDataObjectFromNode(String workflowId, String nodeId, IHICData dataObject);
	public void removeDataObjectFromNode(String workflowId, String nodeId, IHICData dataObject, boolean performDbOperation);
	
	public void updateDataObjectFromNodeOnGivenStatus(String workflowId, String nodeId, IHICData dataObject,String status);
	
	/*public void addDataObjectToDBQueue(String workflowId, String nodeId, IHICData dataObject);*/
	public void appendToDataObjectMetaData(String workflowId, String nodeId, IHICData dataObject,
			Dataobject doDb) throws WorkflowComponentException;
	
	public void deleteToDataObjectMetaData(Dataobject doDb) throws WorkflowComponentException;
	public void deleteToDataObject(Dataobject doDb) throws WorkflowComponentException;
	
	public IHICData getMatchingDataObjectForUser(IHICData incomingDataObject, String nodeExecStatus);
	public IHICData getCompletedMatchingDataObjectForUser(IHICData incomingDataObject);
	public IHICData updateFormValuesWithDBData(IHICData incomingDO);
	
	public void addToHangingObjects(String workflowName, IHICData dataObject);
	
	public void removeFromHangingObjects(String workflowName, IHICData dataObject);
	
	public void updateDataObjectStatus(String workflowId, String nodeId, IHICData dataObject,String status);
}

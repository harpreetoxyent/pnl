/**
 * 
 */
package com.oxymedical.component.workflowComponent.nodeInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.INodeConnector;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.NodeConnector;

/**
 * @author vka
 *
 */
public class WorkflowNodeCollection 
{
	// String value is one workflow id (complete transaction) and Hashtable is the collection of workflow attribute
	Hashtable<String, Hashtable<String, NodeObject>> nodeCollection = null;
	Hashtable<String, List<INodeConnector>> connectorCollection = null;
	
	public WorkflowNodeCollection() {
		nodeCollection = new Hashtable<String, Hashtable<String, NodeObject>>();
		connectorCollection = new Hashtable<String, List<INodeConnector>>();
	}
	
	/**
	 * @return the connectorCollection
	 */
	public Hashtable<String, List<INodeConnector>> getConnectorCollection() {
		return connectorCollection;
	}

	/**
	 * @param connectorCollection the connectorCollection to set
	 */
	public void setConnectorCollection(String nodeKey ,List<INodeConnector> connectorCollection) {
		//this.connectorCollection = connectorCollection;
		this.connectorCollection.put(nodeKey, connectorCollection);
	}
	
	/**
	 * @return the nodeCollection
	 */
	public Hashtable<String, Hashtable<String, NodeObject>> getNodeCollection() {
		return nodeCollection;
	}
	
	/**
	 * @param nodeCollection the nodeCollection to set
	 */
	public void setNodeCollection(String nodeId ,Hashtable<String, NodeObject> nodeCollection) {
		this.nodeCollection.put(nodeId, nodeCollection);
		
	}

	/**
	 * Returns first node in the workflow collection
	 * @param activeWorkflowId
	 * @param activeNodeId
	 * @return
	 */
	public NodeObject getFirstNode(String activeWorkflowId, String activeNodeId)
	{
		return getNode(activeWorkflowId, activeNodeId);
	}
	
	
	public NodeObject getNode(String workflowId, String nodeId)
	{
		return nodeCollection.get(workflowId).get(nodeId);
	}
	
	
	public boolean isLastNode(String workflowId, String nodeId)
	{
		List<NodeObject> nextNOList = getNextNodes(workflowId, nodeId);
		if ((nextNOList != null) && (nextNOList.size() > 0))
		{
			if (nextNOList.get(0) != null) return false;
		}
		return true;
	}
	
	public List<NodeObject> getNextNodes(String activeWorkflowId, String activeNodeId)
	{
		List<NodeObject> resultList = null;
		if ((nodeCollection != null) && (connectorCollection != null) && (activeWorkflowId != null) && (activeNodeId != null))
		{
			NodeObject startNO = nodeCollection.get(activeWorkflowId).get(activeNodeId);
			List<INodeConnector> ncList = connectorCollection.get(activeWorkflowId);

			if (ncList == null) return resultList;

			for (int i = 0; i < ncList.size(); i++)
			{
				if (ncList.get(i).getFromNodeId().toLowerCase().equals(activeNodeId.toLowerCase()))
				{
					NodeObject connectedNO = nodeCollection.get(activeWorkflowId).get(ncList.get(i).getToNodeId());
					if (connectedNO != null)
					{
						if (resultList == null) resultList = new ArrayList<NodeObject>();
						resultList.add(connectedNO);
					}
				}
			}
		}
		return resultList;
	}
	public List<NodeObject> getPreviousNodes(String activeWorkflowId, String activeNodeId)
	{
			
		List<NodeObject> resultList = null;
		NodeObject startNO = nodeCollection.get(activeWorkflowId).get(activeNodeId);
		List<INodeConnector> ncList = connectorCollection.get(activeWorkflowId);
		
		if (ncList == null) return resultList;
		
		for (int i = 0; i < ncList.size(); i++)
		{
			if (ncList.get(i).getToNodeId().toLowerCase().equals(activeNodeId.toLowerCase()))
			{
				NodeObject connectedNO = nodeCollection.get(activeWorkflowId).get(ncList.get(i).getFromNodeId());
				if (connectedNO != null)
				{
					if (resultList == null) resultList = new ArrayList<NodeObject>();
					resultList.add(connectedNO);
				}
			}
		}
		return resultList;
	}
	
	public NodeObject getDefaultNode(String activeWorkflowId)
	{
		Hashtable<String, NodeObject> nodes = nodeCollection.get(activeWorkflowId);
		Collection<NodeObject> nodeList = nodes.values();
		Iterator<NodeObject> noIter = nodeList.iterator();
		
		while (noIter.hasNext())
		{
			NodeObject no = noIter.next();
			if (no.getNodeAttributes().getIsStart().equalsIgnoreCase("true")) return no;
		}
		
		return null;
	}
	
	public NodeObject getDefaultNode()
	{
		Enumeration<String> workflowKeys = nodeCollection.keys() ;
		while (workflowKeys.hasMoreElements())
		{
			String key = workflowKeys.nextElement();
			NodeObject retVal = getDefaultNode(key);
			if (retVal != null) return retVal;
		}
		return null;
	}

	public void setNodeCollection(
			Hashtable<String, Hashtable<String, NodeObject>> nodeCollection) {
		this.nodeCollection = nodeCollection;
	}

	public void setConnectorCollection(
			Hashtable<String, List<INodeConnector>> connectorCollection) {
		this.connectorCollection = connectorCollection;
	}

}

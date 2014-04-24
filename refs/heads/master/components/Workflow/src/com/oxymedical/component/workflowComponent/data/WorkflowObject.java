package com.oxymedical.component.workflowComponent.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeBuilder.IWorkflowAttributes;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;

public class WorkflowObject
{
	private List<NodeObject> _nodes = null;
	private HashMap<String, NodeObject> _nodesTable = null;
	private IWorkflowAttributes _workflowAttr = null;

	public List<NodeObject> getNodes()
	{
		return _nodes;
	}

	public void addNode(NodeObject node) throws WorkflowComponentException
	{
		if (_nodes == null) _nodes = new LinkedList<NodeObject>();
		this._nodes.add(node);
		
		// update Hashmap
		updateNodesTable(node);
	}
	private void updateNodesTable(NodeObject node) throws WorkflowComponentException
	{
		if (_nodesTable == null) _nodesTable = new HashMap<String, NodeObject>();
		if ((node == null) 
				|| (node.getNodeAttributes() != null) 
				|| (node.getNodeAttributes().getNodeId() != null))
			throw new WorkflowComponentException("Node identification information not present while adding node to collection");
		
		_nodesTable.put(node.getNodeAttributes().getNodeId(), node);
	}
	
	public int nodeCount()
	{
		if (_nodes == null) return 0;
		return _nodes.size();
	}

	public IWorkflowAttributes getWorkflowAttributes()
	{
		return _workflowAttr;
	}

	public void setWorkflowAttributes(IWorkflowAttributes workflowAttributes)
	{
		this._workflowAttr = workflowAttributes;
	}
	
	public NodeObject getNextNode(NodeObject currentNodeObj)
	{
		int currNodeIndex = _nodes.indexOf(currentNodeObj);
		if (_nodes.size() == currNodeIndex+1) return null;
		return _nodes.get(currNodeIndex+1);
	}
	
	public NodeObject getNode(String nodeId)
	{
		return _nodesTable.get(nodeId);
	}
}

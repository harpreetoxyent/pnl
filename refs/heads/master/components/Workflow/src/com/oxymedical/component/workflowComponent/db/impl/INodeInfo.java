package com.oxymedical.component.workflowComponent.db.impl;

import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeType;

public interface INodeInfo {

	public com.oxymedical.component.appadmin.model.resources_app.Nodeinfo addNodeInfo(
			java.lang.String formPatternId,
			java.lang.String datapatternId,java.lang.String nodeName
			,java.lang.String userpatternId,java.lang.String scenario,java.lang.String info
			,java.lang.String data,int nodeDetailId,int id, NodeType nodeType)throws WorkflowComponentException ;
	
	public Nodeinfo getNodeBasedOnNodeName(String nodeName) throws WorkflowComponentException;
	public Nodeinfo getNodeBasedOnId(int id) throws WorkflowComponentException;
	public List<Nodeinfo> getNodeListBasedOnNodeDetailId(int nodeDetailId);
}
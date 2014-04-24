package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodeconnector;
import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeType;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;

public class NodeInfoImpl implements INodeInfo {

	@Override
	public Nodeinfo addNodeInfo( String formPatternId,
			String datapatternId,  String nodeName,
			String userpatternId, String scenario,
			String info, String data, int nodeDetailId,int id,NodeType nodeType)throws WorkflowComponentException {
	
		Nodeinfo nodeinfo= new Nodeinfo();
		nodeinfo.setFormpatternid(formPatternId);
		nodeinfo.setDatapatternid(datapatternId);
		nodeinfo.setNodename(nodeName);
		nodeinfo.setUserpatternid(userpatternId);
		nodeinfo.setScenario(scenario);
		nodeinfo.setInfo(info);
		nodeinfo.setData(data);
		nodeinfo.setNodetype("I");
		if (nodeType.equals(NodeType.ACTION_NODE))nodeinfo.setNodetype("A");
		
		if(nodeDetailId>0)
		{
		INodeDetails nodeDetailsImpl= new NodeDetailsImpl();
		Nodedetail nodeDetail=nodeDetailsImpl.getNodeDetailBasedOnNodeDetailId(nodeDetailId);
		nodeinfo.setNodedetail(nodeDetail);
		}
        Object obj=null;
		
		try {
			obj=ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(nodeinfo);
		} catch (DBComponentException e) {
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		if(obj!=null)
		{
			nodeinfo=(Nodeinfo)obj;
			return nodeinfo;
		}
		return null;
	}

	public Nodeinfo getNodeBasedOnNodeName(String nodeName) throws WorkflowComponentException
	{
		return getNodeInfoBasedOnNodeName(nodeName);
	}
	
	public static Nodeinfo getNodeInfoBasedOnNodeName(String nodeName) throws WorkflowComponentException
	{
		Nodeinfo nodeInfo=null;
		String nodeQuery=WorkflowSqlCommands.Find_Node_Based_On_NodeName;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter event = new NameQueryParameter(WorkflowSqlCommands.nodeName, nodeName);
		listParam.add(event);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(nodeQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				throw (new WorkflowComponentException("No Node Present for this nodename " + nodeName
						));
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					nodeInfo = (Nodeinfo) upRowData;
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		return nodeInfo;
	}
	
	public static Integer getNodeIdBasedOnNodeName(String nodeName) throws WorkflowComponentException
	{
		Nodeinfo node = getNodeInfoBasedOnNodeName(nodeName);
		return node.getId();
	}
	
	public Nodeinfo getNodeBasedOnId(int id) throws WorkflowComponentException
	{ 
		return getNodeInfoBasedOnId(id);
	}
	
	
	public static Nodeinfo getNodeInfoBasedOnId(int id) throws WorkflowComponentException
	{ 
		Nodeinfo nodeInfo=null;
		String nodeQuery=WorkflowSqlCommands.Find_Node_Based_On_NoId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter node = new NameQueryParameter(WorkflowSqlCommands.nodeid, id);
		listParam.add(node);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(nodeQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				throw (new WorkflowComponentException("No Node Present for this nodeId " + id
						));
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					nodeInfo = (Nodeinfo) upRowData;
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return nodeInfo;
		
	}
	public void deleteNodeInfoBasedOnNodeId(int id) 
	{ 
		Nodeinfo nodeInfo=null;
		String nodeQuery=WorkflowSqlCommands.Delete_NodeInfo_Based_On_Id;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter node = new NameQueryParameter(WorkflowSqlCommands.nodeid, id);
		listParam.add(node);
		try {
			ConnectionDatabase.GetInstanceOfDatabaseComponent().executeDMLQueryWithNameParameter(
					nodeQuery, listParam);
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
	}
	
	public static String getNodeNameBasedOnId(int id) throws WorkflowComponentException
	{
		Nodeinfo node = getNodeInfoBasedOnId(id);
		return node.getNodename();
	}

	
	public List<Nodeinfo> getNodeListBasedOnNodeDetailId(int nodeDetailId)
	{
		List<Nodeinfo> nodeInfoList=new LinkedList<Nodeinfo>();
		String ndoeQuery=WorkflowSqlCommands.FIND_NODES_BASED_NODEDTAILID;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter nodeInfo = new NameQueryParameter(WorkflowSqlCommands.nodedetailid,nodeDetailId);
		listParam.add(nodeInfo);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(ndoeQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				System.err.println("Noconnector  present with this workflowid");
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					Nodeinfo nodeInfoObject = (Nodeinfo) upRowData;
					nodeInfoList.add(nodeInfoObject);
				}
			}
		} catch (DBComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeInfoList;
	}
	
}

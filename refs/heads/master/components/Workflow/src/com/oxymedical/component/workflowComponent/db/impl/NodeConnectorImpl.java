package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodeconnector;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;

public class NodeConnectorImpl implements INodeConnectorInfo {

	@Override
	public Nodeconnector addNodeConnector(String connectorName,
			String fromNodeId, String toNodeId,String workflowName)
			throws WorkflowComponentException {
			Nodeconnector nodeConnector= new Nodeconnector();
			if(connectorName!=null)
			{
				nodeConnector.setConnectorname(connectorName);
			}
        INodeInfo nodeInfoImpl= new NodeInfoImpl();
        Nodeinfo fromNodeObject= nodeInfoImpl.getNodeBasedOnNodeName(fromNodeId);
        if(fromNodeObject!=null)
        {
        	nodeConnector.setNodeinfoByFromNodeId(fromNodeObject);
        }
        Nodeinfo toNodeObject= nodeInfoImpl.getNodeBasedOnNodeName(toNodeId);
        if(toNodeObject!=null)
        {
        	nodeConnector.setNodeinfoByToNodeId(toNodeObject);
        }
        if(workflowName!=null)
        {
        	IWorkFlow workFlow= new WorkFlowImpl();
        	Workflowinfo workfowInfo=workFlow.getWorkFlowBasedOnName(workflowName);
            nodeConnector.setWorkflowinfo(workfowInfo);
        }
       Object obj=null;
		
		try {
			obj=ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(nodeConnector);
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		if(obj!=null)
		{
			nodeConnector=(Nodeconnector)obj;
		
		}
		return nodeConnector;
	}

	public List<Nodeconnector> getNodeConnectorsBasedOnWorkFlow(int workflowId)
	{
		List<Nodeconnector> nodeConnectors= new ArrayList<Nodeconnector>();

		String connectorQuery=WorkflowSqlCommands.Find_NodeConnector_Based_On_WorkflowId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter work = new NameQueryParameter(WorkflowSqlCommands.workflowid,workflowId);
		listParam.add(work);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(connectorQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				System.err.println("Noc connector  present with this workflowid");
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					Nodeconnector nodeConnectorObject = (Nodeconnector) upRowData;
					nodeConnectors.add(nodeConnectorObject);
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return nodeConnectors;
		
	}
	public void deleteWorkflowFromNodeConnector(int workflowID)
	{
		String workQuery=WorkflowSqlCommands.Find_NodeConnector_Based_On_WorkflowId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter work = new NameQueryParameter(WorkflowSqlCommands.workflowid, workflowID);
		listParam.add(work);
		try 
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(workQuery, listParam);
			if(list!=null && list.size()>0)
			{
				for (Iterator fieldRow = list.iterator(); fieldRow.hasNext();)
				{
					Nodeconnector node= (Nodeconnector)fieldRow.next();
					ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(node);
					
				}
			}
			
		} 
		catch (DBComponentException e) 
		{
			e.printStackTrace();
		}
	}

	
}

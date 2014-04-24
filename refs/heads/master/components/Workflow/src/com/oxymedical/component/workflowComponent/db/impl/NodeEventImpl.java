package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Eventtype;
import com.oxymedical.component.appadmin.model.resources_app.Nodeevent;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;

public class NodeEventImpl implements INodeEvent {

	@Override
	public Nodeevent addNodeEvent(int workflowNodeId, String params, String eventType) throws WorkflowComponentException{
		
		
		Nodeevent nodeevent= new Nodeevent();
		nodeevent.setParams(params);
		Workflownodeinfo workflowNodeInfo= new Workflownodeinfo();
		workflowNodeInfo.setId(workflowNodeId);
		nodeevent.setWorkflownodeinfo(workflowNodeInfo);
		
		IEventType event= new EventTypeImpl();
		Eventtype eventTypeObject=event.getEventTypeBasedOnEventType(eventType);
		
		if(eventTypeObject!=null)
		{
			nodeevent.setEventtype(eventTypeObject);
		}
		 Object obj=null;
			
			try {
				obj=ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(nodeevent);
			} catch (DBComponentException e) {
				e.printStackTrace();
			}
			if(obj!=null)
			{
				nodeevent=(Nodeevent)obj;
			
			}
		
		return nodeevent;
	}
	
	public List<Nodeevent> getNodeEventBasedOnNodeId(int workflowNodeId) throws WorkflowComponentException
	{
		List<Nodeevent> nodeEventObjects=new ArrayList<Nodeevent>();
		String nodeEventQuery=WorkflowSqlCommands.Find_Nodeevents_Based_On_NodeId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter event = new NameQueryParameter(WorkflowSqlCommands.workflownodeinfoid,workflowNodeId);
		listParam.add(event);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(nodeEventQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				System.err.println("No node event present for this node id");
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					Nodeevent nodeEvent = (Nodeevent) upRowData;
					nodeEventObjects.add(nodeEvent);
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return nodeEventObjects;
		
	}
	public void deleteNodeEventBasedOnWorkflowNodeId(int workflowNodeId)
	{
		String nodeEventQuery=WorkflowSqlCommands.Find_Nodeevents_Based_On_NodeId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter event = new NameQueryParameter(WorkflowSqlCommands.workflownodeinfoid,workflowNodeId);
		listParam.add(event);
		try 
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(nodeEventQuery, listParam);
			if(list!=null && list.size()>0)
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					Nodeevent nodeEvent = (Nodeevent) upRowData;
					ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(nodeEvent);
					
				}
			}
			
		} 
		catch (DBComponentException e) 
		{
			e.printStackTrace();
		}
	}

}

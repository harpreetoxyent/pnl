package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;


public class WorkFlowImpl implements IWorkFlow
{

	@Override
	public Workflowinfo addWorkFlow(String workflowName, String deleted,int workflowId,boolean isVisual,boolean createdFromUI,int visualizerid) throws WorkflowComponentException{

	   Workflowinfo workflow= new Workflowinfo();
//       workflow.setName(workflowName);
       workflow.setDisplayname(workflowName);
       workflow.setDeleted(deleted);
       workflow.setIsVisual(isVisual);
       workflow.setIsCreatedFromUi(createdFromUI);
       
       if(workflowId>0)
       {
    	   workflow.setId(workflowId);
       }
       if(visualizerid>0)
       {  
    	   INodeDetails nodeDetailsImpl= new NodeDetailsImpl();
    	   Nodedetail nodedetail=nodeDetailsImpl.getNodeDetailBasedOnNodeDetailId(visualizerid);
    	   workflow.setNodedetail(nodedetail);
       }
       Object obj=null;
		
		try {
			obj=ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(workflow);
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		if(obj!=null)
		{
			workflow=(Workflowinfo)obj;
			return workflow;
		}
		return null;

		
	}
	
	public Workflowinfo getWorkFlowBasedOnId(int id) throws WorkflowComponentException
	{ 
		Workflowinfo workflow=null;
		String workQuery=WorkflowSqlCommands.Find_WorkFlow_Based_On_Id;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter work = new NameQueryParameter(WorkflowSqlCommands.workflowid, id);
		listParam.add(work);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(workQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					workflow = (Workflowinfo) upRowData;
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return workflow;
		
	}
	public void deleteWorkFlowBasedOnID(int workflowId) throws WorkflowComponentException
	{ 
		String workQuery=WorkflowSqlCommands.DELETE_WORKFLOW_BASED_ON_ID;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter work = new NameQueryParameter(WorkflowSqlCommands.workflowid, workflowId);
		listParam.add(work);
		try {
			ConnectionDatabase.GetInstanceOfDatabaseComponent().executeDMLQueryWithNameParameter(workQuery, listParam);
			
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		
	}
	public Workflowinfo getWorkFlowBasedOnName(String workflowName) throws WorkflowComponentException
	{ 
		return getWorkflowBasedOnWorkflowName(workflowName);
	}
	
	public static Workflowinfo getWorkflowBasedOnWorkflowName(String workflowName) throws WorkflowComponentException
	{ 
		Workflowinfo workflow=null;
		String workQuery=WorkflowSqlCommands.Find_WorkFlow_Based_On_Name;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter work = new NameQueryParameter(WorkflowSqlCommands.workflowname,workflowName);
		listParam.add(work);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(workQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				System.err.println("No work flow present with this workflowname");
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					workflow = (Workflowinfo) upRowData;
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return workflow;
		
	}
	
	public List<Workflowinfo> getListOfWorkFlowList() throws WorkflowComponentException
	{
		List<Workflowinfo> workflowList= new ArrayList<Workflowinfo>();
		String workQuery=WorkflowSqlCommands.find_All_workflow;
		System.out.println("-------workQuery--------"+workQuery);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().getList(workQuery);
			System.out.println("-------list--------"+list);
			if (list == null || (list.size() == 0))
			{
				System.out.println("-------workflow list is null or size zero--------");
				/*throw (new WorkflowComponentException("No workflow Present for this aplication"
						));*/
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					Workflowinfo workflow = (Workflowinfo) upRowData;
					workflowList.add(workflow);
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return workflowList;
	}
	public static Workflowinfo getWorkflowBasedOnStatus(String nodeStatus) throws WorkflowComponentException
	{
		Workflowinfo workflowinfo=null;
		String workQuery=WorkflowSqlCommands.Find_WORKFLOW_FOR_VISUALIZER;
		System.out.println("query is==="+workQuery);
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter work = new NameQueryParameter(WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS,nodeStatus);
		listParam.add(work);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(workQuery, listParam);
			System.out.println("after the query execute===");
			if (list == null || (list.size() == 0))
			{
				System.out.println("No work flow present with this nodestatus");
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object[] upRowData = (Object[])upRow.next();
					
					for(int i=0;i<upRowData.length;i++)
					{
						System.out.println("upRowData[i]---"+upRowData[i].getClass());
						if(upRowData[i] instanceof Workflowinfo)
						{
							workflowinfo=(Workflowinfo)upRowData[i];
							return workflowinfo;
						}
					}
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return workflowinfo;
	}
}
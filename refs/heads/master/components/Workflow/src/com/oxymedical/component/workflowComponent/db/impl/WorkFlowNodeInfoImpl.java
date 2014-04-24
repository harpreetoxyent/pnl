package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;
import com.oxymedical.component.workflowComponent.operation.db.WorkflowDatabaseInfo;
import com.oxymedical.core.querydata.QueryData;

public class WorkFlowNodeInfoImpl implements IWorkFlowNodeInfo {

	@Override
	public Workflownodeinfo addWorkFlowNodeInfo(int workflowId, int nodeId,String status, boolean isStartNode)
			throws WorkflowComponentException {
		
		Workflownodeinfo workflownodeinfo= new Workflownodeinfo();
	    
		
		IWorkFlow workflow = new WorkFlowImpl();
		Workflowinfo workflowinfo=workflow.getWorkFlowBasedOnId(workflowId);
		if(workflowinfo!=null)
			workflownodeinfo.setWorkflowinfo(workflowinfo);
		
		INodeInfo nodeInfo = new NodeInfoImpl();
		Nodeinfo nodeObject = nodeInfo.getNodeBasedOnId(nodeId);
		if(nodeObject!=null)
			workflownodeinfo.setNodeinfo(nodeObject);
		workflownodeinfo.setIsStartNode(isStartNode);
		workflownodeinfo.setStatus(status);
    Object obj=null;
		
		try {
			obj=ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(workflownodeinfo);
		} catch (DBComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj!=null)
		{
			workflownodeinfo=(Workflownodeinfo)obj;
		
		}
		return workflownodeinfo;
	}
	
	public void deleteWorkflowFromWorkflowNodeInfo(int workflowID)
	{
		String workQuery=WorkflowSqlCommands.Find_Workflownodeinfo_Based_On_WorkflowID;
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
					Workflownodeinfo node= (Workflownodeinfo)fieldRow.next();
					NodeEventImpl nei = new NodeEventImpl();
					nei.deleteNodeEventBasedOnWorkflowNodeId(node.getId());
					ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(node);
					
				}
			}
			
		} 
		catch (DBComponentException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void deleteXMLWorkflowFromWorkflowNodeInfo(int workflowID)
	{
		String workQuery=WorkflowSqlCommands.Find_Workflownodeinfo_Based_On_WorkflowID;
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
					Workflownodeinfo node= (Workflownodeinfo)fieldRow.next();
					Nodeinfo nodeObject= new Nodeinfo();
				    nodeObject=node.getNodeinfo();
					NodeEventImpl nei = new NodeEventImpl();
					nei.deleteNodeEventBasedOnWorkflowNodeId(node.getId());
					NodeInfoImpl nii = new NodeInfoImpl();
					ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(node);
					ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(nodeObject);
					//nii.deleteNodeInfoBasedOnNodeId(node.getNodeinfo());
					
					
				}
			}
			
		} 
		catch (DBComponentException e) 
		{
			e.printStackTrace();
		}
		/*catch(WorkflowComponentException wce)
		{
			wce.printStackTrace();
		}*/
	}
	
	public static Workflownodeinfo getWorkFlowNodeInfo(int workflowId, int nodeId) throws WorkflowComponentException
	{
		Workflownodeinfo wfNodeInfo = null;
		String nodeQuery = WorkflowSqlCommands.FIND_WORKFLOWNODE_BASED_ON_ID;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter workflowParam = new NameQueryParameter(WorkflowSqlCommands.CONST_WORKFLOWNODE_WFID, workflowId);
		listParam.add(workflowParam);
		NameQueryParameter nodeParam = new NameQueryParameter(WorkflowSqlCommands.CONST_WORKFLOWNODE_NODEID, nodeId);
		listParam.add(nodeParam);
		
		try
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(nodeQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					wfNodeInfo = (Workflownodeinfo) upRow.next();
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		return wfNodeInfo;
	}



	public static Workflownodeinfo getWorkFlowNodeInfo(String workflowName, String nodeName) throws WorkflowComponentException
	{
		Workflownodeinfo wfNodeInfo = null;
		String nodeQuery = WorkflowSqlCommands.FIND_WORKFLOWNODE_BASED_ON_NAME;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter workflowParam = new NameQueryParameter(WorkflowSqlCommands.workflowname, workflowName);
		listParam.add(workflowParam);
		NameQueryParameter nodeParam = new NameQueryParameter(WorkflowSqlCommands.nodeName, nodeName);
		listParam.add(nodeParam);
		
		try
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(nodeQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				
			}
			else
			{
				Object[] objects = (Object[]) list.get(0);
				for (int i = 0; i < objects.length; i++)
				{
					Object obj = objects[i];
					if (obj instanceof Workflownodeinfo)
						wfNodeInfo = (Workflownodeinfo) obj;
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		return wfNodeInfo;
	}
	
	public List<Workflownodeinfo> getWorkflowNodesInfoListBasedOnWorkflowId(int id) throws WorkflowComponentException
	{
		List<Workflownodeinfo> workNodeInfoList= new ArrayList<Workflownodeinfo>();
		String nodeQuery=WorkflowSqlCommands.find_WorkFlowNodesInfo_Based_On_WorkFlowId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter node = new NameQueryParameter(WorkflowSqlCommands.workflowid, id);
		listParam.add(node);
		try
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(nodeQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				/*throw (new WorkflowComponentException("No Node Present for this nodeId " + id
						));*/
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object[] upRowData = (Object[])upRow.next();
					Workflownodeinfo workNodeInfo = (Workflownodeinfo) upRowData[0];
					workNodeInfoList.add(workNodeInfo);
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		return workNodeInfoList;
	}
	
	
	
	public static List getWorkflowNodesInfoListBasedOnWorkflowId(String workflowName) throws WorkflowComponentException
	{
		List workNodeInfoList = null;
		String nodeQuery = WorkflowSqlCommands.find_WorkFlowNodesInfo_Based_On_WorkFlowName;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter node = new NameQueryParameter(WorkflowSqlCommands.workflowname, workflowName);
		listParam.add(node);
		
		try
		{
			workNodeInfoList = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(nodeQuery, listParam);
			if (workNodeInfoList == null || (workNodeInfoList.size() == 0))
			{
				
			}
			else
			{
				/*for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object[] upRowData = (Object[])upRow.next();
					Workflownodeinfo workNodeInfo = (Workflownodeinfo) upRowData[0];
					workNodeInfoList.add(workNodeInfo);
				}*/
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		return workNodeInfoList;
	}
	
	
	
	public static List getWorkflowNodeWithActionAndPackage() throws WorkflowComponentException
	{
		List workNodeInfoList = null;
		String wfQuery = WorkflowSqlCommands.FIND_WORKFLOWNODES_WITH_ACTION_AND_PACKAGE;
		
		try
		{
			workNodeInfoList = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.getList(wfQuery);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		return workNodeInfoList;
	}
	
	
	public static void main(String[] args)
	{
		/*WorkflowComponent.dataBaseInfo = new WorkflowDatabaseInfo();
		WorkflowComponent.dataBaseInfo.setUserName("root");
		WorkflowComponent.dataBaseInfo.setPassword("");
		WorkflowComponent.dataBaseInfo.setDbName("appadmin");
		WorkflowComponent.dataBaseInfo.setDbServerName("localhost");
		WorkflowComponent.dataBaseInfo.setDBPort("3306");
		WorkflowComponent.dataBaseInfo.setDbType("mysql");
*/
		//try
		//{
			
			WorkFlowNodeInfoImpl w = new WorkFlowNodeInfoImpl();
			w.deleteWorkflowFromWorkflowNodeInfo(2);
			/*List output = getWorkflowNodesInfoListBasedOnWorkflowId("WF6");
			String[][] dataOutput = QueryData.iterateListData(output);
			
			if (dataOutput == null)
			{
				WorkflowComponent.log(0, "dataOutput == null");
				return;
			}
			for (int i = 0; i < dataOutput.length; i++)
			{
				for (int j = 0; j < dataOutput[i].length; j++)
				{
					System.out.print(dataOutput[i][j] + "\t");
				}
			}*/
		//}
		//catch (WorkflowComponentException e)
		//{
		//	e.printStackTrace();
		//}
	}

}

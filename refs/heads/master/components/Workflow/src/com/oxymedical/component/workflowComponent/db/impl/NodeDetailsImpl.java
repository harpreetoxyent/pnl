package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.tools.Tool;

import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.appadmin.model.resources_app.Nodeinfo;
import com.oxymedical.component.appadmin.model.resources_app.Toolcateogry;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;

public class NodeDetailsImpl implements INodeDetails {

	@Override
	public Nodedetail addNodeDetail(int nodeDetailId, String action,
			String packageName, String formpattern,String deleted,String nodeDescription,int toolCategoryId, boolean isVisualizer, String inputXml)
			throws WorkflowComponentException
	{
		
		Nodedetail nodeDetail= new Nodedetail();
		if(nodeDetailId>0)
		{
			nodeDetail.setId(nodeDetailId);
		}
		nodeDetail.setAction(action);
		nodeDetail.setPacakge(packageName);
		nodeDetail.setFormpattern(formpattern);
		nodeDetail.setDeleted(deleted);
		nodeDetail.setIsVisualizer(isVisualizer);
		nodeDetail.setInputxml(inputXml);
		nodeDetail.setNodeDescription(nodeDescription);
		if(toolCategoryId>0)
		{
			Toolcateogry toolcateogry=ToolCategoryImpl.getToolCategoryBasedOnToolId(toolCategoryId);
			nodeDetail.setToolcateogry(toolcateogry);
		}
	
		INodeInfo nodeInfo = new NodeInfoImpl();
	
		Object obj=null;
		try{
				obj=ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(nodeDetail);
			} catch (DBComponentException e) {
				e.printStackTrace();
			}
			if(obj!=null)
			{
				nodeDetail=(Nodedetail)obj;
				return nodeDetail;
			}
			return null;
	
	}
	
	public Nodedetail getNodeDetailBasedOnNodeDetailId(int nodeDetailId) throws WorkflowComponentException
	{
		Nodedetail nodeDetail=null;
		String nodeQuery = WorkflowSqlCommands.FIND_NODEDETAIL_BASED_ON_NODEDETIALID;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter nodeParam = new NameQueryParameter(WorkflowSqlCommands.nodedetailid, nodeDetailId);
		listParam.add(nodeParam);
		
		try
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(nodeQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					nodeDetail = (Nodedetail) upRow.next();
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		return nodeDetail;
		
	}
	
	public static List getNodeDetailsBasedOnWorkflowName(String workFlowName) throws WorkflowComponentException
	{
		List nodeDetailsList= new LinkedList();
		String nodeDetailQuery = WorkflowSqlCommands.Find_NODEDETAILS_BASED_ON_WORKFLOWNAME;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter nodeParam = new NameQueryParameter(WorkflowSqlCommands.workflowname, workFlowName);
		listParam.add(nodeParam);
		
		try
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(nodeDetailQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object[] returnObjects = (Object[]) upRow.next();
					if(returnObjects!=null)
					{
						for(int i=0;i<returnObjects.length;i++)
						{
							Object obj=returnObjects[i];
							if(obj instanceof Nodedetail)
							{
								Nodedetail nodedetail=(Nodedetail)obj;
								nodeDetailsList.add(nodedetail.getId());
							}
						}
					}
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		return nodeDetailsList;
	}

}

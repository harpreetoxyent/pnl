package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.oxymedical.component.appadmin.model.resources_app.Toolcateogry;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;

public class ToolCategoryImpl {
	
	public static Toolcateogry getToolCategoryBasedOnToolId(int toolId) throws WorkflowComponentException 
	{
		Toolcateogry toolcateogry=null;
		String toolQuery = WorkflowSqlCommands.Find_TOOL_BASED_ON_TOOLID;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter toolParam = new NameQueryParameter(WorkflowSqlCommands.toolcategoryid, toolId);
		listParam.add(toolParam);
		try
		{
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(toolQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				return null;
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					toolcateogry = (Toolcateogry) upRow.next();
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		return toolcateogry;
	}
	public static Toolcateogry addToolCateogry(int toolId,String cateogryName) throws WorkflowComponentException 
	{
		Toolcateogry toolcateogry= new Toolcateogry();
		toolcateogry.setCategory(cateogryName);
		if(toolId>0)
			toolcateogry.setId(toolId);
		Object obj=null;
		try{
				obj=ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(toolcateogry);
			} catch (DBComponentException e) {
				e.printStackTrace();
			}
			if(obj!=null)
			{
				toolcateogry=(Toolcateogry)obj;
				return toolcateogry;
			}
			return null;
	}
}


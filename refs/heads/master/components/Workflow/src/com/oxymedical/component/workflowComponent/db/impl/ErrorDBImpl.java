package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Errorinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;

public class ErrorDBImpl {

	
	public static Errorinfo addErrorInfoForDataObject(Dataobject dataobject,String message) throws WorkflowComponentException
	{
		Errorinfo errorinfo= new Errorinfo();
		errorinfo.setDataobject(dataobject);
		errorinfo.setErrormessage(message);
		Object obj = null;
		try
		{
			obj = ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(errorinfo);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}

		if (obj != null)
		{
			return (Errorinfo) obj;
		}
		return null;
	}
	
	public static void deleteErrorInfoForDataObject (Dataobject dataobject) throws WorkflowComponentException
	{
		List<Errorinfo> errorList= new ArrayList<Errorinfo>();
		String errorQuery=WorkflowSqlCommands.FIND_ERROR_BASED_ON_DATAOBJECTID;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter work = new NameQueryParameter(WorkflowSqlCommands.DATAOBJECTID,dataobject.getId());
		listParam.add(work);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(errorQuery, listParam);
			if(list != null || (list.size()> 0))
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					Errorinfo errorObject = (Errorinfo) upRowData;
					errorList.add(errorObject);
				}
			}
		} catch (DBComponentException e) {
			throw (new WorkflowComponentException("Exception from the database component"
			));
			//e.printStackTrace();
		}
	if(errorList!=null && errorList.size()>0)
	{
		for(int i=0;i<errorList.size();i++)
		{
		Errorinfo errorObject=errorList.get(i);
		try {
			ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(errorObject);
		} catch (DBComponentException e) 
		{
			e.printStackTrace();
		}
		}
	}
	}
}

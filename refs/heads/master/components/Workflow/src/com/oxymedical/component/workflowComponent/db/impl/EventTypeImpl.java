package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Eventtype;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;

public class EventTypeImpl implements IEventType{

	@Override
	public Eventtype getEventTypeBasedOnEventType(String eventType)
			throws WorkflowComponentException 
		{
		Eventtype eventObject= null;
		
		String eventQuery=WorkflowSqlCommands.Find_EventType_From_Event;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter event = new NameQueryParameter(WorkflowSqlCommands.eventtype, eventType);
		listParam.add(event);
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
			.executeHSQLQueryWithNameParameter(eventQuery, listParam);
			if (list == null || (list.size() == 0))
			{
				throw (new WorkflowComponentException("No Event Present for this eventtype " + eventType
						));
			}
			else
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					eventObject = (Eventtype) upRowData;
				}
			}
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		return eventObject;
	}

}

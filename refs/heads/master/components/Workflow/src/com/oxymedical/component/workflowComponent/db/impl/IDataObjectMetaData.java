package com.oxymedical.component.workflowComponent.db.impl;

import com.oxymedical.component.appadmin.model.resources_app.Dataobjectmetadata;
import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;

public interface IDataObjectMetaData
{
	public Dataobjectmetadata addDataObjectMetaData(
			Dataobject doQueue, 
			String key, 
			Object value) throws WorkflowComponentException;
}

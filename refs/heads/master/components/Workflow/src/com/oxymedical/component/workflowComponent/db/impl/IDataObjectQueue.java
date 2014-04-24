package com.oxymedical.component.workflowComponent.db.impl;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;

public interface IDataObjectQueue
{
	public Dataobject addDataObjectToQueue(
			String formPatternId, 
			String datapatternId, 
			String status, 
			Workflownodeinfo nodeName,
			String userpatternId,
			String userId,
			String uniqueId) throws WorkflowComponentException;
	
	public Dataobject addDataObjectToQueueWithExistingCheck(
			String formPatternId, 
			String datapatternId,
			String status, 
			Workflownodeinfo nodeInfo, 
			String userpatterns, 
			String userId, 
			String uniqueId)
			throws WorkflowComponentException;
}

package com.oxymedical.component.workflowComponent.db.impl;


import com.oxymedical.component.appadmin.model.resources_app.Eventtype;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;

public interface IEventType {

	
	public Eventtype getEventTypeBasedOnEventType(java.lang.String eventType) throws WorkflowComponentException;
}

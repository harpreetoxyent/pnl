package com.oxymedical.component.workflowComponent.db.impl;

import com.oxymedical.component.appadmin.model.resources_app.Nodedetail;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;

public interface INodeDetails {

	public Nodedetail addNodeDetail(int nodeDetailId, java.lang.String action,
			java.lang.String packageName, java.lang.String formpattern,
			java.lang.String deleted, java.lang.String nodeDescription,int toolCateogryId,boolean isVisualizer, java.lang.String inputXml)
			throws WorkflowComponentException;

	public Nodedetail getNodeDetailBasedOnNodeDetailId(int nodeDetailId)
			throws WorkflowComponentException;
}

package com.oxymedical.component.workflowComponent;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.core.commonData.Application;

public interface IWorkflowParser {
	
	public void readProcessDefnXML(Document document) throws DocumentException,WorkflowComponentException;
	
//	public void parseProcessDefnXML();
	
	public WorkflowNodeCollection parseProcessDefnXML(Application application) throws WorkflowComponentException;

}

package com.oxymedical.component.workflowComponent;

import java.io.File;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventPublisher;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.workflowComponent.dataObjectManager.IDataObjectManager;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.containerInfo.Event;
import com.oxymedical.hic.application.NOLISRuntime;


public interface IWorkflowComponent extends IComponent 
{
	public IHICData notify(IHICData object, Event event);
	
	public void createProcessDefinition(File processDefnXMLDocument);
	
	public WorkflowNodeCollection getWorkflowCollection();
	
	public void newWorkflowApplication(String applicationFileName, String applicationPath, String baseDirectoryPath)throws WorkflowComponentException;
	
	public void startWorkflow() throws WorkflowComponentException;
	
	public Application getApplication();
	
	public void setApplication(Application application);
	
	public void setRuntime(NOLISRuntime runtime);
	
	public IHICData invokeWorkflow(IHICData dataObject);
	
	public void setDataBaseSetting(IHICData hicData);

	public IDataObjectManager getDataObjectManager();

	public IHICData getDataObjectsForUser(IHICData data);
	
	public void initalizeWorkFlow(IHICData hicData);
	
	public IHICData executeNextNode(IHICData inputHicData);
	@EventSubscriber(topic="GetDataObjectForpatient")
	public IHICData retrieveUserDataObjectByUniqueID(IHICData incomingDO,Object dummy);
	@EventPublisher(topic="DataObjectForpatient")
	public IHICData returnDataObjectForpatient(IHICData incomingDO);
	
}

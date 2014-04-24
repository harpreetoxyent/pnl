package com.oxymedical.component.workflowComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventPublisher;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.workflowComponent.command.tool.IObserver;
import com.oxymedical.component.workflowComponent.command.tool.ToolCompletionObserver;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.dataObjectManager.DataObjectManager;
import com.oxymedical.component.workflowComponent.dataObjectManager.IDataObjectManager;
import com.oxymedical.component.workflowComponent.dataobject.util.DataObjectListUtil;
import com.oxymedical.component.workflowComponent.db.impl.IWorkFlow;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowImpl;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.helper.WorkflowHelper;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.nodeInfo.WorkflowNodeCollection;
import com.oxymedical.component.workflowComponent.nodemanager.INodeEventManager;
import com.oxymedical.component.workflowComponent.nodemanager.NodeEventManager;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;
import com.oxymedical.component.workflowComponent.operation.db.DatabaseConnectionSetting;
import com.oxymedical.component.workflowComponent.operation.db.WorkflowDatabaseInfo;
import com.oxymedical.component.workflowComponent.parser.WorkflowParser;
import com.oxymedical.component.workflowComponent.visualizer.VisualizerManager;
import com.oxymedical.component.workflowComponent.workflowManager.WorkflowManager;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.containerInfo.Event;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.router.IRouter;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;
import com.oxymedical.framework.objectbroker.annotations.InjectNewType;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;


/**
 * Components interacts with this class to perform Workflow operations. 
 * This class implements IWorkflowComponent.
 * @author      Oxyent Medical
 * @version     1.0.0
*/


public class WorkflowComponent implements IWorkflowComponent,IComponent 
{
	
	public IHICData hicData = null;
	private Application application = null;
	WorkflowParser workflowParser = null;
//	WorkflowNodeCollection workflowCollection = null;
	public static WorkflowDatabaseInfo dataBaseInfo = null;
	
	@InjectNew
	public static DBComponent workflowDbComponent;
	//TODO Added a hack for running application with workflow
	//Need to change and discuss architecture
//	private HashMap<String, WorkflowNodeCollection> workflowMapWithApplication = new HashMap<String, WorkflowNodeCollection>(); 
	private WorkflowManager workflowManager = null;
	private NOLISRuntime runtime = null;
	private IDataObjectManager doManager = null;
	private NodeObject currNode = null;
	private NodeObject nextNode = null;
	private IObserver toolCompletionObserver = new ToolCompletionObserver(this);
	private static boolean isApplicationRegistered = false;
	private INodeEventManager nodeEventMgr = null;

	@InjectNew
	public static RuleComponent ruleComponent;
	
	@InjectNew
	static public LoggingComponent logger;
	
	@InjectNewType(implementationClass="com.oxymedical.servlet.HICServlet.HICRouter")
	public static IRouter router;

	
	public WorkflowComponent() 
	{
		workflowParser = new WorkflowParser();
		workflowManager = new WorkflowManager();
		doManager = new DataObjectManager();
		nodeEventMgr = new NodeEventManager();
		workflowManager.setToolCompletionObserver(this.toolCompletionObserver);
		workflowManager.setRouter(router);
	}
	
	public void newWorkflowApplication(IApplication app)throws WorkflowComponentException 
	{
		if (app == null) throw (new WorkflowComponentException("Application is null"));
		newWorkflowApplication(app.getApplicationName(), app.getApplicationFolderPath(), app.getBaseDirectoryPath());
	}
	
	
	/**
     * This method is used initialize Workflow Application settings such as 
     * application file name, application path and base directory etc.
     * 
     * @param applicationName  
     * @param applicationPath
     * @param baseDirectoryPath    
     * @throws WorkflowComponentException
     * @returns void
     * 
    */
	public void newWorkflowApplication(String applicationName, String applicationPath, String baseDirectoryPath)throws WorkflowComponentException {
		
		if(applicationName==null || applicationPath==null || applicationName.trim()=="" || applicationPath.trim()=="")
		{
			throw (new WorkflowComponentException("Application source not found"));
		}
		else if(baseDirectoryPath==null || baseDirectoryPath.trim()=="")
		{
			throw (new WorkflowComponentException("Base Directory Path not found"));
		}
		
		else
		{
			String applicationFileName="";
			applicationPath = applicationPath + "/";
			applicationFileName = applicationName + WorkflowConstant.APPLICATION_EXTN;
			application = new Application();
			application.setApplicationName(applicationName);
			application.setApplicationFileName(applicationFileName);
			application.setApplicationFolderPath(applicationPath);
			application.setBaseDirectoryPath(baseDirectoryPath);
		}
	}

	// this method is used to start the work flow component 
	public void startWorkflow()throws WorkflowComponentException
	{
		
	}
	
	public static void main(String args[]) throws Exception
	{
		try 
		{
			getInstanceOfLoggingComponent();
			IHICData hicData = new HICData();
			/*IWorkflowPattern workflowPatten= new WorkflowPattern();
			workflowPatten.setWorkflowNode("WF02");
			workflowPatten.setWorkflowPattern("comoxymedicalcomponentstoolssleepdataarchitectureSleepDataArchitecture");*/
			IData data = new Data();
			List list= new ArrayList();
			list.add(1);
			list.add(2);
			list.add(3);
			IFormPattern fp = new FormPattern();
			fp.setFormId("visualmode");
			Hashtable<String, Object> formVals = new Hashtable<String, Object>();
			formVals.put("textbox206","WF01");
			//formVals.put("textbox220","1");
			formVals.put("checkbox4", "true");
			formVals.put("listbox5",list);
			//hardcoded
			//formVals.put(VisualizerConstants.filename, edfFileName);
			fp.setFormValues(formVals);
			data.setFormPattern(fp);
			data.setUserId("adminuserone");
			data.setMethodName("GetDataObjectForpatient");
			hicData.setData(data);
			IApplication app = new Application();
			app.setApplicationFileName("NEISUI.esp");
			app.setApplicationFolderPath("D:\\NOLIS_WORK\\NOLISApplication\\NEISUI/");
			app.setApplicationName("NEISUI");
			app.setBaseDirectoryPath("c:/NEIS/glassfish/domains/domain1/lib/ext");
			app.setRenderApplicationType("ZK");
			app.setServerDirectory("c:/NEIS/glassfish/domains/domain1/autodeploy");
			hicData.setApplication(app);
			WorkflowComponent work = new WorkflowComponent();
			//work.checkConnectionSettings(hicData);
			work.initalizeWorkFlow(hicData);
		    		}
		
		finally
		{
			
		}
	}
	
		
	/**
	 * Creates process definition based on jPWL & BPEL
	 */
	public void createProcessDefinition(File processDefnXMLDocument) 
	{
		// TODO Not in the current scope
	}
	
	public void destroy() 								{	}
	public void run()									{	}
	public void start(Hashtable<String, Document> arg0) {	}
	public void stop() 									{	}
	public static LoggingComponent getInstanceOfLoggingComponent() {
		if (logger == null) {
			logger = new LoggingComponent();
		}
		return logger;
	}

	@EventSubscriber(topic="InvokeWorkflow")
	public IHICData invokeWorkflow(IHICData dataObject)
	{
		IHICData retDO = dataObject;
		if(dataObject == null){
			System.out.println("WorkflowComponent.invokeWorkflow(): HICData is null");
		}else{
			DatabaseConnectionSetting.checkConnectionSettings(dataObject);
			if (dataObject == null) return dataObject;
	         
			// User not authenticated
			if (((dataObject == null) 
					|| (dataObject.getData() == null) 
					|| (dataObject.getData().getUserId() == null)) && (!dataObject.getData().getMethodName().equalsIgnoreCase("authenticateUserEx"))) 
				return dataObject;
			this.hicData = dataObject;
	
			try
			{
				// isValid(dataObject);
				workflowManager.initializeWorkflow(hicData.getApplication());
	
				// If there is no workflow, please go back
				if (workflowManager.getWorkflowCollection(dataObject.getApplication()) == null)
				{
					WorkflowComponent.log(0,"---Workflow collection is null so return from here only---");
					return retDO;
				}
				workflowManager.setRouter(router);
				workflowManager.invokeWorkflow(dataObject);
			}
			catch (WorkflowComponentException e)
			{
				e.printStackTrace();
			}
		}

		return retDO;
	}
	
	@EventSubscriber(topic="GetUserDataObject")
	public IHICData getUserDataObject(IHICData incomingDO)
	{
		IHICData retDO = null;
		retDO = doManager.getMatchingDataObjectForUser(incomingDO);
		return retDO;
	}
	
	@EventSubscriber(topic="GetDataObjectForpatient")
	public IHICData retrieveUserDataObjectByUniqueID(IHICData incomingDO,Object dummy)
	{
    	incomingDO = doManager.updateFormValuesWithDBData(incomingDO);
    	returnDataObjectForpatient(incomingDO);
		return incomingDO;
	}

	@EventPublisher(topic="DataObjectForpatient")
	public IHICData returnDataObjectForpatient(IHICData incomingDO)
	{
		Object[] objectArray = { incomingDO };
		objectArray[0] = incomingDO;
		WorkflowComponent.log(0, "inside the DataObjectForpatient in workflow======");
		try {
			runtime.FireEvent("DataObjectForpatient", objectArray, PublicationScope.Global );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return incomingDO;
	}	
	
	@EventSubscriber(topic="NotifyWorkflow")
	public IHICData notify(IHICData object, Event event) 
	{
		this.hicData = (IHICData)object;
		return hicData;	
		
	}
	public void initalizeWorkFlow(IHICData hicData)
	{
	
		this.hicData=hicData;
		try
		{
			System.out.println("-----hicData  application name --" + hicData.getApplication().getApplicationName());
			logger.log(0, "hicData  application name --" + hicData.getApplication().getApplicationName());
			newWorkflowApplication(hicData.getApplication());
			workflowManager.startWorkflow(application);
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
		}
	}
	
	public IHICData getHicData() 
	{
		return this.hicData;
	}
	
	public void setHicData(IHICData arg0) 
	{
		this.hicData = arg0;
	}

	public void maintenance(IMaintenanceData arg0) 
	{
		
	}

	/**
	 * @return the workflowCollection
	 */
	public WorkflowNodeCollection getWorkflowCollection() 
	{
		return this.workflowManager.getWorkflowCollection(); //workflowCollection;
	}

	
	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public void setRuntime(NOLISRuntime runtime)
	{
		this.runtime = runtime;
	}


	/**
	 * Fetches list of all DataObjects from each Node of Workflow matching
	 * current user. The returned list data is stored in
	 * <code>dataObject.getData().getQueryData().getListData()</code>. 
	 * The order of columns in list is as follows:
	 * 
	 * <ol>
	 *   <li>Workflow Name</li>
	 *   <li>Node Name</li>
	 *   <li>Current DO Status / Node Status</li>
	 *   <li>DO Unique Id</li>
	 * </ol>
	 * 
	 * @param dataObject
	 * @return
	 */
	@EventSubscriber(topic="GetDataObjectsOnWorkflowNodeForUser")
	public IHICData getDataObjectsForUser(IHICData data)
	{
		if (data.getData().getQueryData() == null)
		{
			QueryData qd = new QueryData();
			data.getData().setQueryData(qd);
		}
		
		data.getData().getQueryData().setListData(
				DataObjectListUtil.createDOListForUser(
						doManager.getAllMatchingDataObjectForUser(data)));
		return data;
	}
	
	
	
	public IDataObjectManager getDataObjectManager()
	{
		return this.doManager;
	}

	@EventSubscriber(topic = "SaveWorkflow")
	public IHICData saveWorkflow(IHICData hicData) throws Exception
	{
		if (hicData == null || hicData.getData().getFormPattern().getFormValues() == null)
		{
			throw new Exception("HicObject or formvalues is null");
		}
		try
		{
			if (!isApplicationRegistered)
			{
				registerWindowWithApplication(hicData);
			}
					
	        if(ruleComponent == null)
			{
			 // TODO - Use RuleManager to perform these steps
			 ruleComponent = new RuleComponent();
			}
	        WorkflowHelper workflowHelper= new WorkflowHelper();
	        workflowHelper.setWorkflowManager(this.workflowManager);
	        workflowHelper.saveWorklowData(hicData, ruleComponent);
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return hicData;
	}


	@EventSubscriber(topic = "executeListWorkflow")
	public IHICData getListInGridWorkflow(Object hicInputData)
	{
		IHICData hicObjectData = (IHICData) hicInputData;

		try
		{
			if (hicObjectData == null)
			{
				throw new Exception("hicdata null");
			}

			DatabaseConnectionSetting.checkConnectionSettings(hicObjectData);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().setConnectionAvailable(true);
			hicObjectData = ConnectionDatabase.GetInstanceOfDatabaseComponent().getListData(hicObjectData);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicObjectData;

	}


	private void registerWindowWithApplication(IHICData hicData)
	{
		ConnectionDatabase.GetInstanceOfDatabaseComponent().registerWindowForApplication(
				hicData);
		isApplicationRegistered = true;
	}


	@EventSubscriber(topic = "AddNewNodeFromUI")
	public IHICData addNewNodeFromUI(IHICData hicData)
	{
		try
		{
			if (hicData == null || hicData.getData().getFormPattern().getFormValues() == null)
			{
				throw new Exception("HicObject or formvalues is null");
			}
			if (!isApplicationRegistered)
			{
				registerWindowWithApplication(hicData);
			}
			WorkflowHelper workflowHelper= new WorkflowHelper();
			workflowHelper.addNewNodeFromUI(hicData);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicData;
	}

	@EventSubscriber(topic = "DeleteWorkflow")
	public IHICData deleteWorkflow(IHICData hicData) throws Exception
	{
		if (hicData == null || hicData.getData().getQueryData().getCondition() == null)
		{
			throw new Exception("HicObject or workflowId is null");
		}
		String workflowId = hicData.getData().getQueryData().getCondition();
		Integer intWorkflowId = Integer.parseInt(workflowId);
		IWorkFlow workFlowImpl = new WorkFlowImpl();
		Workflowinfo workflowinfo = workFlowImpl.getWorkFlowBasedOnId(intWorkflowId);
		hicData.getData().getFormPattern().getFormValues().put(
				WorkflowConstant.INPUT_WORKFLOWNAME,workflowinfo.getName());
		executeClearSelectedWorkflow(hicData);
		workFlowImpl.deleteWorkFlowBasedOnID(intWorkflowId);
		workflowManager.updateWorkflowMap(hicData.getApplication());
		
		return hicData;
	}

	/**
	 * This function only sets information about Status, FormPattern,
	 * DataPattern, UserPattern from Next Node so that next Node can be
	 * executed. Note that InvokeWorkflow is not called from here. <br>
	 * After setting the values, it is returned and since the infrastructure
	 * automatically call InvokeWorklow, the invocation is handled.
	 */
	@EventSubscriber(topic="ProcessNextWorkflowTool")
	public IHICData executeNextNode(IHICData inputHicData)
	{
		try
		{
			return this.workflowManager.completeCurrentAndUpdateDOForNextNode/*updateDOForNextNode*/(inputHicData);
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
		}
		return inputHicData;
	}

	
	/**
	 * This method is added to revert the node for the exception of the tool.
	 * @param inputHicData
	 * @return
	 */
	@EventSubscriber(topic="ProcessRevertOperation")
	public IHICData executeRevertOperation(IHICData inputHicData)
	{
		WorkflowHelper workflowHelper=new WorkflowHelper();
		workflowHelper.setDoManager(doManager);
		workflowHelper.setWorkflowManager(workflowManager);
		inputHicData=workflowHelper.executeRevertOperation(inputHicData);
		return inputHicData;
	}

	
	/**
	 * This method is added to clear all running workflow in the application.
	 * @param inputHicData
	 * @return
	 */
	@EventSubscriber(topic="ProcessClearAllWorkflow")
	public IHICData executeClearAllWorkflow(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "ProcessClearAllWorkflow invoked");
		WorkflowHelper workflowHelper=new WorkflowHelper();
		workflowHelper.setDoManager(doManager);
		workflowHelper.setWorkflowManager(workflowManager);
		inputHicData=workflowHelper.executeClearAllWorkflow(inputHicData);
		return inputHicData;
	}
	
	@EventSubscriber(topic = "updateDataWorkflow")
	public IHICData udpateDataWorkflow(Object hicInputData)
	{
		IHICData hicObjectData = (IHICData) hicInputData;
		try
		{
			if (hicObjectData == null)
			{
				throw new Exception("hicdata null");
			}
			DatabaseConnectionSetting.checkConnectionSettings(hicObjectData);
			ConnectionDatabase.GetInstanceOfDatabaseComponent().setConnectionAvailable(true);
			hicObjectData = ConnectionDatabase.GetInstanceOfDatabaseComponent().updateData(hicObjectData);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			hicObjectData.getData().setReturnMessage(e.getMessage());
			logger.log(0, "[ERROR - " + e.getMessage() + "]");
			return hicObjectData;
		}
		return hicObjectData;
	}
	
	/**
	 * This method is used to get the status of all the dataobjects.
	 * @param hicInputData
	 * @return
	 */
	@EventSubscriber(topic = "getworkflowtoolstatus")
	public IHICData getWorkflowToolStatus(Object hicInputData)
	{
		WorkflowComponent.log(0, "getworkflowtoolstatus----invoked");
		WorkflowHelper workflowHelper=new WorkflowHelper();
		workflowHelper.setDoManager(doManager);
		workflowHelper.setWorkflowManager(workflowManager);
		IHICData hicObjectData=workflowHelper.getWorkflowToolStatus(hicInputData);
		return hicObjectData;

	} 
	/**
	 * This method is used to stop the ongoing processing of the 
	 * dataobjeect.
	 * @param inputHicData
	 * @return
	 */
	@EventSubscriber(topic="ProcessStopOperation")
	public IHICData executeStopOperation(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "inside the ProcessStopOperation--");
		WorkflowHelper workflowHelper=new WorkflowHelper();
		workflowHelper.setDoManager(doManager);
		workflowHelper.setWorkflowManager(workflowManager);
		inputHicData=workflowHelper.executeStopOperation(inputHicData);
		return inputHicData;
		
	}
	/**
	 * This method is used to stop,delete and removed the dataobjects  
	 * respective to the selected workflow.
	 * @param inputHicData
	 * @return
	 */
	@EventSubscriber(topic="ClearSelectedWorkflow")
	public IHICData executeClearSelectedWorkflow(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "inside the exectue of the ClearSelectedWorkflow");
		WorkflowHelper workflowHelper=new WorkflowHelper();
		workflowHelper.setDoManager(doManager);
		workflowHelper.setWorkflowManager(workflowManager);
		inputHicData=workflowHelper.executeClearSelectedWorkflow(inputHicData);
		return inputHicData;
	}
	/**
	 * This method is used to stop,delete and removed the dataobjects  
	 * respective to the selected workflow and selected patient.
	 * @param inputHicData
	 * @return
	 */
	@EventSubscriber(topic="ClearPatientWorkflow")
	public IHICData executeClearPatientWorkflow(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "inside the exectue of the ClearPatientWorkflow");
		WorkflowHelper workflowHelper=new WorkflowHelper();
		workflowHelper.setDoManager(doManager);
		workflowHelper.setWorkflowManager(workflowManager);
		inputHicData=workflowHelper.executeClearPatientWorkflow(inputHicData);
		return inputHicData;
	}
	
	/**
	 * This method is used to add new tool cateogry
	 * @param inputHicData
	 * @return
	 */
	@EventSubscriber(topic="AddNewToolCateogry")
	public IHICData addNewToolCategory(IHICData inputHicData) throws Exception
	{
		if (inputHicData == null || inputHicData.getData().getFormPattern().getFormValues() == null)
		{
			throw new Exception("HicObject or formvalues is null");
		}
		try
		{
			if (!isApplicationRegistered)
			{
				registerWindowWithApplication(inputHicData);
			}
		}
		catch (Exception e)
		{
		}

		return inputHicData;
	}
	
	public static void log(int level, Object message) 
	{
		System.out.println(message);
		if (logger != null) logger.log(level, message.toString());
	}
	
	
	public WorkflowManager getWorkflowManager()
	{
		return this.workflowManager;
	}
	/**
	 * This method is used to duplicate the selected workflow
	 * @param inputHicData
	 * @return
	 */
	@EventSubscriber(topic="DuplicateWorkflow")
	public IHICData duplicateWorkflow(IHICData inputHicData) throws Exception
	{
		WorkflowHelper workflowHelper= new WorkflowHelper();
		workflowHelper.setWorkflowManager(this.workflowManager);
		workflowHelper.duplicateWorkflow(inputHicData, ruleComponent);
		return inputHicData;
	}
	
	/**
	 * Visualizer handler
	 * @param dataObject
	 * @return
	 */
	@EventSubscriber(topic="OpenVisualiser")
	public IHICData openVisualiser(IHICData dataObject)
	{
		WorkflowComponent.log(0,"---OpenVisualiser-- hicdata--"+dataObject);
		VisualizerManager.callVisualizer(dataObject);
		return dataObject;
	}

	@Override
	public void setDataBaseSetting(IHICData hicData) {
	
		DatabaseConnectionSetting.setDataBaseSetting(hicData);
	}

}	




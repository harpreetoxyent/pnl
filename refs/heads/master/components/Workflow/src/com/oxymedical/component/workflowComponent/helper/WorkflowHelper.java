package com.oxymedical.component.workflowComponent.helper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Toolcateogry;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.dataObjectManager.IDataObjectManager;
import com.oxymedical.component.workflowComponent.dataobject.util.UniqueIDUtil;
import com.oxymedical.component.workflowComponent.db.impl.DataObjectQueueImpl;
import com.oxymedical.component.workflowComponent.db.impl.ErrorDBImpl;
import com.oxymedical.component.workflowComponent.db.impl.NodeDetailsImpl;
import com.oxymedical.component.workflowComponent.db.impl.ToolCategoryImpl;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowImpl;
import com.oxymedical.component.workflowComponent.erl.ERLCreator;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.nodeConnectorBuilder.INodeConnector;
import com.oxymedical.component.workflowComponent.nodeInfo.NodeObject;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;
import com.oxymedical.component.workflowComponent.operation.db.NodeCollectionOperation;
import com.oxymedical.component.workflowComponent.operation.db.NodeConnectorCollectionOperation;
import com.oxymedical.component.workflowComponent.operation.db.NodeOperation;
import com.oxymedical.component.workflowComponent.operation.status.WorkflowToolStatus;
import com.oxymedical.component.workflowComponent.workflowManager.WorkflowManager;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IWorkflowPattern;
import com.oxymedical.core.commonData.WorkflowPattern;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.util.HashUtil;

/**
 * @author hku
 *
 */
public class WorkflowHelper {
	
	private WorkflowManager workflowManager;
	
	private IDataObjectManager doManager;

	/**
	 * This method is used to save the new workflow
	 * from the ui.
	 * @param inputHicData
	 * @return
	 */
	public IHICData saveWorklowData(IHICData hicData,
			        RuleComponent ruleComponent) {

		Hashtable<String, Object> formValues = null;
		String windowFormID = null;

		try {
			IData data = hicData.getData();
			IFormPattern formPatternData = data.getFormPattern();
			windowFormID = formPatternData.getFormId();
			formValues = formPatternData.getFormValues();
			IApplication application = hicData.getApplication();
			String applicationName = application.getApplicationName();
			Hashtable databaseTableHash = createHashTablesForSave(
					applicationName, windowFormID, formValues);

			if (databaseTableHash != null) {
				Hashtable fieldDataValue = createFormValuesForSave(databaseTableHash);
				WorkflowComponent.log(0, "fieldDataValue---" + fieldDataValue);
				if (fieldDataValue != null) {
					List selectedNodesID = (List) fieldDataValue.get("nodeid");
					String isVisual = (String) fieldDataValue.get("isVisual");
					String workflowName = (String) fieldDataValue.get("name");
					String workFlowId = (String) fieldDataValue.get("id");
					String visualizerid = (String) fieldDataValue
							.get("visualizerid");
					int visualizer = 0;
					if (visualizerid != null)
						visualizer = Integer.parseInt(visualizerid);
					NodeCollectionOperation nodeCollectionOperation = new NodeCollectionOperation();

					Workflowinfo workflowinfo = nodeCollectionOperation
							.addWorkflowFromUI(workflowName, workFlowId,
									selectedNodesID, isVisual, visualizer);
					Hashtable<String, NodeObject> nodeHash = nodeCollectionOperation
							.getNodeObjectTableForWorkFlow(workflowinfo);

					NodeConnectorCollectionOperation connectorCollectionOperation = new NodeConnectorCollectionOperation();
					List<INodeConnector> connectorList = connectorCollectionOperation
							.getConnectorListForWorkflow(workflowinfo);
					if (connectorList != null && connectorList.size() > 0)
						this.workflowManager.getWorkflowCollection(
								hicData.getApplication())
								.getConnectorCollection().put(
										workflowinfo.getName(), connectorList);
					if (nodeHash != null && nodeHash.size() > 0)
						this.workflowManager.getWorkflowCollection(
								hicData.getApplication()).getNodeCollection()
								.put(workflowinfo.getName(), nodeHash);
					this.workflowManager.setWorkflowCollectionForApplication(hicData
							.getApplication());
					
					createERLForSpecficWorkflow(workflowinfo,ruleComponent,hicData);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hicData;
	}
	//
	
	/**
	 * This method is used to create erl for 
	 * the specfic workflow
	 * @param workflowinfo
	 * @param ruleComponent
	 * @param hicData
	 * @throws WorkflowComponentException
	 */
	private void createERLForSpecficWorkflow(Workflowinfo workflowinfo,RuleComponent ruleComponent,IHICData hicData) throws WorkflowComponentException
	{
		Object workflowObj = this.workflowManager.getWorkflowCollection(
				hicData.getApplication()).getNodeCollection().get(
				workflowinfo.getName());
		ERLCreator erlCreator = new ERLCreator();
		erlCreator.createERLBasedOnWorkflowObject(workflowinfo
				.getName(), workflowObj, hicData.getApplication()
				.getApplicationName());

		// TODO: we will change this code and create rete for only
		// the workfow added.
		String masterPageLocation = PropertyUtil
				.setUpProperties("GLASSFISH_DOMAIN_HOME")
				+ "/config/rules/masterpage.xml";
		try {
			// Should be called only once
			ruleComponent.buildRete(masterPageLocation);
		} catch (ComponentException e) {
			throw new WorkflowComponentException(e.getMessage());
		}
	}
  
	private Hashtable createHashTablesForSave(String applicationName,
			String baseWindow, Hashtable requestTable) {
		Hashtable databaseTableHash = new Hashtable();

		try {
			databaseTableHash = ConnectionDatabase
					.GetInstanceOfDatabaseComponent().getDataHandler()
					.getHashDataForApplication(applicationName, baseWindow,
							requestTable, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return databaseTableHash;
	}

	private Hashtable createFormValuesForSave(Hashtable databaseTableHash) {
		Hashtable fieldsData = new Hashtable();
		if (databaseTableHash != null
				&& databaseTableHash.get(WorkflowConstant.DBNAME) != null) {
			fieldsData = (Hashtable) databaseTableHash
					.get(WorkflowConstant.DBNAME);
			fieldsData = ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.getDataHandler().getFieldsFromHashData(fieldsData);

		}
		if (fieldsData != null) {
			return fieldsData;
		}
		return null;
	}

	public WorkflowManager getWorkflowManager() {
		return workflowManager;
	}

	public void setWorkflowManager(WorkflowManager workflowManager) {
		this.workflowManager = workflowManager;
	}
	
	/**
	 * This method is used to add new tool
	 * that is new node from the ui
	 * @param inputHicData
	 * @return
	 */
	public IHICData addNewNodeFromUI(IHICData hicData)
	{
		String windowFormID = null;
		Hashtable<String, Object> formValues = null;
		try
		{
				IData data = hicData.getData();
				IFormPattern formPatternData = data.getFormPattern();
				windowFormID = formPatternData.getFormId();
				formValues = formPatternData.getFormValues();
				IApplication application = hicData.getApplication();
				String applicationName = application.getApplicationName();
				Hashtable databaseTableHash = createHashTablesForSave(applicationName, windowFormID,
						formValues);
				if (databaseTableHash != null)
				{
					Hashtable fieldDataValue = createFormValuesForSave(databaseTableHash);
					WorkflowComponent.log(0, "fieldDataValue---"+fieldDataValue);
					if (fieldDataValue != null)
					{
						NodeOperation nodeOperation = new NodeOperation();
						nodeOperation.addNewNode(fieldDataValue, applicationName);
					}
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hicData;
	}
	
	/**
	 * This method is used to give the specific name
	 * for the workflow
	 * @param String
	 * @return
	 */
	
	private String getWorkflowNameForCopy(String workString) throws WorkflowComponentException
	{
		Workflowinfo workflowinfoCheck=WorkFlowImpl.getWorkflowBasedOnWorkflowName(workString);
		if(workflowinfoCheck!=null)
		{
			workString=WorkflowConstant.COPY + workString;
			workString=getWorkflowNameForCopy(workString);
		}		
		return workString;
	}
	/**
	 * This method is used to 
	 * add the new too cateogry
	 * @param inputHicData
	 * @return
	 */
	public IHICData addNewToolCategory(IHICData hicData)
	{
		Hashtable<String, Object> formValues = null;
		String windowFormID = null;
		try
		{
			IData data = hicData.getData();
			IFormPattern formPatternData = data.getFormPattern();
			windowFormID = formPatternData.getFormId();
			formValues = formPatternData.getFormValues();
			IApplication application = hicData.getApplication();
			String applicationName = application.getApplicationName();
			
			Hashtable databaseTableHash = createHashTablesForSave(applicationName, windowFormID, formValues);
			if (databaseTableHash != null)
			{
				Hashtable fieldDataValue = createFormValuesForSave(databaseTableHash);
				if (fieldDataValue != null)
				{
					String cateogryName = (String) fieldDataValue.get(WorkflowConstant.INPUT_TOOLCATEGORYNAME);
					String toolId = (String) fieldDataValue.get(WorkflowConstant.INPUT_TOOLCATEGORY);
					int toolCategoryId = 0;
					if (toolId != null) toolCategoryId = Integer.parseInt(toolId);
					
					Toolcateogry toolcateogry = ToolCategoryImpl.addToolCateogry(toolCategoryId, cateogryName);
				}
			}
		}
		catch (Exception e)
		{
		}
		return hicData;
	}
	
	/**
	 * This method is used to copy
	 * the selected workflow
	 * @param inputHicData
	 * @return
	 */
	public IHICData duplicateWorkflow(IHICData inputHicData,RuleComponent ruleComponent) throws Exception
	{
		String workflowName = (String) inputHicData.getData().getFormPattern().getFormValues().get(
				WorkflowConstant.INPUT_WORKFLOWNAME);
		if (workflowName != null)
		{
			Workflowinfo workflowinfoCheck=WorkFlowImpl.getWorkflowBasedOnWorkflowName(workflowName);
			List nodeDetailsList = NodeDetailsImpl.getNodeDetailsBasedOnWorkflowName(workflowName);
			workflowName = WorkflowConstant.COPY + workflowName;
			workflowName=this.getWorkflowNameForCopy(workflowName);
			NodeCollectionOperation nodeCollectionOperation = new NodeCollectionOperation();
			int nodeDetailId=workflowinfoCheck.getNodedetail()!=null ?workflowinfoCheck.getNodedetail().getId():0;
			Workflowinfo workflowinfo = nodeCollectionOperation.addWorkflowFromUI(workflowName, null,
					nodeDetailsList, "true",nodeDetailId);
			Hashtable<String, NodeObject> nodeHash = nodeCollectionOperation
					.getNodeObjectTableForWorkFlow(workflowinfo);
			NodeConnectorCollectionOperation connectorCollectionOperation = new NodeConnectorCollectionOperation();
			
			List<INodeConnector> connectorList = connectorCollectionOperation
					.getConnectorListForWorkflow(workflowinfo);
			this.workflowManager.getWorkflowCollection().getConnectorCollection().put(workflowinfo.getName(),
					connectorList);
			this.workflowManager.getWorkflowCollection().getNodeCollection().put(workflowinfo.getName(),
					nodeHash);
			this.workflowManager.setWorkflowCollectionForApplication(inputHicData.getApplication());
			createERLForSpecficWorkflow(workflowinfo,ruleComponent,inputHicData);
			
		}
		return inputHicData;
	}
	
	/**
	 * This method is used to clear and deleted the dataobjects 
	 * related to the selected workflow and for the selected patient
	 * @param inputHicData
	 * @return
	 */
	public IHICData executeClearPatientWorkflow(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "inside the exectue of the ClearPatientWorkflow");
		String unqiueId = inputHicData.getUniqueID();
		String formPattern = inputHicData.getData().getFormPattern().getFormId();
		
		Hashtable inputFormValues = new Hashtable();
		inputFormValues = inputHicData.getData().getFormPattern().getFormValues();
		String status = inputHicData.getData().getStatus();
		
		String workflowName = (String) inputHicData.getData().getFormPattern().getFormValues().get(
				WorkflowConstant.INPUT_WORKFLOWNAME);
		
		Hashtable<String, Object> metaDataForExist = HashUtil.removeNonRequiredKeysFromHashtable(
				ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, inputHicData.getData().getFormPattern()
						.getFormValues());
		
		String unqiueIdcreated = UniqueIDUtil.getUniqueIdForDBSearch(metaDataForExist, workflowName);
		try
		{
			List<Object[]> returnObjs = DataObjectQueueImpl.checkDataObjectForUser(null, null, null,
					inputHicData.getData().getUserPatternString(), inputHicData.getData().getUserId(),
					unqiueIdcreated, null, workflowName, null, metaDataForExist);
			stopDeleteDataObject(returnObjs,inputHicData);
		}
		catch (Exception e)
		{
			inputHicData.getData().setReturnMessage(e.getMessage());
			WorkflowComponent.logger.log(0, "[ERROR - " + e.getMessage() + "]");
			return inputHicData;
		}
		
		inputHicData.getData().getFormPattern().setFormId(formPattern);
		inputHicData.getData().getFormPattern().setFormValues(inputFormValues);
		inputHicData.setUniqueID(unqiueId);
		inputHicData.getData().setStatus(status);
		return inputHicData;
	}
	
	/**
	 * This methods is used to stop and deleted 
	 * and remove the dataobjects from the node
	 * @param returnObjs
	 * @param inputHicData
	 */
	private void stopDeleteDataObject(List<Object[]> returnObjs,IHICData inputHicData)
	{
	try
	{
		if (returnObjs!=null && returnObjs.size() > 0)
		{
			WorkflowComponent.log(0, "returnObjs----" + returnObjs.size());
			Dataobject dObject = null;
			for (int i = 0; i < returnObjs.size(); i++)
			{
				dObject = (Dataobject) returnObjs.get(i)[0];
				stopDeleteDataObject(dObject, inputHicData);
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * This methods is used to stop and deleted 
	 * and remove the specfic dataobject from the node s
	 * @param returnObjs
	 * @param inputHicData
	 */
	private void stopDeleteDataObject(Dataobject dObject,IHICData inputHicData)
	{
		try
		{
		IWorkflowPattern workflowPatten = new WorkflowPattern();
		workflowPatten.setWorkflowNode(dObject.getWorkflownodeinfo().getNodeinfo().getNodename());
		workflowPatten.setWorkflowPattern(dObject.getWorkflownodeinfo().getWorkflowinfo().getName());
		workflowPatten.setWorkflowNodeStatus(dObject.getStatus());
		inputHicData.getData().setWorkflowPattern(workflowPatten);
		
		inputHicData.setUniqueID(dObject.getUniqueid());
		inputHicData.getData().setStatus(dObject.getStatus());
		inputHicData.getData().getDataPattern().setDataPatternId(dObject.getDatapattern());
		inputHicData.getData().getFormPattern().setFormId(dObject.getFormpattern());					
		inputHicData=UniqueIDUtil.updateHICDataFromUnqiueID(dObject.getUniqueid(), inputHicData);
		inputHicData = doManager.updateFormValuesWithDBData(inputHicData);
		if (dObject.getNodeexecutionstatus().equalsIgnoreCase(
				WorkflowConstant.NODE_EXECUTION_STATUS_PROGRESS))
		{
			List<String> functions = new ArrayList<String>();
			functions.add("stopworkflowtool");
			inputHicData = this.workflowManager.performActionGetInfoFromWorkflow(inputHicData,
					functions)/* executeFunctions(functions,inputHicData) */;
		}
		
		doManager.removeDataObjectFromNode(dObject.getWorkflownodeinfo().getWorkflowinfo()
				.getName(), dObject.getWorkflownodeinfo().getNodeinfo().getNodename(),
				inputHicData, false);
		
		this.getDoManager().deleteToDataObjectMetaData(dObject);
		ErrorDBImpl.deleteErrorInfoForDataObject(dObject);
		this.getDoManager().deleteToDataObject(dObject);
		}
		catch (Exception e) {
		}
	}

	public IDataObjectManager getDoManager() {
		return doManager;
	}

	public void setDoManager(IDataObjectManager doManager) {
		this.doManager = doManager;
	}
	
	/**
	 * This method is used to clear and deleted the dataobjects 
	 * related to the all the  workflows
	 * @param inputHicData
	 * @return
	 */
	public IHICData executeClearAllWorkflow(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "ProcessClearAllWorkflow invoked");
		String unqiueId = inputHicData.getUniqueID();
		String formPattern = inputHicData.getData().getFormPattern().getFormId();
		
		Hashtable inputFormValues = new Hashtable();
		inputFormValues = inputHicData.getData().getFormPattern().getFormValues();
		String status = inputHicData.getData().getStatus();
		List<Dataobject> output;
		
		try
		{
			output = DataObjectQueueImpl.getAllDataObjects();
			if ((output != null) && (output.size() > 0))
			{
				for (Dataobject doDB : output)
				{
					if (doDB != null)
					{
						stopDeleteDataObject(doDB, inputHicData);
					}
				}
			}
			WorkflowComponent.log(0, "ProcessClearAllWorkflow Finished");
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
			inputHicData.getData().setReturnMessage(e.getMessage());
			WorkflowComponent.logger.log(0, "[ERROR - " + e.getMessage() + "]");
			return inputHicData;
		}
		inputHicData.getData().getFormPattern().setFormId(formPattern);
		inputHicData.getData().getFormPattern().setFormValues(inputFormValues);
		inputHicData.setUniqueID(unqiueId);
		inputHicData.getData().setStatus(status);
		return inputHicData;
	}
	
	
	/**
	 * This method is used to clear and deleted the dataobjects 
	 * related to the selected workflow
	 * @param inputHicData
	 * @return
	 */
	public IHICData executeClearSelectedWorkflow(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "inside the exectue of the ClearSelectedWorkflow");
		String unqiueId = inputHicData.getUniqueID();
		String formPattern = inputHicData.getData().getFormPattern().getFormId();
		
		Hashtable inputFormValues = new Hashtable();
		inputFormValues = inputHicData.getData().getFormPattern().getFormValues();
		String status = inputHicData.getData().getStatus();

		String workflowName = (String) inputHicData.getData().getFormPattern().getFormValues().get(
				WorkflowConstant.INPUT_WORKFLOWNAME);
		try
		{
			List<Object[]> returnObjs = DataObjectQueueImpl.checkDataObjectForUser(null, null, null,
					inputHicData.getData().getUserPatternString(), inputHicData.getData().getUserId(), null,
					null, workflowName, null, null);
			stopDeleteDataObject(returnObjs,inputHicData);
		}
		catch (Exception e)
		{
			inputHicData.getData().setReturnMessage(e.getMessage());
			WorkflowComponent.logger.log(0, "[ERROR - " + e.getMessage() + "]");
			return inputHicData;
		}
		inputHicData.getData().getFormPattern().setFormId(formPattern);
		inputHicData.getData().getFormPattern().setFormValues(inputFormValues);
		inputHicData.setUniqueID(unqiueId);
		inputHicData.getData().setStatus(status);
		return inputHicData;
	}
	/**
	 * This method is used to get the status
	 * of the all the dataobjects.
	 * @param hicInputData
	 * @return
	 */
	public IHICData getWorkflowToolStatus(Object hicInputData)
	{
		WorkflowComponent.log(0, "getworkflowtoolstatus--workflow helper--invoked");
		IHICData hicObjectData = (IHICData) hicInputData;
		IHICData hicDataForStatus = (IHICData) hicObjectData.clone();
		hicDataForStatus = hicObjectData;

		// TODO This should come from the clone method. On cloning we have to
		// work.
		String unqiueId = hicObjectData.getData().getStatus();
		Hashtable inputFormValues = new Hashtable();
		inputFormValues = hicObjectData.getData().getFormPattern().getFormValues();
		String status = hicObjectData.getData().getStatus();
		
		IData data = hicObjectData.getData();
		QueryData queryResponseData = data.getQueryData();
		String queryStr = data.getQueryData().getCondition();
		
		String patientId = inputFormValues.get(ApplicationConstant.KEY_PATIENT_ID) != null 
								? (String) inputFormValues.get(ApplicationConstant.KEY_PATIENT_ID)
										: null;
		String patientMRN = inputFormValues.get(ApplicationConstant.KEY_PATIENT_MRN) != null 
								? (String) inputFormValues.get(ApplicationConstant.KEY_PATIENT_MRN)
										: null;
		
		String uniqueID = null;
		String nodeExecutionStatus=inputFormValues.get(WorkflowConstant.INPUT_NODESTATUS) != null 
		? (String) inputFormValues.get(WorkflowConstant.INPUT_NODESTATUS)
				: null;
					
		
		if (patientId != null && patientMRN != null)
			uniqueID = UniqueIDUtil.getUniqueIdForDBSearch(hicObjectData.getData().getFormPattern()
					.getFormValues(), null);
		
		String userId = data.getUserId();
		WorkflowToolStatus workflowStatus = new WorkflowToolStatus();
		List<Object> returnObjects = workflowStatus.getListOfDataObjectsForStatus(queryStr, userId, uniqueID, nodeExecutionStatus);
		
		try
		{
			if (returnObjects != null && returnObjects.size() > 0)
			{
				List<String> functions = new ArrayList<String>();
				functions.add("getworkflowtoolstatus");
				List<Object> formValuesList = (List) returnObjects.get(1);
				List<Object> allValues = (List) returnObjects.get(0);
				
				if (formValuesList != null && formValuesList.size() > 0)
				{
					for (int i = 0; i < formValuesList.size(); i++)
					{
						Hashtable formValues = (Hashtable) formValuesList.get(i);
						String nodeExecutionstatus = (String) formValues
								.get(WorkflowConstant.INPUT_NODEEXECUTIONSTATUS);
						
						if (nodeExecutionstatus
								.equalsIgnoreCase(WorkflowConstant.NODE_EXECUTION_STATUS_WAITING))
						{
							Object[] objs = (Object[]) allValues.get(i);
							continue;
						}
						else
						{
							hicDataForStatus = workflowStatus.createHICDataForStatus(hicDataForStatus,
									formValues);
							hicDataForStatus = this.workflowManager.performActionGetInfoFromWorkflow(
									hicDataForStatus, functions) 
							/* executeFunctions(functions, hicDataForStatus)*/;
							
							Object[] objs = (Object[]) allValues.get(i);
							Object obj = hicDataForStatus.getData().getReturnedData();
							int length = objs.length;
							objs[objs.length - 1] = obj;
						}
					}
				}
				hicObjectData.getData().getQueryData().setListData(allValues);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			hicObjectData.getData().setReturnMessage(e.getMessage());
			WorkflowComponent.logger.log(0, "[ERROR - " + e.getMessage() + "]");
			return hicObjectData;
		}
		
		hicObjectData.getData().setStatus(status);
		hicObjectData.setUniqueID(unqiueId);
		hicObjectData.getData().getFormPattern().setFormValues(inputFormValues);
		return hicObjectData;

	}
	//
	
  /**
   * This method is used to stop the on going processing of the
   * dataobject.
 * @param inputHicData
 * @return
 */
public IHICData executeStopOperation(IHICData inputHicData)
	{
		WorkflowComponent.log(0, "inside the ProcessStopOperation--workflow helper");
		try
		{
			// TODO This should come from the clone method. On cloning we have
			// to work.
			String unqiueId = inputHicData.getUniqueID();
			Hashtable inputFormValues = new Hashtable();
			inputFormValues = inputHicData.getData().getFormPattern().getFormValues();
			String status = inputHicData.getData().getStatus();
			
			//IWorkflowPattern origWP = (IWorkflowPattern) inputHicData.getData().getWorkflowPattern().clone();
			
			
			WorkflowToolStatus workflowStatus = new WorkflowToolStatus();
			inputHicData = workflowStatus.createHICDataForStatus(inputHicData, inputFormValues);
			inputHicData = UniqueIDUtil.updateUniqueId(inputHicData);
			
			List<String> functions = new ArrayList<String>();
			functions.add("stopworkflowtool");
			inputHicData = this.workflowManager.performActionGetInfoFromWorkflow(inputHicData, functions)
			/* executeFunctions(functions, inputHicData) */;
			
			boolean isStopped = false;
			if (inputHicData.getData().getReturnedData() != null)
			{
				isStopped = (Boolean) inputHicData.getData().getReturnedData();
			}

			if (isStopped)
			{
				inputHicData.getData().setStatus(status);
				inputHicData.setUniqueID(unqiueId);
				inputHicData.getData().getFormPattern().setFormValues(inputFormValues);
				inputHicData.getData().setReturnedData(isStopped);
				//inputHicData.getData().setWorkflowPattern(origWP);
				return inputHicData;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			inputHicData.getData().setReturnMessage(e.getMessage());
			WorkflowComponent.logger.log(0, "[ERROR - " + e.getMessage() + "]");
			return inputHicData;
		}
		return inputHicData;
	}

/**
 * This method is added to revert the node for the exception of the tool.
 * @param inputHicData
 * @return
 */
public IHICData executeRevertOperation(IHICData inputHicData)
{
	Dataobject doDB = null;
	Hashtable inputValues = inputHicData.getData().getFormPattern().getFormValues();
	String workflowId = (String) inputValues.get(WorkflowConstant.INPUT_WORKFLOWNAME);
	String workflowNodeId = (String) inputValues.get(WorkflowConstant.INPUT_NODENAME);
	String workflowNodeStatus = (String) inputValues.get(WorkflowConstant.INPUT_NODESTATUS);
	String userId = inputHicData.getData().getUserId();
	try
	{
		Hashtable<String, Object> metaDataForExist = HashUtil.removeNonRequiredKeysFromHashtable(
				ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, inputValues);
		
		// Get List of Dataobject from database
		List<Object[]> output 
				= DataObjectQueueImpl.checkDataObjectForUser(
						null, null, workflowNodeStatus, 
						inputHicData.getData().getUserPatternString(), 
						inputHicData.getData().getUserId(), null, null, 
						workflowId, workflowNodeId, metaDataForExist);
		
		if ((output != null) && (output.size() > 0))
			doDB = (Dataobject) output.get(0)[0];
		
		if (doDB != null)
		{
			String unquieId = doDB.getUniqueid();
			ErrorDBImpl.deleteErrorInfoForDataObject(doDB);
			
			DataObjectQueueImpl.setDataObjectExecutionStatus(workflowId, workflowNodeId, userId,
					unquieId, WorkflowConstant.NODE_EXECUTION_STATUS_WAITING, metaDataForExist);
		}
	}
	catch (WorkflowComponentException e)
	{
		e.printStackTrace();
		inputHicData.getData().setReturnMessage(e.getMessage());
		WorkflowComponent.logger.log(0, "[ERROR - " + e.getMessage() + "]");
		return inputHicData;
	}
	inputHicData.getData().setWorkflowPattern(null);
	inputHicData.getData().setStatus(null);
	return inputHicData;
}



}

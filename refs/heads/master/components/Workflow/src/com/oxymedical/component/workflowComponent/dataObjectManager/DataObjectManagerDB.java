package com.oxymedical.component.workflowComponent.dataObjectManager;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Dataobjectmetadata;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.dataobject.util.DataObjectListUtil;
import com.oxymedical.component.workflowComponent.dataobject.util.UniqueIDUtil;
import com.oxymedical.component.workflowComponent.db.impl.DataObjectMetaDataImpl;
import com.oxymedical.component.workflowComponent.db.impl.DataObjectQueueImpl;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowImpl;
import com.oxymedical.component.workflowComponent.db.impl.WorkFlowNodeInfoImpl;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.hic.NOLISRuntimeHandler;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.core.util.HashUtil;
import com.oxymedical.core.util.number.NumberUtil;

public class DataObjectManagerDB implements IDataObjectManager
{

	@Override
	public List<IHICData> getAllMatchingDataObjectForUser(IHICData incomingDataObject)
	{
		return null;
	}


	@Override
	public Hashtable<String, Hashtable<String, List<IHICData>>> getMatchingDataObject(
			IHICData incomingDataObject)
	{
		return null;
	}

	@Override
	public IHICData getCompletedMatchingDataObjectForUser(IHICData incomingDataObject)
	{
		return getMatchingDataObjectForUser(
				incomingDataObject, WorkflowConstant.NODE_EXECUTION_STATUS_COMPLETED);
	}
	
	@Override
	public IHICData getMatchingDataObjectForUser(IHICData incomingDataObject)
	{
		return getMatchingDataObjectForUser(incomingDataObject, null);
	}

	@Override
	public IHICData getMatchingDataObjectForUser(IHICData incomingDataObject, String nodeExecStatus)
	{
		/*String tempUniqueId = incomingDataObject.getUniqueID();
		
		// Update DO unique id to Patient ID which is unique for each patient
		updateDataObjectUniqueId(incomingDataObject);*/

		// For UniqueId to be same across server restart, search should not be based on UniqueId
		List<Object[]> output 
			= DataObjectQueueImpl.checkDataObjectForUser(incomingDataObject, nodeExecStatus, false);
		
		// Check for null if list of data object 
		if (output != null)
		{
			boolean idSet=false;
			for (int i = 0; i < output.size(); i++)
			{
				Dataobject dObj = (Dataobject) output.get(i)[0];
				
				if (dObj != null)
				{
					//This check is added in case more than one dataoject is recieved for same node exection status.
					//So a unqiue id check is added if the stored dataobject and incoming dataobject has same 
					//unqiue id and then unqiue id set
					if(output.size()>1)
					{
						incomingDataObject=UniqueIDUtil.updateUniqueId(incomingDataObject);
						String uniqueId=incomingDataObject.getUniqueID();
						if(uniqueId.equalsIgnoreCase(dObj.getUniqueid()))
					       idSet=true;
					}
					// Update incoming data object					
					incomingDataObject = NOLISRuntimeHandler.prepareHICData(incomingDataObject);
					incomingDataObject.getData().getFormPattern().setFormId(dObj.getFormpattern());
					incomingDataObject.getData().getDataPattern().setDataPatternId(dObj.getDatapattern());
					// TODO create User Pattern info also
					// incomingDataObject.getData().a
					incomingDataObject.getData().setStatus(dObj.getStatus());
					incomingDataObject.setUniqueID(dObj.getUniqueid());	
					
					incomingDataObject.getData().getWorkflowPattern().setWorkflowPattern(dObj.getWorkflownodeinfo().getWorkflowinfo().getName());
					incomingDataObject.getData().getWorkflowPattern().setWorkflowNode(dObj.getWorkflownodeinfo().getNodeinfo().getNodename());
					
					// Fetch meta data for this data object
					/*List<Dataobjectmetadata> doMetadataList = DataObjectMetaDataImpl.getAllMetaDataForDataObject(dObj.getId());
					if (doMetadataList != null)
					{
						// Create form values
						Hashtable<String, Object> formValues = incomingDataObject.getData().getFormPattern().getFormValues();
						if (formValues == null) formValues = new Hashtable<String, Object>();
						
						ArrayList toolOutValues= new ArrayList();
						for (int j = 0; j < doMetadataList.size(); j++)
						{
							Dataobjectmetadata metaData = doMetadataList.get(j);
							if ((formValues.containsKey(WorkflowConstant.TOOL_OUTPUT_FILE)) 
									&& (metaData.getDatakey().equals(WorkflowConstant.TOOL_OUTPUT_FILE)))
							{
								Object obj = formValues.get(WorkflowConstant.TOOL_OUTPUT_FILE);
								if(obj instanceof ArrayList)
								{
									toolOutValues = (ArrayList)obj;

									if (!toolOutValues.contains(metaData.getDatavalue()))
										toolOutValues.add(metaData.getDatavalue());
									formValues.put(WorkflowConstant.TOOL_OUTPUT_FILE,toolOutValues);
								}
								else
								{
									String toolValue = obj.toString();

									if (!toolOutValues.contains(toolValue))
										toolOutValues.add(toolValue);
									if (!toolOutValues.contains(metaData.getDatavalue()))
										toolOutValues.add(metaData.getDatavalue());
									formValues.put(WorkflowConstant.TOOL_OUTPUT_FILE,toolOutValues);
								}
							}
							else
							{	
								// Add entries to Form Values; but do not over-write
								if (!formValues.containsKey(metaData.getDatakey()))
									formValues.put(metaData.getDatakey(), metaData.getDatavalue());
							}
						}
						
						// Set Form Values
						incomingDataObject.getData().getFormPattern().setFormValues(formValues);
					}*/
					
					// Sent back the first matching data according to input received.
					if(output.size()>1 && i<output.size() && !idSet)
					{
						continue;
					}
					else
						break;
				}
			}
		}
		
		/*incomingDataObject.setUniqueID(tempUniqueId);*/
		
		return incomingDataObject;
	}


	@Override
	public List<IHICData> getMatchingDataObjectForWorkflowNode(String workflowIdKey, String nodeIdKey,
			IHICData incomingDataObject)
	{
		return null;
	}


	@Override
	public boolean isDOPresentOnWorkflowNode(String workflowIdKey, String nodeIdKey,
			IHICData incomingDataObject)
	{
		return false;
	}


	@Override
	public void removeDataObjectFromNode(String workflowId, String nodeId, IHICData dataObject)
	{
		WorkflowComponent.log(0, "[DataObjectManagerDB][removeDataObjectFromNode]");
		updateDataObjectStatusInDBQueue(workflowId, nodeId, dataObject);
	}
	
	/**
	 * Updating status of DO in Database. This function is called from
	 * removeDataObjectFromNode. Here we are not removing entry from DB since we
	 * would require meta data information about DO at each node in workflow.
	 * 
	 * @param workflowId
	 * @param nodeId
	 * @param dataObject
	 */
	private void updateDataObjectStatusInDBQueue(String workflowId, String nodeId, IHICData dataObject)
	{
		HashMap<String, Object> doDetails = dataObject.getDataObjectDetails();
		try
		{
			// This makes sure that the current call is pertaining to Workflow tool
			if (!isPatientSchedulePresentInFormValues(dataObject)) return;

			Hashtable<String, Object> metaData 
					= HashUtil.removeNonRequiredKeysFromHashtable(
							ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, 
							(Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES));
			
			DataObjectQueueImpl.setDataObjectAsCompleted
			(
				workflowId, nodeId, 
				StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_ID)), 
				StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_UNIQUE_ID)),
				metaData
			);
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void storeDataObjectOnNode(String workflowId, String nodeId, IHICData dataObject)
	{
		addDataObjectToDBQueue(workflowId, nodeId, dataObject);
	}
	
	
	/**
	 * Function for adding DO to Node Queue in database.
	 * @param workflowId
	 * @param nodeId
	 * @param dataObject
	 */
	private void addDataObjectToDBQueue(String workflowId, String nodeId, IHICData dataObject)
	{
		try
		{
			// This makes sure that the current call is pertaining to Workflow tool
			if (!isPatientSchedulePresentInFormValues(dataObject)) return;
			
			// Disallow non-visual WF to store entry in DB
			Workflowinfo workflow = WorkFlowImpl.getWorkflowBasedOnWorkflowName(workflowId);
			Boolean isVisual = workflow.getIsVisual();
			if (isVisual == null) return;
			if (!isVisual.booleanValue()) return;

			/*// Update DO unique id to Patient ID which is unique for each patient
			updateDataObjectUniqueId(dataObject);*/
			
			// Get WorkflowNodeInfo for current nodeId
			Workflownodeinfo wfNodeInfo = WorkFlowNodeInfoImpl.getWorkFlowNodeInfo(workflowId, nodeId);
			


			/*
			 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			 */
			DataObjectListUtil.printDataObject(dataObject, "THIS IS WHAT HAS COME FOR SAVE", "*");
			WorkflowComponent.log(0, 
					"[workflowId]" + workflowId 
					+ "\n[nodeId]" + nodeId 
					+ "\n[NODE_EXECUTION_STATUS]" 
						+ ((wfNodeInfo.getNodeinfo().getNodetype().indexOf("I") >= 0) 
							? WorkflowConstant.NODE_EXECUTION_STATUS_WAITING 
							: WorkflowConstant.NODE_EXECUTION_STATUS_PROGRESS));
			/*
			 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			 */

			
			// Store DataObject
			HashMap<String, Object> doDetails = dataObject.getDataObjectDetails();

			// Create meta data based on which search is required
			Hashtable<String, Object> metaData 
					= HashUtil.removeNonRequiredKeysFromHashtable(
							ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, 
							(Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES));
			
			Dataobject doQueue 
				= DataObjectQueueImpl.addDataObjectToDbQueueWithExistingCheck(
						StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_FORM_PATTERN)), 
						StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_DATA_PATTERN)), 
						StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_STATUS)), 
						wfNodeInfo, 
						StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_PATTERN)), 
						StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_ID)), 
						StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_UNIQUE_ID)), 
						(wfNodeInfo.getNodeinfo().getNodetype().indexOf("I") >= 0) 
								? WorkflowConstant.NODE_EXECUTION_STATUS_WAITING 
								: WorkflowConstant.NODE_EXECUTION_STATUS_PROGRESS,
						metaData);


			// Store DataObject Metadata
			appendToDataObjectMetaData(workflowId, nodeId, dataObject, doQueue);
			
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	public void appendToDataObjectMetaData(String workflowId, String nodeId, IHICData dataObject,
			Dataobject doDb) throws WorkflowComponentException
	{
		// Store DataObject Metadata
		Hashtable<String, Object> hash = dataObject.getData().getFormPattern().getFormValues();
		Enumeration<String> hashKeys = hash.keys();

		while (hashKeys.hasMoreElements())
		{
			String key = hashKeys.nextElement();
			/*if(hash.get(key) instanceof ArrayList)
			{
				ArrayList toolValues=(ArrayList)hash.get(key);
				for(int i=0;i<toolValues.size();i++)
				{
					String value = toolValues.get(i).toString();
					DataObjectMetaDataImpl.addDataObjectMetadataWithExistingCheck(doDb, key, value);
				}
				
			}
			else
			{*/
			Object value = hash.get(key);
			DataObjectMetaDataImpl.addDataObjectMetadataWithExistingCheck(doDb, key, value);
			/*}*/
		}
	}
	

	/** Instead "isPatientSchedulePresentInFormValues" function should be used */
	boolean isPatientPresentInFormValues(IHICData incomingDataObject)
	{
		String pId = getPatientIdFromFormValues(incomingDataObject);
		return (pId != null) ? true : false;
	}
	
	boolean isPatientSchedulePresentInFormValues(IHICData incomingDataObject)
	{
		return DataObjectListUtil.isPatientSchedulePresentInFormValues(incomingDataObject);
		/*boolean infoPresent = true;
		String pId = getPatientIdFromFormValues(incomingDataObject);
		infoPresent = infoPresent && ((pId != null) ? true : false);
		String sId = getScheduleIdFromFormValues(incomingDataObject);
		infoPresent = infoPresent && ((sId != null) ? true : false);
		
		return infoPresent;*/
	}
	
	String getPatientIdFromFormValues(IHICData incomingDataObject)
	{
		return DataObjectListUtil.getPatientIdFromFormValues(incomingDataObject);
		
		/*HashMap<String, Object> doDetails = incomingDataObject.getDataObjectDetails();
		
		Hashtable<String, Object> formValues = 
					(Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES);
		
		String patientId = null;
		if (formValues != null)
			patientId = (String) formValues.get(ApplicationConstant.KEY_PATIENT_ID);
		
		return patientId;*/
	}
	
	String getScheduleIdFromFormValues(IHICData incomingDataObject)
	{
		return DataObjectListUtil.getScheduleIdFromFormValues(incomingDataObject);
		
		/*HashMap<String, Object> doDetails = incomingDataObject.getDataObjectDetails();
		
		Hashtable<String, Object> formValues = 
					(Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES);
		
		String scheduleId = null;
		if (formValues != null)
			scheduleId = (String) formValues.get(ApplicationConstant.KEY_SCHEDULE_ID);
		
		return scheduleId;*/
	}

	
	/*private void updateDataObjectUniqueId(IHICData dataObject)
	{
		String patientId = (String) dataObject.getData().getFormPattern().getFormValues().get(ApplicationConstant.KEY_PATIENT_ID);
		if (patientId != null) dataObject.setUniqueID(patientId);
	}*/



	/**
	 * Updates the status according to the given status.
	 * 
	 */
	@Override
	public void updateDataObjectFromNodeOnGivenStatus(String workflowId,
			String nodeId, IHICData dataObject, String status) 
	{
		updateDataObjectGivenStatusInDBQueue(workflowId,nodeId, dataObject,status);
		
	}
	/**
	 * Updating status of DO in Database in case of the error and update the status of pervious node
	 * to waiting. This function is called from
	 * removeDataObjectFromNode. Here we are not removing entry from DB since we
	 * would require meta data information about DO at each node in workflow.
	 * 
	 * @param workflowId
	 * @param nodeId
	 * @param dataObject
	 */
	private void updateDataObjectGivenStatusInDBQueue(String workflowId, String nodeId, IHICData dataObject,String status)
	{
		HashMap<String, Object> doDetails = dataObject.getDataObjectDetails();
		try
		{
			// This makes sure that the current call is pertaining to Workflow tool
			if (!isPatientPresentInFormValues(dataObject)) return;

			Hashtable<String, Object> metaData 
					= HashUtil.removeNonRequiredKeysFromHashtable(
							ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, 
							(Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES));
			DataObjectQueueImpl.setDataObjectExecutionStatus
			(
				workflowId, nodeId, 
				StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_ID)), 
				StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_UNIQUE_ID)),				
				status,
				metaData
			);
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
		}
	}

  /**
   * This method is used to delete the objectmetadata
   * in case of error and reverting the tool to previous status.
   */

	@Override
	public void deleteToDataObjectMetaData(Dataobject doDb)
			throws WorkflowComponentException 
	{
	
		List<Dataobjectmetadata> dataobjectMetaDataList=DataObjectMetaDataImpl.getAllMetaDataForDataObject(doDb.getId());
		if(dataobjectMetaDataList!=null)
		{
			for(int i=0;i<dataobjectMetaDataList.size();i++)
			{
				Dataobjectmetadata dataMetaObject=dataobjectMetaDataList.get(i);
				DataObjectMetaDataImpl.deleteMetaDataObject(dataMetaObject);
			}
		}
	}

	
	/**
	 * This method is used to add the delete do object 
	 * in case of the error
	 *  
	 */
	@Override
	public void deleteToDataObject(Dataobject doDb)
			throws WorkflowComponentException 
	{
		DataObjectQueueImpl.deleteDataObject(doDb);
		
	}
	

	@Override
	public IHICData updateFormValuesWithDBData(IHICData incomingDO)
	{
		HashMap<String, Object> map = incomingDO.getDataObjectDetails();
		
		WorkflowComponent.log(0, "[INSIDE FUNCTION - updateFormValuesWithDBData]");
		String patientId = getPatientIdFromFormValues(incomingDO);
		WorkflowComponent.log(0, "[INSIDE FUNCTION - updateFormValuesWithDBData]; patientId=" + patientId);
		if (patientId !=null)
		{
			String scheduleId = getScheduleIdFromFormValues(incomingDO);
			WorkflowComponent.log(0, "[INSIDE FUNCTION - updateFormValuesWithDBData]; scheduleId=" + scheduleId);

			HashMap<String, Object> ret 
					= DataObjectListUtil.getPatientDOFormValuesFromDB(
							patientId, scheduleId, 
							(String)map.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN), 
							(String)map.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE));
			if (ret != null)
			{
				Hashtable<String, Object> formValues = incomingDO.getData().getFormPattern().getFormValues();
				
				Iterator<String> keyIter = ret.keySet().iterator();
				while (keyIter.hasNext())
				{
					String key = keyIter.next();
					if (!formValues.containsKey(key)) formValues.put(key, ret.get(key));
					WorkflowComponent.log(0, "[INSIDE FUNCTION - updateFormValuesWithDBData]\t[key]" + key + "\t[value]" + ret.get(key));
				}
			}
		}
		return incomingDO;
	}
	

	public void addToHangingObjects(String workflowName, IHICData dataObject)
	{
		// TODO
	}
	
	public void removeFromHangingObjects(String workflowName, IHICData dataObject)
	{
		// TODO
	}


	/**
	 * Updating status of DO in Database. This is required so that for each
	 * event is able to update its own status in data object.
	 * 
	 * @param workflowId
	 * @param nodeId
	 * @param dataObject
	 * @param status
	 */
	@Override
	public void updateDataObjectStatus(String workflowId,
			String nodeId, IHICData dataObject, String status) 
	{
		// Update in database
		updateDataObjectFromNodeOnGivenStatus(workflowId, nodeId, dataObject,status);
	}

	@Override
	public void removeDataObjectFromNode(String workflowId, String nodeId,
			IHICData dataObject, boolean performDbOperation) 
	{
		// Nothing
	}
}

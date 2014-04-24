package com.oxymedical.component.workflowComponent.dataobject.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Dataobjectmetadata;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.db.impl.DataObjectMetaDataImpl;
import com.oxymedical.component.workflowComponent.db.impl.DataObjectQueueImpl;
import com.oxymedical.component.workflowComponent.operation.db.WorkflowDatabaseInfo;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.constants.CoreConstants;

public class DataObjectListUtil
{
	/**
	 * Each entry in list if an array with the following order of columns
	 * 	<ol>
	 * 		<li>Workflow Name</li>
	 * 		<li>Node Name</li>
	 * 		<li>Current DO Status / Node Status</li>
	 * 		<li>DO Unique Id</li>
	 * 	</ol>
	 * @param hicDataList
	 * @return
	 */
	public static List<Object> createDOListForUser(List<IHICData> hicDataList)
	{
		List<Object> retList = null;
		if (hicDataList == null) return retList;
		
		for (int i=0; i < hicDataList.size(); i++)
		{
			IHICData hicData = hicDataList.get(i);
			String[] entry = new String[4];
			
			entry[0] = hicData.getData().getWorkflowPattern().getWorkflowPattern();
			entry[1] = hicData.getData().getWorkflowPattern().getWorkflowNode();
			entry[2] = hicData.getData().getWorkflowPattern().getWorkflowNodeStatus();
			entry[3] = hicData.getUniqueID();
			
			if (retList == null) retList = new ArrayList<Object>();
			retList.add(entry);
		}
		return retList;
	}
	
	
	public static void printDataObject(IHICData dObj, String title, String charType)
	{
		/*
		 * Uncomment these two lines when you want to debug. Ideally we should
		 * use LoggingComponent here to debug. That is a TODO
		 */
		if (dObj == null) return;
		WorkflowComponent.log(0, "[******************** " + title + " ********************]");
		WorkflowComponent.log(0, dObj.toString());
	}


	/**
	 * Returns hashmap of all meta data related to current patient / schedule
	 * for all nodes of the workflow
	 * 
	 * @param patientId
	 * @param scheduleId
	 * @return
	 */
	public static HashMap<String, Object> getPatientDOFormValuesFromDB(String patientId, String scheduleId, String workflowName, String nodeName)
	{
		WorkflowComponent.log(0, "[INSIDE FUNCTION - getPatientDOFormValuesFromDB]\t PATIENT ID=" +patientId+ "" + "\t[SCHEDULE ID]" +scheduleId+ "");
		HashMap<String, Object> retData = null;
		
		/*List<Dataobjectmetadata> dolist 
				= DataObjectMetaDataImpl.getAllDataObjectForPatientSchedule(patientId, scheduleId);*/
		
		Hashtable<String, Object> uniqueIdFields = new Hashtable<String, Object>();
		uniqueIdFields.put(ApplicationConstant.KEY_PATIENT_ID, ""+patientId);
		uniqueIdFields.put(ApplicationConstant.KEY_SCHEDULE_ID, ""+scheduleId);
		
		List<Object[]> dolist = DataObjectQueueImpl.checkDataObject(null, null, null, null, null, null, null, workflowName, nodeName, uniqueIdFields);
		
		WorkflowComponent.log(0, "[dolist]--getPatientDOFormValuesFromDB" + dolist);
		if (dolist != null)
		{
			WorkflowComponent.log(0, "[dolist.SIZE]" + dolist.size());
			for (int i = 0; i < dolist.size(); i++)
			{
				Object[] obj = dolist.get(i);
				Dataobject doPatient = (Dataobject) obj[0];
				WorkflowComponent.log(0, "[DATA OBJECT ID]" + doPatient.getId());
				List<Dataobjectmetadata> doMetaDataList = DataObjectMetaDataImpl.getAllMetaDataForDataObject(doPatient.getId());

				if (doMetaDataList != null)
				{
					for (int j = 0; j < doMetaDataList.size(); j++)
					{
						Dataobjectmetadata doMetaData = doMetaDataList.get(j);

						if (retData == null) retData = new HashMap<String, Object>();
						retData.put(doMetaData.getDatakey(), doMetaData.getDataValue());
						WorkflowComponent.log(0, "\t[key]" + doMetaData.getDatakey() + "\t[value]" + doMetaData.getDataValue());
					}
				}
			}
		}

		return retData;
	}
	public static IHICData updateUniqueId(IHICData dataObject)
	{
		return UniqueIDUtil.updateUniqueId(dataObject);
		
		/*HashMap<String, Object> doDetails = dataObject.getDataObjectDetails();
		String retUid = DataObjectListUtil.getUniqueIdFromMetaData(
				(String) doDetails.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN),
				(Hashtable<String, Object>)doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES), 
				(String) doDetails.get(CoreConstants.DATAOBJECT_UNIQUE_ID));
		dataObject.setUniqueID(retUid);
		return dataObject;*/
	}
	
	/**
	 * Create unique Id of DO based on Patient Id and Schedule Id. This would
	 * make it unique across sessions.
	 * 
	 * @param metaData
	 * @param existingUniqueId
	 * @return
	 */
	/*private static String getUniqueIdFromMetaData(String workflowName, Hashtable<String, Object> metaData, String existingUniqueId)
	{
		if ((metaData == null) || (metaData.size() <= 0)) return existingUniqueId;
		
		String uniqueId = "", valSep = "_";
		for (int i = 0; i < ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO.length; i++)
		{
			String key = ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO[i];
			String value = (String) metaData.get(key);
			
			if (value != null)
			{
				uniqueId = uniqueId + value;
			}
			if (i < ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO.length - 1)
				uniqueId = uniqueId + valSep;
			else
				uniqueId = uniqueId + valSep + workflowName;
		}
		
		return (uniqueId.equals("") || uniqueId.replace(valSep, "").equals("")) ? existingUniqueId : uniqueId + valSep + workflowName;
	}*/

	
	
	public static boolean isPatientSchedulePresentInFormValues(IHICData incomingDataObject)
	{
		boolean infoPresent = true;
		String pId = getPatientIdFromFormValues(incomingDataObject);
		infoPresent = infoPresent && ((pId != null) ? true : false);
		String sId = getScheduleIdFromFormValues(incomingDataObject);
		infoPresent = infoPresent && ((sId != null) ? true : false);
		
		return infoPresent;
	}
	
	public static String getPatientIdFromFormValues(IHICData incomingDataObject)
	{
		HashMap<String, Object> doDetails = incomingDataObject.getDataObjectDetails();
		
		Hashtable<String, Object> formValues = 
					(Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES);
		
		String patientId = null;
		if (formValues != null)
			patientId = (String) formValues.get(ApplicationConstant.KEY_PATIENT_ID);
		
		return patientId;
	}
	
	public static String getScheduleIdFromFormValues(IHICData incomingDataObject)
	{
		HashMap<String, Object> doDetails = incomingDataObject.getDataObjectDetails();
		
		Hashtable<String, Object> formValues = 
					(Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES);
		
		String scheduleId = null;
		if (formValues != null)
			scheduleId = (String) formValues.get(ApplicationConstant.KEY_SCHEDULE_ID);
		
		return scheduleId;
	}
	
	
	public static IHICData updateFormValuesWithDBData(IHICData incomingDO, String workFlowName, String nodeName)
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
							workFlowName, 
							nodeName);
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
	
	public static void main (String[] args)
	{
		WorkflowComponent.dataBaseInfo = new WorkflowDatabaseInfo();
		WorkflowComponent.dataBaseInfo.setUserName("root");
		WorkflowComponent.dataBaseInfo.setPassword("");
		WorkflowComponent.dataBaseInfo.setDbName("appadmin");
		WorkflowComponent.dataBaseInfo.setDbServerName("localhost");
		WorkflowComponent.dataBaseInfo.setDBPort("3306");
		WorkflowComponent.dataBaseInfo.setDbType("mysql");
		
		/*HashMap<String, Object> ret = getPatientDOFormValuesFromDB(19, 1, null, null);
		if ((ret != null) && (ret.size() > 0))
		{
			Iterator<String> keyIter = ret.keySet().iterator();
			while (keyIter.hasNext())
			{
				String key = keyIter.next();
				WorkflowComponent.log(0, "[key]" + key + "\t[value]" + ret.get(key));
			}
		}*/
	}
}

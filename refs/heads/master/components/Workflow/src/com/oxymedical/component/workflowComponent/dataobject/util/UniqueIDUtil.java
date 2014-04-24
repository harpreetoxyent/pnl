package com.oxymedical.component.workflowComponent.dataobject.util;

import java.util.HashMap;
import java.util.Hashtable;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.stringutil.StringUtil;

public class UniqueIDUtil
{
	private static String UNIQUE_ID_VALUE_SEPARATOR = "_";
	private static String DB_SEARCH_LIKE_CHARACTER = "%";
	
	/**
	 * Create unique Id of DO based on Patient Id and Schedule Id. This would
	 * make it unique across sessions.
	 * 
	 * @param dataObject
	 * @return
	 */
	public static IHICData updateUniqueId(IHICData dataObject)
	{
		HashMap<String, Object> doDetails = dataObject.getDataObjectDetails();
		String retUid = UniqueIDUtil.getUniqueIdFromMetaData(
				(String) doDetails.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN),
				(Hashtable<String, Object>)doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES), 
				(String) doDetails.get(CoreConstants.DATAOBJECT_UNIQUE_ID));
		dataObject.setUniqueID(retUid);
		return dataObject;
	}
	
	/**
	 * Create unique Id of DO based on Patient Id and Schedule Id. This would
	 * make it unique across sessions.
	 * 
	 * @param metaData
	 * @param existingUniqueId
	 * @return
	 */
	private static String getUniqueIdFromMetaData(String workflowName, Hashtable<String, Object> metaData, String existingUniqueId)
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
			/*else
				uniqueId = uniqueId + valSep + workflowName;*/
		}
		
		return (uniqueId.equals("") || uniqueId.replace(valSep, "").equals("")) ? existingUniqueId : uniqueId + valSep + workflowName;
	}


	/**
	 * Used to create search value based on provided attributes for unique id.
	 * For all those fields that are part of unique id but not provided, '%' is
	 * used.
	 * 
	 * @param uniqueIdFields
	 * @return
	 */
	public static String getUniqueIdForDBSearch(Hashtable<String,Object> uniqueIdFields,String workflowname)
	{
		String searchStr = "";
		
		for (int i = 0; i < ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO.length; i++)
		{
			String key = ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO[i];
			String value = (String) uniqueIdFields.get(key);
			
			if (value != null)
			{
				searchStr = searchStr + value;
			}
			else
			{
				searchStr = searchStr + DB_SEARCH_LIKE_CHARACTER;
			}
			if (i < ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO.length - 1)
				searchStr = searchStr + UNIQUE_ID_VALUE_SEPARATOR;
		}
		searchStr = searchStr + UNIQUE_ID_VALUE_SEPARATOR;
		if(workflowname!=null)
		{
			searchStr = searchStr + workflowname;
		}
		else
		{
			searchStr = searchStr + DB_SEARCH_LIKE_CHARACTER;
		}
		
		return searchStr;
	}
	
	
	public static IHICData updateHICDataFromUnqiueID(String unqiueID,IHICData inputHicData)
	{
		
		String[] ids = StringUtil.splitString(unqiueID,UNIQUE_ID_VALUE_SEPARATOR);
		if (ids.length == 4)
		{
			inputHicData.getData().getFormPattern().getFormValues().put(
					ApplicationConstant.KEY_PATIENT_ID, ids[0]);
			inputHicData.getData().getFormPattern().getFormValues().put(
					ApplicationConstant.KEY_PATIENT_MRN, ids[1]);
			inputHicData.getData().getFormPattern().getFormValues().put(
					ApplicationConstant.KEY_SCHEDULE_ID, ids[2]);
		}
		return inputHicData;
	}
}

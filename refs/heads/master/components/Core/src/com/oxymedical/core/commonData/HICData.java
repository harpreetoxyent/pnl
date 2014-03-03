package com.oxymedical.core.commonData;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.ldapData.ILdapData;
import com.oxymedical.core.stringutil.StringUtil;

public class HICData implements IHICData, Cloneable, java.io.Serializable
{
	String uniqueID;
	LinkedList<ISource> srcHistoryList =null;
	ILdapData ldapData;
	IDataType dataType =null;
	IData data = null;
	IMetaData metaData =null;
	IDataBase database;
	IApplication application = null;
	
	// Session Id attribute added
	String sessionId = null;
	
	//Start changes by netram sahu on 26-May-2012, Connected more than two database and Application
	
	public HICData(){
		
		this.setData(this.data);
		this.setUniqueID(this.uniqueID);
		this.setApplication(this.application);
		this.setLdapData(this.ldapData);
		this.setDatabase(this.database);
		this.setDataType(this.dataType);
		this.setMetaData(this.metaData);
		this.setSessionId(this.sessionId);
		if (this.srcHistoryList!=null && this.srcHistoryList.size() > 0)
	    {
	        LinkedList<ISource> sourceList = new LinkedList<ISource>(this.srcHistoryList);
	        this.setSrcHistoryList(sourceList);
	    }
		
	}
	
	
	//End changes by netram sahu
	
	public LinkedList<ISource> getSrcHistoryList()
	{
		return srcHistoryList;
	}

	public void setSrcHistoryList(LinkedList<ISource> srcHistoryList)
	{
		this.srcHistoryList = srcHistoryList;
	}

	public IData getData()
	{
		return data;
	}

	public void setData(IData data)
	{
		this.data = data;
	}

	public IDataType getDataType()
	{
		return dataType;
	}

	public void setDataType(IDataType dataType)
	{
		this.dataType = dataType;
	}

	public IMetaData getMetaData()
	{
		return metaData;
	}

	public void setMetaData(IMetaData metaData)
	{
		this.metaData = metaData;
	}


	public String getUniqueID()
	{
		return this.uniqueID;
	}

	public void setUniqueID(String uniqueID)
	{
		this.uniqueID = uniqueID;
	}

	public Object clone()
	{
		try
		{
			IHICData hicCloneData = new HICData();
			hicCloneData.setData(this.data);
			hicCloneData.setUniqueID(this.uniqueID);
			hicCloneData.setApplication(this.application);
			hicCloneData.setLdapData(this.ldapData);
			hicCloneData.setDatabase(this.database);
			hicCloneData.setDataType(this.dataType);
			hicCloneData.setMetaData(this.metaData);
			hicCloneData.setSessionId(this.sessionId);
			if (this.srcHistoryList!=null && this.srcHistoryList.size() > 0)
	        {
	            LinkedList<ISource> sourceList = new LinkedList<ISource>(this.srcHistoryList);
	            hicCloneData.setSrcHistoryList(sourceList);
	        }
			
			return hicCloneData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ILdapData getLdapData()
	{
		return ldapData;
	}

	public void setLdapData(ILdapData ldapData)
	{
		this.ldapData = ldapData;
	}

	public IDataBase getDatabase()
	{
		return database;
	}

	public void setDatabase(IDataBase database)
	{
		this.database = database;
	}

	public IApplication getApplication()
	{
		return this.application;
	}

	public void setApplication(IApplication application)
	{
		this.application = application;
	}
	
	
	public HashMap<String, Object> getDataObjectDetails()
	{
		HashMap<String, Object> doDetails = new HashMap<String, Object>();
		
		String fp, dp, up, status, userid, uniqueid, wp, wpNode;
		fp = dp = up = status = userid = uniqueid = wp = wpNode = null;
		Hashtable<String, Object> fv = null;
		Hashtable<String, Boolean> evSt = null;
		
		
		if (this != null)
		{
			if (this.getData() != null)
			{
				// Form Id and Form Values
				if (this.getData().getFormPattern() != null)
				{
					if (this.getData().getFormPattern().getFormId() != null)
					{
						fp = this.getData().getFormPattern().getFormId();
					}
					if (this.getData().getFormPattern().getFormValues() != null)
					{
						fv = this.getData().getFormPattern().getFormValues();
					}
				}
				
				// Data Pattern
				if ((this.getData().getDataPattern() != null) && 
						(this.getData().getDataPattern().getDataPatternId() != null))
				{
					dp = this.getData().getDataPattern().getDataPatternId();
				}
				
				// User Patterns
				if (this.getData().getUserPatternString() != null)
				{
					up = this.getData().getUserPatternString();
				}
				
				// Status information
				if (this.getData().getStatus() != null)
				{
					status = this.getData().getStatus();
				}
				
				// User information
				if (this.getData().getUserId() != null)
				{
					userid = this.getData().getUserId();
				}
				
				// Workflow Information
				if (this.getData().getWorkflowPattern() != null)
				{
					if (this.getData().getWorkflowPattern().getWorkflowPattern() != null)
					{
						wp = this.getData().getWorkflowPattern().getWorkflowPattern();
					}
					if (this.getData().getWorkflowPattern().getWorkflowNode() != null)
					{
						wpNode = this.getData().getWorkflowPattern().getWorkflowNode();
					}
					if (this.getData().getWorkflowPattern().getWorkflowNodeEventStatus() != null)
					{
						evSt = this.getData().getWorkflowPattern().getWorkflowNodeEventStatus();
					}
				}
			}
			
			// Unique Id
			if (this.getUniqueID() != null)
			{
				uniqueid = this.getUniqueID();
			}
		}
		
		doDetails.put(CoreConstants.DATAOBJECT_FORM_PATTERN, fp);
		doDetails.put(CoreConstants.DATAOBJECT_DATA_PATTERN, dp);
		doDetails.put(CoreConstants.DATAOBJECT_USER_PATTERN, up);
		doDetails.put(CoreConstants.DATAOBJECT_STATUS, status);
		doDetails.put(CoreConstants.DATAOBJECT_USER_ID, userid);
		doDetails.put(CoreConstants.DATAOBJECT_UNIQUE_ID, uniqueid);
		doDetails.put(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN, wp);
		doDetails.put(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE, wpNode);
		doDetails.put(CoreConstants.DATAOBJECT_FORM_VALUES, fv);
		doDetails.put(CoreConstants.DATAOBJECT_EVENT_STATUS, evSt);
		return doDetails;
	}
	
	
	@Override
	public String toString()
	{
		HashMap<String, Object> doDetails = this.getDataObjectDetails();
		if (doDetails == null) return null;
		
		StringBuffer outputSB = new StringBuffer();
		try
		{
			outputSB.append("****************************************");
			outputSB.append("\n[DATAOBJECT_FORM_PATTERN]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_FORM_PATTERN)));
			outputSB.append("\n[DATAOBJECT_DATA_PATTERN]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_DATA_PATTERN)));
			outputSB.append("\n[DATAOBJECT_USER_PATTERN]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_PATTERN)));
			outputSB.append("\n[DATAOBJECT_STATUS]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_STATUS)));
			outputSB.append("\n[DATAOBJECT_USER_ID]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_ID)));
			outputSB.append("\n[DATAOBJECT_UNIQUE_ID]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_UNIQUE_ID)));
			outputSB.append("\n[DATAOBJECT_WORKFLOW_PATTERN]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN)));
			outputSB.append("\n[DATAOBJECT_WORKFLOW_PATTERN_NODE]").append(StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE)));

			Hashtable<String, Object> metaDataPrint 
			= (Hashtable<String, Object>) doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES);
			if ((metaDataPrint != null) && (metaDataPrint.size() > 0))
			{
				Enumeration<String> hashKeys = metaDataPrint.keys();

				while (hashKeys.hasMoreElements())
				{
					String key = hashKeys.nextElement();
					String value = metaDataPrint.get(key).toString();
					outputSB.append("\n[FORM VALUES]\t[KEY]").append(key).append("\t[VALUE]").append(value);
				}
			}
			Hashtable<String, Boolean> evStPrint 
			= (Hashtable<String, Boolean>) doDetails.get(CoreConstants.DATAOBJECT_EVENT_STATUS);
			if ((evStPrint != null) && (evStPrint.size() > 0))
			{
				Enumeration<String> hashKeys = evStPrint.keys();

				while (hashKeys.hasMoreElements())
				{
					String key = hashKeys.nextElement();
					String value = evStPrint.get(key).toString();
					outputSB.append("\n[EVENT STATUS]\t[KEY]").append(key).append("\t[VALUE]").append(value);
				}
			}
			outputSB.append("\n****************************************");
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
			
			outputSB.append("********** ENDED WITH AN ERROR *********\n");
			outputSB.append("*****************" + e.getMessage() +"***********************");
		}
		return outputSB.toString();
	}

	@Override
	public boolean equals(IHICData hicData)
	{
		if (hicData == null) return false;
		boolean isEqual = true;
		
		HashMap<String, Object> curr = getDataObjectDetails();
		HashMap<String, Object> recd = hicData.getDataObjectDetails();
		
		isEqual = isEqual && areHICAttributesEqual(curr, recd);
		isEqual = isEqual && matchFormValues(curr, recd, false);
		
		return isEqual;
	}
	
	public int hashCode()
	{
		int hash = 7;
		
		HashMap<String, Object> curr = getDataObjectDetails();
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_FORM_PATTERN) ? 0 : curr.get(CoreConstants.DATAOBJECT_FORM_PATTERN).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_DATA_PATTERN) ? 0 : curr.get(CoreConstants.DATAOBJECT_DATA_PATTERN).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_USER_PATTERN) ? 0 : curr.get(CoreConstants.DATAOBJECT_USER_PATTERN).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_STATUS) ? 0 : curr.get(CoreConstants.DATAOBJECT_STATUS).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_USER_ID) ? 0 : curr.get(CoreConstants.DATAOBJECT_USER_ID).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_UNIQUE_ID) ? 0 : curr.get(CoreConstants.DATAOBJECT_UNIQUE_ID).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN) ? 0 : curr.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE) ? 0 : curr.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE).toString().hashCode());
		hash = 31 * hash + (null == curr.get(CoreConstants.DATAOBJECT_FORM_PATTERN) ? 0 : curr.get(CoreConstants.DATAOBJECT_FORM_PATTERN).toString().hashCode());
		
		Hashtable<String, Object> currFormVal = (Hashtable<String, Object>) curr.get(CoreConstants.DATAOBJECT_FORM_VALUES);

		if (currFormVal != null)
		{
			Enumeration<String> currKeys = currFormVal.keys();
			while (currKeys.hasMoreElements())
			{
				String key = currKeys.nextElement();
				// Check values of only those keys that are mentioned as required keys
				if (!StringUtil.arrayContainsValue(
						ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, key, false)) 
					continue;
				hash = 31 * hash + (null == currFormVal.get(key) ? 0 : currFormVal.get(key).toString().hashCode());
			}
		}
		
		return hash;

	}
	
	
	public boolean subsetOf(IHICData hicData)
	{
		if (hicData == null) return false;
		boolean isEqual = true;
		
		HashMap<String, Object> curr = getDataObjectDetails();
		HashMap<String, Object> recd = hicData.getDataObjectDetails();
		
		isEqual = isEqual && areHICAttributesEqual(curr, recd);
		isEqual = isEqual && matchFormValues(curr, recd, true);
		
		return isEqual;
	}
	
	
	private boolean areHICAttributesEqual(HashMap<String, Object> curr, HashMap<String, Object> recd)
	{
		boolean isEqual = true;
		
		String currVal = (String) curr.get(CoreConstants.DATAOBJECT_FORM_PATTERN);
		String recdVal = (String) recd.get(CoreConstants.DATAOBJECT_FORM_PATTERN);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;

		currVal = (String) curr.get(CoreConstants.DATAOBJECT_DATA_PATTERN);
		recdVal = (String) recd.get(CoreConstants.DATAOBJECT_DATA_PATTERN);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;
		
		currVal = (String) curr.get(CoreConstants.DATAOBJECT_USER_PATTERN);
		recdVal = (String) recd.get(CoreConstants.DATAOBJECT_USER_PATTERN);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;
		
		currVal = (String) curr.get(CoreConstants.DATAOBJECT_STATUS);
		recdVal = (String) recd.get(CoreConstants.DATAOBJECT_STATUS);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;
		
		currVal = (String) curr.get(CoreConstants.DATAOBJECT_USER_ID);
		recdVal = (String) recd.get(CoreConstants.DATAOBJECT_USER_ID);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;
		
		currVal = (String) curr.get(CoreConstants.DATAOBJECT_UNIQUE_ID);
		recdVal = (String) recd.get(CoreConstants.DATAOBJECT_UNIQUE_ID);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;
		
		currVal = (String) curr.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN);
		recdVal = (String) recd.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;
		
		currVal = (String) curr.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE);
		recdVal = (String) recd.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE);
		isEqual = isEqual && StringUtil.isStringValueEqual(currVal, recdVal, false);
		if (!isEqual) return isEqual;
		
		return isEqual;
	}
	
	
	private boolean matchFormValues(HashMap<String, Object> curr, HashMap<String, Object> recd, boolean subsetOf)
	{
		Object currVal = null, recdVal = null;
		boolean isEqual = true;
		
		Hashtable<String, Object> currFormVal = (Hashtable<String, Object>) curr.get(CoreConstants.DATAOBJECT_FORM_VALUES);
		Hashtable<String, Object> recdFormVal = (Hashtable<String, Object>) recd.get(CoreConstants.DATAOBJECT_FORM_VALUES);

		if ((currFormVal != null) && (recdFormVal != null))
		{
			int currFormValSize = 1, recdFormValSize = 0;
			if (currFormVal != null) currFormValSize = currFormVal.size();
			if (recdFormVal != null) recdFormValSize = recdFormVal.size();
			
			if (subsetOf)
			{
				if (currFormValSize <= recdFormValSize) isEqual = isEqual && true;
				else isEqual = isEqual && false;
			}
			else 
			{
				if (currFormValSize == recdFormValSize) isEqual = isEqual && true;
				else isEqual = isEqual && false;
			}
			
			if (isEqual)
			{
				Enumeration<String> currKeys = currFormVal.keys();
				while (currKeys.hasMoreElements())
				{
					String key = currKeys.nextElement();
					
					// Check values of only those keys that are mentioned as required keys
					if (!StringUtil.arrayContainsValue(
							ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, key, false)) 
						continue;
					
					currVal = currFormVal.get(key);
					recdVal = recdFormVal.get(key);
					
					// Both are String 
					if ((currVal instanceof String) && (recdVal instanceof String))
					{
						isEqual = isEqual && StringUtil.isStringValueEqual((String)currVal, (String)recdVal, false);
					}
					// Both are ArrayList
					else if ((currVal instanceof ArrayList) && (recdVal instanceof ArrayList))
					{
						// Both are NOT Null
						if ((currVal != null) && (recdVal != null))
						{
							if (subsetOf)
							{
								// For "subsetOf", Current Val size can be less than or equal to Received Val
								if (((ArrayList)currVal).size() <= ((ArrayList)recdVal).size())
								{
									isEqual = isEqual && containsEntries((ArrayList)currVal, (ArrayList)recdVal);
								}
								// Otherwise they are not equal
								else isEqual = isEqual && false;
							}
							else
							{
								// Current Val size SHOULD BE equal to Received Val
								if (((ArrayList)currVal).size() == ((ArrayList)recdVal).size())
								{
									isEqual = isEqual && containsEntries((ArrayList)currVal, (ArrayList)recdVal);
								}
								// Otherwise they are not equal
								else isEqual = isEqual && false;
							}
						}
						// Current Val is Null but Received Val is NOT Null
						else if ((currVal == null) && (recdVal != null))
						{
							// They are equal on if it is "subsetOf"
							if (subsetOf) isEqual = isEqual && true;
							// Otherwise they are NOT equal
							else isEqual = isEqual && false;
						}
						// Current Val is NOT Null but Received Val is Null
						else if ((currVal != null) && (recdVal == null)) isEqual = isEqual && false;
						
						// 4th case is both are null and hence are equal. Therefore no check required.
					}
					// Current Value is String; Received Value is ArrayList
					else if ((currVal instanceof String) && (recdVal instanceof ArrayList))
					{
						// Both are NOT Null
						if ((currVal != null) && (recdVal != null))
						{
							if (subsetOf)
							{
								isEqual = isEqual && ((ArrayList)recdVal).contains(currVal);
							}
							else isEqual = isEqual && false;
						}
						// Current Val is Null but Received Val is NOT Null
						else if ((currVal == null) && (recdVal != null))
						{
							if (subsetOf) isEqual = isEqual && true;
							else isEqual = isEqual && false;
						}
						// Current Val is NOT Null but Received Val is Null
						else if ((currVal != null) && (recdVal == null)) isEqual = isEqual && false;
						
						// 4th case is both are null and hence are equal. Therefore no check required.
					}
					// For all other cases like 
					// - Current Value is ArrayList; Received Value is String
					// - Current Value and Received Value is of some other data type
					else isEqual = isEqual && false;
					
					if (!isEqual) return isEqual;
				}
			}
		}
		else if ((currFormVal == null) && (recdFormVal != null))
		{
			if (subsetOf) isEqual = true;
			else isEqual = false;
		}
		else if  ((currFormVal != null) && (recdFormVal == null)) isEqual = false;
		return isEqual;

	}
	
	
	private boolean containsEntries(List listToCheck, List referenceList)
	{
		boolean contains = true;
		for (int i = 0; i < listToCheck.size(); i++)
		{
			Object entry = listToCheck.get(i);
			if (!referenceList.contains(entry)) contains = contains && false;
		}
		return contains;
	}

	// Session Id attribute added
	
	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}

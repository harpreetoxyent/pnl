package com.oxymedical.component.workflowComponent.db.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Workflowinfo;
import com.oxymedical.component.appadmin.model.resources_app.Workflownodeinfo;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;
import com.oxymedical.component.workflowComponent.operation.db.WorkflowDatabaseInfo;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.dateutil.DateUtil;
import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.core.util.HashUtil;

/**
 * NodeCompletionStatus has 3 possible values <BR>
 * 	<ul>
 * 		<li>C - Completed</li>
 * 		<li>P - In Progress</li>
 * 		<li>W - Waiting user input</li>
 * 	</ul>
 * 
 * @author hs
 * 
 */
public class DataObjectQueueImpl extends BaseDBImpl implements IDataObjectQueue
{
	

	@Override
	public Dataobject addDataObjectToQueue(String formPatternId, String datapatternId, String status,
			Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId) throws WorkflowComponentException
	{
		return addDataObjectToDbQueue(formPatternId, datapatternId, status, nodeInfo, userpatterns, userId, uniqueId);
	}
	
	
	public static Dataobject addDataObjectToDbQueue(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId) throws WorkflowComponentException
	{
		return addUpdateDataObjectToDbQueue(formPatternId, datapatternId,
				status, nodeInfo, userpatterns, userId, uniqueId,
				null);
	}
	
	public static List<Dataobject> getAllDataObjects() throws WorkflowComponentException
	{
		List<Dataobject> dataobjectList= new ArrayList<Dataobject>();
		String workQuery=WorkflowSqlCommands.find_All_dataobject;
		try {
			List list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().getList(workQuery);
			
			if (list !=null || (list.size()>0))
			{
				for (Iterator upRow = list.iterator(); upRow.hasNext();)
				{
					Object upRowData = upRow.next();
					Dataobject workflow = (Dataobject) upRowData;
					dataobjectList.add(workflow);
				}
			}
		} catch (DBComponentException e) {

			throw (new WorkflowComponentException("Exception from the database component"
			));
		}
		return dataobjectList;
	}
	public static Dataobject addUpdateDataObjectToDbQueue(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId,
			Dataobject dObject) throws WorkflowComponentException
	{
		return addUpdateDataObjectToDbQueue(formPatternId, datapatternId,
				status, nodeInfo, userpatterns, userId, uniqueId,
				dObject, WorkflowConstant.NODE_EXECUTION_STATUS_DEFAULT);
	}
	
	
	
	
	public static Dataobject addUpdateDataObjectToDbQueue(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId,
			Dataobject dObject, String nodeExecutionStatus) throws WorkflowComponentException
	{
		if (dObject == null) dObject = new Dataobject();
		
		if (datapatternId != null) dObject.setDatapattern(datapatternId);
		if (formPatternId != null) dObject.setFormpattern(formPatternId);
		if (userpatterns != null) dObject.setUserpatterns(userpatterns);
		if (nodeInfo != null) dObject.setWorkflownodeinfo(nodeInfo);
		if (status != null) dObject.setStatus(status);
		if (userId != null) dObject.setUserid(userId);
		if (uniqueId != null) dObject.setUniqueid(uniqueId);
		if (nodeExecutionStatus == null) nodeExecutionStatus = WorkflowConstant.NODE_EXECUTION_STATUS_DEFAULT;
		dObject.setNodeexecutionstatus(nodeExecutionStatus);
		Date currentDate= new Date();
		String toolStartTime = DateUtil.formatDate(currentDate,"yyyy-MM-dd,HH:mm:ss").split(",")[1];
		java.sql.Date toolstartdate = new java.sql.Date(currentDate.getTime()); 
		dObject.setToolstartdate(toolstartdate);
		dObject.setToolstarttime(Time.valueOf(toolStartTime));
		
		Object obj = null;
		try
		{
			obj = ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(dObject);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
		
		if (obj != null)
		{
			return (Dataobject) obj;
		}
		return null;
	}
	
	/**
	 * Check for only those DOs whose NodeExecution Status is "P"
	 * @param formPatternId
	 * @param datapatternId
	 * @param status
	 * @param nodeInfo
	 * @param userpatterns
	 * @param userId
	 * @param uniqueId
	 * @return
	 */
	public static Dataobject checkDataObjectInQueue(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId)
	{
		return checkExistingDataObjectInQueue(formPatternId, datapatternId, status, 
				nodeInfo, userpatterns, userId, uniqueId, 
				WorkflowConstant.NODE_EXECUTION_STATUS_DEFAULT, false);
	}

	
	
	public static Dataobject checkDataObjectInQueueEx(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId)
	{
		return checkExistingDataObjectInQueue(formPatternId, datapatternId, status,
				nodeInfo, userpatterns, userId, uniqueId, 
				WorkflowConstant.NODE_EXECUTION_STATUS_DEFAULT, true);
	}
	
	
	public static Dataobject checkDataObjectInQueueEx(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId,
			String nodeExecutionStatus)
	{
		return checkExistingDataObjectInQueue(formPatternId, datapatternId, status,
				nodeInfo, userpatterns, userId, uniqueId, nodeExecutionStatus, true);
	}

	
	/**
	 * Check for all DOs irrespective of NodeExecution Status
	 * @param formPatternId
	 * @param datapatternId
	 * @param status
	 * @param nodeInfo
	 * @param userpatterns
	 * @param userId
	 * @param uniqueId
	 * @return
	 */
	public static Dataobject checkAllDataObjectInQueue(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId)
	{
		return checkExistingDataObjectInQueue(formPatternId, datapatternId, status,
				nodeInfo, userpatterns, userId, uniqueId, null, false);
	}
	
	
	/**
	 * All logic behind checking DOs in Queue
	 * @param formPatternId
	 * @param datapatternId
	 * @param status
	 * @param nodeInfo
	 * @param userpatterns
	 * @param userId
	 * @param uniqueId
	 * @param checkAll
	 * @return
	 */
	private static Dataobject checkExistingDataObjectInQueue(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId,
			String nodeExecutionStatus, boolean checkWithAllConditions)
	{
		Dataobject doExist = null;
		
		String sqlQuery;
		if (checkWithAllConditions)
			sqlQuery = WorkflowSqlCommands.Find_AllDataObjectInQueueEx;
		else if (nodeExecutionStatus == null)
			sqlQuery = WorkflowSqlCommands.Find_AllDataObjectInQueue;
		else
			sqlQuery = WorkflowSqlCommands.Find_DataObjectInQueue;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_USERID, userId);
		listParam.add(userNameIdParam);
		NameQueryParameter uniqueIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_UNIQUEID, uniqueId);
		listParam.add(uniqueIdParam);
		
		if (checkWithAllConditions)
		{
			if (formPatternId != null)
			{
				sqlQuery = sqlQuery + WorkflowSqlCommands.Find_AllDataObjectInQueueEx_FormPattern;
				NameQueryParameter fpParam = new NameQueryParameter(WorkflowSqlCommands.CONST_FORMPATTERN, formPatternId);
				listParam.add(fpParam);
			}
			if (datapatternId != null)
			{
				sqlQuery = sqlQuery + WorkflowSqlCommands.Find_AllDataObjectInQueueEx_DataPattern;
				NameQueryParameter dpParam = new NameQueryParameter(WorkflowSqlCommands.CONST_DATAPATTERN, datapatternId);
				listParam.add(dpParam);
			}
			if (userpatterns != null)
			{
				sqlQuery = sqlQuery + WorkflowSqlCommands.Find_AllDataObjectInQueueEx_UserPattern;
				NameQueryParameter upParam = new NameQueryParameter(WorkflowSqlCommands.CONST_USERPATTERNS, userpatterns);
				listParam.add(upParam);
			}
			/*if (nodeExecutionStatus != null)
			{
				sqlQuery = sqlQuery + WorkflowSqlCommands.Find_AllDataObjectInQueueEx_NodeExecutionStatus;
				NameQueryParameter nodeStatusParam = new NameQueryParameter(WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, nodeExecutionStatus);
				listParam.add(nodeStatusParam);
			}*/
			
			NameQueryParameter statusParam = new NameQueryParameter(WorkflowSqlCommands.CONST_STATUS, status);
			listParam.add(statusParam);
		}
		else if (nodeExecutionStatus != null)
		{
			NameQueryParameter nodeExeStatusParam = new NameQueryParameter(WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, nodeExecutionStatus);
			listParam.add(nodeExeStatusParam);
		}
		
		List list;
		try
		{
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);

			if ((list != null) && (list.size() > 0))
			{
				for (Iterator doRow = list.iterator(); doRow.hasNext();)
				{
					Object doRowData = doRow.next();
					doExist = (Dataobject) doRowData;
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}

		return doExist;
	}
	
	
	public Dataobject addDataObjectToQueueWithExistingCheck(String formPatternId, String datapatternId,
			String status, Workflownodeinfo nodeInfo, String userpatterns, String userId, String uniqueId)
			throws WorkflowComponentException
	{
		return addDataObjectToDbQueueWithExistingCheck(formPatternId, datapatternId,
				status, nodeInfo, userpatterns, userId, uniqueId, 
				WorkflowConstant.NODE_EXECUTION_STATUS_DEFAULT, null);
	}
	
	
	public static Dataobject addDataObjectToDbQueueWithExistingCheck(String formPattern,
			String dataPattern, String status, Workflownodeinfo nodeInfo, String userPatterns,
			String userId, String uniqueId, String nodeExecutionStatus, 
			Hashtable<String, Object> metaData) throws WorkflowComponentException
	{
		/*Dataobject dObject = checkDataObjectInQueueEx(formPatternId, datapatternId, status,
				nodeInfo, userpatterns, userId, uniqueId);*/
		List<Object[]> dObjectList 
				= checkDataObjectForUser(
						formPattern, dataPattern,
						status, userPatterns, userId, uniqueId,
						/*nodeExecutionStatus*/null, 
						nodeInfo.getWorkflowinfo().getName(), 
						nodeInfo.getNodeinfo().getNodename(), 
						metaData);
		
		Dataobject dObject = null;
		if (dObjectList.size() > 0)
		{
			try
			{
				dObject = (Dataobject) dObjectList.get(0)[0];
			}
			catch (NullPointerException npe) { }
		}
		
		WorkflowComponent.log(0, "[***************************************************************]"
				+ "\n[***** INSIDE [addDataObjectToDbQueueWithExistingCheck] FUNCTION *****]"
				+ "\n[***************************************************************]");

		if (dObject != null)
		{
			WorkflowComponent.log(0, "[getDatapattern]" + dObject.getDatapattern()
					+ "\n[getFormpattern]" + dObject.getFormpattern()
					+ "\n[getNodeexecutionstatus]" + dObject.getNodeexecutionstatus()
					+ "\n[getStatus]" + dObject.getStatus()
					+ "\n[getUniqueid]" + dObject.getUniqueid()
					+ "\n[getUserid]" + dObject.getUserid()
					+ "\n[getUserpatterns]" + dObject.getUserpatterns()
					+ "\n[getId]" + dObject.getId()
					+ "\n[getWorkflowinfo]" + dObject.getWorkflownodeinfo().getWorkflowinfo().getName()
					+ "\n[getNodeinfo]" + dObject.getWorkflownodeinfo().getNodeinfo().getNodename());
		}
		else
		{
			WorkflowComponent.log(0, "[RETRIEVED DATA OBJECT FROM DATABASE IS ---NULL---]");
		}

		WorkflowComponent.log(0, "[***************]"
					+ "\n[***** END *****]"
					+ "\n[***************]");

		return addUpdateDataObjectToDbQueue(formPattern, dataPattern,
				status, nodeInfo, userPatterns, userId, uniqueId,
				dObject, nodeExecutionStatus);
	}
	
	
	
	/**
	 * Set NodeExecutionStatus of DO for said Node as "C"
	 * @param nodeInfo
	 * @param userId
	 * @param uniqueId
	 * @return
	 * @throws WorkflowComponentException
	 */
	public static Dataobject setDataObjectAsCompleted(String workflowName, String nodeName, String userId,
			String uniqueId, Hashtable<String, Object> metaData) throws WorkflowComponentException
	{
		return setDataObjectExecutionStatus(workflowName, nodeName, userId, uniqueId, 
				WorkflowConstant.NODE_EXECUTION_STATUS_COMPLETED, metaData);
	}

	/**
	 * Set NodeExecutionStatus of DO for said Node
	 * @param wfNodeInfo
	 * @param userId
	 * @param uniqueId
	 * @param nodeExecutionStatus
	 * @return
	 * @throws WorkflowComponentException
	 */
	public static Dataobject setDataObjectExecutionStatus(String workflowName, String nodeName,
			String userId, String uniqueId, String nodeExecutionStatus, Hashtable<String, Object> metaData)
			throws WorkflowComponentException
	{
		WorkflowComponent.log(0, "[DataObjectQueueImpl][setDataObjectExecutionStatus]"
				+ "\n[workflowName]" + workflowName
				+ "\n[nodeName]" + nodeName
				+ "\n[userId]" + userId
				+ "\n[uniqueId]" + uniqueId
				+ "\n[nodeExecutionStatus]" + nodeExecutionStatus
				+ "\n[metaData]" + metaData
				);
		
		// First fetch; then save
		
		Dataobject doExist = null;
		
		// Prepare for Fetch
		String sqlQuery = WorkflowSqlCommands.FIND_DATAOBJECT_BASEDON_UNIQUEID;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userParam = new NameQueryParameter(WorkflowSqlCommands.CONST_USERID, userId);
		listParam.add(userParam);
		NameQueryParameter uniqueIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_UNIQUEID, uniqueId);
		listParam.add(uniqueIdParam);
		NameQueryParameter wfParam = new NameQueryParameter(WorkflowSqlCommands.workflowname, workflowName);
		listParam.add(wfParam);
		NameQueryParameter nodeParam = new NameQueryParameter(WorkflowSqlCommands.nodeName, nodeName);
		listParam.add(nodeParam);
		
		sqlQuery = DataObjectMetaDataImpl.addMetaDataConditionToQuery(metaData, sqlQuery, listParam);
		WorkflowComponent.log(0, "[DataObjectQueueImpl][setDataObjectExecutionStatus][sqlQuery]" + sqlQuery);
		
		List<Object[]> list;
		try
		{
			// Fetch
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);

			if ((list != null) && (list.size() > 0))
			{
				Object doRowData = list.get(0)[0];
				doExist = (Dataobject) doRowData;
				
				/*for (Iterator doRow = list.iterator(); doRow.hasNext();)
				{
					Object doRowData = doRow.next();
					doExist = (Dataobject) doRowData;
					
					// Take only for DataObject object
					break;
				}*/
			}
			
			// Save
			if (doExist != null)
			{
				WorkflowComponent.log(0, "[UPDATING DATAOBJECT NODE EXECUTION STATUS - DataObject found][nodeExecutionStatus]" + nodeExecutionStatus);
				doExist.setNodeexecutionstatus(nodeExecutionStatus);
				Object obj = ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(doExist);
				doExist = (Dataobject) obj;
				WorkflowComponent.log(0, "[Formpattern]" + doExist.getFormpattern() 
						+ "\n[Nodeexecutionstatus]" + doExist.getNodeexecutionstatus() 
						+ "\n[Uniqueid]" + doExist.getUniqueid());
			}
			else
			{
				WorkflowComponent.log(0, "[UPDATING DATAOBJECT NODE EXECUTION STATUS - DataObject NOT found]");
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}

		return doExist;
	}


	/**
	 * Returns list of Array containing DataObjects, WorkflowInfo, NodeInfo
	 * objects based on the specified inputs.
	 * 
	 * @param formPattern
	 * @param dataPattern
	 * @param status
	 * @param userPatterns
	 * @param userId
	 * @param uniqueId
	 * @param nodeExecutionStatus
	 * @param workflowName
	 * @param nodeName
	 * @param metaData
	 * @return
	 */
	public static List<Object[]> checkDataObjectForUser(String formPattern, String dataPattern,
			String status, String userPatterns, String userId, String uniqueId,
			String nodeExecutionStatus, String workflowName, String nodeName, 
			Hashtable<String, Object> metaData)
	{
		Dataobject doExist = null;
		String sqlQuery = WorkflowSqlCommands.FIND_MATCHING_DATAOBJECT_FOR_USER;
		
		// USERID condition
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_USERID, userId);
		listParam.add(userNameIdParam);
		
		// Add conditions to sqlQuery conditionally
		sqlQuery = updateSqlAndCreateNameParameter(listParam, formPattern, WorkflowSqlCommands.CONST_FORMPATTERN, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_FORMPATTERN_CONDITION, true);
		sqlQuery = updateSqlAndCreateNameParameter(listParam, dataPattern, WorkflowSqlCommands.CONST_DATAPATTERN, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_DATAPATTERN_CONDITION, true);
		sqlQuery = updateSqlAndCreateNameParameter(listParam, userPatterns, WorkflowSqlCommands.CONST_USERPATTERNS, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_USERPATTERN_CONDITION, true);
		sqlQuery = updateSqlAndCreateNameParameter(listParam, nodeExecutionStatus, WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_NODEEXECUTIONSTATUS_CONDITION, true);
		sqlQuery = updateSqlAndCreateNameParameter(listParam, uniqueId, WorkflowSqlCommands.CONST_UNIQUEID, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_UNIQUEID_CONDITION, true);
		sqlQuery = updateSqlAndCreateNameParameter(listParam, status, WorkflowSqlCommands.CONST_STATUS, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_STATUS_CONDITION, true);
		sqlQuery = updateSqlAndCreateNameParameter(listParam, workflowName, WorkflowSqlCommands.workflowname, 
				sqlQuery, WorkflowSqlCommands.WORKFLOWINFO_NAME_CONDITION, true);
		sqlQuery = updateSqlAndCreateNameParameter(listParam, nodeName, WorkflowSqlCommands.nodeName, 
				sqlQuery, WorkflowSqlCommands.NODEINFO_NODENAME_CONDITION, true);
		
		sqlQuery = DataObjectMetaDataImpl.addMetaDataConditionToQuery(metaData, sqlQuery, listParam);
		
		WorkflowComponent.log(0, "[sqlQuery]--checkDataObjectForUser" + sqlQuery);
		WorkflowComponent.log(0, "[listParam]--checkDataObjectForUser" + listParam.size());
		
		List<Object[]> list = null;
		try
		{
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		
		WorkflowComponent.log(0, "[list.size()]" + list.size());

		return list;
	}
	
	
	public static List<Object[]> checkDataObjectForUser(IHICData dObj, String nodeExecutionStatus)
	{
		return checkDataObjectForUser(dObj, nodeExecutionStatus, true);
	}
	
	
	public static List<Object[]> checkDataObjectForUser(IHICData dObj, String nodeExecutionStatus, boolean checkUniqueId)
	{
		HashMap<String, Object> doDetails = dObj.getDataObjectDetails();
		
		// Create meta data based on which search is required
		Hashtable<String, Object> metaDataForExist 
				= HashUtil.removeNonRequiredKeysFromHashtable(
						ApplicationConstant.REQUIRED_KEYS_FOR_UNIQUE_DO, 
						(Hashtable<String, Object>) 
								doDetails.get(CoreConstants.DATAOBJECT_FORM_VALUES));

		
		// Get List of Dataobject from database
		List<Object[]> output 
			= DataObjectQueueImpl.checkDataObjectForUser(
					StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_FORM_PATTERN)), 
					StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_DATA_PATTERN)), 
					StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_STATUS)), 
					StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_PATTERN)), 
					StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_USER_ID)), 
					(checkUniqueId) ? StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_UNIQUE_ID)) : null, 
					nodeExecutionStatus, 
					StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN)), 
					StringUtil.getString(doDetails.get(CoreConstants.DATAOBJECT_WORKFLOW_PATTERN_NODE)), 
					metaDataForExist);
		
		return output;
	}
	
	public static void deleteDataObject(Dataobject dataobject) throws WorkflowComponentException
	{
		try
		{
		ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(dataobject);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}
	}

	
	
	public static Dataobject getDataObjectBasedOnId(int doId)
	{
		Dataobject doExist = null;
		String sqlQuery = WorkflowSqlCommands.FIND_MATCHING_DATAOBJECT_FOR_ID;
		
		// USERID condition
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_DATAOBJECT_ID, doId);
		listParam.add(userNameIdParam);
		
		WorkflowComponent.log(0, "[sqlQuery]--getDataObjectBasedOnId" + sqlQuery);
		WorkflowComponent.log(0, "[listParam]--getDataObjectBasedOnId" + listParam.size());
		
		List<Object[]> output = null;
		try
		{
			output = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
			
			if ((output != null) && (output.size() > 0))
			{
				Object[] objArr = output.get(0);
				Object obj = objArr[0];
				if (obj instanceof Dataobject) doExist = (Dataobject) obj;
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}
		
		return doExist;
	}

	 public static List<Object> getDataObjectsForStatus(String userId, String nodeExecutionStatus) throws WorkflowComponentException
	 {
		 List<Object> listObjects=null;
		
		 	String workQuery=WorkflowSqlCommands.FIND_DATAOBJECT_STATUS;
			ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
			NameQueryParameter userNameIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_USERID, userId);
			listParam.add(userNameIdParam);
			if(nodeExecutionStatus!=null)
			{	
			NameQueryParameter nodeStausIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, nodeExecutionStatus);
			listParam.add(nodeStausIdParam);
			}
			else
			{
			NameQueryParameter nodeStausIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, "%");
			listParam.add(nodeStausIdParam);
			}
			
			
			try {
				listObjects = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(workQuery, listParam);
				
				if (listObjects !=null || (listObjects.size()>0))
				{
					return listObjects;					
				}
			} catch (DBComponentException e) {

				throw (new WorkflowComponentException("Exception from the database component"
				));
			}
		 return listObjects;
	 }
	 
	 public static List<Object> getDataObjectsForStatusCondition(String userId,String uniqueId, String nodeExecutionStatus) throws WorkflowComponentException
	 {
		 List<Object> listObjects=null;
		
		String workQuery=WorkflowSqlCommands.FIND_DATAOBJECT_STATUS_CONDITIONS;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter userNameIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_USERID, userId);
		listParam.add(userNameIdParam);
		NameQueryParameter uniqueIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_UNIQUEID, uniqueId);
	    listParam.add(uniqueIdParam);
	    if(nodeExecutionStatus!=null)
		{	
		NameQueryParameter nodeStausIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, nodeExecutionStatus);
		listParam.add(nodeStausIdParam);
		}
		else
		{
		NameQueryParameter nodeStausIdParam = new NameQueryParameter(WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, "%");
		listParam.add(nodeStausIdParam);
		}
		 try {
			 listObjects = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(workQuery, listParam);
				
				if (listObjects !=null || (listObjects.size()>0))
				{
					return listObjects;					
				}
			} catch (DBComponentException e) {

				throw (new WorkflowComponentException("Exception from the database component"
				));
			}
		 return listObjects;
	 }
	
	public static void main(String[] args)
	{
		WorkflowComponent.dataBaseInfo = new WorkflowDatabaseInfo();
		WorkflowComponent.dataBaseInfo.setUserName("root");
		WorkflowComponent.dataBaseInfo.setPassword("");
		WorkflowComponent.dataBaseInfo.setDbName("appadmin");
		WorkflowComponent.dataBaseInfo.setDbServerName("localhost");
		WorkflowComponent.dataBaseInfo.setDBPort("3306");
		WorkflowComponent.dataBaseInfo.setDbType("mysql");
		
		Hashtable<String, Object> metaData = new Hashtable<String, Object>();
		metaData.put("PATIENTID", "20");
//		metaData.put("PATIENTMRN", "50191960");
		
		/*List<Object[]> output = checkDataObjectForUser("ToolSleepDataArchitectureInput", "",
				null, "Administrative", "adminuserone", null,
				null, "WF6", "comoxymedicalcomponentstoolssleepdataarchitectureinputscreen", metaData);*/
		
		List<Object[]> output = checkDataObjectForUser("ToolDownsamplingInput", null,
				null, null, "adminuserone", null,
				null, "WF1", null, metaData);
		
		WorkflowComponent.log(0, output.size());
		
		for (int i = 0; i < output.size(); i++)
		{
			Object[] retObjs = output.get(i);
			for (int j = 0; j < retObjs.length; j++)
			{
				Object retObj = retObjs[j];
				WorkflowComponent.log(0, retObj.getClass());
				if (retObj instanceof Dataobject)
				{
					Dataobject dObj = (Dataobject) retObj;
					WorkflowComponent.log(0, "[dObj.getFormpattern()]" + dObj.getFormpattern());
					WorkflowComponent.log(0, "[dObj.getId()]" + dObj.getId());
				}
			}
		}
		
		/*try
		{
			setDataObjectExecutionStatus("WF01", "comoxymedicalcomponentstoolssleepdataarchitectureinputscreen", "adminuserone",
					"4ba563a5-90a2-4913-93f4-c55a8fa9b595oxdelx61s/192.168.1.75e481ae1e-2a6c-4653-8a76-0998314f3144IHICData18e0d200-853b-345b-8a55-8cd21871d3b5", 
					"C", metaData);
		}
		catch (WorkflowComponentException e)
		{
			e.printStackTrace();
		}*/
		
		Dataobject dObj = getDataObjectBasedOnId(19);
		WorkflowComponent.log(0, "[dObj.getFormpattern()]" + dObj.getFormpattern());
	}
	public static List<Object[]> checkDataObject(String formPattern, String dataPattern,
			String status, String userPatterns, String userId, String uniqueId,
			String nodeExecutionStatus, String workflowName, String nodeName, 
			Hashtable<String, Object> metaData)
	{
		Dataobject doExist = null;
		String sqlQuery = WorkflowSqlCommands.FIND_MATCHING_DATAOBJECT;
		sqlQuery = sqlQuery + WorkflowSqlCommands.CONDITION_JOINER_WHERE;
		WorkflowComponent.log(0, "[sqlQuery]---checkDataObject" + sqlQuery);
		// USERID condition
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		
		// Add conditions to sqlQuery conditionally
		boolean addAndCondition = true;
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, formPattern, WorkflowSqlCommands.CONST_USERID, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_USER_CONDITION, addAndCondition);
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, formPattern, WorkflowSqlCommands.CONST_FORMPATTERN, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_FORMPATTERN_CONDITION, addAndCondition);
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, dataPattern, WorkflowSqlCommands.CONST_DATAPATTERN, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_DATAPATTERN_CONDITION, addAndCondition);
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, userPatterns, WorkflowSqlCommands.CONST_USERPATTERNS, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_USERPATTERN_CONDITION, addAndCondition);
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, nodeExecutionStatus, WorkflowSqlCommands.CONST_NODEEXECUTIONSTATUS, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_NODEEXECUTIONSTATUS_CONDITION, addAndCondition);
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, status, WorkflowSqlCommands.CONST_STATUS, 
				sqlQuery, WorkflowSqlCommands.DATAOBJECT_STATUS_CONDITION, addAndCondition);
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, workflowName, WorkflowSqlCommands.workflowname, 
				sqlQuery, WorkflowSqlCommands.WORKFLOWINFO_NAME_CONDITION, addAndCondition);
		if (sqlQuery.toLowerCase().trim().endsWith(" where")) addAndCondition = false; else addAndCondition = true;
		sqlQuery = updateSqlAndCreateNameParameter(listParam, nodeName, WorkflowSqlCommands.nodeName, 
				sqlQuery, WorkflowSqlCommands.NODEINFO_NODENAME_CONDITION, addAndCondition);
		
		if (uniqueId != null)
		{
			sqlQuery = updateSqlAndCreateNameParameter(listParam, uniqueId, WorkflowSqlCommands.CONST_UNIQUEID, 
					sqlQuery, (uniqueId.indexOf("%") > 0) ? WorkflowSqlCommands.DATAOBJECT_UNIQUEID_LIKE_CONDITION : WorkflowSqlCommands.DATAOBJECT_UNIQUEID_CONDITION, true);
		}
		
		sqlQuery = DataObjectMetaDataImpl.addMetaDataConditionToQuery(metaData, sqlQuery, listParam);
		
		WorkflowComponent.log(0, "[sqlQuery checkDataObject ---]" + sqlQuery);
		WorkflowComponent.log(0, "[listParam---checkDataObject]" + listParam.size());
		
		List<Object[]> list = null;
		try
		{
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
			WorkflowComponent.log(0, "[list.size()---checkDataObject]" + list.size());
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}

		return list;
	}
	

	
}

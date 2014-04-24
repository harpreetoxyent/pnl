/**
 * 
 */
package com.oxymedical.component.workflowComponent.dataObjectManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.dataobject.util.DataObjectListUtil;
import com.oxymedical.component.workflowComponent.dataobject.util.UniqueIDUtil;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.app.ApplicationConstant;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.stringutil.StringUtil;

/**
 * @author vka
 *
 */
public class DataObjectManager implements IDataObjectManager 
{
	HashMap<String, HashMap<String, HashMap<String, IHICData>>> dObjectColl = null;
	IDataObjectManager doMgrDb = new DataObjectManagerDB();
//	private static IWorkflowComponent workflowComponent=null;	
//	private Object dataObject = null;
//	NodeObject nodeObject =null;
	
	private static LoggingComponent logger1 = WorkflowComponent.logger;
	private HashMap<String, List<IHICData>> _hangingObjs = new HashMap<String, List<IHICData>>();
	
	
	
	/**
	 * This search should not be based on FP/DP/UP but based on hash-code since
	 * the incomingDataObject status and FP/DP/UP might not be the same as that
	 * of stored data object
	 */
	public Hashtable<String, Hashtable<String, List<IHICData>>> getMatchingDataObject(
			IHICData incomingDataObject)
	{
		Hashtable<String, Hashtable<String, List<IHICData>>> matchingDOs 
				= new Hashtable<String, Hashtable<String, List<IHICData>>>();
		
		if ((dObjectColl == null) || (incomingDataObject == null) 
				|| (incomingDataObject.getData() == null) 
//				|| (incomingDataObject.getData().getUserId() == null)
				) 
			return matchingDOs;
		
		Set<String> workflowIdKeys = dObjectColl.keySet();
		Iterator<String> keyIterator = workflowIdKeys.iterator();
		while (keyIterator.hasNext())
		{ 
			String workflowIdKey = (String) keyIterator.next();

			HashMap<String, HashMap<String, IHICData>> dObjectColl_1 = dObjectColl.get(workflowIdKey);
			Set<String> nodeIdKeys = dObjectColl_1.keySet();
			Iterator<String> nodeIdKeyIterator = nodeIdKeys.iterator();
			while (nodeIdKeyIterator.hasNext())
			{ 
				String nodeIdKey = (String)nodeIdKeyIterator.next();

				Collection<IHICData> storedHICDataList = dObjectColl_1.get(nodeIdKey).values();
				Iterator <IHICData> dataObjectsIterator = storedHICDataList.iterator();
				while (dataObjectsIterator.hasNext())
				{
					IHICData storedHICData = dataObjectsIterator.next();

//					if (storedHICData.getUniqueID().equals(incomingDataObject.getUniqueID()))
					if ((storedHICData.getUniqueID().equals(incomingDataObject.getUniqueID())) 
							&& (isRecordEqual(storedHICData, incomingDataObject)))
//					if (isDOEqual(storedHICData, incomingDataObject))
					{
						Hashtable<String, List<IHICData>> matchingDOsForNodes = null;
						if (!matchingDOs.containsKey(workflowIdKey))
							matchingDOsForNodes = new Hashtable<String, List<IHICData>>();
						else
							matchingDOsForNodes = matchingDOs.get(workflowIdKey);
						
						List<IHICData> doList = null;
						if (matchingDOsForNodes.containsKey(nodeIdKey))
							doList = matchingDOsForNodes.get(nodeIdKey);
						else
							doList = new ArrayList<IHICData>();
						
						
//						DataObjectListUtil.printDataObject(storedHICData, "THIS IS MATCHED DO - [getMatchingDataObject]", "*");
//						WorkflowComponent.log(0, "[workflowIdKey]" + workflowIdKey + "\n[nodeIdKey]" + nodeIdKey);
						
						doList.add(storedHICData);
						matchingDOsForNodes.put(nodeIdKey, doList);
						matchingDOs.put(workflowIdKey, matchingDOsForNodes);
						/*returnData[0] = workflowIdKey;
						returnData[1] = nodeIdKey;
						returnData[2] = storedHICData;
						return returnData;*/
					}
				}
			}
		}

		return matchingDOs;
	}

	
	public List<IHICData> getMatchingDataObjectForWorkflowNode(String workflowIdKey, 
			String nodeIdKey, IHICData incomingDataObject)
	{
		List<IHICData> doList = null;

		Collection<IHICData> storedHICDataList = dObjectColl.get(workflowIdKey).get(nodeIdKey).values();
		Iterator <IHICData> dataObjectsIterator = storedHICDataList.iterator();
		while (dataObjectsIterator.hasNext())
		{
			IHICData storedHICData = dataObjectsIterator.next();

//			if (storedHICData.getUniqueID().equals(incomingDataObject.getUniqueID()))
			if ((storedHICData.getUniqueID().equals(incomingDataObject.getUniqueID())) 
					&& (isRecordEqual(storedHICData, incomingDataObject)))
			{
				if (doList == null) doList = new ArrayList<IHICData>();
				doList.add(storedHICData);
			}
		}
		return doList;
	}
	
	public boolean isDOPresentOnWorkflowNode(String workflowIdKey, String nodeIdKey, 
			IHICData incomingDataObject)
	{
		List<IHICData> doList 
			= getMatchingDataObjectForWorkflowNode(workflowIdKey, nodeIdKey, incomingDataObject);
		if (doList != null) return true;
		return false;
	}
	
	
	
	
	
	public IHICData getMatchingDataObjectForUser(IHICData incomingDataObject)
	{
		if (isPatientSchedulePresentInFormValues(incomingDataObject))
		{
//			WorkflowComponent.log(0, "Inside [getMatchingDataObjectForUser][Going to DB Impl]");
			incomingDataObject = doMgrDb.getMatchingDataObjectForUser(incomingDataObject);
			return incomingDataObject;
		}
		else
		{
//			WorkflowComponent.log(0, "Inside [getMatchingDataObjectForUser][Taking from memory]");
			List<IHICData> dOs = getDOForUser(incomingDataObject, false, false);
			if (dOs != null)
			{
				return dOs.get(0);
			}
		}
		return null;
	}
	
	
	public List<IHICData> getAllMatchingDataObjectForUser(IHICData incomingDataObject)
	{
		return getDOForUser(incomingDataObject, true, true);
	}
	
	
	private List<IHICData> getDOForUser(IHICData incomingDataObject, boolean getAll, 
			boolean compareUserIdOnly)
	{
		/*Hashtable<String, Hashtable<String, List<IHICData>>> matchingDOs 
		  		= new Hashtable<String, Hashtable<String, List<IHICData>>>();*/
		List<IHICData> returnDataList = null;
		if ((dObjectColl == null) || (incomingDataObject == null) 
				|| (incomingDataObject.getData() == null) 
				|| (incomingDataObject.getData().getUserId() == null)) 
			return returnDataList;
		
		Set<String> workflowIdKeys = dObjectColl.keySet();
		Iterator<String> keyIterator = workflowIdKeys.iterator();
		while (keyIterator.hasNext())
		{ 
			String workflowIdKey = (String) keyIterator.next();
			HashMap<String, HashMap<String, IHICData>> dObjectColl_1 = dObjectColl.get(workflowIdKey);
			Set<String> nodeIdKeys = dObjectColl_1.keySet();
			Iterator<String> nodeIdKeyIterator = nodeIdKeys.iterator();
			while (nodeIdKeyIterator.hasNext())
			{
				String nodeIdKey = (String)nodeIdKeyIterator.next();
				Collection<IHICData> storedHICDataList = dObjectColl_1.get(nodeIdKey).values();
				Iterator <IHICData> dataObjectsIterator = storedHICDataList.iterator();
				while (dataObjectsIterator.hasNext())
				{
					IHICData storedHICData = dataObjectsIterator.next();
					
					boolean areDOEqual = false;
					if (compareUserIdOnly) 
						areDOEqual = isDOUserIdEqual(storedHICData, incomingDataObject);
					else 
						areDOEqual = isDOEqual(storedHICData, incomingDataObject);
					
					if (areDOEqual)
					{
						if (returnDataList == null) returnDataList = new ArrayList<IHICData>();
//						WorkflowComponent.log(0, "[getDOForUser][storedHICData]\n" + storedHICData);
						returnDataList.add(storedHICData);
						
						if (!getAll) return returnDataList;
					}
				}
			}
		}

		return returnDataList;
	}

	private boolean isDOEqual(IHICData stored, IHICData incoming)
	{
		if(stored==null ||stored.getData()==null||incoming==null||incoming.getData()==null)
			return false;
		
		boolean equal = true;
		try 
		{
			equal = equal && isDOUserIdEqual(stored, incoming);

			if ((stored.getData().getFormPattern().getFormId() != null) 
					&& (incoming.getData().getFormPattern().getFormId() != null))
			{
				// Check FP
				equal = equal && 
						stored.getData().getFormPattern().getFormId()
							.equals(
									incoming.getData().getFormPattern().getFormId());
			}
			
			if ((stored.getData().getDataPattern().getDataPatternId() != null) 
					&& (incoming.getData().getDataPattern().getDataPatternId() != null))
			{
				// Check DP
				equal = equal && 
						stored.getData().getDataPattern().getDataPatternId()
							.equals(
									incoming.getData().getDataPattern().getDataPatternId());
			}
			
			if ((stored.getData().getUserPatternString() != null) 
					&& (incoming.getData().getUserPatternString() != null))
			{
				// Check UP
				equal = equal && 
						stored.getData().getUserPatternString()
							.equals(
									incoming.getData().getUserPatternString());
			}
			
			// Check if DO present on Node pertains to same record.
			equal = equal && isRecordEqual(stored, incoming);
		}
		catch (NullPointerException npe)
		{
		}
		return equal;
	}
	
	private boolean isDOUserIdEqual(IHICData stored, IHICData incoming)
	{
		if (stored == null || stored.getData() == null || incoming == null || incoming.getData() == null)
			return false;
		if ((stored.getData().getUserId() == null) || (incoming.getData().getUserId() == null))
			return false;
		
		return stored.getData().getUserId().equals(incoming.getData().getUserId());
	}
	
	
	private boolean isRecordEqual(IHICData stored, IHICData incoming)
	{
		boolean equal = true;
		
		if (((stored.getData().getFormPattern().getFormValues() == null) 
				&& (incoming.getData().getFormPattern().getFormValues() != null))
				||
				((stored.getData().getFormPattern().getFormValues() != null) 
						&& (incoming.getData().getFormPattern().getFormValues() == null)))
			return false;
		
		if ((stored.getData().getFormPattern().getFormValues() != null) 
				&& (incoming.getData().getFormPattern().getFormValues() != null))
		{
			String storedPatient = (String) stored.getData().getFormPattern().getFormValues()
					.get(ApplicationConstant.KEY_PATIENT_MRN);
			String incomingPatient = (String) incoming.getData().getFormPattern().getFormValues()
					.get(ApplicationConstant.KEY_PATIENT_MRN);
			equal = equal && StringUtil.isStringValueEqual(storedPatient, incomingPatient, false);
			
			
			storedPatient = (String) stored.getData().getFormPattern().getFormValues()
					.get(ApplicationConstant.KEY_PATIENT_ID);
			incomingPatient = (String) incoming.getData().getFormPattern().getFormValues()
					.get(ApplicationConstant.KEY_PATIENT_ID);
			equal = equal && StringUtil.isStringValueEqual(storedPatient, incomingPatient, false);
			

			storedPatient = (String) stored.getData().getFormPattern().getFormValues()
					.get(ApplicationConstant.KEY_SCHEDULE_ID);
			incomingPatient = (String) incoming.getData().getFormPattern().getFormValues()
					.get(ApplicationConstant.KEY_SCHEDULE_ID);
			equal = equal && StringUtil.isStringValueEqual(storedPatient, incomingPatient, false);

		}
		
		return equal;
	}

	
/*	private IHICData getMatchingDataObjectEx(IHICData incomingDataObject)
	{
		Collection<Hashtable<String, Collection<IHICData>>> wfLevelColl = dObjectColl.values();
		Iterator <Hashtable<String, Collection<IHICData>>> wfLevelCollIterator = wfLevelColl.iterator();
		while (wfLevelCollIterator.hasNext())
		{
			Collection<Collection<IHICData>> doCollection = wfLevelCollIterator.next().values();
			Iterator <Collection<IHICData>> doCollIterator = doCollection.iterator();
			while (doCollIterator.hasNext())
			{
				Collection<IHICData> storedHICDataList = doCollIterator.next();
				Iterator <IHICData> dataObjectsIterator = storedHICDataList.iterator();
				while (dataObjectsIterator.hasNext())
				{
					IHICData storedHICData = dataObjectsIterator.next();
					if (storedHICData.equals(incomingDataObject))
					{
						return storedHICData;
					}
				}
			}
		}
		
		return null;
	}*/

	public void removeDataObjectFromNode(String workflowId, String nodeId, IHICData dataObject)
	{
		removeDataObjectFromNode(workflowId, nodeId, dataObject, true);
	}

	public void removeDataObjectFromNode(String workflowId, String nodeId, IHICData dataObject, boolean performDbOperation)
	{
		if (dObjectColl == null) return;
		
		WorkflowComponent.log(0, "[BEFORE REMOVAL; DATA OBJECT COLLECTION IS ] \n" + dObjectColl);
		
		/*
		 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		 */
		DataObjectListUtil.printDataObject(dataObject, "HICDATA RECEIVED FOR [removeDataObjectFromNode]", "*");
		/*
		 * &&&&&&&&&&&&&&&&&&&&&& PRINTING &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		 */
		
		WorkflowComponent.log(0, "[REMOVING DATA OBJECT FROM NODE][WORKFLOWID]" + workflowId + "\t[NODEID]" + nodeId);
		HashMap<String, IHICData> doColl = dObjectColl.get(workflowId).get(nodeId);
		WorkflowComponent.log(0, "[doColl SIZE]" + doColl.size());
		
		if (doColl != null)
		{
			doColl.remove(dataObject.getUniqueID());
			/*for (int i = 0; i < doColl.size(); i++)
			{
				IHICData storedHICData = doColl.get(i);
				if (storedHICData.equals(dataObject))
				{
					WorkflowComponent.log(0, "[REMOVING DATA OBJECT]");
					doColl.remove(storedHICData);
					//break;
				}
			}*/
		}
		
		/*Iterator <IHICData> dataObjectsIterator = doColl.iterator();
		while (dataObjectsIterator.hasNext())
		{
			IHICData storedHICData = dataObjectsIterator.next();
			if (storedHICData.equals(dataObject))
			{
				WorkflowComponent.log(0, "[REMOVING DATA OBJECT]");
				//dataObjectsIterator.remove();
				doColl.remove(storedHICData);
				//break;
			}
		}*/
		
		WorkflowComponent.log(0, "[AFTER REMOVAL; DATA OBJECT COLLECTION IS ] \n" + dObjectColl);
		
		// Update in database also.
		if (performDbOperation)
			doMgrDb.removeDataObjectFromNode(workflowId, nodeId, dataObject);
	}

	
	public void storeDataObjectOnNode(String workflowId, String nodeId, IHICData dataObject)
	{
		DataObjectListUtil.printDataObject(dataObject, "HICDATA RECEIVED FOR [STORING ONTO NODE]", "*");
		// For updating DO Unique Id
		dataObject = DataObjectListUtil.updateUniqueId(dataObject);
		DataObjectListUtil.printDataObject(dataObject, "[UNIQUE ID UPDATED] [STORING ONTO NODE]", "*");
		
		if (dObjectColl == null) 
			dObjectColl = new HashMap<String, HashMap<String, HashMap<String, IHICData>>>();
		
		WorkflowComponent.log(0, "[BEFORE STORING; DATA OBJECT COLLECTION IS ] \n" + dObjectColl.size());
		
		if (dObjectColl.containsKey(workflowId))
		{
			HashMap<String, HashMap<String, IHICData>> nodeColl = dObjectColl.get(workflowId);
			if ((nodeColl != null) && (nodeColl.containsKey(nodeId)))
			{
				HashMap<String, IHICData> doColl = nodeColl.get(nodeId);
				doColl.put(dataObject.getUniqueID(), dataObject);
			}
			else
			{
				if (nodeColl == null) nodeColl = new HashMap<String, HashMap<String, IHICData>>(); 
				HashMap<String, IHICData> doColl = new HashMap<String, IHICData>();
				doColl.put(dataObject.getUniqueID(), dataObject);
				nodeColl.put(nodeId, doColl);
			}
		}
		else
		{
			HashMap<String, HashMap<String, IHICData>> nodeColl 
				= new HashMap<String, HashMap<String, IHICData>>(); 
			
			HashMap<String, IHICData> doColl = new HashMap<String, IHICData>();
			doColl.put(dataObject.getUniqueID(), dataObject);
			nodeColl.put(nodeId, doColl);
			dObjectColl.put(workflowId, nodeColl);
		}
		
		WorkflowComponent.log(0, "[AFTER STORING; DATA OBJECT COLLECTION IS ] \n" + dObjectColl.size());
		
		// Store it to DB also
		doMgrDb.storeDataObjectOnNode(workflowId, nodeId, dataObject);
	}
	
	@Override
	public void appendToDataObjectMetaData(String workflowId, String nodeId, IHICData dataObject,
			Dataobject doDb) throws WorkflowComponentException
	{
		doMgrDb.appendToDataObjectMetaData(workflowId, nodeId, dataObject, doDb);
	}
	
	
	@Override
	public IHICData getMatchingDataObjectForUser(IHICData incomingDataObject, String nodeExecStatus)
	{
		return doMgrDb.getMatchingDataObjectForUser(incomingDataObject, nodeExecStatus);
	}
	
	@Override
	public IHICData getCompletedMatchingDataObjectForUser(IHICData incomingDataObject)
	{
		return doMgrDb.getCompletedMatchingDataObjectForUser(incomingDataObject);
	}

	/** Instead "isPatientSchedulePresentInFormValues" function should be used */
	boolean isPatientPresentInFormValues(IHICData incomingDataObject)
	{
		return ((DataObjectManagerDB)doMgrDb).isPatientPresentInFormValues(incomingDataObject);
	}
	
	boolean isPatientSchedulePresentInFormValues(IHICData incomingDataObject)
	{
		return ((DataObjectManagerDB)doMgrDb).isPatientSchedulePresentInFormValues(incomingDataObject);
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
	@Override
	public void updateDataObjectFromNodeOnGivenStatus(String workflowId,
			String nodeId, IHICData dataObject, String status) 
	{
		
		removeDataObjectFromNode(workflowId, nodeId, dataObject,false);
		// Update in database also.
		doMgrDb.updateDataObjectFromNodeOnGivenStatus(workflowId, nodeId, dataObject,status);
		
	}


	  /**
	   * This method is used to delete the objectmetadata
	   * in case of error and reverting the tool to previous status.
	   */
	@Override
	public void deleteToDataObjectMetaData(Dataobject doDb)
			throws WorkflowComponentException 
	{
		doMgrDb.deleteToDataObjectMetaData(doDb);
		
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
		doMgrDb.deleteToDataObject(doDb);
	}
	
	@Override
	public IHICData updateFormValuesWithDBData(IHICData incomingDO)
	{
		return doMgrDb.updateFormValuesWithDBData(incomingDO);
	}
	
	
	public HashMap<String, List<IHICData>> getHangingObjects()
	{
		return _hangingObjs;
	}


	public void addToHangingObjects(String workflowName, IHICData dataObject)
	{
		List<IHICData> doList = _hangingObjs.get(workflowName);
		if (doList == null) doList = new ArrayList<IHICData>();
		
		doList.add(dataObject);
	}
	
	public void removeFromHangingObjects(String workflowName, IHICData dataObject)
	{
		List<IHICData> doList = _hangingObjs.get(workflowName);
		if (doList == null) doList = new ArrayList<IHICData>();
		
		doList.remove(dataObject);
	}
	
	
	/**
	 * Updating status of DO in Database
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
		doMgrDb.updateDataObjectFromNodeOnGivenStatus(workflowId, nodeId, dataObject,status);
	}

}

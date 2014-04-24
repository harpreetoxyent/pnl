package com.oxymedical.component.workflowComponent.db.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.appadmin.model.resources_app.Dataobject;
import com.oxymedical.component.appadmin.model.resources_app.Dataobjectmetadata;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.WorkflowComponent;
import com.oxymedical.component.workflowComponent.constants.WorkflowConstant;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;
import com.oxymedical.component.workflowComponent.exception.WorkflowComponentException;
import com.oxymedical.component.workflowComponent.operation.db.ConnectionDatabase;
import com.oxymedical.component.workflowComponent.operation.db.WorkflowDatabaseInfo;
import com.oxymedical.core.commonData.app.ApplicationConstant;

public class DataObjectMetaDataImpl extends BaseDBImpl implements IDataObjectMetaData
{

	@Override
	public Dataobjectmetadata addDataObjectMetaData(Dataobject doQueue, String key, Object value)
			throws WorkflowComponentException
	{
		return addDataObjectMetaDataToDB(doQueue, key, value);
	}
	
	
	public static Dataobjectmetadata addDataObjectMetaDataToDB(Dataobject doQueue, String key, Object value)
			throws WorkflowComponentException
	{
		return addUpdateDataObjectMetaDataToDB(doQueue, key, value, null);
	}
	
	
	public static Dataobjectmetadata addUpdateDataObjectMetaDataToDB(Dataobject doQueue, String key,
			Object value, Dataobjectmetadata doMetadata) throws WorkflowComponentException
	{
		if (doMetadata == null) doMetadata = new Dataobjectmetadata();
		
		if (doQueue != null) doMetadata.setDataobject(doQueue);
		if (key != null) doMetadata.setDatakey(key);
		if (value != null) doMetadata.setDataValue(value);
		
		Object obj = null;
		try
		{
			obj = ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(doMetadata);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}

		if (obj != null)
		{
			return (Dataobjectmetadata) obj;
		}
		return null;
	}

	
	
	public static Dataobjectmetadata checkDataObjectMetaDataExist(Dataobject doQueue, 
			String key, Object value)
	{
		Dataobjectmetadata doMetadataExist = null;
		String sqlQuery = WorkflowSqlCommands.Find_DataObjectMetaData;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter doIdParam 
			= new NameQueryParameter(WorkflowSqlCommands.CONST_DOMETADATA_DOID, doQueue);
		listParam.add(doIdParam);
		
		NameQueryParameter keyParam 
			= new NameQueryParameter(WorkflowSqlCommands.CONST_DOMETADATA_KEY, key);
		listParam.add(keyParam);
		
		/*if ((value.getClass() == String.class)
				|| (value.getClass() == int.class)
				|| (value.getClass() == Integer.class)
				|| (value.getClass() == long.class)
				|| (value.getClass() == Long.class)
				|| (value.getClass() == float.class)
				|| (value.getClass() == Float.class)
				|| (value.getClass() == double.class)
				|| (value.getClass() == Double.class)
			)
		{
			value = (String)value;
		}
		else
		{
			return doMetadataExist;
		}

		NameQueryParameter valueParam 
			= new NameQueryParameter(WorkflowSqlCommands.CONST_DOMETADATA_VALUE, value);
		listParam.add(valueParam);*/
		
		List list;
		try
		{
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);

			if ((list != null) && (list.size() > 0))
			{
				for (Iterator doMetadataRow = list.iterator(); doMetadataRow.hasNext();)
				{
					Object doMetadataRowData = doMetadataRow.next();
					doMetadataExist = (Dataobjectmetadata) doMetadataRowData;
				}
			}
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}

		return doMetadataExist;
	}
	
	
	
	public static Dataobjectmetadata addDataObjectMetadataWithExistingCheck(Dataobject doQueue, String key,
			Object value) throws WorkflowComponentException
	{
		Dataobjectmetadata doMetadata = checkDataObjectMetaDataExist(doQueue, key, value);
		return addUpdateDataObjectMetaDataToDB(doQueue, key, value, doMetadata);
	}
	
	
	/**
	 * Returns list of all Meta data, related to a single data object identified by dataObjectId
	 * @param dataObjectId
	 * @return
	 */
	public static List<Dataobjectmetadata> getAllMetaDataForDataObject(int dataObjectId)
	{
		List<Dataobjectmetadata> list = null;
		String sqlQuery = WorkflowSqlCommands.FIND_ALL_METADATA_FOR_DATAOBJECT;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		
		updateSqlAndCreateNameParameter(listParam,
				new Integer(dataObjectId), WorkflowSqlCommands.CONST_DOMETADATA_DOID, sqlQuery, null,
				false);
		WorkflowComponent.log(0, "[sqlQuery]" + sqlQuery);
		
		try
		{
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}

		return list;
	}
	
	
	public static void deleteMetaDataObject(Dataobjectmetadata dataobjectmetadata) throws WorkflowComponentException
	{
		try
		{
		ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(dataobjectmetadata);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
			throw new WorkflowComponentException(e);
		}

	}


	public static List<Dataobjectmetadata> getAllDataObjectForPatientSchedule(int patientId, int scheduleId)
	{
		List<Dataobjectmetadata> dolist = null;
		String sqlQuery = WorkflowSqlCommands.FIND_ALL_METADATA_FOR_PATIENTID;
		
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		
		updateSqlAndCreateNameParameter(listParam,
				ApplicationConstant.KEY_SCHEDULE_ID, WorkflowSqlCommands.CONST_DOMETADATA_KEY, sqlQuery, null,
				false);
		updateSqlAndCreateNameParameter(listParam,
				""+scheduleId, WorkflowSqlCommands.CONST_DOMETADATA_VALUE, sqlQuery, null,
				false);
		
		WorkflowComponent.log(0, "[getAllDataObjectForPatient][sqlQuery]" + sqlQuery);
		WorkflowComponent.log(0, "[getAllDataObjectForPatient][listParam]" + listParam.size());
		
		
		try
		{
			dolist = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		}
		catch (DBComponentException e)
		{
			e.printStackTrace();
		}

		return dolist;
	}
	
	
	public static String addMetaDataConditionToQuery(Hashtable<String, Object> metaData, String sqlQuery,
			ArrayList<NameQueryParameter> listParam)
	{
		if ((metaData != null) && (metaData.size() > 0))
		{
			if ( (!sqlQuery.toLowerCase().trim().endsWith(WorkflowSqlCommands.CONDITION_JOINER_WHERE.trim())) 
				&& (!sqlQuery.toLowerCase().trim().endsWith(WorkflowSqlCommands.CONDITION_JOINER_AND.trim())) )
				sqlQuery = sqlQuery + WorkflowSqlCommands.CONDITION_JOINER_AND ;
			sqlQuery = sqlQuery + " (";
			Enumeration<String> keys = metaData.keys();
			while (keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value = (String)metaData.get(key);
				
				if ((key != null) && (value != null))
				{
					// Add OR condition
					if (!sqlQuery.endsWith("(")) 
						sqlQuery = sqlQuery + WorkflowSqlCommands.CONDITION_JOINER_OR;

					// Open Bracket
					sqlQuery = sqlQuery + "(";
					
					// Key condition; To make parameter name as UNIQUE, value of "key" is appended to name
				
					sqlQuery = updateSqlAndCreateNameParameter(listParam, key, WorkflowSqlCommands.CONST_DOMETADATA_KEY + key, 
							sqlQuery, WorkflowSqlCommands.DATAOBJECTMETADATA_DATAKEY_CONDITION + key, false);
					
					// Add AND condition
					sqlQuery = sqlQuery + WorkflowSqlCommands.CONDITION_JOINER_AND;
					
					// Value condition; ; To make parameter name as UNIQUE, value of "value" is appended to name
					sqlQuery = updateSqlAndCreateNameParameter(listParam, value, WorkflowSqlCommands.CONST_DOMETADATA_VALUE + value.replaceAll(WorkflowConstant.NODE_PATTERN_NAME,""), 
							sqlQuery, WorkflowSqlCommands.DATAOBJECTMETADATA_DATAVALUE_CONDITION + value.replaceAll(WorkflowConstant.NODE_PATTERN_NAME,""), false);
					
					// Close Bracket
					sqlQuery = sqlQuery + ") ";
				}
				
			} // End While
			
			// Closing BRACKET matching opening bracket
			sqlQuery = sqlQuery + ")";

		} // Closing bracket for MetaData
		
		return sqlQuery;
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

		Dataobject doQueue = DataObjectQueueImpl.getDataObjectBasedOnId(41);
		
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		ht.put("PatientId", "234");
		ht.put("PatientMRN", "54345545");
		ht.put("InputScreen", "ToolDownsamplingInput");
		
		String key = "toolOutputFile";
		try
		{
			/*Dataobjectmetadata resultantDOMD = addUpdateDataObjectMetaDataToDB(doQueue, key,
					ht, null);*/
			Dataobjectmetadata checkDOMD = checkDataObjectMetaDataExist(doQueue, 
					key, ht);
		}
		catch (/*WorkflowComponent*/Exception e)
		{
			e.printStackTrace();
		}

		
		List<Dataobjectmetadata> output = getAllMetaDataForDataObject(41);
		
		WorkflowComponent.log(0, output.size());
		
		for (int i = 0; i < output.size(); i++)
		{
			Dataobjectmetadata metaData = output.get(i);
			WorkflowComponent.log(0, "[Id]" + metaData.getId() + "\t[Datakey]" + metaData.getDatakey() + "\t[Datavalue]" + metaData.getDatavalue());
		}
	}
}

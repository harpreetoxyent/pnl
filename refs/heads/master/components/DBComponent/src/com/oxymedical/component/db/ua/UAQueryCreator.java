package com.oxymedical.component.db.ua;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.hibernate.mapping.PersistentClass;

import com.oxymedical.component.db.application.query.Condition;
import com.oxymedical.component.db.application.query.Join;
import com.oxymedical.component.db.application.query.QueryParameter;
import com.oxymedical.component.db.application.query.parser.HICQueryParser;
import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.dao.ConcreteDAO;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;
import com.oxymedical.component.db.utilities.DBOperationUtilities;
import com.oxymedical.core.ioutil.FileIO;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.core.stringutil.StringUtil;

/**
 * This class is used to create the query based on input sql from ui
 * for useradmin.
 * added on 21 july 2009
 * @author pra
 *
 */
public class UAQueryCreator {
	
	
	
	public UAQueryCreator()
	{
		
	}

	/**
	 * This method returns the hibernate query.
	 * added by pra on 21 july 2009
	 * @param tableNameClassNameMap
	 * @param queryParam
	 * @param queryType
	 * @return
	 */
	public String queryString(Hashtable<String, ConcreteDAO> tableNameClassNameMap,QueryParameter queryParam, String queryType,Properties propertyObject)
	{
		String classtableNames = null;
		String selClassField = null;
		String fieldTable = null;
		String classFields = null;
		String selCondition = null;
		String aliasName = null;
		String fieldName = null;
		String tableName = null;
		String columnTable = null;
		String fieldValue = null;
		String hsqlQuery = null;
		ConcreteDAO concreteDAO = null;
		//this is the join condition query
		String joinCondition=null;
		HICQueryParser hicQueryParser = new HICQueryParser();
		PersistentClass persistentClass = null;
		List tablePropertyList = null;
		Hashtable<Integer, Condition> conditionHash = null;
		//used to get the join hash
		Hashtable<Integer, Condition> joinHash = null;
		LinkedHashMap<String, String> reqFieldHash = queryParam
				.getFieldNameLabelHash();
		Hashtable<PersistentClass, String> tableAliasHash = new Hashtable<PersistentClass, String>();
		// Enumeration reqFieldEnum = reqFieldHash.keys();
		// hashtable change into LinkedHashMap
		Object[] reqFieldEnum = reqFieldHash.keySet().toArray();
		List dataTableName = queryParam.getTableName();
		String conditionString = queryParam.getCondition();
		classtableNames = "";

		try {
			if (dataTableName.size() >= 2) {
				DBOperationUtilities.validateField(queryType, reqFieldHash);
			}
			if (!conditionString.equals(DBConstants.DB_BLANK)
					&& conditionString != null) {
				// DBComponent.logger.log(0," Create condition hash");
				conditionHash = hicQueryParser.parseConditions(conditionString);
			} else if (null != queryParam.getConditionHash()
					&& !(queryParam.getConditionHash().size()==0)) {

				conditionHash = queryParam.getConditionHash();
			}
			if(null != queryParam.getJoinHash()
					&& !(queryParam.getJoinHash().size()==0))
					{
				  joinHash=queryParam.getJoinHash();
					}
			for (int tableCounter = 0; tableCounter < dataTableName.size(); tableCounter++) {
				String retTable = (String) dataTableName.get(tableCounter);
				concreteDAO = tableNameClassNameMap.get(retTable.toLowerCase()
						.trim());
				if (null == concreteDAO) {
				
					throw (new DBComponentException(
							DBExceptionConstants.NULL_POINTER_EXCEPTION
									+ retTable + DBConstants.DB_CONCRETE_DAO
									+ concreteDAO + " in getDAOByName"));
				}

				persistentClass = concreteDAO.getPersistanceClass();
				aliasName = persistentClass.getTable().getName();
				tableAliasHash.put(persistentClass, aliasName);
				classtableNames += concreteDAO.getDaoClassName()
						+ DBConstants.DB_AS_CLAUSE + aliasName + ",";
			}
			classtableNames = classtableNames.substring(0, classtableNames
					.length() - 1);
		/*	if(null!=joinHash)
			{
			//added to retrive the  join criteria in hibernate form
				joinCondition=retHibJoinCriteria(joinHash,persistentClass,tableAliasHash,tableNameClassNameMap);
			}*/
			// retreiving criteria in hibernate form
			if (null != conditionHash) {
				selCondition = retHibCriteria(conditionHash, persistentClass,
						tableAliasHash,tableNameClassNameMap,propertyObject);
			}
			// reqFieldEnum = reqFieldHash.keys();
			reqFieldEnum = reqFieldHash.keySet().toArray();
			selClassField = "";
			classFields = "";
			for (int counter = 0; counter < reqFieldEnum.length; counter++) {
				tablePropertyList = null;
				String key = (String) reqFieldEnum[counter];
				fieldTable = key;
				// if field has table name too
				if (fieldTable.indexOf('.') > 0) {
					String[] tableField = DBOperationUtilities
							.retreiveTableField(fieldTable);
					fieldName = tableField[1].toLowerCase().trim();
					tableName = tableField[0];
					persistentClass = (PersistentClass) tableNameClassNameMap
							.get(tableName.toLowerCase().trim())
							.getPersistanceClass();
					if (null == persistentClass) {
						throw (new DBComponentException(
								DBExceptionConstants.NULL_POINTER_EXCEPTION
										+ fieldName + DBConstants.DB_COMMA
										+ tableName
										+ DBConstants.DB_PERSISTENT_CLASS
										+ persistentClass + " in getDAOByName"));
					}
				} else {
					fieldName = fieldTable.toLowerCase().trim();
				}
				if (tableAliasHash.size() > 0) {
					columnTable = DBOperationUtilities.extractTableAlias(
							persistentClass, tableAliasHash);

				}
				if (null == columnTable) {
					throw new DBComponentException(
							DBExceptionConstants.NULL_POINTER_EXCEPTION
									+ DBConstants.DB_COLUMN_TABLE + columnTable
									+ " in getDAOByName");
				}
				if (null == tablePropertyList) {
						fieldValue = reqFieldHash.get(fieldTable);
						String propertyValue=propertyObject.getProperty(fieldValue.trim());
						if(propertyValue!=null)
						{
							classFields=propertyValue;
							System.out.println("");
						}
						else
						{
						classFields = (String) DBOperationUtilities
								.retClassField(persistentClass, fieldName);
						}
					if (null == classFields) {
						throw new DBComponentException(
								DBExceptionConstants.NULL_POINTER_EXCEPTION
										+ persistentClass
										+ DBConstants.DB_COMMA + fieldName
										+ DBConstants.DB_CLASS_FIELDS
										+ classFields + " in getDAOByName");
					}
					if (fieldName.indexOf(DBConstants.DB_AS_BLANK) > 0) {
						classFields = columnTable
								+ DBConstants.DB_DOT
								+ classFields
								+ DBConstants.DB_AS_CLAUSE
								+ fieldName
										.substring(
												fieldName
														.indexOf(DBConstants.DB_AS_BLANK)
														+ DBConstants.DB_AS_BLANK
																.length(),
												fieldName.length())
										.toUpperCase();
					} else {
						if (classFields.indexOf(DBConstants.DB_DOT)>0) {
							String[] fields=StringUtil.splitString(classFields, DBConstants.DB_DOT);
							classFields = columnTable + DBConstants.DB_DOT
							+ classFields + DBConstants.DB_AS_CLAUSE
							+ fields[1].toUpperCase();
							
						}
						else{
						classFields = columnTable + DBConstants.DB_DOT
								+ classFields + DBConstants.DB_AS_CLAUSE
								+ classFields.toUpperCase();
						}
						
					}
					if (null != classFields)
						selClassField += classFields + ",";
				} else {
					for (int listCounter = 0; listCounter < tablePropertyList
							.size(); listCounter++) {
						String propertyName = (String) tablePropertyList
								.get(listCounter);
						classFields = columnTable + DBConstants.DB_DOT
								+ propertyName + DBConstants.DB_AS_CLAUSE
								+ propertyName.toUpperCase();
						if (null != classFields)
							selClassField += classFields + ",";
					}
				}
			}
			if (null == selClassField)
				selClassField = DBConstants.DB_BLANK;
			if (!(selClassField.equals(DBConstants.DB_BLANK))) {
				selClassField = selClassField.substring(0, selClassField
						.length() - 1);
				selClassField = DBConstants.DB_SELECT_CLAUSE + selClassField;
			}
			if (null == selCondition)
				selCondition = DBConstants.DB_BLANK;
			if (!selCondition.equals(DBConstants.DB_BLANK)) {
				selCondition = DBConstants.DB_WHERE_CLAUSE + selCondition;
			}
			if(null==joinCondition)
			{
			//join condition
				joinCondition=DBConstants.DB_BLANK;
			}
			if (!joinCondition.equals(DBConstants.DB_BLANK)) {
				joinCondition = DBConstants.DB_SPACE + joinCondition;
			}
			classtableNames = DBConstants.DB_FROM_CLAUSE + classtableNames;
			hsqlQuery = selClassField + classtableNames +joinCondition+selCondition;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return hsqlQuery;
	}
	
	/**
	 * This method returns the condition string for useramdin
	 * added by pra on 21 july 2009
	 * @param conditionHash
	 * @param persistentClass
	 * @param tableAliasHash
	 * @param tableNameClassNameMap
	 * @return
	 * @throws DBComponentException
	 */
	private String retHibCriteria(Hashtable conditionHash,
			PersistentClass persistentClass, Hashtable tableAliasHash,Hashtable<String, ConcreteDAO> tableNameClassNameMap,Properties propertyObject)
			throws DBComponentException {
		Object selConstant = null;
		String selCondition = null;
		String table = null;
		String join = null;
		Enumeration conditionHashEnum = null;
		ConcreteDAO concreteDAO = null;
		boolean aliasExist = false;
		boolean isProperty=false;
		conditionHashEnum = conditionHash.keys();
		if (null != conditionHashEnum) {
			selCondition = "";
			while (conditionHashEnum.hasMoreElements()) {
				int conditionCounter = 0;
				join = "";

				Integer condNo = (Integer) conditionHashEnum.nextElement();
				Condition retCondition = (Condition) conditionHash.get(condNo);
				String retField = retCondition.getField();
				String propertyValue=propertyObject.getProperty(retField.trim());
				if(propertyValue!=null)
				{
					retField=propertyValue;
					isProperty=true;
				}
				else {
					String[] tableField = DBOperationUtilities
							.retreiveTableField(retField);
					retField = tableField[1];
				}
				String selOperator = retCondition.getOperator();

				if (null != retCondition.getJoin()
						&& !(retCondition.equals(DBConstants.DB_BLANK))) {
					join = retCondition.getJoin();
				}

				if (!retCondition.getValue().equals(DBConstants.DB_BLANK)
						&& null != retCondition.getValue()) {
					selConstant = (String) retCondition.getValue();
				} else {
					String consTable = retCondition.getConsTable();
					String consTableValue =consTable+"."+retCondition.getConsField();
					String consValue=propertyObject.getProperty(consTableValue.trim());				
					concreteDAO = (ConcreteDAO) tableNameClassNameMap
							.get(consTable.toLowerCase().trim());
					if (null == concreteDAO) {
						throw (new DBComponentException(
								DBExceptionConstants.NULL_POINTER_EXCEPTION
										+ consTable
										+ DBConstants.DB_CONCRETE_DAO
										+ concreteDAO + " in retHibCriteria"));
					}
					persistentClass = concreteDAO.getPersistanceClass();
					if (tableAliasHash.size() > 0) {
						if (retCondition.getConsTable().equals(
								DBConstants.DB_BLANK)
								|| retCondition.getConsField().equals("")) {
							throw (new DBComponentException(
									DBExceptionConstants.EXCEPTION
											+ "CONDITION retCondition = "
											+ retCondition.getConsTable()
											+ DBConstants.DB_COMMA
											+ "CONDITION retCondition = "
											+ retCondition.getConsTable()
											+ " in retHibCriteria"));
						}

						table = DBOperationUtilities.extractTableAlias(
								persistentClass, tableAliasHash);
					}
					if (null == table)
						throw new DBComponentException(
								DBExceptionConstants.NULL_POINTER_EXCEPTION
										+ consTable + DBConstants.DB_TABLE
										+ table + " in retHibCriteria");
					if(consValue!=null)
					{
						selConstant = table
						+ DBConstants.DB_DOT+consValue;	
					}
					else
					{
					
					selConstant = table
							+ DBConstants.DB_DOT
							+ DBOperationUtilities.retClassField(
									persistentClass, retCondition
											.getConsField());
				   }

				}

				// more than 1 table involved
				if (!retCondition.getTable().equals(DBConstants.DB_BLANK)
						&& retCondition != null) {
					String tableName = retCondition.getTable();
					concreteDAO = (ConcreteDAO) tableNameClassNameMap
							.get(tableName.toLowerCase().trim());
					if (null == concreteDAO) {
						throw (new DBComponentException(
								DBExceptionConstants.NULL_POINTER_EXCEPTION
										+ tableName
										+ DBConstants.DB_CONCRETE_DAO
										+ concreteDAO + " in retHibCriteria"));
					}

					persistentClass = concreteDAO.getPersistanceClass();
				}
				table = null;
				Object retFieldObject =null;
				if(isProperty)
				{
					retFieldObject=retField;
					isProperty=false;
				}
				else
				{
					retFieldObject = DBOperationUtilities.retClassField(
						persistentClass, retField);
				}
				if (null == retFieldObject) {
					throw new DBComponentException(
							DBExceptionConstants.NULL_POINTER_EXCEPTION
									+ DBConstants.DB_RET_FIELD_
									+ retFieldObject + " in retHibCriteria");
				}
				// if table have any alias defined for it
				if (tableAliasHash.size() > 0) {
					table = DBOperationUtilities.extractTableAlias(
							persistentClass, tableAliasHash);
					if (null == table) {
						throw new DBComponentException(
								DBExceptionConstants.NULL_POINTER_EXCEPTION
										+ DBConstants.DB_TABLE + table
										+ " in retHibCriteria");
					}
					aliasExist = true;
				}
				// no alias defined for table

				if (aliasExist)
					selCondition += DBConstants.DB_SPACE + join
							+ DBConstants.DB_SPACE + table + DBConstants.DB_DOT
							+ retFieldObject + DBConstants.DB_SPACE
							+ selOperator + selConstant;
				else
					selCondition += DBConstants.DB_SPACE + join
							+ DBConstants.DB_SPACE + retFieldObject
							+ DBConstants.DB_SPACE + selOperator + selConstant;
				conditionCounter++;
			}
		}
		return selCondition;

	}
	
}

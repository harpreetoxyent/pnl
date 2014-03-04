package com.oxymedical.component.db.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.application.query.Field;
import com.oxymedical.component.db.application.register.IRegisterWindow;
import com.oxymedical.component.db.application.register.pattern.DataPattern;
import com.oxymedical.component.db.application.register.pattern.FormPattern;
import com.oxymedical.component.db.application.tree.TreeNode;
import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;
import com.oxymedical.component.db.utilities.ConvertDataType;
import com.oxymedical.core.querydata.QueryData;

/**
 * RenderingComponent interact with DatabaseComponent through DataHandler class.
 * This class implements IDataHandler.
 * 
 * @author Oxyent Medical
 * @version 1.0.0
 */
public class DataHandler implements IDataHandler {
	/** is used to set the user name in it. */
	private String connUserName;
	/** is used to set the password in it. */
	private String connPassword;
	/** is used to set the server address in it. */
	private String serverAddress;
	/** is used to set the application name in it. */
	private String applicationName;
	/** is used to set the base directory name in it. */
	private String baseDirectory;
	/**
	 * is used to set the application formpattern list retrieve from the
	 * hashtable.
	 */
	private List applicationFormList = null;
	/**
	 * this hashtable maps formPattern id string to the objects of the
	 * FormPattern class.
	 */
	private Hashtable<String, FormPattern> formPatternHash = null;
	/**
	 * this hashtable maps dataPattern id string to the objects of the
	 * DataPattern class.
	 */
	private Hashtable<String, LinkedList<DataPattern>> dataPatternHash = null;
	/**
	 * this hashtable maps table name with List of the objects of Field
	 * class.this object has the name and value for the field in it.
	 */
	private Hashtable<String, List<Field>> tableFieldHash = null;
	/**
	 * this List object is used to set the name and value for the field in the
	 * list.
	 */
	private List<Field> fieldList = null;
	/** is used to invoke the methods of this class. */
	IRegisterWindow registerWindow = null;
	/** is used to invoke the methods of this class. */
	IDBComponent dbComponent = null;

	// private Hashtable<String,LinkedList<DataPattern>> testdataPatternHash =
	// null;
	/**
	 * Constructor for initializing several objects
	 * 
	 */
	public DataHandler() {

	}

	/**
	 *This method is use to set connection and set the data for connecting to
	 * the database.
	 * 
	 * @param userName
	 * @param password
	 * @param appName
	 * @param baseDir
	 * @returns void
	 */
	public void connectionSettings(String userName, String password,
			String serverAdd, String appName, String baseDir) {
		connUserName = userName;
		connPassword = password;
		serverAddress = serverAdd;
		applicationName = appName;
		baseDirectory = baseDir;
	}

	/**
	 * This method gets data from database for list.
	 * 
	 * @param fieldLabelHash
	 * @param tableList
	 * @param condition
	 * @throws DBComponentException
	 * @returns String
	 */
	public String getListXML(LinkedHashMap<String, String> fieldLabelHash,
			List tableList, String condition) throws DBComponentException {
		// DBComponent.logger.log(0,"calling getlist for list");
		String XMLOutput = null;

		XMLOutput = dbComponent.getDAOByName(fieldLabelHash, tableList,
				condition);
		return XMLOutput;
	}

	/**
	 *Method use to add hours.
	 * 
	 * @param str_date
	 * @param hours
	 * @throws DBComponentException
	 * @returns String
	 */
	
	public String advanceHours(String str_date, int hours)
			throws DBComponentException {
		String dateStr = null;
		fieldList = new ArrayList<Field>();
		tableFieldHash = new Hashtable<String, List<Field>>();
		ConvertDataType cnvt = new ConvertDataType();
		dateStr = cnvt.advanceHours(str_date, hours);
		/*
		 * executeQuery has been commented. this's why following line has been commented.
		 * If someone required, uncomment this line and use as required.
		 * changes by wasim, 20-May-2009.
		 * 
		 * 
		 */
		/*executeQuery("delete from resDate");
		executeQuery("add into resDate values date:=" + dateStr);*/
		return dateStr;
	}

	/**
	 * This method is use to get data from database for grid.
	 * 
	 * @param listId
	 * @param baseWindow
	 * @throws DBComponentException
	 * @returns String
	 */
	public String getListXML(String listId, String baseWindow)
			throws DBComponentException {
		String XMLOutput = null;
		// DBComponent.logger.log(0,"calling getlist for grid");
		registerWindow = dbComponent.getRegisterWindow();
		formPatternHash = new Hashtable<String, FormPattern>();
		dataPatternHash = new Hashtable<String, LinkedList<DataPattern>>();
		applicationFormList = new ArrayList();
		// DBComponent.logger.log(0,registerWindow.getApplicationPatternHash());
		if (registerWindow.getApplicationPatternHash().isEmpty()) {
			throw new DBComponentException(DBExceptionConstants.EXCEPTION
					+ DBConstants.DB_REGISTER_WINDOW + registerWindow
					+ "in getListXML");
		}
		if (applicationName.equals(DBConstants.DB_BLANK)
				|| null == applicationName) {
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_APPLICATION_NAME + applicationName
							+ "in getListXML");
		}
		applicationFormList = registerWindow.getApplicationPatternHash().get(
				applicationName.trim());
		if (null == applicationFormList || applicationFormList.isEmpty())
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_APPLICATION_FORM_LIST
							+ applicationFormList + "in getListXML");
		for (int formListCounter = 0; formListCounter < applicationFormList
				.size(); formListCounter++) {
			String selFormPattern = (String) applicationFormList
					.get(formListCounter);
			if (selFormPattern.equalsIgnoreCase(baseWindow.trim())) {
				formPatternHash = registerWindow.getBaseFormPatternHash().get(
						selFormPattern);
				dataPatternHash = registerWindow.getBaseDataPatternHash().get(
						selFormPattern);
				if (null != formPatternHash) {
					Enumeration formPatternElementEnum = formPatternHash.keys();
					while (formPatternElementEnum.hasMoreElements()) {
						String selId = (String) formPatternElementEnum
								.nextElement();
						FormPattern selElement = formPatternHash.get(selId);
						if (selElement.getElementName()
								.equalsIgnoreCase(listId)) {
							String dataPatternId = selElement
									.getAssocDataPatternId();
							if (null == dataPatternId)
								continue;
							LinkedList<DataPattern> selDataPattern = dataPatternHash
									.get(dataPatternId);
							if (null == selDataPattern) {
								// DBComponent.logger.log(0," associated data pattern not found");
								continue;
							}
							String databaseName = "";
							String tableName = "";
							if (selDataPattern != null)
								for (int count = 0; count < selDataPattern
										.size(); count++) {
									DataPattern dataPattern = selDataPattern
											.get(count);
									tableName = dataPattern.getTableName();
									databaseName = dataPattern
											.getDatabaseName();
									XMLOutput = dbComponent.getDAOByName(
											databaseName, tableName);
								}
							/*
							 * DataPattern selDataPattern =
							 * dataPatternHash.get(dataPatternId); if(null ==
							 * selDataPattern) {//DBComponent.logger.log(0,
							 * "Associated data pattern does'not exist");
							 * continue; } String tableName =
							 * selDataPattern.getTableName(); String
							 * databaseName = selDataPattern.getDatabaseName();
							 * XMLOutput =
							 * dbComponent.getDAOByName(databaseName,
							 * tableName);
							 */
							break;
						}
					}
				} else {
					// DBComponent.logger.log(0,"No match for given request found");
					return XMLOutput;
				}
			}
		}
		// DBComponent.logger.log(0,"!!!!!!!!! In Data Handler grid output = " +
		// XMLOutput);
		return XMLOutput;
	}

	public List getGridList(String listId, String baseWindow)
			throws DBComponentException {
		// DBComponent.logger.log(0,"this is getList method in getlist*****************************");

		List output = null;
		// DBComponent.logger.log(0,"calling getlist for grid");
		registerWindow = dbComponent.getRegisterWindow();
		formPatternHash = new Hashtable<String, FormPattern>();
		dataPatternHash = new Hashtable<String, LinkedList<DataPattern>>();
		applicationFormList = new ArrayList();
		// DBComponent.logger.log(0,registerWindow.getApplicationPatternHash());
		if (registerWindow.getApplicationPatternHash().isEmpty()) {
			throw new DBComponentException(DBExceptionConstants.EXCEPTION
					+ DBConstants.DB_REGISTER_WINDOW + registerWindow
					+ "  **** in getList    *****");
		}
		if (applicationName.equals(DBConstants.DB_BLANK)
				|| null == applicationName) {
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_APPLICATION_NAME + applicationName
							+ "in getList");
		}
		// DBComponent.logger.log(0,"applicationName---------------"+applicationName);
		// DBComponent.logger.log(0,"registerWindow.getApplicationPatternHash()---------------"+registerWindow.getApplicationPatternHash());
		applicationFormList = registerWindow.getApplicationPatternHash().get(
				applicationName.trim());
		// DBComponent.logger.log(0,"size of applicationFormlist---------------registerWindow"+registerWindow.toString());
		// DBComponent.logger.log(0,"size of applicationFormlist---------------"+applicationFormList.size());
		if (null == applicationFormList || applicationFormList.isEmpty())
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_APPLICATION_FORM_LIST
							+ applicationFormList + "in getList");
		for (int formListCounter = 0; formListCounter < applicationFormList
				.size(); formListCounter++) {

			if (dbComponent != null) {
				// DBComponent.logger.log(0,"^^^^^DBComponent not null dbComponent"+dbComponent.toString());
			} else {
				// DBComponent.logger.log(0,"^^^^^DBComponent null dbComponent");
			}
			String selFormPattern = (String) applicationFormList
					.get(formListCounter);
			// DBComponent.logger.log(0,"^^^^^selFormPattern"+selFormPattern);
			// DBComponent.logger.log(0,"^^^^^baseWindow"+baseWindow);
			if (selFormPattern.equalsIgnoreCase(baseWindow.trim())) {
				// DBComponent.logger.log(0,"^^^^^registerWindow.getBaseFormPatternHash()"+registerWindow.getBaseFormPatternHash());
				// DBComponent.logger.log(0,"^^^^^registerWindow.getBaseDataPatternHash()"+registerWindow.getBaseDataPatternHash());
				formPatternHash = registerWindow.getBaseFormPatternHash().get(
						selFormPattern);
				dataPatternHash = registerWindow.getBaseDataPatternHash().get(
						selFormPattern);
				// DBComponent.logger.log(0,"^^^^^formPatternHash"+formPatternHash.keys());
				// DBComponent.logger.log(0,"^^^^^dataPatternHash"+dataPatternHash.keys());
				if (null != formPatternHash) {
					Enumeration formPatternElementEnum = formPatternHash.keys();
					// DBComponent.logger.log(0,"^^^^^formPatternElementEnum"+formPatternElementEnum.toString());
					while (formPatternElementEnum.hasMoreElements()) {
						String selId = (String) formPatternElementEnum
								.nextElement();
						// DBComponent.logger.log(0,"^^^^^selId"+selId);
						FormPattern selElement = formPatternHash.get(selId);
						// DBComponent.logger.log(0,"^^^^^selElement"+selElement.toString());
						if (selElement.getElementName()
								.equalsIgnoreCase(listId)) {
							String dataPatternId = selElement
									.getAssocDataPatternId();

							// DBComponent.logger.log(0,"^^^^^dataPatternId"+dataPatternId);
							LinkedList<DataPattern> selDataPattern = dataPatternHash
									.get(dataPatternId);
							if (null == selDataPattern) {
								// DBComponent.logger.log(0," associated data pattern not found");
								continue;
							}
							String databaseName = "";
							if (selDataPattern != null)
								for (int count = 0; count < selDataPattern
										.size(); count++) {
									DataPattern dataPattern = selDataPattern
											.get(count);
									String tableName = dataPattern
											.getTableName();
									databaseName = dataPattern
											.getDatabaseName();
									output = dbComponent.getDAOData(
											databaseName, tableName);
								}

							// DataPattern selDataPattern =
							// dataPatternHash.get(dataPatternId);
							// DBComponent.logger.log(0,"^^^^^selDataPattern"+selDataPattern.toString());

							/*
							 * String tableName = selDataPattern.getTableName();
							 * String databaseName =
							 * selDataPattern.getDatabaseName();
							 * 
							 */
							break;
						}
					}
				} else {
					// DBComponent.logger.log(0,"No match for given request found");
					return output;
				}
			}
		}
		// DBComponent.logger.log(0,"!!!!!!!!! In Data Handler grid output = " +
		// XMLOutput);
		return output;
	}

	/**
	 * This method is used to save data in database.
	 * 
	 * @param String
	 *            maintenanceID
	 * @return void
	 * @throws DBComponentException
	 */
	public Object save(String baseWindow, Hashtable requestTable,
			String queryType) throws DBComponentException {
		Hashtable<String, Hashtable<String, List<Field>>> databaseTableHash = new Hashtable<String, Hashtable<String, List<Field>>>();
		formPatternHash = new Hashtable<String, FormPattern>();
		dataPatternHash = new Hashtable<String, LinkedList<DataPattern>>();
		registerWindow = dbComponent.getRegisterWindow();
		try {
			// DBComponent.logger.log(0,"##### solving query In save");
			applicationFormList = registerWindow.getApplicationPatternHash()
					.get(applicationName);

			if (null == applicationFormList || applicationFormList.isEmpty())
				throw new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ DBConstants.DB_APPLICATION_FORM_LIST
								+ applicationFormList + "in save");
			for (int formListCounter = 0; formListCounter < applicationFormList
					.size(); formListCounter++) {

				String selFormPattern = (String) applicationFormList
						.get(formListCounter);
				if (selFormPattern.equalsIgnoreCase(baseWindow.trim())) {
					formPatternHash = registerWindow.getBaseFormPatternHash()
							.get(selFormPattern);
					dataPatternHash = registerWindow.getBaseDataPatternHash()
							.get(selFormPattern);
					break;
				}
			}
			Enumeration requestTableEnum = requestTable.keys();
			while (requestTableEnum.hasMoreElements()) {
				String requestFieldName = (String) requestTableEnum
						.nextElement();
				Enumeration formPatternElementEnum = formPatternHash.keys();
				while (formPatternElementEnum.hasMoreElements()) {
					String retId = (String) formPatternElementEnum
							.nextElement();
					FormPattern selElement = formPatternHash.get(retId);
					if (selElement.getElementId().trim().equalsIgnoreCase(
							requestFieldName.trim())) {
						String dataPatternId = selElement
								.getAssocDataPatternId();
						if (null == dataPatternId) {
							// DBComponent.logger.log(0,"data pattern id not found");
							continue;
						}
						//Change done for the saving data in the multiple tables
						LinkedList<DataPattern> selDataPattern = dataPatternHash
								.get(dataPatternId);
						if (null == selDataPattern) {
							// DBComponent.logger.log(0," associated data pattern not found");
							continue;
						}

						Field field = null;
						String databaseName = "";
						for (int count = 0; count < selDataPattern.size(); count++) {
							DataPattern dataPattern = selDataPattern.get(count);
							String tableName = dataPattern.getTableName();
							databaseName = dataPattern.getDatabaseName();
							String selField = dataPattern.getFieldName();
							if (null == databaseTableHash.get(databaseName)) {
								tableFieldHash = new Hashtable<String, List<Field>>();
								fieldList = new ArrayList<Field>();
							} else {
								// tableFieldHash =
								// databaseTableHash.get(testdatabaseName);
								if (null == tableFieldHash.get(tableName)) {
									fieldList = new ArrayList<Field>();
								} else {
									fieldList = tableFieldHash.get(tableName);
								}
							}
							field = new Field();
							field.setName(selField);
							field.setValue(requestTable.get(requestFieldName));
							fieldList.add(field);
							tableFieldHash.put(tableName, fieldList);
							databaseTableHash.put(databaseName, tableFieldHash);
						}
   // This code is commented as the implementation for the saving the data is changed.
						/*
						 * } String tableName =""; String databaseName ="";
						 * String selField = ""; String tableName =
						 * selDataPattern.getTableName(); String databaseName =
						 * selDataPattern.getDatabaseName(); String selField =
						 * selDataPattern.getFieldName();DBComponent.logger.log(0,
						 * "----------------tableName---------------"
						 * +tableName);DBComponent.logger.log(0,
						 * "----------------tableName---------------"
						 * +databaseName); if(null ==
						 * databaseTableHash.get(databaseName)) { tableFieldHash
						 * = new Hashtable<String,List<Field>>(); fieldList =
						 * new ArrayList<Field>(); } else { tableFieldHash =
						 * databaseTableHash.get(databaseName); if(null ==
						 * tableFieldHash.get(tableName)) { fieldList = new
						 * ArrayList<Field>(); } else { fieldList =
						 * tableFieldHash.get(tableName); } } Field field = new
						 * Field(); field.setName(selField);
						 * field.setValue(requestTable.get(requestFieldName));
						 * fieldList.add(field); tableFieldHash.put(tableName,
						 * fieldList); databaseTableHash.put(databaseName,
						 * tableFieldHash);
						 */
					}
				}
			}

			if (null != databaseTableHash && !databaseTableHash.isEmpty()) {
				Object objId = dbComponent.saveField(databaseTableHash, queryType);
				return objId;
			} else {
				// throw new
				// DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION
				// + DBConstants.DB_APPLICATION_TABLE_HASH + databaseTableHash
				// +"in save");
			}
		} catch (ConstraintViolationException cve) {
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION
							+ baseWindow + DBConstants.DB_COMMA + requestTable
							+ DBConstants.DB_COMMA + queryType + "in save");
		}

		return null;
	}

	/**
	 * This method will be called by other components those have their injected
	 * instance of db component and want to get form value mapping for use
	 * 
	 * @param registerWin
	 * @param baseWindow
	 * @param requestTable
	 * @param queryType
	 * @return
	 * @throws DBComponentException
	 */
	public Hashtable<String, Hashtable<String, List<Field>>> getHashDataForApplication(
			/* IRegisterWindow registerWin, */String applicationName,
			String baseWindow, Hashtable requestTable, String queryType)
			throws DBComponentException {
		Hashtable<String, Hashtable<String, List<Field>>> databaseTableHash = new Hashtable<String, Hashtable<String, List<Field>>>();
		formPatternHash = new Hashtable<String, FormPattern>();
		dataPatternHash = new Hashtable<String, LinkedList<DataPattern>>();
		registerWindow = dbComponent.getRegisterWindow();
		DBComponent.logger.log(0,"----applicationName is-------"+ applicationName);
		try {
			applicationFormList = registerWindow.getApplicationPatternHash()
					.get(applicationName);
			if (null == applicationFormList || applicationFormList.isEmpty())
				throw new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ DBConstants.DB_APPLICATION_FORM_LIST
								+ applicationFormList + "in save");
			for (int formListCounter = 0; formListCounter < applicationFormList
					.size(); formListCounter++) {

				String selFormPattern = (String) applicationFormList
						.get(formListCounter);
				if (selFormPattern.equalsIgnoreCase(baseWindow.trim())) {
					formPatternHash = registerWindow.getBaseFormPatternHash()
							.get(selFormPattern);
					dataPatternHash = registerWindow.getBaseDataPatternHash()
							.get(selFormPattern);
					break;
				}
			}
			Enumeration requestTableEnum = requestTable.keys();
			while (requestTableEnum.hasMoreElements()) {
				String requestFieldName = (String) requestTableEnum
						.nextElement();
				Enumeration formPatternElementEnum = formPatternHash.keys();
				while (formPatternElementEnum.hasMoreElements()) {
					String retId = (String) formPatternElementEnum
							.nextElement();
					FormPattern selElement = formPatternHash.get(retId);
					if (selElement.getElementId().trim().equalsIgnoreCase(
							requestFieldName.trim())) {
						String dataPatternId = selElement
								.getAssocDataPatternId();
						if (null == dataPatternId) {
							// DBComponent.logger.log(0,"data pattern id not found");
							continue;
						}
						LinkedList<DataPattern> selDataPattern = dataPatternHash
								.get(dataPatternId);
						if (null == selDataPattern) {
							// DBComponent.logger.log(0," associated data pattern not found");
							continue;
						}

						Field field = null;
						String databaseName = "";
						if (selDataPattern != null)
							for (int count = 0; count < selDataPattern.size(); count++) {
								DataPattern dataPattern = selDataPattern
										.get(count);
								String tableName = dataPattern.getTableName();
								databaseName = dataPattern.getDatabaseName();
								String selField = dataPattern.getFieldName();
								if (null == databaseTableHash.get(databaseName)) {
									tableFieldHash = new Hashtable<String, List<Field>>();
									fieldList = new ArrayList<Field>();
								} else {
									// tableFieldHash =
									// databaseTableHash.get(testdatabaseName);
									if (null == tableFieldHash.get(tableName)) {
										fieldList = new ArrayList<Field>();
									} else {
										fieldList = tableFieldHash
												.get(tableName);
									}
								}
								field = new Field();
								field.setName(selField);
								field.setValue(requestTable
										.get(requestFieldName));
								fieldList.add(field);
								tableFieldHash.put(tableName, fieldList);
								databaseTableHash.put(databaseName,
										tableFieldHash);
							}
						/*
						 * DataPattern selDataPattern =
						 * dataPatternHash.get(dataPatternId); if(null ==
						 * selDataPattern) {
						 * //DBComponent.logger.log(0," associated data pattern not found"
						 * ); continue; } String tableName =
						 * selDataPattern.getTableName(); String databaseName =
						 * selDataPattern.getDatabaseName(); String selField =
						 * selDataPattern.getFieldName(); if(null ==
						 * databaseTableHash.get(databaseName)) { tableFieldHash
						 * = new Hashtable<String,List<Field>>(); fieldList =
						 * new ArrayList<Field>(); } else { tableFieldHash =
						 * databaseTableHash.get(databaseName); if(null ==
						 * tableFieldHash.get(tableName)) { fieldList = new
						 * ArrayList<Field>(); } else { fieldList =
						 * tableFieldHash.get(tableName); } } Field field = new
						 * Field(); field.setName(selField);
						 * field.setValue(requestTable.get(requestFieldName));
						 * fieldList.add(field); tableFieldHash.put(tableName,
						 * fieldList);DBComponent.logger.log(0,
						 * "-----Putting data on databasehashtable--tableName--"
						 * +tableName);DBComponent.logger.log(0,
						 * "-----Putting data on databasehashtable--fieldList-size-"
						 * +fieldList.size());
						 * databaseTableHash.put(databaseName, tableFieldHash);
						 */
					}
				}
			}
			/*
			 * if(null != databaseTableHash && !databaseTableHash.isEmpty()) {
			 * dbComponent.saveField(databaseTableHash,queryType); } else {
			 * throw new
			 * DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION
			 * + DBConstants.DB_APPLICATION_TABLE_HASH + databaseTableHash
			 * +"in save"); }
			 */
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION
							+ baseWindow + DBConstants.DB_COMMA + requestTable
							+ DBConstants.DB_COMMA + queryType + "in save");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION
							+ baseWindow + DBConstants.DB_COMMA + requestTable
							+ DBConstants.DB_COMMA + queryType + "in save");
		}
		DBComponent.logger.log(0,"-----returning on databasehashtable----"
				+ databaseTableHash.size());
		return databaseTableHash;
	}

	/**
	 * this method will called by other components those have injected instance
	 * of database components and want to get values for fields objects, so that
	 * Field class will not be directly imported in other components
	 * 
	 * @param databaseHashData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Hashtable getFieldsFromHashData(Hashtable databaseHashData) {
		Hashtable tbr = new Hashtable();
		List<Field> fieldList = new ArrayList<Field>();
		try {
			DBComponent.logger.log(0,"Check size of database hash data--------"
					+ databaseHashData.size());
			Enumeration requestTableEnum = databaseHashData.keys();
			while (requestTableEnum.hasMoreElements()) {
				String requestFieldName = (String) requestTableEnum
						.nextElement();
				DBComponent.logger.log(0,"-requestFieldName--String---"
						+ requestFieldName);
				fieldList = (List) databaseHashData.get(requestFieldName);
				for (Field field : fieldList) {
					String fieldName = field.getName();
					DBComponent.logger.log(0,"---fieldname---" + fieldName);
					Object fieldValue = field.getValue();
					DBComponent.logger.log(0,"---fieldValue---" + fieldValue);
					tbr.put(fieldName, fieldValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBComponent.logger.log(0,"before returning tbr check size------" + tbr.size());
		return tbr;
	}

	/**
	 * This method handles any type of query getting it as string input.
	 * 
	 * @param sql
	 * @return void
	 * @throws DBComponentException
	 * 
	 * This method now commented.
	 * Changes by Wasim, 20-May-2009
	 * 
	 */
	/*public String executeQuery(String sql) throws DBComponentException {

		String outputXML = null;
		if (sql != null && !sql.equals(DBConstants.DB_BLANK))

			if (null != sql && !sql.equals(DBConstants.DB_BLANK))

			{
				// DBComponent.logger.log(0,"inside execute query = " + sql);
				outputXML = dbComponent.stringQuery(sql);
			} else {
				throw new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ DBConstants.DB_SQL_QUERY + sql
								+ " in executeQuery");

			}
		return outputXML;
	}*/

	/**
	 * This method handles any type of query getting it as string input.
	 * 
	 * @param sql
	 * @return void
	 * @throws DBComponentException
	 */
	public List executeQueryList(String sql) throws DBComponentException {

		// DBComponent.logger.log(0,"*******************************in executeQuery List************************");

		List output = null;
		if (sql != null && !sql.equals(DBConstants.DB_BLANK))

			if (null != sql && !sql.equals(DBConstants.DB_BLANK))
			{
				String[] querylist = sql.split(DBConstants.DB_UNION);
				// DBComponent.logger.log(0,"inside execute query = " + sql);
				for(int i = 0; i < querylist.length; i++)
				{
					if(output==null)
						output = dbComponent.stringQueryList(querylist[i]);
					else
						output.addAll(dbComponent.stringQueryList(querylist[i]));
				}
				//System.out.println(QueryData.iterateListData(output));
			} else {
				throw new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ DBConstants.DB_SQL_QUERY + sql
								+ " in executeQuery");

			}
		return output;
	}

	/**
	 * This method used to add parent level of the tree and set the variables
	 * into the object of this class.
	 * 
	 * @param levelnumber
	 * @param tableList
	 * @param displayfield
	 * @param valuefield
	 * @param conditionstring
	 * @return void
	 * @throws DBComponentException
	 */
	public void addLevel(String levelnumber, String tableList,
			String displayfield, String valuefield, String conditionstring)
			throws DBComponentException {
		TreeNode treeNode = new TreeNode();
		if (null != levelnumber && !levelnumber.equals(DBConstants.DB_BLANK)
				&& null != tableList && !tableList.equals(DBConstants.DB_BLANK)
				&& null != displayfield
				&& !displayfield.equals(DBConstants.DB_BLANK)
				&& null != valuefield
				&& !valuefield.equals(DBConstants.DB_BLANK)
				&& null != conditionstring) {
			// DBComponent.logger.log(0,"@@@@@@@@@@@@@@@Add node to root");
			treeNode.setLevelNo(levelnumber);
			treeNode.setTableList(tableList);
			treeNode.setDisplayfield(displayfield);
			treeNode.setValueField(valuefield);
			treeNode.setParentNodeLevel(DBConstants.DB_BLANK);
			treeNode.setConditionString(conditionstring);
			dbComponent.addToTreeHash(treeNode);
		} else {
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_LEVEL_NUM + levelnumber
							+ DBConstants.DB_COMMA + DBConstants.DB_TABLE_LIST
							+ tableList + DBConstants.DB_COMMA
							+ DBConstants.DB_DISPLAY_FIELD + displayfield
							+ DBConstants.DB_COMMA + DBConstants.DB_VALUE_FIELD
							+ valuefield + DBConstants.DB_COMMA
							+ DBConstants.DB_CONDITION_STRING + conditionstring
							+ " in addLevel");
		}

	}

	/**
	 * This method used to add child level of the tree and set the variables
	 * into the object of this class.
	 * 
	 * @param levelnumber
	 * @param tableList
	 * @param displayfield
	 * @param parentNodelevel
	 * @param conditionField
	 * @param conditionValue
	 * @return void
	 * @throws DBComponentException
	 */
	public void addChildLevel(String levelnumber, String tableList,
			String displayfield, String valuefield, String parentNodelevel,
			String conditionField, String conditionValue)
			throws DBComponentException {
		// DBComponent.logger.log(0,"@@@@@@@@@@@@@@addChildlevel called");
		TreeNode treeNode = new TreeNode();
		if (!levelnumber.equals(DBConstants.DB_BLANK) && null != levelnumber
				&& !tableList.equals(DBConstants.DB_BLANK) && null != tableList
				&& !displayfield.equals(DBConstants.DB_BLANK)
				&& null != displayfield
				&& !valuefield.equals(DBConstants.DB_BLANK)
				&& null != valuefield
				&& !parentNodelevel.equals(DBConstants.DB_BLANK)
				&& null != parentNodelevel && null != conditionField) {
			treeNode.setLevelNo(levelnumber);
			treeNode.setTableList(tableList);
			treeNode.setDisplayfield(displayfield);
			treeNode.setValueField(valuefield);
			treeNode.setParentNodeLevel(parentNodelevel);
			treeNode.setConditionField(conditionField);
			treeNode.setConditionValue(conditionValue);
			// treeNode.setConditionString(conditionString);
			dbComponent.addToTreeHash(treeNode);
		} else {
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_LEVEL_NUM + levelnumber
							+ DBConstants.DB_COMMA + DBConstants.DB_TABLE_LIST
							+ tableList + DBConstants.DB_COMMA
							+ DBConstants.DB_DISPLAY_FIELD + displayfield
							+ DBConstants.DB_COMMA + DBConstants.DB_VALUE_FIELD
							+ valuefield + DBConstants.DB_COMMA
							+ DBConstants.DB_PARENT_NODE_LEVEL
							+ parentNodelevel + DBConstants.DB_COMMA
							+ DBConstants.DB_CONDITION_FIELD + conditionField
							+ DBConstants.DB_COMMA
							+ DBConstants.DB_CONDITION_VALUE + conditionValue
							+ " in addChildLevel");
		}
	}

	/**
	 * This method is used to retrieve the XML by invoking the method of
	 * DBComponent class.
	 * 
	 * @param tree
	 * @returns String
	 */
	public String showTree(String tree) throws DBComponentException {
		String outputXML = null;
		// DBComponent.logger.log(0,"show tree called ");
		outputXML = dbComponent.createTreeXml();
		return outputXML;
	}

	/**
	 * This method is used to set the instance of DBComponent class.
	 * 
	 * @param dbComponent
	 * @return void
	 */
	public void setDbComponent(IDBComponent dbComponent) {
		this.dbComponent = dbComponent;
	}

}

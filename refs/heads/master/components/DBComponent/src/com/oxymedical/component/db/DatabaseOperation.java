package com.oxymedical.component.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;

import com.oxymedical.component.db.XML.FieldValueXML;
import com.oxymedical.component.db.application.query.Condition;
import com.oxymedical.component.db.application.query.Field;
import com.oxymedical.component.db.application.query.Join;
import com.oxymedical.component.db.application.query.OrderBy;
import com.oxymedical.component.db.application.query.ParserQueryParameters;
import com.oxymedical.component.db.application.query.QueryParameter;
import com.oxymedical.component.db.application.query.parser.HICQueryParser;
import com.oxymedical.component.db.application.query.parser.ParserConstants;
import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.constants.RegisterConstants;
import com.oxymedical.component.db.constants.TreeConstants;
import com.oxymedical.component.db.dao.ConcreteDAO;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;
import com.oxymedical.component.db.utilities.ConvertDataType;
import com.oxymedical.component.db.utilities.DBOperationUtilities;
import com.oxymedical.core.stringutil.StringUtil;

/**
 * @author vka
 *
 */
public class DatabaseOperation {
	/** This is configuration file that contains the pozo and table mappings. */
	private Configuration configuration = null;
	/** This object is used to call the method of its class. */
	private FieldValueXML fieldValueXML = null;
	/** This object is used to call the method of its class. */
	private HICQueryParser hicQueryParser = null;
	/** This object is used to call the method of its class. */
	private ConvertDataType cnvtDataType = null;
	/** This object contains reference to POJO classes */
	private URLClassLoader dbClassLoader = null;

	/** This hashtable maps table name with the class name */
	Hashtable<String, ConcreteDAO> tableNameClassNameMap = null;
	public Hashtable<String, ConcreteDAO> getTableNameClassNameMap() {
		return tableNameClassNameMap;
	}

	public void setTableNameClassNameMap(
			Hashtable<String, ConcreteDAO> tableNameClassNameMap) {
		this.tableNameClassNameMap = tableNameClassNameMap;
	}

	/** This Hashtable maps class name and its Table */
	Hashtable<String, Table> classTableMap = null;

	/*
	 * 
	 */// can be given as private
	private void initDataBaseMapping()
	{
		Iterator mappingIterator = configuration.getClassMappings();
		while (mappingIterator.hasNext())
		{
			ConcreteDAO concreteDAO = new ConcreteDAO();
			PersistentClass mappingObject = (PersistentClass) mappingIterator.next();

			String entityName = mappingObject.getEntityName();
			Table tableObject = mappingObject.getTable();

			classTableMap.put(entityName, tableObject);
			concreteDAO.setDaoClassName(entityName);
			concreteDAO.setPersistanceClass(mappingObject);
			tableNameClassNameMap.put(tableObject.getQuotedName().toLowerCase().trim(), concreteDAO);

			Iterator myClassSubs = mappingObject.getDirectSubclasses();
			while (myClassSubs.hasNext())
			{
				PersistentClass myClassSub = (PersistentClass) myClassSubs.next();
				concreteDAO = new ConcreteDAO();
				concreteDAO.setDaoClassName(myClassSub.getEntityName());
				concreteDAO.setPersistanceClass(myClassSub);
				tableNameClassNameMap.put(myClassSub.getTable().getQuotedName().toLowerCase().trim(),
						concreteDAO);
			}
		}
	}

	
	/**
	 * Constructor is used to gets the configuration file and initialize the
	 * Database settings accordingly.
	 * 
	 * @param config
	 *            has classmappings and used to retrieve the Persistence class
	 *            object.
	 */
	public DatabaseOperation(Configuration config) {

		tableNameClassNameMap = new Hashtable<String, ConcreteDAO>();
		classTableMap = new Hashtable<String, Table>();
		configuration = config;
		initDataBaseMapping();

	}

	/**
	 * Constructor for initializing several objects if required for its class.
	 * 
	 */
	public DatabaseOperation() {
	}

	/**
	 *This method is used to set Configuration object which is use to retrieve
	 * persistence object.
	 * 
	 * @param config
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void setConfiguration(Configuration config) {
		this.configuration = config;
	}
/*
 * this method is used to generate the sequence of 
 * objects of tables in which object will be persisted in database
 * added by pra
 */
	public LinkedHashMap<String, List<Object>> orderTable(
			Hashtable<String, List<Field>> tableFieldHash,
			URLClassLoader dBClassLoader,String databaseName) {
		Enumeration orderFieldEnum = tableFieldHash.keys();
		Enumeration tableFieldEnum = tableFieldHash.keys();

		LinkedHashMap<String, List<Object>> forgeinKeyInformation = new LinkedHashMap<String, List<Object>>();
		LinkedList<Object> objects;
		try {
			while (tableFieldEnum.hasMoreElements()) {
				String tableName = (String) tableFieldEnum.nextElement();
				objects = this.forgeinKeyList(tableName, dBClassLoader);
				if (objects != null && objects.size() > 0) {
					for (int k = 0; k < objects.size(); k++) {
						String tName = this.getTableName(objects.get(k),databaseName);
						forgeinKeyInformation = this.updateForgeinInfoData(
								forgeinKeyInformation, tName, tableName,
								objects, dBClassLoader, orderFieldEnum,
								tableFieldHash,databaseName);
						forgeinKeyInformation.put(tableName, objects);
					}

				} else {
					if (!forgeinKeyInformation.containsKey(tableName.toLowerCase()))
					forgeinKeyInformation.put(tableName, objects);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();

		}
		return forgeinKeyInformation;
	}
/*
 * this method updates the information of forgein keys in the 
 * linkedhashmap.
 * added by pra
 */
	public LinkedHashMap<String, List<Object>> updateForgeinInfoData(
			LinkedHashMap<String, List<Object>> forgeinKeyInformation,
			String tName, String tableName, LinkedList<Object> objects,
			URLClassLoader dBClassLoader, Enumeration orderFieldEnum,
			Hashtable<String, List<Field>> tableFieldHash,String databaseName) {

		boolean isPresent = this.checkReferenceTable(orderFieldEnum, tName);
		orderFieldEnum = tableFieldHash.keys();
		if (isPresent) {
			LinkedList<Object> keys = this.forgeinKeyList(tName, dBClassLoader);
			if (keys != null && keys.size() > 0) {

				for (int k = 0; k < keys.size(); k++) {
					String tabName = this.getTableName(keys.get(k),databaseName);
					forgeinKeyInformation = this.updateForgeinInfoData(
							forgeinKeyInformation, tabName, tName, keys,
							dBClassLoader, orderFieldEnum, tableFieldHash,databaseName);

				}
				//now inserting the table's foriegn key information in infohash 
				if (!forgeinKeyInformation.containsKey(tName.toLowerCase()))
				forgeinKeyInformation.put(tName.toLowerCase(), keys);
			} else {
				if (forgeinKeyInformation.containsKey(tName.toLowerCase())) {
					forgeinKeyInformation.put(tableName, objects);
				} else {
					forgeinKeyInformation.put(tName.toLowerCase(), keys);
					forgeinKeyInformation.put(tableName.toLowerCase(), objects);
				}
			}
		}
		
		return forgeinKeyInformation;
	}
/*
 * this method retunrs the table name of the object
 * added by pra
 */

	public String getTableName(Object obj,String databaseName)
	{
		org.hibernate.mapping.ForeignKey col = (org.hibernate.mapping.ForeignKey) obj;
        String ref=col.getReferencedEntityName();
		String spiltString=RegisterConstants.REG_PACKAGE_NAME1+databaseName+ ".";
		String[] refers = ref.toLowerCase().split(spiltString.toLowerCase());
		return refers[1].toLowerCase();
	}
/*
 * this method checks if reference table is present in the list
 * added by pra
 */
	public boolean checkReferenceTable(Enumeration orderFieldEnum,
			String referTable) {
		boolean isPresent = false;
		while (orderFieldEnum.hasMoreElements()) {
			String tableName = (String) orderFieldEnum.nextElement();
			//Condition changed by pra on 22-May-2009
			if (referTable.equalsIgnoreCase(tableName)) {
				isPresent = true;
				return isPresent;
			}

		}
		return isPresent;

	}
/* This method is used to return the list of forgein keys
 * for object to persisited
 * added by pra
 */
	public LinkedList forgeinKeyList(String tableName,
			URLClassLoader dBClassLoader) {
		LinkedList<Object> fKeys = new LinkedList<Object>();
		ConcreteDAO concreteDAO = null;
		PersistentClass persistentClass = null;
		Class javaClass = null;
		Class mappedClass = null;
		try {
			if (null != dBClassLoader) {

				Thread.currentThread().setContextClassLoader(dBClassLoader);
			}

			DBComponent.logger.log(0, "tableNameClassNameMap = "
					+ tableNameClassNameMap);
			concreteDAO = tableNameClassNameMap.get(tableName.toLowerCase()
					.trim());
			if (null == concreteDAO) {
				// concrete dao null for the table dataTableName
				throw (new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION + tableName
								+ DBConstants.DB_CONCRETE_DAO + concreteDAO
								+ " in retrieveObjectConcreteDAO"));
			}
			javaClass = dBClassLoader.loadClass(concreteDAO.getDaoClassName());
			persistentClass = concreteDAO.getPersistanceClass();
			Table tableObject = persistentClass.getTable();
			Iterator itr = tableObject.getForeignKeyIterator();
			while (itr.hasNext()) {
				org.hibernate.mapping.ForeignKey col = (org.hibernate.mapping.ForeignKey) itr
						.next();
				fKeys.add(col);
				/*
				 * String ref=col.getReferencedEntityName(); String
				 * []refers=ref.split(RegisterConstants.REG_PACKAGE_NAME+".");
				 * isOrder=checRefference(forgeinKeyInformation,refers[1]);
				 */

				/*
				 * String refrenceTable=col.getReferencedEntityName();
				 * 
				 * List colName = col.getColumns();
				 * 
				 * for(int i=0;i<colName.size();i++) {
				 * System.out.println("--------------1234-------------"
				 * +((org.hibernate.mapping.Column)colName.get(i)).getName()); }
				 */
			}
			return fKeys;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fKeys;
	}
/*This method is used to retrive the primary key value of the 
 * object.
 * Done by pra
 */
	public Object primaryKeyValue(String databaseName, String dataTableName,
			URLClassLoader dBClassLoader, Object obj) {
		ConcreteDAO concreteDAO = null;
		PersistentClass persistentClass = null;
		Class javaClass = null;
		Class mappedClass = null;

		try {
			if (null != dBClassLoader) {
				Thread.currentThread().setContextClassLoader(dBClassLoader);
			}
			DBComponent.logger.log(0, "tableNameClassNameMap = "
					+ tableNameClassNameMap);
			concreteDAO = tableNameClassNameMap.get(dataTableName.toLowerCase()
					.trim());
			if (null == concreteDAO) {
				// concrete dao null for the table dataTableName
				throw (new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ dataTableName + DBConstants.DB_CONCRETE_DAO
								+ concreteDAO + " in retrieveObjectConcreteDAO"));
			}
			javaClass = dBClassLoader.loadClass(concreteDAO.getDaoClassName());
			persistentClass = concreteDAO.getPersistanceClass();
			mappedClass = persistentClass.getMappedClass();
			Property property = persistentClass.getIdentifierProperty();
			Getter getter = property.getGetter(mappedClass);
			Class[] parameterTypes = getter.getMethod().getParameterTypes();
			Method method = javaClass.getMethod(getter.getMethodName(),
					parameterTypes);
			Object ob = method.invoke(obj, null);
			if (ob != null) {
				return ob;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// creating object to be saved
	protected Object retrieveObjectConcreteDAO(String databaseName,
			String dataTableName, List<Field> fieldList,
			URLClassLoader dBClassLoader) throws DBComponentException {

		ConcreteDAO concreteDAO = null;
		PersistentClass persistentClass = null;
		Class javaClass = null;
		Class mappedClass = null;
		Object obj = null;
		Iterator fieldIterator = null;
		cnvtDataType = new ConvertDataType();
		try {
			// dataTableName = dataTableName.toLowerCase();
			if (null != dBClassLoader) {
				Thread.currentThread().setContextClassLoader(dBClassLoader);
			}
			DBComponent.logger.log(0, "tableNameClassNameMap = "
					+ tableNameClassNameMap);
			concreteDAO = tableNameClassNameMap.get(dataTableName.toLowerCase()
					.trim());
			if (null == concreteDAO) {
				// concrete dao null for the table dataTableName
				throw (new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ dataTableName + DBConstants.DB_CONCRETE_DAO
								+ concreteDAO + " in retrieveObjectConcreteDAO"));
			}
			javaClass = dBClassLoader.loadClass(concreteDAO.getDaoClassName());
			persistentClass = concreteDAO.getPersistanceClass();
			mappedClass = persistentClass.getMappedClass();
			fieldIterator = fieldList.iterator();
			obj = mappedClass.newInstance();
			while (fieldIterator.hasNext()) {
				Field field = (Field) fieldIterator.next();
				String fieldName = field.getName();
				Object fieldValue = field.getValue();
				String classField = (String) DBOperationUtilities
						.retClassField(persistentClass, fieldName);
				Iterator propertyIt = persistentClass.getPropertyIterator();
				boolean primaryKeysPresent = false;
				while (propertyIt.hasNext()) {
					primaryKeysPresent = true;
					Property property = (Property) propertyIt.next();
					if (property.getName().equalsIgnoreCase(classField)) {
						// property.
						Setter setter = property.getSetter(mappedClass);
						Class[] parameterTypes = setter.getMethod()
								.getParameterTypes();
						Method method = javaClass.getMethod(setter
								.getMethodName(), parameterTypes);
						fieldValue = cnvtDataType.setObjectValue(fieldValue,
								parameterTypes[0].getName());
						Object parObj = null;
						if (property.getValue().getType().isAssociationType()) {
							PersistentClass parPersClass = configuration
									.getClassMapping(parameterTypes[0]
											.getName());
							parObj = DBOperationUtilities.extractParentObject(
									parPersClass, parObj, fieldValue);

							if (null != fieldValue) {
								method.invoke(obj, parObj);
							}
						} else {
							if (null != fieldValue) {
								method.invoke(obj, fieldValue);
							}
						}
					}
				}
				if (!primaryKeysPresent) {
					// Table might contain composite id's
					Component identifier = (Component) persistentClass
							.getIdentifier();
					Iterator columnIterator = identifier.getPropertyIterator();
					while (columnIterator.hasNext()) {
						Property property = (Property) columnIterator.next();
						if (property.getName().equalsIgnoreCase(classField)) {
							Setter setter = property.getSetter(mappedClass);
							Class[] parameterTypes = setter.getMethod()
									.getParameterTypes();
							Method method = javaClass.getMethod(setter
									.getMethodName(), parameterTypes);
							fieldValue = cnvtDataType.setObjectValue(
									fieldValue, parameterTypes[0].getName());
							Object parObj = null;
							if (property.getValue().getType()
									.isAssociationType()) {
								PersistentClass parPersClass = configuration
										.getClassMapping(parameterTypes[0]
												.getName());
								parObj = DBOperationUtilities
										.extractParentObject(parPersClass,
												parObj, fieldValue);

								if (null != fieldValue) {
									method.invoke(obj, parObj);
								}
							} else {
								if (null != fieldValue) {
									method.invoke(obj, fieldValue);
								}
							}
						}
					}
				}
				Property property = persistentClass.getIdentifierProperty();
				if (property != null
						&& property.getName().equalsIgnoreCase(classField)) {
					if (null == fieldValue
							||fieldValue.equals(DBConstants.DB_NULL)
							|| fieldValue.equals(DBConstants.DB_BLANK)) {

						continue;
					}

					Setter setter = property.getSetter(mappedClass);
					Class[] parameterTypes = setter.getMethod()
							.getParameterTypes();
					Method method = javaClass.getMethod(setter.getMethodName(),
							parameterTypes);
					fieldValue = cnvtDataType.setObjectValue(fieldValue,
							parameterTypes[0].getName());
					if (null != fieldValue) {
						method.invoke(obj, fieldValue);
					}
				}
			}
			return obj;
		} catch (ConstraintViolationException cve) {
			// cve.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + " in retrieveObjectConcreteDAO");
		} catch (DataException de) {
			// de.printStackTrace();
			throw new DBComponentException(DBExceptionConstants.DATA_EXCEPTION
					+ databaseName + DBConstants.DB_COMMA + dataTableName
					+ " in retrieveObjectConcreteDAO");
		} catch (SQLGrammarException sge) {
			// sge.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.SQL_GRAMMER_EXCEPTION + databaseName
							+ DBConstants.DB_COMMA + dataTableName
							+ " in retrieveObjectConcreteDAO");
		} catch (HibernateException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + databaseName
							+ DBConstants.DB_COMMA + dataTableName
							+ " in retrieveObjectConcreteDAO");
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.CLASS_NOT_FOUND_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + " in retrieveObjectConcreteDAO");
		} catch (SecurityException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.SECURITY_EXCEPTION + databaseName
							+ DBConstants.DB_COMMA + dataTableName
							+ " in retrieveObjectConcreteDAO");
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.NO_SUCH_METHOD_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + " in retrieveObjectConcreteDAO");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.ILLEGAL_ARGUMENT_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + " in retrieveObjectConcreteDAO");
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.ILLEGAL_ACCESS_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + " in retrieveObjectConcreteDAO");
		} catch (InvocationTargetException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.INVOCATION_TARGET_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + " in retrieveObjectConcreteDAO");
		} catch (InstantiationException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.INSTANTATION_EXCEPTION + databaseName
							+ DBConstants.DB_COMMA + dataTableName
							+ " in retrieveObjectConcreteDAO");
		}
	}

	// creates hsql query from string query
	protected String createHIbQuery(ParserQueryParameters parserQueryParameter)
			throws DBComponentException {
		hicQueryParser = new HICQueryParser();
		String hsqlQuery = null;
		String selCondition = null;
		String queryType = null;
		List<String> tables = null;
		ConcreteDAO concreteDAO = null;
		PersistentClass persistentClass = null;

		if (null != parserQueryParameter.getQueryType()
				&& !(parserQueryParameter.getQueryType()
						.equals(DBConstants.DB_BLANK))) {
			queryType = parserQueryParameter.getQueryType();
			if (null != parserQueryParameter.getTables()) {
				tables = parserQueryParameter.getTables();
				String retTable = (String) tables.get(0);
				retTable = retTable.toLowerCase();
				concreteDAO = tableNameClassNameMap.get(retTable.toLowerCase()
						.trim());
				if (null == concreteDAO) {
					// null exception
					throw (new DBComponentException(
							DBExceptionConstants.NULL_POINTER_EXCEPTION
									+ parserQueryParameter
									+ " in createHIbQuery"));
				}
				persistentClass = concreteDAO.getPersistanceClass();
				if (null != parserQueryParameter.getConditions()) {
					Hashtable<Integer, Condition> conditionHash = parserQueryParameter
							.getConditions();

					if (null != conditionHash) {
						selCondition = retHibCriteria(conditionHash,
								persistentClass, new Hashtable());
					}

				}
				// DBComponent.logger.log(0,"condition = " + selCondition);
				String classtableNames = concreteDAO.getDaoClassName();
				if (null == selCondition)
					selCondition = DBConstants.DB_BLANK;
				if ((selCondition.equals(DBConstants.DB_BLANK))) {
					hsqlQuery = queryType + DBConstants.DB_FROM_CLAUSE
							+ classtableNames;

				} else {
					hsqlQuery = queryType + DBConstants.DB_FROM_CLAUSE
							+ classtableNames + DBConstants.DB_WHERE_CLAUSE
							+ selCondition;

				}

			} else {
				// DBComponent.logger.log(0,"!!!!!!!!!!!!!!!!!Illegel query type tablename missing Type not mentioned");
				return null;
			}

		} else {
			// DBComponent.logger.log(0,"!!!!!!!!!!!!!!!!!!Illegel query type");
			return null;
		}
		return hsqlQuery + DBConstants.DB_HASH + queryType;

	}

	private String retHibCriteria(Hashtable conditionHash,
			PersistentClass persistentClass, Hashtable tableAliasHash)
			throws DBComponentException 
	{
		Object selConstant = null;
		String selCondition = null;
		String table = null;
		String join = null;
		Enumeration conditionHashEnum = null;
		ConcreteDAO concreteDAO = null;
		boolean aliasExist = false;
		conditionHashEnum = conditionHash.keys();
		if (null != conditionHashEnum) {
			selCondition = "";
			while (conditionHashEnum.hasMoreElements()) {
				int conditionCounter = 0;
				join = "";

				Integer condNo = (Integer) conditionHashEnum.nextElement();
				Condition retCondition = (Condition) conditionHash.get(condNo);

				String retField = retCondition.getField();
				if (retField.indexOf('.') > 0)
				{
					String[] tableField = DBOperationUtilities.retreiveTableField(retField);
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
					selConstant = table
							+ DBConstants.DB_DOT
							+ DBOperationUtilities.retClassField(
									persistentClass, retCondition
											.getConsField());

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
				Object retFieldObject = DBOperationUtilities.retClassField(
						persistentClass, retField);
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

	protected Hashtable<String, List<String>> getDAOByName(
			QueryParameter queryParam, String queryType)
			throws DBComponentException {
		// DBComponent.logger.log(0,"----------------------getDAOby name for query params in getDAOByName----------------------------------- ");
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
		String orderCondition=null;

		hicQueryParser = new HICQueryParser();
		PersistentClass persistentClass = null;
		List tablePropertyList = null;
		Hashtable<Integer, Condition> conditionHash = null;
		//used to get the join hash
		Hashtable<Integer, Condition> joinHash = null;
		Hashtable<String, List<String>> queryFielLabel = new Hashtable<String, List<String>>();
		List<String> fieldLabelList = new ArrayList<String>();

		LinkedHashMap<String, String> reqFieldHash = queryParam
				.getFieldNameLabelHash();
		Hashtable<PersistentClass, String> tableAliasHash = new Hashtable<PersistentClass, String>();
		Hashtable<Integer, Condition> orderByHash = null;
		
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
					&& !queryParam.getConditionHash().isEmpty()) {

				conditionHash = queryParam.getConditionHash();
			}
			if(null != queryParam.getJoinHash()
					&& !queryParam.getJoinHash().isEmpty())
					{
				  joinHash=queryParam.getJoinHash();
					}
			if(null != queryParam.getOrderByHash()
					&& !queryParam.getOrderByHash().isEmpty())
					{
				   orderByHash=queryParam.getOrderByHash();
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
			if(null!=joinHash)
			{
			//added to retrive the  join criteria in hibernate form added by pra
				joinCondition=retHibJoinCriteria(joinHash,persistentClass,tableAliasHash);
			}
			// retreiving criteria in hibernate form
			if (null != conditionHash) {
				selCondition = retHibCriteria(conditionHash, persistentClass,
						tableAliasHash);
			}
			// retrivies criteria for orderby added by pra on 27 july 
			if (null != orderByHash) {
				orderCondition = retHibOrderByCriteria(orderByHash, persistentClass,
						tableAliasHash);
			}
			
			
			// reqFieldEnum = reqFieldHash.keys();
			reqFieldEnum = reqFieldHash.keySet().toArray();
			selClassField = "";
			classFields = "";
			for (int counter = 0; counter < reqFieldEnum.length; counter++) {
				/*
				 * 
				 * while (reqFieldEnum.hasMoreElements()) { String key =
				 * (String) reqFieldEnum.nextElement();
				 */
				//implementation changesd in this to implement concat. added by pra.
				String[] fields=null;
				boolean isConcat=false;
				tablePropertyList = null;
				String key = (String) reqFieldEnum[counter];
				if (queryType.equalsIgnoreCase(DBConstants.DB_TREE_QUERY)) {
					fieldTable = reqFieldHash.get(key);
					// DBComponent.logger.log(0,"FieldTable = " + fieldTable);
				} else {
					fieldTable = key;
				}
				if(fieldTable.indexOf("concat")>=0)
				{
				fieldTable=fieldTable.replace(DBConstants.DB_CONCAT,"");	
				fieldTable=fieldTable.replace(DBConstants.DB_BRACKET_OPEN,"");	
				fieldTable=fieldTable.replace(DBConstants.DB_BRACKET_CLOSE,"");	
				fields=StringUtil.splitString(fieldTable, DBConstants.DB_CONCAT_QUERY_SEP);
				isConcat=true;
				}
				else if (fieldTable.indexOf('.') > 0) {
					String[] tableField = DBOperationUtilities
							.retreiveTableField(fieldTable);
					fieldName = tableField[1].toLowerCase().trim();
					tableName = tableField[0];
					
					persistentClass = (PersistentClass) tableNameClassNameMap
							.get(tableName.toLowerCase().trim())
							.getPersistanceClass();
					
					if (null == persistentClass) {
						// DBComponent.logger.log(0,"--------------in persistance class----------------");
						throw (new DBComponentException(
								DBExceptionConstants.NULL_POINTER_EXCEPTION
										+ fieldName + DBConstants.DB_COMMA
										+ tableName
										+ DBConstants.DB_PERSISTENT_CLASS
										+ persistentClass + " in getDAOByName"));
					}
					if (fieldName.equalsIgnoreCase(DBConstants.DB_GETALL)) {
						tablePropertyList = (List) DBOperationUtilities
								.retClassField(persistentClass, fieldValue);
					}
				} else {
					fieldName = fieldTable.toLowerCase().trim();
					if (fieldName.equalsIgnoreCase(DBConstants.DB_GETALL)) {
						// DBComponent.logger.log(0,"--------------in else if ----------------");
						// old message
						throw new DBComponentException(
								DBExceptionConstants.EXCEPTION
										+ DBConstants.DB_ILLEGAL_QUERY
										+ queryParam + DBConstants.DB_COMMA
										+ queryType + " in getDAOByName");
					}
				}
				
					// DBComponent.logger.log(0,"classField = " + classFields);
				if(isConcat)
				{
					String concatQuery="";
					String asField="";
					for(int a=0;a<fields.length;a++)
					{
						String[] tableField = DBOperationUtilities
						.retreiveTableField(fields[a].trim());
						fieldName = tableField[1].toLowerCase().trim();
						tableName = tableField[0];
						persistentClass = (PersistentClass) tableNameClassNameMap
								.get(tableName.toLowerCase().trim())
								.getPersistanceClass();
						if (null == persistentClass) {
							// DBComponent.logger.log(0,"--------------in persistance class----------------");
							throw (new DBComponentException(
									DBExceptionConstants.NULL_POINTER_EXCEPTION
											+ fieldName + DBConstants.DB_COMMA
											+ tableName
											+ DBConstants.DB_PERSISTENT_CLASS
											+ persistentClass + " in getDAOByName"));
						}
						classFields=getClassField(queryType, fieldValue, persistentClass, fieldName, tableAliasHash, key, fieldTable, reqFieldHash, fieldLabelList,isConcat);
						if(classFields!=null)
						{
							asField=fieldName.toUpperCase();
							if(concatQuery.equalsIgnoreCase(""))
							concatQuery=concatQuery+classFields;
							else
								concatQuery=concatQuery+DBConstants.DB_CONCAT_JOINER+classFields;
						}
					}
					if(!concatQuery.equalsIgnoreCase(""))
						selClassField +=DBConstants.DB_BLANK+DBConstants.DB_CONCAT+DBConstants.DB_BRACKET_OPEN+concatQuery+DBConstants.DB_BRACKET_CLOSE+DBConstants.DB_AS_BLANK+asField +"," ;
				}
				else
				{
					if (null == tablePropertyList) {
						classFields=getClassField(queryType, fieldValue, persistentClass, fieldName, tableAliasHash, key, fieldTable, reqFieldHash, fieldLabelList,isConcat);
						if (null != classFields)
							selClassField += classFields + ",";
					}
					else {
						if (tableAliasHash.size() > 0) {
							columnTable = DBOperationUtilities.extractTableAlias(
									persistentClass, tableAliasHash);
	
						}
						if (null == columnTable) {
							// DBComponent.logger.log(0,"!!!!!!!!!!!1 No table exist for the column ");
							throw new DBComponentException(
									DBExceptionConstants.NULL_POINTER_EXCEPTION
											+ DBConstants.DB_COLUMN_TABLE + columnTable
											+ " in getDAOByName");
						}
					
						for (int listCounter = 0; listCounter < tablePropertyList
								.size(); listCounter++) {
							String propertyName = (String) tablePropertyList
									.get(listCounter);
							fieldLabelList.add(propertyName);
							classFields = columnTable + DBConstants.DB_DOT
									+ propertyName + DBConstants.DB_AS_CLAUSE
									+ propertyName.toUpperCase();
							if (null != classFields)
								selClassField += classFields + ",";
						}
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
			
			if(null==orderCondition)
			{
			//join condition
				orderCondition=DBConstants.DB_BLANK;
			}
			if (!orderCondition.equals(DBConstants.DB_BLANK)) {
				orderCondition = orderCondition.substring(0, orderCondition
						.length() - 1);
				orderCondition = DBConstants.DB_SPACE + DBConstants.DB_ORDER_BY+ DBConstants.DB_SPACE+orderCondition;
			}
			classtableNames = DBConstants.DB_FROM_CLAUSE + classtableNames;
			hsqlQuery = selClassField + classtableNames +joinCondition+selCondition+orderCondition;
			queryFielLabel.put(hsqlQuery, fieldLabelList);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new DBComponentException(e);
		}
		// DBComponent.logger.log(0,"queryFielLabel = " + queryFielLabel);
		return queryFielLabel;
	}
	
	
	/**
	 * This method returns the datatable field based on the query field input 
	 * by the user.
	 * method added by pra
	 * @param queryType
	 * @param fieldValue
	 * @param persistentClass
	 * @param fieldName
	 * @param tableAliasHash
	 * @param key
	 * @param fieldTable
	 * @param reqFieldHash
	 * @param fieldLabelList
	 * @param isConcat
	 * @return
	 * @throws DBComponentException
	 */
	private String getClassField(String queryType,String fieldValue, PersistentClass persistentClass,String fieldName,
			Hashtable<PersistentClass, String> tableAliasHash,String key,String fieldTable ,
			LinkedHashMap<String, String> reqFieldHash,List<String> fieldLabelList,boolean isConcat) throws DBComponentException
	{
		String columnTable=null;
		String classFields = null;
      
		if (tableAliasHash.size() > 0) {
			columnTable = DBOperationUtilities.extractTableAlias(
					persistentClass, tableAliasHash);

		}
		if (null == columnTable) {
			// DBComponent.logger.log(0,"!!!!!!!!!!!1 No table exist for the column ");
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_COLUMN_TABLE + columnTable
							+ " in getDAOByName");
		}
			if (queryType.equalsIgnoreCase(DBConstants.DB_TREE_QUERY)) {
				fieldValue = fieldName;
				fieldLabelList.add(key);
				classFields = (String) DBOperationUtilities
						.retClassField(persistentClass, fieldValue);
			} else {
				fieldValue = reqFieldHash.get(fieldTable);
				fieldLabelList.add((String) reqFieldHash
						.get(fieldTable));
				classFields = (String) DBOperationUtilities
						.retClassField(persistentClass, fieldName);
			}
			if (null == classFields) {
				// DBComponent.logger.log(0,"!!!!!!!!!!!!!!! No field exist with the name = "
				// + fieldName);
				throw new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ persistentClass
								+ DBConstants.DB_COMMA + fieldName
								+ DBConstants.DB_CLASS_FIELDS
								+ classFields + " in getDAOByName");
			}
			if(isConcat)
			{
				classFields = columnTable + DBConstants.DB_DOT
				+ classFields; 
			}
			else{
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
				classFields = columnTable + DBConstants.DB_DOT
						+ classFields + DBConstants.DB_AS_CLAUSE
						+ classFields.toUpperCase();
			}
			}
			return classFields;
		}

	protected Object createOutputString(List objectList, String calledBy,
			String tableName, List<String> fieldLabelList)
			throws DBComponentException {
		// DBComponent.logger.log(0,"inside createOutputString ");
		Hashtable<Integer, Hashtable> recordFieldHash = new Hashtable<Integer, Hashtable>();
		Hashtable<String, Object> fieldValueHash = null;
		Integer resultCount = 0;
		int objectCounter = 0;
		String keyValue = null;
		String xmlOutput = null;
		ArrayList resultList = (ArrayList) objectList;
		Iterator resultListItr = resultList.iterator();
		Object retValue = null;
		while (resultListItr.hasNext()) {
			fieldValueHash = new Hashtable<String, Object>();
			resultCount++;
			Object obj = (Object) resultListItr.next();
			retValue = obj;

			if (obj!=null && !obj.getClass().isArray()) {
				PersistentClass parPersClass = configuration
						.getClassMapping(retValue.getClass().getName());
				if (null != parPersClass)
					retValue = DBOperationUtilities.extractPrimaryValue(
							parPersClass, retValue);

				for (int i = 0; i < 2; i++) {

					if (i == 0) {
						keyValue = TreeConstants.TREE_TEXT;
					} else {
						keyValue = TreeConstants.TREE_VALUE;
					}
					DBComponent.logger.log(0, " obj.toString() = "
							+ retValue.toString());
					fieldValueHash.put(keyValue, retValue.toString());
				}

			} else {
				Object[] objArr = (Object[]) obj;
				if (null == obj)
					continue;
				for (objectCounter = 0; objectCounter < objArr.length; objectCounter++) {
					String retField = (String) fieldLabelList
							.get(objectCounter);
					if(retField!=null)
					{
						if (retField.indexOf(DBConstants.DB_AS_BLANK) > 0) {
							retField = retField.substring(retField
									.indexOf(DBConstants.DB_AS_BLANK)
									+ DBConstants.DB_AS_BLANK.length(), retField
									.length());
						}
						int dotIndex = retField.indexOf(DBConstants.DB_DOT);
						if (dotIndex > 0) {
							retField = retField.substring(dotIndex + 1, retField
									.length());
						}
						retValue = objArr[objectCounter];
						if (!retField.equalsIgnoreCase(DBConstants.DB_NULL)
								&& null != retValue) {
							PersistentClass parPersClass = configuration
									.getClassMapping(retValue.getClass().getName());
	
							if (null != parPersClass)
								retValue = DBOperationUtilities
										.extractPrimaryValue(parPersClass, retValue);
	
							fieldValueHash.put(retField, retValue);
						} else if (!retField.equalsIgnoreCase(DBConstants.DB_NULL)
								&& null == retValue)
	
						{
							String Value = "";
							fieldValueHash.put(retField, Value);
							// DBComponent.logger.log(0,"!!!!!!!!!!!!!Null entry found for the tree");
	
						}
					}
					
				}
			}
			recordFieldHash.put(resultCount, fieldValueHash);

		}
		if (calledBy.equalsIgnoreCase(DBConstants.DB_TREE_QUERY))
			return recordFieldHash;

		fieldValueXML = new FieldValueXML();
		xmlOutput = fieldValueXML.createFieldVlaueXML(recordFieldHash);
		// DBComponent.logger.log(0,"xmlOutput = " + xmlOutput);
		return xmlOutput;

	}
	
	// can give method as protected
	/**
	 * 
	 * 
	 * @param result
	 * @param concreteDAO
	 * @param javaClass
	 * @return
	 * @throws DBComponentException
	 */
	protected String executeQuery(List result, ConcreteDAO concreteDAO,
			Class javaClass) throws DBComponentException {
		// DBComponent.logger.log(0,"************** in executeQuery *************");
		Integer recordCount = 0;
		Hashtable<Integer, Hashtable> recordFieldHash = new Hashtable<Integer, Hashtable>();
		Hashtable<String, Object> fieldValueHash = null;
		Class mappedClass = null;
		try {
			Iterator resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				recordCount++;
				fieldValueHash = new Hashtable<String, Object>();
				Object obj = resultIterator.next();

				// DBComponent.logger.log(0,"************** in executequery list *************"+obj.toString());

				PersistentClass persistentClass = concreteDAO
						.getPersistanceClass();
				mappedClass = persistentClass.getMappedClass();
				Iterator propertyIt = persistentClass.getPropertyIterator();
				boolean primaryKeysPresent = false;
				while (propertyIt.hasNext()) {
					// getPropertyIterator means table contains primary ids
					primaryKeysPresent = true;
					Property property = (Property) propertyIt.next();

					// To check if the property is a foreign key value
					if (!property.getValue().isSimpleValue())
						continue;

					Getter getter = property.getGetter(mappedClass);
					Method method = javaClass.getMethod(getter.getMethodName(),
							null);
					Object resultObject = method.invoke(obj, null);
					if (property.getValue().getType().isAssociationType()) {
						PersistentClass parPersClass = configuration
								.getClassMapping(resultObject.getClass()
										.getName());
						resultObject = DBOperationUtilities
								.extractPrimaryValue(parPersClass, resultObject);

					}
					if (null == resultObject)
						fieldValueHash.put(property.getName(),
								DBConstants.DB_BLANK);
					else
						fieldValueHash.put(property.getName(), resultObject);
				}
				if (!primaryKeysPresent) {
					// Table might contain composite id's
					Component identifier = (Component) persistentClass
							.getIdentifier();
					Iterator columnIterator1 = identifier.getTable()
							.getColumnIterator();
					Iterator columnIterator = identifier.getPropertyIterator();
					while (columnIterator.hasNext()) {
						Property property = (Property) columnIterator.next();

						if (!property.getValue().isSimpleValue())
							continue;

						Getter getter = property.getGetter(mappedClass);
						Method method = javaClass.getMethod(getter
								.getMethodName(), null);
						if (null != obj) {
							Object resultObject = method.invoke(obj, null);
							if (property.getValue().getType()
									.isAssociationType()) {
								PersistentClass parPersClass = configuration
										.getClassMapping(resultObject
												.getClass().getName());
								resultObject = DBOperationUtilities
										.extractPrimaryValue(parPersClass,
												resultObject);

							}
							if (null == resultObject) {
								fieldValueHash.put(property.getName(),
										DBConstants.DB_BLANK);
							} else {
								// DBComponent.logger.log(0,"result object -------------------"+resultObject);
								fieldValueHash.put(property.getName(),
										resultObject);
							}
						}
					}

				}
				Property property = persistentClass.getIdentifierProperty();
				if (null != property && (property.getValue().isSimpleValue())) {
					Getter getter = property.getGetter(mappedClass);
					Method method = javaClass.getMethod(getter.getMethodName(),
							null);
					Object resultObject = method.invoke(obj, null);
					if (property.getValue().getType().isAssociationType()) {
						PersistentClass parPersClass = configuration
								.getClassMapping(resultObject.getClass()
										.getName());
						resultObject = DBOperationUtilities
								.extractPrimaryValue(parPersClass, resultObject);

					}
					if (null != resultObject)
						fieldValueHash.put(property.getName(), resultObject);
					else
						fieldValueHash.put(property.getName(),
								DBConstants.DB_BLANK);
				}
				recordFieldHash.put(recordCount, fieldValueHash);
			}
			fieldValueXML = new FieldValueXML();
			String xmlOutput = fieldValueXML
					.createFieldVlaueXML(recordFieldHash);
			// DBComponent.logger.log(0,"value of table---------------------"+xmlOutput);
			return xmlOutput;
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.NO_SUCH_METHOD_EXCEPTION
							+ DBConstants.DB_COMMA + concreteDAO
							+ DBConstants.DB_COMMA + javaClass
							+ " in executeQuery");
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
			throw (new DBComponentException(
					DBExceptionConstants.ILLEGAL_ACCESS_EXCEPTION
							+ DBConstants.DB_COMMA + concreteDAO
							+ DBConstants.DB_COMMA + javaClass
							+ " in executeQuery"));
		} catch (InvocationTargetException e) {
			// e.printStackTrace();
			throw (new DBComponentException(
					DBExceptionConstants.INVOCATION_TARGET_EXCEPTION
							+ DBConstants.DB_COMMA + concreteDAO
							+ DBConstants.DB_COMMA + javaClass
							+ " in executeQuery"));
		}

	}
	
	/* this method returns the update query 
	 *  added by pra
	 */
	
	protected String createHIbUpdateQuery(ParserQueryParameters parserQueryParameter)
	throws DBComponentException {
	
		
		hicQueryParser = new HICQueryParser();
		String hsqlQuery = null;
		String selCondition = null;
		String queryType = null;
		List<String> tables = null;
		ConcreteDAO concreteDAO = null;
		PersistentClass persistentClass = null;
		List<Field> fieldList = null;
		StringBuffer fields = new StringBuffer();

		if (null != parserQueryParameter.getQueryType()
				&& !(parserQueryParameter.getQueryType()
						.equals(DBConstants.DB_BLANK))) {
			queryType = parserQueryParameter.getQueryType();
			if (null != parserQueryParameter.getTables()) {
				if (null != parserQueryParameter.getFieldList()){
				tables = parserQueryParameter.getTables();
				String retTable = (String) tables.get(0);
				retTable = retTable.toLowerCase();
				concreteDAO = tableNameClassNameMap.get(retTable.toLowerCase()
						.trim());
				if (null == concreteDAO) {
					// null exception
					throw (new DBComponentException(
							DBExceptionConstants.NULL_POINTER_EXCEPTION
									+ parserQueryParameter
									+ " in createHIbQuery"));
				}
				persistentClass = concreteDAO.getPersistanceClass();
				fieldList=parserQueryParameter.getFieldList();
				Iterator fieldIterator = fieldList.iterator();
				while(fieldIterator.hasNext())
				{
				
					Field field = (Field) fieldIterator.next();
					String fieldName = field.getName();
					Object fieldValue = field.getValue();
					String classField = (String) DBOperationUtilities
							.retClassField(persistentClass, fieldName);
				Iterator propertyIt=persistentClass.getPropertyIterator();
				while(propertyIt.hasNext())
				{
					Property property = (Property) propertyIt.next();
					
					if (property.getName().equalsIgnoreCase(classField)) {
						//need to implement according to type of property.
					    if(!fieldValue.toString().equalsIgnoreCase("null"))
					        fields=fields.append(property.getName()).append(DBConstants.DB_EQUAL).append("'").append(fieldValue).append("'").append(DBConstants.DB_COMMA);
					        else
					         fields=fields.append(property.getName()).append(DBConstants.DB_EQUAL).append(fieldValue).append(DBConstants.DB_COMMA);
							}
				}
				}
				
				int index = fields.lastIndexOf(DBConstants.DB_COMMA);
				String fieldQuery= fields.substring(0, index);
				if (null != parserQueryParameter.getConditions()) {
					Hashtable<Integer, Condition> conditionHash = parserQueryParameter
							.getConditions();

					if (null != conditionHash) {
						selCondition = retHibCriteria(conditionHash,
								persistentClass, new Hashtable());
					}

				}
				// DBComponent.logger.log(0,"condition = " + selCondition);
				String classtableNames = concreteDAO.getDaoClassName();
				if (null == selCondition)
					selCondition = DBConstants.DB_BLANK;
				if ((selCondition.equals(DBConstants.DB_BLANK))) {
					hsqlQuery = queryType + "  " +classtableNames+DBConstants.DB_SET+fieldQuery.toString()
							+  DBConstants.DB_WHERE_CLAUSE+selCondition;

				} else {
					hsqlQuery = queryType +"  "  +classtableNames+DBConstants.DB_SET+fieldQuery.toString()
					+  DBConstants.DB_WHERE_CLAUSE+selCondition;

				}

			}} else {
				// DBComponent.logger.log(0,"!!!!!!!!!!!!!!!!!Illegel query type tablename missing Type not mentioned");
				return null;
			}

		} else {
			// DBComponent.logger.log(0,"!!!!!!!!!!!!!!!!!!Illegel query type");
			return null;
		}
		return hsqlQuery + DBConstants.DB_HASH + queryType;
	}
	
	/**
	 * Getter for ORM configuration information
	 * @return
	 */
	public Configuration getConfiguration()
	{
		return this.configuration;
	}

	/**
	 * @return the dbClassLoader
	 */
	public URLClassLoader getDbClassLoader()
	{
		return dbClassLoader;
	}

	/**
	 * @param dbClassLoader the dbClassLoader to set
	 */
	public void setDbClassLoader(URLClassLoader dbClassLoader)
	{
		this.dbClassLoader = dbClassLoader;
	}
	
	/**
	 * This method is used to create the join query for the hibernate.
	 * Added by pra on 10-july-2009
	 * @param joinHash
	 * @param persistentClass
	 * @param tableAliasHash
	 * @return String join query
	 * @throws DBComponentException
	 */
	private String retHibJoinCriteria(Hashtable joinHash,
			PersistentClass persistentClass, Hashtable tableAliasHash)
			throws DBComponentException {
		String joinCondition=null;
		String table = null;
		Enumeration joinHashEnum = null;
		ConcreteDAO concreteDAO = null;
		boolean aliasExist = false;
		joinHashEnum = joinHash.keys();
		if (null != joinHashEnum) {
			joinCondition = "";
			while (joinHashEnum.hasMoreElements()) {				
				int conditionCounter = 0;
				Integer condNo = (Integer) joinHashEnum.nextElement();
				Join joinCond = (Join) joinHash.get(condNo);
				String parentTableName=joinCond.getParentTableName();
				String fieldName=joinCond.getFieldName();
				String joinType=joinCond.getJoinType();
				String forgeinTable=joinCond.getForeginTable();
				
		    concreteDAO = (ConcreteDAO) tableNameClassNameMap
				.get(parentTableName.toLowerCase().trim());
		if (null == concreteDAO) {
			throw (new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ parentTableName
							+ DBConstants.DB_CONCRETE_DAO
							+ concreteDAO + " in retHibJoinCriteria"));
		}

		persistentClass = concreteDAO.getPersistanceClass();
		if (tableAliasHash.size() > 0) {
			table = DBOperationUtilities.extractTableAlias(
					persistentClass, tableAliasHash);
		}
		if (null == table)
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ parentTableName + DBConstants.DB_TABLE
							+ table + " in retHibJoinCriteria");
		Object selConstant = table
				+ DBConstants.DB_DOT
				+ DBOperationUtilities.retClassField(
						persistentClass,fieldName);
		
		concreteDAO = (ConcreteDAO) tableNameClassNameMap
			.get(forgeinTable.toLowerCase().trim());
		 persistentClass = concreteDAO.getPersistanceClass();
		 String aliasName = persistentClass.getTable().getName();
		if (tableAliasHash.size() > 0) {
			table = DBOperationUtilities.extractTableAlias(
					persistentClass, tableAliasHash);
		}
		if (null == table)
		{
		tableAliasHash.put(persistentClass, aliasName);
		}
			String selConstantArray [] =selConstant.toString().split(".");
			joinCondition=joinCondition + joinType+DBConstants.DB_SPACE+selConstantArray[0]+"."+aliasName+
			DBConstants.DB_AS_BLANK+aliasName+DBConstants.DB_SPACE;
			System.out.println("joinCondition:- "+joinCondition);
		conditionCounter++;
			}
	}
		return joinCondition;

}

	/**
	 * This method is used to create the order query for the hibernate.
	 * Added by pra on 27-july-2009
	 * @param orderHash
	 * @param persistentClass
	 * @param tableAliasHash
	 * @return String order query
	 * @throws DBComponentException
	 */
private String retHibOrderByCriteria(Hashtable orderByHash,
			PersistentClass persistentClass, Hashtable tableAliasHash)
			throws DBComponentException {
		String orderCondition=null;
		String table = null;
		Enumeration orderHashEnum = null;
		ConcreteDAO concreteDAO = null;
		boolean aliasExist = false;
		orderHashEnum = orderByHash.keys();
		if (null != orderHashEnum) {
			orderCondition = "";
			while (orderHashEnum.hasMoreElements()) {				
				int conditionCounter = 0;
				Integer condNo = (Integer) orderHashEnum.nextElement();
				OrderBy orderCond = (OrderBy) orderByHash.get(condNo);;
				String tableName=orderCond.getTableName();
				String fieldName=orderCond.getFieldName();
				String orderType=orderCond.getOrderType();
				
		    concreteDAO = (ConcreteDAO) tableNameClassNameMap
				.get(tableName.toLowerCase().trim());
		if (null == concreteDAO) {
			throw (new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ tableName
							+ DBConstants.DB_CONCRETE_DAO
							+ concreteDAO + " in retHibOrderByCriteria"));
		}

		persistentClass = concreteDAO.getPersistanceClass();
		if (tableAliasHash.size() > 0) {
			table = DBOperationUtilities.extractTableAlias(
					persistentClass, tableAliasHash);
		}
		if (null == table)
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ tableName + DBConstants.DB_TABLE
							+ table + " in retHibOrderByCriteria");
		Object selConstant = table
				+ DBConstants.DB_DOT
				+ DBOperationUtilities.retClassField(
						persistentClass,fieldName);
		
		orderCondition=orderCondition +DBConstants.DB_SPACE+selConstant.toString();
			if(orderType!=null)
			{
				orderCondition=orderCondition+DBConstants.DB_SPACE+orderType;
			}
		 orderCondition=orderCondition+DBConstants.DB_COMMA;
		 conditionCounter++;
			}
	}
		
		return orderCondition;

}
	
}

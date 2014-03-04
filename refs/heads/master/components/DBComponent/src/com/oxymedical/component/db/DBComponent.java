package com.oxymedical.component.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.db.application.DataHandler;
import com.oxymedical.component.db.application.IDataHandler;
import com.oxymedical.component.db.application.query.Field;
import com.oxymedical.component.db.application.query.ParserQueryParameters;
import com.oxymedical.component.db.application.query.QueryParameter;
import com.oxymedical.component.db.application.query.parser.HICQueryParser;
import com.oxymedical.component.db.application.query.parser.ParserConstants;
import com.oxymedical.component.db.application.register.IRegisterWindow;
import com.oxymedical.component.db.application.register.Register;
import com.oxymedical.component.db.application.tree.DisplayField;
import com.oxymedical.component.db.application.tree.TreeNode;
import com.oxymedical.component.db.application.tree.TreeResultObject;
import com.oxymedical.component.db.application.tree.TreeXML;
import com.oxymedical.component.db.application.tree.ValueField;
import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.constants.RegisterConstants;
import com.oxymedical.component.db.dao.ConcreteDAO;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;
import com.oxymedical.component.db.factory.ORMSessionFactory;
import com.oxymedical.component.db.mappingGenerator.DBMappingGenerator;
import com.oxymedical.component.db.mappingGenerator.DataPatternParser;
import com.oxymedical.component.db.query.FieldConvertedQueryCreator;
import com.oxymedical.component.db.query.IQueryCreator;
import com.oxymedical.component.db.query.QueryCreator;
import com.oxymedical.component.db.query.TableLinkedQueryCreator;
import com.oxymedical.component.db.query.data.DBQuery;
import com.oxymedical.component.db.utilities.DBServerURLGenerator;
import com.oxymedical.component.db.utilities.DBStructureUtil;
import com.oxymedical.component.db.utilities.DBUtilities;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.db.utilities.parameters.UserInfo;
import com.oxymedical.component.logging.LoggingComponent;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.DataBase;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IDataBase;
import com.oxymedical.core.commonData.IFormPattern;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.xmlutil.XmlReader;
import com.oxymedical.framework.objectbroker.annotations.InjectNew;

/**
 * Components interacts with this class to perform database operations. This
 * class implements IComponent and IDBComponent.
 * 
 * @author Oxyent Medical
 * @version 1.0.0
 */
public class DBComponent implements IComponent, IDBComponent {
	/**
	 * is used to call methods of it Class DatabaseOperation that handles all
	 * database operations.
	 */
	private HashMap<String, DatabaseOperation> dbOpers = new HashMap<String, DatabaseOperation>();
	private DatabaseOperation databaseOperation = null;
	
	/**
	 * is used to call methods of it Class Configuration i.e. difined in
	 * hibernate package.
	 */
	private Configuration config = null;

	/** is used to call methods of it Class DatabaseSetUpUtilities. */
	private DatabaseSetUpUtilities dbSetupUtility = new DatabaseSetUpUtilities();
	/** It is used to open session for performing DB operations */
	private SessionFactory sessionFactory = null;
	/**
	 * This is the hashtable which maps level number of the tree to the object
	 * of the TreeNode class.
	 */
	Hashtable<String, TreeNode> treeNodeHash = null;
	@InjectNew
	static public LoggingComponent logger;
	/**
	 * This is the hashtable which maps level number of the tree to the list of
	 * the TreeResultObject class objects to make the level wise result.
	 */
	Hashtable<String, List<TreeResultObject>> treeLevelResultHash = null; // level
	// wise
	// result
	/** This object is used to call the methods of its class. */
	protected UserInfo userInfo = null;

	/** It loads the required class */
	URLClassLoader DBClassLoader = null;// (URLClassLoader)

	// DBComponent.class.getClassLoader();
	/** It is HIC specific data */
	public IHICData hicData = null;
	// public IDBComponent dbComponent = null;
	/** Gets instance of the Register class */
	IRegisterWindow register = new Register();
	/** This object is used to call the methods of its class. */
	IDataHandler dataHandler = new DataHandler();

	/**
	 * This object is used to call the methods of its class that automatically
	 * build the ant and setting up the properties.
	 */
	DBMappingGenerator dbMappingGenerator = new DBMappingGenerator();
	private boolean isConnectionAvailable = false;

	private int firstResult;
	private int maxResult;

	private ORMSessionFactory sessFac;
	private Hashtable<String, List<String>> foreignKeyInfo = null;
	private IApplication app = null;
	
	/** This variable is used to store query parameter values 
	 * and passes it to the HICQueryParser object*/
	List<String> queryParmValues = null;

	public static LoggingComponent getInstanceOfLoggingComponent() {
		if (logger == null) {
			logger = new LoggingComponent();
		}
		return logger;
	}

	/**
	 * Constructor for initializing several objects
	 * 
	 */
	public DBComponent() {
	}

	/**
	 * This method returns an instance of RegisterWindow class.
	 * 
	 * @returns IRegisterWindow
	 */
	public IRegisterWindow getRegisterWindow() {
		return register;
	}

	/**
	 * This method is used initialize Database settings such as Configuration
	 * file, resourcs jar etc.
	 * 
	 * @param connUserName
	 * @param connPassword
	 * @param serverAddress
	 * @param baseDirectory
	 * @param databaseName
	 * @param createResources
	 *            this is boolean variable.
	 * @throws DBComponentException
	 * @returns void
	 * @throws DBComponentException
	 */
	public void createDatabaseResources(String connUserName, 
			String connPassword, String serverAddress, String baseDirectory, 
			String databaseName, Boolean createResources) 
	throws DBComponentException
	{
		createDatabaseResourcesOnly(connUserName, connPassword, serverAddress, baseDirectory, databaseName,
				createResources, null);

		loadDBResourcesAndCreateConfig(baseDirectory, DBConstants.DB_RESOURCES_JAR);
	}

	/**
	 * This overlaoded method is used initialize Database settings such as
	 * Configuration file, resourcs jar etc.
	 * 
	 * @param connUserName
	 * @param connPassword
	 * @param serverAddress
	 * @param baseDirectory
	 * @param databaseName
	 * @param createResources
	 *            this is boolean variable.
	 * @throws DBComponentException
	 * @returns void
	 * @throws DBComponentException
	 */
	public void createDatabaseResources(String connUserName,
			String connPassword, String serverAddress, String baseDirectory,
			String databaseName, Boolean createResources, String resourceName)
			throws DBComponentException
	{
		createDatabaseResourcesOnly(connUserName, connPassword, serverAddress, baseDirectory, databaseName,
				createResources, resourceName);

		loadDBResourcesAndCreateConfig(baseDirectory, resourceName);
	}


	public void createDatabaseResourcesOnly(String connUserName,
			String connPassword, String serverAddress, String baseDirectory,
			String databaseName, Boolean createResources, String resourceName)
			throws DBComponentException
	{
		String serverURL = null;
		if (null == connUserName || null == connPassword || null == serverAddress || null == baseDirectory
				|| null == databaseName || null == createResources)
		{
			throw new DBComponentException("Null parameter passes for creating database resources");
		}

		serverURL = RegisterConstants.REG_DRIVER + serverAddress + RegisterConstants.REG_PORT + databaseName;
		
		if (resourceName == null)
			this.setUpDataConfiguration(connUserName, connPassword, serverURL,
					RegisterConstants.REG_PACKAGE_NAME, baseDirectory);
		else
			this.setUpDataConfiguration(connUserName, connPassword, serverURL,
					RegisterConstants.REG_PACKAGE_NAME, baseDirectory, resourceName);

		if (createResources)
		{
			this.registerDBAndGenerateMappings();
		}
	}
	
	public void loadDBResourcesAndCreateConfig(String baseDirectory, String resourceName) throws DBComponentException
	{
		this.loadResourcesJarInLoader(baseDirectory, resourceName + ".jar");
		this.createDBConfiguration();
	}
	

	
	/**
	 * This method returns an instance of data handler
	 * 
	 * @returns IDataHandler
	 */
	public IDataHandler getDataHandler() {
		dataHandler.setDbComponent(this);
		return dataHandler;

	}

	/**
	 * This method is used to create database configuration and setting up the
	 * hibernate properties.
	 * 
	 * @returns void
	 * @throws DBComponentException
	 */
	public void createDBConfiguration() throws DBComponentException {
		try {
			if (null != userInfo) {
				config = dbSetupUtility.CreateConfiguration();

				setUpHibernateProperties();
				databaseOperation = new DatabaseOperation(config);
			}
		} catch (DBComponentException e) {
			throw e;
		}
	}

	// TODO Enhance it for other database types as well
	private void setUpHibernateProperties()
	{
		config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		config.setProperty("hibernate.connection.username", userInfo.getUserName());
		config.setProperty("hibernate.connection.password", userInfo.getPassword());
		config.setProperty("hibernate.connection.url", userInfo.getServerURL());
		config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		config.setProperty("show_sql", "true");
		
		 
		 config.setProperty("hibernate.cache.use_query_cache", "true");
		 config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		 config.setProperty("hibernate.cache.use_second_level_cache", "true");
		
		   

		//config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EHCacheProvider");
		/*
		 * Points to ponder 
		 * 1. "autoReconnect" property should not be used since it is deprecated by MySQL
		 * (http://dev.mysql.com/doc/refman/5.1/en/connector-j-usagenotes-troubleshooting.html) 
		 * 
		 * 2. Hibernate's own connection pooling algorithm is not intended for use in a production system.
		 * (http://docs.jboss.org/hibernate/stable/core/reference/en/html/session-configuration.html)
		 */
		 
		// Added for connection pooling
		config.setProperty("c3p0.acquire_increment", "1");
		config.setProperty("c3p0.idle_test_period", "300");
		config.setProperty("c3p0.max_size", "5");
		config.setProperty("c3p0.min_size", "1");
		config.setProperty("c3p0.timeout", "5000");
	}

	/**
	 * This method is used to create database configuration from InputStream
	 * class object and setting up the hibernate properties.
	 * 
	 * @param modulefileStream
	 * @returns void
	 * @throws DBComponentException
	 */
	public void CreateConfigurationFromInputStream(InputStream modulefileStream)
			throws DBComponentException {
		config = dbSetupUtility
				.CreateConfigurationFromInputStream(modulefileStream);
		setUpHibernateProperties();
		databaseOperation = new DatabaseOperation(config);
	}

	/**
	 * This method is used to load resources jar at the specfied path.
	 * 
	 * @param jarPathFile
	 * @param jarFileName
	 * @returns URLClassLoader
	 * @throws DBComponentException
	 */
	public URLClassLoader loadResourcesJarInLoader(String jarPathFile,
			String jarFileName) throws DBComponentException {
		DBClassLoader = dbSetupUtility.loadResourcesJarInLoader(jarPathFile,
				jarFileName, DBClassLoader);
		return DBClassLoader;
	}

	/**
	 * This method register database and generate mappings.
	 * 
	 * @throws DBComponentException
	 */
	public void registerDBAndGenerateMappings() throws DBComponentException {
		getInstanceOfLoggingComponent();
		if (null != userInfo) {
			logger.log(0,
					"[DBComponent][registerDBAndGenerateMappings][UserInfo]"
							+ userInfo.getUserName() + "_"
							+ userInfo.getPassword() + "_"
							+ userInfo.getServerURL() + "_"
							+ userInfo.getPackageName() + "_"
							+ userInfo.getBaseDirectoryAddress());

			if (null != userInfo.getJarName() && !userInfo.equals(""))
				dbMappingGenerator.registerDB(userInfo.getUserName(), userInfo
						.getPassword(), userInfo.getServerURL(), userInfo
						.getPackageName(), userInfo.getBaseDirectoryAddress(),
						userInfo.getJarName());
			else
				dbMappingGenerator.registerDB(userInfo.getUserName(), userInfo
						.getPassword(), userInfo.getServerURL(), userInfo
						.getPackageName(), userInfo.getBaseDirectoryAddress());

		}
	}

	public SessionFactory getSessionFactory() {
		if (null != DBClassLoader) {
			Thread.currentThread().setContextClassLoader(DBClassLoader);
		}

		// Fetch sessionFactory from ORMSessionFactory. Since there can be
		// multiple configs, creating a new sessionFactory everytime using
		// buildSessionFactory can be costly. Hence maintaining a HashMap of
		// sessionFactories pertaining to each ORM Configuration and fetching
		// from that HashMap.
		if (sessFac == null)
			sessFac = new ORMSessionFactory();
		sessionFactory = sessFac.getSessionFactory(config);

		return sessionFactory;
	}

	/**
	 * This method is use to excecute the hibernate specific query.
	 * 
	 * @param hsql
	 * @param listParameter
	 * @returns List
	 * @throws DBComponentException
	 */
	public List executeHSQLQueryWithNameParameter(String hsql,
			ArrayList<NameQueryParameter> listParameter)
			throws DBComponentException {
		Session session = null;
		Query hsqlQuery = null;
		List result = null;
		try {
			session = getSessionFactory().openSession();
			// logger.log(0,hsql);
			hsqlQuery = session.createQuery(hsql);
			if (null != listParameter) {
				for (Iterator it = listParameter.iterator(); it.hasNext();) {
					NameQueryParameter nameQueryParameter = (NameQueryParameter) it
							.next();
					if (null != nameQueryParameter) {
						if (null != nameQueryParameter.getTypeParam()) {
							hsqlQuery.setParameter(nameQueryParameter
									.getParameter(), nameQueryParameter
									.getValueParameter(), nameQueryParameter
									.getTypeParam());
						} else {
							hsqlQuery.setParameter(nameQueryParameter
									.getParameter(), nameQueryParameter
									.getValueParameter());
						}

					}
				}
			}
			if (null != hsqlQuery) {
				hsqlQuery.setCacheable(true);
				result = hsqlQuery.list();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + hsql
							+ "in executeHSQLQueryWithNameParameter");
		} finally {
			if (null != session)
				session.close();
		}
		return result;
	}

	/**
	 * This method creates the object of mapped class
	 * 
	 * @param className
	 * @returns object
	 * @throws DBComponentException
	 */
	public Object createObject(String className) throws DBComponentException {
		System.out.println("inside create object---111");
		PersistentClass persistenClass = config.getClassMapping(className);
		System.out.println("inside create object---222");
		String entityName = persistenClass.getEntityName();
		Table tableObject = persistenClass.getTable();
		Class mappedClass = persistenClass.getMappedClass();
		Object obj = null;
		try {
			System.out.println("inside create object try block---333");
			obj = mappedClass.newInstance();
			System.out.println("inside create object try block---444");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new DBComponentException("InstantiationException for "
					+ className + " in createObject");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new DBComponentException("IllegalAccessException for "
					+ className + " in createObject");
		}
		return obj;
	}

	/**
	 * This method gets the mapped class
	 * 
	 * @param className
	 * @returns persistentClass
	 * 
	 */
	public PersistentClass getPersistentClass(String className) {
		PersistentClass persistenClass = config.getClassMapping(className);
		return persistenClass;
	}

	/**
	 * This method to save the object of the Object class.
	 * 
	 * @param obj
	 *            object to be saved.
	 * @returns Object
	 * @throws DBComponentException
	 */
	/*
	 * method return type change to object from boolean done by pra
	 */
	public Object saveObject(Object obj) throws DBComponentException {
		Object retVal = null;
		Session session = null;
		Transaction tx = null;
		try {

			session = getSessionFactory().openSession();
			if (session != null) {

				tx = session.beginTransaction();

				// session.saveOrUpdate(obj);
				retVal = session.merge(obj);
				tx.commit();

				return retVal;
			}
		}

		catch (ConstraintViolationException cve) {
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION + obj
							+ "in saveObject");
		} catch (DataException de) {
			de.printStackTrace();
			throw new DBComponentException(DBExceptionConstants.DATA_EXCEPTION
					+ obj + "in saveObject");
		} catch (SQLGrammarException sge) {
			throw new DBComponentException(
					DBExceptionConstants.SQL_GRAMMER_EXCEPTION + obj
							+ "in saveObject");
		} catch (HibernateException e) {
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + obj
							+ " in saveObject");
		}
		// catch()
		finally {
			if (null != session) {
				session.close();

			}
		}
		return retVal;
	}

	/**
	 * This method takes simple english query as input and create hibernate
	 * queries and perform operations accordingly.
	 * 
	 * @param sql
	 *            Query in simple english.
	 * @returns List
	 * @throws DBComponentException
	 * 
	 * 
	 *             Changes by Wasim , 20-May-2009
	 */

	public List stringQueryList(String sql) throws DBComponentException {
		Hashtable<String, List<String>> queryFieldHash = null;
		ParserQueryParameters parserQueryParameter = null;
		// Hashtable<String, String> fieldLabelHash = null;
		LinkedHashMap<String, String> fieldLabelHash = null;
		QueryParameter queryParameter = null;
		List tableList = null;
		List<Field> fieldList = null;
		// String retData = null;
		List retList = null;

		if (null != dbOpers) {
			HICQueryParser hicQueryParser = new HICQueryParser();
			hicQueryParser.setQueryParamValues(queryParmValues);
			parserQueryParameter = hicQueryParser.parseHICQuery(sql);

			// Fetch database from Query and get
			// DatabaseOperation object pertaining to database
			String databaseName = parserQueryParameter.getDatabaseName();
			if (!setConfigAndDBClassLoader(databaseName)) {
				if (this.config == null)
					return retList;
			}

			if (null == parserQueryParameter)
				return retList;
			if (parserQueryParameter.getQueryType().equalsIgnoreCase(
					ParserConstants.deleteQuery)) {
				String hsqlQuery = databaseOperation
						.createHIbQuery(parserQueryParameter);

				if (hsqlQuery.equals(DBConstants.DB_BLANK) || null == hsqlQuery) {
					// logger.log(0,"No match found for given request");
					return retList;

				}
				String queryArray[] = hsqlQuery.split(DBConstants.DB_HASH);
				this.executeDMLQuery(queryArray[0]);
			} else if (parserQueryParameter.getQueryType().equalsIgnoreCase(
					ParserConstants.updateQuery)) {
				String hsqlQuery = databaseOperation
						.createHIbUpdateQuery(parserQueryParameter);
				if (hsqlQuery.equals(DBConstants.DB_BLANK) || null == hsqlQuery) {
					// logger.log(0,"No match found for given request");
					return retList;

				}
				// If query is legal and the database has been updated then int
				// result is 1 otherwise it is 0.If result is 0 then we are
				// returning null retList else we are adding the result to
				// retList so that it is not null and can be checked where the
				// function has been called.
				String queryArray[] = hsqlQuery.split(DBConstants.DB_HASH);
				int result = this.executeDMLQuery(queryArray[0]);
				if(result == 1)
				{
					retList = new ArrayList();
					retList.add(result);
				}

			} else if (parserQueryParameter.getQueryType().equalsIgnoreCase(
					ParserConstants.hibInsert)) {
				String tableName = (String) parserQueryParameter.getTables()
						.get(0);
				fieldList = parserQueryParameter.getFieldList();
				Object retObject = databaseOperation.retrieveObjectConcreteDAO(
						DBConstants.DB_BLANK, tableName, fieldList,
						DBClassLoader);
				if (null == retObject) {
					// throw (new
					// DBComponentException("!!!!!!!No hibernate mappings exist for this table"));
					// logger.log(0,"!!!!!!!No hibernate mappings exist for this table");
					return retList;
				}
				this.saveObject(retObject);

			} else if (parserQueryParameter.getQueryType().equalsIgnoreCase(
					ParserConstants.hibSelect)) {
				fieldLabelHash = new LinkedHashMap<String, String>();
				Boolean move = true;
				tableList = parserQueryParameter.getTables();
				fieldList = parserQueryParameter.getFieldList();
				if (fieldList.size() == 1 && tableList.size() == 1) {
					String retField = fieldList.get(0).getName().toLowerCase()
							.trim();
					String tableName = (String) tableList.get(0);
					if (retField.equalsIgnoreCase(DBConstants.DB_GETALL)) {
						move = false;
						retList = this.getDAOData(DBConstants.DB_BLANK,
								tableName);

					} else {
						move = true;
					}
				}
				if (move) {
					for (int fieldListCounter = 0; fieldListCounter < fieldList
							.size(); fieldListCounter++) {
						String retField = fieldList.get(fieldListCounter)
								.getName();
						fieldLabelHash.put(retField, retField);
					}
                    //added information of joins. done by pra on 7/10/2009 updated for orderby on 27/july 2009
					queryParameter = new QueryParameter(fieldLabelHash,
							parserQueryParameter.getConditions(), tableList, "",parserQueryParameter.getJoins(),parserQueryParameter.getOrderBy());

					queryFieldHash = databaseOperation.getDAOByName(
							queryParameter, "");
					if (queryFieldHash.isEmpty() || null == queryFieldHash) {
						// logger.log(0,"!!!!!!!!!!!!No match found for list request!!!!!!!!!!");
						return retList;
					} else {
						retList = this.createOutputList(queryFieldHash);
					}
				}
				return retList;
			}
			return retList;
		} else {
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_DATABASE_OPERATION
							+ databaseOperation + " in stringQuery");
		}

	}

	public Boolean deleteObject(Object obj) throws DBComponentException {
		Boolean retVal = null;
		Session session = null;

		try {

			session = getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			session.delete(obj);
			tx.commit();
		}

		catch (ConstraintViolationException cve) {
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION + obj
							+ "in deleteObject");
		} catch (DataException de) {
			throw new DBComponentException(DBExceptionConstants.DATA_EXCEPTION
					+ obj + "in deleteObject");
		} catch (SQLGrammarException sge) {
			throw new DBComponentException(
					DBExceptionConstants.SQL_GRAMMER_EXCEPTION + obj
							+ "in deleteObject");
		} catch (StaleStateException e) {
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + obj
							+ " in deleteObject");
		} catch (HibernateException e) {
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + obj
							+ " in deleteObject");
		}
		// catch()
		finally {
			if (null != session) {
				// session.flush();
				session.close();

			}
		}
		return retVal;
	}

	/*
	 * this method reurns the hashtable which contains the information of order
	 * of table in which they will be persisted in table added by pra
	 */
	public Hashtable<String, LinkedHashMap<String, List<Object>>> orderSave(
			Hashtable<String, Hashtable<String, List<Field>>> databaseTableHash) {
		Hashtable<String, List<Field>> tableFieldHash = null;
		Enumeration tableFieldEnum = null;
		Enumeration databaseTableEnum = databaseTableHash.keys();
		Hashtable<String, LinkedHashMap<String, List<Object>>> orderSave = new Hashtable<String, LinkedHashMap<String, List<Object>>>();
		LinkedHashMap<String, List<Object>> forgeinKeyInfo = null;

		while (databaseTableEnum.hasMoreElements()) {
			String databaseName = (String) databaseTableEnum.nextElement();

			// Based on database, set Hibernate Configuration and DBClassLoader
			if (!setConfigAndDBClassLoader(databaseName))
				return null;
			tableFieldHash = databaseTableHash.get(databaseName);
			if (null == tableFieldHash) {
				// logger.log(0,"!!!!!!!!!!! No tables exist for this database = "
				// + databaseName);
				return null;
			}
			forgeinKeyInfo = databaseOperation.orderTable(tableFieldHash,
					DBClassLoader,databaseName);
			orderSave.put(databaseName, forgeinKeyInfo);

		}
		return orderSave;
	}

	/**
	 * This method is used to save fields.It in turn calls database operation to
	 * set values for object and retrieve the object and then pass object to
	 * saveObject method.
	 * 
	 * @param databaseTableHash
	 * @param queryType
	 * @returns void
	 * @throws DBComponentException
	 */
	public Object saveField(
			Hashtable<String, Hashtable<String, List<Field>>> databaseTableHash,
			String queryType) throws DBComponentException {

		Enumeration databaseTableEnum = null;
		Hashtable<String, List<Field>> tableFieldHash = null;
		LinkedHashMap<String, List<Object>> orderHash = null;
		Enumeration tableFieldEnum = null;
		List<Field> fieldList = null;
		Object retObject = null;
		Hashtable<String, LinkedHashMap<String, List<Object>>> order = null;

		if (null != dbOpers) {
			order = this.orderSave(databaseTableHash);
			databaseTableEnum = databaseTableHash.keys();

			while (databaseTableEnum.hasMoreElements()) {
				String databaseName = (String) databaseTableEnum.nextElement();

				// Fetch databaseOperation from dbOpers HashMap
				// pertaining to current database and check for null
				if (!setConfigAndDBClassLoader(databaseName))
					return null;

				tableFieldHash = databaseTableHash.get(databaseName);
				orderHash = order.get(databaseName);
				if (null == tableFieldHash) {
					logger.log(0,
							"ERROR - No tables exist for this database = "
									+ databaseName);
					return null;
				}
				Object[] keys = orderHash.keySet().toArray();
				for (int count = 0; count < keys.length; count++) {
					String tableName = (String) keys[count];
					fieldList = tableFieldHash.get(tableName);
					if (null == fieldList || fieldList.isEmpty()) {
						logger
								.log(0,
										"ERROR - No fields specified for the given save query");
						return null;
					}

					// First take out fields that contain List type.
					List<Field> fieldsContainingListData = null;
					for (int fieldCount = fieldList.size() - 1; fieldCount >= 0; fieldCount--) {
						if (fieldList.get(fieldCount).getValue() instanceof java.util.List) {
							if (fieldsContainingListData == null)
								fieldsContainingListData = new ArrayList<Field>();
							fieldsContainingListData.add(fieldList
									.get(fieldCount));

							// Remove them from fieldList
							fieldList.remove(fieldCount);
						}
					}

					// If there are NOT any fields containing List data ...
					if (fieldsContainingListData == null) {
						// ... Perform save and nothing else
						retObject = performSave(databaseName, tableName,
								fieldList);
					} else {
						// First delete "to-be-orphaned" records
						deleteOrphanRecord(tableName, fieldList);

						// ... otherwise iterate through each list
						for (int fieldCount = 0; fieldCount < fieldsContainingListData
								.size(); fieldCount++) {
							// Get field name and field value
							Field field = fieldsContainingListData
									.get(fieldCount);
							String fieldName = field.getName();
							List fieldValue = (List) field.getValue();
							for (int fieldValueCount = 0; fieldValueCount < fieldValue
									.size(); fieldValueCount++) {
								// Create a new field based on each fieldValue
								Field newField = new Field();
								newField.setName(fieldName);
								newField.setValue(fieldValue
										.get(fieldValueCount));

								// Add it to fieldList
								fieldList.add(newField);

								// Perform save
								retObject = performSave(databaseName,
										tableName, fieldList);

								// and remove it from fieldList so that next
								// time another newField can be added.
								fieldList.remove(newField);
							}
						}

					}

					/*
					 * retObject = databaseOperation.retrieveObjectConcreteDAO(
					 * databaseName, tableName, fieldList, DBClassLoader);
					 */

					if (null == retObject) {
						logger.log(0,
								"ERROR - No hibernate mappings exist for this table "
										+ tableName);
						return null;

					}
					/* retObject = this.saveObject(retObject); */

					Object value = databaseOperation.primaryKeyValue(
							databaseName, tableName, DBClassLoader, retObject);
					retObject = value;

					orderHash.remove(tableName);
					tableFieldHash = updateTableFieldHash(tableFieldHash,
							orderHash, value, tableName);
				}
			}
		} else {
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_DATABASE_OPERATION
							+ databaseOperation + " in saveField");
		}
		return retObject;
	}

	/**
	 * To delete those records that are left orphaned and are not related to any
	 * record in parent table. This is used in cases where parent record now has
	 * a new set of child and thus the older ones should be deleted
	 * 
	 * @param tableName
	 * @param savedFields
	 * @throws DBComponentException
	 */
	private void deleteOrphanRecord(String tableName, List<Field> savedFields)
			throws DBComponentException {
		// Check foreignKey detail
		if (this.foreignKeyInfo != null) {
			// Get foreignKey columns
			List<String> foreignKeyCols = this.foreignKeyInfo.get(tableName);
			if (foreignKeyCols != null) {
				String deleteSql = null;

				// Iterate through all foreign keys
				for (int i = 0; i < foreignKeyCols.size(); i++) {
					// Iterate through savedFields so as to get value of field
					// used for querying database
					for (int j = 0; j < savedFields.size(); j++) {
						if (foreignKeyCols.get(i).equalsIgnoreCase(
								savedFields.get(j).getName())) {
							// Get class name from table name
							DBStructureUtil dbsUtil = new DBStructureUtil();
							dbsUtil.setDbc(this);
							String className = dbsUtil
									.getClassNameFromTable(tableName);

							// Create delete-sql
							if (deleteSql == null)
								deleteSql = "delete from " + className
										+ " where ";
							else
								deleteSql = deleteSql + " and ";

							deleteSql = deleteSql + " " + foreignKeyCols.get(i)
									+ " = " + savedFields.get(j).getValue();

							break;
						}
					}
				}

				// Execute deleteSql if it is not null
				if (deleteSql != null)
					this.executeDMLQuery(deleteSql);
			}
		}
	}

	/**
	 * This function creates a DAO object based on information passed and then
	 * saves the object in database.
	 * 
	 * @param databaseName
	 * @param tableName
	 * @param fieldList
	 * @return output of save; otherwise null
	 * @throws DBComponentException
	 */
	private Object performSave(String databaseName, String tableName,
			List<Field> fieldList) throws DBComponentException {
		Object retObject = databaseOperation.retrieveObjectConcreteDAO(
				databaseName, tableName, fieldList, DBClassLoader);

		if (null == retObject) {
			logger.log(0, "ERROR - No hibernate mappings exist for this table"
					+ tableName);
			return retObject;

		}
		return this.saveObject(retObject);
	}

	/*
	 * this methods updates the forgein keys in the tables added by pra
	 */
	public Hashtable<String, List<Field>> updateTableFieldHash(
			Hashtable<String, List<Field>> tableFieldHash,
			LinkedHashMap<String, List<Object>> orderHash, Object value,
			String refTableName) {
		Object[] orderEnum = orderHash.keySet().toArray();
		for (int keys = 0; keys < orderEnum.length; keys++) {
			String tableName = (String) orderEnum[keys];
			List objects = orderHash.get(tableName);
			List<Field> fields = tableFieldHash.get(tableName);
			for (int i = 0; i < objects.size(); i++) {
				org.hibernate.mapping.ForeignKey col = (org.hibernate.mapping.ForeignKey) objects
						.get(i);
				String refTable = col.getReferencedTable().getName();
				String field = "";
				List colName = col.getColumns();

				if (refTable.equalsIgnoreCase(refTableName)) {
					for (int j = 0; j < colName.size(); j++) {
						//change done by pra changed index.
						field = ((org.hibernate.mapping.Column) colName.get(j))
								.getName();
						for (int k = 0; k < fields.size(); k++) {
							Field fie = fields.get(k);
							if (fie.getName().equalsIgnoreCase(field)) {
								if (fie.getValue() == null
										|| fie.getValue() == "") {
									Field f = new Field();
									f.setValue(value);
									f.setName(fie.getName());
									fields.remove(k);
									fields.add(f);

									// Update foreignKeyInfo
									updateForeignKeyInfo(tableName, fie
											.getName());
								}
							} else {
								Field f = new Field();
								f.setValue(value);
								f.setName(field);
								fields.add(f);

								// Update foreignKeyInfo
								updateForeignKeyInfo(tableName, field);
							}
						}
					}
				}
			}
			tableFieldHash.put(tableName, fields);
		}
		return tableFieldHash;
	}

	private void updateForeignKeyInfo(String tableName, String fieldName) {
		if (foreignKeyInfo == null)
			foreignKeyInfo = new Hashtable<String, List<String>>();
		List<String> foreignKeyCols = foreignKeyInfo.get(tableName);
		if (foreignKeyCols == null)
			foreignKeyCols = new ArrayList<String>();
		foreignKeyCols.add(fieldName);
		foreignKeyInfo.put(tableName, foreignKeyCols);
	}

	/**
	 * This method is to retreive data from database using a SQL query
	 * 
	 * @param sqlQuery
	 * @returns List
	 * @throws DBComponentException
	 */
	public List getSQLList(String sqlQuery) throws DBComponentException {
		List result = null;
		Session session = null;
		SQLQuery sqlQueryObj = null;
		try {
			session = getSessionFactory().openSession();
			sqlQueryObj = session.createSQLQuery(sqlQuery);
			result = sqlQueryObj.list();
		}

		catch (ConstraintViolationException cve) {
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION
							+ sqlQuery + " in getList");
		} catch (DataException de) {
			throw new DBComponentException(DBExceptionConstants.DATA_EXCEPTION
					+ sqlQuery + "in getList");
		} catch (HibernateException e) {
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + sqlQuery
							+ "in getList");
		} finally {
			if (null != session) {
				session.close();

			}
		}
		return result;
	}

	/**
	 * This method is to retreive data from database using a HSQL query
	 * 
	 * @param hsql
	 * @returns List
	 * @throws DBComponentException
	 */

	public List getList(String hsql) throws DBComponentException 
	{
		logger.log(0, "[Database Component] [in getList mathod] [hql=]" + hsql);
		List result = null;
		Session session = null;
		Query hsqlQuery = null;
		Transaction tx = null;
		int firstResults = getFirstResult();
		int maxResults = getMaxResult();
		try 
		{
			session = getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.clear();
			System.out.println("HSQL:- "+hsql);
			hsqlQuery = session.createQuery(hsql);
			if (maxResults != 0) 
			{
				hsqlQuery.setFirstResult(firstResults);
				hsqlQuery.setMaxResults(maxResults);
			}
			result = hsqlQuery.list();
			tx.commit();
		}

		catch (ConstraintViolationException cve) 
		{
			if (tx!=null) tx.rollback();
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION + hsql
							+ " in getList");
		} 
		catch (DataException de) 
		{
			if (tx!=null) tx.rollback();
			throw new DBComponentException(DBExceptionConstants.DATA_EXCEPTION
					+ hsql + "in getList");
		} catch (HibernateException e) 
		{
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			/*
			 * logger.log(0,"in database------------" + e.getMessage()); throw
			 * new DBComponentException(DBExceptionConstants.HIBERNATE_EXCEPTION
			 * + hsql + "in getList");
			 */
		} 
		finally 
		{
			if (null != session) 
			{
				System.out.println("------------Inside getList-------closing, flusing and refreshing session");
				session.flush();
				session.close();
			}
		}
		clearFirstMaxResults();
		return result;
	}

	/**
	 * This method is used to set the DataConfiguration by creating the instance
	 * of the UserInfo class.
	 * 
	 * @param userName
	 * @param password
	 * @param serverURL
	 *            this is the url of the server e.g
	 *            "jdbc:mysql://localhost:3306"
	 * @param packageName
	 * @param baseDirectoryAddress
	 * @returns void
	 */
	public void setUpDataConfiguration(String userName, String password,
			String serverURL, String packageName, String baseDirectoryAddress) {
		userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setPassword(password);
		userInfo.setServerURL(serverURL);
		userInfo.setPackageName(packageName);
		userInfo.setBaseDirectoryAddress(baseDirectoryAddress);

	}

	/**
	 * This method is used to set the DataConfiguration by creating the instance
	 * of the UserInfo class.
	 * 
	 * @param userName
	 * @param password
	 * @param dbName
	 * @param serverName
	 * @param port
	 * @param dbType
	 * @param packageName
	 * @param baseDirectoryAddress
	 * @param jarName
	 */
	public void setUpDataConfiguration(String userName, String password,
			String dbName, String serverName, String port, String dbType,
			String packageName, String baseDirectoryAddress) {
		setUpDataConfiguration(userName, password, DBServerURLGenerator
				.getServerURL(dbType, dbName, serverName, port), packageName,
				baseDirectoryAddress);
	}

	/**
	 * This method is used to set the DataConfiguration by creating the instance
	 * of the UserInfo class.
	 * 
	 * @param userName
	 * @param password
	 * @param serverURL
	 * @param packageName
	 * @param baseDirectoryAddress
	 * @param jarName
	 * @returns void
	 */
	public void setUpDataConfiguration(String userName, String password,
			String serverURL, String packageName, String baseDirectoryAddress,
			String jarName) {
		userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setPassword(password);
		userInfo.setServerURL(serverURL);
		userInfo.setPackageName(packageName);
		userInfo.setBaseDirectoryAddress(baseDirectoryAddress);
		userInfo.setJarName(jarName);
	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @returns void
	 */
	public void stop() {

	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @returns void
	 */
	public void destroy() {
	}

	public static void main(String args[]) {
		DBComponent.getInstanceOfLoggingComponent();
		Hashtable<String, Object> formValues = new Hashtable();
		List<String> textId = new ArrayList<String>();
		LinkedList news = new LinkedList();
		Hashtable h = new Hashtable();

		// DatabaseMetadata dd = new DatabaseMetadata();
		formValues.put("textbox47", "test");
		formValues.put("textbox49", "0");
		formValues.put("combobox22", "1");
	
		
		/*select s.appointmentEndDate,s.appointmentEndTime,s.appointmentStartDate, s.appointmentStartTime, s.id,p.firstName ,st.type
		 from Schedule s
		 left outer join s.patient as p
		 left outer join s.studytype as st
		 where s.patient.id=1*/
		/*-----join query
		 * get schedule.AppointmentStartDate,schedule.AppointmentEndDate,schedule.AppointmentEndDate,schedule.AppointmentStartTime,"
		+ "schedule.AppointmentEndTime,schedule.id,patient.firstname,studytype.Type from neiswispicdb.schedule joins "
		+ "leftjoin schedule.StudyType:=studytype.id,leftjoin schedule.PatientID:=patient.id conditions schedule.patientID:="+1*/
		

		IHICData hicData = new HICData();
		com.oxymedical.core.commonData.IData data = new com.oxymedical.core.commonData.Data();

		com.oxymedical.core.commonData.IFormPattern formPattern = new com.oxymedical.core.commonData.FormPattern();
		formPattern.setFormId("addroom");
		formPattern.setFormValues(formValues);
		Object[] array = data.getClass().getMethods();
		data.setMethodName("save");

		com.oxymedical.core.commonData.IDataPattern dataPattern = new com.oxymedical.core.commonData.DataPattern();
		dataPattern.setDataPatternId("neiswispicdb");
		data.setDataPattern(dataPattern);
		data.setFormPattern(formPattern);
		QueryData qd = new QueryData();
		qd.setCondition("get primarycarephysician.id,concat(primarycarephysician.PrimaryRefDocFName|primarycarephysician.PrimaryRefDocMName|primarycarephysician.PrimaryRefDocLName)" +
				" from neiswispicdb.primarycarephysician");

		// data.setColumnOrder(columnOrder);
		data.setQueryData(qd);
		IApplication application = new Application();
		application.setApplicationName("NOLISUI");
		application
				.setBaseDirectoryPath("D:\\CDrive\\NOLIS_SVN\\NOLIS\\trunk\\dev\\src\\main\\NOLISApps\\NOLIS_Wisconsin\\NOLISUI\\");
		application.setApplicationFileName("NOLISUI.esp");
		application
				.setApplicationFolderPath("D:\\CDrive\\NOLIS_SVN\\NOLIS\\trunk\\dev\\src\\main\\NOLISApps\\NOLIS_Wisconsin\\NOLISUI\\");
		application
				.setServerDirectory("c:/glassfish/domains/domain1/autodeploy");
		application
				.setBaseDirectoryPath("c:/glassfish/domains/domain1/lib/ext");
		hicData.setApplication(application);
		hicData.setData(data);
		DBComponent dbComponent = new DBComponent();
		DBComponent.getInstanceOfLoggingComponent();
		try {
			IHICData hic = dbComponent.getListData(hicData);
			/*
			 * for(int i=0;i<1000;i++) { IHICData hic =
			 * dbComponent.dbOperation(hicData); }
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @returns void
	 */
	public void run() {

	}
	
	public synchronized void storeConnection(String dbName, DatabaseOperation dbOpr){
		dbOpers.put(dbName, dbOpr);
	}

	public synchronized void getConnection(String dbName) throws DBComponentException
	{
		System.out.println("getConnection dbName: " + dbName);
		
		// Creation of DB Resources task has been delegated to "createDBResourcesAndMappings" function
		boolean createDBResources = true;
		
		if(isConnectionAvailable == true && dbName.length() == 0){
			return;
		}else if(dbOpers.get(dbName) != null){
			setConfigAndDBClassLoader(dbName);
			return;
		}else if(dbOpers.get(dbName) == null){
			//check if resource_jar file exists and if so then load it
			String baseAppPath = hicData.getApplication().getBaseDirectoryPath();
			String jarFileName = "resource_" + dbName + ".jar";
			String resourceFileName = baseAppPath + File.separator + jarFileName;
			if(new File(resourceFileName).exists()){
				//loadResourcesJarInLoader(baseAppPath, jarFileName);
				//this.databaseOperation.setDbClassLoader(this.DBClassLoader);
				//dbOpers.put(dbName, this.databaseOperation);
				//return;
				createDBResources = false;
			}
		}
		DBComponent.getInstanceOfLoggingComponent();
		if (hicData == null)
		{
			throw new DBComponentException("hicData is null");
		}

		dataHandler = this.getDataHandler();
		Document formPatternDoc = null;
		String defaultForm = "";
		Document document = null;
		String dataPatternName;
		String serverName = "", userName = "", password = "";
		ArrayList<String> dataPatternArray = null;
		XmlReader xmlReader = new XmlReader();
		
		DBUtilities dbUtilities = new DBUtilities();
		IApplication application = hicData.getApplication();
		String applicationFolderPath = application.getApplicationFolderPath();
		String applicationFileName = application.getApplicationFileName();
		try
		{
			if (applicationFolderPath == null || applicationFolderPath.trim().equals(""))
				throw (new DBComponentException("Application Source not found"));
			else if (applicationFileName == null || applicationFileName.trim().equals(""))
				throw (new DBComponentException("Application Source not found"));
			else
			{
				String applicationFile = applicationFolderPath + applicationFileName;

				dataPatternArray = new ArrayList<String>();
				document = xmlReader.getDocumentFromPath(applicationFile);
				org.dom4j.Element root = document.getRootElement();
				if (root.getName().trim().equalsIgnoreCase(RegisterConstants.APPROOT_TAGNAME))
				{
					Element formPattern = root.element(RegisterConstants.FORM_PATTERN_TAG_NAME);
					List lst = root.elements(RegisterConstants.FORM_PATTERN_TAG_NAME);
					List dataList = root.elements("DataPattern");
					List formPatternList = formPattern.elements();
					String mainAppDocStr;
					// mainAppDocStr =
					// zkBuilderUtility.startApplication(application);
					if (dataList.size() > 0)
					{
						Element dataRoot = (org.dom4j.Element) dataList.get(0);
						logger.log(0, "datalist--------------get(0)--" + dataList.get(0));
						List dataSubLst = dataRoot.elements("datapattern");
						logger.log(0, "datapattern------------size0--" + dataSubLst.size());
						for (int i = 0; i < dataSubLst.size(); i++)
						{
							org.dom4j.Element page = (org.dom4j.Element) dataSubLst.get(i);
							/*
							 * if(page.attributeValue(UIConstants.defaultArg)!=null
							 * &&
							 * page.attributeValue(UIConstants.defaultArg).equals
							 * ("true")) defaultForm =
							 * page.attributeValue(UIConstants
							 * .elementName).trim();
							 */
							dataPatternArray.add(page.attributeValue("name"));
							logger.log(0, "page attribute value---------" + page.attributeValue("name"));
						}
					}

					if (null != formPatternList && formPatternList.size() > 0)
					{
						// logger.log(0,"formPatternList:size:"+formPatternList.size());
						for (int i = 0; i < formPatternList.size(); i++)
						{
							Element page = (Element) formPatternList.get(i);
							String formPatternXmlName = page.attributeValue(RegisterConstants.ELEMENT_ID);
							// logger.log(0,"formPatternXmlName1111111111111"+formPatternXmlName);
							if (null != page.attributeValue(RegisterConstants.DEFAULT_ARG)
									&& page.attributeValue(RegisterConstants.DEFAULT_ARG).equals(
											RegisterConstants.TRUE))
							{
								defaultForm = page.attributeValue(RegisterConstants.ELEMENT_NAME).trim();
								// mainAppDocStr = mainAppDocStr +
								// zkBuilderUtility.includeDefaultFormPattern(formPatternXmlName);
							}

							formPatternDoc = dbUtilities.getFormPatternXmlDoc(application, page
									.attributeValue(RegisterConstants.ELEMENT_ID));
							// ClientScriptBuilder.createDocList(formPatternDoc);

							// logger.log(0,"The form pattern xml document, name = "
							// +formPatternXmlName);
							// formPatternDoc =
							// zkBuilderUtility.getFormPatternXmlDoc(application,
							// formPatternXmlName);
							if (null == formPatternDoc)
							{
								logger.log(0, "The form pattern xml document, name = " + formPatternXmlName
										+ " not found");
								return;
							}

							if (application.getApplicationName().equalsIgnoreCase("maintenance"))
							{
								// monitDBComponent.getRegisterWindow().registerBaseWindow(doc,application.getApplicationName());
							}
							else if (application.getApplicationName().equalsIgnoreCase("BillingTracking"))
							{
								// bTDBComponent.getRegisterWindow().registerBaseWindow(doc,application.getApplicationName());
							}
							else
							{
								getRegisterWindow().registerBaseWindow(formPatternDoc,
										application.getApplicationName());
							}
						}
					}

					if (lst.size() > 0)
					{
						root = (org.dom4j.Element) lst.get(0);
						lst = root.elements(RegisterConstants.FORM_TAG_NAME);

					}

					String connectionStr[] = dbUtilities.getConnectionSettings(application
							.getApplicationFolderPath()
							+ RegisterConstants.DATA_SETTINGS_PATH);
					if (connectionStr != null)
					{
						serverName = connectionStr[0];
						userName = connectionStr[2];
						password = connectionStr[3];
					}

					for (int count = 0; count < dataPatternArray.size(); count++)
					{
						// Creating Database Resources
						dataPatternName = dataPatternArray.get(count);

						// logger.log(0,"datapatternArray-----------"+dataPatternArray.get(count));
						if (application.getApplicationName().equalsIgnoreCase("maintenance"))
						{

							// monitDBComponent.createDatabaseResources(userName,
							// password, serverName,
							// application.getBaseDirectoryPath(),
							// dataPatternName, createDBResources,
							// "resources_maintenance.jar");
							// monitDBComponent
						}
						else if (application.getApplicationName().equalsIgnoreCase("BillingTracking"))
						{

							// bTDBComponent.createDatabaseResources(userName,
							// password, serverName,
							// application.getBaseDirectoryPath(),
							// dataPatternName, createDBResources ,
							// "resources_bt.jar");

						}
						else
						{

							DataPatternParser.parseDataPattern(application, dataPatternName);

							createDatabaseResources(userName, password, serverName, application
									.getBaseDirectoryPath(), dataPatternName, createDBResources, "resource_"
									.concat(dataPatternName));

							// getDataHandler().connectionSettings("root","1234",
							// "localhost","GIPTime","C:/apache-tomcat-5.5.20/common/lib/ext");
							getDataHandler().connectionSettings(userName, password, serverName,
									application.getApplicationName(), application.getBaseDirectoryPath());

							// For maintaining different config and
							// DBClassLoader for each database.
							this.databaseOperation.setDbClassLoader(this.DBClassLoader);
							dbOpers.put(dataPatternName, this.databaseOperation);

						}
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw (new DBComponentException(exp.getMessage()));
		}
		isConnectionAvailable = true;

	}
	
	@EventSubscriber(topic = "createDBResourcesAndMappings")
	public synchronized void createDBResourcesAndMappings(IHICData hicData) throws DBComponentException
	{
		/*DBComponent.getInstanceOfLoggingComponent();
		if (hicData == null)
		{
			if (this.app == null)
			{
				throw new DBComponentException("hicData is null");
			}
		}
		dataHandler = this.getDataHandler();
		Document document = null;
		String serverName = "", userName = "", password = "";
		ArrayList<String> dataPatternArray = null;
		
		DBUtilities dbUtilities = new DBUtilities();
		IApplication application = (hicData != null) ? hicData.getApplication() : this.app;
		
		if (application == null)
		{
			throw new DBComponentException("application is null");
		}
		String applicationFolderPath = application.getApplicationFolderPath();
		String applicationFileName = application.getApplicationFileName();

		try
		{
			if (applicationFolderPath == null || applicationFolderPath.trim().equals(""))
				throw (new DBComponentException("Application Source not found"));
			else if (applicationFileName == null || applicationFileName.trim().equals(""))
				throw (new DBComponentException("Application Source not found"));
			else
			{

				String applicationFile = applicationFolderPath + applicationFileName;

				dataPatternArray = new ArrayList<String>();
				
				XmlReader xmlReader = new XmlReader();
				document = xmlReader.getDocumentFromPath(applicationFile);
				org.dom4j.Element root = document.getRootElement();
				if (root.getName().trim().equalsIgnoreCase(RegisterConstants.APPROOT_TAGNAME))
				{
					List dataList = root.elements("DataPattern");
					if (dataList.size() > 0)
					{
						Element dataRoot = (Element) dataList.get(0);
						List dataSubLst = dataRoot.elements("datapattern");
						for (int i = 0; i < dataSubLst.size(); i++)
						{
							Element page = (Element) dataSubLst.get(i);
							dataPatternArray.add(page.attributeValue("name"));
						}
					}

					String connectionStr[] = dbUtilities.getConnectionSettings(application
							.getApplicationFolderPath()
							+ RegisterConstants.DATA_SETTINGS_PATH);
					if (connectionStr != null)
					{
						serverName = connectionStr[0];
						userName = connectionStr[2];
						password = connectionStr[3];
					}
					
					boolean createDBResources = true;
					for (int count = 0; count < dataPatternArray.size(); count++)
					{
						String dataPatternName = dataPatternArray.get(count);

						System.out.println("[Creating Database Resource Only]" + dataPatternName);
						createDatabaseResourcesOnly(userName, password, serverName, application
								.getBaseDirectoryPath(), dataPatternName, createDBResources, "resource_"
								.concat(dataPatternName));
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw (new DBComponentException(exp.getMessage()));
		}*/
	}

	/**
	 * This function is called by other components those have injected
	 * dbcomponent with them and they want to register application from there
	 * dbcomponent instance.
	 * 
	 * @param hicData
	 */
	public void registerWindowForApplication(IHICData hicData) {
		Document formPatternDoc = null;
		String defaultForm = "";
		Document document = null;
		String dataPatternName;
		ArrayList<String> dataPatternArray = null;
		XmlReader xmlReader = new XmlReader();
		DBUtilities dbUtilities = new DBUtilities();
		IApplication application = hicData.getApplication();
		String applicationFolderPath = application.getApplicationFolderPath();
		logger.log(0,
				"folder path is registerWindowForApplication ----------------"
						+ applicationFolderPath);
		String applicationFileName = application.getApplicationFileName();

		logger.log(0, "application name---registerWindowForApplication--------"
				+ applicationFileName);
		try {
			if (applicationFolderPath == null
					|| applicationFolderPath.trim().equals(""))
				throw (new DBComponentException("Application Source not found"));
			else if (applicationFileName == null
					|| applicationFileName.trim().equals(""))
				throw (new DBComponentException("Application Source not found"));
			else {

				String applicationFile = applicationFolderPath
						+ applicationFileName;

				dataPatternArray = new ArrayList<String>();
				document = xmlReader.getDocumentFromPath(applicationFile);
				org.dom4j.Element root = document.getRootElement();
				if (root.getName().trim().equalsIgnoreCase(
						RegisterConstants.APPROOT_TAGNAME)) {
					Element formPattern = root
							.element(RegisterConstants.FORM_PATTERN_TAG_NAME);
					List lst = root
							.elements(RegisterConstants.FORM_PATTERN_TAG_NAME);
					List dataList = root.elements("DataPattern");
					List formPatternList = formPattern.elements();
					if (dataList.size() > 0) {
						Element dataRoot = (org.dom4j.Element) dataList.get(0);
						logger.log(0, "datalist--------------get(0)--"
								+ dataList.get(0));
						List dataSubLst = dataRoot.elements("datapattern");
						logger.log(0, "datapattern------------size0--"
								+ dataSubLst.size());
						for (int i = 0; i < dataSubLst.size(); i++) {
							org.dom4j.Element page = (org.dom4j.Element) dataSubLst
									.get(i);
							/*
							 * if(page.attributeValue(UIConstants.defaultArg)!=null
							 * &&
							 * page.attributeValue(UIConstants.defaultArg).equals
							 * ("true")) defaultForm =
							 * page.attributeValue(UIConstants
							 * .elementName).trim();
							 */
							dataPatternArray.add(page.attributeValue("name"));
							logger.log(0, "page attribute value---------"
									+ page.attributeValue("name"));
						}
					}

					if (null != formPatternList && formPatternList.size() > 0) {
						// logger.log(0,"formPatternList:size:"+formPatternList.size());
						for (int i = 0; i < formPatternList.size(); i++) {
							Element page = (Element) formPatternList.get(i);
							String formPatternXmlName = page
									.attributeValue(RegisterConstants.ELEMENT_ID);
							// logger.log(0,"formPatternXmlName1111111111111"+formPatternXmlName);
							if (null != page
									.attributeValue(RegisterConstants.DEFAULT_ARG)
									&& page.attributeValue(
											RegisterConstants.DEFAULT_ARG)
											.equals(RegisterConstants.TRUE)) {
								defaultForm = page.attributeValue(
										RegisterConstants.ELEMENT_NAME).trim();
								// mainAppDocStr = mainAppDocStr +
								// zkBuilderUtility.includeDefaultFormPattern(formPatternXmlName);
							}

							formPatternDoc = dbUtilities
									.getFormPatternXmlDoc(
											application,
											page
													.attributeValue(RegisterConstants.ELEMENT_ID));
							// ClientScriptBuilder.createDocList(formPatternDoc);

							// logger.log(0,"The form pattern xml document, name = "
							// +formPatternXmlName);
							// formPatternDoc =
							// zkBuilderUtility.getFormPatternXmlDoc(application,
							// formPatternXmlName);
							if (null == formPatternDoc) {
								logger.log(0,
										"The form pattern xml document, name = "
												+ formPatternXmlName
												+ " not found");
								return;
							}

							if (application.getApplicationName()
									.equalsIgnoreCase("maintenance")) {

								// monitDBComponent.getRegisterWindow().registerBaseWindow(doc,application.getApplicationName());
							} else if (application.getApplicationName()
									.equalsIgnoreCase("BillingTracking")) {

								// bTDBComponent.getRegisterWindow().registerBaseWindow(doc,application.getApplicationName());
							} else {

								getRegisterWindow().registerBaseWindow(
										formPatternDoc,
										application.getApplicationName());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String[] getConnectionSettings(String dbSettingsFile)
			throws DBComponentException {
		String connectionSettings[] = { "", "", "", "" };
		String driverClass, serverName, user, pwd;

		XmlReader xmlReader = new XmlReader();
		Document databaseConfig = xmlReader.getDocumentFromPath(dbSettingsFile);

		Element el = databaseConfig.getRootElement();
		// connUrl = "jdbc:mysql://localhost:3306/datapattern0";
		driverClass = "com.mysql.jdbc.Driver";
		serverName = el.element("servername").getTextTrim();
		user = el.element("user").getTextTrim();
		pwd = el.element("password").getTextTrim();
		connectionSettings[0] = serverName;
		connectionSettings[1] = driverClass;
		connectionSettings[2] = user;
		connectionSettings[3] = pwd;

		return connectionSettings;
	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @returns void
	 */
	public void start(Hashtable<String, org.dom4j.Document> arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * This method is DML such as retreive HSQL query
	 * 
	 * @param hsql
	 * @returns Integer
	 * @throws DBComponentException
	 */
	public Integer executeDMLQuery(String hsql) throws DBComponentException {
		logger.log(0, "-----in executeDMLQuery check hsql ---" + hsql);
		Integer result = null;
		Session session = null;
		Query hsqlQuery = null;
		try {
			session = getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();

			hsqlQuery = session.createQuery(hsql);
			result = hsqlQuery.executeUpdate();
			tx.commit();
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION + hsql
							+ " in executeDMLQuery");
		} catch (DataException de) {
			de.printStackTrace();
			throw new DBComponentException(DBExceptionConstants.DATA_EXCEPTION
					+ hsql + "in executeDMLQuery");
		} catch (SQLGrammarException sge) {
			sge.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.SQL_GRAMMER_EXCEPTION + hsql
							+ " in executeDMLQuery");
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + hsql
							+ "in executeDMLQuery");
		}

		finally {
			if (null != session)

				session.close();
		}
		return result;

	}

	/**
	 * This method opens session and creates hibernate specific query.
	 * 
	 * @param hsql
	 * @param listParameter
	 * @returns Integer
	 * @throws DBComponentException
	 */

	public Integer executeDMLQueryWithNameParameter(String hsql,
			ArrayList<NameQueryParameter> listParameter)
			throws DBComponentException {
		logger.log(0, "---DBComponent executeDMLQueryWithNameParameter----"
				+ hsql);
		Session session = null;
		Query hsqlQuery = null;
		Integer result = null;
		Transaction tx = null;
		try {
			session = getSessionFactory().openSession();
			logger.log(0, " ---session get----" + session.getClass());
			tx = session.beginTransaction();
			logger.log(0, "----transection begin with session----"
					+ tx.toString());
			hsqlQuery = session.createQuery(hsql);
			if (null != listParameter) {
				for (Iterator it = listParameter.iterator(); it.hasNext();) {
					NameQueryParameter nameQueryParameter = (NameQueryParameter) it
							.next();
					if (null != nameQueryParameter) {
						if (null != nameQueryParameter.getTypeParam()) {
							hsqlQuery.setParameter(nameQueryParameter
									.getParameter(), nameQueryParameter
									.getValueParameter(), nameQueryParameter
									.getTypeParam());
						} else {
							hsqlQuery.setParameter(nameQueryParameter
									.getParameter(), nameQueryParameter
									.getValueParameter());
						}

					}
				}
			}
			if (null != hsqlQuery) {
				result = hsqlQuery.executeUpdate();
				logger.log(0,
						"----transection direct commit from section------");
				tx.commit();
				DBComponent.logger.log(0,
						"----tx iscommited--from dbcomp-executeDMLQueryWithNameParameter---"
								+ tx.wasCommitted());
				DBComponent.logger.log(0,
						"----result ---from dbcomp-executeDMLQueryWithNameParameter---"
								+ result);
			}
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.CONSTRAINT_VOILATION_EXCEPTION + hsql
							+ " in executeDMLQueryWithNameParameter");
		} catch (DataException de) {
			de.printStackTrace();
			throw new DBComponentException(DBExceptionConstants.DATA_EXCEPTION
					+ hsql + "  in executeDMLQueryWithNameParameter");
		} catch (SQLGrammarException sge) {
			sge.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.SQL_GRAMMER_EXCEPTION + hsql
							+ " in executeDMLQueryWithNameParameter");
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.HIBERNATE_EXCEPTION + hsql
							+ " in executeDMLQueryWithNameParameter");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != session)
				session.close();
		}
		logger.log(0, "--return--result --executeDMLQueryWithNameParameter---"
				+ result);
		return result;
	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @param maintenanceData
	 * @returns void
	 */
	public void maintenance(IMaintenanceData maintenanceData) {

	}

	/**
	 * This method is used to retrieve the data for grid.
	 * 
	 * @param databaseName
	 * @param dataTableName
	 * @returns String
	 * @throws DBComponentException
	 */

	public String getDAOByName(String databaseName, String dataTableName)
			throws DBComponentException {
		String outputXML = null;
		Object resultList = null;
		ConcreteDAO concreteDAO = null;
		Class javaClass = null;
		String hsqlQuery = null;
		try {
			if (null != databaseOperation) {
				concreteDAO = (ConcreteDAO) databaseOperation.tableNameClassNameMap
						.get(dataTableName.trim());
				// logger.log(0,"!!!!!!!  tableNameClassNameMap = " +
				// databaseOperation.tableNameClassNameMap);
				if (null == concreteDAO) {
					throw new DBComponentException((new StringBuilder(
							"No hibernate mappings exist for this table = "))
							.append(dataTableName).toString());
				}
				javaClass = DBClassLoader.loadClass(concreteDAO
						.getDaoClassName());
				hsqlQuery = (new StringBuilder("from ")).append(
						concreteDAO.getDaoClassName()).append(" ").toString();

				resultList = getList(hsqlQuery);
				if (null == resultList) {
					// logger.log(0,"!!!!!!!!!!!!!!No data found for table = "
					// + dataTableName);

				} else {
					outputXML = databaseOperation.executeQuery(
							(List) resultList, concreteDAO, javaClass);
					// logger.log(0,"In DBComponent gRID dATA = " +
					// outputXML);
				}
			}
			return outputXML;
		} catch (ClassNotFoundException e) {
			throw new DBComponentException(
					DBExceptionConstants.CLASS_NOT_FOUND_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + "in getDAOByName");
		}

	}

	public List getDAOData(String databaseName, String dataTableName)
			throws DBComponentException {

		String outputXML = null;
		// Object resultList = null;
		List resulelist = new ArrayList();
		ConcreteDAO concreteDAO = null;
		Class javaClass = null;
		String hsqlQuery = null;
		try {
			if (null != databaseOperation) {
				concreteDAO = (ConcreteDAO) databaseOperation.tableNameClassNameMap
						.get(dataTableName.trim());
				// logger.log(0,"!!!!!!!  tableNameClassNameMap = " +
				// databaseOperation.tableNameClassNameMap);
				if (null == concreteDAO) {
					throw new DBComponentException((new StringBuilder(
							"No hibernate mappings exist for this table = "))
							.append(dataTableName).toString());
				}
				javaClass = DBClassLoader.loadClass(concreteDAO
						.getDaoClassName());
				hsqlQuery = (new StringBuilder("from ")).append(
						concreteDAO.getDaoClassName()).append(" ").toString();

				resulelist = getList(hsqlQuery);
				if (null == resulelist) {
					// logger.log(0,"!! ERROR !! No data found for table = " +
					// dataTableName);

				} else {
					outputXML = databaseOperation.executeQuery(
							(List) resulelist, concreteDAO, javaClass);
					// logger.log(0,"In DBComponent gRID dATA = " +
					// outputXML);
				}
			}
			return resulelist;
		} catch (ClassNotFoundException e) {
			throw new DBComponentException(
					DBExceptionConstants.CLASS_NOT_FOUND_EXCEPTION
							+ databaseName + DBConstants.DB_COMMA
							+ dataTableName + "in getDAOByName");
		}

	}

	/**
	 * This method is used to get data for the list.
	 * 
	 * @param fieldHash
	 * @param tableList
	 * @param condition
	 * @returns String
	 * @throws DBComponentException
	 */
	// Used to get data for list
	public String getDAOByName(LinkedHashMap<String, String> fieldHash,
			List tableList, String condition) throws DBComponentException {
		String outputXML = null;
		Hashtable<String, List<String>> queryFieldHash = null;
		QueryParameter queryParameter = null;

		if (null != databaseOperation) {
			if (fieldHash.isEmpty() || null == fieldHash) {
				throw new DBComponentException(
						DBExceptionConstants.NULL_POINTER_EXCEPTION
								+ DBConstants.DB_FIELD_HASH + fieldHash
								+ "in getDAOByName");

			}

			queryParameter = new QueryParameter(fieldHash, null, tableList,
					condition);
			queryFieldHash = databaseOperation.getDAOByName(queryParameter,
					DBConstants.DB_BLANK);
			if (queryFieldHash.isEmpty() || null == queryFieldHash) {
				// logger.log(0,"!!!!!!!!! No match found for list request");
				return outputXML;

			} else {
				outputXML = this.createOutput(queryFieldHash);

			}
			return outputXML;
		} else {
			throw (new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_DATABASE_OPERATION
							+ databaseOperation + "in getDAOByName" + condition));
		}
	}

	private String createOutput(Hashtable<String, List<String>> queryFieldHash)
			throws DBComponentException {
		String outputXML = null;
		String hsqlQuery = null;
		List<String> fieldLabelList = null;
		Enumeration queryFieldEnum = null;
		// Object resultList = null;
		List resultList = null;
		if (queryFieldHash.size() == 1) {
			queryFieldEnum = queryFieldHash.keys();
			while (queryFieldEnum.hasMoreElements()) {
				hsqlQuery = (String) queryFieldEnum.nextElement();
				fieldLabelList = queryFieldHash.get(hsqlQuery);
			}
		}

		resultList = this.getList(hsqlQuery);
		logger.log(0, "[resultList in dbclass]");
		logger.log(0, "" + resultList);
		if (resultList.equals(DBConstants.DB_BLANK) || null == resultList) {
			logger
					.log(0,
							"!!!! ERROR: No list match found for the given query");
			return outputXML;
		}

		outputXML = (String) databaseOperation
				.createOutputString(resultList, DBConstants.DB_LIST_QUERY,
						DBConstants.DB_BLANK, fieldLabelList);
		return outputXML;
	}

	private List createOutputList(Hashtable<String, List<String>> queryFieldHash)
			throws DBComponentException {

		String outputXML = null;
		String hsqlQuery = null;
		List<String> fieldLabelList = null;
		Enumeration queryFieldEnum = null;
		// Object resultList = null;
		List resultList = null;
		if (queryFieldHash.size() == 1) {
			queryFieldEnum = queryFieldHash.keys();
			while (queryFieldEnum.hasMoreElements()) {
				hsqlQuery = (String) queryFieldEnum.nextElement();
				fieldLabelList = queryFieldHash.get(hsqlQuery);
			}
		}

		resultList = this.getList(hsqlQuery);

		if (resultList.equals(DBConstants.DB_BLANK) || null == resultList) {
			logger.log(0, "[No list match found for the given query]");
			return resultList;
		}

	/*	outputXML = (String) databaseOperation
				.createOutputString(resultList, DBConstants.DB_LIST_QUERY,
						DBConstants.DB_BLANK, fieldLabelList);*/
		// return outputXML;

		logger.log(0, "[XML OUTPUT]" + outputXML);

		return resultList;
	}

	/**
	 * This method is used to create tree by taking object of the TreeNode
	 * class.
	 * 
	 * @param treeNode
	 * @returns String
	 * @throws DBComponentException
	 */
	public void addToTreeHash(TreeNode treeNode) throws DBComponentException {
		String hsqlQuery = null;
		QueryParameter queryParam = new QueryParameter();
		List tableList = new ArrayList();
		LinkedHashMap fieldHash = new LinkedHashMap();
		String conditionString = null;
		List fieldLabelList = null;
		TreeResultObject treeResultObj = null;
		Hashtable treeResultHash = null;
		Enumeration treeResultHashEnum = null;
		Integer recCount = 0;
		Hashtable retHash = null;
		List treeResultObjectList = null;
		Hashtable queryFieldHash = null;
		// Object resultList = null;
		List resultList = null;
		if (databaseOperation != null) {
			if (null == treeNodeHash) {
				treeNodeHash = new Hashtable();
			}

			if (null == treeNodeHash.get(treeNode.getLevelNo())) {
				treeNodeHash.put(treeNode.getLevelNo(), treeNode);
				if (treeNode.getParentNodeLevel().equals(DBConstants.DB_BLANK)) {
					// logger.log(0,"Getting for root");
					treeLevelResultHash = new Hashtable();
					tableList = new ArrayList();
					tableList.add(treeNode.getTableList());
					queryParam.setTableName(tableList);
					queryParam.setCondition(treeNode.getConditionString()
							.trim());
					fieldHash = new LinkedHashMap();
					// dbCons
					fieldHash.put(DBConstants.DB_TREE_DISPLAY, treeNode
							.getDisplayfield());
					fieldHash.put(DBConstants.DB_TREE_VALUE, treeNode
							.getValueField());
					queryParam.setFieldNameLabelHash(fieldHash);
					queryFieldHash = databaseOperation.getDAOByName(queryParam,
							DBConstants.DB_TREE_QUERY);
					if (queryFieldHash.size() == 1) {
						for (Enumeration queryFieldEnum = queryFieldHash.keys(); queryFieldEnum
								.hasMoreElements();) {
							hsqlQuery = (String) queryFieldEnum.nextElement();
							fieldLabelList = (ArrayList) (ArrayList) queryFieldHash
									.get(hsqlQuery);
						}

					}
					resultList = getList(hsqlQuery);
					if (null == resultList || null == fieldLabelList) {
						// logger.log(0,"!!!!!!!!!!1No result found for root!!!!!!!!!!!");
						return;
					}
					treeResultHash = (Hashtable) databaseOperation
							.createOutputString(resultList,
									DBConstants.DB_TREE_QUERY, "",
									fieldLabelList);
					treeResultHashEnum = treeResultHash.keys();
					treeResultObjectList = new ArrayList();
					while (treeResultHashEnum.hasMoreElements()) {
						recCount = (Integer) treeResultHashEnum.nextElement();
						retHash = (Hashtable) treeResultHash.get(recCount);
						treeResultObj = createTreeObject(retHash);
						treeResultObj.setParentNode(DBConstants.DB_BLANK);
						treeResultObj.setOwnNode(treeNode.getLevelNo());
						treeResultObjectList.add(treeResultObj);
					}
					treeLevelResultHash.put(treeNode.getLevelNo(),
							treeResultObjectList);

				} else {
					tableList = new ArrayList();
					tableList.add(treeNode.getTableList());
					queryParam.setTableName(tableList);
					fieldHash = new LinkedHashMap();
					fieldHash.put(DBConstants.DB_TREE_DISPLAY, treeNode
							.getDisplayfield());
					// logger.log(0,"display field = " +
					// treeNode.getDisplayfield());

					fieldHash.put(DBConstants.DB_TREE_VALUE, treeNode
							.getValueField());
					// logger.log(0,"value field = " +
					// treeNode.getValueField());
					queryParam.setFieldNameLabelHash(fieldHash);
					// logger.log(0,"fieldHash = " + fieldHash);
					// logger.log(0,"queryParam = " + queryParam);
					if (null == treeLevelResultHash
							|| treeLevelResultHash.isEmpty()) {
						return;
					}
					// logger.log(0,"treeNode.getParentNodeLevel() = " +
					// treeNode.getParentNodeLevel());
					treeResultObjectList = (List) treeLevelResultHash
							.get(treeNode.getParentNodeLevel());
					// logger.log(0,"treeResultObjectList = " +
					// treeResultObjectList);
					if (treeResultObjectList.isEmpty()
							|| null == treeResultObjectList) {
						return;
					}
					List childTreeResultObjectList = new ArrayList();
					for (int resultObjectCounter = 0; resultObjectCounter < treeResultObjectList
							.size(); resultObjectCounter++) {
						treeResultObj = (TreeResultObject) treeResultObjectList
								.get(resultObjectCounter);
						if (null == treeResultObj) {
							// logger.log(0,"!!!!!!!!!!!!! TreeResultObjecy is null");
							return;
						}
						if (treeNode.getConditionValue().equalsIgnoreCase(
								treeResultObj.getDisplayField().getName())) {
							conditionString = (new StringBuilder(String
									.valueOf(treeNode.getConditionField())))
									.append("=").append(
											treeResultObj.getDisplayField()
													.getText()).toString();
						}

						else if (treeNode.getConditionValue().equalsIgnoreCase(
								treeResultObj.getValueField().getName())) {
							conditionString = (new StringBuilder(String
									.valueOf(treeNode.getConditionField())))
									.append("=").append(
											treeResultObj.getValueField()
													.getText()).toString();
						}
						queryParam.setCondition(conditionString);
						queryFieldHash = databaseOperation.getDAOByName(
								queryParam, DBConstants.DB_TREE_QUERY);
						if (queryFieldHash.size() == 1) {
							for (Enumeration queryFieldEnum = queryFieldHash
									.keys(); queryFieldEnum.hasMoreElements();) {
								hsqlQuery = (String) queryFieldEnum
										.nextElement();
								fieldLabelList = (ArrayList) (ArrayList) queryFieldHash
										.get(hsqlQuery);
							}

						}
						resultList = getList(hsqlQuery);
						if (null != resultList) {
							TreeResultObject newTreeResultObj;
							treeResultHash = (Hashtable) databaseOperation
									.createOutputString(resultList,
											DBConstants.DB_TREE_QUERY, "",
											fieldLabelList);
							if (null == treeResultHash
									|| treeResultHash.isEmpty()) {
								// logger.log(0,"!!!!!!!!!No parent found for child");
								continue;
							}
							treeResultHashEnum = treeResultHash.keys();
							while (treeResultHashEnum.hasMoreElements()) {
								recCount = (Integer) treeResultHashEnum
										.nextElement();
								retHash = (Hashtable) treeResultHash
										.get(recCount);
								newTreeResultObj = createTreeObject(retHash);
								newTreeResultObj.setOwnNode(treeNode
										.getLevelNo());
								newTreeResultObj
										.setParentNode((new StringBuilder())
												.append(
														treeResultObj
																.getValueField()
																.getText())
												.append("L").append(
														treeResultObj
																.getOwnNode())
												.toString());
								childTreeResultObjectList.add(newTreeResultObj);
							}

						}
					}
					treeLevelResultHash.put(treeNode.getLevelNo(),
							childTreeResultObjectList);
				}
			} else {
				// logger.log(0,"Request for this level already exist");
			}
		} else {
			throw new DBComponentException(
					DBExceptionConstants.NULL_POINTER_EXCEPTION
							+ DBConstants.DB_DATABASE_OPERATION
							+ databaseOperation + " in addToTreeHash");
		}

	}

	private TreeResultObject createTreeObject(Hashtable treeResultHash)
			throws DBComponentException {
		ValueField valueField = null;
		DisplayField displayField = null;
		TreeResultObject newTreeResultObj = new TreeResultObject();
		Enumeration recordHashEnum = null;

		recordHashEnum = treeResultHash.keys();
		while (recordHashEnum.hasMoreElements()) {
			String fieldName = (String) recordHashEnum.nextElement();
			Object value = treeResultHash.get(fieldName);
			if (fieldName.trim().equalsIgnoreCase(DBConstants.DB_TREE_DISPLAY)) {
				displayField = new DisplayField();
				displayField.setName(fieldName);
				displayField.setText(value);
				newTreeResultObj.setDisplayField(displayField);
			} else if (fieldName.trim().equalsIgnoreCase(
					DBConstants.DB_TREE_VALUE)) {
				valueField = new ValueField();
				valueField.setName(fieldName);
				valueField.setText(value);
				newTreeResultObj.setValueField(valueField);
			}
		}
		return newTreeResultObj;
	}

	/**
	 * This method is used to retrieve the XML by invoking the method of TreeXML
	 * class.
	 * 
	 * @returns String
	 */
	public String createTreeXml() throws DBComponentException {
		String outputXML = null;
		TreeXML treeLevel = new TreeXML();

		if (null != treeLevelResultHash) {
			outputXML = treeLevel.createXML(treeLevelResultHash);
			treeNodeHash = null;
		} else {
			// logger.log(0,"No tree data exist for displaying");
			return outputXML;
		}
		return outputXML;
	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @returns IHICData
	 */
	public IHICData getHicData() {
		return this.hicData;
	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @param arg0
	 * @returns void
	 */
	public void setHicData(IHICData arg0) {
		hicData = arg0;
	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * 
	 * @returns Object
	 */
	@EventSubscriber(topic = "save")
	public IHICData dbOperation(IHICData dataObj) throws DBComponentException {

		hicData = dataObj;
		String Str = null;
		try {

			getConnection("");
			dataHandler = this.getDataHandler();
			String windowFormID = null;
			String sourceMethod = null;
			String queStr = null;
			Hashtable<String, Object> formValues = null;
			IData data = null;
			if (hicData != null) {
				data = hicData.getData();
				IFormPattern formPatternData = data.getFormPattern();
				windowFormID = formPatternData.getFormId();
				// windowFormID="formpattern0";
				formValues = formPatternData.getFormValues();

				sourceMethod = data.getMethodName();

				// sourceMethod = "saveForm";

			}

			if (null != windowFormID && null != formValues) {
				if (sourceMethod.equals("save")) {

					Object objId = dataHandler.save(windowFormID, formValues,"");
					//return inserted data unique id. changes by Wasim Khan.30-July-2009
					System.out.println("table primary key is " + objId.getClass());
					hicData.setUniqueID(objId.toString());
					
					
				}
			}
		} catch (DBComponentException exp) {
			exp.printStackTrace();
			throw new DBComponentException(exp.getMessage());
		}

		return hicData;
	}
	
	public String getDBNameFromQuery(String sqlStr){
		String dbName = "";
		try{
			HICQueryParser hicQueryParser = new HICQueryParser();
			ParserQueryParameters parserQueryParameter = hicQueryParser.parseHICQuery(sqlStr);
			dbName = parserQueryParameter.getDatabaseName();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return dbName;
	}

	@EventSubscriber(topic = "executeList")
	public IHICData getListData(IHICData requestData)
			throws DBComponentException {
		DBUtilities dbUtilities = new DBUtilities();
		getInstanceOfLoggingComponent();
		QueryData queryResponseData = null;
		hicData = requestData;
		IData data = requestData.getData();
		setColumnOrder(data);
		queryResponseData = data.getQueryData();
		logger.log(0, "[queryRequestData is]" + queryResponseData);
		String queryStr = data.getQueryData().getCondition();
		LinkedHashMap<String, LinkedHashMap<String, String>> colRecord = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		String responseXML = null;
		List<Object> fetchValue = null;
		boolean exception = false;
		try {
			getConnection(getDBNameFromQuery(queryStr));
			dataHandler = this.getDataHandler();
			
			fetchValue = dataHandler.executeQueryList(queryStr);
			if (fetchValue != null) {
				queryResponseData.setListData(fetchValue);
				data.setQueryData(queryResponseData);
			}
		} catch (DBComponentException exp) {

			exp.printStackTrace();
			/*
			 * throw new DBComponentException(
			 * "!!!!! Error occurred while executing the the action query " +
			 * exp.getMessage());
			 */
		}

		return requestData;
	}

	/**
	 * This method tabkes databasename and return all tables.
	 * 
	 * @param requestData
	 * @return
	 * @throws DBComponentException
	 */
	@EventSubscriber(topic = "dbStructure")
	public IHICData getDataMeta(IHICData requestData)
			throws DBComponentException {

		DBUtilities dbUtilities = new DBUtilities();
		QueryData queryResponseData = null;
		hicData = requestData;
		IData data = requestData.getData();
		queryResponseData = data.getQueryData();
		String queryStr = data.getQueryData().getCondition();
		String[] queryArray = null;
		String dataBaseName = null;
		String tableName = null;
		if (queryStr != null) {
			queryArray = queryStr.trim().split("=");
			if (queryArray.length > 1) {
				String value = queryArray[0].trim();
				if (value.equalsIgnoreCase("dbName")) {
					dataBaseName = queryArray[1];
				} else if (value.trim().equalsIgnoreCase("tableName")) {
					tableName = queryArray[1];
				}

			}
		}

		List fetchValue = null;
		try {
			DBStructureUtil dbsUtil = new DBStructureUtil();
			if (dataBaseName != null) {
				getConnection(dataBaseName);
				this.setConfigAndDBClassLoader(dataBaseName);
				dbsUtil.setDbc(this);
				fetchValue = dbsUtil.getAllTableClasses();
			} else if (tableName != null) {
				getConnection("");
				String[] split = tableName.trim().split("#");
				String dbName = null;
				if (split.length > 1) {
					dbName = split[0];
					tableName = split[1].trim();
				}

				this.setConfigAndDBClassLoader(dbName.trim());
				dbsUtil.setDbc(this);
				fetchValue = dbsUtil.getOnlyTableClassFields(tableName);
			}
			if (fetchValue != null) {
				Collections.sort(fetchValue);
				queryResponseData.setListData(fetchValue);
				data.setQueryData(queryResponseData);
			}
		} catch (DBComponentException exp) {

			exp.printStackTrace();
			/*
			 * throw new DBComponentException(
			 * "!!!!! Error occurred while executing the the action query " +
			 * exp.getMessage());
			 */
		}

		return requestData;
	}

	/*
	 * This method is used to generate the colum order for the ouput. Previously
	 * it was done in uilibaray ,now it is handled in dbcomponent method added
	 * by pra
	 */
	private void setColumnOrder(IData data) {
		String condStr = data.getQueryData().getCondition();
		LinkedHashMap columnOrder = new LinkedHashMap();
		String limitStr = "";
		if (condStr.indexOf(DBConstants.DB_PAGE_LIMIT) > 0) {
			int i = condStr.indexOf(DBConstants.DB_PAGE_LIMIT);
			limitStr = condStr.substring(
					i + DBConstants.DB_PAGE_LIMIT.length(), condStr.length());
			condStr = condStr.substring(0, i);
			String[] limits = limitStr.split(",");
			setFirstResult(Integer.valueOf(limits[0]).intValue());
			setMaxResult(Integer.valueOf(limits[1]).intValue());
		}

		int k;
		int getIndex = condStr.indexOf("get");
		int fromIndex = condStr.indexOf("from");
		String[] paramValues = condStr.substring(getIndex + 3, fromIndex)
				.trim().split(",");
		String idField = null;
		for (int i = 0; i < paramValues.length; i++) {
			String[] eachParam = paramValues[i].split("\\.");
			if (i == 0) {
				idField = eachParam[1];
			}
			k = 0;
			for (int j = 0; j < eachParam.length; j++) {
				k = j + 1;
				if (k < eachParam.length) {
					if (eachParam[k].indexOf(DBConstants.DB_AS_BLANK) > 0) {
						eachParam[k] = eachParam[k].substring(eachParam[k]
								.indexOf(DBConstants.DB_AS_BLANK)
								+ DBConstants.DB_AS_BLANK.length(),
								eachParam[k].length());
					}
					columnOrder.put(eachParam[k], eachParam[k]);
				}
			}
		}
		data.getQueryData().setCondition(condStr);
		data.setColumnOrder(columnOrder);
		data.getQueryData().setIdField(idField);
	}

	@EventSubscriber(topic = "updateRecord")
	public IHICData updateData(IHICData requestData)
			throws DBComponentException {

		QueryData queryResponseData = null;
		hicData = requestData;
		IData data = requestData.getData();
		queryResponseData = data.getQueryData();

		String queryStr = data.getQueryData().getCondition();
		String responseXML = null;
		List fetchValue;
		
		if(requestData.getData().getFormPattern() != null && requestData.getData().getFormPattern().getFormValues() != null){
			queryParmValues = (ArrayList)requestData.getData().getFormPattern().getFormValues().get("QueryParamValues");
			if(queryParmValues != null){
				requestData.getData().getFormPattern().getFormValues().remove("QueryParamValues");
				//requestData.getData().getFormPattern().getFormValues().put("QueryParamValues", null);
			} 
		}
		/*
		 * this.getConnection(); dataHandler = this.getDataHandler();
		 */
		try {
			getConnection(getDBNameFromQuery(queryStr));
			dataHandler = this.getDataHandler();
			fetchValue = dataHandler.executeQueryList(queryStr);
			/*
			 * Following line commented. now call executeQueryList instead of
			 * excuteQuery method. changes by wasim , 20-May-2009.
			 */
			/* responseXML = dataHandler.executeQuery(queryStr); */
		} catch (DBComponentException exp) {

			throw new DBComponentException(
					"!!!!! Error occurred while executing the the action query "
							+ exp.getMessage());
		}

		return requestData;
	}

	//@EventSubscriber(topic = "sqlQuery")
	@EventSubscriber(topic = "getListObject")
	public IHICData getListObject(IHICData requestData)
			throws DBComponentException {
		DBUtilities dbUtilities = new DBUtilities();
		QueryData queryResponseData = null;
		hicData = requestData;
		IData data = requestData.getData();
		queryResponseData = data.getQueryData();
		logger.log(0, "[queryRequestData is]" + queryResponseData);
		String queryStr = data.getQueryData().getCondition();

		LinkedHashMap<String, LinkedHashMap<String, String>> colRecord = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		String responseXML = null;
		List<Object> fetchValue = null;
		boolean exception = false;
		try {
			String dbName = queryResponseData.getDBName();
			if(dbName == null || dbName.length() == 0){
				dbName = getDBNameFromQuery(queryStr);
			}
			getConnection(dbName);
			//this.setConfigAndDBClassLoader("neiswispicdb");
			dataHandler = this.getDataHandler();
			// fetchValue = dataHandler.executeQueryList(queryStr);
			fetchValue = (List) getList(queryStr);
			if (fetchValue != null) {
				queryResponseData.setListData(fetchValue);
				data.setQueryData(queryResponseData);
			}
			// getMetaData();
		}

		catch (DBComponentException exp) {
			exp.printStackTrace();
			/*
			 * throw new DBComponentException(
			 * "!!!!! Error occurred while executing the the action query " +
			 * exp.getMessage());
			 */
		}

		return requestData;
	}

	void getMetaData() {
		Object obj = null;
		for (Iterator iter = config.getImports().values().iterator(); iter
				.hasNext();) {
			obj = iter.next();

		}

		PersistentClass pc = config
				.getClassMapping("com.oxymedical.component.render.Patient");
		String clname = pc.getClassName();

		Iterator it = pc.getPropertyIterator();

		while (it.hasNext()) {
			Property next = (Property) it.next();
		}
	}

	public Object invoke() {
		return null;

	}

	public boolean isConnectionAvailable() {
		return isConnectionAvailable;
	}

	public void setConnectionAvailable(boolean isConnectionAvailable) {
		this.isConnectionAvailable = isConnectionAvailable;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public void clearFirstMaxResults() {
		setFirstResult(0);
		setMaxResult(0);
	}

	/**
	 * @return the config
	 */
	public Configuration getConfig() {
		return config;
	}

	public List getQueryResult(DBQuery dbq) throws DBComponentException {
		IQueryCreator qc = new FieldConvertedQueryCreator(
				new TableLinkedQueryCreator(new QueryCreator(this)));
		String query = qc.createQuery(dbq);
		return getList(query);
	}

	/**
	 * Sets config and DBClassLoader local vars based on database
	 * 
	 * @param databaseName
	 * @return - true / false on whether the local vars have been set or not.
	 */
	public boolean setConfigAndDBClassLoader(String databaseName) {
		// Even if databaseName is blank i.e. query does not contain
		// database name, we assume that there is only one data pattern
		// and for that data pattern, databaseOperation is already set.
		if ((null == databaseName) || ("".equals(databaseName)))
			return false;

		// Check if dbOpers contain databaseName key. This check is required for
		// all components that inject DBComponent and use it. For these cases,
		// there might not be any entry in dbOpers
		if (dbOpers.containsKey(databaseName)) {
			this.databaseOperation = dbOpers.get(databaseName);

			if (null != databaseOperation) {
				// Fetch Hibernate configuration and DBClassLoader object as
				// well.
				this.config = this.databaseOperation.getConfiguration();
				this.DBClassLoader = this.databaseOperation.getDbClassLoader();
			}
		}
		return true;
	}
	
	public DatabaseOperation getDatabaseOperation() {
		return databaseOperation;
	}

	public void setDatabaseOperation(DatabaseOperation databaseOperation) {
		this.databaseOperation = databaseOperation;
	}

	@EventSubscriber(topic = "setApplicationInDBComponent")
	public void setApplication(IHICData hicData){
		this.app = (Application)hicData.getData().getList().get(0);
	}

	public void getEibData(IHICData hicData) {
		// TODO Auto-generated method stub	
	}	
}
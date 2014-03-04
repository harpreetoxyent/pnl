package com.oxymedical.component.db;

import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;

import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.db.application.IDataHandler;
import com.oxymedical.component.db.application.query.Field;
import com.oxymedical.component.db.application.register.IRegisterWindow;
import com.oxymedical.component.db.application.tree.TreeNode;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.query.data.DBQuery;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.core.commonData.IApplication;
import com.oxymedical.core.commonData.IHICData;


/**
 * This interface states that DBComponent implements 
 * all methods of IDBComponent.
 * @author      Oxyent Medical
 * @version     1.0.0
 */


public interface IDBComponent
{

	public Object createObject(String className) throws DBComponentException;
	PersistentClass getPersistentClass(String className);
	public void addToTreeHash(TreeNode treeNode) throws DBComponentException;
	public IRegisterWindow getRegisterWindow();
	public void registerDBAndGenerateMappings() throws DBComponentException;
	public void createDatabaseResources(String connUserName, String connPassword, String serverAddress,String baseDirectory,String databaseName, Boolean createResources)  throws DBComponentException;
	public Object saveField(Hashtable<String,Hashtable<String,List<Field>>> databaseTableHash, String queryType) throws DBComponentException;
    public URLClassLoader loadResourcesJarInLoader(String jarPathFile, String jarFileName)throws DBComponentException;
	public void createDBConfiguration() throws DBComponentException;
	public void CreateConfigurationFromInputStream(InputStream	modulefileStream) throws DBComponentException;
	public void setUpDataConfiguration(String userName, String password, String serverURL, String packageName, String baseDirectoryAddress);
	public void setUpDataConfiguration(String userName, String password, String serverURL, String packageName, String baseDirectoryAddress, String jarName);
	public void createDatabaseResources(String connUserName, String connPassword, String serverAddress,String baseDirectory,String databaseName, Boolean createResources , String resourceName)  throws DBComponentException;
	public IDataHandler getDataHandler();
	public List executeHSQLQueryWithNameParameter(String hsql, ArrayList<NameQueryParameter> listParameter) throws DBComponentException;
	public Object getList(String hsql) throws DBComponentException;
	/*previously this method was returning boolean
	 * Now this method is returning the object which is saved so to save the information
	 * of object primary key in case if the object is refferenced by other objects.
	 * Done by pra
	 */
	public Object saveObject(Object obj) throws DBComponentException;
	public Integer executeDMLQuery(String hsql)  throws DBComponentException;
	public Integer executeDMLQueryWithNameParameter(String hsql, ArrayList<NameQueryParameter> listParameter) throws DBComponentException;
	public String getDAOByName(String databaseName, String dataTableName) throws DBComponentException;
	public List getDAOData(String databaseName, String dataTableName) throws DBComponentException;
	public String getDAOByName(LinkedHashMap<String,String> fieldHash, List tables, String condition) throws DBComponentException;
/*
 * This method has been removed from dbcomponent class
 * chnages by wasim, 21-May-2009
 * 
 */
	//public String stringQuery(String sql) throws DBComponentException;
	public List stringQueryList(String sql) throws DBComponentException;
	public String createTreeXml() throws DBComponentException;
	public Boolean deleteObject(Object obj) throws DBComponentException;
	public Configuration getConfig();
	public List getQueryResult(DBQuery dbq) throws DBComponentException;
	
	public IHICData getHicData();
	public void setHicData(IHICData hicData);
	public void getConnection(String dbName) throws DBComponentException;
	@EventSubscriber(topic = "createDBResourcesAndMappings")
	public void createDBResourcesAndMappings(IHICData hicData) throws DBComponentException;
	@EventSubscriber(topic = "setApplicationInDBComponent")
	public void setApplication(IHICData hicData);
	public IHICData getListData(IHICData requestData) throws DBComponentException;
}

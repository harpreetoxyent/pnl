package com.oxymedical.component.db.application;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.application.query.Field;
import com.oxymedical.component.db.application.register.IRegisterWindow;
import com.oxymedical.component.db.exception.DBComponentException;

/**
 * This interface states that DataHandler class implements 
 * all methods of IDataHandler.
 * @author      Oxyent Medical
 * @version     1.0.0
 */
public interface IDataHandler 
{
	//public String executeQuery(String sql) throws DBComponentException;
	public List executeQueryList(String sql) throws DBComponentException;
	public String getListXML(String listId, String baseWindow) throws DBComponentException;
	public List getGridList(String listId, String baseWindow) throws DBComponentException;
	public String getListXML(LinkedHashMap<String,String> fieldLabelHash, List tableList, String condition)  throws DBComponentException;
	public void connectionSettings(String userName, String password, String serverAdd, String appName, String baseDir);
	public void setDbComponent(IDBComponent dbComponent);
	public Object save(String baseWindow, Hashtable requestTable ,String queryTupe) throws DBComponentException;
	public void addChildLevel(String levelnumber, String tableList, String displayfield, String valuefield, String parentNodelevel, String conditionField, String  conditionValue) throws DBComponentException;
	public void addLevel(String levelnumber,String tableList, String displayfield,String valuefield, String conditionstring) throws DBComponentException;
	public String showTree(String tree) throws DBComponentException;
	public String advanceHours(String str_date , int hours) throws DBComponentException;
	public Hashtable<String,Hashtable<String,List<Field>>> getHashDataForApplication(/*IRegisterWindow registerWin,*/String applicationName, String baseWindow, Hashtable requestTable ,String queryType) throws DBComponentException;
	public Hashtable getFieldsFromHashData(Hashtable databaseHashData);
}

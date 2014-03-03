package com.oxymedical.core.commonData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.oxymedical.core.communication.ICommunicationData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.userdata.IUserPattern;

public class Data implements IData, Serializable
{
	IDataPattern dataPattern =null;
	String userId;
	String status;
	IFormPattern formPattern = null;
	Set<IUserPattern> userPatterns = null;
	private String methodName = null;
	private String invokeComponentId;
	private String invokeComponentClass;
	private Object returnedData = null;
	private QueryData queryData = null;
	private LinkedHashMap columnOrder = null;
	private List list = null;
	private String sqlQuery = null;
	private Object rawData = null;
	
	private IUserInfo userInfo = null;
	
	private IGroup groupInfo=null;
	
	private ICommunicationData commData = null;
	private String _returnMessage = null;
	private IWorkflowPattern _workflowPattern = null;
	private String groupId;
	private String groupName;
	
	public IDataPattern getDataPattern()
	{
		return this.dataPattern;
	}

	public String getStatus()
	{
		return this.status;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setDataPattern(IDataPattern dataPattern)
	{
		this.dataPattern = dataPattern;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public IFormPattern getFormPattern()
	{
		return formPattern;
	}

	public void setFormPattern(IFormPattern formPattern)
	{
		this.formPattern = formPattern;
	}

	public void addUserPatterns(IUserPattern userPattern)
	{
		if (userPatterns == null) userPatterns = new HashSet<IUserPattern>();
		userPatterns.add(userPattern);
	}

	public Set<IUserPattern> getUserPatterns()
	{
		return this.userPatterns;
	}

	public void setUserPatterns(Set<IUserPattern> userPattern)
	{
		this.userPatterns = userPattern;
	}
	
	public String getUserPatternString()
	{
		if (userPatterns == null) return null;
		
		final String sep = "";
		List<String> upList = new ArrayList<String>();
		Iterator<IUserPattern> userPatternIter = this.userPatterns.iterator();
		while (userPatternIter.hasNext())
		{
			IUserPattern userPattern = (IUserPattern) userPatternIter.next();
			upList.add(userPattern.getUserPatternId());
		}
		Collections.sort(upList);
		
		String retVal = "";
		for (int i=0; i<upList.size(); i++)
		{
			retVal = retVal + (retVal.equals("") ? "" : sep) + upList.get(i);
		}
		return retVal;
	}


	public String getMethodName()
	{
		return methodName;
	}

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	public String getInvokeComponentId()
	{
		return invokeComponentId;
	}

	public void setInvokeComponentId(String invokeComponentId)
	{
		this.invokeComponentId = invokeComponentId;
	}

	public String getInvokeComponentClass()
	{
		return invokeComponentClass;
	}

	public void setInvokeComponentClass(String invokeComponentClass)
	{
		this.invokeComponentClass = invokeComponentClass;
	}

	public Object getReturnedData()
	{
		return this.returnedData;
	}

	public void setReturnedData(Object returnedData)
	{
		this.returnedData = returnedData;
	}

	/**
	 * @return the queryData
	 */
	public QueryData getQueryData()
	{
		return queryData;
	}

	/**
	 * @param queryData the queryData to set
	 */
	public void setQueryData(QueryData queryData)
	{
		this.queryData = queryData;
	}

	/**
	 * @return the columnOrder
	 */
	public LinkedHashMap getColumnOrder()
	{
		return columnOrder;
	}

	/**
	 * @param columnOrder the columnOrder to set
	 */
	public void setColumnOrder(LinkedHashMap columnOrder)
	{
		this.columnOrder = columnOrder;
	}

	/**
	 * @return the list
	 */
	public List getList()
	{
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List list)
	{
		this.list = list;
	}

	/**
	 * @return the sqlQuery
	 */
	public String getSqlQuery()
	{
		return sqlQuery;
	}

	/**
	 * @param sqlQuery the sqlQuery to set
	 */
	public void setSqlQuery(String sqlQuery)
	{
		this.sqlQuery = sqlQuery;
	}

	public Object getRawData()
	{
		return this.rawData;
	}

	public void setRawData(Object rawData)
	{
		this.rawData = rawData;
	}

	/**
	 * @return the userInfo
	 */
	public IUserInfo getUserInfo() 
	{
		return userInfo;
	}

	
	public IGroup getGroup() {
		return groupInfo;
	}

	public void setGroup(Group groupInfo) {
		this.groupInfo = groupInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(IUserInfo userInfo) 
	{
		this.userInfo = userInfo;
	}

	@Override
	public ICommunicationData getCommunicationInfo()
	{
		return this.commData;
	}

	@Override
	public void setCommunicationInfo(ICommunicationData commInfo)
	{
		this.commData = commInfo;
	}

	/**
	 * Any error message returned by called component for showing on UI.
	 */
	@Override
	public String getReturnMessage()
	{
		return this._returnMessage;
	}
	@Override
	public void setReturnMessage(String returnMessage)
	{
		this._returnMessage = returnMessage;
	}
	
	
	/**
	 * Information about workflow pattern
	 */
	public IWorkflowPattern getWorkflowPattern()
	{
		return this._workflowPattern;
	}

	public void setWorkflowPattern(IWorkflowPattern workflowPattern)
	{
		this._workflowPattern = workflowPattern;
	}
	
	public Object clone()
	{
		// TODO: Not all object cloning is completed
		IData data = new Data();
		data.setDataPattern(dataPattern);
		data.setUserId(userId);
		data.setStatus(status);
		data.setFormPattern(formPattern.clone());
		data.setUserPatterns(userPatterns);
		data.setMethodName(methodName);
		data.setInvokeComponentId(invokeComponentId);
		data.setInvokeComponentClass(invokeComponentClass);
		data.setReturnedData(returnedData);
		data.setQueryData(queryData);
		data.setColumnOrder(columnOrder);
		data.setList(list);
		data.setSqlQuery(sqlQuery);
		data.setRawData(rawData);
		data.setUserInfo(userInfo);
		data.setCommunicationInfo(commData);
		data.setReturnMessage(_returnMessage);
		data.setWorkflowPattern(_workflowPattern);
		data.setGroupInfo(groupInfo);
		data.setGroupId(groupId);
		data.setGroupName(groupName);
		return data;
	}

	@Override
	public String getGroupId() {
		
		return this.groupId;
	}

	@Override
	public String getGroupName() {

		return this.groupName;
	}

	@Override
	public void setGroupId(String groupId) {
		this.groupId=groupId;
		
	}

	@Override
	public void setGroupName(String groupName) {
		this.groupName=groupName;
		
	}

	@Override
	public IGroup getGroupInfo() {
		
		return this.groupInfo;
	}

	@Override
	public void setGroupInfo(IGroup groupInfo) {
		this.groupInfo=groupInfo;
		
	}

	

	
	
}

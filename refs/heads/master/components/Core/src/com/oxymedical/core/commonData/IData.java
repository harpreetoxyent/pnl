package com.oxymedical.core.commonData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.oxymedical.core.communication.ICommunicationData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.userdata.IUserPattern;


public interface IData 
{
	/**
	 * Used by Workflow component for DataPattern
	 */
	public IDataPattern getDataPattern();
	public void setDataPattern(IDataPattern dataPattern);

	/**
	 * Used by Workflow component for status (Business Status)
	 */
	public String getStatus();
	public void setStatus(String status);
	public IWorkflowPattern getWorkflowPattern();
	public void setWorkflowPattern(IWorkflowPattern workflowPattern);
	
	
	public String getReturnMessage();
	public void setReturnMessage(String returnMessage);

	/**
	 * Used by Workflow component for form info and form data
	 */
	public IFormPattern getFormPattern();
	public void setFormPattern(IFormPattern formPattern);

	/**
	 * Used by Workflow component for UserPattern
	 */
	public String getUserId();
	public void setUserId(String userId);
	
	
	public String getGroupId();
	public void setGroupId(String groupId);
	
	public String getGroupName();
	public void setGroupName(String groupName);
	/**
	 * Used by components for publishing and Subscribing Topic
	 */
	public String getMethodName();
	public void setMethodName(String methodname);
	
	/**
	 * Add getter setter for componentId
	 */
	public void setInvokeComponentId(String invokeComponentId);
	public String getInvokeComponentId();
	
	/**
	 * Add getter setter for className
	 */
	public void setInvokeComponentClass(String invokeComponentClass);
	public String getInvokeComponentClass();
	
	/**
	 * Used by Workflow component for UserPattern
	 */
	public Set<IUserPattern> getUserPatterns();
	public void setUserPatterns(Set<IUserPattern> userPattern);
	public void addUserPatterns(IUserPattern userPattern);
	public String getUserPatternString();

	/**
	 * Used by uilibrary.zs for showing output data
	 */
	public Object getReturnedData();
	public void setReturnedData(Object returnedData);

	
	public QueryData getQueryData();
	public void setQueryData(QueryData queryData);

	/**
	 * @return the columnOrder
	 */
	public LinkedHashMap getColumnOrder();
	public void setColumnOrder(LinkedHashMap columnOrder);

	/**
	 * @return the list
	 */
	public List getList();
	public void setList(List list);

	/**
	 * @return the sqlQuery
	 */
	public String getSqlQuery();
	public void setSqlQuery(String sqlQuery);

	/**
	 * Option for transferring raw data from Workflow component to other components.
	 */
	public Object getRawData();
	public void setRawData(Object rawData);

	/**
	 * this is used by userAdmin
	 */
	public IUserInfo getUserInfo();
	public void setUserInfo(IUserInfo userInfo);
	
	public IGroup getGroupInfo();
	public void setGroupInfo(IGroup groupInfo);
	/**
	 * this is used by Communication component
	 */
	public ICommunicationData getCommunicationInfo();
	public void setCommunicationInfo(ICommunicationData commInfo);
	
	
	//public IGroup getGroupInfo();
	//public void setGroupInfo(IGroup groupInfo);
	
}

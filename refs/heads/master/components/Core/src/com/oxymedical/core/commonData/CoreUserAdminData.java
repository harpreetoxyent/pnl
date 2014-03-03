package com.oxymedical.core.commonData;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.communication.ICommunicationData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.userdata.IUserPattern;

public class CoreUserAdminData implements ICoreUserAdmindata ,Serializable
{
	String companyName = null;
	CoreOrganizationStructure orgStructure = null;

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the orgStructure
	 */
	public CoreOrganizationStructure getOrgStructure() {
		return orgStructure;
	}
	/**
	 * @param orgStructure the orgStructure to set
	 */
	public void setOrgStructure(CoreOrganizationStructure orgStructure) {
		this.orgStructure = orgStructure;
	}
	
	public IDataPattern getDataPattern()		{	return null;	}
	public IDataUnit getDataUnit()				{	return null;	}
	public String getStatus()					{	return null;	}
	public String getUserId()					{	return null;	}
	public IFormPattern getFormPattern()		{	return null;	}
	public Set<IUserPattern> getUserPatterns()	{	return null;	}
	public String getMethodName()				{	return null;	}
	public String getInvokeComponentClass()		{	return null;	}
	public String getInvokeComponentId()		{	return null;	}
	public Object getReturnedData()				{	return null;	}
	public void setData(IDataUnit data)							{	}
	public void setDataPattern(IDataPattern dataPattern)		{	}
	public void setStatus(String status)						{	}
	public void setUserId(String userId)						{	}
	public void setFormPattern(IFormPattern formPattern)		{	}
	public void addUserPatterns(IUserPattern userPattern)		{	}
	public void setUserPatterns(Set<IUserPattern> userPattern)	{	}
	public void setMethodName(String methodname)				{	}
	public void setInvokeComponentClass(String invokeComponentClass){}
	public void setInvokeComponentId(String invokeComponentId)	{	}
	public void setReturnedData(Object returnedData)			{	}

	public LinkedHashMap getColumnOrder()	{	return null;	}
	public List getList()	{	return null;	}
	public QueryData getQueryData()	{	return null;	}
	public String getSqlQuery()	{	return null;	}
	public void setColumnOrder(LinkedHashMap columnOrder)	{	}
	public void setList(List list)	{	}
	public void setQueryData(QueryData queryData)	{	}
	public void setSqlQuery(String sqlQuery)	{	}

	public String getUserPatternString()		{	return null;	}
	public Object getRawData()	{		return null;	}
	public void setRawData(Object rawData)	{	}

	@Override public IUserInfo getUserInfo() { return null; }

	@Override public void setUserInfo(IUserInfo userCreds) { }

	@Override public ICommunicationData getCommunicationInfo() { return null; }
	@Override public void setCommunicationInfo(ICommunicationData commInfo) { }

	@Override public String getReturnMessage() { return null; }
	@Override public void setReturnMessage(String returnMessage) { }

	@Override public IWorkflowPattern getWorkflowPattern() { return null; }
	@Override public void setWorkflowPattern(IWorkflowPattern workflowPattern) {  }
	@Override
	public String getGroupId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getGroupName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setGroupId(String groupId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGroupName(String groupName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public IGroup getGroupInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setGroupInfo(IGroup groupInfo) {
		// TODO Auto-generated method stub
		
	}	
}

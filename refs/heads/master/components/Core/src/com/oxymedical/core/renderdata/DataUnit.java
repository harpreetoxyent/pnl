package com.oxymedical.core.renderdata;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.IUserInfo;
import com.oxymedical.core.commonData.UserInfo;
import com.oxymedical.core.communication.ICommunicationData;
import com.oxymedical.core.ldapData.ILdapData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.userdata.IUserPattern;

/**
 * This class defines the data-structure to be used while processing rendering
 * data
 * 
 * @author Oxyent Medical
 * @version 1.0.0
 * databasename and table name attribute deleted
 *	Changes by wasim, 26-May-2009
 */
public class DataUnit extends CommonRenderData implements IDataUnit
{
	private boolean isTrue = false;
	private String parentId = null;
	private List list = null;
	private String sqlQuery = null;
	private QueryData queryData = null;
	private LinkedHashMap columnOrder = null;
	private Object dataObject = null;
	private String dataPatternId = null;
	private Object metaData = null;
	private ISource source = null;
	private String invokeComponentId;
	private String invokeComponentClass;
	String userId;
	Set<IUserPattern> userPatterns = null;
	String uniqueId;
	private String status = null;
	private ICommunicationData commData;
	private ILdapData ldapData;
	private String sessionId;
	
	/*
	 * Iusercredential name changed as IuserInfo
	 */
	private IUserInfo userInfo = null;
	private String currentApplicationName;

	
	public ISource getSource()
	{
		return source;
	}

	public void setSource(ISource source)
	{
		this.source = source;
	}

	public Object getMetaData()
	{
		return metaData;
	}

	public void setMetaData(Object metaData)
	{
		this.metaData = metaData;
	}

	/**
	 * @return the dataPatternId
	 */
	public String getDataPatternId()
	{
		return dataPatternId;
	}

	/**
	 * @param dataPatternId
	 *            the dataPatternId to set
	 */
	public void setDataPatternId(String dataPatternId)
	{
		this.dataPatternId = dataPatternId;
	}

	public Object getDataObject()
	{
		return dataObject;
	}

	public void setDataObject(Object dataObject)
	{
		this.dataObject = dataObject;
	}

	/**
	 * @return the isTrue
	 */
	public boolean isTrue()
	{
		return isTrue;
	}

	/**
	 * @param isTrue
	 *            the isTrue to set
	 */
	public void setTrue(boolean isTrue)
	{
		this.isTrue = isTrue;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public List getList()
	{
		return list;
	}

	public void setList(List list)
	{
		// this.list = new ArrayList();
		this.list = list;
	}

	public String getSqlQuery()
	{
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery)
	{
		this.sqlQuery = sqlQuery;
	}

	public QueryData getQueryData()
	{
		return queryData;
	}

	public void setQueryData(QueryData queryData)
	{
		this.queryData = queryData;
	}

	public LinkedHashMap getColumnOrder()
	{
		return columnOrder;
	}

	public void setColumnOrder(LinkedHashMap columnOrder)
	{
		this.columnOrder = columnOrder;
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

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Set<IUserPattern> getUserPatterns()
	{
		return userPatterns;
	}

	public void setUserPatterns(Set<IUserPattern> userPatterns)
	{
		this.userPatterns = userPatterns;
	}

	public String getUniqueId()
	{
		return uniqueId;
	}

	public void setUniqueId(String uniqueId)
	{
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * @return the userCredentials
	 */
	

	public String getCurrentApplicationName()
	{
		return currentApplicationName;
	}

	public void setCurrentApplicationName(String currentApplicationName)
	{
		this.currentApplicationName = currentApplicationName;
	}
	
	// userCredential class changed.
	
	
	/**
	 * @return the userInfo
	 */
	public IUserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(IUserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
	public ICommunicationData getCommunicationData()
	{
		return this.commData;
	}
	
	public void setCommunicationData(ICommunicationData commData)
	{
		this.commData = commData;
	}

	// Session Id attribute added
	
	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public ILdapData getLdapData() {
		return this.ldapData;
	}

	@Override
	public void setLdapData(ILdapData ldapData) {
		this.ldapData = ldapData;
	}
	
}

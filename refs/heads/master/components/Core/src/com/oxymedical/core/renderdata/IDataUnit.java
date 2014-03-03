package com.oxymedical.core.renderdata;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.oxymedical.core.commonData.ISource;
import com.oxymedical.core.commonData.IUserInfo;
import com.oxymedical.core.communication.ICommunicationData;
import com.oxymedical.core.ldapData.ILdapData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.userdata.IUserPattern;
/**
 *  This class defines the data-structure to be used while processing rendering data 
 *   
 * @author Oxyent Medical
 * @version 1.0.0
 */
public interface IDataUnit extends ICommonRenderData
{
	public Object getDataObject();
	public void setDataObject(Object dataObject);

	/**
	 * @return the isTrue
	 */
	public boolean isTrue();
	/**
	 * @param isTrue the isTrue to set
	 */
	public void setTrue(boolean isTrue);

	/**
	 * @return the parentId
	 */
	public String getParentId();
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId);
	/**
	 * @return the dataPatternId
	 */
	public String getDataPatternId();
	/**
	 * @param dataPatternId the dataPatternId to set
	 */
	public void setDataPatternId(String dataPatternId);
	public List getList();
	public void setList(List list);
	public String getSqlQuery();
	public void setSqlQuery(String sqlQuery);
	public QueryData getQueryData();
	public void setQueryData(QueryData queryData);
	public LinkedHashMap getColumnOrder();
	public void setColumnOrder(LinkedHashMap columnOrder);
	
	
	
	/**
	 * Add getter setters for meta data
	 */
	public void setMetaData(Object metaData);
	public Object getMetaData();
	
	/**
	 * Add getter setter for Source 
	 */
	public void setSource(ISource source);
	public ISource getSource();
	
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
	 * Add getter setter for UniqueId
	 */
	public void setUniqueId(String uniqueId);
	public String getUniqueId();
	
	/**
	 * Add getter setter for userId
	 */
	public void setUserId(String userId);
	public String getUserId();
	
	/**
	 * Used by Workflow component for UserPattern
	 */
	public Set<IUserPattern> getUserPatterns();
	public void setUserPatterns(Set<IUserPattern> userPattern);

	/**
	 * getter & setter for status to be used by IHICData
	 */
	public String getStatus();
	public void setStatus(String status);

	/**
	 * @param userInfo the userCredentials to set
	 */
	public IUserInfo getUserInfo();
	public void setUserInfo(IUserInfo userInfo);	
	
	public String getCurrentApplicationName();
	public void setCurrentApplicationName(String currentApplication);
	
	public ICommunicationData getCommunicationData();
	public void setCommunicationData(ICommunicationData commData);
	
	public ILdapData getLdapData();
	public void setLdapData(ILdapData ldapData);
	
	// Session id attribute added in data unit
	public String getSessionId();
	public void setSessionId(String sessionId);
	
}

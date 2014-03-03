package com.oxymedical.core.commonData;

import java.util.HashMap;
import java.util.LinkedList;

import com.oxymedical.core.ldapData.ILdapData;

public interface IHICData
{	
	public IData getData();
	public void setData(IData data);

	/**
	 * Used by ProxyInvocationHandler class
	 */
	public IDataType getDataType();
	public void setDataType(IDataType dataType);

	/**
	 * Used by ProxyInvocationHandler class
	 */
	public IMetaData getMetaData();
	public void setMetaData(IMetaData metaData);
	
	
	public LinkedList<ISource> getSrcHistoryList();
	public void setSrcHistoryList(LinkedList<ISource> source) ;
	public String getUniqueID();
	public void setUniqueID(String uniqueID);

	
	public ILdapData getLdapData();
	public void setLdapData(ILdapData ldapData);
	public Object clone();
	
	/**
	 * Used by HICFrameworkServlet class
	 */
	public IDataBase getDatabase();
	public void setDatabase(IDataBase database);
	
	public IApplication getApplication();
	public void setApplication(IApplication application);
	
	/**
	 * For fetch information about certain data in Hashmap
	 * @return
	 */
	public HashMap<String, Object> getDataObjectDetails();
	public String toString();
	public boolean equals(IHICData hicData);
	public boolean subsetOf(IHICData hicData);
	
	// Session Id attribute added
	public String getSessionId();
	public void setSessionId(String sessionId);
	
}

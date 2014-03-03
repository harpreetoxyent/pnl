package com.oxymedical.core.ldapData;

import java.util.Hashtable;

import org.dom4j.Document;

import com.oxymedical.core.commonData.IData;

/*
 * 
 */
public interface ILdapData{
	public Document getLdapDoc();
	public void setLdapDoc(Document ldapDoc);
	public Hashtable<String, Object> getAttributes();
	public void setAttributes(Hashtable<String, Object> attributes);
	public void addAttribute(String key, Object value);
	
	//for connection
	String getUidSuffix();
	public void setUidSuffix(String uidSuffix);
	public String getUserName();
	public void setUserName(String userName);
	public String getPassword();
	public void setPassword(String password);
	public String getServerUrl();
	public void setServerUrl(String serverUrl);
	public String getPort();
	public void setPort(String port);
	public boolean getUseSSL();
	public void setUseSSL(boolean useSSL);

	//for search
	public String getSearchBase();
	public void setSearchBase(String searchBase);
	public int getSearchScope();
	public void setSearchScope(int searchScope);
	public String getSearchFilter();
	public void setSearchFilter(String searchFilter);
}

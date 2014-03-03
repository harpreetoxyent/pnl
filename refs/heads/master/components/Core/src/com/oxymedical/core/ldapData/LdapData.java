package com.oxymedical.core.ldapData;

import java.util.Hashtable;
import org.dom4j.Document;

public class LdapData implements ILdapData 
{
	private Document ldapDoc = null;
	private String uidSuffix;
	private String userName;
	private String password;
	private String serverUrl;
	private String port;
	private boolean useSSL = false;
	private String searchBase;
	private int searchScope = 2;
	private String searchFilter;
	private Hashtable<String, Object> attributes;
	
	
	public String getUidSuffix() {
		return uidSuffix;
	}
	public void setUidSuffix(String uidSuffix) {
		this.uidSuffix = uidSuffix;
	}
	public Document getLdapDoc() {
		return ldapDoc;
	}
	public void setLdapDoc(Document ldapDoc) {
		this.ldapDoc = ldapDoc;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public boolean getUseSSL() {
		return useSSL;
	}
	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}
	public String getSearchBase() {
		return searchBase;
	}
	public void setSearchBase(String searchBase) {
		this.searchBase = searchBase;
	}
	public int getSearchScope() {
		return searchScope;
	}
	public void setSearchScope(int searchScope) {
		this.searchScope = searchScope;
	}
	public String getSearchFilter() {
		return searchFilter;
	}
	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}
	public Hashtable<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Hashtable<String, Object> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(String key, Object value){
		if(this.attributes == null){
			this.attributes = new Hashtable<String, Object>();
		}
		this.attributes.put(key, value);
	}
}

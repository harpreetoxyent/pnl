/**
 * 
 */
package com.oxymedical.core.constants;

import java.util.ArrayList;

import com.oxymedical.core.propertyUtil.PropertyUtil;

/**
 * @author wkh
 * 
 */
public class CoreConstants
{
	//for ldap
	public static final String UserAuthenticatedInLDAP_KEY = "UserAuthenticatedInLDAP";
	public static final String LDAP_SEARCH_REQ_PROPS_KEY = "LDAP_REQ_PROPS";
	public static final ArrayList<String> LDAP_SEARCH_REQ_PROPS_LIST = new ArrayList<String>();
	static {
		LDAP_SEARCH_REQ_PROPS_LIST.add("givenName");
		LDAP_SEARCH_REQ_PROPS_LIST.add("sn");
		LDAP_SEARCH_REQ_PROPS_LIST.add("sAMAccountName");
		LDAP_SEARCH_REQ_PROPS_LIST.add("name");
		LDAP_SEARCH_REQ_PROPS_LIST.add("userPrincipalName");
		LDAP_SEARCH_REQ_PROPS_LIST.add("mail");
		LDAP_SEARCH_REQ_PROPS_LIST.add("description");
		LDAP_SEARCH_REQ_PROPS_LIST.add("distinguishedName");
	}
	
	public static final String sourceURL = "sourceURL";
	public static final String addUserURL = "addUserURL";
	public static final String appConfigInfo = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME") + "/config/applicationInfomation.xml";
	public static final String appInfo = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME") + "/applications/j2ee-modules/";
	public static final String applicationName = "ApplicationName";
	public static final String appFolder = "applicationInfo";
	public static final String sourcedir = "sourcedir";
	public static final String serverIPAddress = "serverIPAddress";
	public static final String serverDirectory = "serverDirectory";
	public static final String serverLibDirectory = "serverLibDirectory";
	public static final String deploy = "deploy";
	public static final String zk = "ZK";
	public static final String DATABASESETTING = "/data/datasettings.xml";
	public static final String APP_EXT = ".esp";
	public static final String APPLICATION_INFO_FILENAME = "applicationinformation.xml";
	public static final String renderOption = "renderOption";

	public static final String DATAOBJECT_FORM_PATTERN = "FP";
	public static final String DATAOBJECT_DATA_PATTERN = "DP";
	public static final String DATAOBJECT_USER_PATTERN = "UP";
	public static final String DATAOBJECT_UNIQUE_ID = "UNIQUEID";
	public static final String DATAOBJECT_USER_ID = "USERID";
	public static final String DATAOBJECT_STATUS = "STATUS";
	public static final String DATAOBJECT_WORKFLOW_PATTERN = "WP";
	public static final String DATAOBJECT_WORKFLOW_PATTERN_NODE = "WPNODE";
	public static final String DATAOBJECT_FORM_VALUES = "FV";
	public static final String DATAOBJECT_EVENT_STATUS = "EVENTSTATUS";
	
	public static final String DATAOBJECT_ERROR_STATUS = "error";

}

package com.oxymedical.component.ldap.constants;

import java.util.ArrayList;

/**
 * This class has all the constants used in the LDAP Component
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class LDAPConstants {
	
	public static final String UID_SUFFIX_KEY = "LDAP_UID_SUFFIX";
	public static final String PORT_FOR_AUTH_KEY = "LDAP_PORT_FOR_AUTH";
	public static final String USE_SSL_FOR_AUTH_KEY = "LDAP_USE_SSL_FOR_AUTH";
	public static final String USER_ID_KEY = "LDAP_USERNAME";
	public static final String PASSWORD_KEY = "LDAP_PASSWORD";
	//public static final String UserAuthenticatedInLDAP = "UserAuthenticatedInLDAP";
	public static final String SEARCH_BASE_KEY = "LDAP_SEARCH_BASE";
	public static final String SEARCH_FILTER_KEY = "LDAP_SEARCH_FILTER";
	public static final String SEARCH_SCOPE_KEY = "LDAP_SEARCH_SCOPE";
	
	public static final String LDAP_CRT_PATH = "LDAP_CRT_PATH";
	public static final String LDAP_USERNAME = "uid=admin,ou=system";
	public static final String LDAP_PASSWORD = "secret";
	public static final String LDAP_CRT_FILENAME = "/oxyentssl.keystore";
	public static final String SERVER_ADDRESS_KEY = "LDAP_SERVER_ADDRESS";
	public static final String TRUST_STORE = "javax.net.ssl.trustStore";
	public static final String UTF8 = "UTF8";
	public static final String OBJECT_CLASS = "(objectClass=*)";
	
	public static final String OBJECTCLASS = "objectclass";
	public static final String COMMONNAME = "cn";
	public static final String GIVENNAME = "givenname";
	public static final String SURNAME = "sn";
	public static final String TELNUM = "telephonenumber";
	public static final String MAIL = "mail";
	public static final String USERPASS = "userpassword";
	public static final String DESCRIPTION = "description";
	public static final String DESIGNATION ="desig";
    public static final String OU = "ou";
    public static final String UID = "uid";
    public static final String UNIQUEMEMBER = "uniqueMember";
    public static final String SYSTEM = "system";
	public static final String ORGANIZATIONALUNIT = "organizationalUnit";
	public static final String INETORGPERSON = "inetOrgPerson";
	public static final String GROUPOFUNIQUENAMES = "groupOfUniqueNames";
	public static final String USER_ROLE = "userrole";
	
	// type of entry to be added
	public static final String TYPE_UNIT = "unit";
	public static final String TYPE_USER = "user";
	public static final String TYPE_ROLE = "role";
	
	public static final String SEARCH_FILTER = "(objectClass=*)";
	public static final String SEARCH_BASE = "DC=spil,DC=com";
	
	// Entity constants
	public static final String ENTITY = "entity";
	public static final String ENTITY_TYPE = "type";
	public static final String ENTITY_NAME = "name";
	public static final String ENTITY_LASTNAME = "lastname";
	public static final String ENTITY_DISPLAYNAME = "displayname";
	public static final String ENTITY_COMMONNAME = "commonname";
	public static final String ENTITY_DESCRIPTION = "description";
	public static final String ENTITY_MAIL = "mail";
	public static final String ENTITY_TELEPHONENUMBER = "telephonenumber";
	public static final String ENTITY_MOBILE = "mobile";
	public static final String ENTITY_DESIGNATION = "designation";
	public static final String ENTITY_PASSWORD = "password";
	public static final String ENTITY_ENTITYPARENT = "parent";
	public static final String ENTITY_UID = "userId";
	public static final String ENTITY_UNIQUE_MEMBER = "uniqueMember";
	
	// Search XML Constants
	public static final String START_VALUE_TAG = "<value><![CDATA[";
	public static final String END_VALUE_TAG = "]]></value>";
	public static final String START_ROOT_TAG = "<root>";
	public static final String END_ROOT_TAG = "</root>";
	public static final String START_PARENT_TAG = "<parent>";
	public static final String START_ENTITY_TAG = "<entity>";
	public static final String START_NAME_TAG = "<name>";
	public static final String END_PARENT_TAG = "</parent>";
	public static final String END_ENTITY_TAG = "</entity>";
	public static final String END_NAME_TAG = "</name>";
	public static final String OU_EQUAL = "ou=";
	public static final String START_TAG = "<";
	public static final String END_TAG = ">";
	public static final String START_TAG_SLACE = "</";
	
	// Role for the user
	
	public static final String ADMINISTRATOR = "admin";
	public static final String MANAGER = "manager";
	public static final String USER = "user";
	
	//Role XML Constants
	
	public static final String ADMIN_XML = "/de/stoneone/component/ldap/Role/AdminRole.xml";
	public static final String MANAGER_XML = "/de/stoneone/component/ldap/Role/ManagerRole.xml";
	public static final String USER_XML = "/de/stoneone/component/ldap/Role/UserRole.xml";
	
}

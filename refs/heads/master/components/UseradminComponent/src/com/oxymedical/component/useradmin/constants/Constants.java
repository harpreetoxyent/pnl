package com.oxymedical.component.useradmin.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.oxymedical.core.propertyUtil.PropertyUtil;

public class Constants 
{
	
	public static final String AuthenticateUserInLDAP = "AuthenticateUserInLDAP";
	public static final String UserPresentInUserAdmin_KEY = "UserPresentInUserAdmin";
	public static final String HibernatePath = "/com/oxymedical/component/useradmin/model/hibernate.cfg.xml";
	//public static final String USER_NAME = "root";
	//public static final String PASSWORD = "1234";
	public static final String SERVER_NAME = "jdbc:mysql://localhost:3306/useradmin";
	public static final String DBNAME = "useradmin";
	public static final String PACKAGE_NAME = "com.oxymedical.component.useradmin.model"; // com.oxymedical.component.useradmin.model";
	public static final String BASEDIR = PropertyUtil.setUpProperties("GLASSFISH_HOME") + "\\domains\\domain1\\lib\\ext";
	//public static final String JAR_NAME = "resources_ua";
	public static final String DEFAULT_PARENT_GROUP_ID = "-1";
	public static final String DEFAULT_PARENT_ORGANIZATION_ID = "-1";
	public static final String PRIVATE = "PRI.";
	public static final String PUBLIC = "PUB.";
	public static final String name = "name";
	public static final String application = "application";
	public static final String groupId = "groupId";
	public static final String companyId = "companyId";
	public static final String countryId = "countryId";
	public static final String countryName ="name";
	public static final String regionName ="name";
	public static final String firstName = "firstName";
	public static final String middleName = "middleName";
	public static final String lastName = "lastName";
	public static final String emailId = "emailAddress";
	public static final String birthday = "birthday";
	public static final String male = "male";
	public static final String regionId = "regionId";
	public static final String userPatternId = "userPatternId";
	public static final String userId = "userId";
	public static final String groupName = "groupName";
	public static final String emailIdUser = "emaildIdUser";
	public static final String password = "password";
	public static final String password_in_db = "password_";
	public static final String containerId = "containerId";
	public static final String userName = "userName";
	public static final String organizationId = "organizationId";
	public static final String organizationId_in_db = "name";
	public static final String parentOrganizationId = "parentOrganizationId";
	public static final String roleId = "roleId";
	public static final String  roleName = "name";
	public static final String rightId = "rightId";
	public static final String className = "className";
	public static final String classPk = "classPk";
	public static final String  SPACE = " ";
	public static final String  DOT = ".";
	public static final String BLANK = "";
	public static final String  GREETING = "Welcome";
	public static final String DEFAULT_PARENT_CONTACT_ID ="-1";
	public static final String GIP_TIME_USER= "/de/stoneone/component/useradmin/conf/UserCont.xml";
	public static final String USER_ADMIN_USER = "/com/oxymedical/component/useradmin/conf/roles.xml";
	public static final String USER_ADMIN_USER1 = "/de/stoneone/component/useradmin/conf/Users1.xml";
	public static final String USER_ITEM ="user";
	public static final String GROUP_ITEM = "group";
	public static final String ORG_ITEM = "organization";
	
	public static final String USERPAT_DFP_ATT = "defaultFormPattern";
	
	public static final String DATABASESETTING = "data/datasettings.xml";
	
	public static final String USER_AUTH_VALID_STATUS = "UserAuthenticated";
	public static final String USER_AUTH_INVALID_STATUS = "Invalid";
	public static final String USERADMIN_DB_NAME = "useradmin";
	// Constants added by pra for creating the XMLs  file and for newly added fields file on 22-May-2009
	public static final String XML_RIGHTS = "Rights";
	public static final String XML_RIGHT = "Right";
	public static final String XML_RIGHTID = "rightid";
	public static final String XML_COMPANYID = "CompanyId";
	public static final String XML_ROLES = "Roles";
	public static final String XML_ROLE = "Role";
	public static final String XML_ROLEID = "roleid";
	public static final String XML_USERPATTERNS = "UserPatterns";
	public static final String XML_USERPATTERN = "UserPattern";
	public static final String XML_USERPATTERNID = "userpatternid";
	public static final String XML_DEFAULTFORMPATTERN = "defaultformpattern";
	public static final String XML_USERS = "Users";
	public static final String XML_USER = "User";
	public static final String XML_USERID = "userid";
	public static final String XML_FILE = ".xml";
	public static final String XML_ROLE_FILE="roles";
	public static final String XML_RIGHT_FILE= "rights";
	public static final String XML_USERPATTERN_FILE="UserPatterns";	
	public static final String employeeNumber = "employeeNumber";
	public static final String category = "category";
	public static final String address1 = "Address1";
	public static final String address2 = "Address2";
	public static final String city = "city";
	public static final String zipCode = "zipCode";
	public static final String state = "state";
	public static final String telephoneNumber = "telephoneNumber";
	public static final String universalProviderIdentificationNumber = "universalProviderIdentificationNumber";
	public static final String nationalProvidedIdentificationNumber = "nationalProvidedIdentificationNumber";
	public static final String prefixId = "prefixId";
	public static final String suffixId = "suffixId";
	public static final String active_ = "active_";
	// added new constant for inactive date/change done by pra on 16june 2009
	public static final String inActiveDate = "inActiveDate";
	 public static final String UA_PROPERITIES_FILE="/com/oxymedical/component/useradmin/queryproperties/useradminDb.properties";
	
	public static final String SIGN_ID = "ID";
	public static final String SIGN_IMAGETYPE = "IMAGETYPE";
	public static final String SIGN_IMAGEDATA = "IMAGEDATA";
	public static final String SIGN_IMAGETAGNO = "IMAGETAGNO";
	public static final String SIGN_IMAGEUNID = "IMAGEUNID";
	public static final String SIGN_USERID = "USERID";
}

package com.oxymedical.hic.request;

public interface IRequestConstants
{
	//public static final String usersXMLPath = "/users/users.xml";
	public static final String users = "/users";
	public static final String USERS_XML = "users.xml";
	public static final String ROLES_XML = "roles.xml";
	public static final String RIGHTS_XML = "rights.xml";
	public static final String USERPATTERNS_XML = "UserPatterns.xml";
	public static final String GROUPS_XML = "Group.xml";
	public static final String ORGS_XML = "Organization.xml";
	
	public static final String authenticate = "authenticate";
	public static final String userName = "UserName";
	public static final String password = "Password";
	public static final String applicationName = "applicationName";
	public static final String SOURCEDIR = "sourceDir";
	public static final String applicationURL = "applicationURL";
	public static final String serverAddress = "serverAddress";
	public static final String laszloweb = "laszlo-web";
	public static final String lps = "lps";
	public static final String UserAdmin = "UserAdmin";
	public static final String lzx = "lzx";
	public static final String Login = "UserAdmin";
	public static final String Dot = ".";
	
	//Added by Harmeet Please dont change
	public static final short USERS = 0;
	public static final short ROLES = 1;
	public static final short RIGHTS = 2;
	public static final short USERPATTERNS = 3;
	public static final short GROUPS = 3;
	public static final short ORGS = 3;
}

package com.oxymedical.component.db.utilities.parameters;
/**
 * This class is use to set and get all the user information. 
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class UserInfo 
{
	/**is used to set the user name while settingup data configuration. */
	String userName = null;
	/**is used to set the password as specified by user */
	String password = null;
	/**is used to set the server url e.g.  "jdbc:mysql://localhost:3306/" */
	String serverURL = null;
	/**is used to set the package name for eg "com.oxymedical.component.test" */
	String packageName = null;
	/**is used to set the jar address or the path of jar */
	String baseDirectoryAddress = null;
	/**is used to set the jar name as specified by user */
	String jarName = null;
	
	/**
	 *This method is used to get the jar name as specified by user. 
	 * 
	 * @return String
	 */
	public String getJarName() {
		return jarName;
	}
	
	/**
	 *This method is used to set jar name as specified by user.
	 * 
	 * @param jarName
	 * @return void
	 */
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}
	
	/**
	 *This method is used to get the jar address or the path of jar.
	 * 
	 * @return String
	 */
	public String getBaseDirectoryAddress() {
		return baseDirectoryAddress;
	}
	
	/**
	 *This method is used to set the jar address or the path of jar.
	 * 
	 * @param baseDirectoryAddress
	 * @return void
	 */
	public void setBaseDirectoryAddress(String baseDirectoryAddress) {
		this.baseDirectoryAddress = baseDirectoryAddress;
	}
	
	/**
	 *This method is used to get the package name for eg "com.oxymedical.component.test".
	 * 
	 * @return String
	 */
	public String getPackageName() {
		return packageName;
	}
	
	/**
	 *This method is used to set the package name for eg "com.oxymedical.component.test".
	 * 
	 * @param packageName
	 * @return void
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	/**
	 *This method is used to get the password.
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 *This method is used to set the password.
	 * 
	 * @param password
	 * @return void
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 *This method is used to get the server url e.g.  "jdbc:mysql://localhost:3306/"
	 * 
	 * @return String
	 */
	public String getServerURL() {
		return serverURL;
	}
	
	/**
	 *This method is used to set the server url e.g.  "jdbc:mysql://localhost:3306/"
	 * 
	 * @param serverURL
	 * @return void
	 */
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	
	/**
	 *This is used to get the user name which is
	 *set while settingup data configuration.
	 * 
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 *This is used to set the user name.
	 * 
	 * @param userName
	 * @return void
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}

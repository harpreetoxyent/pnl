package com.oxymedical.component.ldap;


/**
 * To get the values from the UI and set the values in the LDAPComponent
 * so it can be used to perform the LDAP query operations   
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */

public class LDAPVO {	
	
	private String commonName;
	private String name;
	private String lastName;	
	private String telephoneNumber;
	private String mail;
	private String userPassword;
	private String description;
	private String displayName;
	private String mobileNumber;
	private String containerName;
	private String designation;
	private String userId;
	private String uniqueMember;
	private String userRole;
	private String type;
		
	/**
	 * Get the type of the entry
	 * @return String
	 */
	public String getType() {
		return type;
	}
	/**
	 * Set the type of the entry
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Get the container name of the entry
	 * @return String
	 */
	public String getContainerName() {
		return containerName;
	}
	/**
	 * Set the container name of the entry
	 * @param containerName
	 */
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	/**
	 * Get the common name of the entry 
	 * @return String
	 */
	public String getCommonName() {
		return commonName;
	}
	/**
	 * Set the common name of the entry
	 * @param commonName
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	/**
	 * Get the description of the entry 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set the description of the entry
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Get the display name of the entry
	 * @return String
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * Set the display name of the entry
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * Get the lastname of the entry 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Set the last name of the entry 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Get the mail id of the entry 
	 * @return String
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * Set the mail id of the entry
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * Get the mobile number of the entry
	 * @return String
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * Set the mobile number of the entry
	 * @param mobileNumber
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}	
	/**
	 * Get the designation of the entry 
	 * @return String
	 */
	public String getDesignation() {
		return designation;
	}
	/**
	 * Set the designation of the entry
	 * @param designation
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	/**
	 * Get the telephone number of the entry
	 * @return String
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	/**
	 * Set the telephone number of the entry
	 * @param telephoneNumber
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	/**
	 * Get the password of the user in the entry
	 * @return String
	 */
	public String getUserPassword() {
		return userPassword;
	}
	/**
	 * Set the password of the user in the entry
	 * @param userPassword
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	/**
	 * Get the name of the entry
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the name of the entry 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the user id of the entry
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Set the user id of the entry
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * Get the unique member of the entry
	 * @return String
	 */
	public String getUniqueMember() {
		return uniqueMember;
	}
	/**
	 * Set the unique member of the entry
	 * @param uniqueMember
	 */
	public void setUniqueMember(String uniqueMember) {
		this.uniqueMember = uniqueMember;
	}
	/**
	 * Get the user role of the user 
	 * @return String
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * Set user role of the user
	 * @param userRole
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
		
	
}

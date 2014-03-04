package com.oxymedical.component.ldap.Role;

/**
 * To Get and set the values of the defined role of the user
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class RoleLdapVO {
	private String name;
	private String commonName;
	private String containerName;

	/**
	 * Get the name value
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name value
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the common name value
	 * @return String
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * Set the common name value
	 * @param commonName
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * Get the container name 
	 * @return String
	 */
	public String getContainerName() {
		return containerName;
	}

	/**
	 * Set the container name 
	 * @param containerName
	 */
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	

}

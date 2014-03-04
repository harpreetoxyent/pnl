package com.oxymedical.component.ldap;

import java.util.ArrayList;

/**
 * To Get and set the values from the LDAP ds so it can be used in the search and 
 * list operations in ldap ds 
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class ListLDAPVO {
	
	private ArrayList<String> cn;
	private ArrayList<String> sn;
	private ArrayList<String> type;
	private ArrayList<String> name;
	private ArrayList<String> firstname;
	private ArrayList<String> description;
	private ArrayList<String> givenname;
	private ArrayList<String> ou;
	private ArrayList<String> mail;
	private ArrayList<String> telephonenumber;
	private ArrayList<String> objectClass;
	
	/**
	 * Get the common name
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getCn() {
		return cn;
	}
	/**
	 * Set the common name
	 * @param cn
	 */
	public void setCn(ArrayList<String> cn) {
		this.cn = cn;
	}
	/**
	 * Get the sur name 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getSn() {
		return sn;
	}
	/**
	 * Set the sur name
	 * @param sn
	 */
	public void setSn(ArrayList<String> sn) {
		this.sn = sn;
	}
	/**
	 * Get the Type
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getType() {
		return type;
	}
	/**
	 * Set the type
	 * @param type
	 */
	public void setType(ArrayList<String> type) {
		this.type = type;
	}
	/**
	 * Get the name 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getName() {
		return name;
	}
	/**
	 * Set the name 
	 * @param name
	 */
	public void setName(ArrayList<String> name) {
		this.name = name;
	}
	/**
	 * Get the description 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getDescription() {
		return description;
	}
	/**
	 * Set the description 
	 * @param description
	 */
	public void setDescription(ArrayList<String> description) {
		this.description = description;
	}
	/**
	 * Get the given name 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getGivenname() {
		return givenname;
	}
	/**
	 * Set the given name
	 * @param givenname
	 */
	public void setGivenname(ArrayList<String> givenname) {
		this.givenname = givenname;
	}
	/**
	 * Get the dn name 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getOu() {
		return ou;
	}
	/**
	 * Set the dn name
	 * @param ou
	 */
	public void setOu(ArrayList<String> ou) {
		this.ou = ou;
	}
	/**
	 * Get the mail id
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMail() {
		return mail;
	}
	/**
	 * Set the mail id
	 * @param mail
	 */
	public void setMail(ArrayList<String> mail) {
		this.mail = mail;
	}
	/**
	 * Get the telephone number
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getTelephonenumber() {
		return telephonenumber;
	}
	/**
	 * Set the telephone number
	 * @param telephonenumber
	 */
	public void setTelephonenumber(ArrayList<String> telephonenumber) {
		this.telephonenumber = telephonenumber;
	}
	/**
	 * Get the first name 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getFirstname() {
		return firstname;
	}
	/**
	 * Set the first name
	 * @param firstname
	 */
	public void setFirstname(ArrayList<String> firstname) {
		this.firstname = firstname;
	}
	/**
	 * Get the object class
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getObjectClass() {
		return objectClass;
	}
	/**
	 * Set the object class
	 * @param objectClass
	 */
	public void setObjectClass(ArrayList<String> objectClass) {
		this.objectClass = objectClass;
	}
	
	
}

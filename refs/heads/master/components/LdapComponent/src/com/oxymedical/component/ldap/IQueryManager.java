package com.oxymedical.component.ldap;

import org.dom4j.Document;

import com.oxymedical.component.ldap.exception.LDAPComponentException;


/**
 * Query Manager is perform the LDAP operations such as add/delete/modify of the entry ,
 * add/delete/replace the attribute in the entry , import the ldif file in to the LDAP ds , 
 * Search the entry in the directory tree and List the entry in the directory tree in the 
 * LDAP ds.  
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public interface IQueryManager 
{
	/**
	 * Add entry in the LDAP ds
	 * @param ldapVO
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean addEntry(LDAPVO ldapVO) throws LDAPComponentException;
	
	
	/**
	 * Get the connection manager
	 * @return IConnectionManager
	 */
	public IConnectionManager getConnectionManager();
	
	/**
	 * Set the connection manager 
	 * @param connectionManager
	 */
	public void setConnectionManager(IConnectionManager connectionManager);
	
	/** 
	 * Delete the entry in the LDAP ds
	 * @param ldapVO
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean deleteEntry(LDAPVO ldapVO) throws LDAPComponentException;
	
	/**
	 * Delete the attribute in the entry in LDAP ds
	 * @param ldapVO
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean deleteAttribute(LDAPVO ldapVO) throws LDAPComponentException;
	
	/**
	 * Modify the attribute in the entry in LDAP ds
	 * @param ldapVO
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean modifyAttribute(LDAPVO ldapVO) throws LDAPComponentException;
	
	/**
	 * Add the attribute in the entry in LDAP ds
	 * @param ldapVO
	 * @return
	 * @throws LDAPComponentException
	 */
	public boolean addAttribute(LDAPVO ldapVO) throws LDAPComponentException;
	
	/**
	 * Search the entry in LDAP ds
	 * @param ldapVO
	 * @return Document
	 * @throws LDAPComponentException
	 */
	public Document search(LDAPVO ldapVO) throws LDAPComponentException;
	
	/**
	 * List the entry in LDAP ds
	 * @param ldapVO
	 * @return Document
	 * @throws LDAPComponentException
	 */
	public Document list(LDAPVO ldapVO) throws LDAPComponentException;
	
	/**
	 * Export the entry and save in to a file 
	 * @param ldapVO
	 * @param fileName
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean exportToLdif(LDAPVO ldapVO, String fileName) throws LDAPComponentException;
	
	/**
	 * Import the file in the LDAP ds 
	 * @param fileName
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean importToLdif(String fileName) throws LDAPComponentException;
	
}

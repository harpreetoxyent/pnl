package com.oxymedical.component.ldap;

import org.dom4j.Document;

import com.oxymedical.component.ldap.exception.LDAPComponentException;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.IHICData;


/**
 * LDAP Component is used to communication with LDAP Directory Servers 
 * using the LDAP (Lightweight Directory Access) Protocol
 * 
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public interface ILDAPComponent {
	
	/**
	 * Add the entry in the ldap ds 
	 * @param hicData
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean addEntry(IHICData hicData) throws LDAPComponentException;
	
	
	/**
	 * Delete the entry in the ldap ds
	 * @param hicData
	 * @return boolean 
	 * @throws LDAPComponentException
	 */
	public boolean deleteEntry(IHICData hicData) throws LDAPComponentException;
	
	/**
	 * Delete the attribute in an entry in the ldap ds
	 * @param hicData
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean deleteAttribute(IHICData hicData) throws LDAPComponentException;
	
	/**
	 * Modify an attribute in an entry in ldap DS
	 * @param hicData
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean modifyAttribute(IHICData hicData) throws LDAPComponentException;
	
	/**
	 * Add the attribute in the directory tree entry in the ldap ds
	 * @param hicData
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean addAttribute(IHICData hicData) throws LDAPComponentException;
	
	/**
	 * Search the  entry in the directory in ldap ds 
	 * @param hicData
	 * @return Document
	 * @throws LDAPComponentException
	 */
	public IHICData search(IHICData hicData) throws LDAPComponentException;
	
	/**
	 * List the entry in the directory in the ldap ds
	 * @param hicData
	 * @return Document
	 * @throws LDAPComponentException
	 */
	public Document list(IHICData hicData) throws LDAPComponentException;
	
	/**
	 * Export entries from DS to a file and saves the file in the form of ldif  
	 * @param hicData
	 * @param fileName
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean exportToLdif(IHICData hicData, String fileName) throws LDAPComponentException;
	
	/**
	 * Import entries from LDIF file to DS
	 * @param fileName
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean importToLdif(String fileName) throws LDAPComponentException;
}	
	


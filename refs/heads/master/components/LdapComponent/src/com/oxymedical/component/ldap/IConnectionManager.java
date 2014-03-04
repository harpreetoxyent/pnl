package com.oxymedical.component.ldap;

import com.novell.ldap.LDAPConnection;
import com.oxymedical.component.ldap.exception.LDAPComponentException;

/**
 * Connection manager is used to make the LDAPConnection
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public interface IConnectionManager 
{
	//public void doConfigureData(Document xmlDocument);
	
	
		
	/**
	 * To connect the LDAP Connection with ldap host and default port  and 
	 * bind the connection with the  Use Name and password 
	 * @param userName
	 * @param password
	 * @return LDAPConnection
	 * @throws LDAPComponentException
	 */
	public LDAPConnection connect(String userName, String password) throws LDAPComponentException ;
	
	/**
	 * Get the LDAPConnection 
	 * @return LDAPConnection
	 * @throws LDAPComponentException
	 */
	public LDAPConnection getConnection()throws LDAPComponentException;
	
	/**
	 * To disconnect the LDAPConnection 
	 * @throws LDAPComponentException
	 */
	public void disconnect() throws LDAPComponentException;
}

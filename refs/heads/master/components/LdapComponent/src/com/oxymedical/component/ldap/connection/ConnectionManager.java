package com.oxymedical.component.ldap.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPJSSESecureSocketFactory;
import com.novell.ldap.LDAPSocketFactory;
import com.oxymedical.component.ldap.IConnectionManager;
import com.oxymedical.component.ldap.constants.LDAPConstants;
import com.oxymedical.component.ldap.exception.LDAPComponentException;
import com.oxymedical.core.propertyUtil.PropertyUtil;


/**
 * Connection manager is used to establish the connection from the LDAP DS 
 * @author      Oxyent Medical
 * @version     1.0.0
 */
public class ConnectionManager implements IConnectionManager 
{
	
	  private static int ldapVersion = LDAPConnection.LDAP_V3;
	 // private int ldapPort = LDAPConstants.LDAP_DEFAULT_PORT;
	  private  String ldapHost = PropertyUtil.setUpProperties(LDAPConstants.SERVER_ADDRESS_KEY);
	  private static LDAPConnection ldapConnection = null;
	  private int ldapPort = LDAPConnection.DEFAULT_SSL_PORT;
	  static LDAPSocketFactory ssf;
		
	  String path = PropertyUtil.setUpProperties(LDAPConstants.LDAP_CRT_PATH) + LDAPConstants.LDAP_CRT_FILENAME;
    /**
	 * Constructor of the connection manager
	 */
	public ConnectionManager() {
		// ldapConnection = new LDAPConnection();
	 }
	
	public LDAPConnection connect(String userName, String password, boolean useSSL, String _ldapHost, String _ldapPort) throws LDAPComponentException {
		try{
			if(useSSL == true){
				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				System.setProperty(LDAPConstants.TRUST_STORE, path);
				ssf = new LDAPJSSESecureSocketFactory();
				LDAPConnection.setSocketFactory(ssf);
			}
			ldapConnection  = new LDAPConnection();
			if(_ldapHost != null && _ldapHost.length() > 0){
				ldapHost = _ldapHost;
			}
			if(_ldapPort != null && _ldapPort.length() > 0){
				ldapPort = Integer.parseInt(_ldapPort);
			}
			ldapConnection.connect(ldapHost, new Integer(ldapPort).intValue());
			// bind to the server
			ldapConnection.bind(ldapVersion, userName, password.getBytes(LDAPConstants.UTF8));
			//logger.log(0,"bind with ssl ");
			
	    }catch(LDAPException ldape) {			
	        throw new LDAPComponentException(ldape.getMessage());			
	    }catch(UnsupportedEncodingException uee) {
	    	throw new LDAPComponentException(uee.getMessage());	       
	    }
	    return ldapConnection;
	}
	    
	public LDAPConnection connect(String userName, String password) throws LDAPComponentException{
		ldapConnection = connect(userName, password, true, "", "");
	    return ldapConnection;
	}
	
	public void disconnect() throws LDAPComponentException {
		try {
			ldapConnection.disconnect();
		}catch(LDAPException ldape ){
			throw new LDAPComponentException(ldape.getMessage());
	    }
	}

		
	public LDAPConnection getConnection() throws LDAPComponentException
	{
		LDAPConnection ldapConnection  = new LDAPConnection();
		return ldapConnection;
	}	
}


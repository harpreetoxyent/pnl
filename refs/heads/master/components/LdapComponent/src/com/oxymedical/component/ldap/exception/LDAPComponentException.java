package com.oxymedical.component.ldap.exception;


import com.oxymedical.component.baseComponent.exception.ComponentException;


/**
 * LDAPComponentExeption is used in the LDAPComponent which extends the super ComponentException 
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class LDAPComponentException extends ComponentException
{
	
    /**
	 * Default serial version ID 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the LDAP Component Exception
	 */
	public LDAPComponentException() {
		   super();
	     }
	   
	  
	/**
	 * Constructor of the LDAP Component Exception
	 * @param messageException
	 */
	public LDAPComponentException(String messageException) {
		 super(messageException);
		}
	   
	/**
	 * Constructor of the LDAP Component Exception
	 * @param causeException
	 */
	public LDAPComponentException(Throwable causeException) {
		 super(causeException);
		}	
	  
	/**
	 * Constructor of the LDAP Component Exception
	 * @param messageException
	 * @param causeException
	 */
	public LDAPComponentException(String messageException, Throwable causeException) {
		super(messageException, causeException);
	   }
	  
	

}

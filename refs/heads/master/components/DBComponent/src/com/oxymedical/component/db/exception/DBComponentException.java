package com.oxymedical.component.db.exception;

import com.oxymedical.component.baseComponent.exception.ComponentException;



/**
 * This class in responsible for all DataBase Component Exceptions .
 * @author      Oxyent Medical
 * @version     1.0.0
 * */
public class DBComponentException extends ComponentException
{
	
	/**
	  * Constructor for class DBComponentException.
	  * 
	 */
	public DBComponentException(){
		
	}
	/**
	  * Calls the constructor of its superclass ComponentException and 
	  * passes message.
	  * @param message 
	  * 
	 */
    public DBComponentException(String message)
    {
    	super(message);
    }
    /**
     * Calls the constructor of its superclass ComponentException and 
     * passes message,cause into it.
     * @param messageException  
     * @param causeException
    */
    public DBComponentException(String messageException, Throwable causeException){
    	super(messageException);
    		
    	}
    /**
     * Calls the constructor of its superclass ComponentException and 
     * passes cause.
     * @param causeException  
     * 
    */
	public DBComponentException(Throwable causeException)
	{
		super(causeException);
	}	

	
}



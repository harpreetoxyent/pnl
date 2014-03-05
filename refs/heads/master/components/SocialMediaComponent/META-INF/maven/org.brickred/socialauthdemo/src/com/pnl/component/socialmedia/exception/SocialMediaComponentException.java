package com.pnl.component.socialmedia.exception;

public class SocialMediaComponentException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 619342883124636369L;
	/**
	  * Constructor for class NLPException.
	  * 
	 */
	public SocialMediaComponentException(){
		
	}
	/**
	  * Calls the constructor of its superclass ComponentException and 
	  * passes message.
	  * @param message 
	  * 
	 */
 public SocialMediaComponentException(String message)
 {
 	super(message);
 }
 /**
  * Calls the constructor of its superclass ComponentException and 
  * passes message,cause into it.
  * @param messageException  
  * @param causeException
 */
 public SocialMediaComponentException(String messageException, Throwable causeException){
 	super(messageException);
 		
 	}
 /**
  * Calls the constructor of its superclass ComponentException and 
  * passes cause.
  * @param causeException  
  * 
 */
	public SocialMediaComponentException(Throwable causeException)
	{
		super(causeException);
	}	
	


}

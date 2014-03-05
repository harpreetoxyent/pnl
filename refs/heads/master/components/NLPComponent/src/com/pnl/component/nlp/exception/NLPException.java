package com.pnl.component.nlp.exception;

public class NLPException  extends Exception 
{

	/**
	  * Constructor for class NLPException.
	  * 
	 */
	public NLPException(){
		
	}
	/**
	  * Calls the constructor of its superclass ComponentException and 
	  * passes message.
	  * @param message 
	  * 
	 */
  public NLPException(String message)
  {
  	super(message);
  }
  /**
   * Calls the constructor of its superclass ComponentException and 
   * passes message,cause into it.
   * @param messageException  
   * @param causeException
  */
  public NLPException(String messageException, Throwable causeException){
  	super(messageException);
  		
  	}
  /**
   * Calls the constructor of its superclass ComponentException and 
   * passes cause.
   * @param causeException  
   * 
  */
	public NLPException(Throwable causeException)
	{
		super(causeException);
	}	
	

}

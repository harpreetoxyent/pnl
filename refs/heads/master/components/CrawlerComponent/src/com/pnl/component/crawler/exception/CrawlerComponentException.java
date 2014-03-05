package com.pnl.component.crawler.exception;

public class CrawlerComponentException extends Exception{

	/**
	  * Constructor for class CrawlerComponentException.
	  * 
	 */
	public CrawlerComponentException(){
		
	}
	/**
	  * Calls the constructor of its superclass ComponentException and 
	  * passes message.
	  * @param message 
	  * 
	 */
   public CrawlerComponentException(String message)
   {
   	super(message);
   }
   /**
    * Calls the constructor of its superclass ComponentException and 
    * passes message,cause into it.
    * @param messageException  
    * @param causeException
   */
   public CrawlerComponentException(String messageException, Throwable causeException){
   	super(messageException);
   		
   	}
   /**
    * Calls the constructor of its superclass ComponentException and 
    * passes cause.
    * @param causeException  
    * 
   */
	public CrawlerComponentException(Throwable causeException)
	{
		super(causeException);
	}	



}

package com.pnl.component.solr.exception;

public class SolrComponentException extends Exception{

	/**
	  * Constructor for class CrawlerComponentException.
	  * 
	 */
	public SolrComponentException(){
		
	}
	/**
	  * Calls the constructor of its superclass ComponentException and 
	  * passes message.
	  * @param message 
	  * 
	 */
   public SolrComponentException(String message)
   {
   	super(message);
   }
   /**
    * Calls the constructor of its superclass ComponentException and 
    * passes message,cause into it.
    * @param messageException  
    * @param causeException
   */
   public SolrComponentException(String messageException, Throwable causeException){
   	super(messageException);
   		
   	}
   /**
    * Calls the constructor of its superclass ComponentException and 
    * passes cause.
    * @param causeException  
    * 
   */
	public SolrComponentException(Throwable causeException)
	{
		super(causeException);
	}	



}

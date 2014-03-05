package com.pnl.component.knowledgeGraph.exception;

public class KnowledgeGraphException extends Exception 
{

	/**
	  * Constructor for class KnowledgeGraphException.
	  * 
	 */
	public KnowledgeGraphException(){
		
	}
	/**
	  * Calls the constructor of its superclass ComponentException and 
	  * passes message.
	  * @param message 
	  * 
	 */
   public KnowledgeGraphException(String message)
   {
   	super(message);
   }
   /**
    * Calls the constructor of its superclass ComponentException and 
    * passes message,cause into it.
    * @param messageException  
    * @param causeException
   */
   public KnowledgeGraphException(String messageException, Throwable causeException){
   	super(messageException);
   		
   	}
   /**
    * Calls the constructor of its superclass ComponentException and 
    * passes cause.
    * @param causeException  
    * 
   */
	public KnowledgeGraphException(Throwable causeException)
	{
		super(causeException);
	}	

}

package com.oxymedical.component.baseComponent.exception;

public class ComponentException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ComponentException()
	{
		super();
	}
	public ComponentException(String messageException)
	{
		super(messageException);
	}
	public ComponentException(String messageException, Throwable causeException)
	{
		super(messageException, causeException);
	}	
	public ComponentException(Throwable causeException)
	{
		super(causeException);
	}	

}

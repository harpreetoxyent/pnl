package com.oxymedical.component.logging.exception;

import com.oxymedical.component.baseComponent.exception.ComponentException;

/**
 * this class is used to define the  Logging exception 
 */
public class LoggingComponentException extends ComponentException
{
	private static final long serialVersionUID = 1L;

	public LoggingComponentException()
	{
		
	}
	public LoggingComponentException(String message)
	{
		super(message);
	}
	public LoggingComponentException(String message ,Throwable cause)
	{
		super(message , cause);
	}
	public LoggingComponentException(Throwable cause)
	{
		super(cause);
	}
}

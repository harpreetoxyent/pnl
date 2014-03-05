package com.oxymedical.component.renderer.exception;

import com.oxymedical.component.renderer.exception.RendererComponentException;

/**
 * this class handles all the SourceNotFound exceptions and it is extended 
 * by the RendererComponentException class
 *
 */
public class SourceNotFoundException extends RendererComponentException
{
	private static final long serialVersionUID = 1L;

	public SourceNotFoundException()
	{
		super();
	}
	public SourceNotFoundException(String message)
	{
		super(message);
	}
	public SourceNotFoundException(String message,Throwable cause)
	{
		super(message,cause);
	}
	public SourceNotFoundException(Throwable cause)
	{
		super(cause);
	}
}


package com.oxymedical.component.renderer.exception;

/**
 * This class handles all the RendererComponentException throws by the Renderer 
 * and it is extends by the Exception class
 *
 */
public class RendererComponentException extends Exception
{
	private static final long serialVersionUID = 1L;
	

	public RendererComponentException()
	{
		super();
	}
	public RendererComponentException(String message)
	{
		super(message);
	}
	public RendererComponentException(String message,Throwable cause)
	{
		super(message,cause);
	}
	public RendererComponentException(Throwable cause)
	{
		super(cause);
	}
}


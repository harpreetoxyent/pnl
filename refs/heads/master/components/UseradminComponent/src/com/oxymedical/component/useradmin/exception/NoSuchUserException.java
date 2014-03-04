package com.oxymedical.component.useradmin.exception;

public class NoSuchUserException extends UAComponentException
{

	public NoSuchUserException() 
	{
		super();
	}

	public NoSuchUserException(String msg) 
	{
		super(msg);
	}

	public NoSuchUserException(String msg, Throwable cause) 
	{
		super(msg, cause);
	}

	public NoSuchUserException(Throwable cause) 
	{
		super(cause);
	}

}

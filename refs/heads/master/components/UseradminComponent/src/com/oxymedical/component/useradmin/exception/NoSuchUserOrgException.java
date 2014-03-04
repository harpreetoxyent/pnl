package com.oxymedical.component.useradmin.exception;

public class NoSuchUserOrgException extends UAComponentException 
{

	private static final long serialVersionUID = 1616257927644170915L;

	public NoSuchUserOrgException() 
	{
		super();
	}

	public NoSuchUserOrgException(String msg) 
	{
		super(msg);
	}

	public NoSuchUserOrgException(String msg, Throwable cause) 
	{
		super(msg, cause);
	}

	public NoSuchUserOrgException(Throwable cause) 
	{
		super(cause);
	}
}

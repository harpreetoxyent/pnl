package com.oxymedical.component.useradmin.exception;

public class NoSuchCompanyException extends UAComponentException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2036262656732982071L;

	public NoSuchCompanyException() {
		super();
	}

	public NoSuchCompanyException(String msg) {
		super(msg);
	}

	public NoSuchCompanyException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchCompanyException(Throwable cause) {
		super(cause);
	}
}

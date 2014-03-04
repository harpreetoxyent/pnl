package com.oxymedical.component.useradmin.exception;

public class InValidPasswordException extends UAComponentException
{

	private static final long serialVersionUID = -2036262656732982071L;

	public InValidPasswordException() {
		super();
	}

	public InValidPasswordException(String msg) {
		super(msg);
	}

	public InValidPasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InValidPasswordException(Throwable cause) {
		super(cause);
	}
}

package com.oxymedical.component.useradmin.exception;

public class NoSuchContainerException extends UAComponentException
{

	private static final long serialVersionUID = 1L;

	public NoSuchContainerException() {
		super();
	}

	public NoSuchContainerException(String msg) {
		super(msg);
	}

	public NoSuchContainerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchContainerException(Throwable cause) {
		super(cause);
	}


}

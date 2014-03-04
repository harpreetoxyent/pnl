package com.oxymedical.component.useradmin.exception;

import com.oxymedical.component.baseComponent.exception.ComponentException;

public class UAComponentException extends ComponentException
{
	private static final long serialVersionUID = 1L ;

	public UAComponentException() {
		super();
	}

	public UAComponentException(String msg) {
		super(msg);
	}

	public UAComponentException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public UAComponentException(Throwable cause) {
		super(cause);
	}

}

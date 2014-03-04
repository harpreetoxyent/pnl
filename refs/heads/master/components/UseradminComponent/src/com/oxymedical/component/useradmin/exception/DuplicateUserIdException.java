package com.oxymedical.component.useradmin.exception;

public class DuplicateUserIdException extends UAComponentException
{
	private static final long serialVersionUID = -2036262656732982071L;

	public DuplicateUserIdException() {
		super();
	}

	public DuplicateUserIdException(String msg) {
		super(msg);
	}

	public DuplicateUserIdException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DuplicateUserIdException(Throwable cause) {
		super(cause);
	}

}

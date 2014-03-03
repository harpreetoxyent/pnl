package com.oxymedical.framework.objectbroker.exception;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class ObjectBrokerException extends Exception 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor.  Create a ObjectBrokerException 
	// containing the given message as its error message
	public ObjectBrokerException(String message)
	{
		super(message);
	}
}

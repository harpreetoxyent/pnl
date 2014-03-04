/**
	 * @author Oxyent Medical Ltd, India
	 * 
	 * No part of this Source may be copied
	 * without Oxyent's prior written permission.
	 * Copyright 2007 Oxyent Medical Ltd, All Rights Reserved.
	 * 
	 *  Version 1.0.0
	 */

package com.oxymedical.component.useradmin;

public interface IContact 
{
	public com.oxymedical.component.useradmin.model.Contact getContact(
			java.lang.String contactId);
			
	public void deleteContact(java.lang.String contactId);

	public void deleteContact(com.oxymedical.component.useradmin.model.Contact contact);
}

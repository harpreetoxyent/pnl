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

public interface ISubject 
{
	IIdentity identity = null;
	IAction action = null;
	ISubjectProfile subjectProfile = null;
	Integer getSubjectId();
	IIdentity getIdentity();
	void setIdentity(IIdentity identity);	
	IAction getAction();
	void setAction(IAction action);	
	ISubjectProfile getSubjectProfile();
	void setSubjectProfile(ISubjectProfile subjectProfile);	

}

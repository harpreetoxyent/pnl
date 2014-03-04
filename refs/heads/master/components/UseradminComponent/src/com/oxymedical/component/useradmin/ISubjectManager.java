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

public interface ISubjectManager 
{
	void addSubject(ISubject subject);
	ISubject getSubject(Integer subjectId);
}

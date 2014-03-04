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

public interface IUserAdminManager 
{
	IActionManager actionManager = null;
	ILayoutManager layoutManager = null;
	ISubjectManager subjectManager = null;
	IIdentityManager identityManager = null;
	
	
	IActionManager getActionManager();
	void setActionManager(IActionManager actionManager);
	ILayoutManager getLayoutManager();
	void setLayoutManager(ILayoutManager layoutManager);
	ISubjectManager getSubjectManager();
	void setSubjectManager(ISubjectManager subjectManager);	
	IIdentityManager getIdentityManager();
	void setIdentityManager(IIdentityManager identityManager);	
}

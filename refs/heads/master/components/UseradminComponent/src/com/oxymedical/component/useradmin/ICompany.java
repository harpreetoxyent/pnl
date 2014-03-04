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

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.UAComponentException;

public interface ICompany 
{
	public java.util.List getCompanies()throws NoSuchCompanyException;
	
	public com.oxymedical.component.useradmin.model.Company getCompany(
	java.lang.String companyId)throws NoSuchCompanyException,DBComponentException;

	public com.oxymedical.component.useradmin.model.Company addCompany(
		java.lang.String companyId,	java.lang.String homeURL)throws DBComponentException, UAComponentException;

	public void updateDisplay(java.lang.String companyId,
		java.lang.String languageId)throws NoSuchCompanyException,DBComponentException;
	
	public com.oxymedical.component.useradmin.model.Company updateCompany(
			java.lang.String companyId,	java.lang.String homeURL)throws DBComponentException;

}

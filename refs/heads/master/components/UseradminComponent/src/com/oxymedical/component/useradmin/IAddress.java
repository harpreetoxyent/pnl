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

public interface IAddress 
{

	public com.oxymedical.component.useradmin.model.Address addAddress(
			java.lang.String userId, java.lang.String className,
			java.lang.String classPK, java.lang.String street1,
			java.lang.String street2, java.lang.String street3,
			java.lang.String city, java.lang.String zip, java.lang.String regionId,
			java.lang.String countryId, java.lang.String typeId, boolean mailing,
			boolean primary);
	
	public void deleteAddress(java.lang.String addressId);

	public void deleteAddresses(java.lang.String companyId,
			java.lang.String className, java.lang.String classPK);

	public com.oxymedical.component.useradmin.model.Address getAddress(
			java.lang.String addressId);

	public java.util.List getAddresses(java.lang.String companyId,
			java.lang.String className, java.lang.String classPK);

	public com.oxymedical.component.useradmin.model.Address updateAddress(
			java.lang.String addressId, java.lang.String street1,
			java.lang.String street2, java.lang.String street3,
			java.lang.String city, java.lang.String zip, java.lang.String regionId,
			java.lang.String countryId, java.lang.String typeId, boolean mailing,
			boolean primary);
}

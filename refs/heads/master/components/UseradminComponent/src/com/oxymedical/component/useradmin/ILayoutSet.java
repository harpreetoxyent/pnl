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

public interface ILayoutSet 
{
	public com.oxymedical.component.useradmin.model.Layoutset addLayoutSet(
			java.lang.String ownerId, java.lang.String companyId);

	public void deleteLayoutSet(java.lang.String ownerId);

	public com.oxymedical.component.useradmin.model.Layoutset getLayoutSet(
			java.lang.String ownerId);

	public com.oxymedical.component.useradmin.model.Layoutset getLayoutSet(
			java.lang.String companyId, java.lang.String virtualHost);
		
	public com.oxymedical.component.useradmin.model.Layoutset updateLookAndFeel(
			java.lang.String ownerId, java.lang.String themeId,
			java.lang.String colorSchemeId);

	public com.oxymedical.component.useradmin.model.Layoutset updatePageCount(
			java.lang.String ownerId);

		
}

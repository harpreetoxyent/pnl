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

import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Region;


public interface IRegion 
{

	public List getRegions()throws DBComponentException ,UAComponentException;
	public List getRegions(String countryId)throws DBComponentException ,UAComponentException;
	public List getRegions(boolean active);
	public List getRegions(String countryId, boolean active);
	public Region getRegion(String regionId)throws DBComponentException ,UAComponentException;
	public Region getRegionFromName(String name) throws UAComponentException , DBComponentException;
}

package com.oxymedical.framework.objectbroker;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

import java.lang.reflect.Type;

import com.oxymedical.framework.objectbroker.Helper.*;
import com.oxymedical.framework.objectbroker.Location.*;
import com.oxymedical.framework.objectbroker.exception.ObjectBrokerException;



public interface IBuilder<TStageEnum> 
{

	 public Object BuildUp(	Class typeToBuild,
				IReadWriteLocator locator,
				String idToBuild, 
				Object existing,
				PolicyList[] transientPolicies) throws ObjectBrokerException;
	 public Object TearDown(IReadWriteLocator locator, Type item) throws ObjectBrokerException;	 
	 
}
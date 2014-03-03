package com.oxymedical.framework.objectbroker;

import com.oxymedical.framework.objectbroker.Helper.*;
import com.oxymedical.framework.objectbroker.Location.IReadWriteLocator;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IBuilderContext 
{
	public IBuilderStrategy getHeadOfChain();
	public IBuilderStrategy getNextInChain(IBuilderStrategy currentStrategy);
	public PolicyList getPolicies();
	public IReadWriteLocator getLocator();
}

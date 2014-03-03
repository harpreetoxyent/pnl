package com.oxymedical.hic.application.strategies.proxypattern;
import com.oxymedical.framework.objectbroker.IBuilderPolicy;

/**
 * @author Oxyent Medical Pvt Ltd, India
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical Pvt Ltd, All Rights Reserved. 
 */

public interface IProxyPatternPolicy extends IBuilderPolicy
{
	/// Returns true if the object should be a singleton.
	boolean isProxy();
}

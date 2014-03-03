package com.oxymedical.framework.objectbroker.strategies.singleton;
import com.oxymedical.framework.objectbroker.*;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface ISingletonPolicy extends IBuilderPolicy
{
	/// Returns true if the object should be a singleton.
	boolean isSingleton();
}

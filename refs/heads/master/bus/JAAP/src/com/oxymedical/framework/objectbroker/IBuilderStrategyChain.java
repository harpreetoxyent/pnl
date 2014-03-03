package com.oxymedical.framework.objectbroker;
import java.util.Iterator;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IBuilderStrategyChain 
{
	IBuilderStrategy getHead();
	void Add(IBuilderStrategy strategy);
	void AddRange(Iterator strategies);
	IBuilderStrategy GetNext(IBuilderStrategy currentStrategy);
}

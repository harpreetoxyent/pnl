package com.oxymedical.framework.objectbroker;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Category;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

@SuppressWarnings("deprecation")
public class BuilderStrategyChain implements IBuilderStrategyChain 
{
	static Category cat = Category.getInstance("ObjectBuilder");
	private List<IBuilderStrategy> strategies;

	public BuilderStrategyChain()
	{
		strategies = new ArrayList<IBuilderStrategy>();
	}
	public IBuilderStrategy getHead()
	{
		if (strategies.size() > 0)
			return strategies.get(0);
		else
			return null;
	}

	public void Add(IBuilderStrategy strategy)
	{
		strategies.add(strategy);
	}
	
	public void AddRange(Iterator strategies)
	{
		while (strategies.hasNext ()) 
		{
			Add((IBuilderStrategy)strategies.next());
		}		
	}
	public IBuilderStrategy GetNext(IBuilderStrategy currentStrategy)
	{
		cat.info("Get Next of BuilderStrategyChain Called");
		for (int idx = 0; idx < strategies.size() - 1; idx++)
		{
			if (currentStrategy.equals(strategies.get(idx)) && idx < strategies.size())
			{
				return strategies.get(idx+1);
			}
		}

		return null;

	}

}

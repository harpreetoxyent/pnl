package com.oxymedical.framework.objectbroker.Helper;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.oxymedical.framework.objectbroker.*;



/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class StrategyList 
{
	private Hashtable <String,List<IBuilderStrategy>> stages;
	private List<String> builderStageList;
	//private Object lockObject = new Object();
	private BuilderStage[] builderstage;
	public StrategyList()
	{
		stages = new Hashtable <String,List<IBuilderStrategy>>();
		builderStageList = new ArrayList<String>();
		builderstage = BuilderStage.values();
		for(int i=0;i <builderstage.length;i++)
		{
			builderStageList.add(builderstage[i].toString());
			stages.put(builderstage[i].toString(), new ArrayList<IBuilderStrategy>() );
		}	
	}
	public void Add(IBuilderStrategy strategy, String stage)
	{
		stages.get(stage).add(strategy);
	}	
	@SuppressWarnings("unchecked")
	public <TStrategy> void AddNew( TStrategy t, String stage)
	{
		TStrategy newStrategy = t;
		stages.get(stage).add((IBuilderStrategy) newStrategy);
	}
	public void Clear()
	{
		for(int i=0;i <builderstage.length;i++)
		{
			stages.get(builderstage[i].toString()).clear();
		}
	}

	/// Creates a reverse strategy chain based on the strategies in the list. Useful
	/// for unbuild operations (which run strategies in reverse of build operations).
	public IBuilderStrategyChain makeReverseStrategyChain()
	{
		//not implemented yet
		return null;
	}

	public IBuilderStrategyChain makeStrategyChain()
	{
		BuilderStrategyChain result = new BuilderStrategyChain();
		Iterator it = builderStageList.iterator();
		while (it.hasNext ()) 
		{
			result.AddRange((stages.get((String)it.next())).iterator());
		}
		return result;
	}
	
	
}


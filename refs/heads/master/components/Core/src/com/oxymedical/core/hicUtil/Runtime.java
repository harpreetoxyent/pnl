package com.oxymedical.core.hicUtil;
import java.util.ArrayList;
import java.util.List;

import com.oxymedical.core.hicUtil.IRuntime;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class Runtime implements IRuntime
{
	private List<String> runtimeLibraryNames;
	
	/*public Runtime()
	{
		runtimeLibraryNames = new ArrayList<String>();
	}*/
	public List<String> getRuntimeLibraryNames() 
	{
		return runtimeLibraryNames;
	}
	public void setRuntimeLibraryNames(List<String> runtimeLibraryNames) 
	{
		this.runtimeLibraryNames = runtimeLibraryNames;
	}
	public String toString()
	{
		return "Runtime Section: \n" +" Runtime LibraryNames = "+
				this.runtimeLibraryNames.toString()
				+ "Count LibraryNames = "+this.runtimeLibraryNames.size();
	}	
}
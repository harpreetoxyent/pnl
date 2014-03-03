package com.oxymedical.core.hicUtil;

import java.util.List;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IRuntime 
{
	List<String> getRuntimeLibraryNames(); 
	void setRuntimeLibraryNames(List<String> runtimeLibraryNames); 
	
}

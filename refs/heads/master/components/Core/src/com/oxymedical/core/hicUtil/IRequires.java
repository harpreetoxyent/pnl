package com.oxymedical.core.hicUtil;

import java.util.List;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public interface IRequires 
{
	List<String> getImportLibraryNames(); 
	void setImportLibraryNames(List<String> importLibraryNames); 
	String getJarFolder();
	void setJarFolder(String jarFolder);
}

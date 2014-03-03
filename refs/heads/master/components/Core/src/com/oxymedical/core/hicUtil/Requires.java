package com.oxymedical.core.hicUtil;
import java.util.ArrayList;
import java.util.List;

import com.oxymedical.core.hicUtil.IRequires;

/**
 * @author Oxyent Medical
 * 
 * No part of this Source may be copied
 * without Oxyent Medical’s prior written permission.
 * Copyright 2007 Oxyent Medical, All Rights Reserved. 
 */

public class Requires implements IRequires
{
	private List<String> importLibraryNames;
	private String jarFolder;
	public Requires()
	{
		importLibraryNames = new ArrayList<String>();
	}
	public List<String> getImportLibraryNames() 
	{
		return importLibraryNames;
	}
	public void setImportLibraryNames(List<String> importLibraryNames) 
	{
		this.importLibraryNames = importLibraryNames;
	}
	public String toString()
	{
		return "Require Section: \n" +" Require LibraryNames = "+
				this.importLibraryNames.toString()
				+"Jar Location = " + this.jarFolder.toString()
				+ "Count LibraryNames = "+this.importLibraryNames.size();
	}
	public String getJarFolder() {
		return jarFolder;
	}
	public void setJarFolder(String jarFolder) {
		this.jarFolder = jarFolder;
	}	
}

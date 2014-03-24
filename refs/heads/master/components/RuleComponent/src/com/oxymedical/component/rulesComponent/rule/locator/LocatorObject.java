package com.oxymedical.component.rulesComponent.rule.locator;

/**
 * Locator Object has the location of the erl and jar folders
 * ERLLocationDir - list of all the erl rule files
 * JARLocationDir - list of all the jar files required to create the rules
 * 
 * @author Oxyent Medical
 *
 */
public class LocatorObject {
	
	private String erlLocationDir;
	
	private String jarLocationDir;
	
	public LocatorObject(String erlDir, String jarDir) {
		this.erlLocationDir = erlDir;
		this.jarLocationDir = jarDir;
	}

	public String getErlLocationDir() {
		return erlLocationDir;
	}

	public String getJarLocationDir() {
		return jarLocationDir;
	}

}

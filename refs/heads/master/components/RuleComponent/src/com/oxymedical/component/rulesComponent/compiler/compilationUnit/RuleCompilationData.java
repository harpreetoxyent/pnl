package com.oxymedical.component.rulesComponent.compiler.compilationUnit;

/**
 * This class stores the compilation unit of the rule class in the class loader
 * 
 * @author Oxyent Medical
 *
 */
public class RuleCompilationData {
	
	private static ClassLoader classLoader = null;

	public static ClassLoader getClassLoader() {
		return classLoader;
	}

	public static void setClassLoader(ClassLoader ruleClassLoader) {
		classLoader = ruleClassLoader;
	}

}

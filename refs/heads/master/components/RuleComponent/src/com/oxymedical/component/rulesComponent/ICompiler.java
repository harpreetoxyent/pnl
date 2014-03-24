package com.oxymedical.component.rulesComponent;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ICompiler {
	
	public void compile(Map map, List<File> jarList) throws Exception;

}

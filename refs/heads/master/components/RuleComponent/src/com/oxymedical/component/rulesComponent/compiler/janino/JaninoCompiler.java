package com.oxymedical.component.rulesComponent.compiler.janino;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.janino.CachingJavaSourceClassLoader;
import org.codehaus.janino.DebuggingInformation;
import org.codehaus.janino.util.resource.MapResourceCreator;
import org.codehaus.janino.util.resource.ResourceFinder;

import com.oxymedical.component.rulesComponent.ICompiler;
import com.oxymedical.component.rulesComponent.compiler.compilationUnit.RuleCompilationData;
import com.oxymedical.component.rulesComponent.exception.RuleComponentException;
import com.oxymedical.component.rulesComponent.resourceFinder.MapResourceFinder;


/**
 * Janino Compiler compiles the rule class using janino compiler
 * 
 * @author Oxyent Medical
 *
 */
public class JaninoCompiler implements ICompiler{


	static Map<String,?> compiledMap;

	/**
	 * Compiles all the generated classes present in map as byte array using the jar list
	 * 
	 * @param jarList
	 * @throws RuleComponentException
	 */
	public void compile(Map map, List<File> jarList) throws RuleComponentException
	{
		try
		{
			MapResourceFinder mrf = new MapResourceFinder(map);

			ClassLoader parentClassLoader = JaninoCompiler.class.getClassLoader();
			URLClassLoader loader = (URLClassLoader)parentClassLoader;

			if (null != jarList && jarList.size() > 0) {
				URL[] url = new URL[jarList.size()];	      
				for(int i=0; i< jarList.size(); i++) {
					url[i] = jarList.get(i).toURL();
				}

				// this loads the jar file at runtime for the generated classes to compile	      
				loader = new URLClassLoader(
						url, parentClassLoader);
			}


			MapResourceCreator classFileResources = new MapResourceCreator();
			ResourceFinder classFileFinder = new org.codehaus.janino.util.resource.MapResourceFinder(compiledMap);

			if(compiledMap==null)
				classFileFinder = ResourceFinder.EMPTY_RESOURCE_FINDER;
			else {
				((org.codehaus.janino.util.resource.MapResourceFinder)classFileFinder).setLastModified(System.currentTimeMillis());

				// Remove the redundant classes from the compiled Map.
				ArrayList<String> removalKeys = new ArrayList<String>();
				for(Iterator<String> i=compiledMap.keySet().iterator();i.hasNext();){
					String key = i.next();
					if(! map.containsKey(key)) removalKeys.add(key);
				}
				for(String key:removalKeys)
					compiledMap.remove(key);
			}


			/// added by ama
			// Used CachingJavaSourceClassLoader instead of JavaSourceClassLoader
			// avoiding the need of recompiling the existing rule files
			// The classFileResources stores the list of already compiled classes.
			ClassLoader ruleClassLoader =  new CachingJavaSourceClassLoader(
					loader,              							// parentClassLoader
					mrf,											 // sourceFinder
					(String) null,                                 // optionalCharacterEncoding
					classFileFinder,                               // classFileCacheResourceFinder
					classFileResources,                           // classFileResourceCreator
					DebuggingInformation.NONE                      // debuggingInformation
			);

			Map classFileMap = classFileResources.getMap();

			// add the compiledMap to this classFileMap
			if(compiledMap!=null)
				classFileMap.putAll(compiledMap);

			RuleCompilationData.setClassLoader(ruleClassLoader);
			//IRuleClass cmd = (IRuleClass) ruleClassLoader.loadClass("Rule_TestRule2_rule").newInstance();
			//IRuleClass cmd1 = (IRuleClass) ruleClassLoader.loadClass("Rule_TestRule1_rule").newInstance();    

			compiledMap = classFileMap;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuleComponentException(e.getMessage());
		}
	}

}

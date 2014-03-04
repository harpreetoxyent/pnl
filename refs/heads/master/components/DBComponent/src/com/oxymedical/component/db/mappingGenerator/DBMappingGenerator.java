package com.oxymedical.component.db.mappingGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.dom4j.DocumentException;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;

/**
 * This class automatically build the 
 * ant and setting up properties by giving up some build constants.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class DBMappingGenerator 
{	
	/**this variable is used to call the init() method of the class Project */
	private Project project;

	/**
	 * This method is used to automatically build the 
	 * ant by giving up some build constants.
	 * 
	 * @param userName
	 * @param password
	 * @param serverURL
	 * @param packageName
	 * @param baseDirectoryDirectory
	 * @returns void 
	 * @throws DBComponentException
	 */
	public void registerDB(	String userName, 
							String password,
							String serverURL,
							String packageName,
							String baseDirectoryDirectory) throws DBComponentException
	{
/*			init("build.xml",".");
		 	HashMap<String, String> m = new HashMap<String, String>();
	        m.put("build.dir", "hello");
	        
	        m.put("build.classes.dir", baseDirectoryDirectory+ "/classes");
	        m.put("build.compiler", "modern");	
	        m.put("gui", "false");
	        m.put("build.java.dir", "./java");
	        m.put("build.gen-hbm.dir", baseDirectoryDirectory+ "/gen-src");
	        m.put("build.gen-src.dir", baseDirectoryDirectory+ "/gen-src");	        
	        m.put("src.dir", "./src"); 
	        m.put("basedir", baseDirectoryDirectory);
	        m.put("username", userName);
	        m.put("password", password);
	        m.put("jarName", "resources"); // harcoded in case if not given
	        m.put("serverURL", serverURL);
	        m.put("name", packageName);
	        m.put("JAVA_HOME", ".");
	        setProperties(m, false);
	        runTarget("createjar");
*/	        
	        registerDB(userName, password, serverURL, packageName, baseDirectoryDirectory, "resources");
	
	}
	/**
	 * This method is used to automatically build the 
	 * ant by giving up some build constants.
	 * 
	 * @param userName
	 * @param password
	 * @param serverURL
	 * @param packageName
	 * @param baseDirectoryDirectory
	 * @param jarName
	 * @returns void 
	 * @throws DBComponentException
	 */
	public void registerDB(	String userName, 
			String password,
			String serverURL,
			String packageName,
			String baseDirectoryDirectory, String jarName) throws DBComponentException
			{
				
				init("build.xml",".");   
				HashMap<String, String> m = new HashMap<String, String>();
				m.put("build.dir", "hello");
				
				m.put("build.classes.dir", baseDirectoryDirectory+ "/classes");
				m.put("build.compiler", "modern");	
				m.put("gui", "false");
				m.put("build.java.dir", "./java");
				m.put("build.gen-hbm.dir", baseDirectoryDirectory+ "/gen-src");
				m.put("build.gen-src.dir", baseDirectoryDirectory+ "/gen-src");
				m.put("src.dir", "./src"); 
				m.put("basedir", baseDirectoryDirectory);
				m.put("username", userName);
				m.put("password", password);
				m.put("serverURL", serverURL);
				m.put("name", packageName + "." + jarName);				
				m.put("jarName", jarName);
				m.put("JAVA_HOME", ".");
				
				setProperties(m, false);
				runTarget("createjar");
		}
	

	private void init(String _buildFile, String _baseDir) throws DBComponentException
	{
		project = new Project();
	   	project.init(); 
	    if (null == _baseDir)
	    {
	    	_baseDir = new String(".");
	    }
	 
	   	project.setBasedir(_baseDir); 
	   	project.addBuildListener(new DBMapGenBuildListener());
	   	
	    if (null == _buildFile) 
	    {
	    	_buildFile=new String("build.xml");
	    }
	    try 
	    { 
			InputStream in = getClass().getResourceAsStream(_buildFile);
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			String line;
			while (null != (line = br.readLine())) 
			{
			    pw.println(line);
			}
			String buildFileOutput = sw.toString();
			File buildFile = File.createTempFile("build", ".xml");
	        // Delete temp file when program exits.
			buildFile.deleteOnExit();
	    
	        // Write to temp file
	        BufferedWriter out = new BufferedWriter(new FileWriter(buildFile));
	        out.write(buildFileOutput);
	        out.close();
	        List dpList=DataPatternParser.getMappingInfo(); 
	        if(dpList!=null)
	        {
				if (dpList.size() > 0)
				{
					try 
					{
						buildFile = BuildXMLEditor.updateForMany2ManyMapping(buildFile);
					} 
					catch (DocumentException e) 
					{
	//			    	throw new DBComponentException(DBExceptionConstants.DOCUMENT_EXCEPTION + _buildFile + DBConstants.DB_COMMA + _baseDir +"in init. Failed to create many 2 many mapping.");
						DBComponent.logger.log(4, new StringBuffer("!! Could not update Many 2 Many relation. !! Error: ").append(e.getMessage()).toString());
						e.printStackTrace();
					}
				}
	        }
	    	ProjectHelper.getProjectHelper().parse(project, buildFile); 
	        
	    }
	    catch(IOException e)
	    {
	    	throw new DBComponentException(DBExceptionConstants.IO_EXCEPTION + _buildFile + DBConstants.DB_COMMA + _baseDir +"in init");
	    }
	    catch (BuildException e)
	    { 
	    	throw new DBComponentException(DBExceptionConstants.BUILD_EXCEPTION + _buildFile + DBConstants.DB_COMMA + _baseDir +"in init"); 
	    }
	  	   
	}	
	
	private void setProperties(Map _properties, boolean _overridable) throws DBComponentException
	{
    
	    if (null == project) 
	    	throw new DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION + DBConstants.DB_PROJECT + project + "in setProperties");
		
	    
	    if (null == _properties) 
	    	throw new DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION + DBConstants.DB_PROPERTIES+ _properties +"in setProperties");
	    
	    java.util.Set propertyNames = _properties.keySet();
	    Iterator iter = propertyNames.iterator();
	    
	    while (iter.hasNext())
	    {
	        String propertyName =  (String) iter.next();
	        String propertyValue = (String) _properties.get(propertyName);
	        if (null == propertyValue) 
	        	continue;
	 
	        if (_overridable) 
	    		project.setProperty(propertyName, propertyValue);
	        else 
	        	project.setUserProperty(propertyName, propertyValue);
	    }
	}

	private void runTarget(String _target) throws DBComponentException
	{
	    if (null == project) 
	    	throw new DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION + DBConstants.DB_PROJECT+ project + "in runTarget");
		
	    if (null == _target) 
	    	_target = project.getDefaultTarget();
	   
	    try 
	    { 
	    	
	    	//Start Changes by netram sahu on 4-May-2012, Updated resource jar file for data type changes 
		    
	    	File dir = new File(DBConstants.EIBDBComponent);
			dir.delete();
	    	project.executeTarget(_target);
	    	
	    	//End Changes by netram sahu

	    	
		}
	    catch (BuildException e)
	    { 
	    	e.printStackTrace();
	    	//throw new DBComponentException(DBExceptionConstants.BUILD_EXCEPTION  +  _target + " in runTarget"); 
		}
	}
}
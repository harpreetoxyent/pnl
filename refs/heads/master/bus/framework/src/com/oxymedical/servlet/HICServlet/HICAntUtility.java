package com.oxymedical.servlet.HICServlet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;


public class HICAntUtility
{
	private Project project;
/*
	public static void main(String[] args)
	{
		String serverDirectory="C:/apache-tomcat-5.5.20/webapps";
		String serverLibDirectory="C:/apache-tomcat-5.5.20/common/lib/ext";
		String serverAddress="http://"+ System.getenv("SERVER_ADDRESS") +"/";
		String sourceDirectory="C:/gip/";
		String applicationName= "gip";
		HICAntUtility antUtil = new HICAntUtility();
		antUtil.integrateHIC(sourceDirectory, applicationName, serverDirectory, serverAddress, serverLibDirectory);
	}*/
	public void integrateHIC(String sourceDirectory, 
			String applicationName, String serverDirectory)
	{
		try
		{
			init("build.xml",sourceDirectory);   
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("basedir", sourceDirectory);
			m.put("sourcedir", sourceDirectory);			
			m.put("applicationname", applicationName);
			m.put("servdir", serverDirectory);
			setProperties(m, false);
			runTarget("wait");
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace(); 
		}
	}
	public void init(String _buildFile, String _baseDir) throws Exception
	{
		project = new Project();
	    try 
	    { 
	    	project.init(); 
	    }
	    catch (BuildException e)
	    { 
	    	throw new Exception("The default task list could not be loaded."); 
	    }
	    
	    if (_baseDir == null)
	    {
	    	_baseDir = new String(".");
	    }
	
	    try 
	    {	 
	    	project.setBasedir(_baseDir); 
	    }
	    catch (BuildException e)
	    { 
	    	throw new Exception("The given basedir doesn't exist, or isn't a directory."); 
	    }
	
	    if (_buildFile == null) 
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
			while ((line = br.readLine()) != null) 
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
	        ProjectHelper.getProjectHelper().parse(project, buildFile);
	    }
	    catch (BuildException e)
	    { 
	    	e.printStackTrace();
	    	throw new Exception("Configuration file " + _buildFile +" isinvalid, or cannot be read."); 
	    }
	}	
	public void setProperties(Map _properties, boolean _overridable) throws Exception
	{
    
	    if (project == null) 
	    	throw new Exception("Properties cannot be set because the project has not been initialized. Please call the 'init' method first !");
	
	    
	    if (_properties == null) 
			throw new Exception("The provided property map is null.");
	    
	    java.util.Set propertyNames = _properties.keySet();
	    Iterator iter = propertyNames.iterator();
	    while (iter.hasNext())
	    {
	        String propertyName =  (String) iter.next();
	        String propertyValue = (String) _properties.get(propertyName);
	        if (propertyValue == null) 
	        	continue;
	 
	        if (_overridable) 
	    		project.setProperty(propertyName, propertyValue);
	        else 
	        	project.setUserProperty(propertyName, propertyValue);
	    }
	}
	public void runTarget(String _target) throws Exception
	{
	    if (project == null) 
	    	throw new Exception("No target can be launched because the project has not been initialized. Please call the 'init' method first !");
	
	    if (_target == null) 
	    	_target = project.getDefaultTarget();
	   
	    try 
	    { 
	    	project.executeTarget(_target);  
		}
	    catch (BuildException e)
	    { 
	    	throw new Exception(e.getMessage()); 
		}
	}

}

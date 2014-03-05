package com.oxymedical.component.renderer.Ant;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import com.oxymedical.component.renderer.Application;
import com.oxymedical.component.renderer.RendererComponent;
import com.oxymedical.component.renderer.constants.ZKConstants;
import com.oxymedical.component.renderer.uiBuilder.zk.library.UiLibrary;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.servlet.HICServlet.URLConstants;

public class HICAntUtility
{
	private Project project;

	
	public static void main(String[] args)
	{
		/*String serverDirectory="C:/apache-tomcat-5.5.20/webapps";
		String serverLibDirectory="C:/apache-tomcat-5.5.20/common/lib/ext";
		String serverAddress="http://127.0.0.1:8080/";
		String sourceDirectory="C://test/";
		String applicationName= "test";
		HICAntUtility antUtil = new HICAntUtility();
		antUtil.integrateHIC(sourceDirectory, applicationName , serverDirectory);*/
		UiLibrary ui = new UiLibrary();
		String dateDiff = ui.getDateDiff("10-05-2011", "01-05-2011");
		System.out.println("dateDiff: " + dateDiff);
	}
	
	public void integrateHIC(String sourceDirectory, 
			String applicationName, String serverDirectory)
	{
		try
		{
			//RendererComponent.logger.log(0,"applicationName="+applicationName);
			//RendererComponent.logger.log(0,"sourceDirectory="+sourceDirectory);
			RendererComponent.logger.log(0,"sourceDirectory = " +sourceDirectory);
			RendererComponent.logger.log(0,"serverDirectory = " +serverDirectory);
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
		RendererComponent.logger.log(0,"_target = " +_target);
	    if (project == null) 
	    	throw new Exception("No target can be launched because the project has not been initialized. Please call the 'init' method first !");
	
	    if (_target == null) 
	    	_target = project.getDefaultTarget();
	   
	    try 
	    { 
	    	RendererComponent.logger.log(0,"called target");
	    	project.executeTarget(_target);  
		}
	    catch (BuildException e)
	    { 
	    	throw new Exception(e.getMessage()); 
		}
	}
	
	public void createApplicationInfo(Application application )
	{
		//File file = new File("C://ApplicationInfo"); 
		  try {       
			  
			  	String persistFilePath = application.getApplicationFolderPath()+ZKConstants.MAIN_FOLDER_NAME+"/"+ ZKConstants.APP_INFO+"/"+ZKConstants.APPLICATION_INFO_FILENAME;
			  	RendererComponent.logger.log(0,"-------persistFilePath--"+persistFilePath);
		        OutputStream fout= new FileOutputStream(persistFilePath);
		        OutputStream bout= new BufferedOutputStream(fout);
		        OutputStreamWriter out 
		         = new OutputStreamWriter(bout, "8859_1");
		        
		        String serverURL = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME");
		           serverURL = serverURL.replace('\\', '/');
		        
		        out.write("<applications>\n");
		        out.write("<application>\n");
		        out.write("\t\t<" + ZKConstants.applicationName + ">" + application.getApplicationName() + "</" + ZKConstants.applicationName + ">\n");
		        out.write("\t\t<" + ZKConstants.serverLibDirectory + ">" + serverURL+"/lib/ext" + "</" + ZKConstants.serverLibDirectory + ">\n");
		        out.write("\t\t<" + ZKConstants.renderOption + ">" + "ZK" + "</" + ZKConstants.renderOption + ">\n");
		        out.write("\t\t<" + ZKConstants.sourcedir + ">" + application.getApplicationFolderPath() + "</" + ZKConstants.sourcedir + ">\n");
		        out.write("\t\t<serverDirectory>" + serverURL + "/autodeploy</serverDirectory>\n");
                out.write("\t</application>\n"); 
                out.write("</applications>\n");
	            out.flush();  // Don't forget to flush!
		        out.close();
		        
		    
		  }
		  catch(Exception e)
		  {
			 // e.printStackTrace();
			  RendererComponent.logger.log(0,e.getMessage());
		  }
		
	}
	
	
	
	
}


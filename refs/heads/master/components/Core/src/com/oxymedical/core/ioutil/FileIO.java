package com.oxymedical.core.ioutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

public class FileIO
{
	public void copyDirectory(File sourceLocation, File targetLocation) throws IOException
	{
		copyDirectory(sourceLocation, targetLocation, true);
	} 
	

	public void copyDirectory(File sourceLocation, File targetLocation, boolean overwrite) throws IOException
	{
		if (sourceLocation.isDirectory())
		{
			if (!targetLocation.exists())
			{
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++)
			{
				copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
			}
		}
		else
		{

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}


	public void copyDirectory(String srcDirectory, String destDirectory) throws IOException
	{
		copyDirectory(srcDirectory, destDirectory, true);
	}
	
	public void copyDirectory(String srcDirectory, String destDirectory, boolean overwrite) throws IOException
	{
		File sourceLocation = new File(srcDirectory);
		File targetLocation = new File(destDirectory);
		if ((!overwrite) && (targetLocation.exists())) return;
		copyDirectory(sourceLocation, targetLocation);
	}


	/**
	 * This function also handles file(s) copy from jar file to a folder
	 * @param srcDirectory
	 * @param destDirectory
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void copyDirectoryAsResource(String srcDirectory, String destDirectory) throws IOException
	{
		// Create sourceLocation File object based on srcDirectory. This is
		// required for getting Jar file name.
		File sourceLocation = new File(FileIO.class.getResource(srcDirectory).getFile());
		
		// Decode URL to replace %20 with spaces
		String jarPath = URLDecoder.decode(sourceLocation.getPath(), "utf-8");
		
		// Check if path contains a jar file in it.
		if (jarPath.indexOf(".jar!") > 0)
		{
			// Fetch jarFile and jarFolder based on jarPath
			String jarFile = jarPath.substring(0, jarPath.indexOf(".jar!") + 4);
			String jarFolder = jarPath.substring(jarPath.indexOf(".jar!") + 5);
			
			// Fetch list of file names from jar that are to be copied
			List<String> fileList = getFilePathsFromJar(jarFile, jarFolder);

			// Return if no matching file(s) found
			if (fileList == null) return;
			
			for (int i = 0; i < fileList.size(); i++)
			{				
				// Prepare destination file name by extracting file name from source fileList
				String sep = "\\";
				if (fileList.get(i).indexOf("/") > fileList.get(i).indexOf("\\")) sep = "/";
				String fileName = fileList.get(i).substring(fileList.get(i).lastIndexOf(sep));
				
				// Create File object for desination
				File targetLocation = new File(destDirectory + fileName);
				
				// Create input and output streams. Folder for input stream
				// should start with "/" otherwise it starts looking from
				// current folder and not from root
				InputStream in = FileIO.class.getResourceAsStream("/" + fileList.get(i));
				OutputStream out = new FileOutputStream(targetLocation);

				// Copy the bits from instream to outstream
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0)
				{
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
		else
		{
			// If srcDirectory is not present in jar, proceed with normal file
			// to file copy
			copyDirectory(srcDirectory, destDirectory);
		}
	}

	private List<String> getFilePathsFromJar(String _jarPath, String _jarFolder)
	{
		List<String> retVal = null;
		try
		{
			// Remove "file:/" from path; also if jarFolder starts with "/" remove it
			if (_jarPath.indexOf("file:") >= 0) _jarPath = _jarPath.substring("file:/".length());
			if ((_jarFolder.indexOf("/") == 0) || (_jarFolder.indexOf("\\") == 0)) _jarFolder = _jarFolder.substring(1);
			
			// For Mac, so that it looks at root
			// For Windows, it should be C:\glassfish\domains\...
			// For Mac, it should be /glassfish/domains/...
			if (_jarPath.indexOf(":") < 0) _jarPath = "/" + _jarPath;

			JarFile jFile = new JarFile(_jarPath);
			Enumeration<JarEntry> jarEntries = jFile.entries();
			
			// Enumerate the entries
			retVal = readJarEnumeration(jarEntries, _jarFolder);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return retVal;
	}
   
	private List<String> readJarEnumeration(Enumeration<JarEntry> jarEntries, String _jarFolder)
	{
		List<String> fileList = null;
		try
		{
			// Format _jarFolder
			_jarFolder = _jarFolder.replace('\\', '/');
			if (_jarFolder.lastIndexOf('/') != _jarFolder.length()-1) _jarFolder = _jarFolder.concat("/");
			
			while (jarEntries.hasMoreElements())
			{
				ZipEntry entry = (ZipEntry) jarEntries.nextElement();
				String entryName = entry.getName().replace('\\', '/');

				// If entry is pertaining to desired folder, move further
				if (entryName.indexOf(_jarFolder) >= 0)
				{
					// We don't want directories, only files
					if (!entry.isDirectory())
					{
						// Add to fileList
						if (fileList == null) fileList = new ArrayList<String>();
						fileList.add(entry.getName());
					}
				}
			}
		}
		catch (Exception e)
		{
		}
		return fileList;
	} 

	
   /**
     * this method is used to copy the files from the source location into the target location
	 * @param String srcFile
	 * @param String targetLocation
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void copyFileAsResource(String srcFile,String targetLocation) throws FileNotFoundException, IOException
	{
		InputStream in = FileIO.class.getResourceAsStream(srcFile);
		if (in == null) throw new FileNotFoundException(srcFile + ": file not found");
		
		OutputStream out = null;
		try 
		{
			out = new FileOutputStream(targetLocation);
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			throw e;
		}
		catch (IOException e) 
		{
			throw e;
		}
		
	}

	public boolean writeToFile(String filePath, String completeFileName, String fileContents)
	{
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object
        
		try
		{
			out = new FileOutputStream(filePath + completeFileName);
			p = new PrintStream( out );
			p.println (fileContents);
			p.close();
		}
		catch(FileNotFoundException e)
		{
			
		}		
        return true;
	}	
		
	public boolean createFolder(String filePath, String folderName)
	{	
		String completeFileName = filePath + folderName;
		File file = new File(completeFileName);
		if(!file.exists())
		{
			return file.mkdirs();
		}
		return true;
	}	
	
	/**
	 * This method reads a jar and gets the contents inside the jar.
	 * @param _jarPath
	 */
	public static void readJar(String _jarPath){
		  try{
		    JarFile jFile = new JarFile(_jarPath);
		    Enumeration jarEntries = jFile.entries();
		    readEnumeration(jarEntries);
		  }catch(Exception e){
			  
		  }
	}
		
	private static void readEnumeration(Enumeration jarEntries){
	  try{
	    while (jarEntries.hasMoreElements()){
	      ZipEntry entry=(ZipEntry)jarEntries.nextElement();
	      	if(!entry.isDirectory()){
	    	  	String name=entry.getName().replace('/','.');
		        if(name.endsWith(".class")){ // / classes inside the jar
		          name=name.substring(0,name.length()-".class".length());
		          Class cls=null;
		          try{
		            cls=Class.forName(name);
		          }catch(Exception e){
		          }
		          if(cls!=null){
		            //do something with the class cls
		          }
		        }
		        //handle jar-files !!!
		        else if(name.endsWith(".jar")){
		          //do the same as above - open a JarFile
		          JarFile jFile=new JarFile("");//uncertain how you do here
		          Enumeration otherEntries=jFile.entries();
		          readEnumeration(otherEntries);	 
		        }
	      	}
	    }
	  }catch(Exception e){
	  }
	} 

	/**
	 * This method gets all the files in the directory with the given extension.
	 * 
	 * @param _dirPath
	 * @param _extn
	 * @return
	 */
	public static List readDirectory(String _dirPath, String _extn)
	{
		File dir = new File(_dirPath);
		List<String> fileNameList = null;
		if (null != dir)
		{
			fileNameList = new ArrayList<String>();
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				String name = files[i].getName().replace('/', '.');
				if ((_extn == null) || ((_extn != null) && (name.endsWith(_extn))))
				{
					fileNameList.add(name);
				}
			}
		}
		return fileNameList;
	}

	private static List readFilepathRecursively(String _dirPath, String _extn, List<String> filepathList)
	{
		File dir = new File(_dirPath);
		if (null != dir) {
			if(filepathList == null)
				filepathList = new ArrayList<String>(); 
			File[] files = dir.listFiles();			
			for(int i=0; i < files.length; i++)
			{
				if(files[i].isFile())
				{
					String path = files[i].getPath();				
			        if (path.endsWith(_extn)){
			        	filepathList.add(path);
			        }
				}
				else if(files[i].isDirectory())
				{
					readFilepathRecursively(files[i].getPath(), _extn, filepathList);
		        }
		    }
		}
		return filepathList;
	}

	public static List readFilepathRecursively(String _dirPath, String _extn)
	{
		List<String> filepath = null;
		return readFilepathRecursively(_dirPath, _extn, filepath);
	}
	/**
	 * This method gets all the files in the directory irrespective of any file
	 * extension.
	 * 
	 * @param _dirPath
	 * @return
	 */
	public static List readFullDirectory(String _dirPath)
	{
		File dir = new File(_dirPath);
		List<String> fileNameList = null;
		if (null != dir)
		{
			fileNameList = new ArrayList<String>();
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				String name = files[i].getName();
				fileNameList.add(name);
			}
		}
		return fileNameList;
	}

	/**
     * this method is used create the Package Structure 
	 * @param String path
	 * @param String packageName
	 * @return
	 */
	public Boolean createPackageStructure(String path, String packageName)
	{
		Boolean ret = false;
		Pattern p = Pattern.compile(".",  Pattern.LITERAL);
		String[] ptStr = p.split(packageName);
		try
		{
			for(int count=0;count<ptStr.length;count++)
			{
				this.createFolder(path, ptStr[count]);
				path =  path + ptStr[count] + "/";
			}
			ret = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ret=false;
		}
		return ret;
	}

	/**
	 * Reads file and return its contents. After every line read it appends a
	 * line.separator so as to return accurate file contents.
	 * 
	 * @param filePath - Path of file
	 * @param completeFileName - File name including file extension
	 * @return
	 */
	public String readFromFile(String filePath, String completeFileName)
	{
		return readFromFile(filePath + completeFileName);
	}	

	/**
	 * Reads file and return its contents. After every line read it appends a
	 * line.separator so as to return accurate file contents.
	 * 
	 * @param fileWithPath - Complete file name with file path
	 * @return
	 */
	public String readFromFile(String fileWithPath)
	{
        StringBuffer sbfr = new StringBuffer();
		InputStream in = null;
		BufferedReader br = null;
        
		try
		{
			in = FileIO.class.getResourceAsStream(fileWithPath);
		    br = new BufferedReader(new InputStreamReader(in));

	        String str;
	        while ((str = br.readLine()) != null) 
	        {
	        	sbfr.append(str).append(System.getProperty("line.separator"));
	        }
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (br != null) br.close();
				if (in != null) in.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
        return sbfr.toString();
	}

	/**
	 * Moves file from one location to other
	 * @param sourcefile
	 * @param targetLocation
	 */
	public static void moveFolder(String sourcefile, String targetLocation,boolean isDeleted)
	{
	    File file = new File(sourcefile);
	    File fileOutput = new File(targetLocation);
	    InputStream in;
		try 
		{
			in = new FileInputStream(file);
			OutputStream out = new FileOutputStream(fileOutput);
		    byte[] buf = new byte[1024];
		    int len;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
		   in.close();
		   out.close();
		   if(isDeleted)
		   {
			   if(file.exists())
			   {
				   file.delete();
			   }
		   }
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	
	public static void main(String args[]) 
	{
		FileIO fileIO = new FileIO();
		/*File inputFile = new File("c:/hello");
		File outputFile = new File("c:/hellotest");*/
		try
		{
			String jar = "file:\\D:\\temp\\cruisecontrol\\projects\\NOLIS\\dev\\src\\main\\Global Jars\\Internal Jar\\renderzk.jar";
			String folder = "\\com\\oxymedical\\component\\renderer\\uiBuilder\\zk\\style\\buttoncoolblue";
			List<String> fileList = fileIO.getFilePathsFromJar(jar, folder);
			System.out.println("[]" + fileList.size());
		}
		catch(Exception e)
		{
			
		}
	}
}

package com.pnl.component.crawler.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.oxymedical.core.propertyUtil.PropertyUtil;

public class Utility {
	
	public static boolean copyFileToHDFS(String source, String dest) throws IOException {
        Configuration conf = new Configuration();
System.out.println("Source Path===>"+source+"-----Destination Path===>"+dest);
    
        // Conf object will read the HDFS configuration parameters from these
        // XML files.
        String hadoopHome = PropertyUtil.setUpProperties("HADOOP_HOME");
        System.out.println(hadoopHome);
        conf.addResource(new Path(hadoopHome+"/conf/core-site.xml"));
        conf.addResource(new Path(hadoopHome+"/conf/hdfs-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        // Get the filename out of the file path
        String filename = source.substring(source.lastIndexOf('/') + 1,
            source.length());

        // Create the destination path including the filename.
        if (dest.charAt(dest.length() - 1) != '/') {
            dest = dest + "/" + filename;
        } else {
            dest = dest + filename;
        }

        // System.out.println("Adding file to " + destination);

        // Check if the file already exists
        Path path = new Path(dest);
        if (fileSystem.exists(path)) {
            System.out.println("File " + dest + " already exists in hdfs.");
            return false;
        }

        // Create a new file and write data to it.
        FSDataOutputStream out = fileSystem.create(path);
        InputStream in = new BufferedInputStream(new FileInputStream(
            new File(source)));

        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = in.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        // Close all the file descripters
        in.close();
        out.close();
        fileSystem.close();
        return true;
    }
  public static String createFile(String name, String content)
  {
	File file = new File(name);
	try {
		 RandomAccessFile raf = new RandomAccessFile(file,"rw");
		 String[] urls = content.split(",");
		 for(int i=0;i<urls.length;i++)
		 {
			 raf.write(urls[i].getBytes());	
			 if(i<urls.length-1)
			 {
			  raf.write("\n".getBytes());
			 }
		 }
		 raf.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return file.getAbsolutePath();
	  
  }
}

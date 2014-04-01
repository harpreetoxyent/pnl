package com.pnl.component.bigdata.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ProcessReducer extends Reducer<Text, Text, Text, Text> {
	// public static final Logger LOG =
	// LoggerFactory.getLogger(ProcessReducer.class);
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		Configuration conf = context.getConfiguration();
		FileSystem fileSystem = FileSystem.get(conf);
		String hadoopFSDefault = conf.get("hadoopFSDefault");
		System.out.println("\n\n\nhapdoopFSDefault=====>" + hadoopFSDefault);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		for (Text value : values) {
			try {
//System.out.println("Values=====>"+value);
				Source xslDoc = new StreamSource("/usr/oxyent/bigData/CleanupWikiState.xsl");

				//Source xmlDoc = new StreamSource("/usr/oxyent/bigData/wikiWisconsin.xml");
				String str = value.toString();
				str=str.replace("<!DOCTYPE html>", "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
				str=str.replace("<html lang=\"en\" dir=\"ltr\" class=\"client-nojs\">", "<html xmlns=\"http://www.w3.org/1999/xhtml\">");
				Source xmlDoc = new StreamSource(new ByteArrayInputStream(str.getBytes()));

				String outputFileName = "/usr/oxyent/bigData/infoNew.xml";

				OutputStream htmlFile = new FileOutputStream(outputFileName);
				
				Transformer trasform;
				trasform = tFactory.newTransformer(xslDoc);

				trasform.transform(xmlDoc, new StreamResult(htmlFile));


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("End" + new Date().getTime());
			break;
		}

	}
}
package com.pnl.component.bigdata.processor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class ProcessMapper extends Mapper<LongWritable, Text, Text, Text> {
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		System.out.println("ProcessMapper.....");
		// LOG.info("value=>"+value+"\n\n\n\n");
		// System.out.println("value=>"+value+"\n\n\n\n");
		// context.write(new Text("key"), new Text(value.getContent()));
		// context.write(new Text("key"), new Text(value.getBytes()));

		Configuration conf = context.getConfiguration();
		String uID = conf.get("uID");
		FileSystem fileSystem = FileSystem.get(conf);
		String hadoopFSDefault = conf.get("hadoopFSDefault");
		System.out.println("\n\n\nhapdoopFSDefault=====>" + hadoopFSDefault);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		InputStream in = null;
		try {
			
			String filename=  ((FileSplit)context.getInputSplit()).getPath().getName();//context.getConfiguration().get("map.input.file");
			filename = filename.replace('-', '/');
			filename = filename.replace('_', ':');
			System.out.println("File name ====> "+filename);
			in = ProcessMapper.class
					.getResourceAsStream("/com/pnl/component/bigdata/xslt/CleanupWikiState.xsl");
			Source xslDoc = new StreamSource(in);
			
			String str = parser(value,filename);
					
			Source xmlDoc = new StreamSource(new ByteArrayInputStream(
					str.getBytes()));

			String outputFileName = "/usr/oxyent/testrun/"+uID+"/bigdata/savedsites/"+new Date().getTime()+".xml";

			OutputStream htmlFile = fileSystem.create(new Path(outputFileName));// new
																				// FileOutputStream(outputFileName);

			Transformer trasform;
			trasform = tFactory.newTransformer(xslDoc);

			trasform.transform(xmlDoc, new StreamResult(htmlFile));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End" + new Date().getTime());
	}
	
	private String parser(Text value,String url)
	{
		String str = value.toString();
		str = str
				.replace(
						"<!DOCTYPE html>",
						"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		
		Document doc = Jsoup.parse(str);
		doc.select("script,jscript,br,hr,link").remove();

		removeComments(doc);
		Elements ele = doc.getElementsByTag("html");
		Element el = ele.get(0);
		Attributes atbr = el.attributes();
		
		    for (Attribute a : atbr) {
		        el.removeAttr(a.getKey());
		    }
		ele = doc.getElementsByTag("img");
		    for (Element e : ele) {
		        atbr = e.attributes();
		        for (Attribute a : atbr) {
		        	if(!a.getKey().equals("src"))
		            e.removeAttr(a.getKey());
		        }
		    }
		    
		 ele = doc.getElementsByTag("a");
		    for (Element e : ele) {
		        atbr = e.attributes();
		        for (Attribute a : atbr) {
		        	if(!a.getKey().equals("href"))
		            e.removeAttr(a.getKey());
		        }
		    }
	
		atbr.put("xmlns", "http://www.w3.org/1999/xhtml");
		doc.head().append("<url>"+url+"</url>");
		doc.outputSettings().charset("UTF-8");
		
		
		str = doc.toString();//StringEscapeUtils.unescapeHtml(doc.toString());
		str=str.replaceAll("& ", "&amp;");
		return str;
	}
	
	 private void removeComments(Node node) {
	        for (int i = 0; i < node.childNodes().size();) {
	            Node child = node.childNode(i);
	            if (child.nodeName().equals("#comment"))
	                child.remove();
	            else {
	                removeComments(child);
	                i++;
	            }
	        }
	    }
}
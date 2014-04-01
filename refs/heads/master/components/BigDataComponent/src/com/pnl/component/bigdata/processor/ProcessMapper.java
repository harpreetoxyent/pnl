package com.pnl.component.bigdata.processor;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.nutch.protocol.Content;

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
		FileSystem fileSystem = FileSystem.get(conf);
		String hadoopFSDefault = conf.get("hadoopFSDefault");
		System.out.println("\n\n\nhapdoopFSDefault=====>" + hadoopFSDefault);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			// System.out.println("Values=====>"+value);
			String file="/usr/oxyent/bigData/CleanupWikiState.xsl";
			Path path = new Path(file);
			if (!fileSystem.exists(path)) {
				System.out.println("File does not exists");
				return;
			}

			FSDataInputStream in = fileSystem.open(path);

			//String filename = file.substring(file.lastIndexOf('/') + 1,
			//		file.length());

			//OutputStream out = new BufferedOutputStream(new FileOutputStream(
			//		new File(filename)));

			//Source xslDoc = new StreamSource("/usr/oxyent/bigData/CleanupWikiState.xsl");
			Source xslDoc = new StreamSource(in);

			// Source xmlDoc = new
			// StreamSource("/usr/oxyent/bigData/wikiWisconsin.xml");
			String str = value.toString();
			str = str
					.replace(
							"<!DOCTYPE html>",
							"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			str = str.replace(
					"<html lang=\"en\" dir=\"ltr\" class=\"client-nojs\">",
					"<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			Source xmlDoc = new StreamSource(new ByteArrayInputStream(
					str.getBytes()));

			String outputFileName = "/usr/oxyent/bigData/savedsites/infoNew123.xml";

			OutputStream htmlFile =fileSystem.create(new Path(outputFileName));// new FileOutputStream(outputFileName);

			Transformer trasform;
			trasform = tFactory.newTransformer(xslDoc);

			trasform.transform(xmlDoc, new StreamResult(htmlFile));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End" + new Date().getTime());
	}
}
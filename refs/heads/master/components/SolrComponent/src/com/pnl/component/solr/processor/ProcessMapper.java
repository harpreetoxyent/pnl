package com.pnl.component.solr.processor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import com.pnl.component.solr.util.SimplePostTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessMapper extends
		Mapper<LongWritable, Text, NullWritable, NullWritable> {
	public static final Logger LOG = LoggerFactory
			.getLogger(ProcessMapper.class);

	@Override
	public void setup(org.apache.hadoop.mapreduce.Mapper.Context context) {
		
		System.out.println("Setup called....");
		/*
		 * String url = "http://localhost:8983/solr"; fileName =
		 * job.get("map.input.file").substring(
		 * (job.get("map.input.file")).lastIndexOf(
		 * System.getProperty("file.separator")) +1); try { server = new
		 * StreamingUpdateSolrServer(url, 100, 5); } catch
		 * (MalformedURLException e) { e.printStackTrace(); }
		 */
	}

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		LOG.info("Map starts...");
		//thisDoc = new SolrInputDocument();
		InputStream is = new ByteArrayInputStream(value.getBytes());
		final StringWriter sw = new StringWriter();
		SimplePostTool.indexData(new InputStreamReader(is));
		LOG.info("End...");
	}

	@Override
	public void cleanup(org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException {
		System.out.println("CleanUp called....");
		try {
			//server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
package com.pnl.component.solr.processor;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.StreamingUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessMapper extends
		Mapper<LongWritable, Text, NullWritable, NullWritable> {
	private StreamingUpdateSolrServer server = null;
	private SolrInputDocument thisDoc = new SolrInputDocument();
	private String fileName;
	private StringTokenizer st = null;
	private int lineCounter = 0;
	public static final Logger LOG = LoggerFactory
			.getLogger(ProcessMapper.class);

	@Override
	public void setup(org.apache.hadoop.mapreduce.Mapper.Context context) {
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
		System.out.println("Mapper");
		st = new StringTokenizer(value.toString());
		lineCounter = 0;
		LOG.info("Map starts...");
		thisDoc = new SolrInputDocument();
		/*
		 * while (st.hasMoreTokens()) { thisDoc = new SolrInputDocument();
		 * LOG.info("value===>"+st.nextToken()); thisDoc.addField("id", fileName
		 * + " " + key.toString() + " " + lineCounter++);
		 * thisDoc.addField("text", "text");
		 * 
		 * try { server.add(thisDoc); } catch (SolrServerException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 * }
		 */
		server.setRequestWriter(new BinaryRequestWriter());
		LOG.info("End...");
	}

	@Override
	public void cleanup(org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException {
		try {
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
}
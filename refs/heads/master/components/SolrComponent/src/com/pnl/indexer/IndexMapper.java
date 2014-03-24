package com.pnl.indexer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.StreamingUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class IndexMapper extends MapReduceBase implements Mapper <LongWritable, Text, NullWritable, NullWritable> 
{
 private StreamingUpdateSolrServer server = null;
 private SolrInputDocument thisDoc = new SolrInputDocument();
 private String fileName;
 private StringTokenizer st = null;
 private int lineCounter = 0;
 public static final Logger LOG = LoggerFactory.getLogger(IndexMapper.class);
 @Override
 public void configure(JobConf job) {
   String url = "http://localhost:8983/solr";
   fileName = job.get("map.input.file").substring(
     (job.get("map.input.file")).lastIndexOf(
     System.getProperty("file.separator")) +1);
     try {
       server = new StreamingUpdateSolrServer(url, 100, 5);
     } catch (MalformedURLException e) {
       e.printStackTrace();
     }
 }

 @Override
 public void map(LongWritable key, Text val, OutputCollector <NullWritable, NullWritable> output, Reporter reporter) throws IOException {
System.out.println("Mapper");
   st = new StringTokenizer(val.toString());
   lineCounter = 0;
   LOG.info("Map starts...");
   thisDoc = new SolrInputDocument();
   /*while (st.hasMoreTokens()) 
   {
     thisDoc = new SolrInputDocument();
     LOG.info("value===>"+st.nextToken());
     thisDoc.addField("id", fileName + " " + key.toString() + " " + lineCounter++);
     thisDoc.addField("text", "text");
     
     try {
       server.add(thisDoc);
     } catch (SolrServerException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }*/
   server.setRequestWriter(new BinaryRequestWriter());
   LOG.info("End...");
 }

 @Override
 public void close() throws IOException {
 try {
     server.commit();
   } catch (SolrServerException e) {
     e.printStackTrace();
   }
 }
}
package com.pnl.component.crawler;

import java.util.*;
import java.io.IOException;
import java.text.*;

// Commons Logging imports
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.parse.ParseSegment;
import org.apache.nutch.indexer.IndexingJob;
import org.apache.nutch.indexer.solr.SolrDeleteDuplicates;
import org.apache.nutch.util.HadoopFSUtil;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.nutch.util.NutchJob;

import org.apache.nutch.crawl.CrawlDb;
import org.apache.nutch.crawl.Generator;
import org.apache.nutch.crawl.Injector;
import org.apache.nutch.crawl.LinkDb;
import org.apache.nutch.fetcher.Fetcher;
import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.pnl.component.crawler.exception.CrawlerComponentException;
import com.pnl.component.crawler.exception.CrawlerExceptionConstants;
import com.pnl.component.crawler.processor.ProcessMapper;
import com.pnl.component.crawler.utilities.Utility;


public class CrawlerComponent extends Configured implements ICrawlerComponent,IComponent {
	  public static final Logger LOG = LoggerFactory.getLogger(CrawlerComponent.class);

	  private static String getDate() {
	    return new SimpleDateFormat("yyyyMMddHHmmss").format
	      (new Date(System.currentTimeMillis()));
	  }


	  /* Perform complete crawling and indexing (to Solr) given a set of root urls and the -solr
	     parameter respectively. More information and Usage parameters can be found below. */
	  @EventSubscriber(topic = "executeCrawler")
	  public void process(HICData hicData) throws CrawlerComponentException {
		IData data = hicData.getData();
		String urls="";
		String depth="";
		String topN="";
		String jobTracker=PropertyUtil.setUpProperties("HADOOP_JOB_TRACKER");
		String fsName=PropertyUtil.setUpProperties("HADOOP_FS_DEFAULT_NAME");
		try
		{
		  urls = data.getFormPattern().getFormValues().get("searchTextBox").toString().trim();
		  depth = data.getFormPattern().getFormValues().get("depth").toString().trim();
		  topN = data.getFormPattern().getFormValues().get("topN").toString().trim();
		}
		catch(NullPointerException exce)
		{
			System.out.println("NullPointerException: CrawlerComponent.process()");
		}
		if(urls.lastIndexOf(",")==urls.length()-1)
		{
			urls=urls.substring(0,urls.length()-1);
		}
		String source = Utility.createFile("seedDummy.txt", urls);
		//System.out.println("Created File in file system.");
		String destination = "/usr/oxyent/demo2/";
		try {
			Utility.copyFileToHDFS(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out
				.println("------------Inside execute of Crawler Component---+ data="
						+ urls+"-----"+depth+"--topN---"+topN);
		try
		{
		Configuration conf = new Configuration();
		// this should be like defined in your mapred-site.xml
		conf.set("fs.default.name", fsName);
		// like defined in hdfs-site.xml
		conf.set("mapred.job.tracker", jobTracker);
		
	    // Replace CallJobFromServlet.class name with your servlet class
	        Job job = new Job(conf, "Crawler Component");
	        job.getConfiguration().set("depth", depth);
	        job.getConfiguration().set("topN", topN);
	        job.getConfiguration().set("rootUrl", destination);
	        job.setJarByClass(CrawlerComponent.class);
	        job.setJobName("Job Name");
	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(Text.class);
	        job.setMapperClass(ProcessMapper.class); // Replace Map.class name with your Mapper class

	        job.setMapOutputKeyClass(Text.class);
	        job.setMapOutputValueClass(Text.class);
	        
	        // Job Input path
	        FileInputFormat.setInputPaths(job, new Path(fsName+"/usr/oxyent/demo/")); 
	        // Job Output path
	        FileOutputFormat.setOutputPath(job, new Path(fsName+"/usr/oxyent/demo1/")); 

	        job.waitForCompletion(true);
		}catch(Exception exce)
		{
			exce.printStackTrace();
			throw new CrawlerComponentException(exce);
		}
	  }
	  
	@Override
	public void start(Hashtable<String, Document> configData) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run() throws ComponentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stop() throws ComponentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void destroy() throws ComponentException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public IHICData getHicData() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setHicData(IHICData hicData) {
		// TODO Auto-generated method stub
		
	}


	@Override
	@MaintenancePublisher
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub
		
	}
}
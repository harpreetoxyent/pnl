package com.pnl.component.crawler;

import java.util.*;
import java.io.IOException;
import java.text.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.Application;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;
import com.pnl.component.crawler.exception.CrawlerComponentException;
import com.pnl.component.crawler.processor.ProcessMapper;
import com.pnl.component.crawler.utilities.Utility;

public class CrawlerComponent extends Configured implements ICrawlerComponent,
		IComponent {
	public static final Logger LOG = LoggerFactory
			.getLogger(CrawlerComponent.class);

	private static String getDate() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System
				.currentTimeMillis()));
	}

	/*
	 * Perform complete crawling and indexing (to Solr) given a set of root urls
	 * and the -solr parameter respectively. More information and Usage
	 * parameters can be found below.
	 */
	@EventSubscriber(topic = "executeCrawler")
	public IHICData process(IHICData hicData) throws CrawlerComponentException {
		IData data = hicData.getData();
		String urls = "";
		String depth = "";
		String topN = "";
		String nutchPlugin="";
		long uID = new Date().getTime();
		String jobTracker = PropertyUtil.setUpProperties("HADOOP_JOB_TRACKER");
		String fsName = PropertyUtil.setUpProperties("HADOOP_FS_DEFAULT_NAME");
		nutchPlugin=PropertyUtil.setUpProperties("NUTCH_PLUGIN_LOCATION");
		try {
			urls = data.getFormPattern().getFormValues().get("searchTextBox")
					.toString().trim();
			depth = data.getFormPattern().getFormValues().get("depth")
					.toString().trim();
			topN = data.getFormPattern().getFormValues().get("topN").toString()
					.trim();
		} catch (NullPointerException exce) {
			System.out
					.println("NullPointerException: CrawlerComponent.process()");
		}
		if (urls.lastIndexOf(",") == urls.length() - 1) {
			urls = urls.substring(0, urls.length() - 1);
		}
		String source = Utility.createFile("seedDemo.txt", urls);
		// System.out.println("Created File in file system.");
		String destination = "/usr/oxyent/testrun/"+uID+"/demo2/";
		try {
			Utility.copyFileToHDFS(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out
				.println("------------Inside execute of Crawler Component---+ data="
						+ urls + "-----" + depth + "--topN---" + topN);
		try {
			Configuration conf = new Configuration();
			// this should be like defined in your mapred-site.xml
			conf.set("fs.default.name", fsName);
			// like defined in hdfs-site.xml
			conf.set("mapred.job.tracker", jobTracker);
			conf.set("nutchPlugin", nutchPlugin);
			// Replace CallJobFromServlet.class name with your servlet class
			Job job = new Job(conf, "Crawler Component");
			job.getConfiguration().set("depth", depth);
			job.getConfiguration().set("topN", topN);
			job.getConfiguration().set("rootUrl", destination);
			job.getConfiguration().set("contentDirectory", "/app/hadoop/tmp/savedsites/");
			job.setJarByClass(CrawlerComponent.class);
			job.setJobName("Crawler");
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			job.setMapperClass(ProcessMapper.class); // Replace Map.class name
														// with your Mapper
														// class

			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);

			// Job Input path
			FileInputFormat.setInputPaths(job, new Path(fsName
					+ "/usr/oxyent/testrun/"+uID+"/demo2/"));
			job.getConfiguration().set("outputDir", "/usr/oxyent/testrun/"+uID+"/demo2/");
			// Job Output path
			FileOutputFormat.setOutputPath(job, new Path(fsName
					+ "/usr/oxyent/testrun/"+uID+"/demo1/"));
			System.out.println("Before calling waitforcompletion");
			job.waitForCompletion(true);
			//System.out.println("Before calling fire event...");
			hicData.getData().getFormPattern().getFormValues().put("uID",String.valueOf(uID));
			hicData.getData().getFormPattern().getFormValues().put("contentDirectory", job.getConfiguration().get("contentDirectory"));
			NOLISRuntime.FireEvent("processData", new Object[] { hicData },
					PublicationScope.Global);
			System.out.println("After calling fire event...");
		} catch (Exception exce) {
			exce.printStackTrace();
			throw new CrawlerComponentException(exce);
		}
		return hicData;
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
	public static void main(String[] args)
	{
		try
		{
			CrawlerComponent crawlComponent = new CrawlerComponent();
			Application app = new Application();
			app.setApplicationName("RecommendationEngine");
			IHICData hicData = new HICData();
			IData data = new Data();
			data.setStatus("newfact");
			data.setUserId("user");
			hicData.setUniqueID("user");
			hicData.setApplication(app);
			hicData.setData(data);		
			crawlComponent.process(hicData);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
}
package com.pnl.component.crawler;

import java.util.*;
import java.io.IOException;
import java.text.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.util.NutchConfiguration;
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
import com.pnl.component.crawler.processor.Crawl;
import com.pnl.component.crawler.utilities.Utility;

public class CrawlerComponent extends Configured implements ICrawlerComponent,
		IComponent {
	public static final Logger LOG = LoggerFactory
			.getLogger(CrawlerComponent.class);

	private static String getDate() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System
				.currentTimeMillis()));
	}

	@EventSubscriber(topic = "executeCrawler")
	public IHICData process(IHICData hicData) throws CrawlerComponentException {
		IData data = hicData.getData();

		String urls = null;
		String depth = null;
		String topN = null;
		String jobTracker = null;
		String fsName = null;
		String nutchPlugin = null;

		long uID = new Date().getTime();

		try {
			jobTracker = PropertyUtil.setUpProperties("HADOOP_JOB_TRACKER");
			fsName = PropertyUtil.setUpProperties("HADOOP_FS_DEFAULT_NAME");
			nutchPlugin = PropertyUtil.setUpProperties("NUTCH_PLUGIN_LOCATION");

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
		//String source = Utility.createFile("seedDemo.txt", urls);
		// System.out.println("Created File in file system.");
		String destination = "/usr/oxyent/testrun/" + uID + "/crawler/";
		try {
			Utility.copyFileToHDFS(urls, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out
				.println("------------Inside execute of Crawler Component---+ data="
						+ urls + "-----" + depth + "--topN---" + topN);

		// TODO Auto-generated method stub
		Configuration conf = NutchConfiguration.create();
		String[] args = new String[] { destination, "-dir", destination,
				"-depth", depth, "-topN", topN };
		conf.set("plugin.folders", nutchPlugin);
		conf.set("mapred.job.tracker", jobTracker);
		conf.set("fs.default.name", fsName);
		conf.set("contentDirectory", "/usr/oxyent/testrun/"+uID+"/savedsites/");
		System.out.println("plugin.folders====>" + nutchPlugin);
		try {
			int res = ToolRunner.run(conf, new Crawl(), args);
			// System.exit(res);
		
			// System.out.println("Before calling fire event...");
			hicData.getData().getFormPattern().getFormValues()
					.put("uID", String.valueOf(uID));
			hicData.getData().getFormPattern().getFormValues()
					.put("contentDirectory", conf.get("contentDirectory"));
			NOLISRuntime.FireEvent("processData", new Object[] { hicData },
					PublicationScope.Global);
			System.out.println("After calling fire event...");
		} catch (Exception exce) {
			exce.printStackTrace();
			throw new CrawlerComponentException(exce);
		}
		return hicData;
	}

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

	public static void main(String[] args) {
		try {
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
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
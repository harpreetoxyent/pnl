package com.pnl.component.bigdata;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;
import com.pnl.component.bigdata.exception.BigDataComponentException;
import com.pnl.component.bigdata.exception.BigDataExceptionConstants;
import com.pnl.component.bigdata.processor.ProcessMapper;
import com.pnl.component.bigdata.processor.ProcessReducer;
import com.pnl.component.bigdata.processor.WholeFileInputFormat;

public class BigDataComponent implements IBigDataComponent, IComponent {

	public static final Logger LOG = LoggerFactory
			.getLogger(BigDataComponent.class);

	@EventSubscriber(topic = "processData")
	public void process(HICData hicData) throws BigDataComponentException {

		Job job = null;
		boolean success = false;
		String inputPaths=null;
		String uID = null;
		try {
			System.out.println("process() called");
			Configuration conf = new Configuration();
			conf.addResource(new Path(PropertyUtil.setUpProperties("HADOOP_HOME")+"/conf/core-site.xml"));
			conf.addResource(new Path(PropertyUtil.setUpProperties("HADOOP_HOME")+"/conf/hdfs-site.xml"));
			String jobTracker = PropertyUtil
					.setUpProperties("HADOOP_JOB_TRACKER");
			String fsName = PropertyUtil
					.setUpProperties("HADOOP_FS_DEFAULT_NAME");
			// this should be like defined in your mapred-site.xml
			conf.set("fs.default.name", fsName);
			// like defined in hdfs-site.xml
			conf.set("mapred.job.tracker", jobTracker);

			job = new Job(conf, "DataProcessor");
			job.setJarByClass(BigDataComponent.class);
			job.getConfiguration().set("hadoopFSDefault", fsName);
			// Job Input path
			inputPaths = (String)hicData.getData().getFormPattern().getFormValues().get("contentDirectory");
			//FileInputFormat.setInputPaths(job, new Path(fsName + "/usr/oxyent/dataprocessor/files"));
			FileInputFormat.setInputPaths(job, new Path(fsName + inputPaths));
			uID = (String)hicData.getData().getFormPattern().getFormValues().get("uID");
			job.getConfiguration().set("uID", uID);
			// Job Output path
			FileOutputFormat.setOutputPath(job, new Path(fsName
					+ "/usr/oxyent/testrun/"+uID+"/dataprocessor1/"));

			job.setMapperClass(ProcessMapper.class);
			//job.setReducerClass(ProcessReducer.class);

			//job.setInputFormatClass(SequenceFileInputFormat.class);
			job.setInputFormatClass(WholeFileInputFormat.class);
		    job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);

			//job.setOutputKeyClass(Text.class);
			//job.setOutputValueClass(Text.class);

			// job.setNumReduceTasks(3);

			success = job.waitForCompletion(true);
			hicData.getData().getFormPattern().getFormValues().put("uID",uID);
			System.out.println("Before calling indexData...");
			NOLISRuntime.FireEvent("indexData", new Object[] { hicData },
					PublicationScope.Global);
			System.out.println("After calling indexData fire event...");
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			throw new BigDataComponentException(
					BigDataExceptionConstants.IO_EXCEPTION);
		} catch (Exception exce) {
			exce.printStackTrace();
		}
		//System.exit(success ? 0 : 1);
System.out.println("Bye bye");
	}

	@Override
	public void start(Hashtable<String, org.dom4j.Document> configData) {
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

package com.pnl.component.solr;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.pnl.component.solr.processor.ProcessMapper;
import com.pnl.component.solr.exception.SolrComponentException;
import com.pnl.component.solr.exception.SolrExceptionConstants;

public class SolrComponent implements ISolrComponent, IComponent {

	@Override
	@EventSubscriber(topic = "storeData")
	public void storeData(HICData hicData) throws SolrComponentException {
		// TODO Auto-generated method stub
		Job job = null;
		boolean success = false;
		try {
			Configuration conf = new Configuration();

			String jobTracker = PropertyUtil
					.setUpProperties("HADOOP_JOB_TRACKER");
			String fsName = PropertyUtil
					.setUpProperties("HADOOP_FS_DEFAULT_NAME");

			// this should be like defined in your mapred-site.xml
			conf.set("fs.default.name", fsName);
			// like defined in hdfs-site.xml
			conf.set("mapred.job.tracker", jobTracker);

			job = new Job(conf, "solrStoreData");
			job.setJarByClass(SolrComponent.class);
			job.getConfiguration().set("hadoopFSDefault", fsName);
			
			// Job Input path
			FileInputFormat.setInputPaths(job, new Path(fsName
					+ "/usr/oxyent/dataprocessor/files"));
			// Job Output path
			FileOutputFormat.setOutputPath(job, new Path(fsName
					+ "/usr/oxyent/dataprocessor1/"));

			job.setMapperClass(ProcessMapper.class);
			
			job.setInputFormatClass(SequenceFileInputFormat.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			// job.setNumReduceTasks(3);

			success = job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new SolrComponentException(
					SolrExceptionConstants.IO_EXCEPTION);
		} catch (Exception exce) {
			exce.printStackTrace();
		}
		// System.exit(success ? 0 : 1);
	}

	@Override
	@EventSubscriber(topic = "processQuery")
	public IHICData getResult(HICData hicData) throws SolrComponentException {
		// TODO Auto-generated method stub
		return null;
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

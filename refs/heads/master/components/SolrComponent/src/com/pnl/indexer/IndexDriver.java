package com.pnl.indexer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.oxymedical.core.propertyUtil.PropertyUtil;

public class IndexDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		// TODO: Add some checks here to validate the input path
		int exitCode = ToolRunner.run(new Configuration(), new IndexDriver(),args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] args) throws Exception {

		/*
		 * Job job = new Job(); job.setJarByClass(IndexDriver.class);
		 * job.setJobName("IndexDriver"); job.setSpeculativeExecution(false);
		 * 
		 * // Set Input and Output paths FileInputFormat.setInputPaths(job, new
		 * Path(args[0])); FileOutputFormat.setOutputPath(job, new
		 * Path(args[1]));
		 * 
		 * // Use TextInputFormat
		 * job.setInputFormatClass(TextInputFormat.class);
		 * 
		 * //Mapper has no output job.setMapperClass(IndexMapper.class);
		 * 
		 * job.setMapOutputKeyClass(NullWritable.class);
		 * job.setMapOutputValueClass(NullWritable.class);
		 * job.setNumReduceTasks(0);
		 * 
		 * boolean success;
		 * 
		 * success = job.waitForCompletion(true); //System.exit(success ? 0 :
		 * 1); return success?0:1;
		 */
		String jobTracker = PropertyUtil.setUpProperties("HADOOP_JOB_TRACKER");
		String fsName = PropertyUtil.setUpProperties("HADOOP_FS_DEFAULT_NAME");
		
		JobConf conf = new JobConf(getConf(), IndexDriver.class);
		conf.setJobName("IndexDriver");
		conf.setSpeculativeExecution(false);

		// Set Input and Output paths
		FileInputFormat.setInputPaths(conf, new Path(args[0].toString()));
		FileOutputFormat.setOutputPath(conf, new Path(args[1].toString()));
		// Use TextInputFormat
		//conf.setInputFormat(TextInputFormat.class);
		conf.setInputFormat(FileInputFormat.class);

		// Mapper has no output
		conf.setMapperClass(IndexMapper.class);
		conf.setMapOutputKeyClass(NullWritable.class);
		conf.setMapOutputValueClass(NullWritable.class);
		conf.setNumReduceTasks(0);
		JobClient.runJob(conf);
		return 0;
	}
}

package com.pnl.component.bigdata;

import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.nutch.protocol.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BigData {

	public static final Logger LOG = LoggerFactory
			.getLogger(BigData.class);

	public static class Map extends Mapper<Text, Content, Text, Text> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(Text key, Content value, Context context)
				throws IOException, InterruptedException {
			// LOG.info("value=>"+value+"\n\n\n\n");
			LOG.info("Mapper...");
			context.write(new Text("key"), new Text(value.getContent()));
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			for (Text value : values) {
				LOG.info("Iteration Starts...");
				Document doc = Jsoup.parse(new String(value.getBytes()),
						"UTF-8");

				Elements title = doc.getElementsByTag("title");
				LOG.info(String.valueOf(title.size()));
				for (Element titlee : title) {
					LOG.info(titlee.html());
				}
				/*
				 * Elements info = doc.getElementsByClass("infobox");
				 * LOG.info("info length => " + info.size()); for (Element trs :
				 * info) { Elements trrs = trs.getElementsByTag("tr"); for
				 * (Element tr : trrs) { //LOG.info("=====line change====");
				 * Elements tds = tr.getElementsByTag("td"); for (Element td :
				 * tds) { LOG.info(td.text()); } } }
				 */
				Elements name = doc.getElementsByClass("country-name");
				// LOG(name.size());
				for (Element n : name) {
					// LOG.info(n.html());
					LOG.info("Country name => " + n.text());
				}
			}

		}
	}

	public static void process(String[] args) throws Exception {

		/*
		 * JobConf conf = new JobConf(DataProcessor.class);
		 * conf.setJobName("wordcount");
		 * 
		 * conf.setOutputKeyClass(Text.class);
		 * conf.setOutputValueClass(IntWritable.class);
		 * 
		 * conf.setMapperClass(Map.class); conf.setCombinerClass(Reduce.class);
		 * conf.setReducerClass(Reduce.class);
		 * 
		 * conf.setInputFormat(TextInputFormat.class);
		 * conf.setOutputFormat(TextOutputFormat.class);
		 * 
		 * FileInputFormat.setInputPaths(conf, new Path(args[0]));
		 * FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		 * 
		 * JobClient.runJob(conf);
		 */

		Job job = new Job();
		job.setJarByClass(BigData.class);
		job.setJobName("DataProcessor");
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormatClass(SequenceFileInputFormat.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// job.setNumReduceTasks(3);

		boolean success;

		success = job.waitForCompletion(true);
		System.exit(success ? 0 : 1);

	}

}

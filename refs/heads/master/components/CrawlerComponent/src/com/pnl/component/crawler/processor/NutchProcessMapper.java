package com.pnl.component.crawler.processor;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class NutchProcessMapper extends  Mapper <LongWritable, Text, NullWritable, NullWritable> 
{

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
	{
		
		// TODO Auto-generated method stub
//		Configuration conf = NutchConfiguration.create();
		Configuration hadoopConf = context.getConfiguration();
//		String depth = hadoopConf.get("depth");
//		String topN = hadoopConf.get("topN");
//		String rootUrl = hadoopConf.get("rootUrl");
//		String contentDirectory = hadoopConf.get("contentDirectory");
		String nutchPlugin = hadoopConf.get("nutchPlugin");
//		conf.set("contentDirectory", contentDirectory);
//		String outputDir = hadoopConf.get("outputDir");
//		String[] args = new String[]{rootUrl,"-dir",outputDir,"-depth",depth,"-topN",topN};
//		conf.set("plugin.folders", nutchPlugin);
		System.out.println("plugin.folders====>"+nutchPlugin);
//		try
//	    {
//			//int res = ToolRunner.run(conf, new Crawl(), args);
//	    }
//		catch(Exception exce)
//	    {
//	    	exce.printStackTrace();
//	    	//throw new CrawlerComponentException(CrawlerExceptionConstants.EXCEPTION+exce);
//	    }
		
	}
}

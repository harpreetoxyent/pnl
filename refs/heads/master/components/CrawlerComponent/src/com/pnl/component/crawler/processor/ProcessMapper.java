package com.pnl.component.crawler.processor;

import java.io.IOException;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.ToolRunner;
import org.apache.nutch.util.NutchConfiguration;


public class ProcessMapper extends  Mapper <LongWritable, Text, NullWritable, NullWritable> 
{

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
	{
		
		// TODO Auto-generated method stub
		Configuration conf = NutchConfiguration.create();
		Configuration conf1 = context.getConfiguration();
		String depth = conf1.get("depth");
		String topN = conf1.get("topN");
		String rootUrl = conf1.get("rootUrl");
		String contentDirectory = conf1.get("contentDirectory");
		conf.set("contentDirectory", contentDirectory);
		//String outputDir = rootUrl+"/output/";
		String outputDir = "/usr/oxyent/demo2";
		String[] args = new String[]{rootUrl,"-dir",outputDir,"-depth",depth,"-topN",topN};
		
	/*	java.util.Iterator<Entry<String, String>> itr=conf.iterator();
		while (itr.hasNext()) {
		      System.out.println(itr.next());
		    }*/
		try
	    {
	     int res = ToolRunner.run(conf, new Crawl(), args);
	     //System.exit(res);
	    }catch(Exception exce)
	    {
	    	exce.printStackTrace();
	    	//throw new CrawlerComponentException(CrawlerExceptionConstants.EXCEPTION+exce);
	    }
		
	}
}

package com.pnl.component.crawler;

import java.util.*;
import java.text.*;

// Commons Logging imports
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapred.*;
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
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.pnl.component.crawler.exception.CrawlerComponentException;
import com.pnl.component.crawler.exception.CrawlerExceptionConstants;

public class CrawlerComponent extends Configured implements Tool,ICrawlerComponent,IComponent {
	  public static final Logger LOG = LoggerFactory.getLogger(CrawlerComponent.class);

	  private static String getDate() {
	    return new SimpleDateFormat("yyyyMMddHHmmss").format
	      (new Date(System.currentTimeMillis()));
	  }


	  /* Perform complete crawling and indexing (to Solr) given a set of root urls and the -solr
	     parameter respectively. More information and Usage parameters can be found below. */
	  public void process(String[] args) throws CrawlerComponentException {
	    Configuration conf = NutchConfiguration.create();
	    try
	    {
	     int res = ToolRunner.run(conf, new CrawlerComponent(), args);
	     //System.exit(res);
	    }catch(Exception exce)
	    {
	    	throw new CrawlerComponentException(CrawlerExceptionConstants.EXCEPTION+exce);
	    }
	  }
	  
	  @Override
	  public int run(String[] args) throws Exception {
	    if (args.length < 1) {
	      System.out.println
	      ("Usage: Crawl <urlDir> -solr <solrURL> [-dir d] [-threads n] [-depth i] [-topN N]");
	      return -1;
	    }
	    Path rootUrlDir = null;
	    Path dir = new Path("crawl-" + getDate());
	    int threads = getConf().getInt("fetcher.threads.fetch", 10);
	    int depth = 5;
	    long topN = Long.MAX_VALUE;
	    String solrUrl = null;
	    
	    for (int i = 0; i < args.length; i++) {
	      if ("-dir".equals(args[i])) {
	        dir = new Path(args[i+1]);
	        i++;
	      } else if ("-threads".equals(args[i])) {
	        threads = Integer.parseInt(args[i+1]);
	        i++;
	      } else if ("-depth".equals(args[i])) {
	        depth = Integer.parseInt(args[i+1]);
	        i++;
	      } else if ("-topN".equals(args[i])) {
	          topN = Integer.parseInt(args[i+1]);
	          i++;
	      } else if ("-solr".equals(args[i])) {
	        solrUrl = args[i + 1];
	        i++;
	      } else if (args[i] != null) {
	        rootUrlDir = new Path(args[i]);
	      }
	    }
	    
	    JobConf job = new NutchJob(getConf());

	    if (solrUrl == null) {
	      LOG.warn("solrUrl is not set, indexing will be skipped...");
	    }
	    else {
	        // for simplicity assume that SOLR is used 
	        // and pass its URL via conf 
	        getConf().set("solr.server.url", solrUrl);
	    }

	    FileSystem fs = FileSystem.get(job);

	    if (LOG.isInfoEnabled()) {
	      LOG.info("crawl started in: " + dir);
	      LOG.info("rootUrlDir = " + rootUrlDir);
	      LOG.info("threads = " + threads);
	      LOG.info("depth = " + depth);      
	      LOG.info("solrUrl=" + solrUrl);
	      if (topN != Long.MAX_VALUE)
	        LOG.info("topN = " + topN);
	    }
	    
	    Path crawlDb = new Path(dir + "/crawldb");
	    Path linkDb = new Path(dir + "/linkdb");
	    Path segments = new Path(dir + "/segments");
	    Path indexes = new Path(dir + "/indexes");
	    Path index = new Path(dir + "/index");

	    Path tmpDir = job.getLocalPath("crawl"+Path.SEPARATOR+getDate());
	    Injector injector = new Injector(getConf());
	    Generator generator = new Generator(getConf());
	    Fetcher fetcher = new Fetcher(getConf());
	    ParseSegment parseSegment = new ParseSegment(getConf());
	    CrawlDb crawlDbTool = new CrawlDb(getConf());
	    LinkDb linkDbTool = new LinkDb(getConf());
	      
	    // initialize crawlDb
	    injector.inject(crawlDb, rootUrlDir);
	    int i;
	    for (i = 0; i < depth; i++) {             // generate new segment
	      Path[] segs = generator.generate(crawlDb, segments, -1, topN, System
	          .currentTimeMillis());
	      
	      if (segs == null) {
	        LOG.info("Stopping at depth=" + i + " - no more URLs to fetch.");
	        break;
	      }else
	      {
	    	for(int k=0;k<segs.length;k++)
	    	{
	    		LOG.info(segs[k].getName());
	    	}
	      }
	      
	      fetcher.fetch(segs[0], threads);  // fetch it
	      if (!Fetcher.isParsing(job)) {
	        parseSegment.parse(segs[0]);    // parse it, if needed
	      }
	      crawlDbTool.update(crawlDb, segs, true, true); // update crawldb
	    }
	    if (i > 0) {
	      linkDbTool.invert(linkDb, segments, true, true, false); // invert links

	      if (solrUrl != null) {
	        // index, dedup & merge
	        FileStatus[] fstats = fs.listStatus(segments, HadoopFSUtil.getPassDirectoriesFilter(fs));
	        IndexingJob indexer = new IndexingJob(getConf());
	        indexer.index(crawlDb, linkDb, 
	                Arrays.asList(HadoopFSUtil.getPaths(fstats)));

	        SolrDeleteDuplicates dedup = new SolrDeleteDuplicates();
	        dedup.setConf(getConf());
	        dedup.dedup(solrUrl);
	      }
	      
	    } else {
	      LOG.warn("No URLs to fetch - check your seed list and URL filters.");
	    }
	    if (LOG.isInfoEnabled()) { LOG.info("crawl finished: " + dir); }
	    return 0;
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
package com.pnl.manager;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pnl.util.Utility;
import com.pnl.crawler.Crawl;

public class Init {

	/**
	 * @param args
	 */
	public static final Logger LOG = LoggerFactory.getLogger(Init.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 2) {
            System.out.println("Usage: Init <local_path> <hdfs_path> [-solr solrURL] [-dir d] [-threads n] [-depth i] [-topN N]");
            System.exit(1);
        }

        try {
        	LOG.info("||STEP-1:|| Initiated:");
			boolean fileAdded=Utility.addFile(args[0], args[1]);
			LOG.info("File Added in hdfs => "+fileAdded);
			
			/*String[] arg = new String[7];
			arg[0]=args[1].substring(args[1].lastIndexOf("/")+1, args[1].length());
			System.out.println("path=>"+arg[0]);
			//demo -dir demo -depth 3 -topN 1
			arg[1]="-dir";
			arg[2]="/user/ruchi/test123/output";
			arg[3]="-depth";
			arg[4]="3";
			arg[5]="-topN";
			arg[6]="1";
			*/
			
			String[] crawlerArgs = new String[args.length-1];
			for(int i=0;i<crawlerArgs.length;i++)
			{
				crawlerArgs[i] = args[i+1];
			}
			
			Crawl.main(crawlerArgs);
		    
			LOG.info("||STEP-1:|| Completed.");
			LOG.info("||STEP-2:|| Initiated:");
		    
			//Call DataProcessor here...
		    
			LOG.info("||STEP-2:|| Completed.");
			LOG.info("||STEP-3:|| Initiated:");
			
			//Call IndexDriver here...
			
			LOG.info("||STEP-3:|| Completed.");
		    LOG.info("Done!");
			
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

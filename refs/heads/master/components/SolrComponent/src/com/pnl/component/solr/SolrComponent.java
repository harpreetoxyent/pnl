package com.pnl.component.solr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;
import com.pnl.component.solr.processor.ProcessMapper;
import com.pnl.component.solr.processor.WholeFileInputFormat;
import com.pnl.component.solr.bean.SolrResultBean;
import com.pnl.component.solr.exception.SolrComponentException;
import com.pnl.component.solr.exception.SolrExceptionConstants;

public class SolrComponent implements ISolrComponent, IComponent {

	private static int fetchSize = 1000;
	private static String url = "http://localhost:8983/solr/collection1/";
	private static HttpSolrServer solrCore;
	private static CommonsHttpSolrServer server = null;
	private static Logger logger = Logger.getLogger(SolrComponent.class
			.getName());
	public static IHICData hicData;
	@Override
	@EventSubscriber(topic = "indexData")
	public void storeData(HICData solrHICData) throws SolrComponentException {
		// TODO Auto-generated method stub
		
		SolrComponent.hicData = solrHICData;
		Job job = null;
		boolean success = false;
		try {
			Configuration conf = new Configuration();

			String jobTracker = PropertyUtil
					.setUpProperties("HADOOP_JOB_TRACKER");
			String fsName = PropertyUtil
					.setUpProperties("HADOOP_FS_DEFAULT_NAME");

			url = PropertyUtil.setUpProperties("SOLR_URL");
			
			// this should be like defined in your mapred-site.xml
			conf.set("fs.default.name", fsName);
			// like defined in hdfs-site.xml
			conf.set("mapred.job.tracker", jobTracker);

			job = new Job(conf, "solrIndexData");
			job.setJarByClass(SolrComponent.class);
			job.getConfiguration().set("hadoopFSDefault", fsName);

			// Job Input path
			FileInputFormat.setInputPaths(job, new Path(fsName
					+ "/usr/oxyent/bigData/savedsites/"));
			// Job Output path
			FileOutputFormat.setOutputPath(job, new Path(fsName
					+ "/usr/oxyent/indexprocessor1/"));

			job.setMapperClass(ProcessMapper.class);

			job.setInputFormatClass(WholeFileInputFormat.class);

			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			// job.setNumReduceTasks(3);

			success = job.waitForCompletion(true);
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			throw new SolrComponentException(
					SolrExceptionConstants.IO_EXCEPTION);
		} catch (Exception exce) {
			exce.printStackTrace();
		}
		// System.exit(success ? 0 : 1);
	}

	@Override
	@EventSubscriber(topic = "processQuery")
	public IHICData getResult(HICData queryHICData) throws SolrComponentException {
		// TODO Auto-generated method stub
		SolrComponent.hicData = queryHICData;
		url = PropertyUtil.setUpProperties("SOLR_URL");

		IData data = hicData.getData();
		String solrQry = data.getFormPattern().getFormValues()
				.get("searchTextBox").toString().trim();

		SolrResultBean resultBean = new SolrResultBean();
		SolrQuery query = new SolrQuery();
		QueryResponse rsp = null;
		List<SolrResultBean> respList = new ArrayList<SolrResultBean>();
		System.out.println("Inside Solr component get result");
		try {
			query.setQuery(solrQry);
			// if (fields != null)
			// query.setFields(fields);
			rsp = getSolrConnection().query(query);
			System.out.println("RSP in Solr Component" + rsp );
			if (rsp == null) {
				return null;
			} else {
				respList = rsp.getBeans(SolrResultBean.class);
				if (respList != null) {
					// System.out.println("respList is not null");
					SolrDocumentList docList = rsp.getResults();
					long iNumberfound = docList.getNumFound();
					if (respList.size() > 0) {
						resultBean = respList.get(0);
						 System.out.println("Capital===>"+resultBean.getCapital());
						 System.out.println("Flag===>"+resultBean.getFlag());
					}
					// this will not work if the respList is zero
					/*
					 * for (SolrResultBean resultBean : respList) {
					 * System.out.println(resultBean.getCapital()); if
					 * (respList.size() <= 0) { SolrResultBean bean = new
					 * SolrResultBean(); respList.add(bean); } }
					 */
				}
				// System.out.println("in else");
			}

		} catch (SolrServerException exc) {
			logger.error(" SolrServerException in getDefaultSolrResults: "
					+ exc.getMessage());
			throw new SolrComponentException(exc.getMessage());
		} catch (Exception exc) {
			logger.error(" Exception in getDefaultSolrResults: "
					+ exc.getMessage());
			throw new SolrComponentException(exc.getMessage());
		}

		System.out.println("******* Solr HIC DATA  before setCommonObject*********");
		hicData.getMetaData().setCommonObject(resultBean);
		System.out.println("******* Solr HIC DATA  after setCommonObject*********");

		hicData.getData().getFormPattern().getFormValues().put("SolrComponent", resultBean);
		System.out.println("******* Solr HIC DATA  after inserting data in FormValues*********");

		return hicData;
	}

	private CommonsHttpSolrServer getSolrConnection() throws Exception {
		try {
			// configure a server object with actual solr values.
			if (server == null)
			{
				System.out.println("******* Solr connection URL is " + url);
				server = new CommonsHttpSolrServer(url);
				System.out.println("******* Solr connection URL 1 " );
				server.setParser(new XMLResponseParser());
				System.out.println("******* Solr connection URL 2 " );

				server.setSoTimeout(1000);
				System.out.println("******* Solr connection URL 3 " );

				server.setConnectionTimeout(1000);
				System.out.println("******* Solr connection URL 4" );

			}

		} catch (MalformedURLException exc) {
			logger.error(" MalformedURLException in getting Solr Connection: "
					+ exc.getMessage());
			throw new SolrComponentException(exc.getMessage());
		} catch (Exception exc) {
			logger.error(" Exception in getting Solr Connection: "
					+ exc.getMessage());
			throw new SolrComponentException(exc.getMessage());
		}
		return server;
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
		return hicData;
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

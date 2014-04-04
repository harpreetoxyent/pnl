package com.pnl.component.socialrules;

import com.pnl.component.socialmedia.*;
import com.pnl.component.solr.bean.SolrResultBean;
public class SocialRules
{
	private  String univURL;
	private  String touristURL;
	
	
	public String getUnivURL() {
		return univURL;
	}
	public void setUnivURL(String univURL) {
		this.univURL = univURL;
	}
	public String getTouristURL() 
	{
		return touristURL;
	}
	public void setTouristURL(String touristURL) 
	{
		this.touristURL = touristURL;

	}
	public void addTouristURLToData()
	{
		SolrResultBean solrResultBean = (SolrResultBean) SocialMediaComponent.getInstanceOfSocialHICObject().getMetaData().getCommonObject();
		System.out.println("URL in Social rules" + solrResultBean.getTourist_urls());
		this.setTouristURL(solrResultBean.getTourist_urls());
		
		SocialMediaComponent.getInstanceOfSocialHICObject().getData().getFormPattern().getFormValues().put("URL", this.getTouristURL());
	}
	
	public void addUnivURLToData()
	{
		SolrResultBean solrResultBean = (SolrResultBean) SocialMediaComponent.getInstanceOfSocialHICObject().getMetaData().getCommonObject();
		System.out.println("URL in Social rules" + solrResultBean.getUniv_urls());
		this.setUnivURL(solrResultBean.getUniv_urls());
		SocialMediaComponent.getInstanceOfSocialHICObject().getData().getFormPattern().getFormValues().put("URL", this.getUnivURL());
	}

}

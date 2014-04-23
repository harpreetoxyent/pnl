package com.pnl.component.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

public class SolrResultBean {

	@Field
	private String id;
	@Field
	private String name;
	@Field
	private String flag;
	@Field
	private String capital;
	@Field
	private String largest_city;
	@Field
	private String governor;
	@Field
	private String lieutenant_governor;
	@Field
	private String senators;
	@Field
	private String us_house_delegation;
	@Field
	private String time_zone;
	@Field
	private String website;
	@Field
	private String short_description;
	@Field
	private String tourist_urls;
	@Field
	private String univ_urls;
	@Field
	private String url;
	@Field
	private String content;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTourist_urls() {
		return tourist_urls;
	}

	public void setTourist_urls(String tourist_urls) {
		this.tourist_urls = tourist_urls;
	}

	public String getUniv_urls() {
		return univ_urls;
	}

	public void setUniv_urls(String univ_urls) {
		this.univ_urls = univ_urls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getLargest_city() {
		return largest_city;
	}

	public void setLargest_city(String largest_city) {
		this.largest_city = largest_city;
	}

	public String getGovernor() {
		return governor;
	}

	public void setGovernor(String governor) {
		this.governor = governor;
	}

	public String getLieutenant_governor() {
		return lieutenant_governor;
	}

	public void setLieutenant_governor(String lieutenant_governor) {
		this.lieutenant_governor = lieutenant_governor;
	}

	public String getSenators() {
		return senators;
	}

	public void setSenators(String senators) {
		this.senators = senators;
	}

	public String getUs_house_delegation() {
		return us_house_delegation;
	}

	public void setUs_house_delegation(String us_house_delegation) {
		this.us_house_delegation = us_house_delegation;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
}

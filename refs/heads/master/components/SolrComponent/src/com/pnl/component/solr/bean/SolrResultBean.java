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
	    private String lieutenant_governo;
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
		public String getLieutenant_governo() {
			return lieutenant_governo;
		}
		public void setLieutenant_governo(String lieutenant_governo) {
			this.lieutenant_governo = lieutenant_governo;
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

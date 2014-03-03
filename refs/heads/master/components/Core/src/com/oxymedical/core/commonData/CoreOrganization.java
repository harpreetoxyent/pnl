package com.oxymedical.core.commonData;

import java.io.Serializable;

public class CoreOrganization implements Serializable{

		 private String organizationId;
	     private String companyId;
	     private String parentOrganizationId;
	     private String name;
	     private String regionId;
	     private String countryId;
	     private String statusId;
	     private String comments;

	     // Constructors

	    /** default constructor */
	    public CoreOrganization() {
	    }

	 /** minimal constructor */
	    public CoreOrganization(String organizationId, String companyId) {
	        this.organizationId = organizationId;
	        this.companyId = companyId;
	    }
	    /** full constructor */
	    public CoreOrganization(String organizationId, String companyId, String parentOrganizationId, String name, String regionId, String countryId, String statusId, String comments) {
	       this.organizationId = organizationId;
	       this.companyId = companyId;
	       this.parentOrganizationId = parentOrganizationId;
	       this.name = name;
	       this.regionId = regionId;
	       this.countryId = countryId;
	       this.statusId = statusId;
	       this.comments = comments;
	    }
	    
	   
	    // Property accessors
	    public String getOrganizationId() {
	        return this.organizationId;
	    }
	    
	    public void setOrganizationId(String organizationId) {
	        this.organizationId = organizationId;
	    }
	    public String getCompanyId() {
	        return this.companyId;
	    }
	    
	    public void setCompanyId(String companyId) {
	        this.companyId = companyId;
	    }
	    public String getParentOrganizationId() {
	        return this.parentOrganizationId;
	    }
	    
	    public void setParentOrganizationId(String parentOrganizationId) {
	        this.parentOrganizationId = parentOrganizationId;
	    }
	    public String getName() {
	        return this.name;
	    }
	    
	    public void setName(String name) {
	        this.name = name;
	    }
	    public String getRegionId() {
	        return this.regionId;
	    }
	    
	    public void setRegionId(String regionId) {
	        this.regionId = regionId;
	    }
	    public String getCountryId() {
	        return this.countryId;
	    }
	    
	    public void setCountryId(String countryId) {
	        this.countryId = countryId;
	    }
	    public String getStatusId() {
	        return this.statusId;
	    }
	    
	    public void setStatusId(String statusId) {
	        this.statusId = statusId;
	    }
	    public String getComments() {
	        return this.comments;
	    }
	    
	    public void setComments(String comments) {
	        this.comments = comments;
	    }
}

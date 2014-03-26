package com.pnl.component.socialrules;

import com.pnl.component.socialmedia.*;;

public class SocialRules
{
	private String univURL;
	private String touristURL;
	
	
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
		this.setTouristURL("www.yahoo.com");
		SocialMediaComponent.getInstanceOfSocialHICObject().getData().getFormPattern().getFormValues().put("TouristURL", this.getTouristURL());
	}

}

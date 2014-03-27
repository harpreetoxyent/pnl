package com.pnl.component.socialrules;

import com.pnl.component.socialmedia.*;;

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
		this.setTouristURL("www.yahoo.com");
		System.out.println("URL in Social rules" + this.getTouristURL());
		SocialMediaComponent.getInstanceOfSocialHICObject().getData().getFormPattern().getFormValues().put("TouristURL", this.getTouristURL());
	}
	
	public void addUnivURLToData()
	{
		this.setUnivURL("www.timesofindia.com");
		System.out.println("URL in Social rules" + this.getUnivURL());
		SocialMediaComponent.getInstanceOfSocialHICObject().getData().getFormPattern().getFormValues().put("UnivURL", this.getUnivURL());
	}

}

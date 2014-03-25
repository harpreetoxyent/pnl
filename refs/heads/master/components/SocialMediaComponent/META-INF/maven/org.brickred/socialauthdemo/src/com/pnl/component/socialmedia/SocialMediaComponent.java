package com.pnl.component.socialmedia;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Feed;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.plugin.FeedPlugin;
import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.Data;
import com.oxymedical.core.commonData.FormPattern;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.commonData.MetaData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.hic.application.NOLISRuntime;
import com.oxymedical.hic.application.eventmanagement.PublicationScope;
import com.pnl.component.socialmedia.controller.BusSocialAuthConfig;
import com.pnl.component.socialmedia.controller.BusSocialAuthManager;
import com.pnl.component.socialmedia.conf.UserInfo;

public class SocialMediaComponent implements ISocialMediaComponent, IComponent 
{
	public IHICData hicData = null;
	BusSocialAuthManager socialAuthManager = null;
	String successUrl = "http://example.com:8080/HICFrameworkServlet/form.zul";
	UserInfo userProfileInfo = null;
	
	
	public SocialMediaComponent()
	{
		try
		{
			BusSocialAuthConfig configAuth = new BusSocialAuthConfig();
			socialAuthManager = new BusSocialAuthManager();
			userProfileInfo = new UserInfo();
			socialAuthManager.setSocialAuthConfig(configAuth);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
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

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @returns IHICData
	 */
	public IHICData getHicData() {
		return this.hicData;
	}

	/**
	 * This method is the inherited abstract method of the interface IComponent.
	 * 
	 * @param arg0
	 * @returns void
	 */
	public void setHicData(IHICData arg0) {
		hicData = arg0;
	}
	@Override
	@MaintenancePublisher
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub

	}

	@EventSubscriber(topic = "executeSocial")
	public IHICData execute(IHICData hicData) throws Exception
	{
		this.hicData = hicData;
		IData data = hicData.getData();
		hicData.setMetaData(new MetaData());
		String providerId = (String) data.getFormPattern().getFormValues()
				.get("providerId");
		System.out.println("**** Provider ID is ****" + providerId);
		String url = socialAuthManager.getAuthenticationUrl(providerId,
				successUrl);
		System.out.println("**** URL  is ****" + url);
		hicData.getMetaData().setCommonObject(url);
		return hicData;
	}
	@EventSubscriber(topic = "loadUserSocialProfile")
	public IHICData  loadUserData(IHICData hicData) throws Exception
	{
		
		IData data = hicData.getData();
		hicData.setMetaData(new MetaData());
		System.out.println("**** HIC Data  is ****" + data.toString());
		String codeReturned = (String) data.getFormPattern().getFormValues()
				.get("code");	
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("code", codeReturned);
		System.out.println("**** Code Returned  is ****" + codeReturned);
		String providerId = (String) data.getFormPattern().getFormValues()
				.get("providerId");	
		String url = socialAuthManager.getAuthenticationUrl(providerId,
				successUrl);
		AuthProvider provider = socialAuthManager.connect(paramsMap);

		System.out.println("**** Provider  is ****" + provider.getProviderId());

		// get profile
		Profile p = provider.getUserProfile();
		
		userProfileInfo.setFirstName(p.getFirstName());
		userProfileInfo.setLastName(p.getLastName());
		userProfileInfo.setEmail(p.getEmail());
		userProfileInfo.setGender(p.getGender());
		userProfileInfo.setCountry(p.getCountry());
		userProfileInfo.setDay(p.getDob().getDay());
		userProfileInfo.setMonth(p.getDob().getMonth());
		userProfileInfo.setYear(p.getDob().getYear());
		userProfileInfo.setCountry(p.getCountry());
		userProfileInfo.setLocation(p.getLocation());
		userProfileInfo.setLanguage(p.getLanguage());	
		userProfileInfo.setProfileImageURL(p.getProfileImageURL());
		
//		int yearDOB = userProfileInfo.getYear();
//		int monthDOB = userProfileInfo.getMonth();
//		int dayDOB = userProfileInfo.getDay();
//		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy");
//		java.util.Date date = new java.util.Date();
//		int thisYear = Integer.parseInt(dateFormat.format(date));
//		
//		dateFormat = new SimpleDateFormat("MM");
//		date = new java.util.Date();
//		int thisMonth = Integer.parseInt(dateFormat.format(date));
//
//		dateFormat = new SimpleDateFormat("dd");
//		date = new java.util.Date();
//		int thisDay = Integer.parseInt(dateFormat.format(date));
//		
//		int age = thisYear-yearDOB;
//		
//		if(thisMonth < monthDOB)
//		{
//			age = age - 1;
//		}
//		
//		if(thisMonth == monthDOB && thisDay < dayDOB)
//		{
//			age = age - 1;
//		}
//		userProfileInfo.setAge(age);
//		System.out.println(" AGE CALCULATED = " + age );
		userProfileInfo.setUserContacts(provider.getContactList());
		List<Contact> list = userProfileInfo.getUserContacts();
		for (int i=0; i<list.size(); i++)
		{
			System.out.println("User Contacts "+i+list.get(i));
		}
		hicData.getMetaData().setCommonObject(userProfileInfo);
		
		if (provider.isSupportedPlugin(org.brickred.socialauth.plugin.FeedPlugin.class)) 
		{
		    FeedPlugin feedPlugin = provider.getPlugin(org.brickred.socialauth.plugin.FeedPlugin.class);
		    List<Feed> feeds = feedPlugin.getFeeds();
		    for (int i=0; i<feeds.size(); i++)
			{
				System.out.println("------Feeds-------- "+i+feeds.get(i));
			}
		}
		return hicData;
	}
	
	@EventSubscriber(topic = "checkRuleForSocialData")
	public IHICData checkRuleForSocialData(IHICData userObject) throws Exception
	{
		IData data = new Data();
		
		if(userProfileInfo != null)
		{
			System.out.println(" User Object is not null");
			int yearDOB = userProfileInfo.getYear();
			int monthDOB = userProfileInfo.getMonth();
			int dayDOB = userProfileInfo.getDay();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			java.util.Date date = new java.util.Date();
			int thisYear = Integer.parseInt(dateFormat.format(date));
			
			dateFormat = new SimpleDateFormat("MM");
			date = new java.util.Date();
			int thisMonth = Integer.parseInt(dateFormat.format(date));
	
			dateFormat = new SimpleDateFormat("dd");
			date = new java.util.Date();
			int thisDay = Integer.parseInt(dateFormat.format(date));
			
			int age = thisYear-yearDOB;
			
			if(thisMonth < monthDOB)
			{
				age = age - 1;
			}
			
			if(thisMonth == monthDOB && thisDay < dayDOB)
			{
				age = age - 1;
			}
			userProfileInfo.setAge(age+"");
			System.out.println(" AGE CALCULATED = " + age );
			
			data.setRawData(userProfileInfo.getAge());
			userObject.setData(data);
			//Call Rule Component to check matching rules
			NOLISRuntime.FireEvent("executeRuleHICData", new Object[]{userObject}, PublicationScope.Global);
			
			return userObject;
		}
		else
		{
			System.out.println(" User Object is null");
			return null;
		}
	}
	public void addUnivURLToAnswerData()
	{
		System.out.println("------Execute the addUniversity URLToAnswerData consequence-------- ");
	}
	public void addTouURLToAnswerData()
	{
		System.out.println("------Execute the addTourist URLToAnswerData consequence-------- ");
	}
}

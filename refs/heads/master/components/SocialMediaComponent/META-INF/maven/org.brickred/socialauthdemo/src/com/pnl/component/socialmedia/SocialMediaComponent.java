package com.pnl.component.socialmedia;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
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
import com.pnl.component.socialmedia.controller.BusSocialAuthConfig;
import com.pnl.component.socialmedia.controller.BusSocialAuthManager;

public class SocialMediaComponent implements ISocialMediaComponent, IComponent 
{
	public IHICData hicData = null;
	BusSocialAuthManager socialAuthManager = null;
	String successUrl = "http://example.com:8080/HICFrameworkServlet/form.zul";
	
	public SocialMediaComponent()
	{
		try
		{
			BusSocialAuthConfig configAuth = new BusSocialAuthConfig();
			socialAuthManager = new BusSocialAuthManager();
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
	public void loadUserData(HICData hicData) throws Exception
	{
		
		IData data = hicData.getData();
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
		// you can obtain profile information
		System.out.println(p.getFirstName());
		// OR also obtain list of contacts
		List<Contact> contactsList = provider.getContactList();
	}
	
//	public static void main(String[] args)
//	{
//		SocialMediaComponent socialComponent = new SocialMediaComponent();
//		HICData hicData = new HICData();
//		IData data = new Data();
//		FormPattern formPattern = new FormPattern();
//		Hashtable<String, Object> formValues = new Hashtable<String, Object>();
//		formPattern.setFormValues(formValues);
//		data.setFormPattern(formPattern);
//		hicData.setData(data);
//		hicData.getData().getFormPattern().getFormValues().put("providerId", "facebook");
//		try {
//			socialComponent.execute(hicData);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//				
//	}
}

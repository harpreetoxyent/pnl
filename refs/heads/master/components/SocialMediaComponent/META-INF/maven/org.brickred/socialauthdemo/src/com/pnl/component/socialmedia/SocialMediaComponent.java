package com.pnl.component.socialmedia;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
import org.dom4j.Document;
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.IData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.pnl.component.socialmedia.controller.BusSocialAuthConfig;
import com.pnl.component.socialmedia.controller.BusSocialAuthManager;

public class SocialMediaComponent implements ISocialMediaComponent, IComponent {

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

	@Override
	public IHICData getHicData() {
		// TODO Auto-generated method stub
		return null;
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

	@EventSubscriber(topic = "executeSocial")
	public IHICData execute(IHICData hicData) throws Exception {
		// Basic Iniitalization needed for Social Auth
		BusSocialAuthConfig configAuth = new BusSocialAuthConfig();
		BusSocialAuthManager socialAuthManager = new BusSocialAuthManager();
		socialAuthManager.setSocialAuthConfig(configAuth);

		// URL of YOUR application which will be called after authentication
		String successUrl = "http://opensource.brickred.com/socialauthdemo/socialAuthSuccessAction.do";

		// Now get selected provider by user from zul
		IData data = hicData.getData();
		String providerId = (String) data.getFormPattern().getFormValues()
				.get("providerId");

		String callbackURL = "http://";
		// get Provider URL to which you should redirect for authentication.
		// id can have values "facebook", "twitter", "yahoo" etc. or the OpenID
		// URL
		String url = socialAuthManager.getAuthenticationUrl(providerId,
				successUrl);
		AuthProvider provider = socialAuthManager
				.connect(new HashMap<String, String>());
		// get profile
		Profile p = provider.getUserProfile();
		// you can obtain profile information
		System.out.println(p.getFirstName());

		// OR also obtain list of contacts
		List<Contact> contactsList = provider.getContactList();
		return hicData;
	}
}

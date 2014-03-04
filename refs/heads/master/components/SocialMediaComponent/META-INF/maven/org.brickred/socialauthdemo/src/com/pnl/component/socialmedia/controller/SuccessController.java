package com.pnl.component.socialmedia.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.spring.bean.SocialAuthTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pnl.component.socialmedia.utilities.ActionConstants;

@Controller
public class SuccessController {

	@Autowired
	private SocialAuthTemplate socialAuthTemplate;
	@RequestMapping(value = "/authSuccess")
	public ModelAndView getRedirectURL(final HttpServletRequest request)
			throws Exception {
		SocialAuthManager manager = socialAuthTemplate.getSocialAuthManager();
		AuthProvider provider = manager.getCurrentAuthProvider();
		HttpSession session = request.getSession();
		String type = null;
		if (session.getAttribute(ActionConstants.REQUEST_TYPE) != null) {
			type = (String) session.getAttribute(ActionConstants.REQUEST_TYPE);
		}
		if (type != null) {
			if (ActionConstants.REGISTRATION.equals(type)) {
				return registration(provider);
			} else if (ActionConstants.IMPORT_CONTACTS.equals(type)) {
				return importContacts(provider);
			} else if (ActionConstants.SHARE.equals(type)) {
				return new ModelAndView("shareForm", "connectedProvidersIds",
						manager.getConnectedProvidersIds());
			}
		}

		return null;
	}

	private ModelAndView registration(final AuthProvider provider)
			throws Exception {
		Profile profile = provider.getUserProfile();
		if (profile.getFullName() == null) {
			String name = null;
			if (profile.getFirstName() != null) {
				name = profile.getFirstName();
			}
			if (profile.getLastName() != null) {
				if (profile.getFirstName() != null) {
					name += " " + profile.getLastName();
				} else {
					name = profile.getLastName();
				}
			}
			if (name == null && profile.getDisplayName() != null) {
				name = profile.getDisplayName();
			}
			if (name != null) {
				profile.setFullName(name);
			}
		}
		ModelAndView view = new ModelAndView("registrationForm", "profile",
				profile);
		return view;
	}

	private ModelAndView importContacts(final AuthProvider provider)
			throws Exception 
	{
		
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList = provider.getContactList();
		if (contactsList != null && contactsList.size() > 0) 
		{
			for (Contact p : contactsList) 
			{
				if (!StringUtils.hasLength(p.getFirstName())
						&& !StringUtils.hasLength(p.getLastName())) 
				{
					p.setFirstName(p.getDisplayName());
				}
				System.out.println("---Contact Retreived -----"+ p.getId()+p.getDisplayName());

			}
		}
		
		ModelAndView view = new ModelAndView("showImportContacts", "contacts",
				contactsList);
		return view;
	}
	

}

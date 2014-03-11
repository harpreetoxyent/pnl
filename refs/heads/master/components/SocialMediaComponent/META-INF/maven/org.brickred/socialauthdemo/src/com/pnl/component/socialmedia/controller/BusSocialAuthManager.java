package com.pnl.component.socialmedia.controller;

import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

public class BusSocialAuthManager extends SocialAuthManager {

	private static final long serialVersionUID = 3455230182428434946L;
	public BusSocialAuthManager()
	{
	}
	@Override
	public void setSocialAuthConfig(final SocialAuthConfig socialAuthConfig)
			throws Exception {
		super.setSocialAuthConfig(socialAuthConfig);
	}

}

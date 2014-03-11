package com.pnl.component.socialmedia.controller;

import java.io.InputStream;

import org.brickred.socialauth.SocialAuthConfig;

public class BusSocialAuthConfig extends SocialAuthConfig {

	private static final long serialVersionUID = 3455230182428434947L;
	InputStream socialConfigProp = null;

	public BusSocialAuthConfig() throws Exception
	{
		try
		{
			socialConfigProp = BusSocialAuthManager.class
					.getResourceAsStream("/com/pnl/component/socialmedia/conf/oauth_consumer.properties");
			this.load(socialConfigProp);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw new Exception(exp.getMessage());
		}
	}

}

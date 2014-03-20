package com.pnl.component.socialmedia.controller;

import java.util.List;

import com.pnl.component.socialmedia.SocialMediaComponent;
import com.pnl.component.socialmedia.conf.UserInfo;

public class PNLViewModel 
{
	
	private SocialMediaComponent socialMedia = new SocialMediaComponent();
	private UserInfo userSocialInfo = new UserInfo();
	
	
	
	public List<UserInfo> getUserFullSocialInfo() throws Exception 
	{
		return null;
	}
	
	public UserInfo getUserSocialInfo() {
		return userSocialInfo;
	}

	public void setUserSocialInfo(UserInfo userSocialInfo) {
		this.userSocialInfo = userSocialInfo;
	}


}

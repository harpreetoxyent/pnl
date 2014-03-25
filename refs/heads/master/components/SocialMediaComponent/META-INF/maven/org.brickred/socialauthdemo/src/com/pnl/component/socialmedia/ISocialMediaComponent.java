package com.pnl.component.socialmedia;


import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;


public interface ISocialMediaComponent 
{
	public IHICData execute(IHICData hicData) throws Exception;		
	public IHICData loadUserData(IHICData hicData) throws Exception;
	public IHICData checkRuleForSocialData(IHICData userObject) throws Exception;
	public void addUnivURLToAnswerData();
	public void addTouURLToAnswerData();
}

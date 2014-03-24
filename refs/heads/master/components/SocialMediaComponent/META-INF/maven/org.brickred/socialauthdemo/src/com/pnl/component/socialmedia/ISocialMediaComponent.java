package com.pnl.component.socialmedia;


import com.oxymedical.core.commonData.IHICData;


public interface ISocialMediaComponent 
{
	public IHICData execute(IHICData hicData) throws Exception;		
	public void addUnivURLToAnswerData();
	public void addTouURLToAnswerData();
}

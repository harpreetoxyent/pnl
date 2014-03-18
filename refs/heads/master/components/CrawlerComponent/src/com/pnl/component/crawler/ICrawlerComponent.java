package com.pnl.component.crawler;

import com.oxymedical.core.commonData.HICData;
import com.pnl.component.crawler.exception.CrawlerComponentException;

public interface ICrawlerComponent {
	
	public void process(HICData hicData) throws CrawlerComponentException;
	
}

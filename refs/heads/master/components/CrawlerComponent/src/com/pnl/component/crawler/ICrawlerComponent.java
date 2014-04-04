package com.pnl.component.crawler;

import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.pnl.component.crawler.exception.CrawlerComponentException;

public interface ICrawlerComponent {
	
	public IHICData process(IHICData hicData) throws CrawlerComponentException;
	
}

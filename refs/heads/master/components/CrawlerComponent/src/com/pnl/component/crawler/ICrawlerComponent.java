package com.pnl.component.crawler;

import com.pnl.component.crawler.exception.CrawlerComponentException;

public interface ICrawlerComponent {
	
	public void process(String[] args) throws CrawlerComponentException;
	
}

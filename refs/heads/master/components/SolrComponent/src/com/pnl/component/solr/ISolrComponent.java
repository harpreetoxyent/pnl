package com.pnl.component.solr;

import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.pnl.component.solr.exception.SolrComponentException;

public interface ISolrComponent {

	public void storeData(HICData hicData) throws SolrComponentException;

	public IHICData getResult(HICData queryHICData) throws SolrComponentException;
	}

package com.pnl.component.bigdata;

import com.oxymedical.core.commonData.HICData;
import com.pnl.component.bigdata.exception.BigDataComponentException;

public interface IBigDataComponent {

	public void process(HICData hicData) throws BigDataComponentException;
}

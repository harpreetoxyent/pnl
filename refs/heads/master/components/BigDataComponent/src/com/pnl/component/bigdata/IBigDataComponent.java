package com.pnl.component.bigdata;

import com.pnl.component.bigdata.exception.BigDataComponentException;

public interface IBigDataComponent {

	public void process(String[] args) throws BigDataComponentException;
}

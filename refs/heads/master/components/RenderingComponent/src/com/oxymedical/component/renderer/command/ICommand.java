package com.oxymedical.component.renderer.command;


import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;
import com.oxymedical.core.router.IRouter;

public interface ICommand
{
	public void setRouter(IDataUnitRouter router);
	public void execute();
	public IHICData getHICData();
}

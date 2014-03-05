package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

public class InvokeClientCallCommand extends BaseCommand implements IUiLibraryCompositeCommand {
	

	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit=updateDataUnit(dataUnit);
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}
	private IDataUnit updateDataUnit(IDataUnit dataUnit)
	{
		dataUnit.setMetaData(this.getParamList());
		return dataUnit;
	}


}

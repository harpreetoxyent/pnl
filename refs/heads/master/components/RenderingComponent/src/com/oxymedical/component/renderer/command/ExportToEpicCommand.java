package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;

import com.oxymedical.component.renderer.data.DataUnit;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;

public class ExportToEpicCommand extends BaseCommand implements IUiLibraryCompositeCommand {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	    IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(dataUnit, this.getSession(),this.getParamList());
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		// TODO Auto-generated method stub
		return this.getData();
	}
	public IDataUnit updateDataUnit(IDataUnit dataUnit,Session session ,String paramList)
	{
	   dataUnit.setMetaData(paramList);
		return dataUnit;
	}
	
}

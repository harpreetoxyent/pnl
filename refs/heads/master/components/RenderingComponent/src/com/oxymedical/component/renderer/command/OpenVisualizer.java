package com.oxymedical.component.renderer.command;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;

public class OpenVisualizer extends BaseCommand implements IUiLibraryCompositeCommand {

	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit.setStatus(this.getParamList());
		this.setData(this.getRouter().routeToModeler(dataUnit));
		
		
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

}

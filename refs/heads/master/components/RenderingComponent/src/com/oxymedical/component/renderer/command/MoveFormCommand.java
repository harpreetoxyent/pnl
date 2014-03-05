package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.oxymedical.core.querydata.*;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

public class MoveFormCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {

	
	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(this.getSession(), dataUnit);
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(Session session, IDataUnit dataUnit) {
		Hashtable reqData = new Hashtable();
		reqData.put("destinationForm", this.getParamList());
		dataUnit.setFormValues(reqData);
		session.getWebApp().setAttribute("currentPage", this.getParamList());
		return dataUnit;
	}

}

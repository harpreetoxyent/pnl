package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

public class ReportDataCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {

	
	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(dataUnit, this.getParamList());
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList) {
		String[] reportArg = paramList.trim().split("_SEP_");

		String reportId = "";
		String reportQuery = "";
		String reportFile = "";

		if (reportArg.length >= 3) {
			reportQuery = reportArg[0];
			reportFile = reportArg[1];
			reportId = reportArg[2];
		}
		System.out.println("==listquery--" + reportQuery);
		QueryData requestData = new QueryData();
		requestData.setCondition(reportQuery);
		//dataUnit.setMethodName("getReportData");
		dataUnit.setQueryData(requestData);
		return dataUnit;
	}

}

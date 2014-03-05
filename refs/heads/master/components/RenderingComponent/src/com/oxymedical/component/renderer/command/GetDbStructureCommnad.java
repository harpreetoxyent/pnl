/**
 * 
 */
package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

/**
 * @author vka
 *
 */
public class GetDbStructureCommnad extends BaseCommand implements
IUiLibraryCompositeCommand {
	

	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit=updateDataUnit(dataUnit, this.getParamList());
		this.setData(this.getRouter().routeToModeler(dataUnit));
		
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList)
	{
		QueryData requestData = new QueryData();
		requestData.setCondition(paramList);
		dataUnit.setQueryData(requestData);
		return dataUnit;
	}
}

package com.oxymedical.component.renderer.command;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.renderdata.IDataUnit;

public class GetWorkFlowStatusToolCommand extends BaseCommand implements
IUiLibraryCompositeCommand {

	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(this.getParamList(), dataUnit);
		this.setData( this.getRouter().routeToModeler(dataUnit));
		
	}

	@Override
	public IHICData getHICData() {
		// TODO Auto-generated method stub
		return this.getData();
	}
   
	private IDataUnit updateDataUnit(String paramList, IDataUnit dataUnit)
	{
		System.out.println("paramList----"+paramList);
		QueryData requestData = new QueryData();
		requestData.setCondition(paramList);
		//dataUnit.setMethodName("getListData");
		dataUnit.setQueryData(requestData);
		return dataUnit;
		
	
	}
}

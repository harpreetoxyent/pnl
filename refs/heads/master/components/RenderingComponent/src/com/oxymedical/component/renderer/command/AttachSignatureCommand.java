package com.oxymedical.component.renderer.command;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.stringutil.StringUtil;

public class AttachSignatureCommand extends BaseCommand implements IUiLibraryCompositeCommand
{
	@Override
	public void execute()
	{
		IDataUnit dataUnit = createDataUnit();
		dataUnit = updateDataUnit(this.getParamList(), dataUnit);
		this.setData( this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData()
	{
		return this.getData();
	}

	private IDataUnit updateDataUnit(String paramList, IDataUnit dataUnit) 
	{
		List<String> listData = new ArrayList<String>();
		String[] params = StringUtil.splitString(paramList, ";");
		
		/*
		 * "If" condition added so that the same command can be used for other
		 * types of AttachSignatureCommand
		 */
		if ("default".equalsIgnoreCase(params[0]))
		{
			listData.add(params[1]); // Current user
			listData.add(params[2]); // Schedule ID
			dataUnit.setList(listData);
		}
		return dataUnit;
	}
}

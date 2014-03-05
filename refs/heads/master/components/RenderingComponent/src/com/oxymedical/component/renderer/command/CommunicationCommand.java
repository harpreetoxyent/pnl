package com.oxymedical.component.renderer.command;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.communication.CommunicationData;
import com.oxymedical.core.communication.CommunicationType;
import com.oxymedical.core.communication.ICommunicationData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.stringutil.StringUtil;

public class CommunicationCommand extends BaseCommand implements IUiLibraryCompositeCommand
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
		ICommunicationData data = new CommunicationData();
		String[] params = StringUtil.splitString(paramList, ";");
		
		System.out.println("[params[0]]" + params[0]);
		if ("forgotpassword".equalsIgnoreCase(params[0]))
		{
			System.out.println("[Inside forgotpassword");
			data.setCommunicationType(CommunicationType.MAIL);
			data.addReceiver(params[1]);
			data.setSubject(CommandConstants.COMMUNICATION_FORGOTPASSWORD_SUBJECT);
			data.addBodyLine(CommandConstants.COMMUNICATION_FORGOTPASSWORD_BODY.replaceAll("@@PASSWORD@@", params[2]));
			dataUnit.setCommunicationData(data);
		}
		return dataUnit;
	}
}

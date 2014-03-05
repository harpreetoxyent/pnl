package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zul.Messagebox;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;

public class RevertWorkflowStatusCommand extends BaseCommand implements
IUiLibraryCompositeCommand {

	@Override
	public void execute() 
	{
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit = updateDataUnit(this.getParamList(), dataUnit);
		this.setData( this.getRouter().routeToModeler(dataUnit));
		
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}
	
	private IDataUnit updateDataUnit(String paramList, IDataUnit dataUnit)
	{
		System.out.println("inside the update command for the RevertWorkflowStatusCommand ");
		Hashtable information = new Hashtable();
		String fieldName =null ;
		String fieldValue =null;
		if(paramList.indexOf("_FIELDSEP_")>=0)
		{
			String[] strValue = paramList.trim().split("_FIELDSEP_");
			if(strValue.length ==6)
			{
				for(int i=0;i<strValue.length;i++)
				{
					String fieldExp = strValue[i];
					if(fieldExp.indexOf("_SEP_")>=0)
					{
						String[] fexp = fieldExp.trim().split("_SEP_");
						fieldName = fexp[0];
						fieldValue = fexp[1];
						if(fieldName!=null && fieldValue!=null)
						{
							information.put(fieldName, fieldValue);
						}
					}
					else
					{
						try
						{
							Messagebox.show("Invalid arguments");
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					}
					
				}
	
			}
		dataUnit.setFormValues(information);
		return dataUnit;
	}
}

package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.oxymedical.component.renderer.uiBuilder.zk.library.UiLibraryUtil;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

public class PSGCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {

	@Override
	public void execute() {
		 this.setFormValues(createUserFormValues(this.getParamList()));
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private Hashtable createUserFormValues(String paramList)
	{
		
		String fieldName ;
		String fieldId;
		Hashtable userauth = new Hashtable();

			String[] strValue = paramList.trim().split("_FIELDSEP_");
			if(strValue.length >=1)
			{
				for(int i=0;i<strValue.length;i++)
				{
					String fieldExp = strValue[i];
					if(fieldExp.indexOf("_SEP_")>=0)
					{
						String[] fexp = fieldExp.trim().split("_SEP_");
						fieldName = fexp[0];
						fieldId = fexp[1];
						String fieldVal = fexp[1];// (String)this.getFormValues().get(fieldId);
						System.out.println(fieldName + " " + fieldVal);
						if(fieldName!=null && fieldVal!=null)
						{
							userauth.put(fieldName, fieldVal);
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
	
		return userauth;
	}
}

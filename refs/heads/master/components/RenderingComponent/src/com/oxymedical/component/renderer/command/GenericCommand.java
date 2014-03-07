package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

public class GenericCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {


	@Override
	public void execute() 
	{
		try
		{
			System.out.println("-------Inside Generic Command Execute---");
			IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
					this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
					this.getMethodName(), this.getSession());
			this.setData(this.getRouter().routeToModeler(dataUnit));
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}

	@Override
	public IHICData getHICData() {
		
		return this.getData();
	}

}

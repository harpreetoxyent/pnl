/**
 * 
 */
package com.oxymedical.component.renderer.command;

import java.util.Hashtable;

import org.zkoss.zk.ui.Session;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;

/**
 * @author wkh
 *
 */
public class ImportFromEpicCommand extends BaseCommand implements IUiLibraryCompositeCommand {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	    IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());		
		this.setData(this.getRouter().routeToModeler(dataUnit));
	}

	@Override
	public IHICData getHICData() {
	
		return this.getData();
	}
	
	
}

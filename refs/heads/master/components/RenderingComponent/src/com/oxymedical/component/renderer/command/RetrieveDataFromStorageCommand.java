package com.oxymedical.component.renderer.command;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.router.IDataUnitRouter;

public class RetrieveDataFromStorageCommand extends BaseCommand  implements IUiLibraryCompositeCommand {


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
				List<String> dataList = new ArrayList<String>();			
				if(paramList != null)
				{
					String args[] = paramList.split("_SEP_");
					if(args != null && args.length == 2)
					{
						// Schedule id corresponding to which acquisition file should be stored.
						dataList.add(0, args[0]);
					
						// Acquisition file path.
						dataList.add(1, args[1]);
					}
				}
				dataUnit.setList(dataList);
				return dataUnit;
			}
			
		}

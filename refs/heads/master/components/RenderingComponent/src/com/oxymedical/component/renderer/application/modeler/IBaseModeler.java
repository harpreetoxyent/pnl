package com.oxymedical.component.renderer.application.modeler;
		
import java.util.List;

import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.renderer.Application;
import com.oxymedical.component.renderer.data.DataUnit;
import com.oxymedical.core.renderdata.IDataUnit;

		
public interface IBaseModeler 
{		
    // Interface Methods
    public void setRequestData(IDataUnit renderingData);
    public void setApplication(Application application);
    public IDBComponent getInstance();
    public void setDataList(List dataList);
}
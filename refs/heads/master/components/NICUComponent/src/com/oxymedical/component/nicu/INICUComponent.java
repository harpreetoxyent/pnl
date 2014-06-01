package com.oxymedical.component.nicu;

import com.oxymedical.component.render.resource_FORTIS.Nursingprogressreport;
import com.oxymedical.core.commonData.IHICData;

public interface INICUComponent
{
	
	public void startUpdateRequest(IHICData hicData);
	
	public void createNursingNoteRecord(Nursingprogressreport nursingprogressreport);

}

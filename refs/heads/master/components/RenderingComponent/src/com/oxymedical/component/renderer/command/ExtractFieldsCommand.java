package com.oxymedical.component.renderer.command;

import java.util.HashMap;
import java.util.Hashtable;

import com.oxymedical.core.commonData.IHICData;

import com.oxymedical.core.renderdata.IDataUnit;


public class ExtractFieldsCommand extends BaseCommand implements
		IUiLibraryCompositeCommand {

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
	
		//dataUnit.setMetaData(paramList);
		Hashtable formValues= new  Hashtable();
		String scheduleid = (String) this.getSession().getAttribute("scheduleId");
		if(scheduleid != null)
			formValues.put("ScheduleID",scheduleid);
		
		String rowid = (String) this.getSession().getAttribute("rowId");
		if(rowid != null)
			formValues.put("PatientId",rowid);
		
		String nolisId = (String) this.getSession().getAttribute("nolisId");
		if(nolisId != null)
			formValues.put("NolisId",nolisId);
		
		String studyId = (String) this.getSession().getAttribute("studyId");
		if(studyId != null)
			formValues.put("StudyId",studyId);
		
		String fname = (String) this.getSession().getAttribute("fname");
		if(fname != null)
			formValues.put("Fname",fname);
		
		String lname = (String) this.getSession().getAttribute("lname");
		if(lname != null)
			formValues.put("Lname",lname);
		
		String tnote = (String) this.getSession().getAttribute("tnote");
		if(tnote != null)
			formValues.put("Tnote",tnote);
		
		if(paramList != null)
			formValues.put("ReportFilePath",paramList);
		
		dataUnit.setFormValues(formValues);
		return dataUnit;
	}
	
}

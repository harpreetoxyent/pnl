package com.oxymedical.component.nicu.request;

import java.util.Calendar;

import com.oxymedical.component.nicu.NICUComponent;
import com.oxymedical.component.render.resource_FORTIS.Nursingprogressreport;

public class UpdateRequest extends Thread 
{

	NICUComponent parentComponent;
	public NICUComponent getParentComponent() {
		return parentComponent;
	}
	public void setParentComponent(NICUComponent parentComponent) {
		this.parentComponent = parentComponent;
	}
	@Override
	public void run() 
	{
		
		while(true)
		{
			try 
			{

				Calendar cal = Calendar.getInstance();
				int minutes = cal.get(Calendar.MINUTE);
				int seconds = cal.get(Calendar.SECOND);
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				
				if((minutes % 60 == 0) && (seconds % 60 == 0))
				{
					for (int hourVariable = 9 ; hourVariable <= 24; hourVariable++)
					{
						if(hourVariable == cal.get(Calendar.HOUR_OF_DAY))
						{		
							Nursingprogressreport nursingProgressReport = new Nursingprogressreport();
							if(nursingProgressReport != null)
							{
								nursingProgressReport.setTime((long) hourVariable);
								parentComponent.createNursingNoteRecord(nursingProgressReport);
								nursingProgressReport.setMethodId((long) 1);
								parentComponent.saveNursingProgressRowInDatabase(nursingProgressReport);
								System.out.println("Inside Update Request Thread of NICU Component");				
								sleep(3600000);
								if(hourVariable == 24)
								{
									break;
								}
							}
						}	
					}
					dayOfWeek = dayOfWeek+1;
					for(int hourVariable = 1 ; hourVariable <= 8 ; hourVariable++)
					{
						//System.out.println("value of hours variable is " + hourVariable);
						if(hourVariable == cal.get(Calendar.HOUR_OF_DAY))
						{		
							Nursingprogressreport nursingProgressReport = new Nursingprogressreport();
							if(nursingProgressReport != null)
							{
								nursingProgressReport.setTime((long) hourVariable);
								nursingProgressReport.setMethodId((long) 1);
								parentComponent.createNursingNoteRecord(nursingProgressReport);
								parentComponent.saveNursingProgressRowInDatabase(nursingProgressReport);
								System.out.println("Inside Update Request Thread of NICU Component");				
								sleep(3600000);
							}
						}	
					}
				}
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
		}
		
	}

}

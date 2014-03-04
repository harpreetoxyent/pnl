package com.oxymedical.component.useradmin.hicintegration;

import com.oxymedical.component.useradmin.OrganizationStructure;
import com.oxymedical.core.commonData.CoreUserAdminData;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.ICoreUserAdmindata;
import com.oxymedical.core.commonData.IHICData;

public class HICIntegration 
{

	
	public IHICData encapsulate(String companyName, OrganizationStructure orgStructure)
    {
    	IHICData hicData = new HICData();
       	ICoreUserAdmindata userData = new CoreUserAdminData();
    	userData.setCompanyName(companyName);
    	//userData.setOrgStructure(orgStructure);
    	//source.setComponent_id("useradmincomponent");
    	hicData.setData(userData);
    	
    	return hicData;
    }


}

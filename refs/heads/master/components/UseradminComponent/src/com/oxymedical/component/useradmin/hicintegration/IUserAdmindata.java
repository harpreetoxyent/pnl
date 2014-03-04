package com.oxymedical.component.useradmin.hicintegration;

import com.oxymedical.component.useradmin.OrganizationStructure;
import com.oxymedical.core.commonData.IData;

public interface IUserAdmindata extends IData 
{
	public void setCompanyName(String companyName);
	public void setOrgStructure(OrganizationStructure orgStructure);	
}

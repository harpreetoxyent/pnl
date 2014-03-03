package com.oxymedical.core.commonData;

import com.oxymedical.core.commonData.IData;

public interface ICoreUserAdmindata extends IData 
{
	public void setCompanyName(String companyName);
	public void setOrgStructure(CoreOrganizationStructure orgStructure);
	public CoreOrganizationStructure getOrgStructure();
	public String getCompanyName();
}

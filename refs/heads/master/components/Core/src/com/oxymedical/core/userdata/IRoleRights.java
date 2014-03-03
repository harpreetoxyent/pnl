package com.oxymedical.core.userdata;

import java.util.List;

public interface IRoleRights
{
	public String getRoleId();
	public ICompany getCompany();
	//public IRights getRights();
  
	public void setRoleId(String roleId);
	public void setCompany(ICompany company);
  //public void setRights(IRights right);
	//Methods added to set and get list of by pra on 30-may-2009
	public List<IRights> getRights();
	public void setRights(List<IRights> rights);
}

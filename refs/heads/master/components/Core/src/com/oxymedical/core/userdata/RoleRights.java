package com.oxymedical.core.userdata;

import java.util.List;

public class RoleRights implements IRoleRights,java.io.Serializable
{
	ICompany company = null;
	String roleId;
	//IRights rights = null;
	//Methods added to set and get list of rights by pra on 30-may-2009
	List<IRights> rights=null;
	
	public List<IRights> getRights() {
		return rights;
	}

	public void setRights(List<IRights> rights) {
		this.rights = rights;
	}

	public ICompany getCompany()
	{
		return this.company;
	}

/*	public IRights getRights()
	{
		return this.rights;
	}*/

	public String getRoleId()
	{
		return this.roleId;
	}

	public void setCompany(ICompany company)
	{
		this.company = company;
	}

/*	public void setRights(IRights right)
	{
		this.rights = right;
	}*/

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}

}

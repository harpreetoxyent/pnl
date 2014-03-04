package com.oxymedical.component.useradmin;

public interface IIdentityManager 
{
	void addIdentity(IIdentity identity);
	IIdentity getIdentity(Integer identityId);
}

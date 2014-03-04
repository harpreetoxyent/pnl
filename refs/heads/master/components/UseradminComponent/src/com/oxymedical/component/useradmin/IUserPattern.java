package com.oxymedical.component.useradmin;

import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.NoSuchCompanyException;
import com.oxymedical.component.useradmin.exception.NoSuchUserPatternException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Userpattern;

public interface IUserPattern
{
	public Userpattern addUserpattern(String companyId, String userPatternId,String comments, String defaultFormPat) throws DBComponentException,NoSuchCompanyException;
	public Userpattern getUserPatternbyCompanyAndId(String companyId , String userpatternId)throws NoSuchUserPatternException,DBComponentException;
	public String getDefaultFormPatFromUserPatternsId(String userPatternId) throws DBComponentException,
	UAComponentException;
}

package com.oxymedical.component.useradmin.util;

public class UserPatternInfo
{
	private String userPatternId;
	private String companyId;
	private String comments;
	private String defaultFormPattern;
	
	public String getUserPatternId()
	{
		return userPatternId;
	}
	public void setUserPatternId(String userPatternId)
	{
		this.userPatternId = userPatternId;
	}
	public String getCompanyId()
	{
		return companyId;
	}
	public void setCompanyId(String companyId)
	{
		this.companyId = companyId;
	}
	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	public String getDefaultFormPattern()
	{
		return defaultFormPattern;
	}
	public void setDefaultFormPattern(String defaultFormPattern)
	{
		this.defaultFormPattern = defaultFormPattern;
	}
}

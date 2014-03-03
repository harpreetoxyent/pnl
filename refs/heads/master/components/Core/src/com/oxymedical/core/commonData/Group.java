package com.oxymedical.core.commonData;

import java.io.Serializable;

public class Group implements IGroup,Serializable
{
	
	String groupId = null;
	String groupName = null;
	
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getGroupId() {
		
		return this.groupId;
	}

	public String getGroupName() {
		
		return this.groupName;
	}
	
		
	

	

}

package com.oxymedical.core.userdata;

public class Rights implements IRights,java.io.Serializable
{
	String rightId;
	//Methods added to set and get rightname by pra on 30-may-2009
	String rightName;
	
	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public String getRightsId()
	{
		return this.rightId;
	}

	public void setRightsId(String rightId)
	{
		this.rightId = rightId;
	}

}

package com.oxymedical.core.commonData;

public class DataPattern implements IDataPattern,java.io.Serializable
{
	/**is used to persist the id of the datapattern in it.*/
	String dataPatternId;
	/**is used to set the pattern name for the datapattern in it.*/
	String pattern;
	/**is used to set the group id for the datapattern.*/
	String groupId;
	
	/**
	 * @return the dataPatternId
	 */
	public String getDataPatternId()
	{
		return dataPatternId;
	}
	/**
	 * @param dataPatternId the dataPatternId to set
	 */
	public void setDataPatternId(String dataPatternId)
	{
		this.dataPatternId = dataPatternId;
	}
	/**
	 * @return the pattern
	 */
	public String getPattern()
	{
		return pattern;
	}
	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern)
	{
		this.pattern = pattern;
	}
	/**
	 * @return the groupId
	 */
	public String getGroupId()
	{
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

}

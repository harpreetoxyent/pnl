package com.oxymedical.component.db.application.register.pattern;
/**
 * This class is use to set the information for the
 * SearchPattern.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class SearchPattern 
{
	/**is used to set the searchId which maps to the searchPatternList in the searchHash hashtable.*/
	String searchId;
	/**is used to set the elementName which is retrieved from the attribute value of the queryElement as FieldName.*/
	String elementName;
	/**is used to set the joinOperator which is retrieved from the attribute value of the queryElement as Operator.*/
	String joinOperator;
	/**is used to set the conditionOperator which is retrieved from the attribute value of the queryElement as Condition.*/
	String conditionOperator;
	/**is used to set the mappingValue which is retrieved from the attribute value of the queryElement as fieldValue.*/
	String mappingValue;
	
	/**
	 * This method is used to get the conditionOperator which is retrieved from the 
	 * attribute value of the queryElement as Condition.
	 * @returns String
	 */
	public String getConditionOperator() 
	{
		return conditionOperator;
	}
	/**
	 * This method is used to set the conditionOperator which is retrieved from the 
	 * attribute value of the queryElement as Condition.
	 * @returns void
	 */
	public void setConditionOperator(String conditionOperator) 
	{
		this.conditionOperator = conditionOperator;
	}
	/**
	 * This method is used to get the elementName which is retrieved from the 
	 * attribute value of the queryElement as FieldName.
	 * @returns String
	 */
	public String getElementName() 
	{
		return elementName;
	}
	/**
	 * This method is used to set the elementName which is retrieved from the 
	 * attribute value of the queryElement as FieldName.
	 * @returns void
	 */
	public void setElementName(String elementName) 
	{
		this.elementName = elementName;
	}
	/**
	 * This method is used to get the joinOperator which is retrieved from the 
	 * attribute value of the queryElement as Operator.
	 * @returns String
	 */
	public String getJoinOperator() 
	{
		return joinOperator;
	}
	/**
	 * This method is used to set the joinOperator which is retrieved from the 
	 * attribute value of the queryElement as Operator.
	 * @returns void
	 */
	public void setJoinOperator(String joinOperator)
	{
		this.joinOperator = joinOperator;
	}
	/**
	 * This method is used to get the mappingValue which is retrieved from the 
	 * attribute value of the queryElement as fieldValue.
	 * @returns String
	 */
	public String getMappingValue() 
	{
		return mappingValue;
	}
	/**
	 * This method is used to set the mappingValue which is retrieved from the 
	 * attribute value of the queryElement as fieldValue.
	 * @returns void
	 */
	public void setMappingValue(String mappingValue) 
	{
		this.mappingValue = mappingValue;
	}
	/**
	 * This method is used to get the searchId 
	 * which maps to the searchPatternList in the searchHash hashtable.
	 * @returns String
	 */
	public String getSearchId()
	{
		return searchId;
	}
	/**
	 * This method is used to set the searchId 
	 * which maps to the searchPatternList in the searchHash hashtable.
	 * @returns void
	 */
	public void setSearchId(String searchId)
	{
		this.searchId = searchId;
	}
}

package com.oxymedical.component.db.application.register.pattern;
/**
 * This class is use to set the information for the
 * FormPattern.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class FormPattern 
{
	/**is used to set the rootWindowId which is retrieved from patternRoot.*/
	String rootWindowId;
	/**is used to set the element id which is retrieved from the patternRoot for the id tag.*/
	String parentWindowId;
	/**is used to get the element id which maps to the form pattern in the formPatternHash hashtable.*/
	String elementId;
	/**is used to set the element name which is retrieved from the patternRoot for the name tag.*/
	String elementName;
	/**is used to set the type i.e. the root id.*/
	String type;
	/**is used to set the datapattern id which is associated with formpattern.*/
	String assocDataPatternId;
	/**is used to set the searchId for the form pattern when the patternRoot attribute consist of the search tag.*/
	String searchId;
	
	/**
	 * Constructor that calls the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public FormPattern()
	{
		super();
	}
	/**
	 * this calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public FormPattern(String rootWindowId, String parentWindowId, String elementId, String elementName, String type, String assocDataPatternId, String searchId) 
	{
		super();
		this.rootWindowId = rootWindowId;
		this.parentWindowId = parentWindowId;
		this.elementId = elementId;
		this.elementName = elementName;
		//this.value = value;
		this.type = type;
		this.assocDataPatternId = assocDataPatternId;
		this.searchId = searchId;
	}
	/**
	 *This method is used to get the datapattern id 
	 *which is associated with formpattern.
	 * @returns String
	 */
	public String getAssocDataPatternId() 
	{
		return assocDataPatternId;
	}
	/**
	 *This method is used to set the datapattern id 
	 *which is associated with formpattern.
	 * @returns void
	 */
	public void setAssocDataPatternId(String assocDataPatternId) 
	{
		this.assocDataPatternId = assocDataPatternId;
	}
	/**
	 * This method is used to get the element id which maps to 
	 * the form pattern in the formPatternHash hashtable.
	 * @returns String
	 */
	public String getElementId() 
	{
		return elementId;
	}
	/**
	 * This method is used to set the element id which maps to 
	 * the form pattern in the formPatternHash hashtable.
	 * @returns void
	 */
	public void setElementId(String elementId) 
	{
		this.elementId = elementId;
	}
	/**
	 * This method is used to get the type i.e.
	 * the root id.
	 * @returns String
	 */
	public String getType() 
	{
		return type;
	}
	/**
	 * This method is used to set the type i.e.
	 * the root id.
	 * @returns void
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * This method is used to get the searchId for the form pattern 
	 * when the patternRoot attribute consist of the search tag.
	 * @returns String
	 */
	public String getSearchId() 
	{
		return searchId;
	}
	/**
	 * This method is used to set the searchId for the form pattern 
	 * when the patternRoot attribute consist of the search tag.
	 * @returns void
	 */
	public void setSearchId(String searchId) 
	{
		this.searchId = searchId;
	}
	/**
	 *This method is used to get the element name which is 
	 *retrieved from the patternRoot for the name tag.
	 * @returns String
	 */
	public String getElementName() 
	{
		return elementName;
	}
	/**
	 * This method is used to set the element name which is 
	 * retrieved from the patternRoot for the name tag.
	 * @returns void
	 */
	public void setElementName(String elementName) 
	{
		this.elementName = elementName;
	}
	/**
	 * This method is used to get the element id which is 
	 * retrieved from the patternRoot for the id tag.
	 * @returns String
	 */
	public String getParentWindowId() 
	{
		return parentWindowId;
	}
	/**
	 * This method is used to set the element id which is 
	 * retrieved from the patternRoot for the id tag.
	 * @returns void
	 */
	public void setParentWindowId(String parentWindowId) 
	{
		this.parentWindowId = parentWindowId;
	}
	/**
	 * This method is used to set the rootWindowId which is
	 * retrieved from patternRoot.
	 * @returns String
	 */
	public String getRootWindowId() 
	{
		return rootWindowId;
	}
	/**
	 * This method is used to set the rootWindowId which is
	 * retrieved from patternRoot.
	 * @returns void
	 */
	public void setRootWindowId(String rootWindowId) 
	{
		this.rootWindowId = rootWindowId;
	}
}

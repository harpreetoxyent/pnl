package com.oxymedical.component.db.application.tree;

/**
 * This class is use to set the information for the
 * DBComponent class.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class ValueField 
{
	/**is used to set the field name.*/
	String name;
	/**is used to set the field value which is the variable of the Object class.*/
	Object text;
	
	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public ValueField() {
		super();
	}
	/**
	 * this calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public ValueField(String name, Object text) {
		super();
		this.name = name;
		this.text = text;
	}
	
	/**
	 * This method is used to get the field name. 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method is used to set the field name in it. 
	 * @param name
	 * @return void
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method is used to get the field value which is 
	 * the variable of the Object class. 
	 * @return Object
	 */
	public Object getText() {
		return text;
	}
	
	/**
	 * This method is used to set the field value which is 
	 * the variable of the Object class.
	 * @param text
	 * @return void
	 */
	public void setText(Object text) {
		this.text = text;
	}
}
	
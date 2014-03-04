package com.oxymedical.component.db.application.tree;
/**
 * This class is use to set the information for the
 * DisplayField.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class DisplayField 
{
	/**is used to hold the field name in it.*/
	String name;
	/**is used to hold the field value in it which is the variable of the class Object.*/
	Object text;
	
	/**
	 * Constructor that call the constructor of 
	 * it's superclass i.e. Object class.
	 * 
	*/
	public DisplayField() {
		super();
	}
	/**
	 * this calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public DisplayField(String name, Object text) {
		super();
		this.name = name;
		this.text = text;
	}
	/**
	 *This method is used to get the field name. 
	 * @returns void
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *This method is used to set the field name.  
	 * @param name
	 * @returns void
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method is used to get the field value 
	 * which is the variable of the class Object. 
	 * @return Object
	 */
	public Object getText() {
		return text;
	}
	
	/**
	 * This method is used to set the field value in it
	 * which is the variable of the class Object.
	 * @param text
	 * @return void
	 */
	public void setText(Object text) {
		this.text = text;
	}

	
}

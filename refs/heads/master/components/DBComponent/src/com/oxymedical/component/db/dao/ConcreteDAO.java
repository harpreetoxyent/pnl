package com.oxymedical.component.db.dao;

import org.hibernate.mapping.PersistentClass;
/**
 * This class is used to hold the Persistence class object and
 * class name which is mapped to the field of the table.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class ConcreteDAO 
{
	/**this variable consists the name of the class that mapped to the field of the table.*/
	private String daoClassName;
	/**this variable is used to persist the Persistent class objects in it.*/
	private PersistentClass persistanceClass;
	
	/**
	 * This method is used to get the name of the class which is
	 * mapped to the field of the table.
	 * @returns String
	 */
	public String getDaoClassName() 
	{
		return daoClassName;
	}
	
	/**
	 * This method is used to set the name of the class which is
	 * mapped to the field of the table. 
	 * @param daoClassName
	 * @return void
	 */
	public void setDaoClassName(String daoClassName) 
	{
		this.daoClassName = daoClassName;
	}
	
	/**
	 * This method is used to get the object of the Persistent 
	 * class which has the information of the mapped class. 
	 * @return PersistentClass
	 */
	public PersistentClass getPersistanceClass() 
	{
		return persistanceClass;
	}
	
	/**
	 *This method is used to set the object of the Persistent 
	 *class which has the information of the mapped class. 
	 * @param persistanceClass
	 * @return void
	 */
	public void setPersistanceClass(PersistentClass persistanceClass) 
	{
		this.persistanceClass = persistanceClass;
	}

}

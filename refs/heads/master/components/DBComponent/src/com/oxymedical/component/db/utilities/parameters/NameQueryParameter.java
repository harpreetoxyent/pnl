package com.oxymedical.component.db.utilities.parameters;

import com.oxymedical.component.db.type.DBComponentType;


/**
 * This class is used to hold the parameter,value of Object class
 * variable and parameter type in it.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class NameQueryParameter 
{
	/**is used to set the parameter in the passed query. */
	String parameter = null;
	/**is used to set the value of parameter i.e. passed in the query.  */
	Object valueParameter = null;
	/**is used to set the type of parameter i.e. passed in the query. */
	DBComponentType typeParam = null;
	
	/**
	 * this constructor takes values of the parameter,value of Object class
     * variable into it's class variables.
	 * 
	*/
	public NameQueryParameter(String parameter, Object valueParameter)
	{
		this.parameter = parameter;
		this.valueParameter = valueParameter;
		this.typeParam = null;
	}
	/**
	 * this constructor takes values of the parameter,value of Object class
     * variable and the type of the parameter into it's class variables.
	 * 
	*/
	public NameQueryParameter(String parameter, Object valueParameter,DBComponentType typeParam)
	{
		this.parameter = parameter;
		this.valueParameter = valueParameter;
		this.typeParam = typeParam;		
	}
	
	/**
	 *This method is used to get the parameter i.e. passed
	 *in the query.
	 * @returns String
	 */
	public String getParameter() 
	{
		return parameter;
	}
	
	/**
	 *This method is used to set the parameter 
	 *in the passed query.
	 * @param parameter
	 * @returns void
	 */
	public void setParameter(String parameter) 
	{
		this.parameter = parameter;
	}
	
	/**
	 *This method is used to get the type of parameter i.e. passed
	 *in the query.
	 * @returns DBComponentType
	 */
	public DBComponentType getTypeParam() 
	{
		return typeParam;
	}
	
	/**
	 *This method is used to set the type of parameter i.e. passed
	 *in the query.
	 * @param typeParam
	 * @return void
	 */
	public void setTypeParam(DBComponentType typeParam) 
	{
		this.typeParam = typeParam;
	}
	
	/**
	 *This method is used to get the value of parameter i.e. passed
	 *in the query.
	 * @return Object
	 */
	public Object getValueParameter() 
	{
		return valueParameter;
	}
	
	/**
	 *This method is used to set the value of parameter i.e. passed
	 *in the query. 
	 * @param valueParameter
	 * @return void
	 */
	public void setValueParameter(Object valueParameter) 
	{
		this.valueParameter = valueParameter;
	}	
}

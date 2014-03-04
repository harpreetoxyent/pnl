package com.oxymedical.component.db.utilities.parameters;

import org.hibernate.type.Type;

/**
 * 
 * 
 */

public class IndexQueryParameter 
{

	int indexParameter;
	Object valueParameter = null;
	Type typeParam = null;
	
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public IndexQueryParameter(int indexParameter, Object valueParameter)
	{
		this.indexParameter = indexParameter;
		this.valueParameter = valueParameter;
		this.typeParam = null;
	}
	
	/**
	 * this constructor calls the constructor of it's superclass i.e. Object
	 *  class and takes values into it's class variables.
	 * 
	*/
	public IndexQueryParameter(int indexParameter, Object valueParameter,Type typeParam)
	{
		this.indexParameter = indexParameter;
		this.valueParameter = valueParameter;
		this.typeParam = typeParam;		
	}
	
	/**
	 *
	 * 
	 * @return int
	 */
	public int getIndexParameter() 
	{
		return indexParameter;
	}
	
	/**
	 *
	 * 
	 * @param indexParameter
	 * @return void
	 */
	public void setIndexParameter(int indexParameter) 
	{
		this.indexParameter = indexParameter;
	}
	
	/**
	 *
	 * 
	 * @return Type
	 */
	public Type getTypeParam() 
	{
		return typeParam;
	}
	
	/**
	 *
	 * 
	 * @param typeParam
	 * @return void
	 */
	public void setTypeParam(Type typeParam) 
	{
		this.typeParam = typeParam;
	}
	
	/**
	 *
	 * 
	 * @return Object
	 */
	public Object getValueParameter() 
	{
		return valueParameter;
	}
	
	/**
	 *
	 * 
	 * @param valueParameter
	 * @return void
	 */
	public void setValueParameter(Object valueParameter) 
	{
		this.valueParameter = valueParameter;
	}		
}

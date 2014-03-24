package com.oxymedical.component.rulesComponent.rete.productionMemory.node;

import com.oxymedical.component.rulesComponent.INode;

/**
 * Object type node of rete
 * 
 * @author Oxyent Medical
 *
 */
public class ObjectTypeNode implements INode 
{
	
	String className;
	
	Class clazz;
	
	AlphaNode alphaNode;
	
	public ObjectTypeNode() 
	{		
	}

	public int getId() 
	{
		return 0;
	}

	public void addNode(INode node) 
	{
			
	}

	public void deleteNode(INode node) 
	{
			
	}

	public AlphaNode getAlphaNode() 
	{
		return alphaNode;
	}

	public void setAlphaNode(AlphaNode alphaNode) 
	{
		this.alphaNode = alphaNode;
	}

	public String getClassName() 
	{
		return className;
	}

	public void setClassName(String className) 
	{
		this.className = className;
	}

	public Class getClazz() 
	{
		return clazz;
	}

	public void setClazz(Class clazz) 
	{
		this.clazz = clazz;
	}
}

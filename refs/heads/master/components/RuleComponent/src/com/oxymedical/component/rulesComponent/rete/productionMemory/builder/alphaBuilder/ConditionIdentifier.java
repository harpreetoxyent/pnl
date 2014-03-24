package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder;

import com.oxymedical.component.rulesComponent.INode;

/**
 * This class identifies a condition uniquely with the name and integer id.
 * 
 * @author Oxyent Medical
 *
 */
public class ConditionIdentifier implements INode 
{
	
	private String name; // name of the object type node
	 
	private Integer condId;
	
	private ConditionConstraints condition;
	
	public ConditionIdentifier(String name, Integer id, ConditionConstraints condition) {
		this.name = name;
		this.condId = id;
		this.condition = condition;
	}
	
	public ConditionIdentifier(String name, ConditionConstraints condition) {
		this.name = name;
		//this.condId = id;
		this.condition = condition;
	}

	public ConditionConstraints getCondition() 
	{
		return condition;
	}
	
	public Integer getCondId() 
	{
		return condId;
	}

	public String getName() 
	{
		return name;
	}

	public void addNode(INode node) 
	{
		
	}

	public void deleteNode(INode node) 
	{
		
	}

	public int getId() 
	{
		return 0;
	}

}

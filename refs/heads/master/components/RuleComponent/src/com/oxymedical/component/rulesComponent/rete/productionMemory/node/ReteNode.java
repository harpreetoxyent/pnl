package com.oxymedical.component.rulesComponent.rete.productionMemory.node;

import java.util.HashMap;

import com.oxymedical.component.rulesComponent.INode;


/**
 * This is the main node of rete that has the list of all object type nodes
 * 
 * @author Oxyent Medical
 *
 */
public class ReteNode implements INode 
{	
	
	HashMap<String, ObjectTypeNode> objectTypeList;
	
	public ReteNode() 
	{		
		this.objectTypeList = new HashMap<String, ObjectTypeNode>();
	}
	
	public void addNode(INode node) 
	{
			
	}

	public void deleteNode(INode node) 
	{
				
	}

	public HashMap<String, ObjectTypeNode> getObjectTypeList() 
	{
		return objectTypeList;
	}

	public void addObjectType(String type, ObjectTypeNode objectType) 
	{
		this.objectTypeList.put(type, objectType);
	}

	public int getId() 
	{
		return 0;
	}

}

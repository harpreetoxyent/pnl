package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.objectTypeBuilder;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.rulesComponent.RuleComponent;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.AlphaNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ObjectTypeNode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ReteNode;
import com.oxymedical.component.rulesComponent.rule.elements.Variables;


/**
 * Builds the object type node of rete
 * 
 * @author Oxyent Medical
 *
 */
public class ObjectTypeBuilder {
	
	private static ClassLoader classLoader;
	
	Hashtable<String, String> types; // to store id, type from the variable list
	
	public ObjectTypeBuilder() {
		types = new Hashtable<String, String>(); 
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader ruleClassLoader) {
		classLoader = ruleClassLoader;
	}

	/**
	 * Parses the variable list and builds object type nodes of rete.
	 * 
	 * @param variableList
	 * @param reteNode
	 */
	public void buildObjectTypeNodes(List variableList, ReteNode reteNode) 
	{
		ObjectTypeNode objectTypeNode = null;
		String type = null;
		Class clazz = null;
		AlphaNode alphaNode = null;
		if (null != variableList) 
		{
			try 
			{
				for (Iterator it = variableList.iterator(); it.hasNext();) 
				{
					Variables var = (Variables)it.next();
					type = var.getType().trim();
					
					// add the variable type and id in hashtable
					types.put(var.getId(), type);					
					objectTypeNode = new ObjectTypeNode();										
					if (!checkContains(type, reteNode)) 
					{						
						clazz = classLoader.loadClass(type);					
						objectTypeNode.setClassName(type);
						objectTypeNode.setClazz(clazz);
						
						alphaNode = new AlphaNode();
						alphaNode.setObjectTypeNode(objectTypeNode);
						objectTypeNode.setAlphaNode(alphaNode); 
						
						// add to rete node
						addToReteNode(type, objectTypeNode, reteNode);
					}
				}
			}
			catch(ClassNotFoundException ie) 
			{
				ie.printStackTrace();
			}
		}
		else 
		{
			 RuleComponent.logger.log(0,"Variable list NULL");
		}
		
	}
	
	
	/**
	 * checks if the type of object type node already exists in rete
	 * @param type
	 * @param reteNode
	 * @return
	 */
	private boolean checkContains(String type, ReteNode reteNode) 
	{
		return reteNode.getObjectTypeList().containsKey(type);
	}

	/**
	 * Adds object type node to rete if not present
	 * 
	 * @param type
	 * @param objectType
	 * @param reteNode
	 */
	private void addToReteNode(String type, ObjectTypeNode objectType, ReteNode reteNode) 
	{
		if (!checkContains(type, reteNode))
		{
			reteNode.addObjectType(type, objectType);
		}
	}
	
	/**
	 * Returns the hashtable that stores the id and type
	 * @return
	 */
	public Hashtable<String, String> getTypes() {
		return types;
	}
	
	
}

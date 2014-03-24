package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder;

import java.util.HashMap;
import java.util.Map;

import com.oxymedical.component.rulesComponent.rete.productionMemory.node.JoinNode;


/**
 * This consists of the details of a condition defined by the user. 
 * 
 * @author Oxyent Medical
 *
 */
public class ConditionConstraints 
{
	
	private String objectTypeName;
	
	private String conditionString;
	
	private String nameOfCondition;
	
	private String constraintType; // this specifies whether the added name id an field or method
	
	private Class dataType; // this stores the data type of field or return type of method
	
	private String operatorType; // this stores the relational operator btn the field and the value
	
	private String value;
	
	private Map<ResolutorKey, JoinNode> conditionJoinMap;
	
	public ConditionConstraints() 
	{
		this.conditionJoinMap = new HashMap<ResolutorKey, JoinNode>();		
	}	
	public JoinNode getJoinNode(ResolutorKey key) 
	{
		return conditionJoinMap.get(key);
	}
	public Map<ResolutorKey, JoinNode> getConditionJoinMap() 
	{
		return this.conditionJoinMap;
	}
	public void addConditionJoin(ResolutorKey key, JoinNode joinNode) 
	{
		this.conditionJoinMap.put(key, joinNode);
	}	
	public String getConstraintType() 
	{
		return constraintType;
	}
	public void setConstraintType(String constraintType) 
	{
		this.constraintType = constraintType;
	}
	public Class getDataType() 
	{
		return dataType;
	}	
	public String getNameOfCondition() 
	{
		return nameOfCondition;
	}
	public void setNameOfCondition(String nameOfCondition) 
	{
		this.nameOfCondition = nameOfCondition;
	}
	public String getOperatorType() 
	{
		return operatorType;
	}
	public void setOperatorType(String operatorType) 
	{
		this.operatorType = operatorType;
	}
	public String getObjectTypeName() 
	{
		return objectTypeName;
	}
	public void setObjectTypeName(String ruleClassName) 
	{
		this.objectTypeName = ruleClassName;
	}
	public String getValue() 
	{
		return value;
	}
	public void setValue(String value) 
	{
		this.value = value;
	}
	public String getConditionString() 
	{
		return conditionString;
	}
	public void setConditionString(String conditionString) 
	{
		this.conditionString = conditionString;
	}
	public void setDataType(Class dataType) {
		this.dataType = dataType;
	}
}

package com.oxymedical.component.rulesComponent.rete.productionMemory.node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.oxymedical.component.rulesComponent.INode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ConditionConstraints;
import com.oxymedical.component.rulesComponent.workingMemory.Fact;


/**
 * Creates join node of rete.
 * Join node consists of a left input and a right input node - beta node
 * and a binder - this can be an AND or OR
 * It consists of a key to identify the join node
 * 
 * @author Oxyent Medical
 *
 */
public class AlphaNode implements INode {
	
	ObjectTypeNode objectTypeNode;
	
	int id;
	
	List<Fact> factsList;
	
	Hashtable<Integer, ConditionConstraints> conditions;	
	
	public AlphaNode() {		
		conditions = new Hashtable<Integer, ConditionConstraints>();
		id = 0;
		factsList = new ArrayList<Fact>();		
	}
	
	public ObjectTypeNode getObjectTypeNode() {
		return objectTypeNode;
	}

	public void setObjectTypeNode(ObjectTypeNode objectTypeNode) {
		this.objectTypeNode = objectTypeNode;
	}
	
	public Hashtable<Integer, ConditionConstraints> getConditionConstraintTable() {
		return conditions;
	}

	public void addConditionConstraint(ConditionConstraints condition) {
		this.conditions.put(new Integer(getId()), condition);
	}

	public int getId() {
		return this.id;
	}
	
	public void incrementId() {
		this.id++;
	}

	public void addNode(INode node) {
		
	}

	public void deleteNode(INode node) {
		
	}

	public List<Fact> getFactsList() {
		return factsList;
	}

	public void addToFactsList(Fact fact) {
		if (!this.factsList.contains(fact))
			this.factsList.add(fact);
	}

}

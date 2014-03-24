package com.oxymedical.component.rulesComponent.rete.productionMemory.node;

import com.oxymedical.component.rulesComponent.INode;

/**
 * Creates beta node of rete.
 * Beta node consists of a left input and a right input node
 * input can either be a condition node or a join node itself
 * 
 * @author Oxyent Medical
 *
 */
public class BetaNode implements INode {
	
	static int id = 0;
	
	private INode leftInput;
	
	private INode rightInput;	
	
	public BetaNode(INode leftInput, INode rightInput) {
		this.leftInput = leftInput;
		this.rightInput = rightInput;		
	}	
	
	public int getId() {
		return this.id;
	}
	
	public int incrementId() {
		return this.id++;
	}

	public void addNode(INode node) {
		
	}

	public void deleteNode(INode node) {
		
	}

	public INode getLeftInput() {
		return leftInput;
	}

	public INode getRightInput() {
		return rightInput;
	}

}

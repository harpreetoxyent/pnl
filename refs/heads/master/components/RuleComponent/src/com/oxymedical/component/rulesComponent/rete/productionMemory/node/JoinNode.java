package com.oxymedical.component.rulesComponent.rete.productionMemory.node;

import com.oxymedical.component.rulesComponent.INode;
import com.oxymedical.component.rulesComponent.rete.productionMemory.builder.alphaBuilder.ResolutorKey;


/**
 * Creates join node of rete.
 * Join node consists of a left input and a right input node - beta node
 * and a binder - this can be an AND or OR
 * It consists of a key to identify a join node
 * 
 * @author Oxyent Medical
 *
 */
public class JoinNode extends BetaNode {
	
	String binder;
	
	ResolutorKey key;
	
	INode childNode;
	
	public INode getChildNode() {
		return childNode;
	}

	public void addChildNode(INode childNode) {
		this.childNode = childNode;
	}

	public JoinNode(INode leftInput, INode rightInput, String binder) {
		super(leftInput, rightInput);
		this.binder = binder;	
	}

	public String getBinder() {
		return binder;
	}

	public ResolutorKey getKey() {
		return key;
	}

	public void setKey(ResolutorKey key) {
		this.key = key;
	}
	
}

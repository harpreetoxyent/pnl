package com.oxymedical.component.rulesComponent.rete.productionMemory;

import com.oxymedical.component.rulesComponent.rete.productionMemory.node.ReteNode;

public class Rete {
	
	ReteNode reteNode;
	
	public Rete(){
		reteNode = new ReteNode();
	}

	public ReteNode getReteNode() {
		return reteNode;
	}

	public void setReteNode(ReteNode reteNode) {
		this.reteNode = reteNode;
	}

}

package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder;

import java.util.ArrayList;
import java.util.List;

import com.oxymedical.component.rulesComponent.rete.productionMemory.node.JoinNode;


/**
 * This identifies a beta node of rete 
 * this contains a condition string which is a participant of join node together with the join node list. 
 * 
 * @author Oxyent Medical
 *
 */
public class BetaIdentifier {
	
	private String condString;
	
	private List<JoinNode> joinNodeList = new ArrayList<JoinNode>();
	
	public BetaIdentifier(String condString, JoinNode joinNode) {
		this.condString = condString;
		this.joinNodeList.add(joinNode);
	}

	public String getCondString() {
		return condString;
	}

	public List<JoinNode> getJoinNodeList() {
		return joinNodeList;
	}

}

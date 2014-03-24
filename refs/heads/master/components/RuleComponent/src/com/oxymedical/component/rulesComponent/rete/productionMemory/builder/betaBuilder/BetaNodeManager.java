package com.oxymedical.component.rulesComponent.rete.productionMemory.builder.betaBuilder;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.oxymedical.component.rulesComponent.rete.productionMemory.node.JoinNode;


/**
 * Helper class that manages the beta nodes of rete tree
 * 
 * @author Oxyent Medical
 *
 */
public class BetaNodeManager {
	
	List<String> participantBetaList; 
	
	List<JoinNode> joinNodeList = null;
	
	static Hashtable<String, List<BetaIdentifier>> betaIdentifierList; // conditionString
	
	public BetaNodeManager() {
		participantBetaList = new ArrayList<String>();		
		betaIdentifierList = new Hashtable<String, List<BetaIdentifier>>();
	}
	
	/**
	 * Gets the matching rules from the beta node identifier
	 * 
	 * @param participantConditionsList
	 * @return
	 */
	public List getMatchingRules(List participantConditionsList){	
		List<BetaIdentifier> betaIdentifierList = null;
		this.joinNodeList = new ArrayList<JoinNode>();
		participantBetaList = new ArrayList<String>();
		for(Iterator it = participantConditionsList.iterator(); it.hasNext();) {
			String condString = (String)it.next();	
			if (null != this.participantBetaList) {
				// add the condition in beta node participant list	
				this.participantBetaList.add(condString);
				for(Iterator participantBetaListIterator = this.participantBetaList.iterator(); participantBetaListIterator.hasNext();) {
					String condition = (String)participantBetaListIterator.next();
					betaIdentifierList = this.betaIdentifierList.get(condition.trim());
					if (null != betaIdentifierList) {
						for(Iterator betaIdentifierListIterator = betaIdentifierList.iterator(); betaIdentifierListIterator.hasNext();) {
							BetaIdentifier betaIdentifier = (BetaIdentifier)betaIdentifierListIterator.next();
							// we need to find from the participantBetaList in case of any composite key conditions added
							if (null != betaIdentifier && checkContains(this.participantBetaList, betaIdentifier.getCondString())) {
								for(Iterator betaIdentifierJoinNodeIterator = betaIdentifier.getJoinNodeList().iterator(); betaIdentifierJoinNodeIterator.hasNext();) {
									JoinNode jn = (JoinNode)betaIdentifierJoinNodeIterator.next();
									if (!this.joinNodeList.contains(jn))									
										this.joinNodeList.add(jn);
								}
							}
						}
					}
				}
			}	
		}
		// RuleComponent.logger.log(0,"this.joinNodeList.size() = " + this.joinNodeList.size());
		return this.joinNodeList;
	}
	
	/**
	 * Gets the list of beta identifer for the specified condition with all its combination
	 * 
	 * @param condString
	 * @return
	 */
	private List<BetaIdentifier> getBetaIdentifierListForCondiiton(String condString) {
		Set keys = this.betaIdentifierList.keySet();
		List<BetaIdentifier> betaIdentifierList = new ArrayList<BetaIdentifier>();
		List<BetaIdentifier> tempBetaIdentiferList = new ArrayList<BetaIdentifier>();
		for(Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String)it.next();
			// RuleComponent.logger.log(0,"key = " +key);
			tempBetaIdentiferList = this.betaIdentifierList.get(key);
			if (null != tempBetaIdentiferList && tempBetaIdentiferList.size() > 0)
				betaIdentifierList.addAll(tempBetaIdentiferList);
		}
		return betaIdentifierList;
	}
	
	/**
	 * Gets the list of conditions seperated by $ in a list.
	 * 
	 * @param condString
	 * @return
	 */
	private List<String> getConditionCollection(String condString) {
		List<String> condCollection = new ArrayList<String>();
		String regx = "[$]";
		String[] st = Pattern.compile(regx).split(condString);
		if (st.length == 0){
			condCollection.add(condString.trim());
		}
		else {
			for(int i=0; i<st.length; i++) {
				condCollection.add(st[i].trim());
			}
		}
		return condCollection;
	}
	
	
	/**
	 * Checks if the condition straing contains in the list of participant conditions
	 * 
	 * @param participantBetaList
	 * @param condString
	 * @return
	 */
	private boolean checkContains(List participantBetaList, String condString) {
		List<String> condCollection = getConditionCollection(condString);
		if (participantBetaList.containsAll(condCollection)) 
			return true;
		
		/*String condition = "", conditionStr="";
		for(Iterator it = participantBetaList.iterator(); it.hasNext();) {
			String cond = (String)it.next();
			condition = condition+cond;
			conditionStr = cond+conditionStr;
			if (cond.equals(condString)) {
				return true;
			}
			else if (condition.equals(condString)) {
				return true;
			}else if (conditionStr.equals(condString)) {
				return true;
			}
		}*/
		return false;
	}
	
	public Hashtable<String, List<BetaIdentifier>> getBetaIdentifierList() {
		return betaIdentifierList;
	}

	/**
	 * Adds a condition string to beta node identifier
	 * 
	 * @param condString
	 * @param betaIdentifier
	 */
	public void addBetaIdentifierList(String condString, BetaIdentifier betaIdentifier) {	
		List<BetaIdentifier> betaIdentifierList = this.betaIdentifierList.get(condString);
		if (null == betaIdentifierList) {
			betaIdentifierList = new ArrayList<BetaIdentifier>();
		}
		//if (null != betaIdentifierList && !checkContains(betaIdentifierList, betaIdentifier)) {
		if (null != betaIdentifierList) {
			betaIdentifierList.add(betaIdentifier);
			this.betaIdentifierList.put(condString, betaIdentifierList);
		}
	}
	
	private boolean checkContains(List betaIdentifierList, BetaIdentifier betaIdentifier) {
		for(Iterator it = betaIdentifierList.iterator(); it.hasNext();) {
			BetaIdentifier ident = (BetaIdentifier)it.next();
			if (ident.getCondString().trim().equals(betaIdentifier.getCondString().trim())) {			
				return true;
			}
		}
		return false;
	}

	public List<String> getParticipantBetaList() {
		return participantBetaList;
	}

	public void registerParticipantBetaCondition(String condString) {
		this.participantBetaList.add(condString);
	}

	public List<JoinNode> getJoinNodeList() {
		return joinNodeList;
	}

}

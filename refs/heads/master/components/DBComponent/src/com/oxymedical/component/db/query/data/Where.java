package com.oxymedical.component.db.query.data;

import java.util.List;

import com.oxymedical.core.querydata.ConditionJoiner;

public class Where
{
	public List<WhereInner> whereQueries;
	public ConditionJoiner outerConditionJoiner = null;
	public boolean firstCondition;
	/**
	 * @return the whereQueries
	 */
	public List<WhereInner> getWhereQueries() {
		return whereQueries;
	}
	/**
	 * @param whereQueries the whereQueries to set
	 */
	public void setWhereQueries(List<WhereInner> whereQueries) {
		this.whereQueries = whereQueries;
	}
	/**
	 * @return the outerConditionJoiner
	 */
	public ConditionJoiner getOuterConditionJoiner() {
		return outerConditionJoiner;
	}
	/**
	 * @param outerConditionJoiner the outerConditionJoiner to set
	 */
	public void setOuterConditionJoiner(ConditionJoiner outerConditionJoiner) {
		this.outerConditionJoiner = outerConditionJoiner;
	}
	/**
	 * @return the firstCondition
	 */
	public boolean isFirstCondition() {
		return firstCondition;
	}
	/**
	 * @param firstCondition the firstCondition to set
	 */
	public void setFirstCondition(boolean firstCondition) {
		this.firstCondition = firstCondition;
	}
	
}

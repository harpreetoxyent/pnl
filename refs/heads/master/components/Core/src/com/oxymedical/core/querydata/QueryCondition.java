package com.oxymedical.core.querydata;

public class QueryCondition
{
	private String fieldName;
	private String fieldValue;
	private ValueRange range;
	private ConditionJoiner joiner;
	
	/** Uniquely identifies each condition */
	// Could be a hashcode
	private long uniqueVal;
	// get this value from UI
	private boolean firstConditon;

	
	/**
	 * @return the firstConditon
	 */
	public boolean isFirstConditon() {
		return firstConditon;
	}

	/**
	 * @param firstConditon the firstConditon to set
	 */
	public void setFirstConditon(boolean firstConditon) {
		this.firstConditon = firstConditon;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the range
	 */
	public ValueRange getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(ValueRange range) {
		this.range = range;
	}

	/**
	 * @return the joiner
	 */
	public ConditionJoiner getJoiner() {
		return joiner;
	}

	/**
	 * @param joiner the joiner to set
	 */
	public void setJoiner(ConditionJoiner joiner) {
		this.joiner = joiner;
	}

	/**
	 * @return the uniqueVal
	 */
	public long getUniqueVal() {
		return uniqueVal;
	}

	/**
	 * @param uniqueVal the uniqueVal to set
	 */
	public void setUniqueVal(long uniqueVal) {
		this.uniqueVal = uniqueVal;
	}
	
	
	
}

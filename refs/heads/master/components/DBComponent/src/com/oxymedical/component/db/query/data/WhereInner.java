package com.oxymedical.component.db.query.data;

import com.oxymedical.core.querydata.ConditionJoiner;
import com.oxymedical.core.querydata.ValueRange;

public class WhereInner
{
	public String fieldName;
	public String fieldValue;
	public ConditionComparator comparator;
	public FieldType fieldType;
	public String fieldNameTableAlias;
	public String fieldValueTableAlias;
	public ConditionJoiner conditionJoiner;
	private ValueRange range;
	
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
	 * @return the comparator
	 */
	public ConditionComparator getComparator() {
		return comparator;
	}
	/**
	 * @param comparator the comparator to set
	 */
	public void setComparator(ConditionComparator comparator) {
		this.comparator = comparator;
	}
	/**
	 * @return the fieldType
	 */
	public FieldType getFieldType() {
		return fieldType;
	}
	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * @return the fieldNameTableAlias
	 */
	public String getFieldNameTableAlias() {
		return fieldNameTableAlias;
	}
	/**
	 * @param fieldNameTableAlias the fieldNameTableAlias to set
	 */
	public void setFieldNameTableAlias(String fieldNameTableAlias) {
		this.fieldNameTableAlias = fieldNameTableAlias;
	}
	/**
	 * @return the fieldValueTableAlias
	 */
	public String getFieldValueTableAlias() {
		return fieldValueTableAlias;
	}
	/**
	 * @param fieldValueTableAlias the fieldValueTableAlias to set
	 */
	public void setFieldValueTableAlias(String fieldValueTableAlias) {
		this.fieldValueTableAlias = fieldValueTableAlias;
	}
	/**
	 * @return the conditionJoiner
	 */
	public ConditionJoiner getConditionJoiner() {
		return conditionJoiner;
	}
	/**
	 * @param conditionJoiner the conditionJoiner to set
	 */
	public void setConditionJoiner(ConditionJoiner conditionJoiner) {
		this.conditionJoiner = conditionJoiner;
	}
	
}

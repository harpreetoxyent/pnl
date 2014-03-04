/**
 * 
 */
package com.oxymedical.component.db.query.data;

import com.oxymedical.component.db.constants.QueryConstants;
import com.oxymedical.core.querydata.ConditionJoiner;
import com.oxymedical.core.querydata.ValueRange;


/**
 * @author vka
 *
 */
public class BaseQuery {

	public String getOperator(ConditionJoiner conditionJoiner)
	{
		switch (conditionJoiner)
		{
		case AND	: return QueryConstants.WHERE_CONDITION_JOINER_AND;
		case OR		: return QueryConstants.WHERE_CONDITION_JOINER_OR;
		default		: return QueryConstants.WHERE_CONDITION_JOINER_AND;
		}
	}

	public String getOperator(ConditionComparator comparator)
	{
		switch (comparator)
		{
		case EQUAL_TO				: return QueryConstants.CONDITION_EQUAL_TO;
		case NOT_EQUAL_TO			: return QueryConstants.CONDITION_NOT_EQUAL_TO;
		case GREATER_THAN			: return QueryConstants.CONDITION_GREATER_THAN;
		case GREATER_THAN_EQUAL_TO	: return QueryConstants.CONDITION_GREATER_THAN_EQUAL_TO;
		case LESS_THAN 				: return QueryConstants.CONDITION_LESS_THAN;
		case LESS_THAN_EQUAL_TO 	: return QueryConstants.CONDITION_LESS_THAN_EQUAL_TO;
		default 					: return QueryConstants.CONDITION_EQUAL_TO;
		}
	}

	public StringBuffer getRangeQuery(WhereInner whereInner)
	{
		ValueRange range = whereInner.getRange();
		StringBuffer rangeStr = new StringBuffer();
		if(range.getStart()!=null)
		{
			rangeStr = rangeStr.append(QueryConstants.CONDITION_GREATER_THAN_EQUAL_TO);
			rangeStr = ((whereInner.getFieldType().equals(FieldType.STRING)||whereInner.getFieldType().equals(FieldType.DATE)) && whereInner.getFieldValueTableAlias() == null) ? rangeStr.append(QueryConstants.STRING_QUOTES) : rangeStr;
			rangeStr = rangeStr.append(range.getStart());
			rangeStr = ((whereInner.getFieldType().equals(FieldType.STRING)||whereInner.getFieldType().equals(FieldType.DATE)) && whereInner.getFieldValueTableAlias() == null) ? rangeStr.append(QueryConstants.STRING_QUOTES) : rangeStr;
		}
		if(range.getEnd()!=null)
		{
			System.out.println();
			if(range.getStart()!=null)
			{
				rangeStr = rangeStr.append(QueryConstants.WHERE_CONDITION_JOINER_AND);
				rangeStr = (whereInner.getFieldNameTableAlias()!= null) ? rangeStr.append(whereInner.getFieldNameTableAlias()).append(QueryConstants.TABLE_FIELD_SEP) : rangeStr;
				rangeStr = rangeStr.append(whereInner.getFieldName());
			}
			rangeStr = rangeStr.append(QueryConstants.CONDITION_LESS_THAN_EQUAL_TO);
			rangeStr = ((whereInner.getFieldType().equals(FieldType.STRING)||whereInner.getFieldType().equals(FieldType.DATE)) && whereInner.getFieldValueTableAlias() == null) ? rangeStr.append(QueryConstants.STRING_QUOTES) : rangeStr;
			rangeStr = rangeStr.append(range.getEnd());
			rangeStr = ((whereInner.getFieldType().equals(FieldType.STRING)||whereInner.getFieldType().equals(FieldType.DATE)) && whereInner.getFieldValueTableAlias() == null) ? rangeStr.append(QueryConstants.STRING_QUOTES) : rangeStr;
			
		}
		return rangeStr;
	}
}

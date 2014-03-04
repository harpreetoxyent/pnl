/**
 * 
 */
package com.oxymedical.component.db.query.data;

import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.constants.QueryConstants;
import com.oxymedical.component.db.utilities.DBStructureUtil;


/**
 * @author vka
 *
 */
public class WhereQuery extends BaseQuery{
	
	public WhereQuery()
	{
		
	}

	
	/**
	 * buildWhereQuery create the complete where clause
	 * @param whereList
	 * @param query
	 * @return
	 */
	public StringBuffer buildWhereQuery(List<Where> whereList,StringBuffer query)
	{
		StringBuffer wherequery = new StringBuffer(query);
		Iterator<Where> whereItr = whereList.iterator();
		while(whereItr.hasNext())
		{
			Where where = whereItr.next();
			List<WhereInner> whereInner = where.getWhereQueries();
			wherequery = (where.getOuterConditionJoiner()==null) ?wherequery.append(QueryConstants.WHERE) :wherequery.append(getOperator(where.getOuterConditionJoiner()));
			wherequery = wherequery.append(QueryConstants.WHERE_CONDITION_BRACKET_START);
			wherequery.append(this.createWhereInner(whereInner));
			wherequery = wherequery.append(QueryConstants.WHERE_CONDITION_BRACKET_END);
		}
		
		return wherequery;
	}
	
	
	
	/**
	 * createWhereInner method create condition query
	 * @param innerCondition
	 * @return
	 */
	public StringBuffer createWhereInner(List<WhereInner> innerCondition)
	{
		StringBuffer innerQuery = new StringBuffer();
		Iterator<WhereInner> innerItr = innerCondition.iterator();
		while(innerItr.hasNext())
		{
			WhereInner whereInner = innerItr.next();
			
			//innerQuery = (whereInner.getConditionJoiner() == null) ? innerQuery : innerQuery.append(getOperator(where.getConditionJoiner()));
			innerQuery = (whereInner.getFieldNameTableAlias()!= null) ? innerQuery.append(whereInner.getFieldNameTableAlias()).append(QueryConstants.TABLE_FIELD_SEP) : innerQuery;
			innerQuery = innerQuery.append(whereInner.getFieldName());
			if(whereInner.getFieldValue()!=null)
			{
				innerQuery = innerQuery.append(getOperator(ConditionComparator.EQUAL_TO));
			}
			innerQuery = ((whereInner.getFieldType().equals(FieldType.STRING)||whereInner.getFieldType().equals(FieldType.DATE)) && whereInner.getFieldValueTableAlias() == null && whereInner.getRange()==null) ? innerQuery.append(QueryConstants.STRING_QUOTES) : innerQuery;
			innerQuery = (whereInner.getFieldValueTableAlias() != null) ? innerQuery.append(whereInner.getFieldValueTableAlias()).append(QueryConstants.TABLE_FIELD_SEP) : innerQuery;
			//innerQuery = innerQuery.append(whereInner.getFieldValue());
			innerQuery = (whereInner.getFieldValue()!=null) ? innerQuery.append(whereInner.getFieldValue()) :(whereInner.getRange()!=null)? innerQuery.append(this.getRangeQuery(whereInner)) : innerQuery; 
			innerQuery = ((whereInner.getFieldType().equals(FieldType.STRING)||whereInner.getFieldType().equals(FieldType.DATE)) && whereInner.getFieldValueTableAlias() == null && whereInner.getRange()==null) ? innerQuery.append(QueryConstants.STRING_QUOTES) : innerQuery;
		}
		return innerQuery;		
	}
	
	public StringBuffer createReferenceCondition(List<From> fromList,StringBuffer query,DBComponent dbObject)
	{
		StringBuffer reference = new StringBuffer(query);
		DBStructureUtil dbUtil = new DBStructureUtil();
		dbUtil.setDbc(dbObject);
		boolean firstTable = true;
		String oldTableName = null;
		StringBuffer refStr = new StringBuffer();
		StringBuffer refCondition = new StringBuffer(" and (");
		String parentTable = null;
		String parentUniqueId = null;
		Iterator<From> fromIterator = fromList.iterator();
		while(fromIterator.hasNext())
		{
			From from = fromIterator.next();
			if(!firstTable)
			{
				String nextTable = from.getTable();
				if(nextTable.equalsIgnoreCase(oldTableName))
				{
					continue;
				}
			}
			if(firstTable)
			{
				parentTable = from.getTable();
				
				//tableName = QueryConstants.CLASSPACKAGE_NAME+tableName;
				parentUniqueId = dbUtil.getUniqueId(parentTable);
				parentTable = parentTable.replace(QueryConstants.CLASSPACKAGE_NAME, "");
				refCondition = refCondition.append(parentTable).append(".").append(parentUniqueId).append("=");
			}
			else
			{
				String nextTable = from.getTable();	
				String referenceId = dbUtil.getReferenceId(nextTable,parentTable);
				if(referenceId!=null)
				{
					nextTable = nextTable.replace(QueryConstants.CLASSPACKAGE_NAME, "");
					refStr.append(refCondition);
					refStr.append(nextTable).append(".").append(referenceId).append(".").append(parentUniqueId).append(")");
				}
			}
					
			firstTable = false;
			oldTableName = from.getTable();
			
		}
		// condition added for deleted patient.
		String deletedColumn = dbUtil.getClassField(parentTable, "deleted");
		refStr = this.mapForeignTable(fromList,dbUtil,refStr);
		if(deletedColumn !=null)
		{
			if(reference.toString().contains("where"))
			{
				String deletedStr = " and "+parentTable+"."+deletedColumn+"='0'";
				refStr = refStr.append(deletedStr);
			}
			else
			{
				String deletedStr = " where "+parentTable+"."+deletedColumn+"='0'";
				refStr = refStr.append(deletedStr);
			}
		}
		reference.append(refStr);
		return reference;
		
	}
	
	private StringBuffer mapForeignTable(List<From> fromList,DBStructureUtil dbUtil,StringBuffer foreignMap)
	{
		boolean firstTable = true;
		String oldTableName = null;
		String parentTable = null;
		String parentUniqueId = null;
		Iterator<From> fromIterator = fromList.iterator();
		while(fromIterator.hasNext())
		{
			From from = fromIterator.next();
			if(!firstTable)
			{
				String nextTable = from.getTable();
				if(nextTable.equalsIgnoreCase(oldTableName))
				{
					continue;
				}
			}
			if(firstTable)
			{
				parentTable = from.getTable();
				
				//tableName = QueryConstants.CLASSPACKAGE_NAME+tableName;
				parentUniqueId = dbUtil.getUniqueId(parentTable);
			}
			else
			{
				String nextTable = from.getTable();	
				String referenceCol = dbUtil.getReferenceColumn(nextTable,parentTable);
				String uniqueId = dbUtil.getUniqueId(nextTable);
				if(uniqueId!=null && referenceCol !=null)
				{
					nextTable = nextTable.replace(QueryConstants.CLASSPACKAGE_NAME, "");
					String parentTableName = parentTable.replace(QueryConstants.CLASSPACKAGE_NAME, "");
					foreignMap.append(" and ");
					foreignMap.append(parentTableName).append(".").append(referenceCol).append("=").append(nextTable).append(".").append(uniqueId);
				}
			}
					
			firstTable = false;
			oldTableName = from.getTable();
			
		}
		return foreignMap;
	}
	
}

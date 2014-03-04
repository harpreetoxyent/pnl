package com.oxymedical.component.db.query;

import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.taskdefs.Sleep;

import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.constants.QueryConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.query.data.DBQuery;
import com.oxymedical.component.db.query.data.FieldType;
import com.oxymedical.component.db.query.data.Select;
import com.oxymedical.component.db.query.data.Where;
import com.oxymedical.component.db.query.data.WhereInner;
import com.oxymedical.component.db.utilities.DBStructureUtil;
import com.oxymedical.core.querydata.QueryData;

public class FieldConvertedQueryCreator extends DecoratedQueryCreator
{

	public FieldConvertedQueryCreator(IQueryCreator qc)
	{
		super(qc);
	}

	@Override
	public String createQuery(DBQuery dbQuery)
	{
		DBQuery dbq = convertCategoriesFieldToTableField(dbQuery);
		return super.qCreator.createQuery(dbq);
	}
	
	/**
	 * This method change the userdefine field to table classfield
	 * 
	 * @param dbQuery
	 * @return
	 */
	private DBQuery convertCategoriesFieldToTableField(DBQuery dbQuery)
	{
		DBStructureUtil dbUtil = new DBStructureUtil();
		IDBComponent dbObject = this.getDBComponent();
		dbUtil.setDbc(dbObject);
		String fieldQuery = QueryConstants.CLASSFIELD_QUERY;
		String mapClassName = dbUtil.getClassNameFromTable("qbuserdefinedfield");
		fieldQuery = fieldQuery.replaceFirst("#", mapClassName);
		List<Where> whereList = dbQuery.getConditions();
		List<Select>selectList = dbQuery.getFields();
		String[][] allValues =null;
		try {
			List<Object> classFieldList = null;
			if(this.getDBComponent() != null)
			{
				classFieldList = this.getDBComponent().executeHSQLQueryWithNameParameter(fieldQuery, null);
			}
			
			if(classFieldList!=null)
			{
				allValues = QueryData.iterateListData(classFieldList);
				
				Iterator<Where> whereItr = whereList.listIterator();
				while(whereItr.hasNext())
				{
					Where where = whereItr.next();
					List<WhereInner> whereInnerList = where.getWhereQueries();
					WhereInner whereInner = whereInnerList.get(0);
					String fieldName = whereInner.getFieldName();
					for(int counter =0; counter<allValues.length; counter++)
					{
						String userDefFieldName = allValues[counter][0];
						if(userDefFieldName.equalsIgnoreCase(fieldName))
						{
							String tableFieldName = allValues[counter][2];
							whereInner.setFieldName(tableFieldName);
							String tableName = allValues[counter][1];
							String aliasName = allValues[counter][1];
							tableName = QueryConstants.CLASSPACKAGE_NAME+tableName;							
							whereInner.setFieldNameTableAlias(aliasName);
							FieldType fieldType = dbUtil.getColumnType(tableName, tableFieldName);
							whereInner.setFieldType(fieldType);
							break;
						}
					}
				}
			}
			
		} catch (DBComponentException e) {
			e.printStackTrace();
		}
		//select query field mapping with class field name.Changes by Wasim,31-July-2009.
		this.mapSelectField(selectList,allValues,dbUtil);
		return dbQuery;
	}
	
	private void mapSelectField(List<Select>listValue,String[][] allValues,DBStructureUtil dbUtil)
	{
		if(allValues == null || listValue == null)
		{
			return;
		}
		//int counterVar = 0;
		Iterator<Select> selectItr = listValue.listIterator();
		while(selectItr.hasNext())
		{
			Select select = selectItr.next();
			String fieldName = select.getField();
			for(int counter =0; counter<allValues.length; counter++)
			{
				String userDefFieldName = allValues[counter][0];
				if(userDefFieldName.equalsIgnoreCase(fieldName))
				{
					String tableFieldName = allValues[counter][2];
					select.setField(tableFieldName);
					String aliasName = allValues[counter][1];
					select.setTableAlias(aliasName);					
				}
			}
			String queryField = select.getField();
			String tableName = select.getTableAlias();
			String classField = dbUtil.getClassField(tableName, queryField);
			if(classField !=null)
			{
				select.setField(classField);
			}
		}
	}
	
}

package com.oxymedical.component.db.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.constants.QueryConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.query.data.DBQuery;
import com.oxymedical.component.db.query.data.From;
import com.oxymedical.component.db.query.data.Where;
import com.oxymedical.component.db.query.data.WhereInner;
import com.oxymedical.component.db.utilities.DBStructureUtil;
import com.oxymedical.core.querydata.QueryData;

public class TableLinkedQueryCreator extends DecoratedQueryCreator
{

	public TableLinkedQueryCreator(IQueryCreator qc)
	{
		super(qc);
	}

	@Override
	public String createQuery(DBQuery dbQuery)
	{
		DBQuery dbq = linkTablesUsedInQuery(dbQuery);
		return super.qCreator.createQuery(dbq);
	}

	private DBQuery linkTablesUsedInQuery(DBQuery dbQuery)
	{
		DBStructureUtil dbUtil = new DBStructureUtil();
		IDBComponent dbObject = this.getDBComponent();
		dbUtil.setDbc(dbObject);
		String fieldQuery = QueryConstants.CLASSFIELD_QUERY;
		String mapClassName = dbUtil.getClassNameFromTable("qbuserdefinedfield");
		fieldQuery = fieldQuery.replaceFirst("#", mapClassName);
		List<Where> whereList = dbQuery.getConditions();
		List<From> fromList = dbQuery.getTables();
		From from = null;
		try {
			List<Object> classFieldList = null;
			if(this.getDBComponent() != null)
			{
				classFieldList = this.getDBComponent().executeHSQLQueryWithNameParameter(fieldQuery, null);
			}
			String[][] allValues =null;
			
			if(classFieldList!=null)
			{
				
				allValues = QueryData.iterateListData(classFieldList);
				if(whereList.size()>0)
				{
					if(fromList ==null)
					{
						fromList = new ArrayList<From>();
					}
					Iterator<Where> whereItr = whereList.listIterator();
					while(whereItr.hasNext())
					{
						from = new From();
						Where where = whereItr.next();
						List<WhereInner> whereInnerList = where.getWhereQueries();
						WhereInner whereInner = whereInnerList.get(0);
						String fieldName = whereInner.getFieldName();
						String alias = whereInner.getFieldNameTableAlias();
						for(int counter =0; counter<allValues.length; counter++)
						{
							String userDefFieldName = allValues[counter][2];
							if(userDefFieldName.equalsIgnoreCase(fieldName))
							{
								String tableName = allValues[counter][1];
								if(!alias.equalsIgnoreCase(tableName))continue;
								tableName = QueryConstants.CLASSPACKAGE_NAME+tableName;	
								from.setTable(tableName);
								String aliasName = allValues[counter][1];
								from.setTableAlias(aliasName);
								fromList.add(from);
								break;
							}
						}
					}
				}
				else
				{
					/*from = new From();
					from.setTable("com.oxymedical.component.render.Studiesprime");
					from.setTableAlias("Studiesprime");
					fromList.add(from);*/
					/*List<From> tableList = dbQuery.getTables();
					if(tableList!=null)
					{
						if(tableList.size()>=1)
						{
							From fromObj = tableList.get(0);
							if(fromObj!=null)
							{
								
							}
						}
					}*/
					
					
				}
			}
			if(fromList!=null)
			dbQuery.setTables(fromList);
			
		} catch (DBComponentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return dbQuery;
	}
}

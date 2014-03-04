/**
 * 
 */
package com.oxymedical.component.db.query;

import java.util.Iterator;
import java.util.List;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.constants.QueryConstants;
import com.oxymedical.component.db.query.data.From;
import com.oxymedical.component.db.query.data.Select;
import com.oxymedical.component.db.utilities.DBStructureUtil;

/**
 * @author wkh
 *
 */
public class QueryHelper {
	
	

	/**
	 * selectQuery method create select class of hsql query
	 * @param selectList
	 * @return
	 */
	public StringBuffer selectQuery(List<Select> selectList)
	{
		if(selectList == null)
		{
			return null;
		}
		StringBuffer selectQuery = new StringBuffer();
		Iterator<Select> itr = selectList.iterator();
		int counter=0;
		while(itr.hasNext())
		{
			Select select = itr.next();
			selectQuery = (counter == 0) ? selectQuery.append(com.oxymedical.component.db.constants.QueryConstants.SELECT) : selectQuery.append(QueryConstants.SELECT_QUERY_FIELD_SEP);
			selectQuery = (select.getTableAlias() != null) ? selectQuery.append(select.getTableAlias()).append(QueryConstants.SELECT_QUERY_TABLE_FIELD_SEP) : selectQuery;
			selectQuery = selectQuery.append(select.getField());
			//selectQuery = (select.getFieldAlias() != null) ? selectQuery.append(QueryConstants.SELECT_QUERY_FIELD_ALIAS_SEP).append(select.getFieldAlias()) : selectQuery;
			counter++;
			
		}
		
		
		return selectQuery;
	}
	
	/**
	 * fromQuery method create from clause of query.
	 * @param fromList
	 * @param str
	 * @return
	 */
	public StringBuffer fromQuery(List<From> fromList,StringBuffer str)
	{
		StringBuffer fromQuery = new StringBuffer(str);
		boolean firstTable = true;
		String oldTableName = null;
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
				if(fromQuery.toString().contains(nextTable))
				{
					continue;
				}
			}
			
			fromQuery = (firstTable) ? fromQuery.append(QueryConstants.FROM) : fromQuery.append(QueryConstants.FROM_QUERY_TABLE_SEP);
			fromQuery = fromQuery.append(from.getTable());
			fromQuery = fromQuery.append(QueryConstants.BLANK_SPACE);
			fromQuery = fromQuery.append(from.getTableAlias());
			firstTable = false;
			oldTableName = from.getTable();
			
		}
		
		return fromQuery;
		
	}

	

}

/**
 * 
 */
package com.oxymedical.component.db.query;

import java.util.List;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.query.data.DBQuery;

/**
 * @author vka
 *
 */
public class DBDecorator {
	
	public DBDecorator()
	{
		
	}

	/**
	 * This method build hsql query from dbquery and get result in list.
	 * @param dbQuery
	 * @param dbObject
	 * @return ListObject 
	 */
	public List getQueryResult(DBQuery dbQuery,DBComponent dbObject)
	{
		
		IQueryCreator qCreator = new FieldConvertedQueryCreator(new TableLinkedQueryCreator(new QueryCreator(dbObject)));
		String query = qCreator.createQuery(dbQuery);
		List valueList = null;
		System.out.println("---qury String---"+query);
		try {
			valueList = dbObject.executeHSQLQueryWithNameParameter(query, null);
		} catch (DBComponentException e) {
		}
		return valueList;
		
	}
}

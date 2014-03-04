package com.oxymedical.component.db.query;

import java.util.List;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.query.data.DBQuery;
import com.oxymedical.component.db.query.data.From;
import com.oxymedical.component.db.query.data.Select;
import com.oxymedical.component.db.query.data.Where;
import com.oxymedical.component.db.query.data.WhereQuery;
import com.oxymedical.component.db.utilities.DBStructureUtil;

public class QueryCreator implements IQueryCreator
{
	DBQuery dbq; 
	DBComponent dbc;
	
	public QueryCreator(DBComponent dbc)
	{
		this.dbc = dbc;
	}
	
	@Override
	public String createQuery(DBQuery dbQuery)
	{
		return createQueryFromInput(dbQuery);
	}

	@Override
	public DBComponent getDBComponent()
	{
		return this.dbc;
	}
	
	private String createQueryFromInput(DBQuery dbQuery)
	{
		StringBuffer queryString =null;
		List<Select> selectList = dbQuery.getFields();
		QueryHelper qHelper = new QueryHelper();
		//create select statement
		queryString = qHelper.selectQuery(selectList);
		List<From> fromList = dbQuery.getTables();
		//create from statement
		queryString = qHelper.fromQuery(fromList,queryString);
		List<Where> whereList = dbQuery.getConditions();
		// create where condition statement
		WhereQuery whereQuery = new WhereQuery();
		queryString = whereQuery.buildWhereQuery(whereList,queryString);
		queryString = whereQuery.createReferenceCondition(fromList, queryString,this.dbc);
		return queryString.toString();
		
		
		
	}
}

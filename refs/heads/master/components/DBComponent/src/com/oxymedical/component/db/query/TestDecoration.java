package com.oxymedical.component.db.query;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.query.data.DBQuery;

public class TestDecoration
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		DBQuery dbq = new DBQuery();
		DBComponent dbc = new DBComponent();
		dbc.setFirstResult(1001);
		IQueryCreator qc = new FieldConvertedQueryCreator(new TableLinkedQueryCreator(new QueryCreator(dbc)));
		String query = qc.createQuery(dbq);
	}

}

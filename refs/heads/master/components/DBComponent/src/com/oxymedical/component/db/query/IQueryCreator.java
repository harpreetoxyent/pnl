package com.oxymedical.component.db.query;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.query.data.DBQuery;

public interface IQueryCreator
{
	public String createQuery(DBQuery dbQuery);
	public DBComponent getDBComponent();
}

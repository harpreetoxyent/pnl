package com.oxymedical.component.db.query;

import com.oxymedical.component.db.DBComponent;

public abstract class DecoratedQueryCreator implements IQueryCreator
{
	protected IQueryCreator qCreator;
	protected DBComponent dbc;
	
	public DecoratedQueryCreator(IQueryCreator qc)
	{
		this.qCreator = qc;
	}
	
	@Override
	public DBComponent getDBComponent()
	{
		return qCreator.getDBComponent();
	}

}

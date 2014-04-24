package com.oxymedical.component.workflowComponent.db.impl;

import java.util.List;

import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.workflowComponent.constants.WorkflowSqlCommands;

public class BaseDBImpl
{
	public static String updateSqlAndCreateNameParameter(List<NameQueryParameter> listParam,
			String parameterValue, String parameterName, String sqlQuery, String sqlAddlCondition,
			boolean appendAndCondition)
	{
		if (parameterValue != null)
		{
			if (sqlAddlCondition != null) 
				sqlQuery = sqlQuery + (appendAndCondition ? WorkflowSqlCommands.CONDITION_JOINER_AND : "") + sqlAddlCondition;
			
			NameQueryParameter param = new NameQueryParameter(parameterName, parameterValue);
			listParam.add(param);
		}
		return sqlQuery;
	}
	
	
	public static String updateSqlAndCreateNameParameter(List<NameQueryParameter> listParam,
			Integer parameterValue, String parameterName, String sqlQuery, String sqlAddlCondition,
			boolean appendAndCondition)
	{
		if (parameterValue != null)
		{
			if (sqlAddlCondition != null) 
				sqlQuery = sqlQuery + (appendAndCondition ? WorkflowSqlCommands.CONDITION_JOINER_AND : "") + sqlAddlCondition;
			
			NameQueryParameter param = new NameQueryParameter(parameterName, parameterValue);
			listParam.add(param);
		}
		return sqlQuery;
	}
}

package com.oxymedical.component.renderer.command;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.querydata.QueriedFields;
import com.oxymedical.core.querydata.QueryCondition;
import com.oxymedical.core.querydata.QueryData;
import com.oxymedical.core.querydata.QueryObject;
import com.oxymedical.core.querydata.ValueRange;
import com.oxymedical.core.renderdata.IDataUnit;
import com.oxymedical.core.stringutil.StringUtil;

public class QueryBuilderCommand extends BaseCommand implements
IUiLibraryCompositeCommand {



	@Override
	public void execute() {
		IDataUnit dataUnit = createDataUnit(this.getClassname(), this.getComponentId(),
				this.getDataPatternId(), this.getFormPatternId(), this.getFormValues(),
				this.getMethodName(), this.getSession());
		dataUnit=updateDataUnit(dataUnit, this.getParamList());
		this.setData(this.getRouter().routeToModeler(dataUnit));

	}

	@Override
	public IHICData getHICData() {
		return this.getData();
	}

	private IDataUnit updateDataUnit(IDataUnit dataUnit, String paramList)
	{
		QueryObject queryObject = new QueryObject();
		List<QueryCondition> conditionList = null;
		List<QueriedFields> fieldsList = null;
		ValueRange valueRange = null;
		QueryCondition qCondition = null;
			
		Hashtable uiValue = this.getFormValues();
		String countStr = (String) uiValue.get("counter");
		conditionList = new ArrayList<QueryCondition>();
		fieldsList = new ArrayList<QueriedFields>();
		if(countStr !=null)
		{
			int maxValue = Integer.parseInt(countStr);
			for(int counter=1;counter<=maxValue;counter++)
			{
				qCondition = null;
				valueRange = null;
				boolean isVariable = true;
				String fieldName = (String) uiValue.get("field"+counter);
				String fieldValue = (String) uiValue.get("variable"+counter);
				String fRange = (String) uiValue.get("firstrange"+counter);
				String sRange = (String) uiValue.get("secondrange"+counter);
				String logicalOperator = (String) uiValue.get("logical"+counter);
				if(fieldName !=null && (fRange!=null || sRange!=null || fieldValue!=null))
				{
					qCondition = new QueryCondition();
					qCondition.setFieldName(fieldName);
				}
				if(fieldValue !=null && fieldValue!="" && qCondition !=null)
				{
					qCondition.setFieldValue(fieldValue);
					isVariable = false;
				}
				if(isVariable)
				{
					if(fRange !=null && fRange!=""&& qCondition !=null)
					{
						valueRange = new ValueRange();
						valueRange.setStart(fRange);
						//qCondition.setRange(valueRange);
					}
					
					if(sRange!=null && sRange !="" && qCondition !=null)
					{
						if(valueRange==null)
						{
							valueRange = new ValueRange();
						}
						valueRange.setEnd(sRange);
					}
				}
				
				
				
				if(counter ==1 && qCondition !=null)
				{
					qCondition.setFirstConditon(true);
				}
				else if(qCondition !=null)
				{
					qCondition.setFirstConditon(false);
				}
				if(logicalOperator !=null && qCondition !=null)
				{
					if(logicalOperator.equalsIgnoreCase("and"))
					{
						qCondition.setJoiner(com.oxymedical.core.querydata.ConditionJoiner.AND);
					}
					else if(logicalOperator.equalsIgnoreCase("or"))
					{
						qCondition.setJoiner(com.oxymedical.core.querydata.ConditionJoiner.OR);
					}
						
				}
				
				if(qCondition !=null)
				{
					qCondition.setRange(valueRange);
					conditionList.add(qCondition);
				}
			}
		}
		else
		{
			//String categoryName = (String) uiValue.get("category1");
			boolean isVariable = true;
			String fieldName = (String) uiValue.get("field1");
			String fieldValue = (String) uiValue.get("variable1");
			String fRange = (String) uiValue.get("firstrange1");
			String sRange = (String) uiValue.get("secondrange1");
			if(fieldName !=null && (fRange!=null || sRange!=null || fieldValue!=null))
			{
				qCondition = new QueryCondition();
				qCondition.setFieldName(fieldName);
				
			}
			if(fieldValue !=null && fieldValue !="" && qCondition !=null)
			{
				qCondition.setFieldValue(fieldValue);
				qCondition.setFirstConditon(true);
				isVariable = false;
			}
			
			if(isVariable)
			{
				if(fRange !=null && fRange!=""&& qCondition !=null)
				{
					valueRange = new ValueRange();
					valueRange.setStart(fRange);
					//qCondition.setRange(valueRange);
				}
				
				if(sRange!=null && sRange !="" && qCondition !=null)
				{
					if(valueRange==null)
					{
						valueRange = new ValueRange();
					}
					valueRange.setEnd(sRange);
				}
			}
			String logicalOperator = (String) uiValue.get("logical1");
			if(logicalOperator !=null && qCondition !=null)
			{
				if(logicalOperator.equalsIgnoreCase("and"))
				{
					qCondition.setJoiner(com.oxymedical.core.querydata.ConditionJoiner.AND);
				}
				else if(logicalOperator.equalsIgnoreCase("or"))
				{
					qCondition.setJoiner(com.oxymedical.core.querydata.ConditionJoiner.OR);
				}
			}
			if(qCondition !=null)
			{
				qCondition.setRange(valueRange);
				conditionList.add(qCondition);
			}
		}
		QueriedFields qField = null;
		String[] fieldsArray = (String[]) uiValue.get("SelectField");
		String dbInfo = (String) uiValue.get("TableName");
		String[] dbArray = StringUtil.splitString(dbInfo,".");
		String tableName=null;
		String dbName = null;
		if(dbArray.length >=2)
		{
			dbName = dbArray[0];
			tableName = dbArray[1];
		}
		else
		{
			System.out.println("Database and table information is not valid input format. valid format is- 'dbName.tablename'");
		}
		
		for(int loopCounter=0;loopCounter<fieldsArray.length; loopCounter++)
		{
			String fieldName = fieldsArray[loopCounter];
			if(fieldName!=null && fieldName!="")
			{
				qField = new QueriedFields();
				qField.setField(fieldName);
				qField.setTableName(tableName);
				fieldsList.add(qField);
			}
		}
		queryObject.setConditions(conditionList);
		queryObject.setFields(fieldsList);
		queryObject.setDbName(dbName);
		QueryData queryData = new QueryData();
		queryData.setQueryObject(queryObject);
		dataUnit.setQueryData(queryData);
		return dataUnit;
	}

}

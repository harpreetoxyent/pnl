package com.oxymedical.component.db.ua;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.DatabaseOperation;
import com.oxymedical.component.db.application.query.Field;
import com.oxymedical.component.db.application.query.ParserQueryParameters;
import com.oxymedical.component.db.application.query.QueryParameter;
import com.oxymedical.component.db.application.query.parser.HICQueryParser;
import com.oxymedical.component.db.application.query.parser.ParserConstants;

import com.oxymedical.component.db.dao.ConcreteDAO;
import com.oxymedical.component.db.exception.DBComponentException;


public class UserAdminQueryHelper
{
	
	private Properties propertyObject;

	public UserAdminQueryHelper()
	{
		
	}
	
	public UserAdminQueryHelper(Properties propertyObject)
	{
		this.propertyObject=propertyObject;
		
	}
	
	public Properties getPropertyObject() {
		return propertyObject;
	}

	public void setPropertyObject(Properties propertyObject) {
		this.propertyObject = propertyObject;
	}

	public List getUserAdminQueryResult(String sql,DBComponent dbObject) throws DBComponentException
	{
		ParserQueryParameters parserQueryParameter = null;
		QueryParameter queryParameter = null;
		DatabaseOperation databaseOperation=dbObject.getDatabaseOperation();
		List retList = null;
		List tableList = null;
		List<Field> fieldList = null;
		LinkedHashMap<String, String> fieldLabelHash = null;
		HICQueryParser hicQueryParser = new HICQueryParser();
		parserQueryParameter = hicQueryParser.parseHICQuery(sql);
		if (null == parserQueryParameter)
			return retList;
		if (parserQueryParameter.getQueryType().equalsIgnoreCase(
				ParserConstants.hibSelect)) {
				fieldLabelHash = new LinkedHashMap<String, String>();
				tableList = parserQueryParameter.getTables();
				fieldList = parserQueryParameter.getFieldList();
					for (int fieldListCounter = 0; fieldListCounter < fieldList
							.size(); fieldListCounter++) {
						String retField = fieldList.get(fieldListCounter)
								.getName();
						fieldLabelHash.put(retField, retField);
					}
					queryParameter = new QueryParameter(fieldLabelHash,
							parserQueryParameter.getConditions(), tableList, "",parserQueryParameter.getJoins(),parserQueryParameter.getOrderBy());
					Hashtable<String, ConcreteDAO> tableNameClassNameMap=databaseOperation.getTableNameClassNameMap();
					UAQueryCreator queryCreator=new UAQueryCreator();
					String hsqlQuery=queryCreator.queryString(tableNameClassNameMap, queryParameter, "",propertyObject);
					retList=dbObject.getList(hsqlQuery);
				return retList;
		
		}
		return retList;
	}


}

package com.oxymedical.component.db.application.query.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;

import com.oxymedical.component.db.application.query.Condition;
import com.oxymedical.component.db.application.query.Field;
import com.oxymedical.component.db.application.query.Join;
import com.oxymedical.component.db.application.query.OrderBy;
import com.oxymedical.component.db.application.query.ParserQueryParameters;
import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;
import com.oxymedical.core.stringutil.StringUtil;
import com.oxymedical.core.stringutil.Tokenizer;

/**
 * This class is used to parse the HICQuery
 * and performs the related operations like delete,get,insert and for condition.
 * @author     Oxyent Medical
 * @version     1.0.0
*/
public class HICQueryParser 
{
	/** this class variable is use to call the methods and set the values in it.*/
	Condition condition = null;
	/** this shows the number of conditions in the conditionHash hashtable*/ 
	int conditionCounter = 0;
	
	/** This variable is used to store query parameter values */
	private List<String> queryParamValues = null;
	
	public void setQueryParamValues(List<String> queryParamValues){
		this.queryParamValues = queryParamValues;
	}
	
	private String getTableColumn(String colStr, String queryType) throws DBComponentException
	{
		String tableName = null;
		String fieldName = null;
		
		if(null != colStr)
		{
			if(colStr.indexOf(DBConstants.DB_DOT)>0)
			{
				String REGEX = DBConstants.DB_DOT;
				Pattern p = Pattern.compile(REGEX,  Pattern.LITERAL);
				String[] idenf = p.split(colStr);
				tableName = idenf[0];
				fieldName = idenf[1];
				if(queryType.equalsIgnoreCase(DBConstants.DB_CONDITION_CLAUSE))
				{	
					tableName =  tableName + DBConstants.DB_HASH + fieldName;
				}
			
			}
			else
			{ 
				//DBComponent.logger.log(0,"field-" + colStr);
				tableName = "";
			}
		}
		return tableName;
			
	}
	
	private List<Field> getQueryCols(String colsStr) throws DBComponentException
	{
		List<Field> fieldList = new ArrayList<Field>();
		if(colsStr != DBConstants.DB_BLANK)
		{
			if(colsStr.indexOf(DBConstants.DB_COMMA)>0)
			{
				String colsArr[] = colsStr.split(DBConstants.DB_COMMA);
				for(int count=0;count<colsArr.length;count++)
				{
					Field field = new Field();
					field.setName(colsArr[count]);
					fieldList.add(field);
					//DBComponent.logger.log(0,"Query Col Name:" + colsArr[count]);
				}
			}
			else
			{
				Field field = new Field();
				field.setName(colsStr);
				//DBComponent.logger.log(0,"Query Col Name:" + colsStr);
				fieldList.add(field);
			}
		}
		return fieldList;
		
	}

	private List getTables(String tablesStr) throws DBComponentException
	{
		List<String> tables = new ArrayList<String>();
		String tablesArr[] = tablesStr.split(DBConstants.DB_COMMA);
		for(int count=0;count<tablesArr.length;count++)
		{
			//DBComponent.logger.log(0,"Table:" + tablesArr[count].trim());
			tables.add(tablesArr[count].trim());
		}
		return tables;
		
	}
	
	private static boolean isNumeric(String s) 
	{
        return s.matches ("[0-9]+");
    }

	private static boolean isBoolean(String s) 
	{
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }
	
	private ParserQueryParameters parseGetQuery(String sql) throws DBComponentException
	{
		String queryCols, tablesAndConditions;
		ParserQueryParameters parserQueryParameters = new ParserQueryParameters();
		
		sql = sql.substring(3);
		String queryArr[] = sql.split(DBConstants.DB_FROM_CLAUSE);
		if(queryArr.length>1)
		{
			queryCols = queryArr[0];
			//parserQueryParameters.setFields(getQueryCols(queryCols));
			parserQueryParameters.setFieldList(getQueryCols(queryCols));
			//this condition works in case when the sql query has joins
			//based on that the query is build.
			if(queryArr[1].indexOf(DBConstants.DB_JOINS_CLAUSE) > 0)
			{
				tablesAndConditions = queryArr[1];
				String joinArr[] = tablesAndConditions.split(DBConstants.DB_JOINS_CLAUSE);
				if(joinArr.length > 1)
				{
				  parserQueryParameters.setTables(getTables(joinArr[0]));
				  String joinStr = joinArr[1];
				  if(joinStr.indexOf(DBConstants.DB_CONDITIONS_CLAUSE)>0)
				  {
				  String[] conditionArr=joinStr.split(DBConstants.DB_CONDITIONS_CLAUSE);
				  String joinConStr=conditionArr[0];
				  String conditionStr = conditionArr[1];
				  conditionStr = conditionStr.trim();
				  if(conditionStr.indexOf(DBConstants.ORDERBY_CLAUSE)>0)
					{
						 String[] orderArr=conditionStr.split(DBConstants.ORDERBY_CLAUSE);
						 String order=orderArr[1];
						 String conditionValue=orderArr[0];	
						 conditionValue = conditionValue.trim();
						 conditionValue = this.createEncodeString(conditionValue);
						 parserQueryParameters.setOrderBy(parseOrderBy(order));
						 parserQueryParameters.setConditions(parseConditions(conditionValue));
						 parserQueryParameters.setJoins(parseJoins(joinConStr));
					}
				  else
				  {
				  parserQueryParameters.setJoins(parseJoins(joinConStr));
				  conditionStr = this.createEncodeString(conditionStr);
				  parserQueryParameters.setConditions(parseConditions(conditionStr));
				  }
				  }
				  else
				  { 
					  if(joinStr.indexOf(DBConstants.ORDERBY_CLAUSE)>0)
						{
						      String[] orderArr=joinStr.split(DBConstants.ORDERBY_CLAUSE);
							  String order=orderArr[1];
							 String joinValue=orderArr[0];	
							 parserQueryParameters.setOrderBy(parseOrderBy(order));
							 parserQueryParameters.setJoins(parseJoins(joinValue));
						}
					  else
					  {
					 parserQueryParameters.setJoins(parseJoins(joinStr));
					  }
				  }
				}
				
			}
			else if(queryArr[1].indexOf(DBConstants.DB_CONDITIONS_CLAUSE) > 0)
			{
				//if query entered by user have condition or criteria
				tablesAndConditions = queryArr[1];
			//DBComponent.logger.log(0,"tablesAndConditions--------"+tablesAndConditions);				
				String conditionArr[] = tablesAndConditions.split(DBConstants.DB_CONDITIONS_CLAUSE);
				if(conditionArr.length > 1)
				{
					// will get list of tables defined in query given by user 
					//and will be set as table list for DB
					parserQueryParameters.setTables(getTables(conditionArr[0]));
					  if(conditionArr[1].indexOf(DBConstants.ORDERBY_CLAUSE)>0)
						{
						     String[] orderArr=conditionArr[1].split(DBConstants.ORDERBY_CLAUSE);
							 String order=orderArr[1];
							 String conditionValue=orderArr[0];	
							 conditionValue = conditionValue.trim();
							 conditionValue = this.createEncodeString(conditionValue);
							 parserQueryParameters.setOrderBy(parseOrderBy(order));
							 parserQueryParameters.setConditions(parseConditions(conditionValue));
						}
					  else
					  {
				    String conditionStr = conditionArr[1];
				    conditionStr = conditionStr.trim();
				    conditionStr = this.createEncodeString(conditionStr);
				    parserQueryParameters.setConditions(parseConditions(conditionStr));
					  }
				 }
			}
			else if(queryArr[1].indexOf(DBConstants.ORDERBY_CLAUSE) > 0)
			{
				tablesAndConditions = queryArr[1];
				String orderArr[] = tablesAndConditions.split(DBConstants.ORDERBY_CLAUSE);
				if(orderArr.length>1)
				{
					parserQueryParameters.setTables(getTables(orderArr[0]));
					String order=orderArr[1];
					parserQueryParameters.setOrderBy(parseOrderBy(order));
				}
			}
			//if query entered by user does'not have condition or criteria
			else
			{
				parserQueryParameters.setTables(getTables(queryArr[1]));
			}
			
		}
		else
		{
			throw (new DBComponentException(DBExceptionConstants.EXCEPTION + sql + " in parseGetQuery"));
		}
		return parserQueryParameters;
		
	}


	/**
	 * Sql should be of the following format
	 * 
	 * <code>add into <<database_name>>.<<table_name>> values <<column_name_1>>:=[<<some_string_or_date_value>>], <<column_name_2>>:=<<some_int_value>></code>
	 * 
	 * For e.g.
	 * <code>add into sampledb.employee values empName:=[Mike Chambers], empAge:=23</code>
	 * 
	 * 
	 * @param sql
	 * @return
	 * @throws DBComponentException
	 */
	private ParserQueryParameters parseInsertQuery(String sql)throws DBComponentException
	{					
		String tableAndColValues = null;
		ParserQueryParameters parserQueryParameters = new ParserQueryParameters();
		List<String> tablesList = new ArrayList<String>();
		
		String queryArr[] = sql.split(DBConstants.DB_INTO_CLAUSE);
		
					
		if(queryArr.length == 2)
		{					
			tableAndColValues = queryArr[1].trim();
			String ColValuesArr[] = tableAndColValues.split(DBConstants.DB_VALUES_CLAUSE);
					
			if(ColValuesArr.length == 2)
			{		
				//	table name to insert values
				String tableName = ColValuesArr[0].trim();
				tablesList.add(tableName);
				parserQueryParameters.setTables(tablesList);
				// get cols
				String cols = ColValuesArr[1].trim();
				List<Field> fieldList = parseColValuesToInsert(cols);
				parserQueryParameters.setFieldList(fieldList);
				
			}	
			else
			{	
				throw new DBComponentException(DBExceptionConstants.EXCEPTION + sql + " in parseInsertQuery");
				
			}	
		}				
		else
		{		
			throw new DBComponentException(DBExceptionConstants.EXCEPTION + sql + " in parseInsertQuery");
			
		} 
		return parserQueryParameters;
		
	}				

	private ParserQueryParameters parseDeleteQuery(String sql) throws DBComponentException
	{
		String conditionStr = null;
		ParserQueryParameters parserQueryParameters = new ParserQueryParameters();
		List<String> tablesList = new ArrayList<String>();
		String queryArr[] = sql.split(DBConstants.DB_FROM_CLAUSE);
				
		if(queryArr.length>1)
		{	
			if(queryArr[1].indexOf(DBConstants.DB_CONDITIONS_CLAUSE)>0)
			{
				String tablesAndConditions = queryArr[1];
				String[] conditionArr = tablesAndConditions.split(DBConstants.DB_CONDITIONS_CLAUSE);
				tablesList.add(conditionArr[0]);
				parserQueryParameters.setTables(tablesList);
				conditionStr = conditionArr[1];
				// to get conditions  in user defined-query
				parserQueryParameters.setConditions(parseConditions(conditionStr));
			}
			
			// called for getting table in user defined-query
			else
			{
				tablesList.add(queryArr[1]);
				parserQueryParameters.setTables(tablesList);
				
			}
		}	
		else
		{	
			throw (new DBComponentException (DBExceptionConstants.EXCEPTION + sql + " in parseDeleteQuery"));
		}	
		return parserQueryParameters;
	}
	private ParserQueryParameters parseUpdateQuery(String sql) throws DBComponentException
	{
		String conditionStr = null;
		ParserQueryParameters parserQueryParameters = new ParserQueryParameters();
		List<String> tablesList = new ArrayList<String>();
		String queryArr[] = sql.split(DBConstants.DB_SET);
				
		if(queryArr.length>1)
		{	
			if(queryArr[1].indexOf(DBConstants.DB_CONDITIONS_CLAUSE)>0)
			{
				String queryCon[]=queryArr[1].split(DBConstants.DB_CONDITIONS_CLAUSE);				
				String queryTables[]=queryArr[0].split(ParserConstants.updateQuery);
				tablesList.add(queryTables[1].trim());
				parserQueryParameters.setTables(tablesList);
				String cols = queryCon[0].trim();
				List<Field> fieldList = parseColValuesToInsert(cols);
				parserQueryParameters.setFieldList(fieldList);
				String conditions = queryCon[1];
				parserQueryParameters.setConditions(parseConditions(conditions));
			}
			else
			{
				throw (new DBComponentException (DBExceptionConstants.EXCEPTION + sql + " in parseUpdateQuery"));
			}
			// called for getting table in user defined-query
		
		}	
		else
		{	
			throw (new DBComponentException (DBExceptionConstants.EXCEPTION + sql + " in parseUpdateQuery"));
		}	
		return parserQueryParameters;
		
	}
	/**
     * This method takes simple query as input and 
     * parse the query and call the corresponding method to complete the
     * query operation.
     * 
     * @param sql  Query in simple english.
     * @returns ParserQueryParameters
     * @throws DBComponentException
     */
	public ParserQueryParameters parseHICQuery(String sql) throws DBComponentException
	{
		ParserQueryParameters parserQueryParameters = null;
		// Replace HIC query operators with sql operators 
		
		//DBComponent.logger.log(0,"HICquery parser sql = " + sql);
		sql = sql.replaceAll(DBConstants.DB_COLON_GREATER , DBConstants.DB_GREATER);
		sql = sql.replaceAll(DBConstants.DB_COLON_LESS , DBConstants.DB_LESS);
		sql = sql.replaceAll(DBConstants.DB_COLON_EQUAL , DBConstants.DB_EQUAL);
		sql = sql.replaceAll(DBConstants.DB_LIKE , DBConstants.DB_LIKE_SYMB);
		
		
		if(null != sql )
		{
			sql = sql.trim();
			if(sql.indexOf(ParserConstants.selectQuery + DBConstants.DB_SPACE)==0 && sql.indexOf(DBConstants.DB_FROM_CLAUSE)>0)
			{
				parserQueryParameters = parseGetQuery(sql);
				parserQueryParameters.setQueryType(ParserConstants.hibSelect);
			}
			else if(sql.indexOf(ParserConstants.deleteQuery + DBConstants.DB_SPACE)==0 && sql.indexOf(DBConstants.DB_FROM_CLAUSE)>0)
			{
				parserQueryParameters = parseDeleteQuery(sql);
				parserQueryParameters.setQueryType(ParserConstants.deleteQuery);
			}
			else if(sql.indexOf(ParserConstants.insertQuery + DBConstants.DB_SPACE)==0 && sql.indexOf(DBConstants.DB_INTO_CLAUSE)>0)
			{
				parserQueryParameters = parseInsertQuery(sql);
				parserQueryParameters.setQueryType(ParserConstants.hibInsert);
				
			}
			else if(sql.indexOf(ParserConstants.updateQuery + DBConstants.DB_SPACE)==0)
			{
				parserQueryParameters = parseUpdateQuery(sql);
				parserQueryParameters.setQueryType(ParserConstants.updateQuery);
				
			}
			
		}
		else
		{
			throw new DBComponentException(DBExceptionConstants.NULL_POINTER_EXCEPTION + DBConstants.DB_SQL_QUERY + sql + " in parseHICQuery");
		}
		
		return parserQueryParameters;
			
	}

	private List<Field> parseColValuesToInsert(String colValues) throws DBComponentException
	{
		//DBComponent.logger.log(0,);
		String colValuesArr[] = colValues.split(DBConstants.DB_COMMA);
		String colName, colValue;
		List<Field> fieldList = new ArrayList<Field>();
		for(int count=DBConstants.DB_ZERO;count<colValuesArr.length;count++)
		{
			Field field = new Field();
			colValue = colValuesArr[count];
			String[] colNameValue = colValue.split(DBConstants.DB_EQUAL);
			if(colNameValue.length == DBConstants.DB_ONE)
			{
				colName = colNameValue[DBConstants.DB_ZERO].trim();
				field.setName(colName);
				colValue = DBConstants.DB_BLANK;
				field.setValue(colValue);
			}
			else
			if(colNameValue.length == DBConstants.DB_TWO)
			{
				if(DBConstants.DB_UNDEFINED.equals(colNameValue[DBConstants.DB_ONE].toLowerCase().trim()) || DBConstants.DB_UNDEFINE.equals(colNameValue[DBConstants.DB_ONE].toLowerCase().trim()))
				{
					colName = colNameValue[DBConstants.DB_ZERO].trim();
					field.setName(colName);
					colValue = DBConstants.DB_BLANK;
					field.setValue(colValue);
				}
				else
				{
					colName = colNameValue[DBConstants.DB_ZERO].trim();
					field.setName(colName);
					
					//cehcking if the query parameter values object exists and that it is of the right size
					if(queryParamValues != null && queryParamValues.size() > count){
						colValue = queryParamValues.get(count);
					}else{
						colValue = colNameValue[DBConstants.DB_ONE].trim();
						if(colValue.startsWith(DBConstants.DB_LEFT_SQUARE_BRAC))
							colValue = colValue.substring(DBConstants.DB_ONE, colValue.length()- DBConstants.DB_ONE);
					}
					field.setValue(colValue);
				}
			}	
			else
			{	
				throw new DBComponentException(DBExceptionConstants.EXCEPTION + colValues + " in parseColValuesToInsert");
			}	
			fieldList.add(field);
		}
		return fieldList;
	}
	/**
     * This method takes condition query as input of user defined-query
     * and create hashtable that maps the condition counter to the condition.
     * @param sql  condition query.
     * @returns Hashtable<Integer,Condition>
     * @throws DBComponentException
     */
	public Hashtable<Integer,Condition> parseConditions(String sql) throws DBComponentException
	{
		Hashtable<Integer,Condition> conditionHash = new Hashtable<Integer,Condition>();
		String REGEX = DBConstants.DB_REGEX;
		String conditionOperatorStr = "";
		String idenf = "";
		String field = "";
	
		
		sql = sql.replaceAll(DBConstants.DB_COLON_GREATER , DBConstants.DB_GREATER);
		sql = sql.replaceAll(DBConstants.DB_COLON_LESS , DBConstants.DB_LESS);
		sql = sql.replaceAll(DBConstants.DB_COLON_EQUAL , DBConstants.DB_EQUAL);
		sql = sql.replaceAll(DBConstants.DB_LIKE , DBConstants.DB_LIKE_SYMB);
		
		Tokenizer tokenizer = new Tokenizer(sql.trim(), REGEX, true);
		
		boolean lhs = true;
	
		for (; tokenizer.hasNext(); ) 
	    {			
			if(!(tokenizer.isNextToken()))
	    	{
	    		field = (String)tokenizer.next();
	    		field = field.trim();
	    		//DBComponent.logger.log(0,"field----"+field);
	    		if(lhs == true && field.length()>0  && (!(field.equals(DBConstants.DB_AND_CLAUSE) || field.equals(DBConstants.DB_OR_CLAUSE))))
	    		{
	    			condition = new Condition();
	    			conditionCounter++;
	    			//DBComponent.logger.log(0,"---------New Condition --------- \n lhs=" + field);
	    			condition.setField(field);
	    			condition.setTable(getTableColumn(field,DBConstants.DB_BLANK));
	    			lhs = false;
	    		}
	    		else if(!lhs && field.length()>0  && (!(field.equals(DBConstants.DB_AND_CLAUSE) || field.equals(DBConstants.DB_OR_CLAUSE))))
	    		{
	    			//DBComponent.logger.log(0,"operator: " + conditionOperatorStr);
	    			condition.setOperator(conditionOperatorStr);
	    			conditionOperatorStr = DBConstants.DB_BLANK;
	    		
	    			//DBComponent.logger.log(0,"rhs=" + field);
	    			if(isNumeric(field) || isBoolean(field))
	    			{	
	    				//DBComponent.logger.log(0,"rhs numeric constant: " + field);
	    				condition.setValue(field);
	    				condition.setConsTable(DBConstants.DB_BLANK);
	    				condition.setConsField(DBConstants.DB_BLANK);
	    				conditionHash.put(conditionCounter, condition);
	    			}
	    			else if(field.startsWith(DBConstants.DB_LEFT_SQUARE_BRAC))
	    			{
	    				field = field.substring(1,field.length()-1);
	    				try {
							field = "'" + URLDecoder.decode(field,"UTF-8") + "'";
						} catch (UnsupportedEncodingException exp) {
							throw new DBComponentException(exp.getMessage());
						}
	    				condition.setValue(field);
	    				condition.setConsTable(DBConstants.DB_BLANK);
	    				condition.setConsField(DBConstants.DB_BLANK);
	    				conditionHash.put(conditionCounter, condition);
	    			}
	    			else if(field.indexOf("%")>0)
	    			{
	    				
	    				condition.setValue(field);
	    				condition.setConsTable(DBConstants.DB_BLANK);
	    				condition.setConsField(DBConstants.DB_BLANK);
	    				conditionHash.put(conditionCounter, condition);
	    			}
	    			else
	    			{	
	    				if(field.equalsIgnoreCase("NULL") || field.equalsIgnoreCase("null"))
	    				{
	    					condition.setValue(field);
	    				}
	    				else
	    				{
		    				String fieldTable = getTableColumn(field,DBConstants.DB_CONDITION_CLAUSE);
		    				String fieldTableArray[] = fieldTable.split(DBConstants.DB_HASH);
		    				//DBComponent.logger.log(0,fieldTableArray[0]);
		    				//DBComponent.logger.log(0,fieldTableArray[1]);
		    				
		    				if(null == fieldTableArray || null == fieldTableArray[0] || null == fieldTableArray[1])
		    				{
		    					throw new DBComponentException("!!!!!!!!!!!!!!!!! Illegal constant value");
		    				}
		    				condition.setValue(DBConstants.DB_BLANK);
		    				condition.setConsTable(fieldTableArray[0]);
		    				condition.setConsField(fieldTableArray[1]);
		    				
	    				}
	    				conditionHash.put(conditionCounter, condition);
	    			}
	    			
	    			lhs=true;
	    		}
	    		else if(lhs==true && (field.equals(DBConstants.DB_AND_CLAUSE) || field.equals(DBConstants.DB_OR_CLAUSE)))
	    		{
	    			//DBComponent.logger.log(0,"logical operator for next condition: " + field);
	    			condition.setJoin(field);
	    		
	    		}
	    	}		
	    	else	
			{	
	    		idenf = (String)tokenizer.next();
	    		idenf = idenf.trim();
	    		//DBComponent.logger.log(0,"idenf----"+idenf);
	    		if(!(idenf.equals(DBConstants.DB_BLANK)))
	    		{
		    		if(conditionOperatorStr.equals(DBConstants.DB_BLANK))
		    			if(idenf.equals(DBConstants.DB_GREATER) || idenf.equals(DBConstants.DB_LESS) || idenf.equals(DBConstants.DB_EQUAL) || idenf.equals(DBConstants.DB_EXC) || idenf.equals(DBConstants.DB_LIKE_SYMB)|| idenf.equals(DBConstants.DB_NOT_EQUAL) )
		    			{
		    				if(idenf.equals(DBConstants.DB_LIKE_SYMB))
		    				{
		    					conditionOperatorStr = DBConstants.DB_LIKE;
		    				}
		    				
		    				else
		    				{
		    					conditionOperatorStr = idenf;
		    				}
		    			}
		    			else
		    			{	
		    				throw new DBComponentException(DBExceptionConstants.EXCEPTION + sql + " in parseConditions");
		    			}
		    		else
		    		{
		    			
		    			if (((conditionOperatorStr.equals(DBConstants.DB_GREATER) || conditionOperatorStr.equals(DBConstants.DB_LESS) || conditionOperatorStr.equals(DBConstants.DB_EXC)) && idenf.equals(DBConstants.DB_EQUAL)) || (idenf.equals(DBConstants.DB_LIKE_SYMB)) )
		    			{
		    				if(idenf.equals(DBConstants.DB_LIKE_SYMB))
		    				{
		    					conditionOperatorStr = conditionOperatorStr + DBConstants.DB_LIKE;
		    				}
		    				else
		    				{
		    					conditionOperatorStr = conditionOperatorStr + idenf;
		    				}
		    			}
		    			else 
		    			{
		    				throw new DBComponentException(DBExceptionConstants.EXCEPTION + sql + " in parseConditions");
		    			}
		    				
		    		}	
	    		}
	        }
	    
	    }
		return conditionHash;
	}
	
	
	/**
	 * This method take query condition string and encode string.
	 * @param conditionStr
	 * @return
	 */
	private String createEncodeString(String conditionStr)
	{
		String finalString ="";
		boolean flag = true;
		if(conditionStr !=null)
		{
			if(conditionStr.indexOf("[")>0)
			{
				String[] conditionArray = conditionStr.split("=");
				if(conditionArray.length>0)
				{
					for(int counter=0; counter<conditionArray.length; counter++)
					{
						String str = conditionArray[counter];
						if(str.trim().startsWith("["))
						{
							str = str.trim();
							String subStr =str.trim().substring(1,str.trim().indexOf("]"));
							//subStr = URLEncoder.encode(subStr);
							try {
								subStr = URLEncoder.encode(subStr, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String nextString = str.substring(str.indexOf("]"),str.length());
							if(flag)
							{
							finalString = (finalString+"["+subStr+nextString).trim();
							flag = false;
							}
							else
							{
								finalString = (finalString+"=["+subStr+nextString).trim();
								
							}
						}
						else
						{
							if(flag)
							{
								finalString = (finalString+str+"=").trim();
								
							}
							else
							{
								finalString = (finalString+"="+str).trim();
							}
						}

					}
				}
			}
			else
			{
				return conditionStr;
			}
		}
		
		return finalString.trim();
	}
	
	
	/**
	 *  This method takes join query as input of user defined-query
     * and create hashtable that maps the join counter to the condition.
     * @param sql  join query.
     * @returns Hashtable<Integer,Condition>
     * @throws DBComponentException
	 * 
	 */
	public Hashtable<Integer,Join> parseJoins(String sql) throws DBComponentException
	{
		Hashtable<Integer,Join> joins= new Hashtable<Integer, Join>();	
		String[] joinStr =sql.trim().split(DBConstants.DB_COMMA);
		Join join= null;
		String joinType=null;
		String joinCon=null;
		for(int i=0;i<joinStr.length;i++)
		{
			if(joinStr[i].indexOf(DBConstants.DB_LEFT_JOIN)>=0)
			{
				int j = joinStr[i].indexOf(DBConstants.DB_LEFT_JOIN);
				joinCon = joinStr[i].substring(
						j+DBConstants.DB_LEFT_JOIN.length(), joinStr[i].length());
				joinType=DBConstants.DB_LEFT_JOINER;
			}
			else if (joinStr[i].indexOf(DBConstants.DB_RIGHT_JOIN)>=0)
			{
				int j = joinStr[i].indexOf(DBConstants.DB_RIGHT_JOIN);
				joinCon = joinStr[i].substring(j+DBConstants.DB_RIGHT_JOIN.length(), joinStr[i].length());
				joinType=DBConstants.DB_RIGHT_JOINER;
			}
			String[] conditionsJoins=joinCon.split(DBConstants.DB_EQUAL);
			String[] joinInfo=StringUtil.splitString(conditionsJoins[0],".");
			String parentTableName=joinInfo[0].toLowerCase();
			String fieldName=joinInfo[1];
			String[] childJoinInfo=StringUtil.splitString(conditionsJoins[1],".");
			String foreginTable=childJoinInfo[0].toLowerCase();
			String foreginTableFieldName=childJoinInfo[1];
			join=new Join();
			join.setJoinType(joinType);
			join.setParentTableName(parentTableName);
			join.setFieldName(fieldName);
			join.setForeginTable(foreginTable);
			join.setForeginTableFieldName(foreginTableFieldName);
			joins.put(i,join);
		}
		return joins;
	}
	

	/**
	 * added by pra 27 june 2009.
	 *  This method takes orderBy query as input of user defined-query
     * and create hashtable that maps the order by counter to the condition.
	 * @param sql
	 * @return returns Hastable contains the information of orderby fields.
	 * @throws DBComponentException
	 */
	public Hashtable<Integer,OrderBy> parseOrderBy(String sql) throws DBComponentException
	{
		Hashtable<Integer,OrderBy> orders= new Hashtable<Integer, OrderBy>();	
		String[] orderStr =sql.trim().split(DBConstants.DB_COMMA);
		OrderBy orderBy=null;
		String order;
		for(int i=0;i<orderStr.length;i++)
		{    orderBy = new OrderBy();
			
			 if(orderStr[i].indexOf(DBConstants.DB_ASCENDING)>0)				 
			 {
				 String[] ord=orderStr[i].split(DBConstants.DB_ASCENDING);
				 orderBy.setOrderType(DBConstants.DB_ASCENDING);
				 order=ord[0].trim();
			 }
			 else if(orderStr[i].indexOf(DBConstants.DB_DECENDING)>0)
			 {
				 String[] ord=orderStr[i].split(DBConstants.DB_DECENDING);
				 orderBy.setOrderType(DBConstants.DB_DECENDING);
				 order=ord[0].trim();
			 }
			 else
			 {
				 order=orderStr[i];
				 orderBy.setOrderType(null);
			 }
			String[]orderInfo=StringUtil.splitString(order,".");
			orderBy.setTableName(orderInfo[0]);
			orderBy.setFieldName(orderInfo[1]);
			orders.put(i, orderBy);
		}
		return orders;
	}
}

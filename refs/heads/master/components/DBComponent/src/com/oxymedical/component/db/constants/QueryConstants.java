/**
 * 
 */
package com.oxymedical.component.db.constants;

/**
 * @author vka
 *
 */
public class QueryConstants {
	//where qbuserdefinedfield.deleted='0'
	public static final String CLASSFIELD_QUERY = "select qbuserdefinedfield.userDefinedFieldName,qbuserdefinedfield.className,qbuserdefinedfield.classFieldName from # as qbuserdefinedfield";
	public static final String SELECT_QUERY_FIELD_SEP = ", ";
	public static final String SELECT = " select ";
	public static final String SELECT_QUERY_TABLE_FIELD_SEP = ".";
	public static final String SELECT_QUERY_FIELD_ALIAS_SEP = " ";
	public static final String FROM = " from ";
	public static final String FROM_QUERY_TABLE_SEP = ", ";
	public static final String BLANK_SPACE = " ";
	public static final String STRING = "String";
	public static final String DATE = "Date";
	public static final String NUMBER = "Number";
	public static final String BIG_DECIMAL = "big_decimal";
	public static final String 	INTEGER = "integer";
	public static final String 	LONG = "long";
	public static final String 	FLOAT = "Float";
	public static final String 	DOUBLE = "double";
	
	public static final String 	SHORT = "short";
	public static final String TIME = "Time";
	public static final String DATE_TIME = "DateTime";
	public static final String CLASSPACKAGE_NAME = "com.oxymedical.component.render.resource_neiswispicdb.";
	
	public static final String WHERE = " where ";
	public static final String WHERE_CONDITION_JOINER_AND = " and ";
	public static final String WHERE_CONDITION_JOINER_OR = " or ";
	
	public static final String TABLE_FIELD_SEP = ".";
	public static final String STRING_QUOTES = "'";
	
	public static final String CONDITION_EQUAL_TO = "=";
	public static final String CONDITION_NOT_EQUAL_TO = "!=";
	public static final String CONDITION_GREATER_THAN = ">";
	public static final String CONDITION_GREATER_THAN_EQUAL_TO = ">=";
	public static final String CONDITION_LESS_THAN = "<";
	public static final String CONDITION_LESS_THAN_EQUAL_TO = "<=";
	public static final String CONDITION_LIKE = " like ";

	public static final String WHERE_CONDITION_BRACKET_START = " ( ";
	public static final String WHERE_CONDITION_BRACKET_END = " ) ";

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
}

package com.oxymedical.component.db.constants;

import com.oxymedical.core.propertyUtil.PropertyUtil;

/**
 *These are the constants defined for the DBComponent class.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class DBConstants
{
	/**
	  * Declares the constant value for the DB_GETALL as all is used to
	  * check that if the table field name consists the 'all' in it.
	 */
	public static final String DB_GETALL = "all";
	/**
	  * Declares the constant value for the DB_FROM_CLAUSE as from is used 
	  * to split the simple query and to make the hibernate sql query.
	 */
	public static final String DB_FROM_CLAUSE = " from ";
	
	public static final String DB_SET = " set ";
	/**
	  * Declares the constant value for the DB_TREE_DISPLAY as displayField is used 
	  * to check the field name is displayField and to generate hashtable fieldHash.  
	 */
	public static final String DB_TREE_DISPLAY = "displayField";
	/**
	  * Declares the constant value for the DB_TREE_VALUE as valueField is used 
	  * to check the field name is valueField and to generate hashtable fieldHash.  
	 */
	public static final String DB_TREE_VALUE = "valueField";
	/**
	  * Declares the constant value for the DB_TREE_QUERY as tree is used to
	  * check that if the queryType is related to tree.
	 */
	public static final String DB_TREE_QUERY = "tree";
	/**
	  * Declares the constant value for the DB_NULL as null is used to
	  * check that field label list is null.
	 */
	public static final String DB_NULL = "null";
	/**
	  * Declares the constant value for the DB_WHERE_CLAUSE as where is used 
	  * to create the hibernate sql query to give condition.
	 */
	public static final String DB_WHERE_CLAUSE = " where ";
	/**
	  * Declares the constant value for the DB_SELECT_CLAUSE as select is 
	  * used to add the select clause.
	 */
	public static final String DB_SELECT_CLAUSE = " select ";
	/**
	  * Declares the constant value for the DB_HASH as #.
	  * The generated hibernate sqlQuery (in stringQuery )is the combination of the hsql and querry type.
	 */
	public static final String DB_HASH = " # ";
	/**
	  * Declares the constant value for the DB_DOT as '.' .
	  *
	 */
	public static final String DB_DOT = ".";
	/**
	  * Declares the constant value for the DB_BLANK as "".
	 */
	public static final String DB_BLANK = "";
	/**
	  * Declares the constant value for the DB_COMMA as ",".
	 */
	public static final String DB_COMMA = ",";
	/**
	  * Declares the constant value for the DB_AS_CLAUSE is 'as'is used
	  * to give the second name or alias of the class name.
	 */
	public static final String DB_AS_CLAUSE = " as ";
	/**
	  * Declares the constant value for the DB_SPACE as " " is to
	  * give the space in the query.
	 */
	public static final String DB_SPACE = " ";
	/**
	  * Declares the constant value for the DB_SLASH as "/" is to
	  * give the slash between the path of the jarFile and the jarFileName.
	 */
	public static final String DB_SLASH = "/";
	/**
	  * Declares the constant value for the DB_RESOURCES_JAR as resources.jar is to
	  * give the jarFileName as resources.jar.
	 */
	public static final String DB_RESOURCES_JAR = "resources.jar";
	/**
	  * Declares the constant value for the DB_CLASS as .class is to
	  * check the extension to the file.
	 */
	public static final String DB_CLASS = ".class";
	/**
	  * Declares the constant value for the DB_CFG_XML as .cfg.xml is to
	  * check the extension to the file.
	 */
	public static final String DB_CFG_XML = ".cfg.xml";
	/**
	  * Declares the constant value for the DB_NEW_LINE as System.getProperty( "line.separator" )
	  * 
	 */
	public static final String DB_NEW_LINE = System.getProperty( "line.separator" );
	/**
	  * Declares the constant value for the DB_HIB_CFG as hibernate.cfg is to
	  * to create the temporary file.
	 */
	public static final String DB_HIB_CFG = "hibernate.cfg";
	/**
	  * Declares the constant value for the DB_LIST_QUERY as list is 
	  * used to define the query related to list.
	 */
	public static final String DB_LIST_QUERY = "list";
	/**
	  * Declares the constant value for the DB_CONDITION_CLAUSE as condition is to
	  * check that that whether query consist of condition clause.
	 */
	public static final String DB_CONDITION_CLAUSE = "condition";
	/**
	  * Declares the constant value for the DB_CONDITIONS_CLAUSE as conditions is to
	  * check that whether query array retrieved from query consist of conditions clause.
	 */
	public static final String DB_CONDITIONS_CLAUSE = " conditions ";
	/**
	  * Declares the constant value for the DB_INTO_CLAUSE as into is used 
	  * while spliting the query.
	 */
	public static final String DB_INTO_CLAUSE = " into ";
	/**
	  * Declares the constant value for the DB_AND_CLAUSE as 'and' is to
	  * check that whether query array consists of 'and' clause in it.
	 */
	public static final String DB_AND_CLAUSE = "and";
	/**
	  * Declares the constant value for the DB_OR_CLAUSE as 'or' is to
	  * check that whether query array consists of 'or' clause in it.
	 */
	public static final String DB_OR_CLAUSE = "or";
	/**
	  * Declares the constant value for the DB_VALUES_CLAUSE as values is to
	  * used to apply the split method for this value.
	 */
	public static final String DB_VALUES_CLAUSE = " values ";
	/**
	  * Declares the constant value for the DB_COLON_GREATER as :>.If query
	  * consist of this tag than convert the tag to >.
	 */
	public static final String DB_COLON_GREATER = ":>";
	/**
	  * Declares the constant value for the DB_COLON_LESS as :<.If query
	  * consist of this tag than convert the tag to <.
	 */
	public static final String DB_COLON_LESS = ":<";
	/**
	  * Declares the constant value for the DB_GREATER as >.If query
	  * consist of :> tag than convert the tag to >.
	 */
	public static final String DB_GREATER = ">";
	/**
	  * Declares the constant value for the DB_LESS as <. If query
	  * consist of :< tag than convert the tag to <.
	 */
	public static final String DB_LESS = "<";
	/**
	  * Declares the constant value for the DB_COLON_EQUAL as ':=' . If query
	  * consist of := tag than convert the tag to =.
	 */
	public static final String DB_EQUAL = "=";
	/**
	  * Declares the constant value for the like as 'like' . If query
	  * consist of like tag than convert the tag to like.
	 */
	public static final String DB_LIKE = "like";	
	/**
	  * Declares the constant value for the like as 'like' . If query
	  * consist of like tag than convert the tag to like.
	 */
	public static final String DB_LIKE_SYMB = "^";	
	
	
	/**
	  * Declares the constant value for the DB_COLON_EQUAL as :=.If query
	  * consist of this tag than convert the tag to =.
	 */
	public static final String DB_COLON_EQUAL = ":=";
	/**
	  * Declares the constant value for the DB_COLON as : .
	 */
	public static final String DB_COLON = ":";
	/**
	  * Declares the constant value for the DB_LEFT_SQUARE_BRAC as [ .
	 */
	public static final String DB_LEFT_SQUARE_BRAC = "[";
	/**
	  * Declares the constant value for the DB_EXC as ! .
	 */
	public static final String DB_EXC = "!";
	/**
	  * Declares the constant value for the DB_REGEX as [ ,=,>,<,!] .
	 */
	public static final String DB_REGEX = "[ ,=,>,<,!,^]";
	/**
	  * Declares the constant value for the DB_XML as .xml is to
	  * give the extension to the temporary file as .xml.
	 */
	public static final String DB_XML = ".xml";
	/**
	  * Declares the constant value for the DB_CONCRETE_DAO as concreteDAO .
	 */
	public static final String DB_CONCRETE_DAO = "concreteDAO = "; 
	/**
	  * Declares the constant value for the DB_TABLE as table .
	 */
	public static final String DB_TABLE = "table = ";
	/**
	  * Declares the constant value for the DB_RET_FIELD_ as retFieldObject .
	 */
	public static final String DB_RET_FIELD_ = "retFieldObject = ";
	/**
	  * Declares the constant value for the DB_PERSISTENT_CLASS as retFieldObject .
	 */
	public static final String DB_PERSISTENT_CLASS = "persistentClass = ";
	/**
	  * Declares the constant value for the DB_ILLEGAL_QUERY.
	 */
	public static final String DB_ILLEGAL_QUERY = "!! Illegal get query !! queryParam & queryType are = ";
	/**
	  * Declares the constant value for the DB_COLUMN_TABLE as columnTable.
	 */
	public static final String DB_COLUMN_TABLE = "columnTable = ";
	/**
	  * Declares the constant value for the DB_CLASS_FIELDS as classFields.
	 */
	public static final String DB_CLASS_FIELDS = "classFields = ";
	/**
	  * Declares the constant value for the DB_CONFIG_SETTING as configSettings.
	 */
	public static final String DB_CONFIG_SETTING = "configSettings = ";
	/**
	  * Declares the constant value for the DB_MODULE_FILE as modulefileStream.
	 */
	public static final String DB_MODULE_FILE = "modulefileStream = ";
	/**
	  * Declares the constant value for the DB_DATABASE_OPERATION as databaseOperation.
	 */
	public static final String DB_DATABASE_OPERATION = "databaseOperation = ";
	/**
	  * Declares the constant value for the DB_FIELD_HASH as fieldHash.
	 */
	public static final String DB_FIELD_HASH = "fieldHash = ";
	/**
	  * Declares the constant value for the DB_REGISTER_WINDOW as registerWindow.
	 */
	public static final String DB_REGISTER_WINDOW = "registerWindow = ";
	/**
	  * Declares the constant value for the DB_APPLICATION_NAME as applicationName.
	 */
	public static final String DB_APPLICATION_NAME = "applicationName = ";
	/**
	  * Declares the constant value for the DB_APPLICATION_FORM_LIST as applicationFormList.
	 */
	public static final String DB_APPLICATION_FORM_LIST = "applicationFormList = ";
	/**
	  * Declares the constant value for the DB_APPLICATION_TABLE_HASH as databaseTableHash.
	 */
	public static final String DB_APPLICATION_TABLE_HASH = "databaseTableHash = ";
	/**
	  * Declares the constant value for the DB_SQL_QUERY as SQL Query .
	 */
	public static final String DB_SQL_QUERY = "SQL Query = ";
	/**
	  * Declares the constant value for the DB_LEVEL_NUM as levelnumber .
	 */
	public static final String DB_LEVEL_NUM = "levelnumber = ";
	/**
	  * Declares the constant value for the DB_VALUE_FIELD as valuefield .
	 */
	public static final String DB_VALUE_FIELD = "valuefield = ";
	/**
	  * Declares the constant value for the DB_DISPLAY_FIELD as displayfield .
	 */
	public static final String DB_DISPLAY_FIELD = "displayfield = ";
	/**
	  * Declares the constant value for the DB_TABLE_LIST as tableList .
	 */
	public static final String DB_TABLE_LIST = "tableList = ";
	/**
	  * Declares the constant value for the DB_CONDITION_STRING as conditionstring .
	 */
	public static final String DB_CONDITION_STRING = "conditionstring = ";
	/**
	  * Declares the constant value for the DB_PARENT_NODE_LEVEL as parentNodelevel .
	 */
	public static final String DB_PARENT_NODE_LEVEL = "parentNodelevel = ";
	/**
	  * Declares the constant value for the DB_CONDITION_FIELD as conditionField .
	 */
	public static final String DB_CONDITION_FIELD = "conditionField = ";
	/**
	  * Declares the constant value for the DB_CONDITION_VALUE as conditionValue .
	 */
	public static final String DB_CONDITION_VALUE = "conditionValue = ";
	/**
	  * Declares the constant value for the DB_PROJECT as project .
	 */
	public static final String DB_PROJECT = "project = ";
	/**
	  * Declares the constant value for the DB_PROPERTIES as _properties .
	 */
	public static final String DB_PROPERTIES = "_properties = ";
	/**
	  * Declares the constant value for the DB_UNDEFINED as undefined .
	 */
	public static final String DB_UNDEFINED = "undefined";
	/**
	  * Declares the constant value for the DB_UNDEFINED as undefined .
	 */
	public static final String DB_UNDEFINE = "undefine";
	/**
	  * Declares the constant value for the DB_MINUS as - .
	 */
	public static final String DB_MINUS = "-";
	/**
	  * Declares the constant value for the DB_ZERO as 0 .
	 */
	public static final int DB_ZERO = 0;
	/**
	  * Declares the constant value for the DB_ONE as 1 .
	 */
	public static final int DB_ONE = 1;
	/**
	  * Declares the constant value for the DB_TWO as 2 .
	 */
	public static final int DB_TWO = 2;
	
	
	public static final String DB_AS_BLANK = " as ";
	
	public static final String DB_PAGE_LIMIT = " limit ";
	
	//added constants for joins doen by pra on 10-jule-2009
	
	public static final String DB_JOINS_CLAUSE = " joins ";
	
	public static final String DB_LEFT_JOIN = "leftjoin";
	
	public static final String DB_RIGHT_JOIN = "rightjoin";

	public static final String DB_LEFT_JOINER="left outer join";
	
	public static final String DB_RIGHT_JOINER="right outer join";
	
	public static final String DB_UNION = " union ";
	
	
	//added new constants for orderby added by pra on 27-july 2009
	public static final String ORDERBY_CLAUSE="orderby";
	public static final String DB_ORDER_BY="order by";
	public static final String DB_ASCENDING ="asc";
	public static final String DB_DECENDING ="desc";
	//added new constants for concatination
	public static final String DB_CONCAT ="concat";
	public static final String DB_CONCAT_JOINER ="||' '||";
	public static final String DB_CONCAT_QUERY_SEP ="|";
	public static final String DB_BRACKET_OPEN ="(";
	public static final String DB_BRACKET_CLOSE =")";
	public static final String DB_NOT_EQUAL = "!=";
	
	//Start Changes by netram sahu on 4-May-2012
	public static final String EIBDBComponent = PropertyUtil.setUpProperties("GLASSFISH_DOMAIN_HOME") + "/config/src/EIBDBComponent-prefs.properties";
	//End Changes by netram sahu

	
}
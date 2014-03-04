package com.oxymedical.component.db.constants;
/**
 *These are the constants defined for the Register class.
 * @author      Oxyent Medical
 * @version     1.0.0
*/
public class RegisterConstants
{
	/**
	  * Declares the constant value for the REG_DATA_PATTERN as datapattern is to
	  * check that if the element has associated to data pattern.
	 */
	public static final String REG_DATA_PATTERN = "datapattern";
	/**
	  * Declares the constant value for the REG_DATA_FIELD as datapattern 
	  * that is used to return the attribute value for datafield.
	 */
	public static final String REG_DATA_FIELD = "datafield";
	/**
	  * Declares the constant value for the REG_DATA_TABLE as datatable is used to
	  * return the attribute with the given datatable.
	 */
	public static final String REG_DATA_TABLE = "datatable";
	/**
	  * Declares the constant value for the REG_SEARCH_TAG as search is used to
	  * checks that whether the element is search in the xml which is passed as document.
	 */
	public static final String REG_SEARCH_TAG = "search";
	/**
	  * Declares the constant value for the REG_NAME_ATTR as name 
	  * that is used to return the attribute value for the patternRoot or searchElement.
	 */
	public static final String REG_NAME_ATTR = "name";
	/**
	  * Declares the constant value for the REG_QUERY_ATTR as Query is used to
	  * return the elements contained in this element as list.
	 */
	public static final String REG_QUERY_ATTR = "Query";
	/**
	  * Declares the constant value for the REG_OPER_ATTR as Operator is used to
	  * return the attribute with the given name Operator.
	 */
	public static final String REG_OPER_ATTR = "Operator";
	/**
	  * Declares the constant value for the REG_COND_ATTR as Condition is used to
	  * return the attribute with the given name Condition.
	 */
	public static final String REG_COND_ATTR = "Condition";
	/**
	  * Declares the constant value for the REG_FIELD_NAME_ATTR as FieldName is used to
	  * return the attribute and attribute value with the given name passed as FieldName.
	 */
	public static final String REG_FIELD_NAME_ATTR = "FieldName";
	/**
	  * Declares the constant value for the REG_FIELD_VALUE_ATTR as FieldValue is used to
	  * return the attribute and attribute value with the given name FieldValue.
	 */
	public static final String REG_FIELD_VALUE_ATTR = "FieldValue";
	/**
	  * Declares the constant value for the REG_ID_ATTR as id 
	  * that is used to return the attribute value i.e. elementId.
	 */
	public static final String REG_ID_ATTR = "id";
	/**
	  * Declares the constant value for the REG_DRIVER as jdbc:mysql:// is used to
	  * create the server url.
	 */
	public static final String REG_DRIVER = "jdbc:mysql://";
	/**
	  * Declares the constant value for the REG_PORT as :3306/ is used to
	  * create the server url.
	 */
	public static final String REG_PORT = ":3306/";
	/**
	  * Declares the constant value for the REG_PACKAGE_NAME as com.oxymedical.component.render is used 
	  * to give the package name viz. used to setting up data configuration.
	 */
	public static final String REG_PACKAGE_NAME1  = "com.oxymedical.component.render.resource_";
	public static final String REG_PACKAGE_NAME  = "com.oxymedical.component.render";
	/**
	  * Declares the constant value for the REG_BASE_WINDOW as basewindow is used to
	  * checks that coming xml root element is basewindow.
	 */
	public static final String REG_BASE_WINDOW = "basewindow";
	
	
	public static final String APPROOT_TAGNAME = "application";
	public static final String FORM_PATTERN_TAG_NAME = "FormPattern";
	public static final String FORM_TAG_NAME = "formpattern";
	public static final String ELEMENT_ID = "id";
	public static final String DEFAULT_ARG = "default";
	public static final String ELEMENT_NAME = "name";
	public static final String DATA_SETTINGS_PATH="data/datasettings.xml";
	public static final String TRUE="true";
	public static final String FORM_PATTERN_FOLDER_NAME ="forms";
	public static final String PATTERN_EXTN = ".xml";
	public static final String REG_ID_FIELDMAP = "DataMappings";
	
	
}

package com.oxymedical.component.db.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;

import com.oxymedical.component.db.constants.DBConstants;
import com.oxymedical.component.db.constants.TreeConstants;
import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.exception.DBExceptionConstants;

/**
 *This class is used to extract the object value for the given class object.
 * 
 * @author Oxyent Medical
 * @version 1.0.0
 */
public class DBOperationUtilities {
	/** ConvertDataType object is used the set the object value in it. */
	private static ConvertDataType cnvtDataType = null;

	/**
	 * Extract the value for the primary field of the given persistence class.
	 * 
	 * @param parPersClass
	 * @param parObj
	 * @returns Object
	 * @throws DBComponentException
	 */
	public static Object extractPrimaryValue(PersistentClass parPersClass,
			Object parObj) throws DBComponentException {
		Object retValue = null;
		try {
			Class parMappedClass = parPersClass.getMappedClass();
			Property getterPc = parPersClass.getIdentifierProperty();
			Getter parGetter = getterPc.getGetter(parMappedClass);
			Method method = parMappedClass.getMethod(parGetter.getMethodName());
			retValue = method.invoke(parObj);

		}

		catch (NoSuchMethodException e) {
			// e.printStackTrace();
			throw (new DBComponentException(
					DBExceptionConstants.NO_SUCH_METHOD_EXCEPTION
							+ "in extractPrimaryValue"));
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
			throw (new DBComponentException(
					DBExceptionConstants.ILLEGAL_ACCESS_EXCEPTION
							+ "in extractPrimaryValue"));
		} catch (InvocationTargetException e) {
			// e.printStackTrace();
			throw (new DBComponentException(
					DBExceptionConstants.INVOCATION_TARGET_EXCEPTION
							+ "in extractPrimaryValue"));
		} catch (IllegalArgumentException e) {
			throw (new DBComponentException(
					"IllegalArgumentExceptionin extract PrimaryValue"));
		}
		return retValue;
	}

	/**
	 * Extracts the primary key object for the given foriegn key table object.
	 * 
	 * @param parPersClass
	 * @param parObj
	 * @param fieldValue
	 * @throws DBComponentException
	 * @returns Object
	 */
	public static Object extractParentObject(PersistentClass parPersClass,
			Object parObj, Object fieldValue) throws DBComponentException {
		// PersistentClass parPersClass =
		// configuration.getClassMapping(parameterTypes[0].getName());
		try {
			cnvtDataType = new ConvertDataType();
			Class parMappedClass = parPersClass.getMappedClass();
			parObj = parMappedClass.newInstance();
			Property pc = parPersClass.getIdentifierProperty();
			Setter parSetter = pc.getSetter(parMappedClass);
			Class[] parParameterTypes = parSetter.getMethod()
					.getParameterTypes();
			Method parMethod = parMappedClass.getMethod(parSetter
					.getMethodName(), parParameterTypes);
			fieldValue = cnvtDataType.setObjectValue(fieldValue,
					parParameterTypes[0].getName());
			parMethod.invoke(parObj, fieldValue);
			return parObj;
		} catch (InstantiationException e) {
			throw new DBComponentException(
					DBExceptionConstants.INSTANTATION_EXCEPTION + parPersClass
							+ DBConstants.DB_COMMA + parObj
							+ DBConstants.DB_AND_CLAUSE + fieldValue
							+ " in extractParentObject");
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.NO_SUCH_METHOD_EXCEPTION
							+ parPersClass + DBConstants.DB_COMMA + parObj
							+ DBConstants.DB_AND_CLAUSE + fieldValue
							+ " in extractParentObject");
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.ILLEGAL_ACCESS_EXCEPTION
							+ parPersClass + DBConstants.DB_COMMA + parObj
							+ DBConstants.DB_AND_CLAUSE + fieldValue
							+ " in extractParentObject");
		} catch (InvocationTargetException e) {
			// e.printStackTrace();
			throw new DBComponentException(
					DBExceptionConstants.INVOCATION_TARGET_EXCEPTION
							+ parPersClass + DBConstants.DB_COMMA + parObj
							+ DBConstants.DB_AND_CLAUSE + fieldValue
							+ " in extractParentObject");
		}
	}

	/**
	 * Checks the given field that it contains its tables or not.
	 * 
	 * @param queryType
	 * @param reqFieldHash
	 * @throws DBComponentException
	 * @returns void
	 */
	public static void validateField(String queryType,
			LinkedHashMap<String, String> reqFieldHash) throws DBComponentException {
		String selField = null;
		//Enumeration<String> reqFieldEnum = reqFieldHash.keys();
		Object[] reqFieldEnum = reqFieldHash.keySet().toArray();
		for(int counter=0;counter<reqFieldEnum.length;counter++)
		{
			//hashtable change into LinkedHashMap
		/*while (reqFieldEnum.hasMoreElements()) {
			selField = reqFieldEnum.nextElement();*/
			selField = (String)reqFieldEnum[counter];		
			if (queryType.equalsIgnoreCase(TreeConstants.TREE_QUERY)) {
				selField = reqFieldHash.get(selField);
			}
			if (selField.indexOf(DBConstants.DB_DOT) < 0) {
				throw new DBComponentException(DBExceptionConstants.EXCEPTION
						+ queryType + DBConstants.DB_COMMA + reqFieldHash
						+ "in validateField");
			}
		}
	}

	/**
	 * Retrieves the table to which that field belongs.
	 * 
	 * @param retField
	 * @returns String[]
	 */
	public static String[] retreiveTableField(String retField) {
		String REGEX = DBConstants.DB_DOT;
		Pattern p = Pattern.compile(REGEX, Pattern.LITERAL);
		String tableField[] = p.split(retField);
		return tableField;
	}

	/**
	 * The method is used to extract the table alias name.
	 * 
	 * @param persistentClass
	 * @param tableAliasHash
	 * @returns String
	 */
	public static String extractTableAlias(PersistentClass persistentClass,
			Hashtable tableAliasHash) {
		PersistentClass ps = null;
		String table = null;
		Enumeration tableAliasEnum = tableAliasHash.keys();
		while (tableAliasEnum.hasMoreElements()) {
			ps = (PersistentClass) tableAliasEnum.nextElement();
			if (persistentClass.equals(ps)) {
				table = (String) tableAliasHash.get(ps);
				break;
			}
		}
		return table;
	}

	/**
	 * Retrieves the property name for the given table field.
	 * 
	 * @param persistentClass
	 * @param fieldName
	 * @returns Object
	 */
	public static Object retClassField(PersistentClass persistentClass,
			String fieldName) 
	{
		if (fieldName.indexOf(DBConstants.DB_AS_BLANK) > 0)
		{
			fieldName = fieldName.substring(0, fieldName.indexOf(DBConstants.DB_AS_BLANK));
		}
		Iterator propertyIt = persistentClass.getPropertyIterator();
		List<String> propertyList = new ArrayList<String>();
		boolean primaryKeysPresent = false;
		while (propertyIt.hasNext())
		{
			primaryKeysPresent = true;
			Property property = (Property) propertyIt.next();
			Iterator propColomnIterator = property.getValue().getColumnIterator();
			while (propColomnIterator.hasNext())
			{
				Column col = (Column) propColomnIterator.next();

				String propertyName = property.getName();
				if (fieldName.equalsIgnoreCase(DBConstants.DB_BLANK))
				{
					propertyList.add(propertyName);
				}
				else if (col.getName().equalsIgnoreCase(fieldName))
				{
					return propertyName;
				}
			}
		}
		
		if (!primaryKeysPresent)
		{
			// Table might contain composite id's
			Component identifier = (Component) persistentClass.getIdentifier();
			Iterator columnIterator = identifier.getPropertyIterator();
			while (columnIterator.hasNext())
			{
				Property property = (Property) columnIterator.next();
				Iterator propColomnIterator = property.getValue().getColumnIterator();
				while (propColomnIterator.hasNext())
				{
					Column col = (Column) propColomnIterator.next();
					// DBComponent.logger.log(0,"col.getName() = "+col.getName());
					String propertyName = property.getName();
					if (fieldName.equalsIgnoreCase(DBConstants.DB_BLANK))
					{
						propertyList.add(propertyName);
					}
					else if (col.getName().equalsIgnoreCase(fieldName))
					{
						return property.getName();
					}
				}
			}
		}
		
		Property property = persistentClass.getIdentifierProperty();
		if (null != property)
		{
			if (null != property.getValue())
			{
				Iterator propColomnIterator = property.getValue().getColumnIterator();
				while (propColomnIterator.hasNext())
				{
					Column col = (Column) propColomnIterator.next();

					String propertyName = property.getName();
					if (fieldName.equalsIgnoreCase(DBConstants.DB_BLANK))
					{
						propertyList.add(propertyName);
					}
					else if (col.getName().equalsIgnoreCase(fieldName))
					{
						return property.getName();
					}
				}
			}
		}
		
		if (fieldName.equalsIgnoreCase(DBConstants.DB_BLANK))
		{
			return propertyList;
		}
		else
		{
			return null;
		}
	}

}

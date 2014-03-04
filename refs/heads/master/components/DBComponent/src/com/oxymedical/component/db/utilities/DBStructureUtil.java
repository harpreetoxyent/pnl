package com.oxymedical.component.db.utilities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.KeyValue;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.property.Setter;
import org.hibernate.type.Type;

import com.oxymedical.component.db.DBComponent;
import com.oxymedical.component.db.IDBComponent;
import com.oxymedical.component.db.query.data.FieldType;
import com.oxymedical.component.db.query.data.From;
import com.oxymedical.component.db.query.data.Select;
import com.oxymedical.component.db.constants.QueryConstants;

/**
 * This class provides functions that fetching class tables and its fields information.
 * 
 * @author hs
 */
// TODO This class is not tested till now.
public class DBStructureUtil
{
	public IDBComponent dbc = null;
	
	// Maintained static variables so that not everytime, we perform expensive
	// operation of iterating the PersistentClasses
	private static List<String> allClassList = null;
	private static Hashtable<String, String> tableClassMap = null;
	private static Hashtable<String, List<String>> tableColInfo = null;
	
	

	/**
	 * Fetches a list of all class tables present in database
	 * @return : List of all class tables
	 */
	public List<String> getAllTableClasses()
	{
		if (allClassList == null)
		{
			allClassList = getClasses();
		}
		return allClassList;
	}
	
	/**
	 * Fetches a list of all fields from all class tables present in database
	 * <code>To be implemented</code>
	 * @return : List of all fields from all class tables
	 */
	public List<Object> getAllTableClassFields()
	{
		// To be implemented
		return null;
	}
	
	/**
	 * Fetches a list of all fields from a class tables present in database
	 * @param tableClassName : Name of class table for which fields are required
	 * @return : List of all fields from the specified class tables
	 */
	public List<String> getOnlyTableClassFields(String tableClassName)
	{
		if (tableColInfo == null) tableColInfo = new Hashtable<String, List<String>>();
		List<String> tableColList = tableColInfo.get(tableClassName);
		if (tableColList == null)
		{
			tableColList = getColumnList(tableClassName);
			tableColInfo.put(tableClassName, tableColList);
		}
		return tableColList;
	}

	private List<String> getClasses()
	{
		List<String> classList=null;
		Iterator<PersistentClass> pcIterator = dbc.getConfig().getClassMappings();
		
		while(pcIterator.hasNext())
		{
			if (classList == null) classList = new ArrayList<String>();
			PersistentClass pClass =  (PersistentClass) pcIterator.next();
			
			String className = pClass.getClassName();
			className = className.replaceAll(QueryConstants.CLASSPACKAGE_NAME, "").trim();
			if(className!=null)
			{
				classList.add(className);
				
				// Also maintain a hashtable for class and table mapping
				if (tableClassMap == null) tableClassMap = new Hashtable<String, String>();
				tableClassMap.put(pClass.getTable().getName().toLowerCase(), className);
			}
		}
		
		return classList;
	}
	
	/**
	 * Returns className related to the specified tableName from Mapping
	 * @param tableName
	 * @return
	 */
	public String getClassNameFromTable(String tableName)
	{
		if (tableClassMap == null) getClasses();
		String retVal = tableClassMap.get(tableName.toLowerCase());
		if (retVal == null) retVal = tableName;
		return retVal;
	}
	

	private List<String> getColumnList(String tableName)
	{
		List<String> columnList=null;
		tableName =QueryConstants.CLASSPACKAGE_NAME+tableName;
		PersistentClass persistClass = this.dbc.getConfig().getClassMapping(tableName.trim());
		
		Iterator<Property> itr = persistClass.getPropertyIterator();
		Class mappedClass = persistClass.getMappedClass();
		while(itr.hasNext())
		{
			if (columnList == null) columnList = new ArrayList<String>();
			Property property =   itr.next();
			String colName = property.getName();
			Setter setter = property.getSetter(mappedClass);			
			Class[] parameterTypes = setter.getMethod().getParameterTypes();
		    String typeName = parameterTypes[0].getName();
		    
			if(colName!=null && !(typeName.equalsIgnoreCase("java.util.Set")))
			{
				columnList.add(colName);
			}
		}
		Property pId = persistClass.getIdentifierProperty();
		if(pId!=null)
		{
			String idName = pId.getName();
			if(idName!=null)
			{
				columnList.add(idName);
			}
		}
		return columnList;
	}
	
	
	
	
	/**
	 * @param dbc the dbc to set
	 */
	public void setDbc(IDBComponent dbc)
	{
		this.dbc = dbc;
	}

	
	/**
	 * This method take input tablename , fieldattribute and return datatype of field.
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public FieldType getColumnType(String tableName,String fieldName)
	{
		PersistentClass persistClass = dbc.getConfig().getClassMapping(tableName.trim());
		Type type = null;
		String typeName = null;
		Property pId = persistClass.getIdentifierProperty();
		if(pId!=null)
		{
			String idName = pId.getName();
			if(idName!=null)
			{
				if(idName.equalsIgnoreCase(fieldName))
				{
					type = pId.getType();
					typeName = type.getName();
				}
			}
		}
		if(type==null)
		{
			Property property = persistClass.getProperty(fieldName);
			
			type = property.getType();
			typeName = type.getName();
		}
		if(typeName.equalsIgnoreCase(QueryConstants.STRING))
		{
			return FieldType.STRING;
		}
		else if(typeName.equalsIgnoreCase(QueryConstants.DOUBLE) || typeName.equalsIgnoreCase(QueryConstants.FLOAT) || typeName.equalsIgnoreCase(QueryConstants.NUMBER)||typeName.equalsIgnoreCase(QueryConstants.INTEGER)||typeName.equalsIgnoreCase(QueryConstants.LONG)||typeName.equalsIgnoreCase(QueryConstants.SHORT)||typeName.equalsIgnoreCase(QueryConstants.BIG_DECIMAL))
		{
			return FieldType.NUMBER;
		}
		else if(typeName.equalsIgnoreCase(QueryConstants.DATE))
		{
			return FieldType.DATE;
		}
		else if(typeName.equalsIgnoreCase(QueryConstants.TIME))
		{
			return FieldType.TIME;
		}
		else if(typeName.equalsIgnoreCase(QueryConstants.DATE_TIME))
		{
			return FieldType.DATE_TIME;
		}
		return FieldType.STRING;
	}
	
	
	/**
	 * This method used to get unique id of the table.
	 * @param tableName
	 * @return
	 */
	public String getUniqueId(String tableName)
	{
		//tableName =QueryConstants.CLASSPACKAGE_NAME+tableName;
		PersistentClass persistClass = dbc.getConfig().getClassMapping(tableName.trim());
		String idName = null;
		Property pId = persistClass.getIdentifierProperty();
		if(pId!=null)
		{
			idName = pId.getName();
		}
		return idName;
	}

	/**
	 * This method used to get reference id of the table;
	 * @param tableName
	 * @return
	 */
	public String getReferenceId(String tableName,String parentTable)
	{
		//tableName =QueryConstants.CLASSPACKAGE_NAME+tableName;
		parentTable = parentTable.replace(QueryConstants.CLASSPACKAGE_NAME, "");
		PersistentClass persistClass = dbc.getConfig().getClassMapping(tableName.trim());
		String colName = null;
		Table table = persistClass.getTable();
		String referenceTableName = null;
		
		Class mappedClass = persistClass.getMappedClass();
		Iterator<Property> itr = persistClass.getPropertyIterator();
		while(itr.hasNext())
		{
			Property property =   itr.next();
			colName = property.getName();
			Setter setter = property.getSetter(mappedClass);			
			Class[] parameterTypes = setter.getMethod().getParameterTypes();
		    String typeName = parameterTypes[0].getName();
		    typeName=   typeName.replace(QueryConstants.CLASSPACKAGE_NAME, "");
			if(typeName.trim().equalsIgnoreCase(parentTable.trim()))
			{
			    return colName;
			}
		}
		
		/*Iterator<ForeignKey> itr1 = table.getForeignKeyIterator();
		while(itr1.hasNext())
		{
			ForeignKey key = itr1.next();
			idName = ((Column)key.getColumns().get(0)).getName();
			Table referencedTable = key.getReferencedTable();
			referenceTableName = referencedTable.getName();
			if(referenceTableName.equalsIgnoreCase(parentTable))
			{
				break;
			}
		}*/
			
		return null;
	}
	
	
	/**
	 * This method takes table field name and return class field name from hbm file.
	 * @param tableName
	 * @param fieldName
	 * @return String
	 */
	public String getClassField(String tableName, String fieldName)
	{
		String classFieldName = null;
		tableName =QueryConstants.CLASSPACKAGE_NAME+tableName.trim();
		PersistentClass persistClass = this.dbc.getConfig().getClassMapping(tableName.trim());
		Iterator<Property> itr = persistClass.getPropertyIterator();
		Class mappedClass = persistClass.getMappedClass();
		while(itr.hasNext())
		{
			Property property =   itr.next();
			String colName = property.getName();
		    
			if(colName!=null && (colName.equalsIgnoreCase(fieldName)))
			{
				classFieldName = colName;
				break;
			}
		}
		return classFieldName;
	}
	
	
	/**
	 * This method return foreign column from parentTable.
	 * @param nextTable
	 * @param parentTable
	 * @return
	 */
	public String getReferenceColumn(String nextTable,String parentTable)
	{
		nextTable = nextTable.replace(QueryConstants.CLASSPACKAGE_NAME, "");
		PersistentClass persistClass = dbc.getConfig().getClassMapping(parentTable.trim());
		String colName = null;
		Table table = persistClass.getTable();
		String referenceTableName = null;
		Class mappedClass = persistClass.getMappedClass();
		Iterator<Property> itr = persistClass.getPropertyIterator();
		while(itr.hasNext())
		{
			Property property =   itr.next();
			colName = property.getName();
			Setter setter = property.getSetter(mappedClass);			
			Class[] parameterTypes = setter.getMethod().getParameterTypes();
		    String typeName = parameterTypes[0].getName();
		    typeName=   typeName.replace(QueryConstants.CLASSPACKAGE_NAME, "");
			if(typeName.trim().equalsIgnoreCase(nextTable.trim()))
			{
			    return colName;
			}
		}
		
		return null;
	}
	
	
}

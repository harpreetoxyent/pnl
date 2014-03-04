package com.oxymedical.component.useradmin.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.useradmin.IUserFields;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Role;
import com.oxymedical.component.useradmin.model.Userfieldsforusertype;
import com.oxymedical.component.useradmin.model.UserfieldsforusertypePK;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;

/**
 * @author pra
 *
 */
public class UserFieldsImpl implements IUserFields {

	// Method now throws UAComponentException instead of DBComponentException
	// .Change done by pra on 26-may-2009
	
/*
 * This methods the list of userdefinedfield objects for the specified userpatternid 
 * Added by pra on 22-May-2009
 */ 
	
public void addUserDefinedFields(String userPatternId, List<String> fields) throws UAComponentException
	{	
	
	//Method implementation changed so as to update the fields during edit.
	//Change done by pra on 16 june 2009.
	String sqlQuery = SQLCommands.Find_Fields_Based_on_UserPatternId;
	ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();	
	NameQueryParameter userPattern = new NameQueryParameter(Constants.userPatternId, userPatternId);
	listParam.add(userPattern);
	List list=null;
	try {
		list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
				.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
	} catch (DBComponentException e1) {
	  throw new UAComponentException("Exception occurred ");
	}
	if(list!=null && list.size()>0)
	{
		for (Iterator fieldRow = list.iterator(); fieldRow.hasNext();)
		{
			Userfieldsforusertype userFields= (Userfieldsforusertype)fieldRow.next();
			try {
				ConnectionDatabase.GetInstanceOfDatabaseComponent().deleteObject(userFields);
			} catch (DBComponentException e) {
			throw new UAComponentException("Error in the fields deleteion--");
			}
		}
	}
	
	
	
		if(fields!=null && fields.size()>0)
		{
		for(int i=0;i<fields.size();i++)
		{
			Userfieldsforusertype userFields = new Userfieldsforusertype();
			//implementation changed as per the composite id's so that edit can be take place done by pra on 11 june2009
			UserfieldsforusertypePK fieldsUser=new UserfieldsforusertypePK();
			fieldsUser.setUserFieldId(fields.get(i));
			fieldsUser.setUserTypeId(userPatternId);
			userFields.setComp_id(fieldsUser);		
			try {
				ConnectionDatabase.GetInstanceOfDatabaseComponent().saveObject(userFields);
			} catch (DBComponentException e) {
				
				throw new UAComponentException("Exception occured in saving the User Defined Fields");
				
			}
		}
		}
	}

/**
 * Method returns the list of fields from the give userpatternid
 * added by pra on 11-june-2009
 * @param userPatternId
 * @return
 * @throws UAComponentException
 */
	public List getUserDefinedFieldsFromUserPattern(String userPatternId)
			throws UAComponentException {
		List<Object> resultList = new LinkedList();
		String sqlQuery = SQLCommands.Select_Fields_Base_On_UserPatternId;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		NameQueryParameter Id = new NameQueryParameter(Constants.userPatternId,
				userPatternId);
		listParam.add(Id);
		List list = null;
		try {
			list = (List) ConnectionDatabase.GetInstanceOfDatabaseComponent()
					.executeHSQLQueryWithNameParameter(sqlQuery, listParam);
		} catch (Exception e) {
			throw (new UAComponentException("Fields with " + userPatternId
					+ " does not exists"));
		}
		if ((list == null) || (list.size() == 0)) {
			return null;
		} else {
			for (Iterator fieldRow = list.iterator(); fieldRow.hasNext();) {
				Object fieldRowRowData = fieldRow.next();
				resultList.add(fieldRowRowData);
			}
		}
		return resultList;
	}
}
package com.oxymedical.component.useradmin;

import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;

/**
 * 
 * interface added for addition of user defined fields.
 * @author pra
 *
 */
public interface IUserFields {
	// Method signature changed previosuly it throws DBComponentException now it
	// throws UAComponentException change done by pra on 26-May-2009
	public void addUserDefinedFields(String userPatternId,List<String>fields) throws UAComponentException;
}

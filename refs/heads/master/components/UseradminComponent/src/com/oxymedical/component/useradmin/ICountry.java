package com.oxymedical.component.useradmin;

import java.util.List;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Country;

public interface ICountry 
{
	public List getCountries()throws DBComponentException , UAComponentException;
	public List getCountries(boolean active);
	public Country getCountry(String countryId) throws UAComponentException,DBComponentException;
	public Country getCountryFromName(String name) throws UAComponentException , DBComponentException;

}

package com.oxymedical.component.renderer.command;

import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.ldapData.ILdapData;
import com.oxymedical.core.ldapData.LdapData;
import com.oxymedical.core.renderdata.IDataUnit;

public class LDAPCommand  extends BaseCommand implements IUiLibraryCompositeCommand{
	
	@Override
	public void execute(){
		IDataUnit dataUnit = createDataUnit();
		dataUnit = updateDataUnit(this.getParamList(), dataUnit);
		this.setData( this.getRouter().routeToModeler(dataUnit));
	}


	@Override
	public IHICData getHICData(){
		return this.getData();
	}

	private IDataUnit updateDataUnit(String paramList, IDataUnit dataUnit){
		ILdapData ldapData = new LdapData();
		if(this.getMethodName().equalsIgnoreCase("authenticateUserInLDAP")){
			String userId = (String)this.getFormValues().get("userId");
			String password = (String)this.getFormValues().get("password");
			ldapData.setUserName(userId);
			ldapData.setPassword(password);
		}else if(this.getMethodName().equalsIgnoreCase("searchInLDAP")){
			String searchFilter = (String)this.getFormValues().get("ldapSearchFilter");
			ldapData.setSearchFilter(searchFilter);
			String getAllLdapAttributes = (String)this.getFormValues().get("getAllLdapAttributes");
			if(getAllLdapAttributes == null || getAllLdapAttributes.equalsIgnoreCase("false")){
				ldapData.addAttribute(CoreConstants.LDAP_SEARCH_REQ_PROPS_KEY, CoreConstants.LDAP_SEARCH_REQ_PROPS_LIST);
			}
		}
		dataUnit.setLdapData(ldapData);
		return dataUnit;
	}
}
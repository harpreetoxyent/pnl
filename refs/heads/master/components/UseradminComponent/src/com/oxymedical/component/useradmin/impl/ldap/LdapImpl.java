package com.oxymedical.component.useradmin.impl.ldap;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.oxymedical.component.db.exception.DBComponentException;
import com.oxymedical.component.db.utilities.parameters.NameQueryParameter;
import com.oxymedical.component.ldap.ILDAPComponent;
import com.oxymedical.component.ldap.LDAPComponent;
import com.oxymedical.component.ldap.exception.LDAPComponentException;
import com.oxymedical.component.useradmin.constants.Constants;
import com.oxymedical.component.useradmin.constants.SQLCommands;
import com.oxymedical.component.useradmin.exception.NoSuchOrganizationException;
import com.oxymedical.component.useradmin.exception.UAComponentException;
import com.oxymedical.component.useradmin.model.Group;
import com.oxymedical.component.useradmin.model.Organization;
import com.oxymedical.component.useradmin.operations.ConnectionDatabase;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.ldapData.ILdapData;
import com.oxymedical.core.ldapData.LdapData;

public class LdapImpl 
{
	static private ILDAPComponent ldapComp = null;
	private ILdapData ldapData = null;
	private IHICData hicData = null;
	/*
	 * 
	*/
	

	public void addLDAPEntry(Hashtable<String,String> ldapInfoHash) throws UAComponentException
	{
		hicData = createLdapXML(ldapInfoHash);
		
		try {
			ldapComp = new LDAPComponent();
		
			ldapComp.addEntry(hicData);
		} catch (LDAPComponentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new UAComponentException(e);
		}
	}
	public void deleteLDAPEntry(Hashtable<String,String> ldapInfoHash) throws UAComponentException
	{
		hicData = createLdapXML(ldapInfoHash);
		
		try 
		{
			ldapComp = new LDAPComponent();
		
			ldapComp.deleteEntry(hicData);
		} catch (LDAPComponentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new UAComponentException(e);
		}
	}
	private IHICData createLdapXML( Hashtable<String,String> ldapInfoHash) throws UAComponentException
	{
		 //Hashtable<String,String> ldapInfoHash = null;
		 String infoKey = null;
		 String infoKeyValue = null;
		 String infoType = null;
		 String infoName = null;
		 String infoParentType = null;
		 String infoParentName = null;
		 String infoParentId = null;
		 String userLastName = null;
		 String userEmail = null;
		 String userTelepNo = null;
		 String userName = null;
		 String userPassword = null;
		 boolean isUser = false;
		 hicData = new HICData();
			 
		 
		 Document document = DocumentHelper.createDocument();
		 Element parentRoot = document.addElement("entity");
			//ldapInfoHash = ldapInfoList.get(listCounter);
			if(null == ldapInfoHash)
			{
				throw new UAComponentException("Incomplete LDAPinformation");
			}
			Enumeration<String> infoHashEnum = ldapInfoHash.keys();
			while(infoHashEnum.hasMoreElements())
			{
				infoKey = infoHashEnum.nextElement();
				if(null == infoKey)
				{
					throw new UAComponentException("Ldap Information is incomplete");
				}
				infoKeyValue = ldapInfoHash.get(infoKey);
				if(infoKey.equalsIgnoreCase("type"))
				{
					if(infoKeyValue.equalsIgnoreCase("user"))
					{
						isUser = true;
					}
					infoType = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("name"))
				{
					infoName = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("lastname"))
				{
					userLastName = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("mail"))
				{
					userEmail = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("telephonenumber"))
				{
					userTelepNo = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("username"))
				{
					userName = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("password"))
				{
					userPassword = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("parentname"))
				{
					infoParentName = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("parentType"))
				{
					infoParentType = infoKeyValue;
				}
				else
				if(infoKey.equalsIgnoreCase("parentId"))
				{
					infoParentId = infoKeyValue;
				}
			}
			Element typeEle = parentRoot.addElement("type");
			Element nameEle = parentRoot.addElement("name");
			nameEle.addText(infoName);
			if(isUser)
			{
				createUserNode(infoType, infoName, userLastName
						, userEmail, userTelepNo, userName , userPassword, parentRoot, typeEle);
				
			}
			else
			{
				typeEle.addText("unit");
			}
			Element parentEle = parentRoot.addElement("parent");
			if(null !=infoParentName && !infoParentName.equalsIgnoreCase(""))
			{
				Element parentEntity = parentEle.addElement("entity");
				Element parentEntityName = parentEntity.addElement("name");
				
				if(isUser)
				{	
					parentEntityName.addText(infoParentId);
					createUserChildNode(parentEntity , infoParentType , infoParentName);
				}
				else
				{
					parentEntityName.addText(infoParentName);
				}
			}
			hicData = new HICData();
			/*try {
				ldapComp = new LDAPComponent();
			} catch (LDAPComponentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			ldapData = new LdapData();
			ldapData.setLdapDoc(document);
			
			//UserAdminComponent.logger.log(0,document.asXML());
			hicData.setLdapData(ldapData);
			return hicData;
			/*try {
				ldapComp.addEntry(hicData);
			} catch (LDAPComponentException e) {
				// TODO Auto-generated catch block	
				//e.printStackTrace();
				throw new UAComponentException(e);
			}
		*/

	}
	private void createUserNode(String infoType, String infoName, String userLastName, String userEmail, String userTelepNo, 
			String infousername , String userPassword, Element parentRoot, Element typeEle)
	{
		typeEle.addText(infoType);
		if(null != userLastName)
		{
			Element lastName = parentRoot.addElement("lastname");
			lastName.addText(userLastName);
		}
		if(null != infoName)
		{
			Element displayName = parentRoot.addElement("displayname");
			displayName.addText(infoName);
		}
		if(null != infoName)
		{
			Element commonNameEle = parentRoot.addElement("commonname");
			commonNameEle.addText(infoName);
		}
		if(null != userEmail)
		{
			Element mailEle = parentRoot.addElement("mail");
			mailEle.addText(userEmail);
		}
		if(null != userTelepNo)
		{
			Element userTele = parentRoot.addElement("telephonenumber");
			userTele.addText(userTelepNo);
		}
		if(null != userPassword)
		{
			Element password = parentRoot.addElement("password");
			password.addText(userPassword);
		}
		if(null != infousername)
		{
			Element userName = parentRoot.addElement("username");
			userName.addText(infousername);
		}
	} 
	private Element createUserChildNode(Element parentEntity , String infoParentType , 
			String infoParentName) throws UAComponentException
	{
		//Element childEntity = null;
		infoParentName = fetchParentInfo(infoParentType , infoParentName);
		if(null == infoParentName)
		{
			Element childParent = parentEntity.addElement("parent");
			parentEntity = childParent;
		}
		else
		{
			Element childParent = parentEntity.addElement("parent");
			Element childParentEntity = childParent.addElement("entity");
			Element childParentName = childParentEntity.addElement("name");
			childParentName.addText(infoParentName);
			parentEntity = childParent;
	
		}
		return parentEntity;
	}
	private String fetchParentInfo(String type , String value) throws UAComponentException
	{
		String sqlQuery = null;
		NameQueryParameter Id = null;
		Group group = null;
		Organization organization = null;
		Object ldapRowData = null;
		String parentName = null;
		ArrayList<NameQueryParameter> listParam = new ArrayList<NameQueryParameter>();
		if(type.equalsIgnoreCase("organization"))
		{
			sqlQuery = SQLCommands.Find_Organization;
			Id = new NameQueryParameter(Constants.organizationId , value);
		}
		else
		{
			sqlQuery = SQLCommands.Select_Group;
			Id = new NameQueryParameter(Constants.groupId , value);
		}
		
		listParam.add(Id);
		List list = null;
		try 
		{
			list = ConnectionDatabase.GetInstanceOfDatabaseComponent().executeHSQLQueryWithNameParameter(sqlQuery,listParam);
		}
		catch (DBComponentException e) 
		{
			// TODO Auto-generated catch block
			throw new UAComponentException("Database Operation not completed properly ");
		}			
		if ((list == null) || (list.size()==0))
		{
			throw (new NoSuchOrganizationException("No organization/group with this id Found " + value));
		}	
		else 
		{
			for(Iterator ldapRow=list.iterator();ldapRow.hasNext();)
			{
				ldapRowData= ldapRow.next();
				//existingOrganization = (Organization)organizationRowData;
			}
			if(null == ldapRowData)
			{
				return null;
			}
			if(type.equalsIgnoreCase("organization"))
			{
				organization = (Organization)ldapRowData;
				parentName = organization.getCompanyId();
			}
			else
			{
				group = (Group) ldapRowData;
				parentName = group.getCompanyId();
			}
			
		}
		return parentName;
	}
	public void setLdapComp(LDAPComponent ldapComp) 
	{
		this.ldapComp = ldapComp;
	}
	
}

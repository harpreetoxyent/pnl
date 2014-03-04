package com.oxymedical.component.ldap.queryoperations;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPConstraints;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import com.novell.ldap.LDAPReferralException;
import com.novell.ldap.LDAPSearchResults;
import com.novell.ldap.util.Base64;
import com.oxymedical.component.ldap.IConnectionManager;
import com.oxymedical.component.ldap.IQueryManager;
import com.oxymedical.component.ldap.LDAPVO;
import com.oxymedical.component.ldap.ListLDAPVO;
import com.oxymedical.component.ldap.Role.RoleLdapVO;
import com.oxymedical.component.ldap.connection.ConnectionManager;
import com.oxymedical.component.ldap.constants.LDAPConstants;
import com.oxymedical.component.ldap.exception.LDAPComponentException;
import com.oxymedical.component.ldap.util.LDAPComponentUtility;



/**
 * The Query manager will receive the directory query and return the results accordingly.
 * It  is capable of handling both tree level (adding and deleting the directory)
 * and entry-level requests (modifying attributes)
 * 
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class QueryManager implements IQueryManager
{
	IConnectionManager connectionManager;
	AttributeOperations attributeOperations;
	
	/**
	 * Constructor of the Query Manager
	 */
	public QueryManager() {
		attributeOperations = new AttributeOperations();
		//connectionManager = new ConnectionManager();
	}

	
	public IConnectionManager getConnectionManager() {
		return connectionManager;
	}

	
	public void setConnectionManager(IConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	/**
	 * Adds an entry to ldap DS
	 * @param ldapVO
	 * @return boolean
	 */
	public boolean addEntry(LDAPVO ldapVO) throws LDAPComponentException
	{
		boolean result = false;
		
		if (null == ldapVO)
			 throw new LDAPComponentException();		
		try 
		{	
			LDAPEntry ldapEntry = add(ldapVO);
			if (null != ldapEntry){
				LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
				lc.add(ldapEntry);
				//LDAPComponent.logger.log(0,"Entry added in the Directory tree");
				//connectionManager.disconnect();
			}
			else
				throw new LDAPComponentException("Name is mandatory");
			
			result = true;
		} 
		catch(LDAPException ldapEx)
		{
			//LDAPComponent.logger.log(0,"ldapVO get type "+ ldapVO.getType());
			if (ldapEx.getMessage().equalsIgnoreCase("Entry Already Exists")){
				//LDAPComponent.logger.log(0,"Entry already exist in ldap server ");
				if ( ldapVO.getType().equalsIgnoreCase("user")){
				LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
				//LDAPComponent.logger.log(0,"ldap vo container is " +ldapVO.getContainerName() + "connection is alive in modify block" + lc.isConnectionAlive());
				modifyAttribute(ldapVO,lc);
				}
			}
			else
			{
				//LDAPComponent.logger.log(0,"In else block in modify ");
				throw new LDAPComponentException(ldapEx.getLocalizedMessage());
			}
			//LDAPComponent.logger.log(0,"******************    ENTRY MODIFIED IN THE LDAP SERVER      ***************");
		}
		finally
		{
			connectionManager.disconnect();
		}
		return result;
	}
	
	/**
	 * Creates an LDAP Entry object from LDAPVO
	 * @param ldapVO
	 * @return
	 * @throws LDAPComponentException 
	 */
	private LDAPEntry add(LDAPVO ldapVO) throws LDAPComponentException {
		 LDAPAttributeSet attributeSet = new LDAPAttributeSet();		 
		 boolean isUser = false;
		 boolean isRole = false;
		 String objectClass = "";
		 String name = ldapVO.getName();
		if (null == name) 
			 return null;
		 
		 String type = ldapVO.getType();
		if (type.equalsIgnoreCase(LDAPConstants.TYPE_USER)) {
			 objectClass = LDAPConstants.INETORGPERSON;	 
			 isUser = true;
		 }
		 else if (type.equalsIgnoreCase(LDAPConstants.TYPE_ROLE)){
			 objectClass = LDAPConstants.GROUPOFUNIQUENAMES;
			 isRole = true;
			 }
		 else
			 objectClass = LDAPConstants.ORGANIZATIONALUNIT;	
			String containerName;
		 	if (null!=ldapVO.getUserId())
		 		containerName = LDAPConstants.UID + "=" + ldapVO.getUserId() + ", " + ldapVO.getContainerName();
			else
				containerName = LDAPConstants.OU + "=" + name + ", " + ldapVO.getContainerName();		 
		 
		 	attributeSet.add( new LDAPAttribute(LDAPConstants.OBJECTCLASS, objectClass));		 
		 
		 if (isUser) {
			 if (null != ldapVO.getName() && !("".equals(ldapVO.getName())))
				 attributeSet.add( new LDAPAttribute(LDAPConstants.GIVENNAME, ldapVO.getName()));    
			 if (null != ldapVO.getLastName() && !("".equals(ldapVO.getLastName())))
				 attributeSet.add( new LDAPAttribute(LDAPConstants.SURNAME, ldapVO.getLastName()));       
			 if (null != ldapVO.getTelephoneNumber() && !("".equals(ldapVO.getTelephoneNumber())))
				 attributeSet.add( new LDAPAttribute(LDAPConstants.TELNUM, ldapVO.getTelephoneNumber())); 
			 if (null != ldapVO.getMail() && !("".equals(ldapVO.getMail())))
				 attributeSet.add( new LDAPAttribute(LDAPConstants.MAIL, ldapVO.getMail()));
			 if (null != ldapVO.getUserPassword() && !("".equals(ldapVO.getUserPassword())))
				 attributeSet.add( new LDAPAttribute(LDAPConstants.USERPASS, ldapVO.getUserPassword()));
			 if (null != ldapVO.getDescription() && !("".equals(ldapVO.getDescription())))
				 attributeSet.add( new LDAPAttribute(LDAPConstants.DESCRIPTION, ldapVO.getDescription()));
			 if (null!= ldapVO.getUserId() && !("".equals(ldapVO.getUserId())))
				 attributeSet.add(new LDAPAttribute(LDAPConstants.UID ,ldapVO.getUserId()));
			 if (null!= ldapVO.getUserRole() && !("".equals(ldapVO.getUserRole())))
			 {
				 LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
				 if ( ldapVO.getUserRole().equalsIgnoreCase(LDAPConstants.ADMINISTRATOR))
				 {
					 	Document adminDoc = LDAPComponentUtility.parseXMLToDocument(LDAPConstants.ADMIN_XML);
					 	RoleLdapVO ldapAdminVO = LDAPComponentUtility.parseRoleDocumentToLDAPVO(adminDoc);
					 	String uniqueMemberValue = LDAPConstants.UID + "=" + ldapVO.getUserId() + ", "+ ldapVO.getContainerName();
					 	try {
					 		lc.modify(ldapAdminVO.getContainerName(), new LDAPModification(
										LDAPModification.ADD, new LDAPAttribute( LDAPConstants.UNIQUEMEMBER,uniqueMemberValue)));
					 		connectionManager.disconnect();
					 	} catch (LDAPException e) {
							e.printStackTrace();
							throw new LDAPComponentException(e.getMessage());
						}
						//LDAPComponent.logger.log(0,"Adiministrator role added one more User");
				 }
				 else if ( ldapVO.getUserRole().equalsIgnoreCase(LDAPConstants.MANAGER))
				 {
						Document managerDoc = LDAPComponentUtility.parseXMLToDocument(LDAPConstants.MANAGER_XML);
						RoleLdapVO ldapAdminVO = LDAPComponentUtility.parseRoleDocumentToLDAPVO(managerDoc);
						String uniqueMemberValue = LDAPConstants.UID + "=" + ldapVO.getUserId() + ", "+ ldapVO.getContainerName();
					 	try {
					 		lc.modify(ldapAdminVO.getContainerName(), new LDAPModification(
										LDAPModification.ADD,new LDAPAttribute( LDAPConstants.UNIQUEMEMBER ,uniqueMemberValue)));
					 		connectionManager.disconnect();
					 	} catch (LDAPException e) {
							throw new LDAPComponentException(e.getMessage());
						}
						//LDAPComponent.logger.log(0,"Manager role added one more User");
				 }
				 else if ( ldapVO.getUserRole().equalsIgnoreCase(LDAPConstants.USER))
				 {
						Document userDoc = LDAPComponentUtility.parseXMLToDocument(LDAPConstants.USER_XML);
						RoleLdapVO ldapAdminVO = LDAPComponentUtility.parseRoleDocumentToLDAPVO(userDoc);
						String uniqueMemberValue = LDAPConstants.UID + "=" + ldapVO.getUserId() + ", "+ ldapVO.getContainerName();
					 	try {
					 		lc.modify(ldapAdminVO.getContainerName(), new LDAPModification(
										LDAPModification.ADD,new LDAPAttribute( LDAPConstants.UNIQUEMEMBER ,uniqueMemberValue)));
					 		connectionManager.disconnect();
					 	} catch (LDAPException e) {
							throw new LDAPComponentException(e.getMessage());
						}
						//LDAPComponent.logger.log(0,"User role added one more User");
				 }
			 }
		 }
		 if(isRole) {
			 if (null != ldapVO.getUniqueMember() && !("".equals(ldapVO.getUniqueMember())))
				 	attributeSet.add( new LDAPAttribute(LDAPConstants.UNIQUEMEMBER, ldapVO.getUniqueMember())); 
		 }
		 if (null != ldapVO.getCommonName() && !("".equals(ldapVO.getCommonName())))
		 		attributeSet.add( new LDAPAttribute(LDAPConstants.COMMONNAME, ldapVO.getCommonName())); 
		 String  dn  = containerName;
		 return new LDAPEntry( dn, attributeSet );
	}
	
	/**
	 * Deletes an LDAP Entry object from LDAP DS
	 * @param ldapVO
	 * @return
	 */
	public boolean deleteEntry(LDAPVO ldapVO) throws LDAPComponentException
	 {
		boolean result = false;
		try 
		{
			String containerName;
		 	if (null!=ldapVO.getUserId())
		 		containerName = LDAPConstants.UID + "=" + ldapVO.getUserId() + ", " + ldapVO.getContainerName();
			else
				containerName = LDAPConstants.OU + "=" + ldapVO.getName() + ", " + ldapVO.getContainerName();	
		 	LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
		 	lc.delete(containerName);
		 	connectionManager.disconnect();
			result = true;
		} catch(LDAPException ldapEx) {
			throw new LDAPComponentException(ldapEx.getMessage());
		}
		return result;
	}
	
	/**
	 * Deletes an attribute of an user entry
	 * @param ldapVO
	 * @return
	 */
	public boolean deleteAttribute(LDAPVO ldapVO) throws LDAPComponentException {
		boolean result = false;
		try {
			String containerName = LDAPConstants.OU + "=" + ldapVO.getName() + ", " + ldapVO.getContainerName();			
			LDAPModification[] modsDel = attributeOperations.deleteAttribute(ldapVO);
			LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			lc.modify(containerName, modsDel);
			connectionManager.disconnect();
			result = true;
		} catch(LDAPException ldapEx) {
			throw new LDAPComponentException(ldapEx.getMessage());
		}
		return result;
	}
	/**
	 * Modifies an attribute of an user entry
	 * @param ldapVO
	 * @return
	 */
	public boolean modifyAttribute(LDAPVO ldapVO , LDAPConnection lc) throws LDAPComponentException {
		boolean result = false;
		try {
			String containerName = LDAPConstants.OU + "=" + ldapVO.getName() + ", " + ldapVO.getContainerName();
			LDAPModification[] modsMod = attributeOperations.modifyAttribute(ldapVO);
			//lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			lc.modify(containerName, modsMod);
			//connectionManager.disconnect();
			result = true;
		} catch(LDAPException ldapEx) {
			ldapEx.printStackTrace();
			throw new LDAPComponentException(ldapEx.getMessage());
		}
		finally
		{
			connectionManager.disconnect();
		}
		return result;
	}
	
	/**
	 * Modifies an attribute of an user entry
	 * @param ldapVO
	 * @return
	 */
	public boolean modifyAttribute(LDAPVO ldapVO) throws LDAPComponentException {
		boolean result = false;
		try {
			String containerName = LDAPConstants.OU + "=" + ldapVO.getName() + ", " + ldapVO.getContainerName();
			LDAPModification[] modsMod = attributeOperations.modifyAttribute(ldapVO);
			LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			lc.modify(containerName, modsMod);
			//connectionManager.disconnect();
			result = true;
		} catch(LDAPException ldapEx) {
			ldapEx.printStackTrace();
			throw new LDAPComponentException(ldapEx.getMessage());
		}
		finally
		{
			connectionManager.disconnect();
		}
		return result;
	}
	
	/**
	 * Adds an attribute of an user entry
	 * @param ldapVO
	 * @return
	 */
	public boolean addAttribute(LDAPVO ldapVO) throws LDAPComponentException {
		boolean result = false;
		try {
			String containerName = LDAPConstants.OU + "=" + ldapVO.getName() + ", " + ldapVO.getContainerName();
			LDAPModification[] modsAdd = attributeOperations.addAttribute(ldapVO);
			LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			lc.modify(containerName, modsAdd);
			connectionManager.disconnect();
			result = true;
		} catch(LDAPException ldapEx) {
			throw new LDAPComponentException(ldapEx.getMessage());
		}
		return result;
	}
	
	/**
	 * Export entries to a ldif file
	 * @param ldapVO
	 * @param fileName
	 * @return
	 * @throws LDAPComponentException
	 */
	public boolean exportToLdif(LDAPVO ldapVO, String fileName) throws LDAPComponentException {
		boolean result = false;
		try {
			String containerName = LDAPConstants.OU + "=" + ldapVO.getName() + ", " + ldapVO.getContainerName();
			ExportToLdif exportToLdif = new ExportToLdif();
			LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			exportToLdif.export(fileName, lc, containerName);
			connectionManager.disconnect();
			result = true;
		} catch(LDAPComponentException ldapEx) {
			throw ldapEx;
		}
		return result;
	}
	
	/**
	 * Import entries to a ldif file
	 * @param ldapVO
	 * @param fileName
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean importToLdif(String fileName) throws LDAPComponentException {
		boolean result = false;
		try {
			ImportToLdif importToLdif = new ImportToLdif();
			LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			importToLdif.importLdif(fileName, lc);
			connectionManager.disconnect();
			result = true;
		} catch(LDAPComponentException ldapEx) {
			throw ldapEx;
		}
		return result;
	}
	
	/**
	 * Searches an entry in ldap DS
	 * 
	 * Search Base
	 * 	 Search base is the container name where to search in the tree
	 * 
	 * Scope  Description  
	 *	Base - Only the base entry  (SCOPE_BASE)
	 *	One-Level - One level below, all direct children of the base entry (SCOPE_ONE)
	 *	Subtree -  The whole subtree below the base, including the base entry (SCOPE_SUB)
	 *
	 * Filter
	 * 	 Returns all the object classes (ObjectClass=*) (used the default)
	 * 
	 * @param ldapVO
	 * @return Document
	 * @throws LDAPComponentException
	 */
	public Document search(LDAPVO ldapVO) throws LDAPComponentException {
		ArrayList<String> valueList = null;
		Hashtable<String, ArrayList<String>> searchResultTable = null;
		String entry = null;
		String parentVal ="";
		String getXmlValue = "";
		Document doc = null;
		int searchScope = LDAPConnection.SCOPE_BASE;  
		//boolean attributeOnly = true;
	    //String attrs[] = {LDAPConnection.NO_ATTRS};                
	    String searchBase = ldapVO.getContainerName();
	   // LDAPComponent.logger.log(0,"searchBase = " +searchBase);
	    String searchFilter = LDAPConstants.OBJECT_CLASS;
	    
         try {	
        	LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			LDAPSearchResults searchResults = lc.search(
							searchBase,      // container to search
	                        searchScope,     // search scope
	                        searchFilter,    // search filter
	                        null,          // return all attributes
	                        false);        // return attrs and values			
			
			 /* To print out the search results,

             *   -- The first while loop goes through all the entries

             *   -- The second while loop goes through all the attributes

             *   -- The third while loop goes through all the attribute values

             */

            while ( searchResults.hasMore()) {
            	searchResultTable = new Hashtable<String, ArrayList<String>>();
                LDAPEntry ldapEntry = null;
                int endTag = 0;
                String value1 = "";
                ldapEntry = searchResults.next();  
               // LDAPComponent.logger.log(0,"\n" + ldapEntry.getDN());
                // LDAPComponent.logger.log(0,"  Attributes: ");
                LDAPAttributeSet attributeSet = ldapEntry.getAttributeSet();
                Iterator allAttributes = attributeSet.iterator();
               
                while(allAttributes.hasNext()) {                	
                    LDAPAttribute attribute = (LDAPAttribute)allAttributes.next();
                    String attributeName = attribute.getName();
                    // LDAPComponent.logger.log(0,"  Attribute Name   " + attributeName);
                    Enumeration allValues = attribute.getStringValues(); 
                    
                    if( allValues != null) {
                    	valueList = new ArrayList<String>();
                    	
                        while(allValues.hasMoreElements()) {
                            String value = (String) allValues.nextElement();
                            
                            if (Base64.isLDIFSafe(value)) {
                                // is printable 
                            //	LDAPComponent.logger.log(0," first value"+value);
                            }
                            else {
                                // base64 encode and then print out
                                value = Base64.encode(value.getBytes()); 
                              //  LDAPComponent.logger.log(0," second value  "+value);
                            }
                          // LDAPComponent.logger.log(0,"  value    " + value);
                            valueList.add(value);
                        } // end of while                        
                    } // end of values if
                    searchResultTable.put(attributeName, valueList);
                 } // end of attributes while
                ListLDAPVO listLdapVO = convertToListLDAPVO(searchResultTable);
                getXmlValue = getXmlDocument(listLdapVO);
                entry = ldapEntry.getDN();
               // LDAPComponent.logger.log(0,"entry is" + entry);
                entry = entry.replaceAll(LDAPConstants.OU_EQUAL, " ");
               // LDAPComponent.logger.log(0," Replace entry" + entry);
                String[] parentValue = entry.split(",");
                for ( int i=1; i< parentValue.length-1; i++)
                {
                	 //	LDAPComponent.logger.log(0, "parent value is "+parentValue[i]);
                	parentVal = parentVal + LDAPComponentUtility.startParentXml(parentValue[i]);
                	endTag++;
                }
                // LDAPComponent.logger.log(0,"parent xml value is "+parentVal);
                if (endTag != 0)
                	value1 = LDAPComponentUtility.endParentXml(endTag);
                	String parentXmlValue =  LDAPConstants.START_ENTITY_TAG + "\n" + getXmlValue  
                	                      + parentVal + value1 + LDAPConstants.END_ENTITY_TAG;
                    //LDAPComponent.logger.log(0,""+parentXmlValue);
                    doc = DocumentHelper.parseText(parentXmlValue);
            }
            connectionManager.disconnect();
        } catch (LDAPException e) {
        	throw new LDAPComponentException(e.getMessage());
		} catch (DocumentException e) {
			throw new LDAPComponentException(e.getMessage());
		} 
	  return doc;
	}
	
	/**
	 * This is a private function to convert from Hashtable to the listldapVO
	 * @param valueHash
	 * @return ldapVO
	 */
	private ListLDAPVO convertToListLDAPVO(Hashtable<String, ArrayList<String>> searchResultTable){
		ListLDAPVO ldapVO = new ListLDAPVO();
		ldapVO.setOu(searchResultTable.get(LDAPConstants.OU));
		//ldapVO.setFirstname(searchResultTable.get("firstname"));
		ldapVO.setName(searchResultTable.get(LDAPConstants.ENTITY_NAME));
		ldapVO.setGivenname(searchResultTable.get(LDAPConstants.GIVENNAME));
		ldapVO.setSn(searchResultTable.get(LDAPConstants.SURNAME));
		ldapVO.setDescription(searchResultTable.get(LDAPConstants.DESCRIPTION));
		ldapVO.setCn(searchResultTable.get(LDAPConstants.COMMONNAME));
		//ldapVO.setUserPassword(valueHash.get("password"));
		//ldapVO.setDesignation(valueHash.get("designation"));		
		ldapVO.setMail(searchResultTable.get(LDAPConstants.ENTITY_MAIL));
		//ldapVO.setMobileNumber(valueHash.get("telephonenumber"));
		//ldapVO.setCompanyName(valueHash.get("companyName"));
		//ldapVO.setGroupName(valueHash.get("groupName"));
		//ldapVO.setOrganizationName(valueHash.get("organizationName"));
		ldapVO.setObjectClass(searchResultTable.get(LDAPConstants.OBJECTCLASS));
		return ldapVO;
	}

	
	/**
	 * This is a private function used to make the xml as String  
	 * @param listhash
	 * @return String
	 */
	private static String getXmlDocument(ListLDAPVO listhash)
	{
		String xmlValue = null;
		StringBuffer strValue = new StringBuffer();
		//ListLDAPVO listhash = new ListLDAPVO();
		 if(null != listhash.getCn() && !("".equals(listhash.getCn())))
			strValue.append(LDAPConstants.START_TAG + LDAPConstants.ENTITY_COMMONNAME + LDAPConstants.END_TAG 
					+ listhash.getCn() + LDAPConstants.START_TAG_SLACE + LDAPConstants.ENTITY_COMMONNAME  + LDAPConstants.END_TAG + "\n");
		 if (null != listhash.getGivenname() && !("".equals(listhash.getGivenname())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.GIVENNAME + LDAPConstants.END_TAG 
					+ listhash.getGivenname() + LDAPConstants.START_TAG_SLACE + LDAPConstants.GIVENNAME + LDAPConstants.END_TAG + "\n");  
		 if (null != listhash.getName() && !("".equals(listhash.getName())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.ENTITY_NAME + LDAPConstants.END_TAG 
					+ listhash.getName() + LDAPConstants.START_TAG_SLACE + LDAPConstants.ENTITY_NAME + LDAPConstants.END_TAG + "\n");    
		 if (null != listhash.getSn() && !("".equals(listhash.getSn())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.ENTITY_LASTNAME + LDAPConstants.END_TAG 
					+ listhash.getSn() + LDAPConstants.START_TAG_SLACE + LDAPConstants.ENTITY_LASTNAME + LDAPConstants.END_TAG + "\n");       
		 if (null != listhash.getTelephonenumber() && !("".equals(listhash.getTelephonenumber())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.TELNUM + LDAPConstants.END_TAG 
					+ listhash.getTelephonenumber() + LDAPConstants.START_TAG_SLACE + LDAPConstants.TELNUM + LDAPConstants.END_TAG + "\n");
		 if (null != listhash.getMail() && !("".equals(listhash.getMail())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.MAIL + LDAPConstants.END_TAG 
					+ listhash.getMail() + LDAPConstants.START_TAG_SLACE + LDAPConstants.MAIL + LDAPConstants.END_TAG + "\n");
		 if (null != listhash.getOu() && !("".equals(listhash.getOu())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.ENTITY_NAME + LDAPConstants.END_TAG 
					+ listhash.getOu() + LDAPConstants.START_TAG_SLACE + LDAPConstants.ENTITY_NAME + LDAPConstants.END_TAG + "\n");
		 if (null != listhash.getDescription() && !("".equals(listhash.getDescription())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.DESCRIPTION + LDAPConstants.END_TAG + listhash.getDescription() + LDAPConstants.START_TAG_SLACE + LDAPConstants.DESCRIPTION + LDAPConstants.END_TAG + "\n");
		
		 if (null != listhash.getObjectClass() && !("".equals(listhash.getObjectClass())))
			 strValue.append(LDAPConstants.START_TAG + LDAPConstants.OBJECTCLASS + LDAPConstants.END_TAG + listhash.getObjectClass() + LDAPConstants.START_TAG_SLACE + LDAPConstants.OBJECTCLASS + LDAPConstants.END_TAG + "\n");
		return strValue.toString();
	}
	
	/**
	 * Lists an entry in ldap DS
	 * 
	 * Search Base
	 * 	 Search base is the container name where to list in the tree
	 * 
	 * Scope  Description  
	 *	Base - Only the base entry  (SCOPE_BASE)
	 *	One-Level - One level below, all direct children of the base entry (SCOPE_ONE)
	 *	Subtree -  The whole subtree below the base, including the base entry (SCOPE_SUB)
	 *
	 * Filter
	 * 	 Returns all the object classes (ObjectClass=*) (used the default)
	 * 
	 * @param ldapVO
	 * @return Document
	 * @throws LDAPComponentException
	 */
	
	public Document search(LDAPConnection ldapCon, String searchBase, int searchScope, String searchFilter, ArrayList<String> requiredEntityProps) throws LDAPComponentException{
		StringBuffer strXml = new StringBuffer();
		Document doc = null;
        try{
        	LDAPSearchResults searchResults = ldapCon.search(searchBase, searchScope, searchFilter, null, false);
        	strXml.append(LDAPConstants.START_ROOT_TAG);
        	int resultCount = 0;
        	while(searchResults.hasMore()){
                LDAPEntry ldapEntry = null;
                try{
                	if(resultCount == 1000){
                		break;
                	}
                	ldapEntry = searchResults.next();
                	resultCount += 1;
                }catch(LDAPReferralException refEx){}
                if(ldapEntry != null){
                	strXml.append(LDAPConstants.START_ENTITY_TAG);
                	LDAPAttributeSet attributeSet = ldapEntry.getAttributeSet();
                	Iterator allAttributes = attributeSet.iterator();
                	while(allAttributes.hasNext()){                	
	                    LDAPAttribute attribute = (LDAPAttribute)allAttributes.next();
	                    String attributeName = attribute.getName();
	                    if(requiredEntityProps == null || (requiredEntityProps != null && requiredEntityProps.contains(attributeName))){
		                    strXml.append(LDAPConstants.START_TAG + attributeName + LDAPConstants.END_TAG);
		                    Enumeration allValues = attribute.getStringValues(); 
		                    
		                    if(allValues != null){
		                        while(allValues.hasMoreElements()){
		                        	String value = (String) allValues.nextElement();
		                        	if(!Base64.isLDIFSafe(value)){
		                            	value = Base64.encode(value.getBytes());
		                            }
		                        	strXml.append(LDAPConstants.START_VALUE_TAG + value + LDAPConstants.END_VALUE_TAG);
		                        }                        
		                    }
		                    strXml.append(LDAPConstants.START_TAG_SLACE + attributeName + LDAPConstants.END_TAG);
	                    }
                	}
                	strXml.append(LDAPConstants.END_ENTITY_TAG);
                }
            }
        	if(strXml.length() > LDAPConstants.START_ROOT_TAG.length()){
        		strXml.append(LDAPConstants.END_ROOT_TAG);
        		//System.out.println("raw xml: " + strXml.toString());
        		doc = DocumentHelper.parseText(strXml.toString());
        	}
        }catch(LDAPException e){
        	throw new LDAPComponentException(e.getMessage());
		}catch(DocumentException e){
			throw new LDAPComponentException(e.getMessage());
		} 
        return doc;
	}
	
	/**
	 * Lists an entry in ldap DS
	 * 
	 * Search Base
	 * 	 Search base is the container name where to list in the tree
	 * 
	 * Scope  Description  
	 *	Base - Only the base entry  (SCOPE_BASE)
	 *	One-Level - One level below, all direct children of the base entry (SCOPE_ONE)
	 *	Subtree -  The whole subtree below the base, including the base entry (SCOPE_SUB)
	 *
	 * Filter
	 * 	 Returns all the object classes (ObjectClass=*) (used the default)
	 * 
	 * @param ldapVO
	 * @return Document
	 * @throws LDAPComponentException
	 */
	
	public Document list(LDAPVO ldapVO) throws LDAPComponentException {
		ArrayList<String> valueList = null;
		Hashtable<String, ArrayList<String>> searchResultTable = null;
		String entry = null;
		String getXmlValue = "";
		String parentXmlValue ="";
		Document doc = null;
		int searchScope = LDAPConnection.SCOPE_SUB;  
		//boolean attributeOnly = true;
	    //String attrs[] = {LDAPConnection.NO_ATTRS};                
	    String searchBase = ldapVO.getContainerName();
	   // LDAPComponent.logger.log(0,"searchBase = " +searchBase);
	   
	    String searchFilter = LDAPConstants.OBJECT_CLASS;
        
        try {	
        	LDAPConnection lc = connectionManager.connect(LDAPConstants.LDAP_USERNAME, LDAPConstants.LDAP_PASSWORD);
			LDAPSearchResults searchResults = lc.search(
							searchBase,      // container to search
	                        searchScope,     // search scope
	                        searchFilter,    // search filter
	                        null,          // return all attributes
	                        false);        // return attrs and values			
			
			 /* To print out the search results,

             *   -- The first while loop goes through all the entries

             *   -- The second while loop goes through all the attributes

             *   -- The third while loop goes through all the attribute values

             */

            while ( searchResults.hasMore()) {
            	searchResultTable = new Hashtable<String, ArrayList<String>>();
                LDAPEntry ldapEntry = null;
                int endTag = 0;
                String parentVal ="";
                ldapEntry = searchResults.next();  
                //LDAPComponent.logger.log(0,"\n" + ldapEntry.getDN());
                // LDAPComponent.logger.log(0,"  Attributes: ");
                LDAPAttributeSet attributeSet = ldapEntry.getAttributeSet();
                Iterator allAttributes = attributeSet.iterator();
               
                while(allAttributes.hasNext()) {                	
                    LDAPAttribute attribute = (LDAPAttribute)allAttributes.next();
                    String attributeName = attribute.getName();
                  // LDAPComponent.logger.log(0,"  Attribute Name   " + attributeName);
                    Enumeration allValues = attribute.getStringValues(); 
                    
                    if( allValues != null) {
                    	valueList = new ArrayList<String>();
                    	
                        while(allValues.hasMoreElements()) {
                            String value = (String) allValues.nextElement();
                            
                            if (Base64.isLDIFSafe(value)) {
                                // is printable 
                            	//LDAPComponent.logger.log(0," first value"+value);
                            }
                            else {
                                // base64 encode and then print out
                                value = Base64.encode(value.getBytes()); 
                              //  LDAPComponent.logger.log(0," second value  "+value);
                            }
                          // LDAPComponent.logger.log(0,"  value    " + value);
                            valueList.add(value);
                        } // end of while                        
                    } // end of values if
                    searchResultTable.put(attributeName, valueList);
                 } // end of attributes while
                ListLDAPVO listLdapVO = convertToListLDAPVO(searchResultTable);
                getXmlValue = getXmlDocument(listLdapVO);
                entry = ldapEntry.getDN();
               // LDAPComponent.logger.log(0,"entry is" + entry);
                entry = entry.replaceAll(LDAPConstants.OU_EQUAL, " ");
               // LDAPComponent.logger.log(0," Replace entry" + entry);
                String[] parentValue = entry.split(",");
                for ( int i=1; i< parentValue.length-1; i++)
                {
                //	LDAPComponent.logger.log(0, "parent value is "+parentValue[i]);
                	parentVal = parentVal + LDAPComponentUtility.startParentXml(parentValue[i]);
                	endTag++;
                }
               // LDAPComponent.logger.log(0,"parent xml value is "+parentVal);
                String value1 = "";
                if (endTag != 0)
                	value1 = LDAPComponentUtility.endParentXml(endTag);
                parentXmlValue =  LDAPConstants.START_ENTITY_TAG + "\n" + getXmlValue  
                	                      + parentVal + value1 + LDAPConstants.END_ENTITY_TAG;
                parentXmlValue = "\n" + parentXmlValue;
                doc = DocumentHelper.parseText(parentXmlValue);
                //LDAPComponent.logger.log(0,""+parentXmlValue);
            }
            connectionManager.disconnect();          
        } catch (LDAPException e) {
        	throw new LDAPComponentException(e.getMessage());
		} catch (DocumentException e) {
			throw new LDAPComponentException(e.getMessage());
		} 
		
        return doc;
	}
}

package com.oxymedical.component.ldap;



import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPConstraints;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPReferralException;
import com.novell.ldap.LDAPSearchResults;
import com.novell.ldap.util.Base64;
import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.ldap.connection.ConnectionManager;
import com.oxymedical.component.ldap.constants.LDAPConstants;
import com.oxymedical.component.ldap.exception.LDAPComponentException;
import com.oxymedical.component.ldap.queryoperations.QueryManager;
import com.oxymedical.component.ldap.util.LDAPComponentUtility;
import com.oxymedical.core.commonData.HICData;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.constants.CoreConstants;
import com.oxymedical.core.ldapData.ILdapData;
import com.oxymedical.core.ldapData.LdapData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;
import com.oxymedical.core.propertyUtil.PropertyUtil;

/**
 * LDAP Component is used to communication with LDAP Directory Servers 
 * using the LDAP (Lightweight Directory Access) Protocol
 * 
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class LDAPComponent implements ILDAPComponent, IComponent
{
	IConnectionManager connectionManager = null;	
	IQueryManager queryManager;	
	
	/**
	 * Constructor of the LDAPComponent
	 * @throws LDAPComponentException 
	 */
	public LDAPComponent() {
		connectionManager = new ConnectionManager();
		queryManager = new QueryManager();
		}
	
	/**
	 * To establish the connection from the LDAP DS 
	 * @return connectionManager
	 */
	public IConnectionManager getConnectionManager()
	{
		return connectionManager;
	}
	
	public void stop() 
	{
	}
	
	
	/**
	 * Adds an entry to LDAP DS 
	 * @param xmlDocument
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean addEntry(IHICData hicData) throws LDAPComponentException {
		LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		return queryManager.addEntry(ldapVO);
	}
	
	/**
	 * Deletes an entry from LDAP DS
	 * @param xmlDocument
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean deleteEntry(IHICData hicData) throws LDAPComponentException {		
		LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		return queryManager.deleteEntry(ldapVO);
	}

	/**
	 * Adds an attribute to an entry in ldap DS
	 * @param xmlDocument
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean addAttribute(IHICData hicData) throws LDAPComponentException {
		LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		
		return queryManager.addAttribute(ldapVO);	
	}

	/**
	 * Delete an attribute of an entry in ldap DS
	 * @param valueHash
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean deleteAttribute(IHICData hicData) throws LDAPComponentException {		
		LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		return queryManager.deleteAttribute(ldapVO);			
	}

	/**
	 * Modify an attribute to an entry in ldap DS
	 * @param xmlDocument
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean modifyAttribute(IHICData hicData) throws LDAPComponentException {		
		LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		return queryManager.modifyAttribute(ldapVO);			
	}
	
	/**
	 * Lists entries in DS
	 * @param valueHash
	 * @return Document
	 * @throws LDAPComponentException
	 */
	public Document list(IHICData hicData) throws LDAPComponentException {
		LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		return queryManager.list(ldapVO);
	}
	
	/**
	 * Export entries from DS to a file and saves the file in the form of ldif
	 * @param xmlDocument
	 * @param fileName
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean exportToLdif(IHICData hicData, String fileName) throws LDAPComponentException {
		LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		return queryManager.exportToLdif(ldapVO, fileName);
	}
	
	/**
	 * Import entries from LDIF file to DS
	 * @param fileName
	 * @throws LDAPComponentException
	 */
	public boolean importToLdif(String fileName) throws LDAPComponentException {
		return queryManager.importToLdif(fileName);
	}
	
	/**
	 * Authenticates a user against an LDAP directory 
	 * @param hicData
	 * @return IHICData
	 * @throws LDAPComponentException
	 */
	@EventSubscriber(topic = "authenticateUserInLDAP")
	public IHICData authenticateUserInLDAP(IHICData hicData){
		try{
			System.out.println("Start: authenticateUserInLDAP");
			if(hicData.getLdapData() == null){
				System.out.println("LDAP Data is null in HIC Data");
			}else{
				ConnectionManager conMgr = new ConnectionManager();
				LDAPConnection ldapCon = getLDAPConnection(hicData, conMgr);
				if(ldapCon != null){
					hicData.getLdapData().addAttribute(CoreConstants.UserAuthenticatedInLDAP_KEY, "true");
					if(ldapCon.isConnected()){
						ldapCon.disconnect();
					}
				}else{
					hicData.getLdapData().addAttribute(CoreConstants.UserAuthenticatedInLDAP_KEY, "false");
				}
			}
			System.out.println("End: authenticateUserInLDAP");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return hicData;
	}
	
	/**
	 * Search for an entry in the LDAP Directory
	 * @param hicData
	 * @return IHICData
	 * @throws LDAPComponentException
	 */
	@SuppressWarnings("unchecked")
	@EventSubscriber(topic = "searchInLDAP")
	public IHICData search(IHICData hicData) throws LDAPComponentException {
		//LDAPVO ldapVO = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
		//return queryManager.search(ldapVO);
		try{
			System.out.println("Start: searchInLDAP");
			if(hicData.getLdapData() == null){
				System.out.println("LDAP Data is null in HIC Data");
			}else{
				ConnectionManager conMgr = new ConnectionManager();
				
				LDAPConnection ldapCon = getLDAPConnection(hicData, conMgr);
				
				if(ldapCon != null){
					String searchBase = getSearchBase(hicData);//"DC=spil,DC=com";
					int searchScope = getSearchScope(hicData);
					String searchFilter = getSearchFilter(hicData);//"(&(objectClass=user)(name=vinayak*))";
					
					ArrayList<String> reqProps = null;//new ArrayList<String>();
					if(hicData.getLdapData().getAttributes() != null){
						reqProps = (ArrayList<String>)hicData.getLdapData().getAttributes().get(CoreConstants.LDAP_SEARCH_REQ_PROPS_KEY);
					}
					
					QueryManager qm = new QueryManager();
					Document resultDoc = qm.search(ldapCon, searchBase, searchScope, searchFilter, reqProps);
	
					//if(resultDoc != null){
						//System.out.println("xml : " + resultDoc.asXML());
					//}
					
					hicData.getLdapData().setLdapDoc(resultDoc);
					
					if(resultDoc != null){
						hicData.getLdapData().addAttribute("LdapDocMap", getHashTableFromDoc(resultDoc));
					}
					
					if(ldapCon.isConnected()){
						conMgr.disconnect();
					}
				}
			}
			System.out.println("End: searchInLDAP");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return hicData;
	}
	
	private LDAPConnection getLDAPConnection(IHICData hicData, ConnectionManager conMgr){
		LDAPConnection ldapCon = null;
		
		String ldap_uname = getUserName(hicData);
		String ldap_pwd = getPassword(hicData);
		String ldap_uid_suffix = getUidSuffix(hicData);
		if(ldap_uid_suffix != null){
			ldap_uname = ldap_uname + ldap_uid_suffix;
		}
		String ldap_url = getServerURL(hicData);
		String ldap_port = getServerPort(hicData);
		boolean useSSL = getUseSSL(hicData);
		
		try{
			ldapCon = conMgr.connect(ldap_uname, ldap_pwd, useSSL, ldap_url, ldap_port);
		}catch(LDAPComponentException ldapEx){
			System.out.println("LDAP Component Exception: " + ldapEx.getMessage());
		}
		
		return ldapCon;
	}
	
	private String getUidSuffix(IHICData hicData){
		String retVal = "";
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getUidSuffix();
			if(retVal == null || retVal.length() == 0){
				retVal = PropertyUtil.setUpProperties(LDAPConstants.UID_SUFFIX_KEY);
				if(retVal == null || retVal.length() == 0){
					System.out.println("LDAP uid suffix not found in LDAP Data or setup.properties : " + LDAPConstants.UID_SUFFIX_KEY);
				}
			}
		}
		return retVal;
	}
	private String getUserName(IHICData hicData){
		String retVal = "";
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getUserName();
			if(retVal == null || retVal.length() == 0){
				retVal = PropertyUtil.setUpProperties(LDAPConstants.USER_ID_KEY);
				if(retVal == null || retVal.length() == 0){
					System.out.println("LDAP username not found in LDAP Data or setup.properties : " + LDAPConstants.USER_ID_KEY);
				}
			}
		}
		return retVal;
	}
	private String getPassword(IHICData hicData){
		String retVal = "";
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getPassword();
			if(retVal == null || retVal.length() == 0){
				retVal = PropertyUtil.setUpProperties(LDAPConstants.PASSWORD_KEY);
				if(retVal == null || retVal.length() == 0){
					System.out.println("LDAP password not found in LDAP Data or setup.properties : " + LDAPConstants.PASSWORD_KEY);
				}
			}
		}
		return retVal;
	}
	private String getServerURL(IHICData hicData){
		String retVal = "";
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getServerUrl();
			if(retVal == null || retVal.length() == 0){
				retVal = PropertyUtil.setUpProperties(LDAPConstants.SERVER_ADDRESS_KEY);
				if(retVal == null || retVal.length() == 0){
					System.out.println("LDAP Server URL not found in LDAP Data or setup.properties : " + LDAPConstants.SERVER_ADDRESS_KEY);
				}
			}
		}
		return retVal;
	}
	private String getServerPort(IHICData hicData){
		String retVal = "";
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getPort();
			if(retVal == null || retVal.length() == 0){
				retVal = PropertyUtil.setUpProperties(LDAPConstants.PORT_FOR_AUTH_KEY);
				if(retVal == null || retVal.length() == 0){
					System.out.println("LDAP Server Port not found in LDAP Data or setup.properties : " + LDAPConstants.PORT_FOR_AUTH_KEY);
				}
			}
		}
		return retVal;
	}
	private boolean getUseSSL(IHICData hicData){
		boolean retVal = false;
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getUseSSL();
		}
		return retVal;
	}
	private String getSearchBase(IHICData hicData){
		String retVal = "";
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getSearchBase();
			if(retVal == null || retVal.length() == 0){
				retVal = PropertyUtil.setUpProperties(LDAPConstants.SEARCH_BASE_KEY);
				if(retVal == null || retVal.length() == 0){
					System.out.println("LDAP SearchBase not found in LDAP Data or setup.properties : " + LDAPConstants.SEARCH_BASE_KEY);
					System.out.println("Using default value : " + LDAPConstants.SEARCH_BASE);
					retVal = LDAPConstants.SEARCH_BASE;
				}
			}
		}
		return retVal;
	}
	private String getSearchFilter(IHICData hicData){
		String retVal = "";
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getSearchFilter();
			if(retVal == null || retVal.length() == 0){
				retVal = PropertyUtil.setUpProperties(LDAPConstants.SEARCH_FILTER_KEY);
				if(retVal == null || retVal.length() == 0){
					System.out.println("LDAP SearchFilter not found in LDAP Data or setup.properties : " + LDAPConstants.SEARCH_FILTER_KEY);
					System.out.println("Using default value : " + LDAPConstants.SEARCH_FILTER);
					retVal = LDAPConstants.SEARCH_FILTER;
				}
			}
		}
		return retVal;
	}
	private int getSearchScope(IHICData hicData){
		int retVal = LDAPConnection.SCOPE_SUB;
		if(hicData.getLdapData() == null){
			System.out.println("LDAP Data is null in HIC Data");
		}else{
			retVal = hicData.getLdapData().getSearchScope();
		}
		return retVal;
	}
	
	@SuppressWarnings("unchecked")
	private Hashtable<String, Hashtable<String, Object>> getHashTableFromDoc(Document doc){
		String entityNodeName = "entity";
		String attrValueNodeName = "value";
		Hashtable <String, Hashtable<String, Object>> resultMap = new Hashtable<String, Hashtable<String, Object>>();
		List<Node> entityNodes = doc.selectNodes("//" + entityNodeName);
		if(entityNodes.size() > 0){
			int entityCounter = 1;
			//iterating through all entity nodes (users)
			for(Iterator<Node> itrEntity = entityNodes.iterator(); itrEntity.hasNext();){
				Hashtable<String, Object> entityMap = new Hashtable<String, Object>();
				Node entityNode = itrEntity.next();
				String resultMapKey = entityNodeName + entityCounter;
				List<Node> attrNodes = entityNode.selectNodes("child::*");
				//iterating through all entity properties (users properties)
				for(Iterator<Node> itrAttr = attrNodes.iterator(); itrAttr.hasNext();){
					Node attrNode = itrAttr.next();
					List<Node> attrValueNodes = attrNode.selectNodes("child::" + attrValueNodeName);
					ArrayList<String> attrValue = new ArrayList<String>();
					//iterating through all entity properties (users properties)
					for(Iterator<Node> itrAttrValue = attrValueNodes.iterator(); itrAttrValue.hasNext();){
						Node attrValueNode = itrAttrValue.next();
						attrValue.add(attrValueNode.getText());
					}
					if(attrValue.size() == 1){
						entityMap.put(attrNode.getName(), attrValue.get(0));
					}else{
						entityMap.put(attrNode.getName(), attrValue);
					}
				}
				resultMap.put(resultMapKey, entityMap);
				entityCounter += 1;
			}
		}
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		System.out.println("Start");
		try{
			ConnectionManager conMgr = new ConnectionManager();
			String ldap_uname = "alfresco@spil.com";
			String ldap_pwd = "Password1"; 
			String ldap_url = "jbndc01.spil.com";
			String ldap_port = "389";
			boolean useSSL = false;
			
			String searchBase = "DC=spil,DC=com";
			int searchScope = LDAPConnection.SCOPE_SUB;
			String searchFilter = "(&(objectClass=user)(givenName=s*))";
			
			LDAPConnection ldapCon = null;
			try{
				ldapCon = conMgr.connect(ldap_uname, ldap_pwd, useSSL, ldap_url, ldap_port);
			}catch(LDAPComponentException ldapEx){
				System.out.println("LDAP Component Exception: " + ldapEx.getMessage());
			}
			boolean ldapUserAuthenticated = false;
			
			if(ldapCon != null){
				ldapUserAuthenticated = true;
				
				QueryManager qm = new QueryManager();
				ArrayList reqProps = new ArrayList<String>();
				reqProps.add("sAMAccountName");
				reqProps.add("name");
				reqProps.add("userPrincipalName");
				reqProps.add("mail");
				reqProps.add("description");
				Document resultDoc = qm.search(ldapCon, searchBase, searchScope, searchFilter, reqProps);
				
				/*IHICData hicData = new HICData();
				ILdapData ldapData = new LdapData();
				ldapData.setLdapDoc(resultDoc);
				hicData.setLdapData(ldapData);
				LDAPVO ldapVpo = LDAPComponentUtility.parseEntityDocumentToLDAPVO(hicData);
				
				String str = ldapVpo.getName();
				System.out.println("details: " + str);*/
				
				if(resultDoc != null){
					System.out.println("xml : " + resultDoc.asXML());
					/*Hashtable<String, Hashtable<String, Object>> resultMap = null;//getHashTableFromDoc(resultDoc);
					Enumeration<String> entityKeys = resultMap.keys();
			        while(entityKeys.hasMoreElements()){
			            String entityKey = entityKeys.nextElement();
			            System.out.println("Key:- " + entityKey + " Value:- " + resultMap.get(entityKey));
			        }*/
				}else{
					System.out.println("no records");
				}
				
				/*//searching the ldap directory
				LDAPSearchResults searchResults = ldapCon.search(
						searchBase,      // container to search
                        searchScope,     // search scope
                        searchFilter,    // search filter
                        null,          // return all attributes
                        false);
				
				//iterating the LDAP directory entries
				System.out.println("result count: " + searchResults.getCount());
				while(searchResults.hasMore()){
					System.out.println("-----------------------------------");
	            	LDAPEntry ldapEntry = null;
	                int endTag = 0;
	                String parentVal ="";
	                try{
	                	ldapEntry = searchResults.next();
	                }catch(LDAPReferralException refEx){
	                }
	                if(ldapEntry != null){
		                LDAPAttributeSet attributeSet = ldapEntry.getAttributeSet();
		                Iterator allAttributes = attributeSet.iterator();
		                while(allAttributes.hasNext()){
		                	LDAPAttribute attribute = (LDAPAttribute)allAttributes.next();
		                    String attributeName = attribute.getName();
		                    if(attributeName.equalsIgnoreCase("displayName") || attributeName.equalsIgnoreCase("userPrincipalName")){
			                    Enumeration allValues = attribute.getStringValues();
			                    String value = "";
			                    if(allValues != null){
			                    	while(allValues.hasMoreElements()){
			                            value += (String)allValues.nextElement() + "~~";
			                            if(!Base64.isLDIFSafe(value)) {
			                            	value += Base64.encode(value.getBytes()) + "~~";
			                            }
			                        }
			                    }
			                    System.out.println("attribute: " + attributeName + "=" + value);
		                    }
		                }
	                }
				}*/
				
				if(ldapCon.isConnected()){
					conMgr.disconnect();
				}
			}
			System.out.println("ldapUserAuthenticated: " + ldapUserAuthenticated);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("End");
	}

	@Override
	public void destroy() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IHICData getHicData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHicData(IHICData hicData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(Hashtable<String, Document> configData) {
		// TODO Auto-generated method stub
		
	}

}

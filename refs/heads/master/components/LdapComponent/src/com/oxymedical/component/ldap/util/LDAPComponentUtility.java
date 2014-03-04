package com.oxymedical.component.ldap.util;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.oxymedical.component.ldap.LDAPComponent;
import com.oxymedical.component.ldap.LDAPVO;
import com.oxymedical.component.ldap.Role.RoleLdapVO;
import com.oxymedical.component.ldap.constants.LDAPConstants;
import com.oxymedical.component.ldap.exception.LDAPComponentException;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.ldapData.ILdapData;

/**
 * Utility class for the LDAPComponent 
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class LDAPComponentUtility {
	
	/**
	 * Parses the xml document and gets the LDAPVO
	 * @param xmlDocument
	 * @return LDAPVO
	 */
	public static LDAPVO parseEntityDocumentToLDAPVO(IHICData hicData) {
		ILdapData ldapData = hicData.getLdapData();
		Document xmlDocument = ldapData.getLdapDoc();
		//LDAPComponent.logger.log(0,"xml value is "+xmlDocument.asXML());
		LDAPVO ldapVO = new LDAPVO();
		Element root = xmlDocument.getRootElement();
		Element nodeElement = null;	
		String system = LDAPConstants.OU + "=" + LDAPConstants.SYSTEM;
		String name;
		for(Iterator i = root.elementIterator(); i.hasNext(); )
		{	
			nodeElement = (Element) i.next();
			name = nodeElement.getName();
			if(name.equals(LDAPConstants.ENTITY_TYPE))
			{
				ldapVO.setType(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.ENTITY_NAME))
			{
				ldapVO.setName(nodeElement.getTextTrim());
				continue;
			}
			
			if(name.equals(LDAPConstants.ENTITY_LASTNAME))
			{
				ldapVO.setLastName(nodeElement.getTextTrim());
				continue;
			}	
			if(name.equals(LDAPConstants.ENTITY_DESCRIPTION))
			{
				ldapVO.setDescription(nodeElement.getTextTrim());
				continue;
			}	
			if(name.equals(LDAPConstants.ENTITY_DISPLAYNAME))
			{
				ldapVO.setDisplayName(nodeElement.getTextTrim());
				continue;
			}	
			if(name.equals(LDAPConstants.ENTITY_COMMONNAME))
			{
				ldapVO.setCommonName(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.ENTITY_PASSWORD))
			{
				ldapVO.setUserPassword(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.ENTITY_MAIL))
			{
				ldapVO.setMail(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.ENTITY_TELEPHONENUMBER))
			{
				ldapVO.setTelephoneNumber(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.ENTITY_UID))
			{
				ldapVO.setUserId(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.ENTITY_UNIQUE_MEMBER))
			{
				ldapVO.setUniqueMember(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.USER_ROLE))
			{
				ldapVO.setUserRole(nodeElement.getTextTrim());
				continue;
			}
			
			if(name.equals(LDAPConstants.ENTITY_ENTITYPARENT))
			{
				String containerName = getContainerName(nodeElement);
				containerName = containerName + system;	
				ldapVO.setContainerName(containerName);
				continue;
			}
			
		}
		if (null == ldapVO.getContainerName() || ("").equals(ldapVO.getContainerName())) {
			ldapVO.setContainerName(system);
		}
		
		return ldapVO;
	}
	
	/**
	 * Gets the parent of a node element through recursion
	 * @param nodeElement
	 * @return String
	 */
	private static String getContainerName(Element nodeElement) {		
		String containerName = "";
		Element entityChild = null;
		if (null != nodeElement) {
			entityChild = nodeElement.element(LDAPConstants.ENTITY);
			//containerName =  LDAPConstants.OU + "=" + entityChild.element(LDAPConstants.ENTITY_NAME).getTextTrim();
			if (null != entityChild) {
				containerName = LDAPConstants.OU  + "=" + (entityChild.element(LDAPConstants.ENTITY_NAME)).getTextTrim() + ", " + getContainerName(entityChild.element(LDAPConstants.ENTITY_ENTITYPARENT));
				}
		}	
		return containerName;
	}
	
	/**
	 * Parent start tag for the search and list xml 
	 * @param parentXml
	 * @return String
	 */
	public static String startParentXml(String parentXml)
	{
		StringBuffer str = new StringBuffer();
		str.append(LDAPConstants.START_PARENT_TAG + "\n");
		str.append("\t" + LDAPConstants.START_ENTITY_TAG + "\n");
		str.append("\t" + LDAPConstants.START_NAME_TAG + parentXml.trim() + LDAPConstants.END_NAME_TAG + "\n");
		return str.toString();
	}
	
	/**
	 * Parent end tag for the search and list xml
	 * @param endTagValue
	 * @return String
	 */
	public static String endParentXml(int endValue)
	{
		StringBuffer str = new StringBuffer();
		while (endValue>0)
		{
			str.append("\t" + LDAPConstants.END_ENTITY_TAG + "\n");
			str.append(LDAPConstants.END_PARENT_TAG + "\n");
			endValue--;
		}
		return str.toString();
	}
	
	/**
	 * To parse the XML file in to document 
	 * @param xmlFile
	 * @return Document
	 * @throws LDAPComponentException
	 */
	public static Document parseXMLToDocument(String xmlFile) throws LDAPComponentException
	{
		InputStream moduleStream =	LDAPComponent.class.getResourceAsStream(xmlFile);
		SAXReader reader = new SAXReader();				
		Document document = null;
		try {
			document = reader.read(moduleStream);
		} catch (DocumentException e) {
			throw new LDAPComponentException(e.getMessage());
		}
		return document;
	}
	
	
	/**
	 * To parse the role document and set the value in RoleLdapVo class
	 * @param roleDocument
	 * @return roleLdapVO
	 */
	public static RoleLdapVO parseRoleDocumentToLDAPVO(Document roleDocument)
	{
		RoleLdapVO roleLdapVO = new RoleLdapVO();
		Element nodeElement = null;
		Element root = roleDocument.getRootElement();
		String system = LDAPConstants.OU + "=" + LDAPConstants.SYSTEM;
		String name;
		for(Iterator i = root.elementIterator(); i.hasNext(); )
		{	
			nodeElement = (Element) i.next();
			name = nodeElement.getName();
			if(name.equals(LDAPConstants.ENTITY_NAME))
			{
				roleLdapVO.setName(nodeElement.getTextTrim());
				continue;
			}
			if(name.equals(LDAPConstants.ENTITY_ENTITYPARENT))
			{
				String containerName = getContainerName(nodeElement);
				containerName = LDAPConstants.OU + "=" + roleLdapVO.getName() + ", " + containerName + system;
				roleLdapVO.setContainerName(containerName);
				continue;
			}
		}
		if (null == roleLdapVO.getContainerName() || ("").equals(roleLdapVO.getContainerName())) {
			String containerName = LDAPConstants.OU + "=" + roleLdapVO.getName() + ", " + system; 
			roleLdapVO.setContainerName(containerName);
		}
		return roleLdapVO;
	
	}
}

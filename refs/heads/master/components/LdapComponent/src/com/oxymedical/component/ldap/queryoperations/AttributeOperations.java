package com.oxymedical.component.ldap.queryoperations;

import java.util.ArrayList;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPModification;
import com.oxymedical.component.ldap.LDAPVO;
import com.oxymedical.component.ldap.constants.LDAPConstants;
import com.oxymedical.component.ldap.exception.LDAPComponentException;


/**
 * Perform the LDAP attribute operations such as modify attribute , 
 *  add attribute and delete attribute
 * @author      Oxyent Medical
 * @version     1.0.0
 *
 */
public class AttributeOperations {

	 /* To delete an attribute of an entry,
      *     -- Specify the dn of the entry to modify
      *     -- Specify the attribute with no value
      *     -- Specify the modification type as delete or replace
      *     -- Call LDAPConnection modify() method
      */
	
	 /**
	 * Deletes an attribute of an already added entry
	 * @param ldapVO
	 * @return LDAPModification[]
	 * @throws LDAPComponentException
	 */
	public LDAPModification[] deleteAttribute(LDAPVO ldapVO) throws LDAPComponentException {
		 ArrayList<LDAPModification> modList = getLDAPModificationList(ldapVO, LDAPModification.DELETE);		 

	     // Create modifications array
	     LDAPModification[] modsDel = new LDAPModification[modList.size()]; 
	     modsDel = (LDAPModification[])modList.toArray(modsDel);
	     return modsDel;
	 }

	 
	 /**
	 * Modifies an attribute of an already added entry
	 * @param ldapVO
	 * @return LDAPModification[]
	 * @throws LDAPComponentException
	 */
	public LDAPModification[] modifyAttribute(LDAPVO ldapVO) throws LDAPComponentException {
		 ArrayList<LDAPModification> modList = getLDAPModificationList(ldapVO, LDAPModification.REPLACE);
		 
		 // Create modifications array
		 LDAPModification[] modsMod = new LDAPModification[modList.size()]; 
	     modsMod = (LDAPModification[])modList.toArray(modsMod);
	     return modsMod;
	 }

	 
	 /**
	 * Add an attribute of an already added entry
	 * @param ldapVO
	 * @return LDAPModification[]
	 * @throws LDAPComponentException
	 */
	public LDAPModification[] addAttribute(LDAPVO ldapVO)  throws LDAPComponentException {
		 ArrayList<LDAPModification> modList = getLDAPModificationList(ldapVO, LDAPModification.ADD);	 
		 
	     // Create modifications array
	     LDAPModification[] modsAdd = new LDAPModification[modList.size()]; 
	     modsAdd = (LDAPModification[])modList.toArray(modsAdd);
	     return modsAdd;
	 }
	 
	 /**
	 * Creates an LDAPModification list 
	 * @param ldapVO
	 * @return ArrayList<LDAPModification>
	 */
	private ArrayList<LDAPModification> getLDAPModificationList(LDAPVO ldapVO, int modifyType){
		 ArrayList<LDAPModification> modList = new ArrayList<LDAPModification>();
		 LDAPAttribute attribute = null;
		 if (null != ldapVO.getTelephoneNumber()) {
			 attribute = new LDAPAttribute(LDAPConstants.TELNUM, ldapVO.getTelephoneNumber());
			 modList.add(new LDAPModification(modifyType,attribute));
		 }
		 if (null != ldapVO.getMail()) {
			 attribute = new LDAPAttribute(LDAPConstants.MAIL, ldapVO.getMail());
			 modList.add(new LDAPModification(modifyType,attribute));
		 }
		 if (null != ldapVO.getUserPassword()) {
			 attribute = new LDAPAttribute(LDAPConstants.USERPASS, ldapVO.getUserPassword());
			 modList.add(new LDAPModification(modifyType,attribute));
		 }
		  if (null != ldapVO.getDescription()){
			 attribute = new LDAPAttribute(LDAPConstants.DESCRIPTION, ldapVO.getDescription());
			 modList.add(new LDAPModification(modifyType,attribute));
		 }
		return modList;
	 }
}

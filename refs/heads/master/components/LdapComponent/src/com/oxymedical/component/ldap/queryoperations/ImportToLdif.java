package com.oxymedical.component.ldap.queryoperations;
import java.io.FileInputStream;import java.io.FileNotFoundException;import java.io.IOException;import java.util.Enumeration;import java.util.Iterator;import com.novell.ldap.LDAPAttribute;import com.novell.ldap.LDAPAttributeSet;import com.novell.ldap.LDAPConnection;import com.novell.ldap.LDAPEntry;import com.novell.ldap.LDAPException;import com.novell.ldap.LDAPLocalException;import com.novell.ldap.LDAPMessage;import com.novell.ldap.LDAPSearchResult;import com.novell.ldap.util.LDIFReader;import com.oxymedical.component.ldap.exception.LDAPComponentException;

/** * Import the ldif file in to the LDAP ds * @author      Oxyent Medical * @version     1.0.0 * */public class ImportToLdif {		/**	 * Import the ldif file in to the LDAP ds	 * @param fileName	 * @param lc	 * @throws LDAPComponentException	 */	public void importLdif(String fileName, LDAPConnection lc) throws LDAPComponentException {		LDIFReader reader = null;        LDAPEntry entry;        LDAPMessage msg;        FileInputStream fis;                try {			 	fis = new FileInputStream( fileName);			 	reader = new LDIFReader(fis);		 if (!reader.isRequest()) {	    	 //LDAPComponent.logger.log(0,"reader request = " +reader.readMessage());	    	 //reader.	    	 msg = reader.readMessage();	    	 while ( (msg = reader.readMessage()) != null) {                 entry = ((LDAPSearchResult)msg).getEntry();                 //LDAPComponent.logger.log(0,"\nEntry DN:" + entry.getDN());                 showAttributes(entry);             }         }         else {           //  LDAPComponent.logger.log(0,"\nLDIF change file\n");             while ( (msg = reader.readMessage()) != null) {            	 entry = ((LDAPSearchResult)msg).getEntry();                 lc.add(entry);                 //LDAPComponent.logger.log(0,"added");                /* if (msg instanceof LDAPAddRequest) {                    LDAPComponent.logger.log(0,"Adding entry...");                    entry = ((LDAPSearchResult)msg).getEntry();                    lc.add(entry);                 }                 else if (msg instanceof LDAPDeleteRequest) {                     LDAPComponent.logger.log(0,"Deleting entry...");                 }                 else if (msg instanceof LDAPModifyDNRequest) {                     LDAPComponent.logger.log(0,"Modifying entry's RDN...");                 }                 else if (msg instanceof LDAPModifyRequest) {                     LDAPComponent.logger.log(0,"Modifying entry's attribute(s)...");                 }*/                 /*LDAPMessageQueue queue = lc.sendRequest(msg, null, null);                 LDAPComponent.logger.log(0,"queue = " +queue);                 if ((retMsg = queue.getResponse()) != null) {                     LDAPResponse response = (LDAPResponse)retMsg;                     int status = response.getResultCode();                     // the return code is LDAP success                     if ( status == LDAPException.SUCCESS ) {                         LDAPComponent.logger.log(0,"Directory information has been" + " modified.");                     }                     // the reutrn code is referral exception                     else if ( status == LDAPException.REFERRAL ) {                         String urls[]=((LDAPResponse)retMsg).getReferrals();                         LDAPComponent.logger.log(0,"Referrals:");                         for ( int i = 0; i < urls.length; i++ )                             LDAPComponent.logger.log(0,"    " + urls[i]);                     }                     // general error                     else {                         LDAPComponent.logger.log(0, response.getErrorMessage());                     }                 }*/             }         }	            }catch (FileNotFoundException e) {			throw new LDAPComponentException(e.getMessage());        }catch (LDAPLocalException e) {        	throw new LDAPComponentException(e.getMessage());        }catch (IOException e) {        	throw new LDAPComponentException(e.getMessage());        }catch(LDAPException ldapEx) {        	throw new LDAPComponentException(ldapEx.getMessage());	    }	}
    public void showAttributes(LDAPEntry entry) {    	String value;
        LDAPAttributeSet as = null;
        LDAPAttribute attr = null;
        Iterator allAttrs;
        Enumeration allAttrValues;
        as = entry.getAttributeSet();
        allAttrs = as.iterator();
      //  LDAPComponent.logger.log(0,"    Attributes:");
        while(allAttrs.hasNext()) {
            attr = (LDAPAttribute)allAttrs.next();
          //  LDAPComponent.logger.log(0,"        " + attr.getName());
            allAttrValues = attr.getStringValues();
            if( allAttrValues != null) {
                while(allAttrValues.hasMoreElements()) {
                    value = (String) allAttrValues.nextElement();
                  //  LDAPComponent.logger.log(0,"            " + value);
                }
            }
        }
    }
}
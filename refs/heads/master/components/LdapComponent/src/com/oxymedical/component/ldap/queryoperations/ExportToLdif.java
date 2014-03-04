package com.oxymedical.component.ldap.queryoperations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPMessage;
import com.novell.ldap.LDAPResponse;
import com.novell.ldap.LDAPSearchConstraints;
import com.novell.ldap.LDAPSearchQueue;
import com.novell.ldap.LDAPSearchResult;
import com.novell.ldap.LDAPSearchResultReference;
import com.novell.ldap.util.LDAPWriter;
import com.novell.ldap.util.LDIFWriter;
import com.oxymedical.component.ldap.constants.LDAPConstants;
import com.oxymedical.component.ldap.exception.LDAPComponentException;


/**
 * Export the entries in to ldif file 
 * @author Oxyent Medical
 * @version 1.0.0
 *
 */
public class ExportToLdif {
	
	/**
	 * Exports entries to LDIF file
	 * @param fileName
	 * @param lc
	 * @param searchBase
	 * @return boolean
	 * @throws LDAPComponentException
	 */
	public boolean export(String fileName, LDAPConnection lc, String searchBase) throws LDAPComponentException {
		
		boolean result = false;
		LDAPMessage msg;
		
        try {
			FileOutputStream fos = new FileOutputStream(fileName);
			LDAPWriter writer = new LDIFWriter(fos);

			// asynchronous search
			LDAPSearchQueue queue =
			    lc.search(searchBase,      // container to search
			              LDAPConnection.SCOPE_SUB,// The whole subtree below the base, including the base entry 
			              LDAPConstants.SEARCH_FILTER,    // search filter
			              null,            // return all attrs
			              false,           // return attrs and values
			              (LDAPSearchQueue)null, // default search queue
			              (LDAPSearchConstraints)null); //default constraints

			while (( msg = queue.getResponse()) != null ) {
			    // the message is a search result reference
			    if ( msg instanceof LDAPSearchResultReference ) {
			        String urls[] =
			            ((LDAPSearchResultReference)msg).getReferrals();
			      //  LDAPComponent.logger.log(0,"Search result references:");
			         //   for ( int i = 0; i < urls.length; i++ )
			            //    LDAPComponent.logger.log(0,urls[i]);
			    }
			    // the message is a search result
			    else if (msg instanceof LDAPSearchResult ) {
			        writer.writeMessage(msg);
			    }
			    // the message is a search response
			    else {
			        LDAPResponse response = (LDAPResponse)msg;
			        int status = response.getResultCode();
			        // the return code is LDAP success
			        if ( status == LDAPException.SUCCESS ) {
			            //LDAPComponent.logger.log(0,"Asynchronous search succeeded.");
			        }
			        // the reutrn code is referral exception
			        else if ( status == LDAPException.REFERRAL ) {
			            String urls[]=((LDAPResponse)msg).getReferrals();
			            //LDAPComponent.logger.log(0,"Referrals:");
			            //for ( int i = 0; i < urls.length; i++ )
			                //LDAPComponent.logger.log(0,urls[i]);
			        }
			        else {
			            //LDAPComponent.logger.log(0,"Asynchronous search failed.");
			            throw new LDAPException( response.getErrorMessage(),
			                                     status,
			                                     response.getMatchedDN());
			        }
			    }
			}

			// close the output stream
			writer.finish();
			fos.close();			
		} catch (FileNotFoundException fnfe) {
			throw new LDAPComponentException(fnfe.getMessage());
		} catch (IOException ioe) {
			throw new LDAPComponentException(ioe.getMessage());
		} catch (LDAPException ldape) {
			throw new LDAPComponentException(ldape.getMessage());
		}
		return result;
	}

}

/**
 * 
 */
package ldapclient;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;

import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import java.util.Vector;


/**
 * @author dropspot
 */

public class LDAPData {
	Exception exception;
	boolean errorStatus;
	
	public Vector<String> search(DirContext context, String query, SearchControls ctrl, String attribute) {
		Vector<String> v = new Vector<String>();
		
	    try {
	    	NamingEnumeration<?> enumeration = context.search("", query, ctrl);
	    
		    while (enumeration.hasMore()) {
		        SearchResult result = (SearchResult) enumeration.next();
		        Attributes attribs = result.getAttributes();
                NamingEnumeration<?> values = ((BasicAttribute) attribs.get(attribute)).getAll();
                while (values.hasMore()) { v.addElement(values.next().toString()); }
		    }
	    } catch (Exception e) { this.errorStatus=true; this.exception=e;  }
	    
		return v;
	}
	
	public boolean update(DirContext context, String newPass, String cn, String dn) {
		try {
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("vpassword",newPass));
			context.modifyAttributes("cn="+cn+","+dn, mods);
			context.close();
		} catch (Exception e) { this.errorStatus=true; this.exception=e; return false; }
		return true;
	}
	
	public boolean isError() {
		return this.errorStatus;
	}
	
	public Exception getException() {
		return this.exception;
	}

}

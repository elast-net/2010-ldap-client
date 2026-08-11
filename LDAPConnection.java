/**
 * 
 */

package ldapclient;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.directory.SearchControls;

public class LDAPConnection {
	public DirContext context;
	public Exception exception;
	public boolean errorStatus;
	
	/* Establishes a connection context using SSL, with given URL */
	public LDAPConnection(
			String url, 
			String trustStorePath, 
			String trustStorePassword, 
			String LDAPLogin, 
			String LDAPPassword) {
		
		this.errorStatus=false;
		try {
			Hashtable<String, Object> env = new Hashtable<String, Object>(11);
			
	        env.put(Context.SECURITY_PROTOCOL, "ssl");
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
			System.setProperty("javax.net.ssl.trustStore", trustStorePath);
		    System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

		    env.put(Context.SECURITY_PRINCIPAL, LDAPLogin);
		    env.put(Context.SECURITY_CREDENTIALS, LDAPPassword);
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, url);

	        this.context = new InitialLdapContext(env,null);
	        if(this.context==null) this.errorStatus=true;
		} catch (Exception e) { this.errorStatus=true; this.exception=e; }
	}

	/* Establishes a connection context without SSL, with given URL */
	public LDAPConnection(
			String url,
			String LDAPLogin, 
			String LDAPPassword) {
		
		this.errorStatus=false;
		try {
			Hashtable<String, Object> env = new Hashtable<String, Object>(11);

		    env.put(Context.SECURITY_PRINCIPAL, LDAPLogin);
		    env.put(Context.SECURITY_CREDENTIALS, LDAPPassword);
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, url);

	        this.context = new InitialLdapContext(env,null);
	        if(this.context==null) this.errorStatus=true;
		} catch (Exception e) { this.errorStatus=true; this.exception=e; }
	}
	
	public DirContext getContext() {
		return this.context;
	}
	
	public SearchControls getControls() {
		SearchControls ctrl = new SearchControls();
		
		try { ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE); }
		catch (Exception e) { this.errorStatus=true; this.exception=e; }
		
		return ctrl;
	}
	
	public boolean isError() {
		return this.errorStatus;
	}
	
	public Exception getException() {
		return this.exception;
	}
}

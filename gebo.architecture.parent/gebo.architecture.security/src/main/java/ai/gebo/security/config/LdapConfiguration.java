/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.config;

/**
 * AI generated comments
 *
 * LdapConfiguration class provides configuration settings for LDAP connectivity.
 * It includes properties such as domain, url, and rootDn with corresponding getter and setter methods.
 */
public class LdapConfiguration {

    // Domain name for LDAP configuration
	private String domain = null;

    // URL for connecting to the LDAP server
	private String url = null;

    // Root Distinguished Name (DN) for LDAP configuration
	private String rootDn = null;

    /**
     * Retrieves the domain name for the LDAP configuration.
     *
     * @return A string representing the domain name.
     */
	public String getDomain() {
		return domain;
	}

    /**
     * Sets the domain name for the LDAP configuration.
     *
     * @param domain A string representing the domain name.
     */
	public void setDomain(String domain) {
		this.domain = domain;
	}

    /**
     * Retrieves the URL for connecting to the LDAP server.
     *
     * @return A string representing the LDAP server URL.
     */
	public String getUrl() {
		return url;
	}

    /**
     * Sets the URL for connecting to the LDAP server.
     *
     * @param url A string representing the LDAP server URL.
     */
	public void setUrl(String url) {
		this.url = url;
	}

    /**
     * Retrieves the root Distinguished Name (DN) for LDAP.
     *
     * @return A string representing the root DN.
     */
	public String getRootDn() {
		return rootDn;
	}

    /**
     * Sets the root Distinguished Name (DN) for LDAP.
     *
     * @param rootDn A string representing the root DN.
     */
	public void setRootDn(String rootDn) {
		this.rootDn = rootDn;
	}
}
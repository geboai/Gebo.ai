/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model.clientregistration;

import java.util.HashMap;
import java.util.Map;

/**
 * Gebo.ai comment agent
 * 
 * Represents the provider details in an OAuth2 client registration context.
 * This class holds configuration information such as authorization URI, token URI,
 * user info endpoint, JWK set URI, issuer URI, and additional configuration metadata.
 */
public class GProviderDetails {
	
	/** The URI for authorization requests */
	private String authorizationUri;

	/** The URI for token requests */
	private String tokenUri;

	/** The user information endpoint details */
	private GUserInfoEndpoint userInfoEndpoint = new GUserInfoEndpoint();

	/** The URI for the JSON Web Key Set */
	private String jwkSetUri;

	/** The issuer's URI */
	private String issuerUri;

	/** Additional metadata for configuration */
	private Map<String, Object> configurationMetadata = new HashMap<String, Object>();
	
	/** Default constructor */
	public GProviderDetails() {
	}

	/**
	 * Gets the authorization URI.
	 * 
	 * @return the authorization URI
	 */
	public String getAuthorizationUri() {
		return authorizationUri;
	}

	/**
	 * Sets the authorization URI.
	 * 
	 * @param authorizationUri the new authorization URI
	 */
	public void setAuthorizationUri(String authorizationUri) {
		this.authorizationUri = authorizationUri;
	}

	/**
	 * Gets the token URI.
	 * 
	 * @return the token URI
	 */
	public String getTokenUri() {
		return tokenUri;
	}

	/**
	 * Sets the token URI.
	 * 
	 * @param tokenUri the new token URI
	 */
	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}

	/**
	 * Gets the user info endpoint.
	 * 
	 * @return the user info endpoint
	 */
	public GUserInfoEndpoint getUserInfoEndpoint() {
		return userInfoEndpoint;
	}

	/**
	 * Sets the user info endpoint.
	 * 
	 * @param userInfoEndpoint the new user info endpoint
	 */
	public void setUserInfoEndpoint(GUserInfoEndpoint userInfoEndpoint) {
		this.userInfoEndpoint = userInfoEndpoint;
	}

	/**
	 * Gets the JWK set URI.
	 * 
	 * @return the JWK set URI
	 */
	public String getJwkSetUri() {
		return jwkSetUri;
	}

	/**
	 * Sets the JWK set URI.
	 * 
	 * @param jwkSetUri the new JWK set URI
	 */
	public void setJwkSetUri(String jwkSetUri) {
		this.jwkSetUri = jwkSetUri;
	}

	/**
	 * Gets the issuer URI.
	 * 
	 * @return the issuer URI
	 */
	public String getIssuerUri() {
		return issuerUri;
	}

	/**
	 * Sets the issuer URI.
	 * 
	 * @param issuerUri the new issuer URI
	 */
	public void setIssuerUri(String issuerUri) {
		this.issuerUri = issuerUri;
	}

	/**
	 * Gets the configuration metadata.
	 * 
	 * @return the configuration metadata map
	 */
	public Map<String, Object> getConfigurationMetadata() {
		return configurationMetadata;
	}

	/**
	 * Sets the configuration metadata.
	 * 
	 * @param configurationMetadata the new configuration metadata map
	 */
	public void setConfigurationMetadata(Map<String, Object> configurationMetadata) {
		this.configurationMetadata = configurationMetadata;
	}
}
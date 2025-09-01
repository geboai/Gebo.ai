/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.model.oauth2;

import ai.gebo.security.model.AuthProvider;
import jakarta.validation.constraints.NotNull;

/*******************************************************************************
 * This data represents both runtime OAuth2 provider basic configuration and
 * pre-defined standard configuration for worldwide standard OAuth2 providers.
 * AI generated comments
 */

public class Oauth2ProviderConfig {

	/**
	 * The authentication provider for OAuth2 authentication process. It cannot be
	 * null.
	 */
	@NotNull
	private AuthProvider provider;

	/**
	 * The URI used for the authorization step in the OAuth2 flow. It cannot be
	 * null.
	 */
	@NotNull
	private String authorizationUri;

	/**
	 * The URI used to exchange an authorization code for a token in the OAuth2
	 * flow. It cannot be null.
	 */
	@NotNull
	private String tokenUri;

	/**
	 * The URI used to retrieve user details as part of the OAuth2 flow. It cannot
	 * be null.
	 */
	@NotNull
	private String userInfoUri;

	/**
	 * The name attribute identifying the user within the UserInfo response. It
	 * cannot be null.
	 */
	@NotNull
	private String userNameAttribute;
	private String introspectionUri;
	private String issuerUri;
	private String jwkSetUri;
	public AuthProvider getProvider() {
		return provider;
	}
	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}
	public String getAuthorizationUri() {
		return authorizationUri;
	}
	public void setAuthorizationUri(String authorizationUri) {
		this.authorizationUri = authorizationUri;
	}
	public String getTokenUri() {
		return tokenUri;
	}
	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}
	public String getUserInfoUri() {
		return userInfoUri;
	}
	public void setUserInfoUri(String userInfoUri) {
		this.userInfoUri = userInfoUri;
	}
	public String getUserNameAttribute() {
		return userNameAttribute;
	}
	public void setUserNameAttribute(String userNameAttribute) {
		this.userNameAttribute = userNameAttribute;
	}
	public String getIntrospectionUri() {
		return introspectionUri;
	}
	public void setIntrospectionUri(String introspectionUri) {
		this.introspectionUri = introspectionUri;
	}
	public String getIssuerUri() {
		return issuerUri;
	}
	public void setIssuerUri(String issuerUri) {
		this.issuerUri = issuerUri;
	}
	public String getJwkSetUri() {
		return jwkSetUri;
	}
	public void setJwkSetUri(String jwkSetUri) {
		this.jwkSetUri = jwkSetUri;
	}
}
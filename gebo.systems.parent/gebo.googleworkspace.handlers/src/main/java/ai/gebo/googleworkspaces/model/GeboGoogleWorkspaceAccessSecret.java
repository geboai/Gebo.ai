/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googleworkspaces.model;

import com.google.api.client.auth.oauth2.Credential;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * This class represents a Google Workspace access secret that stores OAuth 2.0 
 * authentication credentials for Google API access. It maintains the access token,
 * refresh token, expiration time, as well as identification information for the secret.
 */
public class GeboGoogleWorkspaceAccessSecret {
	// Unique identifier for this access secret
	private String id = null;
	// User identifier associated with this access secret, cannot be null
	@NotNull
	private String uid=null;
	// OAuth 2.0 access token for API requests
	private String accessToken;
	// OAuth 2.0 refresh token to obtain new access tokens
	private String refreshToken;
	// Timestamp indicating when the access token expires (in milliseconds)
	private Long expirationTime;

	/**
	 * Default constructor that creates an empty access secret instance.
	 */
	public GeboGoogleWorkspaceAccessSecret() {
		
	}

	/**
	 * Returns the unique identifier of this access secret.
	 * 
	 * @return the ID of this access secret
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for this access secret.
	 * 
	 * @param id the ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Creates a new instance from a Google OAuth Credential object.
	 * 
	 * @param c the Google OAuth Credential containing token information
	 * @param id the unique identifier for this access secret
	 * @param uid the user identifier associated with this access secret
	 * @return a new GeboGoogleWorkspaceAccessSecret populated with the credential data
	 */
	public static GeboGoogleWorkspaceAccessSecret of(Credential c, String id, String uid) {
		GeboGoogleWorkspaceAccessSecret secret=new GeboGoogleWorkspaceAccessSecret();
		secret.id=id;
		secret.uid=uid;
		secret.accessToken=c.getAccessToken();
		secret.refreshToken=c.getRefreshToken();
		secret.expirationTime=c.getExpirationTimeMilliseconds();
		return secret;
	}

	/**
	 * Returns the OAuth 2.0 access token.
	 * 
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the OAuth 2.0 access token.
	 * 
	 * @param accessToken the access token to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Returns the OAuth 2.0 refresh token.
	 * 
	 * @return the refresh token
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Sets the OAuth 2.0 refresh token.
	 * 
	 * @param refreshToken the refresh token to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * Returns the expiration time of the access token in milliseconds.
	 * 
	 * @return the expiration time in milliseconds
	 */
	public Long getExpirationTime() {
		return expirationTime;
	}

	/**
	 * Sets the expiration time of the access token.
	 * 
	 * @param expirationTime the expiration time to set, in milliseconds
	 */
	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	/**
	 * Returns the user identifier associated with this access secret.
	 * 
	 * @return the user identifier
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the user identifier for this access secret.
	 * 
	 * @param uid the user identifier to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
}
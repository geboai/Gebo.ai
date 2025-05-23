/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

import java.util.List;

/**
 * GeboGoogleOauth2SecretContent is a subclass of AbstractGeboSecretContent that
 * represents the secret content for Google OAuth2 authentication details.
 * This class contains fields for storing unique identifiers, tokens, and other
 * necessary information for OAuth2 authentication.
 * 
 * Gebo.ai comment agent
 */
public class GeboGoogleOauth2SecretContent extends AbstractGeboSecretContent {

	// Unique identifier for the OAuth2 secret
	private String uid = null;

	// Token used for authentication
	private String token = null;

	// Location associated with the secret
	private String location = null;

	// Google project ID
	private String projectId = null;

	// List of scopes associated with the OAuth2 token
	private List<String> scopes = null;

	/**
	 * Returns the type of secret represented by this class.
	 * 
	 * @return GeboSecretType.OAUTH2_GOOGLE
	 */
	@Override
	public GeboSecretType type() {
		return GeboSecretType.OAUTH2_GOOGLE;
	}

	/**
	 * Gets the unique identifier for the OAuth2 secret.
	 * 
	 * @return the UID
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the unique identifier for the OAuth2 secret.
	 * 
	 * @param uid the UID to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * Gets the token used for authentication.
	 * 
	 * @return the authentication token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token used for authentication.
	 * 
	 * @param token the authentication token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the location associated with the secret.
	 * 
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location associated with the secret.
	 * 
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the Google project ID.
	 * 
	 * @return the project ID
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * Sets the Google project ID.
	 * 
	 * @param projectId the project ID to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * Gets the list of scopes associated with the OAuth2 token.
	 * 
	 * @return the list of scopes
	 */
	public List<String> getScopes() {
		return scopes;
	}

	/**
	 * Sets the list of scopes associated with the OAuth2 token.
	 * 
	 * @param scopes the list of scopes to set
	 */
	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

}
/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.model;

/**
 * GeboSecretType enumerates the various types of secrets 
 * that can be managed within the system.
 * 
 * <p>The supported secret types include:</p>
 * <ul>
 *   <li>{@link #USERNAME_PASSWORD} - Represents a classic username/password pair.</li>
 *   <li>{@link #TOKEN} - Represents an authentication token.</li>
 *   <li>{@link #SSH_KEY} - Represents an SSH key for secure access.</li>
 *   <li>{@link #CUSTOM_SECRET} - Represents a user-defined custom secret.</li>
 *   <li>{@link #OAUTH2_STANDARD} - Represents a standard OAuth2 token.</li>
 *   <li>{@link #OAUTH2_GOOGLE} - Represents a Google OAuth2 token.</li>
 *   <li>{@link #GOOGLE_CLOUD_JSON_CREDENTIALS} - Represents JSON credentials for Google Cloud access.</li>
 * </ul>
 * 
 * Gebo.ai comment agent
 */
public enum GeboSecretType {
	USERNAME_PASSWORD, // Username and password credential type
	TOKEN, // General token type
	SSH_KEY, // SSH key credential type
	CUSTOM_SECRET, // User-defined custom secret type
	OAUTH2_STANDARD, // OAuth2 standard token type
	OAUTH2_GOOGLE, // OAuth2 token specifically for Google services
	GOOGLE_CLOUD_JSON_CREDENTIALS, // JSON credentials for Google Cloud
	OAUTH2_AUTHORIZED_CLIENT
}
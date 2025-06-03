/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.model;

/**
 * Gebo.ai comment agent
 *
 * Enum representing different authentication providers supported by the system.
 * These providers are used to identify the source of authentication such as
 * local database or third-party services.
 */
public enum AuthProvider {
	// Local database authentication
	local(AuthProviderType.LOCAL_JWT, "Jwt local auth"),

	// Authentication using Facebook account
	facebook(AuthProviderType.OAUTH2, "Facebook auth provider"),

	// Authentication using Google account
	google(AuthProviderType.OAUTH2, "Google auth provider"),

	// Authentication using GitHub account
	github(AuthProviderType.OAUTH2, "GitHub auth provider"),

	// Authentication using Microsoft account
	microsoft(AuthProviderType.OAUTH2, "Microsoft auth provider"),
	// Authentication using Linkedin account
	linkedin(AuthProviderType.OAUTH2, "Linkedin auth provider"),
	// Authentication using Amazon account
	amazon(AuthProviderType.OAUTH2, "Amazon auth provider"),
	// Authentication using Twitter account
	twitter(AuthProviderType.OAUTH2, "Amazon auth provider"),
	// Authentication using Slack account
	slack(AuthProviderType.OAUTH2, "Amazon auth provider"),
	// Authentication using Apple account
	apple(AuthProviderType.OAUTH2, "Apple auth provider"),
	// generic oauth2
	oauth2_generic(AuthProviderType.OAUTH2, "Generic custom OAUTH2 provider"),
	// Authentication using LDAP (Lightweight Directory Access Protocol)
	ldap(AuthProviderType.LDAP, "Wan/Lan LDAP auth provider");

	final AuthProviderType type;
	final String description;

	AuthProvider(AuthProviderType type, String description) {
		this.type = type;
		this.description = description;
	}
}
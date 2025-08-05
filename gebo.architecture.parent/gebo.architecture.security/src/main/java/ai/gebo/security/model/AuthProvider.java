/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Gebo.ai comment agent
 *
 * Enum representing different authentication providers supported by the system.
 * These providers are used to identify the source of authentication such as
 * local database or third-party services.
 */
@Getter
public enum AuthProvider {
	// Local database authentication
	local(AuthProviderType.LOCAL_JWT, "Jwt local auth", false,Oauth2LoginModel.SPA),

	// Authentication using Facebook account
	facebook(AuthProviderType.OAUTH2, "Facebook auth provider", false,Oauth2LoginModel.SPA),

	// Authentication using Google account
	google(AuthProviderType.OAUTH2, "Google auth provider", false,Oauth2LoginModel.BACKEND),

	// Authentication using GitHub account
	github(AuthProviderType.OAUTH2, "GitHub auth provider", false,Oauth2LoginModel.SPA),

	// Authentication using Microsoft account "common" endpoints
	microsoft(AuthProviderType.OAUTH2, "Microsoft auth provider", false,Oauth2LoginModel.SPA),
	// Authentication using Microsoft account "multitenant" endpoints
	microsoft_multitenant(AuthProviderType.OAUTH2, "Microsoft multitenant auth provider", true,Oauth2LoginModel.SPA),
	// Authentication using Linkedin account
	linkedin(AuthProviderType.OAUTH2, "Linkedin auth provider", false,Oauth2LoginModel.SPA),
	// Authentication using Amazon account
	amazon(AuthProviderType.OAUTH2, "Amazon auth provider", false,Oauth2LoginModel.SPA),

	// Authentication using Slack account
	slack(AuthProviderType.OAUTH2, "Slack auth provider", false,Oauth2LoginModel.SPA),
	// Authentication using X account
	x(AuthProviderType.OAUTH2, "X auth provider", false,Oauth2LoginModel.SPA),
	// Authentication using Apple account
	apple(AuthProviderType.OAUTH2, "Apple auth provider", false,Oauth2LoginModel.SPA),
	// generic oauth2
	oauth2_generic(AuthProviderType.OAUTH2, "Generic custom OAUTH2 provider", false,Oauth2LoginModel.SPA),
	// Authentication using LDAP (Lightweight Directory Access Protocol)
	ldap(AuthProviderType.LDAP, "Wan/Lan LDAP auth provider", false,Oauth2LoginModel.SPA);

	final AuthProviderType type;
	final String description;
	final boolean multitenant;
	final Oauth2LoginModel loginModel;

	AuthProvider(AuthProviderType type, String description, boolean multitenant, Oauth2LoginModel loginModel) {
		this.type = type;
		this.description = description;
		this.multitenant = multitenant;
		this.loginModel = loginModel;
	}

	@AllArgsConstructor
	@Getter
	public static class AuthProviderDto {
		@NotNull
		final AuthProvider provider;
		@NotNull
		final AuthProviderType type;
		@NotNull
		final String description;
		@NotNull
		final boolean multitenant;
		@NotNull
		final Oauth2LoginModel loginModel;
	}

	public static List<AuthProviderDto> getOauth2Providers() {
		List<AuthProviderDto> providers = new ArrayList<>();
		AuthProvider[] values = values();
		for (AuthProvider authProvider : values) {
			switch (authProvider) {
			case local:
			case ldap: {
				continue;
			}
			default: {
				AuthProviderDto dto = new AuthProviderDto(authProvider, authProvider.getType(),
						authProvider.getDescription(), authProvider.multitenant, authProvider.loginModel);
				providers.add(dto);
			}

			}
		}
		return providers;
	}
}
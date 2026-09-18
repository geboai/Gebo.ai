/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.security.model.GeboLoginPolicy;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent Configuration properties for the Gebo application
 * security settings. These properties are prefixed with "ai.gebo.security" in
 * the configuration files.
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.security")
public class GeboSecurityConfig {

	// Nested Auth class instance for authentication properties.
	private final Auth auth = new Auth();
	// Nested SystemUser class instance: the platform's own, non-human identity.
	private final SystemUser systemUser = new SystemUser();
	@NotNull
	private GeboLoginPolicy loginPolicy = GeboLoginPolicy.REQUIRE_INVITATION;
	private List<Oauth2RuntimeConfiguration> oauth2configs = new ArrayList<Oauth2RuntimeConfiguration>();
	private Boolean oauth2UISetupEnabled = true;
	private Boolean oauth2LoginEnabled = true;
	private Boolean oauth2ResourceServerEnabled = true;
	// TTL (seconds) of the resource-server sync cache: under
	// TRUST_EVERY_OAUTH_IDENTITY an accepted bearer token triggers a user
	// create/sync only when its token is not (or no longer) in this cache, so the
	// sync runs at most once per token per TTL window instead of on every request.
	private long oauth2ResourceServerSyncCacheTtlSeconds = 300;
	private boolean useAcl = false;

	/**
	 * Represents configuration properties related to authentication. Includes token
	 * secret and expiration settings.
	 */
	public static class Auth {
		private String tokenSecret;
		private long tokenExpirationMsec;

		/**
		 * Retrieves the token secret used for authentication.
		 * 
		 * @return the token secret.
		 */
		public String getTokenSecret() {
			return tokenSecret;
		}

		/**
		 * Sets the token secret used for authentication.
		 * 
		 * @param tokenSecret the token secret.
		 */
		public void setTokenSecret(String tokenSecret) {
			this.tokenSecret = tokenSecret;
		}

		/**
		 * Retrieves the token expiration time in milliseconds.
		 * 
		 * @return the token expiration time in milliseconds.
		 */
		public long getTokenExpirationMsec() {
			return tokenExpirationMsec;
		}

		/**
		 * Sets the token expiration time in milliseconds.
		 * 
		 * @param tokenExpirationMsec the token expiration time in milliseconds.
		 */
		public void setTokenExpirationMsec(long tokenExpirationMsec) {
			this.tokenExpirationMsec = tokenExpirationMsec;
		}
	}

	/**
	 * The platform's own identity - the account the software authenticates as when it
	 * acts on its own behalf rather than on a user's.
	 *
	 * <p>
	 * It exists because a great deal of work happens on <b>no user's</b> thread: LLM
	 * clients are built at startup and on model replication, MCP connectors reconnect
	 * in the background, schedulers run jobs. Such a thread carries no
	 * {@code SecurityContext} - or, under {@code IdentityUtil.doAs}, one holding a
	 * locally-minted {@code UsernamePasswordAuthenticationToken} with <i>null
	 * credentials</i> - so there is no token to forward to a remote service. Rather
	 * than let those calls go out anonymously (or invent a shared static
	 * password-equivalent), the platform mints a real, short-lived
	 * {@code LOCAL_JWT} for this identity; see
	 * {@code ai.gebo.security.services.IGeboSystemUserService}.
	 * </p>
	 *
	 * <p>
	 * It is <b>virtual</b>: it has no Mongo document and cannot be created, logged
	 * into, or federated. See {@code GeboSystemUserServiceImpl} for how each of those
	 * is prevented.
	 * </p>
	 *
	 * <pre>
	 * ai.gebo.security.system-user:
	 *   username: heimdall@bifrost.gebo.ai
	 *   roles: [SYSTEM, ADMIN]
	 *   token-expiration-msec: 300000
	 * </pre>
	 */
	public static class SystemUser {

		/** Username when none is configured. Not a routable mailbox - it is not a person. */
		public static final String DEFAULT_USERNAME = "heimdall@bifrost.gebo.ai";

		/** Marks the principal as the platform itself. */
		public static final String SYSTEM_ROLE = "SYSTEM";

		/** Full administrative rights. */
		public static final String ADMIN_ROLE = "ADMIN";

		private String username = DEFAULT_USERNAME;

		/**
		 * Roles granted to the system identity.
		 *
		 * <p>
		 * {@code ADMIN} is in the default set deliberately: the platform runs
		 * {@code GrantedAuthorityDefaults("")} (no {@code ROLE_} prefix), so an
		 * {@code ADMIN} authority is exactly what the ~90
		 * {@code @PreAuthorize("hasRole('ADMIN')")} endpoints test for. Granting it here
		 * makes every one of them callable by the system identity without touching a
		 * single controller.
		 * </p>
		 */
		private List<String> roles = new ArrayList<>(List.of(SYSTEM_ROLE, ADMIN_ROLE));

		/**
		 * Lifetime of a minted system token. Short by design: a fresh one is created per
		 * outgoing call, so it never needs to outlive it.
		 */
		private long tokenExpirationMsec = 300000L;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public List<String> getRoles() {
			return roles;
		}

		public void setRoles(List<String> roles) {
			this.roles = roles;
		}

		public long getTokenExpirationMsec() {
			return tokenExpirationMsec;
		}

		public void setTokenExpirationMsec(long tokenExpirationMsec) {
			this.tokenExpirationMsec = tokenExpirationMsec;
		}
	}

	/**
	 * Retrieves the Auth instance for authentication configuration.
	 *
	 * @return the Auth instance.
	 */
	public Auth getAuth() {
		return auth;
	}

	/**
	 * @return the platform's own (virtual) identity configuration
	 */
	public SystemUser getSystemUser() {
		return systemUser;
	}

	public GeboLoginPolicy getLoginPolicy() {
		return loginPolicy;
	}

	public void setLoginPolicy(GeboLoginPolicy loginPolicy) {
		this.loginPolicy = loginPolicy;
	}

	public List<Oauth2RuntimeConfiguration> getOauth2configs() {
		return oauth2configs;
	}

	public void setOauth2configs(List<Oauth2RuntimeConfiguration> oauth2configs) {
		this.oauth2configs = oauth2configs;
	}

	public Boolean getOauth2UISetupEnabled() {
		return oauth2UISetupEnabled;
	}

	public void setOauth2UISetupEnabled(Boolean oauth2uiSetupEnabled) {
		oauth2UISetupEnabled = oauth2uiSetupEnabled;
	}

	public Boolean getOauth2LoginEnabled() {
		return oauth2LoginEnabled;
	}

	public void setOauth2LoginEnabled(Boolean oauth2Enabled) {
		this.oauth2LoginEnabled = oauth2Enabled;
	}

	public long getOauth2ResourceServerSyncCacheTtlSeconds() {
		return oauth2ResourceServerSyncCacheTtlSeconds;
	}

	public void setOauth2ResourceServerSyncCacheTtlSeconds(long oauth2ResourceServerSyncCacheTtlSeconds) {
		this.oauth2ResourceServerSyncCacheTtlSeconds = oauth2ResourceServerSyncCacheTtlSeconds;
	}

	public Boolean getOauth2ResourceServerEnabled() {
		return oauth2ResourceServerEnabled;
	}

	public void setOauth2ResourceServerEnabled(Boolean oauth2ResourceServerEnabled) {
		this.oauth2ResourceServerEnabled = oauth2ResourceServerEnabled;
	}

	public boolean isUseAcl() {
		return useAcl;
	}

	public void setUseAcl(boolean useAcl) {
		this.useAcl = useAcl;
	}

}
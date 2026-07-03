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

import ai.gebo.acl.AclGrantType;
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
	@NotNull
	private GeboLoginPolicy loginPolicy = GeboLoginPolicy.REQUIRE_INVITATION;
	private List<Oauth2RuntimeConfiguration> oauth2configs = new ArrayList<Oauth2RuntimeConfiguration>();
	private Boolean oauth2UISetupEnabled = true;
	private Boolean oauth2LoginEnabled = true;
	private Boolean oauth2ResourceServerEnabled = true;
	private boolean useAcl = false;

	/**
	 * Policy, per {@link AclGrantType}, deciding what an <em>empty</em> ACL means
	 * while the platform runs in ACL mode. When {@code false} (the default) an
	 * object with no acl aliases is only accessible by admins; when {@code true}
	 * an empty acl is treated as "everyone can perform that grant".
	 */
	private final EmptyAclGrantsEveryone emptyAclGrantsEveryone = new EmptyAclGrantsEveryone();

	/**
	 * Per-grant flags telling whether an empty acl grants access to everyone.
	 * Bound from {@code ai.gebo.security.empty-acl-grants-everyone.*}.
	 */
	public static class EmptyAclGrantsEveryone {
		private boolean read = false;
		private boolean write = false;
		private boolean execute = false;

		public boolean isRead() {
			return read;
		}

		public void setRead(boolean read) {
			this.read = read;
		}

		public boolean isWrite() {
			return write;
		}

		public void setWrite(boolean write) {
			this.write = write;
		}

		public boolean isExecute() {
			return execute;
		}

		public void setExecute(boolean execute) {
			this.execute = execute;
		}
	}

	public EmptyAclGrantsEveryone getEmptyAclGrantsEveryone() {
		return emptyAclGrantsEveryone;
	}

	/**
	 * Tells whether an empty acl should be treated as "everyone can do it" for the
	 * given grant, according to the configured {@link EmptyAclGrantsEveryone}
	 * policy.
	 *
	 * @param grant the grant being checked
	 * @return {@code true} if an empty acl grants the given action to everyone
	 */
	public boolean isEmptyAclGrantsEveryone(AclGrantType grant) {
		if (grant == null)
			return false;
		switch (grant) {
		case READ:
			return emptyAclGrantsEveryone.isRead();
		case WRITE:
			return emptyAclGrantsEveryone.isWrite();
		case EXECUTE:
			return emptyAclGrantsEveryone.isExecute();
		default:
			return false;
		}
	}

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
	 * Retrieves the Auth instance for authentication configuration.
	 * 
	 * @return the Auth instance.
	 */
	public Auth getAuth() {
		return auth;
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
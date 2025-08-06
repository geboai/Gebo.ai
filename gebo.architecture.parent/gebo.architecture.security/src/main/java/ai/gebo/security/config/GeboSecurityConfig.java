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
	@NotNull
	private GeboLoginPolicy loginPolicy = GeboLoginPolicy.REQUIRE_INVITATION;
	private List<Oauth2RuntimeConfiguration> oauth2configs=new ArrayList<Oauth2RuntimeConfiguration>();
	private Boolean oauth2UISetupEnabled=true;
	private Boolean oauth2Enabled = true;


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

	public Boolean getOauth2Enabled() {
		return oauth2Enabled;
	}

	public void setOauth2Enabled(Boolean oauth2Enabled) {
		this.oauth2Enabled = oauth2Enabled;
	}

}
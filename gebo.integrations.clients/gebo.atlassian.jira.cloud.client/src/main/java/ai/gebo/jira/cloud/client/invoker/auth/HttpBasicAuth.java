/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.invoker.auth;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpHeaders;

import org.springframework.util.MultiValueMap;

/**
 * AI generated comments
 * 
 * HTTP Basic Authentication implementation for Jira Cloud client. This class handles
 * creating and applying Basic Authentication headers to outgoing HTTP requests
 * by encoding the username and password credentials.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
public class HttpBasicAuth implements Authentication {
	/** The username for authentication */
	private String username;
	/** The password for authentication */
	private String password;

	/**
	 * Gets the username used for authentication.
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username to be used for authentication.
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password used for authentication.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password to be used for authentication.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Applies HTTP Basic Authentication to the request headers.
	 * Creates and adds the Authorization header with Base64 encoded credentials
	 * in the format "username:password".
	 * 
	 * @param queryParams the query parameters for the request
	 * @param headerParams the HTTP headers for the request
	 */
	@Override
	public void applyToParams(MultiValueMap<String, String> queryParams, HttpHeaders headerParams) {
		if (username == null && password == null) {
			return;
		}
		String str = (username == null ? "" : username) + ":" + (password == null ? "" : password);
		headerParams.add(HttpHeaders.AUTHORIZATION,
				"Basic " + new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8))));
		// headerParams.add(HttpHeaders.AUTHORIZATION,
		// Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
	}
}
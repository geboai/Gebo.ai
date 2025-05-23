/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.invoker.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

/**
 * AI generated comments
 * This class provides API key-based authentication for the Jira Cloud client.
 * It implements the Authentication interface and handles adding API key
 * credentials either as query parameters or header values.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
public class ApiKeyAuth implements Authentication {
	/** The location where the API key should be applied (query or header) */
	private final String location;
	
	/** The name of the parameter that will hold the API key */
	private final String paramName;

	/** The API key value */
	private String apiKey;
	
	/** Optional prefix for the API key (like "Bearer") */
	private String apiKeyPrefix;

	

	/**
	 * Constructs an ApiKeyAuth instance with specified location and parameter name.
	 * 
	 * @param location where the API key should be added (query or header)
	 * @param paramName the name of the parameter to hold the API key
	 */
	public ApiKeyAuth(String location, String paramName) {
		this.location = location;
		this.paramName = paramName;
	}

	/**
	 * Gets the location where the API key should be applied.
	 * 
	 * @return the location (query or header)
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets the name of the parameter that holds the API key.
	 * 
	 * @return the parameter name
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * Gets the API key value.
	 * 
	 * @return the API key
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Sets the API key value.
	 * 
	 * @param apiKey the API key to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * Gets the API key prefix.
	 * 
	 * @return the API key prefix
	 */
	public String getApiKeyPrefix() {
		return apiKeyPrefix;
	}

	/**
	 * Sets the API key prefix.
	 * 
	 * @param apiKeyPrefix the prefix to prepend to the API key (e.g., "Bearer")
	 */
	public void setApiKeyPrefix(String apiKeyPrefix) {
		this.apiKeyPrefix = apiKeyPrefix;
	}

	/**
	 * Applies the API key authentication to either query parameters or headers
	 * based on the configured location.
	 * 
	 * @param queryParams the query parameters collection to potentially modify
	 * @param headerParams the headers collection to potentially modify
	 */
	@Override
	public void applyToParams(MultiValueMap<String, String> queryParams, HttpHeaders headerParams) {
		if (apiKey == null) {
			return;
		}
		String value;
		if (apiKeyPrefix != null) {
			value = apiKeyPrefix + " " + apiKey;
		} else {
			value = apiKey;
		}
		if (location.equals("query")) {
			queryParams.add(paramName, value);
		} else if (location.equals("header")) {
			headerParams.add(paramName, value);
		}
	}
}
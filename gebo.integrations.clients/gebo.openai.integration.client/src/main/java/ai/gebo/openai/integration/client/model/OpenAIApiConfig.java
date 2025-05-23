/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.model;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;

public class OpenAIApiConfig {
	public static final String OPENAI_BASE_PATH = "https://api.openai.com/";
	private String providerId = "openai";
	private String basePath = OPENAI_BASE_PATH;
	private String apiKey = null;
	private boolean apiKeyMandatory = true;

	public OpenAIApiConfig() {

	}

	public OpenAIApiConfig(OpenAIApiConfig c) {
		this.basePath = c.basePath;
		this.apiKey = c.apiKey;
		this.providerId = c.providerId;
		this.apiKeyMandatory = c.apiKeyMandatory;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public boolean isApiKeyMandatory() {
		return apiKeyMandatory;
	}

	public void setApiKeyMandatory(boolean apiKeyMandatory) {
		this.apiKeyMandatory = apiKeyMandatory;
	}

	public static OpenAIApiConfig of(GBaseModelConfig config, boolean apiKeyMandatory) {
		OpenAIApiConfig c = new OpenAIApiConfig();
		c.setApiKeyMandatory(apiKeyMandatory);

		if (config.getApiSecretCode() != null)
			c.setApiKey(config.getApiSecretCode());
		if (config.getBaseUrl() != null)
			c.setBasePath(config.getBaseUrl());
		return c;
	}
}

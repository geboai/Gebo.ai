/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.repository.JiraSystemRepository;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ai.gebo.architecture.search.model.SearchCallParameters;

/**
 * AI generated comments
 * 
 * Factory class responsible for creating and configuring Jira API clients.
 * This service connects to Jira systems using credentials stored in the secrets service.
 */
@Service
class JiraApiClientFactory {
	/** Service for accessing secrets and credentials stored in the Gebo system */
	@Autowired
	private IGeboSecretsAccessService secretService;
	
	/** Repository for retrieving Jira system configurations */
	@Autowired
	private JiraSystemRepository systemsRepository;

	/**
	 * Creates an API client for a Jira system identified by its code.
	 * 
	 * @param code The unique identifier for the Jira system
	 * @return Configured ApiClient instance for the specified Jira system
	 * @throws GeboCryptSecretException If there is an issue accessing the secret
	 * @throws RuntimeException If no Jira system is found with the provided code
	 */
	ApiClient getApiClient(String code) throws GeboCryptSecretException {
		Optional<GJiraSystem> data = systemsRepository.findById(code);
		if (!data.isPresent()) {
			throw new RuntimeException("No such JIRA system found: " + code);
		}
		return getApiClient(data.get());
	}

	/**
	 * Creates an API client for a given Jira system using its configuration.
	 * 
	 * @param s The Jira system configuration object
	 * @return Configured ApiClient instance
	 * @throws GeboCryptSecretException If there is an issue accessing the secret
	 * @throws RuntimeException If the secret type is not supported
	 */
	ApiClient getApiClient(GJiraSystem s) throws GeboCryptSecretException {
		return getApiClient(s, (SearchCallParameters) null);
	}

	/**
	 * A client of the Jira system for a search: its HTTP calls time out as the search
	 * call parameters say (the clients of the content integration, given by
	 * {@link #getApiClient(GJiraSystem)}, keep the client's own settings).
	 */
	ApiClient getApiClient(GJiraSystem s, SearchCallParameters searchParameters) throws GeboCryptSecretException {
		String baseUri = s.getBaseUri();
		String secretCode = s.getSecretCode();
		AbstractGeboSecretContent secretContent = secretService.getSecretContentById(secretCode);
		switch (secretContent.type()) {
		case TOKEN: {
			// Configure API client with token-based authentication
			GeboTokenContent token = (GeboTokenContent) secretContent;
			ApiClient apiClient = searchParameters != null ? new ApiClient(searchRestTemplate(searchParameters))
					: new ApiClient();
			apiClient.setBasePath(baseUri);
			apiClient.setPassword(token.getToken());
			apiClient.setUsername(token.getUser());
			return apiClient;
		}
		default: {
			throw new RuntimeException("Unsupported secret type: " + secretContent.type());
		}
		}

	}


	/**
	 * The RestTemplate of a search client: as the generated client builds its own
	 * (buffered, so a response can be read more than once), with the search timeouts.
	 */
	static RestTemplate searchRestTemplate(SearchCallParameters parameters) {
		final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(parameters.connectTimeout());
		requestFactory.setReadTimeout(parameters.readTimeout());
		return new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
	}
}

/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceConnection;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.architecture.search.model.SearchCallParameters;

/**
 * Factory class responsible for creating Confluence connections.
 * This service handles the creation of both cloud and on-premise Confluence connections
 * based on the provided system configuration.
 * 
 * AI generated comments
 */
@Service
class ConfluenceConnectionFactory {
	/**
	 * Service for accessing secrets from the Gebo secrets management system.
	 */
	@Autowired
	private IGeboSecretsAccessService secretService;
	
	/**
	 * Service that provides REST template functionality with additional wrapping.
	 */
	@Autowired
	private RestTemplateWrapperService restWrapperService;

	/**
	 * Creates a Cloud Confluence connection based on the provided system configuration.
	 * 
	 * @param s The Confluence system configuration containing base URI and secret code
	 * @return A configured CloudConfluenceConnection with appropriate credentials
	 * @throws GeboCryptSecretException If there is an issue retrieving or decrypting the secret
	 */
	CloudConfluenceConnection getCloudConnection(GConfluenceSystem s) throws GeboCryptSecretException {
		return getCloudConnection(s, null);
	}

	/**
	 * A connection to the cloud system for a search: its HTTP calls time out as the
	 * search call parameters say (the connections of the content integration, given by
	 * {@link #getCloudConnection(GConfluenceSystem)}, use the shared client as before).
	 */
	CloudConfluenceConnection getCloudConnection(GConfluenceSystem s, SearchCallParameters searchParameters)
			throws GeboCryptSecretException {
		CloudConfluenceConnection api = new CloudConfluenceConnection(client(searchParameters));
		api.setBaseUrl(s.getBaseUri());
		AbstractGeboSecretContent secret = secretService.getSecretContentById(s.getSecretCode());
		GeboTokenContent credentials = (GeboTokenContent) secret;
		api.setUsername(credentials.getUser());
		api.setPassword(credentials.getToken());
		return api;
	}

	/**
	 * Creates an On-Premise Confluence connection based on the provided system configuration.
	 * 
	 * @param s The Confluence system configuration containing base URI and secret code
	 * @return A configured OnPremiseConfluenceConnection with appropriate credentials
	 * @throws GeboCryptSecretException If there is an issue retrieving or decrypting the secret
	 */
	OnPremiseConfluenceConnection getOnPremiseConnection(GConfluenceSystem s) throws GeboCryptSecretException {
		return getOnPremiseConnection(s, null);
	}

	/**
	 * A connection to the on-premise system for a search: its HTTP calls time out as the
	 * search call parameters say (the connections of the content integration, given by
	 * {@link #getOnPremiseConnection(GConfluenceSystem)}, use the shared client as before).
	 */
	OnPremiseConfluenceConnection getOnPremiseConnection(GConfluenceSystem s, SearchCallParameters searchParameters)
			throws GeboCryptSecretException {
		OnPremiseConfluenceConnection api = new OnPremiseConfluenceConnection(client(searchParameters));
		api.setBaseUrl(s.getBaseUri());
		AbstractGeboSecretContent secret = secretService.getSecretContentById(s.getSecretCode());
		GeboUsernamePasswordContent credentials = (GeboUsernamePasswordContent) secret;
		api.setUsername(credentials.getUsername());
		api.setPassword(credentials.getPassword());
		return api;

	}

	/** The shared client, or one of its own timing out as the search parameters say. */
	private RestTemplateWrapperService client(SearchCallParameters searchParameters) {
		return searchParameters != null
				? RestTemplateWrapperService.withTimeouts(searchParameters.connectTimeout(),
						searchParameters.readTimeout())
				: restWrapperService;
	}
}

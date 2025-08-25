/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.services.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.services.IGOauth2AccessTokenService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * Service implementation for handling OAuth2 access tokens.
 * Provides functionality to retrieve access tokens using client credentials.
 * AI generated comments
 */
@Service
public class GOauth2AccessTokenServiceImpl implements IGOauth2AccessTokenService {
	@Autowired
	IGeboSecretsAccessService secretAccessService;
	@Autowired
	RestTemplateWrapperService restTemplateWrapper;

	/**
	 * Default constructor.
	 */
	public GOauth2AccessTokenServiceImpl() {

	}

	/**
	 * Retrieves an access token from the OAuth2 provider using client credentials.
	 *
	 * @param accessUrl the URL to obtain the token from
	 * @param scope the scope of the access request
	 * @param secretId the secret ID to retrieve credentials
	 * @return the OAuth2 access token
	 * @throws GeboRestIntegrationException if there's an error during REST integration
	 * @throws GeboCryptSecretException if there's an error in decrypting the secret
	 */
	@Override
	public String getAccessToken(String accessUrl, String scope, String secretId)
			throws GeboRestIntegrationException, GeboCryptSecretException {

		// Retrieve secret content using the secret ID
		AbstractGeboSecretContent secretObject = secretAccessService.getSecretContentById(secretId);
		GeboOauth2SecretContent oauth2coords = null;

		// Verify that the secret type is OAUTH2_STANDARD
		if (secretObject.type() != null && secretObject.type() == GeboSecretType.OAUTH2_STANDARD) {
			oauth2coords = (GeboOauth2SecretContent) secretObject;
		} else {
			throw new IllegalStateException(
					"Oauth2 access with secret of type:" + secretObject.getClass().getName() + " type");
		}

		// 1) Build the token endpoint URL
		String tokenUrl = String.format(accessUrl);

		// 2) Define the scope (resource + .default)
		// String scope = "https://graph.microsoft.com/.default";

		// 3) Prepare the form data
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("grant_type", "client_credentials");
		formData.add("client_id", oauth2coords.getClientId());
		formData.add("client_secret", oauth2coords.getSecret());
		formData.add("scope", scope);

		// 4) Set headers for x-www-form-urlencoded
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 5) Wrap form data + headers in an HttpEntity
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

		// 6) Use RestTemplate to post the request
		ResponseEntity<Map> value = restTemplateWrapper.exchange(tokenUrl, HttpMethod.POST, requestEntity, Map.class);
		Map<String, Object> response = value.getBody();

		// 7) Extract the "access_token" field from the response
		return (String) response.get("access_token");
	}
}
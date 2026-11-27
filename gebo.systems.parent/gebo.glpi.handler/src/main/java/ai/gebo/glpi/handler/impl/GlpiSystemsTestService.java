/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.glpi.client.invoker.ApiClient;
import ai.gebo.glpi.client.model.Session;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;

/**
 * Checks a GLPI system before it is stored: both credentials are present and of
 * the right kind, GLPI grants a token with them, and an authenticated call
 * (the current session) answers.
 */
@Service
public class GlpiSystemsTestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlpiSystemsTestService.class);

	private final GlpiApiClientFactory apiClientFactory;

	public GlpiSystemsTestService(GlpiApiClientFactory apiClientFactory) {
		this.apiClientFactory = apiClientFactory;
	}

	private static boolean blank(String value) {
		return value == null || value.isBlank();
	}

	public OperationStatus<GGlpiSystem> testGlpiSystem(GGlpiSystem system) {
		OperationStatus<GGlpiSystem> outValue = new OperationStatus<GGlpiSystem>();
		outValue.setResult(system);
		if (GlpiApiClientFactory.rootUrlOf(system.getBaseUri()) == null) {
			outValue.getMessages().add(GUserMessage.errorMessage("No base URI configured",
					"The url of the GLPI installation is missing"));
		}
		if (blank(system.getOauthClientSecretCode())) {
			outValue.getMessages().add(GUserMessage.errorMessage("No OAuth client credentials",
					"The client id and secret of the OAuth client registered in GLPI are missing"));
		}
		if (blank(system.getSecretCode())) {
			outValue.getMessages().add(GUserMessage.errorMessage("No GLPI user credentials",
					"The username and password of the GLPI user are missing"));
		}
		if (outValue.isHasErrorMessages()) {
			return outValue;
		}
		try {
			String token = apiClientFactory.requestNewToken(system);
			ApiClient apiClient = apiClientFactory.getApiClient(system);
			apiClient.setAccessToken(token);
			Session session = new GlpiRemoteAccess(apiClient).session();
			String user = session != null
					? (session.getFriendlyName() != null ? session.getFriendlyName() : session.getName())
					: null;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("GLPI system " + system.getBaseUri() + " test passed, session user " + user);
			}
			outValue.getMessages().add(GUserMessage.successMessage("GLPI system configuration OK",
					"Connected to GLPI" + (user != null ? " as " + user : "")));
		} catch (GeboCryptSecretException e) {
			LOGGER.error("GLPI system test: secret access error", e);
			outValue.getMessages().add(GUserMessage.errorMessage("Gebo.ai secret credentials access error",
					"Something went wrong in the system credentials storing system"));
		} catch (IllegalStateException e) {
			LOGGER.error("GLPI system test: wrong credentials setup", e);
			outValue.getMessages().add(GUserMessage.errorMessage("Wrong credential format", e.getMessage()));
		} catch (HttpClientErrorException e) {
			LOGGER.error("GLPI system test: GLPI refused the call", e);
			outValue.getMessages().add(GUserMessage.errorMessage("GLPI refused the credentials",
					"GLPI answered " + e.getStatusCode().value() + ": " + e.getResponseBodyAsString()
							+ ". Check the OAuth client (password grant and api scope allowed), the user and its password."));
		} catch (RestClientResponseException e) {
			LOGGER.error("GLPI system test: GLPI error", e);
			outValue.getMessages().add(GUserMessage.errorMessage("GLPI error",
					"GLPI answered " + e.getStatusCode().value() + ": " + e.getResponseBodyAsString()));
		} catch (ResourceAccessException e) {
			LOGGER.error("GLPI system test: GLPI unreachable", e);
			outValue.getMessages().add(GUserMessage.errorMessage("Cannot reach GLPI",
					"Check the base URI of the GLPI installation. " + e.getMessage()));
		} catch (Throwable th) {
			LOGGER.error("GLPI system test failed", th);
			outValue.getMessages().add(GUserMessage.errorMessage("Cannot access GLPI", th));
		}
		return outValue;
	}
}

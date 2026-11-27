/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.openproject.handler.GOpenProjectSystem;

/**
 * Checks an OpenProject system before it is saved: base URI and credentials set,
 * then an authenticated call to the account the credentials belong to.
 */
@Service
public class OpenProjectSystemsTestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenProjectSystemsTestService.class);
	private final OpenProjectApiClientFactory clientFactory;

	public OpenProjectSystemsTestService(OpenProjectApiClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public OperationStatus<GOpenProjectSystem> testOpenProjectSystem(GOpenProjectSystem system) {
		OperationStatus<GOpenProjectSystem> status = new OperationStatus<GOpenProjectSystem>();
		status.setResult(system);
		if (system.getBaseUri() == null || system.getBaseUri().isBlank()) {
			status.getMessages().add(GUserMessage.errorMessage("No base URI", "The OpenProject server address is missing"));
			return status;
		}
		if (system.getSecretCode() == null || system.getSecretCode().isBlank()) {
			status.getMessages()
					.add(GUserMessage.errorMessage("No credentials", "The OpenProject credentials are missing"));
			return status;
		}
		try {
			Map<String, Object> me = clientFactory.getConnection(system).get("/users/me");
			String login = OpenProjectHal.text(me, "login");
			if (login == null) {
				login = OpenProjectHal.text(me, "name");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenProject system {} answered for the account {}", system.getBaseUri(), login);
			}
			status.getMessages().add(GUserMessage.successMessage("OpenProject connected",
					"Connected to " + system.getBaseUri() + (login != null ? " as " + login : "")));
		} catch (GeboCryptSecretException e) {
			LOGGER.error("Cannot read the OpenProject credentials " + system.getSecretCode(), e);
			status.getMessages().add(GUserMessage.errorMessage("Credentials access error",
					"Something went wrong reading the stored credentials"));
		} catch (IllegalArgumentException e) {
			status.getMessages().add(GUserMessage.errorMessage("Wrong credentials type", e.getMessage()));
		} catch (RestClientResponseException e) {
			LOGGER.warn("OpenProject connection test to " + system.getBaseUri() + " failed", e);
			int code = e.getStatusCode().value();
			String detail = code == 401 || code == 403 ? OpenProjectErrors.refused(e)
					: code == 404
							? "No OpenProject API v3 at this address (404): check the base URI, without /api/v3"
							: OpenProjectErrors.refused(e);
			status.getMessages().add(GUserMessage.errorMessage("Cannot connect to OpenProject", detail));
		} catch (RestClientException e) {
			LOGGER.warn("OpenProject connection test to " + system.getBaseUri() + " failed", e);
			status.getMessages().add(GUserMessage.errorMessage("Cannot reach OpenProject",
					OpenProjectErrors.unreachable(e, system.getBaseUri())));
		}
		return status;
	}
}

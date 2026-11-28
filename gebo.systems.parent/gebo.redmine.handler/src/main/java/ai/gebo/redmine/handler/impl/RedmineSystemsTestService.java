/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.redmine.client.model.MyAccount;
import ai.gebo.redmine.handler.GRedmineSystem;

/**
 * Checks a Redmine system before it is saved: base URI and credentials set,
 * then an authenticated call to the account the credentials belong to.
 */
@Service
public class RedmineSystemsTestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedmineSystemsTestService.class);
	private final RedmineApiClientFactory clientFactory;

	public RedmineSystemsTestService(RedmineApiClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public OperationStatus<GRedmineSystem> testRedmineSystem(GRedmineSystem system) {
		OperationStatus<GRedmineSystem> status = new OperationStatus<GRedmineSystem>();
		status.setResult(system);
		if (system.getBaseUri() == null || system.getBaseUri().isBlank()) {
			status.getMessages().add(GUserMessage.errorMessage("No base URI", "The Redmine server address is missing"));
			return status;
		}
		if (system.getSecretCode() == null || system.getSecretCode().isBlank()) {
			status.getMessages()
					.add(GUserMessage.errorMessage("No credentials", "The Redmine credentials are missing"));
			return status;
		}
		try {
			MyAccount account = clientFactory.getConnection(system).myAccount().getMyAccount(RedmineConnection.JSON,
					null);
			String login = account != null && account.getUser() != null ? account.getUser().getLogin() : null;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine system {} answered for the account {}", system.getBaseUri(), login);
			}
			status.getMessages().add(GUserMessage.successMessage("Redmine connected",
					"Connected to " + system.getBaseUri() + (login != null ? " as " + login : "")));
		} catch (GeboCryptSecretException e) {
			LOGGER.error("Cannot read the Redmine credentials " + system.getSecretCode(), e);
			status.getMessages().add(GUserMessage.errorMessage("Credentials access error",
					"Something went wrong reading the stored credentials"));
		} catch (IllegalArgumentException e) {
			status.getMessages().add(GUserMessage.errorMessage("Wrong credentials type", e.getMessage()));
		} catch (RestClientResponseException e) {
			LOGGER.warn("Redmine connection test to " + system.getBaseUri() + " failed", e);
			int code = e.getStatusCode().value();
			String detail = code == 401 || code == 403
					? "Redmine refused the credentials (" + code + ")"
					: code == 404
							? "No Redmine REST API at this address (404): check the base URI and that the REST web service is enabled in Administration, Settings, API"
							: "Redmine answered " + code + " " + e.getStatusText();
			status.getMessages().add(GUserMessage.errorMessage("Cannot connect to Redmine", detail));
		} catch (RestClientException e) {
			LOGGER.warn("Redmine connection test to " + system.getBaseUri() + " failed", e);
			status.getMessages().add(GUserMessage.errorMessage("Cannot reach Redmine",
					RedmineErrors.unreachable(e, system.getBaseUri())));
		}
		return status;
	}
}

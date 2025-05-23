/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * This service handles testing and validation of Confluence system configurations.
 * It supports both Cloud and On-Premise Confluence versions by testing the connection
 * with the provided credentials.
 */
package ai.gebo.atlassian.confluence.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceConnection;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceSpaceApi;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesList;
import ai.gebo.atlassian.confluence.handler.ConfluenceVersion;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceSpaceApi;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesList;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.GeboInvalidAccessException;
import ai.gebo.restintegration.abstraction.layer.GeboNotFoundException;
import ai.gebo.restintegration.abstraction.layer.GeboRemoteBackendErrorException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

@Service
public class ConfluenceSystemsTestService {
	@Autowired
	IGeboSecretsAccessService secretsService;
	@Autowired
	RestTemplateWrapperService templateWrapperService;

	/**
	 * Tests the connectivity and configuration of a Confluence system.
	 * Validates the appropriate credentials based on the Confluence version
	 * and attempts to connect to the system.
	 * 
	 * @param object The Confluence system configuration to test
	 * @return OperationStatus containing the result and any error messages
	 */
	public OperationStatus<GConfluenceSystem> testConfluenceSystem(GConfluenceSystem object) {
		OperationStatus<GConfluenceSystem> outValue = null;
		outValue = new OperationStatus<GConfluenceSystem>();
		outValue.setResult(object);
		String secretCode = object.getSecretCode();
		if (secretCode != null && secretCode.trim().length() > 0) {
			AbstractGeboSecretContent secretContent;
			try {
				secretContent = secretsService.getSecretContentById(secretCode);

				if (object.getConfluenceVersion() != null) {
					ConfluenceVersion confluenceVersion = object.getConfluenceVersion();
					if (confluenceVersion == ConfluenceVersion.CLOUD) {
						outValue = testCloudConfluenceSystem(secretContent, object);
					} else if (confluenceVersion == ConfluenceVersion.ONPREMISE7X) {
						outValue = testOnPremiseConfluenceSystem(secretContent, object);
					}

				} else {
					outValue.getMessages().add(GUserMessage.errorMessage("No confluence version inserted",
							"The confluence version field data is missing"));
				}
			} catch (GeboCryptSecretException e) {
				outValue.getMessages().add(GUserMessage.errorMessage("Gebo.ai secret credentials access error",
						"Something went wrong in the system credentials storing system"));
			}
		} else

		{
			outValue.getMessages().add(
					GUserMessage.errorMessage("No credentials inserted", "The confluence credentials are missing"));
		}
		return outValue;
	}

	/**
	 * Enriches the operation status with appropriate error messages based on the exception type.
	 * Translates technical exceptions into user-friendly messages.
	 * 
	 * @param out The operation status to enrich with error messages
	 * @param e The exception that occurred during testing
	 */
	private void enrichWithExceptionTranscoding(OperationStatus<GConfluenceSystem> out, Throwable e) {
		out.getMessages().clear();
		if (e instanceof GeboRemoteBackendErrorException) {
			out.getMessages()
					.add(GUserMessage.errorMessage("The configured confluence backend is in error state (5XX)", e));
		} else if (e instanceof GeboInvalidAccessException) {
			out.getMessages().add(GUserMessage.errorMessage("Inserted credentials are invalid", e));
		} else if (e instanceof GeboNotFoundException) {
			out.getMessages().add(GUserMessage
					.errorMessage("The remote system address seems to point to an unavailable resource (4XX)", e));
		} else {
			out.getMessages().add(GUserMessage
					.errorMessage("Wrong Confluence system configuration or remote system in an incoherent state", e));

		}
	}

	/**
	 * Tests an On-Premise Confluence system with provided credentials.
	 * Expects username/password credentials and attempts to fetch spaces
	 * to validate the connection.
	 * 
	 * @param secretContent The secret content containing credentials
	 * @param object The Confluence system configuration to test
	 * @return OperationStatus containing the result and any error messages
	 */
	private OperationStatus<GConfluenceSystem> testOnPremiseConfluenceSystem(AbstractGeboSecretContent secretContent,
			GConfluenceSystem object) {
		OperationStatus<GConfluenceSystem> out = OperationStatus.of(object);

		if (secretContent.type() == GeboSecretType.USERNAME_PASSWORD) {
			GeboUsernamePasswordContent up = (GeboUsernamePasswordContent) secretContent;
			OnPremiseConfluenceConnection api = new OnPremiseConfluenceConnection(templateWrapperService);
			api.setBaseUrl(object.getBaseUri());
			api.setUsername(up.getUsername());
			api.setPassword(up.getPassword());
			OnPremiseConfluenceSpaceApi browser = new OnPremiseConfluenceSpaceApi(api);
			try {
				OnPremiseConfluenceSpacesList result = browser.getAllSpaces(0, 500, "global");
			} catch (Throwable th) {
				enrichWithExceptionTranscoding(out, th);
			} finally {
			}
		} else {
			out.getMessages().clear();
			out.getMessages().add(GUserMessage.errorMessage("Wrong credential format",
					"For Confluence on premise software the required credentials format are username+password"));
		}
		return out;
	}

	/**
	 * Tests a Cloud Confluence system with provided credentials.
	 * Expects token-based credentials and attempts to fetch spaces
	 * to validate the connection.
	 * 
	 * @param secretContent The secret content containing credentials
	 * @param object The Confluence system configuration to test
	 * @return OperationStatus containing the result and any error messages
	 */
	private OperationStatus<GConfluenceSystem> testCloudConfluenceSystem(AbstractGeboSecretContent secretContent,
			GConfluenceSystem object) {
		OperationStatus<GConfluenceSystem> out = OperationStatus.of(object);
		if (secretContent.type() == GeboSecretType.TOKEN) {
			GeboTokenContent up = (GeboTokenContent) secretContent;
			CloudConfluenceConnection api = new CloudConfluenceConnection(templateWrapperService);
			api.setBaseUrl(object.getBaseUri());
			api.setUsername(up.getUser());
			api.setPassword(up.getToken());
			CloudConfluenceSpaceApi spaceApi = new CloudConfluenceSpaceApi(api);
			try {
				CloudConfluenceSpacesList result = spaceApi.getAllSpaces(0, 500, null);
			} catch (Throwable th) {
				enrichWithExceptionTranscoding(out, th);
			} finally {
			}
		} else {
			out.getMessages().clear();
			out.getMessages().add(GUserMessage.errorMessage("Wrong credential format",
					"For Confluence on premise software the required credentials format are username+password"));
		}
		return out;
	}
}
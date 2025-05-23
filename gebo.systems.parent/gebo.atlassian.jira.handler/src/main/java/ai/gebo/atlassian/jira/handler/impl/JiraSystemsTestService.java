/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jira.cloud.client.api.ProjectsApi;
import ai.gebo.jira.cloud.client.invoker.ApiClient;
import ai.gebo.jira.cloud.client.model.Project;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * 
 * Service class responsible for testing Jira system configurations.
 * This service validates Jira credentials and connection settings.
 */
@Service
public class JiraSystemsTestService {
	/**
	 * Service for accessing Gebo secrets (credentials)
	 */
	@Autowired
	IGeboSecretsAccessService secretsService;
	/**
	 * Service for REST API communication
	 */
	@Autowired
	RestTemplateWrapperService templateWrapperService;

	/**
	 * Tests a Jira system configuration by validating credentials and connectivity.
	 * 
	 * The method retrieves the secret credentials using the secretCode, sets up the
	 * API client with the appropriate authentication, and attempts to retrieve
	 * projects from the Jira instance to verify connectivity.
	 * 
	 * @param object The Jira system configuration to test
	 * @return Operation status with validation results and appropriate messages
	 */
	public OperationStatus<GJiraSystem> testJiraSystem(GJiraSystem object) {
		OperationStatus<GJiraSystem> outValue = null;
		outValue = new OperationStatus<GJiraSystem>();
		outValue.setResult(object);
		String secretCode = object.getSecretCode();
		if (secretCode != null && secretCode.trim().length() > 0) {
			AbstractGeboSecretContent secretContent;
			try {
				// Retrieve the secret content associated with the secretCode
				secretContent = secretsService.getSecretContentById(secretCode);
				if (secretContent == null) {
					outValue.getMessages().add(GUserMessage.errorMessage("Missing Jira credentials infos",
							"Missing Jira credentials for company cloud system, please insert new credentials"));
					return outValue;
				}
				if (object.getBaseUri() != null && object.getBaseUri().trim().length() > 0) {
					// Initialize Jira API client with base URI
					ApiClient apiClient = new ApiClient();
					apiClient.setBasePath(object.getBaseUri());
					// Handle different types of credentials
					switch (secretContent.type()) {
					case TOKEN: {
						// Set up token-based authentication
						GeboTokenContent token = (GeboTokenContent) secretContent;
						apiClient.setUsername(token.getUser());
						apiClient.setPassword(token.getToken());
					}
						break;
					case USERNAME_PASSWORD: {
						// Set up username/password authentication
						GeboUsernamePasswordContent up = (GeboUsernamePasswordContent) secretContent;
						apiClient.setUsername(up.getUsername());
						apiClient.setPassword(up.getPassword());
					}
						break;
					}
					// Test connection by retrieving all projects
					ProjectsApi projects = new ProjectsApi(apiClient);
					List<Project> projectsList = projects.getAllProjects("*", null, List.of("*"));
					outValue.getMessages().add(GUserMessage.successMessage("Jira system configuration OK",
							"Jira system is correctly set"));
				} else {
					outValue.getMessages().add(GUserMessage.errorMessage("Missing Jira base url",
							"Missing Jira base url for company cloud system"));
				}
			} catch (GeboCryptSecretException e) {
				// Handle exception when accessing secret credentials
				outValue.getMessages().add(GUserMessage.errorMessage("Gebo.ai secret credentials access error",
						"Something went wrong in the system credentials storing system"));
			}
		} else
		{
			// Handle case when no credentials are provided
			outValue.getMessages().add(
					GUserMessage.errorMessage("No credentials inserted", "The confluence credentials are missing"));
		}
		return outValue;
	}

}
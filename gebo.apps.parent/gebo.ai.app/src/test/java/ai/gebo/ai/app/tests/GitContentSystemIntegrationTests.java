/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import com.fasterxml.jackson.core.JsonProcessingException;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Integration test class for testing Git content management systems.
 * Extends the AbstractBaseTestLLmsIntegrationTests class.
 * AI generated comments
 */
//@Ignore
public class GitContentSystemIntegrationTests extends AbstractBaseTestLLmsIntegrationTests {

    /**
     * Test method for running a public Git endpoint test.
     *
     * @throws InstantiationException        if an instantiation error occurs.
     * @throws IllegalAccessException        if there is no access to a class.
     * @throws GeboPersistenceException      if persistence fails.
     * @throws GeboJobServiceException       if job service fails.
     * @throws InterruptedException          if the test is interrupted.
     * @throws JsonProcessingException       if JSON processing fails.
     */
	// @Ignore
	@Test
	public void runPublicGitEndpointTest() throws InstantiationException, IllegalAccessException,
			GeboPersistenceException, GeboJobServiceException, InterruptedException, JsonProcessingException {
		// Log starting of public git endpoint test
		LOGGER.info("Start public git endpoint test");
		
		// Create a new Git content management system
		GGitContentManagementSystem system = new GGitContentManagementSystem();
		system.setDescription("Default git system"); // Set description for the system
		system.setPublicAccess(true); // Enable public access
		system.setBaseUri("https://github.com/"); // Set base URI

		// Persist the Git content management system object
		system = persistentObjectManager.insert(system);
		
		// Create and persist a new Git project endpoint
		GGitProjectEndpoint endpoint = createAndPersist("openi40 git project", GGitProjectEndpoint.class);
		endpoint.setPublicAccess(true); // Enable public access for the endpoint
		endpoint.setRepositoryUri("https://github.com/chrishantha/sample-java-programs.git"); // Set repository URI
		endpoint.setBranch("main"); // Set the branch to 'main'
		endpoint.setContentManagementSystem(system.getCode()); // Associate with content management system code

		// Update the persisted object with new data
		endpoint = persistentObjectManager.update(endpoint);

		// Run the test and wait for completion, then verify results
		runAndWaitDoneCheckingResults(endpoint, true);

		// Clean up changes made to the persistent storage
		cleanPersistent(endpoint);

		// Log ending of public git endpoint test
		LOGGER.info("End public git endpoint test");
	}
}
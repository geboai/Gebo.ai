/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.integration.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTestsWithFakeLLMS;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.atlassian.confluence.handler.ConfluenceVersion;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceBrowsingService;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceContentManagementHandlerImpl;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceSystemsTestService;
import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.impl.JiraBrowsingContext;
import ai.gebo.atlassian.jira.handler.impl.JiraBrowsingService;
import ai.gebo.atlassian.jira.handler.impl.JiraContentManagementHandlerImpl;
import ai.gebo.atlassian.jira.handler.impl.JiraSystemsTestService;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.monolithic.app.Main;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

@SpringBootTest(classes = Main.class)
public class AtlassianJiraIntegrationTests extends AbstractGeboMonolithicIntegrationTestsWithFakeLLMS {
	public static String JIRA_CLOUD_SPACE_URL = System.getenv("JIRA_CLOUD_SPACE_URL");
	public static String JIRA_CLOUD_API_KEY = System.getenv("JIRA_CLOUD_API_KEY");
	public static String JIRA_CLOUD_USER = System.getenv("JIRA_CLOUD_USER");
	@Autowired
	JiraBrowsingService jiraBrowsingService;
	@Autowired
	JiraSystemsTestService jiraSystemTestService;

	@Test
	public void testJiraBrowsingAndPublishing() throws GeboCryptSecretException, GeboPersistenceException,
			VirtualFilesystemBrowsingException, InstantiationException, IllegalAccessException, JsonProcessingException,
			GeboJobServiceException, InterruptedException {
		String secretId = registerSecurityToken(JIRA_CLOUD_API_KEY, JIRA_CLOUD_USER, "Confluence Cloud account",
				JiraContentManagementHandlerImpl.ATLASSIAN_JIRA);
		GJiraSystem system = new GJiraSystem();
		system.setDescription("Integration tests jira configuration");
		system.setContentManagementSystemType(ConfluenceContentManagementHandlerImpl.ATLASSIAN_CONFLUENCE);
		system.setBaseUri(JIRA_CLOUD_SPACE_URL);
		system.setSecretCode(secretId);
		OperationStatus<GJiraSystem> testResult = jiraSystemTestService.testJiraSystem(system);
		assertFalse(testResult.isHasErrorMessages(),
				"System test basic service cannot fail, otherwise the remote account is not valid");
		system = super.persistentObjectManager.insert(system);
		JiraBrowsingContext jiraContext = JiraBrowsingContext.of(system.getCode());
		OperationStatus<List<GVirtualFilesystemRoot>> roots = jiraBrowsingService.getRoots(jiraContext);
		roots.getMessages().forEach((m) -> {
			LOGGER.info("User message:" + m.getSummary() + " severity:" + m.getSeverity());
		});
		assertFalse(roots.isHasErrorMessages(), "Navigating the remote jira system may not return error messages");
		assertFalse(roots.getResult().isEmpty(),
				"Navigating the remote jira system may not return empty root projects");
		List<VFilesystemReference> references = new ArrayList<>();
		for (GVirtualFilesystemRoot root : roots.getResult()) {
			LOGGER.info("Root=>" + root.toString());
			BrowseParam param = new BrowseParam();
			param.root = root;
			OperationStatus<List<PathInfo>> firstLevelChilds = jiraBrowsingService.browse(param, jiraContext);
			assertFalse(firstLevelChilds.isHasErrorMessages(),
					"Navigating the remote jira system on 2nd level may not return error messages");
			if (!firstLevelChilds.getResult().isEmpty()) {
				for (PathInfo child : firstLevelChilds.getResult()) {

					LOGGER.info("2nd Level child=>" + child.name + " " + child.absolutePath);
					BrowseParam _param = new BrowseParam();
					_param.root = param.root;
					_param.path = child;
					OperationStatus<List<PathInfo>> secondLevelChilds = jiraBrowsingService.browse(_param, jiraContext);
					assertFalse(secondLevelChilds.isHasErrorMessages(),
							"Navigating the remote jira system on 3rd level may not return error messages");
					for (PathInfo sndLevelChild : secondLevelChilds.getResult()) {
						LOGGER.info("3rd Level child=>" + sndLevelChild.name + " " + sndLevelChild.absolutePath);

					}
					VFilesystemReference reference = new VFilesystemReference();
					reference.root = root;
					reference.path = child;
					references.add(reference);
				}
			}
		}

		GJiraProjectEndpoint _endpoint = createAndPersist("Jira  documents source", GJiraProjectEndpoint.class);
		_endpoint.setJiraSystemCode(system.getCode());

		_endpoint.setPaths(references);
		_endpoint = persistentObjectManager.update(_endpoint);
		runAndWaitDoneCheckingResults(_endpoint, true);

	}
}

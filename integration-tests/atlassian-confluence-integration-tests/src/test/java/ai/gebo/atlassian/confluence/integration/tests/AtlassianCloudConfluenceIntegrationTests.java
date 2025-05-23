/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.integration.tests;

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
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceBrowsingContext;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceBrowsingService;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceContentManagementHandlerImpl;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceSystemsTestService;
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
public class AtlassianCloudConfluenceIntegrationTests extends AbstractGeboMonolithicIntegrationTestsWithFakeLLMS {
	public static String CONFLUENCE_CLOUD_SPACE_URL = System.getenv("CONFLUENCE_CLOUD_SPACE_URL");
	public static String CONFLUENCE_CLOUD_API_KEY = System.getenv("CONFLUENCE_CLOUD_API_KEY");
	public static String CONFLUENCE_CLOUD_USER = System.getenv("CONFLUENCE_CLOUD_USER");
	@Autowired
	ConfluenceBrowsingService confluenceBrowsingService;
	@Autowired
	ConfluenceSystemsTestService confluenceSystemTestService;

	@Test
	public void testCloudConfluenceIntegration()
			throws GeboCryptSecretException, GeboPersistenceException, InstantiationException, IllegalAccessException,
			JsonProcessingException, GeboJobServiceException, InterruptedException, VirtualFilesystemBrowsingException {
		String secretId = registerSecurityToken(CONFLUENCE_CLOUD_API_KEY, CONFLUENCE_CLOUD_USER,
				"Confluence Cloud account", ConfluenceContentManagementHandlerImpl.ATLASSIAN_CONFLUENCE);
		GConfluenceSystem system = new GConfluenceSystem();
		system.setDescription("Integration tests confluence configuration");
		system.setContentManagementSystemType(ConfluenceContentManagementHandlerImpl.ATLASSIAN_CONFLUENCE);
		system.setConfluenceVersion(ConfluenceVersion.CLOUD);
		system.setBaseUri(CONFLUENCE_CLOUD_SPACE_URL);
		system.setSecretCode(secretId);
		OperationStatus<GConfluenceSystem> testResult = confluenceSystemTestService.testConfluenceSystem(system);
		assertFalse(testResult.isHasErrorMessages(),
				"System test basic service cannot fail, otherwise the remote account is not valid");
		system = super.persistentObjectManager.insert(system);
		OperationStatus<List<GVirtualFilesystemRoot>> roots = this.confluenceBrowsingService
				.getRoots(ConfluenceBrowsingContext.of(system.getCode()));
		roots.getMessages().forEach((m) -> {
			LOGGER.info("User message:" + m.getSummary() + " severity:" + m.getSeverity());
		});
		assertFalse(roots.isHasErrorMessages(),
				"Navigating the remote confluence system may not return error messages");
		for (GVirtualFilesystemRoot root : roots.getResult()) {
			BrowseParam param = new BrowseParam();
			param.root = root;
			LOGGER.info("Root=>" + root.getDescription() + " code=" + root.getCode());
			OperationStatus<List<PathInfo>> secondLevel = this.confluenceBrowsingService.browse(param,
					ConfluenceBrowsingContext.of(system.getCode()));
			assertFalse(secondLevel.isHasErrorMessages(), "Navigating the second level with errors");
			for (PathInfo path : secondLevel.getResult()) {

				if (path.folder) {
					param = new BrowseParam();
					param.root = root;
					param.path = path;
					LOGGER.info(
							"Folder root code=" + root.getCode() + " name=" + path.name + " code=" + path.absolutePath);
					OperationStatus<List<PathInfo>> thirdLevel = this.confluenceBrowsingService.browse(param,
							ConfluenceBrowsingContext.of(system.getCode()));
					assertFalse(thirdLevel.isHasErrorMessages(), "Navigating the third level with errors");
					for (PathInfo child : thirdLevel.getResult()) {
						if (child.folder) {
							LOGGER.info("Folder root  code=" + root.getCode() + " 2nd level name=" + path.name
									+ " code=" + path.absolutePath + " 3rd level name=" + child.name + " code="
									+ child.absolutePath);
						} else {
							LOGGER.info("Resource root  code=" + root.getCode() + " 2nd level name=" + path.name
									+ " code=" + path.absolutePath + " 3rd level name=" + child.name + " code="
									+ child.absolutePath);
						}
					}
				} else {
					LOGGER.info("Resource root  code=" + root.getCode() + " name=" + path.name + " code="
							+ path.absolutePath);
				}
			}
		}
		List<GVirtualFilesystemRoot> values = roots.getResult();
		for (GVirtualFilesystemRoot value : values) {
			LOGGER.info(value.getCode());
		}
		GConfluenceProjectEndpoint endpoint = createAndPersist("Confluence cloud test",
				GConfluenceProjectEndpoint.class);
		endpoint.setConfluenceSystemCode(system.getCode());
		endpoint.setDescription("Confluence cloud test");
		endpoint.setConfluenceVersion(ConfluenceVersion.CLOUD);
		List<VFilesystemReference> paths = new ArrayList<VFilesystemReference>();
		for (GVirtualFilesystemRoot value : values) {
			VFilesystemReference reference = new VFilesystemReference();
			reference.root = value;
			paths.add(reference);
		}
		endpoint.setPaths(paths);
		persistentObjectManager.update(endpoint);
		runAndWaitDoneCheckingResults(endpoint);

	}

}

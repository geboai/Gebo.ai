/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.sharepoint.integration.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTestsWithFakeLLMS;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.monolithic.app.Main;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.IGSharepointContentManagementSystemHandler;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemBrowsingService;
import ai.gebo.sharepoint.handler.SharepointVersion;
import ai.gebo.sharepoint.handler.impl.SharepointBrowsingContext;
import ai.gebo.sharepoint.handler.impl.SharepointSystemsTestService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

@SpringBootTest(classes = Main.class)
public class MicrosoftSharepointIntegrationTests extends AbstractGeboMonolithicIntegrationTestsWithFakeLLMS {
	@Autowired
	SharepointSystemsTestService sharePointSystemTestService;
	@Autowired
	IGSharepointContentManagementSystemHandler handler;
	@Autowired
	IGMicrosoftGraphVirtualFilesystemBrowsingService sharepointVirtualFileSystemBrowsingService;
	static String SHAREPOINT_CLIENT_ID = System.getenv("SHAREPOINT_CLIENT_ID");
	static String SHAREPOINT_TENANT_ID = System.getenv("SHAREPOINT_TENANT_ID");
	static String SHAREPOINT_SECRET_KEY = System.getenv("SHAREPOINT_SECRET_KEY");
	static String SHAREPOINT_BASE_URL = System.getenv("SHAREPOINT_BASE_URL");
	static String MICROSOFT_GRAPH_BASE_URL = "https://graph.microsoft.com/v1.0";

	@Test
	public void checkSharepointConnectivityTest()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException, GeboCryptSecretException,
			VirtualFilesystemBrowsingException, JsonProcessingException, GeboJobServiceException, InterruptedException {
		GSharepointProjectEndpoint endpoint = createAndPersist("Sharepoint endpoint", GSharepointProjectEndpoint.class);
		GProject pk = getProject(endpoint);
		GeboOauth2SecretContent oauth2Credentials = new GeboOauth2SecretContent();
		oauth2Credentials.setClientId(SHAREPOINT_CLIENT_ID);
		oauth2Credentials.getCustomAttributes().put("tenantId", SHAREPOINT_TENANT_ID);
		oauth2Credentials.setSecret(SHAREPOINT_SECRET_KEY);
		GSharepointContentManagementSystem system = new GSharepointContentManagementSystem();
		system.setContentManagementSystemType(handler.getHandledSystemType().getCode());
		// system.setBaseUri(sharePointBaseUrl);
		system.setBaseUri(MICROSOFT_GRAPH_BASE_URL);
		system.setDescription("Sharepoint system");
		system.setSharepointVersion(SharepointVersion.CLOUD_VERSION);
		system.setSecretCode(secretsAccessService.storeSecret(oauth2Credentials, "Sharepoint credentials",
				handler.getHandledSystemType().getCode()));
		OperationStatus<GSharepointContentManagementSystem> status = sharePointSystemTestService
				.testSharepointSystem(system);
		for (GUserMessage msg : status.getMessages()) {
			LOGGER.info("Summary:" + msg.getSummary() + " detail:" + msg.getDetail());
		}
		assertFalse(status.isHasErrorMessages(),
				"Sharepoint cloud system configuration testing must not have error messages");

		system = persistentObjectManager.insert(system);

		SharepointBrowsingContext context = new SharepointBrowsingContext();
		context.setSystemCode(system.getCode());
		OperationStatus<List<GVirtualFilesystemRoot>> roots = sharepointVirtualFileSystemBrowsingService
				.getRoots(context);
		assertFalse(roots.isHasErrorMessages(),
				"Sharepoint cloud system browsing testing must not have error messages");
		List<VFilesystemReference> paths = new ArrayList<VFilesystemReference>();
		for (GVirtualFilesystemRoot root : roots.getResult()) {
			BrowseParam param = new BrowseParam();
			param.root = root;
			LOGGER.info("Root description:" + root.getDescription() + " root code=>" + root.getCode());
			OperationStatus<List<PathInfo>> mainBrowsingChilds = sharepointVirtualFileSystemBrowsingService
					.browse(param, context);
			// assertFalse(mainBrowsingChilds.isHasErrorMessages(),
			// "Sharepoint cloud system browsing testing must not have error messages");
			if (mainBrowsingChilds.getResult() != null) {
				for (PathInfo r : mainBrowsingChilds.getResult()) {
					LOGGER.info("Item description:" + r.name + " absolutePath:" + r.absolutePath);
					if (r.folder) {
						VFilesystemReference reference = new VFilesystemReference();
						reference.root = root;
						reference.path = r;
						param = new BrowseParam();
						param.root = root;
						param.path = r;
						LOGGER.info("Opening path :" + r.name + " absolutePath:" + r.absolutePath);
						OperationStatus<List<PathInfo>> listings = sharepointVirtualFileSystemBrowsingService
								.browse(param, context);
						if (listings.getResult() != null) {
							paths.add(reference);
							for (PathInfo r1 : listings.getResult()) {
								LOGGER.info("Childs of type: " + (r1.metaType != null ? r1.metaType.name() : null)
										+ " of path:" + r.name + " Item description:" + r1.name + " absolutePath:"
										+ r1.absolutePath);
							}
						}

					} else if (!r.folder && r.metaType == PathInfoMetaType.WEB_PAGE) {
						VFilesystemReference reference = new VFilesystemReference();
						reference.root = root;
						reference.path = r;
						param = new BrowseParam();
						param.root = root;
						param.path = r;
						paths.add(reference);
						LOGGER.info("Adding resource :" + r.name + " absolutePath:" + r.absolutePath);

					}
				}
			}
		}
		assertFalse(paths.isEmpty(), "Paths references cannot be empty");
		endpoint.setPaths(paths);
		endpoint.setSharePointSystemCode(system.getCode());
		endpoint = persistentObjectManager.update(endpoint);
		super.runAndWaitDoneCheckingResults(endpoint, true);
	}
}

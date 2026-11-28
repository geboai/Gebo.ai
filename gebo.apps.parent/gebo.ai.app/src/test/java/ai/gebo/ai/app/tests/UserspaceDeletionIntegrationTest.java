/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.config.GeboAISecurityConfig;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.controllers.UserspaceController;
import ai.gebo.userspace.handler.dto.UserspaceFolderDto;
import ai.gebo.userspace.handler.dto.UserspaceKnowledgebaseDto;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * A person deletes a folder, and a knowledge base, of their own userspace: the
 * folder's data source, its documents, their vectors and its uploaded files go,
 * then the folder and the knowledge base. Both deletions used to check the owner
 * and delete nothing; the data source deletion they rely on was refused to anyone
 * not an administrator. A folder named next to another knowledge base is refused.
 */
public class UserspaceDeletionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String OWNER = "userspace.owner@gebo.ai";

	@Autowired
	UserspaceController userspaceController;
	@Autowired
	IGUserspaceContentManagementSystemHandler userspaceHandler;

	@Override
	protected void beforeEachCallback() throws Exception {
		if (getUser(OWNER) == null) {
			createUser(OWNER, List.of(GeboAISecurityConfig.USER_ROLE));
		}
	}

	@Test
	public void theOwnerDeletesAFolderWithItsContentsAndThenTheKnowledgeBase() throws Exception {
		actAsTheOwner();
		final UserspaceKnowledgebaseDto knowledgeBase = newKnowledgeBase("owner's knowledge base");
		final UserspaceFolderDto folder = newFolder(knowledgeBase, "owner's folder");
		final GUserspaceProjectEndpoint uploads = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folder.uploadCode);
		final Path files = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(userspaceHandler.getSystem(uploads),
				uploads));

		// ingested as the platform does it, by an administrator's job
		impersonateAdminUser();
		Files.createDirectories(files);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), files.resolve("owned.docx"),
				StandardCopyOption.REPLACE_EXISTING);
		enableWorkflowSteps(persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase.code),
				persistentObjectManager.findById(GProject.class, folder.code), uploads);
		awaitFinished(ingestionJobService.executeSyncJob(uploads, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name()));
		final GDocumentReference document = documentsOf(uploads).get(0);
		assertTrue(awaitVectors(document.getCode(), true), "the uploaded file is indexed");

		actAsTheOwner();
		userspaceController.deleteUserspaceFolder(folder);
		final long deadline = System.currentTimeMillis() + 60_000l;
		while (System.currentTimeMillis() < deadline
				&& (persistentObjectManager.findById(GUserspaceProjectEndpoint.class, uploads.getCode()) != null
						|| !documentsOf(uploads).isEmpty() || Files.exists(files))) {
			Thread.sleep(1000);
		}
		assertNull(persistentObjectManager.findById(GUserspaceProjectEndpoint.class, uploads.getCode()),
				"the folder's data source is gone");
		assertTrue(documentsOf(uploads).isEmpty(), "and its documents");
		assertTrue(awaitVectors(document.getCode(), false), "and their vectors");
		assertFalse(Files.exists(files), "and its uploaded files");
		assertNull(persistentObjectManager.findById(GProject.class, folder.code), "the folder is gone");
		assertNotNull(persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase.code),
				"its knowledge base stays");

		final UserspaceFolderDto second = newFolder(knowledgeBase, "owner's second folder");
		userspaceController.deleteUserKnowledgebase(knowledgeBase);
		assertNull(persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase.code),
				"the knowledge base is gone");
		assertNull(persistentObjectManager.findById(GProject.class, second.code), "with its folders");
	}

	@Test
	public void aFolderNamedNextToAnotherKnowledgeBaseIsRefused() throws Exception {
		actAsTheOwner();
		final UserspaceKnowledgebaseDto first = newKnowledgeBase("first knowledge base");
		final UserspaceFolderDto folder = newFolder(first, "folder of the first");
		final UserspaceKnowledgebaseDto other = newKnowledgeBase("other knowledge base");
		final UserspaceFolderDto forged = new UserspaceFolderDto();
		forged.code = folder.code;
		forged.uploadCode = folder.uploadCode;
		forged.parentUserspaceKnowledgebaseCode = other.code;

		assertThrows(RuntimeException.class, () -> userspaceController.deleteUserspaceFolder(forged),
				"a folder is deleted only through its own knowledge base");
		assertNotNull(persistentObjectManager.findById(GProject.class, folder.code), "the folder stays");
		assertNotNull(persistentObjectManager.findById(GUserspaceProjectEndpoint.class, folder.uploadCode),
				"with its data source");

		userspaceController.deleteUserKnowledgebase(first);
		userspaceController.deleteUserKnowledgebase(other);
	}

	private UserspaceKnowledgebaseDto newKnowledgeBase(String description) throws Exception {
		final UserspaceKnowledgebaseDto knowledgeBase = new UserspaceKnowledgebaseDto();
		knowledgeBase.description = description;
		return userspaceController.newUserKnowledgebase(knowledgeBase);
	}

	private UserspaceFolderDto newFolder(UserspaceKnowledgebaseDto knowledgeBase, String description) throws Exception {
		final UserspaceFolderDto folder = new UserspaceFolderDto();
		folder.description = description;
		folder.parentUserspaceKnowledgebaseCode = knowledgeBase.code;
		return userspaceController.newUserspaceFolder(folder);
	}

	private List<GDocumentReference> documentsOf(GUserspaceProjectEndpoint endpoint) throws Exception {
		return persistentObjectManager.findAll(GDocumentReference.class).stream()
				.filter(d -> d.getProjectEndpointReference() != null
						&& endpoint.getCode().equals(d.getProjectEndpointReference().getCode())
						&& (d.getDeleted() == null || !d.getDeleted()))
				.toList();
	}

	/**
	 * Waits for the document's vectors to be, or not to be, in the test store. Read
	 * from its data: its metadata index keeps the vectors it deleted.
	 */
	private boolean awaitVectors(String documentCode, boolean present) throws InterruptedException {
		for (int i = 0; i < 60; i++) {
			final boolean stored = getTestVectorStore().getAllData().stream().anyMatch(
					x -> x.getMetadata() != null && documentCode.equals(x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)));
			if (present == stored) {
				return true;
			}
			Thread.sleep(1000);
		}
		return false;
	}

	private void awaitFinished(GJobStatus job) throws Exception {
		JobSummary summary = null;
		for (int i = 0; i < 40; i++) {
			Thread.sleep(5000);
			summary = workflowStatsService.getJobSummary(job.getCode());
			if (summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished()) {
				return;
			}
		}
		throw new AssertionError("the ingestion has to end");
	}

	private static void actAsTheOwner() {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(OWNER,
				"NOPASSWORD", List.of(new SimpleGrantedAuthority(GeboAISecurityConfig.USER_ROLE))));
	}
}

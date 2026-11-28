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

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.config.GeboAISecurityConfig;
import ai.gebo.userspace.handler.GUserspaceFile;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.controllers.UserspaceController;
import ai.gebo.userspace.handler.dto.UserspaceFileDto;
import ai.gebo.userspace.handler.dto.UserspaceFolderDto;
import ai.gebo.userspace.handler.dto.UserspaceKnowledgebaseDto;
import ai.gebo.userspace.handler.repository.UserspaceFileRepository;
import ai.gebo.userspace.handler.service.UserspaceManagementServiceImpl;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * A person deletes a folder, and a knowledge base, of their own userspace: the
 * folder's data source, its documents, their vectors and its uploaded files go,
 * then the folder and the knowledge base. Both deletions used to check the owner
 * and delete nothing; the data source deletion they rely on was refused to anyone
 * not an administrator. A folder named next to another knowledge base is refused.
 * The owner deletes single files too: they leave the folder at once and the
 * knowledge base at the folder's next publish; nobody else deletes them. A file
 * uploaded again after its deletion is ingested again (its upload used to fail on
 * the record the first upload left); an upload is refused to whoever cannot access
 * the folder's knowledge base and for a name leading out of the folder. Who
 * uploaded a file owns it and may delete it. A folder's publish and its status
 * find the knowledge base from the folder, not from the request. A knowledge base
 * goes with the knowledge bases its owner made under it. A folder, its files
 * and its documents' files are read only by who can access its knowledge base.
 */
public class UserspaceDeletionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String OWNER = "userspace.owner@gebo.ai";
	private static final String SOMEONE_ELSE = "userspace.someone.else@gebo.ai";

	@Autowired
	UserspaceController userspaceController;
	@Autowired
	IGUserspaceContentManagementSystemHandler userspaceHandler;
	@Autowired
	UserspaceManagementServiceImpl uploadService;
	@Autowired
	UserspaceFileRepository userspaceFiles;

	@Override
	protected void beforeEachCallback() throws Exception {
		if (getUser(OWNER) == null) {
			createUser(OWNER, List.of(GeboAISecurityConfig.USER_ROLE));
		}
		if (getUser(SOMEONE_ELSE) == null) {
			createUser(SOMEONE_ELSE, List.of(GeboAISecurityConfig.USER_ROLE));
		}
	}

	@Test
	public void theOwnerDeletesAFileAndTheNextPublishRemovesItsDocument() throws Exception {
		actAs(OWNER);
		final UserspaceKnowledgebaseDto knowledgeBase = newKnowledgeBase("knowledge base with files");
		final UserspaceFolderDto folder = newFolder(knowledgeBase, "folder with files");
		final GUserspaceProjectEndpoint uploads = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folder.uploadCode);
		final Path files = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(userspaceHandler.getSystem(uploads),
				uploads));
		uploadService.manageUpload(uploads.getCode(),
				List.of(multipartOf(TEST_001_DOCX_FILE), multipartOf(TEST_001_ODT_FILE)));
		final String deletedName = Path.of(TEST_001_DOCX_FILE).getFileName().toString();
		final String keptName = Path.of(TEST_001_ODT_FILE).getFileName().toString();

		impersonateAdminUser();
		enableWorkflowSteps(persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase.code),
				persistentObjectManager.findById(GProject.class, folder.code), uploads);
		awaitFinished(ingestionJobService.executeSyncJob(uploads, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name()));
		final GDocumentReference deleted = documentNamed(uploads, deletedName);
		final GDocumentReference kept = documentNamed(uploads, keptName);
		assertNotNull(deleted, "the first file is ingested");
		assertNotNull(kept, "the second file is ingested");
		assertTrue(awaitVectors(deleted.getCode(), true), "the first file is indexed");
		assertTrue(awaitVectors(kept.getCode(), true), "the second file is indexed");

		actAs(SOMEONE_ELSE);
		assertThrows(GeboPersistenceException.class,
				() -> userspaceController.deleteUserspaceFiles(List.of(fileOf(uploads, deletedName))),
				"only the folder's owner or an administrator deletes its files");
		actAs(OWNER);
		assertThrows(RuntimeException.class,
				() -> userspaceController.deleteUserspaceFiles(
						List.of(fileOf(uploads, deletedName), fileOf(uploads, "../" + keptName))),
				"a name cannot lead out of the folder");
		assertTrue(Files.exists(files.resolve(deletedName)), "a refused request deletes nothing");

		userspaceController.deleteUserspaceFiles(List.of(fileOf(uploads, deletedName)));
		assertFalse(Files.exists(files.resolve(deletedName)), "the file leaves the folder");
		assertTrue(Files.exists(files.resolve(keptName)), "the other file stays");
		final GUserspaceFile record = userspaceFiles.findById(uploads.getCode() + "/" + deletedName).orElse(null);
		assertTrue(record != null && Boolean.TRUE.equals(record.getDeleted()), "its upload record is deleted");
		assertTrue(userspaceController.listUserspaceFiles(uploads.getCode()).stream()
				.noneMatch(x -> deletedName.equals(x.name)), "it is no longer listed");

		impersonateAdminUser();
		awaitFinished(ingestionJobService.executeSyncJob(uploads, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name()));
		assertNull(documentNamed(uploads, deletedName), "the next publish deletes its document");
		assertTrue(awaitVectors(deleted.getCode(), false), "and erases its vectors");
		assertNotNull(documentNamed(uploads, keptName), "the other document stays");
		assertTrue(awaitVectors(kept.getCode(), true), "with its vectors");

		actAs(OWNER);
		uploadService.manageUpload(uploads.getCode(), List.of(multipartOf(TEST_001_DOCX_FILE)));
		assertTrue(Files.exists(files.resolve(deletedName)), "the deleted file is uploaded again");
		final GUserspaceFile again = userspaceFiles.findById(uploads.getCode() + "/" + deletedName).orElse(null);
		assertTrue(again != null && !Boolean.TRUE.equals(again.getDeleted()), "its record is live again");
		impersonateAdminUser();
		awaitFinished(ingestionJobService.executeSyncJob(uploads, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name()));
		final GDocumentReference reingested = documentNamed(uploads, deletedName);
		assertNotNull(reingested, "the next publish ingests it again");
		assertTrue(awaitVectors(reingested.getCode(), true), "with its vectors");

		actAs(OWNER);
		userspaceController.deleteUserKnowledgebase(knowledgeBase);
	}

	@Test
	public void anUploadIsRefusedToWhoCannotAccessTheFolderAndForANameLeavingIt() throws Exception {
		actAs(OWNER);
		final UserspaceKnowledgebaseDto knowledgeBase = newKnowledgeBase("knowledge base refusing uploads");
		final UserspaceFolderDto folder = newFolder(knowledgeBase, "folder refusing uploads");
		final GUserspaceProjectEndpoint uploads = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folder.uploadCode);
		final Path files = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(userspaceHandler.getSystem(uploads),
				uploads));
		final MockMultipartFile leaving = new MockMultipartFile("files[]", "../leaving.docx", null,
				multipartOf(TEST_001_DOCX_FILE).getBytes());

		assertThrows(RuntimeException.class,
				() -> uploadService.manageUpload(uploads.getCode(), List.of(multipartOf(TEST_001_ODT_FILE), leaving)),
				"a name cannot lead out of the folder");
		assertFalse(Files.exists(files.getParent().resolve("leaving.docx")), "nothing is written out of it");
		assertFalse(Files.exists(files.resolve(Path.of(TEST_001_ODT_FILE).getFileName().toString())),
				"nor in it: every name is checked first");

		actAs(SOMEONE_ELSE);
		assertThrows(RuntimeException.class,
				() -> uploadService.manageUpload(uploads.getCode(), List.of(multipartOf(TEST_001_ODT_FILE))),
				"who cannot access the knowledge base cannot upload into its folders");
		assertFalse(Files.exists(files.resolve(Path.of(TEST_001_ODT_FILE).getFileName().toString())),
				"the refused file is not written");

		actAs(OWNER);
		userspaceController.deleteUserKnowledgebase(knowledgeBase);
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

	@Test
	public void publishAndItsStatusFindTheKnowledgeBaseFromTheFolder() throws Exception {
		actAs(OWNER);
		final UserspaceKnowledgebaseDto knowledgeBase = newKnowledgeBase("knowledge base to publish");
		final UserspaceFolderDto folder = newFolder(knowledgeBase, "folder to publish");
		actAs(SOMEONE_ELSE);
		final UserspaceKnowledgebaseDto other = newKnowledgeBase("someone else's knowledge base");
		final UserspaceFolderDto forged = new UserspaceFolderDto();
		forged.code = folder.code;
		forged.uploadCode = folder.uploadCode;
		forged.parentUserspaceKnowledgebaseCode = other.code;

		assertThrows(RuntimeException.class, () -> userspaceController.publishFolder(forged),
				"a folder is published only by who can access its own knowledge base");
		assertThrows(RuntimeException.class, () -> userspaceController.getPublishingStatus(forged),
				"and only they read its status");
		actAs(OWNER);
		assertNotNull(userspaceController.getPublishingStatus(folder), "its owner reads it");

		userspaceController.deleteUserKnowledgebase(knowledgeBase);
		actAs(SOMEONE_ELSE);
		userspaceController.deleteUserKnowledgebase(other);
	}

	@Test
	public void whoUploadedAFileOwnsItAndDeletesIt() throws Exception {
		actAs(OWNER);
		final UserspaceKnowledgebaseDto knowledgeBase = newKnowledgeBase("shared knowledge base");
		final UserspaceFolderDto folder = newFolder(knowledgeBase, "shared folder");
		final GKnowledgeBase shared = persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase.code);
		shared.setAccessibleUsers(List.of(OWNER, SOMEONE_ELSE));
		persistentObjectManager.update(shared);
		final GUserspaceProjectEndpoint uploads = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folder.uploadCode);
		final Path files = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(userspaceHandler.getSystem(uploads),
				uploads));
		final String ownersName = Path.of(TEST_001_DOCX_FILE).getFileName().toString();
		final String theirsName = Path.of(TEST_001_ODT_FILE).getFileName().toString();
		uploadService.manageUpload(uploads.getCode(), List.of(multipartOf(TEST_001_DOCX_FILE)));
		actAs(SOMEONE_ELSE);
		uploadService.manageUpload(uploads.getCode(), List.of(multipartOf(TEST_001_ODT_FILE)));

		final List<UserspaceFileDto> listed = userspaceController.listUserspaceFiles(uploads.getCode());
		assertTrue(listed.stream().anyMatch(x -> theirsName.equals(x.name) && x.owner), "the uploader owns their file");
		assertTrue(listed.stream().anyMatch(x -> ownersName.equals(x.name) && !x.owner), "not the others'");
		assertThrows(GeboPersistenceException.class,
				() -> userspaceController.deleteUserspaceFiles(List.of(fileOf(uploads, ownersName))),
				"who neither owns the folder nor uploaded the file cannot delete it");
		assertTrue(Files.exists(files.resolve(ownersName)), "it stays");
		userspaceController.deleteUserspaceFiles(List.of(fileOf(uploads, theirsName)));
		assertFalse(Files.exists(files.resolve(theirsName)), "the uploader deletes their own");

		actAs(OWNER);
		assertTrue(userspaceController.listUserspaceFiles(uploads.getCode()).stream()
				.anyMatch(x -> ownersName.equals(x.name) && x.owner), "the owner's file is theirs");
		userspaceController.deleteUserKnowledgebase(knowledgeBase);
	}

	@Test
	public void aKnowledgeBaseGoesWithTheKnowledgeBasesItsOwnerMadeUnderIt() throws Exception {
		actAs(OWNER);
		final UserspaceKnowledgebaseDto parent = newKnowledgeBase("parent knowledge base");
		final UserspaceKnowledgebaseDto child = newChildKnowledgeBase(parent, "child knowledge base");
		final UserspaceFolderDto childFolder = newFolder(child, "folder of the child");
		actAs(SOMEONE_ELSE);
		final UserspaceKnowledgebaseDto othersChild = newChildKnowledgeBase(parent, "someone else's child");

		actAs(OWNER);
		userspaceController.deleteUserKnowledgebase(parent);
		assertNull(persistentObjectManager.findById(GKnowledgeBase.class, parent.code), "the knowledge base is gone");
		assertNull(persistentObjectManager.findById(GKnowledgeBase.class, child.code),
				"with the knowledge base its owner made under it");
		assertNull(persistentObjectManager.findById(GProject.class, childFolder.code), "and that one's folders");
		assertNotNull(persistentObjectManager.findById(GKnowledgeBase.class, othersChild.code),
				"a child someone else made stays theirs");

		actAs(SOMEONE_ELSE);
		userspaceController.deleteUserKnowledgebase(othersChild);
	}

	@Test
	public void aFolderAndItsFilesAreReadOnlyByWhoCanAccessItsKnowledgeBase() throws Exception {
		actAs(OWNER);
		final UserspaceKnowledgebaseDto knowledgeBase = newKnowledgeBase("knowledge base to read");
		final UserspaceFolderDto folder = newFolder(knowledgeBase, "folder to read");
		final GUserspaceProjectEndpoint uploads = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folder.uploadCode);
		uploadService.manageUpload(uploads.getCode(), List.of(multipartOf(TEST_001_DOCX_FILE)));
		impersonateAdminUser();
		enableWorkflowSteps(persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase.code),
				persistentObjectManager.findById(GProject.class, folder.code), uploads);
		awaitFinished(ingestionJobService.executeSyncJob(uploads, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name()));
		final GDocumentReference document = documentNamed(uploads,
				Path.of(TEST_001_DOCX_FILE).getFileName().toString());
		assertNotNull(document, "the file is ingested");

		actAs(SOMEONE_ELSE);
		assertThrows(RuntimeException.class, () -> userspaceController.findUserspaceFolderByCode(folder.code),
				"who cannot access the knowledge base does not read its folder");
		assertThrows(RuntimeException.class, () -> userspaceController.listUserspaceFiles(uploads.getCode()),
				"nor its files");
		assertTrue(userspaceController.findUserspaceFileByCodes(List.of(document.getCode())).isEmpty(),
				"nor its documents' files");

		final GKnowledgeBase shared = persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBase.code);
		shared.setAccessibleUsers(List.of(OWNER, SOMEONE_ELSE));
		persistentObjectManager.update(shared);
		assertNotNull(userspaceController.findUserspaceFolderByCode(folder.code), "shared with them, they read it");
		assertTrue(userspaceController.listUserspaceFiles(uploads.getCode()).size() == 1, "and its files");
		assertTrue(userspaceController.findUserspaceFileByCodes(List.of(document.getCode())).size() == 1,
				"and its documents' files");

		actAs(OWNER);
		assertNotNull(userspaceController.findUserspaceFolderByCode(folder.code), "its owner reads it");
		userspaceController.deleteUserKnowledgebase(knowledgeBase);
	}

	private UserspaceKnowledgebaseDto newChildKnowledgeBase(UserspaceKnowledgebaseDto parent, String description)
			throws Exception {
		final UserspaceKnowledgebaseDto knowledgeBase = new UserspaceKnowledgebaseDto();
		knowledgeBase.description = description;
		knowledgeBase.parentKnowledgebaseCode = parent.code;
		return userspaceController.newUserKnowledgebase(knowledgeBase);
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

	private GDocumentReference documentNamed(GUserspaceProjectEndpoint endpoint, String name) throws Exception {
		return documentsOf(endpoint).stream().filter(d -> name.equals(d.getName())).findFirst().orElse(null);
	}

	private static UserspaceFileDto fileOf(GUserspaceProjectEndpoint endpoint, String name) {
		final UserspaceFileDto file = new UserspaceFileDto();
		file.parentUserspaceUploadCode = endpoint.getCode();
		file.name = name;
		return file;
	}

	private MultipartFile multipartOf(String classPathElement) throws Exception {
		try (InputStream stream = getClass().getClassLoader().getResourceAsStream(classPathElement)) {
			return new MockMultipartFile("files[]", Path.of(classPathElement).getFileName().toString(), null,
					stream.readAllBytes());
		}
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
		actAs(OWNER);
	}

	private static void actAs(String username) {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,
				"NOPASSWORD", List.of(new SimpleGrantedAuthority(GeboAISecurityConfig.USER_ROLE))));
	}
}

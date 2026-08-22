/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.ragsystem.vectorstores.test.services.TestVectorStore;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.uploads.content.handler.GUploadsContentManagementSystem;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;
import ai.gebo.uploads.content.handler.IGUploadsContentManagementSystemHandler;
import ai.gebo.uploads.content.handler.UploadedFileInfo;
import ai.gebo.uploads.content.handler.controllers.UploadsBrowsingController;
import ai.gebo.uploads.content.handler.service.UploadsSystemsManagementServiceImpl;

/**
 * Integration tests of the uploads data source, covering the whole life of its
 * contents and not only the first upload: staging a first batch, adding further
 * batches to an endpoint that already holds files, adding files directly to a
 * persisted endpoint, browsing them, and removing them.
 *
 * <p>
 * The deletion assertions are the reason this test drives two ingestion runs:
 * removing a file from the data source folder is only half of the story, the
 * other half is the standard ingestion reconciliation, which at the following
 * publish detects the vanished path, flags the document as deleted and asks the
 * vectorization module to dispose of its embeddings. The second run therefore
 * asserts on the test vector store having received deletions, which is the
 * observable proof that the contents really left the knowledge base.
 * </p>
 */
public class UploadsEndpointIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	UploadsSystemsManagementServiceImpl uploadsService;

	@Autowired
	IGUploadsContentManagementSystemHandler uploadsHandler;

	@Autowired
	UploadsBrowsingController browsingController;

	/** First batch, staged through a handshake code as the creation flow does. */
	private static final List<String> FIRST_BATCH = List.of(TEST_001_PDF_FILE, TEST_001_DOCX_FILE);

	/** Second batch, added when the data source already holds contents. */
	private static final List<String> SECOND_BATCH = List.of(TEST_001_ODT_FILE, TEST_001_XLSX_FILE);

	/**
	 * Reads a test resource and wraps it in a multipart file named after the
	 * resource leaf name, which is what a browser upload sends.
	 *
	 * @param classPathElement the test resource to read.
	 * @return the multipart file to feed the uploads service with.
	 * @throws IOException when the resource cannot be read.
	 */
	private MultipartFile multipartOf(String classPathElement) throws IOException {
		InputStream is = getClass().getResourceAsStream(classPathElement);
		if (is == null)
			is = getClass().getClassLoader().getResourceAsStream(classPathElement);
		if (is == null)
			throw new RuntimeException("The resource:" + classPathElement + " does not exist");
		try (InputStream stream = is) {
			String fileName = Path.of(classPathElement).getFileName().toString();
			return new MockMultipartFile("files[]", fileName, null, stream.readAllBytes());
		}
	}

	/**
	 * Wraps every given test resource in a multipart file.
	 *
	 * @param classPathElements the test resources to read.
	 * @return the multipart files to feed the uploads service with.
	 * @throws IOException when a resource cannot be read.
	 */
	private List<MultipartFile> multipartsOf(List<String> classPathElements) throws IOException {
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		for (String element : classPathElements) {
			files.add(multipartOf(element));
		}
		return files;
	}

	/**
	 * Stages a batch under a fresh handshake code and applies it to the endpoint,
	 * which is what saving the editor does.
	 *
	 * @param endpoint      the data source receiving the files.
	 * @param resourcePaths the test resources to upload.
	 * @return the updated data source.
	 */
	private GUploadsProjectEndpoint uploadStagedBatch(GUploadsProjectEndpoint endpoint, List<String> resourcePaths)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException {
		String handshakeCode = UUID.randomUUID().toString();
		uploadsService.manageUpload(handshakeCode, multipartsOf(resourcePaths));
		endpoint.setUploadHandshakeCode(handshakeCode);
		return uploadsService.update(endpoint);
	}

	/**
	 * The leaf names of the given test resources, which are the names the files
	 * take once uploaded.
	 *
	 * @param resourcePaths the test resources.
	 * @return their file names.
	 */
	private List<String> fileNamesOf(List<String> resourcePaths) {
		return resourcePaths.stream().map(x -> Path.of(x).getFileName().toString()).toList();
	}

	/**
	 * Resolves the folder holding the contents of a data source.
	 *
	 * @param endpoint the data source.
	 * @return its contents folder.
	 */
	private Path contentsFolderOf(GUploadsProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		GUploadsContentManagementSystem system = uploadsHandler.getSystem(endpoint);
		return Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
	}

	/** How long the disposal of the embeddings of a removed file is awaited. */
	private static final int DELETED_VECTORS_MAX_WAIT_CYCLES = 12;

	/**
	 * Waits for the test vector store to receive deletions beyond the ones it had
	 * already recorded.
	 *
	 * <p>
	 * The ingestion flags the vanished documents and hands their codes to the
	 * vectorization dispose component through the message broker, which handles
	 * them asynchronously: reading the store the instant the job status flips is a
	 * race, so the check is retried for a bounded time.
	 * </p>
	 *
	 * @param deletedBefore how many deletions the store had recorded before.
	 * @return true when new deletions arrived within the allotted time.
	 */
	private boolean awaitDeletedVectors(int deletedBefore) throws InterruptedException {
		TestVectorStore vectorStore = getTestVectorStore();
		for (int cycle = 0; cycle < DELETED_VECTORS_MAX_WAIT_CYCLES; cycle++) {
			if (vectorStore.getDeletedDocumentIds().size() > deletedBefore) {
				return true;
			}
			Thread.sleep(5000);
		}
		return vectorStore.getDeletedDocumentIds().size() > deletedBefore;
	}

	/**
	 * Exercises the whole contents life of an uploads data source: a first staged
	 * batch, a second batch added when the data source is no longer empty, the
	 * ingestion of everything, then the removal of one file and the reconciliation
	 * of the knowledge base at the following publish.
	 */
	@Test
	public void testUploadsContentsAddedAndDeletedOverTime()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboJobServiceException, InterruptedException {
		GUploadsProjectEndpoint endpoint = createAndPersist("uploads test data", GUploadsProjectEndpoint.class);

		// --- first batch, the only one the handler used to accept ------------------
		endpoint = uploadStagedBatch(endpoint, FIRST_BATCH);
		Path contentsFolder = contentsFolderOf(endpoint);
		assertNull(endpoint.getUploadHandshakeCode(),
				"The handshake code has to be consumed, a later save must not re-apply a staging area");
		assertEquals(FIRST_BATCH.size(), endpoint.getUploadedContents().size(),
				"The first batch has to be tracked in the uploaded contents");
		for (String fileName : fileNamesOf(FIRST_BATCH)) {
			assertTrue(Files.exists(contentsFolder.resolve(fileName)),
					"The uploaded file " + fileName + " has to be in the contents folder");
		}

		// --- second batch: this is what the first-upload-only gate used to drop ----
		endpoint = uploadStagedBatch(endpoint, SECOND_BATCH);
		assertEquals(FIRST_BATCH.size() + SECOND_BATCH.size(), endpoint.getUploadedContents().size(),
				"A data source that already holds files has to accept further batches");
		for (String fileName : fileNamesOf(SECOND_BATCH)) {
			assertTrue(Files.exists(contentsFolder.resolve(fileName)),
					"The file " + fileName + " added later has to be in the contents folder");
		}

		List<UploadedFileInfo> beforeIngestion = uploadsService.listUploadedFiles(endpoint.getCode());
		assertEquals(FIRST_BATCH.size() + SECOND_BATCH.size(), beforeIngestion.size(),
				"Every uploaded file has to be listed");
		for (UploadedFileInfo info : beforeIngestion) {
			assertTrue(info.tracked, "The file " + info.name + " was uploaded through Gebo.ai, it has to be tracked");
			assertFalse(info.ingested, "Nothing can be ingested before the first publish");
			assertTrue(info.size > 0, "The listing has to carry the size of " + info.name);
		}

		// --- publish everything ----------------------------------------------------
		int allFiles = FIRST_BATCH.size() + SECOND_BATCH.size();
		runAndWaitDoneCheckingResults(endpoint, allFiles, true);

		List<UploadedFileInfo> afterIngestion = uploadsService.listUploadedFiles(endpoint.getCode());
		assertEquals(allFiles, afterIngestion.size(), "The publish must not change what the data source holds");
		for (UploadedFileInfo info : afterIngestion) {
			assertTrue(info.ingested, "The file " + info.name + " has to be reported as ingested after the publish");
			assertNotNull(info.documentCode,
					"An ingested file has to carry the code of its document so the editor can open it");
		}

		// --- delete one file -------------------------------------------------------
		UploadedFileInfo removedFile = afterIngestion.get(0);
		OperationStatus<GUploadsProjectEndpoint> deletion = uploadsService.deleteUploadedFiles(endpoint.getCode(),
				List.of(removedFile.absolutePath));
		assertFalse(deletion.isHasErrorMessages(), "The deletion of an owned file cannot report errors");
		assertNotNull(deletion.getResult(), "The deletion has to return the updated data source");
		endpoint = deletion.getResult();
		assertFalse(Files.exists(contentsFolder.resolve(removedFile.name)),
				"The removed file cannot be in the contents folder any more");
		assertEquals(allFiles - 1, endpoint.getUploadedContents().size(),
				"The removed file has to be dropped from the tracked contents");
		assertFalse(endpoint.getUploadedContents().contains(removedFile.name),
				"The tracked contents cannot still name the removed file");
		assertEquals(allFiles - 1, uploadsService.listUploadedFiles(endpoint.getCode()).size(),
				"The listing has to reflect the removal");

		// --- publish again: the knowledge base has to lose the removed contents ----
		// The exact content of the store is not asserted here on purpose: this run is
		// about what LEAVES it, and the disposal of the embeddings is asked for by a
		// message consumed by the vectorization module on its own thread, so it is
		// awaited below rather than expected to be already done.
		int deletedVectorsBefore = getTestVectorStore().getDeletedDocumentIds().size();
		runAndWaitDoneCheckingResults(endpoint, true, false);

		assertTrue(awaitDeletedVectors(deletedVectorsBefore),
				"Publishing after a removal has to dispose of the embeddings of the removed file");
		assertTrue(
				documentReferenceRepository.findById(removedFile.documentCode)
						.map(x -> x.getDeleted() != null && x.getDeleted()).orElse(true),
				"The document of the removed file has to be flagged as deleted or be gone");

		cleanPersistent(endpoint);
	}

	/**
	 * Checks the browsing of a data source and the boundary it is confined to: the
	 * editor browses the contents through the same virtual filesystem abstraction
	 * used for the other data sources, and neither browsing nor deletion may
	 * address anything outside the folder the data source owns.
	 */
	@Test
	public void testUploadsBrowsingAndDeletionStayInsideTheDataSource()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, VirtualFilesystemBrowsingException {
		GUploadsProjectEndpoint endpoint = createAndPersist("uploads browsing test data",
				GUploadsProjectEndpoint.class);

		// Files added straight to an existing data source, the "add more files" path
		// of the editor.
		endpoint = uploadsService.uploadToEndpoint(endpoint.getCode(), multipartsOf(FIRST_BATCH));
		Path contentsFolder = contentsFolderOf(endpoint);
		assertEquals(FIRST_BATCH.size(), endpoint.getUploadedContents().size(),
				"Files uploaded directly have to be tracked as well");

		// --- browsing --------------------------------------------------------------
		OperationStatus<List<GVirtualFilesystemRoot>> roots = browsingController
				.getUploadsEndpointRoots(endpoint.getCode());
		assertNotNull(roots.getResult(), "The browsing has to return a root");
		assertEquals(1, roots.getResult().size(), "A data source has exactly one browsing root, its contents folder");
		GVirtualFilesystemRoot root = roots.getResult().get(0);
		assertEquals(endpoint.getDescription(), root.getDescription(),
				"The root has to be presented with the description of the data source");
		assertEquals("GUploadsProjectEndpoint", root.getIconKey(), "The root has to carry the uploads icon key");

		BrowseParam param = new BrowseParam();
		param.root = root;
		OperationStatus<List<PathInfo>> children = browsingController.browseUploadsEndpointPath(param,
				endpoint.getCode());
		assertNotNull(children.getResult(), "Browsing the root has to return its children");
		assertEquals(FIRST_BATCH.size(), children.getResult().size(),
				"Browsing the root has to list the uploaded files");
		List<String> browsedNames = children.getResult().stream().map(x -> x.name).toList();
		for (String fileName : fileNamesOf(FIRST_BATCH)) {
			assertTrue(browsedNames.contains(fileName), "Browsing has to show the uploaded file " + fileName);
		}

		// --- the deletion boundary -------------------------------------------------
		Path outsideFile = contentsFolder.getParent().resolve("outside-of-the-data-source.txt");
		Files.writeString(outsideFile, "must survive");
		try {
			OperationStatus<GUploadsProjectEndpoint> escaping = uploadsService.deleteUploadedFiles(endpoint.getCode(),
					List.of("../" + outsideFile.getFileName().toString(), outsideFile.toAbsolutePath().toString()));
			assertTrue(escaping.isHasErrorMessages(),
					"Addressing an entry outside the data source has to be reported as an error");
			assertTrue(Files.exists(outsideFile), "A file outside the data source folder cannot be deleted");
			assertEquals(FIRST_BATCH.size(), uploadsService.listUploadedFiles(endpoint.getCode()).size(),
					"A refused deletion cannot touch the contents of the data source");
		} finally {
			Files.deleteIfExists(outsideFile);
		}

		// The folder itself is not deletable content either.
		OperationStatus<GUploadsProjectEndpoint> folderDeletion = uploadsService
				.deleteUploadedFiles(endpoint.getCode(), List.of(contentsFolder.toAbsolutePath().toString()));
		assertTrue(folderDeletion.isHasErrorMessages(), "The contents folder itself cannot be deleted as an entry");
		assertTrue(Files.exists(contentsFolder), "The contents folder has to survive");

		cleanPersistent(endpoint);
	}
}

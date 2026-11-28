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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.filesystem.content.handler.controllers.FileSystemsController;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.impl.repository.ContentHandshakeDataRepository;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * Certifies that a deleted data source leaves no trace of its ingestion: made
 * again under the same name in the same project it gets the same code, and so its
 * documents the same codes, and its files, unchanged since, are indexed again
 * instead of being skipped as already ingested. The deletion goes through the
 * data source's controller, as the admin UI does.
 */
public class DeletedDataSourceRecreatedIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String FILE = "kept-as-it-was.docx";

	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;

	@Autowired
	FileSystemsController fileSystemsController;

	@Autowired
	ContentHandshakeDataRepository acknowledgements;

	@Test
	public void aDataSourceMadeAgainUnderTheSameNameIndexesItsUnchangedFilesAgain() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("deleted data source made again",
				GFilesystemProjectEndpoint.class);
		final String endpointCode = endpoint.getCode();
		final GProject project = getProject(endpoint);
		final GKnowledgeBase kb = persistentObjectManager.findById(GKnowledgeBase.class,
				project.getRootKnowledgeBaseCode());
		final Path original = Files.createTempFile("kept-as-it-was", ".docx");
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), original, StandardCopyOption.REPLACE_EXISTING);
		final FileTime date = Files.getLastModifiedTime(original);

		Path folder = placeFile(endpoint, original, date);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);
		ingest(endpoint);
		final String code = codeOf(endpoint, FILE);
		awaitVectors(code, true);
		assertTrue(acknowledgements.countByContentCodeAndProcessedTrue(code) > 0, "the ingestion acknowledged");

		// deleted through its controller: the record, the documents, the vectors and the
		// acknowledgements of its ingestion all go
		fileSystemsController.deleteFilesystemEndpoint(endpoint);
		awaitDeleted(endpoint);
		awaitVectors(code, false);
		try (Stream<?> left = acknowledgements.findByContentCode(code)) {
			assertEquals(0, left.count(), "no acknowledgement of the deleted data source's documents left");
		}

		// made again under the same name in the same project: the same code
		GFilesystemProjectEndpoint again = new GFilesystemProjectEndpoint();
		again.setDescription(endpoint.getDescription());
		again.setParentProjectCode(project.getCode());
		again = persistentObjectManager.insert(again);
		enableWorkflowSteps(kb, project, again);
		assertEquals(endpointCode, again.getCode(), "the data source made again has the same code");

		// the same file, as it was: same name, size and modification date
		folder = placeFile(again, original, date);
		again.setPath(List.of(VFilesystemReference.from(folder)));
		again = persistentObjectManager.update(again);
		final ComputedWorkflowStatus status = ingest(again).getWorkflowStatus().getRootStatus();
		assertEquals(code, codeOf(again, FILE), "the document has the same code");
		assertFalse(awaitVectors(code, true).isEmpty(), "the unchanged file is indexed again");
		assertEquals(0, status.getBatchDiscardedInput(), "nothing skipped as already ingested");
		Files.deleteIfExists(original);
		cleanPersistent(again);
	}

	/** Copies the file into the endpoint's folder with the given modification date. */
	private Path placeFile(GFilesystemProjectEndpoint endpoint, Path source, FileTime date) throws Exception {
		final GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		final Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		final Path file = folder.resolve(FILE);
		Files.copy(source, file, StandardCopyOption.REPLACE_EXISTING);
		Files.setLastModifiedTime(file, date);
		return folder;
	}

	/** Waits for the deletion messages to have removed the data source and its documents. */
	private void awaitDeleted(GFilesystemProjectEndpoint endpoint) throws Exception {
		final long deadline = System.currentTimeMillis() + 60_000l;
		boolean gone = false;
		while (!gone && System.currentTimeMillis() < deadline) {
			Thread.sleep(1000);
			try (Stream<GDocumentReference> documents = documentReferenceRepository
					.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(
							endpoint.getClass().getName(), endpoint.getCode())) {
				gone = persistentObjectManager.findById(GFilesystemProjectEndpoint.class, endpoint.getCode()) == null
						&& documents.findAny().isEmpty();
			}
		}
		assertTrue(gone, "the data source and its documents have to be deleted");
	}

	/** Runs an ingestion of the endpoint and waits for its end. */
	private JobSummary ingest(GFilesystemProjectEndpoint endpoint) throws Exception {
		final GJobStatus job = ingestionJobService.executeSyncJob(endpoint, null, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name());
		JobSummary summary = null;
		int cycles = 0;
		Thread.sleep(5000);
		do {
			Thread.sleep(5000);
			summary = workflowStatsService.getJobSummary(job.getCode());
			printSummary(summary);
			cycles++;
		} while (!(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished()) && cycles < 40);
		assertTrue(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished(),
				"the ingestion has to end");
		assertFalse(summary.getWorkflowStatus().isHasErrors(), "the ingestion has no errors");
		return summary;
	}

	private String codeOf(GFilesystemProjectEndpoint endpoint, String name) {
		try (Stream<GDocumentReference> documents = documentReferenceRepository
				.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
						endpoint.getClass().getName(), endpoint.getCode(), "-")) {
			return documents.filter(x -> name.equals(x.getName())).map(GDocumentReference::getCode).findFirst()
					.orElseThrow(() -> new AssertionError("no document " + name));
		}
	}

	/** Waits for the document to have vectors, or to have none, and gives how many it has. */
	private List<String> awaitVectors(String code, boolean present) throws InterruptedException {
		final long deadline = System.currentTimeMillis() + 60_000l;
		List<String> now = vectorIds(code);
		while (System.currentTimeMillis() < deadline && now.isEmpty() == present) {
			Thread.sleep(1000);
			now = vectorIds(code);
		}
		assertEquals(present, !now.isEmpty(),
				present ? "the document " + code + " has to be indexed" : "the vectors of " + code + " have to go");
		return now;
	}

	private List<String> vectorIds(String code) {
		return getTestVectorStore().getAllData().stream()
				.filter(x -> x.getMetadata() != null && code.equals(x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)))
				.map(x -> x.getId()).toList();
	}
}

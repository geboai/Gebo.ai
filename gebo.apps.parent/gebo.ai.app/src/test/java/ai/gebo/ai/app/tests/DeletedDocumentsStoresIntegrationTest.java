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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService;
import ai.gebo.jobs.services.IGeboInversionOfControlIngestionService.GConsumers;
import ai.gebo.systems.abstraction.layer.NoContentConsumingSessionParam;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * Certifies what a deleted document leaves behind, over the whole ingestion workflow
 * (content handler, chunker, vectorizer): its record stays, logically deleted, and its
 * vectors are erased - whether the deletion is found by the deletions check ending an
 * ingestion or reported by the source itself, as the folder watcher does. The job
 * counts it among its deleted documents, not in its input.
 */
public class DeletedDocumentsStoresIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String WATCHED = "deleted-while-watched.docx";
	private static final String PUBLISHED = "deleted-before-a-publish.odt";
	private static final String STAYING = "staying.docx";
	private static final String OTHER_DOCX = "TEST-CONTENTS-001/file-sample_1MB.docx";

	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;

	@Autowired
	IGeboInversionOfControlIngestionService inversionOfControlIngestionService;

	@Autowired
	JobStatusRepository jobStatusRepository;

	@Test
	public void aDeletedDocumentKeepsItsRecordAndLosesItsVectors() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("deleted documents stores",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		Path watched = folder.resolve(WATCHED);
		Path published = folder.resolve(PUBLISHED);
		Path staying = folder.resolve(STAYING);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), watched, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(extractResource(TEST_001_ODT_FILE, ".odt"), published, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(extractResource(OTHER_DOCX, ".docx"), staying, StandardCopyOption.REPLACE_EXISTING);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);

		ingest(endpoint);
		final String watchedCode = codeOf(endpoint, WATCHED);
		final String publishedCode = codeOf(endpoint, PUBLISHED);
		final String stayingCode = codeOf(endpoint, STAYING);
		awaitVectors(watchedCode, true);
		awaitVectors(publishedCode, true);
		final Set<String> stayingVectors = awaitVectors(stayingCode, true);

		// 1. the source itself reports the deletion: a session of the folder watcher
		Files.delete(watched);
		watcherReports(endpoint, folder, watched, ChangedFile.Type.DELETE);
		assertTrue(awaitVectors(watchedCode, false).isEmpty(), "the vectors of the document reported deleted erased");
		final GDocumentReference watchedRecord = documentReferenceRepository.findById(watchedCode).orElse(null);
		assertTrue(watchedRecord != null, "the record of the deleted document stays");
		assertEquals(Boolean.TRUE, watchedRecord.getDeleted(), "logically deleted");
		assertFalse(vectorIds(publishedCode).isEmpty(), "the other documents' vectors untouched");
		assertEquals(stayingVectors, vectorIds(stayingCode));
		final ComputedWorkflowStatus watcherSession = awaitDeletedCounted(lastJobOf(endpoint), 1);
		assertEquals(2, watcherSession.getBatchDocumentsInput(),
				"the two documents still there are the session's input, the deleted one is not");

		// 2. the deletions check ending an ingestion finds the deletion
		Files.delete(published);
		final ComputedWorkflowStatus publish = ingest(endpoint).getWorkflowStatus().getRootStatus();
		assertEquals(1, publish.getBatchDeletedDocuments(), "the document found deleted counted");
		assertEquals(1, publish.getBatchDocumentsInput(), "the document still there is the input");
		assertTrue(awaitVectors(publishedCode, false).isEmpty(), "the vectors of the document found deleted erased");
		final GDocumentReference publishedRecord = documentReferenceRepository.findById(publishedCode).orElse(null);
		assertTrue(publishedRecord != null, "the record of the deleted document stays");
		assertEquals(Boolean.TRUE, publishedRecord.getDeleted(), "logically deleted");
		assertEquals(stayingVectors, vectorIds(stayingCode), "the document still there keeps its vectors");
		assertEquals(Boolean.TRUE, documentReferenceRepository.findById(watchedCode).get().getDeleted(),
				"the document reported deleted before stays deleted");
		cleanPersistent(endpoint);
	}

	/** A session of the folder watcher on one changed file, as GFilesystemChangesHandlingService.onChange runs it. */
	private void watcherReports(GFilesystemProjectEndpoint endpoint, Path folder, Path file, ChangedFile.Type type)
			throws Exception {
		final ChangedFiles changes = new ChangedFiles(folder.toFile(),
				Set.of(new ChangedFile(folder.toFile(), file.toFile(), type)));
		final GConsumers consumers = inversionOfControlIngestionService.getConsumer(endpoint,
				new NoContentConsumingSessionParam());
		try {
			filesystemHandler.consume(endpoint, consumers.getContentConsumer(), consumers.getMessagesConsumer(),
					consumers.getErrorConsumer(), changes);
		} finally {
			consumers.getContentConsumer().endConsuming();
			consumers.getMessagesConsumer().endConsuming();
			consumers.end();
		}
	}

	/** The code of the last job started on the endpoint. */
	private String lastJobOf(GFilesystemProjectEndpoint endpoint) {
		return jobStatusRepository
				.findFirstByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeOrderByStartDateTimeDesc(
						endpoint.getClass().getName(), endpoint.getCode())
				.orElseThrow(() -> new AssertionError("no job on the endpoint")).getCode();
	}

	/** Waits for the job's discovery step to count the deleted documents expected. */
	private ComputedWorkflowStatus awaitDeletedCounted(String jobCode, long expected) throws Exception {
		final long deadline = System.currentTimeMillis() + 60_000l;
		ComputedWorkflowStatus root = null;
		while (System.currentTimeMillis() < deadline) {
			final JobSummary summary = workflowStatsService.getJobSummary(jobCode);
			root = summary != null && summary.getWorkflowStatus() != null ? summary.getWorkflowStatus().getRootStatus()
					: null;
			if (root != null && root.getBatchDeletedDocuments() == expected) {
				return root;
			}
			Thread.sleep(1000);
		}
		throw new AssertionError("the job " + jobCode + " counts "
				+ (root != null ? root.getBatchDeletedDocuments() : "no") + " deleted document(s), expected " + expected);
	}

	/** Runs an ingestion of the endpoint and waits for its end. */
	private JobSummary ingest(GFilesystemProjectEndpoint endpoint) throws Exception {
		GJobStatus job = ingestionJobService.executeSyncJob(endpoint, null, GWorkflowType.STANDARD.name(),
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

	/** The ids of the document's vectors stored now (the deleted ones left out). */
	private Set<String> vectorIds(String code) {
		return getTestVectorStore().getAllData().stream()
				.filter(x -> x.getMetadata() != null && code.equals(x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)))
				.map(Document::getId).collect(Collectors.toCollection(HashSet::new));
	}

	/** Waits for the document to have vectors, or to have none, and gives the ones it has. */
	private Set<String> awaitVectors(String code, boolean present) throws InterruptedException {
		final long deadline = System.currentTimeMillis() + 60_000l;
		Set<String> now = vectorIds(code);
		while (System.currentTimeMillis() < deadline && now.isEmpty() == present) {
			Thread.sleep(1000);
			now = vectorIds(code);
		}
		assertEquals(present, !now.isEmpty(),
				present ? "the document " + code + " has to be indexed" : "the vectors of " + code + " have to go");
		return now;
	}
}

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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.ragsystem.vectorstores.test.services.TestVectorStore;
import ai.gebo.systems.abstraction.layer.controllers.ContentsResetController;
import ai.gebo.systems.abstraction.layer.controllers.ContentsResetController.ResetContentRequest;
import ai.gebo.systems.abstraction.layer.impl.repository.ContentHandshakeDataRepository;
import ai.gebo.systems.abstraction.layer.model.ContentHandshakeData;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * Certifies the re-ingestion change detection over the whole ingestion workflow
 * (content handler, chunker, vectorizer): the vectorizer acknowledges each
 * document it indexed to its content handler with the hash of its text; on the
 * next ingestions the chunker does not read again a document with the date and
 * size last ingested, discards one read again whose text is the same, and sends
 * on only a document whose text changed, whose vectors are then replaced. A
 * contents reset makes the next ingestion index every document again, each
 * document keeping its uniqueId.
 */
public class ReingestionChangeDetectionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String CHANGING = "changing.docx";
	private static final String STABLE = "stable.odt";
	private static final String OTHER_DOCX = "TEST-CONTENTS-001/file-sample_1MB.docx";

	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;

	@Autowired
	ContentHandshakeDataRepository handshakeRepository;

	@Autowired
	ContentsResetController contentsResetController;

	@Test
	public void onlyADocumentWhoseTextChangedIsIndexedAgain() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("re-ingestion change detection",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		Path changing = folder.resolve(CHANGING);
		Path stable = folder.resolve(STABLE);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), changing, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(extractResource(TEST_001_ODT_FILE, ".odt"), stable, StandardCopyOption.REPLACE_EXISTING);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);

		// 1. first ingestion: both indexed, both acknowledged with the hash of their text
		ComputedWorkflowStatus chunker = chunkerOf(ingest(endpoint));
		assertEquals(2, chunker.getBatchSentToNextStep(), "both documents indexed the first time");
		final String changingCode = codeOf(endpoint, CHANGING);
		final String stableCode = codeOf(endpoint, STABLE);
		final ContentHandshakeData changingFirst = awaitAcknowledged(changingCode, null);
		final ContentHandshakeData stableFirst = awaitAcknowledged(stableCode, null);
		assertNotNull(changingFirst.getHash(), "the hash of the text indexed acknowledged");
		assertEquals(Files.size(changing), changingFirst.getFileSize(), "with the size of the document indexed");
		assertNotNull(stableFirst.getHash());
		final Set<String> changingVectors = vectorIds(changingCode);
		final Set<String> stableVectors = vectorIds(stableCode);
		assertFalse(changingVectors.isEmpty());
		assertFalse(stableVectors.isEmpty());
		final int deletedBefore = getTestVectorStore().getDeletedDocumentIds().size();

		// 2. nothing changed: neither document read again nor indexed
		chunker = chunkerOf(ingest(endpoint));
		assertEquals(2, chunker.getBatchDiscardedInput(), "both documents unchanged");
		assertEquals(0, chunker.getBatchSentToNextStep());
		assertEquals(0, chunker.getChunksProcessed(), "not read again: no chunk made");
		assertEquals(changingVectors, vectorIds(changingCode));
		assertEquals(stableVectors, vectorIds(stableCode));
		assertEquals(deletedBefore, getTestVectorStore().getDeletedDocumentIds().size(), "no vector deleted");

		// 3. a document touched, its text the same: read again, its text hash the same, discarded
		Files.setLastModifiedTime(changing, FileTime.fromMillis(Files.getLastModifiedTime(changing).toMillis() + 120_000l));
		chunker = chunkerOf(ingest(endpoint));
		assertEquals(2, chunker.getBatchDiscardedInput(), "a new date with the same text is no change");
		assertEquals(0, chunker.getBatchSentToNextStep());
		assertTrue(chunker.getChunksProcessed() > 0, "the touched document read again");
		assertEquals(changingVectors, vectorIds(changingCode));
		assertEquals(deletedBefore, getTestVectorStore().getDeletedDocumentIds().size(), "no vector deleted");

		// 4. a document whose text changed: only it indexed again, its vectors replaced
		Files.copy(extractResource(OTHER_DOCX, ".docx"), changing, StandardCopyOption.REPLACE_EXISTING);
		Files.setLastModifiedTime(changing, FileTime.fromMillis(System.currentTimeMillis()));
		chunker = chunkerOf(ingest(endpoint));
		assertEquals(1, chunker.getBatchSentToNextStep(), "only the changed document indexed again");
		assertEquals(1, chunker.getBatchDiscardedInput(), "the other one unchanged");
		final ContentHandshakeData changingSecond = awaitAcknowledged(changingCode, changingFirst.getReceivedDate());
		assertNotEquals(changingFirst.getHash(), changingSecond.getHash(), "the new text acknowledged");
		assertEquals(Files.size(changing), changingSecond.getFileSize());
		final Set<String> changedVectors = awaitVectorsReplaced(changingCode, changingVectors);
		assertFalse(changedVectors.isEmpty(), "the changed document indexed");
		assertTrue(getTestVectorStore().getDeletedDocumentIds().containsAll(changingVectors),
				"the vectors of its old text deleted");
		assertEquals(stableVectors, vectorIds(stableCode), "the unchanged document's vectors untouched");

		// 5. the contents reset: every document indexed again, each keeping its uniqueId
		final Long changingUniqueId = documentReferenceRepository.findById(changingCode).get().getUniqueId();
		final Long stableUniqueId = documentReferenceRepository.findById(stableCode).get().getUniqueId();
		assertNotNull(changingUniqueId);
		assertNotNull(stableUniqueId);
		final ContentHandshakeData changingBeforeReset = awaitAcknowledged(changingCode, null);
		final ContentHandshakeData stableBeforeReset = awaitAcknowledged(stableCode, null);
		final ResetContentRequest reset = new ResetContentRequest();
		reset.projectEndpoint = GObjectRef.<GProjectEndpoint>of(endpoint);
		assertEquals(2, contentsResetController.resetContentsIngestion(reset).resetEntries);
		assertTrue(documentReferenceRepository.findById(changingCode).isPresent(), "the documents kept");
		chunker = chunkerOf(ingest(endpoint));
		assertEquals(2, chunker.getBatchSentToNextStep(), "every document indexed again after the reset");
		assertEquals(0, chunker.getBatchDiscardedInput());
		awaitAcknowledged(changingCode, changingBeforeReset.getReceivedDate());
		awaitAcknowledged(stableCode, stableBeforeReset.getReceivedDate());
		assertEquals(changingUniqueId, documentReferenceRepository.findById(changingCode).get().getUniqueId(),
				"the uniqueId kept across the reset");
		assertEquals(stableUniqueId, documentReferenceRepository.findById(stableCode).get().getUniqueId());
		cleanPersistent(endpoint);
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

	/** The chunker's step (TOKENIZATION) in the job's workflow status. */
	private static ComputedWorkflowStatus chunkerOf(JobSummary summary) {
		final List<ComputedWorkflowStatus> found = new ArrayList<>();
		collect(summary.getWorkflowStatus().getRootStatus(), found);
		return found.stream().filter(x -> GStandardWorkflowStep.TOKENIZATION.name().equalsIgnoreCase(x.getWorkflowStepId()))
				.findFirst().orElseThrow(() -> new AssertionError("no chunker step in the job status"));
	}

	private static void collect(ComputedWorkflowStatus status, List<ComputedWorkflowStatus> found) {
		if (status == null) {
			return;
		}
		found.add(status);
		for (ComputedWorkflowStatus child : status.getChilds()) {
			collect(child, found);
		}
	}

	private String codeOf(GFilesystemProjectEndpoint endpoint, String name) {
		try (Stream<GDocumentReference> documents = documentReferenceRepository
				.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
						endpoint.getClass().getName(), endpoint.getCode(), "-")) {
			return documents.filter(x -> name.equals(x.getName())).map(GDocumentReference::getCode).findFirst()
					.orElseThrow(() -> new AssertionError("no document " + name));
		}
	}

	/** The newest acknowledgement of the document received after the given date. */
	private ContentHandshakeData awaitAcknowledged(String code, Date after) throws InterruptedException {
		final long deadline = System.currentTimeMillis() + 60_000l;
		while (System.currentTimeMillis() < deadline) {
			final List<ContentHandshakeData> acks;
			try (Stream<ContentHandshakeData> stream = handshakeRepository.findByContentCode(code)) {
				acks = stream.filter(x -> Boolean.TRUE.equals(x.getProcessed()) && x.getReceivedDate() != null
						&& (after == null || x.getReceivedDate().after(after))).toList();
			}
			if (!acks.isEmpty()) {
				return acks.stream().max((a, b) -> a.getReceivedDate().compareTo(b.getReceivedDate())).get();
			}
			Thread.sleep(1000);
		}
		throw new AssertionError("no acknowledgement of " + code + " received" + (after != null ? " after " + after : ""));
	}

	/** The ids of the document's vectors stored now (the deleted ones left out). */
	private Set<String> vectorIds(String code) {
		final TestVectorStore store = getTestVectorStore();
		return store.getAllData().stream()
				.filter(x -> x.getMetadata() != null && code.equals(x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)))
				.map(Document::getId).collect(Collectors.toCollection(HashSet::new));
	}

	private Set<String> awaitVectorsReplaced(String code, Set<String> old) throws InterruptedException {
		final long deadline = System.currentTimeMillis() + 60_000l;
		Set<String> now = vectorIds(code);
		while (System.currentTimeMillis() < deadline && (now.isEmpty() || now.equals(old))) {
			Thread.sleep(1000);
			now = vectorIds(code);
		}
		return now;
	}
}

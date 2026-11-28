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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.UserMessageRepository;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.workflows.compute.model.JobSummary;
import ai.gebo.workflows.compute.service.impl.GWorkflowJobCloser;

/**
 * What a reading session could not read is told and counted, and a job is
 * closed once.
 * <ul>
 * <li>A data source that cannot be read at all (here its folder does not exist)
 * gets an error message on its job, and the job counts the failure among its
 * errors. Both used to be only in the log.</li>
 * <li>A folder that cannot be read is skipped: the rest of the data source is
 * read, and the contents of the folder, not met, are not told deleted. The
 * whole reading used to stop at the folder.</li>
 * <li>A job is closed as soon as its workflow is done, by the end of workflow
 * receiver, through an update that only matches a job still processing: the
 * status daemon, which used to close it on its next round and broadcast its end
 * a second time, finds it closed.</li>
 * </ul>
 */
public class ReadFailureAndJobEndIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;
	@Autowired
	UserMessageRepository userMessageRepository;
	@Autowired
	GWorkflowJobCloser jobCloser;

	@Test
	public void aDataSourceThatCannotBeReadIsToldAndCountedAsAnError() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("unreadable data source",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path missing = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint), "missing");
		Files.deleteIfExists(missing);
		endpoint.setPath(List.of(VFilesystemReference.from(missing)));
		endpoint = persistentObjectManager.update(endpoint);
		try {
			// the selected folder that is gone does not stop the reading: it is told and counted
			final String jobCode = ingestionJobService.executeSyncJob(endpoint, null, GWorkflowType.STANDARD.name(),
					GStandardWorkflow.INGESTION.name()).getCode();

			JobSummary summary = awaitFinished(jobCode);
			final ComputedWorkflowStatus discovery = summary.getWorkflowStatus().getRootStatus();
			assertEquals(0, discovery.getBatchDocumentsInput(), "nothing was read");
			assertEquals(1, discovery.getBatchDocumentsProcessingErrors(), "the failed reading is an error");
			assertTrue(summary.getWorkflowStatus().isHasErrors(), "the job has errors");

			List<GUserMessage> errors = awaitErrorMessages(jobCode);
			assertEquals(1, errors.size(), "one error message, not one per place that saw the failure");
			assertTrue(errors.get(0).getDetail().contains("existent directory"),
					"the message tells why: " + errors.get(0).getDetail());
		} finally {
			cleanPersistent(endpoint);
		}
	}

	@Test
	public void aFolderThatCannotBeReadIsSkippedAndItsContentsAreKept() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("folder that cannot be read",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Path locked = folder.resolve("locked");
		Path open = folder.resolve("open");
		Files.createDirectories(locked);
		Files.createDirectories(open);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), folder.resolve("first.docx"),
				StandardCopyOption.REPLACE_EXISTING);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), locked.resolve("behind-the-lock.docx"),
				StandardCopyOption.REPLACE_EXISTING);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), open.resolve("next-to-the-lock.docx"),
				StandardCopyOption.REPLACE_EXISTING);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);
		try {
			JobSummary first = awaitFinished(ingestionJobService.executeSyncJob(endpoint, null,
					GWorkflowType.STANDARD.name(), GStandardWorkflow.INGESTION.name()).getCode());
			assertFalse(first.getWorkflowStatus().isHasErrors(), "the first reading has no errors");
			assertEquals(3, first.getWorkflowStatus().getRootStatus().getBatchDocumentsInput(), "three files");
			final String lockedCode = documentNamed(endpoint, "behind-the-lock.docx").getCode();
			assertFalse(awaitVectors(lockedCode).isEmpty(), "the file in the folder is indexed");

			Files.setPosixFilePermissions(locked, Set.of());
			assumeFalse(Files.isReadable(locked), "the folder can be made unreadable (not running as root)");
			JobSummary second = awaitFinished(ingestionJobService.executeSyncJob(endpoint, null,
					GWorkflowType.STANDARD.name(), GStandardWorkflow.INGESTION.name()).getCode());
			final ComputedWorkflowStatus discovery = second.getWorkflowStatus().getRootStatus();
			assertEquals(2, discovery.getBatchDocumentsInput(), "the files out of the unreadable folder are read");
			assertEquals(1, discovery.getBatchDocumentsProcessingErrors(), "the unreadable folder is an error");
			assertTrue(second.getWorkflowStatus().isHasErrors(), "the job has errors");
			List<GUserMessage> errors = awaitErrorMessages(second.getCode());
			assertEquals(1, errors.size(), "one error message");
			assertTrue(errors.get(0).getSummary().contains("locked"), "it names the folder: " + errors.get(0).getSummary());

			GDocumentReference behindTheLock = documentNamed(endpoint, "behind-the-lock.docx");
			assertFalse(Boolean.TRUE.equals(behindTheLock.getDeleted()), "a file not read is not told deleted");
			assertFalse(vectorsOf(lockedCode).isEmpty(), "and its vectors stay");
		} finally {
			Files.setPosixFilePermissions(locked, PosixFilePermissions.fromString("rwxr-xr-x"));
			cleanPersistent(endpoint);
		}
	}

	@Test
	public void aJobIsClosedOnceAsSoonAsItsWorkflowIsDone() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("closed once", GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), folder.resolve("closed-once.docx"),
				StandardCopyOption.REPLACE_EXISTING);
		endpoint.setPath(List.of(VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);
		try {
			GJobStatus job = ingestionJobService.executeSyncJob(endpoint, null, GWorkflowType.STANDARD.name(),
					GStandardWorkflow.INGESTION.name());
			JobSummary summary = awaitFinished(job.getCode());
			assertFalse(summary.getWorkflowStatus().isHasErrors(), "the ingestion has no errors");

			// the status daemon looks every two minutes: well before, the job is closed
			GJobStatus closed = null;
			for (int i = 0; i < 30; i++) {
				closed = persistentObjectManager.findById(GJobStatus.class, job.getCode());
				if (closed != null && Boolean.FALSE.equals(closed.getProcessing())) {
					break;
				}
				Thread.sleep(1000);
			}
			assertNotNull(closed, "the job exists");
			assertEquals(Boolean.FALSE, closed.getProcessing(), "the job is closed once its workflow is done");
			assertEquals(Boolean.TRUE, closed.getFinished(), "and finished");
			assertNotNull(closed.getEndDateTime(), "with its end date");
			assertFalse(jobCloser.closeAndBroadcastEnded(job.getCode()),
					"a job already closed is not closed, nor its end broadcast, again");
		} finally {
			cleanPersistent(endpoint);
		}
	}

	private JobSummary awaitFinished(String jobCode) throws Exception {
		JobSummary summary = null;
		int cycles = 0;
		do {
			Thread.sleep(5000);
			summary = workflowStatsService.getJobSummary(jobCode);
			printSummary(summary);
			cycles++;
		} while (!(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished()) && cycles < 40);
		assertTrue(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished(),
				"the job's workflow has to end");
		return summary;
	}

	private GDocumentReference documentNamed(GFilesystemProjectEndpoint endpoint, String name) throws Exception {
		return persistentObjectManager.findAll(GDocumentReference.class).stream()
				.filter(d -> d.getProjectEndpointReference() != null
						&& endpoint.getCode().equals(d.getProjectEndpointReference().getCode())
						&& name.equals(d.getName()))
				.findFirst().orElseThrow(() -> new AssertionError("no document " + name));
	}

	private List<Document> awaitVectors(String documentCode) throws InterruptedException {
		List<Document> vectors = List.of();
		for (int i = 0; i < 60 && vectors.isEmpty(); i++) {
			vectors = vectorsOf(documentCode);
			if (vectors.isEmpty()) {
				Thread.sleep(1000);
			}
		}
		return vectors;
	}

	/**
	 * The document's vectors in the test store, read from its data: its metadata
	 * index keeps the vectors it deleted, so it would not show one gone.
	 */
	private List<Document> vectorsOf(String documentCode) {
		return getTestVectorStore().getAllData().stream().filter(
				x -> x.getMetadata() != null && documentCode.equals(x.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)))
				.toList();
	}

	private List<GUserMessage> awaitErrorMessages(String jobCode) throws InterruptedException {
		List<GUserMessage> errors = List.of();
		// the jobs master stores the user messages in batches, flushed when its queue
		// waits for a while: a message can take some tens of seconds to be stored
		for (int i = 0; i < 60 && errors.isEmpty(); i++) {
			errors = userMessageRepository.findByJobId(jobCode)
					.filter(m -> m.getSeverity() == GUserMessage.MsgServerity.error).collect(Collectors.toList());
			if (errors.isEmpty()) {
				Thread.sleep(1000);
			}
		}
		// a few more seconds for a second message, if one were sent
		Thread.sleep(3000);
		return userMessageRepository.findByJobId(jobCode)
				.filter(m -> m.getSeverity() == GUserMessage.MsgServerity.error).collect(Collectors.toList());
	}
}

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.knowledgebase.repositories.UserMessageRepository;
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
	JobStatusRepository jobStatusRepository;
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
			final GFilesystemProjectEndpoint unreadable = endpoint;
			assertThrows(GeboJobServiceException.class, () -> ingestionJobService.executeSyncJob(unreadable, null,
					GWorkflowType.STANDARD.name(), GStandardWorkflow.INGESTION.name()), "the reading fails");
			List<GJobStatusItem> jobs = jobStatusRepository
					.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndProcessingTrue(
							GFilesystemProjectEndpoint.class.getName(), endpoint.getCode());
			assertEquals(1, jobs.size(), "the job of the failed reading");
			final String jobCode = jobs.get(0).getCode();

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

	private List<GUserMessage> awaitErrorMessages(String jobCode) throws InterruptedException {
		List<GUserMessage> errors = List.of();
		for (int i = 0; i < 20 && errors.isEmpty(); i++) {
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

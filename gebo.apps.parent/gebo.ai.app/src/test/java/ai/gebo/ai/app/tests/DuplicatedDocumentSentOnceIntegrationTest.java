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
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * A document a reading session meets twice (here: its folder chosen twice in
 * the data source) is sent to the ingestion once. Its second arrival is input
 * the discovery discards.
 */
public class DuplicatedDocumentSentOnceIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;

	@Test
	public void aDocumentMetTwiceInASessionIsSentOnce() throws Exception {
		GFilesystemProjectEndpoint endpoint = createAndPersist("duplicated document", GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		Path folder = Path.of(localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint));
		Files.createDirectories(folder);
		Files.copy(extractResource(TEST_001_DOCX_FILE, ".docx"), folder.resolve("met-twice.docx"),
				StandardCopyOption.REPLACE_EXISTING);
		// the same folder chosen twice: the session meets its document twice
		endpoint.setPath(List.of(VFilesystemReference.from(folder), VFilesystemReference.from(folder)));
		endpoint = persistentObjectManager.update(endpoint);
		try {
			final ComputedWorkflowStatus discovery = ingest(endpoint).getWorkflowStatus().getRootStatus();
			assertEquals(2, discovery.getBatchDocumentsInput(), "the document is met twice");
			assertEquals(1, discovery.getBatchSentToNextStep(), "and sent to the ingestion once");
			assertEquals(1, discovery.getBatchDiscardedInput(), "its second arrival is discarded");
			assertFalse(discovery.getChilds().isEmpty(), "the ingestion has steps after the discovery");
			for (ComputedWorkflowStatus step : discovery.getChilds()) {
				assertEquals(1,
						step.getBatchDocumentsProcessed() + step.getBatchDiscardedInput()
								+ step.getBatchDocumentsProcessingErrors(),
						"the step " + step.getWorkflowStepId() + " receives the document once");
			}
		} finally {
			cleanPersistent(endpoint);
		}
	}

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
}

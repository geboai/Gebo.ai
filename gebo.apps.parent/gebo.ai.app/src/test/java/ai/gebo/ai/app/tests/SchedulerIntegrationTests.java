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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.git.content.handler.controllers.GITSystemsController;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingFrequency;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingTime;
import ai.gebo.model.OperationStatus;

/**
 * End-to-end proof that the centralized publish scheduler
 * (gebo.architecture.scheduling's AbstractCentralSchedulingService, the
 * MonolithicCentralSchedulingService flavor here) actually fires a job: a
 * project endpoint is programmed with a single one-shot run ~2 minutes in the
 * future (not launched directly), then the test waits for the scheduler's own
 * {@code @Scheduled} tick to dispatch it - proving the whole message chain
 * (controller -> GRescheduleProjectEndpointMessagePayload -> central
 * scheduler -> PublishProjectEndpointMessagePayload -> GitJobLaunchManager)
 * works, not just that a job can be launched synchronously.
 */
public class SchedulerIntegrationTests extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	private GITSystemsController gitSystemsController;

	@Test
	public void scheduledPublishIsLaunchedByCentralScheduler() throws InstantiationException, IllegalAccessException,
			GeboPersistenceException, InterruptedException {
		LOGGER.info("Start scheduled publish test");

		GGitContentManagementSystem system = new GGitContentManagementSystem();
		system.setDescription("Default git system");
		system.setPublicAccess(true);
		system.setBaseUri("https://github.com/");
		system = persistentObjectManager.insert(system);

		GGitProjectEndpoint endpoint = createAndPersist("scheduler test git project", GGitProjectEndpoint.class);
		endpoint.setPublicAccess(true);
		endpoint.setRepositoryUri("https://github.com/chrishantha/sample-java-programs.git");
		endpoint.setBranch("main");
		endpoint.setContentManagementSystem(system.getCode());
		endpoint.setPublished(true);
		endpoint.setSynchPeriodically(true);

		long scheduledAt = System.currentTimeMillis() + Duration.ofMinutes(2).toMillis();
		ReindexingTime time = new ReindexingTime();
		time.setCreatedTime(System.currentTimeMillis());
		time.setTimeComponent(List.of(scheduledAt));
		ReindexingProgrammedTable table = new ReindexingProgrammedTable();
		table.setFrequency(ReindexingFrequency.DATES);
		table.setTimes(List.of(time));
		endpoint.setProgrammedTables(List.of(table));

		// Goes through the controller (not persistentObjectManager.update directly)
		// so GAbstractSystemsArchitectureController.processReschedule actually sends
		// the GRescheduleProjectEndpointMessagePayload to the central scheduler.
		OperationStatus<GGitProjectEndpoint> result = gitSystemsController.updateGitEndpoint(endpoint);
		assertFalse(result.isHasErrorMessages(), "Updating the endpoint must not report errors: "
				+ result.getMessages().stream().map(m -> m.getSummary()).collect(Collectors.joining(", ")));
		endpoint = result.getResult();

		LOGGER.info("Endpoint " + endpoint.getCode() + " scheduled for " + scheduledAt
				+ " - waiting for the central scheduler to dispatch it");

		// The run is ~2 minutes out and the scheduler ticks every 60s, so give it
		// comfortable room either side rather than polling from the very start.
		Thread.sleep(Duration.ofSeconds(90).toMillis());

		List<GJobStatus> launched = List.of();
		int nCycles = 0;
		int NMAXCYCLES = 10;
		do {
			launched = statusRepo
					.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(endpoint.getClass().getName(),
							endpoint.getCode())
					.toList();
			LOGGER.info("On cycle=>" + nCycles + " jobs launched for endpoint so far:" + launched.size());
			if (!launched.isEmpty()) {
				break;
			}
			Thread.sleep(Duration.ofSeconds(20).toMillis());
			nCycles++;
		} while (nCycles < NMAXCYCLES);

		assertTrue(!launched.isEmpty(),
				"The central scheduler must have dispatched a publish for the scheduled endpoint by now");

		cleanPersistent(endpoint);
		LOGGER.info("End scheduled publish test");
	}
}

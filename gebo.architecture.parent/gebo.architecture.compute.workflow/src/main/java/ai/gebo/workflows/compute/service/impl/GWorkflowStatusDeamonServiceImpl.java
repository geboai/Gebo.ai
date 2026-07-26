/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.workflows.compute.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Implementation of the IGGeboScheduledSyncronizationService interface that
 * handles scheduled ingestion tasks. This singleton component manages
 * scheduling of content reading and vectorization for project endpoints, and
 * maintains job statuses in the system.
 */

@Component
@Scope("singleton")
@AllArgsConstructor
public class GWorkflowStatusDeamonServiceImpl {
	static Logger LOGGER = LoggerFactory.getLogger(GWorkflowStatusDeamonServiceImpl.class);
	private final IGPersistentObjectManager persistenceManager;
	private final JobStatusRepository jobStatusRepository;
	private final WorkflowStatusEmitter statusEmitter;
	private final IWorkflowStatusHandlerRepositoryPattern workflowStatusRepoPattern;
	public static final int CHECK_JOB_STATUS_PERIOD = 1000 * 60 * 2;
	public static final int EXECUTION_TIMEOUT = 1000 * 60 * 120;

	@Scheduled(initialDelay = 10000, fixedRate = CHECK_JOB_STATUS_PERIOD)
	public void checkJobsStatus() {
		Stream<GJobStatusItem> processingStream = jobStatusRepository.findByProcessingTrue();
		processingStream.forEach(status -> {
			long now = System.currentTimeMillis();
			// Every still-processing job is re-evaluated on every scheduled run, not just
			// ones started within the last CHECK_JOB_STATUS_PERIOD - that used to gate
			// entry into this block, but the batch-finished / timeout-passed checks below
			// need to keep running for as long as a job stays in "processing" state
			// (ingestion routinely takes longer than one CHECK_JOB_STATUS_PERIOD), and
			// timeoutPassed - checked against the much longer EXECUTION_TIMEOUT - could
			// never fire while gated behind the short recency window anyway.
			if (status.getStartDateTime() != null) {
				GWorkflowType wType = GWorkflowType.STANDARD;
				try {
					wType = GWorkflowType.valueOf(status.getWorkflowType());
				} catch (Throwable t) {
				}
				List<IWorkflowStatusHandler> handlers = workflowStatusRepoPattern
						.findByWorkflowsTypeAndWorkflowId(wType, status.getWorkflowId());
				if (!handlers.isEmpty()) {
					ComputedWorkflowResult measures = handlers.get(0).computeWorkflowStatus(status.getCode(),
							status.getWorkflowType(), status.getWorkflowId());
					boolean batchFinished = (measures.isFinished()
							&& measures.getRootStatus().getBatchDocumentsInput() > 0);
					boolean timeoutPassed = (status.getStartDateTime().getTime() + EXECUTION_TIMEOUT) <= now;
					if (batchFinished || timeoutPassed) {
						try {
							GJobStatus writablestatus = persistenceManager.findById(GJobStatus.class, status.getCode());
							writablestatus.setProcessing(false);
							writablestatus.setFinished(true);
							writablestatus.setEndDateTime(new Date());
							persistenceManager.update(writablestatus);
							statusEmitter.broadcastEnded(writablestatus);
						} catch (GeboPersistenceException e) {
							LOGGER.error("Erro accessing persistence", e);
						}
					}
				}
			}
		});
	}
}
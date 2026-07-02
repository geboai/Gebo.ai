package ai.gebo.jobs.services.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.jobs.services.IGeboWorkflowsStatsService;
import ai.gebo.jobs.services.model.ComputedWorkflowStatusData;
import ai.gebo.jobs.services.repository.ComputedWorkflowStatusDataRepository;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.base.GObjectRef;
import lombok.AllArgsConstructor;

/**
 * Periodically walks the {@link JobStatusRepository}, computes the
 * {@link ComputedWorkflowResult} of each job through its
 * {@link IWorkflowStatusHandler} (the same way
 * {@code GGeboIngestionJobQueueServiceImpl#getJobSummary} does) and stores a
 * flattened {@link ComputedWorkflowStatusData} snapshot per workflow step into
 * the {@link ComputedWorkflowStatusDataRepository}.
 *
 * There is one snapshot document per (job, workflow step), keyed
 * deterministically, so while a job is running each run refreshes its rows in
 * place. Once the job's workflow is finished the snapshot is flagged
 * {@code finalized}: from then on the facts are frozen and the job is skipped on
 * subsequent runs (no more recomputation). {@code year/month/day} attribute the
 * step to its processing date, for data-volume visualizations over time.
 */
@Service
@AllArgsConstructor
public class GeboWorkflowsStatsServiceImpl implements IGeboWorkflowsStatsService {

	private static final long INITIAL_DELAY = 30 * 1000L;
	private static final long FIXED_RATE = 15 * 60 * 1000L;
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboWorkflowsStatsServiceImpl.class);

	private final JobStatusRepository statusRepository;
	private final IWorkflowStatusHandlerRepositoryPattern workflowHandlersRepositoryPattern;
	private final ComputedWorkflowStatusDataRepository statusDataRepository;
	private final IGPersistentObjectManager persistentObjectManager;

	@Override
	@Scheduled(initialDelay = INITIAL_DELAY, fixedRate = FIXED_RATE)
	public void computeAndStoreWorkflowsStats() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin computeAndStoreWorkflowsStats()");
		}
		statusRepository.findAll().forEach(job -> {
			try {
				snapshotJob(job);
			} catch (Exception e) {
				LOGGER.error("Error while computing workflow stats for job " + (job != null ? job.getCode() : null), e);
			}
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End computeAndStoreWorkflowsStats()");
		}
	}

	private void snapshotJob(GJobStatus job) {
		String jobId = job.getCode();
		String workflowType = job.getWorkflowType();
		String workflowId = job.getWorkflowId();
		if (jobId == null || workflowType == null || workflowId == null) {
			return;
		}

		// The job has already been snapshotted as finished: facts are frozen, skip.
		if (statusDataRepository.existsByJobIdAndFinalizedTrue(jobId)) {
			return;
		}

		List<IWorkflowStatusHandler> handlers = workflowHandlersRepositoryPattern
				.findByWorkflowsTypeAndWorkflowId(workflowType, workflowId);
		if (handlers.isEmpty()) {
			return;
		}

		IWorkflowStatusHandler handler = handlers.get(0);
		ComputedWorkflowResult result = handler.computeWorkflowStatus(jobId, workflowType, workflowId);
		if (result == null || result.getRootStatus() == null) {
			return;
		}

		boolean finished = result.isFinished();
		GObjectRef<GProjectEndpoint> endpointRef = job.getProjectEndpointReference();
		ProjectHierarchy hierarchy = resolveHierarchy(endpointRef);
		List<ComputedWorkflowStatus> steps = handler.items(result.getRootStatus());
		List<ComputedWorkflowStatusData> snapshots = new ArrayList<ComputedWorkflowStatusData>(steps.size());
		for (ComputedWorkflowStatus step : steps) {
			snapshots.add(toData(jobId, endpointRef, hierarchy, step, finished));
		}
		if (!snapshots.isEmpty()) {
			statusDataRepository.saveAll(snapshots);
		}
	}

	/**
	 * Resolves the parent {@link GProject} and grandparent {@link GKnowledgeBase} of
	 * the job's project endpoint through the persistence manager, as object
	 * references. Any resolution failure is logged and yields empty references so
	 * the snapshot is still stored.
	 */
	private ProjectHierarchy resolveHierarchy(GObjectRef<GProjectEndpoint> endpointRef) {
		ProjectHierarchy hierarchy = new ProjectHierarchy();
		if (endpointRef == null || endpointRef.getCode() == null) {
			return hierarchy;
		}
		try {
			GProjectEndpoint endpoint = persistentObjectManager.findByReference(endpointRef, GProjectEndpoint.class);
			if (endpoint == null || endpoint.getParentProjectCode() == null) {
				return hierarchy;
			}
			GProject project = persistentObjectManager.findById(GProject.class, endpoint.getParentProjectCode());
			if (project == null) {
				return hierarchy;
			}
			hierarchy.projectReference = GObjectRef.of(project);
			if (project.getRootKnowledgeBaseCode() != null) {
				GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class,
						project.getRootKnowledgeBaseCode());
				if (knowledgeBase != null) {
					hierarchy.knowledgeBaseReference = GObjectRef.of(knowledgeBase);
				}
			}
		} catch (Exception e) {
			LOGGER.warn("Unable to resolve project/knowledge base hierarchy for endpoint "
					+ (endpointRef != null ? endpointRef.getCode() : null), e);
		}
		return hierarchy;
	}

	private ComputedWorkflowStatusData toData(String jobId, GObjectRef<GProjectEndpoint> projectEndpointReference,
			ProjectHierarchy hierarchy, ComputedWorkflowStatus step, boolean finished) {
		ComputedWorkflowStatusData data = new ComputedWorkflowStatusData();
		data.setId(snapshotId(jobId, step));
		data.setJobId(jobId);
		data.setProjectEndpointReference(projectEndpointReference);
		data.setProjectReference(hierarchy.projectReference);
		data.setKnowledgeBaseReference(hierarchy.knowledgeBaseReference);
		data.setWorkflowType(step.getWorkflowType());
		data.setWorkflowId(step.getWorkflowId());
		data.setWorkflowStepId(step.getWorkflowStepId());
		data.setDescription(step.getDescription());
		data.setBatchDocumentsInput(step.getBatchDocumentsInput());
		data.setBatchDocumentsProcessingErrors(step.getBatchDocumentsProcessingErrors());
		data.setBatchDocumentsProcessed(step.getBatchDocumentsProcessed());
		data.setBatchSentToNextStep(step.getBatchSentToNextStep());
		data.setBatchDiscardedInput(step.getBatchDiscardedInput());
		data.setChunksProcessed(step.getChunksProcessed());
		data.setTokensProcessed(step.getTokensProcessed());
		data.setCompleted(step.isCompleted());
		data.setHasErrors(step.isHasErrors());
		data.setStartedRunning(step.isStartedRunning());
		data.setLevelId(step.getLevelId());
		data.setEnabledStep(step.isEnabledStep());
		data.setStartProcessingTimestamp(step.getStartProcessingTimestamp());
		data.setLastProcessingTimestamp(step.getLastProcessingTimestamp());
		LocalDate bucket = processingDate(step);
		data.setYear(bucket.getYear());
		data.setMonth(bucket.getMonthValue());
		data.setDay(bucket.getDayOfMonth());
		data.setFinalized(finished);
		return data;
	}

	/**
	 * Deterministic id: one document per (job, workflow step). While the job runs
	 * it is refreshed in place; once finished it stays as the frozen fact.
	 */
	private String snapshotId(String jobId, ComputedWorkflowStatus step) {
		return String.join("|", jobId, nz(step.getWorkflowType()), nz(step.getWorkflowId()),
				nz(step.getWorkflowStepId()));
	}

	/**
	 * Day bucket the step is attributed to: its last processing timestamp, falling
	 * back to the first one, then to now if the step has not processed anything yet.
	 */
	private LocalDate processingDate(ComputedWorkflowStatus step) {
		Date reference = step.getLastProcessingTimestamp() != null ? step.getLastProcessingTimestamp()
				: step.getStartProcessingTimestamp();
		Instant instant = reference != null ? reference.toInstant() : Instant.now();
		return instant.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private String nz(String value) {
		return value == null ? "" : value;
	}

	/**
	 * The resolved project / knowledge base references of a job's project endpoint.
	 */
	private static class ProjectHierarchy {
		private GObjectRef<GProject> projectReference = null;
		private GObjectRef<GKnowledgeBase> knowledgeBaseReference = null;
	}
}

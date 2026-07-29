package ai.gebo.workflows.compute.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.workflows.compute.model.ComputedWorkflowStatusData;
import ai.gebo.workflows.compute.model.JobSummary;
import ai.gebo.workflows.compute.model.JobWorkflowStepSummary;
import ai.gebo.workflows.compute.model.JobWorkflowStepSummaryTimeSlotStats;
import ai.gebo.workflows.compute.repository.ComputedWorkflowStatusDataRepository;
import ai.gebo.workflows.compute.service.IGeboWorkflowsStatsService;
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
 * {@code finalized}: from then on the facts are frozen and the job is skipped
 * on subsequent runs (no more recomputation). {@code year/month/day} attribute
 * the step to its processing date, for data-volume visualizations over time.
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
	/** Repository for content batch processing data */
	private final ContentsBatchProcessedRepository contentsBatchRepo;
	private final static long STATS_TIME_SLOT = 1000 * 60;

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
	 * Resolves the parent {@link GProject} and grandparent {@link GKnowledgeBase}
	 * of the job's project endpoint through the persistence manager, as object
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
	 * back to the first one, then to now if the step has not processed anything
	 * yet.
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
	 * Retrieves a summary of a job with optional details
	 * 
	 * @param jobId   The job ID to get summary for
	 * @param details Whether to include detailed vectorization processing data
	 * @return A job summary object or null if job not found
	 * @throws GeboPersistenceException If there's an issue with persistence
	 */
	@Override
	public JobSummary getJobSummary(String jobId) throws GeboPersistenceException {
		Optional<GJobStatus> jobOpt = statusRepository.findById(jobId);
		if (jobOpt.isEmpty())
			return null;
		GJobStatus job = jobOpt.get();
		final JobSummary summary = new JobSummary();
		summary.setCode(job.getCode());
		summary.setDescription(job.getDescription());
		String workflowType = job.getWorkflowType();
		String workflowId = job.getWorkflowId();
		summary.setWorkflowType(workflowType);
		summary.setWorkflowId(workflowId);
		// workflowType/workflowId can be null or an unrecognized value for a
		// malformed/legacy GJobStatus (GWorkflowType.valueOf(null/unknown) throws) -
		// degrade to "no handler found" (the same branch an empty lookup result
		// already takes below) rather than 500 the whole request.
		GWorkflowType parsedWorkflowType = null;
		if (workflowType != null) {
			try {
				parsedWorkflowType = GWorkflowType.valueOf(workflowType);
			} catch (IllegalArgumentException e) {
				LOGGER.warn("Job {} has an unrecognized workflowType '{}' - returning a partial summary", jobId,
						workflowType);
			}
		}
		List<IWorkflowStatusHandler> handler = parsedWorkflowType == null ? List.of()
				: workflowHandlersRepositoryPattern.findByWorkflowsTypeAndWorkflowId(parsedWorkflowType, workflowId);
		if (!handler.isEmpty()) {
			ComputedWorkflowResult status = handler.get(0).computeWorkflowStatus(jobId, workflowType, workflowId);
			summary.setWorkflowStatus(status);
			final List<ComputedWorkflowStatus> itemslist = handler.get(0).items(status.getRootStatus());
			final TreeMap<Long, Map<String, JobWorkflowStepSummaryTimeSlotStats>> stats = new TreeMap<Long, Map<String, JobWorkflowStepSummaryTimeSlotStats>>();
			Stream<ContentsBatchProcessed> stream = contentsBatchRepo.findByJobId(jobId);
			stream.forEach(processed -> {
				Date timestamp = processed.getTimestamp();
				if (timestamp != null) {
					long minutesFloorStart = (timestamp.getTime() / STATS_TIME_SLOT) * STATS_TIME_SLOT;
					long minutesFloorEnd = minutesFloorStart + STATS_TIME_SLOT;
					Long key = new Long(minutesFloorStart);
					if (!stats.containsKey(key)) {
						stats.put(key, new HashMap<String, JobWorkflowStepSummaryTimeSlotStats>());
					}
					String stepKey = (of(jobId) + "-" + of(processed.getWorkflowType()) + "-"
							+ of(processed.getWorkflowId()) + "-" + processed.getWorkflowStepId()).toLowerCase();

					if (!stats.get(key).containsKey(stepKey)) {
						JobWorkflowStepSummaryTimeSlotStats statsEntry = new JobWorkflowStepSummaryTimeSlotStats();
						statsEntry.setStartDateTime(new Date(minutesFloorStart));
						statsEntry.setEndDateTime(new Date(minutesFloorEnd));
						stats.get(key).put(stepKey, statsEntry);
					}

					stats.get(key).get(stepKey).incrementBy(processed);
				} else {
					LOGGER.error("Entry without timestamp");
				}
			});
			if (!stats.isEmpty() && stats.firstKey() != null && stats.lastKey() != null) {
				long startDateTime = stats.firstKey();
				long endDateTime = stats.lastKey();
				for (ComputedWorkflowStatus item : itemslist) {
					String stepKey = (of(jobId) + "-" + of(item.getWorkflowType()) + "-" + of(item.getWorkflowId())
							+ "-" + of(item.getWorkflowStepId())).toLowerCase();
					JobWorkflowStepSummary stepSummary = new JobWorkflowStepSummary();
					stepSummary.setWorkflowType(item.getWorkflowType());
					stepSummary.setWorkflowId(item.getWorkflowId());
					stepSummary.setWorkflowStepId(item.getWorkflowStepId());
					for (long actualDateTime = startDateTime; actualDateTime <= endDateTime; actualDateTime += STATS_TIME_SLOT) {
						Map<String, JobWorkflowStepSummaryTimeSlotStats> entry = stats.get(new Long(actualDateTime));
						JobWorkflowStepSummaryTimeSlotStats slot = null;
						if (entry != null && entry.containsKey(stepKey)) {
							slot = entry.get(stepKey);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("date=" + new Date(actualDateTime) + "=>" + slot.toString());
							}
						} else {
							slot = new JobWorkflowStepSummaryTimeSlotStats();
							slot.setStartDateTime(new Date(actualDateTime));
							slot.setEndDateTime(new Date(actualDateTime + STATS_TIME_SLOT));
						}
						stepSummary.add(slot);
					}
					summary.getWorkflowStepsSummaries().add(stepSummary);
				}

			}
		}
		// LOGGER.info("Summary=>"+summary.getWorkflowStepsSummaries());
		return summary;
	}

	/**
	 * Retrieves the status of a job by its code
	 * 
	 * @param code The job code to look up
	 * @return The job status object or null if not found
	 * @throws GeboPersistenceException If there's an issue retrieving the status
	 */
	@Override
	public GJobStatus getStatus(String code) throws GeboPersistenceException {
		
		return statusRepository.findById(code).orElse(null);
			
	}

	private static String of(String s) {
		return s != null ? s.trim().toLowerCase() : "";
	}

	/**
	 * The resolved project / knowledge base references of a job's project endpoint.
	 */
	private static class ProjectHierarchy {
		private GObjectRef<GProject> projectReference = null;
		private GObjectRef<GKnowledgeBase> knowledgeBaseReference = null;
	}
}

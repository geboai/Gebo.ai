package ai.gebo.workflows.compute.service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.workflows.compute.model.JobSummary;

/**
 * Service that periodically snapshots the computed status of every workflow
 * known to the {@code JobStatusRepository} into a base stats collection, to be
 * used for workflow data-volume visualizations.
 */
public interface IGeboWorkflowsStatsService {

	/**
	 * Computes the {@code ComputedWorkflowStatus} of every job and persists one
	 * {@code ComputedWorkflowStatusData} snapshot per workflow step.
	 */
	void computeAndStoreWorkflowsStats();

	/**
	 * Retrieves detailed information about a specific job. By default, includes all
	 * detailed information.
	 * 
	 * @param jobId The unique identifier of the job
	 * @return A summary of the job with detailed information
	 * @throws GeboJobServiceException  If the job summary cannot be retrieved
	 * @throws GeboPersistenceException If there are persistence-related issues
	 */
	public JobSummary getJobSummary(String jobId) throws GeboJobServiceException, GeboPersistenceException;

	/**
	 * Retrieves the current status of a job.
	 * 
	 * @param code The unique identifier of the job
	 * @return The current status of the job
	 * @throws GeboJobServiceException If the status cannot be retrieved
	 */
	public GJobStatus getStatus(String code) throws GeboJobServiceException;
}

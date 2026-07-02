package ai.gebo.jobs.services;

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
}

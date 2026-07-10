package ai.gebo.workflows.compute.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.workflows.compute.model.ComputedWorkflowStatusData;

/**
 * Repository for the periodically computed workflow status snapshots. Acts as a
 * base stats collection for workflow data-volume visualizations.
 */
public interface ComputedWorkflowStatusDataRepository extends MongoRepository<ComputedWorkflowStatusData, String> {

	List<ComputedWorkflowStatusData> findByJobId(String jobId);

	/**
	 * Whether the job has already been snapshotted as finished (frozen facts), so
	 * it must not be recomputed again.
	 */
	boolean existsByJobIdAndFinalizedTrue(String jobId);
}

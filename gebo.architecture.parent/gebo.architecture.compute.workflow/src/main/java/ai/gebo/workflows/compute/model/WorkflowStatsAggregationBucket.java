package ai.gebo.workflows.compute.model;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * A single aggregated workflow-stats row. The dimension fields (knowledgeBaseReference,
 * projectReference, projectEndpointReference, workflowType, workflowId, workflowStepId)
 * are populated only for the fields that were present in the drill-down criteria;
 * the others are null because they have been aggregated across. {@code day} is
 * null on the monthly dataset.
 */
@Data
public class WorkflowStatsAggregationBucket {
	private GObjectRef<GKnowledgeBase> knowledgeBaseReference;
	private GObjectRef<GProject> projectReference;
	private GObjectRef<GProjectEndpoint> projectEndpointReference;
	private String workflowType;
	private String workflowId;
	private String workflowStepId;
	private Integer year;
	private Integer month;
	private Integer day;
	private long batchDocumentsInput;
	private long batchDocumentsProcessed;
	private long batchDocumentsProcessingErrors;
	private long batchSentToNextStep;
	private long batchDiscardedInput;
	private long chunksProcessed;
	private long tokensProcessed;
	private long nrSnapshots;
}

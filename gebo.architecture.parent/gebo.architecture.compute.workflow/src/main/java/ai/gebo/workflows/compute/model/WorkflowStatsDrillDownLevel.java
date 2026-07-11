package ai.gebo.workflows.compute.model;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * Drill-down criteria for the workflow stats base ({@code ComputedWorkflowStatusData}).
 *
 * Every non-null field acts as an equality filter; null fields are aggregated
 * across (they are not used as grouping/filtering criteria). The reference
 * fields (knowledge base, project, project endpoint) filter when their code is
 * present.
 */
@Data
public class WorkflowStatsDrillDownLevel {
	private GObjectRef<GKnowledgeBase> knowledgeBaseReference;
	private GObjectRef<GProject> projectReference;
	private GObjectRef<GProjectEndpoint> projectEndpointReference;
	private String workflowType;
	private String workflowId;
	private String workflowStepId;
	private Integer year;
	private Integer month;
}

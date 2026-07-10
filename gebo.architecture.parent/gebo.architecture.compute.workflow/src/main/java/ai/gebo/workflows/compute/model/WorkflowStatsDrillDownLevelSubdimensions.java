package ai.gebo.workflows.compute.model;

import java.util.List;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * Carries the distinct values of the dimensions that were aggregated across in a
 * dataset (i.e. the dimensions not fixed by the drill-down criteria). It lets a
 * client discover the values available to drill down further.
 *
 * Only the aggregated (non-fixed) dimensions are populated; a dimension fixed by
 * the criteria is left null because its value is already known.
 */
@Data
public class WorkflowStatsDrillDownLevelSubdimensions {
	private List<GObjectRef<GKnowledgeBase>> knowledgeBaseReference;
	private List<GObjectRef<GProject>> projectReference;
	private List<GObjectRef<GProjectEndpoint>> projectEndpointReference;
	private List<String> workflowType;
	private List<String> workflowId;
	private List<String> workflowStepId;
	private List<Integer> year;
	private List<Integer> month;
}

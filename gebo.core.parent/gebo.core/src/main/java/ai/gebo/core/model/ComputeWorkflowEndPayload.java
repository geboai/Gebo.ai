package ai.gebo.core.model;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import lombok.Data;

@Data
public class ComputeWorkflowEndPayload extends GBaseMessagePayload {
	String jobId;
	String workflowType;
	String workflowId;
}

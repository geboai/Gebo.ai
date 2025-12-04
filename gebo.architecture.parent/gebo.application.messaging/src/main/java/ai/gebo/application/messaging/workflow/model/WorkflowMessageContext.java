package ai.gebo.application.messaging.workflow.model;

import ai.gebo.application.messaging.IGMessagePayloadType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkflowMessageContext {
	private final WorkflowContext workflowContext;
	private final IGMessagePayloadType payload;

}

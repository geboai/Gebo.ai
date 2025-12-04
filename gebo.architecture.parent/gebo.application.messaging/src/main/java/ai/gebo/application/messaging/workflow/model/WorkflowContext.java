package ai.gebo.application.messaging.workflow.model;

import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkflowContext {
	@NotNull
	private final String knowledgeBaseCode;
	@NotNull
	private final String projectCode;
	@NotNull
	private final GObjectRef dataSource;

}

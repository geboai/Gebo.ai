package ai.gebo.architecture.documents.cache.service.impl;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
@Component
public class TokenizerStandardWorkflowIngestionStepEnabledHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	public TokenizerStandardWorkflowIngestionStepEnabledHandler() {
		super(GStandardWorkflowStep.TOKENIZATION);

	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId, WorkflowContext context) {
		return true;
	}

}

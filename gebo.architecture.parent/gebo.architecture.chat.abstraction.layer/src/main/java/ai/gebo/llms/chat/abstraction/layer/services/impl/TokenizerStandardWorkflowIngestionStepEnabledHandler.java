package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
@Component
public class TokenizerStandardWorkflowIngestionStepEnabledHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	public TokenizerStandardWorkflowIngestionStepEnabledHandler() {
		super(GStandardWorkflowStep.TOKENIZATION);

	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId) {
		return true;
	}

}

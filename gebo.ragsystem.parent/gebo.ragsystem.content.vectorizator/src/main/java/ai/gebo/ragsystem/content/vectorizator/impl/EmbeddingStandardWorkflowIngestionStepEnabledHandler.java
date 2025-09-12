package ai.gebo.ragsystem.content.vectorizator.impl;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;

@Component
public class EmbeddingStandardWorkflowIngestionStepEnabledHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	public EmbeddingStandardWorkflowIngestionStepEnabledHandler() {
		super(GStandardWorkflowStep.EMBEDDING);

	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId) {
		return true;
	}

}

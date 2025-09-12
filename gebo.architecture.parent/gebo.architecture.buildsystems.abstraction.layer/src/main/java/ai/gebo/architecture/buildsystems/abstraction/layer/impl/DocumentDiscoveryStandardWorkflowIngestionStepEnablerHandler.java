package ai.gebo.architecture.buildsystems.abstraction.layer.impl;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;

@Component
public class DocumentDiscoveryStandardWorkflowIngestionStepEnablerHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	public DocumentDiscoveryStandardWorkflowIngestionStepEnablerHandler() {
		super(GStandardWorkflowStep.DOCUMENT_DISCOVERY);

	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId) {

		return true;
	}

}

package ai.gebo.ragsystem.content.fulltext.processor.impl;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.opensearch.config.OpenSearchConfig;

@Component
public class FullTextStandardWorkflowIngestionStepEnabledHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {
	final OpenSearchConfig openSearchConfig;

	public FullTextStandardWorkflowIngestionStepEnabledHandler(OpenSearchConfig openSearchConfig) {
		super(GStandardWorkflowStep.FULLTEXT_INDEXING);
		this.openSearchConfig = openSearchConfig;

	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId, WorkflowContext context) {
		return openSearchConfig != null && openSearchConfig.isEnabled();
	}

}

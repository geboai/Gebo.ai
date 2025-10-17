package ai.gebo.ragsystem.content.graphrag_processor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;

@Component

public class GraphextractionStandardWorkflowIngestionStepEnablerHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {
	private final IGraphDataExtractionService extractionService;

	public GraphextractionStandardWorkflowIngestionStepEnablerHandler(@Autowired(required = false)IGraphDataExtractionService extractionService) {
		super(GStandardWorkflowStep.GRAPHEXTRACTION);
		this.extractionService = extractionService;
	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId, WorkflowContext context) {

		return this.extractionService!=null && this.extractionService.isConfigured(context);
	}

}

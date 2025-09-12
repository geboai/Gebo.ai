package ai.gebo.ragsystem.content.graphrag_processor.impl;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;

@Component

public class GraphextractionStandardWorkflowIngestionStepEnablerHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {
	private final IGraphDataExtractionService extractionService;

	public GraphextractionStandardWorkflowIngestionStepEnablerHandler(IGraphDataExtractionService extractionService) {
		super(GStandardWorkflowStep.GRAPHEXTRACTION);
		this.extractionService = extractionService;
	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId) {

		return this.extractionService.isConfigured();
	}

}

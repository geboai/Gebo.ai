/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.workflow.steps.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;

/**
 * Monolith, config-driven enablement of the GRAPHEXTRACTION (graphrag) step:
 * enabled only when a graph extraction service is present and configured for the
 * given context — the behaviour that previously lived in the graphrag_processor
 * perform-module.
 */
@Component
public class GraphextractionLocalWorkflowStepEnabledHandler extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	private final IGraphDataExtractionService extractionService;

	public GraphextractionLocalWorkflowStepEnabledHandler(
			@Autowired(required = false) IGraphDataExtractionService extractionService) {
		super(GStandardWorkflowStep.GRAPHEXTRACTION);
		this.extractionService = extractionService;
	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId, WorkflowContext context) {
		return this.extractionService != null && this.extractionService.isConfigured(context);
	}

}

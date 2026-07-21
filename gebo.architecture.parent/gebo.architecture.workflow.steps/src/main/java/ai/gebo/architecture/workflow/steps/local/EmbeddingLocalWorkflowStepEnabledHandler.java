/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.workflow.steps.local;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;

/**
 * Monolith, config-driven enablement of the EMBEDDING (semantic indexing) step.
 * Embedding has no runtime prerequisite in the monolith, so it is always enabled
 * when this module is present — identical to the behaviour that previously lived
 * in the vectorizator perform-module.
 */
@Component
public class EmbeddingLocalWorkflowStepEnabledHandler extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	public EmbeddingLocalWorkflowStepEnabledHandler() {
		super(GStandardWorkflowStep.EMBEDDING);
	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId, WorkflowContext context) {
		return true;
	}

}

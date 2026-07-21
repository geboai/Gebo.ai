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
import ai.gebo.architecture.opensearch.config.OpenSearchConfig;

/**
 * Monolith, config-driven enablement of the FULLTEXT_INDEXING step: enabled only
 * when OpenSearch is configured and enabled — the behaviour that previously lived
 * in the fulltext.processor perform-module.
 */
@Component
public class FullTextLocalWorkflowStepEnabledHandler extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	private final OpenSearchConfig openSearchConfig;

	public FullTextLocalWorkflowStepEnabledHandler(OpenSearchConfig openSearchConfig) {
		super(GStandardWorkflowStep.FULLTEXT_INDEXING);
		this.openSearchConfig = openSearchConfig;
	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId, WorkflowContext context) {
		return openSearchConfig != null && openSearchConfig.isEnabled();
	}

}

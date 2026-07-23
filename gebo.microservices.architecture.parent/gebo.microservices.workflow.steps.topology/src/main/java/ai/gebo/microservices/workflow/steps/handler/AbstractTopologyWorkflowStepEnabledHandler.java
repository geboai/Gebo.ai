/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps.handler;

import ai.gebo.application.messaging.workflow.AbstractStandardWorkflowIngestionStepEnablerHandler;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.microservices.workflow.steps.IWorkflowParticipantsEnablementResolver;

/**
 * Base for the microservices step-enablement handlers: {@code isEnabled} delegates
 * to the {@link IWorkflowParticipantsEnablementResolver}, which answers from the
 * topology (on tyr) or from tyr's cached answer (everywhere else). The workflow
 * router and status-tree computation consume these handlers unchanged.
 */
public abstract class AbstractTopologyWorkflowStepEnabledHandler
		extends AbstractStandardWorkflowIngestionStepEnablerHandler {

	private final IWorkflowParticipantsEnablementResolver resolver;

	protected AbstractTopologyWorkflowStepEnabledHandler(GStandardWorkflowStep step,
			IWorkflowParticipantsEnablementResolver resolver) {
		super(step);
		this.resolver = resolver;
	}

	@Override
	public boolean isEnabled(String workflowId, String workflowStepId, WorkflowContext context) {
		return resolver.isStepEnabled(context, getWorkflowType().name(), workflowId, handledStep.name());
	}
}

/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps;

import java.util.Set;

import ai.gebo.application.messaging.workflow.model.WorkflowContext;

/**
 * Resolves, for a given workflow run, the set of standard-workflow steps that are
 * enabled in this installation. There are two implementations:
 *
 * <ul>
 * <li>on <b>tyr</b> the set is computed directly from the deployment topology
 * (a step is enabled when the microservice that performs it is part of the
 * installation);</li>
 * <li>everywhere else the set is fetched <b>once per run from tyr</b> and cached,
 * so the per-message {@link #isStepEnabled} check that the workflow router runs is
 * a local map lookup rather than a REST call.</li>
 * </ul>
 *
 * <p>
 * The step-enablement handlers ({@code IWorkflowStepEnabledHandler}) delegate
 * their {@code isEnabled} to this resolver, so the routing/status logic that
 * consumes them stays unchanged.
 * </p>
 */
public interface IWorkflowParticipantsEnablementResolver {

	/**
	 * The enabled steps for a run, by their {@code GStandardWorkflowStep} name
	 * (upper-case).
	 *
	 * @param context      the run context (knowledge base / project / data source);
	 *                     may be {@code null} when the answer is context-independent
	 * @param workflowType the workflow type (e.g. {@code STANDARD})
	 * @param workflowId   the workflow id (e.g. {@code INGESTION})
	 * @return the enabled step names; never {@code null}
	 */
	Set<String> enabledSteps(WorkflowContext context, String workflowType, String workflowId);

	/**
	 * Whether a single step is enabled for the run — a membership test over
	 * {@link #enabledSteps}.
	 */
	default boolean isStepEnabled(WorkflowContext context, String workflowType, String workflowId, String stepId) {
		if (stepId == null) {
			return false;
		}
		return enabledSteps(context, workflowType, workflowId).contains(stepId.toUpperCase());
	}
}

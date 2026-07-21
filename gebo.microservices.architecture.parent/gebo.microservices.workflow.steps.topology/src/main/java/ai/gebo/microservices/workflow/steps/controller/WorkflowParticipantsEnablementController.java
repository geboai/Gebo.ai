/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Conditional;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.microservices.workflow.steps.config.WorkflowAuthorityConditions.OnWorkflowAuthority;
import ai.gebo.microservices.workflow.steps.resolver.TopologyWorkflowParticipantsEnablementResolver;

/**
 * tyr's authority endpoint: returns the set of enabled standard-workflow steps for
 * a run, computed from the deployment topology. The off-tyr services that run the
 * workflow logic (the chunker) call this <b>once per run</b> and cache the answer.
 *
 * <p>
 * This jar also lands on the chunker, whose component scan would otherwise
 * instantiate this {@code @RestController} and fail on the tyr-only
 * {@link TopologyWorkflowParticipantsEnablementResolver}. The
 * {@link OnWorkflowAuthority} condition makes the scan skip it off-tyr; on tyr it
 * is registered by the tyr-gated {@code @Bean} in the autoconfiguration (tyr does
 * not component-scan this package, so there is no double registration). Reachable
 * by the platform's system identity because the async pipeline threads that call
 * it authenticate as {@code APPLICATION}.
 * </p>
 */
@RestController
@Conditional(OnWorkflowAuthority.class)
@RequestMapping("api/users/WorkflowParticipantsEnablementController")
@PreAuthorize("hasAnyRole('ADMIN','USER','APPLICATION')")
public class WorkflowParticipantsEnablementController {

	private final TopologyWorkflowParticipantsEnablementResolver resolver;

	public WorkflowParticipantsEnablementController(TopologyWorkflowParticipantsEnablementResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * The enabled step names for the run, by their {@code GStandardWorkflowStep}
	 * name. Topology-based enablement is context-independent, so no run context is
	 * required.
	 */
	@GetMapping(value = "enabledSteps", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> enabledSteps(@RequestParam("workflowType") String workflowType,
			@RequestParam("workflowId") String workflowId) {
		return new ArrayList<>(resolver.enabledSteps(null, workflowType, workflowId));
	}
}

/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.workflow.steps.resolver;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.workflow.steps.IWorkflowParticipantsEnablementResolver;

/**
 * The <b>tyr-side</b> resolver: the single authority. A non-mandatory step is
 * enabled when the microservice that performs it — the one owning the messaging
 * module its target component targets — is part of this installation's topology.
 * Mandatory steps are always enabled. The answer is context-independent, so it is
 * cached per {@code (workflowType, workflowId)}.
 *
 * <p>
 * This resolver never calls the enabled-steps endpoint (that endpoint is backed by
 * this resolver), so there is no recursion when tyr's own status-tree computation
 * consults the step handlers.
 * </p>
 */
public class TopologyWorkflowParticipantsEnablementResolver implements IWorkflowParticipantsEnablementResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyWorkflowParticipantsEnablementResolver.class);

	private final GeboMicroservicesTopology topology;
	private final GeboTtlCache cache;

	public TopologyWorkflowParticipantsEnablementResolver(GeboMicroservicesTopology topology, GeboTtlCache cache) {
		this.topology = topology;
		this.cache = cache;
	}

	@Override
	public Set<String> enabledSteps(WorkflowContext context, String workflowType, String workflowId) {
		String key = "topo|" + workflowType + "|" + workflowId;
		return cache.get(key, () -> computeFromTopology(workflowId));
	}

	private Set<String> computeFromTopology(String workflowId) {
		Set<String> enabled = new LinkedHashSet<>();
		for (GStandardWorkflowStep step : GStandardWorkflowStep.values()) {
			if (workflowId != null && !step.getWorkflow().name().equalsIgnoreCase(workflowId)) {
				continue;
			}
			if (step.isMandatoryStep()) {
				enabled.add(step.name());
				continue;
			}
			String moduleId = step.getTargetComponent().getModuleId();
			boolean present = topology.forMessagingModuleId(moduleId).isPresent();
			if (present) {
				enabled.add(step.name());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Topology enablement: step {} (module {}) -> {}", step.name(), moduleId, present);
			}
		}
		return enabled;
	}
}

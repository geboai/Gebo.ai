/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import java.util.Set;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGPromptPlaceholdersDeclaration;

/**
 * The placeholders an agent's prompt may name, filled from the agent's own
 * session by {@code GAbstractGenericalAgentService.createAgentTemplateParams}
 * (see {@link AgentPromptTemplateParams}): declared so that a prompt naming
 * them is accepted at save time.
 */
@Component
public class GAgentPromptPlaceholdersDeclaration implements IGPromptPlaceholdersDeclaration {

	@Override
	public Set<String> declaredPlaceholders() {
		return Set.of(AgentPromptTemplateParams.NETWORK_SCENARY_TEMPLATE_PARAM,
				AgentPromptTemplateParams.AGENT_IDENTITY_TEMPLATE_PARAM,
				AgentPromptTemplateParams.AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM,
				AgentPromptTemplateParams.NETWORK_AGENTS_CAPABILITIES_TEMPLATE_PARAM,
				AgentPromptTemplateParams.INPUT_TEMPLATE_PARAM, AgentPromptTemplateParams.PRIVATE_CONTEXT_TEMPLATE_PARAM,
				AgentPromptTemplateParams.SHARED_CONTEXT_TEMPLATE_PARAM, AgentPromptTemplateParams.FORMAT_TEMPLATE_PARAM);
	}
}

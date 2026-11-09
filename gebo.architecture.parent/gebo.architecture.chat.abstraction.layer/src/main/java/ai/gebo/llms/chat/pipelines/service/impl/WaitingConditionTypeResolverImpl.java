/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.pipelines.service.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.services.IWaitingConditionTypeResolver;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslator;

/**
 * The type of a waiting condition is the id of the translator the transport picks
 * for it: the same one that shows it to the client.
 */
@Service
public class WaitingConditionTypeResolverImpl implements IWaitingConditionTypeResolver {
	private final NetworkOfAgentsSuspensionTransport transport;

	public WaitingConditionTypeResolverImpl(NetworkOfAgentsSuspensionTransport transport) {
		this.transport = transport;
	}

	@Override
	public String typeOf(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition == null || transport == null) {
			return null;
		}
		final IAgentSuspendedWaitingConditionTransportTranslator translator = transport.translatorOf(condition);
		return translator != null ? translator.getId() : null;
	}
}

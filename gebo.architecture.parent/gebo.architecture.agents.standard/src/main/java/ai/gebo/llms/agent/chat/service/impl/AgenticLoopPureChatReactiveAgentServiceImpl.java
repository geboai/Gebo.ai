/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.security.services.IGSecurityService;

/**
 * The single agent with tools working in a loop, for the free chats: every tool but
 * the internal knowledge base search, which a free chat does not query.
 */
@Service
public class AgenticLoopPureChatReactiveAgentServiceImpl extends AgenticLoopReactiveAgentServiceImpl {
	public static final String AGENTIC_LOOP_PURE_CHAT_NETWORK_AGENT_SERVICE = "AgenticLoopPureChatNetworkAgentService";
	private static final String DESCRIPTION = "Single agent that operates every available tool but the internal knowledge base search in a loop, for the free chats";

	public AgenticLoopPureChatReactiveAgentServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);
	}

	@Override
	public String getId() {
		return AGENTIC_LOOP_PURE_CHAT_NETWORK_AGENT_SERVICE;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	protected List<String> filterAutoMountedTools(List<String> toolNames) {
		if (toolNames == null) {
			return null;
		}
		List<String> filtered = toolNames.stream()
				.filter(x -> !InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL.equals(x)).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agentic loop pure chat agent id:" + getId() + " mounts " + filtered.size() + " of "
					+ toolNames.size() + " tool(s), without the internal knowledge base search");
		}
		return filtered;
	}
}

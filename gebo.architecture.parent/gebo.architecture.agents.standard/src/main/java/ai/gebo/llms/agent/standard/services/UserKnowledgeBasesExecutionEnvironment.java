/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * The environment the agents networks and the tools run in when they are called
 * from outside a chat (an A2A task, a tool exported through MCP), for the user the
 * call runs as: as a chat whose profile lets the user choose among all the
 * knowledge bases the user can see. A chat gives its own knowledge bases instead
 * (see {@code IGChatSessionLifeCycleService#getSessionAvailableKnowledgeBases}).
 *
 * <p>
 * The knowledge bases are read under the identity the call runs as (the Spring
 * security context of the current thread): run these methods there.
 * </p>
 */
@Component
public class UserKnowledgeBasesExecutionEnvironment {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserKnowledgeBasesExecutionEnvironment.class);

	private final IGKnowledgebaseVisibilityService visibilityService;

	public UserKnowledgeBasesExecutionEnvironment(IGKnowledgebaseVisibilityService visibilityService) {
		this.visibilityService = visibilityService;
	}

	/** The codes of all the knowledge bases the current user can see; none when they cannot be read. */
	public List<String> knowledgeBaseCodes() {
		try {
			final List<GKnowledgeBase> visibles = visibilityService.allVisibleKnowledgebases();
			final List<String> codes = visibles != null
					? visibles.stream().filter(kb -> kb != null && kb.getCode() != null).map(GKnowledgeBase::getCode)
							.distinct().toList()
					: List.of();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("knowledgeBaseCodes() the user's calls reach " + codes.size() + " knowledge base(s)");
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Knowledge bases of the user's calls: " + codes);
			}
			return codes;
		} catch (RuntimeException e) {
			LOGGER.error("Cannot read the knowledge bases the user can see: the call reaches none", e);
			return List.of();
		}
	}

	/** The environment of an agents network run for the user, with its knowledge bases. */
	public Map<String, Object> networkEnvironment(List<String> knowledgeBaseCodes) {
		final Map<String, Object> environment = new HashMap<>();
		environment.put(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE, List.copyOf(knowledgeBaseCodes));
		return environment;
	}

	/** The given tools context also carrying the knowledge bases, as a chat's does. */
	public Map<String, Object> toolsContext(Map<String, Object> toolsContext, List<String> knowledgeBaseCodes) {
		final Map<String, Object> out = toolsContext != null ? new HashMap<>(toolsContext) : new HashMap<>();
		out.put(ToolCallbackDeclarationUtil.CHAT_KNOWLEDGE_BASES_CONTEXT_KEY, List.copyOf(knowledgeBaseCodes));
		return out;
	}

	/** The request context of a call for the user, whose tools get the knowledge bases. */
	public IChatRequestContext requestContext(String userQuery, List<String> knowledgeBaseCodes) {
		return IChatRequestContext.builder().actualUserRequest(userQuery != null ? userQuery : "")
				.toolsContext(toolsContext(null, knowledgeBaseCodes)).build();
	}
}

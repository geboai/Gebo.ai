/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.prompts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGPromptContextProvider;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/**
 * The conversation, for the prompts that name it: {@code {chatTitle}},
 * {@code {chatProfileDescription}} and {@code {knowledgeBasesCatalog}}, the
 * knowledge bases the request works on, one per line with their description.
 */
@Component
public class GConversationPromptContextProvider implements IGPromptContextProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GConversationPromptContextProvider.class);
	public static final String CHAT_TITLE = "chatTitle";
	public static final String CHAT_PROFILE_DESCRIPTION = "chatProfileDescription";
	public static final String KNOWLEDGE_BASES_CATALOG = "knowledgeBasesCatalog";

	private final GUserChatSessionRepository conversations;
	private final ChatProfilesRepository profiles;
	private final KnowledgeBaseRepository knowledgeBases;

	public GConversationPromptContextProvider(GUserChatSessionRepository conversations,
			ChatProfilesRepository profiles, KnowledgeBaseRepository knowledgeBases) {
		this.conversations = conversations;
		this.profiles = profiles;
		this.knowledgeBases = knowledgeBases;
	}

	@Override
	public Set<String> getPlaceholders() {
		return Set.of(CHAT_TITLE, CHAT_PROFILE_DESCRIPTION, KNOWLEDGE_BASES_CATALOG);
	}

	@Override
	public Map<String, Object> valuesFor(Set<String> asked, IChatRequestContext context) {
		final Map<String, Object> values = new HashMap<String, Object>();
		if (context == null) {
			return values;
		}
		if (asked.contains(CHAT_TITLE) || asked.contains(CHAT_PROFILE_DESCRIPTION)) {
			final GUserChatSession conversation = conversationOf(context);
			if (asked.contains(CHAT_TITLE)) {
				values.put(CHAT_TITLE, conversation != null && conversation.getDescription() != null
						? conversation.getDescription()
						: "");
			}
			if (asked.contains(CHAT_PROFILE_DESCRIPTION)) {
				String description = "";
				if (conversation != null && conversation.getChatProfileCode() != null) {
					final Optional<GChatProfileConfiguration> profile = profiles
							.findById(conversation.getChatProfileCode());
					description = profile.isPresent() && profile.get().getDescription() != null
							? profile.get().getDescription()
							: "";
				}
				values.put(CHAT_PROFILE_DESCRIPTION, description);
			}
		}
		if (asked.contains(KNOWLEDGE_BASES_CATALOG)) {
			values.put(KNOWLEDGE_BASES_CATALOG, catalogOf(context));
		}
		return values;
	}

	private GUserChatSession conversationOf(IChatRequestContext context) {
		final String code = context.getSessionID();
		if (code == null || code.isBlank()) {
			return null;
		}
		try {
			return conversations.findById(code).orElse(null);
		} catch (RuntimeException notRead) {
			LOGGER.debug("The conversation " + code + " cannot be read for the prompt", notRead);
			return null;
		}
	}

	/** The knowledge bases the request's tools work on, as the tools context names them. */
	@SuppressWarnings("unchecked")
	private String catalogOf(IChatRequestContext context) {
		final Object codes = context.getToolsContext() != null
				? context.getToolsContext().get(ToolCallbackDeclarationUtil.CHAT_KNOWLEDGE_BASES_CONTEXT_KEY)
				: null;
		if (!(codes instanceof List<?> list) || list.isEmpty()) {
			return "";
		}
		final StringBuilder catalog = new StringBuilder();
		try {
			for (GKnowledgeBase knowledgeBase : knowledgeBases.findAllById((List<String>) list)) {
				catalog.append(catalog.length() > 0 ? "\n" : "").append("- ").append(knowledgeBase.getCode());
				if (knowledgeBase.getDescription() != null && !knowledgeBase.getDescription().isBlank()) {
					catalog.append(": ").append(knowledgeBase.getDescription());
				}
			}
		} catch (RuntimeException notRead) {
			LOGGER.debug("The knowledge bases of the request cannot be read for the prompt", notRead);
		}
		return catalog.toString();
	}
}

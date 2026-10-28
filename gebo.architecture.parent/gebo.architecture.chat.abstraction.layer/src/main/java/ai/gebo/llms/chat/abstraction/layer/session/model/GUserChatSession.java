/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext.ChatRequestContextImpl.ChatRequestContextImplBuilder;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry.ChatSessionEntryImpl.ChatSessionEntryImplBuilder;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * AI generated comments Represents a user chat context within the chat
 * abstraction layer model. Stores details about chat interactions and
 * configurations for a user.
 */
@Document
@Data
public class GUserChatSession extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource {

	private static final String EMPTY_TEXT = "<<empty text>>";
	private Date chatCreationDateTime = null; // Timestamp for chat creation
	@HashIndexed
	private String username = null; // Username for the chat context

	/**
	 * Who else may take part in this conversation, beside the person it belongs
	 * to - who remains its owner.
	 * <p>
	 * Granted the way everything else in the platform is granted, and in both of
	 * the ways it grants things: by naming users and groups, and through the
	 * access control lists, so an installation enforcing ACLs sees a conversation
	 * as it sees any other object.
	 * <p>
	 * <b>What this does not yet change:</b> the chat list still shows a person
	 * their own conversations and no others, so nothing yet opens a conversation
	 * granted this way.
	 * <p>
	 * That is a decision, not an oversight. Showing a conversation to several
	 * people means letting them work in it at the same time, and that is not a
	 * matter of reading rows: it needs the people in a conversation to see each
	 * other's turns as they happen, over websockets, with everything that follows
	 * about who holds it, what arrives while somebody else is writing and how the
	 * history settles. That is its own piece of work and it is not in this
	 * release. The grant is modelled now because work that must be answered by
	 * somebody else needs somewhere to say so, and because saying it on the
	 * conversation - the way the platform grants anything - is where it will be
	 * read from when the rest is built.
	 */
	private List<String> accessibleUsers = null;

	/** The groups whose members may take part in this conversation. */
	private List<String> accessibleGroups = null;

	/** Whether everyone may; null or false keeps it to its own people. */
	private Boolean accessibleToAll = null;

	/** The access control lists this conversation is granted through. */
	private List<Integer> aclAliases = null;
	@HashIndexed
	private String contextCode = null; // Code of the external context this chat belongs to
	@GObjectReference(referencedType = GChatProfileConfiguration.class)
	private String chatProfileCode = null; // Chat profile configuration code
	private GObjectRef<GBaseChatModelConfig> modelReference = null; // Reference to the chat model configuration
	private Boolean ragChat = null; // Indicates if the chat supports Retrieval-Augmented Generation
	private String pipelineCode = null; // Chat pipeline this session runs through (null = default pipeline; e.g. "open-chat")
	private String chatMemoryId = null; // Identifier for chat memory
	private List<ChatInteractions> interactions = new ArrayList<ChatInteractions>(); // List of chat interactions
	private String chatModelCode = null; // Code for the chat model used
	private List<String> choosedKnowledgeBases = null; // List of chosen knowledge bases for the chat
	/**
	 * The language the user explicitly asked the answers of this chat in (e.g.
	 * "French"), named in English; null when the user never asked for one. It holds
	 * until the user asks for another one.
	 */
	private String userRequiredLanguage = null;
	/**
	 * The language this chat is held in (e.g. "Italian"), named in English: set by the
	 * first trusted detection of a user's message and never changed by a later one, so a
	 * short or misdetected message does not switch it; only a language the user asks for
	 * explicitly ({@link #userRequiredLanguage}) wins over it. Null until a message is
	 * detected.
	 */
	private String chatLanguage = null;

	/**
	 * The language of the earliest request of this chat that had one, the request
	 * {@code excludedRequestId} left out (the one being answered); null when none had
	 * one. The language of a chat started before its own was kept.
	 */
	public String earliestRequestLanguage(String excludedRequestId) {
		if (interactions != null) {
			for (ChatInteractions interaction : interactions) {
				final GeboChatRequest request = interaction != null ? interaction.getRequest() : null;
				if (request != null && request.getUserLanguage() != null && !request.getUserLanguage().isBlank()
						&& (excludedRequestId == null || !excludedRequestId.equals(request.getId()))) {
					return request.getUserLanguage();
				}
			}
		}
		return null;
	}

	public IChatRequestContext createChatRequestContext() {
		ChatRequestContextImplBuilder builder = IChatRequestContext.builder();
		builder.sessionID(getCode());
		List<IChatSessionEntry> _interactions = new ArrayList<>();
		String lastQuestion = EMPTY_TEXT;
		for (int i = 0; i < interactions.size(); i++) {
			ChatInteractions interaction = interactions.get(i);
			ChatSessionEntryImplBuilder sBuilder = IChatSessionEntry.builder();
			String user = interaction.getRequest() != null && interaction.getRequest().getQuery() != null
					? interaction.getRequest().getQuery()
					: EMPTY_TEXT;
			lastQuestion = user;
			String assistant = interaction.getResponse() != null && interaction.getResponse().getQueryResponse() != null
					? interaction.getResponse().getQueryResponse().toString()
					: EMPTY_TEXT;
			sBuilder.user(user);
			sBuilder.assistant(assistant);
			_interactions.add(sBuilder.build());
			if (i == interactions.size() - 1) {
				if (interaction.getRequest() != null) {
					Map<String, Object> pipelineInfos = new HashMap<>();
					if (interaction.getRequest().getChatPipelineProcessId() != null) {
						pipelineInfos.put("user-choosed-pipelineId",
								interaction.getRequest().getChatPipelineProcessId());
					}
					if (interaction.getRequest().getUserIntent() != null) {
						pipelineInfos.put("user-intent", interaction.getRequest().getUserIntent().name());
					}
					if (interaction.getResponse() != null
							&& interaction.getResponse().getPipelineRouterDecisionCode() != null) {
						pipelineInfos.put("routed-pipelineId",
								interaction.getResponse().getPipelineRouterDecisionCode());
					}
					if (interaction.getResponse() != null && interaction.getResponse().getPipelineParams() != null) {
						pipelineInfos.put("routed-params", interaction.getResponse().getPipelineParams());
					}
					builder.pipelineInfos(pipelineInfos);
					builder.userLanguage(interaction.getRequest().getUserLanguage());
				}
			}
		}
		builder.interactions(_interactions);
		builder.actualUserRequest(lastQuestion);
		return builder.build();
	}


	/**
	 * The person this conversation belongs to. Not {@code userCreated}: a
	 * conversation opened for somebody - by work running as them, say - is theirs,
	 * and the security services ask the owner, not whoever wrote the row.
	 */
	@Override
	public String owner() {
		return username != null ? username : super.owner();
	}
}

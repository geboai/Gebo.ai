/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.angular.persistence.model.SimpleGObjectRef;
import ai.gebo.architecture.angular.persistence.service.GeboAngularFormGroupMetaInfoService;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/**
 * A chat profile a conversation runs with is not deletable: the conversation's
 * {@code chatProfileCode} is a {@code @GObjectReference} to the profile, and the
 * check the admin UI asks before deleting
 * ({@code checkDeletableBySimpleObjectRef}, which names the type by its simple
 * class name) counts it. A conversation that already has turns counts as much as
 * a new one: the dependants are matched by example on a fresh conversation, whose
 * list of interactions is not null.
 */
public class ChatProfileDeletableStatusTest extends AbstractBaseIntegrationTest {
	@Autowired
	private IGPersistentObjectManager persistenceManager;
	@Autowired
	private GUserChatSessionRepository sessions;
	@Autowired
	private GeboAngularFormGroupMetaInfoService metaInfo;

	private GChatProfileConfiguration aProfile(String description) throws Exception {
		final GChatProfileConfiguration profile = new GChatProfileConfiguration();
		profile.setDescription(description);
		return persistenceManager.insert(profile);
	}

	private GUserChatSession aChatWithATurn(String profileCode) {
		final GUserChatSession session = new GUserChatSession();
		session.setCode(UUID.randomUUID().toString());
		session.setDescription("Chat of the profile deletable test");
		session.setUsername(DEFAULT_ALL_ROLES_USER);
		session.setChatCreationDateTime(new Date());
		session.setChatProfileCode(profileCode);
		final GeboChatRequest request = new GeboChatRequest();
		request.setQuery("a question already asked");
		final ChatInteractions turn = new ChatInteractions();
		turn.setRequest(request);
		session.setInteractions(new ArrayList<>(List.of(turn)));
		return sessions.insert(session);
	}

	private boolean deletableFromTheAdminUi(GChatProfileConfiguration profile) throws Exception {
		final SimpleGObjectRef ref = new SimpleGObjectRef();
		ref.setSimpleClassName(GChatProfileConfiguration.class.getSimpleName());
		ref.setCode(profile.getCode());
		return metaInfo.getDeletableStatus(ref).isDeletable();
	}

	@Test
	public void aProfileAChatRunsWithIsNotDeletable() throws Exception {
		final GChatProfileConfiguration profile = aProfile("Profile a chat runs with");
		final GUserChatSession chat = aChatWithATurn(profile.getCode());
		try {
			assertFalse(persistenceManager.isDeletable(profile), "the chat refers to the profile");
			assertFalse(deletableFromTheAdminUi(profile), "the admin UI's check sees the chat");

			sessions.delete(chat);
			assertTrue(deletableFromTheAdminUi(profile), "once the chat is gone the profile can go");
		} finally {
			sessions.deleteById(chat.getCode());
			persistenceManager.delete(profile);
		}
	}

	@Test
	public void aProfileNoChatRunsWithIsDeletable() throws Exception {
		final GChatProfileConfiguration profile = aProfile("Profile no chat runs with");
		final GUserChatSession otherChat = aChatWithATurn(aProfile("Another profile").getCode());
		try {
			assertTrue(deletableFromTheAdminUi(profile), "a chat with another profile does not count");
		} finally {
			sessions.delete(otherChat);
			persistenceManager.delete(profile);
			persistenceManager.delete(persistenceManager.findById(GChatProfileConfiguration.class,
					otherChat.getChatProfileCode()));
		}
	}
}

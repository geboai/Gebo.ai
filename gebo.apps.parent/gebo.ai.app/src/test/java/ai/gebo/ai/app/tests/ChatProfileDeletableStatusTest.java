/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.angular.persistence.model.SimpleGObjectRef;
import ai.gebo.architecture.angular.persistence.service.GeboAngularFormGroupMetaInfoService;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.client.rest.controllers.GeboAdminChatProfilesConfigurationController;
import ai.gebo.llms.openai.model.GOpenAIChatModelConfig;
import ai.gebo.model.base.GObjectRef;

/**
 * A chat profile a conversation runs with is not deletable: the conversation's
 * {@code chatProfileCode} is a {@code @GObjectReference} to the profile, and the
 * check the admin UI asks before deleting
 * ({@code checkDeletableBySimpleObjectRef}, which names the type by its simple
 * class name) counts it. A conversation that already has turns counts as much as
 * a new one: the dependants are matched by example on a fresh conversation, whose
 * list of interactions is not null. The delete endpoint runs the same check, so
 * a caller going straight to it is refused as well.
 */
public class ChatProfileDeletableStatusTest extends AbstractBaseIntegrationTest {
	@Autowired
	private IGPersistentObjectManager persistenceManager;
	@Autowired
	private GUserChatSessionRepository sessions;
	@Autowired
	private GeboAngularFormGroupMetaInfoService metaInfo;
	@Autowired
	private GeboAdminChatProfilesConfigurationController profilesController;
	@Autowired
	private MongoTemplate mongoTemplate;

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

	/**
	 * A conversation saved by an older version holds an intent this one no
	 * longer has: it still counts against deleting its profile, and it reads.
	 */
	@Test
	public void aChatSavedWithARemovedIntentCountsAndReads() throws Exception {
		final GChatProfileConfiguration profile = aProfile("Profile an old chat runs with");
		final GUserChatSession chat = aChatWithATurn(profile.getCode());
		try {
			mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(chat.getCode())),
					new Update().set("interactions.0.request.userIntent", "PURE_SEARCH"), GUserChatSession.class);

			assertFalse(deletableFromTheAdminUi(profile), "the old chat refers to the profile");
			assertThrows(GeboPersistenceException.class, () -> profilesController.deleteChatProfile(profile),
					"the endpoint refuses as well");
			final GUserChatSession read = sessions.findById(chat.getCode()).orElseThrow();
			assertEquals(DeliverableIntent.UNKNOWN, read.getInteractions().get(0).getRequest().getUserIntent(),
					"the removed intent reads as unknown");
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

	@Test
	public void theDeleteEndpointRefusesAProfileAChatRunsWith() throws Exception {
		final GChatProfileConfiguration profile = aProfile("Profile the endpoint is asked to delete");
		final GUserChatSession chat = aChatWithATurn(profile.getCode());
		try {
			assertThrows(GeboPersistenceException.class, () -> profilesController.deleteChatProfile(profile),
					"the chat refers to the profile");
			assertNotNull(persistenceManager.findById(GChatProfileConfiguration.class, profile.getCode()),
					"the profile is still there");

			sessions.delete(chat);
			profilesController.deleteChatProfile(profile);
			assertNull(persistenceManager.findById(GChatProfileConfiguration.class, profile.getCode()),
					"once the chat is gone the endpoint deletes it");
		} finally {
			sessions.deleteById(chat.getCode());
			if (persistenceManager.findById(GChatProfileConfiguration.class, profile.getCode()) != null) {
				persistenceManager.delete(profile);
			}
		}
	}

	/**
	 * A network exported over A2A names its chat profile in an element of the list of
	 * the exports of its server: the profile is not deletable while an export names it.
	 */
	@Test
	public void aProfileAnA2AExportWorksWithIsNotDeletable() throws Exception {
		final GChatProfileConfiguration profile = aProfile("Profile an A2A export works with");
		final GChatProfileConfiguration other = aProfile("Profile no export works with");
		final A2AExportedAgent first = new A2AExportedAgent();
		first.setNetworkCode("a-network");
		first.setSkillName("first");
		final A2AExportedAgent second = new A2AExportedAgent();
		second.setNetworkCode("a-network");
		second.setSkillName("second");
		second.setChatProfileCode(profile.getCode());
		A2AServerConfig server = new A2AServerConfig();
		server.setDescription("A2A server of the profile deletable test");
		server.setExportedRelativeUrl("deletable-test-" + UUID.randomUUID());
		server.setExportedAgents(List.of(first, second));
		server = persistenceManager.insert(server);
		try {
			assertFalse(persistenceManager.isDeletable(profile), "an export of the server names the profile");
			assertFalse(deletableFromTheAdminUi(profile), "the admin UI's check sees the export");
			assertThrows(GeboPersistenceException.class, () -> profilesController.deleteChatProfile(profile),
					"the endpoint refuses as well");
			assertTrue(persistenceManager.isDeletable(other), "a profile no export names can go");

			persistenceManager.delete(server);
			server = null;
			assertTrue(deletableFromTheAdminUi(profile), "once the server is gone the profile can go");
		} finally {
			if (server != null) {
				persistenceManager.delete(server);
			}
			persistenceManager.delete(profile);
			persistenceManager.delete(other);
		}
	}

	/** The same for a network exported as a tool of an MCP server. */
	@Test
	public void aProfileAnMcpNetworkToolWorksWithIsNotDeletable() throws Exception {
		final GChatProfileConfiguration profile = aProfile("Profile an MCP tool works with");
		final GeboMCPAgentsNetworkTool tool = new GeboMCPAgentsNetworkTool();
		tool.setAgentsNetworkCode("a-network");
		tool.setToolName("ask");
		tool.setChatProfileCode(profile.getCode());
		GeboMCPServerConfig server = new GeboMCPServerConfig();
		server.setDescription("MCP server of the profile deletable test");
		server.setExportedUniqueRelativeUrl("deletable-test-" + UUID.randomUUID());
		server.setAgentNetworkAsTools(List.of(tool));
		server = persistenceManager.insert(server);
		try {
			assertFalse(persistenceManager.isDeletable(profile), "a tool of the server names the profile");
			assertFalse(deletableFromTheAdminUi(profile), "the admin UI's check sees the tool");
			assertThrows(GeboPersistenceException.class, () -> profilesController.deleteChatProfile(profile),
					"the endpoint refuses as well");

			persistenceManager.delete(server);
			server = null;
			assertTrue(deletableFromTheAdminUi(profile), "once the server is gone the profile can go");
		} finally {
			if (server != null) {
				persistenceManager.delete(server);
			}
			persistenceManager.delete(profile);
		}
	}

	/**
	 * A chat model is checked through the profiles that run with it. A fresh
	 * profile holds no initialized property besides its reference, so the example
	 * has nothing to ignore: the check must still run, not fail, and see the
	 * profile.
	 */
	@Test
	public void aChatModelAProfileRunsWithIsNotDeletable() throws Exception {
		final GBaseChatModelConfig model = new GOpenAIChatModelConfig();
		model.setCode("deletable-test-chat-model-" + UUID.randomUUID());
		model.setDescription("Chat model of the deletable test");
		assertTrue(persistenceManager.isDeletable(model), "no profile runs with the model yet");

		final GChatProfileConfiguration profile = new GChatProfileConfiguration();
		profile.setDescription("Profile running with the test chat model");
		profile.setChatModelReference(GObjectRef.of(model));
		final GChatProfileConfiguration saved = persistenceManager.insert(profile);
		try {
			assertFalse(persistenceManager.isDeletable(model), "the profile runs with the model");
		} finally {
			persistenceManager.delete(saved);
		}
	}
}

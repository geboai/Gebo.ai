/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.model.GChatLibraryInstances;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatLibraryInstancesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;

/**
 * Two agents of a network work at the same time, and each has the library it works
 * with created for the conversation: the conversation must record both, or the
 * files one of them wrote are in a folder no later request finds.
 */
class GAIToolsLibraryInstancesParallelReclaimTest {
	private static final String CONVERSATION = "the-conversation";
	private static final String SPREADSHEETS = "spreadsheets-folder";
	private static final String TEXT_DOCUMENTS = "text-documents-folder";

	/** What the repository holds: a copy is handed out and a copy is kept, as a database does. */
	private final AtomicReference<Map<String, String>> stored = new AtomicReference<>();

	private static AIToolsAndResourcesLibraryType type(String id) {
		AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(id);
		return type;
	}

	@Test
	void twoAgentsCreatingTheirLibrariesAtTheSameTimeAreBothRecordedByTheConversation() throws Exception {
		final AIToolsAndResourcesLibraryType spreadsheets = type(SPREADSHEETS);
		final AIToolsAndResourcesLibraryType textDocuments = type(TEXT_DOCUMENTS);
		final CountDownLatch creatingTheFirst = new CountDownLatch(1);
		IGAIToolsAndResourcesLibrarySource source = mock(IGAIToolsAndResourcesLibrarySource.class);
		when(source.getId()).thenReturn("documents");
		when(source.getManagedTypes()).thenReturn(List.of(spreadsheets, textDocuments));
		when(source.getInstanceById(anyString(), anyString())).thenAnswer(invocation -> {
			AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
			instance.setId(invocation.getArgument(0));
			return instance;
		});
		when(source.createInstance(any(), anyList())).thenAnswer(invocation -> {
			final AIToolsAndResourcesLibraryType created = invocation.getArgument(0);
			if (SPREADSHEETS.equals(created.getId())) {
				// the other agent enters while this folder is being created
				creatingTheFirst.countDown();
				Thread.sleep(400);
			}
			AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
			instance.setId("instance-of-" + created.getId());
			instance.setLibraryType(created);
			return instance;
		});
		IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries = mock(
				IGAIToolsAndResourcesLibrarySourceRepositoryPattern.class);
		when(libraries.getImplementations()).thenReturn(List.of(source));
		GUserChatSession conversation = new GUserChatSession();
		conversation.setCode(CONVERSATION);
		conversation.setUsername("somebody");
		GUserChatSessionRepository conversations = mock(GUserChatSessionRepository.class);
		when(conversations.findById(CONVERSATION)).thenReturn(Optional.of(conversation));
		ChatLibraryInstancesRepository records = mock(ChatLibraryInstancesRepository.class);
		when(records.findById(any())).thenAnswer(invocation -> {
			final Map<String, String> ids = stored.get();
			if (ids == null) {
				return Optional.empty();
			}
			GChatLibraryInstances record = new GChatLibraryInstances();
			record.setUserChatContextCode(CONVERSATION);
			record.setInstanceIds(new LinkedHashMap<>(ids));
			return Optional.of(record);
		});
		when(records.save(any())).thenAnswer(invocation -> {
			final GChatLibraryInstances saved = invocation.getArgument(0);
			stored.set(new LinkedHashMap<>(saved.getInstanceIds()));
			return saved;
		});
		final GAIToolsLibraryInstancesServiceImpl service = new GAIToolsLibraryInstancesServiceImpl(libraries,
				conversations, records);

		final ExecutorService agents = Executors.newFixedThreadPool(2);
		try {
			Future<Map<String, AIToolsAndResourcesLibraryInstance>> spreadsheetsAgent = agents
					.submit(() -> service.reclaim(CONVERSATION, List.of(SPREADSHEETS)));
			assertTrue(creatingTheFirst.await(5, TimeUnit.SECONDS));
			Future<Map<String, AIToolsAndResourcesLibraryInstance>> textDocumentsAgent = agents
					.submit(() -> service.reclaim(CONVERSATION, List.of(TEXT_DOCUMENTS)));

			assertEquals("instance-of-" + SPREADSHEETS,
					spreadsheetsAgent.get(10, TimeUnit.SECONDS).get(SPREADSHEETS).getId());
			assertEquals("instance-of-" + TEXT_DOCUMENTS,
					textDocumentsAgent.get(10, TimeUnit.SECONDS).get(TEXT_DOCUMENTS).getId());
		} finally {
			agents.shutdownNow();
		}

		assertEquals(Map.of(SPREADSHEETS, "instance-of-" + SPREADSHEETS, TEXT_DOCUMENTS,
				"instance-of-" + TEXT_DOCUMENTS), stored.get(),
				"the conversation records the library of each agent: neither save replaced the other's");
	}
}

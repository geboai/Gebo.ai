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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.model.GChatLibraryInstances;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatLibraryInstancesRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/**
 * Two agents of a network work at the same time, each on the library it works
 * with: the conversation must hold both, or the files one of them wrote are in a
 * folder no later request finds. The network reserves the handles of both before
 * they start, so nothing is written while they work; agents run with no network
 * reserving are given their handles as they access the libraries, one at a time.
 */
class GAIToolsLibraryInstancesParallelReclaimTest {
	private static final String CONVERSATION = "the-conversation";
	private static final String SPREADSHEETS = "spreadsheets-folder";
	private static final String TEXT_DOCUMENTS = "text-documents-folder";

	/** What the repository holds: a copy is handed out and a copy is kept, as a database does. */
	private final AtomicReference<Map<String, String>> stored = new AtomicReference<>();
	private final AtomicInteger saves = new AtomicInteger();
	/** The instances the source allocated, by their id: the handle each was allocated under. */
	private final Set<String> allocated = ConcurrentHashMap.newKeySet();
	private final CountDownLatch allocatingTheFirst = new CountDownLatch(1);
	private GAIToolsLibraryInstancesServiceImpl service;

	private static AIToolsAndResourcesLibraryType type(String id) {
		AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
		type.setId(id);
		return type;
	}

	@BeforeEach
	void setUp() {
		IGAIToolsAndResourcesLibrarySource source = mock(IGAIToolsAndResourcesLibrarySource.class);
		when(source.getId()).thenReturn("documents");
		when(source.getManagedTypes()).thenReturn(List.of(type(SPREADSHEETS), type(TEXT_DOCUMENTS)));
		// an instance exists once it was allocated under its handle, not before
		when(source.getInstanceById(anyString(), anyString())).thenAnswer(invocation -> {
			if (!allocated.contains(invocation.<String>getArgument(0))) {
				return null;
			}
			AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
			instance.setId(invocation.getArgument(0));
			return instance;
		});
		when(source.allocateInstance(any(), anyList(), anyString())).thenAnswer(invocation -> {
			final AIToolsAndResourcesLibraryType type = invocation.getArgument(0);
			if (SPREADSHEETS.equals(type.getId())) {
				// the other agent enters while this folder is being allocated
				allocatingTheFirst.countDown();
				Thread.sleep(400);
			}
			AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
			instance.setId(invocation.getArgument(2));
			instance.setLibraryType(type);
			allocated.add(instance.getId());
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
			saves.incrementAndGet();
			return saved;
		});
		service = new GAIToolsLibraryInstancesServiceImpl(libraries, conversations, records);
	}

	/** The two agents reclaim their libraries at the same time; what each was given, by its library type. */
	private Map<String, String> theTwoAgentsWorkAtTheSameTime() throws Exception {
		final ExecutorService agents = Executors.newFixedThreadPool(2);
		try {
			Future<Map<String, AIToolsAndResourcesLibraryInstance>> spreadsheetsAgent = agents
					.submit(() -> service.reclaim(CONVERSATION, List.of(SPREADSHEETS)));
			assertTrue(allocatingTheFirst.await(5, TimeUnit.SECONDS));
			Future<Map<String, AIToolsAndResourcesLibraryInstance>> textDocumentsAgent = agents
					.submit(() -> service.reclaim(CONVERSATION, List.of(TEXT_DOCUMENTS)));
			return Map.of(SPREADSHEETS, spreadsheetsAgent.get(10, TimeUnit.SECONDS).get(SPREADSHEETS).getId(),
					TEXT_DOCUMENTS, textDocumentsAgent.get(10, TimeUnit.SECONDS).get(TEXT_DOCUMENTS).getId());
		} finally {
			agents.shutdownNow();
		}
	}

	@Test
	void theHandlesReservedBeforeTheRunAreWhatTheAgentsWorkOnAndNothingIsWrittenWhileTheyWork() throws Exception {
		service.reserve(CONVERSATION, List.of(SPREADSHEETS));
		service.reserve(CONVERSATION, List.of(TEXT_DOCUMENTS));
		final Map<String, String> reserved = stored.get();
		assertEquals(Set.of(SPREADSHEETS, TEXT_DOCUMENTS), reserved.keySet());
		assertTrue(allocated.isEmpty(), "a handle is not an instance: nothing is allocated before an agent accesses it");
		final int savesBeforeTheRun = saves.get();

		final Map<String, String> worked = theTwoAgentsWorkAtTheSameTime();

		assertEquals(reserved, worked, "each agent works on the instance of the handle reserved for its library");
		assertEquals(reserved, stored.get());
		assertEquals(savesBeforeTheRun, saves.get(),
				"what the conversation holds was written before the agents started: they only read it");
		assertEquals(Set.copyOf(reserved.values()), allocated);
	}

	@Test
	void twoAgentsWithNoHandleReservedAreGivenTheirsOneAtATimeAndBothAreRecorded() throws Exception {
		final Map<String, String> worked = theTwoAgentsWorkAtTheSameTime();

		assertEquals(worked, stored.get(),
				"the conversation records the library of each agent: neither save replaced the other's");
		assertEquals(Set.copyOf(worked.values()), allocated);
	}
}

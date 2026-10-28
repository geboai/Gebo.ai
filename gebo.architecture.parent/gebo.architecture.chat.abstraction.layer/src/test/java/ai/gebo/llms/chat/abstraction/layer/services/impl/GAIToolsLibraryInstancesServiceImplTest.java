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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.model.AIResource;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/**
 * A conversation holds one instance of every library its agents work with:
 * created the first time one is needed and recorded on the conversation,
 * reclaimed by every agent after that, dropped with the conversation.
 */
class GAIToolsLibraryInstancesServiceImplTest {
	private static final String WORKING_DIRECTORY = "working-directory";
	private static final String CONVERSATION = "the-conversation";

	private IGAIToolsAndResourcesLibrarySource source;
	private GUserChatSessionRepository conversations;
	private GUserChatSession conversation;
	private AIToolsAndResourcesLibraryType workingDirectory;
	private GAIToolsLibraryInstancesServiceImpl service;

	@BeforeEach
	void setUp() {
		workingDirectory = new AIToolsAndResourcesLibraryType();
		workingDirectory.setId(WORKING_DIRECTORY);
		workingDirectory.setDescription("A working directory");
		source = mock(IGAIToolsAndResourcesLibrarySource.class);
		when(source.getId()).thenReturn("working-directories");
		when(source.getManagedTypes()).thenReturn(List.of(workingDirectory));
		when(source.createInstance(eq(workingDirectory), anyList())).thenAnswer(invocation -> {
			AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
			instance.setId("dir-of-" + invocation.<List<String>>getArgument(1).get(0));
			instance.setLibraryType(workingDirectory);
			instance.setDescription("/work");
			AIResource readme = new AIResource();
			readme.setId("README.md");
			readme.setDescription("what the directory is for");
			instance.setResources(List.of(readme));
			return instance;
		});
		IGAIToolsAndResourcesLibrarySourceRepositoryPattern libraries = mock(
				IGAIToolsAndResourcesLibrarySourceRepositoryPattern.class);
		when(libraries.getImplementations()).thenReturn(List.of(source));
		conversation = new GUserChatSession();
		conversation.setCode(CONVERSATION);
		conversation.setUsername("somebody");
		conversations = mock(GUserChatSessionRepository.class);
		when(conversations.findById(CONVERSATION)).thenReturn(Optional.of(conversation));
		when(conversations.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
		service = new GAIToolsLibraryInstancesServiceImpl(libraries, conversations);
	}

	@Test
	void theFirstAgentNeedingALibraryHasItCreatedAndTheConversationRecordsIt() {
		Map<String, AIToolsAndResourcesLibraryInstance> held = service.reclaim(CONVERSATION, List.of(WORKING_DIRECTORY));

		assertEquals("dir-of-" + CONVERSATION, held.get(WORKING_DIRECTORY).getId());
		assertEquals(Map.of(WORKING_DIRECTORY, "dir-of-" + CONVERSATION), conversation.getLibraryInstanceIds());
		verify(conversations).save(conversation);
		verify(source).createInstance(eq(workingDirectory), eq(List.of(CONVERSATION, "somebody")));
	}

	@Test
	void everyAgentAfterThatReclaimsTheSameInstance() {
		AIToolsAndResourcesLibraryInstance existing = new AIToolsAndResourcesLibraryInstance();
		existing.setId("dir-of-" + CONVERSATION);
		existing.setLibraryType(workingDirectory);
		conversation.setLibraryInstanceIds(Map.of(WORKING_DIRECTORY, existing.getId()));
		when(source.getInstanceById(existing.getId(), CONVERSATION)).thenReturn(existing);

		assertSame(existing, service.reclaim(CONVERSATION, List.of(WORKING_DIRECTORY)).get(WORKING_DIRECTORY));
		assertSame(existing, service.reclaim(CONVERSATION, List.of(WORKING_DIRECTORY)).get(WORKING_DIRECTORY));
		verify(source, never()).createInstance(any(), anyList());
		verify(conversations, never()).save(any());
	}

	@Test
	void anInstanceThatIsGoneIsCreatedAgain() {
		conversation.setLibraryInstanceIds(Map.of(WORKING_DIRECTORY, "dir-of-old"));
		when(source.getInstanceById("dir-of-old", CONVERSATION)).thenReturn(null);

		Map<String, AIToolsAndResourcesLibraryInstance> held = service.reclaim(CONVERSATION, List.of(WORKING_DIRECTORY));

		assertEquals("dir-of-" + CONVERSATION, held.get(WORKING_DIRECTORY).getId());
		assertEquals("dir-of-" + CONVERSATION, conversation.getLibraryInstanceIds().get(WORKING_DIRECTORY));
	}

	@Test
	void aTypeTheInstallationLacksAndAConversationThatDoesNotExistHoldNothing() {
		assertTrue(service.reclaim(CONVERSATION, List.of("a-library-this-installation-lacks")).isEmpty());
		assertTrue(service.reclaim("no-such-conversation", List.of(WORKING_DIRECTORY)).isEmpty());
		assertTrue(service.reclaim(CONVERSATION, List.of()).isEmpty());
		verify(source, never()).createInstance(any(), anyList());
	}

	@Test
	void theAgentsAreToldWhatTheLibrariesHold() {
		Map<String, AIToolsAndResourcesLibraryInstance> held = service.reclaim(CONVERSATION, List.of(WORKING_DIRECTORY));

		List<String> described = service.describe(held);

		assertEquals(1, described.size());
		assertTrue(described.get(0).contains("A working directory: /work"), described.get(0));
		assertTrue(described.get(0).contains("README.md: what the directory is for"), described.get(0));
		assertEquals(List.of(), service.describe(Map.of()), "nothing to say of no library");
	}

	@Test
	void droppingReleasesWhatTheConversationHeld() {
		AIToolsAndResourcesLibraryInstance existing = new AIToolsAndResourcesLibraryInstance();
		existing.setId("dir-of-" + CONVERSATION);
		conversation.setLibraryInstanceIds(Map.of(WORKING_DIRECTORY, existing.getId()));
		when(source.getInstanceById(existing.getId(), CONVERSATION)).thenReturn(existing);

		service.dropHeldBy(CONVERSATION);

		verify(source).dropInstance(existing);
		assertNull(conversation.getLibraryInstanceIds());
		verify(conversations).save(conversation);
	}

	@Test
	void anInstanceThatCannotBeDroppedDoesNotStopTheRest() {
		AIToolsAndResourcesLibraryInstance existing = new AIToolsAndResourcesLibraryInstance();
		existing.setId("dir-of-" + CONVERSATION);
		conversation.setLibraryInstanceIds(Map.of(WORKING_DIRECTORY, existing.getId()));
		when(source.getInstanceById(existing.getId(), CONVERSATION)).thenReturn(existing);
		doThrow(new IllegalStateException("busy")).when(source).dropInstance(existing);

		service.dropHeldBy(CONVERSATION);

		assertNull(conversation.getLibraryInstanceIds(), "the conversation holds nothing any more");
		verify(conversations).save(conversation);
		service.dropHeldBy("no-such-conversation");
	}
}

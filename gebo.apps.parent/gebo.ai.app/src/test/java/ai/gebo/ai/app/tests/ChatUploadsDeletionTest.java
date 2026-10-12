/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatUploadsController;
import ai.gebo.model.OperationStatus;

public class ChatUploadsDeletionTest extends AbstractBaseTestLLmsIntegrationTests {

	@Autowired
	private GeboUserChatUploadsController uploadsController;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	@Override
	protected void beforeEachCallback() throws Exception {
		// New chats of the same user get the same code once the base class wipes the sessions.
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
	}

	@Test
	public void testUploadsAreDeletedOnlyThroughTheirOwnChat() throws Exception {
		String chat = newSession("first chat");
		String otherChat = newSession("second chat");
		MockMultipartFile file = new MockMultipartFile("files[]", "notes.txt", "text/plain",
				"Paris is the capital of France.".getBytes(StandardCharsets.UTF_8));
		OperationStatus<List<UserUploadedContent>> uploaded = uploadsController.chatSessionUpload(chat, List.of(file));
		assertNotNull(uploaded.getResult(), "The text file must be accepted");
		List<UserUploadedContent> contents = uploaded.getResult();

		assertThrows(SecurityException.class, () -> uploadsController.deleteSessionUploads(otherChat, contents));

		OperationStatus<List<UserUploadedContent>> deleted = uploadsController.deleteSessionUploads(chat, contents);
		assertEquals(contents.stream().map(UserUploadedContent::getCode).toList(),
				deleted.getResult().stream().map(UserUploadedContent::getCode).toList());
	}

	private String newSession(String query) throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery(query);
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}
}

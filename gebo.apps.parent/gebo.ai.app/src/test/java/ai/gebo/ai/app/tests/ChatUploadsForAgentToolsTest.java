/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySourceRepositoryPattern;
import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GChatUploadFileTypes;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatAgentsToolsDescriber;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.impl.ChatUploadsFilesProvider;
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatUploadsController;
import ai.gebo.model.OperationStatus;

/**
 * A chat accepts the files its agents' libraries work on even when no content
 * handler reads them: kept for the tools, found among the conversation's uploads,
 * not read into the chat. What neither reads is refused and leaves nothing behind.
 * The chat's agents mount a test library taking pictures; the beans standing for it
 * are this class's own, so no other test context gets them.
 */
public class ChatUploadsForAgentToolsTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String PICTURES_LIBRARY = "test-pictures-library";

	@MockitoBean
	private IGChatAgentsToolsDescriber agentsToolsDescriber;
	@MockitoSpyBean
	private IGAIToolsAndResourcesLibrarySourceRepositoryPattern librarySources;
	@Autowired
	private GeboUserChatUploadsController uploadsController;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private IGChatStorageAreaService storageArea;
	@Autowired
	private ChatUploadsFilesProvider uploadsFiles;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	@Override
	protected void beforeEachCallback() throws Exception {
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		when(agentsToolsDescriber.libraryTypeIds(any())).thenReturn(List.of(PICTURES_LIBRARY));
		doAnswer(invocation -> {
			@SuppressWarnings("unchecked")
			final List<IGAIToolsAndResourcesLibrarySource> real = (List<IGAIToolsAndResourcesLibrarySource>) invocation
					.callRealMethod();
			final List<IGAIToolsAndResourcesLibrarySource> all = new ArrayList<>(real);
			all.add(new PicturesLibrary());
			return all;
		}).when(librarySources).getImplementations();
	}

	@Test
	public void aPictureIsKeptForTheAgentsToolsAndNotReadIntoTheChat() throws Exception {
		final String chat = newSession("a chat with pictures");
		final GChatUploadFileTypes accepted = uploadsController.acceptedFileTypes(chat, null);
		assertEquals(List.of(".png"), accepted.getToolsExtensions(), "the library's pictures, kept for its tools");
		assertTrue(accepted.getChatExtensions().contains(".txt"), "the content handlers' files are read in the chat");

		final OperationStatus<List<UserUploadedContent>> uploaded = uploadsController.chatSessionUpload(chat,
				List.of(new MockMultipartFile("files[]", "chart.png", "image/png", png())));
		assertNotNull(uploaded.getResult(), "a picture the chat's agents work on is accepted");
		final UserUploadedContent picture = uploaded.getResult().get(0);
		assertEquals(".png", picture.getExtension());
		assertEquals(0L, picture.getTokensCount(), "nothing of it is read into the chat");

		final List<String> listed = uploadsFiles.filesOf(chat).list(null, false).stream().map(GLibraryFile::path)
				.toList();
		assertEquals(List.of("chart.png"), listed, "the tools find it among the conversation's uploads");

		final IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		final GeboChatRequest request = new GeboChatRequest();
		request.setUserChatContextCode(chat);
		request.setQuery("Put the chart in the report");
		request.setUserUploadedContents(List.of(picture));
		assertDoesNotThrow(() -> lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET),
				"a request carrying an upload with no ingested text starts");
		lifeCycleService.releaseRequest(request);
	}

	@Test
	public void aFileNeitherTheChatNorItsAgentsReadIsRefusedAndLeavesNothingBehind() throws Exception {
		final String chat = newSession("a chat refusing a file");
		final OperationStatus<List<UserUploadedContent>> uploaded = uploadsController.chatSessionUpload(chat,
				List.of(new MockMultipartFile("files[]", "raw.unknownext", "application/octet-stream",
						"not a document".getBytes(StandardCharsets.UTF_8))));
		assertNull(uploaded.getResult(), "refused");
		assertFalse(uploaded.getMessages().isEmpty(), "the user is told why");
		try (Stream<Path> files = Files.list(storageArea.getSessionPath(chat))) {
			assertEquals(List.of(), files.toList(), "the refused file is not left in the session's folder");
		}
		assertTrue(uploadsFiles.filesOf(chat).list(null, false).isEmpty());
	}

	@Test
	public void aChatWhoseAgentsHoldNoLibraryAcceptsOnlyWhatItReads() throws Exception {
		when(agentsToolsDescriber.libraryTypeIds(any())).thenReturn(List.of());
		final String chat = newSession("a chat without libraries");
		assertEquals(List.of(), uploadsController.acceptedFileTypes(chat, null).getToolsExtensions());
		final OperationStatus<List<UserUploadedContent>> uploaded = uploadsController.chatSessionUpload(chat,
				List.of(new MockMultipartFile("files[]", "chart.png", "image/png", png())));
		assertNull(uploaded.getResult(), "no agent of this chat works on pictures");
	}

	private String newSession(String query) throws Exception {
		final GeboChatRequest first = new GeboChatRequest();
		first.setQuery(query);
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}

	private static byte[] png() throws Exception {
		final BufferedImage image = new BufferedImage(4, 2, BufferedImage.TYPE_INT_RGB);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		return out.toByteArray();
	}

	/** A library whose tools work on uploaded pictures; it never runs here. */
	private static final class PicturesLibrary implements IGAIToolsAndResourcesLibrarySource {
		@Override
		public String getId() {
			return "test-pictures";
		}

		@Override
		public String getDescription() {
			return "Test pictures";
		}

		@Override
		public List<AIToolsAndResourcesLibraryType> getManagedTypes() {
			final AIToolsAndResourcesLibraryType type = new AIToolsAndResourcesLibraryType();
			type.setId(PICTURES_LIBRARY);
			type.setDescription("Pictures for the tools");
			type.setUploadFileTypes(List.of("PNG"));
			return List.of(type);
		}

		@Override
		public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner) {
			return null;
		}

		@Override
		public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type,
				List<String> owners) {
			return null;
		}

		@Override
		public void dropInstance(AIToolsAndResourcesLibraryInstance instance) {
		}

		@Override
		public List<ToolCallback> getToolCallbacks(AIToolsAndResourcesLibraryInstance instance) {
			return List.of();
		}
	}
}

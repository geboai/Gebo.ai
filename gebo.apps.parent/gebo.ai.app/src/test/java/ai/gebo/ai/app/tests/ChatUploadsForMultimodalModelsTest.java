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
 * A picture uploaded to a chat whose model takes pictures is accepted although no
 * content handler reads it, travels with the request as a file and reaches the model
 * in the last user message; a model that declares text only never gets it, and the
 * calls working on the request's text get none.
 */
public class ChatUploadsForMultimodalModelsTest extends AbstractBaseTestLLmsIntegrationTests {
	@MockitoBean
	private IGChatAgentsToolsDescriber agentsToolsDescriber;
	@Autowired
	private GeboUserChatUploadsController uploadsController;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService storageArea;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	private final List<org.springframework.ai.chat.prompt.Prompt> prompts = new java.util.concurrent.CopyOnWriteArrayList<>();

	@Override
	protected void beforeEachCallback() throws Exception {
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		// no agent of the chat works on pictures: only the model can want them
		when(agentsToolsDescriber.libraryTypeIds(any())).thenReturn(List.of());
		prompts.clear();
		ai.gebo.llms.abstraction.layer.tests.TestChatModel.setGlobalPromptObserver(prompts::add);
	}

	@org.junit.jupiter.api.AfterEach
	public void restoreTheModel() {
		ai.gebo.llms.abstraction.layer.tests.TestChatModel.clearGlobalPromptObserver();
		config(answeringModel()).setInputModalities(null);
	}

	@SuppressWarnings("rawtypes")
	private IGConfigurableChatModel answeringModel() {
		return chatModelRuntimeDao.defaultHandler();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void aPictureReachesTheModelThatDeclaresPictures() throws Exception {
		final IGConfigurableChatModel model = answeringModel();
		config(model).setInputModalities(List.of(ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT,
				ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE));
		final String chat = newSession("a chat with a model that sees");
		final GChatUploadFileTypes accepted = uploadsController.acceptedFileTypes(chat, null);
		assertEquals(List.of("image/*"), accepted.getModelMimeTypes(), "what the model declares and its integration carries");
		assertEquals(List.of(), accepted.getToolsExtensions());

		final byte[] png = png();
		final OperationStatus<List<UserUploadedContent>> uploaded = uploadsController.chatSessionUpload(chat,
				List.of(new MockMultipartFile("files[]", "chart.png", "image/png", png)));
		assertNotNull(uploaded.getResult(), "a picture the chat's model takes is accepted");
		final UserUploadedContent picture = uploaded.getResult().get(0);

		final GeboChatRequest request = new GeboChatRequest();
		request.setUserChatContextCode(chat);
		request.setQuery("What does the chart show?");
		request.setUserUploadedContents(List.of(picture));
		final ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources resources = lifeCycleService
				.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		try {
			final ai.gebo.llms.abstraction.layer.model.IChatRequestContext context = resources
					.createChatRequestContext();
			assertEquals(1, context.getMedia().size(), "the picture travels with the request");
			assertEquals("chart.png", context.getMedia().get(0).getName());
			assertEquals("image/png", context.getMedia().get(0).getMimeType());

			final ai.gebo.architecture.ai.model.GPromptTemplateConfig prompt = new ai.gebo.architecture.ai.model.GPromptTemplateConfig();
			prompt.setSystemPromptTemplate("You answer questions about the files of the conversation.");
			prompt.setUserPromptTemplate("{question}");
			prompt.setContextDocuments(ai.gebo.architecture.ai.model.ContextContentRequired.NOT_REQUIRED);

			prompts.clear();
			model.textResponse(prompt, java.util.Map.of(), context);
			assertFalse(prompts.isEmpty(), "the model was called");
			final org.springframework.ai.chat.messages.UserMessage seen = lastUserMessage(prompts.get(prompts.size() - 1));
			assertEquals(1, seen.getMedia().size(), "the model gets the picture in its last user message");
			assertEquals("image/png", seen.getMedia().get(0).getMimeType().toString());
			org.junit.jupiter.api.Assertions.assertArrayEquals(png, seen.getMedia().get(0).getDataAsByteArray());

			// a call on the request's text, as the router's and the rewriter's
			prompts.clear();
			model.textResponse(prompt, java.util.Map.of(), context.withoutMedia());
			assertTrue(lastUserMessage(prompts.get(prompts.size() - 1)).getMedia().isEmpty());

			// the same request to a model that declares text only
			config(model).setInputModalities(null);
			prompts.clear();
			model.textResponse(prompt, java.util.Map.of(), context);
			assertTrue(lastUserMessage(prompts.get(prompts.size() - 1)).getMedia().isEmpty(),
					"a text only model never gets a file");
		} finally {
			lifeCycleService.releaseRequest(request);
		}
	}

	@Test
	public void aTextOnlyModelsChatRefusesAPictureNobodyReads() throws Exception {
		final String chat = newSession("a chat with a text only model");
		assertEquals(List.of(), uploadsController.acceptedFileTypes(chat, null).getModelMimeTypes());
		final OperationStatus<List<UserUploadedContent>> uploaded = uploadsController.chatSessionUpload(chat,
				List.of(new MockMultipartFile("files[]", "chart.png", "image/png", png())));
		assertNull(uploaded.getResult(), "neither the model nor an agent of this chat takes pictures");
	}

	@Test
	public void aPictureTheChatGeneratedTravelsWithTheNextRequests() throws Exception {
		final String chat = newSession("a chat that generated a picture");
		final ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource generated = storageArea
				.addMedia(org.springframework.ai.content.Media.builder()
						.mimeType(org.springframework.util.MimeTypeUtils.IMAGE_PNG).data(png()).name("drawing").build(),
						chat);
		assertEquals("image/png", generated.getContentType(), "the whole type, as the file is served");
		assertTrue(generated.getFileSize() > 0);
		final List<ai.gebo.llms.abstraction.layer.model.IChatRequestMedia> media = storageArea.getSessionMedia(chat);
		assertEquals(1, media.size(), "a later question about the picture can reach a model that sees");
		assertEquals("image/png", media.get(0).getMimeType());
		org.junit.jupiter.api.Assertions.assertArrayEquals(png(), media.get(0).readBytes());
	}

	@Autowired
	private ai.gebo.architecture.ai.files.IGConversationGeneratedFilesStore generatedFiles;
	@Autowired
	private ai.gebo.llms.chat.abstraction.layer.services.impl.ChatGeneratedFilesProvider generatedLocation;

	@Test
	public void aFileAToolOrAnAgentReturnedIsKeptAndFoundAmongTheConversationsFiles() throws Exception {
		final String chat = newSession("a chat whose tool returned a picture");
		final byte[] png = png();
		final ai.gebo.architecture.ai.files.GLibraryFile kept = generatedFiles.store(chat, "chart", "image/png", png);
		assertEquals("generated", kept.location());
		assertEquals("chart.png", kept.path(), "named with the extension of its type");
		assertEquals("image/png", kept.contentType());
		final ai.gebo.architecture.ai.files.GLibraryFile again = generatedFiles.store(chat, "chart.png", "image/png",
				png);
		assertEquals("chart (2).png", again.path(), "two files of one name stay apart");

		final ai.gebo.architecture.ai.files.IGFilesLocation location = generatedLocation.filesOf(chat);
		assertFalse(location.isWritable());
		assertEquals(List.of("chart.png", "chart (2).png"),
				location.list(null, false).stream().map(ai.gebo.architecture.ai.files.GLibraryFile::path).toList());
		org.junit.jupiter.api.Assertions.assertArrayEquals(png,
				location.read("chart.png").content().getInputStream().readAllBytes(), "the tools working on files open it");
		assertEquals(2, storageArea.getSessionMedia(chat).size(), "and a model that sees gets it on the next requests");
	}

	private static org.springframework.ai.chat.messages.UserMessage lastUserMessage(
			org.springframework.ai.chat.prompt.Prompt prompt) {
		org.springframework.ai.chat.messages.UserMessage last = null;
		for (org.springframework.ai.chat.messages.Message message : prompt.getInstructions()) {
			if (message instanceof org.springframework.ai.chat.messages.UserMessage user) {
				last = user;
			}
		}
		assertNotNull(last, "the prompt has a user message");
		return last;
	}

	private String newSession(String query) throws Exception {
		final GeboChatRequest first = new GeboChatRequest();
		first.setQuery(query);
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}

	@SuppressWarnings("rawtypes")
	private static ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig config(IGConfigurableChatModel model) {
		return (ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig) model.getConfig();
	}

	private static byte[] png() throws Exception {
		final BufferedImage image = new BufferedImage(4, 2, BufferedImage.TYPE_INT_RGB);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		return out.toByteArray();
	}
}

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelFilesDelivery;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.impl.RefusedFilesNotifier;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatAgentsToolsDescriber;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatUploadsController;
import ai.gebo.model.OperationStatus;
import ai.gebo.system.messages.model.GSystemMessage;
import ai.gebo.system.messages.services.IGSystemMessagesService;

/**
 * Certifies what happens when a model provider refuses the files of a request, over
 * the real path: a picture is uploaded to a chat whose model takes pictures, the
 * request reaches the model through the chat client of the platform, and the model
 * answers it with the error the client of a provider raises for a refused request.
 * The request is made again without the files and answered, the model is told which
 * files it did not get, the person asking and the administrators are told through the
 * system messages - whichever provider refuses, and also when the refusal comes after
 * a tool ran, on a thread that does not know the person.
 */
public class FilesAProviderRefusesIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String ANSWER = "GEBO_TEST_REFUSED_FILES_ANSWER: I could not see the chart.";
	private static final String TOOL = "refused_files_test_tool";
	private static final String LEFT_OUT = "could not be read by the model provider and were left out";
	/** The picture as the model and the messages name it: a file's name is cleaned for the providers. */
	private static final String PICTURE = "chart-png";

	@MockitoBean
	private IGChatAgentsToolsDescriber agentsToolsDescriber;
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
	@Autowired
	private IGSystemMessagesService systemMessages;

	/** The prompts the model was called with, in the order of the calls. */
	private final List<Prompt> prompts = new CopyOnWriteArrayList<>();
	/** What the provider answers a request carrying files, null when it takes them. */
	private final AtomicReference<RuntimeException> refusal = new AtomicReference<>();
	/** Whether the provider refuses the files only once a tool ran. */
	private final AtomicBoolean refusesAfterATool = new AtomicBoolean(false);
	/** Whether the model already asked for its tool, in the request under way. */
	private final AtomicBoolean toolAsked = new AtomicBoolean(false);
	private Map<GModelInputModality, GModelFilesDelivery.Limits> shippedLimits;

	@Override
	protected void beforeEachCallback() throws Exception {
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		when(agentsToolsDescriber.libraryTypeIds(any())).thenReturn(List.of());
		forgetWhatWasTold();
		prompts.clear();
		refusal.set(null);
		refusesAfterATool.set(false);
		toolAsked.set(true);
		TestChatModel.setGlobalPromptObserver(prompt -> {
			prompts.add(prompt);
			final RuntimeException refused = refusal.get();
			if (refused != null && carriesFiles(prompt) && (!refusesAfterATool.get() || aToolRan(prompt))) {
				throw refused;
			}
		});
		TestChatModel.setGlobalToolCallLogic(content -> toolAsked.compareAndSet(false, true)
				? List.of(new AssistantMessage.ToolCall("call-refused-files-1", "function", TOOL, "{}"))
				: null);
		TestChatModel.setGlobalResponseLogic(content -> ANSWER);
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalPromptObserver();
		TestChatModel.clearGlobalToolCallLogic();
		TestChatModel.clearGlobalResponseLogic();
		forgetWhatWasTold();
	}

	/** The installation ships with every kind of file off (application.yml): this test turns the kinds on. */
	@BeforeEach
	void filesAreSentToTheModels() {
		shippedLimits = GModelFilesDelivery.get();
		final Map<GModelInputModality, GModelFilesDelivery.Limits> on = new EnumMap<>(GModelInputModality.class);
		for (GModelInputModality kind : GModelInputModality.values()) {
			on.put(kind, GModelFilesDelivery.Limits.of(true, 4, 5, 10, 1000));
		}
		GModelFilesDelivery.set(on);
		config(answeringModel()).setInputModalities(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE));
	}

	@AfterEach
	void asShipped() {
		GModelFilesDelivery.set(shippedLimits);
		config(answeringModel()).setInputModalities(null);
	}

	@Test
	public void aRequestWhoseFilesTheProviderRefusesIsAnsweredAndThePersonAndTheAdministratorsAreTold()
			throws Exception {
		final IGConfigurableChatModel<?> model = answeringModel();
		final GeboChatRequest request = aRequestWithAPicture();
		final LLMChatRequestResources resources = lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		try {
			final IChatRequestContext context = resources.createChatRequestContext();
			// as Spring AI tells the refusal of a provider it calls over its own HTTP client
			refusal.set(new NonTransientAiException(
					"400 - {\"message\":\"GEBO_TEST_PROVIDER_WORDS: image format not supported\"}"));

			final String answer = model.textResponse(question(), Map.of(), context);

			assertEquals(ANSWER, answer, "the request is answered");
			assertEquals(2, prompts.size(), "by a second call");
			assertEquals(1, filesOf(prompts.get(0)), "the first carried the picture");
			assertEquals(0, filesOf(prompts.get(1)), "the second carries no file");
			assertTrue(prompts.get(1).getContents().contains(LEFT_OUT) && prompts.get(1).getContents().contains(PICTURE),
					"and tells the model which file it did not get: " + prompts.get(1).getContents());

			final List<GSystemMessage> toThePerson = told(DEFAULT_ALL_ROLES_USER, false);
			assertEquals(1, toThePerson.size(), "the person asking is told");
			assertTrue(toThePerson.get(0).getDetail().contains(PICTURE),
					"which file was left out: " + toThePerson.get(0).getDetail());
			assertEquals(List.of(DEFAULT_ALL_ROLES_USER), toThePerson.get(0).getRecipients(), "and nobody else");
			assertEquals(List.of(), told("another-person", false), "another person is told nothing");

			final List<GSystemMessage> toTheAdministrators = told("an-administrator", true);
			assertEquals(1, toTheAdministrators.size(), "the administrators are told");
			final String detail = toTheAdministrators.get(0).getDetail();
			assertTrue(detail.contains("image/png"), "the kind of file refused: " + detail);
			assertTrue(detail.contains("GEBO_TEST_PROVIDER_WORDS"), "in the provider's own words: " + detail);
			assertTrue(detail.contains(DEFAULT_ALL_ROLES_USER), "and whose request it was: " + detail);
			assertFalse(detail.contains(PICTURE), "not the names of the person's files: " + detail);

			// the same files refused again tell nobody twice: what was hidden stays hidden
			final long told = toThePerson.get(0).getRevision();
			prompts.clear();
			assertEquals(ANSWER, model.textResponse(question(), Map.of(), context));
			assertEquals(told, told(DEFAULT_ALL_ROLES_USER, false).get(0).getRevision(),
					"the message of the person is the one already told");
		} finally {
			lifeCycleService.releaseRequest(request);
		}
	}

	@Test
	public void theRefusalOfEveryProviderIsAnsweredWithoutTheFilesAndAnotherErrorIsNot() throws Exception {
		final IGConfigurableChatModel<?> model = answeringModel();
		final GeboChatRequest request = aRequestWithAPicture();
		final LLMChatRequestResources resources = lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		try {
			final IChatRequestContext context = resources.createChatRequestContext();

			// the errors the clients of the providers raise for a request they refuse
			final Map<String, RuntimeException> refusals = new LinkedHashMap<>();
			refusals.put("OpenAI, bad request",
					com.openai.errors.BadRequestException.builder()
							.headers(com.openai.core.http.Headers.builder().build()).build());
			refusals.put("OpenAI, too large", com.openai.errors.UnexpectedStatusCodeException.builder().statusCode(413)
					.headers(com.openai.core.http.Headers.builder().build()).build());
			refusals.put("Anthropic, bad request",
					com.anthropic.errors.BadRequestException.builder()
							.headers(com.anthropic.core.http.Headers.builder().build())
							.body(com.anthropic.core.JsonValue.from(Map.of("type", "error"))).build());
			refusals.put("Anthropic, too large",
					com.anthropic.errors.ContentTooLargeException.builder()
							.headers(com.anthropic.core.http.Headers.builder().build())
							.body(com.anthropic.core.JsonValue.from(Map.of("type", "error"))).build());
			refusals.put("Google, invalid argument",
					new com.google.genai.errors.ClientException(400, "INVALID_ARGUMENT", "Unsupported MIME type"));
			refusals.put("Bedrock, validation",
					(RuntimeException) software.amazon.awssdk.services.bedrockruntime.model.ValidationException.builder()
							.statusCode(400).message("The model does not support this file").build());
			refusals.put("Spring AI, bad request", new NonTransientAiException("400 - {\"message\":\"bad image\"}"));
			refusals.put("Spring AI, unprocessable", new NonTransientAiException("422 - {\"message\":\"bad image\"}"));
			for (Map.Entry<String, RuntimeException> refused : refusals.entrySet()) {
				prompts.clear();
				refusal.set(refused.getValue());
				assertEquals(ANSWER, model.textResponse(question(), Map.of(), context),
						refused.getKey() + ": the request is answered");
				assertEquals(2, prompts.size(), refused.getKey() + ": by a second call");
				assertEquals(0, filesOf(prompts.get(1)), refused.getKey() + ": which carries no file");
			}

			// the errors of a request that is not refused for what it carries: the request fails as it did
			final Map<String, RuntimeException> others = new LinkedHashMap<>();
			others.put("OpenAI, unauthorized",
					com.openai.errors.UnauthorizedException.builder()
							.headers(com.openai.core.http.Headers.builder().build()).build());
			others.put("Google, permission denied",
					new com.google.genai.errors.ClientException(403, "PERMISSION_DENIED", "No access to the model"));
			others.put("Bedrock, access denied",
					(RuntimeException) software.amazon.awssdk.services.bedrockruntime.model.AccessDeniedException
							.builder().statusCode(403).message("Not authorized").build());
			others.put("Spring AI, unauthorized", new NonTransientAiException("401 - {\"message\":\"bad key\"}"));
			for (Map.Entry<String, RuntimeException> other : others.entrySet()) {
				prompts.clear();
				refusal.set(other.getValue());
				assertThrows(Throwable.class, () -> model.textResponse(question(), Map.of(), context),
						other.getKey() + ": the request fails");
				assertEquals(1, prompts.size(), other.getKey() + ": and is not made again");
			}
		} finally {
			lifeCycleService.releaseRequest(request);
		}
	}

	@Test
	public void theRefusalAfterAToolRanOfAStreamedRequestTellsThePersonToo() throws Exception {
		final AtomicBoolean toolRan = new AtomicBoolean(false);
		final ToolCallback tool = FunctionToolCallback.builder(TOOL, (Map<String, Object> input) -> {
			toolRan.set(true);
			return "GEBO_TEST_REFUSED_FILES_TOOL_RESULT";
		}).description("A tool of the test of the refused files.").inputType(Map.class).build();
		final IGConfigurableChatModel<?> model = answeringModel().cloneWithOptions("refused-files-test-",
				new ChatModelConfigOptions(null, null, null, List.of(TOOL), List.of(tool)));
		final GeboChatRequest request = aRequestWithAPicture();
		final LLMChatRequestResources resources = lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		try {
			final IChatRequestContext context = resources.createChatRequestContext();
			// as the client of Google tells a refused request; the provider takes the picture
			// until a tool ran, then refuses it
			refusal.set(new com.google.genai.errors.ClientException(400, "INVALID_ARGUMENT", "Unsupported MIME type"));
			refusesAfterATool.set(true);
			toolAsked.set(false);

			final List<String> streamed = model.streamStringResponse(question(), Map.of(), context).collectList()
					.block(Duration.ofSeconds(60));

			assertNotNull(streamed);
			assertTrue(toolRan.get(), "the model called its tool");
			assertTrue(String.join("", streamed).contains(ANSWER), "the request is answered: " + streamed);
			final Prompt last = prompts.get(prompts.size() - 1);
			assertTrue(aToolRan(last), "by a call made after the tool ran");
			assertEquals(0, filesOf(last), "which carries no file");
			final List<GSystemMessage> toThePerson = told(DEFAULT_ALL_ROLES_USER, false);
			assertEquals(1, toThePerson.size(), "the person asking is told, though the call is made on another thread");
			assertTrue(toThePerson.get(0).getDetail().contains(PICTURE), toThePerson.get(0).getDetail());
			assertEquals(1, told("an-administrator", true).size(), "and the administrators are");
		} finally {
			lifeCycleService.releaseRequest(request);
		}
	}

	/** A request of a new chat with a picture uploaded to it. */
	private GeboChatRequest aRequestWithAPicture() throws Exception {
		final GeboChatRequest first = new GeboChatRequest();
		first.setQuery("a chat whose picture the provider refuses");
		lifeCycleService.createChatSession(first);
		final String chat = first.getUserChatContextCode();
		final OperationStatus<List<UserUploadedContent>> uploaded = uploadsController.chatSessionUpload(chat,
				List.of(new MockMultipartFile("files[]", "chart.png", "image/png", png())));
		assertNotNull(uploaded.getResult(), "a picture the chat's model takes is accepted");
		final GeboChatRequest request = new GeboChatRequest();
		request.setUserChatContextCode(chat);
		request.setQuery("What does the chart show?");
		request.setUserUploadedContents(List.of(uploaded.getResult().get(0)));
		return request;
	}

	private static GPromptTemplateConfig question() {
		final GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setSystemPromptTemplate("You answer questions about the files of the conversation.");
		prompt.setUserPromptTemplate("{question}");
		prompt.setContextDocuments(ContextContentRequired.NOT_REQUIRED);
		return prompt;
	}

	/** The messages of the refused files a person sees. */
	private List<GSystemMessage> told(String username, boolean administrator) {
		return systemMessages.findVisibleFor(username, administrator).stream()
				.filter(message -> RefusedFilesNotifier.MESSAGES_SOURCE.equals(message.getSource())).toList();
	}

	private void forgetWhatWasTold() {
		for (GSystemMessage message : systemMessages.findAll()) {
			if (RefusedFilesNotifier.MESSAGES_SOURCE.equals(message.getSource())) {
				systemMessages.retract(message.getSource(), message.getKey());
			}
		}
	}

	private static boolean carriesFiles(Prompt prompt) {
		return filesOf(prompt) > 0;
	}

	private static int filesOf(Prompt prompt) {
		int files = 0;
		for (Message message : prompt.getInstructions()) {
			if (message instanceof UserMessage user && user.getMedia() != null) {
				files += user.getMedia().size();
			}
		}
		return files;
	}

	private static boolean aToolRan(Prompt prompt) {
		return prompt.getInstructions().stream().anyMatch(ToolResponseMessage.class::isInstance);
	}

	@SuppressWarnings("rawtypes")
	private IGConfigurableChatModel answeringModel() {
		return chatModelRuntimeDao.defaultHandler();
	}

	@SuppressWarnings("rawtypes")
	private static GBaseChatModelConfig config(IGConfigurableChatModel model) {
		return (GBaseChatModelConfig) model.getConfig();
	}

	private static byte[] png() throws Exception {
		final BufferedImage image = new BufferedImage(4, 2, BufferedImage.TYPE_INT_RGB);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		return out.toByteArray();
	}
}

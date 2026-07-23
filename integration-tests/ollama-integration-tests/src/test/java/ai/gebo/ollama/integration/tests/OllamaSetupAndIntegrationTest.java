package ai.gebo.ollama.integration.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.RestClientException;
import org.testcontainers.qdrant.QdrantContainer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTests;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;
import ai.gebo.model.OperationStatus;
import ai.gebo.monolithic.api.client.api.AuthControllerApi;
import ai.gebo.monolithic.api.client.api.ChatModelsControllerApi;
import ai.gebo.monolithic.api.client.api.ChatModelsLookupControllerApi;
import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;
import ai.gebo.monolithic.api.client.api.GeboUserChatsControllerApi;
import ai.gebo.monolithic.api.client.api.TokenRenewControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.GLookupEntryRefGBaseChatModelConfig;
import ai.gebo.monolithic.api.client.model.GUserChatInfo;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.LoginRequest;
import ai.gebo.monolithic.api.client.model.OperationStatusAuthResponse;
import ai.gebo.monolithic.app.Main;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import lombok.Data;
import tools.jackson.core.JacksonException;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class OllamaSetupAndIntegrationTest extends AbstractGeboMonolithicIntegrationTests {

	static QdrantContainer qdrantContainer = new QdrantContainer("qdrant/qdrant:latest");
	static boolean qdrantStartedUp = false;
	static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	GeboVectorStoreConfigurationService vectorStoreConfigurationService;
	@Autowired
	ShrinkedChatSessionStateRepository shrinkedRepo;
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();
	@Autowired
	IGChatSessionLifeCycleService chatLifecycleService;
	@Autowired
	IGDocumentReferenceFactory docreferenceFactory;
	@Autowired
	IGDocumentReferenceIngestionHandler ingestionHandler;
	@Autowired
	IGChatService chatService;
	@Autowired
	IGPromptConfigDao promptsDao;
	@Autowired
	IGShrinkedChatSessionStateService shrinkedSessionService;
	@Autowired
	IGChatFullSessionStateService fullSessionService;
	@Autowired
	DocumentReferenceRepository documentsRepo;

	@BeforeEach
	protected void beforeEachCallback() throws Exception {
		if (!qdrantStartedUp) {
			qdrantContainer.setExposedPorts(List.of(6333, 6334));
			qdrantContainer.start();
			qdrantStartedUp = true;
		}
		GeboMongoVectorStoreConfig actualConfiguration = vectorStoreConfigurationService.getActualConfiguration();
		actualConfiguration.setProduct(VectorStoreProduct.QDRANT);
		actualConfiguration.setQdrantConfig(new QdrantConfig());
		actualConfiguration.getQdrantConfig().setHost("localhost");
		actualConfiguration.getQdrantConfig().setPort(qdrantContainer.getGrpcPort());
		actualConfiguration.getQdrantConfig().setTls(false);
		OperationStatus<GeboMongoVectorStoreConfig> result = vectorStoreConfigurationService
				.validateAndTestConfiguration(actualConfiguration);
		assertFalse(result.isHasErrorMessages(), "Change of vector store to qdrant have to be without errors");
		LOGGER.info("Change to qdrant vector store: " + mapper.writeValueAsString(result));
		vectorStoreConfigurationService.save(actualConfiguration);
	}

	@Override
	protected void enableWorkflowSteps(GKnowledgeBase kb, GProject project, GProjectEndpoint endpoint)
			throws GeboPersistenceException {

	}

	@Data
	public static class TChatInteraction {
		String user, assistant;
	}

	public static class TInteractions extends ArrayList<TChatInteraction> {

	}

	static final String[] INGESTION_FILES = new String[] { "/chat-sessions/files/The Secret Garden.pdf",
			"/chat-sessions/files/Schatzinsel_E.pdf", "/chat-sessions/files/the-story-of-doctor-dolittle.pdf" };
	public static final ObjectMapper MAPPER = JsonMapper.builder()
			.changeDefaultPropertyInclusion(v -> JsonInclude.Value.construct(Include.NON_NULL, Include.NON_NULL))
			.build();

	void showShrinked(GUserChatSession s) throws JacksonException {
		ShrinkedChatSessionState data = this.shrinkedSessionService.retrieveState(s);
		LOGGER.info(MAPPER.writeValueAsString(data));
	}

	@Test
	public void ollamaChatHistoryConsolidationTest() throws InterruptedException, StreamReadException,
			DatabindException, IOException, GeboPersistenceException, RestClientException, URISyntaxException,
			LLMConfigException, GeboContentHandlerSystemException, GeboIngestionException, GeboChatException {
		Thread.currentThread().sleep(60000);
		ApiClient apiClient = new ApiClient();
		AuthControllerApi controllerApi = new AuthControllerApi(apiClient);

		LoginRequest login = new LoginRequest();
		login.setUsername("mymail@gmail.com");
		login.setPassword("mypassword");
		OperationStatusAuthResponse result = controllerApi.authenticateUser(login);
		result.getMessages().forEach(x -> {
			LOGGER.info(x.getSummary() + " - " + x.getDetail());
		});
		assertFalse(result.isHasErrorMessages(), "Login cannot be with errors");
		InputStream chatResource = getClass().getResourceAsStream("/chat-sessions/history-consolidation-test.json");
		TInteractions interactions = mapper.readValue(chatResource, TInteractions.class);

		ApiClient authApiClient = new ApiClient();
		authApiClient.setApiKey(result.getResult().getSecurityHeaderData().getToken());
		TokenRenewControllerApi tokenRenewApi = new TokenRenewControllerApi(authApiClient);
		ChatModelsControllerApi chatModelsControllerApi = new ChatModelsControllerApi(authApiClient);

		ChatModelsLookupControllerApi chatmodelsLookupApi = new ChatModelsLookupControllerApi(authApiClient);
		List<GLookupEntryRefGBaseChatModelConfig> models = chatmodelsLookupApi.getRuntimeConfiguredChatModelsLookup(null);
		assertFalse(models.isEmpty(), "At least a default chat model must be configured");
		GLookupEntryRefGBaseChatModelConfig defaultModel = models.get(0);
		GeboUserChatsControllerApi userChatsAi = new GeboUserChatsControllerApi(authApiClient);
		GUserChatInfo cleanChat = userChatsAi.createCleanChatByModelCode(defaultModel.getCode());
		GeboChatControllerApi chatControllerApi = new GeboChatControllerApi(authApiClient);
		// load the created user context
		GUserChatSession data = persistentObjectManager.findById(GUserChatSession.class, cleanChat.getCode());
		// inject the false history
		IGConfigurableChatModel chatModel = this.chatModelRuntimeDao.defaultHandler();

		GPromptTemplateConfig prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.PROMPT_USE_STANDARD_CHAT_PROMPT);
		for (int i = 0; i < INGESTION_FILES.length; i++) {
			GeboChatRequest request = new GeboChatRequest();
			request.setStreamResponse(false);
			request.setQuery(interactions.get(i).getUser());
			request.setUserChatContextCode(cleanChat.getCode());
			request.setId(UUID.randomUUID().toString());
			this.chatLifecycleService.ensureChatSessionExists(request);
			ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse();
			response.setId(UUID.randomUUID().toString());
			response.setUserChatContextCode(data.getCode());
			response.setQuery(request.getQuery());
			response.setQueryResponse(interactions.get(i).getAssistant());
			String file = INGESTION_FILES[i];
			LOGGER.info("Ingesting and adding file:" + file);
			LLMChatRequestResources r = this.chatLifecycleService.startRequest(request, chatModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

			try (InputStream is = getClass().getResourceAsStream(file)) {
				if (is == null)
					throw new RuntimeException("No readable resource!!!");
				File tempfile = File.createTempFile("gebo-test", ".pdf");
				FileUtils.copyInputStreamToFile(is, tempfile);
				GDocumentReference ingested = this.docreferenceFactory
						.createReference(Path.of(tempfile.getAbsolutePath()));
				documentsRepo.insert(ingested);
				FileInputStream fis = new FileInputStream(tempfile);
				IngestionHandlerData ing = this.ingestionHandler.handleContent(ingested, fis);
				fis.close();
				if (!ing.isUnmanagedContent()) {
					AIDocumentsSet set = AIDocumentsSet.from(ing.getStream().toList());

					r = this.chatLifecycleService.addRetrievedDocuments(request, set, chatModel,
							LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);

					this.chatLifecycleService.endRequest(request, response);

					this.chatLifecycleService.chatRequestCompleted(request, chatModel);
					this.showShrinked(data);
				}
				ChatFullSessionState fullState = fullSessionService.retrieveState(data);
				boolean value = fullState.getRetrievedDocuments().getValue().getData().isEmpty();
				assertFalse(value, "The retrieve documents structure cannot be empty");
				ShrinkedChatSessionState shrinkedState = shrinkedSessionService.retrieveState(data);
				value = (!shrinkedState.getRelevantRetrievedDocuments().isEmpty()
						|| !shrinkedState.getLatestRequestsRetrievedDocuments().getData().isEmpty());
				assertTrue(value, "The latest and relevant retrieve documents structure cannot be both empty");

			}
			// request.setUserUploadedContents(rv.getResult());
			// GeboChatResponse response = chatControllerApi.chat(request);
			// LOGGER.info("Chat response:" + response.getQueryResponse());
			// newToken = tokenRenewApi.renew().getToken();
			// authApiClient.setApiKey(newToken);
			Thread.currentThread().sleep(10000);
		}
		int index = 1;
		List<ChatInteractions> _interactions = new ArrayList<>();
		for (TChatInteraction tChatInteraction : interactions) {
			LOGGER.info("Managing chat iteration #" + index);
			ChatInteractions interaction = new ChatInteractions();
			interaction.setRequest(new GeboChatRequest());
			interaction.getRequest().setQuery(tChatInteraction.getUser());
			interaction.getRequest().setUserChatContextCode(data.getCode());
			interaction.setRequestNTokens(tokenEstimator.estimate(tChatInteraction.getUser()));

			this.chatLifecycleService.startRequest(interaction.getRequest(), chatModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
			interaction.setResponse(new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse());
			interaction.getResponse().setQueryResponse(tChatInteraction.getAssistant());
			interaction.getResponse().setUserChatContextCode(data.getCode());
			interaction.setResponseNTokens(tokenEstimator.estimate(tChatInteraction.getAssistant()));
			_interactions.add(interaction);
			data.setInteractions(_interactions);
			persistentObjectManager.update(data);
			this.chatLifecycleService.endRequest(interaction.getRequest(),
					(ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse) interaction.getResponse());
			this.chatLifecycleService.chatRequestCompleted(interaction.getRequest(), chatModel);
			this.showShrinked(data);
			ChatFullSessionState fullState = fullSessionService.retrieveState(data);
			boolean value = fullState.getRetrievedDocuments().getValue().getData().isEmpty();
			assertFalse(value, "The retrieve documents structure cannot be empty");
			ShrinkedChatSessionState shrinkedState = shrinkedSessionService.retrieveState(data);
			value = (!shrinkedState.getRelevantRetrievedDocuments().isEmpty()
					|| !shrinkedState.getLatestRequestsRetrievedDocuments().getData().isEmpty());
			assertTrue(value, "The latest and relevant retrieve documents structure cannot be both empty");

			Thread.currentThread().sleep(10000);
			index++;

		}

		persistentObjectManager.update(data);

		int loopIndex = 0;
		ShrinkedChatSessionState shrinkedState = null;
		Optional<ShrinkedChatSessionState> opt = null;
		do {
			opt = shrinkedRepo.findById(data.getCode());
			if (opt.isPresent())
				break;
			Thread.currentThread().sleep(10000);
		} while (loopIndex < 10);
		opt = shrinkedRepo.findById(data.getCode());

		assertNotNull(opt.isEmpty(), "Shrinked state must be already being calculated");
		shrinkedState = opt.get();
		if (shrinkedState.getChatHistory() != null) {
			LOGGER.info("Consolidated text:" + shrinkedState.getChatHistory().getConsolidationText());
		}

		LOGGER.info("Shrinked state:" + shrinkedState);

	}

	@Test
	public void ollamaSyntheticChatHistoryConsolidationTest() throws InterruptedException, StreamReadException,
			DatabindException, IOException, GeboPersistenceException, RestClientException, URISyntaxException {
		Thread.currentThread().sleep(60000);
		ApiClient apiClient = new ApiClient();
		AuthControllerApi controllerApi = new AuthControllerApi(apiClient);

		LoginRequest login = new LoginRequest();
		login.setUsername("mymail@gmail.com");
		login.setPassword("mypassword");
		OperationStatusAuthResponse result = controllerApi.authenticateUser(login);
		result.getMessages().forEach(x -> {
			LOGGER.info(x.getSummary() + " - " + x.getDetail());
		});
		assertFalse( result.isHasErrorMessages(),"Login cannot be with errors");
		InputStream chatResource = getClass().getResourceAsStream("/chat-sessions/history-consolidation-test.json");
		TInteractions interactions = mapper.readValue(chatResource, TInteractions.class);

		ApiClient authApiClient = new ApiClient();
		authApiClient.setApiKey(result.getResult().getSecurityHeaderData().getToken());
		ChatModelsControllerApi chatModelsControllerApi = new ChatModelsControllerApi(authApiClient);

		ChatModelsLookupControllerApi chatmodelsLookupApi = new ChatModelsLookupControllerApi(authApiClient);
		List<GLookupEntryRefGBaseChatModelConfig> models = chatmodelsLookupApi.getRuntimeConfiguredChatModelsLookup(null);
		assertFalse(models.isEmpty(), "At least a default chat model must be configured");
		GLookupEntryRefGBaseChatModelConfig defaultModel = models.get(0);
		GeboChatControllerApi chatControllerApi = new GeboChatControllerApi(authApiClient);
		GeboUserChatsControllerApi userChatsAi = new GeboUserChatsControllerApi(authApiClient);
		GUserChatInfo cleanChat = userChatsAi.createCleanChatByModelCode(defaultModel.getCode());
		// load the created user context
		GUserChatSession data = persistentObjectManager.findById(GUserChatSession.class, cleanChat.getCode());
		// inject the false history
		List<ChatInteractions> _interactions = new ArrayList<>();
		for (TChatInteraction tChatInteraction : interactions) {
			ChatInteractions interaction = new ChatInteractions();
			interaction.setRequest(new GeboChatRequest());
			interaction.getRequest().setQuery(tChatInteraction.getUser());
			interaction.setRequestNTokens(tokenEstimator.estimate(tChatInteraction.getUser()));
			interaction.setResponse(new GeboTemplatedChatResponse<>());
			interaction.getResponse().setQueryResponse(tChatInteraction.getAssistant());
			interaction.setResponseNTokens(tokenEstimator.estimate(tChatInteraction.getAssistant()));
			_interactions.add(interaction);
		}
		data.setInteractions(_interactions);
		TokenRenewControllerApi tokenRenewApi = new TokenRenewControllerApi(authApiClient);
		String newToken = tokenRenewApi.renew().getToken();
		authApiClient.setApiKey(newToken);
		persistentObjectManager.update(data);

		for (int i = _interactions.size() - 3; i < _interactions.size(); i++) {
			ai.gebo.monolithic.api.client.model.GeboChatRequest request = new ai.gebo.monolithic.api.client.model.GeboChatRequest();
			request.setStreamResponse(false);
			request.setQuery(_interactions.get(i).getRequest().getQuery());
			request.setUserChatContextCode(cleanChat.getCode());
			request.setId(UUID.randomUUID().toString());
			// request.setUserUploadedContents(rv.getResult());
			GeboChatResponse response = chatControllerApi.chat(request);
			LOGGER.info("Chat response:" + response.getQueryResponse());
			newToken = tokenRenewApi.renew().getToken();
			authApiClient.setApiKey(newToken);
			Thread.currentThread().sleep(10000);
		}
		int loopIndex = 0;
		ShrinkedChatSessionState shrinkedState = null;
		Optional<ShrinkedChatSessionState> opt = null;
		do {
			opt = shrinkedRepo.findById(data.getCode());
			if (opt.isPresent())
				break;
			Thread.currentThread().sleep(10000);
		} while (loopIndex < 10);
		opt = shrinkedRepo.findById(data.getCode());

		assertNotNull(opt.isEmpty(), "Shrinked state must be already being calculated");
		shrinkedState = opt.get();
		if (shrinkedState.getChatHistory() != null) {
			LOGGER.info("Consolidated text:" + shrinkedState.getChatHistory().getConsolidationText());
		}

		LOGGER.info("Shrinked state:" + shrinkedState);

	}
}

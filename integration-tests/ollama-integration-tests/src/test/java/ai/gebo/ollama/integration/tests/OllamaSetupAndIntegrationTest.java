package ai.gebo.ollama.integration.tests;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.testcontainers.qdrant.QdrantContainer;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTests;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboTemplatedChatResponse;
import ai.gebo.llms.openai.services.OpenAIEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.monolithic.api.client.api.AuthControllerApi;
import ai.gebo.monolithic.api.client.api.GeboChatControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.GUserChatInfo;
import ai.gebo.monolithic.api.client.model.GeboChatResponse;
import ai.gebo.monolithic.api.client.model.LoginRequest;
import ai.gebo.monolithic.api.client.model.OperationStatusAuthResponse;
import ai.gebo.monolithic.app.Main;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;
import lombok.Data;

@SpringBootTest(classes = Main.class,webEnvironment = WebEnvironment.DEFINED_PORT)
public class OllamaSetupAndIntegrationTest extends AbstractGeboMonolithicIntegrationTests {

	static QdrantContainer qdrantContainer = new QdrantContainer("qdrant/qdrant:latest");
	static boolean qdrantStartedUp = false;
	static final ObjectMapper mapper = new ObjectMapper();
	@Autowired
	GeboVectorStoreConfigurationService vectorStoreConfigurationService;
	@Autowired
	IGChatModelRuntimeConfigurationDao chatModelsRuntimeDao;
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

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
		assertFalse("Change of vector store to qdrant have to be without errors", result.isHasErrorMessages());
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

	@Test
	public void ollamaChatHistoryConsolidationTest()
			throws InterruptedException, StreamReadException, DatabindException, IOException, GeboPersistenceException {
		Thread.currentThread().sleep(40000);
		ApiClient apiClient = new ApiClient();
		AuthControllerApi controllerApi = new AuthControllerApi(apiClient);

		LoginRequest login = new LoginRequest();
		login.setUsername("mymail@gmail.com");
		login.setPassword("mypassword");
		OperationStatusAuthResponse result = controllerApi.authenticateUser(login);
		result.getMessages().forEach(x -> {
			LOGGER.info(x.getSummary() + " - " + x.getDetail());
		});
		assertFalse("Login cannot be with errors", result.isHasErrorMessages());
		InputStream chatResource = getClass().getResourceAsStream("/chat-sessions/history-consolidation-test.json");
		TInteractions interactions = mapper.readValue(chatResource, TInteractions.class);
		IGConfigurableChatModel defaultModel = chatModelsRuntimeDao.defaultHandler();
		ApiClient authApiClient = new ApiClient();
		apiClient.setApiKey(result.getResult().getSecurityHeaderData().getToken());
		GeboChatControllerApi chatControllerApi = new GeboChatControllerApi(authApiClient);
		GUserChatInfo cleanChat = chatControllerApi.createCleanChatByModelCode(defaultModel.getCode());
		// load the created user context
		GUserChatContext data = persistentObjectManager.findById(GUserChatContext.class, cleanChat.getCode());
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
		persistentObjectManager.update(data);
		for (int i = _interactions.size() - 3; i < _interactions.size(); i++) {
			ai.gebo.monolithic.api.client.model.GeboChatRequest request = new ai.gebo.monolithic.api.client.model.GeboChatRequest();
			request.setStreamResponse(false);
			request.setQuery(_interactions.get(i).getRequest().getQuery());
			request.setUserChatContextCode(cleanChat.getCode());
			request.setId(UUID.randomUUID().toString());
			GeboChatResponse response = chatControllerApi.chat(request);
			LOGGER.info("Chat response:" + response.getQueryResponse());
		}

	}

}

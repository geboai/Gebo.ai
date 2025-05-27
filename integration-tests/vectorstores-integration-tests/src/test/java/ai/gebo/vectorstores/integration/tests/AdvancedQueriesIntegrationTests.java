/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.vectorstores.integration.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.api.OpenAiApi.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.qdrant.QdrantContainer;

import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint.TestEndpointType;
import ai.gebo.architecture.integration.tests.AbstractGeboMonolithicIntegrationTestsWithFakeLLMS;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.llms.openai.model.GOpenAIEmbeddingModelChoice;
import ai.gebo.llms.openai.model.GOpenAIEmbeddingModelConfig;
import ai.gebo.llms.openai.services.OpenAIEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.monolithic.app.Main;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;
import ai.gebo.security.repository.UserRepository.UserInfos;

@SpringBootTest(classes = Main.class)
public class AdvancedQueriesIntegrationTests extends AbstractGeboMonolithicIntegrationTestsWithFakeLLMS {
	static QdrantContainer qdrantContainer = new QdrantContainer("qdrant/qdrant:v1.13.2");
	static boolean qdrantStartedUp = false;
	@Autowired
	GeboVectorStoreConfigurationService vectorStoreConfigurationService;
	@Autowired
	OpenAIEmbeddingModelConfigurationSupportService embeddingSupportService;
	@Autowired
	VectorizedContentRepository vectorizatedContents;
	@Autowired
	IGRagDocumentsCachedDao ragDocumentsCachedDao;

	@Override
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
		IGConfigurableEmbeddingModel defaultHandler = embeddingModelRuntimeDao.defaultHandler();
		embeddingModelRuntimeDao.deleteByCode(defaultHandler.getCode());

		String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
		String OPENAI_USER_NAME = System.getenv("OPENAI_USER");
		if (OPENAI_API_KEY == null || OPENAI_USER_NAME == null)
			throw new IllegalStateException("OPENAI_API_KEY and OPENAI_USER must be set");
		GOpenAIEmbeddingModelConfig openaiEmbeddingModelConfig = new GOpenAIEmbeddingModelConfig();
		String secretCode = registerSecurityToken(OPENAI_API_KEY, OPENAI_USER_NAME, "OpenAI secret code", "OPENAI");
		openaiEmbeddingModelConfig.setApiSecretCode(secretCode);
		openaiEmbeddingModelConfig.setDescription("Openai default embedding model");
		OperationStatus<List<GOpenAIEmbeddingModelChoice>> choices = embeddingSupportService
				.getModelChoices(openaiEmbeddingModelConfig);
		assertFalse("Openai models lookup must be ok", choices.isHasErrorMessages());
		Optional<GOpenAIEmbeddingModelChoice> bestModel = choices.getResult().stream()
				.filter(x -> x.getCode().startsWith(EmbeddingModel.TEXT_EMBEDDING_3_LARGE.value)).findFirst();
		assertTrue("Openai model setup correctly", bestModel.isPresent());
		GOpenAIEmbeddingModelChoice model = bestModel.get();
		openaiEmbeddingModelConfig.setChoosedModel(model);
		openaiEmbeddingModelConfig.setDefaultModel(true);
		openaiEmbeddingModelConfig.setModelTypeCode(embeddingSupportService.getType().getCode());
		openaiEmbeddingModelConfig = persistentObjectManager.insert(openaiEmbeddingModelConfig);
		embeddingModelRuntimeDao.addRuntimeByConfig(openaiEmbeddingModelConfig);
		LOGGER.info("Setup on openai embedding");

	}

	static final List<String> RESOURCES_TO_EMBED = List.of("TESTFILES/Vangeli-non-canonici.pdf",
			"TESTFILES/esoterismo-e-massoneria.pdf");
	static final String SEMANTIC_REQUEST = "chi non fara del bene non entrera nel regno dei cieli";

	@Test
	public void embedAndAdvancedQueries() throws InstantiationException, IllegalAccessException,
			GeboPersistenceException, IOException, GeboJobServiceException, InterruptedException {
		TestProjectEndpoint endpoint = createAndPersist("Italian full non apocrife ghospel texts",
				TestProjectEndpoint.class);
		endpoint.setTestType(TestEndpointType.PATH_CONTENTS);
		List<String> paths = new ArrayList<String>();
		for (String classpathEntry : RESOURCES_TO_EMBED) {
			Path path = extractResource(classpathEntry, ".pdf");
			paths.add(path.toAbsolutePath().toString());
		}
		endpoint.setTestFilesystemPaths(paths);
		endpoint = persistentObjectManager.update(endpoint);
		runAndWaitDoneCheckingResults(endpoint, false);
		GProject pj = getProject(endpoint);
		List<String> knowledgeBases = List.of(pj.getRootKnowledgeBaseCode());
		IGConfigurableEmbeddingModel openaiDefaultEmbeddingModel = embeddingModelRuntimeDao.defaultHandler();
		VectorStore vectorStore = openaiDefaultEmbeddingModel.getVectorStore();
		Stream<GVectorizedContent> vectorizatedStream = vectorizatedContents.findByProjectEndpoint(endpoint);
		List<GVectorizedContent> vectorizated = vectorizatedStream.toList();
		assertFalse("Vectorizated list cannot be empty", vectorizated.isEmpty());
		LOGGER.info("Vectorized infos " + mapper.writeValueAsString(vectorizated));
		RagDocumentsCachedDaoResult results = ragDocumentsCachedDao.semanticSearch(SEMANTIC_REQUEST, knowledgeBases,
				openaiDefaultEmbeddingModel, getDefaultUserInfos());
		assertFalse("I risultati della ricerca di test non possono essere vuoti", results.getDocumentItems().isEmpty());
		assertFalse("Non puo essere 0 byte la ricerca di prova ", results.getNBytes() == 0);
		assertFalse("Non puo essere 0 tokens la ricerca di prova ", results.getNTokens() == 0);
		RagQueryOptions options = new RagQueryOptions(0, CompletenessLevel.STRICT_QUERY_RELATED, 12, -1);
		RagDocumentsCachedDaoResult results1 = ragDocumentsCachedDao.semanticSearch(SEMANTIC_REQUEST, options,
				knowledgeBases, openaiDefaultEmbeddingModel, getDefaultUserInfos());
		assertFalse("I risultati della ricerca di test non possono essere vuoti",
				results1.getDocumentItems().isEmpty());
		assertFalse("Non puo essere 0 byte la ricerca di prova ", results1.getNBytes() == 0);
		assertFalse("Non puo essere 0 tokens la ricerca di prova ", results1.getNTokens() == 0);

	}

	@Override
	protected void afterEachCallback() throws Exception {

	}

}

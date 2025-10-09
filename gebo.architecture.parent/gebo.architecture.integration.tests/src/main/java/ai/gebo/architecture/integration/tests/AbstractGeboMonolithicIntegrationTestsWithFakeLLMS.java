/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.integration.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import com.fasterxml.jackson.core.JsonProcessingException;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.architecture.graphrag.extraction.model.EntityObject;
import ai.gebo.architecture.graphrag.extraction.model.EventObject;
import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.WorkflowStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.tests.KnowledgeExtractionCallEvent;
import ai.gebo.llms.abstraction.layer.tests.TestChatModelConfiguration;
import ai.gebo.llms.abstraction.layer.tests.TestChatModelSupportServiceImpl;
import ai.gebo.llms.abstraction.layer.tests.TestEmbeddingModelConfiguration;
import ai.gebo.llms.abstraction.layer.tests.TestEmbeddingModelSupportServiceImpl;
import ai.gebo.llms.abstraction.layer.tests.TestKnowledgeExtractionChatModelSupportServiceImpl;
import ai.gebo.llms.abstraction.layer.tests.TestKnowledgeExtractionModelConfiguration;
import ai.gebo.llms.abstraction.layer.vectorstores.GAccountingExtendedVectorStoreAdapter;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.ragsystem.vectorstores.test.services.TestVectorStore;

/**
 * AI generated comments
 * 
 * An abstract test class that provides integration tests for GEBO architecture
 * with fake LLMS. It handles preparation and cleanup of environment and manages
 * lifecycle of chat and embedding models for integration tests.
 */
public abstract class AbstractGeboMonolithicIntegrationTestsWithFakeLLMS
		extends AbstractGeboMonolithicIntegrationTests {

	// Default test chat model code
	public static final String DEFAULT_TEST_CHAT_MODEL_CODE = "TEST_CHAT_MODEL_X001";
	// Default test embedding model code
	public static final String DEFAULT_TEST_EMBEDDING_MODEL_CODE = "TEST_EMBEDDING_MODEL_X001";

	public static final String DEFAULT_KNOWLEDGE_EXTRACTION_CHAT_MODEL_CODE = "DEFAULT_KNOWLEDGE_EXTRACTION_CHAT_MODEL_CODE_001";

	/**
	 * Callback method to be executed before each test. Can be overridden by
	 * subclasses to perform operations before each test.
	 * 
	 * @throws Exception if any error occurs during setup
	 */
	protected void beforeEachCallback() throws Exception {
	}

	/**
	 * Callback method to be executed after each test. Can be overridden by
	 * subclasses to perform cleanup operations after each test.
	 * 
	 * @throws Exception if any error occurs during cleanup
	 */
	protected void afterEachCallback() throws Exception {
	}

	/**
	 * Retrieves the test vector store used for managing and accessing embedded
	 * document vectors.
	 * 
	 * @return the test vector store instance
	 */
	protected TestVectorStore getTestVectorStore() {
		IGConfigurableEmbeddingModel embeddingModel = embeddingModelRuntimeDao
				.findByCode(DEFAULT_TEST_EMBEDDING_MODEL_CODE);
		VectorStore vectorStore = embeddingModel.getVectorStore();
		assert vectorStore instanceof GAccountingExtendedVectorStoreAdapter;
		GAccountingExtendedVectorStoreAdapter usedVectorStore = (GAccountingExtendedVectorStoreAdapter) vectorStore;
		assert usedVectorStore.getWrapped() instanceof TestVectorStore;
		TestVectorStore usedTestVectorStore = (TestVectorStore) usedVectorStore.getWrapped();
		return usedTestVectorStore;
	}

	/**
	 * Prepares the test environment by initializing chat and embedding models
	 * before each test.
	 * 
	 * @throws Exception if any error occurs during preparation
	 */
	@BeforeEach
	public void prepareEnvironment() throws Exception {
		LOGGER.info("Start initializing chat & embedding model");
		LOGGER.info("GEBO_HOME=" + HOME.toString());
		LOGGER.info("GEBO_WORKDIRECTORY=" + HOME.toString());
		cleanAllDb();
		// Prepare a default chat model configuration
		TestChatModelConfiguration testChatModelConfiguration = new TestChatModelConfiguration();
		testChatModelConfiguration
				.setModelTypeCode(TestChatModelSupportServiceImpl.TEST_CONFIGURABLE_CHAT_MODEL_SERVICE);
		testChatModelConfiguration.setChoosedModel(new GBaseModelChoice());
		testChatModelConfiguration.getChoosedModel().setCode(TestChatModelSupportServiceImpl.TEST_MODEL_001);
		testChatModelConfiguration.setDefaultModel(true);
		testChatModelConfiguration.setCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		chatModelRuntimeDao.addRuntimeByConfig(testChatModelConfiguration);
		// Prepare an embedding model configuration
		TestEmbeddingModelConfiguration embedModelConfig = new TestEmbeddingModelConfiguration();
		embedModelConfig.setCode(DEFAULT_TEST_EMBEDDING_MODEL_CODE);
		embedModelConfig.setModelTypeCode(TestEmbeddingModelSupportServiceImpl.TEST_EMBEDDING_MODEL);
		embedModelConfig.setChoosedModel(new GBaseModelChoice());
		embedModelConfig.getChoosedModel().setCode(TestEmbeddingModelSupportServiceImpl.TEST_EMBEDDING_MODEL_001);
		embedModelConfig.setDefaultModel(true);
		embeddingModelRuntimeDao.addRuntimeByConfig(embedModelConfig);
		// Prepare a knowledge extraction (IE) model configuration with default fixed
		// extraction data
		TestKnowledgeExtractionModelConfiguration knowledgeExtractionChatModelConfig = new TestKnowledgeExtractionModelConfiguration();
		knowledgeExtractionChatModelConfig.setModelTypeCode(
				TestKnowledgeExtractionChatModelSupportServiceImpl.TEST_CONFIGURABLE_KNOWLEDGE_EXTRACTION_CHAT_MODEL_SERVICE);
		knowledgeExtractionChatModelConfig.setChoosedModel(new GBaseModelChoice());
		knowledgeExtractionChatModelConfig.getChoosedModel()
				.setCode(TestKnowledgeExtractionChatModelSupportServiceImpl.TEST_KNOWLEDGE_EXTRACTION_MODEL_001);
		knowledgeExtractionChatModelConfig.setDefaultModel(false);
		knowledgeExtractionChatModelConfig.setCode(DEFAULT_KNOWLEDGE_EXTRACTION_CHAT_MODEL_CODE);
		// LLMExtractionResult extractionResult = this.createGraphragExtractionSample();
		// knowledgeExtractionChatModelConfig.getResponseObjects().put(LLMExtractionResult.class,
		// extractionResult);
		knowledgeExtractionChatModelConfig.getResponseCallbacks().put(LLMExtractionResult.class,
				(KnowledgeExtractionCallEvent event) -> this.createGraphragExtractionSample(event));
		chatModelRuntimeDao.addRuntimeByConfig(knowledgeExtractionChatModelConfig);
		GraphRagExtractionConfig graphragConfig = new GraphRagExtractionConfig();
		graphragConfig.setExtractionPrompt("This is a knowledge extraction prompt \n\n${format}");
		graphragConfig.setDefaultConfiguration(true);
		graphragConfig.setUsedModelConfiguration(GObjectRef.of(knowledgeExtractionChatModelConfig));
		graphragConfig.setDescription("Default knowledge extraction model");
		persistentObjectManager.insert(graphragConfig);
		createDefaultUser();
		beforeEachCallback();
		LOGGER.info("End initializing chat & embedding model");
	}

	@Override
	protected void enableWorkflowSteps(GKnowledgeBase kb, GProject project, GProjectEndpoint endpoint)
			throws GeboPersistenceException {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_KNOWLEDGE_EXTRACTION_CHAT_MODEL_CODE);
		GraphRagExtractionConfig graphragConfig = new GraphRagExtractionConfig();
		graphragConfig.setExtractionPrompt("This is a knowledge extraction prompt \n\n${format}");
		graphragConfig.setDefaultConfiguration(true);
		graphragConfig.setUsedModelConfiguration(GObjectRef.of((GBaseChatModelConfig) model.getConfig()));
		graphragConfig.setDescription("Default knowledge extraction model");
		graphragConfig.setKnowledgeBaseCode(kb.getCode());
		graphragConfig.setProjectCode(project.getCode());
		graphragConfig.setEndpoint(GObjectRef.of(endpoint));
		persistentObjectManager.insert(graphragConfig);
	}

	private static final String START_CHUNK_ID = "[CHUNK-ID]";
	private static final String END_CHUNK_ID = "[/CHUNK-ID]";

	private LLMExtractionResult createGraphragExtractionSample(KnowledgeExtractionCallEvent event) {

		List<String> documentIds = event.getMessages().stream().map(x -> {
			if (x.getText() != null) {
				String text = x.getText();
				int startIndex = text.indexOf(START_CHUNK_ID);
				int endIndex = text.indexOf(END_CHUNK_ID);
				if (startIndex >= 0 && endIndex >= 0) {
					String id = text.substring(startIndex + START_CHUNK_ID.length(), endIndex);
					return id;
				} else
					return null;
			} else {
				return null;
			}
		}).filter(y -> y != null).toList();
		LLMExtractionResult sample = createGraphragExtractionSample(documentIds);
		return sample;
	}

	private LLMExtractionResult createGraphragExtractionSample() {
		return this.createGraphragExtractionSample(List.of());
	}

	private LLMExtractionResult createGraphragExtractionSample(List<String> ids) {
		LLMExtractionResult data = new LLMExtractionResult();
		EntityObject entity = new EntityObject();
		entity.setType("PRODUCT");
		entity.setName("Yamaha XTZ");
		entity.setConfidence(0.9);
		entity.setLongDescription("Yamaha XTZ motorbike");
		entity.getChunkIds().addAll(ids);
		data.getEntities().add(entity);
		EntityObject entity1 = new EntityObject();
		entity1.setType("PRODUCT");
		entity1.setName("Citroen pallas");
		entity1.setConfidence(0.9);
		entity1.setLongDescription("Citroen pallas car");
		entity1.getChunkIds().addAll(ids);
		data.getEntities().add(entity1);
		EventObject event = new EventObject();
		event.setParticipantEntities(List.of(entity, entity1));
		event.setTitle("Collision on the road");
		event.setType("ACCIDENT");
		event.getChunkIds().addAll(ids);
		data.getEvents().add(event);
		return data;
	}

	/**
	 * Resets the test environment by clearing chat and embedding models after each
	 * test.
	 * 
	 * @throws Exception if any error occurs during reset
	 */
	@AfterEach
	public void resetEnvironment() throws Exception {
		LOGGER.info("Start clearing chat & embedding model");
		IGConfigurableChatModel chatModel = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		if (chatModel != null) {
			chatModel.delete();
			chatModelRuntimeDao.deleteByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		}
		IGConfigurableEmbeddingModel embeddingModel = embeddingModelRuntimeDao
				.findByCode(DEFAULT_TEST_EMBEDDING_MODEL_CODE);
		if (embeddingModel != null) {
			embeddingModel.delete();
			embeddingModelRuntimeDao.deleteByCode(DEFAULT_TEST_EMBEDDING_MODEL_CODE);
		}
		statusRepo.deleteAll();
		afterEachCallback();
		cleanAllDb();
		LOGGER.info("End clearing chat & embedding model");
	}

	/**
	 * Logs the contents of the virtual filesystem, including documents and folders.
	 * 
	 * @throws GeboPersistenceException if any persistence error occurs
	 */
	protected void logVirtualFilesystemContent() throws GeboPersistenceException {
		List<GDocumentReference> allDocs = persistentObjectManager.findAll(GDocumentReference.class);
		for (GDocumentReference doc : allDocs) {
			LOGGER.info("Virtual Document:[deleted=" + doc.getDeleted() + "] code:" + doc.getCode());
		}
		List<GVirtualFolder> allfolders = persistentObjectManager.findAll(GVirtualFolder.class);
		for (GVirtualFolder fold : allfolders) {
			LOGGER.info("Virtual Folder:[deleted=" + fold.getDeleted() + "] code:" + fold.getCode());
		}
	}

	/**
	 * Runs a job, waits for completion, and checks the results. Ensures that the
	 * expected number of documents are vectorized and verifies the integrity of the
	 * vector store.
	 *
	 * @param endpoint                       the project endpoint to execute
	 * @param howManyFilesWait               the expected number of files to wait
	 *                                       for vectorization
	 * @param checkVectorDeletionNotOccurred flag to check vector deletion
	 * @throws GeboJobServiceException  if a job service error occurs
	 * @throws GeboPersistenceException if a persistence error occurs
	 * @throws JsonProcessingException  if a JSON processing error occurs
	 * @throws InterruptedException     if the thread is interrupted
	 */
	protected void runAndWaitDoneCheckingResults(GProjectEndpoint endpoint, long howManyFilesWait,
			boolean checkVectorDeletionNotOccurred)
			throws JsonProcessingException, GeboJobServiceException, GeboPersistenceException, InterruptedException {
		this.runAndWaitDoneCheckingResults(endpoint, howManyFilesWait, checkVectorDeletionNotOccurred, 20);
	}

	/**
	 * Runs a job, waits for completion, and checks the results. Ensures that the
	 * expected number of documents are vectorized and verifies the integrity of the
	 * vector store.
	 *
	 * @param endpoint                       the project endpoint to execute
	 * @param howManyFilesWait               the expected number of files to wait
	 *                                       for vectorization
	 * @param checkVectorDeletionNotOccurred flag to check vector deletion
	 * @throws GeboJobServiceException  if a job service error occurs
	 * @throws GeboPersistenceException if a persistence error occurs
	 * @throws JsonProcessingException  if a JSON processing error occurs
	 * @throws InterruptedException     if the thread is interrupted
	 */
	protected void runAndWaitDoneCheckingResults(GProjectEndpoint endpoint, long howManyFilesWait,
			boolean checkVectorDeletionNotOccurred, int NMAXCYCLES)
			throws GeboJobServiceException, GeboPersistenceException, JsonProcessingException, InterruptedException {
		GJobStatus syncJobStatus = ingestionJobService.executeSyncJob(endpoint, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name());
		JobSummary summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
		// Maximum number of cycles to wait for job completion
		int nCycles = 0;
		Thread.sleep(20000); // Initial delay before starting polling loop

		do {
			Thread.sleep(10000); // Interval between job status checks
			summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
			LOGGER.info("On cycle=>" + nCycles);
			// LOGGER.info("Summary=" + mapper.writeValueAsString(summary));
			nCycles++;
			printSummary(summary);
		} while ((!(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished()))
				&& nCycles < NMAXCYCLES);
		summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
		LOGGER.info("Summary=" + mapper.writeValueAsString(summary));
		assertTrue(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished(),
				"Contents read have to be terminated in this point");
		// assertTrue(summary.getVectorizationTerminated(), "Vectorization have to be
		// terminated in this point");

		// assertEquals(howManyFilesWait,
		// summary.getCurrentBatchDocumentVectorizedCounter(),
		// "Expected a single resource to be vectorized");

		TestVectorStore usedTestVectorStore = getTestVectorStore();

		Map<Object, List<Document>> splittedForCodes = usedTestVectorStore
				.getStoredForMetaAttribute(DocumentMetaInfos.CONTENT_CODE);
		Assertions.assertFalse(splittedForCodes.isEmpty(), "The vector store has received 0 values splitted by code");
		Assertions.assertEquals(splittedForCodes.size(), howManyFilesWait,
				"The number of received files must be equal to the expected value");
		List<Document> allData = usedTestVectorStore.getAllData();
		Assertions.assertFalse(allData.isEmpty(), "All embedded entries cannot be empty");
		long withoutMetaData = allData.stream().map(x -> x.getMetadata() == null || x.getMetadata().isEmpty()).count();
		Assertions.assertFalse(withoutMetaData == 0l, "AI documents without metadata cannot be present");
		List<ExtractedDocumentMetaData> extractedMetaData = allData.stream().map(x -> {
			return ExtractedDocumentMetaData.of(x.getMetadata());
		}).toList();

		List<ExtractedDocumentMetaData> listwithoutMetaData = extractedMetaData.stream()
				.filter(x -> !x.isEnrichedWithMetaInfo()).toList();
		for (ExtractedDocumentMetaData extractedDocumentMetaData : listwithoutMetaData) {
			LOGGER.error("The document=>" + extractedDocumentMetaData.getCode()
					+ " has not being enriched with metadata header");
		}
		Assertions.assertTrue(listwithoutMetaData.isEmpty(), "There are AI documents without meta header");
		if (checkVectorDeletionNotOccurred) {
			Assertions.assertTrue(usedTestVectorStore.getDeletedDocumentIds().isEmpty(),
					"This use case have not lead to deleted vectors");
		}
		logVirtualFilesystemContent();

	}

	/**
	 * Overloaded method: runs a job and waits for it to be done, checking the
	 * results.
	 * 
	 * @param endpoint the project endpoint to execute
	 * @throws GeboJobServiceException  if a job service error occurs
	 * @throws GeboPersistenceException if a persistence error occurs
	 * @throws JsonProcessingException  if a JSON processing error occurs
	 * @throws InterruptedException     if the thread is interrupted
	 */
	protected void runAndWaitDoneCheckingResults(GProjectEndpoint endpoint)
			throws GeboJobServiceException, GeboPersistenceException, JsonProcessingException, InterruptedException {
		runAndWaitDoneCheckingResults(endpoint, true, true, 20);
	}

	/**
	 * Overloaded method: runs a job and waits for it to be done, checking the
	 * results with configurable vector store checking.
	 * 
	 * @param endpoint             the project endpoint to execute
	 * @param checkTestVectorStore flag to check test vector store
	 * @throws JsonProcessingException  if a JSON processing error occurs
	 * @throws GeboJobServiceException  if a job service error occurs
	 * @throws GeboPersistenceException if a persistence error occurs
	 * @throws InterruptedException     if the thread is interrupted
	 */
	protected void runAndWaitDoneCheckingResults(GProjectEndpoint endpoint, boolean checkTestVectorStore)
			throws JsonProcessingException, GeboJobServiceException, GeboPersistenceException, InterruptedException {
		runAndWaitDoneCheckingResults(endpoint, checkTestVectorStore, true, 20);
	}

	/**
	 * Overloaded method: runs a job and waits for it to be done, checking the
	 * results with configurable vector store and deletion checking.
	 * 
	 * @param endpoint               the project endpoint to execute
	 * @param checkTestVectorStore   flag to check test vector store
	 * @param checkNodDeletedVectors flag to check that no vectors were deleted
	 * @throws JsonProcessingException  if a JSON processing error occurs
	 * @throws GeboJobServiceException  if a job service error occurs
	 * @throws GeboPersistenceException if a persistence error occurs
	 * @throws InterruptedException     if the thread is interrupted
	 */
	protected void runAndWaitDoneCheckingResults(GProjectEndpoint endpoint, boolean checkTestVectorStore,
			boolean checkNodDeletedVectors)
			throws JsonProcessingException, GeboJobServiceException, GeboPersistenceException, InterruptedException {
		runAndWaitDoneCheckingResults(endpoint, checkTestVectorStore, checkNodDeletedVectors, 20);
	}

	/**
	 * Overloaded method: runs a job and waits for it to be done, checking the
	 * results with configurable cycles for checking.
	 * 
	 * @param endpoint                       the project endpoint to execute
	 * @param checkTestVectorStore           flag to check test vector store
	 * @param checkVectorDeletionNotOccurred flag to check vector deletion
	 * @param NMAXCYCLES                     maximum number of checking cycles
	 * @throws GeboJobServiceException  if a job service error occurs
	 * @throws GeboPersistenceException if a persistence error occurs
	 * @throws JsonProcessingException  if a JSON processing error occurs
	 * @throws InterruptedException     if the thread is interrupted
	 */
	protected void runAndWaitDoneCheckingResults(GProjectEndpoint endpoint, boolean checkTestVectorStore,
			boolean checkVectorDeletionNotOccurred, int NMAXCYCLES)
			throws GeboJobServiceException, GeboPersistenceException, JsonProcessingException, InterruptedException {
		GJobStatus syncJobStatus = ingestionJobService.executeSyncJob(endpoint, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name());
		JobSummary summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());

		int nCycles = 0;
		do {
			Thread.sleep(10000);
			summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
			LOGGER.info("On cycle=>" + nCycles);
			printSummary(summary);

			nCycles++;
			if (checkTestVectorStore) {
				TestVectorStore usedTestVectorStore = getTestVectorStore();
				List<String> vectorizedDocuments = usedTestVectorStore.getHandledGeboDocumentCodes();
				LOGGER.info("RESULTING_VECTORIZED-DOCUMENTS:" + vectorizedDocuments);
			}

		} while (!(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished())
				&& nCycles < NMAXCYCLES);
		summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
		LOGGER.info("Summary=" + mapper.writeValueAsString(summary));
		assertTrue((summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isFinished()),
				"Contents read have to be terminated in this point");
		// assertTrue(summary.getVectorizationTerminated(), "Vectorization have to be
		// terminated in this point");
		if (checkTestVectorStore) {
			TestVectorStore usedTestVectorStore = getTestVectorStore();

			Map<Object, List<Document>> splittedForCodes = usedTestVectorStore
					.getStoredForMetaAttribute(DocumentMetaInfos.CONTENT_CODE);
			Assertions.assertFalse(splittedForCodes.isEmpty(),
					"The vector store has received 0 values splitted by code");
			List<Document> allData = usedTestVectorStore.getAllData();
			List<String> vectorizedDocuments = usedTestVectorStore.getHandledGeboDocumentCodes();
			LOGGER.info("RESULTING_VECTORIZED-DOCUMENTS:" + vectorizedDocuments);
			Assertions.assertFalse(allData.isEmpty(), "All embedded entries cannot be empty");
			long withoutMetaData = allData.stream().map(x -> x.getMetadata() == null || x.getMetadata().isEmpty())
					.count();
			Assertions.assertFalse(withoutMetaData == 0l, "AI documents without metadata cannot be present");
			List<ExtractedDocumentMetaData> extractedMetaData = allData.stream().map(x -> {
				return ExtractedDocumentMetaData.of(x.getMetadata());
			}).toList();

			List<ExtractedDocumentMetaData> listwithoutMetaData = extractedMetaData.stream()
					.filter(x -> !x.isEnrichedWithMetaInfo()
							&& (!(x.getExtension() == null || x.getExtension().equalsIgnoreCase(".gitignore"))))
					.toList();
			for (ExtractedDocumentMetaData extractedDocumentMetaData : listwithoutMetaData) {
				LOGGER.error("The document=>" + extractedDocumentMetaData.getCode()
						+ " has not being enriched with metadata header");
			}
			Assertions.assertTrue(listwithoutMetaData.isEmpty(), "There are AI documents without meta header");
			LOGGER.info("Deleted vectors:" + usedTestVectorStore.getDeletedDocumentIds());
			if (checkVectorDeletionNotOccurred) {
				Assertions.assertTrue(usedTestVectorStore.getDeletedDocumentIds().isEmpty(),
						"This use case have not lead to deleted vectors");
			}

		}
		logVirtualFilesystemContent();
	}

}
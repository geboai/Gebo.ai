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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestConstants;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint.TestEndpointType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.WorkflowStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.tests.TestChatModelConfiguration;
import ai.gebo.llms.abstraction.layer.tests.TestChatModelSupportServiceImpl;
import ai.gebo.llms.abstraction.layer.tests.TestEmbeddingModelConfiguration;
import ai.gebo.llms.abstraction.layer.tests.TestEmbeddingModelSupportServiceImpl;
import ai.gebo.llms.abstraction.layer.vectorstores.GAccountingExtendedVectorStoreAdapter;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.ragsystem.vectorstores.test.services.TestVectorStore;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * Abstract class for integration tests involving Language Learning Models
 * (LLMs). Provides common functionality for running tests related to content
 * ingestion and processing. AI generated comments
 */
public abstract class AbstractBaseTestLLmsIntegrationTests extends AbstractBaseIntegrationTest {

	/**
	 * Executes a test for ingesting a single content file and ensures the expected
	 * number of files are vectorized. Logs the process and validates the
	 * vectorization and storage of document references.
	 *
	 * @param bundleFile       the file to be ingested
	 * @param extension        the file extension
	 * @param description      description of the test
	 * @param howManyFilesWait expected number of files to be vectorized
	 * @throws GeboJobServiceException           if an error occurs in the job
	 *                                           service
	 * @throws GeboPersistenceException          if an error occurs in persistence
	 * @throws GeboContentHandlerSystemException if an error occurs in content
	 *                                           handling
	 * @throws IOException                       if an I/O error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of an object fails
	 * @throws IllegalAccessException            if access to a class or field is
	 *                                           illegal
	 */
	protected void runSingleContentIngestionTest(String bundleFile, String extension, String description,
			long howManyFilesWait)
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		LOGGER.info(
				"*******************************************************************************************************");
		LOGGER.info("Running=>" + description);
		LOGGER.info(
				"*******************************************************************************************************");
		TestProjectEndpoint endpoint = createAndPersist(description, TestProjectEndpoint.class);
		endpoint.setOpenZips(true);
		endpoint.setTestType(TestEndpointType.CONTAINED_CONTENTS);
		endpoint = persistentObjectManager.update(endpoint);
		GProject pj = getProject(endpoint);
		GVirtualFolder folder = new GVirtualFolder();
		folder.setParentProjectCode(endpoint.getParentProjectCode());
		folder.setRootKnowledgebaseCode(pj.getRootKnowledgeBaseCode());
		folder.setCode("runSinglePDFContentsReadingAndEmbedding");
		folder.setDescription("Test runSinglePDFContentsReadingAndEmbedding");
		folder.setProjectEndpointReference(GObjectRef.of(endpoint));
		Path file = extractResource(bundleFile, extension);
		endpoint.getTestVirtualFolders().add(folder);
		// Create a document reference for the file using the reference factory
		GDocumentReference document = docreferenceFactory.createReference(file,
				"runSinglePDFContentsReadingAndEmbedding", null, null, endpoint, folder,
				TestConstants.TEST_CONTENT_SYSTEM_MODULE);
		endpoint.getTestDocumentReferences().add(document);
		persistentObjectManager.update(endpoint);
		// Execute the ingestion synchronously and obtain job status
		GJobStatus syncJobStatus = ingestionJobService.executeSyncJob(endpoint, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name());
		JobSummary summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
		WorkflowStatus workflowStatus = summary.getWorkflowStatus();
		int NMAXCYCLES = 20;
		int nCycles = 0;
		do {
			Thread.sleep(10000);
			summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
			workflowStatus = summary.getWorkflowStatus();
			nCycles++;

		} while (!((workflowStatus == null || (workflowStatus.getTotalDocumentsSuccessfull()
				+ workflowStatus.getTotalDocumentsWithErrors()) > howManyFilesWait)) && nCycles < NMAXCYCLES);
		summary = ingestionJobService.getJobSummary(syncJobStatus.getCode());
		LOGGER.info("Summary=" + mapper.writeValueAsString(summary));
		assertTrue(summary.getWorkflowStatus() != null && summary.getWorkflowStatus().isCompleted(),
				"Contents read have to be terminated in this point");
		/*
		 * assertTrue(summary.getVectorizationTerminated(),
		 * "Vectorization have to be terminated in this point");
		 * assertEquals(howManyFilesWait,
		 * summary.getCurrentBatchDocumentVectorizedCounter(),
		 * "Expected a single resource to be vectorized");
		 */
		IGConfigurableEmbeddingModel embeddingModel = embeddingModelRuntimeDao
				.findByCode(DEFAULT_TEST_EMBEDDING_MODEL_CODE);
		TestVectorStore usedTestVectorStore = getTestVectorStore();
		List<Document> documents = usedTestVectorStore.getStoredForMetaAttributeAndValue(DocumentMetaInfos.CONTENT_CODE,
				document.getCode());
		assertNotNull(documents, "Documents have to be stored in vector store with meta info with key=>"
				+ DocumentMetaInfos.CONTENT_CODE + " coherent with the single ingested document");
		org.junit.jupiter.api.Assertions.assertFalse(documents.isEmpty(), "Documents list cannot be empty");
		Map<Object, List<Document>> splittedForCodes = usedTestVectorStore
				.getStoredForMetaAttribute(DocumentMetaInfos.CONTENT_CODE);
		Assertions.assertFalse(splittedForCodes.isEmpty(), "The vector store has received 0 values splitted by code");
		Assertions.assertEquals(splittedForCodes.size(), howManyFilesWait,
				"The number of received files must be equal to the expected value");

		LOGGER.info(
				"*******************************************************************************************************");
		LOGGER.info("Exiting=>" + description);
		LOGGER.info(
				"*******************************************************************************************************");
		cleanPersistent(endpoint);
	}

	/**
	 * Executes a test for ingesting multiple content files and ensures the expected
	 * number of files are vectorized. Logs the process and validates the results.
	 *
	 * @param bundleFiles      list of files to be ingested
	 * @param description      description of the test
	 * @param howManyFilesWait expected number of files to be vectorized
	 * @throws GeboJobServiceException           if an error occurs in the job
	 *                                           service
	 * @throws GeboPersistenceException          if an error occurs in persistence
	 * @throws GeboContentHandlerSystemException if an error occurs in content
	 *                                           handling
	 * @throws IOException                       if an I/O error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of an object fails
	 * @throws IllegalAccessException            if access to a class or field is
	 *                                           illegal
	 */
	protected void runContentsIngestionTest(List<String> bundleFiles, String description, long howManyFilesWait)
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		LOGGER.info(
				"*******************************************************************************************************");
		LOGGER.info("Running=>" + description);
		LOGGER.info(
				"*******************************************************************************************************");
		TestProjectEndpoint endpoint = createAndPersist(description, TestProjectEndpoint.class);
		endpoint.setOpenZips(true);
		endpoint.setTestType(TestEndpointType.PATH_CONTENTS);
		endpoint = persistentObjectManager.update(endpoint);
		GProject pj = getProject(endpoint);
		GVirtualFolder folder = new GVirtualFolder();
		folder.setParentProjectCode(endpoint.getParentProjectCode());
		folder.setRootKnowledgebaseCode(pj.getRootKnowledgeBaseCode());
		folder.setCode("runContentsIngestionTest");
		folder.setDescription("Test runContentsIngestionTest");
		for (String bundled : bundleFiles) {
			String extension = bundled.substring(bundled.lastIndexOf("."));
			Path file = extractResource(bundled, extension);
			endpoint.getTestFilesystemPaths().add(file.toString());
		}
		endpoint.getTestVirtualFolders().add(folder);

		persistentObjectManager.update(endpoint);
		// Run the test and wait for results checking based on the expected number of
		// files
		runAndWaitDoneCheckingResults(endpoint, howManyFilesWait, true);
		cleanPersistent(endpoint);
		LOGGER.info(
				"*******************************************************************************************************");
		LOGGER.info("Exiting=>" + description);
		LOGGER.info(
				"*******************************************************************************************************");
	}
}
/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.ai.app.tests;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint;
import ai.gebo.architecture.contentsystems.abstraction.layer.test.TestProjectEndpoint.TestEndpointType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;

/**
 * Class representing a series of architectural tests for testing content
 * reading and embedding services. This class extends
 * AbstractBaseTestLLmsIntegrationTests.
 * 
 * AI generated comments
 */
public class ArchitecturalTests extends AbstractBaseTestLLmsIntegrationTests {

	/**
     * Test running a job with no contents to be read and embedded.
     * This test verifies the handling of an empty content scenario.
     * 
     * @throws GeboJobServiceException if a job service error occurs
     * @throws GeboPersistenceException if a persistence error occurs
     * @throws InstantiationException if instantiation of a class fails
     * @throws IllegalAccessException if access to a class or its methods fails
     */
    @Test
    public void runNoContentsReadingAndEmbedding()
            throws GeboJobServiceException, GeboPersistenceException, InstantiationException, IllegalAccessException {
        LOGGER.info("Running no contents full contents reading and vectorizing test");

        TestProjectEndpoint endpoint = createAndPersist(
                "Running no contents full contents reading and vectorizing test", TestProjectEndpoint.class);
        endpoint.setTestType(TestEndpointType.CONTAINED_CONTENTS);
        endpoint = persistentObjectManager.update(endpoint);
        LOGGER.info("Saved no contents endpoint, running syncronous job");
        GJobStatus syncJobStatus = ingestionJobService.executeSyncJob(endpoint, GWorkflowType.STANDARD.name(),
				GStandardWorkflow.INGESTION.name());
        cleanPersistent(endpoint);
    }

	/**
	 * Test reading and embedding a single PDF file content.
	 * 
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 */
	@Test
	public void runSinglePDFContentsReadingAndEmbedding()
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		runSingleContentIngestionTest(TEST_001_PDF_FILE, ".pdf",
				"Single pdf content reading and embedding integration test", 1l);
	}

	/**
	 * Test reading and embedding a single DOCX file content.
	 * 
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 */
	@Test
	public void runSingleDOCXContentsReadingAndEmbedding()
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		runSingleContentIngestionTest(TEST_001_DOCX_FILE, ".docx",
				"Single docx content reading and embedding integration test", 1l);
	}

	/**
	 * Test reading and embedding a single DOC file content.
	 * 
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 */
	@Test
	public void runSingleDOCContentsReadingAndEmbedding()
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		runSingleContentIngestionTest(TEST_001_DOC_FILE, ".doc",
				"Single doc content reading and embedding integration test", 1l);
	}

	/**
	 * Test reading and embedding a single XLS file content.
	 * 
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 */
	@Test
	public void runSingleXLSContentsReadingAndEmbedding()
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		runSingleContentIngestionTest(TEST_001_XLS_FILE, ".xls",
				"Single xls content reading and embedding integration test", 1l);
	}

	/**
	 * Test reading and embedding a single XLSX file content.
	 * 
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 */
	@Test
	public void runSingleXLSXContentsReadingAndEmbedding()
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		runSingleContentIngestionTest(TEST_001_XLSX_FILE, ".xlsx",
				"Single xlsx content reading and embedding integration test", 1l);
	}

	/**
	 * Test reading and embedding a single ODT file content.
	 * 
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 */
	@Test
	public void runSingleODTContentsReadingAndEmbedding()
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		runSingleContentIngestionTest(TEST_001_ODT_FILE, ".odt",
				"Single odt content reading and embedding integration test", 1l);
	}

	/**
	 * Test reading and embedding a ZIP file containing multiple contents.
	 * 
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 */
	@Test
	public void runZIPContentsReadingAndEmbedding()
			throws GeboJobServiceException, GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {
		runContentsIngestionTest(List.of(TEST_001_ZIP_FILE),
				"Single zip content reading and embedding integration test", 7l);
	}

	/**
	 * Test integration involving reading and embedding contents with a single
	 * erroneous file.
	 * 
	 * @throws InstantiationException            if instantiation of a class fails
	 * @throws IllegalAccessException            if access to a class or its methods
	 *                                           fails
	 * @throws GeboJobServiceException           if a job service error occurs
	 * @throws GeboPersistenceException          if a persistence error occurs
	 * @throws GeboContentHandlerSystemException if a content handler system error
	 *                                           occurs
	 * @throws IOException                       if an IO error occurs
	 * @throws InterruptedException              if the thread is interrupted
	 */
	@Test
	public void runContentsWithErrorFile()
			throws InstantiationException, IllegalAccessException, GeboJobServiceException, GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, InterruptedException {
		List<String> files = ALL_DATA_FILES;
		runContentsIngestionTest(files, "Running contents reading & embedding with a single error file",
				files.size() - 1);
	}
}
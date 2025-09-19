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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.filesystem.content.handler.impl.GFilesystemChangesHandlingService;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * AI generated comments Integration tests for shared filesystem
 * functionalities.
 */
public class SharedFilesystemIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {

	// Dependency injection for interacting with the filesystem content management
	// system.
	@Autowired
	IGFilesystemContentManagementSystemHandler filesystemHandler;

	// Service for handling changes in the filesystem.
	@Autowired
	GFilesystemChangesHandlingService fsChangeService;

	/**
	 * Test to process data in a shared filesystem. It validates the ability to
	 * handle and process files in a simulated shared environment.
	 *
	 * @throws InstantiationException            if there is an error creating
	 *                                           instances.
	 * @throws IllegalAccessException            if there is an error accessing a
	 *                                           method.
	 * @throws GeboPersistenceException          for persistence-related errors.
	 * @throws GeboContentHandlerSystemException for content handler system errors.
	 * @throws IOException                       if an I/O error occurs during file
	 *                                           handling.
	 * @throws GeboJobServiceException           for job service related exceptions.
	 * @throws InterruptedException              if the thread executing the test is
	 *                                           interrupted.
	 */
	@Test
	public void testSharedFilesystemProcessing()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboJobServiceException, InterruptedException {
		GFilesystemProjectEndpoint endpoint = createAndPersist("shared filesystem test data",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		// Create base folder for testing; similar usage to a shared test project
		// folder.
		String baseFolder = localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint);
		Files.createDirectories(Path.of(baseFolder));
		for (String path : ALL_DATA_FILES) {
			Path copied = copyResource(path, baseFolder);
		}
		endpoint.setPath(List.of(VFilesystemReference.from(Path.of(baseFolder))));
		endpoint = persistentObjectManager.update(endpoint);
		runAndWaitDoneCheckingResults(endpoint, ALL_DATA_FILES.size() - 1, true);
		cleanPersistent(endpoint);
	}

	// Large ZIP file used for specific test case.
	private static final String BLAVASKY_ZIP = "TEST-CONTENTS-001/Teosophy-Helena-Petrovna-Blavatsky.zip";

	/**
	 * Test to process a large ZIP file which might encounter OutOfMemoryError.
	 * Validates the system's stability and memory handling capabilities.
	 *
	 * @throws InstantiationException            if there is an error creating
	 *                                           instances.
	 * @throws IllegalAccessException            if there is an error accessing a
	 *                                           method.
	 * @throws GeboPersistenceException          for persistence-related errors.
	 * @throws GeboContentHandlerSystemException for content handler system errors.
	 * @throws IOException                       if an I/O error occurs during file
	 *                                           handling.
	 * @throws GeboJobServiceException           for job service related exceptions.
	 * @throws InterruptedException              if the thread executing the test is
	 *                                           interrupted.
	 */
	@Test
	public void testZipGoingOutOfMemoryProcessing()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboJobServiceException, InterruptedException {
		GFilesystemProjectEndpoint endpoint = createAndPersist("shared filesystem test data",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		// Set up test environment using a project-like folder.
		String baseFolder = localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint);
		Files.createDirectories(Path.of(baseFolder));
		String path = BLAVASKY_ZIP;
		Path copied = copyResource(path, baseFolder);
		endpoint.setPath(List.of(VFilesystemReference.from(Path.of(baseFolder))));
		endpoint.setOpenZips(true);
		endpoint = persistentObjectManager.update(endpoint);
		runAndWaitDoneCheckingResults(endpoint, 10, true, 90);
		cleanPersistent(endpoint);
	}

	/**
	 * Test to validate the detection and processing of changes in the shared
	 * filesystem. This involves adding new resources and confirming their
	 * recognition and handling by the system.
	 *
	 * @throws InstantiationException            if there is an error creating
	 *                                           instances.
	 * @throws IllegalAccessException            if there is an error accessing a
	 *                                           method.
	 * @throws GeboPersistenceException          for persistence-related errors.
	 * @throws GeboContentHandlerSystemException for content handler system errors.
	 * @throws IOException                       if an I/O error occurs during file
	 *                                           handling.
	 * @throws GeboJobServiceException           for job service related exceptions.
	 * @throws InterruptedException              if the thread executing the test is
	 *                                           interrupted.
	 */
	// @Test // Currently commented out: Uncomment to execute this test.
	public void testSharedFilesystemChangesProcessing()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboJobServiceException, InterruptedException {
		GFilesystemProjectEndpoint endpoint = createAndPersist("shared filesystem test data",
				GFilesystemProjectEndpoint.class);
		GFilesystemContentManagementSystem system = filesystemHandler.getSystem(endpoint);
		// Create base folder and simulate shared project conditions.
		String baseFolder = localFolderDiscoveryService.getLocalPersistentFolder(system, endpoint);
		Files.createDirectories(Path.of(baseFolder));
		for (String path : ALL_DATA_FILES) {
			Path copied = copyResource(path, baseFolder);
		}
		endpoint.setPath(List.of(VFilesystemReference.from(Path.of(baseFolder))));
		endpoint = persistentObjectManager.update(endpoint);
		fsChangeService.addHandling(endpoint);
		runAndWaitDoneCheckingResults(endpoint, ALL_DATA_FILES.size() - 1, true);
		long howMany = statusRepo.count();
		copyResource(TEST_001_ZIP_FILE, baseFolder);
		Thread.sleep(60000);
		long after1minute = statusRepo.count();
		assertEquals(howMany + 1, after1minute, "After one minute expected that the changes service has done its job");
		cleanPersistent(endpoint);
	}
}
/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ai.gebo.ai.app.tests.AbstractBaseTestLLmsIntegrationTests;
import ai.gebo.ai.app.virtualremotefs.tests.architecture.TestRootHolder;
import ai.gebo.ai.app.virtualremotefs.tests.architecture.TestVirtualRemoteProjectEndpoint;
import ai.gebo.architecture.integration.tests.model.TestVirtualFilesystemNode;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.monolithic.app.Main;

/**
 * AI generated comments
 * This class contains integration tests for Virtual Remote Filesystem Architecture.
 * It extends the AbstractBaseTestLLmsIntegrationTests to utilize shared test configuration.
 */
@SpringBootTest(classes = Main.class)
public class VirtualRemoteFilesystemArchitectureTests extends AbstractBaseTestLLmsIntegrationTests {
	// File path to the JSON resource representing the virtual filesystem
	public final String RESOURCE_FILE = "/VIRTUAL_FILESYSTEM_001/virtual-filesystem.json";
	// Root item identifier for the virtual filesystem
	public final String ROOT_ITEM_ID = "XXXXX0001";
	// Identifier for a resource intended for removal in test scenarios
	public final String RESOURCE_TO_REMOVE_ID = "XXXXX0001-INDEX-001-2";
	// Identifier for a folder intended for removal in test scenarios
	public final String FOLDER_TO_REMOVE_ID = "XXXXX0001-FOLDER-001";

	/**
	 * Test method for ingesting virtual filesystem nodes and verifying their deletion.
	 * It performs operations like loading resources, creating and persisting project endpoints, 
	 * synchronizing nodes, and validating the deletion of specific documents and folders.
	 *
	 * @throws IOException if an I/O error occurs
	 * @throws InstantiationException if an instantiation error occurs
	 * @throws IllegalAccessException if access to a class or method is denied
	 * @throws GeboPersistenceException if a persistence error occurs
	 * @throws GeboJobServiceException if a job service error occurs
	 * @throws InterruptedException if the thread is interrupted during execution
	 */
	@Test
	public void ingestVirtualFIlesystemNodesWithDeletionTest() throws IOException, InstantiationException,
			IllegalAccessException, GeboPersistenceException, GeboJobServiceException, InterruptedException {
		// Load virtual filesystem node resources from a file and set as the root node
		TestVirtualFilesystemNode root = TestVirtualFilesystemNode.loadResources(RESOURCE_FILE);
		TestRootHolder.rootNode = root;
		// Create and persist a test project endpoint for reading and deletion operations
		TestVirtualRemoteProjectEndpoint pendpoint = createAndPersist("Virtual filesystem read and deletion",
				TestVirtualRemoteProjectEndpoint.class);
		// Set up the root object and reference for the virtual filesystem
		GVirtualFilesystemRoot rootObject = new GVirtualFilesystemRoot();
		rootObject.setAbsolutePath("/");
		rootObject.setCode(ROOT_ITEM_ID);
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = rootObject;
		// Associate the reference with the project endpoint
		pendpoint.setPaths(List.of(reference));
		pendpoint = persistentObjectManager.update(pendpoint);
		// Run the process and wait for it to complete, then verify outcomes
		runAndWaitDoneCheckingResults(pendpoint, true);
		// Attempt to remove a single node and check if it is deleted as expected
		TestVirtualFilesystemNode item2Remove = TestRootHolder.findNode(RESOURCE_TO_REMOVE_ID);
		TestRootHolder.rootNode.removeNode(RESOURCE_TO_REMOVE_ID);
		// Relaunch synchronization and verify the logical deletion of the node
		runAndWaitDoneCheckingResults(pendpoint, true, false);
		List<GDocumentReference> allDocs = persistentObjectManager.findAll(GDocumentReference.class);
		List<GDocumentReference> deletedList = allDocs.stream().filter(x -> x.getDeleted()).toList();
		assertFalse(deletedList.isEmpty(), "The list of deleted documents cannot be empty");
		assertEquals(deletedList.size(), 1, "Exactly one document has been deleted");
		assertTrue(deletedList.get(0).getCode().endsWith(item2Remove.getId()),
				"The deleted document have to end with id of removed node");
		// Remove the folder and check for its deletion
		TestVirtualFilesystemNode folder2remove = TestRootHolder.findNode(FOLDER_TO_REMOVE_ID);
		TestRootHolder.rootNode.removeNode(FOLDER_TO_REMOVE_ID);
		runAndWaitDoneCheckingResults(pendpoint, true, false);
		allDocs = persistentObjectManager.findAll(GDocumentReference.class);
		deletedList = allDocs.stream().filter(x -> x.getDeleted()).toList();
		assertFalse(deletedList.isEmpty(), "The list of deleted documents cannot be empty");
		assertEquals(deletedList.size(), 4, "Exactly 4 document has been deleted");
		List<GVirtualFolder> allFolders = persistentObjectManager.findAll(GVirtualFolder.class);
		List<GVirtualFolder> deletedFolders = allFolders.stream().filter(x -> x.getDeleted()).toList();
		assertFalse(deletedFolders.isEmpty(), "The list of deleted folders cannot be empty");
		assertEquals(deletedFolders.size(), 1, "Exactly one folder has been deleted");
		assertTrue(deletedFolders.get(0).getCode().endsWith(folder2remove.getId()),
				"The deleted folder have to end with id of removed node");
	}
}
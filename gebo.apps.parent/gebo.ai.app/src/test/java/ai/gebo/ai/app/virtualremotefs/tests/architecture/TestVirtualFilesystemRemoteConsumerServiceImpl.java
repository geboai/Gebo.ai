/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.springframework.stereotype.Service;

import ai.gebo.ai.app.virtualremotefs.tests.architecture.TestVirtualFilesystemCustomContent.TestVirtualFilesystemNodeType;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.integration.tests.model.TestVirtualFilesystemNode;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import junit.framework.Assert;

/**
 * AI generated comments
 *
 * Service implementation for consuming virtual filesystem resources remotely.
 */
@Service
public class TestVirtualFilesystemRemoteConsumerServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<TestVirtualRemoteSystem, TestVirtualRemoteProjectEndpoint, TestVirtualFilesystemNativeObject, TestVirtualfilesystemNavigationCoordinates, TestVirtualFilesystemRemoteReference>
		implements IGTestVirtualFilesystemRemoteConsumerService {

	/**
	 * Constructs the service with a document reference factory.
	 *
	 * @param documentFactory the factory for creating document references
	 */
	public TestVirtualFilesystemRemoteConsumerServiceImpl(IGDocumentReferenceFactory documentFactory) {
		super(documentFactory);
	}

	/**
	 * Retrieves a resource handle for a specified system endpoint and filesystem reference.
	 *
	 * @param system the remote system
	 * @param endpoint the project endpoint within the system
	 * @param reference the filesystem object reference
	 * @param cache a cache map to store reusable data
	 * @return a remote reference for the specified resource
	 * @throws GeboContentHandlerSystemException if an error occurs during processing
	 */
	@Override
	public TestVirtualFilesystemRemoteReference getResourceHandle(TestVirtualRemoteSystem system,
			TestVirtualRemoteProjectEndpoint endpoint, GAbstractVirtualFilesystemObject reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException {

		if (reference instanceof GDocumentReference) {
			// Ensure that the reference contains RESOURCE_NODE_ID metadata
			Assertions.assertTrue(
					reference.getCustomMetaInfos().containsKey(TestVirtualFilesystemNativeObject.RESOURCE_NODE_ID),
					"Item of type => " + reference.getClass().getName()
							+ " without RESOURCE_NODE_ID in customMetaInfos ");
			TestVirtualFilesystemRemoteReference out = new TestVirtualFilesystemRemoteReference();
			out.setResource(true);
			out.setId(
					reference.getCustomMetaInfos().get(TestVirtualFilesystemNativeObject.RESOURCE_NODE_ID).toString());
			return out;
		}
		if (reference instanceof GVirtualFolder) {
			// Ensure that the reference contains FOLDER_NODE_ID metadata
			Assertions.assertTrue(
					reference.getCustomMetaInfos().containsKey(TestVirtualFilesystemNativeObject.FOLDER_NODE_ID),
					"Item of type => " + reference.getClass().getName()
							+ " without FOLDER_NODE_ID in customMetaInfos ");
			TestVirtualFilesystemRemoteReference out = new TestVirtualFilesystemRemoteReference();
			out.setFolder(true);
			out.setId(reference.getCustomMetaInfos().get(TestVirtualFilesystemNativeObject.FOLDER_NODE_ID).toString());
			return out;
		}
		throw new RuntimeException("Wrong type of referred object =>" + reference.getClass().getName());
	}

	/**
	 * Streams the content of a resource from the filesystem.
	 *
	 * @param system the remote system
	 * @param endpoint the endpoint in the system
	 * @param reference the reference to the desired resource
	 * @param cache a cache map for storing data
	 * @return an InputStream to read the resource
	 * @throws GeboContentHandlerSystemException if there's an issue with content handling
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public InputStream streamResource(TestVirtualRemoteSystem system, TestVirtualRemoteProjectEndpoint endpoint,
			TestVirtualFilesystemRemoteReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		// Validate that the reference is indeed a resource and not a folder
		Assertions.assertTrue(reference.isResource(), "Streamed item must be a resource");
		Assertions.assertFalse(reference.isFolder(), "Streamed item must NOT be a folder");
		Assertions.assertNotNull(reference.getId(), "Streamed item id must NOT be null");
		TestVirtualFilesystemNode node = TestRootHolder.findNode(reference.getId());
		LOGGER.info("Streaming item with id:" + node.getId() + " content:" + node.getContent());
		if (node.getContent() != null)
			return new ByteArrayInputStream(node.getContent().getBytes());
		else
			return InputStream.nullInputStream();
	}

	/**
	 * Retrieves the messaging module ID.
	 * 
	 * @return the messaging module ID as a string
	 */
	@Override
	public String getMessagingModuleId() {
		return TestVirtualFilesystemContentHandlerImpl.TEST_MODULE;
	}

	/**
	 * Obtains position coordinates from a list of native filesystem objects.
	 *
	 * @param childCoordinates list of filesystem native objects
	 * @return coordinates for navigation through the filesystem
	 * @throws GeboContentHandlerSystemException if an error occurs during the operation
	 */
	@Override
	protected TestVirtualfilesystemNavigationCoordinates getPositionCoordinate(
			List<TestVirtualFilesystemNativeObject> childCoordinates) throws GeboContentHandlerSystemException {
		TestVirtualfilesystemNavigationCoordinates coords = new TestVirtualfilesystemNavigationCoordinates();
		// Assume the first item is the root. Initiating with the first root element.
		TestVirtualFilesystemNativeObject root = childCoordinates.get(0);
		GVirtualFilesystemRoot rootItem = new GVirtualFilesystemRoot();
		rootItem.setCode(root.getCode());
		rootItem.setDescription(root.getName());
		rootItem.setAbsolutePath("/");
		coords.setRoot(rootItem);
		// Adding each child's path information into coordinates
		for (int i = 1; i < childCoordinates.size(); i++) {
			TestVirtualFilesystemNativeObject thisItem = childCoordinates.get(i);
			coords.getBrowsingSteps().add(toPathInfo(thisItem));
			coords.getBrowsingStepsCustom().add(toCustom(thisItem));
		}
		return coords;
	}

	/**
	 * Converts a TestVirtualFilesystemNativeObject instance to a custom content structure.
	 *
	 * @param thisItem the native filesystem object
	 * @return a custom content representation of the object
	 */
	private TestVirtualFilesystemCustomContent toCustom(TestVirtualFilesystemNativeObject thisItem) {
		TestVirtualFilesystemCustomContent item = new TestVirtualFilesystemCustomContent();
		item.type = thisItem.isFolder() ? TestVirtualFilesystemNodeType.NODE : TestVirtualFilesystemNodeType.LEAF;
		item.id = thisItem.getCode();
		return item;
	}

	/**
	 * Converts a TestVirtualFilesystemNativeObject to basic path information.
	 *
	 * @param thisItem the filesystem native object
	 * @return path information for the object
	 */
	private PathInfo toPathInfo(TestVirtualFilesystemNativeObject thisItem) {
		PathInfo path = new PathInfo();
		path.absolutePath = thisItem.getCode();
		path.folder = thisItem.isFolder();
		path.name = thisItem.getName();
		return path;
	}

	/**
	 * Retrieves child nodes from the given coordinates in the virtual filesystem.
	 *
	 * @param nativeCoordinates parent coordinates list
	 * @param position navigation coordinates for the position
	 * @param system the remote system
	 * @param endpoint the endpoint in the remote system
	 * @param messagesConsumer for processing user messages
	 * @param environment additional context for the operation
	 * @return list of NativeCoordinatePointers for the child nodes
	 * @throws GeboContentHandlerSystemException if an error occurs with content handling
	 */
	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<TestVirtualFilesystemNativeObject> nativeCoordinates,
			TestVirtualfilesystemNavigationCoordinates position, TestVirtualRemoteSystem system,
			TestVirtualRemoteProjectEndpoint endpoint, IGUserMessagesConsumer messagesConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		TestVirtualFilesystemNativeObject last = lastNode(nativeCoordinates);
		if (!(last.isFolder() || last.isResource()))
			throw new RuntimeException("Data structure for test is wrong");
		List<NativeCoordinatePointer> pointers = new ArrayList<NativeCoordinatePointer>();
		if (last.isFolder()) {
			// For folders, add each child as either a folder or resource pointer
			for (TestVirtualFilesystemNode child : last.getFolderObject().getChilds()) {
				if (child.isFolder()) {
					NativeCoordinatePointer folderPointer = new NativeCoordinatePointer();
					folderPointer.parentCoordinates = new ArrayList<TestVirtualFilesystemNativeObject>(
							nativeCoordinates);
					folderPointer.child = new TestVirtualFilesystemNativeObject();
					folderPointer.child.setFolderObject(child);
					pointers.add(folderPointer);
				}
				if (child.isResource()) {
					NativeCoordinatePointer resourcePointer = new NativeCoordinatePointer();
					resourcePointer.parentCoordinates = new ArrayList<TestVirtualFilesystemNativeObject>(
							nativeCoordinates);
					resourcePointer.child = new TestVirtualFilesystemNativeObject();
					resourcePointer.child.setResourceObject(child);
					pointers.add(resourcePointer);
				}
			}
		}
		return pointers;
	}

	/**
	 * Converts navigation coordinates into native filesystem coordinates.
	 *
	 * @param position the navigation coordinates
	 * @param system the remote system
	 * @param endpoint the endpoint within the system
	 * @param root the virtual folder root
	 * @param consumer for processing consumed content
	 * @param messagesConsumer for user messages
	 * @param errorConsumer for handling access errors
	 * @param environment additional context or state information
	 * @return a list of native filesystem object
	 * @throws GeboContentHandlerSystemException if any errors arise during conversion
	 */
	@Override
	protected List<TestVirtualFilesystemNativeObject> toNativeCoordinates(
			TestVirtualfilesystemNavigationCoordinates position, TestVirtualRemoteSystem system,
			TestVirtualRemoteProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		List<TestVirtualFilesystemNativeObject> coords = new ArrayList<TestVirtualFilesystemNativeObject>();
		String rootId = position.getRoot().getCode();
		TestVirtualFilesystemNode rootNode = TestRootHolder.findNode(rootId);
		TestVirtualFilesystemNativeObject rootObject = new TestVirtualFilesystemNativeObject();
		rootObject.setFolderObject(rootNode);
		coords.add(rootObject);
		for (TestVirtualFilesystemCustomContent child : position.getBrowsingStepsCustom()) {
			TestVirtualFilesystemNativeObject item = new TestVirtualFilesystemNativeObject();
			TestVirtualFilesystemNode thisitem = TestRootHolder.findNode(child.getId());
			if (thisitem.isFolder()) {
				item.setFolderObject(thisitem);
			} else {
				item.setResourceObject(thisitem);
			}
			coords.add(item);
		}
		return coords;
	}

	/**
	 * Creates an environment map for the current system and endpoint.
	 *
	 * @param system the specified remote system
	 * @param endpoint the specified remote endpoint
	 * @param errorConsumer for error handling during content access
	 * @return a map serving as the execution environment
	 * @throws GeboContentHandlerSystemException if any error arises
	 */
	@Override
	protected Map<String, Object> createEnvironment(TestVirtualRemoteSystem system,
			TestVirtualRemoteProjectEndpoint endpoint, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		return new HashMap<String, Object>();
	}

	/**
	 * Clears the environment map for a system and endpoint.
	 *
	 * @param environment the environment map to clear
	 * @param system the remote system
	 * @param endpoint the project endpoint
	 * @throws GeboContentHandlerSystemException if any error arises during the process
	 */
	@Override
	protected void clearEnvironment(Map<String, Object> environment, TestVirtualRemoteSystem system,
			TestVirtualRemoteProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		// Method implementation intentionally empty for clearing the environment.
	}

	/**
	 * Converts a filesystem reference into navigation coordinates.
	 *
	 * @param path the filesystem reference path
	 * @return navigation coordinates
	 * @throws GeboContentHandlerSystemException if any error occurs in content handling
	 */
	@Override
	protected TestVirtualfilesystemNavigationCoordinates toNavigationPosition(VFilesystemReference path)
			throws GeboContentHandlerSystemException {
		String id = null;
		if (path.path != null) {
			id = path.path.absolutePath;
		} else {
			id = path.root.getCode();
		}
		TestVirtualFilesystemNode item = TestRootHolder.findNode(id);
		TestVirtualfilesystemNavigationCoordinates coordinates = new TestVirtualfilesystemNavigationCoordinates();
		coordinates.setRoot(path.root);
		if (item != null && item.parent != null) {
			TestVirtualFilesystemNode current = item;
			do {
				PathInfo pathinfo = new PathInfo();
				pathinfo.folder = current.isFolder();
				pathinfo.name = current.getName();
				pathinfo.absolutePath = current.getId();
				coordinates.getBrowsingSteps().add(0, pathinfo);
				TestVirtualFilesystemCustomContent custom = new TestVirtualFilesystemCustomContent();
				custom.type = current.isFolder() ? TestVirtualFilesystemNodeType.NODE
						: TestVirtualFilesystemNodeType.LEAF;
				custom.id = current.getId();
				coordinates.getBrowsingStepsCustom().add(0, custom);
				current = current.parent;
			} while (current != null);
		}
		return coordinates;
	}

	/**
	 * Describes an object based on references.
	 *
	 * @param references the list of native object references
	 * @param system the remote system
	 * @param endpoint the system's project endpoint
	 * @param environment additional environment data
	 * @return a description of the last referenced object
	 */
	@Override
	protected String describeObject(List<TestVirtualFilesystemNativeObject> references, TestVirtualRemoteSystem system,
			TestVirtualRemoteProjectEndpoint endpoint, Map<String, Object> environment) {
		TestVirtualFilesystemNativeObject lastRef = lastNode(references);
		return lastRef.getName();
	}

	/**
	 * Provides a description of the system.
	 *
	 * @param system the remote system
	 * @return a brief system description
	 */
	@Override
	protected String describeSystem(TestVirtualRemoteSystem system) {
		return "System:" + system.getDescription();
	}

	/**
	 * Provides a description of a project endpoint.
	 *
	 * @param system the remote system
	 * @param endpoint the project endpoint
	 * @param environment additional environment data
	 * @return a description of the test endpoint
	 */
	@Override
	protected String describeProjectEndpoint(TestVirtualRemoteSystem system, TestVirtualRemoteProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "Test endpoint:" + endpoint.getDescription();
	}

	/**
	 * Verifies the existence of a remote object.
	 *
	 * @param system the remote system
	 * @param endpoint the project endpoint in system
	 * @param doc the filesystem object document
	 * @param reference a reference to the remote resource
	 * @param environment additional environment context
	 * @return the verified filesystem object
	 * @throws GeboContentHandlerSystemException if conformance issues are encountered
	 */
	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(TestVirtualRemoteSystem system,
			TestVirtualRemoteProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc,
			TestVirtualFilesystemRemoteReference reference, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		// Mark the document as deleted if the node does not exist
		doc.setDeleted(TestRootHolder.findNode(reference.getId()) == null);
		doc.setModificationDate(new Date());
		return doc;
	}

}
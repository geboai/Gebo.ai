/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * An interface to consume resources from a remote virtual filesystem in a
 * content management system.
 * <p>
 * This interface outlines the methods required to:
 * <ul>
 * <li>Process or “consume” all resources within a specified virtual folder
 * hierarchy.</li>
 * <li>Obtain a reference to a remote resource by document reference.</li>
 * <li>Stream remote resources for further handling or processing.</li>
 * </ul>
 * 
 * @param <SystemType>        The specific type of content management system.
 * @param <EndpointType>      The project endpoint type in the virtual
 *                            filesystem.
 * @param <ResourceReference> The handle type used to reference remote
 *                            filesystem resources.
 */
public interface IGRemoteVirtualFilesystemConsumingService<SystemType extends GContentManagementSystem, EndpointType extends GVirtualFilesystemProjectEndpoint, ResourceReference extends IGRemoteVirtualFilesystemResourceReference> {
	
	/**
	 * Consumes all resources found under the specified root folder of the remote
	 * virtual filesystem, using the provided {@link IGContentConsumer}.
	 * <p>
	 * The method also accepts an {@link IGUserMessagesConsumer} to handle
	 * user-facing messages that might arise during consumption (e.g., logs,
	 * notifications, or errors).
	 * 
	 * @param system           The content management system instance.
	 * @param endpoint         The endpoint pointing to the virtual filesystem
	 *                         project.
	 * @param root             The root folder within the remote virtual filesystem
	 *                         to start consumption.
	 * @param consumer         The consumer responsible for processing each
	 *                         discovered resource.
	 * @param messagesConsumer The consumer responsible for handling user-facing
	 *                         messages.
	 * @param errorConsumer    The consumer responsible for handling access errors during processing.
	 * @throws GeboContentHandlerSystemException if any exception occurs during
	 *                                           resource consumption.
	 */
	public void consumeAll(SystemType system, EndpointType endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException;

	/**
	 * Retrieves a reference (handle) to a particular resource in the remote virtual
	 * filesystem based on a provided {@link GDocumentReference}.
	 * <p>
	 * A cache map is provided to store and retrieve potentially expensive-to-obtain
	 * data, thereby reducing redundant operations.
	 * 
	 * @param system    The content management system instance.
	 * @param endpoint  The endpoint pointing to the virtual filesystem project.
	 * @param reference The document reference describing the resource to retrieve.
	 * @param cache     A map for caching objects that may be reused across multiple
	 *                  calls.
	 * @return A resource reference handle usable for streaming or further resource
	 *         interaction.
	 * @throws GeboContentHandlerSystemException if any exception occurs while
	 *                                           retrieving the resource handle.
	 */
	public ResourceReference getResourceHandle(SystemType system, EndpointType endpoint, GAbstractVirtualFilesystemObject reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException;

	/**
	 * Opens and returns an {@link InputStream} for a specific resource in the
	 * remote virtual filesystem, allowing the caller to read the resource content.
	 * <p>
	 * A cache map may be used to store intermediate or reusable objects to optimize
	 * future calls.
	 * 
	 * @param system    The content management system instance.
	 * @param endpoint  The endpoint pointing to the virtual filesystem project.
	 * @param reference The reference (handle) to the resource.
	 * @param cache     A map for caching objects that may be reused across multiple
	 *                  calls.
	 * @return An {@link InputStream} to read the resource content.
	 * @throws GeboContentHandlerSystemException if an issue arises within the
	 *                                           content handler.
	 * @throws IOException                       if there is an error opening or
	 *                                           reading the stream.
	 */
	public InputStream streamResource(SystemType system, EndpointType endpoint, ResourceReference reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException, IOException;

	/**
	 * Retrieves the messaging module ID associated with this consuming service.
	 * 
	 * @return A string representing the messaging module ID.
	 */
	public String getMessagingModuleId();

	/**
	 * Checks the given items in the remote virtual filesystem to see if they have
	 * been updated or deleted.
	 * 
	 * @param system        The content management system instance.
	 * @param endpoint      The endpoint pointing to the virtual filesystem project.
	 * @param errorConsumer The consumer for handling errors related to item access.
	 * @param itemsToCheck  A stream of items to check for updates or deletions.
	 * @return A stream containing items that were detected as updated or deleted.
	 * @throws GeboContentHandlerSystemException if any exception occurs during the
	 *                                           check.
	 */
	public Stream<GAbstractVirtualFilesystemObject> checkUpdatedOrDeleted(SystemType system, EndpointType endpoint,
			IGContentsAccessErrorConsumer errorConsumer, Stream<GAbstractVirtualFilesystemObject> itemsToCheck) throws GeboContentHandlerSystemException;

}
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
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.system.ingestion.GeboIngestionException;

/**
 * AI generated comments Handler interface for managing interactions with
 * content management systems. Provides methods for consuming and processing
 * content from various endpoints.
 * 
 * @param <SystemIntegrationType> The type of the content management system
 *                                being integrated.
 * @param <ProjectEndpointType>   The type of the project endpoint being used.
 */
public interface IGContentManagementSystemHandler<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
		extends IGMessageReceiver {

	/**
	 * Retrieves the type of the system being handled by this handler.
	 *
	 * @return A GContentManagementSystemType representing the type of system
	 *         managed.
	 */
	public GContentManagementSystemType getHandledSystemType();

	/**
	 * Consumes content from the specified project endpoint using the given
	 * consumers.
	 *
	 * @param projectEndpoint  The endpoint from which to consume content.
	 * @param consumer         The content consumer to handle the content.
	 * @param messagesConsumer The consumer to handle user messages.
	 * @param errorConsumer    The consumer to handle access errors.
	 * @throws GeboContentHandlerSystemException If an error occurs during content
	 *                                           consumption.
	 */
	public void consume(ProjectEndpointType projectEndpoint, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException;

	/**
	 * Finds a project endpoint using specified system and project codes.
	 *
	 * @param systemCode          The system code to identify the system.
	 * @param projectEndpointCode The project endpoint code to locate the specific
	 *                            endpoint.
	 * @return The project endpoint matching the specified codes.
	 * @throws GeboContentHandlerSystemException If the project endpoint cannot be
	 *                                           found.
	 */
	public ProjectEndpointType findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException;

	/**
	 * Retrieves the configurations for all systems managed by the handler.
	 *
	 * @return A list of system configurations.
	 */
	public List<SystemIntegrationType> getConfigurations();

	/**
	 * Checks if a given project endpoint is managed by this handler.
	 *
	 * @param endpoint The project endpoint to check.
	 * @return true if the endpoint is managed, false otherwise.
	 */
	public boolean isManagedEndpoint(GProjectEndpoint endpoint);

	/**
	 * Retrieves the system associated with the specified project endpoint.
	 *
	 * @param projectEndPoint The project endpoint associated with the system.
	 * @return The system linked to the specified endpoint.
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           retrieval.
	 */
	public SystemIntegrationType getSystem(ProjectEndpointType projectEndPoint)
			throws GeboContentHandlerSystemException;

	/**
	 * Streams content from a given document reference.
	 *
	 * @param reference The document reference for the content to stream.
	 * @param cache     A map to cache previously accessed content.
	 * @return An InputStream to stream the document content.
	 * @throws GeboContentHandlerSystemException If an error occurs accessing the
	 *                                           content.
	 * @throws IOException                       If an I/O error occurs.
	 */
	public InputStream streamContent(GDocumentReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException;

	/**
	 * Reads and returns a document from a specified reference.
	 *
	 * @param reference The reference to the document to read.
	 * @param cache     A map to cache document data.
	 * @return A GeboDocument object representing the document content.
	 * @throws GeboContentHandlerSystemException If an error occurs in the handler
	 *                                           system.
	 * @throws IOException                       If an I/O error occurs.
	 * @throws GeboIngestionException            If an error occurs during document
	 *                                           ingestion.
	 */
	public GeboDocument readDocument(GDocumentReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException;

	/**
	 * Checks the status of virtual filesystem objects to determine if they are
	 * updated or deleted.
	 *
	 * @param endpoint      The endpoint representing the project context.
	 * @param itemsToCheck  The stream of filesystem objects to check.
	 * @param errorConsumer The consumer to handle access errors.
	 * @return A stream of filesystem objects that are updated or deleted.
	 * @throws GeboContentHandlerSystemException If a system error occurs.
	 */
	public Stream<GAbstractVirtualFilesystemObject> checkUpdatedOrDeleted(ProjectEndpointType endpoint,
			Stream<GAbstractVirtualFilesystemObject> itemsToCheck, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException;

	/****************************************************
	 * Returns true if this handler caches files or accesses file in an accessible
	 * filesystem
	 * 
	 * @return
	 */
	public boolean isContentsOnLocalFilesystem();
}
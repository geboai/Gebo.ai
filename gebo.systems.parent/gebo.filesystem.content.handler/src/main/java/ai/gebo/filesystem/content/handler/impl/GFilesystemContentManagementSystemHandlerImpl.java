/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.filesystem.content.handler.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;
import ai.gebo.systems.abstraction.layer.RemoteVirtualFileSystemContentConsumingSessionParam;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;

/**
 * AI generated comments
 * 
 * Implementation of the filesystem content management system handler. This
 * service manages file system interactions for the content management system,
 * handling operations like file discovery, consumption, and change tracking.
 */
public class GFilesystemContentManagementSystemHandlerImpl extends
		GAbstractContentManagementSystemHandler<GFilesystemContentManagementSystem, GFilesystemProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam>
		implements IGFilesystemContentManagementSystemHandler {

	/**
	 * Constructor for the filesystem content management system handler.
	 * 
	 * @param buildSystemHandlerRepository Repository for build system handlers
	 * @param contentHandler               Factory for document references
	 * @param configurationsDao            DAO for content management system
	 *                                     configurations
	 * @param endpointsDao                 DAO for project endpoint configurations
	 * @param localFolderDiscoveryService  Service for discovering local folders
	 * @param persistentObjectManager      Manager for persistent objects
	 * @param messageBroker                Broker for message handling
	 * @param ingestionHandler             Handler for document reference ingestion
	 */
	public GFilesystemContentManagementSystemHandlerImpl(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,

			IGContentManagementSystemConfigurationDao<GFilesystemContentManagementSystem> configurationsDao,
			IGProjectEndpointRuntimeConfigurationDao<GFilesystemProjectEndpoint> endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointsDao,
				localFolderDiscoveryService, persistentObjectManager, messageBroker, ingestionHandler);

	}

	/**
	 * Static definition of the content management system type handled by this
	 * implementation.
	 */
	private static final GContentManagementSystemType handledSystemType = new GContentManagementSystemType();
	static {
		handledSystemType.setCode("DEFAULT.FILESYSTEM.CONTENT.HANDLER");
		handledSystemType.setDescription("Generical filesystem handler");
		handledSystemType.setCapabilities(List.of(GSystemRole.SOURCE_MANAGEMENT, GSystemRole.DOCUMENTS_MANAGEMENT));

	}

	/**
	 * Returns the type of content management system handled by this implementation.
	 * 
	 * @return The content management system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {

		return handledSystemType;
	}

	/**
	 * Implements the actual content consumption process for the filesystem. Creates
	 * a root item and processes all files in the specified path.
	 * 
	 * @param contentManagementConfig The filesystem CMS configuration
	 * @param buildSystems            List of build systems
	 * @param endpoint                The filesystem project endpoint
	 * @param consumer                The content consumer
	 * @param messagesConsumer        The messages consumer
	 * @param errorConsumer           The error consumer
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           consumption
	 */
	@Override
	protected void consumeImplementation(GFilesystemContentManagementSystem contentManagementConfig,
			List<GBuildSystem> buildSystems, GFilesystemProjectEndpoint endpoint,
			RemoteVirtualFileSystemContentConsumingSessionParam param, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {

		GVirtualFolder rootItem = createRootItem(contentManagementConfig, endpoint);
		consumer.accept(rootItem);
		if (endpoint.getPath() == null)
			return;
		for (VFilesystemReference entry : endpoint.getPath()) {
			Path file = null;
			file = Path.of(VFilesystemReference.absolutePathOf(entry));
			Predicate<Path> predicate = new Predicate<Path>() {
				@Override
				public boolean test(Path t) {

					return true;
				}
			};
			if (Files.exists(file)) {
				this.consume(rootItem, contentManagementConfig, buildSystems, endpoint, file, predicate, consumer,
						messagesConsumer, errorConsumer);
			} else {
				// a selected folder that is gone is told and counted as an error, and the
				// other selected paths are read
				LOGGER.error("The selected path " + file + " of the data source " + endpoint.getCode()
						+ " does not exist");
				errorConsumer.accept(ContentsAccessError.of(
						new GeboContentHandlerSystemException("The configured endpoint must lead to an existent directory"),
						ContentsAccessedObjectType.FOLDER, file.toString()));
			}
		}
	}

	/**
	 * Finds a project endpoint by its system and endpoint codes.
	 * 
	 * @param systemCode          The system code
	 * @param projectEndpointCode The project endpoint code
	 * @return The found filesystem project endpoint
	 * @throws GeboContentHandlerSystemException If the endpoint cannot be found
	 */
	@Override
	public GFilesystemProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {

		return this.endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Checks if the given endpoint is managed by this handler.
	 * 
	 * @param endpoint The project endpoint to check
	 * @return true if the endpoint is an instance of GFilesystemProjectEndpoint
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {

		return endpoint instanceof GFilesystemProjectEndpoint;
	}

	/**
	 * Gets the content management system for a given project endpoint.
	 * 
	 * @param projectEndPoint The project endpoint
	 * @return The filesystem content management system, or null if none exists
	 * @throws GeboContentHandlerSystemException If an error occurs while retrieving
	 *                                           the system
	 */
	@Override
	public GFilesystemContentManagementSystem getSystem(GFilesystemProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		List<GFilesystemContentManagementSystem> configs = this.configurationsDao.getConfigurations();
		return configs.isEmpty() ? null : configs.get(0);
	}

	/**
	 * Processes changed files by categorizing them as added, modified, or deleted
	 * and handling each category appropriately.
	 * 
	 * @param endpoint         The filesystem project endpoint
	 * @param contentConsumer  The content consumer
	 * @param messagesConsumer The messages consumer
	 * @param errorConsumer    The error consumer
	 * @param changedFiles     The set of changed files to process
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           processing
	 */
	@Override
	public void consume(GFilesystemProjectEndpoint endpoint, IGContentConsumer contentConsumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			ChangedFiles changedFiles) throws GeboContentHandlerSystemException {
		List<Path> added = new ArrayList<Path>();
		List<Path> modified = new ArrayList<Path>();
		List<Path> deleted = new ArrayList<Path>();
		Path root = changedFiles.getSourceDirectory().toPath();
		for (ChangedFile changedFile : changedFiles) {
			String internalPath = changedFile.getRelativeName();
			Path file = Path.of(root.toAbsolutePath().toString(), internalPath);

			switch (changedFile.getType()) {
			case ADD: {
				added.add(file);
			}
				break;
			case MODIFY: {
				modified.add(file);
			}
				break;
			case DELETE: {
				deleted.add(file);
			}
			}
		}
		consumeAddedFiles(endpoint, root, contentConsumer, messagesConsumer, errorConsumer, added);
		consumeModifiedFiles(endpoint, root, contentConsumer, messagesConsumer, errorConsumer, modified);
		consumeDeletedFiles(endpoint, root, contentConsumer, messagesConsumer, errorConsumer, deleted);
	}

	/**
	 * Processes deleted files by notifying the system of their removal.
	 * 
	 * @param endpoint         The filesystem project endpoint
	 * @param root             The root directory
	 * @param contentConsumer  The content consumer
	 * @param messagesConsumer The messages consumer
	 * @param errorConsumer    The error consumer
	 * @param deleted          List of deleted file paths
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           processing
	 */
	private void consumeDeletedFiles(GFilesystemProjectEndpoint endpoint, Path root, IGContentConsumer contentConsumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer, List<Path> deleted)
			throws GeboContentHandlerSystemException {
		for (Path file : deleted) {
			GVirtualFolder vfolder = consumeAndReturnParentVirtualFolder(endpoint, root, file, contentConsumer);
			consumeDeletedDocument(endpoint, vfolder, file, contentConsumer, messagesConsumer, errorConsumer);
		}
	}

	/**
	 * Processes modified files by updating their content in the system.
	 * 
	 * @param endpoint         The filesystem project endpoint
	 * @param root             The root directory
	 * @param contentConsumer  The content consumer
	 * @param messagesConsumer The messages consumer
	 * @param errorConsumer    The error consumer
	 * @param modified         List of modified file paths
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           processing
	 */
	private void consumeModifiedFiles(GFilesystemProjectEndpoint endpoint, Path root, IGContentConsumer contentConsumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer, List<Path> modified)
			throws GeboContentHandlerSystemException {
		for (Path file : modified) {
			GVirtualFolder vfolder = consumeAndReturnParentVirtualFolder(endpoint, root, file, contentConsumer);
			consumeDocument(endpoint, vfolder, file, contentConsumer, messagesConsumer, errorConsumer);
		}
	}

	/**
	 * Processes newly added files by adding them to the system.
	 * 
	 * @param endpoint         The filesystem project endpoint
	 * @param root             The root directory
	 * @param contentConsumer  The content consumer
	 * @param messagesConsumer The messages consumer
	 * @param errorConsumer    The error consumer
	 * @param added            List of added file paths
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           processing
	 */
	private void consumeAddedFiles(GFilesystemProjectEndpoint endpoint, Path root, IGContentConsumer contentConsumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer, List<Path> added)
			throws GeboContentHandlerSystemException {
		for (Path file : added) {
			GVirtualFolder vfolder = consumeAndReturnParentVirtualFolder(endpoint, root, file, contentConsumer);
			consumeDocument(endpoint, vfolder, file, contentConsumer, messagesConsumer, errorConsumer);
		}
	}

	/**
	 * The virtual folder a changed file belongs to, built as the publish builds it
	 * ({@link #consumeImplementation}): the root item of the endpoint, then one child
	 * item for each folder between the watched folder and the file, each consumed. A
	 * file directly in the watched folder belongs to the root item. So a file the
	 * watcher reports gets the same code as the publish gave it, and a modification or
	 * a deletion reaches the document the publish registered.
	 * 
	 * @param endpoint The filesystem project endpoint
	 * @param path     The watched folder the change was reported for: one of the
	 *                 endpoint's folders
	 * @param file     The changed file
	 * @param consumer The content consumer
	 * @return The parent virtual folder for the given file
	 * @throws GeboContentHandlerSystemException If an error occurs during folder
	 *                                           creation
	 */
	protected GVirtualFolder consumeAndReturnParentVirtualFolder(GFilesystemProjectEndpoint endpoint, Path path,
			Path file, IGContentConsumer consumer) throws GeboContentHandlerSystemException {
		GVirtualFolder rootItem = createRootItem(getSystem(endpoint), endpoint);
		consumer.accept(rootItem);
		GVirtualFolder parentFolder = rootItem;
		Path folder = path.toAbsolutePath().normalize();
		for (String name : foldersBetween(path, file)) {
			folder = folder.resolve(name);
			GVirtualFolder folderItem = createChildItem(parentFolder, endpoint, name, name, name);
			folderItem.setAbsolutePath(folder.toString());
			// a folder of a deleted file may be gone already
			if (Files.isDirectory(folder)) {
				try {
					folderItem.setModificationDate(new Date(Files.getLastModifiedTime(folder).toMillis()));
				} catch (Throwable t) {
				}
			}
			consumer.accept(folderItem);
			parentFolder = folderItem;
		}
		return parentFolder;
	}

	/**
	 * The names of the folders between a watched folder and a file in it, outermost
	 * first: none for a file directly in the watched folder, and none for a file
	 * outside it. Never an empty name: relativizing a folder to itself gives an empty
	 * path, which some Java versions count as an existing directory.
	 */
	static List<String> foldersBetween(Path watched, Path file) {
		final List<String> names = new ArrayList<String>();
		if (watched == null || file == null) {
			return names;
		}
		final Path base = watched.toAbsolutePath().normalize();
		final Path parent = file.toAbsolutePath().normalize().getParent();
		if (parent == null || !parent.startsWith(base)) {
			return names;
		}
		for (Path part : base.relativize(parent)) {
			final String name = part.toString();
			if (!name.isEmpty()) {
				names.add(name);
			}
		}
		return names;
	}

	/**
	 * Returns the messaging module ID for this handler.
	 * 
	 * @return The messaging module ID
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.SHARED_FILESYSTEM_MODULE;
	}

	@Override
	public boolean isContentsOnLocalFilesystem() {

		return true;
	}

}
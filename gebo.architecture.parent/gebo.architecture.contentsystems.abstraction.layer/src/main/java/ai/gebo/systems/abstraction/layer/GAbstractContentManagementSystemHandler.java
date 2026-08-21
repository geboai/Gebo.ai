/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.systems.abstraction.layer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GMessagingComponentRef;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowItem;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStructure;
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandler;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.patterns.IGRuntimeModuleComponent;
import ai.gebo.architecture.patterns.model.GModuleUseInfo;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.MInfoType;
import ai.gebo.architecture.patterns.model.GModuleUseInfo.ModuleType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GSoftwareArtifact;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.AbstractContentConsumingSessionParam;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.BuildSystemRef;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;
import jakarta.el.MethodNotFoundException;

public abstract class GAbstractContentManagementSystemHandler<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint, ContentConsumingSessionParamType extends AbstractContentConsumingSessionParam>
		implements
		IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType, ContentConsumingSessionParamType>,
		IGRuntimeModuleComponent {

	// Logger for the handler
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	// Repository for build system handlers
	protected IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository = null;

	// Factory for creating document references
	protected IGDocumentReferenceFactory contentHandler = null;

	// DAO for content management system configurations
	protected IGContentManagementSystemConfigurationDao<SystemIntegrationType> configurationsDao = null;

	// DAO for project endpoint runtime configurations
	protected IGProjectEndpointRuntimeConfigurationDao<ProjectEndpointType> endpointsDao = null;

	// Service for discovering local persistent folders
	protected IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService = null;

	// Manager for persistent objects
	protected IGPersistentObjectManager persistentObjectManager = null;

	// Looks up the GProject/GKnowledgeBase hierarchy a project endpoint hangs off of.
	// Field-injected (unlike the constructor-injected fields above) so adding it does
	// not ripple through every concrete subclass's constructor across every
	// content-handler module.
	@Autowired
	protected IGKnowledgeBaseHierarchyLookupService knowledgeBaseHierarchyLookupService;

	// Same field-injection rationale as knowledgeBaseHierarchyLookupService above.
	@Autowired
	protected IGSecurityAuditLoggerService securityAuditLoggerService;

	// Resolves the active STANDARD ingestion workflow so a data source can be linked
	// to the pipeline steps actually enabled for it. Held through an ObjectProvider
	// and resolved lazily at getDataFlowMetaInfos() call time, NOT injected eagerly:
	// the workflow subsystem depends transitively back on content handlers (via the
	// step-enabled handlers -> graph/chat/search services -> the remote-filesystem
	// search+consuming beans, which are themselves handlers), so a direct field
	// injection here forms a startup bean cycle. The compliance register is read
	// long after context refresh, so lazy resolution avoids the cycle while still
	// giving the live repository. Absent (compute-workflow not deployed) -> the
	// handler still reports its source endpoints, just without the connecting edges.
	@Autowired
	protected ObjectProvider<IWorkflowStatusHandlerRepositoryPattern> workflowStatusHandlerRepositoryProvider;

	// Message broker for system messaging
	protected IGMessageBroker messageBroker = null;

	// Handler for document reference ingestion
	protected IGDocumentReferenceIngestionHandler ingestionHandler = null;

	/**
	 * Constructor for initializing the content management system handler.
	 * 
	 * @param buildSystemHandlerRepository The repository pattern for build system
	 *                                     handlers.
	 * @param contentHandler               The factory for content handling.
	 * @param configurationsDao            The DAO for configurations.
	 * @param endpointsDao                 The DAO for endpoint configurations.
	 * @param localFolderDiscoveryService  Service for discovering local folders.
	 * @param persistentObjectManager      The manager for persistent objects.
	 * @param messageBroker                The message broker for the system.
	 * @param ingestionHandler             The ingestion handler for document
	 *                                     references.
	 */
	public GAbstractContentManagementSystemHandler(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,
			IGContentManagementSystemConfigurationDao<SystemIntegrationType> configurationsDao,
			IGProjectEndpointRuntimeConfigurationDao<ProjectEndpointType> endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		this.buildSystemHandlerRepository = buildSystemHandlerRepository;
		this.contentHandler = contentHandler;
		this.configurationsDao = configurationsDao;
		this.endpointsDao = endpointsDao;
		this.localFolderDiscoveryService = localFolderDiscoveryService;
		this.persistentObjectManager = persistentObjectManager;
		this.messageBroker = messageBroker;
		this.ingestionHandler = ingestionHandler;
	}

	/**
	 * Consumes the document by processing it, checking if it's an archive, and
	 * handling it accordingly.
	 *
	 * @param endpoint         The project endpoint.
	 * @param hierarchy        The virtual folder hierarchy.
	 * @param file             The file path.
	 * @param consumer         The content consumer.
	 * @param messagesConsumer The messages consumer.
	 * @param errorConsumer    The error consumer.
	 * @throws GeboContentHandlerSystemException If an error occurs during document
	 *                                           handling.
	 */
	protected void consumeDocument(ProjectEndpointType endpoint, GVirtualFolder hierarchy, Path file,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin extractDocument(....,file:" + file.toAbsolutePath().toString() + ")");
		}
		try {
			GDocumentReference document = contentHandler.createReference(file, hierarchy.getCode(), hierarchy.getUri(),
					null, endpoint, hierarchy, getMessagingModuleId(), getMessagingSystemId());
			document.setParentVirtualFolderCode(hierarchy.getCode());
			boolean manageArchive = endpoint.getOpenZips() != null && endpoint.getOpenZips();

			if (isArchive(document)) {
				if (manageArchive) {
					consumeArchive(document, endpoint, hierarchy, file, consumer, messagesConsumer, errorConsumer);
				}
			} else {
				consumer.accept(document);
			}
		} catch (Throwable th) {
			try {
				errorConsumer.accept(ContentsAccessError.of(th, ContentsAccessedObjectType.RESOURCE, file.toString()));
				messagesConsumer.accept(GUserMessage.errorMessage("Error managing: " + file.getFileName(), th));
			} catch (Throwable e) {
			}
			LOGGER.error("document extraction error", th);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End extractDocument(....,file:" + file.toAbsolutePath().toString() + ")");
		}
	}

	/**
	 * Determines if a given document reference is an archive.
	 *
	 * @param reference The document reference to check.
	 * @return true if the reference is an archive, otherwise false.
	 */
	protected boolean isArchive(GDocumentReference reference) {
		return isZip(reference);
	}

	/**
	 * Determines if a given document reference is a ZIP file.
	 *
	 * @param reference The document reference to check.
	 * @return true if the reference is a ZIP file, otherwise false.
	 */
	protected boolean isZip(GDocumentReference reference) {
		return reference.getExtension() != null && (reference.getExtension().equalsIgnoreCase(".zip")
				|| reference.getExtension().equalsIgnoreCase(".gz"));
	}

	/**
	 * Consumes and processes the content of a ZIP archive.
	 *
	 * @param document         The document reference.
	 * @param endpoint         The project endpoint.
	 * @param hierarchy        The folder hierarchy.
	 * @param file             The file path.
	 * @param consumer         The content consumer.
	 * @param messagesConsumer The messages consumer.
	 * @param errorConsumer    The error consumer.
	 * @throws GeboContentHandlerSystemException If an error occurs during archive
	 *                                           handling.
	 */
	protected void consumeArchive(GDocumentReference document, ProjectEndpointType endpoint, GVirtualFolder hierarchy,
			Path file, IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		GVirtualFolder baseFolder = new GVirtualFolder(document);
		baseFolder.setParentVirtualFolderCode(hierarchy.getParentVirtualFolderCode());
		String relativePath = hierarchy.getRelativePath();
		if (relativePath == null) {
			relativePath = "";
		}
		relativePath += "/" + document.getName();
		baseFolder.setRelativePath(relativePath);
		consumer.accept(baseFolder);
		try {
			if (isZip(document)) {
				consumer.accept(document);
				messagesConsumer
						.accept(GUserMessage.infoMessage("Analyzing archive:" + file.toAbsolutePath().toString(),
								"Trying opening archive and read contents"));
				final List<GBaseVersionableObject> contents = new ArrayList<GBaseVersionableObject>();
				final List<GUserMessage> userMessages = new ArrayList<GUserMessage>();
				final IGContentConsumer cachedConsumer = new IGContentConsumer() {
					@Override
					public void accept(GBaseVersionableObject t) {
						contents.add(t);
					}

					@Override
					public void endConsuming() {
					}
				};
				final IGUserMessagesConsumer cachedUserMessageConsumer = new IGUserMessagesConsumer() {
					@Override
					public void accept(GUserMessage t) {
						userMessages.add(t);
					}

					@Override
					public void endConsuming() {
					}
				};
				final ZipFile zipFile = new ZipFile(file.toAbsolutePath().toString());
				;
				try {
					Stream<? extends ZipEntry> stream = zipFile.stream();
					stream.forEach(x -> {
						try {
							consumeZipArchiveEntry(file, x, zipFile, endpoint, baseFolder, cachedConsumer,
									cachedUserMessageConsumer, errorConsumer);
						} catch (GeboContentHandlerSystemException e) {
							LOGGER.error("Error accessing archive " + file, e);
							messagesConsumer.accept(GUserMessage.errorMessage("Error reading archive:" + file, e));
						}
					});
				} finally {
					try {
						zipFile.close();
					} catch (Throwable th) {
					}
					for (GBaseVersionableObject c : contents) {
						try {
							consumer.accept(c);
						} catch (Throwable th) {
						}
					}
					for (GUserMessage um : userMessages) {
						try {
							messagesConsumer.accept(um);
						} catch (Throwable th) {
						}
					}
				}
			}
		} catch (Throwable e) {
			errorConsumer.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, file.toString()));
			LOGGER.error("Error accessing archive " + file, e);
			messagesConsumer.accept(GUserMessage.errorMessage("Error reading archive:" + file, e));
		}
	}

	/**
	 * Consumes a single entry within a ZIP archive.
	 *
	 * @param originalFile     The original file path.
	 * @param entry            The ZIP entry.
	 * @param zipFile          The ZIP file object.
	 * @param endpoint         The project endpoint.
	 * @param baseFolder       The base folder structure.
	 * @param consumer         The content consumer.
	 * @param messagesConsumer The messages consumer.
	 * @param errorConsumer    The error consumer.
	 * @throws GeboContentHandlerSystemException If an error occurs during entry
	 *                                           consumption.
	 */
	protected void consumeZipArchiveEntry(Path originalFile, ZipEntry entry, ZipFile zipFile,
			ProjectEndpointType endpoint, GVirtualFolder baseFolder, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		String path = entry.getName();
		if (entry.isDirectory()) {
			GVirtualFolder childFolder = createChildItem(baseFolder, endpoint, path, path, path);
			childFolder.setAbsolutePath(baseFolder.getAbsolutePath() + "/" + path);
			childFolder.setAbsoluteArchivePath(originalFile.toAbsolutePath().toString());
			childFolder.setArchiveInternalPath(path);
			childFolder.setNestedInArchive(true);
			childFolder.setRelativePath(baseFolder.getRelativePath() + "/" + path);
			consumer.accept(childFolder);
			messagesConsumer.accept(GUserMessage
					.infoMessage("Scanning archive subfolder: " + zipFile.getName() + " " + path, "scanning folder"));
		} else {
			try {
				messagesConsumer.accept(GUserMessage.infoMessage(
						"Analyzing archive:" + zipFile.getName() + " entry: " + entry.getName(),
						"Trying opening archive and read contents"));
				GDocumentReference document = contentHandler.createArchiveReference(originalFile, zipFile, entry,
						baseFolder.getCode(), endpoint, baseFolder, getMessagingModuleId(), getMessagingSystemId());
				document.setParentVirtualFolderCode(baseFolder.getCode());
				consumer.accept(document);
				messagesConsumer.accept(GUserMessage.successMessage(
						"Analyzed archive:" + zipFile.getName() + " entry: " + entry.getName(), "Done successfully"));
			} catch (Throwable e) {
				errorConsumer.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.RESOURCE, path));
				LOGGER.error("Error accessing archive " + originalFile.getFileName() + " entry: " + entry.getName(), e);
				messagesConsumer.accept(GUserMessage
						.errorMessage("Error reading archive:" + zipFile.getName() + " entry: " + entry.getName(), e));
			}
		}
	}

	/**
	 * Handles consumption of documents marked as deleted.
	 *
	 * @param endpoint         The project endpoint.
	 * @param hierarchy        The folder hierarchy.
	 * @param file             The file path.
	 * @param consumer         The content consumer.
	 * @param messagesConsumer The messages consumer.
	 * @param errorConsumer    The error consumer.
	 * @throws GeboContentHandlerSystemException If an error occurs during document
	 *                                           handling.
	 */
	protected void consumeDeletedDocument(ProjectEndpointType endpoint, GVirtualFolder hierarchy, Path file,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin extractDocument(....,file:" + file.toAbsolutePath() + ")");
		}
		try {
			GDocumentReference document = contentHandler.createDeletedReference(file, hierarchy.getCode(),
					hierarchy.getUri(), null, endpoint, getMessagingModuleId(), getMessagingSystemId());
			document.setParentVirtualFolderCode(hierarchy.getCode());

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End extractDocument(....,file:" + file.toAbsolutePath() + ")");
			}
			consumer.accept(document);
			messagesConsumer.accept(GUserMessage.successMessage("Managed deleted " + file.getFileName(),
					"Managed deleted file:" + file.getFileName() + " with code:" + document.getCode()));
		} catch (Throwable e) {
			try {
				LOGGER.error("Error handling deletion", e);
				messagesConsumer
						.accept(GUserMessage.errorMessage("Error handling deletion of " + file.toAbsolutePath(), e));
			} catch (Throwable e1) {
			}
		}
	}

	/**
	 * Retrieve the content management system associated with a project endpoint.
	 *
	 * @param projectEndPoint The project endpoint.
	 * @return The content management system.
	 * @throws GeboContentHandlerSystemException If retrieval fails.
	 */
	public abstract SystemIntegrationType getSystem(ProjectEndpointType projectEndPoint)
			throws GeboContentHandlerSystemException;

	/**
	 * Consumes content from a project endpoint, processing with a consumer and
	 * handling messages and errors.
	 * 
	 * @param consumer         The content consumer.
	 * @param messagesConsumer The messages consumer.
	 * @param errorConsumer    The error consumer.
	 * @param endpoint         The project endpoint.
	 *
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           consumption.
	 */
	@Override
	public void consume(ProjectEndpointType endpoint, ContentConsumingSessionParamType sessionParam,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		// Logged once per sync run (not per document/file) to avoid flooding the
		// audit log - consumeImplementation() below fans out to potentially
		// thousands of per-file reads during a single ingestion crawl.
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		try {
			consumeInternal(endpoint, sessionParam, consumer, messagesConsumer, errorConsumer);
			logDataAccessEvent(event, endpoint, SecurityAuditTaxonomy.Outcome.SUCCESS);
		} catch (RuntimeException | GeboContentHandlerSystemException e) {
			logDataAccessEvent(event, endpoint, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	// Takes an already-created SecurityEvent (never calls newSecurityEvent()
	// itself) so newSecurityEvent()'s caller-stack capture points at consume()
	// - the real API entry point - not at this shared helper.
	private void logDataAccessEvent(SecurityEvent event, ProjectEndpointType endpoint, String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.INTEGRATION_DATA_ACCESS);
		event.setCategory(SecurityAuditTaxonomy.Category.INTEGRATION_DATA_ACCESS);
		event.setAction(SecurityAuditTaxonomy.Action.INTEGRATION_DATA_CONSUME);
		event.setResourceId(endpoint != null ? endpoint.getCode() : null);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	private void consumeInternal(ProjectEndpointType endpoint, ContentConsumingSessionParamType sessionParam,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {

		SystemIntegrationType contentManagementSystem = getSystem(endpoint);
		List<GBuildSystem> buildSystems = new ArrayList<GBuildSystem>();
		if (endpoint.getBuildSystemsRefs() != null) {
			for (BuildSystemRef buildSystemConfig : endpoint.getBuildSystemsRefs()) {
				IGBuildSystemHandler handler = buildSystemHandlerRepository
						.findByCode(buildSystemConfig.getBuildSystemTypeCode());
				if (handler == null)
					throw new GeboContentHandlerSystemException(
							"Unknown build system type:" + buildSystemConfig.getBuildSystemTypeCode());
				GBuildSystem buildSystem = handler
						.findConfigurationByCode(buildSystemConfig.getBuildSystemConfigCode());
				buildSystems.add(buildSystem);
			}
		}

		consumeImplementation(contentManagementSystem, buildSystems, endpoint, sessionParam, consumer, messagesConsumer,
				errorConsumer);
	}

	/**
	 * Abstract method to implement the actual content consumption logic.
	 *
	 * @param contentManagementConfig The content management configuration.
	 * @param buildSystems            The list of build systems.
	 * @param endpoint                The project endpoint.
	 * @param sessionParam            TODO
	 * @param consumer                The content consumer.
	 * @param messagesConsumer        The messages consumer.
	 * @param errorConsumer           The error consumer.
	 * @throws GeboContentHandlerSystemException If an exception occurs during
	 *                                           implementation.
	 */
	abstract protected void consumeImplementation(SystemIntegrationType contentManagementConfig,
			List<GBuildSystem> buildSystems, ProjectEndpointType endpoint,
			ContentConsumingSessionParamType sessionParam, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException;

	/**
	 * Creates the root virtual folder item.
	 *
	 * @param contentManagementSystem The content management system.
	 * @param endpoint                The project endpoint.
	 * @return The root virtual folder item.
	 * @throws GeboContentHandlerSystemException If an error occurs during creation.
	 */
	protected GVirtualFolder createRootItem(SystemIntegrationType contentManagementSystem, ProjectEndpointType endpoint)
			throws GeboContentHandlerSystemException {
		GVirtualFolder rootItem = new GVirtualFolder();
		try {
			GProject project = knowledgeBaseHierarchyLookupService.findProjectByCode(endpoint.getParentProjectCode());
			rootItem.setCode(project.getRootKnowledgeBaseCode() + "/" + project.getCode() + "/" + endpoint.getCode());
			rootItem.setDescription(endpoint.getDescription());
			rootItem.setRootKnowledgebaseCode(project.getRootKnowledgeBaseCode());
			rootItem.setParentProjectCode(project.getCode());
			rootItem.setParentVirtualFolderCode(null);
			rootItem.setName(".");
			rootItem.setRelativePath("");
			rootItem.setProjectEndpointReference(GObjectRef.of(endpoint));
			rootItem.setParentVirtualFolderCode(null);
			rootItem.setMessagingModuleId(getMessagingSystemId());
			rootItem.setAclAliases(endpoint.getAclAliases());
			return rootItem;
		} catch (GeboPersistenceException e) {
			throw new GeboContentHandlerSystemException("exception in createRootItem(...)", e);
		}
	}

	/**
	 * Creates a child item under a parent virtual folder.
	 *
	 * @param parent   The parent virtual folder.
	 * @param endpoint The project endpoint.
	 * @param codePart The part for the code.
	 * @param name     The name of the child item.
	 * @param urlPart  The URL part.
	 * @return The created child virtual folder item.
	 * @throws GeboContentHandlerSystemException If an error occurs during creation.
	 */
	protected GVirtualFolder createChildItem(GVirtualFolder parent, ProjectEndpointType endpoint, String codePart,
			String name, String urlPart) throws GeboContentHandlerSystemException {
		GVirtualFolder childItem = new GVirtualFolder();
		childItem.setCode(parent.getCode() + "/" + codePart);
		childItem.setDescription(name);
		childItem.setRootKnowledgebaseCode(parent.getRootKnowledgebaseCode());
		childItem.setParentProjectCode(parent.getParentProjectCode());
		childItem.setParentVirtualFolderCode(parent.getCode());
		childItem.setName(name);
		childItem.setRelativePath((parent.getRelativePath() != null ? (parent.getRelativePath() + "/") : "") + urlPart);
		childItem.setProjectEndpointReference(GObjectRef.of(endpoint));
		childItem.setMessagingModuleId(getMessagingSystemId());
		childItem.setUri(parent.getUri() + "/" + urlPart);
		return childItem;
	}

	/**
	 * Consumes resources from the specified file path and processes them.
	 *
	 * @param actualItem               The current folder item.
	 * @param contentManagementSystem  The content management system.
	 * @param buildSystems             The list of build systems.
	 * @param endpoint                 The project endpoint.
	 * @param file                     The file path.
	 * @param handleFileCheckPredicate Predicate to check files.
	 * @param consumer                 The content consumer.
	 * @param messagesConsumer         The messages consumer.
	 * @param errorConsumer            The error consumer.
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           consumption.
	 */
	protected void consume(GVirtualFolder actualItem, SystemIntegrationType contentManagementSystem,
			List<GBuildSystem> buildSystems, ProjectEndpointType endpoint, Path file,
			Predicate<Path> handleFileCheckPredicate, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin extract(....,file:" + file.toAbsolutePath().toString() + ")");
		}

		if (Files.isDirectory(file)) {
			messagesConsumer.accept(GUserMessage.infoMessage("Analizyng folder:" + file.toString(),
					"Analizyng folder:" + file.toString() + " code:" + actualItem.getCode()));
			for (GBuildSystem gBuildSystem : buildSystems) {
				IGBuildSystemHandler buildSystem = buildSystemHandlerRepository
						.findByCode(gBuildSystem.getBuildSystemTypeCode());
				List<GDependencyTree> trees = buildSystem.extractDeliverablesMetaInfos(gBuildSystem, actualItem, file);
				if (trees != null) {
					trees.forEach(consumer);
					for (GDependencyTree t : trees) {
						GSoftwareArtifact artifact = new GSoftwareArtifact(t);
						consumer.accept(artifact);
					}
				}
				buildSystem.consumeCustomAdditionalMetaInformations(gBuildSystem, actualItem, file, consumer);
			}
			final List<Path> folders = new ArrayList<Path>();
			final List<Path> files = new ArrayList<Path>();
			try {
				DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file);
				directoryStream.forEach(x -> {
					if (Files.isDirectory(x)) {
						folders.add(x);
					} else {
						files.add(x);
					}
				});
			} catch (IOException e) {
				errorConsumer.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, file.toString()));
				throw new GeboContentHandlerSystemException("Cannot access path:" + file.toString(), e);
			}
			if (!files.isEmpty()) {
				for (Path actualfile : files) {
					consumeDocument(endpoint, actualItem, actualfile, consumer, messagesConsumer, errorConsumer);
				}
			}
			for (Path folder : folders) {
				String pathPart = folder.getFileName().toString();
				GVirtualFolder folderlItem = createChildItem(actualItem, endpoint, pathPart, pathPart, pathPart);
				folderlItem.setAbsolutePath(folder.toAbsolutePath().toString());
				try {
					FileTime mt = Files.getLastModifiedTime(folder);
					folderlItem.setModificationDate(new Date(mt.toMillis()));
				} catch (Throwable t) {
				}
				consumer.accept(folderlItem);
				consume(folderlItem, contentManagementSystem, buildSystems, endpoint, folder, handleFileCheckPredicate,
						consumer, messagesConsumer, errorConsumer);
			}
		} else if (Files.exists(file)) {
			consumeDocument(endpoint, actualItem, file, consumer, messagesConsumer, errorConsumer);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End extract(....,file:" + file.toAbsolutePath() + ")");
		}
	}

	/**
	 * Gets all configurations for the content management systems.
	 *
	 * @return A list of content management system configurations.
	 */
	public List<SystemIntegrationType> getConfigurations() {
		return new ArrayList(this.configurationsDao.getConfigurations());
	}

	/**
	 * Gets the component type of the system.
	 *
	 * @return The component type.
	 */
	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Accepts a message envelope, currently not implemented.
	 *
	 * @param t The message envelope.
	 */
	@Override
	public void accept(GMessageEnvelope t) {
		throw new MethodNotFoundException(
				"The call of accept(..) on class " + getClass().getName() + " has not been properly implemented");
	}

	/**
	 * Gets the list of accepted payload types.
	 *
	 * @return A list of string-based payload types.
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of();
	}

	/**
	 * Gets the messaging system ID.
	 *
	 * @return The messaging system ID.
	 */
	@Override
	public String getMessagingSystemId() {
		return "Content.Handler." + getHandledSystemType().getCode();
	}

	/**
	 * Determines whether every payload type is accepted.
	 *
	 * @return false, implying selective payload acceptance.
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	/**
	 * Streams content based on a document reference.
	 * 
	 * @param reference The document reference.
	 * @param cache     A cache of objects.
	 *
	 * @return An InputStream for the document content.
	 * @throws GeboContentHandlerSystemException If an error occurs during
	 *                                           streaming.
	 * @throws IOException                       If an IO exception occurs.
	 */
	@Override
	public TypedInputStream streamContent(StreamingPurpose streamingPurpose, GDocumentReference reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException, IOException {
		InputStream is = null;
		if (reference.getNestedInArchive() != null && reference.getNestedInArchive()) {
			final ZipFile zipFile = new ZipFile(reference.getAbsoluteArchivePath());
			ZipEntry entry = zipFile.getEntry(reference.getArchiveInternalPath());
			BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry)) {
				final ZipFile zip = zipFile;

				public void close() throws IOException {
					try {
						super.close();
					} catch (Throwable th) {
					}
					try {
						zip.close();
					} catch (Throwable th) {
					}
				};
			};
			is = zipFile.getInputStream(entry);
		} else if (reference.getArtificiallyGeneratedContent() != null
				&& reference.getArtificiallyGeneratedContent().trim().length() > 0) {
			is = new ByteArrayInputStream(reference.getArtificiallyGeneratedContent().getBytes());
		} else if (reference.getAbsolutePath() != null) {
			Path path = Path.of(reference.getAbsolutePath());
			is = Files.newInputStream(path, StandardOpenOption.READ);
		} else
			return null;
		return TypedInputStream.of(is, reference.getContentType(), reference.getExtension());
	}

	/**
	 * Gets module use information for the current system.
	 *
	 * @return A list of module use info.
	 */
	@Override
	public List<GModuleUseInfo> getModuleUseInfo() {
		List<GModuleUseInfo> modules = new ArrayList<GModuleUseInfo>();
		List<SystemIntegrationType> configs = getConfigurations();
		GContentManagementSystemType type = getHandledSystemType();
		GModuleUseInfo baseinfo = new GModuleUseInfo();
		baseinfo.setModuleId(getMessagingModuleId());
		baseinfo.setHandlerId(type.getCode());
		baseinfo.setInfoType(MInfoType.EXISTENCE);
		baseinfo.setModuleType(ModuleType.CONTENT);
		baseinfo.setUsed(false);
		List<ProjectEndpointType> cfgs = endpointsDao.getConfigurations();
		baseinfo.setUsed(cfgs.size() > 0);
		modules.add(baseinfo);
		TreeMap<String, GModuleUseInfo> uses = new TreeMap<String, GModuleUseInfo>();
		for (ProjectEndpointType projectEndpointType : cfgs) {
			try {
				SystemIntegrationType system = getSystem(projectEndpointType);
				String typeCode = system.getContentManagementSystemType();
				if (!uses.containsKey(typeCode)) {
					GModuleUseInfo thisInfo = new GModuleUseInfo(baseinfo);
					thisInfo.setSpecsCode(typeCode);
					uses.put(typeCode, thisInfo);
					modules.add(thisInfo);
				}
				GModuleUseInfo entry = uses.get(typeCode);
				entry.setConfigNumbers(entry.getConfigNumbers() + 1);
				entry.setUsed(true);
				entry.setInfoType(MInfoType.SETUP);
			} catch (GeboContentHandlerSystemException e) {
				LOGGER.error("Exception in module use stats", e);
			}
		}
		return modules;
	}

	/**
	 * Reports this content handler's <b>data sources</b> for the compliance
	 * register: one input endpoint per configured project endpoint, the point at
	 * which documents enter the installation.
	 *
	 * <p>
	 * The same two DAOs {@link #getModuleUseInfo()} already walks supply the data -
	 * {@code endpointsDao.getConfigurations()} for the configured endpoints and
	 * {@link #getSystem(GProjectEndpoint)} for the system each hangs off - so this
	 * is the base-class implementation shared by every content handler
	 * (git, filesystem, uploads, sharepoint, confluence, jira, aws-s3,
	 * googledrive, mcp-client, integration, userspace, webdav) at once.
	 * </p>
	 *
	 * <p>
	 * The locator is built from the system's {@code baseUri} and the endpoint code
	 * through {@link DataEndpoint#setEndpoint(String)}, so any credential embedded
	 * in the URI is stripped before it is stored. Locality is only asserted when
	 * it can be established from the locator - a corporate source over a real DNS
	 * name is left undetermined rather than guessed. Every source is marked as
	 * carrying personal data: ingested documents are in scope for the record of
	 * processing activities regardless of the connector.
	 * </p>
	 */
	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		List<ProjectEndpointType> endpoints = endpointsDao.getConfigurations();
		if (endpoints == null || endpoints.isEmpty()) {
			// A handler with no configured endpoint is not a data source yet; keep
			// it out of the register rather than showing an empty connector.
			return null;
		}
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));
		String product = getHandledSystemType() != null ? getHandledSystemType().getCode() : "content source";
		for (ProjectEndpointType endpoint : endpoints) {
			if (endpoint == null || endpoint.getCode() == null) {
				continue;
			}
			String baseUri = null;
			try {
				SystemIntegrationType system = getSystem(endpoint);
				if (system != null) {
					baseUri = system.getBaseUri();
				}
			} catch (GeboContentHandlerSystemException e) {
				LOGGER.error("Exception building data-flow source endpoint for " + endpoint.getCode(), e);
			}
			DataEndpoint source = new DataEndpoint();
			source.setId("source-" + endpoint.getCode());
			source.setDescription(endpoint.getDescription() != null ? endpoint.getDescription() : endpoint.getCode());
			source.setProduct(product);
			// baseUri may be null for connectors that carry no single address
			// (e.g. uploads); the endpoint code alone still identifies the source.
			source.setEndpoint(baseUri != null ? baseUri : product + ":" + endpoint.getCode());
			source.setInput(true);
			source.setTypes(new ArrayList<MetaEndpointType>(List.of(MetaEndpointType.DOCUMENTS)));
			source.setPersonalData(true);
			source.setLocality(DataEndpointLocality.hintFromLocator(source.getEndpoint()));
			flow.getDataEndpoints().add(source);
			addWorkflowLinks(flow, source, endpoint);
		}
		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	/**
	 * Connects this data source to the pipeline components it actually flows
	 * through, using the live workflow structure rather than a fixed assumption.
	 *
	 * <p>
	 * The STANDARD/INGESTION workflow ({@code GStandardWorkflowStep}) routes
	 * {@code DOCUMENT_DISCOVERY -> TOKENIZATION -> {EMBEDDING, GRAPHEXTRACTION,
	 * FULLTEXT_INDEXING}}, and which of the optional steps are on is decided per
	 * data source by {@code IWorkflowStepEnabledHandler}. {@code IWorkflowStatusHandler.getWorkflowStructure(...)}
	 * returns exactly that resolved tree for this source's {@link WorkflowContext},
	 * so the edges drawn here are the ones this endpoint is really wired to - not
	 * every step the platform could run.
	 * </p>
	 *
	 * <p>
	 * Each workflow step maps to a messaging component
	 * ({@code GStandardWorkflowStep.getTargetComponent()}); the destination
	 * endpoints are the ones those components publish into the same register
	 * (the tokenizer's chunk cache, the vectorizator's vector store, the
	 * full-text index). A downstream edge is emitted only when that component is
	 * actually running on this node ({@code messageBroker.checkReceivingComponentPresent}),
	 * so an enabled-but-undeployed step does not draw an edge to a store that
	 * is not there.
	 * </p>
	 */
	private void addWorkflowLinks(GDataFlowMetaInfos flow, DataEndpoint source, ProjectEndpointType endpoint) {
		IWorkflowStatusHandlerRepositoryPattern workflowStatusHandlerRepositoryPattern = workflowStatusHandlerRepositoryProvider != null
				? workflowStatusHandlerRepositoryProvider.getIfAvailable()
				: null;
		if (workflowStatusHandlerRepositoryPattern == null) {
			return;
		}
		WorkflowContext context = buildWorkflowContext(endpoint);
		if (context == null) {
			return;
		}
		List<IWorkflowStatusHandler> handlers = workflowStatusHandlerRepositoryPattern
				.findByWorkflowsTypeAndWorkflowId(GWorkflowType.STANDARD, GStandardWorkflow.INGESTION.name());
		if (handlers == null || handlers.isEmpty()) {
			return;
		}
		ComputedWorkflowStructure structure;
		try {
			structure = handlers.get(0).getWorkflowStructure(GWorkflowType.STANDARD.name(),
					GStandardWorkflow.INGESTION.name(), context);
		} catch (RuntimeException e) {
			LOGGER.error("Exception computing workflow structure for data-flow links of " + endpoint.getCode(), e);
			return;
		}
		if (structure == null || structure.getRootStep() == null) {
			return;
		}
		Set<String> enabledSteps = new HashSet<String>();
		collectEnabledSteps(structure.getRootStep(), enabledSteps);

		// TOKENIZATION is the mandatory first processing step; connecting the source
		// to it is what turns a floating data-source node into the head of a flow.
		// The target component comes from GStandardWorkflowStep itself - the same
		// enum the router uses to route the real message traffic - so this view and
		// the runtime pipeline can never disagree on where a step runs.
		GMessagingComponentRef tokenizer = GStandardWorkflowStep.TOKENIZATION.getTargetComponent();
		if (!enabledSteps.contains(GStandardWorkflowStep.TOKENIZATION.name()) || !messageBroker
				.checkReceivingComponentPresent(tokenizer.getModuleId(), tokenizer.getComponentId())) {
			return;
		}
		String chunkEndpointId = crossRef(tokenizer, "chunk-cache");
		DataTransformationMetaInfo ingestEngine = DataTransformationMetaInfo.of("ingest-" + endpoint.getCode(),
				"Document ingestion and chunking", List.of(MetaEndpointType.DOCUMENTS), List.of(MetaEndpointType.CHUNK));
		flow.getEngines().add(ingestEngine);
		flow.getTransformations().add(DataTransformationInfo.of("ingest-flow-" + endpoint.getCode(),
				"Documents from '" + source.getDescription() + "' are ingested and chunked", ingestEngine,
				flow.qualifiedId(source.getId()), chunkEndpointId));

		// Optional downstream steps: one edge from the chunk cache to each enabled,
		// deployed indexing store. Only the store's endpoint id is a local
		// convention here; the component that owns it is taken from the workflow
		// step's own target, not restated.
		addDownstreamLink(flow, endpoint, enabledSteps, chunkEndpointId, GStandardWorkflowStep.EMBEDDING,
				"vector-store", MetaEndpointType.VECTORIAL_DATABASE, "Embedding / semantic indexing");
		addDownstreamLink(flow, endpoint, enabledSteps, chunkEndpointId, GStandardWorkflowStep.FULLTEXT_INDEXING,
				"fulltext-index", MetaEndpointType.FULLTEXT_INDEX, "Full-text indexing");
		addDownstreamLink(flow, endpoint, enabledSteps, chunkEndpointId, GStandardWorkflowStep.GRAPHEXTRACTION,
				"graph-store", MetaEndpointType.GRAPH_DATABASE, "Knowledge-graph extraction");
	}

	private void addDownstreamLink(GDataFlowMetaInfos flow, ProjectEndpointType endpoint, Set<String> enabledSteps,
			String chunkEndpointId, GStandardWorkflowStep step, String endpointLocalId, MetaEndpointType producedType,
			String description) {
		GMessagingComponentRef target = step.getTargetComponent();
		if (!enabledSteps.contains(step.name())
				|| !messageBroker.checkReceivingComponentPresent(target.getModuleId(), target.getComponentId())) {
			return;
		}
		DataTransformationMetaInfo engine = DataTransformationMetaInfo.of(step.name().toLowerCase() + "-" + endpoint.getCode(),
				description, List.of(MetaEndpointType.CHUNK), List.of(producedType));
		flow.getEngines().add(engine);
		flow.getTransformations().add(DataTransformationInfo.of(step.name().toLowerCase() + "-flow-" + endpoint.getCode(),
				description + " of '" + endpoint.getCode() + "'", engine, chunkEndpointId,
				crossRef(target, endpointLocalId)));
	}

	/**
	 * Qualified id of an endpoint owned by the component a workflow step targets.
	 * The component identity comes from {@code GStandardWorkflowStep}; only the
	 * endpoint's local id is supplied by the caller.
	 */
	private String crossRef(GMessagingComponentRef component, String endpointLocalId) {
		return GDataFlowMetaInfos.qualifiedId(new GeboComponentInfo(component.getModuleId(), component.getComponentId()),
				endpointLocalId);
	}

	private void collectEnabledSteps(ComputedWorkflowItem item, Set<String> out) {
		if (item == null) {
			return;
		}
		if (item.isEnabledStep() && item.getWorkflowStepId() != null) {
			out.add(item.getWorkflowStepId().toUpperCase());
		}
		if (item.getChilds() != null) {
			for (ComputedWorkflowItem child : item.getChilds()) {
				collectEnabledSteps(child, out);
			}
		}
	}

	private WorkflowContext buildWorkflowContext(ProjectEndpointType endpoint) {
		try {
			String projectCode = endpoint.getParentProjectCode();
			if (projectCode == null) {
				return null;
			}
			GProject project = knowledgeBaseHierarchyLookupService.findProjectByCode(projectCode);
			String knowledgeBaseCode = project != null ? project.getRootKnowledgeBaseCode() : null;
			if (knowledgeBaseCode == null) {
				return null;
			}
			return new WorkflowContext(knowledgeBaseCode, projectCode, GObjectRef.of(endpoint));
		} catch (GeboPersistenceException e) {
			LOGGER.error("Exception building workflow context for " + endpoint.getCode(), e);
			return null;
		}
	}

	/**
	 * Modifies a virtual file system object as a child under a specified root
	 * folder.
	 *
	 * @param root                    The root folder.
	 * @param virtualFileSystemObject The virtual file system object.
	 */
	public void modifyAsChild(GVirtualFolder root, GAbstractVirtualFilesystemObject virtualFileSystemObject) {
		virtualFileSystemObject.setParentVirtualFolderCode(root.getCode());
		virtualFileSystemObject.setRelativePath(root.getRelativePath() + "/" + virtualFileSystemObject.getName());
		if (virtualFileSystemObject.getParentProjectCode() == null) {
			virtualFileSystemObject.setParentProjectCode(root.getParentProjectCode());
		}
		if (virtualFileSystemObject.getRootKnowledgebaseCode() == null) {
			virtualFileSystemObject.setRootKnowledgebaseCode(root.getRootKnowledgebaseCode());
		}
		if (virtualFileSystemObject.getProjectEndpointReference() == null) {
			virtualFileSystemObject.setProjectEndpointReference(root.getProjectEndpointReference());
		}
	}

	/**
	 * Check if items have been updated or deleted.
	 *
	 * @param endpoint      The project endpoint.
	 * @param itemsToCheck  The items to check.
	 * @param errorConsumer The error consumer.
	 * @return A stream of items to be checked.
	 * @throws GeboContentHandlerSystemException If an error occurs during the
	 *                                           check.
	 */
	@Override
	public Stream<GAbstractVirtualFilesystemObject> checkUpdatedOrDeleted(ProjectEndpointType endpoint,
			Stream<GAbstractVirtualFilesystemObject> itemsToCheck, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {

		return itemsToCheck.map(x -> {
			if (x.getAbsolutePath() != null && (x.getNestedInArchive() == null || !x.getNestedInArchive())) {
				Path path = Path.of(x.getAbsolutePath());
				if (Files.exists(path)) {
					x.setDeleted(false);
					try {
						FileTime mtime = Files.getLastModifiedTime(path);
						x.setModificationDate(Date.from(mtime.toInstant()));
						if (x instanceof GDocumentReference) {
							GDocumentReference r = (GDocumentReference) x;
							r.setFileSize(Files.size(path));
						}
					} catch (Throwable e) {
					}
				} else {
					x.setDeleted(true);
					x.setModificationDate(new Date());
				}
			}
			return x;
		});
	}

	/**
	 * Reads a document based on its reference.
	 *
	 * @param reference The document reference.
	 * @param cache     A cache for storing state.
	 * @return A GeboDocument object.
	 * @throws GeboContentHandlerSystemException If an error occurs during reading.
	 * @throws IOException                       If an IO exception occurs.
	 * @throws GeboIngestionException            If an ingestion error occurs.
	 */
	@Override
	public GeboDocument readDocument(GDocumentReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException {
		if (ingestionHandler.isHandled(reference)) {
			TypedInputStream stream = streamContent(StreamingPurpose.SERVING, reference, cache);
			if (stream == null || stream.getInputStream() == null)
				return null;
			return ingestionHandler.handleDocument(reference, stream != null ? stream.getInputStream() : null);
		}
		return null;
	}
}
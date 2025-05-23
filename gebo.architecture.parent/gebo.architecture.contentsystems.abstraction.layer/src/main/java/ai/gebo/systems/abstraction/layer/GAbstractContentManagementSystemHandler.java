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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandler;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
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
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.BuildSystemRef;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;
import jakarta.el.MethodNotFoundException;

public abstract class GAbstractContentManagementSystemHandler<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
        implements IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType>, IGRuntimeModuleComponent {

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
    
    // Message broker for system messaging
    protected IGMessageBroker messageBroker = null;
    
    // Handler for document reference ingestion
    protected IGDocumentReferenceIngestionHandler ingestionHandler = null;

    /**
     * Constructor for initializing the content management system handler.
     * 
     * @param buildSystemHandlerRepository       The repository pattern for build system handlers.
     * @param contentHandler                     The factory for content handling.
     * @param configurationsDao                  The DAO for configurations.
     * @param endpointsDao                       The DAO for endpoint configurations.
     * @param localFolderDiscoveryService        Service for discovering local folders.
     * @param persistentObjectManager            The manager for persistent objects.
     * @param messageBroker                      The message broker for the system.
     * @param ingestionHandler                   The ingestion handler for document references.
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
     * Consumes the document by processing it, checking if it's an archive, and handling it accordingly.
     *
     * @param endpoint          The project endpoint.
     * @param hierarchy         The virtual folder hierarchy.
     * @param file              The file path.
     * @param consumer          The content consumer.
     * @param messagesConsumer  The messages consumer.
     * @param errorConsumer     The error consumer.
     * @throws GeboContentHandlerSystemException If an error occurs during document handling.
     */
    protected void consumeDocument(ProjectEndpointType endpoint, GVirtualFolder hierarchy, Path file,
                                   IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
                                   IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Begin extractDocument(....,file:" + file.toAbsolutePath().toString() + ")");
        }
        try {
            GDocumentReference document = contentHandler.createReference(file, hierarchy.getCode(), hierarchy.getUri(),
                    null, endpoint, hierarchy, getMessagingModuleId());
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
     * @throws GeboContentHandlerSystemException If an error occurs during archive handling.
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
                messagesConsumer.accept(GUserMessage.infoMessage("Analyzing archive:" + file.toAbsolutePath().toString(),
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
     * @param originalFile  The original file path.
     * @param entry         The ZIP entry.
     * @param zipFile       The ZIP file object.
     * @param endpoint      The project endpoint.
     * @param baseFolder    The base folder structure.
     * @param consumer      The content consumer.
     * @param messagesConsumer The messages consumer.
     * @param errorConsumer The error consumer.
     * @throws GeboContentHandlerSystemException If an error occurs during entry consumption.
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
                        baseFolder.getCode(), endpoint, baseFolder, getMessagingModuleId());
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
     * @param endpoint          The project endpoint.
     * @param hierarchy         The folder hierarchy.
     * @param file              The file path.
     * @param consumer          The content consumer.
     * @param messagesConsumer  The messages consumer.
     * @param errorConsumer     The error consumer.
     * @throws GeboContentHandlerSystemException If an error occurs during document handling.
     */
    protected void consumeDeletedDocument(ProjectEndpointType endpoint, GVirtualFolder hierarchy, Path file,
                                          IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
                                          IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Begin extractDocument(....,file:" + file.toAbsolutePath() + ")");
        }
        try {
            GDocumentReference document = contentHandler.createDeletedReference(file, hierarchy.getCode(),
                    hierarchy.getUri(), null, endpoint, getMessagingModuleId());
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
     * Consumes content from a project endpoint, processing with a consumer and handling messages and errors.
     *
     * @param endpoint         The project endpoint.
     * @param consumer         The content consumer.
     * @param messagesConsumer The messages consumer.
     * @param errorConsumer    The error consumer.
     * @throws GeboContentHandlerSystemException If an error occurs during consumption.
     */
    @Override
    public void consume(ProjectEndpointType endpoint, IGContentConsumer consumer,
                        IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
            throws GeboContentHandlerSystemException {

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

        consumeImplementation(contentManagementSystem, buildSystems, endpoint, consumer, messagesConsumer,
                errorConsumer);
    }

    /**
     * Abstract method to implement the actual content consumption logic.
     *
     * @param contentManagementConfig The content management configuration.
     * @param buildSystems            The list of build systems.
     * @param endpoint                The project endpoint.
     * @param consumer                The content consumer.
     * @param messagesConsumer        The messages consumer.
     * @param errorConsumer           The error consumer.
     * @throws GeboContentHandlerSystemException If an exception occurs during implementation.
     */
    abstract protected void consumeImplementation(SystemIntegrationType contentManagementConfig,
                                                  List<GBuildSystem> buildSystems, ProjectEndpointType endpoint, IGContentConsumer consumer,
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
            GProject project = persistentObjectManager.findById(GProject.class, endpoint.getParentProjectCode());
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
     * @param actualItem           The current folder item.
     * @param contentManagementSystem The content management system.
     * @param buildSystems         The list of build systems.
     * @param endpoint             The project endpoint.
     * @param file                 The file path.
     * @param handleFileCheckPredicate Predicate to check files.
     * @param consumer             The content consumer.
     * @param messagesConsumer     The messages consumer.
     * @param errorConsumer        The error consumer.
     * @throws GeboContentHandlerSystemException If an error occurs during consumption.
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
     * @return An InputStream for the document content.
     * @throws GeboContentHandlerSystemException If an error occurs during streaming.
     * @throws IOException                       If an IO exception occurs.
     */
    @Override
    public InputStream streamContent(GDocumentReference reference, Map<String, Object> cache)
            throws GeboContentHandlerSystemException, IOException {
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
            return zipFile.getInputStream(entry);
        } else if (reference.getArtificiallyGeneratedContent() != null
                && reference.getArtificiallyGeneratedContent().trim().length() > 0) {
            return new ByteArrayInputStream(reference.getArtificiallyGeneratedContent().getBytes());
        } else if (reference.getAbsolutePath() != null) {
            Path path = Path.of(reference.getAbsolutePath());
            return Files.newInputStream(path, StandardOpenOption.READ);
        } else
            return null;
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
     * Modifies a virtual file system object as a child under a specified root folder.
     *
     * @param root                 The root folder.
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
     * @param endpoint       The project endpoint.
     * @param itemsToCheck   The items to check.
     * @param errorConsumer  The error consumer.
     * @return A stream of items to be checked.
     * @throws GeboContentHandlerSystemException If an error occurs during the check.
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
            InputStream stream = streamContent(reference, cache);
            return ingestionHandler.handleDocument(reference, stream);
        }
        return null;
    }
}
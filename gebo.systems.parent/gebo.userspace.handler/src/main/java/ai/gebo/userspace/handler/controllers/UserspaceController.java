/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.userspace.handler.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import ai.gebo.userspace.handler.GUserspaceContentManagementSystem;
import ai.gebo.userspace.handler.GUserspaceFile;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.dto.UserspaceFileDto;
import ai.gebo.userspace.handler.dto.UserspaceFolderDto;
import ai.gebo.userspace.handler.dto.UserspaceKnowledgebaseDto;
import ai.gebo.userspace.handler.repository.UserspaceFileRepository;
import ai.gebo.userspace.handler.repository.UserspaceProjectEndpointRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Controller for managing user space operations, including knowledgebases,
 * folders, and files. AI generated comments
 */
@RestController
@RequestMapping(value = "api/user/UserspaceController")
public class UserspaceController
		extends GAbstractSystemsArchitectureController<GUserspaceContentManagementSystem, GUserspaceProjectEndpoint> {

	/**
	 * Nested emitter component for userspace controller messaging
	 */
	@Component
	@Scope("singleton")
	public static class UserspaceControllerEmitter extends ControllerNestedEmitter {

		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.USERSPACE_MODULE;
		}
	}

	final IGLocalPersistentFolderDiscoveryService localFolderService;
	final IGUserspaceContentManagementSystemHandler handler;
	final DocumentReferenceRepository documentReferenceRepository;
	final ProjectRepository projectRepository;
	final UserspaceProjectEndpointRepository endpointRepository;
	final UserspaceFileRepository filesRepository;
	final IGGeboIngestionJobQueueService jobQueueService;

	/**
	 * Constructor for UserspaceController with dependency injection
	 */
	public UserspaceController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			UserspaceControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			IGLocalPersistentFolderDiscoveryService localFolderService,
			IGUserspaceContentManagementSystemHandler handler, DocumentReferenceRepository documentReferenceRepository,
			ProjectRepository projectRepository, UserspaceProjectEndpointRepository endpointRepository,
			UserspaceFileRepository filesRepository, IGGeboIngestionJobQueueService jobQueueService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.localFolderService = localFolderService;
		this.handler = handler;
		this.documentReferenceRepository = documentReferenceRepository;
		this.projectRepository = projectRepository;
		this.endpointRepository = endpointRepository;
		this.filesRepository = filesRepository;
		this.jobQueueService = jobQueueService;
	}

	@Autowired
	IGKnowledgebaseVisibilityService knowledgeBasesVisibilityService;

	/**
	 * Lists child personal knowledgebases for the given parent knowledgebase codes
	 * 
	 * @param codes List of parent knowledgebase codes
	 * @return List of knowledgebase DTOs
	 */
	@PostMapping(value = "listChildPersonalKnowledgebases", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<UserspaceKnowledgebaseDto> listChildPersonalKnowledgebases(@RequestBody List<String> codes) {
		List<GKnowledgeBase> visibles = knowledgeBasesVisibilityService.visiblesAndChildKnowledgebases(codes);
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		return visibles.stream().map(k -> {
			UserspaceKnowledgebaseDto i = new UserspaceKnowledgebaseDto();
			i.code = k.getCode();
			i.description = k.getDescription();
			i.parentKnowledgebaseCode = k.getParentKnowledgebaseCode();
			if (k.getUsername() != null && k.getUsername().equals(owner)) {
				i.owned = true;
			}
			return i;
		}).toList();
	}

	/**
	 * Retrieves all personal knowledgebases for the current user
	 * 
	 * @return List of knowledgebase DTOs
	 */
	@GetMapping(value = "getPersonalKnowledgebases", consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<UserspaceKnowledgebaseDto> getPersonalKnowledgebases() {
		List<GKnowledgeBase> kbs = knowledgeBasesVisibilityService.getPersonalKnowledgebases();
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		return kbs.stream().map(k -> {
			UserspaceKnowledgebaseDto i = new UserspaceKnowledgebaseDto();
			i.code = k.getCode();
			i.description = k.getDescription();
			if (k.getUsername() != null && k.getUsername().equals(owner)) {
				i.owned = true;
			}
			return i;
		}).toList();
	}

	/**
	 * Creates a new user knowledgebase
	 * 
	 * @param object The knowledgebase DTO with details to create
	 * @return The created knowledgebase DTO with assigned code
	 * @throws GeboPersistenceException If there's an error saving to the database
	 */
	@PostMapping(value = "newUserKnowledgebase", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserspaceKnowledgebaseDto newUserKnowledgebase(@NotNull @Valid @RequestBody UserspaceKnowledgebaseDto object)
			throws GeboPersistenceException {
		final UserInfos user = securityService.getCurrentUser();
		if (object.code != null)
			throw new RuntimeException("Cannot insert an object having a code");
		GKnowledgeBase knowledgeBase = new GKnowledgeBase();
		knowledgeBase.setObjectSpaceType(ObjectSpaceType.USERSPACE);
		knowledgeBase.setParentKnowledgebaseCode(object.parentKnowledgebaseCode);
		knowledgeBase.setDescription(object.description);
		knowledgeBase.setUsername(user.getUsername());
		knowledgeBase.setAccessibleToAll(false);
		knowledgeBase.setAccessibleUsers(List.of(user.getUsername()));
		knowledgeBase.setAccessibleGroups(object.accessibleGroups);
		knowledgeBase = persistentObjectManager.insert(knowledgeBase);
		UserspaceKnowledgebaseDto i = new UserspaceKnowledgebaseDto();
		i.code = knowledgeBase.getCode();
		i.description = knowledgeBase.getDescription();
		i.parentKnowledgebaseCode = knowledgeBase.getParentKnowledgebaseCode();
		i.owned = true;
		return i;
	}

	/**
	 * Finds a user knowledgebase by its code
	 * 
	 * @param code The knowledgebase code to search for
	 * @return The knowledgebase DTO if found and accessible
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */
	@GetMapping(value = "findUserKnowledgebaseByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserspaceKnowledgebaseDto findUserKnowledgebaseByCode(@NotNull @RequestParam("code") String code)
			throws GeboPersistenceException {
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		GKnowledgeBase knowledgebase = persistentObjectManager.findById(GKnowledgeBase.class, code);
		if (securityService.isCanAccess(knowledgebase, false)) {
			UserspaceKnowledgebaseDto i = new UserspaceKnowledgebaseDto();
			i.code = knowledgebase.getCode();
			i.description = knowledgebase.getDescription();
			i.parentKnowledgebaseCode = knowledgebase.getParentKnowledgebaseCode();
			i.owned = owner != null && knowledgebase.getUsername() != null && knowledgebase.getUsername().equals(owner);
			return i;
		} else
			throw new RuntimeException("The knowledge base addressed is not in your user space");
	}

	/**
	 * Updates an existing user knowledgebase
	 * 
	 * @param object The knowledgebase DTO with updated information
	 * @return The updated knowledgebase DTO
	 * @throws GeboPersistenceException If there's an error saving to the database
	 */
	@PostMapping(value = "updateUserKnowledgebase", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserspaceKnowledgebaseDto updateUserKnowledgebase(
			@NotNull @Valid @RequestBody UserspaceKnowledgebaseDto object) throws GeboPersistenceException {
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		if (object.code == null)
			throw new RuntimeException("Cannot update an object not having a code");
		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class, object.code);
		if (!(knowledgeBase.getUsername() != null && knowledgeBase.getUsername().equals(owner))) {
			throw new RuntimeException("Cannot update an object not owned");
		}
		knowledgeBase.setObjectSpaceType(ObjectSpaceType.USERSPACE);
		knowledgeBase.setDescription(object.description);
		knowledgeBase.setAccessibleGroups(object.accessibleGroups);
		knowledgeBase = persistentObjectManager.update(knowledgeBase);
		UserspaceKnowledgebaseDto i = new UserspaceKnowledgebaseDto();
		i.code = knowledgeBase.getCode();
		i.description = knowledgeBase.getDescription();
		i.parentKnowledgebaseCode = knowledgeBase.getParentKnowledgebaseCode();
		i.owned = true;
		return i;
	}

	/**
	 * Deletes a user knowledgebase
	 * 
	 * @param object The knowledgebase DTO to delete
	 * @throws GeboPersistenceException If there's an error deleting from the
	 *                                  database
	 */
	@PostMapping(value = "deleteUserKnowledgebase", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserKnowledgebase(@NotNull @Valid @RequestBody UserspaceKnowledgebaseDto object)
			throws GeboPersistenceException {
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		if (object.code == null)
			throw new RuntimeException("Cannot update an object not having a code");
		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class, object.code);
		if (!(knowledgeBase.getUsername() != null && knowledgeBase.getUsername().equals(owner))) {
			throw new RuntimeException("Cannot update an object not owned");
		}
	}

	/**
	 * Creates a new folder in the userspace
	 * 
	 * @param folderdto The folder DTO with details to create
	 * @return The created folder DTO with assigned codes
	 * @throws GeboPersistenceException If there's an error saving to the database
	 */
	@PostMapping(value = "newUserspaceFolder", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserspaceFolderDto newUserspaceFolder(@NotNull @Valid @RequestBody UserspaceFolderDto folderdto)
			throws GeboPersistenceException {
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		String knowledgeBaseCode = folderdto.parentUserspaceKnowledgebaseCode;
		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class, knowledgeBaseCode);
		if (!(knowledgeBase.getUsername() != null && knowledgeBase.getUsername().equals(owner))) {
			throw new RuntimeException("Cannot update an object not owned");
		}
		GProject project = new GProject();
		project.setRootKnowledgeBaseCode(knowledgeBaseCode);
		project.setDescription(folderdto.description);
		project.setObjectSpaceType(ObjectSpaceType.USERSPACE);
		project = persistentObjectManager.insert(project);
		GUserspaceProjectEndpoint endpoint = new GUserspaceProjectEndpoint();
		endpoint.setDescription("uploaded contents");
		endpoint.setParentProjectCode(project.getCode());
		endpoint.setUsername(owner);
		endpoint.setObjectSpaceType(ObjectSpaceType.USERSPACE);
		endpoint = this.insertEndpoint(endpoint);
		UserspaceFolderDto outdto = new UserspaceFolderDto();
		outdto.description = project.getDescription();
		outdto.code = project.getCode();
		outdto.parentUserspaceKnowledgebaseCode = project.getRootKnowledgeBaseCode();
		outdto.uploadCode = endpoint.getCode();
		return outdto;
	}

	/**
	 * Finds a userspace folder by its code
	 * 
	 * @param code The folder code to search for
	 * @return The folder DTO if found
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */
	@GetMapping(value = "findUserspaceFolderByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserspaceFolderDto findUserspaceFolderByCode(@NotNull @RequestParam("code") String code)
			throws GeboPersistenceException {
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		GProject project = persistentObjectManager.findById(GProject.class, code);
		if (project == null)
			return null;
		UserspaceFolderDto folder = new UserspaceFolderDto();
		folder.code = project.getCode();
		folder.description = project.getDescription();
		folder.parentUserspaceKnowledgebaseCode = project.getRootKnowledgeBaseCode();
		folder.owner = owner != null && project.getUserCreated() != null && project.getUserCreated().equals(owner);
		List<GUserspaceProjectEndpoint> endpoints = endpointRepository.findByParentProjectCode(code);
		if (endpoints.isEmpty()) {
			return null;
		}
		folder.uploadCode = endpoints.get(0).getCode();
		return folder;
	}

	/**
	 * Updates an existing userspace folder
	 * 
	 * @param folderdto The folder DTO with updated information
	 * @return The updated folder DTO
	 * @throws GeboPersistenceException If there's an error saving to the database
	 */
	@PostMapping(value = "updateUserspaceFolder", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserspaceFolderDto updateUserspaceFolder(@NotNull @Valid @RequestBody UserspaceFolderDto folderdto)
			throws GeboPersistenceException {
		if (folderdto.code == null || folderdto.uploadCode == null
				|| folderdto.parentUserspaceKnowledgebaseCode == null) {
			throw new RuntimeException("Folder with null code or uploadCode or parentUserspaceKnowledgebaseCode");
		}
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();

		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class,
				folderdto.parentUserspaceKnowledgebaseCode);
		if (knowledgeBase == null) {
			throw new RuntimeException("Knowledge base does not exist");
		}
		if (!(knowledgeBase.getUsername() != null && knowledgeBase.getUsername().equals(owner))) {
			throw new RuntimeException("Cannot update an object not owned");
		}
		GProject pj = persistentObjectManager.findById(GProject.class, folderdto.code);
		GUserspaceProjectEndpoint endpoint = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folderdto.uploadCode);
		pj.setDescription(folderdto.description);
		persistentObjectManager.update(pj);
		return folderdto;
	}

	/**
	 * Deletes a userspace folder
	 * 
	 * @param folderdto The folder DTO to delete
	 * @throws GeboPersistenceException If there's an error deleting from the
	 *                                  database
	 */
	@PostMapping(value = "deleteUserspaceFolder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserspaceFolder(@NotNull @Valid @RequestBody UserspaceFolderDto folderdto)
			throws GeboPersistenceException {
		if (folderdto.code == null || folderdto.uploadCode == null
				|| folderdto.parentUserspaceKnowledgebaseCode == null) {
			throw new RuntimeException("Folder with null code or uploadCode or parentUserspaceKnowledgebaseCode");
		}
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();

		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class,
				folderdto.parentUserspaceKnowledgebaseCode);
		if (knowledgeBase == null) {
			throw new RuntimeException("Knowledge base does not exist");
		}
		if (!(knowledgeBase.getUsername() != null && knowledgeBase.getUsername().equals(owner))) {
			throw new RuntimeException("Cannot update an object not owned");
		}
		GProject pj = persistentObjectManager.findById(GProject.class, folderdto.code);
		GUserspaceProjectEndpoint endpoint = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folderdto.uploadCode);
	}

	/**
	 * Lists all folders in a userspace knowledgebase
	 * 
	 * @param userspaceKnowledgeBase The knowledgebase code to list folders from
	 * @return List of folder DTOs
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */
	@GetMapping(value = "listUserspaceFolders", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserspaceFolderDto> listUserspaceFolders(
			@RequestParam("userspaceKnowledgeBase") String userspaceKnowledgeBase) throws GeboPersistenceException {
		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class, userspaceKnowledgeBase);
		if (!securityService.isCanAccess(knowledgeBase, false)) {
			throw new RuntimeException("Cannot access this userspace");
		}
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		final TreeMap<String, UserspaceFolderDto> folders = new TreeMap<String, UserspaceFolderDto>();
		Stream<GProject> projects = projectRepository.findByRootKnowledgeBaseCode(userspaceKnowledgeBase);
		projects.forEach(x -> {
			UserspaceFolderDto folder = new UserspaceFolderDto();
			folder.code = x.getCode();
			folder.description = x.getDescription();
			folder.parentUserspaceKnowledgebaseCode = userspaceKnowledgeBase;
			folder.owner = x.getUserCreated() != null && x.getUserCreated().equals(owner);
			List<GUserspaceProjectEndpoint> endpoints = endpointRepository.findByParentProjectCode(folder.code);
			if (!endpoints.isEmpty()) {
				folder.uploadCode = endpoints.get(0).getCode();
				folders.put(folder.code, folder);
			}
		});
		return new ArrayList<UserspaceFolderDto>(folders.values());
	}

	/**
	 * Lists all files in a userspace upload location
	 * 
	 * @param userspaceUploadCode The upload endpoint code
	 * @return List of file DTOs
	 * @throws GeboPersistenceException          If there's an error accessing the
	 *                                           database
	 * @throws GeboContentHandlerSystemException If there's an error with the
	 *                                           content handler
	 * @throws IOException                       If there's an error reading from
	 *                                           the filesystem
	 */
	@GetMapping(value = "listUserspaceFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserspaceFileDto> listUserspaceFiles(@RequestParam("userspaceUploadCode") String userspaceUploadCode)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {

		GUserspaceProjectEndpoint endpoint = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				userspaceUploadCode);
		String folder = localFolderService.getLocalPersistentFolder(handler.getSystem(endpoint), endpoint);
		final TreeMap<String, UserspaceFileDto> list = new TreeMap<String, UserspaceFileDto>();
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		Path path = Path.of(folder);

		final Map<String, GUserspaceFile> mappedFiles = new HashMap<String, GUserspaceFile>();
		final List<GUserspaceFile> files = this.filesRepository.findByUserspaceEndpointCode(userspaceUploadCode);
		files.forEach(x -> {
			mappedFiles.put(x.getName(), x);
		});
		if (Files.exists(path) && Files.isDirectory(path) && Files.isReadable(path)) {
			Stream<Path> paths = Files.list(path);
			paths.forEach(x -> {
				if (x.getFileName() != null) {
					UserspaceFileDto userspacefile = new UserspaceFileDto();
					userspacefile.name = x.getFileName().toString();
					int lastDot = userspacefile.name.lastIndexOf(".");
					userspacefile.extension = lastDot >= 0 ? userspacefile.name.substring(lastDot).toLowerCase() : null;
					userspacefile.parentUserspaceUploadCode = endpoint.getCode();
					userspacefile.owner = false;
					File file = x.toFile();
					if (file != null) {
						userspacefile.size = file.length();
						userspacefile.modificationTime = file.lastModified() > 0 ? new Date(file.lastModified()) : null;
					}
					GUserspaceFile mappedFile = mappedFiles.get(userspacefile.name);
					if (mappedFile != null && owner != null && mappedFile.getUserCreated() != null) {
						userspacefile.owner = owner.equals(mappedFile.getUserCreated());
					}
					list.put(userspacefile.name, userspacefile);
				}
			});
		}
		Stream<GDocumentReference> documents = documentReferenceRepository.findByProjectEndpoint(endpoint);
		documents.forEach(doc -> {
			if (doc.getName() != null) {
				UserspaceFileDto usfile = list.get(doc.getName());
				if (usfile != null) {
					usfile.code = doc.getCode();
					usfile.processed = true;
				}
			}
		});
		List<GUserspaceFile> filesObjects = filesRepository.findByUserspaceEndpointCode(endpoint.getCode());
		filesObjects.forEach(x -> {
			if (x.getName() != null) {
				UserspaceFileDto usfile = list.get(x.getName());
				if (usfile != null) {
					usfile.owner = x.getUserCreated() != null && x.getUserCreated().equals(owner);
				}
			}
		});
		return new ArrayList<UserspaceFileDto>(list.values());
	}

	/**
	 * Deletes userspace files
	 * 
	 * @param files The list of file DTOs to delete
	 */
	@PostMapping(value = "deleteUserspaceFiles", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserspaceFiles(@NotNull @Valid @RequestBody List<UserspaceFileDto> files) {
		// Method implementation is empty
	}

	/**
	 * Class representing the publishing status of a folder
	 */
	public static class PublishingStatus {
		public boolean underPubishingAlgorithm = false;
		public boolean hasBeenPublished = false;
		public String jobId = null;
		@NotNull
		public UserspaceFolderDto folder = null;
	}

	/**
	 * Gets the publishing status of a folder
	 * 
	 * @param folder The folder DTO to check status for
	 * @return The publishing status
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */
	@PostMapping(value = "getPublishingStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PublishingStatus getPublishingStatus(@NotNull @Valid @RequestBody UserspaceFolderDto folder)
			throws GeboPersistenceException {
		GProject project = persistentObjectManager.findById(GProject.class, folder.code);
		GKnowledgeBase knowledgebase = persistentObjectManager.findById(GKnowledgeBase.class,
				folder.parentUserspaceKnowledgebaseCode);
		if (!securityService.isCanAccess(knowledgebase, false)) {
			throw new RuntimeException("Cannot modify or upload on this knowledge base");
		}
		GUserspaceProjectEndpoint endpoint = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folder.uploadCode);
		boolean running = this.jobQueueService.isRunningSyncJob(endpoint);
		PublishingStatus status = new PublishingStatus();
		status.folder = folder;
		status.hasBeenPublished = endpoint.getPublished() != null && endpoint.getPublished();
		status.underPubishingAlgorithm = running;
		return status;
	}

	/**
	 * Publishes a folder to make its content available
	 * 
	 * @param folder The folder DTO to publish
	 * @return Operation status with publishing information
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */
	@PostMapping(value = "publishFolder", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<PublishingStatus> publishFolder(@NotNull @Valid @RequestBody UserspaceFolderDto folder)
			throws GeboPersistenceException {
		GProject project = persistentObjectManager.findById(GProject.class, folder.code);
		GKnowledgeBase knowledgebase = persistentObjectManager.findById(GKnowledgeBase.class,
				folder.parentUserspaceKnowledgebaseCode);
		if (!securityService.isCanAccess(knowledgebase, false)) {
			throw new RuntimeException("Cannot modify or upload on this knowledge base");
		}
		GUserspaceProjectEndpoint endpoint = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				folder.uploadCode);
		boolean running = this.jobQueueService.isRunningSyncJob(endpoint);
		PublishingStatus status = new PublishingStatus();
		status.folder = folder;
		status.hasBeenPublished = endpoint.getPublished() != null && endpoint.getPublished();
		status.underPubishingAlgorithm = running;
		OperationStatus<PublishingStatus> out = OperationStatus.of(status);
		out.getMessages().clear();
		if (running) {
			out.getMessages().add(GUserMessage.errorMessage("This folder is under publishing algorithm",
					"Wait some minutes to try to publish again"));
		} else {
			try {
				GJobStatus job = this.jobQueueService.createNewAsyncJob(endpoint, GWorkflowType.STANDARD.name(),
						GStandardWorkflow.INGESTION.name());
				status.jobId = job.getCode();
				status.underPubishingAlgorithm = true;
				status.hasBeenPublished = true;
				out.getMessages().add(GUserMessage.successMessage("Publishing folder updates",
						"The folder updates are under publishing you can browse them on the knowledge base section or use your documents to chat or search from in few seconds"));
			} catch (GeboJobServiceException jobServiceException) {
				out.getMessages().add(GUserMessage.errorMessage("This folder is under publishing algorithm",
						"Wait some minutes to try to publish again"));
			}
		}
		return out;
	}

	private static final String ENDPOINT_TYPE_NAME = GUserspaceProjectEndpoint.class.getName();

	/**
	 * Finds userspace files by their document reference codes
	 * 
	 * @param codes List of document reference codes
	 * @return List of file DTOs
	 */
	@PostMapping(value = "findUserspaceFileByCodes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<UserspaceFileDto> findUserspaceFileByCodes(@NotNull @Valid @RequestBody List<String> codes) {
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		List<UserspaceFileDto> files = new ArrayList<UserspaceFileDto>();
		List<GDocumentReference> documents = documentReferenceRepository.findAllById(codes);
		final Map<String, List<GDocumentReference>> sameEndpoint = new HashMap<String, List<GDocumentReference>>();
		documents.stream()
				.filter(x -> x.getProjectEndpointReference() != null
						&& x.getProjectEndpointReference().getClassName() != null
						&& x.getProjectEndpointReference().getClassName().equals(ENDPOINT_TYPE_NAME)
						&& x.getAbsolutePath() != null && Files.exists(Path.of(x.getAbsolutePath())))
				.forEach(y -> {
					String code = y.getProjectEndpointReference().getCode();
					if (code != null) {
						if (!sameEndpoint.containsKey(code)) {
							sameEndpoint.put(code, new ArrayList<GDocumentReference>());
						}
						sameEndpoint.get(code).add(y);
					}
				});
		if (!sameEndpoint.isEmpty()) {
			List<GUserspaceProjectEndpoint> endpoints = endpointRepository.findAllById(sameEndpoint.keySet());
			for (GUserspaceProjectEndpoint endpoint : endpoints) {
				List<GDocumentReference> documentList = sameEndpoint.get(endpoint.getCode());
				List<GUserspaceFile> uploadedFiles = filesRepository.findByUserspaceEndpointCode(endpoint.getCode());
				for (GDocumentReference doc : documentList) {
					Path path = Path.of(doc.getAbsolutePath());
					UserspaceFileDto filedto = new UserspaceFileDto();
					File file = path.toFile();
					if (file != null && file.getName() != null) {
						filedto.code = doc.getCode();
						filedto.processed = true;
						filedto.extension = doc.getExtension();
						filedto.modificationTime = file != null && file.lastModified() > 0l
								? new Date(file.lastModified())
								: null;
						filedto.name = file.getName();
						filedto.size = file.length();
						filedto.parentUserspaceUploadCode = endpoint.getCode();
						filedto.owner = doc.getUserCreated() != null && owner != null
								&& doc.getUserCreated().equals(owner);
						files.add(filedto);
					}
				}
			}
		}
		return files;
	}
}
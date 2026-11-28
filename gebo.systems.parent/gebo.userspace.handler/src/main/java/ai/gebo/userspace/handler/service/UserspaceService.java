/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.userspace.handler.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.NoContentConsumingSessionParam;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import ai.gebo.userspace.handler.GUserspaceContentManagementSystem;
import ai.gebo.userspace.handler.GUserspaceFile;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.dto.UserspaceFileDto;
import ai.gebo.userspace.handler.dto.UserspaceFolderDto;
import ai.gebo.userspace.handler.dto.UserspaceKnowledgebaseDto;
import ai.gebo.userspace.handler.model.PublishingStatus;
import ai.gebo.userspace.handler.model.UserUploadToUserSpaceParam;
import ai.gebo.userspace.handler.repository.UserspaceFileRepository;
import ai.gebo.userspace.handler.repository.UserspaceProjectEndpointRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Service for managing user space operations, including knowledgebases,
 * folders, and files. AI generated comments
 */
@Service
@AllArgsConstructor
public class UserspaceService {

	final IGLocalPersistentFolderDiscoveryService localFolderService;
	final IGUserspaceContentManagementSystemHandler handler;
	final DocumentReferenceRepository documentReferenceRepository;
	final ProjectRepository projectRepository;
	final UserspaceProjectEndpointRepository endpointRepository;
	final UserspaceFileRepository filesRepository;
	final IGGeboIngestionJobQueueService jobQueueService;
	final IGKnowledgebaseVisibilityService knowledgeBasesVisibilityService;
	final IGSecurityService securityService;
	final IGPersistentObjectManager persistentObjectManager;
	final KnowledgeBaseRepository knowledgeBaseRepository;

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

	public UserspaceKnowledgebaseDto newUserKnowledgebase(UserspaceKnowledgebaseDto object)
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

	public UserspaceKnowledgebaseDto findUserKnowledgebaseByCode(String code) throws GeboPersistenceException {
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

	public UserspaceKnowledgebaseDto updateUserKnowledgebase(UserspaceKnowledgebaseDto object)
			throws GeboPersistenceException {
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
	 * The user's own knowledge base the DTO names, before its deletion: refused when it is not theirs.
	 * 
	 * @param object The knowledgebase DTO to delete
	 * @return the knowledge base to delete
	 * @throws GeboPersistenceException If there's an error deleting from the
	 *                                  database
	 */

	public GKnowledgeBase ownedKnowledgebaseToDelete(UserspaceKnowledgebaseDto object)
			throws GeboPersistenceException {
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		if (object.code == null)
			throw new RuntimeException("Cannot update an object not having a code");
		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class, object.code);
		if (knowledgeBase == null) {
			throw new RuntimeException("Knowledge base does not exist");
		}
		if (!(knowledgeBase.getUsername() != null && knowledgeBase.getUsername().equals(owner))) {
			throw new RuntimeException("Cannot update an object not owned");
		}
		return knowledgeBase;
	}

	/**
	 * The knowledge bases the user made under one of their own: they go with it.
	 *
	 * @param knowledgeBase the knowledge base
	 * @return its child knowledge bases the user owns
	 */
	public List<GKnowledgeBase> ownedChildKnowledgebases(GKnowledgeBase knowledgeBase) {
		final String owner = securityService.getCurrentUser().getUsername();
		return knowledgeBaseRepository.findByParentKnowledgebaseCode(knowledgeBase.getCode()).stream()
				.filter(k -> owner != null && owner.equals(k.getUsername())).toList();
	}

	/**
	 * The folders of a userspace knowledge base.
	 *
	 * @param knowledgeBaseCode the knowledge base code
	 * @return its folders
	 */
	public List<GProject> foldersOf(String knowledgeBaseCode) {
		try (Stream<GProject> projects = projectRepository.findByRootKnowledgeBaseCode(knowledgeBaseCode)) {
			return projects.toList();
		}
	}

	/**
	 * The data sources holding the uploads of a userspace folder.
	 *
	 * @param folderCode the folder (project) code
	 * @return its upload data sources
	 */
	public List<GUserspaceProjectEndpoint> uploadsOf(String folderCode) {
		return endpointRepository.findByParentProjectCode(folderCode);
	}

	/**
	 * Finds a userspace folder by its code
	 * 
	 * @param code The folder code to search for
	 * @return The folder DTO if found
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */

	public UserspaceFolderDto findUserspaceFolderByCode(String code) throws GeboPersistenceException {
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

	public UserspaceFolderDto updateUserspaceFolder(UserspaceFolderDto folderdto) throws GeboPersistenceException {
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
	 * The folder the DTO names, before its deletion: refused when its knowledge base is not the user's or it is not in it.
	 * 
	 * @param folderdto The folder DTO to delete
	 * @return the folder (project) to delete
	 * @throws GeboPersistenceException If there's an error deleting from the
	 *                                  database
	 */

	public GProject ownedFolderToDelete(UserspaceFolderDto folderdto) throws GeboPersistenceException {
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
		// the folder has to be one of the user's own knowledge base: otherwise any
		// folder could be named next to a knowledge base of one's own
		if (pj == null || !folderdto.parentUserspaceKnowledgebaseCode.equals(pj.getRootKnowledgeBaseCode())) {
			throw new RuntimeException("The folder is not in this knowledge base");
		}
		return pj;
	}

	/**
	 * Lists all folders in a userspace knowledgebase
	 * 
	 * @param userspaceKnowledgeBase The knowledgebase code to list folders from
	 * @return List of folder DTOs
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */

	public List<UserspaceFolderDto> listUserspaceFolders(String userspaceKnowledgeBase)
			throws GeboPersistenceException {
		GKnowledgeBase knowledgeBase = persistentObjectManager.findById(GKnowledgeBase.class, userspaceKnowledgeBase);
		if (!securityService.isCanAccess(knowledgeBase, false)) {
			throw new RuntimeException("Cannot access this userspace");
		}
		final UserInfos user = securityService.getCurrentUser();
		final String owner = user.getUsername();
		final TreeMap<String, UserspaceFolderDto> folders = new TreeMap<String, UserspaceFolderDto>();
		try (Stream<GProject> projects = projectRepository.findByRootKnowledgeBaseCode(userspaceKnowledgeBase)) {
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
		}
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

	public List<UserspaceFileDto> listUserspaceFiles(String userspaceUploadCode)
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
			// closed once listed: the stream holds the folder's file handle
			try (Stream<Path> paths = Files.list(path)) {
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
		}
		// closed once read: the stream holds an open cursor on the database
		try (Stream<GDocumentReference> documents = documentReferenceRepository.findByProjectEndpoint(endpoint)) {
			documents.forEach(doc -> {
				if (doc.getName() != null) {
					UserspaceFileDto usfile = list.get(doc.getName());
					if (usfile != null) {
						usfile.code = doc.getCode();
						usfile.processed = true;
					}
				}
			});
		}
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
	 * Deletes userspace files: each is removed from its folder's files and its
	 * upload record is marked deleted. As for any data source whose files go, the
	 * knowledge base loses their documents at the folder's next publish: the
	 * handler hands the documents of the deleted records over as deleted, and the
	 * end of the ingestion erases their vectors, graph and full-text fragments.
	 * Every file is checked before any is deleted.
	 *
	 * @param files           The list of file DTOs to delete
	 * @param allowedToDelete whether the current user may delete the contents of a
	 *                        folder's data source
	 * @throws GeboPersistenceException          If a file is not one the user may
	 *                                           delete, or the database fails
	 * @throws GeboContentHandlerSystemException If the folder's files cannot be
	 *                                           located
	 * @throws IOException                       If a file cannot be deleted
	 */

	public void deleteUserspaceFiles(List<UserspaceFileDto> files,
			Predicate<GUserspaceProjectEndpoint> allowedToDelete)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		final Map<String, GUserspaceProjectEndpoint> endpoints = new HashMap<String, GUserspaceProjectEndpoint>();
		final Map<UserspaceFileDto, Path> targets = new LinkedHashMap<UserspaceFileDto, Path>();
		for (UserspaceFileDto file : files) {
			if (file.parentUserspaceUploadCode == null || file.name == null) {
				throw new RuntimeException("File with null name or parentUserspaceUploadCode");
			}
			GUserspaceProjectEndpoint endpoint = endpoints.get(file.parentUserspaceUploadCode);
			if (endpoint == null) {
				endpoint = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
						file.parentUserspaceUploadCode);
				if (endpoint == null) {
					throw new RuntimeException("The folder of " + file.name + " does not exist");
				}
				endpoints.put(endpoint.getCode(), endpoint);
			}
			// the folder's owner or an administrator, or who uploaded the file
			if (!allowedToDelete.test(endpoint) && !uploadedByCurrentUser(endpoint, file.name)) {
				throw new GeboPersistenceException(
						"Only the folder's owner, an administrator or who uploaded it can delete " + file.name);
			}
			final Path folder = Path.of(localFolderService.getLocalPersistentFolder(handler.getSystem(endpoint), endpoint))
					.toAbsolutePath().normalize();
			final Path target = folder.resolve(file.name).normalize();
			// only a file of this folder: a name cannot lead elsewhere
			if (!folder.equals(target.getParent()) || Files.isDirectory(target)) {
				throw new RuntimeException("The file " + file.name + " is not in this folder");
			}
			targets.put(file, target);
		}
		for (Map.Entry<UserspaceFileDto, Path> entry : targets.entrySet()) {
			final UserspaceFileDto file = entry.getKey();
			Files.deleteIfExists(entry.getValue());
			final String recordCode = file.parentUserspaceUploadCode + "/" + entry.getValue().getFileName();
			GUserspaceFile record = filesRepository.findById(recordCode).orElse(null);
			if (record == null) {
				record = new GUserspaceFile();
				record.setCode(recordCode);
				record.setName(entry.getValue().getFileName().toString());
				record.setUserspaceEndpointCode(file.parentUserspaceUploadCode);
			}
			record.setDeleted(true);
			filesRepository.save(record);
		}
	}

	/**
	 * Whether the current user uploaded the folder's file of this name.
	 */
	private boolean uploadedByCurrentUser(GUserspaceProjectEndpoint endpoint, String name) {
		final UserInfos user = securityService.getCurrentUser();
		final GUserspaceFile record = filesRepository.findById(endpoint.getCode() + "/" + Path.of(name).getFileName())
				.orElse(null);
		return user != null && record != null && record.getUserCreated() != null
				&& record.getUserCreated().equals(user.getUsername());
	}

	/**
	 * The uploads data source of a folder the current user can publish: whoever can
	 * access its knowledge base, found from the data source itself and not from the
	 * request.
	 */
	private GUserspaceProjectEndpoint publishableUploads(UserspaceFolderDto folder) throws GeboPersistenceException {
		final GUserspaceProjectEndpoint endpoint = folder.uploadCode != null
				? persistentObjectManager.findById(GUserspaceProjectEndpoint.class, folder.uploadCode)
				: null;
		if (endpoint == null) {
			throw new RuntimeException("The folder's uploads do not exist");
		}
		final GProject project = persistentObjectManager.findById(GProject.class, endpoint.getParentProjectCode());
		final GKnowledgeBase knowledgebase = project != null
				? persistentObjectManager.findById(GKnowledgeBase.class, project.getRootKnowledgeBaseCode())
				: null;
		if (knowledgebase == null || !securityService.isCanAccess(knowledgebase, false)) {
			throw new RuntimeException("Cannot modify or upload on this knowledge base");
		}
		return endpoint;
	}

	/**
	 * Gets the publishing status of a folder
	 * 
	 * @param folder The folder DTO to check status for
	 * @return The publishing status
	 * @throws GeboPersistenceException If there's an error accessing the database
	 */

	public PublishingStatus getPublishingStatus(UserspaceFolderDto folder) throws GeboPersistenceException {
		GUserspaceProjectEndpoint endpoint = publishableUploads(folder);
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

	public OperationStatus<PublishingStatus> publishFolder(UserspaceFolderDto folder) throws GeboPersistenceException {
		GUserspaceProjectEndpoint endpoint = publishableUploads(folder);
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
				GJobStatus job = this.jobQueueService.createNewAsyncJob(endpoint, new NoContentConsumingSessionParam(),
						GWorkflowType.STANDARD.name(), GStandardWorkflow.INGESTION.name());
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

	public List<UserspaceFileDto> findUserspaceFileByCodes(List<String> codes) {
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

	public OperationStatus<PublishingStatus> transferUploadsToUserSpaceAndPublish(
			@Valid @NotNull @RequestBody UserUploadToUserSpaceParam param) {
		return null;
	}
}
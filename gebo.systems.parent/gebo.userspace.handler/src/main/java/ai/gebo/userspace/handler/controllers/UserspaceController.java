/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.userspace.handler.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import ai.gebo.userspace.handler.GUserspaceContentManagementSystem;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.dto.UserspaceFileDto;
import ai.gebo.userspace.handler.dto.UserspaceFolderDto;
import ai.gebo.userspace.handler.dto.UserspaceKnowledgebaseDto;
import ai.gebo.userspace.handler.model.PublishingStatus;
import ai.gebo.userspace.handler.model.UserUploadToUserSpaceParam;
import ai.gebo.userspace.handler.service.UserspaceService;
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

	final UserspaceService userspaceService;

	public UserspaceController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			UserspaceControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			UserspaceService userspaceService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.userspaceService = userspaceService;

	}

	/**
	 * Lists child personal knowledgebases for the given parent knowledgebase codes
	 * 
	 * @param codes List of parent knowledgebase codes
	 * @return List of knowledgebase DTOs
	 */
	@PostMapping(value = "listChildPersonalKnowledgebases", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<UserspaceKnowledgebaseDto> listChildPersonalKnowledgebases(@RequestBody List<String> codes) {
		return this.userspaceService.listChildPersonalKnowledgebases(codes);
	}

	/**
	 * Retrieves all personal knowledgebases for the current user
	 * 
	 * @return List of knowledgebase DTOs
	 */
	@GetMapping(value = "getPersonalKnowledgebases", consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<UserspaceKnowledgebaseDto> getPersonalKnowledgebases() {
		return this.userspaceService.getPersonalKnowledgebases();
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
		return this.userspaceService.newUserKnowledgebase(object);
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
		return userspaceService.updateUserKnowledgebase(object);
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
		userspaceService.deleteUserKnowledgebase(object);
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
		return this.userspaceService.findUserspaceFolderByCode(code);
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
		return userspaceService.updateUserspaceFolder(folderdto);
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
		userspaceService.deleteUserspaceFolder(folderdto);
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
		return userspaceService.listUserspaceFolders(userspaceKnowledgeBase);
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

		return userspaceService.listUserspaceFiles(userspaceUploadCode);
	}

	/**
	 * Deletes userspace files
	 * 
	 * @param files The list of file DTOs to delete
	 */
	@PostMapping(value = "deleteUserspaceFiles", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserspaceFiles(@NotNull @Valid @RequestBody List<UserspaceFileDto> files) {
		userspaceService.deleteUserspaceFiles(files);
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
		return userspaceService.getPublishingStatus(folder);
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
		return userspaceService.publishFolder(folder);
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
		return userspaceService.findUserspaceFileByCodes(codes);
	}

	@PostMapping(value = "transferUploadsToUserSpaceAndPublish", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<PublishingStatus> transferUploadsToUserSpaceAndPublish(
			@Valid @NotNull @RequestBody UserUploadToUserSpaceParam param) {
		return userspaceService.transferUploadsToUserSpaceAndPublish(param);
	}
}
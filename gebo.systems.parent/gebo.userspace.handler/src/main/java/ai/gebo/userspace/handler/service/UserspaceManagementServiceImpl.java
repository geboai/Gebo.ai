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
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.userspace.handler.GUserspaceFile;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.repository.UserspaceFileRepository;

/**
 * AI generated comments
 * 
 * Service implementation that manages userspace file operations.
 * This service provides functionality for handling file uploads to userspace projects.
 */
@Service
public class UserspaceManagementServiceImpl {
	/** Manages persistent object operations */
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	
	/** Service to discover local persistent folders */
	@Autowired
	IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService;
	
	/** Handler for userspace content management system operations */
	@Autowired
	IGUserspaceContentManagementSystemHandler handler;
	
	/** Repository for userspace file persistence */
	@Autowired
	UserspaceFileRepository filesRepository;

	/** Tells who may upload into a folder */
	@Autowired
	IGSecurityService securityService;

	/**
	 * Default constructor for the service
	 */
	public UserspaceManagementServiceImpl() {

	}

	/**
	 * Manages the upload of multiple files to a specific project endpoint.
	 * 
	 * @param projectEndpoint The code identifying the project endpoint
	 * @param files List of multipart files to upload
	 * @throws IOException If there's an error during file operations
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler system
	 * @throws GeboPersistenceException If there's an error with persistence operations
	 */
	public void manageUpload(String projectEndpoint, List<MultipartFile> files)
			throws IOException, GeboContentHandlerSystemException, GeboPersistenceException {
		// Retrieve the project endpoint from the persistence layer
		GUserspaceProjectEndpoint endpoint = persistentObjectManager.findById(GUserspaceProjectEndpoint.class,
				projectEndpoint);
		if (endpoint == null)
			throw new RuntimeException("The folder to upload into does not exist");
		// who may publish the folder may upload into it: who can access its knowledge
		// base, found from the folder itself
		GProject userspaceFolder = persistentObjectManager.findById(GProject.class, endpoint.getParentProjectCode());
		GKnowledgeBase knowledgeBase = userspaceFolder != null
				? persistentObjectManager.findById(GKnowledgeBase.class, userspaceFolder.getRootKnowledgeBaseCode())
				: null;
		if (knowledgeBase == null || !securityService.isCanAccess(knowledgeBase, false))
			throw new RuntimeException("Cannot modify or upload on this knowledge base");
		// Get the local folder path for the endpoint
		String folder = localFolderDiscoveryService.getLocalPersistentFolder(handler.getSystem(endpoint), endpoint);
		Path path = Path.of(folder).toAbsolutePath().normalize();
		// every name is checked before any file is written: a file of this folder,
		// a name cannot lead elsewhere
		for (MultipartFile entry : files) {
			String name = entry.getOriginalFilename();
			if (name == null || !path.equals(path.resolve(name).normalize().getParent()))
				throw new RuntimeException("The file " + name + " is not a file name of this folder");
		}
		File file = path.toFile();
		// Create the directory if it doesn't exist
		if (!file.exists())
			file.mkdirs();
		// Process each file in the upload
		for (MultipartFile entry : files) {
			Path movedPath = path.resolve(entry.getOriginalFilename()).normalize();
			File movedfile = copy(entry, movedPath);
			// Create a record of the uploaded file
			GUserspaceFile userspacefile = new GUserspaceFile();
			userspacefile.setCode(endpoint.getCode() + "/" + movedfile.getName());
			userspacefile.setName(movedfile.getName());
			userspacefile.setUserspaceEndpointCode(endpoint.getCode());
			// who uploaded it: the owner of the file in the folder's listing
			userspacefile.setUserCreated(securityService.getCurrentUser().getUsername());
			userspacefile.setDateCreated(new Date());
			// Store the file record in the repository: a file uploaded again replaces
			// its record, also the one its deletion left
			filesRepository.save(userspacefile);
		}

	}

	/**
	 * Helper method to copy a multipart file to a specific path
	 * 
	 * @param entry The multipart file to copy
	 * @param movedPath The destination path
	 * @return The resulting file at the destination
	 * @throws IOException If file transfer fails
	 */
	private File copy(MultipartFile entry, Path movedPath) throws IOException {
		File file = movedPath.toFile();
		entry.transferTo(file);
		return file;
	}

}
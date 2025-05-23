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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
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
		// Get the local folder path for the endpoint
		String folder = localFolderDiscoveryService.getLocalPersistentFolder(handler.getSystem(endpoint), endpoint);
		Path path = Path.of(folder);
		File file = path.toFile();
		// Create the directory if it doesn't exist
		if (!file.exists())
			file.mkdirs();
		// Process each file in the upload
		for (MultipartFile entry : files) {
			Path movedPath = Path.of(path.toAbsolutePath().toString(), entry.getOriginalFilename());
			File movedfile = copy(entry, movedPath);
			// Create a record of the uploaded file
			GUserspaceFile userspacefile = new GUserspaceFile();
			userspacefile.setCode(endpoint.getCode() + "/" + movedfile.getName());
			userspacefile.setName(movedfile.getName());
			userspacefile.setUserspaceEndpointCode(endpoint.getCode());
			// Store the file record in the repository
			filesRepository.insert(userspacefile);
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
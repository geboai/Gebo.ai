/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.config.GeboConfig;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;
import ai.gebo.uploads.content.handler.TmpUploadedContents;
import ai.gebo.uploads.content.handler.impl.GUploadsContentManagementSystemHandlerImpl;
import ai.gebo.uploads.content.handler.repositories.TmpUploadedContentsRepository;

/**
 * AI generated comments
 * 
 * This service manages the uploading and handling of file content in the Gebo system.
 * It provides functionality to temporarily store uploaded files and then process them
 * when needed by the system.
 */
@Service
public class UploadsSystemsManagementServiceImpl {
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	@Autowired
	TmpUploadedContentsRepository uploadedContentsRepository;
	@Autowired
	IGGeboConfigService geboConfig;
	@Autowired
	IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService;
	@Autowired
	GUploadsContentManagementSystemHandlerImpl handler;

	/**
	 * Default constructor for UploadsSystemsManagementServiceImpl
	 */
	public UploadsSystemsManagementServiceImpl() {

	}

	/**
	 * Relative path to the temporary upload folder
	 */
	static final String relativeUploadFolder = "DEFAULT.UPLOADS.CONTENT.HANDLER.TMP";

	/**
	 * Manages the uploading of files by storing them in a temporary location
	 * and creating a record in the database.
	 * 
	 * @param handshakeCode Unique identifier for this upload session
	 * @param files List of files to be uploaded
	 * @throws IOException If there's an error during file operations
	 */
	public void manageUpload(String handshakeCode, List<MultipartFile> files) throws IOException {
		TmpUploadedContents tmpUpload = new TmpUploadedContents();
		tmpUpload.setCode(handshakeCode);
		tmpUpload.setDescription("Uploaded content");

		if (geboConfig.getGeboWorkDirectory() == null)
			throw new RuntimeException("Gebo working directory is not set");
		Path path = Path.of(geboConfig.getGeboWorkDirectory(), relativeUploadFolder, handshakeCode);
		File file = path.toFile();
		if (!file.exists())
			file.mkdirs();
		for (MultipartFile entry : files) {

			tmpUpload.getUploadedContents().add(entry.getOriginalFilename());
			Path movedPath = Path.of(path.toAbsolutePath().toString(), entry.getOriginalFilename());
			copy(entry, movedPath);
		}
		uploadedContentsRepository.insert(tmpUpload);
	}

	/**
	 * Processes an upload by moving files from temporary storage to their final location
	 * and updating the endpoint with the uploaded content information.
	 * 
	 * @param endpoint The endpoint associated with the upload
	 * @return Updated endpoint with file information
	 * @throws GeboPersistenceException If there's an error with persistence
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler
	 * @throws IOException If there's an error during file operations
	 */
	private GUploadsProjectEndpoint handleUpload(GUploadsProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		GUploadsProjectEndpoint returned = endpoint;
		if (endpoint.getUploadedContents() == null || endpoint.getUploadedContents().isEmpty()) {
			String uploadCode = endpoint.getUploadHandshakeCode();
			if (uploadCode != null) {
				Optional<TmpUploadedContents> entry = uploadedContentsRepository.findById(uploadCode);
				if (entry.isPresent()) {
					TmpUploadedContents value = entry.get();
					if (geboConfig.getGeboWorkDirectory() == null)
						throw new RuntimeException("Gebo working directory is not set");
					String baseFolder = localFolderDiscoveryService
							.getLocalPersistentFolder(handler.getSystem(endpoint), endpoint);
					List<File> toRemove = new ArrayList<File>();
					if (value.getUploadedContents() != null) {
						for (String fileName : value.getUploadedContents()) {
							Path path = Path.of(geboConfig.getGeboWorkDirectory(), relativeUploadFolder, uploadCode,
									fileName);
							File file = path.toFile();
							File out = Path.of(baseFolder, fileName).toFile();
							FileCopyUtils.copy(file, out);
							toRemove.add(file);
						}
					}
					endpoint.setUploadedContents(value.getUploadedContents());
					
					returned = persistentObjectManager.update(endpoint);
					for (File file : toRemove) {
						file.delete();
					}
					uploadedContentsRepository.deleteById(uploadCode);
				}
			}
		}
		return returned;
	}

	/**
	 * Inserts a new uploads project endpoint and handles any associated file uploads.
	 * 
	 * @param endpoint The endpoint to be inserted
	 * @return The inserted and potentially updated endpoint
	 * @throws GeboPersistenceException If there's an error with persistence
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler
	 * @throws IOException If there's an error during file operations
	 */
	public GUploadsProjectEndpoint insert(GUploadsProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		if (endpoint.getUploadHandshakeCode() != null) {
			endpoint = persistentObjectManager.insert(endpoint);
			return handleUpload(endpoint);
		} else {
			return persistentObjectManager.insert(endpoint);
		}

	}

	/**
	 * Updates an existing uploads project endpoint and handles any associated file uploads.
	 * 
	 * @param endpoint The endpoint to be updated
	 * @return The updated endpoint
	 * @throws GeboPersistenceException If there's an error with persistence
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler
	 * @throws IOException If there's an error during file operations
	 */
	public GUploadsProjectEndpoint update(GUploadsProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {

		return handleUpload(endpoint);

	}

	/**
	 * Copies a MultipartFile to a specified path.
	 * 
	 * @param entry The MultipartFile to be copied
	 * @param movedPath The destination path
	 * @throws IOException If there's an error during file operations
	 */
	private void copy(MultipartFile entry, Path movedPath) throws IOException {
		File file = movedPath.toFile();
		entry.transferTo(file);
	}

}
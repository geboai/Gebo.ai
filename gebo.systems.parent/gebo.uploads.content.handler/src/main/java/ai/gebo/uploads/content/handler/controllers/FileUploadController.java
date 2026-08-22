/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.uploads.content.handler.service.UploadsSystemsManagementServiceImpl;

/**
 * AI generated comments
 * 
 * Controller for handling file upload operations available to administrators.
 * This REST controller provides endpoints for generating handshake tokens and
 * uploading files into the system.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/FileUploadController")
public class FileUploadController {
	/**
	 * Service implementation for managing file uploads in the system.
	 */
	@Autowired
	UploadsSystemsManagementServiceImpl fileUploadService;

	/**
	 * Default constructor for the FileUploadController.
	 */
	public FileUploadController() {

	}

	/**
	 * Inner class representing a handshake token used for secure file upload operations.
	 * Generates a random UUID as the token value.
	 */
	public static class HandShakeToken {
		public String token = UUID.randomUUID().toString();
	}

	/**
	 * Generates a new handshake token for the file upload process.
	 * 
	 * @return A new HandShakeToken instance with a randomly generated UUID
	 */
	@GetMapping(value = "getHandShakeCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public HandShakeToken getHandShakeCode() {
		return new HandShakeToken();
	}

	/**
	 * Inner class representing metadata about uploaded content files.
	 */
	public static class UploadedContents {
		/**
		 * The name of the uploaded file.
		 */
		public String fileName = null;
	}

	/**
	 * Handles the upload of multiple files to the system.
	 * 
	 * @param handShakeCode The handshake code provided for authentication/verification
	 * @param files A list of multipart files to be uploaded
	 * @throws IOException If an error occurs during file processing
	 */
	@PostMapping(value = "upload/{handShakeCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void upload(@PathVariable("handShakeCode") String handShakeCode,
			@RequestParam("files[]") List<MultipartFile> files) throws IOException {
		fileUploadService.manageUpload(handShakeCode, files);
	}

	/**
	 * Adds files to an uploads data source that already exists.
	 *
	 * <p>
	 * The handshake flow above stages files until the endpoint is saved, which is
	 * what creation needs since the endpoint has no code yet. Once the data source
	 * exists it owns a contents folder, so files are stored there directly and are
	 * immediately browsable and ingestable at the next publish.
	 * </p>
	 *
	 * @param endpointCode The code of the target uploads data source
	 * @param files        A list of multipart files to be uploaded
	 * @throws IOException                       If an error occurs during file
	 *                                           processing
	 * @throws GeboContentHandlerSystemException If the data source folder cannot be
	 *                                           resolved
	 * @throws GeboPersistenceException          If the data source cannot be
	 *                                           updated
	 */
	@PostMapping(value = "uploadToEndpoint/{endpointCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void uploadToEndpoint(@PathVariable("endpointCode") String endpointCode,
			@RequestParam("files[]") List<MultipartFile> files)
			throws IOException, GeboContentHandlerSystemException, GeboPersistenceException {
		fileUploadService.uploadToEndpoint(endpointCode, files);
	}

}
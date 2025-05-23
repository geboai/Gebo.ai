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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.userspace.handler.service.UserspaceManagementServiceImpl;

/**
 * AI generated comments
 * 
 * REST controller that handles file uploads to user workspace.
 * This controller provides endpoints for uploading files to specific user workspace folders.
 */
@RestController
@RequestMapping(value = "api/user/UserspaceUploadController")
public class UserspaceUploadController {
	/**
	 * Service that manages the file upload operations for user workspace.
	 */
	@Autowired
	UserspaceManagementServiceImpl fileUploadService;

	/**
	 * Default constructor for UserspaceUploadController.
	 */
	public UserspaceUploadController() {

	}

	/**
	 * Handles file uploads to a specific user workspace folder.
	 * 
	 * @param userspaceFolderCode The identifier code for the target folder in the user's workspace
	 * @param files List of files to be uploaded
	 * @throws IOException If there is an error handling the file data
	 * @throws GeboContentHandlerSystemException If there is a system-level error in the content handler
	 * @throws GeboPersistenceException If there is an error persisting the uploaded files
	 */
	@PostMapping(value = "upload/{userspaceFolderCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void upload(@PathVariable("userspaceFolderCode") String userspaceFolderCode,
			@RequestParam("files[]") List<MultipartFile> files)
			throws IOException, GeboContentHandlerSystemException, GeboPersistenceException {
		fileUploadService.manageUpload(userspaceFolderCode, files);
	}

}
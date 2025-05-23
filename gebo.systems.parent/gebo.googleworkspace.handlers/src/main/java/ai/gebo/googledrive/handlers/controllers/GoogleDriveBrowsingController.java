/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.googledrive.handlers.GoogleDriveSystemContext;
import ai.gebo.googledrive.handlers.IGGoogleDriveVirtualFilesystemBrowser;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * AI generated comments
 * 
 * REST controller that provides endpoints for browsing Google Drive virtual filesystems.
 * This controller requires ADMIN role access and is responsible for retrieving 
 * file system roots and browsing paths within Google Drive.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/GoogleDriveBrowsingController")
public class GoogleDriveBrowsingController {
	/**
	 * Service responsible for browsing Google Drive virtual filesystem.
	 */
	@Autowired
	IGGoogleDriveVirtualFilesystemBrowser browsingService;

	/**
	 * Retrieves the list of root directories/files from Google Drive.
	 *
	 * @param driveSystemCode Identifier for the Google Drive system to access
	 * @return OperationStatus containing a list of virtual filesystem roots
	 * @throws VirtualFilesystemBrowsingException If there's an error accessing the filesystem
	 */
	@GetMapping(value = "getGoogleDriveRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getGoogleDriveRoots(
			@RequestParam("driveSystemCode") String driveSystemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(GoogleDriveSystemContext.of(driveSystemCode));
	}

	/**
	 * Browses a specific path in Google Drive and returns its contents.
	 *
	 * @param param Browsing parameters that specify the path and options
	 * @param driveSystemCode Identifier for the Google Drive system to access
	 * @return OperationStatus containing a list of path information objects
	 * @throws VirtualFilesystemBrowsingException If there's an error accessing the filesystem
	 */
	@PostMapping(value = "browseGoogleDrivePath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseGoogleDrivePath(@RequestBody BrowseParam param,
			@RequestParam("driveSystemCode") String driveSystemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, GoogleDriveSystemContext.of(driveSystemCode));
	}

	/*
	 * @GetMapping(value = "getGoogleDrivePath", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public PathRoute
	 * getGoogleDrivePath(@RequestParam("path") String path,
	 * 
	 * @RequestParam("driveSystemCode") String driveSystemCode) { return
	 * browsingService.getPath(path, driveSystemCode); }
	 */

}
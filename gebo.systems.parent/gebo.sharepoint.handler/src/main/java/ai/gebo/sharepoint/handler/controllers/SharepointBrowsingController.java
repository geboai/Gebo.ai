/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.controllers;

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

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemBrowsingService;
import ai.gebo.sharepoint.handler.impl.SharepointBrowsingContext;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * REST controller that handles Sharepoint browsing operations.
 * This controller provides endpoints for navigating and retrieving information
 * from Sharepoint file systems.
 * 
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/SharepointBrowsingController")
public class SharepointBrowsingController {
	/**
	 * Service for browsing Microsoft Graph virtual filesystem.
	 */
	@Autowired
	IGMicrosoftGraphVirtualFilesystemBrowsingService virtualFilesystemBrowsingService;

	/**
	 * Retrieves all Sharepoint root locations for a given system.
	 *
	 * @param systemCode The identifier for the system to get roots from
	 * @return An OperationStatus containing a list of virtual filesystem roots
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing operation
	 */
	@GetMapping(value = "getSharepointRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getSharepointRoots(
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return virtualFilesystemBrowsingService.getRoots(SharepointBrowsingContext.of(systemCode));
	}

	/**
	 * Browses a specific path in the Sharepoint filesystem.
	 *
	 * @param param The parameters specifying the path to browse
	 * @param systemCode The identifier for the system to browse
	 * @return An OperationStatus containing a list of path information
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing operation
	 */
	@PostMapping(value = "browseSharepointPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseSharepointPath(@RequestBody BrowseParam param,
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return virtualFilesystemBrowsingService.browse(param, SharepointBrowsingContext.of(systemCode));
	}

	/**
	 * Retrieves the navigation status for specified filesystem references.
	 *
	 * @param systemCode The identifier for the system to check
	 * @param references The list of virtual filesystem references to get status for
	 * @return An OperationStatus containing the navigation tree statuses for the references
	 * @throws VirtualFilesystemBrowsingException If an error occurs during status retrieval
	 */
	@PostMapping(value = "getSharepointNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getSharepointNavigationStatus(
			@RequestParam("systemCode") String systemCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return virtualFilesystemBrowsingService.navigationStatus(references, SharepointBrowsingContext.of(systemCode));
	}

}
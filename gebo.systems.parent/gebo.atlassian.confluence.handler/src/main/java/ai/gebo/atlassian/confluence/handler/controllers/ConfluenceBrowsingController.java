/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.controllers;

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

import ai.gebo.atlassian.confluence.handler.impl.ConfluenceBrowsingContext;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceBrowsingService;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * Controller responsible for handling Confluence browsing operations.
 * This class provides REST API endpoints for browsing Confluence content
 * through a virtual filesystem abstraction. Access is restricted to users
 * with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/ConfluenceBrowsingController")
public class ConfluenceBrowsingController {
	/**
	 * Service that handles Confluence browsing functionality
	 */
	@Autowired
	ConfluenceBrowsingService browsingService;

	/**
	 * Retrieves available Confluence virtual filesystem roots for a given system.
	 * 
	 * @param systemCode The system identifier code
	 * @return OperationStatus containing a list of available filesystem roots
	 * @throws VirtualFilesystemBrowsingException If there is an error accessing the virtual filesystem
	 */
	@GetMapping(value = "getConfluenceRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getConfluenceRoots(
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(ConfluenceBrowsingContext.of(systemCode));
	}

	/**
	 * Browses a specific path within the Confluence virtual filesystem.
	 * 
	 * @param param Browse parameters specifying the path and other options
	 * @param systemCode The system identifier code
	 * @return OperationStatus containing a list of path information elements
	 * @throws VirtualFilesystemBrowsingException If there is an error accessing the virtual filesystem
	 */
	@PostMapping(value = "browseConfluencePath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseConfluencePath(@RequestBody BrowseParam param,
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, ConfluenceBrowsingContext.of(systemCode));
	}

	/**
	 * Retrieves navigation status for specified filesystem references.
	 * 
	 * @param systemCode The system identifier code
	 * @param references List of virtual filesystem references to check status for
	 * @return OperationStatus containing a list of navigation tree statuses
	 * @throws VirtualFilesystemBrowsingException If there is an error accessing the virtual filesystem
	 */
	@PostMapping(value = "getConfluenceNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getConfluenceNavigationStatus(
			@RequestParam("systemCode") String systemCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return browsingService.navigationStatus(references, ConfluenceBrowsingContext.of(systemCode));
	}

}
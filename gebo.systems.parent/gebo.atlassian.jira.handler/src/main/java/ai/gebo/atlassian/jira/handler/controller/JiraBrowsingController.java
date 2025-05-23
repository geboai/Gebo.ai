/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.controller;

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

import ai.gebo.atlassian.jira.handler.impl.JiraBrowsingContext;
import ai.gebo.atlassian.jira.handler.impl.JiraBrowsingService;
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
 * REST controller that provides endpoints for browsing Jira resources through a virtual filesystem abstraction.
 * This controller requires admin privileges for access and handles operations such as retrieving filesystem roots,
 * browsing paths, and getting navigation status information for the Jira integration.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/JiraBrowsingController")
public class JiraBrowsingController {
	/**
	 * Service responsible for handling Jira browsing operations.
	 */
	@Autowired
	JiraBrowsingService browsingService;

	/**
	 * Retrieves the list of available Jira filesystem roots for a given system.
	 * 
	 * @param systemCode The identifier for the Jira system to query
	 * @return Operation status containing a list of virtual filesystem roots
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing
	 */
	@GetMapping(value = "getJiraRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getJiraRoots(
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(ai.gebo.atlassian.jira.handler.impl.JiraBrowsingContext.of(systemCode));
	}

	/**
	 * Browses a Jira path according to the provided browsing parameters.
	 * 
	 * @param param The browsing parameters that specify the navigation details
	 * @param systemCode The identifier for the Jira system to browse
	 * @return Operation status containing a list of path information objects
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing
	 */
	@PostMapping(value = "browseJiraPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseJiraPath(@RequestBody BrowseParam param,
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, JiraBrowsingContext.of(systemCode));
	}

	/**
	 * Retrieves the navigation status for a list of filesystem references in the Jira system.
	 * 
	 * @param systemCode The identifier for the Jira system to query
	 * @param references List of virtual filesystem references to check status for
	 * @return Operation status containing navigation tree status information
	 * @throws VirtualFilesystemBrowsingException If an error occurs during status retrieval
	 */
	@PostMapping(value = "getJiraNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getJiraNavigationStatus(
			@RequestParam("systemCode") String systemCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return browsingService.navigationStatus(references, JiraBrowsingContext.of(systemCode));
	}
}
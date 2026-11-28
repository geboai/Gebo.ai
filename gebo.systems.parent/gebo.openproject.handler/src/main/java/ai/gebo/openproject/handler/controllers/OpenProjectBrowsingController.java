/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.controllers;

import java.util.List;

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
import ai.gebo.openproject.handler.OpenProjectBrowsingContext;
import ai.gebo.openproject.handler.impl.OpenProjectBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Browsing of an OpenProject system for the data source editor tree.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/OpenProjectBrowsingController")
public class OpenProjectBrowsingController {
	private final OpenProjectBrowsingService browsingService;

	public OpenProjectBrowsingController(OpenProjectBrowsingService browsingService) {
		this.browsingService = browsingService;
	}

	@GetMapping(value = "getOpenProjectRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getOpenProjectRoots(@RequestParam("systemCode") String systemCode)
			throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(OpenProjectBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "browseOpenProjectPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseOpenProjectPath(@RequestBody BrowseParam param,
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, OpenProjectBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "getOpenProjectNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getOpenProjectNavigationStatus(
			@RequestParam("systemCode") String systemCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return browsingService.navigationStatus(references, OpenProjectBrowsingContext.of(systemCode));
	}
}

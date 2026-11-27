/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.controllers;

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
import ai.gebo.redmine.handler.RedmineBrowsingContext;
import ai.gebo.redmine.handler.impl.RedmineBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Browsing of a Redmine system for the data source editor tree.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/RedmineBrowsingController")
public class RedmineBrowsingController {
	private final RedmineBrowsingService browsingService;

	public RedmineBrowsingController(RedmineBrowsingService browsingService) {
		this.browsingService = browsingService;
	}

	@GetMapping(value = "getRedmineRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getRedmineRoots(@RequestParam("systemCode") String systemCode)
			throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(RedmineBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "browseRedminePath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseRedminePath(@RequestBody BrowseParam param,
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, RedmineBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "getRedmineNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getRedmineNavigationStatus(
			@RequestParam("systemCode") String systemCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return browsingService.navigationStatus(references, RedmineBrowsingContext.of(systemCode));
	}
}

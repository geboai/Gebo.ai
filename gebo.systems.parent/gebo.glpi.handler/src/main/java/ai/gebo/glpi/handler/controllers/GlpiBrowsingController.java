/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.glpi.handler.GlpiBrowsingContext;
import ai.gebo.glpi.handler.impl.GlpiBrowsingService;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/GlpiBrowsingController")
public class GlpiBrowsingController {

	private final GlpiBrowsingService browsingService;

	public GlpiBrowsingController(GlpiBrowsingService browsingService) {
		this.browsingService = browsingService;
	}

	@GetMapping(value = "getGlpiRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getGlpiRoots(@RequestParam("systemCode") String systemCode)
			throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(GlpiBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "browseGlpiPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseGlpiPath(@RequestBody BrowseParam param,
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, GlpiBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "getGlpiNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getGlpiNavigationStatus(
			@RequestParam("systemCode") String systemCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return browsingService.navigationStatus(references, GlpiBrowsingContext.of(systemCode));
	}
}

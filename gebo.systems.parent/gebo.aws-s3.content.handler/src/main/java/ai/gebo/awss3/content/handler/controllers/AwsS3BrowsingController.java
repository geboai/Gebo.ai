/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.controllers;

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

import ai.gebo.awss3.content.handler.AwsS3SystemContext;
import ai.gebo.awss3.content.handler.IGAwsS3VirtualFilesystemBrowser;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/AwsS3BrowsingController")
public class AwsS3BrowsingController {

	@Autowired
	IGAwsS3VirtualFilesystemBrowser browsingService;

	@GetMapping(value = "getAwsS3Roots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getAwsS3Roots(
			@RequestParam("s3SystemCode") String s3SystemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(AwsS3SystemContext.of(s3SystemCode));
	}

	@PostMapping(value = "browseAwsS3Path", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseAwsS3Path(@RequestBody BrowseParam param,
			@RequestParam("s3SystemCode") String s3SystemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, AwsS3SystemContext.of(s3SystemCode));
	}
}
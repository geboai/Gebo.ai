/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.IGKnowledgeBaseBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.KnowledgeBaseContext;
import jakarta.validation.constraints.NotNull;

/**
 * A REST controller for handling user interactions with their knowledge base in the virtual filesystem.
 * Provides endpoints for browsing, retrieving the roots, and checking navigation status of knowledge bases.
 *
 * AI generated comments
 */
@RestController
@RequestMapping("api/user/UserKnowledgeBaseBrowsingController")
public class UserKnowledgeBaseBrowsingController {
	@Autowired
	IGKnowledgebaseVisibilityService visibilityService; // Service for determining visibility of knowledge bases
	@Autowired
	IGKnowledgeBaseBrowsingService browsingService; // Service for browsing knowledge base files

	/**
	 * Retrieves the root directories of knowledge bases a user is authorized to access based on provided codes.
	 *
	 * @param codes The list of codes identifying accessible knowledge bases.
	 * @return The status and list of root directories accessible to the user.
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing.
	 * @throws GeboPersistenceException If there is a persistence layer error.
	 */
	@GetMapping(value = "getKnowledgeBaseRoots", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getKnowledgeBaseRoots(
			@NotNull @RequestParam("codes") List<String> codes)
			throws VirtualFilesystemBrowsingException, GeboPersistenceException {
		List<GKnowledgeBase> knowledgebases = visibilityService.visiblesRootKnowledgebases(codes);
		KnowledgeBaseContext context = KnowledgeBaseContext.of(knowledgebases);
		return browsingService.getRoots(context);

	}

	/**
	 * Allows browsing of a knowledge base path based on specified parameters and codes, retrieving path information.
	 *
	 * @param param Parameters detailing the path to browse.
	 * @param codes Codes representing the accessible knowledge bases.
	 * @return The status and list of path information.
	 * @throws GeboPersistenceException If there is a persistence layer error.
	 * @throws VirtualFilesystemBrowsingException If an error occurs during browsing.
	 */
	@PostMapping(value = "browseKnowledgeBasePath", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseKnowledgeBasePath(@RequestBody BrowseParam param,
			@NotNull @RequestParam("codes") List<String> codes)
			throws GeboPersistenceException, VirtualFilesystemBrowsingException {
		List<GKnowledgeBase> knowledgebases = visibilityService.visiblesAndChildKnowledgebases(codes);
		KnowledgeBaseContext context = KnowledgeBaseContext.of(knowledgebases);
		return browsingService.browse(param, context);
	}

	/**
	 * Retrieves the navigation status within a virtual filesystem for given references and codes.
	 *
	 * @param references Filesystem references to check navigation status.
	 * @param codes Codes representing the accessible knowledge bases.
	 * @return The status and navigation tree information.
	 * @throws GeboPersistenceException If there is a persistence layer error.
	 * @throws VirtualFilesystemBrowsingException If an error occurs while retrieving navigation status.
	 */
	@PostMapping(value = "getKnowledgeBaseNavigationStatus", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getKnowledgeBaseNavigationStatus(
			@RequestBody List<VFilesystemReference> references, @NotNull @RequestParam("codes") List<String> codes)
			throws GeboPersistenceException, VirtualFilesystemBrowsingException {
		List<GKnowledgeBase> knowledgebases = visibilityService.visiblesAndChildKnowledgebases(codes);
		KnowledgeBaseContext context = KnowledgeBaseContext.of(knowledgebases);
		return browsingService.navigationStatus(references, context);
	}

	/**
	 * Retrieves all root knowledge bases accessible to the current user.
	 *
	 * @return A list of accessible root knowledge bases.
	 */
	@GetMapping(value = "getAccessibleRootKnowledgeBases", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAccessibleRootKnowledgeBases() {
		List<GKnowledgeBase> kbases = visibilityService.allVisibleRootKnowledgebases();
		// Transforms each knowledge base into a GBaseObject containing code and description
		return kbases.stream().map(x -> {
			var k = new GBaseObject();
			k.setCode(x.getCode());
			k.setDescription(x.getDescription());
			return k;
		}).toList();
	}

}
/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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

import ai.gebo.filesystem.content.handler.GFileSystemShareReference;
import ai.gebo.filesystem.content.handler.IGFileSystemShareReferenceRuntimeDao;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathRoute;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.IGServerVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.ServerFileSystemContext;

/**
 * AI generated comments
 * 
 * Controller responsible for browsing and navigating shared file systems.
 * This REST controller provides endpoints for retrieving file system roots,
 * browsing directories, and obtaining navigation status.
 * Only users with ADMIN role are allowed to access these endpoints.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/FileSystemsBrowsingController")
public class FileSystemsBrowsingController {
	/** Data access object for shared file system references */
	@Autowired
	IGFileSystemShareReferenceRuntimeDao sharedFileSystemsDao;
	
	/** Service for browsing virtual file systems */
	@Autowired
	IGServerVirtualFilesystemBrowsingService serverFilesystemBrowsingService;

	/**
	 * Default constructor for FileSystemsBrowsingController
	 */
	public FileSystemsBrowsingController() {

	}

	/**
	 * Creates and configures a server file system context based on shared file system configurations.
	 * The method filters out invalid paths and only includes directories that exist and are readable.
	 * 
	 * @return A configured ServerFileSystemContext with limiting roots
	 */
	private ServerFileSystemContext getServerFilesystemContext() {
		ServerFileSystemContext context = new ServerFileSystemContext();
		for (GFileSystemShareReference cfg : this.sharedFileSystemsDao.getConfigurations()) {
			VFilesystemReference reference = cfg.getReference();
			if (reference!=null && reference.path != null && reference.path.absolutePath != null) {
				Path path = Path.of(reference.path.absolutePath);
				if (Files.exists(path) && Files.isDirectory(path) && Files.isReadable(path)) {
					context.getLimitingRoots().add(path);
				}
			} else if (reference!=null && reference.root != null && reference.root.getAbsolutePath() != null) {
				Path path = Path.of(reference.root.getAbsolutePath());
				if (Files.exists(path) && Files.isDirectory(path) && Files.isReadable(path)) {
					context.getLimitingRoots().add(path);
				}
			}
		}
		return context;
	}

	/**
	 * Retrieves a list of all shared file system roots that are available.
	 * 
	 * @return An OperationStatus containing a list of GVirtualFilesystemRoot objects
	 * @throws VirtualFilesystemBrowsingException if an error occurs during browsing
	 */
	@GetMapping(value = "getSharedFilesystemRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getSharedFilesystemRoots()
			throws VirtualFilesystemBrowsingException {
		OperationStatus<List<GVirtualFilesystemRoot>> roots = serverFilesystemBrowsingService
				.getRoots(getServerFilesystemContext());
		return roots;
	}

	/**
	 * Browses a specific path within the shared file systems.
	 * 
	 * @param param The browse parameters containing navigation information
	 * @return An OperationStatus containing a list of PathInfo objects representing the contents of the browsed path
	 * @throws VirtualFilesystemBrowsingException if an error occurs during browsing
	 */
	@PostMapping(value = "browseSharedFilesystemRootsPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseSharedFilesystemRootsPath(@RequestBody BrowseParam param)
			throws VirtualFilesystemBrowsingException {
		return serverFilesystemBrowsingService.browse(param, getServerFilesystemContext());
	}

	/**
	 * Retrieves the navigation status for a list of file system references.
	 * This provides information about the availability and state of each referenced location.
	 * 
	 * @param references The list of file system references to check
	 * @return An OperationStatus containing a list of VirtualFilesystemNavigationTreeStatus objects
	 * @throws VirtualFilesystemBrowsingException if an error occurs during status retrieval
	 */
	@PostMapping(value = "getSharedFilesystemNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getSharedFilesystemNavigationStatus(
			@RequestBody List<VFilesystemReference> references) throws VirtualFilesystemBrowsingException {
		return serverFilesystemBrowsingService.navigationStatus(references, getServerFilesystemContext());
	}
}
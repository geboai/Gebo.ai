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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.filesystem.content.handler.GFileSystemShareReference;
import ai.gebo.filesystem.content.handler.IGFileSystemShareReferenceRuntimeDao;
import ai.gebo.filesystem.content.handler.config.GeboAiFilesystemsConfig;
import ai.gebo.filesystem.content.handler.impl.GFilesystemChangesHandlingService;
import ai.gebo.filesystem.content.handler.service.FileSystemsManagementService;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.IGServerVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.ServerFileSystemContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Controller for managing file system share settings.
 * This controller handles operations related to configuring, managing, and browsing
 * shared file system references. Access is restricted to users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/FileSystemSharesSettingController")
public class FileSystemSharesSettingController {
	@Autowired
	GeboAiFilesystemsConfig filesystemConfig;
	@Autowired
	IGFileSystemShareReferenceRuntimeDao runtimeConfigDao;
	@Autowired
	IGPersistentObjectManager persistentObjectManager;
	@Autowired
	FileSystemsManagementService filesystemManagementService;

	@Autowired
	IGServerVirtualFilesystemBrowsingService serverFilesystemBrowsingService;

	/**
	 * Default constructor
	 */
	public FileSystemSharesSettingController() {

	}

	/**
	 * Creates a server filesystem context with available system roots.
	 * Identifies readable directories from available file system roots.
	 * 
	 * @return ServerFileSystemContext containing available file system roots
	 */
	private ServerFileSystemContext getServerFilesystemContext() {
		ServerFileSystemContext context = new ServerFileSystemContext();
		File[] roots = File.listRoots();
		if (roots != null) {
			for (File file : roots) {
				Path path = file.toPath();
				if (Files.isDirectory(path) && Files.isReadable(path)) {
					context.getLimitingRoots().add(path);
				}
			}
		}
		return context;
	}

	/**
	 * Configuration class for shared filesystem UI settings.
	 * Contains information about UI enablement status and configured shares.
	 */
	public static class SharedFilesystemUIConfig {
		public boolean uiSettingsEnabled = false;
		public List<GFileSystemShareReference> shares = null;
	}

	/**
	 * Retrieves the current configuration for shared file systems.
	 * 
	 * @return SharedFilesystemUIConfig containing UI enablement status and configured shares
	 */
	@GetMapping(value = "getSharedFileSystemsActualConfiguration", produces = MediaType.APPLICATION_JSON_VALUE)
	public SharedFilesystemUIConfig getSharedFileSystemsActualConfiguration() {
		SharedFilesystemUIConfig out = new SharedFilesystemUIConfig();
		if (filesystemConfig.getAllowFilesystemSharesUI() != null && filesystemConfig.getAllowFilesystemSharesUI()) {
			out.uiSettingsEnabled = true;
			out.shares = runtimeConfigDao.getConfigurations();
		}
		return out;
	}

	/**
	 * Retrieves a specific file system share reference by its code.
	 * 
	 * @param code The unique code of the file system share reference
	 * @return GFileSystemShareReference matching the provided code
	 * @throws GeboPersistenceException If there is an error retrieving the share reference
	 * @throws IllegalStateException If filesystem shares UI is not enabled
	 */
	@GetMapping(value = "getFileSystemShareReferenceByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GFileSystemShareReference getFileSystemShareReferenceByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		if (filesystemConfig.getAllowFilesystemSharesUI() != null && filesystemConfig.getAllowFilesystemSharesUI()) {

			return persistentObjectManager.findById(GFileSystemShareReference.class, code);
		} else
			throw new IllegalStateException("Illegal filesystem reference");
	}

	/**
	 * Checks if a file system share reference can be inserted without conflicts.
	 * Verifies that the new share doesn't overlap with existing shares.
	 * 
	 * @param data The file system share reference to validate
	 * @return OperationStatus containing validation results and possibly error messages
	 */
	@PostMapping(value = "checkCanBeInsertedFileSystemShareReference", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GFileSystemShareReference> checkCanBeInsertedFileSystemShareReference(
			@Valid @RequestBody GFileSystemShareReference data) {
		OperationStatus<GFileSystemShareReference> status = new OperationStatus<GFileSystemShareReference>();
		List<GFileSystemShareReference> references = runtimeConfigDao.getConfigurations();
		List<GFileSystemShareReference> svrp = new ArrayList<GFileSystemShareReference>();
		for (GFileSystemShareReference share : references) {
			if (share.getReference() != null) {
				Path spath = share.getReference().toPath();
				Path path = data.getReference().toPath();
				if (spath.startsWith(path) || path.startsWith(spath)) {
					svrp.add(share);
				}
			}
		}
		if (svrp.isEmpty())
			return OperationStatus.of(data);
		else {
			status.getMessages().clear();
			for (GFileSystemShareReference s : svrp) {
				status.getMessages()
						.add(GUserMessage.errorMessage(
								"Conflicting configuration with share with code: " + s.getCode() + " path:"
										+ s.getReference(),
								"The path: " + data.getReference() + " conflicts with existing: " + s.getReference()
										+ " on share with code:" + s.getCode()));
			}
			return status;
		}
	}

	/**
	 * Inserts a new file system share reference.
	 * 
	 * @param data The file system share reference to insert
	 * @return The inserted GFileSystemShareReference
	 * @throws GeboPersistenceException If there is an error persisting the share reference
	 * @throws IllegalStateException If the reference is invalid or filesystem shares UI is not enabled
	 */
	@PostMapping(value = "insertFileSystemShareReference", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GFileSystemShareReference insertFileSystemShareReference(@RequestBody GFileSystemShareReference data)
			throws GeboPersistenceException {
		if (data.getMongoConfigured() != null && data.getMongoConfigured() && data.getReference() != null
				&& filesystemConfig.getAllowFilesystemSharesUI() != null
				&& filesystemConfig.getAllowFilesystemSharesUI()) {
			File file = data.getReference().toPath().toFile();
			if (!file.isDirectory() || !file.exists())
				throw new IllegalStateException("Illegal filesystem reference");

			return persistentObjectManager.insert(data);
		} else
			throw new IllegalStateException("Illegal filesystem reference");
	}

	/**
	 * Deletes a file system share reference.
	 * 
	 * @param data The file system share reference to delete
	 * @throws GeboPersistenceException If there is an error deleting the share reference
	 * @throws IllegalStateException If the reference is invalid or filesystem shares UI is not enabled
	 */
	@PostMapping(value = "deleteFileSystemShareReference", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteFileSystemShareReference(@RequestBody GFileSystemShareReference data)
			throws GeboPersistenceException {
		if (data.getMongoConfigured() != null && data.getMongoConfigured()
				&& filesystemConfig.getAllowFilesystemSharesUI() != null
				&& filesystemConfig.getAllowFilesystemSharesUI()) {
			File file = data.getReference().toPath().toFile();
			if (!file.isDirectory() || !file.exists())
				throw new IllegalStateException("Illegal filesystem reference");
			persistentObjectManager.delete(data);
		} else
			throw new IllegalStateException("Illegal filesystem reference");
	}

	/**
	 * Retrieves the root nodes of the file system.
	 * 
	 * @return OperationStatus containing list of virtual filesystem roots
	 */
	@GetMapping(value = "getRootGFileSystemNodes", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getRootGFileSystemNodes() {
		try {
			if (filesystemConfig.getAllowFilesystemSharesUI() != null
					&& filesystemConfig.getAllowFilesystemSharesUI()) {
				return serverFilesystemBrowsingService.getRoots(getServerFilesystemContext());
			}
			return OperationStatus.ofError("Cannot configure",
					"The shared filesystem configuration is not manageable in this installation");
		} catch (Throwable e) {
			
			return OperationStatus.of(e);
		}
	}

	/**
	 * Retrieves children nodes for a given filesystem node.
	 * 
	 * @param node The browse parameters specifying the node to retrieve children for
	 * @return OperationStatus containing list of path information for child nodes
	 * @throws VirtualFilesystemBrowsingException If there is an error browsing the filesystem
	 */
	@PostMapping(value = "getGFileSystemNodeChildrens", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> getGFileSystemNodeChildrens(@Valid @RequestBody BrowseParam node)
			throws VirtualFilesystemBrowsingException {
		if (filesystemConfig.getAllowFilesystemSharesUI() != null && filesystemConfig.getAllowFilesystemSharesUI()) {
			return serverFilesystemBrowsingService.browse(node, getServerFilesystemContext());
		}
		return OperationStatus.ofError("Cannot configure",
				"The shared filesystem configuration is not manageable in this installation");
	}

	/**
	 * Retrieves navigation status for given filesystem references.
	 * 
	 * @param reference List of filesystem references to get navigation status for
	 * @return OperationStatus containing list of navigation tree statuses
	 * @throws VirtualFilesystemBrowsingException If there is an error retrieving navigation status
	 */
	@PostMapping(value = "getGFileSystemNodeNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getGFileSystemNodeNavigationStatus(
			@RequestBody List<VFilesystemReference> reference) throws VirtualFilesystemBrowsingException {
		return serverFilesystemBrowsingService.navigationStatus(reference, getServerFilesystemContext());
	}

	/**
	 * Data class representing a filesystem reference with usage information.
	 */
	public static class FSReference {
		@NotNull
		public String code = null;
		@NotNull
		public Long used = 0l;
	}

	/**
	 * Retrieves usage information for a list of filesystem shares.
	 * 
	 * @param existingSharesCodes List of share codes to get usage information for
	 * @return List of FSReference objects containing usage information
	 * @throws GeboPersistenceException If there is an error retrieving usage information
	 */
	@PostMapping(value = "getUsedFilesystemShares", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<FSReference> getUsedFilesystemShares(@Valid @RequestBody List<String> existingSharesCodes)
			throws GeboPersistenceException {
		List<FSReference> out = new ArrayList<FSReference>();
		for (String code : existingSharesCodes) {
			FSReference ref = new FSReference();
			ref.code = code;
			ref.used = filesystemManagementService.getUsedEndpointsCount(code);
			out.add(ref);
		}
		return out;
	}
}
/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGServerVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.systems.abstraction.layer.model.ServerFileSystemContext;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.UserspaceContext;

/**
 * AI generated comments
 * 
 * Service implementation that enables browsing of virtual filesystems within a userspace context.
 * This service acts as a bridge between the userspace context and the server filesystem browsing service.
 */
@Service
public class UserspaceBrowsingService implements IGVirtualFilesystemBrowsingService<UserspaceContext> {
	@Autowired
	IGServerVirtualFilesystemBrowsingService fileSystemBrowsingService;
	@Autowired
	IGLocalPersistentFolderDiscoveryService localPersistentFolderServce;
	@Autowired
	IGUserspaceContentManagementSystemHandler handler;

	/**
	 * Translates a UserspaceContext to a ServerFileSystemContext by retrieving 
	 * the local persistent folders for each endpoint in the context.
	 * 
	 * @param context The userspace context to translate
	 * @return A server file system context with appropriate limiting roots
	 * @throws VirtualFilesystemBrowsingException If there's an error accessing local persistent folders
	 */
	private ServerFileSystemContext translateContext(UserspaceContext context)
			throws VirtualFilesystemBrowsingException {
		try {
			ServerFileSystemContext _context = new ServerFileSystemContext();
			List<Path> paths = new ArrayList<Path>();

			for (GUserspaceProjectEndpoint endpoint : context.getEndpoints()) {
				String folder = localPersistentFolderServce.getLocalPersistentFolder(handler.getSystem(endpoint),
						endpoint);
				paths.add(Path.of(folder));
			}
			_context.setLimitingRoots(paths);
			return _context;

		} catch (GeboContentHandlerSystemException e) {
			throw new VirtualFilesystemBrowsingException("Cannot access local persistent folder", e);
		}

	}

	/**
	 * Retrieves the available filesystem roots in the given userspace context.
	 * 
	 * @param context The userspace context to use for retrieving roots
	 * @return An operation status containing the list of virtual filesystem roots
	 * @throws VirtualFilesystemBrowsingException If any error occurs during the browsing operation
	 */
	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(UserspaceContext context)
			throws VirtualFilesystemBrowsingException {

		return fileSystemBrowsingService.getRoots(translateContext(context));
	}

	/**
	 * Browses the filesystem according to the provided parameters within the userspace context.
	 * 
	 * @param param The browse parameters specifying what to browse
	 * @param context The userspace context to use for browsing
	 * @return An operation status containing the list of path information
	 * @throws VirtualFilesystemBrowsingException If any error occurs during the browsing operation
	 */
	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, UserspaceContext context)
			throws VirtualFilesystemBrowsingException {

		return fileSystemBrowsingService.browse(param, translateContext(context));
	}

	/**
	 * Indicates whether this service supports navigation status.
	 * 
	 * @return true, indicating navigation status is supported
	 */
	@Override
	public boolean isSupportsNavigationStatus() {

		return true;
	}

	/**
	 * Gets the parent reference for a given filesystem reference in the userspace context.
	 * 
	 * @param reference The filesystem reference to get the parent for
	 * @param context The userspace context to use
	 * @return The parent filesystem reference
	 * @throws VirtualFilesystemBrowsingException If any error occurs while getting the parent
	 */
	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, UserspaceContext context)
			throws VirtualFilesystemBrowsingException {

		return fileSystemBrowsingService.getParent(reference, translateContext(context));
	}

}
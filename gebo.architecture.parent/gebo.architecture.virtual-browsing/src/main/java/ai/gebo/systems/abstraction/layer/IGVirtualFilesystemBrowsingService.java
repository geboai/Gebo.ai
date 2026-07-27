/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.util.List;

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;

/***
 * AI generated comments
 * 
 * Interface for a service that creates a virtual filesystem navigation logic
 * over a generical content system (can be a filesystem or a CMS or another
 * remote system)
 * 
 * @param <VFScontext> expresses the context to create navigation informations
 *                     from, can be a remote system account or a filesystem
 *                     reference or other logical/technical infos
 * 
 */
public interface IGVirtualFilesystemBrowsingService<VFScontext extends IGVirtualFileSystemContext> {
	
	/**
	 * Enum representing keys used for navigation within the virtual filesystem.
	 */
	public static enum NavigationKey {
		CODE, // Represents navigation by a code identifier.
		ABSOLUTE_PATH // Represents navigation by an absolute path identifier.
	}

	/***
	 * Returns the logical virtual filesystem browsing roots set.
	 * 
	 * @param context - The context from which to determine the roots.
	 * @return OperationStatus containing a list of GVirtualFilesystemRoot objects.
	 * @throws VirtualFilesystemBrowsingException if an error occurs during retrieval.
	 */
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(VFScontext context)
			throws VirtualFilesystemBrowsingException;

	/**
	 * Returns children of a specific navigation path.
	 * 
	 * @param param   - Parameters defining the browsing criteria.
	 * @param context - The context for browsing.
	 * @return OperationStatus containing a list of PathInfo objects.
	 * @throws VirtualFilesystemBrowsingException if an error occurs during browsing.
	 */
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, VFScontext context)
			throws VirtualFilesystemBrowsingException;

	/***
	 * Returns true if the getParent(...) method is implemented, indicating the
	 * actual navigation tree is recreatable from a VFilesystemReference within the
	 * tree nodes set.
	 * 
	 * @return boolean indicating support for navigation status.
	 */
	public boolean isSupportsNavigationStatus();

	/**
	 * Gets the default navigation key used for navigation operations.
	 * 
	 * @return NavigationKey representing the default navigation key.
	 */
	public default NavigationKey getNavigationKey() {
		return NavigationKey.CODE;
	}

	/**
	 * Retrieves the navigation status for a list of filesystem references within
	 * the given context.
	 * 
	 * @param references - List of VFilesystemReference objects.
	 * @param context    - The context in which navigation status is requested.
	 * @return OperationStatus containing a list of VirtualFilesystemNavigationTreeStatus objects.
	 * @throws VirtualFilesystemBrowsingException if navigation status cannot be rebuilt.
	 */
	public default OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> navigationStatus(
			List<VFilesystemReference> references, VFScontext context) throws VirtualFilesystemBrowsingException {
		if (!isSupportsNavigationStatus())
			throw new VirtualFilesystemBrowsingException(
					"Cannot rebuild navigation status if isSupportsNavigationStatus() returns false");

		// Rebuilding navigation tree using the provided logic and references.
		return VirtualFilesystemNavigationLogic.instance.rebuildNavigationTree(this, references, context);

	}

	/***
	 * Gets the parent node of a current node of the navigation tree visit.
	 * 
	 * @param reference - Current VFilesystemReference whose parent is to be found.
	 * @param context   - The context for finding the parent node.
	 * @return VFilesystemReference representing the parent node.
	 * @throws VirtualFilesystemBrowsingException if an error occurs while retrieving the parent.
	 */
	public VFilesystemReference getParent(VFilesystemReference reference, VFScontext context)
			throws VirtualFilesystemBrowsingException;

}
/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationNode;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService.NavigationKey;

/***
 * AI generated comments
 * Utility that performs an IOC on a Virtual Filesystem service to resolve visited
 * tree reconstruction using getParent(...) method on nodes
 */
public class VirtualFilesystemNavigationLogic {
	
	/**
	 * Private constructor to prevent instantiation, enforcing singleton pattern.
	 */
	private VirtualFilesystemNavigationLogic() {

	}

	/**
	 * Singleton instance of VirtualFilesystemNavigationLogic.
	 */
	public static final VirtualFilesystemNavigationLogic instance = new VirtualFilesystemNavigationLogic();

	/***
	 * Reconstructs the navigation tree by performing IOC on the browsing service.
	 *
	 * @param <VFScontext> Type of virtual file system context
	 * @param virtualFilesystemBrowsingService The service used for browsing virtual filesystem
	 * @param references List of references to virtual filesystem nodes
	 * @param context The context of virtual file system
	 * @return OperationStatus containing a list of VirtualFilesystemNavigationTreeStatus
	 * @throws VirtualFilesystemBrowsingException if browsing operation fails
	 */
	public <VFScontext extends IGVirtualFileSystemContext> OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> rebuildNavigationTree(
			IGVirtualFilesystemBrowsingService<VFScontext> virtualFilesystemBrowsingService,
			List<VFilesystemReference> references, VFScontext context) throws VirtualFilesystemBrowsingException {
		Map<String, VirtualFilesystemNavigationNode> intermediateNavigationCache = new HashMap<String, VirtualFilesystemNavigationNode>();
		Map<String, VirtualFilesystemNavigationTreeStatus> foundRootCache = new HashMap<String, VirtualFilesystemNavigationTreeStatus>();
		return internalRebuildNavigationTree(virtualFilesystemBrowsingService, references, context,
				intermediateNavigationCache, foundRootCache);
	}

	/**
	 * Internally reconstructs the navigation tree, handling intermediate cache and root cache.
	 * 
	 * @param <VFScontext> Type of virtual file system context
	 * @param virtualFilesystemBrowsingService The service used for browsing virtual filesystem
	 * @param references List of references to virtual filesystem nodes
	 * @param context The context of virtual file system
	 * @param intermediateNavigationCache Cache for intermediate navigation nodes
	 * @param foundRootCache Cache for found root nodes
	 * @return OperationStatus containing a list of VirtualFilesystemNavigationTreeStatus
	 * @throws VirtualFilesystemBrowsingException if browsing operation fails
	 */
	private <VFScontext extends IGVirtualFileSystemContext> OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> internalRebuildNavigationTree(
			IGVirtualFilesystemBrowsingService<VFScontext> virtualFilesystemBrowsingService,
			List<VFilesystemReference> references, VFScontext context,
			Map<String, VirtualFilesystemNavigationNode> intermediateNavigationCache,
			Map<String, VirtualFilesystemNavigationTreeStatus> foundRootCache)
			throws VirtualFilesystemBrowsingException {
		List<VirtualFilesystemNavigationTreeStatus> trees = new ArrayList<VirtualFilesystemNavigationTreeStatus>();
		for (VFilesystemReference reference : references) {
			// Modifies trees in memory and returns the same root reference for items in the same navigation trees
			OperationStatus<VirtualFilesystemNavigationTreeStatus> rootNode = this
					.internalRebuildNavigationTreeCheckingCache(virtualFilesystemBrowsingService, reference, context,
							intermediateNavigationCache, foundRootCache);
			if (rootNode == null)
				continue;
			if (rootNode.isHasErrorMessages()) {
				OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> rv = OperationStatus.of(List.of());
				rv.getMessages().clear();
				rv.setMessages(rootNode.getMessages());
				return rv;
			}
			if (!trees.stream().anyMatch(x -> x == rootNode.getResult())) {
				trees.add(rootNode.getResult());
			}
		}
		return OperationStatus.of(trees);
	}

	/**
	 * Reconstructs the navigation tree by checking caches and handling intermediate and found root nodes.
	 * 
	 * @param <VFScontext> Type of virtual file system context
	 * @param virtualFilesystemBrowsingService The service used for browsing virtual filesystem
	 * @param reference Reference to a virtual filesystem node
	 * @param context The context of virtual file system
	 * @param intermediateNavigationCache Cache for intermediate navigation nodes
	 * @param foundRootCache Cache for found root nodes
	 * @return OperationStatus containing a VirtualFilesystemNavigationTreeStatus
	 * @throws VirtualFilesystemBrowsingException if browsing operation fails
	 */
	private <VFScontext extends IGVirtualFileSystemContext> OperationStatus<VirtualFilesystemNavigationTreeStatus> internalRebuildNavigationTreeCheckingCache(
			IGVirtualFilesystemBrowsingService<VFScontext> virtualFilesystemBrowsingService,
			VFilesystemReference reference, VFScontext context,
			Map<String, VirtualFilesystemNavigationNode> intermediateNavigationCache,
			Map<String, VirtualFilesystemNavigationTreeStatus> foundRootCache)
			throws VirtualFilesystemBrowsingException {
		if (reference.root == null)
			throw new VirtualFilesystemBrowsingException("Cannot handle null root references");
		List<String> thisNavigationFoundRoots = new ArrayList<String>();
		// Iterate to root starting from parent
		while (!isRoot(reference)) {
			String thisNodeKey = generateKey(reference, virtualFilesystemBrowsingService);
			thisNavigationFoundRoots.add(thisNodeKey);
			// If this node is already fully navigated, simply return root
			if (foundRootCache.containsKey(thisNodeKey)) {
				VirtualFilesystemNavigationTreeStatus rootItem = foundRootCache.get(thisNodeKey);
				addCache(foundRootCache, thisNavigationFoundRoots, rootItem);
				return OperationStatus.of(foundRootCache.get(thisNodeKey));
			}
			VirtualFilesystemNavigationNode currentNode = intermediateNavigationCache.get(thisNodeKey);
			if (currentNode == null) {
				currentNode = new VirtualFilesystemNavigationNode();
				currentNode.setValue(reference);
				intermediateNavigationCache.put(thisNodeKey, currentNode);
			}			
			// Navigate back one step, if childs of parent are not known navigate them
			// Add this node using cached key as child or existing nodes from other navigation routes in this request
			VFilesystemReference parent = virtualFilesystemBrowsingService.getParent(reference, context);
			String parentKey = generateKey(parent, virtualFilesystemBrowsingService);
			thisNavigationFoundRoots.add(parentKey);
			VirtualFilesystemNavigationNode parentNode = intermediateNavigationCache.get(parentKey);
			if (parentNode == null) {
				parentNode = new VirtualFilesystemNavigationNode();
				parentNode.setValue(parent);
				intermediateNavigationCache.put(parentKey, parentNode);
			}
			if (!parentNode.isOpened()) {
				BrowseParam param = new BrowseParam();
				param.root = parentNode.getValue().root;
				param.path = parentNode.getValue().path;
				OperationStatus<List<PathInfo>> status = virtualFilesystemBrowsingService.browse(param, context);
				if (!status.isHasErrorMessages()) {
					parentNode.setOpened(true);
					List<PathInfo> childs = status.getResult();
					for (PathInfo pathInfo : childs) {
						VFilesystemReference childInfo = new VFilesystemReference();
						childInfo.root = parentNode.getValue().root;
						childInfo.path = pathInfo;
						// This will take current node or same node passed in other navigations
						String childKey = generateKey(childInfo, virtualFilesystemBrowsingService);
						thisNavigationFoundRoots.add(childKey);
						VirtualFilesystemNavigationNode cached = intermediateNavigationCache.get(childKey);
						if (cached != null) {
							parentNode.getChilds().add(cached);
						} else {
							VirtualFilesystemNavigationNode newChildNode = new VirtualFilesystemNavigationNode();
							newChildNode.setValue(childInfo);
							intermediateNavigationCache.put(childKey, newChildNode);
							parentNode.getChilds().add(newChildNode);
						}
					}
				}

			}
			if (foundRootCache.containsKey(parentKey)) {
				VirtualFilesystemNavigationTreeStatus rootItem = foundRootCache.get(parentKey);
				addCache(foundRootCache, thisNavigationFoundRoots, rootItem);
				return OperationStatus.of(foundRootCache.get(thisNodeKey));
			}
			reference = parent;
		}

		// If it's a root node, return directly this root node, recuperating all navigated childs from cache
		if (isRoot(reference)) {
			VirtualFilesystemNavigationTreeStatus treeStatus = new VirtualFilesystemNavigationTreeStatus();
			treeStatus.setRoot(reference.root);
			addCache(foundRootCache, thisNavigationFoundRoots, treeStatus);
			BrowseParam param = new BrowseParam();
			param.root = treeStatus.getRoot();
			param.path = null;
			OperationStatus<VirtualFilesystemNavigationTreeStatus> rvalue = OperationStatus.of(treeStatus);
			OperationStatus<List<PathInfo>> status = virtualFilesystemBrowsingService.browse(param, context);
			if (!status.isHasErrorMessages()) {
				List<PathInfo> childs = status.getResult();
				for (PathInfo pathInfo : childs) {
					VFilesystemReference childInfo = new VFilesystemReference();
					childInfo.root = treeStatus.getRoot();
					childInfo.path = pathInfo;
					// This will take current node or same node passed in other navigations
					String childKey = generateKey(childInfo, virtualFilesystemBrowsingService);
					VirtualFilesystemNavigationNode cached = intermediateNavigationCache.get(childKey);
					if (cached != null) {
						treeStatus.getChilds().add(cached);
					} else {
						VirtualFilesystemNavigationNode newChildNode = new VirtualFilesystemNavigationNode();
						newChildNode.setValue(childInfo);
						intermediateNavigationCache.put(childKey, newChildNode);
						treeStatus.getChilds().add(newChildNode);
					}
				}
			} else {
				rvalue.getMessages().clear();
				rvalue.setMessages(status.getMessages());
			}
			return rvalue;
		}
		
		return null;
	}

	/**
	 * Adds a collection of roots to the cache.
	 * 
	 * @param foundRootCache Cache of found root nodes
	 * @param thisNavigationFoundRoots List of navigation roots found in current session
	 * @param rootItem The root item to be cached
	 */
	private void addCache(Map<String, VirtualFilesystemNavigationTreeStatus> foundRootCache,
			List<String> thisNavigationFoundRoots, VirtualFilesystemNavigationTreeStatus rootItem) {
		for (String key : thisNavigationFoundRoots) {
			foundRootCache.put(key, rootItem);
		}
	}

	/**
	 * Determines if a reference is a root node.
	 * 
	 * @param reference The filesystem reference to check
	 * @return true if the reference is a root node, false otherwise
	 */
	private boolean isRoot(VFilesystemReference reference) {
		return reference.path == null;
	}

	/**
	 * Generates a unique key for a given file system reference based on the navigation key strategy.
	 * 
	 * @param <VFScontext> Type of virtual file system context
	 * @param value The filesystem reference for which to generate the key
	 * @param virtualFilesystemBrowsingService The service used for browsing virtual filesystem
	 * @return A unique key as a String
	 * @throws VirtualFilesystemBrowsingException if a key generation issue occurs
	 */
	private <VFScontext extends IGVirtualFileSystemContext> String generateKey(VFilesystemReference value,
			IGVirtualFilesystemBrowsingService<VFScontext> virtualFilesystemBrowsingService)
			throws VirtualFilesystemBrowsingException {
		StringBuffer key = new StringBuffer();
		if (value.root == null)
			throw new VirtualFilesystemBrowsingException("Node with null root value");
		NavigationKey navigationKey = virtualFilesystemBrowsingService.getNavigationKey();
		switch (navigationKey) {
		case ABSOLUTE_PATH: {
			key.append("ROOT:");
			key.append(value.root.getAbsolutePath());
		}
			break;
		case CODE: {
			key.append("ROOT:");
			key.append(value.root.getCode());
			if (value.path != null) {
				key.append("|");
			}
		}
			break;
		}
		if (value.path != null) {
			key.append("|");
			key.append("PATH:" + value.path.absolutePath);
		}
		return key.toString();
	}
}
/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.zammad.handler.ZammadBrowsingContext;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.client.model.Group;
import ai.gebo.zammad.client.model.KnowledgeBase;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * Browses a Zammad system for the data source editor tree: the groups and the
 * knowledge bases are the roots, then tickets, categories, answers and
 * attachments, with
 * the same encoding the ingestion decodes. It rebuilds the tree of a saved
 * selection too, since the parent of a path is the path without its last
 * step.
 */
@Service
public class ZammadBrowsingService implements IGVirtualFilesystemBrowsingService<ZammadBrowsingContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZammadBrowsingService.class);
	private final ZammadApiClientFactory clientFactory;

	public ZammadBrowsingService(ZammadApiClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private ZammadRemoteTree tree(ZammadBrowsingContext context) throws VirtualFilesystemBrowsingException {
		try {
			return new ZammadRemoteTree(clientFactory.getConnection(context.getSystemCode()));
		} catch (GeboCryptSecretException e) {
			throw new VirtualFilesystemBrowsingException("Cannot read the credentials of the Zammad system "
					+ context.getSystemCode(), e);
		}
	}

	private static <T> OperationStatus<List<T>> failure(String what, Throwable e) {
		OperationStatus<List<T>> status = OperationStatus.of(new ArrayList<T>());
		status.getMessages().clear();
		String detail = ZammadErrors.of(e, null);
		status.getMessages().add(GUserMessage.errorMessage("Cannot read the Zammad " + what, detail));
		return status;
	}

	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(ZammadBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		try {
			ZammadRemoteTree tree = tree(context);
			List<GVirtualFilesystemRoot> roots = new ArrayList<GVirtualFilesystemRoot>();
			for (Group group : tree.listGroups()) {
				roots.add(ZammadNavigationUtil.toRoot(ZammadNodes.group(tree.getBaseUri(), group)));
			}
			ZammadKnowledgeBaseIndex index = tree.knowledgeBases();
			for (KnowledgeBase knowledgeBase : index.getKnowledgeBases()) {
				roots.add(ZammadNavigationUtil.toRoot(ZammadNodes.knowledgeBase(tree.getBaseUri(),
						knowledgeBase.getId(), index.knowledgeBaseTitle(knowledgeBase.getId()))));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad system {} has {} groups and knowledge bases", context.getSystemCode(),
						roots.size());
			}
			return OperationStatus.of(roots);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot list the groups and knowledge bases of the Zammad system " + context.getSystemCode(),
					e);
			return failure("groups and knowledge bases", e);
		}
	}

	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, ZammadBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		try {
			ZammadRemoteTree tree = tree(context);
			ZammadPathComponent root = ZammadNavigationUtil.rootOf(param.root);
			String parentPath = param.path != null ? param.path.absolutePath : null;
			ZammadPathNodeType type = root.type;
			String id = root.id;
			List<ZammadPathComponent> steps = ZammadNavigationUtil.parsePath(parentPath);
			if (!steps.isEmpty()) {
				ZammadPathComponent last = steps.get(steps.size() - 1);
				type = last.type;
				id = last.id;
			}
			List<PathInfo> paths = new ArrayList<PathInfo>();
			for (ZammadNativePositionObject child : tree.children(root, type, id)) {
				paths.add(ZammadNavigationUtil.toPathInfo(parentPath, child));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Zammad browse of {} {} in {}: {} items", type, id, root, paths.size());
			}
			return OperationStatus.of(paths);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot browse the Zammad path " + param.path + " of the system " + context.getSystemCode(),
					e);
			return failure("contents", e);
		}
	}

	@Override
	public boolean isSupportsNavigationStatus() {
		return true;
	}

	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, ZammadBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		VFilesystemReference parent = new VFilesystemReference();
		parent.root = reference.root;
		String parentPath = reference.path != null ? ZammadNavigationUtil.parentPath(reference.path.absolutePath)
				: null;
		if (parentPath == null) {
			return parent;
		}
		List<ZammadPathComponent> steps = ZammadNavigationUtil.parsePath(parentPath);
		ZammadPathComponent last = steps.get(steps.size() - 1);
		String name = last.id;
		try {
			ZammadPathComponent root = ZammadNavigationUtil.rootOf(reference.root);
			String lastParentId = steps.size() > 1 ? steps.get(steps.size() - 2).id : root.id;
			ZammadNativePositionObject node = tree(context).node(root, last, lastParentId);
			name = node.getName();
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.warn("Cannot read the name of the Zammad " + last + ", shown by its id", e);
		}
		PathInfo info = new PathInfo();
		info.absolutePath = parentPath;
		info.name = name;
		info.folder = true;
		info.metaType = ZammadNavigationUtil.metaTypeOf(last.type);
		parent.path = info;
		return parent;
	}
}

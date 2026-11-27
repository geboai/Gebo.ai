/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.util.ArrayList;
import java.util.List;

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
import ai.gebo.redmine.client.model.ProjectSummary;
import ai.gebo.redmine.handler.RedmineBrowsingContext;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * Browses a Redmine system for the data source editor tree: the projects are
 * the roots, then sections, issues, wiki pages, attachments and files, with
 * the same encoding the ingestion decodes. It rebuilds the tree of a saved
 * selection too, since the parent of a path is the path without its last
 * step.
 */
@Service
public class RedmineBrowsingService implements IGVirtualFilesystemBrowsingService<RedmineBrowsingContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedmineBrowsingService.class);
	private final RedmineApiClientFactory clientFactory;

	public RedmineBrowsingService(RedmineApiClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private RedmineRemoteTree tree(RedmineBrowsingContext context) throws VirtualFilesystemBrowsingException {
		try {
			return new RedmineRemoteTree(clientFactory.getConnection(context.getSystemCode()));
		} catch (GeboCryptSecretException e) {
			throw new VirtualFilesystemBrowsingException("Cannot read the credentials of the Redmine system "
					+ context.getSystemCode(), e);
		}
	}

	private static <T> OperationStatus<List<T>> failure(String what, Throwable e) {
		OperationStatus<List<T>> status = OperationStatus.of(new ArrayList<T>());
		status.getMessages().clear();
		String detail = RedmineErrors.of(e, null);
		status.getMessages().add(GUserMessage.errorMessage("Cannot read the Redmine " + what, detail));
		return status;
	}

	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(RedmineBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		try {
			RedmineRemoteTree tree = tree(context);
			List<GVirtualFilesystemRoot> roots = new ArrayList<GVirtualFilesystemRoot>();
			for (ProjectSummary project : tree.listProjects()) {
				String identifier = project.getIdentifier() != null ? project.getIdentifier()
						: String.valueOf(project.getId());
				roots.add(RedmineNavigationUtil.toRoot(identifier, project.getName(),
						tree.getBaseUri() + "/projects/" + RedmineNodes.encode(identifier)));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine system {} has {} projects", context.getSystemCode(), roots.size());
			}
			return OperationStatus.of(roots);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot list the projects of the Redmine system " + context.getSystemCode(), e);
			return failure("projects", e);
		}
	}

	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, RedmineBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		try {
			RedmineRemoteTree tree = tree(context);
			String projectId = RedmineNavigationUtil.projectIdOf(param.root);
			String parentPath = param.path != null ? param.path.absolutePath : null;
			RedminePathNodeType type = RedminePathNodeType.PROJECT;
			String id = projectId;
			List<RedminePathComponent> steps = RedmineNavigationUtil.parsePath(parentPath);
			if (!steps.isEmpty()) {
				RedminePathComponent last = steps.get(steps.size() - 1);
				type = last.type;
				id = last.id;
			}
			List<PathInfo> paths = new ArrayList<PathInfo>();
			for (RedmineNativePositionObject child : tree.children(projectId, type, id)) {
				paths.add(RedmineNavigationUtil.toPathInfo(parentPath, child));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Redmine browse of {} {} in {}: {} items", type, id, projectId, paths.size());
			}
			return OperationStatus.of(paths);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot browse the Redmine path " + param.path + " of the system " + context.getSystemCode(),
					e);
			return failure("contents", e);
		}
	}

	@Override
	public boolean isSupportsNavigationStatus() {
		return true;
	}

	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, RedmineBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		VFilesystemReference parent = new VFilesystemReference();
		parent.root = reference.root;
		String parentPath = reference.path != null ? RedmineNavigationUtil.parentPath(reference.path.absolutePath)
				: null;
		if (parentPath == null) {
			return parent;
		}
		List<RedminePathComponent> steps = RedmineNavigationUtil.parsePath(parentPath);
		RedminePathComponent last = steps.get(steps.size() - 1);
		String name = last.id;
		try {
			RedmineNativePositionObject node = tree(context)
					.node(RedmineNavigationUtil.projectIdOf(reference.root), last);
			name = node.getName();
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.warn("Cannot read the name of the Redmine " + last + ", shown by its id", e);
		}
		PathInfo info = new PathInfo();
		info.absolutePath = parentPath;
		info.name = name;
		info.folder = true;
		info.metaType = RedmineNavigationUtil.metaTypeOf(last.type);
		parent.path = info;
		return parent;
	}
}

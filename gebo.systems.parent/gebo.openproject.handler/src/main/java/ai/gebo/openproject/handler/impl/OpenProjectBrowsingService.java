/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

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
import ai.gebo.openproject.handler.OpenProjectBrowsingContext;
import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectPathComponent;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;

/**
 * Browses an OpenProject system for the data source editor tree: the projects are
 * the roots, then sections, work packages, documents, meetings, news,
 * attachments and internal comments, with
 * the same encoding the ingestion decodes. It rebuilds the tree of a saved
 * selection too, since the parent of a path is the path without its last
 * step.
 */
@Service
public class OpenProjectBrowsingService implements IGVirtualFilesystemBrowsingService<OpenProjectBrowsingContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenProjectBrowsingService.class);
	private final OpenProjectApiClientFactory clientFactory;

	public OpenProjectBrowsingService(OpenProjectApiClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private OpenProjectRemoteTree tree(OpenProjectBrowsingContext context) throws VirtualFilesystemBrowsingException {
		try {
			return new OpenProjectRemoteTree(clientFactory.getConnection(context.getSystemCode()));
		} catch (GeboCryptSecretException e) {
			throw new VirtualFilesystemBrowsingException("Cannot read the credentials of the OpenProject system "
					+ context.getSystemCode(), e);
		}
	}

	private static <T> OperationStatus<List<T>> failure(String what, Throwable e) {
		OperationStatus<List<T>> status = OperationStatus.of(new ArrayList<T>());
		status.getMessages().clear();
		String detail = OpenProjectErrors.of(e, null);
		status.getMessages().add(GUserMessage.errorMessage("Cannot read the OpenProject " + what, detail));
		return status;
	}

	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(OpenProjectBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		try {
			OpenProjectRemoteTree tree = tree(context);
			List<GVirtualFilesystemRoot> roots = new ArrayList<GVirtualFilesystemRoot>();
			for (Map<String, Object> project : tree.listProjects()) {
				String identifier = OpenProjectHal.text(project, "identifier");
				if (identifier == null) {
					identifier = OpenProjectHal.string(project, "id");
				}
				roots.add(OpenProjectNavigationUtil.toRoot(identifier, OpenProjectHal.text(project, "name"),
						tree.getBaseUri() + "/projects/" + OpenProjectNodes.encode(identifier)));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenProject system {} has {} projects", context.getSystemCode(), roots.size());
			}
			return OperationStatus.of(roots);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot list the projects of the OpenProject system " + context.getSystemCode(), e);
			return failure("projects", e);
		}
	}

	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, OpenProjectBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		try {
			OpenProjectRemoteTree tree = tree(context);
			String projectId = OpenProjectNavigationUtil.projectIdOf(param.root);
			String parentPath = param.path != null ? param.path.absolutePath : null;
			OpenProjectPathNodeType type = OpenProjectPathNodeType.PROJECT;
			String id = projectId;
			List<OpenProjectPathComponent> steps = OpenProjectNavigationUtil.parsePath(parentPath);
			if (!steps.isEmpty()) {
				OpenProjectPathComponent last = steps.get(steps.size() - 1);
				type = last.type;
				id = last.id;
			}
			List<PathInfo> paths = new ArrayList<PathInfo>();
			for (OpenProjectNativePositionObject child : tree.children(projectId, type, id)) {
				paths.add(OpenProjectNavigationUtil.toPathInfo(parentPath, child));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenProject browse of {} {} in {}: {} items", type, id, projectId, paths.size());
			}
			return OperationStatus.of(paths);
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.error("Cannot browse the OpenProject path " + param.path + " of the system " + context.getSystemCode(),
					e);
			return failure("contents", e);
		}
	}

	@Override
	public boolean isSupportsNavigationStatus() {
		return true;
	}

	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, OpenProjectBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		VFilesystemReference parent = new VFilesystemReference();
		parent.root = reference.root;
		String parentPath = reference.path != null ? OpenProjectNavigationUtil.parentPath(reference.path.absolutePath)
				: null;
		if (parentPath == null) {
			return parent;
		}
		List<OpenProjectPathComponent> steps = OpenProjectNavigationUtil.parsePath(parentPath);
		OpenProjectPathComponent last = steps.get(steps.size() - 1);
		String name = last.id;
		try {
			OpenProjectNativePositionObject node = tree(context)
					.node(OpenProjectNavigationUtil.projectIdOf(reference.root), last);
			name = node.getName();
		} catch (RestClientException | IllegalArgumentException e) {
			LOGGER.warn("Cannot read the name of the OpenProject " + last + ", shown by its id", e);
		}
		PathInfo info = new PathInfo();
		info.absolutePath = parentPath;
		info.name = name;
		info.folder = true;
		info.metaType = OpenProjectNavigationUtil.metaTypeOf(last.type);
		parent.path = info;
		return parent;
	}
}

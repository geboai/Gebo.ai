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

import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedmineNavigationCoordinates;
import ai.gebo.redmine.handler.model.RedminePathComponent;
import ai.gebo.redmine.handler.model.RedminePathNodeType;

/**
 * The encoding of the Redmine virtual filesystem, shared by browsing,
 * ingestion, the declared data sources and search.
 * <ul>
 * <li>a root is a project: code and absolute path {@code PROJECT:<identifier>}
 * (Redmine project identifiers cannot change once the project exists; a
 * numeric project id is accepted too, by the API and here)</li>
 * <li>a path is the cumulative list of typed steps below the root, separated
 * by {@code |}: {@code SECTION:ISSUES|ISSUE:12|ATTACHMENT:34},
 * {@code SECTION:WIKI|WIKI_PAGE:Start|WIKI_CONTENT:Start}, a private note
 * under its issue by its journal id: {@code SECTION:ISSUES|ISSUE:12|PRIVATE_NOTE:56}.
 * Redmine wiki titles cannot contain {@code |}, and issue, attachment and
 * journal ids are numbers.</li>
 * </ul>
 * Once data sources are saved this encoding is a compatibility contract: the
 * stored paths are decoded with it.
 */
public final class RedmineNavigationUtil {
	public static final String PROJECT_PREFIX = "PROJECT:";
	public static final String STEPS_SEPARATOR = "|";
	public static final String TYPE_SEPARATOR = ":";

	private RedmineNavigationUtil() {
	}

	public static GVirtualFilesystemRoot toRoot(String projectIdentifier, String projectName, String uri) {
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(PROJECT_PREFIX + projectIdentifier);
		root.setAbsolutePath(PROJECT_PREFIX + projectIdentifier);
		root.setDescription(projectName != null && !projectName.equals(projectIdentifier)
				? projectName + " (" + projectIdentifier + ")"
				: projectIdentifier);
		root.setUri(uri);
		return root;
	}

	public static GVirtualFilesystemRoot toRoot(RedmineNativePositionObject project) {
		return toRoot(project.getId(), project.getName(), project.getUrl());
	}

	/**
	 * The project identifier (or id) a root stands for.
	 */
	public static String projectIdOf(GVirtualFilesystemRoot root) {
		if (root == null) {
			throw new IllegalArgumentException("A Redmine reference needs its project root");
		}
		String code = root.getAbsolutePath() != null ? root.getAbsolutePath() : root.getCode();
		if (code == null) {
			throw new IllegalArgumentException("A Redmine root needs its project code");
		}
		return code.startsWith(PROJECT_PREFIX) ? code.substring(PROJECT_PREFIX.length()) : code;
	}

	public static String segment(RedminePathNodeType type, String id) {
		return type.name() + TYPE_SEPARATOR + id;
	}

	public static String childPath(String parentAbsolutePath, RedminePathNodeType type, String id) {
		String segment = segment(type, id);
		return parentAbsolutePath == null || parentAbsolutePath.isEmpty() ? segment
				: parentAbsolutePath + STEPS_SEPARATOR + segment;
	}

	public static RedminePathComponent parseSegment(String segment) {
		int idx = segment.indexOf(TYPE_SEPARATOR);
		if (idx <= 0) {
			throw new IllegalArgumentException("Not a Redmine path step: " + segment);
		}
		RedminePathNodeType type = RedminePathNodeType.valueOf(segment.substring(0, idx));
		return new RedminePathComponent(type, segment.substring(idx + 1));
	}

	public static List<RedminePathComponent> parsePath(String absolutePath) {
		List<RedminePathComponent> steps = new ArrayList<RedminePathComponent>();
		if (absolutePath == null || absolutePath.isEmpty()) {
			return steps;
		}
		for (String segment : absolutePath.split("\\" + STEPS_SEPARATOR)) {
			if (!segment.isEmpty()) {
				steps.add(parseSegment(segment));
			}
		}
		return steps;
	}

	/**
	 * The path one step up, or null when the path is a single step (its parent
	 * is the root itself).
	 */
	public static String parentPath(String absolutePath) {
		if (absolutePath == null) {
			return null;
		}
		int idx = absolutePath.lastIndexOf(STEPS_SEPARATOR);
		return idx < 0 ? null : absolutePath.substring(0, idx);
	}

	public static PathInfoMetaType metaTypeOf(RedminePathNodeType type) {
		switch (type) {
		case ISSUE_CONTENT:
		case WIKI_CONTENT:
		case PRIVATE_NOTE:
			return PathInfoMetaType.WEB_PAGE;
		case ATTACHMENT:
		case FILE:
			return PathInfoMetaType.FILE;
		default:
			return PathInfoMetaType.FOLDER;
		}
	}

	public static PathInfo toPathInfo(String parentAbsolutePath, RedmineNativePositionObject node) {
		PathInfo info = new PathInfo();
		info.absolutePath = childPath(parentAbsolutePath, node.getType(), node.getId());
		info.name = node.getName();
		info.folder = node.isFolder();
		info.metaType = metaTypeOf(node.getType());
		return info;
	}

	public static RedmineNavigationCoordinates toCoordinates(VFilesystemReference reference) {
		RedmineNavigationCoordinates coordinates = new RedmineNavigationCoordinates();
		coordinates.setRoot(reference.root);
		if (reference.path != null && reference.path.absolutePath != null) {
			List<RedminePathComponent> steps = parsePath(reference.path.absolutePath);
			String cumulative = null;
			for (int i = 0; i < steps.size(); i++) {
				RedminePathComponent step = steps.get(i);
				cumulative = childPath(cumulative, step.type, step.id);
				PathInfo info = new PathInfo();
				info.absolutePath = cumulative;
				info.name = i == steps.size() - 1 && reference.path.name != null ? reference.path.name : step.id;
				info.folder = step.type.isFolder();
				info.metaType = metaTypeOf(step.type);
				coordinates.getBrowsingSteps().add(info);
				coordinates.getBrowsingStepsCustom().add(step);
			}
		}
		return coordinates;
	}

	/**
	 * Coordinates of a native path, the project first.
	 */
	public static RedmineNavigationCoordinates toCoordinates(List<RedmineNativePositionObject> natives) {
		RedmineNavigationCoordinates coordinates = new RedmineNavigationCoordinates();
		if (natives.isEmpty()) {
			return coordinates;
		}
		coordinates.setRoot(toRoot(natives.get(0)));
		String cumulative = null;
		for (int i = 1; i < natives.size(); i++) {
			RedmineNativePositionObject node = natives.get(i);
			PathInfo info = toPathInfo(cumulative, node);
			cumulative = info.absolutePath;
			coordinates.getBrowsingSteps().add(info);
			coordinates.getBrowsingStepsCustom().add(new RedminePathComponent(node.getType(), node.getId()));
		}
		return coordinates;
	}

	/**
	 * A reference to an item, as search results and declared data sources carry.
	 */
	public static VFilesystemReference toReference(String projectId, String projectName, List<RedminePathComponent> steps,
			String name) {
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = toRoot(projectId, projectName, null);
		if (steps != null && !steps.isEmpty()) {
			String cumulative = null;
			for (RedminePathComponent step : steps) {
				cumulative = childPath(cumulative, step.type, step.id);
			}
			RedminePathComponent last = steps.get(steps.size() - 1);
			PathInfo info = new PathInfo();
			info.absolutePath = cumulative;
			info.name = name != null ? name : last.id;
			info.folder = last.type.isFolder();
			info.metaType = metaTypeOf(last.type);
			reference.path = info;
		}
		return reference;
	}

	/**
	 * A document or folder name: the characters a file name cannot hold become
	 * dashes.
	 */
	public static String sanitizeName(String name) {
		if (name == null) {
			return "";
		}
		return name.replaceAll("[\\\\/:*?\"<>|\\r\\n\\t]", "-").trim();
	}
}

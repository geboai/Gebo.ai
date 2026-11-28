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

import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.zammad.handler.model.ZammadNativePositionObject;
import ai.gebo.zammad.handler.model.ZammadNavigationCoordinates;
import ai.gebo.zammad.handler.model.ZammadPathComponent;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;

/**
 * The encoding of the Zammad virtual filesystem, shared by browsing,
 * ingestion, the declared data sources and search.
 * <ul>
 * <li>a root is a group or a knowledge base: code and absolute path
 * {@code GROUP:<group id>} or {@code KNOWLEDGE_BASE:<knowledge base id>}</li>
 * <li>a path is the cumulative list of typed steps below the root, separated
 * by {@code |}: {@code TICKET:12|ATTACHMENT:40-7} (article 40, attachment 7),
 * {@code TICKET:12|INTERNAL_NOTE:41} (the internal article 41),
 * {@code CATEGORY:1|CATEGORY:2|ANSWER:5|ANSWER_CONTENT:5}. Every step id is a
 * number, or two numbers for a ticket attachment.</li>
 * </ul>
 * Once data sources are saved this encoding is a compatibility contract: the
 * stored paths are decoded with it.
 */
public final class ZammadNavigationUtil {
	public static final String GROUP_PREFIX = ZammadPathNodeType.GROUP.name() + ":";
	public static final String KNOWLEDGE_BASE_PREFIX = ZammadPathNodeType.KNOWLEDGE_BASE.name() + ":";
	public static final String STEPS_SEPARATOR = "|";
	public static final String TYPE_SEPARATOR = ":";
	/** Between the article id and the attachment id of a ticket attachment. */
	public static final String ARTICLE_ATTACHMENT_SEPARATOR = "-";

	private ZammadNavigationUtil() {
	}

	public static GVirtualFilesystemRoot toRoot(ZammadPathNodeType type, String id, String name, String uri) {
		if (type == null || !type.isRoot()) {
			throw new IllegalArgumentException("A Zammad root is a group or a knowledge base, not " + type);
		}
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(segment(type, id));
		root.setAbsolutePath(segment(type, id));
		root.setDescription(name != null && !name.isBlank() ? name : segment(type, id));
		root.setUri(uri);
		return root;
	}

	public static GVirtualFilesystemRoot toRoot(ZammadNativePositionObject node) {
		return toRoot(node.getType(), node.getId(), node.getName(), node.getUrl());
	}

	/**
	 * The group or knowledge base a root stands for.
	 */
	public static ZammadPathComponent rootOf(GVirtualFilesystemRoot root) {
		if (root == null) {
			throw new IllegalArgumentException("A Zammad reference needs its group or knowledge base root");
		}
		String code = root.getAbsolutePath() != null ? root.getAbsolutePath() : root.getCode();
		if (code == null) {
			throw new IllegalArgumentException("A Zammad root needs its code");
		}
		ZammadPathComponent component = parseSegment(code);
		if (!component.type.isRoot()) {
			throw new IllegalArgumentException("Not a Zammad root: " + code);
		}
		return component;
	}

	public static String segment(ZammadPathNodeType type, String id) {
		return type.name() + TYPE_SEPARATOR + id;
	}

	public static String childPath(String parentAbsolutePath, ZammadPathNodeType type, String id) {
		String segment = segment(type, id);
		return parentAbsolutePath == null || parentAbsolutePath.isEmpty() ? segment
				: parentAbsolutePath + STEPS_SEPARATOR + segment;
	}

	public static ZammadPathComponent parseSegment(String segment) {
		int idx = segment.indexOf(TYPE_SEPARATOR);
		if (idx <= 0) {
			throw new IllegalArgumentException("Not a Zammad path step: " + segment);
		}
		ZammadPathNodeType type = ZammadPathNodeType.valueOf(segment.substring(0, idx));
		return new ZammadPathComponent(type, segment.substring(idx + 1));
	}

	public static List<ZammadPathComponent> parsePath(String absolutePath) {
		List<ZammadPathComponent> steps = new ArrayList<ZammadPathComponent>();
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

	public static PathInfoMetaType metaTypeOf(ZammadPathNodeType type) {
		switch (type) {
		case TICKET_CONTENT:
		case INTERNAL_NOTE:
		case ANSWER_CONTENT:
			return PathInfoMetaType.WEB_PAGE;
		case ATTACHMENT:
			return PathInfoMetaType.FILE;
		default:
			return PathInfoMetaType.FOLDER;
		}
	}

	public static PathInfo toPathInfo(String parentAbsolutePath, ZammadNativePositionObject node) {
		PathInfo info = new PathInfo();
		info.absolutePath = childPath(parentAbsolutePath, node.getType(), node.getId());
		info.name = node.getName();
		info.folder = node.isFolder();
		info.metaType = metaTypeOf(node.getType());
		return info;
	}

	public static ZammadNavigationCoordinates toCoordinates(VFilesystemReference reference) {
		ZammadNavigationCoordinates coordinates = new ZammadNavigationCoordinates();
		coordinates.setRoot(reference.root);
		if (reference.path != null && reference.path.absolutePath != null) {
			List<ZammadPathComponent> steps = parsePath(reference.path.absolutePath);
			String cumulative = null;
			for (int i = 0; i < steps.size(); i++) {
				ZammadPathComponent step = steps.get(i);
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
	 * Coordinates of a native path, the group or knowledge base first.
	 */
	public static ZammadNavigationCoordinates toCoordinates(List<ZammadNativePositionObject> natives) {
		ZammadNavigationCoordinates coordinates = new ZammadNavigationCoordinates();
		if (natives.isEmpty()) {
			return coordinates;
		}
		coordinates.setRoot(toRoot(natives.get(0)));
		String cumulative = null;
		for (int i = 1; i < natives.size(); i++) {
			ZammadNativePositionObject node = natives.get(i);
			PathInfo info = toPathInfo(cumulative, node);
			cumulative = info.absolutePath;
			coordinates.getBrowsingSteps().add(info);
			coordinates.getBrowsingStepsCustom().add(new ZammadPathComponent(node.getType(), node.getId()));
		}
		return coordinates;
	}

	/**
	 * A reference to an item, as search results and declared data sources carry.
	 */
	public static VFilesystemReference toReference(ZammadPathNodeType rootType, String rootId, String rootName,
			List<ZammadPathComponent> steps, String name) {
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = toRoot(rootType, rootId, rootName, null);
		if (steps != null && !steps.isEmpty()) {
			String cumulative = null;
			for (ZammadPathComponent step : steps) {
				cumulative = childPath(cumulative, step.type, step.id);
			}
			ZammadPathComponent last = steps.get(steps.size() - 1);
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

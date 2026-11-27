/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.gebo.glpi.handler.model.GlpiNativePositionObject;
import ai.gebo.glpi.handler.model.GlpiNavigationCoordinates;
import ai.gebo.glpi.handler.model.GlpiPathComponent;
import ai.gebo.glpi.handler.model.GlpiPathNodeType;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * The GLPI navigation encoding, shared by browsing, consuming and search. It is
 * persisted in the data sources (endpoint.paths), so it is a compatibility
 * contract: change it only with a migration of the stored paths.
 * <ul>
 * <li>root: {@code ENTITY:<entity id>} (code and absolutePath)</li>
 * <li>path: cumulative typed segments separated by {@code |}:
 * {@code TICKETS}, {@code TICKETS|TICKET:<id>},
 * {@code TICKETS|TICKET:<id>|TICKET-BODY:<id>} (also FOLLOWUP, TASK, SOLUTION,
 * DOCUMENT), {@code KB}, {@code KB|KBARTICLE:<id>}</li>
 * </ul>
 * Only GLPI numeric ids are encoded, never names, so decoding needs no API
 * call.
 */
public final class GlpiNavigationUtil {
	public static final String ENTITY_PREFIX = "ENTITY:";
	public static final String TICKETS_SECTION = "TICKETS";
	public static final String KB_SECTION = "KB";
	public static final String TICKET_PREFIX = "TICKET:";
	public static final String TICKET_BODY_PREFIX = "TICKET-BODY:";
	public static final String FOLLOWUP_PREFIX = "FOLLOWUP:";
	public static final String TASK_PREFIX = "TASK:";
	public static final String SOLUTION_PREFIX = "SOLUTION:";
	public static final String DOCUMENT_PREFIX = "DOCUMENT:";
	public static final String KB_ARTICLE_PREFIX = "KBARTICLE:";
	public static final String PATH_SEPARATOR = "|";
	public static final String TICKETS_SECTION_NAME = "Tickets";
	public static final String KB_SECTION_NAME = "Knowledge base";

	private static final Map<GlpiPathNodeType, String> PREFIXES = Map.of(GlpiPathNodeType.TICKET, TICKET_PREFIX,
			GlpiPathNodeType.TICKET_BODY, TICKET_BODY_PREFIX, GlpiPathNodeType.FOLLOWUP, FOLLOWUP_PREFIX,
			GlpiPathNodeType.TASK, TASK_PREFIX, GlpiPathNodeType.SOLUTION, SOLUTION_PREFIX,
			GlpiPathNodeType.DOCUMENT, DOCUMENT_PREFIX, GlpiPathNodeType.KB_ARTICLE, KB_ARTICLE_PREFIX);

	private GlpiNavigationUtil() {
	}

	/** The root of a GLPI entity */
	public static GVirtualFilesystemRoot toRoot(long entityId, String description) {
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(ENTITY_PREFIX + entityId);
		root.setAbsolutePath(ENTITY_PREFIX + entityId);
		root.setDescription(description != null ? description : ENTITY_PREFIX + entityId);
		return root;
	}

	/** The entity id a root encodes, null if the root is not a GLPI entity */
	public static Long entityIdOf(GVirtualFilesystemRoot root) {
		if (root == null) {
			return null;
		}
		String encoded = root.getCode() != null ? root.getCode() : root.getAbsolutePath();
		if (encoded == null || !encoded.startsWith(ENTITY_PREFIX)) {
			return null;
		}
		return GlpiNativePositionObject.asLong(encoded.substring(ENTITY_PREFIX.length()));
	}

	/** One encoded path segment */
	public static String segment(GlpiPathComponent component) {
		switch (component.type) {
		case TICKETS_SECTION:
			return TICKETS_SECTION;
		case KB_SECTION:
			return KB_SECTION;
		case ENTITY:
			throw new IllegalArgumentException("An entity is a root, not a path segment");
		default:
			return PREFIXES.get(component.type) + component.id;
		}
	}

	/** Decodes one path segment, null when it is not a GLPI segment */
	public static GlpiPathComponent parseSegment(String segment) {
		if (segment == null) {
			return null;
		}
		String token = segment.trim();
		if (TICKETS_SECTION.equals(token)) {
			return new GlpiPathComponent(GlpiPathNodeType.TICKETS_SECTION, null);
		}
		if (KB_SECTION.equals(token)) {
			return new GlpiPathComponent(GlpiPathNodeType.KB_SECTION, null);
		}
		int colon = token.indexOf(':');
		if (colon <= 0) {
			return null;
		}
		String prefix = token.substring(0, colon + 1);
		Long id = GlpiNativePositionObject.asLong(token.substring(colon + 1));
		if (id == null) {
			return null;
		}
		for (Map.Entry<GlpiPathNodeType, String> entry : PREFIXES.entrySet()) {
			if (entry.getValue().equals(prefix)) {
				return new GlpiPathComponent(entry.getKey(), id);
			}
		}
		return null;
	}

	/** Decodes a whole encoded path, skipping (and so ignoring) unknown segments */
	public static List<GlpiPathComponent> parsePath(String absolutePath) {
		List<GlpiPathComponent> components = new ArrayList<GlpiPathComponent>();
		if (absolutePath == null || absolutePath.isBlank()) {
			return components;
		}
		for (String segment : absolutePath.split("\\" + PATH_SEPARATOR)) {
			GlpiPathComponent component = parseSegment(segment);
			if (component != null) {
				components.add(component);
			}
		}
		return components;
	}

	/** Encodes a list of path components as a cumulative path */
	public static String encodePath(List<GlpiPathComponent> components) {
		StringBuilder builder = new StringBuilder();
		for (GlpiPathComponent component : components) {
			if (builder.length() > 0) {
				builder.append(PATH_SEPARATOR);
			}
			builder.append(segment(component));
		}
		return builder.toString();
	}

	/** The PathInfo of a child node: its parent's path plus its own segment */
	public static PathInfo toPathInfo(PathInfo parent, GlpiPathComponent component, String name) {
		PathInfo pathInfo = new PathInfo();
		String own = segment(component);
		pathInfo.absolutePath = parent != null && parent.absolutePath != null && !parent.absolutePath.isBlank()
				? parent.absolutePath + PATH_SEPARATOR + own
				: own;
		pathInfo.name = name;
		pathInfo.folder = component.type.isFolder();
		pathInfo.metaType = metaTypeOf(component.type);
		return pathInfo;
	}

	public static PathInfoMetaType metaTypeOf(GlpiPathNodeType type) {
		switch (type) {
		case ENTITY:
		case TICKETS_SECTION:
		case KB_SECTION:
		case TICKET:
			return PathInfoMetaType.FOLDER;
		case DOCUMENT:
			return PathInfoMetaType.FILE;
		default:
			return PathInfoMetaType.WEB_PAGE;
		}
	}

	/** Coordinates of a selected (stored) reference */
	public static GlpiNavigationCoordinates toNavigationCoordinates(VFilesystemReference reference) {
		GlpiNavigationCoordinates coordinates = new GlpiNavigationCoordinates();
		coordinates.setRoot(reference.root);
		if (reference.path != null && reference.path.absolutePath != null) {
			PathInfo previous = null;
			for (GlpiPathComponent component : parsePath(reference.path.absolutePath)) {
				PathInfo step = toPathInfo(previous, component, null);
				coordinates.getBrowsingSteps().add(step);
				coordinates.getBrowsingStepsCustom().add(component);
				previous = step;
			}
			if (!coordinates.getBrowsingSteps().isEmpty()) {
				coordinates.getBrowsingSteps().get(coordinates.getBrowsingSteps().size() - 1).name = reference.path.name;
			}
		}
		return coordinates;
	}

	/** Coordinates of a native path (root entity first) */
	public static GlpiNavigationCoordinates toNavigationCoordinates(List<GlpiNativePositionObject> nativePath) {
		if (nativePath == null || nativePath.isEmpty()) {
			return null;
		}
		GlpiNavigationCoordinates coordinates = new GlpiNavigationCoordinates();
		GlpiNativePositionObject first = nativePath.get(0);
		coordinates.setRoot(toRoot(first.getId(), first.getName()));
		PathInfo previous = null;
		for (int i = 1; i < nativePath.size(); i++) {
			GlpiNativePositionObject step = nativePath.get(i);
			GlpiPathComponent component = new GlpiPathComponent(step.getType(), step.getId());
			PathInfo pathInfo = toPathInfo(previous, component, step.getName());
			coordinates.getBrowsingSteps().add(pathInfo);
			coordinates.getBrowsingStepsCustom().add(component);
			previous = pathInfo;
		}
		return coordinates;
	}

	/** The reference of an item reached from the given root through the given steps */
	public static VFilesystemReference toVirtualFilesystemReference(long entityId, String entityName,
			List<GlpiPathComponent> steps, String name) {
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = toRoot(entityId, entityName);
		PathInfo previous = null;
		for (GlpiPathComponent step : steps) {
			previous = toPathInfo(previous, step, null);
		}
		if (previous != null) {
			previous.name = name;
		}
		reference.path = previous;
		return reference;
	}

	/**
	 * A name usable as a folder or document name: GLPI titles can hold slashes
	 * and line breaks, the document relative path is built from names
	 */
	public static String sanitizeName(String name) {
		if (name == null) {
			return null;
		}
		String cleaned = name.replaceAll("[\\\\/]", "-").replaceAll("[\\p{Cntrl}]", " ").replaceAll("\\s+", " ")
				.trim();
		return cleaned.isEmpty() ? null : cleaned;
	}
}

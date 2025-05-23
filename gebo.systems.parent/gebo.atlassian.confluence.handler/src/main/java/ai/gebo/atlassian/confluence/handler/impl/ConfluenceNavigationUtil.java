/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * Utility class for encoding and decoding Confluence navigation elements for a virtual filesystem.
 * This class provides methods to convert between Confluence data models (pages, spaces, attachments)
 * and virtual filesystem representations.
 */
package ai.gebo.atlassian.confluence.handler.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceListItem;
import ai.gebo.atlassian.confluence.cloud.model.CloudConfluenceSpacesListItemExpandable;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNativePositionObject;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceNavigationCoordinates;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluencePathComponent;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluencePathNodeType;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceAttachmentItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceListItem;
import ai.gebo.atlassian.confluence.onpremise.model.OnPremiseConfluenceSpacesListItemExpandable;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;

public class ConfluenceNavigationUtil {
	/**
	 * Prefix used to identify Confluence pages in path strings
	 */
	public static final String PAGE_PREFIX = "CONFLUENCE-PAGE:";
	
	/**
	 * Prefix used to identify Confluence subpages in path strings
	 */
	public static final String SUBPAGES_PREFIX = "CONFLUENCE-SUBPAGES:";
	
	/**
	 * Prefix used to identify Confluence attachments in path strings
	 */
	public static final String ATTACHMENT_PREFIX = "CONFLUENCE-ATTACHMENT:";
	
	/**
	 * Delimiter used to separate path components in a hierarchy
	 */
	public static final String HIERARCHY_SPLITTER = "|";

	/**
	 * Creates a virtual filesystem root from an on-premise Confluence space
	 * 
	 * @param key The space key
	 * @param name The space name
	 * @param _expandable Expandable object containing additional space information
	 * @return A new GVirtualFilesystemRoot object representing the space
	 */
	public static GVirtualFilesystemRoot encodeSpace(String key, String name,
			OnPremiseConfluenceSpacesListItemExpandable _expandable) {
		GVirtualFilesystemRoot item = new GVirtualFilesystemRoot();
		item.setCode(key);
		item.setDescription("Space:" + name);
		item.setUri(_expandable != null ? _expandable.getHomepage() : null);
		return item;
	}

	/**
	 * Creates a virtual filesystem root from a cloud Confluence space
	 * 
	 * @param key The space key
	 * @param name The space name
	 * @param _expandable Expandable object containing additional space information
	 * @return A new GVirtualFilesystemRoot object representing the space
	 */
	public static GVirtualFilesystemRoot encodeSpace(String key, String name,
			CloudConfluenceSpacesListItemExpandable _expandable) {
		GVirtualFilesystemRoot item = new GVirtualFilesystemRoot();
		item.setCode(key);
		item.setDescription("Space:" + name);
		item.setUri(_expandable != null ? _expandable.getHomepage() : null);
		return item;
	}

	/**
	 * Extracts the space key from a virtual filesystem root
	 * 
	 * @param vfs The virtual filesystem root
	 * @return The space key
	 */
	public static String decodeRoot(GVirtualFilesystemRoot vfs) {
		return vfs.getCode();
	}

	/**
	 * Creates a PathInfo object for an on-premise Confluence page
	 * 
	 * @param p The Confluence page item
	 * @return A PathInfo representing the page
	 */
	public static PathInfo encodeAsPage(OnPremiseConfluenceListItem p) {
		PathInfo info = new PathInfo();
		info.absolutePath = PAGE_PREFIX + p.getId();
		info.name = p.getTitle();
		info.folder = false;
		return info;
	}

	/**
	 * Creates a PathInfo object for a cloud Confluence page
	 * 
	 * @param p The Confluence page item
	 * @return A PathInfo representing the page
	 */
	public static PathInfo encodeAsPage(CloudConfluenceListItem p) {
		PathInfo info = new PathInfo();
		info.absolutePath = PAGE_PREFIX + p.getId();
		info.name = p.getTitle();
		info.folder = false;
		return info;
	}

	/**
	 * Creates a folder PathInfo object for an on-premise Confluence page's subpages
	 * 
	 * @param p The Confluence page item
	 * @return A PathInfo representing the page's subpages as a folder
	 */
	public static PathInfo encodeAsFolder(OnPremiseConfluenceListItem p) {
		PathInfo info = new PathInfo();
		info.absolutePath = SUBPAGES_PREFIX + p.getId();
		info.name = p.getTitle() + " (subpages)";
		info.folder = true;
		return info;
	}

	/**
	 * Creates a folder PathInfo object for a cloud Confluence page's subpages
	 * 
	 * @param p The Confluence page item
	 * @return A PathInfo representing the page's subpages as a folder
	 */
	public static PathInfo encodeAsFolder(CloudConfluenceListItem p) {
		PathInfo info = new PathInfo();
		info.absolutePath = SUBPAGES_PREFIX + p.getId();
		info.name = p.getTitle() + " (subpages)";
		info.folder = true;
		return info;
	}

	/**
	 * Extracts space keys from a list of filesystem references
	 * 
	 * @param paths List of filesystem references
	 * @return Array of space keys
	 */
	public static String[] decodeSpaces(List<VFilesystemReference> paths) {
		if (paths == null)
			return new String[0];
		return paths.stream().filter(x -> {
			return x.path == null && x.root != null;
		}).map(y -> {
			return y.root.getCode();
		}).toList().toArray(new String[0]);
	}

	/**
	 * Extracts page IDs from a list of filesystem references that represent individual pages
	 * 
	 * @param paths List of filesystem references
	 * @return Array of page IDs
	 */
	public static String[] decodeSinglePages(List<VFilesystemReference> paths) {
		if (paths == null)
			return new String[0];
		return paths.stream().filter(x -> {
			return x.path != null && x.path.absolutePath != null && x.path.absolutePath.startsWith(PAGE_PREFIX);
		}).map(y -> {
			return y.path.absolutePath.substring(PAGE_PREFIX.length());
		}).toList().toArray(new String[0]);
	}

	/**
	 * Extracts page IDs from a list of filesystem references that represent pages with subpages
	 * 
	 * @param paths List of filesystem references
	 * @return Array of page IDs
	 */
	public static String[] decodePagesAndSubpages(List<VFilesystemReference> paths) {
		if (paths == null)
			return new String[0];
		return paths.stream().filter(x -> {
			return x.path != null && x.path.absolutePath != null && x.path.absolutePath.startsWith(SUBPAGES_PREFIX);
		}).map(y -> {
			return y.path.absolutePath.substring(SUBPAGES_PREFIX.length());
		}).toList().toArray(new String[0]);
	}

	/**
	 * Combines multiple PathInfo objects into a single hierarchical path
	 * 
	 * @param pathinfos The PathInfo objects to combine
	 * @return A new PathInfo representing the combined path
	 */
	public static PathInfo combine(PathInfo... pathinfos) {
		if (pathinfos != null && pathinfos.length > 0) {
			PathInfo out = new PathInfo();
			for (PathInfo pathInfo : pathinfos) {
				if (out.absolutePath == null || out.absolutePath.trim().length() == 0) {
					out.absolutePath = pathInfo.absolutePath;
					out.name = pathInfo.name;
					out.folder = pathInfo.folder;
				} else {
					out.absolutePath += (HIERARCHY_SPLITTER + pathInfo.absolutePath);
					out.name = pathInfo.name;
					out.folder = pathInfo.folder;
				}
			}
			return out;
		}
		return null;
	}

	/**
	 * Splits a hierarchical path into its component parts
	 * 
	 * @param path The PathInfo to split
	 * @return List of PathInfo objects representing each component
	 */
	public static List<PathInfo> splitPath(PathInfo path) {
		if (path == null || path.absolutePath == null) {
			return List.of();
		}
		StringTokenizer tokenizer = new StringTokenizer(path.absolutePath, HIERARCHY_SPLITTER);
		List<PathInfo> out = new ArrayList<PathInfo>();
		while (tokenizer.hasMoreTokens()) {
			PathInfo thisPath = new PathInfo();
			thisPath.absolutePath = tokenizer.nextToken();
			thisPath.folder = tokenizer.hasMoreTokens();
			thisPath.name = null;
			out.add(thisPath);
		}
		if (!out.isEmpty()) {
			out.get(out.size() - 1).name = path.name;
			out.get(out.size() - 1).folder = path.folder;
		}
		return out;
	}

	/**
	 * Converts a list of PathInfo objects to ConfluencePathComponent objects
	 * 
	 * @param browsingSteps List of PathInfo objects
	 * @return List of ConfluencePathComponent objects
	 */
	public static List<ConfluencePathComponent> toCustomSteps(List<PathInfo> browsingSteps) {

		List<ConfluencePathComponent> out = new ArrayList<ConfluencePathComponent>();
		if (browsingSteps != null)
			for (PathInfo pathInfo : browsingSteps) {
				out.add(toConfluencePathComponent(pathInfo));
			}
		return out;
	}

	/**
	 * Converts a PathInfo object to a ConfluencePathComponent
	 * 
	 * @param pathInfo The PathInfo to convert
	 * @return A new ConfluencePathComponent
	 * @throws RuntimeException if the path format is not recognized
	 */
	private static ConfluencePathComponent toConfluencePathComponent(PathInfo pathInfo) {
		ConfluencePathComponent component = new ConfluencePathComponent();
		if (pathInfo.absolutePath.startsWith(ATTACHMENT_PREFIX)) {
			component.type = ConfluencePathNodeType.ATTACHMENT;
			component.id = pathInfo.absolutePath.substring(ATTACHMENT_PREFIX.length());
		} else if (pathInfo.absolutePath.startsWith(SUBPAGES_PREFIX)) {
			component.type = ConfluencePathNodeType.PAGE_CONTAINER;
			component.id = pathInfo.absolutePath.substring(SUBPAGES_PREFIX.length());
		} else if (pathInfo.absolutePath.startsWith(PAGE_PREFIX)) {
			component.type = ConfluencePathNodeType.PAGE;
			component.id = pathInfo.absolutePath.substring(PAGE_PREFIX.length());
		} else
			throw new RuntimeException("AbsolutePath:" + pathInfo.absolutePath + " not understood");
		return component;
	}

	/**
	 * Converts a list of native position objects to navigation coordinates
	 * 
	 * @param nativeCoordinates List of ConfluenceNativePositionObject objects
	 * @return ConfluenceNavigationCoordinates object
	 */
	public static ConfluenceNavigationCoordinates toNavigationCoordinates(
			List<ConfluenceNativePositionObject> nativeCoordinates) {

		ConfluenceNavigationCoordinates coordinates = new ConfluenceNavigationCoordinates();
		if (!nativeCoordinates.isEmpty()) {
			ConfluenceNativePositionObject root = nativeCoordinates.get(0);
			coordinates.setRoot(toRoot(root));
			for (int i = 1; i < nativeCoordinates.size(); i++) {
				ConfluenceNativePositionObject nativeValue = nativeCoordinates.get(i);
				coordinates.getBrowsingSteps().add(toPathInfo(nativeValue));
				coordinates.getBrowsingStepsCustom().add(toCustomPathInfo(nativeValue));
			}
		}
		return coordinates;
	}

	/**
	 * Converts a ConfluenceNativePositionObject to a ConfluencePathComponent
	 * 
	 * @param nativeValue The native position object
	 * @return A ConfluencePathComponent
	 */
	private static ConfluencePathComponent toCustomPathInfo(ConfluenceNativePositionObject nativeValue) {
		ConfluencePathComponent component = new ConfluencePathComponent();
		component.id = nativeValue.getCode();
		if (nativeValue.isConfluenceAttachment()) {
			component.type = ConfluencePathNodeType.ATTACHMENT;
		} else if (nativeValue.isConfluencePage()) {
			component.type = ConfluencePathNodeType.PAGE;
		} else if (nativeValue.isConfluenceSpace()) {
			component.type = ConfluencePathNodeType.SPACE;
		} else if (nativeValue.isConfluenceSuperPage()) {
			component.type = ConfluencePathNodeType.PAGE_CONTAINER;
		}
		return component;
	}

	/**
	 * Converts a ConfluenceNativePositionObject to a PathInfo
	 * 
	 * @param nativeValue The native position object
	 * @return A PathInfo
	 */
	private static PathInfo toPathInfo(ConfluenceNativePositionObject nativeValue) {
		PathInfo path = new PathInfo();
		String prefix = nativeValue.isConfluenceAttachment() ? ATTACHMENT_PREFIX
				: nativeValue.isConfluencePage() ? PAGE_PREFIX
						: nativeValue.isConfluenceSuperPage() ? SUBPAGES_PREFIX : null;
		path.absolutePath = prefix + nativeValue.getCode();
		path.name = nativeValue.getName();
		path.folder = nativeValue.isFolder();
		return path;
	}

	/**
	 * Converts a ConfluenceNativePositionObject to a GVirtualFilesystemRoot
	 * 
	 * @param root The native position object representing a space
	 * @return A GVirtualFilesystemRoot
	 * @throws RuntimeException if the root is not a Confluence space
	 */
	private static GVirtualFilesystemRoot toRoot(ConfluenceNativePositionObject root) {
		GVirtualFilesystemRoot vfsroot = new GVirtualFilesystemRoot();
		if (root.isConfluenceSpace()) {
			vfsroot.setAbsolutePath(root.getCode());
			vfsroot.setDescription(root.getName());
			vfsroot.setUri(root.getUrl());
			return vfsroot;
		} else
			throw new RuntimeException(
					"Root item:" + root.getCode() + " is not a confluence space, so something is going wrong");

	}

	/**
	 * Creates a PathInfo object for an on-premise Confluence attachment
	 * 
	 * @param attach The attachment item
	 * @return A PathInfo representing the attachment
	 */
	public static PathInfo encodeAsAttachment(OnPremiseConfluenceAttachmentItem attach) {
		PathInfo pathIndfo = new PathInfo();
		pathIndfo.absolutePath = ATTACHMENT_PREFIX + attach.getId();
		pathIndfo.name = attach.getTitle();
		return pathIndfo;
	}

	/**
	 * Creates a PathInfo object for a cloud Confluence attachment
	 * 
	 * @param attach The attachment item
	 * @return A PathInfo representing the attachment
	 */
	public static PathInfo encodeAsAttachment(CloudConfluenceAttachmentItem attach) {
		PathInfo pathIndfo = new PathInfo();
		pathIndfo.absolutePath = ATTACHMENT_PREFIX + attach.getId();
		pathIndfo.name = attach.getTitle();
		return pathIndfo;
	}

}
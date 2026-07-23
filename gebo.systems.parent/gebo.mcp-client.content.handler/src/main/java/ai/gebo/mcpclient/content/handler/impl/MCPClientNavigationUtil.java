/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import ai.gebo.mcpclient.content.handler.impl.model.MCPClientNavigationCoordinates;
import ai.gebo.mcpclient.content.handler.impl.model.MCPClientPathComponent;
import ai.gebo.mcpclient.content.handler.impl.model.MCPClientPathNodeType;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.PathInfoMetaType;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * Encodes the "remote system browsing" contract for MCP servers: it uniformly
 * translates between the platform's virtual filesystem primitives
 * ({@link GVirtualFilesystemRoot}, {@link PathInfo}, {@link VFilesystemReference})
 * and the shallow MCP hierarchy (server → resource).
 * <p>
 * The MCP base resource model is a flat list, so a reference is at most two
 * levels deep: a {@link #SERVER_PREFIX server root} optionally followed by a
 * single {@link #RESOURCE_PREFIX resource} step.
 */
public class MCPClientNavigationUtil {

	/** Prefix marking a virtual filesystem root as an MCP server (drive). */
	public static final String SERVER_PREFIX = "MCP-SERVER:";

	/** Prefix marking a path element as an MCP resource. */
	public static final String RESOURCE_PREFIX = "MCP-RESOURCE:";

	/**
	 * Builds the single virtual filesystem root exposed by an MCP client
	 * configuration: the server itself, seen as a virtual drive.
	 *
	 * @param configCode  the MCP client configuration code
	 * @param description a human readable name for the server
	 * @param baseUri     the server base URI, when known
	 * @return the root representing the MCP server
	 */
	public static GVirtualFilesystemRoot toRoot(String configCode, String description, String baseUri) {
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(SERVER_PREFIX + configCode);
		root.setAbsolutePath(SERVER_PREFIX + configCode);
		root.setDescription(description != null ? description : configCode);
		root.setUri(baseUri);
		return root;
	}

	/**
	 * @return {@code true} if the given root addresses an MCP server.
	 */
	public static boolean isServerRoot(GVirtualFilesystemRoot root) {
		return root != null && root.getCode() != null && root.getCode().startsWith(SERVER_PREFIX);
	}

	/**
	 * Extracts the MCP system code carried by a server root.
	 *
	 * @param root the server root
	 * @return the system code, or {@code null} when the root is not a server root
	 */
	public static String getServerCode(GVirtualFilesystemRoot root) {
		return isServerRoot(root) ? root.getCode().substring(SERVER_PREFIX.length()) : null;
	}

	/**
	 * Renders an MCP resource as a leaf {@link PathInfo} directly under the server
	 * root.
	 *
	 * @param uri  the canonical MCP resource URI
	 * @param name the resource name (falls back to the URI when {@code null})
	 * @return the leaf path info representing the resource
	 */
	public static PathInfo toResourcePathInfo(String uri, String name) {
		PathInfo pathInfo = new PathInfo();
		pathInfo.name = name != null ? name : uri;
		pathInfo.folder = false;
		pathInfo.metaType = PathInfoMetaType.FILE;
		pathInfo.absolutePath = RESOURCE_PREFIX + uri;
		return pathInfo;
	}

	/**
	 * Reconstructs the navigation coordinates for a stored
	 * {@link VFilesystemReference}. A reference with no path (or a path that is not
	 * a resource) resolves to the bare server root, meaning "the whole server".
	 *
	 * @param path the stored virtual filesystem reference
	 * @return the corresponding MCP navigation coordinates
	 */
	public static MCPClientNavigationCoordinates toNavigationCoordinates(VFilesystemReference path) {
		MCPClientNavigationCoordinates coordinates = new MCPClientNavigationCoordinates();
		coordinates.setRoot(path.root);
		if (path.path != null && path.path.absolutePath != null
				&& path.path.absolutePath.startsWith(RESOURCE_PREFIX)) {
			String uri = path.path.absolutePath.substring(RESOURCE_PREFIX.length());
			PathInfo step = new PathInfo();
			step.absolutePath = RESOURCE_PREFIX + uri;
			step.name = path.path.name;
			step.folder = false;
			coordinates.getBrowsingSteps().add(step);
			MCPClientPathComponent component = new MCPClientPathComponent();
			component.type = MCPClientPathNodeType.RESOURCE;
			component.uri = uri;
			coordinates.getBrowsingStepsCustom().add(component);
		}
		return coordinates;
	}

	/**
	 * Builds a {@link VFilesystemReference} pointing at a single MCP resource under
	 * the given server root.
	 *
	 * @param root the MCP server root
	 * @param uri  the canonical MCP resource URI
	 * @param name the resource name
	 * @return the reference to the resource
	 */
	public static VFilesystemReference toVirtualFilesystemReference(GVirtualFilesystemRoot root, String uri,
			String name) {
		VFilesystemReference reference = new VFilesystemReference();
		reference.root = root;
		reference.path = toResourcePathInfo(uri, name);
		return reference;
	}

	/**
	 * Extracts the resource URI addressed by a browse parameter's path, or
	 * {@code null} when the path does not point at a resource.
	 */
	public static String resourceUriOf(BrowseParam param) {
		if (param == null || param.path == null || param.path.absolutePath == null) {
			return null;
		}
		return param.path.absolutePath.startsWith(RESOURCE_PREFIX)
				? param.path.absolutePath.substring(RESOURCE_PREFIX.length())
				: null;
	}
}

/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl.model;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.mcpclient.content.handler.impl.GMCPClientRemoteVirtualFilesystemConsumingServiceImpl;
import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * Represents an MCP object — the server (folder) or a resource (leaf) — as a
 * node of the virtual filesystem navigation.
 * <p>
 * The {@code setServer(...)} / {@code setResource(...)} mutators stamp the
 * {@link #getResourceReferenceMetaInfos() metadata map} with the keys the
 * {@link GMCPClientRemoteVirtualFilesystemConsumingServiceImpl consuming service}
 * later reads back to rebuild a {@link MCPClientResourceReference} and stream the
 * content.
 */
public class MCPClientNativePositionObject extends AbstractNativePositionObject {

	private static final String DEFAULT_RESOURCE_CONTENT_TYPE = "text/plain";

	private String code = null;
	private String name = null;
	private String url = null;
	private boolean folder = false;
	private boolean resource = false;
	private String resourceContentType = null;
	private Date resourceModificationTime = null;
	private Long resourceFileSize = null;
	private final HashMap<String, Object> resourceReferenceMetaInfos = new HashMap<String, Object>();

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public boolean isResource() {
		return resource;
	}

	@Override
	public boolean isFolder() {
		return folder;
	}

	@Override
	public HashMap<String, Object> getResourceReferenceMetaInfos() {
		return resourceReferenceMetaInfos;
	}

	@Override
	public String getResourceContentType() {
		return resourceContentType;
	}

	@Override
	public Date getResourceModificationTime() {
		return resourceModificationTime;
	}

	@Override
	public Long getResourceFileSize() {
		return resourceFileSize;
	}

	/**
	 * Configures this node as the MCP server root folder (the virtual drive).
	 *
	 * @param serverCode the code identifying the MCP server (its system code)
	 * @param serverName a user readable name for the server
	 * @param serverUrl  the server base URL, when known
	 */
	public void setServer(String serverCode, String serverName, String serverUrl) {
		this.code = serverCode;
		this.name = serverName;
		this.url = serverUrl;
		this.folder = true;
		this.resource = false;
		resourceReferenceMetaInfos.put(GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_OBJECT_TYPE,
				MCPClientPathNodeType.SERVER.name());
	}

	/**
	 * Configures this node as a single streamable MCP resource (a leaf document).
	 *
	 * @param resourceUri  the canonical MCP resource URI
	 * @param resourceName a user readable name for the resource
	 * @param mimeType     the advertised MIME type, or {@code null}
	 */
	public void setResource(String resourceUri, String resourceName, String mimeType) {
		this.code = resourceUri;
		this.name = resourceName != null ? resourceName : resourceUri;
		this.url = resourceUri;
		this.folder = false;
		this.resource = true;
		this.resourceContentType = mimeType != null ? mimeType : DEFAULT_RESOURCE_CONTENT_TYPE;
		resourceReferenceMetaInfos.put(GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_OBJECT_TYPE,
				MCPClientPathNodeType.RESOURCE.name());
		resourceReferenceMetaInfos.put(GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_RESOURCE_URI,
				resourceUri);
		if (resourceName != null) {
			resourceReferenceMetaInfos.put(GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_RESOURCE_NAME,
					resourceName);
		}
		if (this.resourceContentType != null) {
			resourceReferenceMetaInfos.put(GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_RESOURCE_MIME,
					this.resourceContentType);
		}
	}

	/**
	 * @return {@code true} when this node addresses the MCP server root folder.
	 */
	public boolean isServer() {
		return folder;
	}
}

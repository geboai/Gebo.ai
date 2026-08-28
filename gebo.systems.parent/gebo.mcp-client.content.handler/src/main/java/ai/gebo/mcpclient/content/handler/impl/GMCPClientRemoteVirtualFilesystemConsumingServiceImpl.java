/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.service.McpClientManagementService;
import ai.gebo.architecture.mcpclients.service.impl.McpClientPool;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.mcpclient.content.handler.GMCPClientSystem;
import ai.gebo.mcpclient.content.handler.IGMCPClientVirtualFilesystemConsumingService;
import ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint;
import ai.gebo.mcpclient.content.handler.impl.model.MCPClientNativePositionObject;
import ai.gebo.mcpclient.content.handler.impl.model.MCPClientNavigationCoordinates;
import ai.gebo.mcpclient.content.handler.impl.model.MCPClientPathComponent;
import ai.gebo.mcpclient.content.handler.impl.model.MCPClientPathNodeType;
import ai.gebo.mcpclient.content.handler.impl.model.MCPClientResourceReference;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Browses an MCP server as a virtual filesystem and streams its resources.
 * <p>
 * The MCP resource model is a flat list, so the hierarchy produced here is only
 * two levels deep: the server root folder (the virtual drive) directly
 * containing one document per exposed resource. Listing resources and reading a
 * resource's content both go through the shared {@link McpClientPool}, which
 * reuses a live, already-authenticated connection resolved from the
 * {@link MCPClientConfig} referenced by the {@link GMCPClientSystem}.
 */
public class GMCPClientRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GMCPClientSystem, MCPClientProjectEndpoint, MCPClientNativePositionObject, MCPClientNavigationCoordinates, MCPClientResourceReference>
		implements IGMCPClientVirtualFilesystemConsumingService {

	/** Messaging module id shared by the MCP content handler and this service. */
	public static final String MCP_CLIENT_MODULE = GStandardModulesConstraints.MCP_CLIENT_MODULE;

	/** Metadata key: the kind of MCP node ({@link MCPClientPathNodeType}). */
	public static final String MCP_OBJECT_TYPE = "MCP-OBJECT-TYPE";

	/** Metadata key: the canonical MCP resource URI. */
	public static final String MCP_RESOURCE_URI = "MCP-RESOURCE-URI";

	/** Metadata key: the MCP resource MIME type. */
	public static final String MCP_RESOURCE_MIME = "MCP-RESOURCE-MIME";

	/** Metadata key: the MCP resource name. */
	public static final String MCP_RESOURCE_NAME = "MCP-RESOURCE-NAME";

	/** Environment/cache key: the resolved {@link MCPClientConfig}. */
	private static final String MCP_CONFIG = "MCP-CONFIG";

	private final McpClientPool clientPool;
	private final McpClientManagementService managementService;

	/**
	 * @param documentFactory   factory used to materialize document references
	 * @param clientPool        pooled MCP connections (reused across calls)
	 * @param managementService resolves the {@link MCPClientConfig} by code
	 */
	public GMCPClientRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			McpClientPool clientPool, McpClientManagementService managementService) {
		super(documentFactory);
		this.clientPool = clientPool;
		this.managementService = managementService;
	}

	@Override
	public String getMessagingModuleId() {
		return MCP_CLIENT_MODULE;
	}

	// ---- environment lifecycle -------------------------------------------------

	@Override
	protected Map<String, Object> createEnvironment(GMCPClientSystem system, MCPClientProjectEndpoint endpoint,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<>();
		environment.put(MCP_CONFIG, resolveConfig(endpoint));
		return environment;
	}

	@Override
	protected Map<String, Object> createEnvironment(GMCPClientSystem system) throws GeboContentHandlerSystemException {
		// The MCP connection is endpoint-scoped; the system-only environment (used by
		// the full-text search path, which MCP does not wire up) carries no config.
		return new HashMap<>();
	}

	@Override
	protected void clearEnvironment(Map<String, Object> environment, GMCPClientSystem system,
			MCPClientProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		environment.clear();
	}

	// ---- navigation ------------------------------------------------------------

	@Override
	protected MCPClientNavigationCoordinates toNavigationPosition(VFilesystemReference path,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return MCPClientNavigationUtil.toNavigationCoordinates(path);
	}

	@Override
	protected List<MCPClientNativePositionObject> toNativeCoordinates(MCPClientNavigationCoordinates position,
			GMCPClientSystem system, MCPClientProjectEndpoint endpoint, GVirtualFolder root, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return buildNativeCoordinates(position, system);
	}

	@Override
	protected List<MCPClientNativePositionObject> toResourcesNativeCoordinates(MCPClientNavigationCoordinates position,
			GMCPClientSystem system, Map<String, Object> environment) throws GeboContentHandlerSystemException {
		return buildNativeCoordinates(position, system);
	}

	/**
	 * Rebuilds the native coordinates for a navigation position: the server root,
	 * optionally followed by a single resource leaf.
	 */
	private List<MCPClientNativePositionObject> buildNativeCoordinates(MCPClientNavigationCoordinates position,
			GMCPClientSystem system) {
		List<MCPClientNativePositionObject> natives = new ArrayList<>();
		MCPClientNativePositionObject server = new MCPClientNativePositionObject();
		server.setServer(system.getCode(), serverName(system), system.getBaseUri());
		natives.add(server);
		if (position.getBrowsingStepsCustom() != null) {
			for (MCPClientPathComponent step : position.getBrowsingStepsCustom()) {
				if (step.type == MCPClientPathNodeType.RESOURCE && step.uri != null) {
					MCPClientNativePositionObject resource = new MCPClientNativePositionObject();
					resource.setResource(step.uri, step.uri, null);
					natives.add(resource);
				}
			}
		}
		return natives;
	}

	@Override
	protected MCPClientNavigationCoordinates getPositionCoordinate(List<MCPClientNativePositionObject> childCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		MCPClientNavigationCoordinates coordinates = new MCPClientNavigationCoordinates();
		if (childCoordinates == null || childCoordinates.isEmpty()) {
			return coordinates;
		}
		MCPClientNativePositionObject server = childCoordinates.get(0);
		GVirtualFilesystemRoot root = new GVirtualFilesystemRoot();
		root.setCode(MCPClientNavigationUtil.SERVER_PREFIX + server.getCode());
		coordinates.setRoot(root);
		for (int i = 1; i < childCoordinates.size(); i++) {
			MCPClientNativePositionObject step = childCoordinates.get(i);
			MCPClientPathComponent component = new MCPClientPathComponent();
			component.type = MCPClientPathNodeType.RESOURCE;
			component.uri = step.getCode();
			coordinates.getBrowsingStepsCustom().add(component);
		}
		return coordinates;
	}

	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<MCPClientNativePositionObject> nativeCoordinates,
			MCPClientNavigationCoordinates position, GMCPClientSystem system, MCPClientProjectEndpoint endpoint,
			IGUserMessagesConsumer messagesConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<>();
		if (nativeCoordinates.isEmpty()) {
			return childs;
		}
		MCPClientNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
		// Only the server root is a container; resources are streamable leaves.
		if (!last.isServer()) {
			return childs;
		}
		MCPClientConfig config = (MCPClientConfig) environment.get(MCP_CONFIG);
		for (McpSchema.Resource resource : listResources(config)) {
			NativeCoordinatePointer pointer = new NativeCoordinatePointer();
			pointer.parentCoordinates = new ArrayList<>(nativeCoordinates);
			pointer.child = new MCPClientNativePositionObject();
			pointer.child.setResource(resource.uri(), resource.name(), resource.mimeType());
			childs.add(pointer);
		}
		return childs;
	}

	// ---- resource handles & streaming ------------------------------------------

	@Override
	public MCPClientResourceReference getResourceHandle(GMCPClientSystem system, MCPClientProjectEndpoint endpoint,
			GAbstractVirtualFilesystemObject reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		return toResourceReference(reference != null ? reference.getCustomMetaInfos() : null);
	}

	@Override
	protected MCPClientResourceReference getResourceHandle(SearchableSystemMetaData system,
			MCPClientNavigationCoordinates navigationPosition, List<MCPClientNativePositionObject> nativeCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		MCPClientNativePositionObject last = nativeCoordinates != null && !nativeCoordinates.isEmpty()
				? nativeCoordinates.get(nativeCoordinates.size() - 1)
				: null;
		return toResourceReference(last != null ? last.getResourceReferenceMetaInfos() : null);
	}

	@Override
	public InputStream streamResource(GMCPClientSystem system, MCPClientProjectEndpoint endpoint,
			MCPClientResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		MCPClientConfig config = (MCPClientConfig) cache.get(MCP_CONFIG);
		if (config == null) {
			config = resolveConfig(endpoint);
			cache.put(MCP_CONFIG, config);
		}
		return doStream(config, reference);
	}

	@Override
	protected InputStream streamResource(GMCPClientSystem system, MCPClientResourceReference remoteReference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException, IOException {
		MCPClientConfig config = (MCPClientConfig) environment.get(MCP_CONFIG);
		if (config == null) {
			throw new GeboContentHandlerSystemException(
					"MCP resource streaming requires an endpoint-scoped configuration in the environment");
		}
		return doStream(config, remoteReference);
	}

	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GMCPClientSystem system,
			MCPClientProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc, MCPClientResourceReference reference,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		// MCP servers expose no per-resource change metadata; deletion detection is
		// left to the periodic re-listing performed by a fresh consume cycle.
		return null;
	}

	// ---- descriptions ----------------------------------------------------------

	@Override
	protected String describeObject(List<MCPClientNativePositionObject> references, GMCPClientSystem system,
			MCPClientProjectEndpoint endpoint, Map<String, Object> environment) {
		if (references != null && !references.isEmpty()) {
			MCPClientNativePositionObject last = references.get(references.size() - 1);
			if (last.isServer()) {
				return "MCP server " + last.getName();
			}
			return "MCP resource " + last.getName();
		}
		return null;
	}

	@Override
	protected String describeSystem(GMCPClientSystem system) {
		return "MCP server " + safe(system.getDescription(), system.getCode());
	}

	@Override
	protected String describeProjectEndpoint(GMCPClientSystem system, MCPClientProjectEndpoint endpoint,
			Map<String, Object> environment) {
		return "MCP data source " + safe(system.getDescription(), system.getCode());
	}

	// ---- helpers ---------------------------------------------------------------

	private MCPClientResourceReference toResourceReference(Map<String, Object> meta) {
		MCPClientResourceReference reference = new MCPClientResourceReference();
		if (meta != null) {
			reference.uri = extractString(meta, MCP_RESOURCE_URI);
			reference.name = extractString(meta, MCP_RESOURCE_NAME);
			reference.mimeType = extractString(meta, MCP_RESOURCE_MIME);
		}
		return reference;
	}

	private InputStream doStream(MCPClientConfig config, MCPClientResourceReference reference)
			throws GeboContentHandlerSystemException {
		if (reference == null || reference.uri == null) {
			return null;
		}
		try {
			return clientPool.execute(config, client -> readResourceContent(client, reference.uri));
		} catch (Exception e) {
			throw new GeboContentHandlerSystemException("Cannot read MCP resource " + reference.uri, e);
		}
	}

	/**
	 * Reads a single MCP resource and flattens its (possibly multiple) content
	 * parts into a single in-memory stream: text parts are appended verbatim,
	 * binary (blob) parts are base64-decoded.
	 */
	private InputStream readResourceContent(io.modelcontextprotocol.client.McpSyncClient client, String uri)
			throws IOException {
		McpSchema.ReadResourceResult result = client.readResource(new McpSchema.ReadResourceRequest(uri));
		if (result == null || result.contents() == null) {
			return null;
		}
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		for (McpSchema.ResourceContents content : result.contents()) {
			if (content instanceof McpSchema.TextResourceContents text) {
				if (text.text() != null) {
					buffer.writeBytes(text.text().getBytes(StandardCharsets.UTF_8));
				}
			} else if (content instanceof McpSchema.BlobResourceContents blob) {
				if (blob.blob() != null) {
					buffer.writeBytes(Base64.getDecoder().decode(blob.blob()));
				}
			}
		}
		return new ByteArrayInputStream(buffer.toByteArray());
	}

	private List<McpSchema.Resource> listResources(MCPClientConfig config) throws GeboContentHandlerSystemException {
		try {
			return clientPool.execute(config, client -> {
				McpSchema.ListResourcesResult result = client.listResources();
				return result != null && result.resources() != null ? result.resources() : List.<McpSchema.Resource>of();
			});
		} catch (Exception e) {
			throw new GeboContentHandlerSystemException("Cannot list resources of MCP server " + config.getCode(), e);
		}
	}

	private MCPClientConfig resolveConfig(MCPClientProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		if (endpoint == null || endpoint.getMcpClientConfigCode() == null) {
			throw new GeboContentHandlerSystemException(
					"MCP endpoint " + (endpoint != null ? endpoint.getCode() : null) + " has no mcpClientConfigCode");
		}
		OperationStatus<MCPClientConfig> status = managementService.findByCode(endpoint.getMcpClientConfigCode());
		MCPClientConfig config = status != null ? status.getResult() : null;
		if (config == null) {
			throw new GeboContentHandlerSystemException(
					"MCP client configuration '" + endpoint.getMcpClientConfigCode() + "' cannot be resolved");
		}
		return config;
	}

	private static String serverName(GMCPClientSystem system) {
		return safe(system.getDescription(), system.getCode());
	}

	private static String safe(String value, String fallback) {
		return value != null && !value.isBlank() ? value : fallback;
	}

	private static String extractString(Map<String, Object> meta, String key) {
		Object value = meta.get(key);
		return value != null ? value.toString() : null;
	}
}

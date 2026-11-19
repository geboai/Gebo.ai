/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.service.impl;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.files.GLibraryFile;
import ai.gebo.architecture.ai.files.IGConversationFilesKeeper;
import ai.gebo.architecture.ai.files.IGConversationGeneratedFilesStore;
import org.springframework.beans.factory.ObjectProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.service.IGExternalToolCallback;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * A {@link ToolCallback} that exposes a single remote MCP tool to the Gebo tool
 * pipeline.
 * <p>
 * The tool definition (name/description/inputSchema) is built up-front from the
 * configuration that was captured at discovery time, so listing the callbacks
 * does not require any network call. The connection to the MCP server is obtained
 * lazily, only when the tool is actually invoked, from the {@link McpClientPool}
 * (which reuses a live, already-authenticated connection across calls).
 */
class McpRemoteToolCallback implements IGExternalToolCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(McpRemoteToolCallback.class);

	private static final String EMPTY_OBJECT_SCHEMA = "{\"type\":\"object\",\"properties\":{}}";

	private final McpClientPool clientPool;
	private final MCPClientConfig config;
	/** The tool name as known by the remote server (without the exporting prefix). */
	private final String remoteToolName;
	private final ToolDefinition toolDefinition;

	private final ObjectProvider<IGConversationGeneratedFilesStore> generatedFiles;

	McpRemoteToolCallback(McpClientPool clientPool, MCPClientConfig config, String exposedName, String remoteToolName,
			String description, String inputSchema) {
		this(clientPool, config, exposedName, remoteToolName, description, inputSchema, null);
	}

	McpRemoteToolCallback(McpClientPool clientPool, MCPClientConfig config, String exposedName, String remoteToolName,
			String description, String inputSchema, ObjectProvider<IGConversationGeneratedFilesStore> generatedFiles) {
		this.clientPool = clientPool;
		this.config = config;
		this.remoteToolName = remoteToolName;
		this.generatedFiles = generatedFiles;
		this.toolDefinition = ToolDefinition.builder().name(exposedName)
				.description(description != null ? description : exposedName)
				.inputSchema(isBlank(inputSchema) ? EMPTY_OBJECT_SCHEMA : inputSchema).build();
	}

	MCPClientConfig getConfig() {
		return config;
	}

	@Override
	public ToolDefinition getToolDefinition() {
		return toolDefinition;
	}

	@Override
	public String call(String toolInput) {
		return call(toolInput, null);
	}

	@Override
	public String call(String toolInput, ToolContext toolContext) {
		try {
			return clientPool.execute(config, client -> {
				McpSchema.CallToolRequest.Builder requestBuilder = McpSchema.CallToolRequest.builder(remoteToolName);
				if (!isBlank(toolInput)) {
					// Let the MCP json mapper parse the raw JSON arguments produced by the LLM.
					requestBuilder.arguments(McpJsonDefaults.getMapper(), toolInput);
				}
				return renderResult(client.callTool(requestBuilder.build()), toolContext);
			});
		} catch (Throwable t) {
			LOGGER.error("Error calling MCP tool '" + remoteToolName + "' on client '" + config.getCode() + "'", t);
			return "Error calling tool '" + remoteToolName + "': " + t.getMessage();
		}
	}

	/**
	 * Flattens the MCP {@link McpSchema.CallToolResult} into a plain string for the
	 * LLM: concatenated text contents, with non-text contents described by type, and
	 * an explicit error marker when the server flagged the call as failed.
	 */
	/**
	 * The result of a remote tool as the model reads it. Its texts are given as they
	 * are. A picture, a recording or a file it returns is kept in the conversation
	 * the tool was called for and named in the text, so that it is not lost: the user
	 * finds it among the conversation's files, the tools working on files open it,
	 * and a model that takes such files sees it on the next requests. Outside a
	 * conversation it is named by its kind and type only. The structured content of a
	 * result that has no text is given as JSON.
	 */
	String renderResult(McpSchema.CallToolResult result, ToolContext toolContext) {
		if (result == null) {
			return "(no result)";
		}
		StringBuilder sb = new StringBuilder();
		boolean hasText = false;
		int files = 0;
		if (result.content() != null) {
			for (McpSchema.Content content : result.content()) {
				if (content instanceof McpSchema.TextContent textContent) {
					sb.append(textContent.text());
					hasText = hasText || !isBlank(textContent.text());
				} else if (content instanceof McpSchema.ImageContent image) {
					sb.append(keep("image", null, image.mimeType(), image.data(), ++files, toolContext));
				} else if (content instanceof McpSchema.AudioContent audio) {
					sb.append(keep("audio", null, audio.mimeType(), audio.data(), ++files, toolContext));
				} else if (content instanceof McpSchema.EmbeddedResource embedded
						&& embedded.resource() instanceof McpSchema.TextResourceContents text) {
					sb.append(text.text());
					hasText = hasText || !isBlank(text.text());
				} else if (content instanceof McpSchema.EmbeddedResource embedded
						&& embedded.resource() instanceof McpSchema.BlobResourceContents blob) {
					sb.append(keep("file", blob.uri(), blob.mimeType(), blob.blob(), ++files, toolContext));
				} else if (content instanceof McpSchema.ResourceLink link) {
					sb.append("[resource ").append(link.name() != null ? link.name() + " " : "").append(link.uri())
							.append(link.mimeType() != null ? " (" + link.mimeType() + ")" : "").append("]");
				} else if (content != null) {
					sb.append("[").append(content.getClass().getSimpleName()).append(" content]");
				}
			}
		}
		if (!hasText && result.structuredContent() != null) {
			try {
				sb.append(McpJsonDefaults.getMapper().writeValueAsString(result.structuredContent()));
			} catch (Exception unreadable) {
				LOGGER.warn("The structured content of MCP tool '" + remoteToolName + "' cannot be rendered: "
						+ unreadable);
			}
		}
		String rendered = sb.length() > 0 ? sb.toString() : "(no content)";
		if (Boolean.TRUE.equals(result.isError())) {
			return "ERROR: " + rendered;
		}
		return rendered;
	}

	/**
	 * Keeps a file a remote tool returned in the conversation and tells the model where
	 * it is; when it cannot be kept - no conversation, no store, unreadable data - the
	 * model is told its kind and type, as before.
	 */
	private String keep(String kind, String uri, String mimeType, String base64, int position,
			ToolContext toolContext) {
		final String described = "[" + kind + (mimeType != null ? " " + mimeType : "") + " content]";
		final String session = ToolCallbackDeclarationUtil.chatSessionCode(toolContext);
		final IGConversationGeneratedFilesStore store = generatedFiles != null ? generatedFiles.getIfAvailable() : null;
		if (session == null || store == null || isBlank(base64)) {
			return described;
		}
		try {
			final byte[] data = java.util.Base64.getDecoder().decode(base64.trim());
			String name = null;
			if (!isBlank(uri)) {
				final String path = uri.contains("?") ? uri.substring(0, uri.indexOf('?')) : uri;
				name = path.substring(Math.max(path.lastIndexOf('/'), path.lastIndexOf(':')) + 1);
			}
			if (isBlank(name)) {
				name = toolDefinition.name() + "-" + kind + "-" + position;
			}
			final GLibraryFile file = store.store(session, name, mimeType, data);
			IGConversationFilesKeeper.report(toolContext, file);
			return "[" + kind + " kept in this conversation as " + file.location() + ":" + file.path() + " ("
					+ file.contentType() + ", " + file.size() + " bytes)]";
		} catch (Exception e) {
			LOGGER.error("The " + kind + " returned by MCP tool '" + remoteToolName + "' cannot be kept in session "
					+ session, e);
			return described;
		}
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}

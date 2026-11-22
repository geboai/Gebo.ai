/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import ai.gebo.llms.agent.standard.services.UserKnowledgeBasesExecutionEnvironment;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import lombok.AllArgsConstructor;

/**
 * Exports networks of agents as MCP tools: each configured network becomes a
 * tool that takes a request in natural language and answers with the text the
 * network produced, run as the caller the MCP request authenticated, over the
 * knowledge bases that caller can see.
 */
@Service
@lombok.RequiredArgsConstructor
public class GeboMCPAgentsNetworkAsToolsProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMCPAgentsNetworkAsToolsProvider.class);

	private final IAgentsNetworkDao agentsNetworkDao;
	private final GTextAgentsNetworkRunner runner;
	private final GeboMcpSecurityContextSupport securitySupport;
	private final UserKnowledgeBasesExecutionEnvironment userEnvironment;
	/** Builds what the MCP server adds to every model call of a tool's run (see {@link #toolCallingOf}). */
	private final GeboToolCallingManagerFactory toolCallingFactory;

	/** The caller an MCP tool's run names itself as: {@code mcp:<server url>/<tool>}. */
	public static final String MCP_CALLER_PREFIX = "mcp:";

	/**
	 * What the MCP server adds to every model call of a tool's run (see
	 * {@link GeboToolCalling}): the server and the tool as the caller, the platform's
	 * loop limits, no listener. One per call: a run's records are its own.
	 */
	GeboToolCalling toolCallingOf(GeboMCPServerConfig config, String toolName) {
		return toolCallingFactory.builder()
				.caller(MCP_CALLER_PREFIX + (config != null ? config.getExportedUniqueRelativeUrl() : null) + "/"
						+ toolName)
				.build();
	}

	/** How long the frames of a run in a conversation are waited for once the run returned. */
	private static final java.time.Duration FRAMES_COLLECTION_TIMEOUT = java.time.Duration.ofMinutes(10);
	/** Runs a network in a conversation; absent in a unit test. */
	@org.springframework.beans.factory.annotation.Autowired(required = false)
	private ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner chatRunner;
	/** Reads the files a run in a conversation left; absent in a unit test. */
	@org.springframework.beans.factory.annotation.Autowired(required = false)
	private ai.gebo.llms.agent.standard.services.ChatRunFiles runFiles;

	void setChatRunner(ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner chatRunner) {
		this.chatRunner = chatRunner;
	}

	void setRunFiles(ai.gebo.llms.agent.standard.services.ChatRunFiles runFiles) {
		this.runFiles = runFiles;
	}

	/** Whether the export names a chat profile and this installation can run in a conversation. */
	private boolean runsInAConversation(GeboMCPAgentsNetworkTool export) {
		return chatRunner != null && export != null && export.getChatProfileCode() != null
				&& !export.getChatProfileCode().isBlank();
	}

	/**
	 * The tools of the exported networks. A network exported with a chat profile
	 * answers through a handler of its own, since its answer is more than text: the
	 * files of its run travel in it (see {@link #callInAConversation}).
	 */
	public List<SyncToolSpecification> buildTools(GeboMCPServerConfig config) {
		final List<SyncToolSpecification> specifications = new ArrayList<>();
		final List<GeboMCPAgentsNetworkTool> exports = config.getAgentNetworkAsTools() != null
				? config.getAgentNetworkAsTools()
				: List.of();
		final java.util.Map<String, GeboMCPAgentsNetworkTool> inAConversation = new java.util.HashMap<>();
		for (GeboMCPAgentsNetworkTool export : exports) {
			if (runsInAConversation(export) && export.getAgentsNetworkCode() != null) {
				inAConversation.put(toolNameOf(export), export);
			}
		}
		for (ToolCallback callback : callbacks(config)) {
			final SyncToolSpecification plain = McpToolUtils.toSyncToolSpecification(callback);
			final GeboMCPAgentsNetworkTool export = inAConversation.get(callback.getToolDefinition().name());
			if (export == null) {
				specifications.add(plain);
				continue;
			}
			final String toolName = callback.getToolDefinition().name();
			specifications.add(SyncToolSpecification.builder().tool(plain.tool())
					.callHandler((exchange, request) -> securitySupport.runAs(
							exchange != null ? exchange.transportContext() : null,
							() -> callInAConversation(config, export, toolName, requestTextOf(request))))
					.build());
		}
		return specifications;
	}

	private static String toolNameOf(GeboMCPAgentsNetworkTool export) {
		return export.getToolName() != null && !export.getToolName().isBlank() ? export.getToolName()
				: export.getAgentsNetworkCode();
	}

	private static String requestTextOf(io.modelcontextprotocol.spec.McpSchema.CallToolRequest request) {
		final Object text = request != null && request.arguments() != null ? request.arguments().get("request") : null;
		return text != null ? text.toString() : "";
	}

	/**
	 * A call of a network exported with a chat profile: the network runs in a new
	 * conversation of the caller, in the environment of that profile, and the tool
	 * answers with the text of its answer and the files the run left - a picture as
	 * image content, a recording as audio content, any other file as an embedded
	 * resource - bytes included, since an MCP client has no way to fetch them from
	 * Gebo. A run that waits for an answer says so in its text, with its questions.
	 */
	io.modelcontextprotocol.spec.McpSchema.CallToolResult callInAConversation(GeboMCPServerConfig config,
			GeboMCPAgentsNetworkTool export, String toolName, String text) {
		final io.modelcontextprotocol.spec.McpSchema.CallToolResult.Builder result = io.modelcontextprotocol.spec.McpSchema.CallToolResult
				.builder();
		ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun run = null;
		try {
			run = chatRunner.run(export.getAgentsNetworkCode(),
					chatRunner.requestOf(text, export.getChatProfileCode(), null),
					ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatRunAttachments.none(),
					toolCallingOf(config, toolName), GTextAgentsNetworkRunner.LOGGING_SINK);
			ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = null;
			final StringBuilder streamed = new StringBuilder();
			final List<ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope> frames = run
					.getFrames().collectList().block(FRAMES_COLLECTION_TIMEOUT);
			if (frames != null) {
				for (ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope<?> frame : frames) {
					final Object content = frame != null ? frame.getContent() : null;
					if (content instanceof ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse carried) {
						response = carried;
					} else if (content instanceof String chunk) {
						streamed.append(chunk);
					}
				}
			}
			// the files the run wrote and generated are kept on the run's own response,
			// not always the one the last frame carries
			response = ai.gebo.llms.agent.standard.services.ChatRunFiles.withFilesOfTheRun(response,
					run.getResponse());
			String answer = response != null && response.getQueryResponse() != null
					&& !response.getQueryResponse().isBlank() ? response.getQueryResponse() : streamed.toString();
			if (run.isSuspended()) {
				final String waits = "The work waits for an answer before it can go on, in conversation "
						+ run.getChatContextCode() + ":\n"
						+ ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport
								.render(run.getSuspendedState());
				answer = answer == null || answer.isBlank() ? waits : answer + "\n\n" + waits;
			}
			result.addTextContent(answer != null ? answer : "");
			int files = 0;
			if (runFiles != null) {
				for (ai.gebo.llms.agent.standard.services.ChatRunFiles.RunFile file : runFiles.of(response)) {
					final String data = java.util.Base64.getEncoder().encodeToString(file.bytes());
					if (file.mimeType().startsWith("image/")) {
						result.addContent(
								new io.modelcontextprotocol.spec.McpSchema.ImageContent(null, data, file.mimeType()));
					} else if (file.mimeType().startsWith("audio/")) {
						result.addContent(
								new io.modelcontextprotocol.spec.McpSchema.AudioContent(null, data, file.mimeType()));
					} else {
						result.addContent(new io.modelcontextprotocol.spec.McpSchema.EmbeddedResource(null,
								new io.modelcontextprotocol.spec.McpSchema.BlobResourceContents(
										"gebo://conversation/" + run.getChatContextCode() + "/"
												+ java.net.URLEncoder.encode(file.name(),
														java.nio.charset.StandardCharsets.UTF_8),
										file.mimeType(), data)));
					}
					files++;
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("MCP tool '" + toolName + "' (network " + export.getAgentsNetworkCode()
						+ ") ran in conversation " + run.getChatContextCode() + " of profile "
						+ export.getChatProfileCode() + " and answers with " + files + " file(s)");
			}
			return result.isError(false).build();
		} catch (AgentException | RuntimeException e) {
			LOGGER.error("MCP tool '" + toolName + "' (network " + export.getAgentsNetworkCode() + ") failed", e);
			return io.modelcontextprotocol.spec.McpSchema.CallToolResult.builder()
					.addTextContent("The network could not answer: " + e.getMessage()).isError(true).build();
		} finally {
			if (run != null) {
				run.dispose();
			}
		}
	}

	/** The tools of the exports of the server, as tool callbacks. */
	List<ToolCallback> callbacks(GeboMCPServerConfig config) {
		List<GeboMCPAgentsNetworkTool> exports = config.getAgentNetworkAsTools();
		List<ToolCallback> callbacks = new ArrayList<>();
		if (exports == null || exports.isEmpty()) {
			return callbacks;
		}
		for (GeboMCPAgentsNetworkTool export : exports) {
			if (export == null || export.getAgentsNetworkCode() == null || export.getAgentsNetworkCode().isBlank()) {
				continue;
			}
			final String networkCode = export.getAgentsNetworkCode();
			GAgentsNetwork network = agentsNetworkDao.findByCode(networkCode);
			if (network == null) {
				LOGGER.warn("MCP server '{}' exports network '{}' as a tool, but the network does not exist",
						config.getExportedUniqueRelativeUrl(), networkCode);
				continue;
			}
			String toolName = export.getToolName() != null && !export.getToolName().isBlank() ? export.getToolName()
					: networkCode;
			String description = "Network of agents: "
					+ (network.getDescription() != null && !network.getDescription().isBlank()
							? network.getDescription()
							: networkCode);
			ToolCallback callback = ToolCallbackDeclarationUtil.declare((GeboMcpAgentToolRequest input, ToolContext ctx) -> {
				try {
					return runner.runNetwork(networkCode, input != null ? input.getRequest() : "", null,
							toolCallingOf(config, toolName));
				} catch (AgentException e) {
					LOGGER.error("MCP tool '" + toolName + "' (network " + networkCode + ") failed", e);
					throw new IllegalStateException("The network could not answer: " + e.getMessage(), e);
				}
			}, toolName, description, GeboMcpAgentToolRequest.class, String.class);
			callbacks.add(new GeboMcpSecurityAwareToolCallback(callback, securitySupport, userEnvironment));
		}
		return callbacks;
	}
}

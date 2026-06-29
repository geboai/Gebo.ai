/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.mcpclients.model.BaseMCPObject;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.MCPPrompt;
import ai.gebo.architecture.mcpclients.model.MCPResource;
import ai.gebo.architecture.mcpclients.model.MCPTool;
import ai.gebo.architecture.mcpclients.model.MCPTransportType;
import ai.gebo.architecture.mcpclients.repository.McpClientConfigRepository;
import ai.gebo.architecture.mcpclients.service.McpClientManagementService;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Default {@link McpClientManagementService} implementation.
 * <p>
 * Discovery is delegated to {@link McpClientConnector} (transport + auth), while
 * persistence and ownership/security stamping reuse the platform
 * {@link McpClientConfigRepository} and {@link IGSecurityService}, mirroring the
 * conventions of the other Gebo CRUD services.
 */
@Service
@AllArgsConstructor
public class McpClientManagementServiceImpl implements McpClientManagementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(McpClientManagementServiceImpl.class);

	private final McpClientConfigRepository repository;
	private final IGSecurityService securityService;
	private final McpClientConnector connector;

	@Override
	public OperationStatus<MCPClientConfig> testAndDiscovery(@NotNull @Valid MCPClientConfig config) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin testAndDiscovery(...) mcp client code:{}", config != null ? config.getCode() : null);
		}
		List<GUserMessage> messages = new ArrayList<>();
		validateConnectionConfig(config, messages);
		if (hasErrors(messages)) {
			return reject(messages);
		}

		// "First discovery" when the config is not yet persisted: either no code at
		// all, or a code that is not present in MongoDB.
		boolean firstDiscovery = isBlank(config.getCode()) || repository.findById(config.getCode()).isEmpty();

		McpSyncClient client = null;
		try {
			client = connector.connectAndInitialize(config);

			List<McpSchema.Tool> remoteTools = listToolsSafe(client, messages);
			List<McpSchema.Resource> remoteResources = listResourcesSafe(client, messages);
			List<McpSchema.Prompt> remotePrompts = listPromptsSafe(client, messages);

			config.setTools(diff(config.getTools(), remoteTools, McpSchema.Tool::name, BaseMCPObject::getName,
					tool -> mapTool(tool), config, firstDiscovery));
			config.setResources(diff(config.getResources(), remoteResources, McpSchema.Resource::uri,
					MCPResource::getUri, resource -> mapResource(resource), config, firstDiscovery));
			config.setPrompts(diff(config.getPrompts(), remotePrompts, McpSchema.Prompt::name, BaseMCPObject::getName,
					prompt -> mapPrompt(prompt), config, firstDiscovery));

			messages.add(GUserMessage.successMessage("MCP server reachable",
					"Discovery completed: " + size(config.getTools()) + " tool(s), " + size(config.getResources())
							+ " resource(s), " + size(config.getPrompts()) + " prompt(s)"
							+ (firstDiscovery ? " (first discovery)" : "")));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End testAndDiscovery(...) mcp client code:{} ok", config.getCode());
			}
			return OperationStatus.of(config, messages);
		} catch (Throwable t) {
			LOGGER.error("Error during MCP testAndDiscovery for code:" + config.getCode(), t);
			messages.add(GUserMessage.errorMessage("Cannot reach the MCP server", t));
			return reject(messages);
		} finally {
			connector.closeQuietly(client);
		}
	}

	@Override
	public OperationStatus<MCPClientConfig> insert(@NotNull @Valid MCPClientConfig config) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin insert(...) mcp client code:{}", config != null ? config.getCode() : null);
		}
		List<GUserMessage> messages = new ArrayList<>();
		validateConnectionConfig(config, messages);
		if (!isBlank(config.getCode()) && repository.findById(config.getCode()).isPresent()) {
			messages.add(GUserMessage.errorMessage("MCP client already exists",
					"An MCP client with code '" + config.getCode() + "' already exists; use update instead"));
		}
		if (hasErrors(messages)) {
			return reject(messages);
		}
		try {
			String username = currentUsername();
			Date now = new Date();
			if (isBlank(config.getCode())) {
				config.setCode(UUID.randomUUID().toString());
			}
			config.setUserCreated(username);
			config.setUserModified(username);
			config.setDateCreated(now);
			config.setDateModified(now);
			MCPClientConfig saved = repository.insert(config);
			messages.add(GUserMessage.successMessage("MCP client created",
					"MCP client '" + saved.getCode() + "' has been created"));
			return OperationStatus.of(saved, messages);
		} catch (Throwable t) {
			LOGGER.error("Error inserting MCP client", t);
			messages.add(GUserMessage.errorMessage("Error inserting MCP client", t));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<MCPClientConfig> update(@NotNull @Valid MCPClientConfig config) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin update(...) mcp client code:{}", config != null ? config.getCode() : null);
		}
		List<GUserMessage> messages = new ArrayList<>();
		validateConnectionConfig(config, messages);
		if (isBlank(config.getCode())) {
			messages.add(GUserMessage.errorMessage("Missing code", "The MCP client to update must have a code"));
			return reject(messages);
		}
		Optional<MCPClientConfig> existingOpt = repository.findById(config.getCode());
		if (existingOpt.isEmpty()) {
			messages.add(GUserMessage.errorMessage("MCP client does not exist",
					"No MCP client with code '" + config.getCode() + "' exists; use insert to create it"));
		}
		if (hasErrors(messages)) {
			return reject(messages);
		}
		try {
			MCPClientConfig existing = existingOpt.get();
			securityService.checkBeingCreator(existing);
			// Preserve creation audit, refresh modification audit.
			config.setUserCreated(existing.getUserCreated());
			config.setDateCreated(existing.getDateCreated());
			config.setUserModified(currentUsername());
			config.setDateModified(new Date());
			MCPClientConfig saved = repository.save(config);
			messages.add(GUserMessage.successMessage("MCP client updated",
					"MCP client '" + saved.getCode() + "' has been updated"));
			return OperationStatus.of(saved, messages);
		} catch (SecurityException se) {
			LOGGER.warn("Rejected update of MCP client {} due to security", config.getCode());
			messages.add(GUserMessage.errorMessage("Not allowed",
					"You are not allowed to update MCP client '" + config.getCode() + "'"));
			return reject(messages);
		} catch (Throwable t) {
			LOGGER.error("Error updating MCP client", t);
			messages.add(GUserMessage.errorMessage("Error updating MCP client", t));
			return reject(messages);
		}
	}

	@Override
	public OperationStatus<Boolean> delete(@NotNull @Valid MCPClientConfig config) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin delete(...) mcp client code:{}", config != null ? config.getCode() : null);
		}
		List<GUserMessage> messages = new ArrayList<>();
		if (config == null || isBlank(config.getCode())) {
			messages.add(GUserMessage.errorMessage("Missing MCP client", "No MCP client (or code) was provided"));
			return rejectBoolean(messages);
		}
		Optional<MCPClientConfig> existingOpt = repository.findById(config.getCode());
		if (existingOpt.isEmpty()) {
			messages.add(GUserMessage.errorMessage("MCP client does not exist",
					"No MCP client with code '" + config.getCode() + "' exists"));
			return rejectBoolean(messages);
		}
		try {
			MCPClientConfig existing = existingOpt.get();
			securityService.checkBeingCreator(existing);
			repository.delete(existing);
			messages.add(GUserMessage.successMessage("MCP client deleted",
					"MCP client '" + existing.getCode() + "' has been deleted"));
			return OperationStatus.of(Boolean.TRUE, messages);
		} catch (SecurityException se) {
			LOGGER.warn("Rejected delete of MCP client {} due to security", config.getCode());
			messages.add(GUserMessage.errorMessage("Not allowed",
					"You are not allowed to delete MCP client '" + config.getCode() + "'"));
			return rejectBoolean(messages);
		} catch (Throwable t) {
			LOGGER.error("Error deleting MCP client", t);
			messages.add(GUserMessage.errorMessage("Error deleting MCP client", t));
			return rejectBoolean(messages);
		}
	}

	@Override
	public OperationStatus<MCPClientConfig> findByCode(String code) {
		if (isBlank(code)) {
			return OperationStatus.ofError("Missing code", "No MCP client code was provided");
		}
		Optional<MCPClientConfig> found = repository.findById(code);
		if (found.isEmpty()) {
			return OperationStatus.ofError("MCP client not found", "No MCP client with code '" + code + "' exists");
		}
		return OperationStatus.of(found.get());
	}

	@Override
	public Page<MCPClientConfig> list(Pageable pageable) {
		return repository.findAll(pageable != null ? pageable : Pageable.unpaged());
	}

	/**
	 * Diffs the previously stored features against the ones discovered on the remote
	 * server, returning the merged list with the {@code addedOnMCPServer} /
	 * {@code deletedOnMCPServer} flags set as specified by
	 * {@link McpClientManagementService#testAndDiscovery(MCPClientConfig)}.
	 *
	 * @param stored         the features currently stored on the config (may be null)
	 * @param remote         the features discovered on the MCP server
	 * @param remoteKey      extracts the match key from a remote feature
	 * @param localKey       extracts the match key from a stored feature
	 * @param mapper         maps a remote feature to a new model object
	 * @param config         the owning config, source of the security attributes
	 * @param firstDiscovery whether this is the very first discovery
	 */
	private <R, M extends BaseMCPObject> List<M> diff(List<M> stored, List<R> remote, Function<R, String> remoteKey,
			Function<M, String> localKey, Function<R, M> mapper, MCPClientConfig config, boolean firstDiscovery) {
		List<M> out = new ArrayList<>();
		Map<String, M> storedByKey = new LinkedHashMap<>();
		if (stored != null) {
			for (M item : stored) {
				storedByKey.put(localKey.apply(item), item);
			}
		}
		Map<String, Boolean> seenRemoteKeys = new LinkedHashMap<>();
		for (R remoteItem : remote) {
			String key = remoteKey.apply(remoteItem);
			seenRemoteKeys.put(key, Boolean.TRUE);
			M existing = firstDiscovery ? null : storedByKey.get(key);
			if (existing == null) {
				// New on the server (or first discovery): create, stamp config security.
				M created = mapper.apply(remoteItem);
				applySecurity(created, config);
				created.setAddedOnMCPServer(Boolean.TRUE);
				created.setDeletedOnMCPServer(Boolean.FALSE);
				out.add(created);
			} else {
				// Still present: keep its stored security, clear the flags.
				existing.setAddedOnMCPServer(Boolean.FALSE);
				existing.setDeletedOnMCPServer(Boolean.FALSE);
				out.add(existing);
			}
		}
		if (!firstDiscovery && stored != null) {
			// Present in the passed config but gone from the server: flag as deleted.
			for (M item : stored) {
				if (!seenRemoteKeys.containsKey(localKey.apply(item))) {
					item.setAddedOnMCPServer(Boolean.FALSE);
					item.setDeletedOnMCPServer(Boolean.TRUE);
					out.add(item);
				}
			}
		}
		return out;
	}

	private MCPTool mapTool(McpSchema.Tool tool) {
		MCPTool model = new MCPTool();
		model.setName(tool.name());
		model.setDescription(tool.description());
		model.setInputSchema(serializeInputSchema(tool));
		return model;
	}

	/**
	 * Serializes the MCP tool input schema to a JSON string so it can be stored and
	 * later replayed as the inputSchema of an exported ToolCallback. Returns null on
	 * failure (the exporter falls back to an empty object schema).
	 */
	private String serializeInputSchema(McpSchema.Tool tool) {
		try {
			if (tool.inputSchema() == null) {
				return null;
			}
			return McpJsonDefaults.getMapper().writeValueAsString(tool.inputSchema());
		} catch (Throwable t) {
			LOGGER.warn("Could not serialize input schema for tool {}", tool.name(), t);
			return null;
		}
	}

	private MCPResource mapResource(McpSchema.Resource resource) {
		MCPResource model = new MCPResource();
		model.setName(resource.name());
		model.setUri(resource.uri());
		return model;
	}

	private MCPPrompt mapPrompt(McpSchema.Prompt prompt) {
		MCPPrompt model = new MCPPrompt();
		model.setName(prompt.name());
		return model;
	}

	/**
	 * Copies the security attributes of the owning config onto a freshly discovered
	 * feature, as required by the discovery contract.
	 */
	private void applySecurity(BaseMCPObject target, MCPClientConfig config) {
		target.setAccessibleGroups(config.getAccessibleGroups());
		target.setAccessibleUsers(config.getAccessibleUsers());
		target.setAccessibleToAll(config.getAccessibleToAll());
		target.setAclAliases(config.getAclAliases());
	}

	private List<McpSchema.Tool> listToolsSafe(McpSyncClient client, List<GUserMessage> messages) {
		try {
			McpSchema.ListToolsResult result = client.listTools();
			return result != null && result.tools() != null ? result.tools() : List.of();
		} catch (Throwable t) {
			LOGGER.warn("MCP server did not return tools", t);
			messages.add(GUserMessage.warnMessage("No tools", "The MCP server did not expose tools: " + t.getMessage()));
			return List.of();
		}
	}

	private List<McpSchema.Resource> listResourcesSafe(McpSyncClient client, List<GUserMessage> messages) {
		try {
			McpSchema.ListResourcesResult result = client.listResources();
			return result != null && result.resources() != null ? result.resources() : List.of();
		} catch (Throwable t) {
			LOGGER.warn("MCP server did not return resources", t);
			messages.add(GUserMessage.warnMessage("No resources",
					"The MCP server did not expose resources: " + t.getMessage()));
			return List.of();
		}
	}

	private List<McpSchema.Prompt> listPromptsSafe(McpSyncClient client, List<GUserMessage> messages) {
		try {
			McpSchema.ListPromptsResult result = client.listPrompts();
			return result != null && result.prompts() != null ? result.prompts() : List.of();
		} catch (Throwable t) {
			LOGGER.warn("MCP server did not return prompts", t);
			messages.add(GUserMessage.warnMessage("No prompts",
					"The MCP server did not expose prompts: " + t.getMessage()));
			return List.of();
		}
	}

	/**
	 * Validates the parts of the configuration required to open a connection,
	 * accumulating {@link GUserMessage}s. Any {@code error} prevents the operation.
	 */
	private void validateConnectionConfig(MCPClientConfig config, List<GUserMessage> messages) {
		if (config == null) {
			messages.add(GUserMessage.errorMessage("Config is null", "No MCP client configuration was provided"));
			return;
		}
		MCPTransportType transportType = config.getTransportType();
		if (transportType == null) {
			messages.add(GUserMessage.errorMessage("Missing transportType", "The MCP client must declare a transport"));
		} else if (transportType == MCPTransportType.STDIO) {
			if (isBlank(config.getStdioCommand())) {
				messages.add(GUserMessage.errorMessage("Missing stdioCommand",
						"The STDIO transport requires a stdioCommand to spawn the MCP server process"));
			}
		} else if (isBlank(config.getBaseUrl())) {
			messages.add(GUserMessage.errorMessage("Missing baseUrl",
					"The " + transportType + " transport requires a baseUrl"));
		}
		if (config.getAuthMode() == null) {
			messages.add(GUserMessage.errorMessage("Missing authMode", "The MCP client must declare an auth mode"));
		}
	}

	private String currentUsername() {
		UserInfos user = securityService.getCurrentUser();
		return user != null && user.getUsername() != null ? user.getUsername() : "system";
	}

	private static int size(List<?> list) {
		return list != null ? list.size() : 0;
	}

	private static boolean hasErrors(List<GUserMessage> messages) {
		return messages.stream().anyMatch(m -> m.getSeverity() == MsgServerity.error);
	}

	private static OperationStatus<MCPClientConfig> reject(List<GUserMessage> messages) {
		OperationStatus<MCPClientConfig> status = new OperationStatus<>();
		status.setResult(null);
		status.setMessages(messages);
		return status;
	}

	private static OperationStatus<Boolean> rejectBoolean(List<GUserMessage> messages) {
		OperationStatus<Boolean> status = new OperationStatus<>();
		status.setResult(Boolean.FALSE);
		status.setMessages(messages);
		return status;
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}

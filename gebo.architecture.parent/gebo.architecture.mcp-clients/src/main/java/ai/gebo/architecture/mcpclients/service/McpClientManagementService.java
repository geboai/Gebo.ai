package ai.gebo.architecture.mcpclients.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface McpClientManagementService {
	/*******
	 * This method does check a mcp server doing lookup on all features
	 * tools/resources/prompts ecc... returning a fully lookupped data structure,
	 * when the config does not exist in the mongodb (code is null) looking up
	 * copies main entity (config) security attributes to all existing lookupped
	 * tool/resource/prompt. In the "first discovery" all tools/resources/prompts
	 * are set with addedOnMCPServer = true, otherwise every tool/resources/prompts
	 * present in the passed config but not in the remote system is flagged as
	 * deletedOnMCPServer = true, all added is with addedOnMCPServer = true every
	 * object that still exists is flagged with addedOnMCPServer = false and
	 * deletedOnMCPServer=false
	 * 
	 * @param config
	 * @return
	 */
	public OperationStatus<MCPClientConfig> testAndDiscovery(@NotNull @Valid MCPClientConfig config);

	public OperationStatus<MCPClientConfig> insert(@NotNull @Valid MCPClientConfig config);

	public OperationStatus<MCPClientConfig> update(@NotNull @Valid MCPClientConfig config);

	public OperationStatus<Boolean> delete(@NotNull @Valid MCPClientConfig config);

	public OperationStatus<MCPClientConfig> findByCode(String code);

	/**
	 * Returns a page of the persisted MCP client configurations.
	 *
	 * @param pageable the pagination/sorting information (may be null for an unpaged
	 *                 result)
	 * @return the requested page of configurations
	 */
	public Page<MCPClientConfig> list(Pageable pageable);
}

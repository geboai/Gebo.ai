/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.security.services.IGSecurityService.AclOwnerInfo;

/**
 * Administrative service to manage the {@link GeboMCPServerConfig} nodes that
 * describe the erogated MCP servers. Every mutation keeps the live runtime in sync
 * so the {@code mcp/<url>} endpoints reflect the persisted state immediately.
 */
public interface IGMCPServerConfigManagerService {

	/**
	 * Creates a new MCP server configuration and publishes its endpoint.
	 *
	 * @param config the configuration to create
	 * @return the persisted configuration
	 * @throws GeboPersistenceException if persistence fails or the relative URL is not unique/valid
	 */
	GeboMCPServerConfig insert(GeboMCPServerConfig config) throws GeboPersistenceException;

	/**
	 * Updates an existing MCP server configuration and refreshes its endpoint.
	 *
	 * @param config the configuration to update
	 * @return the persisted configuration
	 * @throws GeboPersistenceException if persistence fails or the relative URL is not unique/valid
	 */
	GeboMCPServerConfig update(GeboMCPServerConfig config) throws GeboPersistenceException;

	/**
	 * Deletes the MCP server configuration with the given code and unpublishes its endpoint.
	 *
	 * @param code the configuration code
	 * @throws GeboPersistenceException if deletion fails
	 */
	void delete(String code) throws GeboPersistenceException;

	/**
	 * Finds the MCP server configuration with the given code.
	 *
	 * @param code the configuration code
	 * @return the configuration, or {@code null} if none exists
	 * @throws GeboPersistenceException if the lookup fails
	 */
	GeboMCPServerConfig findByCode(String code) throws GeboPersistenceException;

	/**
	 * Returns all MCP server configurations.
	 *
	 * @return the list of configurations
	 * @throws GeboPersistenceException if the lookup fails
	 */
	List<GeboMCPServerConfig> findAll() throws GeboPersistenceException;

	/**
	 * Returns a page of MCP server configurations.
	 *
	 * @param pageable the pagination information
	 * @return a page of configurations
	 * @throws GeboPersistenceException if the lookup fails
	 */
	Page<GeboMCPServerConfig> getPagedList(Pageable pageable) throws GeboPersistenceException;

	/**
	 * Sets the ACL grants (per user/group owner) on the configuration identified by
	 * the given code and refreshes its endpoint.
	 *
	 * @param code   the configuration code
	 * @param owners the owners and grant types to apply
	 * @return the persisted configuration
	 * @throws GeboPersistenceException if persistence fails
	 */
	GeboMCPServerConfig setAccessAcls(String code, List<AclOwnerInfo> owners) throws GeboPersistenceException;
}

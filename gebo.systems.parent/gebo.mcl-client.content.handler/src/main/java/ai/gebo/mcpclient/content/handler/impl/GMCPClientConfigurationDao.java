/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.mcpclient.content.handler.GMCPClientSystem;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;

/**
 * Singleton configuration DAO for the MCP client content management system.
 * <p>
 * MCP integrations have no per-instance system record: exactly one static
 * {@link GMCPClientSystem} represents the subsystem (mirroring the filesystem
 * handler). The connection details live per endpoint on the referenced
 * {@link ai.gebo.architecture.mcpclients.model.MCPClientConfig}, so there is no
 * Mongo repository and no system CRUD.
 */
@Service
public class GMCPClientConfigurationDao extends GAbstractRuntimeConfigurationDao<GMCPClientSystem>
		implements IGContentManagementSystemConfigurationDao<GMCPClientSystem> {

	/** Content management system type code for MCP client integrations. */
	public static final String MCP_CLIENT_TYPE = "MCP-CLIENT";

	/** Stable code of the single MCP client system instance. */
	public static final String MCP_CLIENT_SYSTEM_CODE = "MCP.CLIENT.HANDLER";

	/** The single, static MCP client system. */
	static final GMCPClientSystem singleSystem = new GMCPClientSystem();

	static {
		singleSystem.setContentManagementSystemType(MCP_CLIENT_TYPE);
		singleSystem.setCode(MCP_CLIENT_SYSTEM_CODE);
		singleSystem.setDescription("MCP servers");
		singleSystem.setReadonly(true);
	}

	/**
	 * @param dynamic optional dynamic configuration source (unused in practice, but
	 *                honoured for symmetry with the other singleton DAOs)
	 */
	public GMCPClientConfigurationDao(
			@Autowired(required = false) IGDynamicConfigurationSource<GMCPClientSystem> dynamic) {
		super(List.of(singleSystem), dynamic);
	}
}

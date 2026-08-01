/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.mcpclient.content.handler.GMCPClientSystem;
import ai.gebo.mcpclient.content.handler.IGMCPClientContentManagementHandler;
import ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint;
import ai.gebo.mcpclient.content.handler.impl.GMCPClientRemoteVirtualFilesystemConsumingServiceImpl;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.architecture.replicator.service.IEntityReplicationService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;

/**
 * Admin REST controller for managing MCP client project endpoints (virtual
 * drives).
 * <p>
 * There is no MCP <em>system</em> CRUD: the MCP system is a singleton, and each
 * endpoint references its own MCP client configuration (managed by the
 * {@code McpClientConfigController} of the {@code gebo.architecture.mcp-clients}
 * module) via {@code mcpClientConfigCode}. Resource selection is driven by
 * {@link MCPClientBrowsingController}.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/MCPClientSystemsController")
public class MCPClientSystemsController
		extends GAbstractSystemsArchitectureController<GMCPClientSystem, MCPClientProjectEndpoint> {

	/**
	 * Nested emitter component providing the MCP messaging module identity.
	 */
	@Component
	@Scope("singleton")
	public static class MCPClientControllerEmitter extends ControllerNestedEmitter {

		@Override
		public String getMessagingModuleId() {
			return GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_CLIENT_MODULE;
		}
	}

	private final IGMCPClientContentManagementHandler handler;

	public MCPClientSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			MCPClientControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGMCPClientContentManagementHandler handler, IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			IGGeboIngestionJobQueueService jobQueueService,
			IEntityReplicationService replicationService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, entityProcessingRunnableFactory, jobQueueService, replicationService);
		this.handler = handler;
	}

	@GetMapping(value = "getMCPClientSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getMCPClientSystemType() {
		return handler.getHandledSystemType();
	}

	@PostMapping(value = "findMCPClientEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<MCPClientProjectEndpoint> findMCPClientEndpointsByQbe(@RequestBody MCPClientProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	@GetMapping("findMCPClientEndpointsByProject")
	public List<MCPClientProjectEndpoint> findMCPClientEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		MCPClientProjectEndpoint config = new MCPClientProjectEndpoint();
		config.setParentProjectCode(parentProjectCode);
		return findEndpointByQbe(config);
	}

	@GetMapping("findMCPClientEndpointsByCode")
	public MCPClientProjectEndpoint findMCPClientEndpointsByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return persistentObjectManager.findById(MCPClientProjectEndpoint.class, code);
	}

	@PostMapping(value = "updateMCPClientEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public MCPClientProjectEndpoint updateMCPClientEndpoint(@RequestBody MCPClientProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	@PostMapping(value = "insertMCPClientEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public MCPClientProjectEndpoint insertMCPClientEndpoint(@RequestBody MCPClientProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	@PostMapping(value = "deleteMCPClientEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteMCPClientEndpoint(@RequestBody MCPClientProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	@PostMapping(value = "publishMCPClientEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> publishMCPClientEndpoint(@RequestBody MCPClientProjectEndpoint endpoint) {
		return publish(endpoint);
	}
}

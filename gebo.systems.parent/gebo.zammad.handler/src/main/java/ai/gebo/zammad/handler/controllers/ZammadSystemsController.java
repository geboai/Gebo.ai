/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.replicator.service.IEntityReplicationService;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.zammad.handler.GZammadProjectEndpoint;
import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.zammad.handler.IGZammadContentManagementSystemHandler;
import ai.gebo.zammad.handler.impl.ZammadApiClientFactory;
import ai.gebo.zammad.handler.impl.ZammadContentManagementHandlerImpl;
import ai.gebo.zammad.handler.impl.ZammadSystemsTestService;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Administration of the Zammad systems and data sources.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/ZammadSystemsController")
public class ZammadSystemsController
		extends GAbstractSystemsArchitectureController<GZammadSystem, GZammadProjectEndpoint> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZammadSystemsController.class);

	@Component
	@Scope("singleton")
	public static class ZammadControllerEmitter extends ControllerNestedEmitter {
		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.ZAMMAD_MODULE;
		}
	}

	private final IGZammadContentManagementSystemHandler handler;
	private final IGeboSecretsAccessService secretAccessService;
	private final ZammadSystemsTestService testService;

	public ZammadSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			ZammadControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGZammadContentManagementSystemHandler handler, IGeboSecretsAccessService secretAccessService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			ZammadSystemsTestService testService, IGGeboIngestionJobQueueService jobQueueService,
			IEntityReplicationService replicationService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService,
				entityProcessingRunnableFactory, jobQueueService, replicationService);
		this.handler = handler;
		this.secretAccessService = secretAccessService;
		this.testService = testService;
	}

	@GetMapping(value = "getZammadSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getZammadSystemType() {
		return handler.getHandledSystemType();
	}

	@GetMapping(value = "getZammadSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GZammadSystem> getZammadSystems() {
		return handler.getConfigurations();
	}

	@GetMapping(value = "findZammadSystemByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GZammadSystem findZammadSystemByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return handler.findConfiguration(code);
	}

	@PostMapping(value = "findZammadEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GZammadProjectEndpoint> findZammadEndpointsByQbe(@RequestBody GZammadProjectEndpoint qbe)
			throws GeboPersistenceException {
		return findEndpointByQbe(qbe);
	}

	@GetMapping(value = "findZammadEndpointsByProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GZammadProjectEndpoint> findZammadEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return handler.findDataSourcesByProject(parentProjectCode);
	}

	@GetMapping(value = "findZammadEndpointByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GZammadProjectEndpoint findZammadEndpointByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return handler.findDataSource(code);
	}

	@PostMapping(value = "testZammadSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GZammadSystem> testZammadSystem(@RequestBody GZammadSystem system) {
		return testService.testZammadSystem(system);
	}

	@PostMapping(value = "insertZammadSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GZammadSystem> insertZammadSystem(@RequestBody GZammadSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(ZammadContentManagementHandlerImpl.ZAMMAD);
		OperationStatus<GZammadSystem> status = testService.testZammadSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(insertSystem(system));
	}

	@PostMapping(value = "updateZammadSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GZammadSystem> updateZammadSystem(@RequestBody GZammadSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(ZammadContentManagementHandlerImpl.ZAMMAD);
		OperationStatus<GZammadSystem> status = testService.testZammadSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(updateSystem(system));
	}

	@PostMapping(value = "deleteZammadSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteZammadSystem(@RequestBody GZammadSystem system) throws GeboPersistenceException {
		deleteSystem(system);
	}

	@PostMapping(value = "insertZammadEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GZammadProjectEndpoint insertZammadEndpoint(@RequestBody GZammadProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	@PostMapping(value = "updateZammadEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GZammadProjectEndpoint updateZammadEndpoint(@RequestBody GZammadProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	@PostMapping(value = "deleteZammadEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteZammadEndpoint(@RequestBody GZammadProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	@PostMapping(value = "publishZammadEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> publishZammadEndpoint(@RequestBody GZammadProjectEndpoint endpoint) {
		return publish(endpoint);
	}

	/**
	 * The fast setup of a Zammad system: server, description and an access token
	 * of the account (Profile, Token Access on Zammad), with the ticket.agent and
	 * knowledge_base.reader permissions.
	 */
	public static class FastZammadSystemInsertRequest {
		@NotNull
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public String accessToken = null;
	}

	@PostMapping(value = "fastZammadConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GZammadSystem> fastZammadConfig(@Valid @RequestBody FastZammadSystemInsertRequest data) {
		String secretId = null;
		try {
			GeboTokenContent secret = new GeboTokenContent();
			secret.setToken(data.accessToken);
			secretId = secretAccessService.storeSecret(secret, "Zammad system " + data.description + " account",
					ZammadContentManagementHandlerImpl.ZAMMAD);
			GZammadSystem system = new GZammadSystem();
			system.setBaseUri(data.baseUri);
			system.setDescription(data.description);
			system.setSecretCode(secretId);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(ZammadContentManagementHandlerImpl.ZAMMAD);
			OperationStatus<GZammadSystem> status = testService.testZammadSystem(system);
			if (status.isHasErrorMessages()) {
				secretAccessService.deleteSecret(secretId);
				return status;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fast Zammad setup of {} with an access token", data.baseUri);
			}
			return OperationStatus.of(persistentObjectManager.insert(system));
		} catch (Throwable th) {
			LOGGER.error("Error inserting the Zammad system " + data.baseUri, th);
			if (secretId != null) {
				try {
					secretAccessService.deleteSecret(secretId);
				} catch (Throwable deleteError) {
					LOGGER.error("Failed to roll back the Zammad secret " + secretId, deleteError);
				}
			}
			OperationStatus<GZammadSystem> status = new OperationStatus<GZammadSystem>();
			status.getMessages().add(GUserMessage.errorMessage("Cannot set up the Zammad system", th));
			return status;
		}
	}

	@Override
	protected boolean isDeclaredInConfiguration(GZammadSystem system) {
		return system != null && handler.isDeclaredInConfiguration(system.getCode());
	}
}

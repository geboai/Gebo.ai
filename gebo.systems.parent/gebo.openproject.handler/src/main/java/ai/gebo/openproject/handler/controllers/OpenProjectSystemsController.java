/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.controllers;

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
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.openproject.handler.GOpenProjectSystem;
import ai.gebo.openproject.handler.IGOpenProjectContentManagementSystemHandler;
import ai.gebo.openproject.handler.impl.OpenProjectApiClientFactory;
import ai.gebo.openproject.handler.impl.OpenProjectContentManagementHandlerImpl;
import ai.gebo.openproject.handler.impl.OpenProjectSystemsTestService;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Administration of the OpenProject systems and data sources.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/OpenProjectSystemsController")
public class OpenProjectSystemsController
		extends GAbstractSystemsArchitectureController<GOpenProjectSystem, GOpenProjectProjectEndpoint> {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenProjectSystemsController.class);

	@Component
	@Scope("singleton")
	public static class OpenProjectControllerEmitter extends ControllerNestedEmitter {
		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.OPENPROJECT_MODULE;
		}
	}

	private final IGOpenProjectContentManagementSystemHandler handler;
	private final IGeboSecretsAccessService secretAccessService;
	private final OpenProjectSystemsTestService testService;

	public OpenProjectSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			OpenProjectControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGOpenProjectContentManagementSystemHandler handler, IGeboSecretsAccessService secretAccessService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			OpenProjectSystemsTestService testService, IGGeboIngestionJobQueueService jobQueueService,
			IEntityReplicationService replicationService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService,
				entityProcessingRunnableFactory, jobQueueService, replicationService);
		this.handler = handler;
		this.secretAccessService = secretAccessService;
		this.testService = testService;
	}

	@GetMapping(value = "getOpenProjectSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getOpenProjectSystemType() {
		return handler.getHandledSystemType();
	}

	@GetMapping(value = "getOpenProjectSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GOpenProjectSystem> getOpenProjectSystems() {
		return handler.getConfigurations();
	}

	@GetMapping(value = "findOpenProjectSystemByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOpenProjectSystem findOpenProjectSystemByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return handler.findConfiguration(code);
	}

	@PostMapping(value = "findOpenProjectEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GOpenProjectProjectEndpoint> findOpenProjectEndpointsByQbe(@RequestBody GOpenProjectProjectEndpoint qbe)
			throws GeboPersistenceException {
		return findEndpointByQbe(qbe);
	}

	@GetMapping(value = "findOpenProjectEndpointsByProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GOpenProjectProjectEndpoint> findOpenProjectEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return handler.findDataSourcesByProject(parentProjectCode);
	}

	@GetMapping(value = "findOpenProjectEndpointByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOpenProjectProjectEndpoint findOpenProjectEndpointByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return handler.findDataSource(code);
	}

	@PostMapping(value = "testOpenProjectSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenProjectSystem> testOpenProjectSystem(@RequestBody GOpenProjectSystem system) {
		return testService.testOpenProjectSystem(system);
	}

	@PostMapping(value = "insertOpenProjectSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenProjectSystem> insertOpenProjectSystem(@RequestBody GOpenProjectSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(OpenProjectContentManagementHandlerImpl.OPENPROJECT);
		OperationStatus<GOpenProjectSystem> status = testService.testOpenProjectSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(insertSystem(system));
	}

	@PostMapping(value = "updateOpenProjectSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenProjectSystem> updateOpenProjectSystem(@RequestBody GOpenProjectSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(OpenProjectContentManagementHandlerImpl.OPENPROJECT);
		OperationStatus<GOpenProjectSystem> status = testService.testOpenProjectSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(updateSystem(system));
	}

	@PostMapping(value = "deleteOpenProjectSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteOpenProjectSystem(@RequestBody GOpenProjectSystem system) throws GeboPersistenceException {
		deleteSystem(system);
	}

	@PostMapping(value = "insertOpenProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GOpenProjectProjectEndpoint insertOpenProjectEndpoint(@RequestBody GOpenProjectProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	@PostMapping(value = "updateOpenProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GOpenProjectProjectEndpoint updateOpenProjectEndpoint(@RequestBody GOpenProjectProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	@PostMapping(value = "deleteOpenProjectEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteOpenProjectEndpoint(@RequestBody GOpenProjectProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	@PostMapping(value = "publishOpenProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> publishOpenProjectEndpoint(@RequestBody GOpenProjectProjectEndpoint endpoint) {
		return publish(endpoint);
	}

	/**
	 * The fast setup of an OpenProject system: server, description and the API key
	 * of the account (My account, Access tokens, API on OpenProject).
	 */
	public static class FastOpenProjectSystemInsertRequest {
		@NotNull
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public String apiKey = null;
	}

	@PostMapping(value = "fastOpenProjectConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenProjectSystem> fastOpenProjectConfig(@Valid @RequestBody FastOpenProjectSystemInsertRequest data) {
		String secretId = null;
		try {
			GeboTokenContent secret = new GeboTokenContent();
			secret.setUser(OpenProjectApiClientFactory.API_KEY_USER);
			secret.setToken(data.apiKey);
			secretId = secretAccessService.storeSecret(secret, "OpenProject system " + data.description + " account",
					OpenProjectContentManagementHandlerImpl.OPENPROJECT);
			GOpenProjectSystem system = new GOpenProjectSystem();
			system.setBaseUri(data.baseUri);
			system.setDescription(data.description);
			system.setSecretCode(secretId);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(OpenProjectContentManagementHandlerImpl.OPENPROJECT);
			OperationStatus<GOpenProjectSystem> status = testService.testOpenProjectSystem(system);
			if (status.isHasErrorMessages()) {
				secretAccessService.deleteSecret(secretId);
				return status;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fast OpenProject setup of {} with an API key", data.baseUri);
			}
			return OperationStatus.of(persistentObjectManager.insert(system));
		} catch (Throwable th) {
			LOGGER.error("Error inserting the OpenProject system " + data.baseUri, th);
			if (secretId != null) {
				try {
					secretAccessService.deleteSecret(secretId);
				} catch (Throwable deleteError) {
					LOGGER.error("Failed to roll back the OpenProject secret " + secretId, deleteError);
				}
			}
			OperationStatus<GOpenProjectSystem> status = new OperationStatus<GOpenProjectSystem>();
			status.getMessages().add(GUserMessage.errorMessage("Cannot set up the OpenProject system", th));
			return status;
		}
	}

	@Override
	protected boolean isDeclaredInConfiguration(GOpenProjectSystem system) {
		return system != null && handler.isDeclaredInConfiguration(system.getCode());
	}
}

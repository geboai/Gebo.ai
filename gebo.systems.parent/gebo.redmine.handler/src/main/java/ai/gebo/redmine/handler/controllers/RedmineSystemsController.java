/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.controllers;

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
import ai.gebo.redmine.handler.GRedmineProjectEndpoint;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.IGRedmineContentManagementSystemHandler;
import ai.gebo.redmine.handler.RedmineAuthType;
import ai.gebo.redmine.handler.impl.RedmineContentManagementHandlerImpl;
import ai.gebo.redmine.handler.impl.RedmineSystemsTestService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Administration of the Redmine systems and data sources.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/RedmineSystemsController")
public class RedmineSystemsController
		extends GAbstractSystemsArchitectureController<GRedmineSystem, GRedmineProjectEndpoint> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedmineSystemsController.class);

	@Component
	@Scope("singleton")
	public static class RedmineControllerEmitter extends ControllerNestedEmitter {
		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.REDMINE_MODULE;
		}
	}

	private final IGRedmineContentManagementSystemHandler handler;
	private final IGeboSecretsAccessService secretAccessService;
	private final RedmineSystemsTestService testService;

	public RedmineSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			RedmineControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGRedmineContentManagementSystemHandler handler, IGeboSecretsAccessService secretAccessService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			RedmineSystemsTestService testService, IGGeboIngestionJobQueueService jobQueueService,
			IEntityReplicationService replicationService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService,
				entityProcessingRunnableFactory, jobQueueService, replicationService);
		this.handler = handler;
		this.secretAccessService = secretAccessService;
		this.testService = testService;
	}

	@GetMapping(value = "getRedmineSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getRedmineSystemType() {
		return handler.getHandledSystemType();
	}

	@GetMapping(value = "getRedmineSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GRedmineSystem> getRedmineSystems() {
		return handler.getConfigurations();
	}

	@GetMapping(value = "findRedmineSystemByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GRedmineSystem findRedmineSystemByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return handler.findConfiguration(code);
	}

	@PostMapping(value = "findRedmineEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GRedmineProjectEndpoint> findRedmineEndpointsByQbe(@RequestBody GRedmineProjectEndpoint qbe)
			throws GeboPersistenceException {
		return findEndpointByQbe(qbe);
	}

	@GetMapping(value = "findRedmineEndpointsByProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GRedmineProjectEndpoint> findRedmineEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return handler.findDataSourcesByProject(parentProjectCode);
	}

	@GetMapping(value = "findRedmineEndpointByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GRedmineProjectEndpoint findRedmineEndpointByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return handler.findDataSource(code);
	}

	@PostMapping(value = "testRedmineSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GRedmineSystem> testRedmineSystem(@RequestBody GRedmineSystem system) {
		return testService.testRedmineSystem(system);
	}

	@PostMapping(value = "insertRedmineSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GRedmineSystem> insertRedmineSystem(@RequestBody GRedmineSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(RedmineContentManagementHandlerImpl.REDMINE);
		OperationStatus<GRedmineSystem> status = testService.testRedmineSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(insertSystem(system));
	}

	@PostMapping(value = "updateRedmineSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GRedmineSystem> updateRedmineSystem(@RequestBody GRedmineSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(RedmineContentManagementHandlerImpl.REDMINE);
		OperationStatus<GRedmineSystem> status = testService.testRedmineSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(updateSystem(system));
	}

	@PostMapping(value = "deleteRedmineSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteRedmineSystem(@RequestBody GRedmineSystem system) throws GeboPersistenceException {
		deleteSystem(system);
	}

	@PostMapping(value = "insertRedmineEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GRedmineProjectEndpoint insertRedmineEndpoint(@RequestBody GRedmineProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	@PostMapping(value = "updateRedmineEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GRedmineProjectEndpoint updateRedmineEndpoint(@RequestBody GRedmineProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	@PostMapping(value = "deleteRedmineEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteRedmineEndpoint(@RequestBody GRedmineProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	@PostMapping(value = "publishRedmineEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> publishRedmineEndpoint(@RequestBody GRedmineProjectEndpoint endpoint) {
		return publish(endpoint);
	}

	/**
	 * The fast setup of a Redmine system: server, description and credentials.
	 */
	public static class FastRedmineSystemInsertRequest {
		@NotNull
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public RedmineAuthType authType = null;
		public String apiKey = null;
		public String username = null;
		public String password = null;
	}

	@PostMapping(value = "fastRedmineConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GRedmineSystem> fastRedmineConfig(@Valid @RequestBody FastRedmineSystemInsertRequest data) {
		String secretId = null;
		try {
			AbstractGeboSecretContent secret;
			switch (data.authType) {
			case API_KEY: {
				GeboTokenContent token = new GeboTokenContent();
				token.setUser(data.username);
				token.setToken(data.apiKey);
				secret = token;
			}
				break;
			case USERNAME_PASSWORD: {
				GeboUsernamePasswordContent credentials = new GeboUsernamePasswordContent();
				credentials.setUsername(data.username);
				credentials.setPassword(data.password);
				secret = credentials;
			}
				break;
			default:
				throw new IllegalArgumentException("Unknown Redmine authentication " + data.authType);
			}
			secretId = secretAccessService.storeSecret(secret, "Redmine system " + data.description + " account",
					RedmineContentManagementHandlerImpl.REDMINE);
			GRedmineSystem system = new GRedmineSystem();
			system.setBaseUri(data.baseUri);
			system.setDescription(data.description);
			system.setSecretCode(secretId);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(RedmineContentManagementHandlerImpl.REDMINE);
			OperationStatus<GRedmineSystem> status = testService.testRedmineSystem(system);
			if (status.isHasErrorMessages()) {
				secretAccessService.deleteSecret(secretId);
				return status;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Fast Redmine setup of {} with {} credentials", data.baseUri, data.authType);
			}
			return OperationStatus.of(persistentObjectManager.insert(system));
		} catch (Throwable th) {
			LOGGER.error("Error inserting the Redmine system " + data.baseUri, th);
			if (secretId != null) {
				try {
					secretAccessService.deleteSecret(secretId);
				} catch (Throwable deleteError) {
					LOGGER.error("Failed to roll back the Redmine secret " + secretId, deleteError);
				}
			}
			OperationStatus<GRedmineSystem> status = new OperationStatus<GRedmineSystem>();
			status.getMessages().add(GUserMessage.errorMessage("Cannot set up the Redmine system", th));
			return status;
		}
	}

	@Override
	protected boolean isDeclaredInConfiguration(GRedmineSystem system) {
		return system != null && handler.isDeclaredInConfiguration(system.getCode());
	}
}

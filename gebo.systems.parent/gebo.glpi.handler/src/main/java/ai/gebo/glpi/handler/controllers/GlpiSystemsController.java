/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.controllers;

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
import ai.gebo.glpi.handler.GGlpiProjectEndpoint;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.glpi.handler.IGGlpiContentManagementSystemHandler;
import ai.gebo.glpi.handler.impl.GlpiContentManagementHandlerImpl;
import ai.gebo.glpi.handler.impl.GlpiSystemsTestService;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/GlpiSystemsController")
public class GlpiSystemsController extends GAbstractSystemsArchitectureController<GGlpiSystem, GGlpiProjectEndpoint> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlpiSystemsController.class);
	/**
	 * The provider name of the OAuth client secret: GLPI is a generic OAuth2
	 * authorization server
	 */
	public static final String GLPI_OAUTH2_PROVIDER_NAME = "oauth2_generic";

	@Component
	@Scope("singleton")
	public static class GlpiControllerEmitter extends ControllerNestedEmitter {

		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.GLPI_MODULE;
		}
	}

	private final IGGlpiContentManagementSystemHandler handler;
	private final IGeboSecretsAccessService secretAccessService;
	private final GlpiSystemsTestService glpiTestService;

	public GlpiSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			GlpiControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGGlpiContentManagementSystemHandler handler, IGeboSecretsAccessService secretAccessService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			GlpiSystemsTestService glpiTestService, IGGeboIngestionJobQueueService jobQueueService,
			IEntityReplicationService replicationService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService,
				entityProcessingRunnableFactory, jobQueueService, replicationService);
		this.handler = handler;
		this.secretAccessService = secretAccessService;
		this.glpiTestService = glpiTestService;
	}

	@GetMapping(value = "getGlpiSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getGlpiSystemType() {
		return handler.getHandledSystemType();
	}

	@GetMapping(value = "getGlpiSystems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GGlpiSystem> getGlpiSystems() {
		return handler.getConfigurations();
	}

	@GetMapping(value = "findGlpiSystemByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GGlpiSystem findGlpiSystemByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return handler.findConfiguration(code);
	}

	@PostMapping(value = "findGlpiEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GGlpiProjectEndpoint> findGlpiEndpointsByQbe(@RequestBody GGlpiProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	@GetMapping(value = "findGlpiEndpointsByProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GGlpiProjectEndpoint> findGlpiEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return handler.findDataSourcesByProject(parentProjectCode);
	}

	@GetMapping(value = "findGlpiEndpointsByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GGlpiProjectEndpoint findGlpiEndpointsByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return handler.findDataSource(code);
	}

	@PostMapping(value = "insertGlpiSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGlpiSystem> insertGlpiSystem(@RequestBody GGlpiSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);
		OperationStatus<GGlpiSystem> status = glpiTestService.testGlpiSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(insertSystem(system));
	}

	@PostMapping(value = "updateGlpiSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGlpiSystem> updateGlpiSystem(@RequestBody GGlpiSystem system)
			throws GeboPersistenceException {
		system.setContentManagementSystemType(GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);
		OperationStatus<GGlpiSystem> status = glpiTestService.testGlpiSystem(system);
		if (status.isHasErrorMessages()) {
			return status;
		}
		return OperationStatus.of(updateSystem(system));
	}

	@PostMapping(value = "testGlpiSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGlpiSystem> testGlpiSystem(@RequestBody GGlpiSystem system) {
		return glpiTestService.testGlpiSystem(system);
	}

	@PostMapping(value = "deleteGlpiSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGlpiSystem(@RequestBody GGlpiSystem system) throws GeboPersistenceException {
		deleteSystem(system);
	}

	@PostMapping(value = "insertGlpiEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGlpiProjectEndpoint insertGlpiEndpoint(@RequestBody GGlpiProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	@PostMapping(value = "updateGlpiEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGlpiProjectEndpoint updateGlpiEndpoint(@RequestBody GGlpiProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	@PostMapping(value = "deleteGlpiEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGlpiEndpoint(@RequestBody GGlpiProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	@PostMapping(value = "publishGlpiEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> publishGlpiEndpoint(@RequestBody GGlpiProjectEndpoint endpoint) {
		return publish(endpoint);
	}

	/** The data of the fast setup form: the GLPI url, the OAuth client and the GLPI user */
	public static class FastGlpiSystemInsertRequest {
		@NotNull
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public String clientId = null;
		@NotNull
		public String clientSecret = null;
		@NotNull
		public String username = null;
		@NotNull
		public String password = null;
	}

	@PostMapping(value = "fastGlpiConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGlpiSystem> fastGlpiConfig(@Valid @RequestBody FastGlpiSystemInsertRequest data) {
		String clientSecretId = null;
		String userSecretId = null;
		try {
			GeboOauth2SecretContent client = new GeboOauth2SecretContent();
			client.setProviderName(GLPI_OAUTH2_PROVIDER_NAME);
			client.setClientId(data.clientId);
			client.setSecret(data.clientSecret);
			clientSecretId = secretAccessService.storeSecret(client, "GLPI OAuth client of " + data.description,
					GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);
			GeboUsernamePasswordContent user = new GeboUsernamePasswordContent();
			user.setUsername(data.username);
			user.setPassword(data.password);
			userSecretId = secretAccessService.storeSecret(user, "GLPI user of " + data.description,
					GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);

			GGlpiSystem system = new GGlpiSystem();
			system.setBaseUri(data.baseUri);
			system.setDescription(data.description);
			system.setOauthClientSecretCode(clientSecretId);
			system.setSecretCode(userSecretId);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);

			OperationStatus<GGlpiSystem> status = glpiTestService.testGlpiSystem(system);
			if (status.isHasErrorMessages()) {
				rollback(clientSecretId, userSecretId);
				return status;
			}
			system = persistentObjectManager.insert(system);
			OperationStatus<GGlpiSystem> out = OperationStatus.of(system);
			out.getMessages().addAll(status.getMessages());
			return out;
		} catch (Throwable th) {
			LOGGER.error("Error inserting the GLPI system configuration", th);
			rollback(clientSecretId, userSecretId);
			OperationStatus<GGlpiSystem> os = new OperationStatus<GGlpiSystem>();
			os.getMessages().add(GUserMessage.errorMessage("Cannot access GLPI", th));
			return os;
		}
	}

	private void rollback(String... secretIds) {
		for (String secretId : secretIds) {
			if (secretId != null) {
				try {
					secretAccessService.deleteSecret(secretId);
				} catch (Throwable deleteError) {
					LOGGER.error("Failed to roll back the GLPI secret " + secretId, deleteError);
				}
			}
		}
	}

	@Override
	protected boolean isDeclaredInConfiguration(GGlpiSystem system) {
		return system != null && handler.isDeclaredInConfiguration(system.getCode());
	}
}

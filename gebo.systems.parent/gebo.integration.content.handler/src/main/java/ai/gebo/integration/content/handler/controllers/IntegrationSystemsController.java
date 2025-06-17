package ai.gebo.integration.content.handler.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
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
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.integration.content.handler.GIntegrationContentSystem;
import ai.gebo.integration.content.handler.GIntegrationProjectEndpoint;
import ai.gebo.integration.content.handler.repositories.IntegrationProjectEndpointRepository;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/IntegrationSystemsController")
public class IntegrationSystemsController
		extends GAbstractSystemsArchitectureController<GIntegrationContentSystem, GIntegrationProjectEndpoint> {
	@Service
	public static class IntegrationSystemsNestedEmitter extends ControllerNestedEmitter {

		/**
		 * Returns the unique identifier for the 3rd party Integration messaging module. This ID
		 * is used by the messaging system to route messages to the appropriate handler.
		 * 
		 * @return String representing the 3rd party Integration module identifier from the
		 *         standard modules constraints
		 */
		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.INTEGRATION_MODULE;
		}

	}

	final IntegrationProjectEndpointRepository endpointRepository;

	public IntegrationSystemsController(IGPersistentObjectManager persistentObjectManager,
			IGMessageBroker messageBroker, IntegrationSystemsNestedEmitter controllerEmitter,
			IGSecurityService securityService, IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			IntegrationProjectEndpointRepository endpointRepository) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.endpointRepository = endpointRepository;
	}

	@GetMapping("findIntegrationEndpointsByProject")
	public List<GIntegrationProjectEndpoint> findIntegrationEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {

		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

	/**
	 * Updates an existing 3rd party Integration project endpoint
	 * 
	 * @param endpoint The project endpoint to update
	 * @return The updated 3rd party Integration project endpoint
	 * @throws GeboPersistenceException If there's an error during the update
	 */
	@PostMapping(value = "updateIntegrationProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GIntegrationProjectEndpoint updateIntegrationProjectEndpoint(
			@RequestBody GIntegrationProjectEndpoint endpoint) throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	/**
	 * Inserts a new 3rd party Integration project endpoint
	 * 
	 * @param endpoint The project endpoint to insert
	 * @return The inserted 3rd party Integration project endpoint
	 * @throws GeboPersistenceException If there's an error during insertion
	 */
	@PostMapping(value = "insertIntegrationProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GIntegrationProjectEndpoint insertIntegrationProjectEndpoint(
			@RequestBody GIntegrationProjectEndpoint endpoint) throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	/**
	 * Deletes a 3rd party Integration project endpoint
	 * 
	 * @param endpoint The project endpoint to delete
	 * @throws GeboPersistenceException If there's an error during deletion
	 */
	@PostMapping(value = "deleteIntegrationProjectEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteIntegrationProjectEndpoint(@RequestBody GIntegrationProjectEndpoint endpoint)
			throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

}

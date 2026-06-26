package ai.gebo.architecture.agents.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.agents.model.AgentServiceDescriptor;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCrudService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkToNetworkAgentAdapterService;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Admin REST controller for {@link GAgentsNetwork} configurations.
 * <p>
 * The mutating endpoints delegate to {@link IGAgentsNetworkCrudService} so every
 * insert/update/delete is validated and reports its outcome as an
 * {@link OperationStatus} carrying the resulting value and/or the diagnostic user
 * messages. The remaining endpoints back an interactive, visual network composer:
 * they let the UI look up the available agent configurations and agent services
 * and discover how those services can be catenated by matching their input/output
 * types &mdash; including {@link IGAgentsNetworkToNetworkAgentAdapterService
 * network-of-agents adapters}, which can be dropped in place of a plain network
 * agent.
 * <p>
 * The runtime DAOs are resolved lazily through the {@link IGRuntimeBinder} to
 * avoid a startup-time circular dependency with the late-wired agent service and
 * configuration sources.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAgentsNetworkAdminController")
@AllArgsConstructor
public class GeboAgentsNetworkAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboAgentsNetworkAdminController.class);

	private final IGAgentsNetworkCrudService networkCrudService;
	private final IGRuntimeBinder runtimeBinder;

	private <T> T get(Class<T> t) {
		return runtimeBinder.getImplementationOf(t);
	}

	// ---------------------------------------------------------------------
	// Network listing / retrieval
	// ---------------------------------------------------------------------

	@GetMapping(value = "getAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAgentsNetwork() {
		IAgentsNetworkDao agentsNetworkDao = get(IAgentsNetworkDao.class);
		List<GAgentsNetwork> configs = agentsNetworkDao.getConfigurations();
		return configs.stream().map(x -> new GBaseObject(x)).toList();
	}

	@GetMapping(value = "getAgentsNetworkByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAgentsNetwork getAgentsNetworkByCode(@RequestParam("code") String code) {
		return get(IAgentsNetworkDao.class).findByCode(code);
	}

	// ---------------------------------------------------------------------
	// Validated CRUD (delegated to the CRUD service)
	// ---------------------------------------------------------------------

	@PostMapping(value = "validateAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> validateAgentsNetwork(@RequestBody @NotNull GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST validateAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		return networkCrudService.validate(network);
	}

	@PostMapping(value = "updateAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> updateAgentsNetwork(@RequestBody @NotNull @Valid GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST updateAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		return networkCrudService.update(network);
	}

	@PostMapping(value = "insertAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> insertAgentsNetwork(@RequestBody @NotNull @Valid GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST insertAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		return networkCrudService.insert(network);
	}

	@PostMapping(value = "deleteAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> deleteAgentsNetwork(@RequestBody @NotNull GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST deleteAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		return networkCrudService.delete(network);
	}

	// ---------------------------------------------------------------------
	// Visual composer: agent configs / services lookup
	// ---------------------------------------------------------------------

	/**
	 * Lists every agent configuration, as lightweight code/description objects, so
	 * the composer can bind a network participant to a concrete configuration.
	 */
	@GetMapping(value = "getAgentConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAgentConfigs() {
		return get(IAgentConfigDao.class).getConfigurations().stream().map(x -> new GBaseObject(x)).toList();
	}

	/**
	 * Lists the agent configurations bound to the given agent service id.
	 */
	@GetMapping(value = "getAgentConfigsByServiceId", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GAgentConfig> getAgentConfigsByServiceId(@RequestParam("serviceId") String serviceId) {
		return get(IAgentConfigDao.class).findByAgentServiceId(serviceId);
	}

	/**
	 * Lists all network agent services available for composition, with their
	 * input/output types and composition flags (routing, network adapter).
	 */
	@GetMapping(value = "getAgentServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentServiceDescriptor> getAgentServices() {
		return get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService).map(AgentServiceDescriptor::of).toList();
	}

	/**
	 * Lists the {@link IGAgentsNetworkToNetworkAgentAdapterService} services, which
	 * wrap a whole network of agents and can be catenated in place of a plain
	 * network agent.
	 */
	@GetMapping(value = "getNetworkAdapterServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentServiceDescriptor> getNetworkAdapterServices() {
		return get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGAgentsNetworkToNetworkAgentAdapterService).map(AgentServiceDescriptor::of)
				.toList();
	}

	// ---------------------------------------------------------------------
	// Visual composer: catenation by matching input/output types
	// ---------------------------------------------------------------------

	/**
	 * Given a source agent service id, returns the services that can be placed
	 * <em>after</em> it: every network agent service (including network adapters)
	 * whose input type can consume the source service's output type.
	 */
	@GetMapping(value = "getCompatibleNextServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentServiceDescriptor> getCompatibleNextServices(@RequestParam("serviceId") String serviceId) {
		IGNetworkAgentService<?, ?> source = resolveNetworkService(serviceId);
		if (source == null || source.getOutputType() == null) {
			return List.of();
		}
		final Class<?> sourceOutput = source.getOutputType();
		return get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService).map(s -> (IGNetworkAgentService<?, ?>) s)
				.filter(candidate -> candidate.getInputType() != null
						&& candidate.getInputType().isAssignableFrom(sourceOutput))
				.map(AgentServiceDescriptor::of).toList();
	}

	/**
	 * Given a target agent service id, returns the services that can be placed
	 * <em>before</em> it: every network agent service (including network adapters)
	 * whose output type can be consumed by the target service's input type.
	 */
	@GetMapping(value = "getCompatiblePreviousServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentServiceDescriptor> getCompatiblePreviousServices(@RequestParam("serviceId") String serviceId) {
		IGNetworkAgentService<?, ?> target = resolveNetworkService(serviceId);
		if (target == null || target.getInputType() == null) {
			return List.of();
		}
		final Class<?> targetInput = target.getInputType();
		return get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService).map(s -> (IGNetworkAgentService<?, ?>) s)
				.filter(candidate -> candidate.getOutputType() != null
						&& targetInput.isAssignableFrom(candidate.getOutputType()))
				.map(AgentServiceDescriptor::of).toList();
	}

	private IGNetworkAgentService<?, ?> resolveNetworkService(String serviceId) {
		IGGenericAgentService service = get(IGAgentServiceRuntimeDao.class).findByCode(serviceId);
		return service instanceof IGNetworkAgentService<?, ?> networkService ? networkService : null;
	}
}

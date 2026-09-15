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
		T implementation = runtimeBinder.getImplementationOf(t);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Lazily resolved " + t.getName() + " to "
					+ (implementation != null ? implementation.getClass().getName() : null));
		}
		return implementation;
	}

	// ---------------------------------------------------------------------
	// Network listing / retrieval
	// ---------------------------------------------------------------------

	@GetMapping(value = "getAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getAgentsNetwork() {
		IAgentsNetworkDao agentsNetworkDao = get(IAgentsNetworkDao.class);
		List<GAgentsNetwork> configs = agentsNetworkDao.getConfigurations();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentsNetwork returned " + (configs != null ? configs.size() : 0) + " network(s)");
		}
		return configs.stream().map(x -> new GBaseObject(x)).toList();
	}

	@GetMapping(value = "getAgentsNetworkByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAgentsNetwork getAgentsNetworkByCode(@RequestParam("code") String code) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentsNetworkByCode code:" + code);
		}
		GAgentsNetwork network = get(IAgentsNetworkDao.class).findByCode(code);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENTS_NETWORK code=" + code + ">");
			LOGGER.trace(String.valueOf(network));
			LOGGER.trace("</AGENTS_NETWORK>");
		}
		return network;
	}

	// ---------------------------------------------------------------------
	// Validated CRUD (delegated to the CRUD service)
	// ---------------------------------------------------------------------

	@PostMapping(value = "validateAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> validateAgentsNetwork(@RequestBody @NotNull GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST validateAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENTS_NETWORK_VALIDATE>");
			LOGGER.trace(String.valueOf(network));
			LOGGER.trace("</AGENTS_NETWORK_VALIDATE>");
		}
		OperationStatus<GAgentsNetwork> status = networkCrudService.validate(network);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST validateAgentsNetwork code:" + (network != null ? network.getCode() : null) + " produced a result:"
					+ (status != null && status.getResult() != null) + " message(s):"
					+ (status != null && status.getMessages() != null ? status.getMessages().size() : 0));
		}
		return status;
	}

	@PostMapping(value = "updateAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> updateAgentsNetwork(@RequestBody @NotNull @Valid GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST updateAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENTS_NETWORK_UPDATE>");
			LOGGER.trace(String.valueOf(network));
			LOGGER.trace("</AGENTS_NETWORK_UPDATE>");
		}
		OperationStatus<GAgentsNetwork> status = networkCrudService.update(network);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST updateAgentsNetwork code:" + (network != null ? network.getCode() : null) + " produced a result:"
					+ (status != null && status.getResult() != null) + " message(s):"
					+ (status != null && status.getMessages() != null ? status.getMessages().size() : 0));
		}
		return status;
	}

	@PostMapping(value = "insertAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> insertAgentsNetwork(@RequestBody @NotNull @Valid GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST insertAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENTS_NETWORK_INSERT>");
			LOGGER.trace(String.valueOf(network));
			LOGGER.trace("</AGENTS_NETWORK_INSERT>");
		}
		OperationStatus<GAgentsNetwork> status = networkCrudService.insert(network);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST insertAgentsNetwork code:" + (network != null ? network.getCode() : null) + " produced a result:"
					+ (status != null && status.getResult() != null) + " message(s):"
					+ (status != null && status.getMessages() != null ? status.getMessages().size() : 0));
		}
		return status;
	}

	@PostMapping(value = "deleteAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> deleteAgentsNetwork(@RequestBody @NotNull GAgentsNetwork network) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST deleteAgentsNetwork code:" + (network != null ? network.getCode() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENTS_NETWORK_DELETE>");
			LOGGER.trace(String.valueOf(network));
			LOGGER.trace("</AGENTS_NETWORK_DELETE>");
		}
		OperationStatus<GAgentsNetwork> status = networkCrudService.delete(network);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST deleteAgentsNetwork code:" + (network != null ? network.getCode() : null) + " produced a result:"
					+ (status != null && status.getResult() != null) + " message(s):"
					+ (status != null && status.getMessages() != null ? status.getMessages().size() : 0));
		}
		return status;
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
		List<GBaseObject> configs = get(IAgentConfigDao.class).getConfigurations().stream()
				.map(x -> new GBaseObject(x)).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentConfigs returned " + configs.size() + " agent configuration(s)");
		}
		return configs;
	}

	/**
	 * Lists the agent configurations bound to the given agent service id.
	 */
	@GetMapping(value = "getAgentConfigsByServiceId", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GAgentConfig> getAgentConfigsByServiceId(@RequestParam("serviceId") String serviceId) {
		List<GAgentConfig> configs = get(IAgentConfigDao.class).findByAgentServiceId(serviceId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentConfigsByServiceId serviceId:" + serviceId + " returned "
					+ (configs != null ? configs.size() : 0) + " agent configuration(s)");
		}
		return configs;
	}

	/**
	 * Lists all network agent services available for composition, with their
	 * input/output types and composition flags (routing, network adapter).
	 */
	@GetMapping(value = "getAgentServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentServiceDescriptor> getAgentServices() {
		List<AgentServiceDescriptor> descriptors = get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService).map(AgentServiceDescriptor::of).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentServices returned " + descriptors.size() + " network agent service(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_SERVICE_DESCRIPTORS>");
			LOGGER.trace(String.valueOf(descriptors));
			LOGGER.trace("</AGENT_SERVICE_DESCRIPTORS>");
		}
		return descriptors;
	}

	/**
	 * Lists the {@link IGAgentsNetworkToNetworkAgentAdapterService} services, which
	 * wrap a whole network of agents and can be catenated in place of a plain
	 * network agent.
	 */
	@GetMapping(value = "getNetworkAdapterServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentServiceDescriptor> getNetworkAdapterServices() {
		List<AgentServiceDescriptor> descriptors = get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGAgentsNetworkToNetworkAgentAdapterService).map(AgentServiceDescriptor::of)
				.toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getNetworkAdapterServices returned " + descriptors.size() + " adapter service(s)");
		}
		return descriptors;
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
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("REST getCompatibleNextServices serviceId:" + serviceId
						+ " cannot be catenated, resolved:" + (source != null) + " outputTypeKnown:"
						+ (source != null && source.getOutputType() != null));
			}
			return List.of();
		}
		final Class<?> sourceOutput = source.getOutputType();
		List<AgentServiceDescriptor> compatible = get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService).map(s -> (IGNetworkAgentService<?, ?>) s)
				.filter(candidate -> candidate.getInputType() != null
						&& candidate.getInputType().isAssignableFrom(sourceOutput))
				.map(AgentServiceDescriptor::of).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getCompatibleNextServices serviceId:" + serviceId + " outputType:"
					+ sourceOutput.getName() + " matched " + compatible.size() + " service(s)");
		}
		return compatible;
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
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("REST getCompatiblePreviousServices serviceId:" + serviceId
						+ " cannot be catenated, resolved:" + (target != null) + " inputTypeKnown:"
						+ (target != null && target.getInputType() != null));
			}
			return List.of();
		}
		final Class<?> targetInput = target.getInputType();
		List<AgentServiceDescriptor> compatible = get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService).map(s -> (IGNetworkAgentService<?, ?>) s)
				.filter(candidate -> candidate.getOutputType() != null
						&& targetInput.isAssignableFrom(candidate.getOutputType()))
				.map(AgentServiceDescriptor::of).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getCompatiblePreviousServices serviceId:" + serviceId + " inputType:"
					+ targetInput.getName() + " matched " + compatible.size() + " service(s)");
		}
		return compatible;
	}

	private IGNetworkAgentService<?, ?> resolveNetworkService(String serviceId) {
		IGGenericAgentService service = get(IGAgentServiceRuntimeDao.class).findByCode(serviceId);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("resolveNetworkService(" + serviceId + ") found:" + (service != null) + " isNetworkAgent:"
					+ (service instanceof IGNetworkAgentService));
		}
		return service instanceof IGNetworkAgentService<?, ?> networkService ? networkService : null;
	}
}

package ai.gebo.architecture.agents.controllers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.AgentServiceDescriptor;
import ai.gebo.architecture.agents.model.AgentTypeCompatibility;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.NetworkServiceFactoryDescriptor;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsCopyService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkCrudService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGAgentsNetworkToNetworkAgentAdapterService;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.services.ConfigurationAuditSupport;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
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
	private final IGAgentsCopyService agentsCopyService;
	/**
	 * The insert/update/delete/copy of a network are security events: a network
	 * decides which models, tools and knowledge its agents use and who may use it.
	 */
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

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
		// created on entry, so the event's caller stack points at this endpoint
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<GAgentsNetwork> status = ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENTS_NETWORK_UPDATE,
				SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK, network != null ? network.getCode() : null, network,
				() -> networkCrudService.update(network));
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
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<GAgentsNetwork> status = ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENTS_NETWORK_INSERT,
				SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK, network != null ? network.getCode() : null, network,
				() -> networkCrudService.insert(network));
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
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		OperationStatus<GAgentsNetwork> status = ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENTS_NETWORK_DELETE,
				SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK, network != null ? network.getCode() : null, null,
				() -> networkCrudService.delete(network));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST deleteAgentsNetwork code:" + (network != null ? network.getCode() : null) + " produced a result:"
					+ (status != null && status.getResult() != null) + " message(s):"
					+ (status != null && status.getMessages() != null ? status.getMessages().size() : 0));
		}
		return status;
	}

	/**
	 * Copies the network of agents with the given code, together with its agents,
	 * into a new, editable network whose participants are copies of the original
	 * agents, also when the original is a read-only network declared by a
	 * configuration bean (see {@link IGAgentsCopyService#copyAgentsNetwork(String)}).
	 */
	@PostMapping(value = "copyAgentsNetwork", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAgentsNetwork> copyAgentsNetwork(@RequestParam("code") String code) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST copyAgentsNetwork code:" + code);
		}
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		// the network copied is the resource; the code of the new, editable copy is a detail
		OperationStatus<GAgentsNetwork> status = ConfigurationAuditSupport.audited(securityAuditLoggerService, event,
				SecurityAuditTaxonomy.EventType.AGENTS_CONFIGURATION, SecurityAuditTaxonomy.Action.AGENTS_NETWORK_COPY,
				SecurityAuditTaxonomy.ResourceType.AGENTS_NETWORK, code, null,
				() -> agentsCopyService.copyAgentsNetwork(code), (e, copied) -> {
					if (copied != null && copied.getResult() != null) {
						e.getDetails().put("copyCode", copied.getResult().getCode());
					}
				});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST copyAgentsNetwork code:" + code + " produced a copy:"
					+ (status != null && status.getResult() != null ? status.getResult().getCode() : null)
					+ " message(s):" + (status != null && status.getMessages() != null ? status.getMessages().size() : 0));
		}
		return status;
	}

	// ---------------------------------------------------------------------
	// Visual composer: agent configs / services lookup
	// ---------------------------------------------------------------------

	/**
	 * Lists every agent configuration, whole, so the composer can bind a network
	 * participant to a concrete configuration knowing the service it runs and its
	 * role.
	 */
	@GetMapping(value = "getAgentConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GAgentConfig> getAgentConfigs() {
		List<GAgentConfig> configs = new ArrayList<>(get(IAgentConfigDao.class).getConfigurations());
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
	 * The tools each of the given agent configurations mounts on its agent's model,
	 * as the agent mounts them when it runs: auto mounted (every registered tool but
	 * the excluded ones) or the selected ones. Read by the network composer to show
	 * the tools of each participant, also before the network is saved. A code with
	 * no configuration, or a configuration with no agent service, mounts no tool.
	 */
	@PostMapping(value = "getAgentsMountedTools", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentMountedTools> getAgentsMountedTools(@RequestBody @NotNull List<String> agentConfigCodes) {
		IAgentConfigDao configDao = get(IAgentConfigDao.class);
		IGAgentServiceRuntimeDao serviceDao = get(IGAgentServiceRuntimeDao.class);
		List<AgentMountedTools> result = new ArrayList<>();
		for (String code : new LinkedHashSet<>(agentConfigCodes)) {
			if (code == null || code.isBlank()) {
				continue;
			}
			GAgentConfig config = configDao.findByCode(code);
			IGGenericAgentService service = config != null && config.getAgentServiceId() != null
					? serviceDao.findByCode(config.getAgentServiceId())
					: null;
			AgentMountedTools mounted;
			if (service != null) {
				mounted = service.getMountedTools(config);
			} else {
				mounted = new AgentMountedTools();
				mounted.setAgentConfigCode(code);
				mounted.setMountMode(AgentMountedTools.MountMode.SELECTED);
			}
			result.add(mounted);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentsMountedTools for " + agentConfigCodes.size() + " code(s) returned "
					+ result.size() + " result(s)");
		}
		return result;
	}

	/**
	 * Lists all network agent services available for composition, with their
	 * input/output types and composition flags (routing, network adapter).
	 */
	@GetMapping(value = "getAgentServices", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentServiceDescriptor> getAgentServices() {
		// the services answering the user directly are listed too, described with the
		// types of the adapter that makes them a network agent: the composer needs to
		// know they exist and that they take part only as the single output node
		final IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern adapterFactories = get(
				IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern.class);
		List<AgentServiceDescriptor> descriptors = get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService || s instanceof IGReactiveAgentService)
				.map(s -> AgentServiceDescriptor.of(s, adapterFactories)).toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentServices returned " + descriptors.size() + " agent service(s), reactive:"
					+ descriptors.stream().filter(AgentServiceDescriptor::isReactive).count());
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

	/**
	 * Lists the network service factories a network can declare to run on, with the
	 * contract each one imposes: the input its input nodes must accept, the output
	 * its output nodes must produce, whether it streams one reactive output node.
	 */
	@GetMapping(value = "getNetworkServiceFactories", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NetworkServiceFactoryDescriptor> getNetworkServiceFactories() {
		final IGAgentsNetworkServiceFactoryRepositoryPattern factories = get(
				IGAgentsNetworkServiceFactoryRepositoryPattern.class);
		List<NetworkServiceFactoryDescriptor> descriptors = factories != null
				? factories.getImplementations().stream().map(f -> (IGAgentsNetworkServiceFactory<?, ?, ?>) f)
						.map(NetworkServiceFactoryDescriptor::of).toList()
				: List.of();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getNetworkServiceFactories returned " + descriptors.size() + " factory(ies)");
		}
		return descriptors;
	}

	/**
	 * For every type the agent services and the network service factories exchange,
	 * the types assignable to it: the answer of Java assignability the composer
	 * needs to tell, while the admin draws an edge, whether what an agent produces
	 * can be given to another, without guessing the type hierarchy on the client.
	 */
	@GetMapping(value = "getAgentTypesCompatibility", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AgentTypeCompatibility> getAgentTypesCompatibility() {
		final Set<Class<?>> types = new LinkedHashSet<>();
		get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGNetworkAgentService).map(s -> (IGNetworkAgentService<?, ?>) s)
				.forEach(s -> {
					if (s.getInputType() != null) {
						types.add(s.getInputType());
					}
					if (s.getOutputType() != null) {
						types.add(s.getOutputType());
					}
				});
		final IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern adapterFactories = get(
				IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern.class);
		get(IGAgentServiceRuntimeDao.class).getConfigurations().stream()
				.filter(s -> s instanceof IGReactiveAgentService).map(s -> AgentServiceDescriptor.of(s, adapterFactories))
				.forEach(d -> {
					typeOf(d.getInputType()).ifPresent(types::add);
					typeOf(d.getOutputType()).ifPresent(types::add);
				});
		final IGAgentsNetworkServiceFactoryRepositoryPattern factories = get(
				IGAgentsNetworkServiceFactoryRepositoryPattern.class);
		if (factories != null) {
			factories.getImplementations().stream().map(f -> (IGAgentsNetworkServiceFactory<?, ?, ?>) f).forEach(f -> {
				if (f.getInputType() != null) {
					types.add(f.getInputType());
				}
				if (f.getOutputType() != null) {
					types.add(f.getOutputType());
				}
			});
		}
		List<AgentTypeCompatibility> compatibilities = types.stream().map(target -> new AgentTypeCompatibility(
				target.getName(),
				types.stream().filter(source -> target.isAssignableFrom(source)).map(Class::getName).toList()))
				.toList();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST getAgentTypesCompatibility returned " + compatibilities.size() + " type(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_TYPES_COMPATIBILITY>");
			LOGGER.trace(String.valueOf(compatibilities));
			LOGGER.trace("</AGENT_TYPES_COMPATIBILITY>");
		}
		return compatibilities;
	}

	/**
	 * The class of a type name the descriptors carry, when the class loader of the
	 * controller knows it (the adapters' types are loaded with the agents).
	 */
	private Optional<Class<?>> typeOf(String typeName) {
		if (typeName == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(Class.forName(typeName, false, getClass().getClassLoader()));
		} catch (ClassNotFoundException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The type " + typeName + " of a reactive agent adapter is not loadable: skipped", e);
			}
			return Optional.empty();
		}
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

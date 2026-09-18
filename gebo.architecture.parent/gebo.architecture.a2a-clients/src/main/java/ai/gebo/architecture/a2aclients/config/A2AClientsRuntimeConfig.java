package ai.gebo.architecture.a2aclients.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.architecture.a2aclients.repository.A2ARemoteAgentConfigRepository;
import ai.gebo.architecture.a2aclients.service.impl.A2AClientConnector;
import ai.gebo.architecture.a2aclients.service.impl.A2ARemoteServiceIds;
import ai.gebo.architecture.a2aclients.service.impl.RemoteA2ANetworkAgentService;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.architecture.agents.services.IGDynamicAgentServiceSupplier;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;

/**
 * Wires enabled {@link A2ARemoteAgentConfig}s into the agent runtime as network
 * participants, using the same two-bean pattern the standard module uses for its
 * dynamic agents:
 * <ul>
 * <li>an {@link IGDynamicAgentServiceSupplier} that produces one
 * {@link RemoteA2ANetworkAgentService} per enabled remote agent, and</li>
 * <li>an {@link IGDynamicAgentConfigDataSource} that produces the matching
 * {@link GAgentConfig} (paired to its service by {@code agentServiceId}).</li>
 * </ul>
 * Both read the registered agents fresh on every call, and both skip configs that
 * are not {@code enabled} — so a remote agent is only mounted once an admin has
 * explicitly enabled it (secure by default).
 */
@Configuration
public class A2AClientsRuntimeConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2AClientsRuntimeConfig.class);

	private final A2ARemoteAgentConfigRepository repository;
	private final A2AClientConnector connector;
	private final IGRuntimeBinder runtimeBinder;

	public A2AClientsRuntimeConfig(A2ARemoteAgentConfigRepository repository, A2AClientConnector connector,
			IGRuntimeBinder runtimeBinder) {
		this.repository = repository;
		this.connector = connector;
		this.runtimeBinder = runtimeBinder;
	}

	private List<A2ARemoteAgentConfig> enabledAgents() {
		List<A2ARemoteAgentConfig> out = new ArrayList<>();
		for (A2ARemoteAgentConfig cfg : repository.findAll()) {
			if (cfg.getEnabled() != null && cfg.getEnabled()) {
				out.add(cfg);
			}
		}
		return out;
	}

	@Bean
	public IGDynamicAgentServiceSupplier a2aRemoteAgentServicesSupplier() {
		return () -> {
			List<IGGenericAgentService> services = new ArrayList<>();
			for (A2ARemoteAgentConfig cfg : enabledAgents()) {
				services.add(new RemoteA2ANetworkAgentService(cfg, connector, runtimeBinder));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("a2aRemoteAgentServicesSupplier produced {} remote A2A agent service(s)", services.size());
			}
			return services;
		};
	}

	@Bean
	public IGDynamicAgentConfigDataSource a2aRemoteAgentConfigDataSource() {
		return () -> {
			List<GAgentConfig> configs = new ArrayList<>();
			for (A2ARemoteAgentConfig cfg : enabledAgents()) {
				String serviceId = A2ARemoteServiceIds.serviceId(cfg);
				GAgentConfig agentConfig = new GAgentConfig();
				agentConfig.setCode(serviceId);
				agentConfig.setAgentServiceId(serviceId);
				agentConfig.setAgentRoleCode(serviceId);
				agentConfig.setDescription(cfg.getDescription() != null ? cfg.getDescription()
						: "Remote A2A agent " + cfg.getExportingPrefix());
				agentConfig.setSubscribeAllTools(false);
				agentConfig.setEnabledFunctions(List.of());
				// Carry the remote agent's ACLs so filterCanDoAction gates access the same way.
				agentConfig.setAccessibleToAll(cfg.getAccessibleToAll());
				agentConfig.setAccessibleUsers(cfg.getAccessibleUsers());
				agentConfig.setAccessibleGroups(cfg.getAccessibleGroups());
				agentConfig.setAclAliases(cfg.getAclAliases());
				configs.add(agentConfig);
			}
			return configs;
		};
	}
}

package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGAgentService;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGDynamicAgentServiceSupplier;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;

@Service
public class AgentServiceRuntimeDaoImpl extends GAbstractRuntimeConfigurationDao<IGGenericAgentService>
		implements IGAgentServiceRuntimeDao {

	public AgentServiceRuntimeDaoImpl(final @Autowired(required = false) List<IGGenericAgentService> implementations,
			final @Autowired(required = false) List<IGDynamicAgentServiceSupplier> agentServiceSuppliers) {
		super(implementations, dynamicDs(agentServiceSuppliers));
	}

	private static IGDynamicConfigurationSource<IGGenericAgentService> dynamicDs(
			final List<IGDynamicAgentServiceSupplier> agentServiceSuppliers) {
		return new IGDynamicConfigurationSource<IGGenericAgentService>() {

			@Override
			public List<IGGenericAgentService> getConfigurations() {

				List<IGGenericAgentService> agentServices = new ArrayList<>();
				if (agentServiceSuppliers != null) {
					for (IGDynamicAgentServiceSupplier supplier : agentServiceSuppliers) {
						List<IGGenericAgentService> supplied = supplier.get();
						if (supplied != null)
							agentServices.addAll(supplied);
					}
				}
				return agentServices;
			}

			@Override
			public IGGenericAgentService findByCode(String code) {

				return getConfigurations().stream()
						.filter(x -> x.getId() != null && code != null && x.getId().equals(code)).findFirst()
						.orElse(null);
			}
		};

	}

	@Override
	public IGGenericAgentService findByCode(String code) {

		return findByPredicate(x -> x.getId() != null && code != null && x.getId().equals(code));
	}

}

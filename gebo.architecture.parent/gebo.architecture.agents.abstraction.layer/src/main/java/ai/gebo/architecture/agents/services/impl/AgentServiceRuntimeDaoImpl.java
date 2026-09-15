package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGDynamicAgentServiceSupplier;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;

@Service
public class AgentServiceRuntimeDaoImpl extends GAbstractRuntimeConfigurationDao<IGGenericAgentService>
		implements IGAgentServiceRuntimeDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentServiceRuntimeDaoImpl.class);

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
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Dynamic agent service supplier " + supplier.getClass().getName() + " supplied "
									+ (supplied != null ? supplied.size() : 0) + " agent service(s)");
						}
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
		IGGenericAgentService service = findByPredicate(x -> x.getId() != null && code != null && x.getId().equals(code));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findByCode(" + code + ") agent service resolved:"
					+ (service != null ? service.getClass().getName() : null));
		}
		return service;
	}

}

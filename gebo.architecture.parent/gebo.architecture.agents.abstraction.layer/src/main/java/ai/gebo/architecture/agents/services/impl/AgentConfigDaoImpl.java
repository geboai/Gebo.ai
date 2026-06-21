package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.repository.AgentConfigRepository;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;

@Service
public class AgentConfigDaoImpl extends GAbstractRuntimeConfigurationDao<GAgentConfig> implements IAgentConfigDao {
	private final IGPersistentObjectManager persistentObjectManager;

	public AgentConfigDaoImpl(@Autowired(required = false) List<IGDynamicAgentConfigDataSource> dataSources,
			AgentConfigRepository agentsRepo, IGPersistentObjectManager persistentObjectManager) {
		super(new ArrayList<>(), compose(dataSources, agentsRepo));
		this.persistentObjectManager = persistentObjectManager;

	}

	@Override
	public GAgentConfig findByCode(String code) {

		return dynamic.findByCode(code);
	}

	private static IGDynamicConfigurationSource<GAgentConfig> compose(List<IGDynamicAgentConfigDataSource> dataSources,
			AgentConfigRepository agentsRepo) {
		IGDynamicConfigurationSource staticDss = new IGDynamicConfigurationSource<GAgentConfig>() {

			@Override
			public List<GAgentConfig> getConfigurations() {
				List<GAgentConfig> configurations = new ArrayList<>();
				if (dataSources != null) {
					for (IGDynamicAgentConfigDataSource ds : dataSources) {
						List<GAgentConfig> cfgs = ds.getConfigurations();
						for (GAgentConfig cfg : cfgs) {
							GAgentConfig clone = cfg.jsonClone();
							clone.setReadOnly(true);
							configurations.add(clone);
						}
					}
				}
				return configurations;
			}

			@Override
			public GAgentConfig findByCode(String code) {

				return getConfigurations().stream()
						.filter(x -> x.getCode() != null && code != null && x.getCode().equals(code)).findFirst()
						.orElse(null);
			}
		};
		return IGDynamicConfigurationSource.compose(staticDss, GDynamicConfigurationSourceAdapter.of(agentsRepo));
	}

	@Override
	public List<GAgentConfig> findByAgentServiceId(String id) {

		return findListByPredicate(
				x -> x.getAgentServiceId() != null && id != null && x.getAgentServiceId().equals(id));
	}

	@Override
	public GAgentConfig insert(GAgentConfig config) throws GeboPersistenceException {
		if (config.getReadOnly() != null && config.getReadOnly())
			throw new GeboPersistenceException("This agentConfig cannot be inserted " + config);
		return persistentObjectManager.insert(config);
	}

	@Override
	public GAgentConfig update(GAgentConfig config) throws GeboPersistenceException {
		if (config.getReadOnly() != null && config.getReadOnly())
			throw new GeboPersistenceException("This agentConfig cannot be updated " + config);
		return persistentObjectManager.update(config);
	}

	@Override
	public void delete(GAgentConfig config) throws GeboPersistenceException {
		if (config.getReadOnly() != null && config.getReadOnly())
			throw new GeboPersistenceException("This agentConfig cannot be deleted " + config);
		persistentObjectManager.delete(config);

	}
}

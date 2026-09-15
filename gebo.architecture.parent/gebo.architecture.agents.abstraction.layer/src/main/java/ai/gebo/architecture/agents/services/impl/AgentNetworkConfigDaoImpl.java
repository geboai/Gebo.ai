package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.repository.AgentsNetworkRepository;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;

@Service
public class AgentNetworkConfigDaoImpl extends GAbstractRuntimeConfigurationDao<GAgentsNetwork>
		implements IAgentsNetworkDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentNetworkConfigDaoImpl.class);
	private final IGPersistentObjectManager persistentObjectManager;

	public AgentNetworkConfigDaoImpl(@Autowired(required = false) List<IDynamicAgentsNetworkDataSource> dataSources,
			AgentsNetworkRepository networksRepo, IGPersistentObjectManager persistentObjectManager) {
		super(new ArrayList<>(), compose(dataSources, networksRepo));
		this.persistentObjectManager = persistentObjectManager;

	}

	@Override
	public GAgentsNetwork findByCode(String code) {
		GAgentsNetwork network = dynamic.findByCode(code);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findByCode(" + code + ") agents network found:" + (network != null) + " participants:"
					+ (network != null && network.getAgents() != null ? network.getAgents().size() : 0));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENTS_NETWORK code=" + code + ">");
			LOGGER.trace(String.valueOf(network));
			LOGGER.trace("</AGENTS_NETWORK>");
		}
		return network;
	}

	private static IGDynamicConfigurationSource<GAgentsNetwork> compose(List<IDynamicAgentsNetworkDataSource> dataSources,
			AgentsNetworkRepository agentsRepo) {
		IGDynamicConfigurationSource staticDss = new IGDynamicConfigurationSource<GAgentsNetwork>() {

			@Override
			public List<GAgentsNetwork> getConfigurations() {
				List<GAgentsNetwork> configurations = new ArrayList<>();
				if (dataSources != null) {
					for (IDynamicAgentsNetworkDataSource ds : dataSources) {
						List<GAgentsNetwork> cfgs = ds.getConfigurations();
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Dynamic agents network data source " + ds.getClass().getName() + " contributed "
									+ (cfgs != null ? cfgs.size() : 0) + " read only network(s)");
						}
						for (GAgentsNetwork cfg : cfgs) {
							GAgentsNetwork clone = cfg.jsonClone();
							clone.setReadOnly(true);
							configurations.add(clone);
						}
					}
				}
				return configurations;
			}

			@Override
			public GAgentsNetwork findByCode(String code) {

				return getConfigurations().stream()
						.filter(x -> x.getCode() != null && code != null && x.getCode().equals(code)).findFirst()
						.orElse(null);
			}
		};
		return IGDynamicConfigurationSource.compose(staticDss, GDynamicConfigurationSourceAdapter.of(agentsRepo));
	}

	@Override
	public GAgentsNetwork insert(GAgentsNetwork config) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inserting agents network code:" + (config != null ? config.getCode() : null));
		}
		if (config.getReadOnly() != null && config.getReadOnly())
			throw new GeboPersistenceException("This GAgentsNetwork cannot be inserted " + config);
		return persistentObjectManager.insert(config);
	}

	@Override
	public GAgentsNetwork update(GAgentsNetwork config) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Updating agents network code:" + (config != null ? config.getCode() : null));
		}
		if (config.getReadOnly() != null && config.getReadOnly())
			throw new GeboPersistenceException("This GAgentsNetwork cannot be updated " + config);
		return persistentObjectManager.update(config);
	}

	@Override
	public void delete(GAgentsNetwork config) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deleting agents network code:" + (config != null ? config.getCode() : null));
		}
		if (config.getReadOnly() != null && config.getReadOnly())
			throw new GeboPersistenceException("This GAgentsNetwork cannot be deleted " + config);
		persistentObjectManager.delete(config);

	}
}

package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.architecture.persistence.GeboPersistenceException;

public interface IAgentsNetworkDao extends IGRuntimeConfigurationDao<GAgentsNetwork> {

	public GAgentsNetwork insert(GAgentsNetwork config) throws GeboPersistenceException;

	public GAgentsNetwork update(GAgentsNetwork config) throws GeboPersistenceException;

	public void delete(GAgentsNetwork config) throws GeboPersistenceException;
}

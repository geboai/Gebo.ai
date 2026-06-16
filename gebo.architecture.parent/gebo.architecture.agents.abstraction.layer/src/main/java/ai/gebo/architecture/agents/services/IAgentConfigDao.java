package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.architecture.persistence.GeboPersistenceException;

public interface IAgentConfigDao extends IGRuntimeConfigurationDao<GAgentConfig> {

	public List<GAgentConfig> findByAgentServiceId(String id);

	public GAgentConfig insert(GAgentConfig config) throws GeboPersistenceException;

	public GAgentConfig update(GAgentConfig config) throws GeboPersistenceException;

	public void delete(GAgentConfig config) throws GeboPersistenceException;

}

package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.config.AgentRolesConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;

@Service
public class GAgentRoleDaoImpl extends GAbstractRuntimeConfigurationDao<GAgentRole> implements IAgentRoleDao {

	public GAgentRoleDaoImpl(AgentRolesConfig config) {
		super(config.getLibrary(), null);

	}

	@Override
	public GAgentRole findByCode(String code) {

		return findByPredicate(x -> x.getCode() != null && code != null && code.equals(x.getCode()));
	}

}

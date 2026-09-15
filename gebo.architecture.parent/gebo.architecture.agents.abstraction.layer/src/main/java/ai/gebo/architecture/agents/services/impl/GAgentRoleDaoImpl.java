package ai.gebo.architecture.agents.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.config.AgentRolesConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;

@Service
public class GAgentRoleDaoImpl extends GAbstractRuntimeConfigurationDao<GAgentRole> implements IAgentRoleDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAgentRoleDaoImpl.class);

	public GAgentRoleDaoImpl(AgentRolesConfig config) {
		super(config.getLibrary(), null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agent roles DAO initialized with "
					+ (config.getLibrary() != null ? config.getLibrary().size() : 0) + " configured role(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_ROLES_LIBRARY>");
			LOGGER.trace(String.valueOf(config.getLibrary()));
			LOGGER.trace("</AGENT_ROLES_LIBRARY>");
		}
	}

	@Override
	public GAgentRole findByCode(String code) {
		GAgentRole role = findByPredicate(x -> x.getCode() != null && code != null && code.equals(x.getCode()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findByCode(" + code + ") agent role found:" + (role != null));
		}
		return role;
	}

}

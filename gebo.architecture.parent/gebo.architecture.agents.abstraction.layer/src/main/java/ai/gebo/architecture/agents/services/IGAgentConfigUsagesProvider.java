package ai.gebo.architecture.agents.services;

import java.util.List;

/**
 * A configuration using agent configurations outside the networks of agents, like an
 * agent exported over A2A or exposed as an MCP tool. An agent configuration it uses
 * is not used solely by a network of agents: it is kept when that network is
 * deleted.
 */
public interface IGAgentConfigUsagesProvider {

	/**
	 * The usages of the agent configuration with the given code, each told in words
	 * (e.g. "exported as A2A agent by the server 'X'"); empty when it uses none.
	 *
	 * @param agentConfigCode the code of the agent configuration
	 * @return the descriptions of its usages, never null
	 */
	List<String> findAgentConfigUsages(String agentConfigCode);
}

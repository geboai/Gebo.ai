package ai.gebo.architecture.agents.services;

public class AgentPromptTemplateParams {

	public static final String AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM = "AGENT_COMUNICATION_CAPABILITY";
	/**
	 * Network-wide roster of every participating agent's exported capabilities,
	 * catalogs, resources and tools, independent of which agents the current agent
	 * can reach. Complements {@link #AGENT_COMUNICATION_CAPABILITY_TEMPLATE_PARAM},
	 * which is scoped to the current agent's reachable peers.
	 */
	public static final String NETWORK_AGENTS_CAPABILITIES_TEMPLATE_PARAM = "NETWORK_AGENTS_CAPABILITIES";
	public static final String AGENT_IDENTITY_TEMPLATE_PARAM = "AGENT_IDENTITY";
	public static final String FORMAT_TEMPLATE_PARAM = "format";
	public static final String NETWORK_SCENARY_TEMPLATE_PARAM = "NETWORK_SCENARY";
	public static final String PRIVATE_CONTEXT_TEMPLATE_PARAM = "PRIVATE_CONTEXT";
	public static final String SHARED_CONTEXT_TEMPLATE_PARAM = "SHARED_CONTEXT";
	public static final String INPUT_TEMPLATE_PARAM = "INPUT";

}

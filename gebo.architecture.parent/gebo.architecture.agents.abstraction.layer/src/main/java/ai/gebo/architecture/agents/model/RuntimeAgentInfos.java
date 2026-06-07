package ai.gebo.architecture.agents.model;

import ai.gebo.architecture.agents.model.AgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class RuntimeAgentInfos {
	private final IGNetworkAgentService service;
	private final GAgentConfig config;
	private final AgentNetworkParticipant networkParticipantConfig;
	private final AgentPrivateSessionContext agentContext = new AgentPrivateSessionContext();
	@Setter
	private int turnOfExecution = 0;
}
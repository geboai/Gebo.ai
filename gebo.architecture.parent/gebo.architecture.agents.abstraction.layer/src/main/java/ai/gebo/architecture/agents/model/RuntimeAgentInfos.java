package ai.gebo.architecture.agents.model;

import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import java.util.concurrent.atomic.AtomicInteger;

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
	/** How many times the network dispatched a message to this agent. */
	private final AtomicInteger invocations = new AtomicInteger();
	@Setter
	private int turnOfExecution = 0;
	@Setter
	private int sessionSharedContributionsReadIndex=0;
}
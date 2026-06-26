package ai.gebo.architecture.agents.model;

import java.util.List;

import ai.gebo.model.IJsonClonable;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GAgentsNetwork extends GBaseObject implements IJsonClonable<GAgentsNetwork> {
	public static enum MessagesTargetsDecision {
		ALLOWED, NOT_ALLOWED
	}

	public static enum CommunicationPolicy {
		ALLOW_ALL, DENY_ALL, ALLOW_LIST, DENY_LIST
	}

	public static enum AgentActivationType {
		ON_RECEIVED_MESSAGE, EVERY_CYCLE
	}

	int maxLoopIteration = 5;

	@Data
	public static class AgentNetworkParticipant {
		@NotNull
		private String agentConfigCode;
		private String agentContextualName;
		private boolean inputNode;
		private boolean outputNode;
		
		@NotNull
		private CommunicationPolicy communicationPolicy = CommunicationPolicy.ALLOW_LIST;

		private List<String> communicationList;

		private Integer maxInvocations;

		private Integer maxConsecutiveInvocations;

		private boolean canCallTools = true;

		private boolean canCallOtherAgents = false;

		public String getNetworkAgentName() {

			return agentConfigCode + (agentContextualName != null ? "-" + agentContextualName : "");
		}
		public void setNetworkAgentName(String s) {
			
		}
	}

	@NotNull
	private String scenarioDescription = null;
	@NotNull
	@NotEmpty
	private List<AgentNetworkParticipant> agents = null;
	private Boolean readOnly = null;
	private Boolean defaultUserInteractionNetwork = null;

}

package ai.gebo.architecture.agents.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.IJsonClonable;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GAgentsNetwork extends GBaseObject
		implements IJsonClonable<GAgentsNetwork>, IGObjectWithSecurity, IAclGrantedResource {
	/*
	 * public String owner(); public Boolean getAccessibleToAll(); public
	 * List<String> getAccessibleUsers(); public List<String> getAccessibleGroups();
	 */
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
	Boolean accessibleToAll = null;
	List<String> accessibleUsers = null;
	List<String> accessibleGroups = null;
	List<Integer> aclAliases = null;

	@Data
	public static class AgentNetworkParticipant {
		@NotNull
		private String agentConfigCode;
		private String agentContextualName;
		private boolean inputNode;
		private boolean outputNode;
		private boolean allowedToNotifyUser = true;
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

package ai.gebo.architecture.agents.model;

import java.util.List;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.IJsonClonable;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GAgentsNetwork extends GBaseObject
		implements IJsonClonable<GAgentsNetwork>, IGObjectWithSecurity, IAclGrantedResource, IGCopiedEntity {
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

	/**
	 * How many times the controller participant may act: the coordination cycles
	 * of a router, the search-and-rewrite cycles of a single agent answering the
	 * user. The other participants act only on what the controller sends.
	 */
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

		/**
		 * The participant the network's loop is counted on: the one that coordinates
		 * the others (a router) or, in a network of one agent answering the user, that
		 * agent. {@link GAgentsNetwork#getMaxLoopIteration()} is the number of times it
		 * may act. Exactly one participant is the controller.
		 */
		private boolean controller = false;

		private boolean canCallTools = true;

		/**
		 * The ids of the tool sources this participant mounts in this network even when
		 * the installation keeps them out of automatic mounting, added to whatever its
		 * agent configuration already mounts
		 * ({@link GAgentConfig#getAdditionalToolSources()}). It is how a family of tools
		 * belongs to the networks that are meant to operate it rather than to every
		 * agent that mounts all tools: the network names the source on the participant
		 * that needs it, the installation excludes it for everyone else.
		 */
		private List<String> additionalToolSources = null;

		private boolean canCallOtherAgents = false;

		public String getNetworkAgentName() {

			return agentConfigCode + (agentContextualName != null ? "-" + agentContextualName : "");
		}

		public void setNetworkAgentName(String s) {

		}
	}

	@NotNull
	private String agentsNetworkServiceFactoryId;
	@NotNull
	private String scenarioDescription = null;
	@NotNull
	@NotEmpty
	private List<AgentNetworkParticipant> agents = null;
	private Boolean readOnly = null;
	/**
	 * When this network is the right choice, in words the administrator can act on
	 * (the kind of LLMs and provider conditions it fits), shown where the network is
	 * chosen.
	 */
	private String suggestedPurpose = null;
	/**
	 * The chat pipeline types this network can be chosen for as the chat network of
	 * agents; not choosable when empty.
	 */
	private List<PipelineType> choosableForPipelineTypes = null;

	/**
	 * The code of the network this one is a copy of, only while the copy is being
	 * inserted: its code is made from it. Neither stored nor exchanged.
	 */
	@Transient
	@JsonIgnore
	private String copiedFromCode = null;
	/**
	 * Whether the network remembers, from one turn of a conversation to the next,
	 * what its agents contributed: the shared context of a run is digested when
	 * the run ends and seeds the next run in the same conversation. Off by
	 * default: a network that does not ask forgets everything but what the
	 * conversation itself keeps.
	 */
	private Boolean rememberAcrossTurns = null;

}

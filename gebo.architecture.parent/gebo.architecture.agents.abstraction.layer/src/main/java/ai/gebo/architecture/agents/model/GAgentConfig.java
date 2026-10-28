package ai.gebo.architecture.agents.model;

import java.util.List;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.IJsonClonable;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GAgentConfig extends GBaseObject
		implements IGObjectWithSecurity, IAclGrantedResource, IJsonClonable<GAgentConfig>, IGCopiedEntity {
	public static enum AgentType {
		AGENT, AGENTS_NETWORK
	}

	private AgentType agentType = AgentType.AGENT;
	@GObjectReference(referencedType = GAgentsNetwork.class)
	private String adaptedAgentNetworkCode;
	private String agentNetworkServiceCode;
	@NotNull
	private String agentServiceId = null;
	private String mainLoopPromptUseCode = null;
	private GPromptTemplateConfig customLoopPrompt = null;
	private Boolean subscribeAllTools = null;
	@NotNull
	private String agentRoleCode = null;

	private Boolean useDefaultChatModel = null;
	private ChatModelsUses useChatModelWithUse = null;
	@GObjectReference(referencedType = GBaseChatModelConfig.class, referencesExtensions = true)
	private GObjectRef<GBaseChatModelConfig> chatModelReference = null;
	private List<Integer> aclAliases = null;
	private Boolean defaultConfiguration = null;
	/**
	 * The probability threshold used for sampling, null by default.
	 */
	private Double topP = null;

	private Double temperature = null;
	private ChatModelThinkingOption thinking = null;
	private Boolean readOnly = null;
	/**
	 * A list of group identifiers that have access to the model configuration.
	 */
	protected List<String> accessibleGroups = null;

	/**
	 * A list of user identifiers that have access to the model configuration.
	 */
	protected List<String> accessibleUsers = null;

	/**
	 * A boolean flag indicating if access is granted to all users.
	 */
	protected Boolean accessibleToAll = null;

	/**
	 * A list of functions that are enabled for direct chat interaction.
	 */
	protected List<String> enabledFunctions = null;

	/**
	 * The ids of the tool sources whose tools this agent leaves out when it mounts
	 * every registered tool ({@link #subscribeAllTools}), on top of the system-wide
	 * exclusions of the automatic mounting: how the tool-calling agent of a network
	 * with search agents is kept off the search tools those agents already run, while
	 * it still operates the other tools (MCP tools, the basic tools of the system).
	 * Null or empty when the agent leaves out only the system-wide exclusions; the
	 * tools selected by name ({@link #enabledFunctions}) are never affected.
	 */
	protected List<String> excludedToolSources = null;

	/**
	 * The ids of the tool sources whose tools this agent mounts even when the
	 * installation keeps them out of automatic mounting
	 * ({@code ai.gebo.agents.tools.auto-mounting.excluded-tool-sources}): the
	 * counterpart of {@link #excludedToolSources}, for a family of tools that only
	 * some agents are meant to operate. An agent that mounts every registered tool
	 * ({@link #subscribeAllTools}) has no other way back to an excluded tool, its
	 * automatic mounting replacing the tools selected by name
	 * ({@link #enabledFunctions}) outright. Null or empty when the agent mounts
	 * nothing the installation excludes; what the configuration itself excludes
	 * stays excluded.
	 */
	protected List<String> additionalToolSources = null;

	/**
	 * The ids of the library types this agent works with: bundles of tools and the
	 * resources those tools work on - a working directory with its file tools, a
	 * sandbox with its code tools - which an agent is given whole. A library is
	 * named by its type and comes in block: every tool of it is mounted, none is
	 * chosen one by one, and the resources are whichever instance of the type the
	 * run holds (see {@code ToolCallbackDeclarationUtil#LIBRARY_INSTANCES_CONTEXT_KEY});
	 * in a run that holds none, the tools say the library is not available. Null or
	 * empty when the agent works with no library.
	 */
	protected List<String> libraryTypeIds = null;

	/**
	 * The code of the agent configuration this one is a copy of, only while the copy
	 * is being inserted: its code is made from it. Neither stored nor exchanged.
	 */
	@Transient
	@JsonIgnore
	private String copiedFromCode = null;
}

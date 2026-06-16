package ai.gebo.architecture.agents.model;

import java.util.List;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
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
public class GAgentConfig extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource,IJsonClonable<GAgentConfig> {
	@NotNull
	private String agentServiceId = null;
	private String mainLoopPromptUseCode = null;
	private GPromptTemplateConfig customLoopPrompt = null;
	private Boolean subscribeAllTools = null;
	@NotNull
	private String agentRoleCode = null;

	private Boolean useDefaultChatModel = null;
	@GObjectReference(referencedType = GBaseChatModelConfig.class, referencesExtensions = true)
	private GObjectRef<GBaseChatModelConfig> chatModelReference = null;
	@NotNull
	private Integer maxLoopIterations = null;
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
}

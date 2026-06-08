package ai.gebo.architecture.agents.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.architecture.agents.model.AgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.Data;

@Data
public class GBaseTaskPerformerNetworkAgentService<InputType, OutputType> extends GAbstractGenericalAgentService
		implements IGNetworkAgentService<InputType, OutputType> {
	private static final String INPUT_TEMPLATE_PARAM = "INPUT";
	private static final String PRIVATE_CONTEXT_TEMPLATE_PARAM = "PRIVATE_CONTEXT";
	private static final String SHARED_CONTEXT_TEMPLATE_PARAM = "SHARED_CONTEXT";
	private static final String NETWORK_SCENARY_TEMPLATE_PARAM = "NETWORK_SCENARY";
	protected final Class<InputType> inputType;
	protected final Class<OutputType> outputType;
	protected final String id;
	protected final String description;
	protected static final ObjectMapper objectMapper = new ObjectMapper();

	public GBaseTaskPerformerNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			GAgentConfigRepository configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			Class<InputType> inputType, Class<OutputType> outputType, String id, String description) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao);
		this.inputType = inputType;
		this.outputType = outputType;
		this.id = id;
		this.description = description;

	}

	@Override
	public List<AgentsExchangeMessage<OutputType>> onMessage(GAgentConfig config, AgentsExchangeMessage<InputType> msg,
			AgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session, AgentPrivateSessionContext mySessionContext,
			ReactiveIdentityUtil runAs) throws LLMConfigException, AgentException {
		final ToolCallsListener callBacksListener = new ToolCallsListener();
		IGConfigurableChatModel agentModel = getAgentModel(config, callBacksListener, null);
		GAgentRole agentRole = this.agentRoleDao.findByCode(config.getAgentRoleCode());
		GPromptTemplateConfig prompt = resolvePrompt(config.getCustomLoopPrompt(), config.getMainLoopPromptUseCode(),
				false);
		Map<String, Object> params = new HashMap<>();
		params.put(NETWORK_SCENARY_TEMPLATE_PARAM, createNetworkScenaryDescription(network, agentRole));
		params.put(SHARED_CONTEXT_TEMPLATE_PARAM, render(session));
		params.put(PRIVATE_CONTEXT_TEMPLATE_PARAM, render(mySessionContext));
		params.put(INPUT_TEMPLATE_PARAM, render(msg));
		OutputType output = null;
		if (String.class.isAssignableFrom(getOutputType())) {
			output = (OutputType) agentModel.textResponse(prompt, params, IChatRequestContext.of(""));
		} else {
			output = (OutputType) agentModel.structuredResponse(prompt, params, IChatRequestContext.of(""), outputType);
		}
		AgentsExchangeMessage<OutputType> out = new AgentsExchangeMessage<OutputType>(session.getId(),
				MessageSemantic.RESPONSE,
				contextAgentPersona.getAgentConfigCode() + (contextAgentPersona.getAgentContextualName() != null
						? "-" + contextAgentPersona.getAgentContextualName()
						: ""),
				agentRole, msg.getFromAgent(), output, 1);
		return List.of(out);
	}

	protected Object render(AgentsExchangeMessage<InputType> msg) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Object render(AgentPrivateSessionContext mySessionContext) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Object render(AgentsCollaborationSessionContext session) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Object createNetworkScenaryDescription(AgentsNetwork network, GAgentRole agentRole) {
		// TODO Auto-generated method stub
		return null;
	}

}

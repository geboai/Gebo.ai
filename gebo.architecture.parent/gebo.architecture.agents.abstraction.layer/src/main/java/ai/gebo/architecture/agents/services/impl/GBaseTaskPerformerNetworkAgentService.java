package ai.gebo.architecture.agents.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.ai.converter.BeanOutputConverter;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.GAbstractGenericalNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.Getter;

@Getter
public class GBaseTaskPerformerNetworkAgentService<InputType, OutputType>
		extends GAbstractGenericalNetworkAgentService<InputType, OutputType>
		implements IGNetworkAgentService<InputType, OutputType> {

	protected final Class<InputType> inputType;
	protected final Class<OutputType> outputType;
	protected final String id;
	protected final String description;

	public GBaseTaskPerformerNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			Class<InputType> inputType, Class<OutputType> outputType, String id, String description) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder);
		this.inputType = inputType;
		this.outputType = outputType;
		this.id = id;
		this.description = description;
	}
	
	@Override
	public List<AgentsExchangeMessage<OutputType>> onMessage(IChatRequestContext chatRequestContext,
			GAgentConfig config, AgentsExchangeMessage<InputType> msg, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {
		final ToolCallsListener callBacksListener = new ToolCallsListener();
		IGConfigurableChatModel agentModel = getAgentModel(config, callBacksListener, null);
		GAgentRole agentRole = this.agentRoleDao.findByCode(config.getAgentRoleCode());
		GPromptTemplateConfig prompt = resolvePrompt(config.getCustomLoopPrompt(), config.getMainLoopPromptUseCode(),
				false);
		Map<String, Object> params = createAgentTemplateParams(network, agentRole, contextAgentPersona, session,
				mySessionContext, msg, agentsDao);

		OutputType output = null;
		if (String.class.isAssignableFrom(getOutputType())) {
			output = (OutputType) agentModel.textResponse(prompt, params, chatRequestContext);
		} else {
			BeanOutputConverter<OutputType> converter = new BeanOutputConverter<>(outputType);
			params.put(FORMAT_TEMPLATE_PARAM, converter.getFormat());
			output = (OutputType) agentModel.structuredResponse(prompt, params, chatRequestContext, outputType);
		}
		AgentsExchangeMessage<OutputType> out = new AgentsExchangeMessage<OutputType>(session.getId(),
				MessageSemantic.RESPONSE,
				contextAgentPersona.getAgentConfigCode() + (contextAgentPersona.getAgentContextualName() != null
						? "-" + contextAgentPersona.getAgentContextualName()
						: ""),
				agentRole, msg.getFromAgent(), output, 1);
		return List.of(out);
	}

}

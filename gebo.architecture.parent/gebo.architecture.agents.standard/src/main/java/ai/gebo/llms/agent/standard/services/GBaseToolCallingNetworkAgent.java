package ai.gebo.llms.agent.standard.services;

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
import ai.gebo.architecture.agents.services.AgentPromptTemplateParams;
import ai.gebo.architecture.agents.services.GAbstractGenericalNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
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

/**
 * Base network agent whose single turn is a <em>tool-calling loop</em>: the
 * agent is handed the tool catalog enabled by its {@link GAgentConfig}
 * (explicitly enabled functions, or every registered tool when the
 * configuration subscribes to all of them, plus the {@code notifyUser} tool when
 * the persona is allowed to notify the user) and the chat model runs Spring AI's
 * framework-controlled tool-execution loop until it produces a final answer.
 *
 * <p>The heavy lifting - model resolution, tool wrapping, the tool-calling
 * manager, prompt template parametrization and context rendering - is inherited
 * from {@link GAbstractGenericalNetworkAgentService} /
 * {@code GAbstractGenericalAgentService}; this class only wires those pieces into
 * a one-shot request/response turn and adapts the produced value to the network
 * exchange envelope.
 *
 * <p>The turn resolves the output in two flavours, exactly like the other
 * standard network agents:
 * <ul>
 * <li>when {@code OutputType} is {@link String} a plain text response is
 * requested;</li>
 * <li>otherwise a structured response is requested and, when the prompt declares
 * the {@code {format}} placeholder, the Jackson-derived format instructions of
 * {@code OutputType} are injected.</li>
 * </ul>
 *
 * @param <InputType>  the payload type carried by inbound
 *                     {@link AgentsExchangeMessage}s
 * @param <OutputType> the payload type produced by the agent
 */
@Getter
public class GBaseToolCallingNetworkAgent<InputType, OutputType>
		extends GAbstractGenericalNetworkAgentService<InputType, OutputType>
		implements IGNetworkAgentService<InputType, OutputType> {

	protected final Class<InputType> inputType;
	protected final Class<OutputType> outputType;
	protected final String id;
	protected final String description;

	public GBaseToolCallingNetworkAgent(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			Class<InputType> inputType, Class<OutputType> outputType, String id, String description,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
		this.inputType = inputType;
		this.outputType = outputType;
		this.id = id;
		this.description = description;
	}

	@Override
	public List<AgentsExchangeMessage<OutputType>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<InputType> msg, int actualContributionNr, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onMessage(...) tool-calling agent id:" + getId() + " persona:"
					+ (contextAgentPersona != null ? contextAgentPersona.getAgentContextualName() : null)
					+ " contributionNr:" + actualContributionNr + " outputType:" + getOutputType().getName());
		}

		// The tool-call listener collects the tool invocations performed during the
		// framework-controlled tool-execution loop; the model is cloned with the tool
		// catalog enabled by the configuration (and the notifyUser tool when the
		// persona may notify the user).
		final ToolCallsListener callBacksListener = new ToolCallsListener();
		IGConfigurableChatModel agentModel = getAgentModel(config, callBacksListener,
				contextAgentPersona.isAllowedToNotifyUser() ? notificationSink : null, runAs);

		GAgentRole agentRole = this.agentRoleDao.findByCode(config.getAgentRoleCode());
		GPromptTemplateConfig prompt = resolvePrompt(config.getCustomLoopPrompt(), config.getMainLoopPromptUseCode(),
				false);
		int tokenBudget = (agentModel.getContextLength() - prompt.getTokensSize()) * 2 / 3;
		Map<String, Object> params = createAgentTemplateParams(prompt, network, agentRole, contextAgentPersona, session,
				mySessionContext, msg.getPayload(), agentsDao, actualContributionNr, tokenBudget);

		OutputType output = null;
		if (String.class.isAssignableFrom(getOutputType())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Requesting textResponse from tool-calling agent model id:" + getId());
			}
			output = (OutputType) agentModel.textResponse(prompt, params, chatRequestContext);
		} else {
			if (isPlaceholderDeclared(prompt, AgentPromptTemplateParams.FORMAT_TEMPLATE_PARAM)) {
				BeanOutputConverter<OutputType> converter = new BeanOutputConverter<>(outputType);
				params.put(AgentPromptTemplateParams.FORMAT_TEMPLATE_PARAM, converter.getFormat());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Requesting structuredResponse from tool-calling agent model id:" + getId()
						+ " targetType:" + outputType.getName());
			}
			output = (OutputType) agentModel.structuredResponse(prompt, params, chatRequestContext, outputType);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onMessage(...) tool-calling agent id:" + getId() + " produced output:" + (output != null));
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

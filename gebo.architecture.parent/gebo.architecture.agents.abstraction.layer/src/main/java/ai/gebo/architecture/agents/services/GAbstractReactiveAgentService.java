package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public abstract class GAbstractReactiveAgentService<RequestType, ResponseType,  AggregatedResponses>
		extends GAbstractGenericalAgentService
		implements IGReactiveAgentService<RequestType, ResponseType> {

	public GAbstractReactiveAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);

	}

	@Override
	public Flux<IGPartialOperation<ResponseType>> execute(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, RequestType request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<RequestType, ResponseType> privateMemory, ReactiveIdentityUtil runAs)
			throws AgentException, LLMConfigException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...) reactive agent service id:" + getId() + " agentConfig code:"
					+ (agentConfig != null ? agentConfig.getCode() : null) + " useDefaultChatModel:"
					+ (agentConfig != null ? agentConfig.getUseDefaultChatModel() : null));
		}
		IGConfigurableChatModel copiedModel = null;
		if (agentConfig.getUseDefaultChatModel() != null && agentConfig.getUseDefaultChatModel()) {
			copiedModel = chatModelsDao.defaultHandler();
		} else {
			copiedModel = chatModelsDao.findByModelReference(agentConfig.getChatModelReference());
		}
		if (copiedModel == null) {
			LOGGER.warn("Setting backup default chat model for actual Agent");
			copiedModel = chatModelsDao.defaultHandler();
			if (copiedModel == null)
				throw new LLMConfigException("Default chat model not set in the system");
		}
		final ToolCallsListener callBacksListener = new ToolCallsListener();
		List<String> allFunctions = agentConfig.getEnabledFunctions();
		if (agentConfig.getSubscribeAllTools() != null && agentConfig.getSubscribeAllTools()) {
			List<ToolCallback> toolsList = toolsRepositoryPattern.getTools();
			if (toolsList != null) {
				allFunctions = toolsList.stream().map(x -> x.getToolDefinition().name()).toList();
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reactive agent id:" + getId() + " resolved " + (allFunctions != null ? allFunctions.size() : 0)
					+ " enabled function(s); cloning model with temperature:" + agentConfig.getTemperature() + " topP:"
					+ agentConfig.getTopP() + " thinking:" + agentConfig.getThinking());
		}
		ChatModelConfigOptions configOptions = new ChatModelConfigOptions(agentConfig.getTemperature(),
				agentConfig.getTopP(), agentConfig.getThinking(), allFunctions,
				createToolCallingManager(callBacksListener, allFunctions, null, runAs));
		IGConfigurableChatModel agentModel = copiedModel.cloneWithOptions(getId(), configOptions);

		final GPromptTemplateConfig agentPrompt = resolvePrompt(agentConfig.getCustomLoopPrompt(),
				agentConfig.getMainLoopPromptUseCode(), false);
		final GAgentRole agentRole = agentRoleDao.findByCode(agentConfig.getAgentRoleCode());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End execute(...) building reactive response flux for agent id:" + getId() + " agentRole:"
					+ (agentRole != null ? agentRole.getCode() : null));
		}
		Flux<IGPartialOperation<ResponseType>> iteration = createResponse(chatRequestContext, agentConfig, request,
				network, contextAgentPersona, notificationSink, session, privateMemory, agentModel, agentRole,
				agentPrompt, runAs, callBacksListener);
		return iteration.subscribeOn(runAs.wrap(Schedulers.boundedElastic()))
				.doOnSubscribe(s -> LOGGER.debug("Begin reactive agentic iteration subscription {} ", getId()))
				.doOnComplete(() -> LOGGER.debug("End agentic iteration {} ", getId()))
				.doOnError(th -> LOGGER.error("Error in reactive agentic iteration " + getId(), th));
	}

	protected abstract Flux<IGPartialOperation<ResponseType>> createResponse(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, RequestType request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink  notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<RequestType, ResponseType> mySessionContext, IGConfigurableChatModel agentModel,
			GAgentRole agentRole, GPromptTemplateConfig agentPrompt, ReactiveIdentityUtil runAs,
			ToolCallsListener callBacksListener) throws LLMConfigException, AgentException;

}
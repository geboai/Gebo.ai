package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.repository.AgentConfigRepository;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
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

public abstract class GAbstractReactiveAgentService<RequestType, ResponseType, NotificationObject, AggregatedResponses>
		extends GAbstractGenericalAgentService
		implements IGReactiveAgentService<RequestType, ResponseType, NotificationObject> {
	public GAbstractReactiveAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IAgentConfigDao configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao);

	}

	@Override
	public Flux<IGPartialOperation<ResponseType>> execute(IChatRequestContext chatRequestContext,
			GAgentConfig agentConfig, RequestType request, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink<NotificationObject> notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<RequestType, ResponseType> privateMemory, ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao)
			throws AgentException, LLMConfigException {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...)");
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
		ChatModelConfigOptions configOptions = new ChatModelConfigOptions(agentConfig.getTemperature(),
				agentConfig.getTopP(), agentConfig.getThinking(), allFunctions,
				createToolCallingManager(callBacksListener, allFunctions, runAs));
		IGConfigurableChatModel agentModel = copiedModel.cloneWithOptions(getId(), configOptions);
		final int maxLoop = agentConfig.getMaxLoopIterations() != null && agentConfig.getMaxLoopIterations() > 0
				? agentConfig.getMaxLoopIterations()
				: 4; 
		final GPromptTemplateConfig agentPrompt = resolvePrompt(agentConfig.getCustomLoopPrompt(),
				agentConfig.getMainLoopPromptUseCode(), false);
		final GAgentRole agentRole = agentRoleDao.findByCode(agentConfig.getAgentRoleCode());
		final AtomicBoolean iterationFinished = new AtomicBoolean(false);
		final AtomicReference<List<AggregatedResponses>> aggregatedResponses = new AtomicReference(
				new ArrayList<AggregatedResponses>());
		Flux<IGPartialOperation<ResponseType>> out = Flux.defer(() -> {
			return Flux.range(0, maxLoop).concatMap(index -> {
				Flux<IGPartialOperation<ResponseType>> iterationStream = Flux.defer(() -> {
					return runAs.doRunAsWithReturn(() -> {
						try {
							if (!iterationFinished.get()) {
								if (LOGGER.isDebugEnabled()) {
									LOGGER.debug("Begin agentic iteration " + getId() + " index=" + index);
								}
								List<AggregatedResponses> pastResponses = aggregatedResponses.get();
								Flux<IGPartialOperation<ResponseType>> iteration = createResponse(request,
										pastResponses, agentModel, agentConfig, agentRole, index, maxLoop, agentPrompt,
										runAs, callBacksListener);
								Function<IGPartialOperation<ResponseType>, IGPartialOperation<ResponseType>> aggregator = createAggregator(
										aggregatedResponses);

								return iteration.subscribeOn(runAs.wrap(Schedulers.boundedElastic())).map(aggregator)
										.map(x -> {
											if (x.isLastMessage())
												iterationFinished.set(true);
											return x;
										}).doOnComplete(() -> LOGGER.debug("End agentic iteration {} index={}", getId(),
												index));
							} else
								return Flux.empty();
						} catch (Throwable th) {
							LOGGER.error("Error in agent execution", th);
							return Flux.just(IGPartialOperation.of(null,
									GUserMessage.errorMessage("Error in agent execution", th)));
						}
					});
				});
				return iterationStream;
			});

		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End execute(...)");
		}
		return out;
	}

	protected abstract Flux<IGPartialOperation<ResponseType>> createResponse(RequestType request,
			List<AggregatedResponses> pastResponses, IGConfigurableChatModel agentModel, GAgentConfig agentConfig,
			GAgentRole agentRole, Integer index, int maxLoop, GPromptTemplateConfig agentPrompt,
			ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener);

	protected abstract Function<IGPartialOperation<ResponseType>, IGPartialOperation<ResponseType>> createAggregator(
			AtomicReference<List<AggregatedResponses>> aggregatorList);

}
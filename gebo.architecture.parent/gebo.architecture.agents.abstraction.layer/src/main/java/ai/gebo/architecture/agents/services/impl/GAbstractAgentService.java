package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.definition.ToolDefinition;

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
import ai.gebo.architecture.agents.services.IGAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public abstract class GAbstractAgentService<RequestType, ResponseType, NotificationObject, AggregatedResponses> extends
		GAbstractGenericalAgentService implements IGAgentService<RequestType, ResponseType, NotificationObject> {

	public GAbstractAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IAgentConfigDao configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao);

	}

	@Override
	public ResponseType execute(IChatRequestContext chatRequestContext, GAgentConfig agentConfig,
			RequestType request, GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona, INotificationSink<NotificationObject> notificationSink, AgentsCollaborationSessionContext session, AgentPrivateSessionContext<RequestType, ResponseType> privateMemory, ReactiveIdentityUtil runAs) throws AgentException, LLMConfigException {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...)");
		}
		final ToolCallsListener callBacksListener = new ToolCallsListener();
		final int maxLoop = agentConfig.getMaxLoopIterations() != null && agentConfig.getMaxLoopIterations() > 0
				? agentConfig.getMaxLoopIterations()
				: 4;
		final GPromptTemplateConfig agentPrompt = resolvePrompt(agentConfig.getCustomLoopPrompt(),
				agentConfig.getMainLoopPromptUseCode(), false);
		final GAgentRole agentRole = agentRoleDao.findByCode(agentConfig.getAgentRoleCode());
		final AtomicBoolean iterationFinished = new AtomicBoolean(false);
		final List<AggregatedResponses> aggregatedResponses = new ArrayList<AggregatedResponses>();
		final IGConfigurableChatModel agentModel = getAgentModel(agentConfig, callBacksListener, runAs);
		ResponseType out = null;
		for (int i = 0; i < maxLoop; i++) {
			if (!iterationFinished.get()) {

				out = createResponse(request, aggregatedResponses, agentModel, agentConfig, agentRole, i, maxLoop,
						agentPrompt, runAs, callBacksListener);
				BiFunction<ResponseType, List<AggregatedResponses>, AggregatedResponses> aggregator = createAggregator(
						aggregatedResponses);
				if (aggregator != null) {
					AggregatedResponses historyStep = aggregator.apply(out, aggregatedResponses);
					aggregatedResponses.add(historyStep);

				}

			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End execute(...)");
		}
		return out;
	}

	protected abstract ResponseType createResponse(RequestType request, List<AggregatedResponses> pastResponses,
			IGConfigurableChatModel agentModel, GAgentConfig agentConfig, GAgentRole agentRole, int i, int maxLoops,
			GPromptTemplateConfig agentPrompt, ReactiveIdentityUtil runAs, ToolCallsListener callBacksListener)
			throws LLMConfigException;

	protected abstract BiFunction<ResponseType, List<AggregatedResponses>, AggregatedResponses> createAggregator(
			List<AggregatedResponses> aggregatorList);

}
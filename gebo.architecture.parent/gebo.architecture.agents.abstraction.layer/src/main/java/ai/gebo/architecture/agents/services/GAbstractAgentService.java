package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
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

public abstract class GAbstractAgentService<RequestType, ResponseType, NotificationObject, AggregatedResponses> extends
		GAbstractGenericalAgentService implements IGAgentService<RequestType, ResponseType, NotificationObject> {

	public GAbstractAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGRuntimeBinder runtimeBinder, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, runtimeBinder, securityService, agentRoleDao,
				rendererFactory);

	}

	@Override
	public ResponseType execute(IChatRequestContext chatRequestContext, GAgentConfig agentConfig, RequestType request,
			GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona,
			INotificationSink<NotificationObject> notificationSink, AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<RequestType, ResponseType> privateMemory, ReactiveIdentityUtil runAs)
			throws AgentException, LLMConfigException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...) agent service id:" + getId() + " agentConfig code:"
					+ (agentConfig != null ? agentConfig.getCode() : null) + " agentRoleCode:"
					+ (agentConfig != null ? agentConfig.getAgentRoleCode() : null));
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting agentic loop with maxLoopIterations:" + maxLoop);
		}
		ResponseType out = null;
		for (int i = 0; i < maxLoop; i++) {
			if (!iterationFinished.get()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Begin agentic iteration " + (i + 1) + "/" + maxLoop + " with "
							+ aggregatedResponses.size() + " aggregated past response(s)");
				}
				out = createResponse(request, aggregatedResponses, agentModel, agentConfig, agentRole, i, maxLoop,
						agentPrompt, runAs, callBacksListener);
				BiFunction<ResponseType, List<AggregatedResponses>, AggregatedResponses> aggregator = createAggregator(
						aggregatedResponses);
				if (aggregator != null) {
					AggregatedResponses historyStep = aggregator.apply(out, aggregatedResponses);
					aggregatedResponses.add(historyStep);

				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End agentic iteration " + (i + 1) + "/" + maxLoop);
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End execute(...) agent service id:" + getId());
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
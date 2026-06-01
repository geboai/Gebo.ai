package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
public abstract class GAbstractAgentService<RequestType, ResponseType, NotificationObject, AggregatedResponses>
		implements IGAgentService<RequestType, ResponseType, NotificationObject> {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern;
	protected IGPromptConfigDao promptsDao;

	@Override
	public Flux<IGPartialOperation<ResponseType>> execute(RequestType request, GAgentConfig agentConfig,
			INotificationSink<NotificationObject> notificationSink) throws AgentException, LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...)");
		}
		IGConfigurableChatModel copiedModel = chatModelsDao.findByModelReference(agentConfig.getChoosedModel());
		if (copiedModel == null)
			throw new LLMConfigException(
					"Referred model with code:" + agentConfig.getChoosedModel() + " is not configured");
		IGConfigurableChatModel agentModel = copiedModel.cloneWithTools(agentConfig.getEnabledTools(), getId());
		final int maxLoop = agentConfig.getMaxLoopIterations() != null && agentConfig.getMaxLoopIterations() > 0
				? agentConfig.getMaxLoopIterations()
				: 4;
		final GPromptTemplateConfig agentPrompt = resolvePrompt(agentConfig.getCustomLoopPrompt(),
				agentConfig.getMainLoopPromptUseCode(), false);
		final GPromptTemplateConfig completenessPrompt = resolvePrompt(agentConfig.getCompleteEvaluationPrompt(),
				agentConfig.getCompleteEvaluationPromptUseCode(), true);
		final AtomicBoolean iterationFinished = new AtomicBoolean(false);
		final AtomicReference<List<AggregatedResponses>> aggregatedResponses = new AtomicReference(
				new ArrayList<AggregatedResponses>());
		Flux<IGPartialOperation<ResponseType>> out = Flux.defer(() -> {
			return Flux.range(0, maxLoop).concatMap(index -> {
				Flux<IGPartialOperation<ResponseType>> iterationStream = Flux.defer(() -> {
					return runAs.doRunAsWithReturn(() -> {
						if (!iterationFinished.get()) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Begin agentic iteration " + getId() + " index=" + index);
							}
							List<AggregatedResponses> pastResponses = aggregatedResponses.get();
							Flux<IGPartialOperation<ResponseType>> iteration = createResponseFlux(request,
									pastResponses, agentModel, agentConfig, index, agentPrompt, completenessPrompt);
							Function<IGPartialOperation<ResponseType>, IGPartialOperation<ResponseType>> aggregator = createAggregator(
									aggregatedResponses);
							
							return iteration.subscribeOn(runAs.wrap(Schedulers.boundedElastic())).map(aggregator)
									.map(x -> {
										if (x.isLastMessage())
											iterationFinished.set(true);
										return x;
									}).doOnComplete(() -> LOGGER.debug("End agentic iteration {} index={}", getId(), index));
						} else
							return Flux.empty();
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

	private GPromptTemplateConfig resolvePrompt(GPromptTemplateConfig prompt, String useCode, boolean nullable)
			throws AgentException {
		GPromptTemplateConfig resolved = prompt != null ? prompt
				: useCode != null ? promptsDao.findByPromptUse(useCode) : null;
		if (resolved == null && !nullable)
			throw new AgentException("Mandatory prompt not present");
		return resolved;
	}

	protected abstract Flux<IGPartialOperation<ResponseType>> createResponseFlux(RequestType request,
			List<AggregatedResponses> pastResponses, IGConfigurableChatModel agentModel, GAgentConfig agentConfig,
			int i, GPromptTemplateConfig agentPrompt, GPromptTemplateConfig completenessPrompt);

	protected abstract <T extends Function<IGPartialOperation<ResponseType>, IGPartialOperation<ResponseType>>> T createAggregator(
			AtomicReference<List<AggregatedResponses>> aggregatorList);
}
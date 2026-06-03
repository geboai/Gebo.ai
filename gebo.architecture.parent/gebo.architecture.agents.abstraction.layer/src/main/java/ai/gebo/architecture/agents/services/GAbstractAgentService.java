package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@AllArgsConstructor
public abstract class GAbstractAgentService<RequestType, ResponseType, NotificationObject, AggregatedResponses>
		implements IGAgentService<RequestType, ResponseType, NotificationObject> {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected final IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected final IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern;
	protected final IGPromptConfigDao promptsDao;
	protected final GAgentConfigRepository configsRepository;
	protected final IGSecurityService securityService;

	@Override
	public Flux<IGPartialOperation<ResponseType>> execute(RequestType request, GAgentConfig agentConfig,
			INotificationSink<NotificationObject> notificationSink) throws AgentException, LLMConfigException {
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
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
		final GPromptTemplateConfig completenessPrompt = resolvePrompt(agentConfig.getCompleteEvaluationPrompt(),
				agentConfig.getCompleteEvaluationPromptUseCode(), true);
		ChatModelConfigOptions verificatorOptions = new ChatModelConfigOptions(agentConfig.getTemperature(),
				agentConfig.getTopP(), agentConfig.getThinking(), List.of(), null);
		IGConfigurableChatModel verificationModel = completenessPrompt != null
				? copiedModel.cloneWithOptions(getId() + "-verifier", verificatorOptions)
				: null;
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
								Flux<IGPartialOperation<ResponseType>> iteration = createResponseFlux(request,
										pastResponses, agentModel, verificationModel, agentConfig, index, maxLoop,
										agentPrompt, completenessPrompt, runAs, callBacksListener);
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

	private ToolCallingManager createToolCallingManager(ToolCallsListener callBacksListener, List<String> allFunctions,
			ReactiveIdentityUtil runAs) {
		final List<ToolCallback> wrapped = GAbstractConfigurableChatModel.wrapTools(runAs, callBacksListener,
				allFunctions, toolsRepositoryPattern);
		final Map<String, ToolCallback> map = new HashMap<>();
		for (ToolCallback toolCallback : wrapped) {
			map.put(toolCallback.getToolDefinition().name(), toolCallback);
		}
		return new AgentToolCallingManagerFactory(callBacksListener, allFunctions, wrapped, map).create();
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
			List<AggregatedResponses> pastResponses, IGConfigurableChatModel agentModel,
			IGConfigurableChatModel verificationModel, GAgentConfig agentConfig, int i, int maxLoops,
			GPromptTemplateConfig agentPrompt, GPromptTemplateConfig completenessPrompt, ReactiveIdentityUtil runAs,
			ToolCallsListener callBacksListener) throws LLMConfigException;

	protected abstract Function<IGPartialOperation<ResponseType>, IGPartialOperation<ResponseType>> createAggregator(
			AtomicReference<List<AggregatedResponses>> aggregatorList);

	protected static String extractContent(ChatResponse chatResponse) {
		if (chatResponse == null) {
			return "";
		}

		Generation result = chatResponse.getResult();
		if (result == null || result.getOutput() == null) {
			return "";
		}

		AssistantMessage output = result.getOutput();

		String text = output.getText();
		return text != null ? text : "";
	}

	protected static void inspectToolCalls(ChatResponse chatResponse, Vector<Object> rawToolCallsCumulator) {
		if (chatResponse == null) {
			return;
		}

		Generation result = chatResponse.getResult();
		if (result == null || result.getOutput() == null) {
			return;
		}

		AssistantMessage output = result.getOutput();

		/*
		 * Nota: con tool execution gestita internamente da Spring AI, spesso le
		 * tool-call intermedie non sono esposte nello stream applicativo. Spring AI
		 * documenta che, nel framework-controlled tool execution, i messaggi interni di
		 * tool execution non sono esposti all’utente.
		 */
		List<AssistantMessage.ToolCall> toolCalls = output.getToolCalls();

		if (!org.springframework.util.CollectionUtils.isEmpty(toolCalls)) {
			for (AssistantMessage.ToolCall toolCall : toolCalls) {

				rawToolCallsCumulator.add(toolCall);
			}
		}

		Map<String, Object> metadata = output.getMetadata();
		if (metadata != null && !metadata.isEmpty()) {
			Object rawToolCalls = metadata.get("tool_calls");
			if (rawToolCalls == null) {
				rawToolCalls = metadata.get("toolCalls");
			}
			if (rawToolCalls != null)
				rawToolCallsCumulator.add(rawToolCalls);

		}
	}

	protected static void inspectMetadata(ChatResponse chatResponse, Logger logger) {
		if (chatResponse == null) {
			return;
		}

		ChatResponseMetadata metadata = chatResponse.getMetadata();

		if (metadata != null) {
			Usage usage = metadata.getUsage();

			if (usage != null) {
				logger.debug("LLM token usage: promptTokens={}, completionTokens={}, totalTokens={}",
						usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
			}

			Object model = metadata.get("model");
			if (model != null) {
				logger.debug("LLM model: {}", model);
			}

			Object id = metadata.get("id");
			if (id != null) {
				logger.debug("LLM response id: {}", id);
			}
		}

		Generation result = chatResponse.getResult();
		if (result != null) {
			ChatGenerationMetadata generationMetadata = result.getMetadata();

			if (generationMetadata != null) {
				String finishReason = generationMetadata.getFinishReason();

				if (finishReason == null) {
					Object rawFinishReason = generationMetadata.get("FINISH_REASON");
					finishReason = Objects.toString(rawFinishReason, null);
				}

				if (finishReason != null) {
					logger.debug("LLM finish reason: {}", finishReason);
				}
			}
		}
	}

	@Override
	public List<GAgentConfig> getAccessibleConfigurations() {
		List<GAgentConfig> configs = this.configsRepository.findByAgentServiceId(getId());
		return securityService.filterCanDoAction(configs, true, AclGrantType.EXECUTE);
	}
}
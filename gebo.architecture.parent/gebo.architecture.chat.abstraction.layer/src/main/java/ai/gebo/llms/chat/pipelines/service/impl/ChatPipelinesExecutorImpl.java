package ai.gebo.llms.chat.pipelines.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.PipelineRoutingInfos;
import ai.gebo.llms.chat.pipelines.model.PipelineRoutingInfosMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.llms.chat.pipelines.service.IInputChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IIntermediateProcessingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.SinkUIEmitterImpl;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component

@AllArgsConstructor
public class ChatPipelinesExecutorImpl implements IChatPipelinesExecutor {
	protected final IChatPipelineStepServiceRepositoryPattern stepsServiceRepoPattern;
	protected final ChatPipelinesConfiguration pipelinesConfiguration;
	protected final IGChatFullSessionStateService sessionStateService;
	protected final IGChatSessionLifeCycleService chatSessionLifecycleService;
	protected final ChatProfilesRepository chatProfilesRepository;
	protected final IGeboThreadManager threadManager;
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatPipelinesExecutorImpl.class);

	protected void add(ChatPipelineExecutionRuntimeData runtimeData, IChatPipelineStepRuntimeData stepdata) {
		runtimeData.getExecutedSteps().add(stepdata);
		List<IStepContribution> enrichings = stepdata.getContextEnrichingContribution();
		int budget = runtimeData.getRemainingTokens();
		if (enrichings != null) {
			for (IStepContribution abstractContextEnrichingData : enrichings) {
				budget -= abstractContextEnrichingData.getRenderedTokensLength() != null
						? abstractContextEnrichingData.getRenderedTokensLength().intValue()
						: 0;

			}
			runtimeData.setRemainingTokens(budget);
		}
	}

	protected ChatPipelineExecutionRuntimeData executeUntillOutput(GeboChatRequest request, GeboChatResponse response,
			ISinkUIEmitter emitter, LinkedHashMap<String, Object> environment, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String pipelineCode, boolean streaming)
			throws ChatPipelineException, IOException, LLMConfigException, GeboChatSessionLifecycleException {
		ChatPipelineConfiguration config = getCfgOrDefault(pipelineCode);
		IChatPipelineStepService firstService = getStep(config.getStepInputId());
		IChatPipelineStepService routerService = getStep(config.getStepRouterId());
		LLMChatRequestResources resources = null;
		resources = this.chatSessionLifecycleService.startRequest(request, chatModel,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
		MinimalChatContext minimalChatContext = this.chatSessionLifecycleService.getMinimalChatContext(request,
				serviceModel.getContextLength() / 3);
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(config,
				chatModel.getContextLength(), resources, response, minimalChatContext, streaming);
		if (environment != null) {
			runtimeData.getSharedEnvironment().putAll(environment);
		}
		// putting a sintetic routing decision for the first 2 steps to mantain the
		// routing coherency
		runtimeData.getRoutingDecisions().add(
				new RoutingDecision(List.of(config.getStepInputId(), config.getStepRouterId()), null, null, Map.of()));
		if (firstService instanceof IInputChatPipelineStepService inputService) {
			IChatPipelineStepRuntimeData data = inputService.execute(runtimeData, emitter, chatModel, serviceModel);

			if (data.getEnvironmentContributions() != null) {
				runtimeData.getSharedEnvironment().putAll(data.getEnvironmentContributions());
			}
			add(runtimeData, data);
		} else
			throw new ChatPipelineException("The step service " + firstService.getStepId() + " is not an input one");
		if (routerService instanceof IRoutingChatPipelineStepService routing) {
			ai.gebo.llms.chat.pipelines.model.RoutingDecision routeData = routing.execute(runtimeData, emitter,
					chatModel, serviceModel);
			if (runtimeData.getChatResponse() != null) {
				runtimeData.getChatResponse().setPipelineRouterDecisionCode(routeData.getPipelineRouterDecisionCode());
			}
			add(runtimeData, routeData.getProcessedOutput());
			if (routeData.getProcessedOutput() != null
					&& routeData.getProcessedOutput().getEnvironmentContributions() != null) {
				runtimeData.getSharedEnvironment().putAll(routeData.getProcessedOutput().getEnvironmentContributions());
			}
			runtimeData.getRoutingDecisions().add(routeData);
		} else
			throw new ChatPipelineException("The step service " + firstService.getStepId() + " is not a routing one");
		IChatPipelineStepService nextService = null;
		do {
			nextService = getNextStep(runtimeData);
			if (nextService.getStepType() != IChatPipelineStepService.StepType.OUTPUT) {
				if (nextService instanceof IRoutingChatPipelineStepService router) {
					RoutingDecision routeData = router.execute(runtimeData, emitter, chatModel, serviceModel);
					if (runtimeData.getChatResponse() != null) {
						runtimeData.getChatResponse()
								.setPipelineRouterDecisionCode(routeData.getPipelineRouterDecisionCode());
					}
					add(runtimeData, routeData.getProcessedOutput());
					if (routeData.getProcessedOutput() != null
							&& routeData.getProcessedOutput().getEnvironmentContributions() != null) {
						runtimeData.getSharedEnvironment()
								.putAll(routeData.getProcessedOutput().getEnvironmentContributions());
					}
					runtimeData.getRoutingDecisions().add(routeData);
				} else if (nextService instanceof IIntermediateProcessingChatPipelineStepService intermediateStep) {
					IChatPipelineStepRuntimeData data = intermediateStep.execute(runtimeData, emitter, chatModel,
							serviceModel);
					add(runtimeData, data);
					if (data.getEnvironmentContributions() != null) {
						runtimeData.getSharedEnvironment().putAll(data.getEnvironmentContributions());
					}
				} else {
					throw new ChatPipelineException(
							"The step service " + nextService.getStepId() + " is not an intermediate service");
				}
			}
		} while (nextService != null && nextService.getStepType() != IChatPipelineStepService.StepType.OUTPUT);
		return runtimeData;
	}

	@Getter
	@AllArgsConstructor
	static class StreamingLastStep {
		final ChatPipelineExecutionRuntimeData runtimeData;
		final IChatPipelineStepService lastStepService;
		final GUserMessage errorMessage;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamingExecute(GeboChatRequest request,
			LinkedHashMap<String, Object> environment, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException, GeboChatException {

		GeboChatResponse response = this.chatSessionLifecycleService.createEmptyResponse(request);

		if (chatModel.getConfig() != null && chatModel.getConfig().getChoosedModel() != null) {
			response.setUsedChatModelCode(chatModel.getConfig().getChoosedModel().getCode());
		}

		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();

		Sinks.Many<GeboChatMessageEnvelope> sink = Sinks.many().unicast().onBackpressureBuffer();

		ISinkUIEmitter emitter = new SinkUIEmitterImpl(sink);

		Mono<StreamingLastStep> routingMono = Mono.fromCallable(() -> {
			return runAs.doRunAsWithReturnAndException(() -> {
				ChatPipelineExecutionRuntimeData runtimeData = executeUntillOutput(request, response, emitter,
						environment, chatModel, serviceModel, pipelineCode, true);

				IChatPipelineStepService nextStep = getNextStep(runtimeData);

				return new StreamingLastStep(runtimeData, nextStep, null);
			});
		}).subscribeOn(runAs.wrap(threadManager.getBoundedElastic())).onErrorResume(th -> {
			GUserMessage errorMessage = GUserMessage.errorMessage("Exception while streaming chat pipeline", th);

			return Mono.just(new StreamingLastStep(null, null, errorMessage));
		}).cache();

		Flux<GeboChatMessageEnvelope> sideChannelFlux = sink.asFlux()
				.subscribeOn(runAs.wrap(threadManager.getBoundedElastic()));

		Flux<GeboChatMessageEnvelope> mainFlux = routingMono.flatMapMany(lastStepData -> {
			try {
				if (lastStepData.getErrorMessage() != null) {
					return Flux.just(new GeboChatMessageEnvelope(lastStepData.getErrorMessage()));
				}

				if (lastStepData
						.getLastStepService() instanceof IStreamingOutputChatPipelineService streamingOutputService) {

					return runAs.doRunAsWithReturnAndException(() -> {
						Flux<GeboChatMessageEnvelope> routingInfoFlux = Flux
								.just(buildRoutingInfos(lastStepData.getRuntimeData(), streamingOutputService, response,
										chatModel, serviceModel));

						Flux<GeboChatMessageEnvelope> outputFlux = streamingOutputService
								.execute(lastStepData.getRuntimeData(), emitter, chatModel, serviceModel);

						return Flux.concat(routingInfoFlux, outputFlux);
					});
				}

				throw new ChatPipelineException("The step service " + lastStepData.getLastStepService().getStepId()
						+ " is not a streaming one");

			} catch (Throwable th) {
				GUserMessage errorMessage = GUserMessage.errorMessage("Exception while streaming chat pipeline", th);

				return Flux.just(new GeboChatMessageEnvelope(errorMessage));
			}
		}).doFinally(signalType -> {
			emitter.complete();
		}).subscribeOn(runAs.wrap(threadManager.getBoundedElastic()));

		return Flux.defer(() -> Flux.merge(sideChannelFlux, mainFlux))
				.subscribeOn(runAs.wrap(threadManager.getBoundedElastic()));
	}

	protected PipelineRoutingInfosMessageEnvelope buildRoutingInfos(ChatPipelineExecutionRuntimeData runtimeData,
			IStreamingOutputChatPipelineService streamingOutputService, GeboChatResponse response,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) {
		PipelineRoutingInfosMessageEnvelope data = new PipelineRoutingInfosMessageEnvelope();
		PipelineRoutingInfos content = new PipelineRoutingInfos();
		content.setPipelineRouterDecisionCode(response.getPipelineRouterDecisionCode());
		content.setChatModel(
				chatModel != null && chatModel.getConfig() != null && chatModel.getConfig().getChoosedModel() != null
						? chatModel.getConfig().getChoosedModel().getCode()
						: null);
		content.setServiceModel(serviceModel != null && serviceModel.getConfig() != null
				&& serviceModel.getConfig().getChoosedModel() != null
						? serviceModel.getConfig().getChoosedModel().getCode()
						: null);
		data.setContent(content);
		if (streamingOutputService != null && !streamingOutputService.getRequiredParameters().isEmpty()) {
			// Copied parameters to the UI infos
			for (StepEnvironmentParameter param : streamingOutputService.getRequiredParameters()) {
				content.getParameters().put(param.getParamName(),
						runtimeData.getSharedEnvironment().get(param.getParamName()));
			}
		}
		runtimeData.getRoutingDecisions().forEach(x -> {
			x.getFutureRoute().forEach(stepId -> {
				data.getContent().getStepIds().add(stepId);
			});
		});
		return data;
	}

	@Override
	public GeboChatResponse execute(GeboChatRequest request, LinkedHashMap<String, Object> environment,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException, GeboChatSessionLifecycleException {
		GeboChatResponse response = this.chatSessionLifecycleService.createEmptyResponse(request);
		ChatPipelineExecutionRuntimeData runtimeData = executeUntillOutput(request, response, null, environment,
				chatModel, serviceModel, pipelineCode, false);
		IChatPipelineStepService nextStep = getNextStep(runtimeData);
		if (nextStep instanceof IOutputChatPipelineService outputService) {
			return outputService.execute(runtimeData, chatModel, serviceModel);
		}
		throw new ChatPipelineException(
				"The step service " + nextStep.getStepId() + " is not an IOutputChatPipelineService");
	}

	private IChatPipelineStepService getStep(String id) throws ChatPipelineException {
		IChatPipelineStepService service = this.stepsServiceRepoPattern.findByCode(id);
		if (service == null)
			throw new ChatPipelineException("The step service " + id + " does not exist");
		return service;
	}

	private IChatPipelineStepService getNextStep(ChatPipelineExecutionRuntimeData runtimeData)
			throws ChatPipelineException {
		if (runtimeData.getRoutingDecisions() == null || runtimeData.getRoutingDecisions().isEmpty())
			throw new ChatPipelineException("No routing informations in this pipeline");
		List<IChatPipelineStepRuntimeData> stepsDone = runtimeData.getExecutedSteps();
		IChatPipelineStepRuntimeData lastStep = stepsDone != null && !stepsDone.isEmpty()
				? stepsDone.get(stepsDone.size() - 1)
				: null;
		if (lastStep == null)
			throw new ChatPipelineException("executing with no steps done in the runtime infos");

		String stepId = lastStep.getStepId();
		int routingDecisionIndex = 0;
		RoutingDecision currentRoutingThread = null;
		for (RoutingDecision r : runtimeData.getRoutingDecisions()) {
			if (r.getFutureRoute().contains(stepId)) {
				currentRoutingThread = r;
				break;
			} else {
				routingDecisionIndex++;
			}
		}
		if (currentRoutingThread == null)
			throw new ChatPipelineException("The actual executed step " + stepId + " is not any routing perspective");
		int index = currentRoutingThread.getFutureRoute().indexOf(stepId);
		if (index < 0)
			throw new ChatPipelineException(
					"The actual executed step " + stepId + " is not in the actual routing perspective");
		if (index + 1 < currentRoutingThread.getFutureRoute().size()) {
			return getStep(currentRoutingThread.getFutureRoute().get(index + 1));
		} else {
			routingDecisionIndex++;
			index = 0;
			if (routingDecisionIndex < runtimeData.getRoutingDecisions().size()) {
				currentRoutingThread = runtimeData.getRoutingDecisions().get(routingDecisionIndex);
				if (index < currentRoutingThread.getFutureRoute().size()) {
					return getStep(currentRoutingThread.getFutureRoute().get(index));
				}
			}
		}
		throw new ChatPipelineException("Reaching out of execution line without any output stage");
	}

	private ChatPipelineConfiguration getCfgOrDefault(String code) throws ChatPipelineException {
		if (code != null) {
			Optional<ChatPipelineConfiguration> matching = pipelinesConfiguration.getPipelines().stream()
					.filter(x -> x.getCode() != null && x.getCode().equals(code)).findFirst();
			if (matching.isPresent())
				return matching.get();
			throw new ChatPipelineException("cannot find chat pipeline config with code=" + code);
		} else {
			Optional<ChatPipelineConfiguration> matching = pipelinesConfiguration.getPipelines().stream()
					.filter(x -> x.isDefaultPipeline()).findFirst();
			if (matching.isPresent())
				return matching.get();
			throw new ChatPipelineException("cannot find default chat pipeline config");
		}
	}

}
